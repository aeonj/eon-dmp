Ext.onReady(function () {
    Ext.Ajax.timeout = 180000;

    var b_elecode = (typeof elecode == 'undefined' ? '' : elecode);
    var b_eletitle = (typeof eletitle == 'undefined' ? '基础数据' : eletitle);

    //判断界面是否默认引用标准列，否则需由界面视图自行配置标准数据列
    var use_default_column = true;

    var topPanel = Ext.create('Ext.form.Panel', {
        id: 'topPanel',
        labelAlign: 'left', // 标签对齐方式
        bodyStyle: 'padding:2 5 5 5', // 表单元素和表单面板的边距
        height: 55,
        fieldDefaults: {
            labelWidth: 65, // 标签宽度
        },
        frame: true,
        // tbar:tb, //工具栏
        layout: 'column',
        border: false,
        items: [{
            id: 'select_ele_box',
            layout: 'form',
            border: false,
            columnWidth: 0.45,
            items: [{
                id: 'cmb_select_ele',
                name: 'select_ele',
                fieldLabel: '要素名称',
                isCodeAsValue: true,
                scrollable: true,
                xtype: 'treefield',
                source: 'ELE',
                condition: '',
                isFullData: true,
                anchor: '100%',
                listeners: {
                    select: function (combo, node, oldnode) {
                        if (node.data.code) {
                            treeEle
                                .setSource(node.data.code);
                        } else {
                            treeEle.setSource(null);
                        }
                        if (node.data.use_default_column) {
                            use_default_column = node.data.use_default_column != '0';
                        } else {
                            use_default_column = true;
                        }
                        if (node.data.class_name=='') {
                            toolbar.hide();
                        } else {
                            toolbar.show();
                        }
                        resetGrid();
                    }
                }
            }]
        }, {
            layout: 'form',
            border: false,
            columnWidth: 0.3,
            items: [{
                id: 'cmb_local_type',
                name: 'local_type',
                fieldLabel: '快速定位',
                xtype: 'combofield',
                enumData: '1#包含+2#左包含+3#右包含+4#等于',
                anchor: '100%'
            }]
        }, {
            layout: 'form',
            border: false,
            columnWidth: 0.25,
            items: [{
                id: 'local_text',
                name: 'local_text',
                xtype: 'textfield',
                hideLabel: true,
                anchor: '100%',
                listeners: {
                    specialkey: function (field, e) {
                        if (e.getKey() == Ext.EventObject.ENTER) {
                            queryNextNode();
                        }
                    }
                }
            }]
        }, {
            layout: 'form',
            border: false,
            items: [{
                id: 'btn_search',
                xtype: 'button',
                text: '查找',
                anchor: '100%',
                handler: function () {
                    queryNode();
                }
            }]
            // columnWidth: 0.07
        }, {
            layout: 'form',
            border: false,
            items: [{
                id: 'btn_nextsearch',
                xtype: 'button',
                text: '下一个',
                anchor: '100%',
                handler: function () {
                    queryNextNode();
                }
            }]
            // columnWidth: 0.05
        }]
    });

    var treeEle = Ext.create('Ext.vcf.AssistTree', {
        //source : b_elecode,
        scrollable: 'y',
        isDirect: true,
        saveDelay: 200,
        trailingBufferZone: 20,
        isPermission: false,
        isFullLevel: typeof ele_isfulllevel == 'undefined' ? true : ele_isfulllevel
        // selectModel : 'multiple'
        // applyTo : 'baseDataDiv',
    });
    var seltreecode = null;
    treeEle.on('itemclick', function (node, event) {
        if (event == this.getRootNode()) {
            seltreecode = null;
        } else {
            seltreecode = event.data.code;
        }
        store.reload({
            params: {
                start: 0,
                limit: bbar.pageSize,
                source: Ext.getCmp('cmb_select_ele').getValue(),
                code: seltreecode
            }
        });
    });
    // var sm = new Ext.grid.CheckboxSelectionModel();
    var cmBase = [{
        xtype: 'rownumberer',
        width: 40
    }, {
        header: '显示码',
        dataIndex: 'code',
        sortable: true,
        width: 100
    }, {
        header: '显示名称',
        dataIndex: 'name',
        sortable: true,
        width: 150
    }, {
        header: '级次',
        dataIndex: 'level',
        width: 60
    }, {
        header: '是否启用',
        dataIndex: 'enabled',
        width: 80,
        renderer: ENABLEDRender
    }, {
        header: '是否删除',
        dataIndex: 'is_deleted',
        width: 80,
        renderer: YESNORender
    }, {
        header: '父级名称',
        dataIndex: 'parent_name',
        sortable: true,
        width: 150
    }];
    var fieldBase = [{
        name: 'id'
    }, {
        name: 'code'
    }, {
        name: 'disp_code'
    }, {
        name: 'name'
    }, {
        name: 'level'
    }, {
        name: 'leaf'
    }, {
        name: 'enabled'
    }, {
        name: 'is_deleted'
    }, {
        name: 'last_ver'
    }, {
        name: 'parent_id'
    }, {
        name: 'parent_name'
    }];
    var uiArray = null;

    function newColumnModel(ray) {
        var cmArray = [];
        var findIsCodeAsValue = function (childs) {
            for (var k = 0; k < childs.length; k++) {
                if (childs[j].uiconf_field == 'isCodeAsValue') {
                    return true;
                }
            }
            return false;
        };
        for (var i = 0; ray != null && i < ray.length; i++) {
            var newField = {
                header: ray[i].field_title,
                dataIndex: ray[i].field_name,
                sortable: true,
                width: ray[i].width,
                hidden: ray[i].hidden == 1
            };
            var children = ray[i].children;
            if (children && typeof children == 'object') {
                for (var j = 0; j < children.length; j++) {
                    if (children[j].uiconf_field == 'source') {
                        if (findIsCodeAsValue(children)) {
                            newField.renderer = eval('typeof ' + children[j].uiconf_value + 'CodeRender!=\'undefined\' ? '
                                + children[j].uiconf_value + 'CodeRender : objEntityRender');
                        } else {
                            newField.renderer = eval('typeof ' + children[j].uiconf_value + 'IdRender!=\'undefined\' ? '
                                + children[j].uiconf_value + 'IdRender : objEntityRender');
                        }
                    } else if (children[j].uiconf_field == 'enumData') {
                        eval('function ' + newField.dataIndex + 'EnumData() {return "' + children[j].uiconf_value + '"};');
                        newField.renderer = eval('typeof ' + newField.dataIndex + 'EnumRender!=\'undefined\' ? ' + newField.dataIndex + 'EnumRender : null');
                    } else if (children[j].uiconf_field == 'xtype') {
                        if (children[j].uiconf_value == 'checkbox'
                            || children[j].uiconf_value == 'chkfield') {
                            newField.renderer = YESNORender;
                        } else if (children[j].uiconf_value == 'radiogroup') {
                            // aa.renderer = {};
                        }

                    }
                }
            }
            cmArray.push(newField);
        }
        return cmBase.concat(cmArray);
    }

    function newStore(ray) {
        var stArray = [];
        for (var i = 0; ray != null && i < ray.length; i++) {
            var newField = {
                name: ray[i].field_name
            };
            stArray.push(newField);
        }
        var recNew = Ext.create('Ext.data.Model', {
            fields: fieldBase.concat(stArray)
        });
        var newStroe = Ext.create('Ext.data.Store', {
            pageSize: parseInt(pagesize_combo.getValue()),
            model: recNew,
            proxy: {
                type: 'ajax',
                actionMethods: {
                    read: 'POST' // Store设置请求的方法，与Ajax请求有区别
                },
                url: 'basedata_list.htm',
                reader: {
                    type: 'json',
                    totalProperty: 'total',
                    rootProperty: 'data'
                }
            }
        });
        newStroe.addListener('beforeload', function () {
            this.getProxy().extraParams = {
                source: Ext.getCmp('cmb_select_ele').getValue(),
                queryparam: Ext.getCmp('queryParam').getValue()
            }
        });
        return newStroe
    }

    function resetGrid() {
        Ext.Ajax.request({
            url: 'ui_ele_designer.htm',
            method: 'POST',
            success: function (response) {
                uiArray = Ext.util.JSON.decode(response.responseText);
                cm = newColumnModel(uiArray.detail);
                store = newStore(uiArray.detail);
                grid.reconfigure(store, cm);
                bbar.bindStore(store);
                store.getProxy().extraParams = {
                    source: Ext.getCmp('cmb_select_ele').getValue(),
                    queryparam: Ext.getCmp('queryParam').getValue()
                };
                store.reload({
                    params: {
                        start: 0,
                        limit: bbar.pageSize,
                        source: Ext.getCmp('cmb_select_ele').getValue(),
                        queryparam: Ext.getCmp('queryParam').getValue()
                    }
                });
            },
            failure: function (response) {
                uiArray = null;
            },
            params: {
                source: Ext.getCmp('cmb_select_ele').getValue()
            }
        });
    }

    var cm = cmBase;
    var record = Ext.create('Ext.data.Model', {
        fields: fieldBase
    });

    /**
     * 数据存储
     */
    var store = Ext.create('Ext.data.Store', {
        pageSize: 50,
        model: record,
        proxy: {
            type: 'ajax',
            actionMethods: {
                read: 'POST' // Store设置请求的方法，与Ajax请求有区别
            },
            url: 'basedata_list.htm',
            reader: {
                type: 'json',
                totalProperty: 'total',
                rootProperty: 'data'
            }
        }
    });

    // 翻页排序时带上查询条件
    store.addListener('beforeload', function () {
        this.getProxy().extraParams = {
            queryParam: Ext.getCmp('queryParam').getValue()
        }
    });

    var pagesize_combo = new Ext.form.ComboBox({
        name: 'pagesize',
        // name: 'pagesize',
        typeAhead: true,
        triggerAction: 'all',
        lazyRender: true,
        mode: 'local',
        store: new Ext.data.ArrayStore({
            fields: ['value', 'text'],
            data: [[10, '10条/页'], [20, '20条/页'],
                [50, '50条/页'], [100, '100条/页'],
                [250, '250条/页'], [500, '500条/页']]
        }),
        valueField: 'value',
        displayField: 'text',
        value: '50',
        editable: false,
        width: 100
    });
    var number = parseInt(pagesize_combo.getValue());
    pagesize_combo.on("select", function (comboBox) {
        bbar.pageSize = parseInt(comboBox.getValue());
        number = parseInt(comboBox.getValue());
        store.pageSize = number;
        var selectModel = treeEle.getSelectionModel();
        var selectNode = selectModel.getSelected().items[0];
        var chrcode = null;
        if (selectNode != null && selectNode != treeEle.getRootNode())
            chrcode = selectNode.data.code;
        store.reload({
            params: {
                start: 0,
                limit: bbar.pageSize,
                source: Ext.getCmp('cmb_select_ele').getValue(),
                code: chrcode
            }
        });
    });

    var bbar = Ext.create('Ext.toolbar.Paging', {
        // pageSize: number,
        // store: store,
        displayInfo: true,
        // displayMsg: '显示{0}条到{1}条,共{2}条',
        plugins: 'ux-progressbarpager', // 分页进度条
        emptyMsg: "没有符合条件的记录",
        items: ['-', '&nbsp;&nbsp;', pagesize_combo]
    });

    // bbar.addListener('beforechange', function (_p, _o) {
    //     // console.log(_p);
    //     // console.log(_o);
    //     // Ext.apply(_o, {
    //     //     source: Ext.getCmp('cmb_select_ele').getValue(),
    //     //     code: seltreecode
    //     // });// 增加自定义参数
    //     // return true;
    // });

    var toolbar = Ext.create('Ext.toolbar.Toolbar', {
        items: [{
            text: '新增',
            iconCls: 'page_addIcon',
            handler: function () {
                addInit();
            }
        }, '-', {
            text: '修改',
            iconCls: 'page_edit_1Icon',
            handler: function () {
                editInit();
            }
        }, '-', {
            text: '删除',
            iconCls: 'page_delIcon',
            handler: function () {
                deleteDataItems();
            }
        }, '-', {
            text: '导入',
            iconCls: 'page_excelIcon',
            handler: function () {
                importWindow.show();
            }
        }, '-', {
            text : '缓存同步',
            iconCls : 'arrow_refreshIcon',
            hidden : b_elecode!=''?true:false,
            handler : function() {
                Ext.Ajax.request({
                    url : 'basedata_sync_memory.htm',
                    success : function(response) {
                        Ext.Msg.alert('提示', '缓存同步成功');
                    },
                    failure : function(response) {
                        Ext.Msg.alert('提示', '缓存同步失败:'+response.responseText);
                    }
                });
            }
        }, '->', new Ext.form.TextField({
            id: 'queryParam',
            name: 'queryParam',
            emptyText: '请输入' + b_eletitle + '名称',
            enableKeyEvents: true,
            listeners: {
                specialkey: function (field, e) {
                    if (e.getKey() == Ext.EventObject.ENTER) {
                        queryBaseItem();
                    }
                }
            },
            width: 150
        }), {
            text: '查询',
            iconCls: 'previewIcon',
            handler: function () {
                queryBaseItem();
            }
        }/*
				 * , '-', { text : '刷新', iconCls : 'arrow_refreshIcon', handler :
				 * function() { store.reload(); } }
				 */]
    });

    var grid = Ext.create('Ext.grid.Panel', {
        title: '<span class="commoncss">数据列表</span>',
        // height : 500,
        // width:600,
        autoScroll: true,
        region: 'center',
        margins: '3 3 3 3',
        itemId: 'grid',
        store: store,
        loadMask: {
            msg: '正在加载表格数据,请稍等...'
        },
        stripeRows: true,
        // frame: true,
        // sm: sm,
        selModel: {
            injectCheckbox: 1,
            type: 'checkboxmodel'
        },
        // cm: cm,
        columns: cm,
        tbar: toolbar,
        bbar: bbar
    });
    var mainTabs = Ext.create('Ext.tab.Panel', {
        id: 'mainTabs',
        region: 'center',
        //style : 'display:none',
        margins: '3 3 3 3',
        activeTab: 0,
        tabPosition: 'top',
        headerCfg: {id: 'tab_header'},
        items: [grid],
        enableTabScroll: false,
        //autoWidth : true
        //autoHeight : true,
        anchor: '100%'
    });


    store.load({
        params: {
            start: 0,
            limit: bbar.pageSize,
            firstload: 'true',
            source: ''
        }
    });

    grid.on('rowdblclick', function (grid, rowIndex, event) {
        editInit();
    });

    /**
     * 布局
     */
    var viewport = new Ext.Viewport({
        layout: 'border',
        items: [{
            region: 'north',
            // title : '<span
            // class="commoncss">要素信息</span>',
            // collapsible : true,
            height: 55,
            maxsize: 36,
            // split : true,
            margins: '3 3 3 3',
            // collapseMode:'mini',
            items: [topPanel]
        }, {
            region: 'west',
            title: '<span class="commoncss">' + b_eletitle + '信息</span>',
            tools: [{
                id: 'refresh',
                handler: function () {
                    treeEle.getStore().reload();
                }
            }],
            width: 350,
            minSize: 220,
            maxSize: 370,
            collapsible: true,
            split: true,
            layout: 'fit',
            margins: '3 3 3 3',
            scrollable: 'y',
            border: false,
            items: [treeEle]
        }, {
            id: 'boxPanel',
            region: 'center',
            // title : '<span
            // class="commoncss">基础数据列表</span>',
            layout: 'fit',
            margins: '3 3 3 3',
            border: false,
            items: [grid]
        }]
    });

    topPanel.on('afterLoadUI', function (comp) {
        viewport.doLayout();
    });

    function reloadMainPanel() {
        var body = Ext.getCmp('detailcenter').body; // 获得panel主体内容
        body.update('').setStyle('background', '#fff'); // 清空panel内容
    }

    /**
     * 根据条件查询
     */
    function queryBaseItem() {
        // var selectModel = deptTree.getSelectionModel();
        // var selectNode = selectModel.getSelectedNode();
        // var rolecode = selectNode.attributes.code;
        store.reload({
            params: {
                start: 0,
                limit: bbar.pageSize,
                source: Ext.getCmp('cmb_select_ele').getValue(),
                code: seltreecode,
                queryparam: Ext.getCmp('queryParam').getValue()
            }
        });
    }

    var addUiArray = [{
        fieldLabel: '显示码',
        name: 'code',
        xtype: 'textfield',
        afterLabelTextTpl: [
            '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>'
        ],
        allowBlank: false,
        labelStyle: micolor,
        anchor: '99%'
    }, {
        fieldLabel: '显示名称',
        name: 'name',
        xtype: 'textfield',
        afterLabelTextTpl: [
            '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>'
        ],
        allowBlank: false,
        labelStyle: micolor,
        anchor: '99%'
    }, {
        fieldLabel: '指定父级',
        name: 'parent_id',
        xtype: 'treefield',
        source: Ext.getCmp('cmb_select_ele').getValue(),
        selectNodeModel: 'all',
        isDirect: true,
        isPermission: false,
        anchor: '99%'
    }, {
        fieldLabel: '是否启用',
        name: 'enabled',
        xtype: 'chkfield',
        anchor: '99%'
    }, {
        id: 'windowmode',
        name: 'windowmode',
        xtype: 'hidden'
    }, {
        name: 'id',
        xtype: 'hidden'
    }, {
        name: 'is_deleted',
        xtype: 'hidden',
        value: '0'
    }, {
        name: 'element_code',
        xtype: 'hidden'
    }];
    var addDataFormPanel = Ext.create('Ext.form.Panel', {
        id: 'addDataFormPanel',
        defaultType: 'textfield',
        fieldDefaults: {
            labelAlign: 'right',
            labelWidth: 70,
            msgTarget: 'side'
        },
        frame: false,
        autoHeight: true,
        userDefined: true,
        bodyStyle: 'padding:15 5 0'
    });

    /**
     * 新增基础数据窗口
     */
    var addDataWindow = Ext.create('Ext.window.Window', {
        layout: 'fit',
        width: 500,
        //height : 215,
        autoHeight: true,
        resizable: true,
        draggable: true,
        closable: true,
        modal: true,
        closeAction: 'hide',
        // iconCls : 'page_addIcon',
        collapsible: true,
        titleCollapse: true,
        maximizable: false,
        buttonAlign: 'right',
        border: false,
        animCollapse: true,
        pageY: 20,
        pageX: document.body.clientWidth / 2 - 420 / 2,
        animateTarget: Ext.getBody(),
        constrain: true,
        items: [addDataFormPanel],
        buttons: [{
            text: '保存',
            iconCls: 'acceptIcon',
            handler: function () {
                var mode = Ext.getCmp('windowmode').getValue();
                if (mode == 'add')
                    saveDataItem();
                else
                    updateDataItem();
            }
        }, {
            text: '关闭',
            iconCls: 'deleteIcon',
            handler: function () {
                addDataWindow.hide();
            }
        }]
    });

    function addInit() {
        addDataFormPanel.removeAll();
        addDataFormPanel.add(addUiArray);
        if (uiArray != null)
            addUIByServer(addDataFormPanel, uiArray);
        addDataWindow.show();
        addDataWindow.setTitle('<span class="commoncss">新增' + b_eletitle + '</span>');
        Ext.getCmp('windowmode').setValue('add');
        var dataSource = Ext.getCmp('cmb_select_ele').getValue();
        var compParent = addDataFormPanel.getForm().findField('parent_id');
        compParent.setSource(dataSource);
        addDataFormPanel.getForm().findField('element_code').setValue(dataSource);
        addDataFormPanel.getForm().findField('enabled').setValue('1');
        var selectNode = treeEle.getSelectionModel().getSelected().items[0];
        if (selectNode) {
            compParent.setValue(selectNode.id);
        }
        if (typeof initRelationBaseInfo != 'undefined')
            initRelationBaseInfo();
    }

    /**
     * 修改数据初始化
     */
    function editInit() {
        var selected = grid.getSelectionModel().getSelected();
        var record = selected.items[0];
        if (Ext.isEmpty(record)) {
            Ext.MessageBox.alert('提示', '请先选择要修改的' + b_eletitle + '信息!');
            return;
        }
        if (selected.items.length > 1) {
            Ext.MessageBox.alert('提示', '只能选择一项进行修改!');
            return;
        }
        addDataFormPanel.removeAll();
        addDataFormPanel.add(addUiArray);
        if (uiArray != null)
            addUIByServer(addDataFormPanel, uiArray);
        addDataWindow.show();
        addDataWindow
            .setTitle('<span style="font-weight:normal">修改' + b_eletitle + '</span>');
        Ext.getCmp('windowmode').setValue('edit');
        var dataSource = Ext.getCmp('cmb_select_ele').getValue();
        var compParent = addDataFormPanel.getForm().findField('parent_id');
        compParent.setSource(dataSource);
        addDataFormPanel.getForm().findField('element_code').setValue(dataSource);
        addDataFormPanel.getForm().loadRecord(record);
        if (typeof initRelationBaseInfo != 'undefined')
            initRelationBaseInfo();
        if (typeof edtRelationBaseInfo != 'undefined')
            edtRelationBaseInfo();
    }

    function saveDataItem() {
        if (!addDataFormPanel.form.isValid()) {
            Ext.MessageBox.alert('提示', '请检查需要填写的录入项！');
            return;
        }
        addDataFormPanel.form.submit({
            url: 'basedata_insert.htm',
            waitTitle: '提示',
            method: 'POST',
            waitMsg: '正在处理数据,请稍候...',
            success: function (form, action) {
                addDataWindow.hide();
                store.reload();
                // 获取对应树节点
                var parent_id = addDataFormPanel.form.findField('parent_id')
                    .getValue();
                refreshNode(parent_id);
                Ext.MessageBox.alert('提示', '数据保存成功!');
            },
            failure: function (form, action) {
                var msg = action.result.msg;
                Ext.MessageBox.alert('提示', '数据保存失败:<br>' + msg);
            }
        });
    }

    /**
     * 修改数据
     */
    function updateDataItem() {
        if (!addDataFormPanel.form.isValid()) {
            Ext.MessageBox.alert('提示', '请检查需要填写的录入项！');
            return;
        }
        addDataFormPanel.form.submit({
            url: 'basedata_update.htm',
            waitTitle: '提示',
            method: 'POST',
            waitMsg: '正在处理数据,请稍候...',
            success: function (form, action) {
                addDataWindow.hide();
                store.reload();
                // 获取对应树节点
                var parent_id = addDataFormPanel.form.findField('parent_id')
                    .getValue();
                refreshNode(parent_id);
                Ext.MessageBox.alert('提示', '数据修改成功!');
            },
            failure: function (form, action) {
                var msg = action.result.msg;
                Ext.MessageBox.alert('提示', '数据修改失败:<br>' + msg);
            }
        });
    }

    /**
     * 刷新指定节点
     */
    function refreshNode(nodeid) {
        treeEle.getStore().reload();
        if (nodeid != null && nodeid != '' && nodeid != '0') {
            treeEle.selectNodeByValue(nodeid, true);
        }
    }

    function deleteDataItems() {
        var rows = grid.getSelectionModel().getSelection();
        if (Ext.isEmpty(rows)) {
            Ext.Msg.alert('提示', '请先选中要删除的' + b_eletitle + '!');
            return;
        }
        var idlist = jsArray2SingleArray(rows, 'id');
        Ext.Msg.confirm('请确认', '<b>提示:</b>确认删除吗？<br>该节点及其所有子节点将一并删除！', function (btn, text) {
            if (btn == 'yes') {
                showWaitMsg();
                Ext.Ajax.request({
                    url: 'basedata_delete.htm',
                    success: function (response) {
                        var resultArray = Ext.util.JSON
                            .decode(response.responseText);
                        store.reload();
                        treeEle.getStore().reload();
                        Ext.Msg.alert('提示', '数据删除成功');
                    },
                    failure: function (response) {
                        Ext.Msg.alert('提示', '数据删除失败');
                    },
                    params: {
                        ids: idlist.join(),
                        element_code: Ext.getCmp('cmb_select_ele').getValue()
                    }
                });
            }
        });
    }

    function queryNode() {
        if (Ext.getCmp('local_text').getValue() == '')
            return;
        treeEle.selectNodeByLikeText(treeEle.getRootNode(), Ext.getCmp('cmb_local_type').getValue(), Ext.getCmp('local_text').getValue(), true);
    }

    function queryNextNode() {
        if (Ext.getCmp('local_text').getValue() == '')
            return;
        var selectNode = treeEle.getSelectionModel().getSelected().items[0];
        if (selectNode == null) {
            selectNode = treeEle.getRootNode();
        }
        treeEle.selectNodeByLikeText(selectNode, Ext.getCmp('cmb_local_type').getValue(), Ext.getCmp('local_text').getValue(), true);
    }

    if (typeof elecode != 'undefined') {
        var selectEle = Ext.getCmp('cmb_select_ele');
        Ext.getCmp('select_ele_box').hide();
        selectEle.setValue(elecode);
        var requestEleInfo = function (combo) {
            var dataurl = 'eleinfo_ajax_id.htm';
            if (combo.isCodeAsValue) {
                dataurl = 'eleinfo_ajax_code.htm';
            }
            Ext.Ajax.request({
                url: dataurl,
                success: function (response) {
                    var resultArray = Ext.util.JSON
                        .decode(response.responseText);
                    resultArray.code = elecode;
                    var newnode = {data: resultArray};
                    combo.fireEvent('select', combo, newnode, null);
                },
                failure: function (response) {
                },
                params: {
                    source: combo.source,
                    value: combo.value
                }
            });
        };
        requestEleInfo(selectEle);
    }
//    if (typeof showtabpanel!='undefined' && showtabPanel=='1') {
//            var titlePne = Ext.get('tab_header');
//            if (titlePne) {
//                titlePne.setStyle('display','');
//            }
//    }

    var importDataPanel = new Ext.form.FormPanel({
        id: 'importpanel',
        defaultType: 'textfield',
        labelAlign: 'right',
        labelWidth: 99,
        frame: true,
        fileUpload: true,
        items: [{
            fieldLabel: '请选择导入文件',
            name: 'theFile',
            id: 'theFile',
            inputType: 'file',
            afterLabelTextTpl: [
                '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>'
            ],
            allowBlank: true,
            anchor: '99%'
        }]
    });
    var importWindow = Ext.create('Ext.vcf.Window', {
        title: '<span style="font-weight:normal">导入Excel</span>',
        width: 380,
        height: 135,
        resizable: false,
        draggable: true,
        maximizable: false,
        items: [{
            region: 'north',
            autoHeight: true,
            items: [importDataPanel]
        }],
        buttons: [{
            text: '导入',
            iconCls: 'acceptIcon',
            handler: function () {
                var theFile = Ext.getCmp('theFile').getValue();
                if (Ext.isEmpty(theFile)) {
                    Ext.Msg.alert('提示', '请先选择您要导入的xls文件...');
                    return;
                }
                if (theFile.substring(theFile.length - 4, theFile.length) != ".xls") {
                    Ext.Msg.alert('提示', '您选择的文件格式不对,只能导入.xls或.xlsx文件!');
                    return;
                }
                importDataPanel.form.submit({
                    url: 'basedata_import.htm',
                    waitTitle: '提示',
                    method: 'POST',
                    waitMsg: '正在处理数据,请稍候...',
                    success: function (form, action) {
                        importWindow.hide();
                        store.reload();
                        // 获取对应树节点
                        refreshNode(null);
                        Ext.MessageBox.alert('提示', action.result.msg);
                    },
                    failure: function (form, action) {
                        var msg = action.result.msg;
                        Ext.MessageBox.alert('提示', '保存失败:<br>' + msg);
                    },
                    params: {
                        element_code: treeEle.getSource()
                    }
                });
            }
        }, {
            text: '关闭',
            iconCls: 'deleteIcon',
            handler: function () {
                importWindow.hide();
            }
        }]
    });


    if (typeof initOtherUI != 'undefined')
        initOtherUI();

});
