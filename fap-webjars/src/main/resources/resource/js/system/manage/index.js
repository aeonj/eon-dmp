/**
 * 首页部分JS
 *
 */
Ext.onReady(function () {
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
        }, '-', {
            text: '切换到桌面风格',
            iconCls: 'ApplicationviewtileIcon',
            handler: function () {
                showWaitMsg();
                Ext.Ajax.request({
                    url: '/manage/save_layout.htm',
                    success: function (response) {
                        var resultArray = Ext.util.JSON.decode(response.responseText);
                        if (resultArray.success) {
                            Ext.MessageBox
                                .confirm(
                                    '请确认',
                                    '您选择的[桌面风格]布局保存成功,立即应用该布局吗?<br>提示：页面会被刷新,请先确认是否有尚未保存的业务数据,以免丢失!',
                                    function (btn, text) {
                                        if (btn == 'yes') {
                                            showWaitMsg('正在为您应用布局...');
                                            location.reload();
                                        }
                                    });
                        } else {
                            Ext.Msg.alert('提示', '切换失败：' + resultArray.msg);
                        }
                    },
                    failure: function (response) {
                        var resultArray = Ext.util.JSON.decode(response.responseText);
                        Ext.Msg.alert('提示', '切换风格失败');
                    },
                    params: {
                        layout: '2'
                    }
                });
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
    /**密码修改**/
    var lockForm = new Ext.form.FormPanel({
        layout: 'column',
        items: [{
            xtype: 'panel',
            layout: 'form',
            labelAlign: 'right',
            bodyStyle: 'padding:10 5 5 5',
            defaultType: 'textfield',
            labelWidth: 60,
            columnWidth: 1,
            items:[{
                fieldLabel: '帐户密码',
                name: 'password',
                inputType: 'password',
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
                anchor: '98%'
            }]
        }, {
            xtype: 'panel',
            columnWidth: 1,
            border: false,
            html: '<div style="font-size:12px;margin-left:10px;width:100%;">(提示:系统已成功锁定,解锁请输入登录帐户密码)</div>',
            anchor: '100%'
        }]
    });

    var lockWindow = new Ext.Window({
        title: '<span class="commoncss">系统锁定</span>',
        iconCls: 'lockIcon',
        layout: 'fit',
        width: 320,
        autoHeight: true,
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
                lockForm.form.findField('password').focus(true, 50);
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
                Ext.Ajax.request({
                    url : '/iaeon_logout.htm',
                    success : function() {
                        window.location.href = '/manage/login.htm';
                    },
                    failure : function(response) {

                    }
                });
            }
        }]
    });

    var userFormPanel = new Ext.form.FormPanel({
        defaultType: 'textfield',
        labelAlign: 'right',
        labelWidth: 70,
        frame: false,
        bodyStyle: 'padding:12px',
        items: [{
            fieldLabel: '登录帐户',
            name: 'userName',
            allowBlank: false,
            readOnly: true,
            fieldClass: 'x-custom-field-disabled',
            anchor: '99%'
        }, {
            fieldLabel: '姓名',
            name: 'trueName',
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
        autoHeight: true,
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
            url: '/manage/unlock_system.htm',
            success: function (response, opts) {
                var resultArray = Ext.util.JSON
                    .decode(response.responseText);
                if (resultArray.success) {
                    lockWindow.hide();
                    setCookie("vcf.lockflag", '0', 240);
                } else {
                    Ext.Msg.alert('提示', '用户名或密码有误,请重新输入', function () {
                        lockForm.form.reset();
                        lockForm.form.findField('password')
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
     * 保存用户自定义皮肤
     */
    function saveUserTheme(o) {
        showWaitMsg();
        Ext.Ajax.request({
            url: '/manage/save_theme.htm',
            success: function (response) {
                var resultArray = Ext.util.JSON.decode(response.responseText);
                if (resultArray.success) {
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
                                        setParam({theme: theme});
                                    } else {
                                        removeParam('theme');
                                    }
                                }
                            });
                } else {
                    Ext.Msg.alert('提示', '保存失败：' + resultArray.msg);
                }
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
                    url: '/manage/load_user_info.htm',
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
        if (password.length<6) {
            Ext.Msg.alert('提示', '新设置的用户密码必须不少于6位!');
            Ext.getCmp('password').setValue('');
            Ext.getCmp('password1').setValue('');
            return;
        }
        userFormPanel.form.submit({
            url: '/manage/update_user_psw.htm',
            waitTitle: '提示',
            method: 'POST',
            waitMsg: '正在处理数据,请稍候...',
            success: function (form, action) {
                if (action.result.success) {
                    Ext.Msg.alert('提示', '密码修改成功', function () {
                        userWindow.hide();
                    });
                } else {
                    Ext.MessageBox.alert('提示', action.result.msg);
                }

            },
            failure: function (form, action) {
                switch (action.failureType) {
                    case Ext.form.action.Action.SERVER_INVALID:
                        if (action.result.code==500201) {
                            Ext.Msg.alert('提示', '您输入的当前密码验证失败,请重新输入',
                                function () {
                                    Ext.getCmp('password2').setValue('');
                                    Ext.getCmp('password2').focus();
                                });
                        } else {
                            Ext.Msg.alert('提示', '密码修改失败:<br>'+action.result.msg);
                        }
                        break;
                    default:
                        Ext.Msg.alert('错误', '密码修改失败:<br>'+action.response.responseText);
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
                        url: '/iaeon_logout.htm',
                        success: function () {
                            window.location.href = '/manage/login.htm';
                        },
                        failure: function (response) {

                        }
                    });
                }
            }
        });
    }

    if (getCookie("vcf.lockflag") == '1') {
        lockWindow.show();
    }

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
