function pageLoad() {
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {
        items: [{
            text: '新增要素',
            iconCls: 'page_addIcon',
            handler: addInit
        }, '-', {
            text: '修改要素',
            iconCls: 'page_edit_1Icon',
            handler: editInit
        }, '-', {
            text: '删除要素',
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
        url: 'element_list.htm'
    });

    Ext.create('Ext.Viewport',{
        layout: 'border',
        items: [{
            id: 'boxPanel',
            region: 'center',
            layout: 'fit',
            margins: '3 3 3 3',
            border: false,
            items : mainGrid
        }]
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
        addWindow.setTitle('新增要素');
        addPanel.reset();
        addWindow.show();
    }
    
    function editInit() {
        editmode = 'edit';
        var selected = mainGrid.getSelectionModel().getSelected();
        var record = selected.items[0];
        if (Ext.isEmpty(record)) {
            Ext.MessageBox.alert('提示', '请先选择要修改的要素信息!');
            return;
        }
        if (selected.items.length > 1) {
            Ext.MessageBox.alert('提示', '只能选择一项进行修改!');
            return;
        }
        addWindow.setTitle('修改要素');
        addPanel.reset();
        addWindow.show();
        addPanel.getForm().loadRecord(record);
    }

    function saveData() {
        var url = editmode=='add' ? 'element_save.htm' : 'element_update.htm';
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
                mainGrid.requery();
                Ext.MessageBox.alert('提示', action.result.msg);
            },
            failure: function (form, action) {
                switch (action.failureType) {
                    case Ext.form.action.Action.SERVER_INVALID:
                        Ext.Msg.alert('提示', '数据保存失败:<br>'+action.result.msg);
                        break;
                    default:
                        Ext.Msg.alert('错误', '数据保存失败:<br>'+action.response.responseText);
                }
            }
        });
    }

    function delData() {
        var rows = mainGrid.getSelectionModel().getSelection();
        if (Ext.isEmpty(rows)) {
            Ext.Msg.alert('提示', '请先选中要删除的要素!');
            return;
        }
        var idlist = jsArray2SingleArray(rows, 'id');
        Ext.Msg.confirm('请确认', '<b>提示:</b>确认删除吗？', function (btn, text) {
            if (btn == 'yes') {
                showWaitMsg();
                Ext.Ajax.request({
                    url: 'element_delete.htm',
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
}
Ext.onReady(pageLoad);
