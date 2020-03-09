/**
 * 首页部分JS
 *
 */
Ext.onReady(function () {
    var downMenu = new Ext.menu.Menu({
        id: 'downMenu',
        items: [{
            text: '电子凭证库控件下载',
            handler: function () {
                window.location.href = "./bank/downloadF.do?reqCode=downLoad&file_name="
                    + encodeURI(encodeURI('电子凭证库客户端.msi'));
            }
        }, {
            text: '手册下载',
            handler: function () {
                window.location.href = "./bank/downloadF.do?reqCode=downLoad&file_name="
                    + encodeURI(encodeURI('银行柜面系统柜员操作手册V0.4.pdf'));
            }
        }]
    });


    Ext.create('Ext.button.Button', {
        text: '下载',
        tooltip: '下载相关文件',
        style: 'background:transparent; border-color: #FFFFFF',
        border: false,
        width: 80,
        valueField: 'value',
        renderTo: 'queryDiv',
        menu : downMenu
    });


    var swThemeMenu = Ext.create('Ext.menu.Menu', {
        id: 'swThemeMenu',
        items: [{
            text: 'triton',
            handler: function () {
                saveUserTheme(this.text)
            }
        }, {
            text: 'neptune',
            handler: function () {
                saveUserTheme(this.text)
            }
        }, {
            text: 'neptune-touch',
            handler: function () {
                saveUserTheme(this.text)
            }
        }, {
            text: 'crisp',
            handler: function () {
                saveUserTheme(this.text)
            }
        }, {
            text: 'crisp-touch',
            handler: function () {
                saveUserTheme(this.text)
            }
        }, {
            text: 'classic',
            handler: function () {
                saveUserTheme(this.text)
            }
        }, {
            text: 'gray',
            handler: function () {
                saveUserTheme(this.text)
            }
        }, {
            text: 'aria',
            handler: function () {
                saveUserTheme(this.text)
            }
        }]
    });
    var switchThemeButton = Ext.create('Ext.button.Button', {
        text: '主题',
        // scale: 'medium',
        tooltip: '快速切换主题包',
        // emptyText: '快速切换主题',
        displayField: 'name',
        style: 'background:transparent; border-color: #FFFFFF',
        border: false,
        width: 80,
        valueField: 'value',
        renderTo: 'themeDiv',
        // iconCls: 'themeIcon',
        // defaultListConfig: {
        //   value: 'triton'
        // },
        // pressed: true,
        menu: swThemeMenu
    });


    // var queryButton = new Ext.Button({
    //     text: '移动办公系统',
    //     iconCls: 'queryIcon',
    //     iconAlign: 'left',
    //     // scale: 'medium',
    //
    //     tooltip: '<span style="font-size:12px">跳转至移动办公系统</span>',
    //     pressed: true,
    //     arrowAlign: 'right',
    //     renderTo: 'queryDiv',
    //     handler: function () {
    //         Ext.MessageBox.show({
    //             title: '提示',
    //             msg: '确认要跳转到移动办公系统吗?',
    //             width: 250,
    //             buttons: Ext.MessageBox.YESNO,
    //             animEl: Ext.getBody(),
    //             icon: Ext.MessageBox.QUESTION,
    //             fn: function (btn) {
    //                 if (btn == 'yes') {
    //                     Ext.MessageBox.show({
    //                         title: '请等待',
    //                         msg: '正在跳转...',
    //                         width: 300,
    //                         wait: true,
    //                         waitConfig: {
    //                             interval: 50
    //                         }
    //                     });
    //                     window.location.href = 'http://58.210.114.122:5080/szcz/login.do?reqCode=init';
    //                 }
    //             }
    //         });
    //     }
    // });

    // var themeButton = new Ext.Button({
    //     text: '主题',
    //     iconCls: 'themeIcon',
    //     iconAlign: 'left',
    //     // scale: 'medium',
    //
    //     tooltip: '<span style="font-size:12px">切换系统主题样式</span>',
    //     pressed: true,
    //     arrowAlign: 'right',
    //     renderTo: 'themeDiv',
    //     menu: themeMenu
    // });

    var mainMenu = new Ext.menu.Menu({
        id: 'mainMenu',
        items: [{
            text: '密码修改',
            iconCls: 'keyIcon',
            handler: function () {
                updateUserInit();
            }
        }, {
            text: '系统锁定',
            iconCls: 'lockIcon',
            handler: function () {
                lockWindow.show();
                setCookie("vcf.lockflag", '1', 240);
            }
        }]
    });

    var configButton = new Ext.Button({
        text: '首选项',
        // iconCls: 'config2Icon',
        // iconAlign: 'left',
        // scale: 'medium',
        style: 'background:transparent;',
        border: false,
        tooltip: '<span style="font-size:12px">首选项设置</span>',
        // pressed: true,
        renderTo: 'configDiv',
        menu: mainMenu
    });

    var closeButton = new Ext.Button({
        text: '注销',
        // iconCls: 'x-fa fa-close',
        iconAlign: 'left',
        // scale: 'medium',
        style: 'background:transparent;',
        border: false,
        tooltip: '<span style="font-size:12px">切换用户,安全退出系统</span>',
        // pressed: true,
        arrowAlign: 'right',
        renderTo: 'closeDiv',
        handler: function () {
            logout();
        }
    });
    var root = Ext.data.NodeInterface.create({
        text: '根节点',
        id: '00',
        expanded: true,
        checked: true,
        leaf: true
    });
    /**皮肤设置
     var root = new Ext.tree.TreeNode({
        text: '根节点',
        id: '00'
    });
     var node01 = new Ext.tree.TreeNode({
        text: '蓝色妖姬',
        theme: 'default',
        id: '01'
    });
     var node02 = new Ext.tree.TreeNode({
        text: '粉红之恋',
        theme: 'lightRed',
        id: '02'
    });
     var node03 = new Ext.tree.TreeNode({
        text: '金碧辉煌',
        theme: 'lightYellow',
        id: '03'
    });
     var node04 = new Ext.tree.TreeNode({
        text: '钢铁战士',
        theme: 'gray',
        id: '04'
    });
     var node05 = new Ext.tree.TreeNode({
        text: '绿水青山',
        theme: 'lightGreen',
        id: '05'
    });
     var node06 = new Ext.tree.TreeNode({
        text: '紫色忧郁',
        theme: 'purple2',
        id: '06'
    });
     var node07 = new Ext.tree.TreeNode({
        text: '火红爱情',
        theme: 'red',
        id: '07'
    });
     root.appendChild(node01);
     root.appendChild(node02);
     root.appendChild(node03);
     root.appendChild(node04);
     root.appendChild(node05);
     root.appendChild(node06);
     root.appendChild(node07);
     **/
    var themeTree = new Ext.tree.TreePanel({
        autoHeight: false,
        autoWidth: false,
        autoScroll: false,
        animate: false,
        rootVisible: false,
        border: false,
        containerScroll: true,
        renderTo: 'themeTreeDiv',
        root: root
    });
    themeTree.expandAll();
    themeTree.on('itemclick', function (node, event) {
        var theme = event.data.theme;
        var o = document.getElementById('previewDiv');
        o.innerHTML = '<img src="./resource/image/theme/' + theme
            + '.jpg" />';
    });

    var previewPanel = new Ext.Panel({
        region: 'center',
        title: '<span class="commoncss">皮肤预览</span>',
        margins: '3 3 3 0',
        activeTab: 0,
        defaults: {
            autoScroll: true
        },
        contentEl: 'previewDiv'
    });

    var themenav = new Ext.Panel({
        title: '<span class="commoncss">皮肤列表</span>',
        region: 'west',
        split: true,
        width: 120,
        minSize: 120,
        maxSize: 150,
        collapsible: true,
        margins: '3 0 3 3',
        contentEl: 'themeTreeDiv',
        bbar: [{
            text: '保存',
            iconCls: 'acceptIcon',
            handler: function () {
                if (runMode == '0') {
                    Ext.Msg.alert('提示',
                        '系统正处于演示模式下运行,您的操作被取消!该模式下只能进行查询操作!');
                    return;
                }
                var o = themeTree.getSelectionModel().getSelectedNode();
                saveUserTheme(o);
            }
        }, '->', {
            text: '关闭',
            iconCls: 'deleteIcon',
            handler: function () {
                themeWindow.hide();
            }
        }]
    });

    var themeWindow = new Ext.Window({
        title: '<span class="commoncss">皮肤设置</span>',
        closable: true,
        width: 500,
        height: 350,
        closeAction: 'hide',
        iconCls: 'bugIcon',
        collapsible: true,
        titleCollapse: true,
        border: true,
        maximizable: false,
        resizable: false,
        modal: true,
        animCollapse: true,
        animateTarget: Ext.getBody(),
        // border:false,
        plain: true,
        layout: 'border',
        items: [themenav, previewPanel]
    });

    /**布局设置**/
        //  var layout_root = {
        //     text: '根节点',
        //     id: '00'
        // };
        //  var layout_node01 = {
        //     text: '传统经典布局',
        //     layout: '1',
        //     id: '01'
        // };
        //  var layout_node02 = {
        //     text: '个性桌面布局',
        //     layout: '2',
        //     id: '02'
        // };

    var layoutStore = Ext.create('Ext.data.TreeStore', {
            root:  {
                text: '根节点',
                id: '00',
                children: [{
                    text: '传统经典布局',
                    layout: '1',
                    id: '01'
                }, {
                    text: '个性桌面布局',
                    layout: '2',
                    id: '02'
                }]
            }
        });
    var layoutTree = Ext.create('Ext.tree.Panel', {
        store: layoutStore,
        autoHeight: false,
        width: 180,
        autoWidth: false,
        autoScroll: false,
        animate: false,
        rootVisible: false,
        border: false,
        containerScroll: true,
        renderTo: 'layoutTreeDiv',
        // root: layout_root
    });
    layoutTree.expandAll();
    layoutTree.on('itemclick', function (node, event) {
        var layout = event.data.layout;
        var o = document.getElementById('layout_previewDiv');
        o.innerHTML = '<img src="./resource/image/theme/layout/' + layout
            + '.jpg" />';
    });

    var layout_previewPanel = new Ext.Panel({
        region: 'center',
        title: '<span class="commoncss">布局预览</span>',
        margins: '3 3 3 0',
        defaults: {
            autoScroll: true
        },
        contentEl: 'layout_previewDiv'
    });

    var layoutnav = Ext.create('Ext.panel.Panel', {
        title: '<span class="commoncss">布局列表</span>',
        region: 'west',
        split: true,
        width: 180,
        minSize: 120,
        maxSize: 150,
        collapsible: true,
        margins: '3 0 3 3',
        contentEl: 'layoutTreeDiv',
        bbar: [{
            text: '保存',
            iconCls: 'acceptIcon',
            width: 78,
            handler: function () {
                var o = layoutTree.getSelectionModel().getSelected().items[0];
                saveUserLayout(o);
            }
        }, {
            text: '关闭',
            iconCls: 'deleteIcon',
            width: 78,
            handler: function () {
                layoutWindow.hide();
            }
        }]
    });

    var layoutWindow = new Ext.Window({
        title: '<span class="commoncss">布局设置</span>',
        closable: true,
        width: 773,
        height: 428,
        closeAction: 'hide',
        iconCls: 'app_rightIcon',
        collapsible: false,
        titleCollapse: false,
        border: true,
        maximizable: false,
        // resizable: false,
        modal: true,
        animCollapse: true,
        animateTarget: Ext.getBody(),
        // border:false,
        plain: true,
        layout: 'border',
        items: [layoutnav, layout_previewPanel]
    });

    /**
     * 布局窗口初始化
     */
    function layoutWindowInit() {
        console.log(layoutTree.getRootNode().childNodes);
        for (i = 0; i < layoutTree.getRootNode().childNodes.length; i++) {
            var child = layoutTree.getRootNode().childNodes[i];
            if (default_layout == child.data.layout) {
                layoutTree.getSelectionModel().select(child);
                // child.select();
            }
        }
        var o = document.getElementById('previewDiv');
        o.innerHTML = '<img src="./resource/image/theme/layout/' + default_layout
            + '.jpg" />';
        layoutWindow.show();

    }

    /**
     * 保存用户自定义布局
     */
    function saveUserLayout(o) {
        if (account == 'developer') {
            Ext.Msg.alert('提示', '开发账户[developer]不支持布局切换,请使用其它帐户登录切换!');
            return;
        }
        showWaitMsg();
        Ext.Ajax.request({
            url: './index.do?reqCode=saveUserLayout',
            success: function (response) {
                var resultArray = Ext.util.JSON.decode(response.responseText);
                Ext.MessageBox
                    .confirm(
                        '请确认',
                        '您选择的['
                        + o.text
                        + ']布局保存成功,立即应用该布局吗?<br>提示：页面会被刷新,请先确认是否有尚未保存的业务数据,以免丢失!',
                        function (btn, text) {
                            if (btn == 'yes') {
                                showWaitMsg('正在为您应用布局...');
                                location.reload();
                            } else {
                                Ext.Msg.alert('提示',
                                    '请在任何时候按[F5]键刷新页面或者重新登录系统以启用['
                                    + o.text + ']布局!',
                                    function () {
                                        themeWindow.hide();
                                    });

                            }
                        });
            },
            params: {
                layout: o.data.layout
            }
        });
    }

    /**密码修改**/
    var lockForm = new Ext.form.FormPanel({
        labelWidth: 60,
        defaultType: 'textfield',
        labelAlign: 'right',
        bodyStyle: 'padding:10 5 5 5',
        layout: 'form',
        items: [{
            fieldLabel: '帐户密码',
            name: 'password',
            inputType: 'password',
            id: 'password_lock',
            labelStyle: micolor,
            allowBlank: false,
            maxLength: 50,
            listeners: {
                specialkey: function (field, e) {
                    if (e.getKey() == Ext.EventObject.ENTER) {
                        unlockSystem();
                    }
                }
            },
            anchor: '100%'
        }, {
            xtype: 'panel',
            border: false,
            html: '<div style="font-size:12px;margin-left:10px">(提示:系统已成功锁定,解锁请输入登录帐户密码)</div>'
        }]
    });

    var lockWindow = new Ext.Window({
        title: '<span class="commoncss">系统锁定</span>',
        iconCls: 'lockIcon',
        layout: 'fit',
        width: 320,
        height: 130,
        closeAction: 'hide',
        collapsible: false,
        closable: false,
        maximizable: false,
        border: false,
        modal: true,
        constrain: true,
        animateTarget: Ext.getBody(),
        items: [lockForm],
        listeners: {
            'show': function (obj) {
                lockForm.form.reset();
                lockForm.findById('password_lock').focus(true, 50);
            }
        },
        buttons: [{
            text: '解锁',
            iconCls: 'keyIcon',
            handler: function () {
                unlockSystem();
            }
        }, {
            text: '重新登录',
            iconCls: 'tbar_synchronizeIcon',
            handler: function () {
                window.location.href = 'login.do?reqCode=logout';
            }
        }]
    });

    var userFormPanel = new Ext.form.FormPanel({
        defaultType: 'textfield',
        labelAlign: 'right',
        labelWidth: 70,
        frame: false,
        bodyStyle: 'padding:5 5 0',
        items: [{
            fieldLabel: '登录帐户',
            name: 'usercode',
            id: 'usercode',
            allowBlank: false,
            readOnly: true,
            fieldClass: 'x-custom-field-disabled',
            anchor: '99%'
        }, {
            fieldLabel: '姓名',
            name: 'username',
            id: 'username',
            allowBlank: false,
            readOnly: true,
            fieldClass: 'x-custom-field-disabled',
            anchor: '99%'
        }, {
            fieldLabel: '当前密码',
            name: 'password2',
            id: 'password2',
            inputType: 'password',
            labelStyle: micolor,
            maxLength: 50,
            allowBlank: false,
            anchor: '99%'
        }, {
            fieldLabel: '新密码',
            name: 'password',
            id: 'password',
            inputType: 'password',
            labelStyle: micolor,
            maxLength: 50,
            allowBlank: false,
            anchor: '99%'
        }, {
            fieldLabel: '确认新密码',
            name: 'password1',
            id: 'password1',
            inputType: 'password',
            labelStyle: micolor,
            maxLength: 50,
            allowBlank: false,
            anchor: '99%'
        }, {
            id: 'userid',
            name: 'userid',
            hidden: true
        }]
    });

    var userWindow = new Ext.Window({
        layout: 'fit',
        width: 300,
        height: 215,
        resizable: false,
        draggable: true,
        closeAction: 'hide',
        modal: true,
        title: '<span class="commoncss">密码修改</span>',
        iconCls: 'keyIcon',
        collapsible: true,
        titleCollapse: true,
        maximizable: false,
        buttonAlign: 'right',
        border: false,
        animCollapse: true,
        animateTarget: Ext.getBody(),
        constrain: true,
        listeners: {
            'show': function (obj) {
                Ext.getCmp('password2').focus(true, 200);
            }
        },
        items: [userFormPanel],
        buttons: [{
            text: '保存',
            iconCls: 'acceptIcon',
            handler: function () {
                if (runMode == '0') {
                    Ext.Msg.alert('提示',
                        '系统正处于演示模式下运行,您的操作被取消!该模式下只能进行查询操作!');
                    return;
                }
                updateUser();
            }
        }, {
            text: '关闭',
            iconCls: 'deleteIcon',
            handler: function () {
                userWindow.hide();
            }
        }]
    });

    function unlockSystem() {
        // showWaitMsg();
        if (!lockForm.form.isValid())
            return;
        var params = lockForm.getForm().getValues();
        Ext.Ajax.request({
            url: 'index.do?reqCode=unlockSystem',
            success: function (response, opts) {
                var resultArray = Ext.util.JSON
                    .decode(response.responseText);
                if (resultArray.flag == "1") {
                    lockWindow.hide();
                    setCookie("vcf.lockflag", '0', 240);
                } else {
                    Ext.Msg.alert('提示', '用户名或密码有误,请重新输入', function () {
                        lockForm.form.reset();
                        lockForm.findById('password_lock')
                            .focus();
                    });
                }
            },
            failure: function (response, opts) {
            },
            params: params
        });
    }

    /**
     * 皮肤窗口初始化
     */
    function themeWindowInit() {
        for (i = 0; i < root.childNodes.length; i++) {
            var child = root.childNodes[i];
            if (default_theme == child.data.theme) {
                child.select();
            }
        }
        var o = document.getElementById('previewDiv');
        o.innerHTML = '<img src="./resource/image/theme/' + default_theme
            + '.jpg" />';
        themeWindow.show();

    }

    /**
     * 保存用户自定义皮肤
     */
    function saveUserTheme(o) {
        showWaitMsg();
        Ext.Ajax.request({
            url: './index.do?reqCode=saveUserTheme',
            success: function (response) {
                var resultArray = Ext.util.JSON.decode(response.responseText);
                Ext.MessageBox
                    .confirm(
                        '请确认',
                        '您选择的['
                        + o
                        + ']皮肤保存成功,立即应用该皮肤吗?<br>提示：页面会被刷新,请先确认是否有尚未保存的业务数据,以免丢失!',
                        function (btn, text) {
                            if (btn == 'yes') {
                                //showWaitMsg('正在为您应用皮肤...');
                                var theme = o;
                                if (theme !== '') {
                                    setParam({ theme: theme });
                                } else {
                                    removeParam('theme');
                                }
                            } else {
                                Ext.Msg.alert('提示',
                                    '请在任何时候按[F5]键刷新页面或者重新登录系统以启用['
                                    + o + ']皮肤!',
                                    function () {
                                        themeWindow.hide();
                                    });

                            }
                        });
            },
            failure: function (response) {
                var resultArray = Ext.util.JSON.decode(response.responseText);
                Ext.Msg.alert('提示', '数据保存失败');
            },
            params: {
                theme: o
            }
        });
    }

    /**
     * 加载当前登录用户信息
     */
    function updateUserInit() {
        userFormPanel.form.reset();
        userWindow.show();
        userWindow.on('show', function () {
            setTimeout(function () {
                userFormPanel.form.load({
                    waitTitle: '提示',
                    waitMsg: '正在读取用户信息,请稍候...',
                    url: 'index.do?reqCode=loadUserInfo',
                    success: function (form, action) {
                    },
                    failure: function (form, action) {
                        Ext.Msg
                            .alert(
                                '提示',
                                '数据读取失败:'
                                + action.failureType);
                    }
                });
            }, 5);
        });
    }

    /**
     * 修改用户信息
     */
    function updateUser() {
        if (!userFormPanel.form.isValid()) {
            return;
        }
        password1 = Ext.getCmp('password1').getValue();
        password = Ext.getCmp('password').getValue();
        if (password1 != password) {
            Ext.Msg.alert('提示', '两次输入的密码不匹配,请重新输入!');
            Ext.getCmp('password').setValue('');
            Ext.getCmp('password1').setValue('');
            return;
        }
        userFormPanel.form.submit({
            url: 'index.do?reqCode=updateUserInfo',
            waitTitle: '提示',
            method: 'POST',
            waitMsg: '正在处理数据,请稍候...',
            success: function (form, action) {
                Ext.MessageBox.alert('提示', '密码修改成功', function () {
                    userWindow.hide();
                });
            },
            failure: function (form, action) {
                var flag = action.result.flag;
                if (flag == '0') {
                    Ext.MessageBox.alert('提示', '您输入的当前密码验证失败,请重新输入',
                        function () {
                            Ext.getCmp('password2').setValue('');
                            Ext.getCmp('password2').focus();
                        });
                } else {
                    Ext.MessageBox.alert('提示', '密码修改失败');
                }
            }
        });
    }

    function logout() {
        Ext.MessageBox.show({
            title: '提示',
            msg: '确认要注销系统,退出登录吗?',
            width: 250,
            buttons: Ext.MessageBox.YESNO,
            animEl: Ext.getBody(),
            icon: Ext.MessageBox.QUESTION,
            fn: function (btn) {
                if (btn == 'yes') {
                    Ext.Ajax.request({
                        url : '/iaeon_logout.htm',
                        success : function() {
                            window.location.href = '/manage/login.htm';
                        },
                        failure : function(response) {

                        }
                    });
                }
            }
        });
    }

    if (getCookie("vcf.lockflag") == '1') {
        lockWindow.show();
    }


    /**消息提醒*/
    //var msg = "<span style='font-size: 20px;'>【来文办理】待处理文件 20 份</span>";
    //var win = new Ext.Window({
    //    width: 340,
    //    height: 200,
    //    layout: 'fit',
    //    modal: false,
    //    plain: true,
    //    bodyStyle:'background-color: #dde6f4',
    //    shadow: false, //去除阴影
    //    draggable: false, //默认不可拖拽
    //    resizable: false,
    //    closable: true,
    //    closeAction: 'hide', //默认关闭为隐藏
    //    autoHide: 15, //15秒后自动隐藏，false则不自动隐藏
    //    title: '待办事项',
    //    html: '' + msg + '',
    //    constructor: function (conf) {
    //        Ext.Window.superclass.constructor.call(this, conf);
    //        this.initPosition(true);
    //    }
    //
    //    ,
    //    initEvents: function () {
    //        Ext.Window.superclass.initEvents.call(this);
    //        //自动隐藏
    //        if (false !== this.autoHide) {
    //            var task = new Ext.util.DelayedTask(this.hide, this), second = (parseInt(this.autoHide) || 3) * 1000;
    //            this.on('beforeshow', function (self) {
    //                task.delay(second);
    //            });
    //        }
    //        this.on('beforeshow', this.showTips);
    //        this.on('beforehide', this.hideTips);
    //        //window大小改变时，重新设置坐标
    //        Ext.EventManager.onWindowResize(this.initPosition, this);
    //        //window移动滚动条时，重新设置坐标
    //        Ext.EventManager.on(window, 'scroll', this.initPosition, this);
    //    }
    //    ,
    //    //参数flag为true时强制更新位置
    //    initPosition: function (flag) {
    //        //不可见时，不调整坐标
    //        if (true !== flag && this.hidden) {
    //            return false;
    //        }
    //        var doc = document, bd = (doc.body || doc.documentElement);
    //        //Ext取可视范围宽高(与上面方法取的值相同), 加上滚动坐标
    //        var left = bd.scrollLeft + Ext.lib.Dom.getViewWidth() - 4 - this.width;
    //        var top = bd.scrollTop + Ext.lib.Dom.getViewHeight() - 20 - this.height;
    //        this.setPosition(left, top);
    //    }
    //    ,
    //    showTips: function () {
    //        var self = this;
    //        if (!self.hidden) {
    //            return false;
    //        }
    //        //初始化坐标
    //        self.initPosition(true);
    //        self.el.slideIn('b', {
    //            callback: function () {
    //                //显示完成后,手动触发show事件,并将hidden属性设置false,否则将不能触发hide事件
    //                self.fireEvent('show', self);
    //                self.hidden = false;
    //            }
    //        });
    //        //不执行默认的show
    //        return false;
    //    }
    //    ,
    //    hideTips: function () {
    //        var self = this;
    //        if (self.hidden) {
    //            return false;
    //        }
    //        self.el.slideOut('b', {
    //            callback: function () {
    //                //渐隐动作执行完成时,手动触发hide事件,并将hidden属性设置true
    //                self.fireEvent('hide', self);
    //                self.hidden = true;
    //            }
    //        });
    //        //不执行默认的hide
    //        return false;
    //    }
    //});
    //function wfd_kindRender(value){
    //    if (value == '1')
    //        return '来文办理';
    //    if (value == '2')
    //        return '会议请示通知';
    //    if (value == '3')
    //        return '建议提案';
    //    if (value == '303')
    //        return '资料库';
    //    if (value == '304')
    //        return '信息资讯';
    //    if (value == '305')
    //        return '税保报表';
    //    if (value == '801')
    //        return '依申请来文';
    //    if (value == '811')
    //        return '正式答复';
    //    if (value == '812')
    //        return '更改补充';
    //    if (value == '813')
    //        return '申请延期';
    //    if (value == '814')
    //        return '征求意见';
    //    if (value == '501')
    //        return '批示件';
    //    return value;
    //};
    //
    //
    //function loadBacklogData(){
    //    Ext.Ajax.request({
    //        url: './moa/moa_wfd.do?reqCode=getBacklogData',
    //        success: function (response) {
    //            var resultArray = Ext.util.JSON.decode(response.responseText);
    //            console.log(resultArray);
    //            msg="";
    //            $.each(resultArray, function(index, content) {
    //               msg+='<span style="margin-left:5px;margin-top:5px;font-size: 18px;">【'+wfd_kindRender(content.wfd_kind)+'】待处理文件 <span style="color: #ff5500;">'+content.num+'</span> 份</.span></br>';
    //            });
    //            win.html=msg;
    //            win.show();
    //        },
    //        failure: function (response) {
    //
    //        }
    //    });
    //}
    //loadBacklogData();
    //var task = {
    //    run: function () {
    //        loadBacklogData();
    //    },
    //    scope: this,
    //    interval: 60000 //1000 = 1 second,
    //};
    //Ext.TaskMgr.start(task);
});


/**
 * 显示系统时钟
 */
function showTime() {
    var date = new Date();
    var h = date.getHours();
    h = h < 10 ? '0' + h : h;
    var m = date.getMinutes();
    m = m < 10 ? '0' + m : m;
    var s = date.getSeconds();
    s = s < 10 ? '0' + s : s;
    document.getElementById('rTime').innerHTML = h + ":" + m + ":" + s;
}

window.onload = function () {
    //setInterval("showTime()", 1000);

};
function setParam(param) {
    var queryString = Ext.Object.toQueryString(
        Ext.apply(Ext.Object.fromQueryString(location.search), param)
    );
    location.search = queryString;
}
function removeParam(paramName) {
    var params = Ext.Object.fromQueryString(location.search);

    delete params[paramName];

    location.search = Ext.Object.toQueryString(params);
}
