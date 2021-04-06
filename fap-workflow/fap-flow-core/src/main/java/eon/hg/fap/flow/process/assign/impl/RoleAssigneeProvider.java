package eon.hg.fap.flow.process.assign.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import eon.hg.fap.common.properties.PropertiesHelper;
import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.common.util.metatype.impl.HashDto;
import eon.hg.fap.core.query.QueryObject;
import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.tools.JsonHandler;
import eon.hg.fap.db.model.primary.PartDetail;
import eon.hg.fap.db.model.primary.PartGroup;
import eon.hg.fap.db.model.primary.Role;
import eon.hg.fap.db.model.primary.User;
import eon.hg.fap.db.service.IOrgTypeService;
import eon.hg.fap.db.service.IPartGroupService;
import eon.hg.fap.db.service.IRoleService;
import eon.hg.fap.db.service.IUserService;
import eon.hg.fap.flow.env.Context;
import eon.hg.fap.flow.model.ProcessInstance;
import eon.hg.fap.flow.model.variable.Variable;
import eon.hg.fap.flow.process.assign.AssigneeProvider;
import eon.hg.fap.flow.process.assign.Entity;
import eon.hg.fap.flow.process.assign.PageQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class RoleAssigneeProvider implements AssigneeProvider {
    private static Logger log = LoggerFactory.getLogger(RoleAssigneeProvider.class);

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IPartGroupService partGroupService;

    @Autowired
    private IOrgTypeService orgTypeService;

    /**
     * 设计器层面是否要用树形结构进行展示
     *
     * @return 返回true，表示设计器会用树形加载当前任务处理人列表
     */
    @Override
    public boolean isTree() {
        return false;
    }

    /**
     * @return 返回当前任务处理人提供者名称，比如员工列表，部门列表等
     */
    @Override
    public String getName() {
        return "指定角色";
    }

    /**
     * 分页方式查询返回具体的任务处理人，可以是具体的人，也可以是部门等之类容器型对象
     *
     * @param pageQuery 用于包装分页信息的查询对象
     * @param parentId  上级实体对象的ID，可能为空
     */
    @Override
    public void queryEntities(PageQuery<Entity> pageQuery, String parentId) {
        QueryObject qo = new QueryObject();
        qo.setCurrentPage(pageQuery.getPageIndex());
        qo.setPageSize(pageQuery.getPageSize());
        IPageList pageList = roleService.list(qo);
        pageQuery.setRecordCount(pageList.getRowCount());
        List<Entity> entities = new ArrayList<>();
        for (int i = 0 ; i<pageList.getResult().size(); i++) {
            Role role = (Role) pageList.getResult().get(i);
            Entity entity=new Entity(Convert.toStr(role.getId()),role.getRoleName());
            entities.add(entity);
        }
        pageQuery.setResult(entities);
    }

    /**
     * 根据指定的处理人ID，返回具体的任务处理人用户名
     *
     * @param entityId        处理人ID，可能是一个用户的用户名，这样就是直接返回这个用户名，也可能是一个部门的ID，那么就是返回这个部门下的所有用户的用户名等
     * @param context         context 流程上下文对象
     * @param processInstance 流程实例对象
     * @return 返回一个或多个任务处理人的ID
     */
    @Override
    public Collection<String> getUsers(String entityId, Context context, ProcessInstance processInstance) {
        String clsName = processInstance.getTag();
        List<Variable> variables = context.getProcessService().getProcessVariables(processInstance);
        Dto dto = new HashDto();
        dto.put("id",Convert.toLong(entityId));
        List<Role> roles = roleService.query("select obj from Role obj join fetch obj.users u where obj.id=:id",dto,-1,-1);
        Role role = roles.get(0);
        List<String> users=new ArrayList<String>();
        for (User user : role.getUsers()) {
            boolean canAdd = true;
            if (user.getPg_id()!=null) {
                PartGroup pg = partGroupService.getObjById(user.getPg_id());
                if (pg!=null) {
                    List<Dto> lstBelong = JsonHandler.parseList(pg.getBelong_source());
                    for (Dto dtoBelong : lstBelong) {
                        boolean is_pass = true;
                        //获取权限字段
                        for (Variable ele : variables) {
                            if (ele.getKey().equals(dtoBelong.getString("eleCode")+"_id")) {
                                if (!isPassPartGroup(pg, ele.getKey(), Convert.toStr(ele.getValue()))) {
                                    is_pass = false;
                                    log.info("当前用户没有该笔业务数据的访问权限，业务字段名为{}",ele.getKey());
                                    break;
                                }
                            }
                        }
                        if (!is_pass) {
                            canAdd = false;
                            break;
                        }
                    }
                }
            }
            if (canAdd) {
                users.add(Convert.toStr(user.getId()));
            }
        }
        return users;
    }

    /**
     * @return 是否禁用当前任务处理人提供器
     */
    @Override
    public boolean disable() {
        return false;
    }

    private boolean isPassPartGroup(PartGroup pg, String source, String value) {
        if (StrUtil.isBlank(value)) {
            return true;
        } else {
            for (PartDetail pd : pg.getDetails()) {
                if (pd.getEleCode().equals(source) && pd.getValue().equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

}
