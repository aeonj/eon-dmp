function pageLoad() {
    var treeMenu = Ext.create('Ext.vcf.AssistTree',{
        rootVisible:false,
        url : 'menu_tree_all.htm',
        renderTo : 'menuTreeDiv'
    });
    var seltreeid =null;
    var seltreetype = "1";
    var selmgid = null;
    treeMenu.on('itemclick',function(cmb,node) {
        seltreeid = node.get('id');
        selmgid = node.get('mgid');
        seltreetype = node.get('type');
        if (seltreetype=='1') {
            mainGrid.query({
                menu_type: seltreetype,
                parent_id: selmgid
            });
        } else {
            mainGrid.query({
                menu_type: seltreetype,
                parent_id: seltreeid
            });
        }
    });

    var toolbar = Ext.create('Ext.toolbar.Toolbar', {
        items: [{
            id: 'menu:insert',
            text: '新增菜单',
            iconCls: 'page_addIcon',
            handler: addInit
        }, '-', {
            id: 'menu:update',
            text: '修改菜单',
            iconCls: 'page_edit_1Icon',
            handler: editInit
        }, '-', {
            id: 'menu:delete',
            text: '删除菜单',
            iconCls: 'page_delIcon',
            handler: removeData
        }, '-', {
            id: 'menu:group_do',
            text: '菜单组维护',
            iconCls: 'page_delIcon',
            handler: mgInit
        }]
    });

    var mainGrid = Ext.create('Ext.vcf.TableGrid', {
        isChecked: true,
        selModel: {
            injectCheckbox: 1,
            type: 'checkboxmodel'
        },
        columnBase: [{
            xtype: 'rownumberer',
            width: 40
        }, {
            header: '编码',
            dataIndex: 'menuCode',
            sortable: true,
            width: 100
        }, {
            header: '菜单名称',
            dataIndex: 'menuName',
            sortable: true,
            width: 150
        }, {
            header: '请求地址',
            dataIndex: 'request',
            width: 260
        }, {
            header: '权限关键字',
            dataIndex: 'authkey',
            width: 60
        }, {
            header: '排序',
            dataIndex: 'sequence',
            width: 60
        }, {
            header: '菜单说明',
            dataIndex: 'info',
            width: 260
        }],
        fieldBase: [{
            name: 'menuCode'
        }, {
            name: 'menuName'
        }, {
            name: 'request'
        }, {
            name: 'sequence'
        }, {
            name: 'info'
        }, {
            name: 'id'
        }],
        params: {menu_type: seltreetype, parent_id: seltreeid},
        tbar: toolbar,
        region: 'center',
        margins: '3 3 3 3',
        url: 'menu_list.htm'
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

    var addPanel = Ext.create('Ext.vcf.FormPanel',{
        layout : 'form',
        items : [{
            fieldLabel: '菜单编码',
            name: 'menuCode',
            afterLabelTextTpl: [
                '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>'
            ],
            allowBlank: false,
            labelStyle: micolor,
            anchor: '99%'
        },{
            fieldLabel: '菜单名称',
            name: 'menuName',
            afterLabelTextTpl: [
                '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>'
            ],
            allowBlank: false,
            labelStyle: micolor,
            anchor: '99%'
        }, {
            fieldLabel: '上级菜单',
            name: 'parent_id',
            xtype: 'treefield',
            store : Ext.create('Ext.data.TreeStore', {
                proxy: {
                    type: 'ajax',
                    url: 'menu_tree_mg.htm'
                },
                root: {
                    id: '0',
                    text: '全部',
                    expanded: true
                },
                listeners: {
                    'beforeload': function (treeloader, operation) {
                        Ext.apply(operation.config.params,{mg_id:selmgid});
                    }
                }
            }),
            selectNodeModel: 'all',
            isDirect: true,
            isPermission: false,
            anchor: '99%'
        },{
            fieldLabel: '请求地址',
            name: 'request',
            anchor: '99%'
        },{
            xtype: 'treefield',
            fieldLabel: '权限关键字',
            isPermission: false,
            source: 'PMS',
            isCodeAsValue: true,
            condition: 'code like \'%:view\'',
            name: 'authkey',
            anchor: '99%'
        },{
            fieldLabel: '图标',
            name: 'icon',
            anchor: '99%'
        },{
            fieldLabel: '排序号',
            name: 'sequence',
            anchor: '99%'
        },{
            fieldLabel: '说明',
            name: 'info',
            anchor: '99%'
        }, {
            name: 'mg_id',
            hidden: true
        }, {
            name: 'id',
            hidden: true
        }],
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
        addWindow.setTitle('新增菜单');
        addPanel.reset();
        addWindow.show();
        addPanel.getForm().findField('mg_id').setValue(selmgid);
        addPanel.getForm().findField('parent_id').setValue(seltreeid);
        addPanel.getForm().findField('parent_id').requestload =true;

    }

    function editInit() {
        editmode = 'edit';
        var selected = mainGrid.getSelectionModel().getSelected();
        var record = selected.items[0];
        if (Ext.isEmpty(record)) {
            Ext.MessageBox.alert('提示', '请先选择要修改的菜单信息!');
            return;
        }
        if (selected.items.length > 1) {
            Ext.MessageBox.alert('提示', '只能选择一项进行修改!');
            return;
        }
        addWindow.setTitle('修改菜单');
        addPanel.reset();
        addWindow.show();
        addPanel.getForm().findField('mg_id').setValue(selmgid);
        addPanel.getForm().findField('parent_id').requestload =true;
        addPanel.getForm().loadRecord(record);

    }

    function saveData() {
        var url = editmode=='add' ? 'menu_save.htm' : 'menu_update.htm';
        if (!addPanel.form.isValid()) {
            Ext.MessageBox.alert('提示', '请检查需要填写的录入项！');
            return;
        }

        addPanel.submit({
            url: url,
            waitTitle: '提示',
            method: 'POST',
            waitMsg: '正在处理数据,请稍候...',
            success: function (form, action) {
                addWindow.hide();
                // 获取对应树节点
                var parent_id = addPanel.form.findField('parent_id')
                    .getValue();
                refreshNode(parent_id);
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
    
    function removeData() {
        var rows = mainGrid.getSelectionModel().getSelection();
        if (Ext.isEmpty(rows)) {
            Ext.Msg.alert('提示', '请先选中要删除的菜单!');
            return;
        }
        var idlist = jsArray2SingleArray(rows, 'id');
        Ext.Msg.confirm('请确认', '<b>提示:</b>确认删除吗？<br>该节点及其所有子节点将一并删除！', function (btn, text) {
            if (btn == 'yes') {
                showWaitMsg();
                Ext.Ajax.request({
                    url: 'menu_delete.htm',
                    success: function (response) {
                        var resultArray = Ext.util.JSON
                            .decode(response.responseText);
                        mainGrid.requery();
                        treeMenu.getStore().reload();
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

    function mgInit() {
        var mgGrid = Ext.create('Ext.vcf.EditorTableGrid', {
            isChecked: false,
            isPaged : false,
            columnBase: [{
                xtype: 'rownumberer',
                width: 40
            }, {
                header: '菜单组名称',
                dataIndex: 'name',
                sortable: true,
                editor: 'textfield',
                width: 160
            }, {
                header: '图标',
                dataIndex: 'icons',
                editor: 'textfield',
                width: 180
            }, {
                header: '排序',
                dataIndex: 'sequence',
                editor: 'textfield',
                width: 50
            }],
            fieldBase: [{
                name: 'name',
                type : 'string'
            }, {
                name: 'icons',
                type : 'string'
            }, {
                name: 'sequence',
                type : 'int'
            }, {
                name: 'id',
                type : 'int'
            }],
            validators: {
                name: {
                    type: 'length',
                    min: 1
                }
            },
            region: 'center',
            margins: '3 3 3 3',
            url : 'menugroup_list.htm'
        });
        var mgwindow = Ext.create('Ext.vcf.Window', {
            width : 460,
            height : 300,
            title : "菜单组维护",
            items: mgGrid,
            tbar : [{
                text: '退出',
                iconCls: 'page_addIcon',
                handler: function () {
                    quit_mg();
                }
            }, '-', {
                text: '新增行',
                iconCls: 'page_addIcon',
                handler: function () {
                    addrow_mg();
                }
            }, '-', {
                text: '删除行',
                iconCls: 'page_edit_1Icon',
                handler: function () {
                    delrow_mg();
                }
            }, '-', {
                text: '保存',
                iconCls: 'page_edit_1Icon',
                handler: function () {
                    save_mg();
                }
            }]
        });
        mgwindow.show();

        function addrow_mg() {
            var rec = Ext.create('Ext.data.Model',{
                    fields : ['name','sequence']
                }),
                edit = mgGrid.editing;

            edit.cancelEdit();
            mgGrid.store.add(rec);
            edit.startEditByPosition({
                row: rec,
                column: 1
            });
        }

        function delrow_mg() {
            var selection = mgGrid.getView().getSelectionModel().getSelection()[0];
            if (selection) {
                mgGrid.store.remove(selection);
            }
        }
        
        function save_mg() {
            // 将record数组对象转换为简单Json数组对象
            var insertArray = [];
            var b = true;
            var i = Ext.Array.each(mgGrid.store.getNewRecords(), function (item) {
                if (!item.get('name')) {
                    return b=false;
                }
                insertArray.push(item.getData());
            });
            var updateArray = [];
            Ext.Array.each(mgGrid.store.getUpdatedRecords(), function (item) {
                if (!item.get('name')) {
                    return b=false;
                }
                updateArray.push(item.getData());
            });
            if (!b) {
                Ext.MessageBox.alert('提示', '菜单组名称不能为空');
                return;
            }
            var removeArray = [];
            Ext.Array.each(mgGrid.store.getRemovedRecords(), function (item) {
                removeArray.push(item.getData());
            });
            Ext.Ajax.request({
                method:'POST',
                url: 'menugroup_save.htm',
                success: function (response) { // 回调函数有1个参数
                    mgGrid.store.commitChanges();
                    refreshNode();
                    Ext.MessageBox.alert('提示', '菜单组保存成功');
                },
                failure: function (response) {
                    Ext.MessageBox.alert('提示', '数据保存失败');
                },
                params: {
                    // 系列化为Json资料格式传入后台处理
                    insertdata: Ext.encode(insertArray),
                    updatedata: Ext.encode(updateArray),
                    removedata: Ext.encode(removeArray)
                }
            });
        }

        function quit_mg() {
            mgwindow.hide();
        }
    }
}
Ext.onReady(pageLoad);