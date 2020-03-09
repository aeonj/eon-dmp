/**
 * 树列表
 * @param compid 页面树Div
 * @param config 参数信息
 *		source : config.source,  //系统要素
 *		isdirect : false,   //是否直接从数据库查询
 *		istable : false,    //是否物理表
 *		isfulldata : false,    //是否获取所有字段数据
 *		isfulllevel : false,    //是否获取所有级次
 *		condition : "",     //sql条件
 *		ispermission : false,   //是否启用数据权限
 *		selectmodel : "single",   //是否复选  single单选  multiple复选
 *		onlyname : false,     //是否只显示名称
 *		checkids : config.checkids,   //设置选中的id集合，以逗号分隔
 *		checkmodel ：cascade,      //复选时，是否级联选中，  nocascade单选
 *		values : config.values     //设置默认值
 * 		
 * @param sets 原始ztree参数
 */
function assist_ztree(compid,config,sets) {
	var otherParam = {
		source : config.source,  //系统要素
		isdirect : config.isdirect || false,   //是否直接从数据库查询
		istable : config.istable || false,    //是否物理表
		isfulldata : config.isfulldata || false,    //是否获取所有字段数据
		isfulllevel : config.isfulllevel || false,    //是否获取所有级次
		condition : config.condition || "",     //sql条件
		ispermission : config.ispermission || false,   //是否启用数据权限
		selectmodel : config.selectmodel || "single",   //是否复选
		onlyname : config.onlyname || false,     //是否只显示名称
		checkids : config.checkids,
		values : config.values
	};
	$.extend(otherParam,config.otherParam);
	var checkbox = false;
	if (typeof config.selectmodel!='undefined') {
		checkbox = config.selectmodel=="multiple";
	}
	var chkboxType= { "Y": "ps", "N": "ps" };
	if (typeof config.checkmodel!='undefined') {
		if (config.checkmodel!='cascade')
			chkboxType= { "Y": "", "N": "" };
	}
	var setting = {
            view: {
                selectedMulti: false
            },
			async: {
				enable: true,
				url: config.url || "/manage/ele_chk_tree.htm",
				autoParam: ["id=node"],
				otherParam: otherParam
			},
			data : {
				key : {
					children : "children",
					name : "text"
				},
				simpleData : {
					enable : false,
					pIdKey : "parent_id",
					rootPId: 0
				}
			},
			check: {
				enable: checkbox,
				chkboxType : chkboxType
			},
			edit : {
				enable : false
			}
		};
	if (sets!=undefined) {
		setting = $.extend(setting,sets);
	}
	$.fn.zTree.init(jQuery("#"+compid), setting);
}
/**
 * 设置勾选的节点
 * @param elid 树ID
 * @param ids 选中的节点集合
 */
function checked_ztree(elid,ids) {
	var arr = [];
	if (ids instanceof Array) {
		arr = ids;
	} else {
		arr = ids.split(',');
	}
	function filter(node) {
		return (containsArray(arr,node.id+""));
	}
	var treeObj = $.fn.zTree.getZTreeObj(elid);
	if (treeObj!=null) {
		var nodes = treeObj.getCheckedNodes(true);
		for (var i=0, l=nodes.length; i < l; i++) {
			treeObj.checkNode(nodes[i], false, false);
			nodes[i].checkedOld = nodes[i].checked;
		}
		nodes = treeObj.getNodesByFilter(filter); // 查找节点集合
		for (var i=0, l=nodes.length; i < l; i++) {
			treeObj.checkNode(nodes[i], true, true);
			nodes[i].checkedOld = nodes[i].checked;
		}
	}
}
/**
 * 设置禁用所有节点
 * @param elid 树UI元素ID
 * @param val 是否禁用，true/false 
 */
function disabled_ztree(elid,val) {
	var treeObj = $.fn.zTree.getZTreeObj(elid);
	if (treeObj!=null) {
		var nodes = treeObj.getNodes();
		for (var i=0, l=nodes.length; i < l; i++) {
			treeObj.setChkDisabled(nodes[i], val, false, true);
		}
	}
}
(function($){
	var TreeField = function(element, config, options) {
        this.parentEl = 'body';
        this.element = $(element);
        this.elementid = this.element.attr('id');
        this.elementname = this.element.attr('name');
        this.treepid = this.elementid+'-tree';
        this.treeid = this.elementid+'-subtree';
        //html template for the tree UI
        if (typeof config.template !== 'string') {
        	config.template = '<div id="' +this.treepid+'" class="seldown dropdown-menu" style="display:none;">' +
        		'<a id="' +this.treeid+'_load">正在加载数据...</a><ul id="' +this.treeid+'" class="ztree ztree-left" style="margin-top:0; min-width:260px; width:260px;"></ul>' +
        		'</div>';
        }
        $('#'+this.treepid).remove();
        this.parentEl = (config.parentEl && $(config.parentEl).length) ? $(config.parentEl) : $(this.parentEl);
        this.container = $(config.template).appendTo(this.parentEl);
        this.element.removeAttr('name');
        var hiddenel = '<input type="hidden" name="'+this.elementname+'" value="'+this.element.val()+'" />';
        this.hiddenel = $(hiddenel).insertBefore(this.element);
        if (typeof options=='undefined') {
        	options = {callback:{}};
        }
        config.checkids = this.hiddenel.val();
        var eletext = this.element.attr('text');
        if (typeof eletext!="undefined") {
        	this.element.val(eletext);
        }
        var comp = this;
        var onAfterClick = options.callback.onAfterClick;
        $.extend(options.callback,{
        	beforeClick: function(treeId, treeNode) {
        		var selectmodel = config.selectmodel || "single";
        		if (selectmodel=="single") {
	        		// all:所有结点都可选中
	        	    // folder:只有目录（非叶子和非根结点）可选
	        	    // leaf：只有叶子结点可选
	        		var nodemodel = config.nodemodel || "leaf";
	        		if (nodemodel=="leaf") {
		    			var check = (treeNode && !treeNode.isParent);
		    			return check;
	        		} else if (nodemodel=="folder") {
		    			var check = (treeNode && treeNode.isParent);
		    			return check;
	        		} else if (nodemodel=="rgchild") {
	        			if (typeof config.rgcodes!='undefined') {
	        				if (typeof treeNode.rg_code!='undefined') {
			        			var rgcodes = config.rgcodes;
			        			var check = (treeNode && rgcodes.indexOf(treeNode.rg_code)>=0);
				    			return check;
	        				}
	        			}
	        		}
	        		return true;
    			} else {
    				var zTree = $.fn.zTree.getZTreeObj(comp.treeid);
    				zTree.checkNode(treeNode, !treeNode.checked, null, true);
    				return false;
    			}
    		},
			onClick: function (e, treeId, treeNode) {
				var selectmodel = config.selectmodel || "single";
				if (selectmodel=="single") {
					var zTree = $.fn.zTree.getZTreeObj(comp.treeid);
					nodes = zTree.getSelectedNodes();
					vtext = "";
					val = "";
					nodes.sort(function compare(a,b){return a.id-b.id;});
					for (var i=0; i<nodes.length; i++) {
						vtext += nodes[i].name + ",";
						if (config.isCodeAsValue)
							val += nodes[i].code + ",";
						else
							val += nodes[i].id + ",";
					}
					if (vtext.length > 0 ) {
						vtext = vtext.substring(0, vtext.length-1);
						val = val.substring(0, val.length-1);
					}
					comp.element.val(vtext);
					comp.hiddenel.val(val);
					comp.hideMenu();
				}
				if (typeof onAfterClick!='undefined') {
					onAfterClick(e,treeId,treeNode);
				}
			},
			onCheck : function(e, treeId, treeNode) {
				var selectmodel = config.selectmodel || "single";
				if (selectmodel=="multiple") {
					var checkmodel = config.checkmodel || "cascade";
					var zTree = $.fn.zTree.getZTreeObj(comp.treeid);
					nodes = zTree.getCheckedNodes(true);
					vtext = "";
					val = "";
					for (var i=0; i<nodes.length; i++) {
						if (checkmodel=="cascade") {
							if (!nodes[i].isParent) {
								vtext += nodes[i].name + ",";
								if (config.isCodeAsValue)
									val += nodes[i].code + ",";
								else
									val += nodes[i].id + ",";
							}
						} else {
							vtext += nodes[i].name + ",";
							if (config.isCodeAsValue)
								val += nodes[i].code + ",";
							else
								val += nodes[i].id + ",";
						}
					}
					if (val.length > 0 ) {
						vtext = vtext.substring(0, vtext.length-1);
						val = val.substring(0, val.length-1);
					}
					comp.element.val(vtext);
					comp.hiddenel.val(val);
				}
			},
			onAsyncSuccess: function(event, treeId, treeNode, msg) {
				$("#" +treeId+'_load').hide();
				var selectmodel = config.selectmodel || "single";
				if (selectmodel=="single") {
					var treeObj = $.fn.zTree.getZTreeObj(treeId);
					var node;
					if (config.isCodeAsValue)
						node =treeObj.getNodeByParam("code",config.checkids,null);
					else
						node = treeObj.getNodeByTId(config.checkids);
					if (node!=null) {
						if (typeof eletext=="undefined") {
							comp.element.val(node.name);
						}
						treeObj.expandNode(node, true);
					} else {
						//异步加载情况请在初始化树后直接赋值
						var text = comp.element.attr("text");
						comp.element.val(text);
					}
				} else {
					if (typeof eletext=="undefined") {
						//var nodes = this.getCheckedNodes(true);
					}
				}
			}
		});
		assist_ztree(comp.treeid,config,options);
        if (this.element.parent().is('div .input-group')) {
        	this.element.parent().on('click', function(){
        		comp.showMenu();
			});
		} else {
			this.element.on('click', function(){
				comp.showMenu();
			});
		}
    	var filterNodes = function(node,text) {
    		if (node==null)
    			return false;
    		else
    			return (node.code.indexOf(text)>-1 || node.name.indexOf(text)>-1);
    	}
    	var getFindNextNode = function (selnode) {
    		var nextnode = selnode.getNextNode();
    		if (nextnode==null) {
    			var parnode = selnode.getParentNode();
    			if (parnode!=null) {
    				return getFindNextNode(parnode);
    			} else {
    				return null;
    			}
    		} else {
    			return nextnode;
    		}
    	}
    	//根据当前节点查找下一节点
    	var findThisNodeChild = function (treeObj,selnode) {
    		if (selnode==null) {
    			return null;
    		}
			var node = treeObj.getNodesByFilter(filterNodes,true,selnode,comp.element.val());
			if (node==null) {
				var nextnode = getFindNextNode(selnode);
				if (filterNodes(nextnode,comp.element.val())) {
					return nextnode;
				} else {
					return findThisNodeChild(treeObj,nextnode);
				}
			} else {
				return node;
			}
    	}
        this.element.on('keypress', function(event){
        	if (event.keyCode==13) {
    			comp.showMenu();
    			var treeObj = $.fn.zTree.getZTreeObj(comp.treeid);
    			var nodes = treeObj.getSelectedNodes();
    			var node = null;
    			if (nodes.length>0) {
    				node = findThisNodeChild(treeObj,nodes[0]);
    			} else {
    				node = treeObj.getNodesByFilter(filterNodes,true,null,comp.element.val());
    			}
    			if (node!=null) 
    				treeObj.selectNode(node);
    			else
    				treeObj.cancelSelectedNode();
        	}
        })
		this.onBodyDown = function(event) {
			if (!(event.target.id == comp.elementid || event.target.id == comp.treeid || $(event.target).parents("#" +comp.treepid).length>0)) {
				comp.hideMenu();
			}
		}
	};
	
	TreeField.prototype = {
	    constructor: TreeField,
	    showMenu : function() {
			var offset = this.element.offset();
			this.container.css({left:offset.left + "px", top:offset.top + this.element.outerHeight() + "px", height:"300px"}).slideDown("fast");
			this.container.find("#" +this.treeid).css({width:this.element.parent().outerWidth()});
			$(this.parentEl).bind("mousedown", this.onBodyDown);
	    },
	    hideMenu : function() {
			$("#" +this.treepid).fadeOut("fast");
			$(this.parentEl).unbind("mousedown", this.onBodyDown);
		},
		setSource : function(source) {
			var treeObj = $.fn.zTree.getZTreeObj(this.treeid);
			treeObj.setting.async.otherParam.source=source;
			config.async.otherParam.source=source;
			treeObj.reAsyncChildNodes(null, "refresh");
		},
        remove: function() {
            $.fn.zTree.destroy(this.treeid);
            this.container.remove();
            this.element.attr('name',this.hiddenel.attr('name'));
            this.hiddenel.remove();
            this.element.off('.treefield');
            this.element.removeData();
        }
		
	};
	
	$.fn.treefield = function(config,options) {
		this.each(function() {
			var el = $(this);
			if (el.data('treefield')) 
				el.data('treefield').remove();
			el.data('treefield', new TreeField(el, config, options));
		});
		return this;
	};
})(jQuery);
