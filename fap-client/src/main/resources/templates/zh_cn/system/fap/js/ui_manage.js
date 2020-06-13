/**
 * 界面视图实现
 * @author aeon 2019
 */
var treeEle = Ext.create('Ext.vcf.AssistTree', {
    source : 'UI',
    autoScroll : true,
    isDirect : true,
    isFullData : true,
    isFullLevel : false,
    isPermission : false
});

Ext.onReady(function() {

    var windowmode = 'add';

    var seltreeid = '-1';
    treeEle.on('itemclick', function(tree,node) {
        if (node.get('id') == '0') {
            seltreeid = '-1';
            return;
        } else {
            seltreeid = node.id;
        }
        form.getForm().reset();
        form.form.load({
            url : 'query_uimanager_view.htm',
            params : {
                ui_id : seltreeid
            },
            waitMsg : '请稍后......',
            success : function(response, option) {
            }
        });
        preview.removeAll();
        if (node.get('xtype') == 'inputpanel' || node.get('xtype') == 'panel' || node.get('xtype') == 'querypanel') {
            Ext.Ajax.request({
                url : 'ui_designer.htm',
                success : function(response) {
                    preview.removeAll();
                    var resultArray = Ext.util.JSON
                        .decode(response.responseText);
                    var previewinput = Ext.create('Ext.vcf.FormPanel',{
                        fieldDefaults: {
                            labelAlign: 'left',
                            labelWidth: 100,
                            msgTarget: 'side'
                        },
                        isPreview : true
                    });
                    addUIByServer(previewinput, resultArray);
                    preview.add(previewinput);
                    //preview.doLayout();
                },
                failure : function(response) {

                },
                params : {
                    ui_id : seltreeid
                }
            });
        } else if (node.get('xtype') == 'tablegrid' || node.get('xtype') == 'grid') {
            Ext.Ajax.request({
                url : 'ui_designer.htm',
                success : function(response) {
                    preview.removeAll();
                    var resultArray = Ext.util.JSON
                        .decode(response.responseText);
                    var previewgrid = Ext.create('Ext.vcf.TableGrid',{
                        url : 'ui_nullstore.htm',
                        isPaged : false,
                        isPreview : true,
                        region : 'center',
                        //width : '100%',
                        height : '100%'
                    });
                    addGridByServer(previewgrid, resultArray);
                    console.log(previewgrid);
                    console.log(preview);
                    preview.add(previewgrid);
                    // previewgrid.doLayout();
                },
                failure : function(response) {

                },
                params : {
                    ui_id : seltreeid
                }
            });
        }
    });

    var toolbar = Ext.create('Ext.toolbar.Toolbar', {
        items: [{
            id: 'ui:insert',
            text : '新增',
            iconCls : 'page_addIcon',
            handler : function() {
                addInit();
            }
        }, '-', {
            id: 'ui:update',
            text : '修改',
            iconCls : 'page_edit_1Icon',
            handler : function() {
                editInit();
            }
        }, '-', {
            id: 'ui:delete',
            text : '删除',
            iconCls : 'page_delIcon',
            handler : function() {
                deleteUIView();
            }
        }, '-', {
            text : '缓存同步',
            iconCls : 'arrow_refreshIcon',
            handler : function() {
                Ext.Ajax.request({
                    url : 'ui_sync_memory.htm',
                    success : function(response) {
                        Ext.Msg.alert('提示', '缓存同步成功');
                    },
                    failure : function(response) {
                        Ext.Msg.alert('提示', '缓存同步失败:'+response.responseText);
                    }
                });
            }
        } ]
    });

    /**
     * 视图基本信息界面panel
     */
    var form = Ext.create('Ext.form.Panel', {
        // autoHeight : true,
        bodyStyle: 'padding:5 10 5 5', // 表单元素和表单面板的边距
        frame: false,
        border: false,
        labelWidth: 65,
        labelAlign: "right",
        items: [{
            layout: "column", // 从左往右的布局
            border: false,
            items: [{
                columnWidth: .33, // 该列有整行中所占百分比
                layout: "form", // 从上往下的布局
                defaultType: 'textfield',
                border: false,
                items: [{
                    name: "ui_code",
                    fieldLabel: "视图编码",
                    anchor: '100%'
                }, {
                    name: "servletpath",
                    fieldLabel: "服务路径",
                    anchor: '100%'
                }]
            }, {
                columnWidth: .33,
                layout: "form",
                defaultType: 'textfield',
                border: false,
                items: [{
                    name: "ui_name",
                    fieldLabel: "视图名称",
                    anchor: '100%'
                }, {
                    name: "comp_id",
                    fieldLabel: "容器ID",
                    anchor: '100%'
                }]
            }, {
                columnWidth: .33,
                layout: "form",
                defaultType: 'textfield',
                border: false,
                items: [{
                    name: "xtype",
                    xtype: "combofield",
                    fieldLabel: "视图类型",
                    allowBlank: false,
                    enumData: "tablegrid#列表视图+inputpanel#录入视图+querypanel#查询视图",
                    anchor: '100%'
                }, {
                    name: "total_column",
                    fieldLabel: "容器列",
                    anchor: '100%'
                }]
            }]
        }]

    });
    /**
     * 视图预览界面panel
     */
    var preview = Ext.create('Ext.Panel', {
        // border : false,
        id : 'preview',
        name : 'preview',
    });
    /**
     * 布局
     */
    var viewport = new Ext.Viewport({
        layout : 'border',
        items : [ {
            region : 'west',
            title : '<span class="commoncss">视图信息</span>',
            tools : [ {
                id : 'refresh',
                handler : function() {
                    treeEle.getStore().reload();
                }
            } ],
            width : 400,
            minSize : 220,
            maxSize : 470,
            collapsible : true,
            split : true,
            layout : 'fit',
            margins : '3 3 3 3',
            border : false,
            items : [ treeEle ]
        }, {
            id : 'boxPanel',
            region : 'center',
            xtype : 'tabpanel',
            activeTab : 0,
            items : [ {
                title : '<span class="commoncss">视图基本信息</span>',
                region : 'center',
                layout : 'border',
                border : true,
                items : [ {
                    height : '200',
                    region : 'north',
                    border : true,
                    tbar : toolbar,
                    items : [ form ]
                }, {
                    title : '<span class="commoncss">预览效果</span>',
                    iconCls : 'previewIcon',
                    region : 'center',
                    border : true,
                    items : [ preview ]
                } ]
            } ]
        } ]
    });

    // 每一个Tab都可以看作为一个Panel
    var tabUIBase = Ext.create('Ext.form.Panel', {
        id : 'tabUIBase',
        region : 'north',
        labelWidth : 60, // 标签宽度
        height : 150,
        // frame : true, // 是否渲染表单面板背景色
        labelAlign : 'right', // 标签对齐方式
        bodyStyle : 'padding:6 6 6 6', // 表单元素和表单面板的边距
        // tbar:tb, //工具栏
        items : [ {
            layout : "column", // 从左往右的布局
            border : false,
            items : [ {
                columnWidth : .49, // 该列有整行中所占百分比
                defaultType : 'textfield',
                layout : "form", // 从上往下的布局
                border : false,
                items : [ {
                    name : "ui_code",
                    fieldLabel : "视图编码",
                    allowBlank : false,
                    labelStyle : micolor,
                    anchor : '100%'
                }, {
                    name : "xtype",
                    xtype : "combofield",
                    fieldLabel : "视图类型",
                    allowBlank : false,
                    enumData : "tablegrid#列表视图+inputpanel#录入视图+querypanel#查询视图",
                    value : 'inputpanel',
                    listeners : {
                        'change' : function(combo, newvalue, oldvalue) {
                            Ext.getCmp('btn_leftcol').setDisabled(combo.getValue()!='tablegrid');
                            Ext.getCmp('btn_rightcol').setDisabled(combo.getValue()!='tablegrid');
                        },
                        'select' : function(combo, record, index) {
                            Ext.Ajax.request({
                                url : 'cache_uiconfmain.htm',
                                success : function(response) {
                                    var array = Ext.util.JSON
                                        .decode(response.responseText);
                                    var resultArray = array['data'];
                                    //editUIGrid.stopEditing();
                                    var storeAttr = editUIGrid.store;
                                    storeAttr.removeAll();
                                    for (var irec = 0; resultArray!=null && irec < resultArray.length; irec++) {
                                        var newrecord = new MyRecord();
                                        storeAttr.add(newrecord);
                                        newrecord.set('uiconf_field',
                                            resultArray[irec]['uiconf_field']);
                                        newrecord.set('uiconf_title',
                                            resultArray[irec].uiconf_title);
                                        newrecord.set('uiconf_value',
                                            resultArray[irec].uiconf_value);
                                        newrecord.set('uiconf_datatype',
                                            resultArray[irec].uiconf_datatype);
                                        newrecord.set('ref_selmodel',
                                            resultArray[irec].ref_selmodel);
                                        newrecord.set('is_contain',
                                            resultArray[irec].is_contain);
                                    }
                                    storeAttr.commitChanges();
                                    editUIGrid.selModel.querySelectRecords();
                                },
                                failure : function(response) {
                                },
                                params : {
                                    uiconf_id : seltreeid,xtype : combo.getValue()
                                }
                            });

                            if (combo.getValue()=='tablegrid') {
                                editColPanel.form.findField('width').show();
                                editColPanel.form.findField('cols').hide();
                                editColPanel.form.findField('field_logic').hide();
                            } else if (combo.getValue()=='inputpanel') {
                                editColPanel.form.findField('width').hide();
                                editColPanel.form.findField('cols').show();
                                editColPanel.form.findField('field_logic').hide();
                            } else if (combo.getValue()=='querypanel') {
                                editColPanel.form.findField('width').hide();
                                editColPanel.form.findField('cols').show();
                                editColPanel.form.findField('field_logic').show();
                            }
                        }
                    },
                    anchor : '100%'
                }, {
                    name : "comp_id",
                    fieldLabel : "容器ID",
                    anchor : '100%'
                } ]
            }, {
                columnWidth : .5,
                defaultType : 'textfield',
                layout : "form",
                border : false,
                items : [ {
                    name : "ui_name",
                    fieldLabel : "视图名称",
                    allowBlank : false,
                    labelStyle : micolor,
                    anchor : '100%'
                }, {
                    name : "servletpath",
                    fieldLabel : "服务路径",
                    allowBlank : true,
                    anchor : '100%'
                }, {
                    name : "total_column",
                    fieldLabel : "容器列",
                    xtype : "numberfield",
                    allowBlank : false,
                    allowDecimals : false, // 是否允许输入小数
                    allowNegative : false, // 是否允许输入负数
                    maxValue : 30, // 允许输入的最大值
                    minValue : 1, // 允许输入的最小值
                    value : 1,
                    anchor : '100%'
                } ]
            }, {
                columnWidth : .01,
                layout : "form",
                border : false,
                items : [ {
                    name : "ui_id",
                    xtype : "hidden"
                }]
            } ]
        } ]
    });

    var editUIGrid = Ext.create('Ext.vcf.ViewEditorTableGrid', {
        id : 'editUIGrid',
        url : 'query_uiconfmain.htm',
        title : '<span class="commoncss">视图属性设置</span>',
        region : 'center',
        contain_field : 'is_contain',
        keyDataIndex : 'uiconf_field',
        editDataIndex : 'is_contain',
        isRowEditor : true,
        columnBase : [ {xtype:'rownumberer'},{
            header : '属性名',
            dataIndex : 'uiconf_field',
            sortable : true,
            width : 100
        }, {
            header : '属性中文名',
            dataIndex : 'uiconf_title',
            sortable : true,
            width : 260
        }, {
            header : '属性值',
            dataIndex : 'uiconf_value',
            itemId : 'uiconf_value',
            editable : true,
            width : 350
        } ],
        fieldBase : [ {
            name : 'uiconf_field'
        }, {
            name : 'uiconf_title'
        }, {
            name : 'uiconf_value'
        }, {
            name : 'uiconf_datatype'
        }, {
            name : 'ref_selmodel'
        }, {
            name : 'is_contain'
        }, {
            name : 'editmode'
        } ],
        params : {
            uiconf_id : seltreeid,xtype : '-1'
        },
        /*
                viewConfig : {
                    // 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
                    onRowSelect : function(row){
                        this.addRowClass(row, this.selectedRowClass);
                    },


                    onRowDeselect : function(row){
                        this.removeRowClass(row, this.selectedRowClass);
                    }
                },
                listeners : {
                    'afteredit' : function(ed) {
                        if (ed.record.get('is_contain')==1) {
                            ed.grid.getView().onRowSelect(ed.row);
                        } else {
                            ed.grid.getView().onRowDeselect(ed.row);
                        }
                    }
                },
        */
        stripeRows : false,
        isPaged : false
    });

    var tabUIMain = new Ext.Panel({
        layout : 'border',
        id : 'tabUIMain',
        title : '视图信息',
        labelWidth : 60, // 标签宽度
        // frame : true, // 是否渲染表单面板背景色
        labelAlign : 'right', // 标签对齐方式
        bodyStyle : 'padding:6 6 6 6', // 表单元素和表单面板的边距
        // tbar:tb, //工具栏
        height : 365,
        items : [ tabUIBase,editUIGrid ]
    });


    var treeCols = new Ext.vcf.AssistTree({
        title: '字段信息',
        rootVisible: false,
        url: 'ui_detail_tree.htm',
        params: {
            ui_id: '-1'
        },
        //uiProvides : { cxj : EnableTreeNodeUI},
        autoScroll: true
    });

    // 检查新增行的可编辑单元格数据合法性
    function validateUIEditGrid(m, colName) {
        var editing = editUIGrid.editing,
            colIndex = editUIGrid.getView().headerCt.child(colName);
        for (var i = 0; i < m.length; i++) {
            var record = m[i];
            var rowIndex = editUIGrid.store.indexOfId(record.id);
            var value = record.get(colName);
            if (Ext.isEmpty(value) && record.get("is_contain") == 1) {
                Ext.Msg.alert('提示', '选中的属性值字段数据不允许为空!', function () {
                    editing.startEditByPosition(rowIndex, colIndex);
                });
                return false;
            }
            //界面视图基本不需要做下面的有效值判断，可以去掉 只有那种赋值用下面的判断才好by aeon
            var editor = editUIGrid.getCellEditor(record);
            if (!Ext.isEmpty(editor) && !Ext.isEmpty(value) && 1 == 0) {
                if (!editor.field.validateValue(value) && record.get("is_contain") == 1) {
                    Ext.Msg.alert('提示', '属性值字段数据校验不合法,请重新输入!', function () {
                        editing.startEditByPosition(rowIndex, colIndex);
                    });
                    return false;
                }
            }
        }
        return true;
    }

    // 检查新增行的可编辑单元格数据合法性
    function validateEditGrid(m, colName) {
        var colIndex = editColGrid.getView().headerCt.child(colName);
        for (var i = 0; i < m.length; i++) {
            var record = m[i];
            var rowIndex = editColGrid.store.indexOfId(record.id);
            var value = record.get(colName);
            if (Ext.isEmpty(value) && record.get("is_contain") == 1) {
                Ext.Msg.alert('提示', '选中的属性值字段数据不允许为空!', function () {
                    editColGrid.startEditByPosition(rowIndex, colIndex);
                });
                return false;
            }
            //界面视图基本不需要做下面的有效值判断，可以去掉 只有那种赋值用下面的判断才好by aeon
            var editor = editColGrid.getCellEditor(record);
            if (!Ext.isEmpty(editor) && !Ext.isEmpty(value) && 1 == 0) {
                if (!editor.field.validateValue(value) && record.get("is_contain") == 1) {
                    Ext.Msg.alert('提示', '属性值字段数据校验不合法,请重新输入!', function () {
                        editColGrid.startEditByPosition(rowIndex, colIndex);
                    });
                    return false;
                }
            }
        }
        return true;
    }

    var seltreecolid = '-1';

    function disposeCacheUICol() {
        var m = editUIGrid.store.getModifiedRecords(); // 获取修改过的record数组对象
        var jsonArray = [];
        // 将record数组对象转换为简单Json数组对象
        Ext.each(m, function (item) {
            jsonArray.push(item.data);
        });
        // 提交到后台处理
        Ext.Ajax.request({
            url: 'update_uiconfandmain_cache.htm',
            success: function (response) { // 回调函数有1个参数
                var array = Ext.util.JSON.decode(response.responseText);
                if (array['success']) {
                    var resultArray = array['data'];
                } else {
                    Ext.MessageBox.alert('提示', array['msg']);
                }
            },
            failure: function (response) {
                Ext.MessageBox.alert('提示', '数据保存失败');
            },
            params: {
                // 系列化为Json资料格式传入后台处理
                dirtydata: Ext.encode(jsonArray),
                ui_id: seltreeid
            }
        });
    }

    function disposeCacheCol() {
        var m = editColGrid.store.getModifiedRecords(); // 获取修改过的record数组对象
        var jsonArray = [];
        // 将record数组对象转换为简单Json数组对象
        Ext.Array.each(m, function (item) {
            jsonArray.push(item.data);
        });
        // 提交到后台处理
        var oldnode = treeCols.getRootNode().findChild("id", editColPanel.getForm().findField('ui_detail_id').getValue());
        if (oldnode == null) return;
        var param = editColPanel.getForm().getValues();
        Ext.Ajax.request({
            url: 'update_uiconfanddetail_cache.htm',
            success: function (response) { // 回调函数有1个参数
                var array = Ext.util.JSON.decode(response.responseText);
                if (array['success']) {
                    var resultArray = array['data'];
                    var newnode = mergeObj(oldnode.data, resultArray);
                    newnode.text = resultArray.field_name + ' ' + resultArray.field_title;
                    treeCols.getRootNode().replaceChild(newnode, oldnode);
                    //mergeObj(oldnode.data,resultArray);
                    //oldnode.setText(resultArray.field_name+' '+resultArray.field_title);
                } else {
                    Ext.MessageBox.alert('提示', array['msg']);
                }
            },
            failure: function (response) {
                Ext.MessageBox.alert('提示', '数据保存失败');
            },
            params: mergeObj(param, {
                // 系列化为Json资料格式传入后台处理
                dirtydata: Ext.encode(jsonArray),
                ui_id: seltreeid
            })
        });
    }

    function validCacheUICol() {
        var storeAttr = editUIGrid.store;
        //editUIGrid.stopEditing();
        var m = storeAttr.getModifiedRecords(); // 获取修改过的record数组对象
        //因还有字段名等uidetail表的记录缓冲，所以在属性记录未作修改的时候也要继续
//		if (Ext.isEmpty(m)) {
//			Ext.MessageBox.alert('提示', '未做任何修改，不需要保存!');
//			return 'abort';
//		}
        if (!validateUIEditGrid(m, 'uiconf_value')) {
            //Ext.Msg.alert('提示', '属性值字段数据校验不合法,请重新输入!');
            return 'abort';
        }
        return 'cache';
    }

    function validCacheCol() {
        var selectModel = treeCols.getSelectionModel();
        var selectNode = selectModel.selected.items[0];
        if (Ext.isEmpty(selectNode)) return 'skip';
        if (!editColPanel.form.isValid()) {
            Ext.MessageBox.alert('提示', '请检查需要填写的字段列信息！');
            return 'abort';
        }
        var b = true;
        var fieldname = editColPanel.getForm().findField('field_name').getValue();
        var ui_detail_id = editColPanel.getForm().findField('ui_detail_id').getValue();
        var childnodes = treeCols.getRootNode().childNodes;
        Ext.each(childnodes, function () {
            if (this.id != ui_detail_id && this.data.code == fieldname) {
                return b = false;
            }
        });
        if (!b) {
            Ext.MessageBox.alert('提示', '填写的字段列编码重复，请检查！');
            return 'abort';
        }
        var storeAttr = editColGrid.store;
        //editColGrid.stopEditing();
        var m = storeAttr.getModifiedRecords(); // 获取修改过的record数组对象
        //因还有字段名等uidetail表的记录缓冲，所以在属性记录未作修改的时候也要继续
//		if (Ext.isEmpty(m)) {
//			Ext.MessageBox.alert('提示', '未做任何修改，不需要保存!');
//			return 'abort';
//		}
        if (!validateEditGrid(m, 'uiconf_value')) {
            //Ext.Msg.alert('提示', '属性值字段数据校验不合法,请重新输入!');
            return 'abort';
        }
        return 'cache';
    }

    treeCols.on('beforeselect', function (model,node) {
        var isvalid = validCacheCol();
        if (isvalid == 'abort') {
            return false;
        } else if (isvalid == 'cache') {
            disposeCacheCol();
        }
        ;
        return true;
    });

    treeCols.on('select', function (model,node) {
        if (node == this.getRootNode()) {
            seltreecolid = '-1';
        } else {
            seltreecolid = node.id;
        }
        editColPanel.getForm().reset();
        editColPanel.getForm().setValues(node.data);
        Ext.Ajax.request({
            url: 'cache_uiconfdetail.htm',
            success: function (response) {
                var array = Ext.util.JSON
                    .decode(response.responseText);
                var resultArray = array['data'];
                // editColGrid.stopEditing();
                var storeAttr = editColGrid.store;
                editColGrid.control_type = node.data.field_type;
                storeAttr.removeAll();
                for (var irec = 0; resultArray != null && irec < resultArray.length; irec++) {
                    var newrecord = new MyRecord();
                    storeAttr.add(newrecord);
                    newrecord.set('uiconf_field',
                        resultArray[irec]['uiconf_field']);
                    newrecord.set('uiconf_title',
                        resultArray[irec].uiconf_title);
                    newrecord.set('uiconf_value',
                        resultArray[irec].uiconf_value);
                    newrecord.set('uiconf_datatype',
                        resultArray[irec].uiconf_datatype);
                    newrecord.set('ref_selmodel',
                        resultArray[irec].ref_selmodel);
                    newrecord.set('is_contain',
                        resultArray[irec].is_contain);
                }
                storeAttr.commitChanges();
                editColGrid.selModel.querySelectRecords();
            },
            failure: function (response) {
            },
            params: {
                ui_id:seltreeid,
                uiconf_id: seltreecolid,
                xtype: node.data.field_type
            }
        });
    });

    var editColPanel = new Ext.form.FormPanel({
        id : 'editColPanel',
        layout : {type:'table',columns:3},
        frame : false, // 是否渲染表单面板背景色
        width: '100%',
        labelAlign : 'right', // 标签对齐方式
        defaultType : 'textfield',
        fieldDefaults: {
            labelAlign: 'left',
            labelWidth: 60,
            msgTarget: 'side'
        },
        // defaults:{//设置默认属性
        //     width:235
        // },
        // tbar:tb, //工具栏
        items : [{
            name: "field_name",
            fieldLabel: "字段编码",
            allowBlank: false,
            labelStyle: micolor,
            anchor: '99%'
        }, {
            name: "field_title",
            fieldLabel: "字段名称",
            allowBlank: false,
            labelStyle: micolor,
            anchor: '99%'
        }, {
            name: "field_type",
            xtype: "combofield",
            fieldLabel: "控件类型",
            allowBlank: false,
            store: XTYPEStore,
            value: '',
            anchor: '99%',
            listeners: {
                'select': function (combo, record, index) {
                    Ext.Ajax.request({
                        url: 'cache_uiconfdetail.htm',
                        success: function (response) {
                            var array = Ext.util.JSON
                                .decode(response.responseText);
                            var resultArray = array['data'];
                            //editColGrid.stopEditing();
                            var storeAttr = editColGrid.store;
                            editColGrid.control_type = combo.getValue();
                            storeAttr.removeAll();
                            for (var irec = 0; resultArray != null && irec < resultArray.length; irec++) {
                                var newrecord = new MyRecord();
                                storeAttr.add(newrecord);
                                newrecord.set('uiconf_field',
                                    resultArray[irec]['uiconf_field']);
                                newrecord.set('uiconf_title',
                                    resultArray[irec].uiconf_title);
                                newrecord.set('uiconf_value',
                                    resultArray[irec].uiconf_value);
                                newrecord.set('uiconf_datatype',
                                    resultArray[irec].uiconf_datatype);
                                newrecord.set('ref_selmodel',
                                    resultArray[irec].ref_selmodel);
                                newrecord.set('is_contain',
                                    resultArray[irec].is_contain);
                            }
                            storeAttr.commitChanges();
                            editColGrid.selModel.querySelectRecords();
                        },
                        failure: function (response) {
                        },
                        params: {
                            ui_id: seltreeid,
                            uiconf_id: seltreecolid,
                            xtype: combo.getValue()
                        }
                    });
                }
            }
        }, {
            name: "field_logic",
            xtype: "combofield",
            fieldLabel: "逻辑关系",
            //allowBlank: false,
            store: LOGICOPStore,
            anchor: '99%'
        }, {
            name: "width",
            fieldLabel: "宽度",
            xtype: "numberfield",
            allowBlank: false,
            allowDecimals: false, // 是否允许输入小数
            allowNegative: false, // 是否允许输入负数
            maxValue: 1000, // 允许输入的最大值
            minValue: 1, // 允许输入的最小值
            value: 100,
            anchor: '99%'
        }, {
            name: "cols",
            fieldLabel: "容器列",
            xtype: "numberfield",
            allowBlank: false,
            allowDecimals: false, // 是否允许输入小数
            allowNegative: false, // 是否允许输入负数
            maxValue: 30, // 允许输入的最大值
            minValue: 1, // 允许输入的最小值
            value: 1,
            anchor: '99%'
        }, {
            name: "ui_detail_id",
            xtype: "hidden"
        }]
    });

    // var editColPanel = new Ext.form.FormPanel({
    //     id : 'editColPanel',
    //     labelWidth : 60, // 标签宽度
    //     // frame : true, // 是否渲染表单面板背景色
    //     labelAlign : 'right', // 标签对齐方式
    //     bodyStyle : 'padding:6 6 6 6', // 表单元素和表单面板的边距
    //     // tbar:tb, //工具栏
    //     items : [ {
    //         layout : "column", // 从左往右的布局
    //         border : false,
    //         items : [ {
    //             columnWidth : .33, // 该列有整行中所占百分比
    //             defaultType : 'textfield',
    //             layout : "form", // 从上往下的布局
    //             border : false,
    //             items : [ {
    //                 name : "field_name",
    //                 fieldLabel : "字段编码",
    //                 allowBlank : false,
    //                 labelStyle : micolor,
    //                 anchor : '99%'
    //             }, {
    //                 name : "width",
    //                 fieldLabel : "宽度",
    //                 xtype : "numberfield",
    //                 allowBlank : false,
    //                 allowDecimals : false, // 是否允许输入小数
    //                 allowNegative : false, // 是否允许输入负数
    //                 maxValue : 1000, // 允许输入的最大值
    //                 minValue : 1, // 允许输入的最小值
    //                 value : 100,
    //                 anchor : '99%'
    //             } ]
    //         }, {
    //             columnWidth : .33,
    //             defaultType : 'textfield',
    //             layout : "form",
    //             border : false,
    //             items : [ {
    //                 name : "field_title",
    //                 fieldLabel : "字段名称",
    //                 allowBlank : false,
    //                 labelStyle : micolor,
    //                 anchor : '99%'
    //             }, {
    //                 name : "cols",
    //                 fieldLabel : "容器列",
    //                 xtype : "numberfield",
    //                 allowBlank : false,
    //                 allowDecimals : false, // 是否允许输入小数
    //                 allowNegative : false, // 是否允许输入负数
    //                 maxValue : 30, // 允许输入的最大值
    //                 minValue : 1, // 允许输入的最小值
    //                 value : 1,
    //                 anchor : '99%'
    //             } ]
    //         }, {
    //             columnWidth : .33,
    //             defaultType : 'textfield',
    //             layout : "form",
    //             border : false,
    //             items : [ {
    //                 name : "field_type",
    //                 xtype : "combofield",
    //                 fieldLabel : "控件类型",
    //                 allowBlank : false,
    //                 store : XTYPEStore,
    //                 value : '',
    //                 anchor : '99%',
    //                 listeners : {
    //                     'select' : function(combo, record, index) {
    //                         Ext.Ajax.request({
    //                             url : 'cache_uiconfdetail.htm',
    //                             success : function(response) {
    //                                 var array = Ext.util.JSON
    //                                     .decode(response.responseText);
    //                                 var resultArray = array['data'];
    //                                 //editColGrid.stopEditing();
    //                                 var storeAttr = editColGrid.store;
    //                                 storeAttr.removeAll();
    //                                 for (var irec = 0; resultArray!=null && irec < resultArray.length; irec++) {
    //                                     var newrecord = new MyRecord();
    //                                     storeAttr.add(newrecord);
    //                                     newrecord.set('uiconf_field',
    //                                         resultArray[irec]['uiconf_field']);
    //                                     newrecord.set('uiconf_title',
    //                                         resultArray[irec].uiconf_title);
    //                                     newrecord.set('uiconf_value',
    //                                         resultArray[irec].uiconf_value);
    //                                     newrecord.set('uiconf_datatype',
    //                                         resultArray[irec].uiconf_datatype);
    //                                     newrecord.set('ref_selmodel',
    //                                         resultArray[irec].ref_selmodel);
    //                                     newrecord.set('is_contain',
    //                                         resultArray[irec].is_contain);
    //                                 }
    //                                 storeAttr.commitChanges();
    //                                 editColGrid.selModel.querySelectRecords();
    //                             },
    //                             failure : function(response) {
    //                             },
    //                             params : {
    //                                 ui_id : seltreeid,
    //                                 uiconf_id : seltreecolid,
    //                                 xtype : combo.getValue()
    //                             }
    //                         });
    //                     }
    //                 }
    //             } ]
    //         }, {
    //             columnWidth : .01,
    //             layout : "form",
    //             border : false,
    //             items : [ {
    //                 name : "ui_detail_id",
    //                 xtype : "hidden"
    //             }]
    //         }]
    //     } ]
    // });
    //
    // 定义一个Record
    Ext.define('MyRecord',{
        extend : 'Ext.data.Record',
        fields:[{
            name : 'uiconf_field',
            type : 'string'
        }, {
            name : 'uiconf_title',
            type : 'string'
        }, {
            name : 'uiconf_value',
            type : 'string'
        }, {
            name : 'uiconf_datatype',
            type : 'string'
        }, {
            name : 'ref_selmodel',
            type : 'string'
        }, {
            name : 'is_contain',
            type : 'integer'
        }, {
            name : 'editmode',
            type : 'string'
        }]});
    var editColGrid = Ext.create('Ext.vcf.ViewEditorTableGrid',{
        id : 'editColGrid',
        url : 'query_uiconfdetail.htm',
        region : 'center',
        keyDataIndex : 'uiconf_field',
        editDataIndex : 'is_contain',
        isRowEditor : true,
        contain_field : 'is_contain',
        columnBase : [ {xtype:'rownumberer'},{
            header : '属性名',
            dataIndex : 'uiconf_field',
            sortable : true,
            width : 100
        }, {
            header : '属性中文名',
            dataIndex : 'uiconf_title',
            sortable : true,
            width : 260
        }, {
            header : '属性值',
            dataIndex : 'uiconf_value',
            editable : true,
            width : 150
        } ],
        fieldBase : [ {
            name : 'uiconf_field'
        }, {
            name : 'uiconf_title'
        }, {
            name : 'uiconf_value'
        }, {
            name : 'uiconf_datatype'
        }, {
            name : 'ref_selmodel'
        }, {
            name : 'is_contain'
        }, {
            name : 'editmode'
        } ],
        params : {
            uiconf_id : '0',
            xtype : '-1'
        },
        /*viewConfig : {
            // 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
            onRowSelect : function(row){
                this.addRowClass(row, this.selectedRowClass);
            },


            onRowDeselect : function(row){
                this.removeRowClass(row, this.selectedRowClass);
            }
        },
        listeners : {
            'afteredit' : function(ed) {
                if (ed.record.get('is_contain')==1) {
                    ed.grid.getView().onRowSelect(ed.row);
                } else {
                    ed.grid.getView().onRowDeselect(ed.row);
                }
            }
        },*/
        stripeRows : false,
        isPaged : false
    });

    //自定义editor
//	editColGrid.getColumnModel().getCellEditor=function(colIndex, rowIndex) {
//		var dataIndex = this.getDataIndex(colIndex);
//		var record = editColGrid.getStore().getAt(rowIndex);
//		return this.config[colIndex].getCellEditor(rowIndex);
//	};
    // 每一个Tab都可以看作为一个Panel
    var tabUIInfo = new Ext.Panel({
        layout : 'border',
        id : 'tabUIInfo',
        title : '字段列信息',
        labelWidth : 60, // 标签宽度
        // frame : true, // 是否渲染表单面板背景色
        labelAlign : 'right', // 标签对齐方式
        bodyStyle : 'padding:6 6 6 6', // 表单元素和表单面板的边距
        // tbar:tb, //工具栏
        height : 365,
        items : [ {
            region : 'west',
            layout : 'border',
            split : true,
            width : 220,
            items : [ {
                region : 'east',
                width : 30,
                bodyStyle : 'padding : 3 3 3 3',
                items : [ {
                    xtype : 'panel',
                    height : 80,
                    border : false,
                    anchor : '100%'
                }, {
                    id : 'btn_upcol',
                    xtype : 'button',
                    iconCls : 'upIcon',
                    listeners : {
                        'click' : function(btn, e) {
                            upColCache();
                        }
                    },
                    anchor : '100%'
                }, {
                    xtype : 'panel',
                    height : 20,
                    border : false,
                    anchor : '100%'
                }, {
                    id : 'btn_downcol',
                    xtype : 'button',
                    iconCls : 'downIcon',
                    listeners : {
                        'click' : function(btn, e) {
                            downColCache();
                        }
                    },
                    anchor : '100%'
                }, {
                    xtype : 'panel',
                    height : 50,
                    border : false,
                    anchor : '100%'
                }, {
                    id : 'btn_leftcol',
                    xtype : 'button',
                    iconCls : 'leftIcon',
                    listeners : {
                        'click' : function(btn, e) {
                            leftColCache();
                        }
                    },
                    anchor : '100%'
                }, {
                    xtype : 'panel',
                    height : 20,
                    border : false,
                    anchor : '100%'
                }, {
                    id : 'btn_rightcol',
                    xtype : 'button',
                    iconCls : 'rightIcon',
                    listeners : {
                        'click' : function(btn, e) {
                            rightColCache();
                        }
                    },
                    anchor : '100%'
                } ]
            }, {
                region : 'center',
                layout : 'fit',
                items : [ treeCols ],
                tbar : Ext.create('Ext.toolbar.Toolbar',{
                    items :[ {
                        text : '新增',
                        iconCls : 'page_addIcon',
                        handler : function() {
                            var validstr = validCacheCol();
                            if (validstr=='abort') {
                                return;
                            } else if (validstr=='cache'){
                                disposeCacheCol();
                            }
                            addColWindow.show();
                            addColWindow.setTitle("<span class='commoncss'> 新增字段信息</span>");
                            addColFormPanel.getForm().findField("ui_id").setValue(tabUIBase.getForm().findField("ui_id").getValue());
                        }
                    }, '-', {
                        text : '删除',
                        iconCls : 'page_delIcon',
                        handler : function() {
                            deleteColCache();
                        }
                    } ]})
            }]
        }, {
            region : 'center',
            layout : 'border',
            border : false,
            items : [ {
                region : 'north',
                height : 80,
                items : [ editColPanel ]
            }, {
                region : 'center',
                layout : 'fit',
                title : '字段属性',
                tools: [{
                    type: 'plus',
                    handler: addUIConf
                },{
                    type: 'minus',
                    handler: delUIConf
                }],
                items : [ editColGrid ]
            } ]
        } ]
    });

    var uiViewTabs = new Ext.TabPanel({
        region : 'center',
        margins : '3 3 3 3',
        activeTab : 0,
        items : [ tabUIMain, tabUIInfo ],
        enableTabScroll : true,
        // autoWidth : true
        // height : 200
        anchor : '100%'
    });

    var addUIViewWindow = new Ext.vcf.Window({
        title : '<span class="commoncss">界面视图管理</span>', // 窗口标题
        iconCls : 'applicationIcon',
        layout : 'fit', // 设置窗口布局模式
        width : 960, // 窗口宽度
        height : 560, // 窗口高度
        // tbar : tb, // 工具栏
        closable : true, // 是否可关闭
        closeAction : 'hide', // 关闭策略
        bodyStyle : 'background-color:#FFFFFF',
        collapsible : true, // 是否可收缩
        maximizable : false, // 设置是否可以最大化
        modal : true,
        animateTarget : Ext.getBody(),
        border : false, // 边框线设置
        // pageY : 10, // 页面定位Y坐标
        // pageX : document.body.clientWidth / 2 - 820 / 2, // 页面定位X坐标
        constrain : true,
        // 设置窗口是否可以溢出父容器
        items : [ uiViewTabs ],
        buttonAlign : 'right',
        buttons : [ {
            text : '保存',
            iconCls : 'acceptIcon',
            handler : function() {
                saveUIView();
            }
        }, {
            text : '关闭',
            iconCls : 'deleteIcon',
            handler : function() {
                addUIViewWindow.hide();
            }
        } ]
    });

    var addColFormPanel = new Ext.form.FormPanel({
        id : 'addColFormPanel',
        defaultType : 'textfield',
        labelAlign : 'right',
        labelWidth : 58,
        frame : false,
        autoHeight: true,
        userDefined : true,
        bodyStyle : 'padding:15 5 0',
        layout : 'form',
        items : [{
            name : "field_name",
            fieldLabel : "字段编码",
            allowBlank : true,
            labelStyle : micolor,
            anchor : '99%'
        },{
            name : "field_title",
            fieldLabel : "字段名称",
            allowBlank : true,
            labelStyle : micolor,
            anchor : '99%'
        },{
            name : "field_type",
            xtype : 'combofield',
            fieldLabel : "控件类型",
            allowBlank : true,
            store : XTYPEStore,
            labelStyle : micolor,
            anchor : '99%'
        },{
            name : "width",
            fieldLabel : "宽度",
            xtype : "numberfield",
            allowBlank : false,
            allowDecimals : false, // 是否允许输入小数
            allowNegative : false, // 是否允许输入负数
            maxValue : 1000, // 允许输入的最大值
            minValue : 1, // 允许输入的最小值
            value : 100,
            anchor : '99%'
        },{
            name : "cols",
            fieldLabel : "容器列",
            xtype : "numberfield",
            allowBlank : false,
            allowDecimals : false, // 是否允许输入小数
            allowNegative : false, // 是否允许输入负数
            maxValue : 30, // 允许输入的最大值
            minValue : 1, // 允许输入的最小值
            value : 1,
            anchor : '99%'
        },{
            name : "ui_id",
            xtype : "hidden",
            anchor : '100%'
        }]
    });

    /**
     * 新增字段窗口
     */
    var addColWindow = new Ext.Window({
        layout : 'fit',
        width : 260,
        //height : 215,
        autoHeight: true,
        closable : true,
        modal : true,
        closeAction : 'hide',
        // iconCls : 'page_addIcon',
        maximizable : false,
        buttonAlign : 'right',
        border : false,
        animCollapse : true,
        pageY : 60,
        pageX : document.body.clientWidth / 2 - 420 / 2,
        animateTarget : Ext.getBody(),
        constrain : true,
        items : [addColFormPanel],
        buttons : [{
            text : '保存',
            iconCls : 'acceptIcon',
            handler : function() {
                appendColCache();
            }
        }, {
            text : '关闭',
            iconCls : 'deleteIcon',
            handler : function() {
                addColWindow.hide();
            }
        }]
    });


    /**
     * 控件信息panel树加载时修改和增加url
     */
    treeCols.on("beforequery", function(loads, node) {
        treeCols.params.ui_id = seltreeid;
    }, this);
    /**
     * 新增初始化
     */
    function addInit() {
        windowmode='add';
        addUIViewWindow.show();
        addUIViewWindow.setTitle("<span class='commoncss'> 新增界面视图</span>");
        loadEditUIView();
    }

    function loadEditUIView() {
        seltreecolid = '-1';
        treeCols.getSelectionModel().deselectAll();
        tabUIBase.getForm().reset();
        tabUIBase.form.load({
            url : 'query_uimanager.htm',
            params : {
                ui_id : seltreeid
            },
            waitMsg : '请稍后......',
            success : function(form, action) {
                var resultData = form.getValues();
                Ext.Ajax.request({
                    url : 'cache_uiconfmain.htm',
                    success : function(response) {
                        var array = Ext.util.JSON
                            .decode(response.responseText);
                        var resultArray = array['data'];
                        //editUIGrid.stopEditing();
                        var storeAttr = editUIGrid.store;
                        editColGrid.control_type = resultData.xtype;
                        storeAttr.removeAll();
                        for (var irec = 0; resultArray!=null && irec < resultArray.length; irec++) {
                            var newrecord = new MyRecord();
                            storeAttr.add(newrecord);
                            newrecord.set('uiconf_field',
                                resultArray[irec]['uiconf_field']);
                            newrecord.set('uiconf_title',
                                resultArray[irec].uiconf_title);
                            newrecord.set('uiconf_value',
                                resultArray[irec].uiconf_value);
                            newrecord.set('uiconf_datatype',
                                resultArray[irec].uiconf_datatype);
                            newrecord.set('ref_selmodel',
                                resultArray[irec].ref_selmodel);
                            newrecord.set('is_contain',
                                resultArray[irec].is_contain);
                        }
                        storeAttr.commitChanges();
                        editUIGrid.selModel.querySelectRecords();
                    },
                    params : {
                        uiconf_id : seltreeid,xtype : resultData.xtype
                    }
                });
                if (resultData.xtype=='tablegrid') {
                    editColPanel.form.findField('width').show();
                    editColPanel.form.findField('cols').hide();
                    editColPanel.form.findField('field_logic').hide();
                } else if (resultData.xtype=='inputpanel') {
                    editColPanel.form.findField('width').hide();
                    editColPanel.form.findField('cols').show();
                    editColPanel.form.findField('field_logic').hide();
                } else if (resultData.xtype=='querypanel') {
                    editColPanel.form.findField('width').hide();
                    editColPanel.form.findField('cols').show();
                    editColPanel.form.findField('field_logic').show();
                }
            }
        });
        if (treeCols.store.isLoaded())
            treeCols.store.reload();
    }
    ;
    /**
     * 修改初始化
     */
    function editInit() {
        windowmode='mod';
        var selectModel = treeEle.getSelectionModel();
        var selectNode = selectModel.selected.items[0];
        if (selectNode == null || selectNode == treeEle.getRootNode()) {
            Ext.Msg.alert('提示', '未选择待修改的视图，不能执行该操作!');
            return;
        }
        addUIViewWindow.show();
        addUIViewWindow.setTitle("<span class='commoncss'> 修改界面视图</span>");
        loadEditUIView();
    }

    function swapColCache(type) {
        var selectModel = treeCols.getSelectionModel();
        var selectNode = selectModel.selected.items[0];
        if (selectNode == null || selectNode == treeEle.getRootNode()) {
            Ext.Msg.alert('提示', '未选择待调整顺序的字段列，不能执行该操作!');
            return;
        }
        var parNode = selectNode.parentNode;
        var swapNode = selectNode;
        if (type=='up') {
            //isFirst方法好像有bug，因此用子节点顺序判断是否在第一位
            if (parNode.indexOf(selectNode)==0) return;
            swapNode = selectNode.previousSibling;
            parNode.insertBefore(selectNode,swapNode);
        } else if (type=='down') {
            if (selectNode.isLast()) return;
            swapNode = selectNode.nextSibling;
            parNode.insertBefore(swapNode,selectNode);
        }
        var order_num=0;
        var order_param = {};
        var childnodes = parNode.childNodes;
        Ext.each(childnodes, function () {
            order_num++;
            order_param[this.id] = order_num;
            this.data.field_index = order_num;
        });
        Ext.Ajax.request({
            url : 'swap_uicolcache.htm',
            success : function(response) {
            },
            failure : function(response) {
            },
            params : {
                ui_id : seltreeid,
                swap : Ext.encode(order_param)
            }
        });
        selectModel.select(selectNode);
    }

    function upColCache() {
        swapColCache('up');
    }

    function downColCache() {
        swapColCache('down');
    }

    function leftColCache() {
        if (tabUIBase.getForm().findField('xtype').getValue()!='tablegrid') {
            return;
        }
    }

    function rightColCache() {
        if (tabUIBase.getForm().findField('xtype').getValue()!='tablegrid') {
            return;
        }
    }

    function appendColCache() {
        if (!addColFormPanel.form.isValid()) {
            Ext.MessageBox.alert('提示', '请检查需要填写的录入项！');
            return;
        }
        addColFormPanel.form.submit({
            url : 'append_uicolcache.htm',
            waitTitle : '提示',
            method : 'POST',
            waitMsg : '正在处理数据,请稍候...',
            success : function(form, action) {
                if (action.result.success) {
                    addColWindow.hide();
                    var nodeid = action.result.data.id;
                    treeCols.getRootNode().appendChild(action.result.data);
                    treeCols.selectNodeByValue(nodeid);
                } else {
                    Ext.MessageBox.alert('提示', action.result.msg);
                }
            },
            failure : function(form, action) {
                switch (action.failureType) {
                    case Ext.form.action.Action.SERVER_INVALID:
                        Ext.Msg.alert('提示', '数据保存失败:<br>'+action.result.msg);
                        break;
                    default:
                        Ext.Msg.alert('错误', '数据保存失败:<br>'+action.response.responseText);
                }
            },
            params: {
                ui_id : seltreeid
            }
        });

    }

    function deleteColCache() {
        var selectModel = treeCols.getSelectionModel();
        var selectNode = selectModel.selected.items[0];
        if (selectNode == null || selectNode == treeEle.getRootNode()) {
            Ext.Msg.alert('提示', '未选择待删除的字段列，不能执行该操作!');
            return;
        }
        Ext.Ajax.request({
            url : 'delete_uicolcache.htm',
            success : function(response) {
                treeCols.getSelectionModel().deselectAll();
                selectNode.remove();
                editColPanel.getForm().reset();
                //editColGrid.stopEditing();
                editColGrid.store.removeAll();
            },
            failure : function(response) {
            },
            params : {
                ui_detail_id : seltreecolid,
                ui_id : seltreeid
            }
        });
    }

    /**
     * 刷新指定节点
     */
    function refreshNode(nodeid) {
        treeEle.store.reload();
        if (!treeEle.store.isLoaded()) {
            treeEle.on('lastExecute',function(loader) {
                if (nodeid!=null && nodeid!='' && nodeid!='0') {
                    treeEle.selectNodeByValue(nodeid,true);
                }
            });
        } else {
            if (nodeid!=null && nodeid!='' && nodeid!='0') {
                treeEle.selectNodeByValue(nodeid,true);
            }
        }
    }

    function saveUIView() {
        var url = '';
        if (windowmode == 'add') {
            url = 'insert_uiitem.htm';
        } else {
            url = 'update_uiitem.htm';
        }
        if (!tabUIBase.form.isValid()) {
            Ext.MessageBox.alert('提示', '请检查需要填写的视图基本信息！');
            return;
        }
        if (tabUIBase.form.findField("ui_code").getValue().length % 3!=0) {
            Ext.MessageBox.alert('提示', '视图基本信息页中，视图编码长度必须是3的倍数！');
            return;
        }
        if (validCacheUICol()=='abort') {
            uiViewTabs.activate(0);
            return;
        } else if (validCacheUICol()=='cache'){
            disposeCacheUICol();
        }
        if (validCacheCol()=='abort') {
            uiViewTabs.activate(1);
            return;
        } else if (validCacheCol()=='cache'){
            disposeCacheCol();
        }
        tabUIBase.form.submit({
            url : url,
            waitTitle : '提示',
            method : 'POST',
            waitMsg : '正在处理数据,请稍候...',
            success : function(form, action) {
                if (action.result.success) {
                    addUIViewWindow.hide();
                    // 获取对应树节点
                    var parent_id = action.result.parent_id;
                    refreshNode(parent_id);
                } else {
                    Ext.MessageBox.alert('提示', action.result.msg);
                }
            },
            failure : function(form, action) {
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

    function deleteUIView() {
        var selectNode = treeEle.getSelectionModel().selected.items[0];
        if (selectNode==null) {
            Ext.Msg.alert('提示', '请先选中要删除的界面视图!');
            return;
        }
        if (!selectNode.isLeaf()) {
            Ext.Msg.alert('提示', '只允许从最底级界面视图开始删除!');
            return;
        }
        Ext.Msg
            .confirm(
                '请确认',
                '<span style="color:red"><b>提示:</b>删除界面视图后不可恢复,请慎重.</span><br>继续删除吗?',
                function(btn, text) {
                    if (btn == 'yes') {

                        showWaitMsg();
                        Ext.Ajax.request({
                            url : 'delete_uiitem.htm',
                            success : function(response) {
                                var resultArray = Ext.util.JSON
                                    .decode(response.responseText);
                                // 获取对应树节点
                                refreshNode(null);
                                Ext.Msg.alert('提示', resultArray.msg);
                            },
                            failure : function(response) {
                                Ext.Msg.alert('提示', "删除失败，失败原因："+response.responseText);
                            },
                            params : {
                                ui_id : seltreeid
                            }
                        });
                    }
                });
    }

    function addUIConf() {
        var combo_fieldtype = editColPanel.getForm().findField('field_type');
        if (combo_fieldtype) {
            if (combo_fieldtype.getValue()==null)
                return;
            var validstr = validCacheCol();
            if (validstr == 'abort') {
                return;
            } else if (validstr == 'cache') {
                disposeCacheCol();
            }
            var pnl_uiconf = Ext.create({
                xtype: 'inputpanel',
                padding: '0 12',
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    name: 'uiconf_field',
                    fieldLabel: '属性名',
                    allowBlank: false,
                    anchor: '98%'
                }, {
                    xtype: 'textfield',
                    name: 'uiconf_title',
                    fieldLabel: '属性中文名',
                    allowBlank: false,
                    anchor: '98%'
                }, {
                    xtype: 'combofield',
                    name: 'uiconf_datatype',
                    enumData: 'string,boolean,object,int',
                    fieldLabel: '属性值类型',
                    allowBlank: false,
                    anchor: '98%'
                }, {
                    xtype: 'textfield',
                    name: 'ref_selmodel',
                    fieldLabel: '属性值枚举',
                    anchor: '98%'
                }, {
                    xtype: 'custnumberfield',
                    name: 'order_no',
                    fieldLabel: '排序号',
                    anchor: '98%'
                }, {
                    xtype: 'hidden',
                    name: 'uiconf_type',
                    value: combo_fieldtype.getValue()
                }]
            });
            var win = Ext.create('Ext.vcf.Window', {
                title: '属性模板新增',
                autoShow: true,
                closeAction: 'destroy',
                items: [{
                    region: 'north',
                    autoHeight: true,
                    items: pnl_uiconf
                }],
                buttons : [ {
                    text : '保存',
                    iconCls : 'acceptIcon',
                    handler: function () {
                        if (pnl_uiconf.form.isValid()) {
                            pnl_uiconf.form.submit({
                                url: 'insert_uiconf.htm',
                                waitTitle: '提示',
                                method: 'POST',
                                waitMsg: '正在处理数据,请稍候...',
                                success: function (form, action) {
                                    if (action.result.success) {
                                        win[win.closeAction]();
                                        // 刷新
                                        //combo_fieldtype.fireEvent('select', combo_fieldtype);
                                        treeCols.selectNodeByValue(seltreecolid);
                                    } else {
                                        Ext.MessageBox.alert('提示', action.result.msg);
                                    }
                                },
                                failure: function (form, action) {
                                    switch (action.failureType) {
                                        case Ext.form.action.Action.SERVER_INVALID:
                                            Ext.Msg.alert('提示', '数据保存失败:<br>' + action.result.msg);
                                            break;
                                        default:
                                            Ext.Msg.alert('错误', '数据保存失败:<br>' + action.response.responseText);
                                    }
                                }
                            });
                        }
                    }
                }, {
                    text : '关闭',
                    iconCls : 'deleteIcon',
                    handler : function () {
                        win[win.closeAction]();
                        treeCols.selectNodeByValue(seltreecolid);
                    }
                } ]
            });
        }
    }

    function delUIConf() {
        var combo_fieldtype = editColPanel.getForm().findField('field_type');
        if (combo_fieldtype) {
            if (combo_fieldtype.getValue()==null)
                return;
            var validstr = validCacheCol();
            if (validstr == 'abort') {
                return;
            } else if (validstr == 'cache') {
                disposeCacheCol();
            }
            Ext.MessageBox
                .prompt(
                    '删除确认',
                    '<span style="color:red"><b>提示:</b>删除属性模版后将影响界面视图的列属性配置,请慎重.</span><br>请输入属性名称：',
                    function (btn, text) {
                        if (btn == 'ok') {
                            Ext.Ajax.request({
                                url: 'delete_uiconf.htm',
                                success: function (response) {
                                    var resultArray = Ext.util.JSON
                                        .decode(response.responseText);
                                    // 刷新
                                    if (resultArray.success) {
                                        //combo_fieldtype.fireEvent('select', combo_fieldtype);
                                        treeCols.selectNodeByValue(seltreecolid);
                                    } else {
                                        Ext.MessageBox.alert('提示', resultArray.msg);
                                    }
                                },
                                failure: function (response) {
                                    Ext.Msg.alert('提示', "删除失败，失败原因：" + response.responseText);
                                },
                                params: {
                                    uiconf_type: combo_fieldtype.getValue(),
                                    uiconf_field: text
                                }
                            });
                        }
                    });
        }
    }

});
