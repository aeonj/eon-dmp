Ext.onReady(function () {
    var belongObj = {belong_source:[],belong_detail:[]};
    var userTreeStore = Ext.create('Ext.data.TreeStore', {
        // type: 'tree',
        proxy: {
            type: 'ajax',
            url: 'user_tree.htm',
            extraParams: {
                org_type: null,
                ele_code: null,
                treetype: -1
            }
        },
        root: {
            text: '用户信息',
            glyph: 'xf015@FontAwesome',
            expanded: true,
            id: '0'
        }
    });


    var userTree = Ext.create('Ext.tree.Panel', {
        store: userTreeStore,
        title: '',
        renderTo: 'userTreeDiv',
        // scrollable: 'y',
        animate: false,
        useArrows: false,
        border: false,
        listeners: {
            afterrender: function () {
                var record = this.getStore().getNodeById('0');
                this.getSelectionModel().select(record)
            }
        }
    });

    userTree.addListener('itemclick', function (node, currentTreeNode) {
        // var selectNode = userTree.getSelectionModel().select(currentTreeNode);
        var cmp_enabled = Ext.getCmp('tbr_enabled');
        var cmp_disabled = Ext.getCmp('tbr_disabled');

        if (cmp_enabled && cmp_disabled) {
            if (currentTreeNode.data.enabled == 1) {
                cmp_enabled.hide();
                cmp_disabled.show();
            } else {
                cmp_enabled.show();
                cmp_disabled.hide();
            }
        }
        if (!currentTreeNode.isLeaf())
            return;

        var comp_ele = userPanel.getForm().findField('orgtype_ele_id');
        if (comp_ele) {
            comp_ele.setSource('EN');
            Ext.Ajax.request({
                url: 'user_ele_by_orgtype.htm',
                success: function (response) {
                    comp_ele.setSource(response.responseText);
                    userPanel.getForm().load({
                        url: 'user_browse.htm',
                        params: {
                            user_id: currentTreeNode.data.id
                        },
                        waitMsg: '请稍后......',
                        success: function (response, option) {

                        }
                    });
                },
                params: {
                    user_id: currentTreeNode.data.id
                }
            });
        }

    });
    userTreeStore.addListener("beforeload", function (store, node) {
        this.getProxy().extraParams = {
            org_type: node.node.data.org_type,
            ele_code: node.node.data.ele_code,
            treetype: node.node.data.treetype
        }
        // var baseParams = {
        //     org_type: node.node.data.org_type,
        //     ele_code: node.node.data.ele_code,
        //     treetype: node.node.data.treetype
        // };
        // Ext.apply(userTreeStore.proxy.extraParams, baseParams);

    });

    // root.select();

    var toolbar = Ext.create('Ext.toolbar.Toolbar', {
        items: [{
            id: 'user:insert',
            text: '新增',
            iconCls: 'x-fa fa-plus-square-o',
            handler: function () {
                addInit();
            }
        }, {
            id: 'user:update',
            text: '修改',
            iconCls: 'x-fa fa-pencil-square-o',
            handler: function () {
                editInit();
            }
        }, {
            id: 'user:delete',
            text: '删除',
            iconCls: 'x-fa fa-trash-o',
            handler: function () {
                deleteUserItem();
            }
        }, {
            id: 'user:f_user_set',
            text: 'F3用户对照',
            iconCls: 'x-fa fa-users',
            handler: function () {
                linkFUser();
            }
        }]
    });

    var userPanel = Ext.create('Ext.form.Panel', {
        id: 'userForm',
        name: 'userForm',
        scrollable: 'y',
        //title : '用户信息',
        labelWidth: 80, // 标签宽度
        // frame : true, // 是否渲染表单面板背景色
        defaultType: 'textfield', // 表单元素默认类型
        labelAlign: 'right', // 标签对齐方式
        bodyStyle: 'padding:20', // 表单元素和表单面板的边距
        items: [{
            fieldLabel: '用户名', // 标签
            name: 'userName', // name:后台根据此name属性取值
            // allowBlank: false,
            readOnly: true,
            anchor: '100%' // 宽度百分比
        }, {
            fieldLabel: '用户姓名', // 标签
            name: 'trueName', // name:后台根据此name属性取值
            value: '',
            readOnly: true,
            anchor: '100%' // 宽度百分比
        }, {
            fieldLabel: '机构类型', // 标签
            name: 'orgtypename', // name:后台根据此name属性取值
            readOnly: true,
            anchor: '100%' // 宽度百分比
        }, {
            fieldLabel: '所属机构', // 标签
            name: 'orgtype_ele_id', // name:后台根据此name属性取值
            xtype: 'treefield',
            readOnly: true,
            anchor: '100%' // 宽度百分比
        }, {
            fieldLabel: '移动电话', // 标签
            name: 'mobile', // name:后台根据此name属性取值
            readOnly: true,
            anchor: '100%' // 宽度百分比
        }, {
            fieldLabel: '固定电话', // 标签
            name: 'telephone', // name:后台根据此name属性取值
            readOnly: true,
            anchor: '100%' // 宽度百分比
        }, {
            fieldLabel: '电子邮件', // 标签
            name: 'email', // name:后台根据此name属性取值
            readOnly: true,
            anchor: '100%' // 宽度百分比
        }, {
            fieldLabel: '性别', // 标签
            name: 'sex', // name:后台根据此name属性取值
            xtype: 'rdofield',
            readOnly: true,
            enumData: '1#男+0#女+-1#未知',
            anchor: '80%' // 宽度百分比
        }, {
            fieldLabel: '身份证号',
            name: 'card',
            maxLength: 18, // 可输入的最大文本长度,不区分中英文字符
            listeners: {
                'blur': function (obj) {
                    isIdCardNo(obj.getValue());
                }
            },
            anchor: '100%'
        }, {
            fieldLabel: '出生日期', // 标签
            name: 'birthday', // name:后台根据此name属性取值
            xtype: 'hidden',
            readOnly: true,
            anchor: '100%' // 宽度百分比
        }, {
            fieldLabel: '地址', // 标签
            name: 'address', // name:后台根据此name属性取值
            readOnly: true,
            anchor: '100%' // 宽度百分比
        }, {
            fieldLabel: '角色信息', // 标签
            xtype: 'textarea',
            heigth: 50,
            name: 'roles', // name:后台根据此name属性取值
            readOnly: true,
            anchor: '100%' // 宽度百分比
        }],
        tbar: toolbar
    });


    // 每一个Tab都可以看作为一个Panel
    var tabUserBase = Ext.create('Ext.panel.Panel', {
        id: 'tabUserBase',
        title: '基本设置',
        labelWidth: 65, // 标签宽度
        // frame : true, // 是否渲染表单面板背景色
        labelAlign: 'right', // 标签对齐方式
        bodyStyle: 'padding:5px 5px 5px 5px', // 表单元素和表单面板的边距
        // tbar:tb, //工具栏
        height: 420,
        scrollable: true,
        items: [{
            layout: 'column',
            border: false,
            items: [{
                columnWidth: 1,
                layout: 'form',

                labelWidth: 65, // 标签宽度
                defaultType: 'textfield',
                border: false,
                items: [{
                    // height:20,
                    fieldLabel: '用户名',
                    name: 'userName',
                    afterLabelTextTpl: [
                        '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>'
                    ],
                    allowBlank: false,
                    labelStyle: micolor,
                    anchor: '99%'

                }, {
                    fieldLabel: '姓名',
                    name: 'trueName',
                    afterLabelTextTpl: [
                        '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>'
                    ],
                    allowBlank: false,
                    labelStyle: micolor,
                    anchor: '99%'
                }, {
                    fieldLabel: '密码',
                    itemId: 'password',
                    name: 'password',
                    inputType: 'password',
                    labelStyle: micolor,
                    anchor: '99%'
                }, {
                    fieldLabel: '确认密码',
                    name: 'password1',
                    inputType: 'password',
                    //vtype:"password",
                    //vtypeText:"两次密码不一致！",
                    //confirmTo : 'password',
                    labelStyle: micolor,
                    anchor: '99%'
                }, {
                    fieldLabel: '所属类型',
                    name: 'orgtype_id',
                    id: 'add_cmb_org_type',
                    xtype: 'combo',
                    store: new Ext.data.Store({
                        proxy: {
                            type: 'ajax',
                            url: 'user_query_orgtype.htm',
                            reader: {
                                type: 'json',
                                items: [{
                                    name: 'id'
                                }, {
                                    name: 'orgName'
                                }]
                            },
                            extraParams: {}
                        }
                    }),
                    mode: 'remote',
                    triggerAction: 'all',
                    valueField: 'id',
                    displayField: 'orgName',
                    forceSelection: true,
                    editable: false,
                    typeAhead: true,
                    afterLabelTextTpl: [
                        '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>'
                    ],
                    allowBlank: false,
                    labelStyle: micolor,
                    anchor: '99%',
                    listeners: {
                        // 设置远程数据源下拉选择框的初始值
                        'select': function (obj) {
                            var cmb = Ext.getCmp("add_cmb_org_code");
                            if (cmb) {
                                cmb.reset();
                                cmb.requestload = true;
                            }
                        }
                    }
                }, {
                    fieldLabel: '用户所属',
                    name: 'orgtype_ele_id',
                    xtype: 'treefield',
                    id: 'add_cmb_org_code',
                    url: 'user_query_org_single_tree.htm',
                    minPickerHeight: 200,
                    scrollable : true,
                    editable: false,
                    allowBlank: true,
                    labelStyle: micolor,
                    anchor: '99%',
                    param:{orgtype:'-1'},
                    //只能选中叶子节点
                    listeners: {
                        'beforequery': function (treeloader, operation) {
                            treeloader.params = {orgtype:Ext.getCmp("add_cmb_org_type").value};
                        }

                    }
                    // listeners: {
                    //     // 设置远程数据源下拉选择框的初始值
                    //     'expand': function () {
                    //         // 将UI树挂到treeDiv容器
                    //         addOrgTree.render('addOrgCodeTreeDiv');
                    //         // addOrgTree.root.expand(); //只是第一次下拉会加载数据
                    //         console.log(addOrgTree.getStore());
                    //         addOrgTree.getStore().reload();      // 每次下拉都会加载数据
                    //     }
                    // }
                }, {
                    fieldLabel: '移动电话',
                    name: 'mobile',
                    regex: /^[1]\d{10}$/,
                    regexText: "移动电话格式不正确，请重新输入！",
                    anchor: '99%'
                }, {
                    fieldLabel: '固定电话',
                    name: 'telephone',
                    regex: /^\d{3}-\d{8}|\d{4}-\d{7}$/,
                    regexText: "固定电话格式不正确,请重新输入! 如:025-88888888",
                    anchor: '99%'
                }, {
                    fieldLabel: '电子邮件',
                    name: 'email',
                    //xtype : 'emailfield',
                    regex: /^([\w]+)(.[\w]+)*@([\w-]+\.){1,5}([A-Za-z]){2,4}$/,// 验证电子邮件格式的正则表达式
                    regexText: '电子邮件格式不合法', // 验证错误之后的提示信息
                    anchor: '99%'
                }, {
                    id: 'add_rdo_gender1',
                    name: 'sex',
                    xtype: 'rdofield',
                    fieldLabel: '性别',
                    enumData: '1#男+0#女+-1#未知',
                    anchor: '99%'
                }, {
                    fieldLabel: '出生日期',
                    name: 'birthday',
                    xtype: 'hidden',
                    format: 'Y-m-d', // 日期格式化
                    anchor: '99%'
                }, {
                    fieldLabel: '身份证号',
                    name: 'card',
                    maxLength: 18, // 可输入的最大文本长度,不区分中英文字符
                    allowBlank: true,
                    listeners: {
                        'blur': function (obj) {
                            isIdCardNo(obj.getValue());
                        }
                    },
                    anchor: '99%'
                }, {
                    fieldLabel: '家庭住址',
                    name: 'address',
                    anchor: '99%'
                }, {
                    id: 'windowmode',
                    name: 'windowmode',
                    hidden: true
                }, {
                    name: 'id',
                    hidden: true
                }]
            }]
        }]
    });

    Ext.apply(Ext.form.VTypes, {
        password: function (val, field) {
            if (field.confirmTo) {
                var pwd = addForm.form.findField(field.confirmTo);
                return (val == pwd.getValue());
            }
            return true;
        }
    });

    var orgtypeStore = Ext.create('Ext.data.Store', {
        model: Ext.create('Ext.data.Model', {
            fields: [{
                name: 'orgCode'
            }, {
                name: 'OrgName'
            }]
        }),
        proxy: {
            type: 'ajax',
            url: 'user_query_orgtype.htm',
            reader: {
                type: 'json'
            },
            extraParams: {}
        }
        // reader: new Ext.data.JsonReader({}, [{
        //     name: 'code'
        // }, {
        //     name: 'name'
        // }]),
        // baseParams: {}
    });

    var treeRole = Ext.create('Ext.vcf.AssistTree', {
        source : 'ROLE',
        title: '',
        isPermission: false,
        selectModel:'multiple',
        region: 'center',
        border: false, // 面板边框
        useArrows: true, // 箭头节点图标
        // root: rootRole, // 根节点
        scrollable: true, // 内容溢出时产生滚动条
        animate: false,
        // 是否动画显示
        anchor: '100%'
    });

    // 绑定选中状态变更事件,增加化横线的效果
    // treeRole.addListener('checkchange', function (node, checked, event) {
    //     if (checked) {
    //         event.getUI().addClass('node_checked');
    //     } else {
    //         event.getUI().removeClass('node_checked');
    //     }
    // });

    var tabUserRole = Ext.create('Ext.panel.Panel', {
        id: 'tabUserRole',
        title: '角色信息',
        layout: 'border',
        height: 420,
        border: false,
        items: [treeRole]
    });

    var tabBelong = Ext.create('Ext.tab.Panel', {
        region : 'center',
        plain: true,
        defaults: {
            scrollable: true
        },
        dockedItems: {
            dock: 'bottom',
            xtype: 'toolbar',
            items: [{
                text: '新增要素',
                glyph: 43,
                listeners: {
                    click: function() {
                        var cmbBelong = Ext.create('Ext.vcf.TreeField',{
                            fieldLabel: '权限要素', // 标签
                            source: 'ELE',   //系统要素，对应ea_element中的字段ele_code值
                            isCodeAsValue: true,  //是否将编码作为值 默认为false，即获取基础数据的ID作为值
                            isDirect: true,    //默认为false，指是否每次从数据库获取
                            isPermission: false,    //默认为true，指是否按权限过滤基础数据
                            // all:所有结点都可选中
                            // exceptRoot：除根结点，其它结点都可选（默认）
                            // folder:只有目录（非叶子和非根结点）可选
                            // leaf：只有叶子结点可选  默认
                            selectNodeModel: 'leaf',
                            selectModel: 'single',   //single单选 （默认） multiple 多选的
                            anchor: '100%'// 宽度百分比
                        });
                        var win = Ext.create('Ext.vcf.Window', {
                            title: '新增要素',
                            autoShow: true,
                            closeAction: 'destroy',
                            items: [{
                                region: 'north',
                                layout: 'form',
                                autoHeight: true,
                                items: [cmbBelong]
                            }],
                            buttons : [ {
                                text : '确定',
                                iconCls : 'acceptIcon',
                                handler: function () {
                                    var eleCode = cmbBelong.getValue();
                                    if (eleCode=="") {
                                        return;
                                    }
                                    if (containsArray(belongObj.belong_source,eleCode,'eleCode')) {
                                        Ext.MessageBox.alert('提示', '要素已存在，请重新选择');
                                        return;
                                    }
                                    var tab = tabBelong.add({
                                        title: cmbBelong.lastSelectionText,
                                        id: 'tab_belong_'+eleCode,
                                            xtype: 'assisttree',
                                            source: eleCode,
                                            isDirect : false,    //默认为false，指是否每次从数据库获取
                                            isPermission : false,    //默认为true，指是否按权限过滤基础数据
                                            selectModel : 'multiple'
                                    });
                                    belongObj.belong_source.push({eleCode:eleCode,eleName:cmbBelong.lastSelectionText});

                                    tabBelong.setActiveTab(tab);
                                    win[win.closeAction]();
                                }
                            }, {
                                text : '关闭',
                                iconCls : 'deleteIcon',
                                handler : function () {
                                    win[win.closeAction]();
                                }
                            } ]
                        });

                    }
                }
            },{
                text: '删除要素',
                glyph: 45,
                listeners: {
                    click: function() {
                        var tab = tabBelong.getActiveTab(),
                            idx = tabBelong.items.indexOf(tab),
                            li_id = tabBelong.getActiveTab().getId(),
                            eleCode = li_id.replace("tab_belong_","");
                        tabBelong.remove(tabBelong.getActiveTab());
                        if (idx>0) {
                            tabBelong.setActiveTab(idx-1);
                        } else {
                            tabBelong.setActiveTab(idx);
                        }
                        //删除json对象
                        removeArrayNew(belongObj.belong_source,eleCode,"eleCode");
                    }
                }
            }]
        }
    });

    var tabUserAuth = Ext.create('Ext.panel.Panel', {
        id: 'tabUserAuth',
        title: '权限信息',
        layout: 'border',
        height: 420,
        border: false,
        items: [{
            xtype : 'panel',
            region : 'north',
            layout : 'form',
            items: [{
                id : 'rdoEdtAuth',
                name: 'is_part', // name:后台根据此name属性取值
                xtype: 'rdofield',
                width: 280,
                enumData: '1#全部权限+2#部分权限+3#自定义权限',
                anchor: '99%', // 宽度百分比
                listeners : {
                    change : function (comp, newValue, oldValue) {
                        var tree = Ext.getCmp('treeEdtAuth');
                        if (newValue=='1') {
                            tree.hide();
                            tabBelong.hide();
                        } else if (newValue=='2') {
                            tree.show();
                            tabBelong.hide();
                        } else {
                            //pnlBelong.show();
                            tree.hide();
                            tabBelong.show();
                        }
                    }
                }
            }]
        },{
            id : 'treeEdtAuth',
            region : 'center',
            xtype : 'assisttree',
            border : false,
            treeRootText : '权限组信息',
            url : '/manage/partgroup_tree.htm',
            hidden : true

        },tabBelong]
    });

    var userTabs = Ext.create('Ext.tab.Panel', {
        region: 'center',
        margins: '3 3 3 3',
        activeTab: 0,
        items: [tabUserBase, tabUserRole, tabUserAuth],
        enableTabScroll: true,
        //autoWidth : true
        // height : 200
        anchor: '100%'
    });

    var addForm = Ext.create('Ext.form.Panel', {
        id: 'addForm',
        region: 'center',
        items: [userTabs],
    });

    var addUserWindow = Ext.create('Ext.window.Window', {
        title: '<span class="commoncss">用户管理</span>', // 窗口标题
        iconCls: 'group_linkIcon',
        layout: 'fit', // 设置窗口布局模式
        width: 560, // 窗口宽度
        height: 550, // 窗口高度
        // tbar : tb, // 工具栏
        closable: true, // 是否可关闭
        // scrollable: true,//可滚动
        closeAction: 'hide', // 关闭策略
        bodyStyle: 'background-color:#FFFFFF',
        collapsible: true, // 是否可收缩
        maximizable: false, // 设置是否可以最大化
        modal: true,
        animateTarget: Ext.getBody(),
        border: false, // 边框线设置
        pageY: 10, // 页面定位Y坐标
        pageX: document.body.clientWidth / 2 - 450 / 2, // 页面定位X坐标
        constrain: true,
        // 设置窗口是否可以溢出父容器
        items: [addForm],
        buttonAlign: 'right',
        buttons: [{
            text: '保存',
            iconCls: 'x-fa fa-save',
            handler: function () {
                var mode = Ext.getCmp('windowmode').getValue();
                if (mode == 'add')
                    saveUserItem();
                else
                    updateUserItem();
            }
        }, {
            text: '关闭',
            iconCls: 'x-fa fa-close',
            handler: function () {
                addUserWindow.hide();
            }
        }]
    });

    //userTabs.activate(0);

    function addInit() {
        belongObj.belong_source = [];
        belongObj.belong_detail = [];
        addUserWindow.show();
        addUserWindow.setTitle('<span class="commoncss">新增用户</span>');
        Ext.getCmp('rdoEdtAuth').setValue("1");
        Ext.getCmp('windowmode').setValue('add');

    }

    function editInit() {
        belongObj.belong_source = [];
        belongObj.belong_detail = [];
        var selectNode = userTree.getSelectionModel().getSelected().items[0];
        // var selectNode = userTree.getSelectionModel().select(userTree.getRootNode());
        if (Ext.isEmpty(selectNode)) {
            Ext.MessageBox.alert('提示', '请先选中左边要修改的用户!');
        }
        if (!selectNode.isLeaf())
            return;
        addUserWindow.show();
        addUserWindow.setTitle('<span class="commoncss">修改用户</span>');
        addForm.getForm().reset();
        Ext.getCmp('windowmode').setValue('edit');
        addForm.form.load({
            url: 'user_query_edtinfo.htm',
            params: {user_id: selectNode.id},
            waitMsg: '请稍后......',
            success: function (form, action) {
                var pg_id = action.result.data['pg_id'],
                    belong_source = action.result.data['belong_source'];
                if (pg_id!=null && pg_id!='-1') {
                    Ext.getCmp('rdoEdtAuth').setValue("2");
                    Ext.getCmp('treeEdtAuth').selectNodeByValue(String(pg_id),false);
                } else if (belong_source!=null && belong_source!='') {
                    Ext.getCmp('rdoEdtAuth').setValue("3");
                    belongObj.belong_source = JSON.parse(belong_source);
                    tabBelong.removeAll();
                    Ext.each(belongObj.belong_source, function (eleObj) {
                        var eleCode = eleObj.eleCode,
                            tree = Ext.create('Ext.vcf.AssistTree',{
                                title: eleObj.eleName,
                                id: 'tab_belong_'+eleCode,
                                source: eleCode,
                                isDirect : false,    //默认为false，指是否每次从数据库获取
                                isPermission : false,    //默认为true，指是否按权限过滤基础数据
                                selectModel : 'multiple',
                            });
                        tree.resetChecked(eleObj.eleValue);
                        tabBelong.add(tree);
                        delete eleObj.eleValue;
                    });
                    tabBelong.setActiveTab(0);
                } else {
                    Ext.getCmp('rdoEdtAuth').setValue("1");
                }
                Ext.getCmp('add_cmb_org_type').getStore().reload();
            },
            failure: function (form, action) {
                Ext.getCmp('add_cmb_org_type').getStore().reload();
            }
        });
        Ext.Ajax.request({
            url: 'user_query_edtroleinfo.htm',
            success: function (response) {
                treeRole.resetChecked(response.responseText);
            },
            params: {
                user_id: selectNode.id
            }
        });
    }

    function saveUserItem() {
        if (!addForm.form.isValid()) {
            Ext.MessageBox.alert('提示', '请检查需要填写的录入项！');
            return;
        }
        password1 = addForm.form.findField('password1').getValue();
        password = addForm.form.findField('password').getValue();
        /**
         * 新增用户时必须输入密码
         */
        if (Ext.isEmpty(password)) {
            //将密码框设置为不允许非空
            addForm.form.findField('password').allowBlank = false;
            //验证form表单
            addForm.form.isValid();
            Ext.MessageBox.alert('提示', '新增用户请输入密码!');
            return;
        }
        if (password1 != password) {
            //将确认密码框设置为不允许非空
            addForm.form.findField('password1').allowBlank = false;
            //验证form表单
            addForm.form.isValid();
            Ext.MessageBox.alert('提示', '两次输入的密码不匹配,请重新输入!');
            return;
        }
        /**
         * 将密码框和确认密码框设置为允许非空以不影响接下来的修改用户
         */
        addForm.form.findField('password').allowBlank = true;
        addForm.form.findField('password1').allowBlank = true;
        //检查角色是否被选定
        var rolenodes = treeRole.getCheckValues();
        if (Ext.isEmpty(rolenodes)) {
            Ext.Msg.alert('提示', '用户对应角色没有设置');
            return;
        }

        var pg_id = '-1';
        if (Ext.getCmp('rdoEdtAuth').getValue()=='2') {
            var selectAuth = Ext.getCmp('treeEdtAuth').getSelectionModel().getSelected().items[0];
            if (!Ext.isEmpty(selectAuth)) {
                pg_id =selectAuth.id;
            }
        }
        if (Ext.getCmp('rdoEdtAuth').getValue()=='3') {
            if (Ext.isEmpty(belongObj.belong_source)) {
                Ext.Msg.alert('提示', '自定义权限没有设置');
                return;
            }
            Ext.each(belongObj.belong_source, function (eleObj) {
                var eleCode = eleObj.eleCode,
                    tree = Ext.getCmp('tab_belong_' + eleCode),
                    eleValue = tree.getCheckValues();
                belongObj.belong_detail.push({eleCode:eleCode,eleValue:eleValue});
            });
        } else {
            belongObj.belong_source=[];
            belongObj.belong_detail=[];
        }


        addForm.form.submit({
            url: 'user_save.htm',
            waitTitle: '提示',
            method: 'POST',
            params: {
                rolenodes: rolenodes,
                belong_source: JSON.stringify(belongObj.belong_source),
                belong_detail: JSON.stringify(belongObj.belong_detail),
                pg_id: pg_id
            },
            waitMsg: '正在处理数据,请稍候...',
            success: function (form, action) {
                addUserWindow.hide();
                // 获取对应树节点
                var parent_id = addForm.form.findField('orgtype_ele_id')
                    .getValue();
                refreshNode(parent_id);
                form.reset();
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

    function updateUserItem() {
        if (!addForm.form.isValid()) {
            Ext.MessageBox.alert('提示', '请检查需要填写的录入项！');
            return;
        }
        password1 = addForm.form.findField('password1').getValue();
        password = addForm.form.findField('password').getValue();
        //更新信息时 只有用户输入的了新密码在做密码验证
        if (password1 != null) {
            if (password1 != password) {
                Ext.MessageBox.alert('提示', '两次输入的密码不匹配,请重新输入!');
                addForm.form.findField('password1').getValue();
                addForm.form.findField('password').getValue();
                return;
            }
        }
        //检查角色是否被选定
        var rolenodes = treeRole.getCheckValues();
        if (Ext.isEmpty(rolenodes)) {
            Ext.Msg.alert('提示', '用户对应角色没有设置');
            return;
        }

        var pg_id = '-1';
        if (Ext.getCmp('rdoEdtAuth').getValue()=='2') {
            var selectAuth = Ext.getCmp('treeEdtAuth').getSelectionModel().getSelected().items[0];
            if (!Ext.isEmpty(selectAuth)) {
                pg_id =selectAuth.id;
            }
        }

        if (Ext.getCmp('rdoEdtAuth').getValue()=='3') {
            if (Ext.isEmpty(belongObj.belong_source)) {
                Ext.Msg.alert('提示', '自定义权限没有设置');
                return;
            }
            Ext.each(belongObj.belong_source, function (eleObj) {
                var eleCode = eleObj.eleCode,
                    tree = Ext.getCmp('tab_belong_' + eleCode),
                    eleValue = tree.getCheckValues();
                belongObj.belong_detail.push({eleCode:eleCode,eleValue:eleValue});
            });
        } else {
            belongObj.belong_source=[];
            belongObj.belong_detail=[];
        }


        addForm.form.submit({
            url: 'user_update.htm',
            waitTitle: '提示',
            method: 'POST',
            params: {
                rolenodes: rolenodes,
                belong_source: JSON.stringify(belongObj.belong_source),
                belong_detail: JSON.stringify(belongObj.belong_detail),
                pg_id: pg_id
            },
            waitMsg: '正在处理数据,请稍候...',
            success: function (form, action) {
                addUserWindow.hide();
                // 获取对应树节点
                var sel_user_id = addForm.form.findField('id')
                    .getValue();
                refreshNode(sel_user_id);
                form.reset();
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

    /**
     * 删除人员
     */
    function deleteUserItem() {
        var selectNode = userTree.getSelectionModel().getSelected().items[0];
        if (!selectNode.isLeaf()) {
            Ext.Msg.alert('提示', '请先选中要删除的用户!');
            return;
        }
        Ext.Msg
            .confirm(
                '请确认',
                '<span style="color:red"><b>提示:</b>删除人员将同时删除和人员相关的角色和权限信息,请慎重.</span><br>继续删除吗?',
                function (btn, text) {
                    if (btn == 'yes') {

                        showWaitMsg();
                        Ext.Ajax.request({
                            url: 'user_delete.htm',
                            success: function (response) {
                                var resultArray = Ext.util.JSON
                                    .decode(response.responseText);
                                // 获取对应树节点
                                refreshNode(null);
                                Ext.Msg.alert('提示', resultArray.msg);
                            },
                            failure: function (response) {
                                var resultArray = Ext.util.JSON
                                    .decode(response.responseText);
                                Ext.Msg.alert('提示', resultArray.msg);
                            },
                            params: {
                                user_id: selectNode.data.id
                            }
                        });
                    }
                });
    }

    function enabledUserItem(val) {
        var selectNode = userTree.getSelectionModel().getSelected().items[0];
        if (!selectNode.isLeaf()) {
            if (val == 1)
                Ext.Msg.alert('提示', '请先选中要启用的用户!');
            else
                Ext.Msg.alert('提示', '请先选中要停用的用户!');
            return;
        }
        Ext.Ajax.request({
            url: './sysuser.do?reqCode=enabledUserItem',
            success: function (response) {
                // 获取对应树节点
                refreshNode(selectNode.id);
            },
            failure: function (response) {
                var resultArray = Ext.util.JSON
                    .decode(response.responseText);
                Ext.Msg.alert('提示', resultArray.msg);
            },
            params: {
                userid: selectNode.id,
                enabled: val
            }
        });

    }

    /**
     * 刷新指定节点
     */
    function refreshNode(nodeid) {
        if (nodeid!=null && nodeid!='') {
            userTreeStore.load({
                node:userTree.getRootNode(),
                callback:function(){
                    var selNode = userTreeStore.getNodeById(nodeid);
                    var idPath = selNode.getPath("id");
                    userTree.expandPath(idPath,'id', '/', function(bSucess, oLastNode) {
                        userTree.getSelectionModel().select(oLastNode);
                    });
                }
            })
        } else {
            userTreeStore.reload();
        }
    }

    /**
     * 布局
     */
    var viewport = new Ext.Viewport({
        layout: 'border',
        // labelAlign : 'right', // 标签对齐方式
        items: [{
            title: '<span class="commoncss">用户管理</span>',
            iconCls: 'userIcon',
            tools: [{
                type: 'refresh',
                handler: function () {
                    userTree.getStore().reload()
                }
            }],
            collapsible: true,
            width: 300,
            minSize: 260,
            maxSize: 380,
            split: true,
            margins: '3 3 3 3',
            region: 'west',
            scrollable: 'y',
            // collapseMode:'mini',
            items: [userTree]
        }, {
            region: 'center',
            layout: 'fit',
            margins: '3 3 3 3',
            border: true,
            scrollable: 'y',
            // autoScroll: true,
            items: [userPanel]
        }]
    });

    function linkFUser() {
        var selectNode = userTree.getSelectionModel().getSelected().items[0];
        // var selectNode = userTree.getSelectionModel().select(userTree.getRootNode());
        if (Ext.isEmpty(selectNode)) {
            Ext.MessageBox.alert('提示', '请先选中左边要修改的用户!');
        }
        if (!selectNode.isLeaf())
            return;
        var linkPanel = Ext.create('Ext.vcf.FormPanel',{
            layout : 'form',
            items : [{
                fieldLabel: 'F3用户',
                name: 'f_user_id',
                isFullLevel : false,
                afterLabelTextTpl: [
                    '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>'
                ],
                allowBlank: false,
                source: 'F_USER',
                isPermission : false,
                anchor: '99%'
            },{
                fieldLabel: 'F3角色',
                name: 'f_role_id',
                afterLabelTextTpl: [
                    '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>'
                ],
                allowBlank: false,
                source: 'F_ROLE',
                isPermission : false,
                anchor: '99%'
            }],
            defaultType: 'treefield',
            frame: false,
            autoHeight: true,
            bodyStyle: 'padding:15 15 0'
        });
        linkPanel.form.load({
            url: 'vcf/query_f_user_role.htm',
            params: {user_id: selectNode.id},
            waitMsg: '请稍后......',
            success: function (form, result, data) {
            }
        });
        var linkwindow = Ext.create('Ext.vcf.Window', {
            width : 460,
            height : 300,
            title : "F3用户角色对照",
            items: linkPanel,
            tbar : [{
                text: '退出',
                iconCls: 'page_addIcon',
                handler: function () {
                    linkwindow.hide();
                }
            }, '-', {
                text: '保存',
                iconCls: 'page_addIcon',
                handler: function () {
                    save_F3Link();
                }
            }]
        });
        linkwindow.show();

        function save_F3Link() {
            if (!linkPanel.form.isValid()) {
                Ext.MessageBox.alert('提示', '请检查需要填写的录入项！');
                return;
            }
            linkPanel.submit({
                url: 'vcf/save_f_user_role.htm',
                waitTitle: '提示',
                method: 'POST',
                params : {user_id:selectNode.id},
                waitMsg: '正在处理数据,请稍候...',
                success: function (form, action) {
                    linkwindow.hide();
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
    }


});