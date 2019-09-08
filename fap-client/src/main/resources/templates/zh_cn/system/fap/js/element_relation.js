function pageLoad() {

    //操作标记  0代表选中被控要素  1代表选中主控要素
    var optflag = "0";
    var editmode = 'add';
    // 表格工具栏
    var toolbar = new Ext.Toolbar({
        items: [{
            text: '新增',
            iconCls: 'addIcon',
            handler: addInit
        }, {
            text: '修改',
            iconCls: 'edit1Icon',
            handler: editInit
        }, {
            text: '删除',
            iconCls: 'deleteIcon',
            handler: delRelation
        }]
    });

    var grid = Ext.create('Ext.vcf.TableGrid', {
        layout: 'fit',
        tbar: toolbar,
        isPaged : false,
        region: 'center',
        margins: '3 3 3 3',
        url: 'relation_list.htm',
        columnBase: [{
            xtype: 'rownumberer',
            width: 30
        }, {
            header: '关联id',
            dataIndex: 'id',
            hidden: true,
            sortable: true
        }, {
            header: '关联名称',
            dataIndex: 'name',
            width: 140,
            sortable: true
        }, {
            header: '主控要素',
            dataIndex: 'pri_ele',
            width: 100,
            sortable: true
        }, {
            header: '被控要素',
            dataIndex: 'sec_ele',
            sortable: true,
            width: 100
        }],
        fieldBase: [{
            name : 'id'
        }, {
            name : 'name'
        }, {
            name : 'pri_ele'
        }, {
            name : 'sec_ele'
        }, {
            name : 'is_deleted'
        }]
    });
    // 监听行选中事件
    grid.on('rowclick', function (pGrid, rowIndex, event) {

        var record = grid.getSelectionModel().getSelected().items[0];

        if (!Ext.isEmpty(record)) {
            treePri.setSource(record.get('pri_ele'));
            treeSec.setSource(record.get('sec_ele'));
            initPriChecked(record);
        } else {
            treePri.setSource('');
            treeSec.setSource('');
        }

    });

    var treePri = new Ext.vcf.AssistTree({
        width: 400,
        minSize: 180,
        maxSize: 400,
        checkReadOnly: true,
        source: '',
        selectModel: 'multiple',
        rootVisible: true,
        collapsible: false,
        autoScroll: true,
        animate: false,
        border: false // 面板边框
    });

    treePri.on('itemclick', function (model, node) {
        var record = grid.getSelectionModel().getSelected().items[0];

        if (!Ext.isEmpty(record)) {
            initSecChecked(record, node);
        }
    });

    var treeSec = new Ext.vcf.AssistTree({
        width: 400,
        minSize: 180,
        maxSize: 300,
        checkReadOnly: true,
        source: '',
        selectModel: 'multiple',
        rootVisible: true,
        collapsible: false,
        autoScroll: true,
        animate: false,
        border: false // 面板边框
    });


    function initPriChecked(record) {
        Ext.Ajax.request({
            url: 'relation_pri_check.htm',
            success: function (response) {
                var resultArray = Ext.util.JSON.decode(response.responseText);
                treePri.resetChecked(resultArray, 'pri_ele_value');
            },
            params: {
                relation_id: record.get('id')
            }
        });
    }

    function initSecChecked(record, node) {
        Ext.Ajax.request({
            url: 'relation_sec_check.htm',
            success: function (response) {
                var resultArray = Ext.util.JSON.decode(response.responseText);
                treeSec.resetChecked(resultArray, 'sec_ele_value');
            },
            params: {
                relation_id: record.get('id'),
                pri_ele_value: node.id
            }
        });
    }

    /**
     * 布局
     */
    Ext.create('Ext.Viewport',{
        layout: 'border',
        items: [{
            region: 'west',
            margins: '3 3 3 3',
            split: true,
            layout: 'fit',
            title: '<span class="commoncss">要素关联列表</span>',
            iconCls: 'layout_contentIcon',
            collapsible: false,
            width: 400,
            items: [grid]
        }, {
            region: 'east',
            margins: '3 3 3 3',
            layout: 'fit',
            split: true,
            title: '<span class="commoncss">被控要素</span>',
            collapsible: false,
            width: 400,
            items: [treeSec]
        }, {
            region: 'center',
            margins: '3 3 3 3',
            layout: 'fit',
            split: true,
            title: '<span class="commoncss">主控要素</span>',
            collapsible: false,
            items: [treePri]
        }]
    });

    var relation_id = "";

    /**
     * 数据存储
     */
    var storeAttr = Ext.create('Ext.data.Store', {
        model: Ext.create('Ext.data.Model', {
            field: [{
                name: 'id'
            }, {
                name: 'main'
            }, {
                name: 'pri_ele_value'
            }, {
                name: 'sec_ele_value'
            }]
        }),
        proxy: {
            type: 'ajax',
            url: 'relation_pri_check.htm',
            reader: {
                type: 'json',
                id: 'id'
            },
            extraParams: {
                relation_id: relation_id
            }
        }
    });
    // 定义一个Record
    Ext.define('MyRecord',{
        extend : 'Ext.data.Record',
        idgen: {
            type: 'sequential',
            seed: 1,
            prefix: '-'
        },
        fields: [{
            name: 'pri_ele_value',
            type: 'string'
        }, {
            name: 'sec_ele_value',
            type: 'string'
        }]
    });
    //操作标记  0代表选中被控要素  1代表选中主控要素
    var optflag = "0";

    var addPanel = Ext.create('Ext.vcf.FormPanel',{
        padding : '2 2',
        items: [{
            name: 'name',
            fieldLabel: '关联名称',
            xtype: 'textfield',
            readOnly: false,
            labelStyle: 'color:blue;',
            allowBlank: false,
            columnWidth: '.3'
        }, {
            fieldLabel: '主控要素',
            name: 'pri_ele',
            xtype: 'treefield',
            isDirect: true,
            source: 'EL',
            //selectModel : 'single',
            isCodeAsValue: true,
            allowBlank: false,
            labelStyle: micolor,
            listeners: {
                'select': function (combotree, newNode, oldNode) {
                    treeEdtPri.setSource(newNode.get('code'));
                    storeAttr.each(function (record) {
                        record.set('is_deleted', 1);
                    });
                }
            },
            columnWidth: '.35'
        }, {
            fieldLabel: '被控要素',
            name: 'sec_ele',
            xtype: 'treefield',
            source: 'EL',
            //selectModel : 'multiple',
            isCodeAsValue: true,
            allowBlank: false,
            labelStyle: micolor,
            listeners: {
                'select': function (combotree, newNode, oldNode) {
                    treeEdtSec.setSource(newNode.get('code'));
                    storeAttr.each(function (record) {
                        record.set('is_deleted', 1);
                    });
                }
            },
            columnWidth: '.35'
        }, {
            name : 'id',
            xtype : "hidden"
        }]
    });

    var treeEdtPri = new Ext.vcf.AssistTree({
        width: 320,
        minSize: 180,
        maxSize: 300,
        source: '',
        selectModel: 'single',
        rootVisible: true,
        collapsible: false,
        autoScroll: true,
        animate: false
    });
    treeEdtPri.on('itemclick', function (model, node) {
        optflag = "1";
        //var checkvalue="['{AB25FA66-D87C-11DD-84C0-8B89EADAF021}','{C5CD6747-D87C-11DD-84C0-8B89EADAF021}']";
        var checkValue = getCheckedValue(node);
        treeEdtSec.resetChecked(Ext.util.JSON.decode(checkValue));
        optflag = "0";
    });

    var treeEdtSec = new Ext.vcf.AssistTree({
        width: 320,
        minSize: 180,
        maxSize: 300,
        source: '',
        selectModel: 'multiple',
        rootVisible: true,
        collapsible: false,
        autoScroll: true,
        animate: false
    });
    treeEdtSec.on('checkchange', function (node,checked) {
        var selectedNode = treeEdtPri.getSelectionModel().getSelected().items[0];
        if (Ext.isEmpty(selectedNode)) {
            Ext.Msg.alert('提示', '请选择主控要素');
            // this.treeCheckfalse();
            return;
        }
        if (!selectedNode.isLeaf()) {
            Ext.Msg.alert('提示', '主控要素只允许选择底级');
            // this.treeCheckfalse();
            return;
        }
        //根节点才保存
        if (node.isLeaf() && optflag == 0) {
            modAttrItem(selectedNode, node);
        }
    });

    var addWindow = Ext.create('Ext.vcf.Window', {
        title: '<span class="commoncss">新增界面<span>', // 窗口标题
        layout: 'border', // 设置窗口布局模式
        width: 900,
        height: 650,
        closable: true, // 是否可关闭
        closeAction: 'hide',
        collapsible: true, // 是否可收缩
        maximizable: true, // 设置是否可以最大化
        border: false, // 边框线设置
        constrain: true, // 设置窗口是否可以溢出父容器
        animateTarget: Ext.getBody(),
        modal: true,
        items: [{
            region: 'north',
            margins: '3 3 3 3',
            split: true,
            layout: 'fit',
            collapsible: false,
            height: 36,
            items: [addPanel]
        }, {
            region: 'west',
            margins: '3 3 3 3',
            split: true,
            layout: 'fit',
            title: '<span class="commoncss">主控要素</span>',
            collapsible: false,
            width: 420,
            items: [treeEdtPri]
        }, {
            region: 'center',
            margins: '3 3 3 3',
            split: true,
            layout: 'fit',
            title: '<span class="commoncss">被控要素</span>',
            collapsible: false,
            items: [treeEdtSec]
        }],
        buttonAlign: 'center',
        buttons: [{
            text: '确定',
            iconCls: 'acceptIcon',
            handler: saveData
        }, {
            text: '取消',
            iconCls: 'deleteIcon',
            handler: function () {
                addWindow.hide();
            }
        }]
    });

    //修改要素关联关系
    function modAttrItem(ctrNode, srcNode) {
        var index = storeAttr.findBy(function (record, id) {
            return record.get('pri_ele_value') == ctrNode.id && record.get('sec_ele_value') == srcNode.id;
        });
        if (srcNode.data.checked) {
            var newrecord = new MyRecord();
            newrecord.set('pri_ele_value', ctrNode.id); // 赋初值
            newrecord.set('sec_ele_value', srcNode.id);
            newrecord.set('is_deleted', 0);
            if (index == -1) {
                storeAttr.add( newrecord);
            } else {
                storeAttr.getAt(index).set('is_deleted', 0);
            }
        } else {
            if (index !== -1) {
                storeAttr.getAt(index).set('is_deleted', 1);
            }
        }
    }

    //遍历storeAttr，返回当前节点已对应的数据集合
    function getCheckedValue(node) {
        var checkedValue = "";
        storeAttr.each(function (record) {
            if (node.id == record.get('pri_ele_value') && record.get('is_deleted') == 0) {
                checkedValue = "'" + record.get('sec_ele_value') + "'," + checkedValue;
            }
        });
        return "[" + checkedValue + "]";
    }

    //保存要素关联关系
    function saveData() {
        var url = editmode=='add' ? 'relation_insert.htm' : 'relation_update.htm';
        if (!addPanel.form.isValid()) {
            Ext.MessageBox.alert('提示', '请检查需要填写的录入项！');
            return;
        }
        var jsonArray = [];
        storeAttr.each(function (record) {
            jsonArray.push(record.data);
        });
        // 提交到后台处理
        addPanel.submit({
            url: url,
            params: {dirtydata: Ext.encode(jsonArray)},
            waitTitle: '提示',
            method: 'POST',
            waitMsg: '正在处理数据,请稍候...',
            success: function (form, action) {
                Ext.Msg.alert('提示', "数据保存成功");
                addWindow.hide();
                treePri.setSource('');
                treeSec.setSource('');
                grid.requery();
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

    //初始化新增界面
    function addInit() {
        editmode = 'add';
        addWindow.show();
        //界面赋初值
        addPanel.getForm().reset();
        treeEdtPri.setSource("");
        treeEdtSec.setSource("");
        storeAttr.removeAll();
        addWindow.setTitle('<span style="font-weight:normal">新增要素关联</span>');
    }

    //初始化修改界面
    function editInit() {
        editmode = 'edit';
        var record = grid.getSelectionModel().getSelected().items[0];
        if (Ext.isEmpty(record)) {
            Ext.MessageBox.alert('提示', '请先选择要修改的数据!');
            return;
        }
        relation_id = record.get('id');
        storeAttr.load({
            params: {
                relation_id: relation_id
            },
            callback: function (records, options, success) {
                treeEdtPri.setSource(record.get('pri_ele'));
                treeEdtSec.setSource(record.get('sec_ele'));
                addWindow.show();
                addWindow.setTitle('<span style="font-weight:normal">修改要素关联</span>');
                addPanel.getForm().loadRecord(record);
            }
        });
    }

    //删除要素关联关系
    function delRelation() {
        var record = grid.getSelectionModel().getSelected().items[0];
        if (Ext.isEmpty(record)) {
            Ext.Msg.alert('提示:', '请先选择要删除的信息');
            return;
        }
        Ext.MessageBox.confirm('请确认', '确认删除吗?', function (btn, text) {
            if (btn == 'yes') {
                showWaitMsg();
                Ext.Ajax.request({
                    url: 'relation_delete.htm',
                    success: function (response) { // 回调函数有1个参数
                        Ext.Msg.alert('提示', "删除成功");
                        treePri.setSource('');
                        treeSec.setSource('');
                        grid.requery();
                    },
                    failure: function (response) {
                        Ext.MessageBox.alert('提示', '数据删除失败');
                    },
                    params: {
                        relation_id: record.get('id')
                    }
                });
            }
        })
    }
}

Ext.onReady(pageLoad);