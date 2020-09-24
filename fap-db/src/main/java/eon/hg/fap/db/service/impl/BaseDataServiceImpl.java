/**
 * <p>Copyright: Copyright (c) 2011-2012</p>
 * <p>最后更新日期:2016年3月1日
 *
 * @author cxj
 */

package eon.hg.fap.db.service.impl;

import eon.hg.fap.common.CommUtil;
import eon.hg.fap.core.domain.virtual.SysMap;
import eon.hg.fap.core.query.QueryObject;
import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.dao.primary.BaseDataDao;
import eon.hg.fap.db.dao.primary.GenericDao;
import eon.hg.fap.db.model.mapper.BaseData;
import eon.hg.fap.db.service.IBaseDataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * 不使用泛型，因操作对象是动态调整的
 *
 * @author AEON
 */
@Service
@Transactional
public class BaseDataServiceImpl implements IBaseDataService {
    @Resource
    private BaseDataDao basedataDao;
    @Resource
    private GenericDao genericDao;

    @Override
    public BaseData save(BaseData basedata) {
        //获取父级
        if (basedata.getParent_id()!=null) {
            BaseData parent = this.getObjById(basedata.getClass(), basedata.getParent_id());
            if (parent.isLeaf()) {
                parent.setLeaf(false);
                this.basedataDao.update(parent);
            }
            basedata.setLevel((byte) (parent.getLevel().byteValue()+1));
        } else {
            basedata.setLevel((byte) 1);
        }
        BaseData bd = this.basedataDao.save(basedata);
        basedata.setTreepath(basedata.getParentpath());
        this.basedataDao.update(bd);
        return bd;
    }

    @Override
    public BaseData update(BaseData basedata) {
        //获取父级
        if (!CommUtil.null2Long(basedata.getParent_id()).equals(CommUtil.null2Long(basedata.getOld_parent_id()))) {
            if (basedata.getOld_parent_id()!=null) {
                //处理原父节点，更新leaf状态
                BaseData parentOld = this.getObjById(basedata.getClass(), basedata.getOld_parent_id());
                if (parentOld!=null) {
                    QueryObject qo = new QueryObject();
                    qo.addQuery("parent_id", new SysMap("parent_id", parentOld.getId()), "=");
                    List<BaseData> childs = this.find(basedata.getClass(), qo);
                    if (childs != null && childs.size() == 0) {
                        parentOld.setLeaf(true);
                        this.basedataDao.update(parentOld);
                    }
                }
            }
            int level;
            if (basedata.getParent_id()!=null) {
                //处理新父节点
                BaseData parentNew = this.getObjById(basedata.getClass(), basedata.getParent_id());
                if (parentNew.isLeaf()) {
                    parentNew.setLeaf(false);
                    this.basedataDao.update(parentNew);
                }
                level = parentNew.getLevel().intValue() + 1;
            } else {
                level = 1;
            }
            //更新自己及儿子节点的级次
            basedata.setLevel((byte) level);
            Stack<BaseData> stack = new Stack();
            stack.push(basedata);
            //前序遍历
            while (!stack.isEmpty()) {
                BaseData curr = stack.pop();
                QueryObject qo = new QueryObject();
                qo.addQuery("parent_id", new SysMap("parent_id", curr.getId()), "=");
                List<BaseData> childs = this.find(basedata.getClass(), qo);
                if (childs!=null && childs.size()>0) {
                    level = curr.getLevel().intValue() + 1;
                    for (BaseData child : childs) {
                        child.setLevel((byte) level);
                        stack.push(child);
                    }
                    curr.setLeaf(false);
                } else {
                    curr.setLeaf(true);
                }
                curr.setTreepath(curr.getParentpath());
                this.basedataDao.update(curr);
            }
        }
        return basedata;
    }


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public synchronized BaseData getObjById(Class clz, Long id) {
        if (id==null) return null;
        this.basedataDao.setEntityClass(clz);
        return this.basedataDao.get(id);
    }

    @Override
    public void delete(Class clz, Long id) {
        BaseData bd = this.getObjById(clz,id);
        if (bd!=null) {
            Long parent_id = bd.getParent_id();
            Stack<BaseData> stack = new Stack();
            Stack<BaseData> stackLast = new Stack();
            stack.push(bd);
            //后序遍历
            while (!stack.isEmpty()) {
                BaseData curr = stack.pop();
                stackLast.push(curr);
                QueryObject qo = new QueryObject();
                qo.addQuery("parent_id", new SysMap("parent_id", curr.getId()), "=");
                List<BaseData> childs = this.find(clz, qo);
                for (BaseData child : childs) {
                    stack.push(child);
                }
            }
            while (!stackLast.isEmpty()) {
                this.basedataDao.remove(stackLast.pop());
            }
            //更新父亲节点
            if (parent_id!=null) {
                BaseData parent = this.getObjById(clz, parent_id);
                if (parent != null) {
                    QueryObject qo = new QueryObject();
                    qo.addQuery("parent_id", new SysMap("parent_id", parent.getId()), "=");
                    List<BaseData> childs = this.find(clz, qo);
                    if (childs != null && childs.size() == 0) {
                        parent.setLeaf(true);
                        this.basedataDao.update(parent);
                    }
                }
            }
        }
    }

    @Override
    public void batchDelete(Class clz, List<Long> ids) {
        this.basedataDao.setEntityClass(clz);
        for (Long id : ids) {
            this.delete(clz,id);
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public IPageList list(Class clz, IQueryObject properties) {
        if (clz!=null) {
            this.basedataDao.setEntityClass(clz);
            return basedataDao.list(properties);
        } else {
            return genericDao.list(properties);
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<BaseData> query(String query, Map params, int begin, int max) {
        return this.basedataDao.query(query, params, begin, max);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public synchronized List<BaseData> find(Class clz, IQueryObject properties) {
        if (properties == null) {
            return null;
        }
        String query = properties.getQuery();
        String construct = properties.getConstruct();
        Map params = properties.getParameters();
        this.basedataDao.setEntityClass(clz);
        return this.basedataDao.find(construct, query, params, -1, -1);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public synchronized BaseData getObjByProperty(Class clz, Object... fields) {
        this.basedataDao.setEntityClass(clz);
        return this.basedataDao.getOne(fields);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<BaseData> findBySql(String sql) {
        return this.basedataDao.findBySql(sql);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<BaseData> findBySql(String sql, Object[] obj) {
        return this.basedataDao.findBySql(sql, obj);
    }

}
