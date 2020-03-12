/**
 * 表单：文本框(属性配置)
 *
 * @author aeon
 * @since 2014-08-20
 */
Ext.onReady(function () {
    var firstForm = new Ext.vcf.QueryPanel({
        id: 'firstForm',
        name: 'firstForm',
        layout: 'form',
        labelWidth: 90, // 标签宽度
        // frame : true, //是否渲染表单面板背景色
        defaultType: 'textfield', // 表单元素默认类型
        labelAlign: 'right', // 标签对齐方式
        bodyStyle: 'padding:5 5 5 5', // 表单元素和表单面板的边距
        items: [{
            fieldLabel: '文本框', // 标签
            name: 'text', // hiddenName:后台根据此hiddenName属性取值
            xtype: 'textfield',
            readOnly: true,
            anchor: '100%' // 宽度百分比
        }, {
            fieldLabel: '下拉框（原始）', // 标签
            name: 'combo', // hiddenName:后台根据此hiddenName属性取值
            xtype: 'combo',
            store: Ext.create('Ext.data.Store',{
                fields: ["Name", "Value"],
                data: [
                    { Name: "男", Value: 1 },
                    { Name: "女", Value: 2 }
                ]
            }),
            queryMode: 'local',
            displayField: 'Name',
            valueField: 'Value',
            anchor: '100%' // 宽度百分比
        }, {
            fieldLabel: '下拉框（枚举1）', // 标签
            name: 'enumcombo', // hiddenName:后台根据此hiddenName属性取值
            xtype: 'combofield',
            enumData: '1#包含+2#左包含+3#右包含+4#等于',
            editable: true,
            readOnly: true,
            value:'1',
            fieldStyle: 'cursor: default; pointer-events: none; color: black !important; background: #F6F6F6;',
            forceSelection: false,
            anchor: '100%' // 宽度百分比
        }, {
            fieldLabel: '下拉框（枚举2）', // 标签
            name: 'enumcombo1', // hiddenName:后台根据此hiddenName属性取值
            xtype: 'combofield',
            enumData: '包含,左包含,右包含,等于',
            anchor: '100%' // 宽度百分比
        }, {
            fieldLabel: '下拉框（字典）', // 标签
            name: 'diccombo', // hiddenName:后台根据此hiddenName属性取值
            xtype: 'combofield',
            store: PARTAUTHTYPEStore,
            anchor: '100%' // 宽度百分比
        }, {
            fieldLabel: '下拉框（基础数据）',
            name: 'elecombo',
            xtype: 'combofield',
            source: 'ELE',    //系统要素，对应ea_element中的字段ele_code值
            condition: '',    //过滤条件
            isCodeAsValue: false,   //是否将编码作为值 默认为false，即获取基础数据的ID作为值
            isDirect: false,   //默认为false，指是否每次从数据库获取
            anchor: '100%'
        }, {
            fieldLabel: '单选下拉树', // 标签
            name: 'singletree', // hiddenName:后台根据此hiddenName属性取值
            xtype: 'treefield',
            source: 'ELE',   //系统要素，对应ea_element中的字段ele_code值
            isCodeAsValue: false,  //是否将编码作为值 默认为false，即获取基础数据的ID作为值
            isDirect: false,    //默认为false，指是否每次从数据库获取
            isPermission: false,    //默认为true，指是否按权限过滤基础数据
            // all:所有结点都可选中
            // exceptRoot：除根结点，其它结点都可选（默认）
            // folder:只有目录（非叶子和非根结点）可选
            // leaf：只有叶子结点可选  默认
            selectNodeModel: 'leaf',
            readOnly:true,
            fieldStyle: 'cursor: default; pointer-events: none; color: black !important; background: #F6F6F6;',
            anchor: '100%'// 宽度百分比
        }, {
            fieldLabel: '多选下拉树', // 标签
            name: 'multipetree', // hiddenName:后台根据此hiddenName属性取值
            xtype: 'treefield',
            source: 'ELE',   //系统要素，对应ea_element中的字段ele_code值
            isCodeAsValue: false,  //是否将编码作为值 默认为false，即获取基础数据的ID作为值
            isDirect: false,    //默认为false，指是否每次从数据库获取
            isPermission: true,    //默认为true，指是否按权限过滤基础数据
            // all:所有结点都可选中
            // exceptRoot：除根结点，其它结点都可选（默认）
            // folder:只有目录（非叶子和非根结点）可选
            // leaf：只有叶子结点可选  默认
            selectNodeModel: 'leaf',
            selectModel: 'multiple',   //single单选 （默认） multiple 多选的
            anchor: '100%'// 宽度百分比
        }, {
            fieldLabel: '金额', // 标签
            name: 'name4', // name:后台根据此name属性取值
            xtype: 'moneyfield',
            editable: false,
            anchor: '100%' // 宽度百分比
        }, {
            fieldLabel: '百分比', // 标签
            name: 'name5', // name:后台根据此name属性取值
            xtype: 'percentfield',
            anchor: '100%' // 宽度百分比
        }, {
            fieldLabel: '值为1和0的复选框', // 标签
            name: 'name6', // name:后台根据此name属性取值
            xtype: 'chkfield',
            anchor: '100%' // 宽度百分比
        }, {
            fieldLabel: '值为1、2、3的单选框', // 标签
            name: 'name7', // name:后台根据此name属性取值
            enumData: '1#男+2#女+3#中性',
            xtype: 'rdofield',
            anchor: '100%' // 宽度百分比
        }, {
            fieldLabel: '我是一个隐藏字段', // 标签
            name: 'name8', // name:后台根据此name属性取值
            xtype: 'hidden',
            anchor: '100%' // 宽度百分比
        }]
    });

    var firstWindow = new Ext.Window({
        title: '<span class="commoncss">VCF平台录入组件</span>', // 窗口标题
        layout: 'fit', // 设置窗口布局模式
        width: 580, // 窗口宽度

        closable: false, // 是否可关闭
        collapsible: true, // 是否可收缩
        maximizable: true, // 设置是否可以最大化
        border: false, // 边框线设置
        constrain: true, // 设置窗口是否可以溢出父容器
        pageY: 20, // 页面定位Y坐标
        pageX: document.body.clientWidth / 2 - 380 / 2, // 页面定位X坐标
        items: [firstForm], // 嵌入的表单面板
        buttons: [{ // 窗口底部按钮配置
            text: '提交', // 按钮文本
            iconCls: 'BFIcon', // 按钮图标
            handler: function () { // 按钮响应函数
                alert(Ext.encode(firstForm.form.getValues()));
            }
        }, { // 窗口底部按钮配置
            text: '条件', // 按钮文本
            iconCls: 'BFIcon', // 按钮图标
            handler: function () { // 按钮响应函数
                alert(Ext.encode(firstForm.getQuery()));
            }
        }, { // 窗口底部按钮配置
            text: '重置', // 按钮文本
            iconCls: 'tbar_synchronizeIcon', // 按钮图标
            handler: function () { // 按钮响应函数
                firstForm.form.reset();
            }
        }]
    });
    firstWindow.show(); // 显示窗口

});