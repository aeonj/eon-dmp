package eon.hg.fap.web.manage.op;

import cn.hutool.core.convert.Convert;
import eon.hg.fap.common.CommUtil;
import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.common.util.metatype.impl.HashDto;
import eon.hg.fap.core.constant.AeonConstants;
import eon.hg.fap.core.domain.entity.IdEntity;
import eon.hg.fap.core.query.QueryObject;
import eon.hg.fap.core.security.SecurityUserHolder;
import eon.hg.fap.core.tools.JsonHandler;
import eon.hg.fap.db.model.primary.Menu;
import eon.hg.fap.db.model.primary.MenuGroup;
import eon.hg.fap.db.model.primary.Role;
import eon.hg.fap.db.model.primary.User;
import eon.hg.fap.db.service.IMenuGroupService;
import eon.hg.fap.db.service.IMenuService;
import eon.hg.fap.db.service.ISysConfigService;
import eon.hg.fap.db.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MenuOP extends TreeSort {
	@Autowired
	private ISysConfigService sysConfigService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IMenuService menuService;
	@Autowired
	private IMenuGroupService menuGroupService;
	@Autowired
	private UserOP userOp;


	private void addParentMenuTree(List<Dto> lst, Menu node) {
		if (node.getParent_id()!=null) {
			Menu parent = menuService.getObjById(node.getParent_id());
			if (parent!=null) {
				List<Dto> parlist = parent.getChild();
				if (!ContainsNode(parlist, node)) {
					parlist.add(MenuData2Dto(node));
				}
				addParentMenuTree(lst, parent);
			} else {
				if (!ContainsNode(lst,node)) {
					lst.add(MenuData2Dto(node));
				}
			}
		} else {
			if (!ContainsNode(lst,node)) {
				lst.add(MenuData2Dto(node));
			}
		}
	}

	public List<Dto> getCardMenuList(Collection<Menu> menus) {
		return getCardMenuList(menus,false);
	}
	/**
	 * 从底潮上遍历
	 * @param menus
	 * @return
	 */
	public List<Dto> getCardMenuList(Collection<Menu> menus, boolean displayAll) {
		List<Dto> lstTree = new ArrayList<>();
		for (Menu menu : menus) {
			if (displayAll || menu.isDisplay()) {
				if (!ContainsNode(lstTree, menu)) {
					addParentMenuTree(lstTree, menu);
				}
			}
		}
		sortAllMenuList(lstTree);
		return lstTree;
	}

    /**
     * 更新树节点
     * @param lst
     */
    public void updateTreesDisplay(List<Dto> lst) {
        for (Dto dto : lst) {
            List<Dto> childrens = (List<Dto>) dto.getList("children");
            if (CommUtil.isNotEmpty(dto.get("displayname"))) {
                dto.put("text", dto.get("displayname"));
            }
            dto.put("qtip",dto.get("name"));
            if (childrens==null || childrens.size()==0) {
                dto.remove("children");
            } else {
                updateTreesDisplay(childrens);
            }
        }
    }



    public List<MenuGroup> getCardMgs() {
		if (AeonConstants.SUPER_USER.equals(SecurityUserHolder.getOnlineUser().getUsername())) {
			if (sysConfigService.getSysConfig().isDisplay_menu_group()) {
				List<MenuGroup> menuGroups = this.menuGroupService.query("select obj from MenuGroup obj order by obj.sequence", null, -1, -1);
				for (MenuGroup menuGroup : menuGroups) {
					List<Dto> treeList = getCardMenuList(menuGroup.getMenus());
					CommUtil.updateTreesLeaf(treeList);
                    updateTreesDisplay(treeList);
					String menuListJson = JsonHandler.toJson(treeList);
					menuGroup.setMenujson(menuListJson);
				}
				return menuGroups;
			} else {
				List<Menu> menuList = this.menuService.query("select obj from Menu obj order by obj.sequence",null, -1,-1);
				List<Dto> treeList = getCardMenuList(menuList);
				CommUtil.updateTreesLeaf(treeList);
                updateTreesDisplay(treeList);
				List<MenuGroup> menuGroupList = new ArrayList<>();
				fillMenuGroupList(treeList, menuGroupList);
				return menuGroupList;
			}
		} else {
			List<User> list = this.userService.query("select user from User as user join fetch user.roles as role join fetch role.menus as menu where user.id=:id", new HashMap() {{
				put("id", CommUtil.null2Long(SecurityUserHolder.getOnlineUser().getUserid()));
			}}, -1, -1);
			if (list != null && list.size() > 0) {
				User user = list.get(0);
				if (sysConfigService.getSysConfig().isDisplay_menu_group()) {
					for (MenuGroup menuGroup : user.getMgs()) {
						List<Dto> treeList = getCardMenuList(menuGroup.getAuthmenus());
						CommUtil.updateTreesLeaf(treeList);
                        updateTreesDisplay(treeList);
						String menuListJson = JsonHandler.toJson(treeList);
						menuGroup.setMenujson(menuListJson);
					}
					return user.getMgs();
				} else {
					Set<Menu> menuSet = new HashSet<>();
					for (Role role : user.getRoles()) {
						menuSet.addAll(role.getMenus());
					}
					List<Dto> treeList = getCardMenuList(menuSet);
					CommUtil.updateTreesLeaf(treeList);
                    updateTreesDisplay(treeList);
					List<MenuGroup> menuGroupList = new ArrayList<>();
					fillMenuGroupList(treeList, menuGroupList);
					return menuGroupList;
				}
			} else {
				return new ArrayList<>();
			}
		}
    }

	private void fillMenuGroupList(List<Dto> treeList, List<MenuGroup> menuGroupList) {
		for (Dto dtoGroup : treeList) {
            MenuGroup mg = new MenuGroup();
            mg.setId(dtoGroup.getLong("id"));
            mg.setName(dtoGroup.getString("name"));
            mg.setSequence(dtoGroup.getInteger("sequence"));
            mg.setType("1");
            mg.setIcons(dtoGroup.getString("iconcls"));
            mg.setMenujson(JsonHandler.toJson(dtoGroup.get("children")));
            menuGroupList.add(mg);
        }
	}

	/**
	 * 菜单管理左侧树
	 * @param dtoParam
	 * @param containAllMenuGroup 是否加载所有的菜单组
	 * @return
	 */
	public List<Dto> getMenuTreeList(Dto dtoParam, boolean containAllMenuGroup, boolean displayAll) {
		Collection<Menu> menus = null;
		if (userOp.getCurrRegion()==null) {
			menus = this.menuService.query("select obj from Menu obj", null, -1, -1);
		} else {
			menus = userOp.getCurrRegion().getMenus();
		}
		List<Dto> lstTree;
		if (sysConfigService.getSysConfig().isDisplay_menu_group()) {
			lstTree = new ArrayList<Dto>();
			for (Dto dto : getCardMenuList(menus,displayAll)) {
				List<MenuGroup> menuGroups = this.menuGroupService.query("select obj from MenuGroup obj join fetch obj.menus as menu where menu.id=:menuid", new HashMap() {{
					put("menuid", dto.getLong("id"));
				}}, -1, -1);
				if (menuGroups != null && menuGroups.size() == 1) {
					MenuGroup mg = menuGroups.get(0);
					mg.getChild().add(dto);
					addMgTree(lstTree, menuGroups.get(0));

				}
			}
			//加载未挂载的菜单组
			if (containAllMenuGroup) {
				List<MenuGroup> menuGroups = this.menuGroupService.query("select obj from MenuGroup obj", new HashMap(), -1, -1);
				for (MenuGroup mg : menuGroups) {
					addMgTree(lstTree, mg);
				}
			}

			sortMenuList(lstTree);
		} else {
			lstTree = getCardMenuList(menus,displayAll);
		}

		boolean isChecked = dtoParam.getString("selectmodel").equalsIgnoreCase("multiple") ;
		if (isChecked) {
			CommUtil.setCheckTreeList(lstTree,dtoParam.getString("checkids"));
		} else {
			CommUtil.updateTreesLeaf(lstTree);
		}
		return lstTree;
	}

	public void sortAllMenuList(List<Dto> lstTree) {
		sortMenuList(lstTree);
		for (Dto dto : lstTree) {
			List<Dto> childs = (List<Dto>) dto.get("children");
			if (childs!=null && childs.size()>0) {
				sortAllMenuList(childs);
			}
		}

	}

	private void addMgTree(List<Dto> lst, MenuGroup node) {
		if (!CommUtil.ContainsNode(lst,(Map dto) ->(Convert.toLong(dto.get("mgid")).longValue()==node.getId().longValue()))) {
			lst.add(MgData2Dto(node));
		}
	}
	
	private boolean ContainsNode(List<Dto> lst, IdEntity node) {
        return CommUtil.ContainsNode(lst, (Map dto) -> (Convert.toLong(dto.get("id")).longValue()==node.getId().longValue()));
    }

	private Dto MgData2Dto(MenuGroup org) {
		Dto dto = new HashDto();
		dto.put("id", Long.valueOf(org.getId().longValue()+1000000));
		dto.put("code", org.getId());
		dto.put("name", org.getName());
		dto.put("text", org.getName());
		dto.put("leaf", false);
		dto.put("iconCls", org.getIcons());
		dto.put("mgid", org.getId());
		dto.put("children", org.getChild());
		dto.put("type", "1");
		dto.put("sequence", org.getSequence());
		return dto;
	}

	private Dto MenuData2Dto(Menu node) {
		Dto dto = new HashDto();
		dto.put("id", node.getId());
		dto.put("code", node.getMenuCode());
        dto.put("name", node.getMenuName());
        dto.put("text", node.getMenuName());
        dto.put("displayname",node.getDisplayName());
		if (node.isDisplay()) {
			dto.put("iconCls", node.getIcon());
		} else {
			dto.put("iconCls", "nodeNoIcon");
		}
		dto.put("leaf", false);
        dto.put("mgid", node.getMg().getId());
		dto.put("children", node.getChild());
		dto.put("parentid",node.getParent_id());
		dto.put("request",node.getRequest());
		dto.put("params",node.getParams());
		dto.put("scrollbar","");
		dto.put("height",0);
		dto.put("width",0);
		dto.put("expanded",node.getExpanded());
		dto.put("authkey",node.getAuthkey());
		dto.put("type", "2");
		dto.put("sequence", node.getSequence());
		return dto;
	}

	public List<Menu> sortMenus(List<Menu> menus) {
		Collections.sort(menus, new Comparator<Menu>() {  
            public int compare(Menu arg0, Menu arg1) {  
                int hits0 = arg0.getSequence();  
                int hits1 = arg1.getSequence();  
                if (hits1 < hits0) {  
                    return 1;  
                } else if (hits1 == hits0) {  
                    return 0;  
                } else {  
                    return -1;  
                }  
            }  
        });
		return menus;
	}

}
