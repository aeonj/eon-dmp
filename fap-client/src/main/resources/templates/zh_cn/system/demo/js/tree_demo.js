/**
 * 有源树
 */
Ext.onReady(function() {

// 定义工具栏
    var tb = new Ext.Toolbar();
    tb.add({
        text : '<span class="commoncss">单选有源树</span>',
        iconCls : 'imageIcon',
        handler : function(item) {
            initWindow1();
        }
    }, '-', {
        text : '<span class="commoncss">多选有源树</span>',
        iconCls : 'imageIcon',
        handler : function(item) {
            initWindow2();
        }
    });
    tb.render(Ext.getBody());

    function initWindow1() {
        // 表格工具栏
        var tbar = new Ext.Toolbar({
            items : [{
                text : '刷新根节点',
                iconCls : 'arrow_refreshIcon',
                handler : function() {
                    tree.getStore().reload();
                }
            }, {
                text : '刷新选中节点',
                iconCls : 'tbar_synchronizeIcon',
                handler : function() {
                    var selectModel = tree.getSelectionModel();
                    //1、获取选择节点
                    var selectNode = selectModel.selected.items[0];
                    //2、获取选择最后节点 不推荐，清空选择后就不对了
                    //var selectNode = selectModel.lastSelected();

                    if (Ext.isEmpty(selectNode)) {
                        Ext.Msg.alert('提示', '没有选中任何节点!');
                    } else {
                        tree.selectNodeByValue(selectNode,false);
                    }
                }
            }]
        });

        // 定义一个树面板
        var tree = Ext.create('Ext.vcf.AssistTree', {
            treeRootText : '预算单位',
            title : '',
            renderTo : 'treeDiv',
            tbar : tbar,
            source : 'EN',
            isCodeAsValue : false,  //是否将编码作为值 默认为false，即获取基础数据的ID作为值
            isDirect : false,    //默认为false，指是否每次从数据库获取
            isPermission : true,    //默认为true，指是否按权限过滤基础数据
            width : 500,
            // border: false, //面板边框
            useArrows : true, // 箭头节点图标
            autoScroll : true,
            animate : false
            // 是否动画显示
        });

        // 绑定节点单击事件
        tree.on("itemclick", function(loader,node) {
            if(node.data.id=='3'){
                Ext.MessageBox.alert('提示', 'ID:' + node.data.id +  ' text:' + node.data.text + " 附加属性值:" + node.data.customProperty);
            }else{
                Ext.MessageBox.alert('提示', 'ID:' + node.data.id +  ' text:' + node.data.text );
            }
        });

        // 定义一个右键菜单
        var contextmenu = new Ext.menu.Menu({
            id : 'theContextMenu',
            items : [{
                iconCls : 'userIcon',
                text : '是男人就点我',
                handler : function() {
                    var selectModel = tree.getSelectionModel(); // 获取树选择模型
                    var selectNode = selectModel.getSelectedNode(); // 获取当前树选中节点对象
                    Ext.Msg.alert('提示', 'ID:' + selectNode.id + " text:" + selectNode.text);
                }
            }, {
                iconCls : 'user_femaleIcon',
                text : '再点一次',
                handler : function() {
                    Ext.Msg.alert('提示', '点我呢点!');
                }
            }]
        });
        // 绑定右键菜单
        tree.on("contextmenu", function(node, e) {
            e.preventDefault();
            node.select();
            contextmenu.showAt(e.getXY());
        });

        var firstWindow = new Ext.Window({
            title : '<span class="commoncss">vcf平台单选有源树</span>', // 窗口标题
            layout : 'fit', // 设置窗口布局模式
            width : 250, // 窗口宽度
            height : 300, // 窗口高度
            closable : false, // 是否可关闭
            collapsible : true, // 是否可收缩
            maximizable : true, // 设置是否可以最大化
            border : false, // 边框线设置
            constrain : true, // 设置窗口是否可以溢出父容器
            pageY : 20, // 页面定位Y坐标
            pageX : document.body.clientWidth / 2 - 300 / 2, // 页面定位X坐标
            items : [tree]
            // 嵌入的表单面板
        });
        firstWindow.show(); // 显示窗口
    }

    function initWindow2() {
        // 表格工具栏
        var tbar = new Ext.Toolbar({
            items : [{
                text : '设置值集1',
                iconCls : 'arrow_refreshIcon',
                handler : function() {
                    if (tree.isCodeAsValue) {
                        tree.resetChecked('101001,102001');
                    } else {
                        tree.resetChecked('{99EB297E-6981-11E3-9C50-8BE9185F8FBA},{99ECD730-6981-11E3-9C50-8BE9185F8FBA}');
                    }
                }
            },{
                text : '设置值集2',
                iconCls : 'arrow_refreshIcon',
                handler : function() {
                    Ext.Ajax.request({
                        url : './vcfDemo.do?reqCode=getCheckValues',
                        success : function(response) {
                            var resultArray = Ext.util.JSON
                                .decode(response.responseText);
                            if (tree.isCodeAsValue) {
                                tree.resetChecked(resultArray,'chr_code');   //第二个参数代表ID或CODE字段名，根据属性isCodeAsValue决定
                            } else {
                                tree.resetChecked(resultArray,'chr_id');
                            }
                        },
                        failure : function(response) {
                            var resultArray = Ext.util.JSON
                                .decode(response.responseText);
                            Ext.Msg.alert('提示', resultArray.msg);
                        },
                        params : {
                        }
                    });

                }
            },{
                text : '设置值集3',
                iconCls : 'arrow_refreshIcon',
                handler : function() {
                    if (tree.isCodeAsValue) {
                        tree.resetChecked(['101001','105001','110001']);
                    } else {
                        tree.resetChecked(['{99EB297E-6981-11E3-9C50-8BE9185F8FBA}','{99F19226-6981-11E3-9C50-8BE9185F8FBA}','{99F7859E-6981-11E3-9C50-8BE9185F8FBA}']);
                    }
                }
            },{
                text : '全选',
                iconCls : 'arrow_refreshIcon',
                handler : function() {
                    tree.treeCheckTrue();
                }
            },{
                text : '全清',
                iconCls : 'arrow_refreshIcon',
                handler : function() {
                    tree.treeCheckFalse();
                }
            },{
                text : '获取选择值',
                iconCls : 'arrow_refreshIcon',
                handler : function() {
                    //根据isCodeAsValue属性决定选中值为ID集合还是CODE集合
                    Ext.Msg.alert('提示', '选中值：'+tree.getCheckValues());
                }
            }]
        });

        // 定义一个树面板
        var tree = Ext.create('Ext.vcf.AssistTree', {
            treeRootText : '用户',
            title : '',
            renderTo : 'treeDiv',
            checkReadOnly : true,
            tbar : tbar,
            source : 'MB',
            isCodeAsValue : true,  //是否将编码作为值 默认为false，即获取基础数据的ID作为值
            isDirect : false,    //默认为false，指是否每次从数据库获取
            isPermission : true,    //默认为true，指是否按权限过滤基础数据
            width : 500,
            selectModel : 'multiple',     //single单选 （默认） multiple 多选的
            // border: false, //面板边框
            useArrows : true, // 箭头节点图标
            autoScroll : true,
            animate : false
            // 是否动画显示
        });

        var firstWindow = new Ext.Window({
            title : '<span class="commoncss">vcf平台多选有源树</span>', // 窗口标题
            layout : 'fit', // 设置窗口布局模式
            width : 450, // 窗口宽度
            height : 400, // 窗口高度
            closable : false, // 是否可关闭
            collapsible : true, // 是否可收缩
            maximizable : true, // 设置是否可以最大化
            border : false, // 边框线设置
            constrain : true, // 设置窗口是否可以溢出父容器
            pageY : 20, // 页面定位Y坐标
            pageX : document.body.clientWidth / 2 - 300 / 2, // 页面定位X坐标
            items : [tree]
            // 嵌入的表单面板
        });
        firstWindow.show(); // 显示窗口
    }
    
});