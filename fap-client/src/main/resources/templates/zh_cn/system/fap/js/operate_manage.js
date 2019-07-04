function pageLoad() {
    var treeMenu = Ext.create('Ext.vcf.AssistTree',{
        rootVisible:false,
        url : 'menu_tree_all.htm',
        renderTo : 'menuTreeDiv'
    });
    var seltreeid ='-1';
    var seltreetype = "1";
    treeMenu.on('itemclick',function(cmb,node) {
        seltreetype = node.get('type');
        if (seltreetype=='1') {
            seltreeid = '-1';
        } else {
            seltreeid = node.get('id');
        }
        mainGrid.query({
            menu_id: seltreeid
        });
    });
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {
        items: [{
            text: '新增操作',
            iconCls: 'page_addIcon',
            handler: addInit
        }, '-', {
            text: '修改操作',
            iconCls: 'page_edit_1Icon',
            handler: editInit
        }, '-', {
            text: '删除操作',
            iconCls: 'page_delIcon',
            handler: delData
        }]
    });
    var mainGrid = Ext.create('Ext.vcf.TableGrid', {
        id: 'main_grid',
        isChecked: true,
        tbar: toolbar,
        region: 'center',
        margins: '3 3 3 3',
        userDefined: true,
        params: {menu_id: seltreeid},
        url: 'operate_list.htm'
    });
    Ext.create('Ext.Viewport',{
        layout: 'border',
        items: [{
            region: 'west',
            title: '<span class="commoncss">菜单列表</span>',
            tools: [{
                id: 'refresh',
                handler: function () {
                    //treeMenu.getStore().reload();
                }
            }],
            width: 260,
            minSize: 220,
            maxSize: 370,
            collapsible: true,
            split: true,
            layout: 'fit',
            margins: '3 3 3 3',
            scrollable: 'y',
            border: false,
            items : treeMenu
        }, {
            id: 'boxPanel',
            region: 'center',
            //title : '<span class="commoncss">基础数据列表</span>',
            layout: 'fit',
            margins: '3 3 3 3',
            border: false,
            items : mainGrid
        }]
    });
    /**
     * 刷新指定节点
     */
    function refreshNode(nodeid) {
        treeMenu.getStore().reload();
        seltreeid =null;
        seltreetype = "1";
        selmgid = null;
        // if (nodeid != null && nodeid != '' && nodeid != '0') {
        //     treeMenu.selectNodeByValue(nodeid, true);
        // }
    }

    var addChoosePanel = Ext.create('Ext.vcf.QueryPanel',{
        id: 'add_choose_panel',
        defaultType: 'textfield',
        region : 'north',
        userDefined: true,
        height: 42
    });

    var addChooseGrid = Ext.create('Ext.vcf.TableGrid',{
        id: 'add_choose_grid',
        isChecked: true,
        region: 'center',
        margins: '3 3 3 3',
        userDefined: true,
        url: 'operate_permissions_list.htm'
    });

    var addChooseWindow = Ext.create('Ext.vcf.Window', {
        width : 510,
        height : 300,
        pageY: 20,
        items : [ addChoosePanel , addChooseGrid ],
        buttons : [ {
            text : '批量新增',
            iconCls : 'acceptIcon',
            handler : saveChooseData
        }, {
            text : '关闭',
            iconCls : 'deleteIcon',
            handler : function() {
                addChooseWindow.hide();
            }
        } ]
    });



    var addPanel = Ext.create('Ext.vcf.FormPanel',{
        id: 'add_panel',
        userDefined : true,
        defaultType: 'textfield',
        frame: false,
        autoHeight: true,
        bodyStyle: 'padding:15 15 0'
    });

    var addWindow = Ext.create('Ext.vcf.Window', {
        width : 510,
        pageY: 20,
        items : [ {
            region : 'north',
            autoHeight : true,
            items : [ addPanel ]
        }],
        buttons : [ {
            text : '保存',
            iconCls : 'acceptIcon',
            handler : saveData
        }, {
            text : '关闭',
            iconCls : 'deleteIcon',
            handler : function() {
                addWindow.hide();
            }
        } ]
    });

    var editmode = 'add';

    function addInit() {
        editmode = 'add';
        if (seltreeid=='-1') {
            Ext.MessageBox.alert('提示', '请先选择菜单!');
            return;
        }
        if (seltreetype=='1') {
            Ext.MessageBox.alert('提示', '一级菜单组不允许挂接操作!');
            return;
        }

        addWindow.setTitle('新增操作');
        addPanel.reset();
        addWindow.show();
    }

    function editInit() {
        editmode = 'edit';
        var selected = mainGrid.getSelectionModel().getSelected();
        var record = selected.items[0];
        if (Ext.isEmpty(record)) {
            Ext.MessageBox.alert('提示', '请先选择要修改的操作信息!');
            return;
        }
        if (selected.items.length > 1) {
            Ext.MessageBox.alert('提示', '只能选择一项进行修改!');
            return;
        }
        addWindow.setTitle('修改操作');
        addPanel.reset();
        addWindow.show();
        addPanel.getForm().loadRecord(record);
    }

    function saveData() {
        var url = editmode=='add' ? 'operate_save.htm' : 'operate_update.htm';
        if (!addPanel.form.isValid()) {
            Ext.MessageBox.alert('提示', '请检查需要填写的录入项！');
            return;
        }

        addPanel.submit({
            url: url,
            waitTitle: '提示',
            method: 'POST',
            waitMsg: '正在处理数据,请稍候...',
            params: {menu_id : seltreeid},
            success: function (form, action) {
                addWindow.hide();
                mainGrid.requery();
                Ext.MessageBox.alert('提示', action.result.msg);
            },
            failure: function (form, action) {
                switch (action.failureType) {
                    case Ext.form.action.Action.SERVER_INVALID:
                        Ext.Msg.alert('提示', '数据保存失败:<br>'+action.result.msg);
                        break;
                    default:
                        Ext.Msg.alert('错误', '数据保存失败:<br>'+action.responseText);
                }
            }
        });
    }

    function delData() {
        var rows = mainGrid.getSelectionModel().getSelection();
        if (Ext.isEmpty(rows)) {
            Ext.Msg.alert('提示', '请先选中要删除的操作!');
            return;
        }
        var idlist = jsArray2SingleArray(rows, 'id');
        Ext.Msg.confirm('请确认', '<b>提示:</b>确认删除吗？<br>该节点及其所有子节点将一并删除！', function (btn, text) {
            if (btn == 'yes') {
                showWaitMsg();
                Ext.Ajax.request({
                    url: 'operate_delete.htm',
                    success: function (response) {
                        var resultArray = Ext.util.JSON
                            .decode(response.responseText);
                        mainGrid.requery();
                        Ext.Msg.alert('提示', resultArray.msg);
                    },
                    failure: function (response) {
                        Ext.Msg.alert('提示', response.responseText);
                    },
                    params: {
                        ids: idlist.join()
                    }
                });
            }
        });
    }

    function saveChooseData() {

    }
}
Ext.onReady(pageLoad);