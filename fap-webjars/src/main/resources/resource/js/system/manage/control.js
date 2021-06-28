/**
 * Created by aeon on 2018/6/7.
 */
/**
 * Created by aeon on 2018/6/1.
 */
Ext.apply(Function.prototype, {

    createInterceptor : function(fcn, scope){
        var method = this;
        return !Ext.isFunction(fcn) ?
            this :
            function() {
                var me = this,
                    args = arguments;
                fcn.target = me;
                fcn.method = method;
                return (fcn.apply(scope || me || window, args) !== false) ?
                    method.apply(me || window, args) :
                    null;
            };
    },


    createCallback : function(){

        var args = arguments,
            method = this;
        return function() {
            return method.apply(window, args);
        };
    },


    createDelegate : function(obj, args, appendArgs){
        var method = this;
        return function() {
            var callArgs = args || arguments;
            if (appendArgs === true){
                callArgs = Array.prototype.slice.call(arguments, 0);
                callArgs = callArgs.concat(args);
            }else if (Ext.isNumber(appendArgs)){
                callArgs = Array.prototype.slice.call(arguments, 0);
                var applyArgs = [appendArgs, 0].concat(args);
                Array.prototype.splice.apply(callArgs, applyArgs);
            }
            return method.apply(obj || window, callArgs);
        };
    },


    defer : function(millis, obj, args, appendArgs){
        var fn = this.createDelegate(obj, args, appendArgs);
        if(millis > 0){
            return setTimeout(fn, millis);
        }
        fn();
        return 0;
    }
});
/**
 *
 */
Ext.override(Ext.tree.Column, {
    cellTpl: [
        '<tpl for="lines">',
        '<div class="{parent.childCls} {parent.elbowCls}-img ',
        '{parent.elbowCls}-<tpl if=".">line<tpl else>empty</tpl>" role="presentation"></div>',
        '</tpl>',
        '<div class="{childCls} {elbowCls}-img {elbowCls}',
        '<tpl if="isLast">-end</tpl><tpl if="expandable">-plus {expanderCls}</tpl>" role="presentation"></div>',
        '<tpl if="checked !== null">',
        '<div role="button" <tpl if="checked===1">style="color:#c8c8c8;"</tpl>',
        ' class="{childCls} {checkboxCls}<tpl if="checked"> {checkboxCls}-checked</tpl>"></div>',
        '</tpl>',
        '<tpl if="glyph">',
        '<span class="{baseIconCls}" ',
        '<tpl if="glyphFontFamily">',
        'style="font-family:{glyphFontFamily}"',
        '</tpl>',
        '>{glyph}</span>',
        '<tpl else>',
        '<tpl if="icon">',
        '<img src="{blankUrl}"',
        '<tpl else>',
        '<div',
        '</tpl>',
        ' role="presentation" class="{childCls} {baseIconCls} {customIconCls} ',
        '{baseIconCls}-<tpl if="leaf">leaf<tpl else><tpl if="expanded">parent-expanded<tpl else>parent</tpl></tpl> {iconCls}" ',
        '<tpl if="icon">style="background-image:url({icon})"/><tpl else>></div></tpl>',
        '</tpl>',
        '<tpl if="href">',
        '<a href="{href}" role="link" target="{hrefTarget}" class="{textCls} {childCls}">{value}</a>',
        '<tpl else>',
        '<span class="{textCls} {childCls}">{value}</span>',
        '</tpl>'
    ]

});

Ext.define('Ext.vcf.Node',{
    extend : 'Ext.data.Record',
    fields:[{
        name : 'id',
        type : 'string'
    }, {
        name : 'code',
        type : 'string'
    }, {
        name : 'name',
        type : 'string'
    }]
});

Ext.define('Ext.vcf.AssistTree.Loader', {
    extend : 'Ext.data.TreeStore',
    //tree : Ext.create('Ext.vcf.AssistTree.Tree'),
    onProxyLoad : function(operation){
        this.callParent(arguments);
        if (this.ownerField)
            this.ownerField.fireEvent('resetChecked', this.ownerField);
        this.ownerField.fireEvent('lastExecute', this.ownerField);
    }
});

Ext.define('Ext.vcf.AssistTree', {
    extend : 'Ext.tree.Panel',
    alias : 'vcf.assisttree',
    xtype : 'assisttree',
    url : '/manage/ele_chk_tree.htm',
    source : '',
    condition : '',
    params : {},
    /*
     * 自定义节点treeUI类，与节点属性uiProvider配合使用
     */
    uiProviders : {},
    /*
     * single单选 （默认） multiple 多选的
     */
    selectModel : 'single',
    treeRootText : '全部',
    checkReadOnly : false,
    //是否将编码作为值
    isCodeAsValue : false,
    /**
     * 是否直接从数据库后台获取数据，默认使用缓冲数据
     * @type Boolean
     */
    isDirect : false,
    /**
     * 是否按权限获取基础数据
     */
    isPermission : true,
    /**
     * 是否获取基础数据表所有字段信息，这样可以在node.attributes属性集获取扩展字段值
     * 默认为false，只获取id,code,name,text,leaf等节点字段值
     */
    isFullData : false,
    /**
     * 是否获取基础数据表所有记录信息，设置为false时，这样可以逐级获取后台数据，但节点查找和定位将不可用
     * 默认为true，获取所有级次的记录信息
     */
    isFullLevel : true,
    /**
     * 节点文本是否只显示名称
     */
    onlyName : false,
    border : true, // 面板边框
    useArrows : true, // 箭头节点图标
    autoScroll : true,
    animate : false,
    clearOnLoad : true,
    checkPropagation : 'both',
    triStateCheckbox: 1,
    initComponent : function() {
        var treeobj = this;

        if (this.source || this.url) {
            /**
             *
             * 与后台通讯的加载器
             */
            this.store = Ext.create('Ext.vcf.AssistTree.Loader', {
                fields: ['id', 'code', 'name', 'text', 'leaf'],
                root: {
                    id: '0',
                    text: this.treeRootText,
                    expanded: true,
                    checked: treeobj.selectModel == 'multiple' ? false : null
                },
                proxy: {
                    type: 'ajax',
                    url: this.url,
                    reader: 'json'
                },
                source: this.source,
                condition: this.condition,
                isDirect: this.isDirect,
                uiProviders: this.uiProviders,
                isPermission: this.isPermission,
                isFullData: this.isFullData,
                isFullLevel: this.isFullLevel,
                onlyName: this.onlyName,
                selectModel: this.selectModel,
                params: this.params,
                ownerField: this,
                listeners: {
                    'beforeload': function (treeloader, operation) {
                        treeobj.fireEvent('beforequery', treeloader, operation);
                        var bparams = {
                            selectmodel: this.selectModel,
                            source: this.source,
                            condition: this.condition,
                            isdirect: this.isDirect,
                            isfulldata: this.isFullData,
                            isfulllevel: this.isFullLevel,
                            onlyname: this.onlyName,
                            ispermission: this.isPermission
                        };
                        treeloader.baseParams = mergeObj(bparams, this.params);
                        //根据源码，此处可导致lastOperations同步变化
                        Ext.apply(operation.config.params, treeloader.baseParams);
                        //Ext.apply(treeloader.proxy.extraParams, treeloader.baseParams);
                    }
                }
            });
        }
        //Ext.apply(this.getSelectionModel().prototype, {
        //    getSelectedNode : function() {
        //        return this.selected.items[0];
        //    }
        //});
        this.callParent(arguments);
        //this.addEvents('resetChecked');
        //this.addEvents('lastExecute');
        //this.store.addEvents('beforequery');
    },
    setSource : function(value) {
        this.source = value;
        this.store.source = value;
        this.store.reload();
    },

    setParams : function(value) {
        this.params = value;
        this.store.params = value;
        this.store.reload();
    },

    getSource : function() {
        return this.source;
    },

    setCondition : function(value) {
        this.condition = value;
        this.store.condition = value;
        this.store.reload();
    },

    getCondition : function() {
        return this.condition;
    },


    /**
     *checkTree全选
     */
    treeCheckTrue:function(node)
    {
        var tree =this;
        if(typeof node != 'undefined'){
            tree.nodeCheckTrue(node);
        }else{
            tree.nodeCheckTrue(tree.getRootNode());
        }
    },

    nodeCheckTrue:function(node)
    {
        node.eachChild(function (child) {
            child.set('checked',true);
            child.cascadeBy(function(n) {
                n.set('checked',true);
            });
        });
    },
    /**
     *checkTree清空
     */
    treeCheckFalse : function()
    {
        var tree =this;
        var nodes = tree.getChecked();
        if(nodes && nodes.length){
            for(var i=0;i<nodes.length;i++){
                nodes[i].set('checked',false);
            }
        }
    } ,

    /**
     * 重置选中结果
     * @param {} values 选中项。可以为逗号分隔的字串，也可以为从后台传出的list json数组对象
     * @param {} field 字段名。如果为后台传出的数组，可支持取指定字段的值
     *
     * 例：
     * 传入逗号分隔的字串
     *    resetChecked('逗号分隔的id')
     * 传入值为json数组对象：
     *   后台：
     *     String jsonString = JsonHelper.encodeObject2Json(从sql语句获取的list对象);
     *       write(jsonString, response);
     *   js端：
     *   var resultArray = Ext.util.JSON
     *                          .decode(response.responseText);
     *   resetChecked(resultArray,'id字段名');
     *
     */
    resetChecked : function(values,field){
        var tree =this;
        if (this.getRootNode().isLoading()) {
            this.on('resetChecked',function(t) {
                tree.setResetChecked(values,field);
            });
        } else {
            tree.setResetChecked(values,field);
        }
    },
    setResetChecked : function(values,field){
        var tree =this;

        var f = function(){
            this.set('checked',false);
            if(tree.isExistsNode(this,values,field,tree.isCodeAsValue)){
                tree.view.setChecked(this,true);
            }
            if (tree.checkReadOnly) {
                //if (this.getUI().checkbox) {
                //    this.getUI().checkbox.readOnly = true;
                //}
            }
        };
        tree.getRootNode().cascadeBy(f);
    },
    isExistsNode : function(node,values,field,iscode) {
        if (typeof values == 'object') {
            if (values!=null) {
                if (iscode && node.data.code) {
                    var code = node.data.code;
                    for (var i=0; i<values.length; i++) {
                        if (typeof values[i] == 'object' && typeof field != 'undefined') {
                            if (code==values[i][field]) {
                                return true;
                            }
                        } else {
                            if (code==values[i]) {
                                return true;
                            }
                        }
                    }
                } else {
                    var id = node.data.id;
                    for (var i=0; i<values.length; i++) {
                        if (typeof values[i] == 'object' && typeof field != 'undefined') {
                            if (id==values[i][field]) {
                                return true;
                            }
                        } else {
                            if (id==values[i]) {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        } else {
            if (values!='') {
                if (iscode && node.data.code) {
                    var code=','+node.data.code+',';
                    if ((','+values+',').indexOf(code)!= -1)
                        return true;
                } else {
                    var ids=','+node.data.id+',';
                    if ((','+values+',').indexOf(ids)!= -1)
                        return true;
                }
            }
            return false;
        }
    },
    /**
     *
     * @param {} node 当前node
     * @param {} type 1#包含+2#左包含+3#右包含+4#精确定位
     * @param {} text 文本
     * @param {} isclick
     */
    selectNodeByLikeText : function (node,type,text,isclick) {
        var tree = this;
        var isBegin = false;
        var selnode = node;
        var fn = function() {
            var result = true;
            if (this==node) {
                isBegin = true;
                return true;
            }
            if (!isBegin)
                return true;
            //为了能级联选中 by aeon
            if (type==1) {
                if (this.get('text').indexOf(text)>=0) {
                    result = false;
                }
            } else if (type==2) {
                if (this.get('text').indexOf(text)==0) {
                    result = false;
                }
            } else if (type==3) {
                if (this.get('text').indexOf(text)==this.get('text').length-text.length) {
                    result = false;
                }
            } else if (type==4) {
                if (this.get('text')==text || this.get('code')==text || this.get('name')==text ) {
                    result = false;
                }
            }
            if (!result && selnode==node) {
                selnode = this;
            }
            return result;
        };
        this.getRootNode().cascadeBy(fn);
        this.selectItemByNode(selnode);
    },

    selectNodeByValue : function(value) {
        var selNode;
        if (this.isCodeAsValue){
            selNode = this.store.findNode('code',value);
        } else {
            selNode = this.store.getNodeById(value);
        }
        this.selectItemByNode(selNode);
    },
    selectItemByNode : function(node) {
        var tree = this;
        tree.getSelectionModel().select(node);
        setTimeout(function() {
            tree.fireEvent("itemclick",tree,node);
        }, 0);
    },
    getCheckValues : function () {
        //检查是否被选定
        var tree = this,
            checkedNodes = tree.getChecked();
        if (Ext.isEmpty(checkedNodes)) {
            return '';
        }
        var nodes = '';
        Ext.each(checkedNodes, function(node) {
            if (node.isLeaf()) {
                if (tree.isCodeAsValue) {
                    nodes = nodes + node.data.code + ',';
                } else {
                    nodes = nodes + node.data.id + ',';
                }
            }
        });
        if (nodes.length>0) {
            nodes = nodes.substring(0,nodes.length-1);
        }
        return nodes;
    }

});

Ext.define('Ext.vcf.ComboField',{
    extend : 'Ext.form.field.ComboBox',
    alias : 'vcf.combofield',
    xtype : 'combofield',
    queryMode : 'local',
    valueField : 'value',
    displayField : 'text',
    url : '/manage/ele_chk_tree.htm',
    /**
     * 枚举项。格式：1#男+2#女
     */
    enumData : '',
    /**
     * 数据源要素
     */
    source : '',
    /**
     * 筛选条件
     */
    condition : '',
    editable : false,
    //是否将编码作为值
    isCodeAsValue : false,
    //是否从后台读取数据，不使用缓存
    isDirect : false,
    //多选使用逗号分隔
    multiStringVal : true,
    validateOnBlur: false,
    forceSelection : true,
    triggerAction : 'all',
    initComponent : function() {
        var me = this;
        //if (Ext.isDefined(this.name)){
        //    if (!Ext.isDefined(this.hiddenName)) {
        //        this.hiddenName = this.name;
        //    }
        //this.name = undefined;
        //}
        if (me.source=='') {
            if (typeof me.store == 'undefined') {
                me.store = new Ext.data.Store({
                    fields : ['value', 'text'],
                    data : me.getEnumData()
                });
            }
        } else {
            me.queryMode = 'remote';
            me.valueField = this.isCodeAsValue ? 'code' : 'id';
            me.displayField = 'name';
            me.store = new Ext.data.ArrayStore({
                autoLoad : true,
                fields : ['id','code','name'],
                url : me.url,
                source : me.source,
                condition : me.condition,
                isDirect : me.isDirect,
                proxy : {
                    type : 'ajax',
                    url : me.url,
                    reader : {
                        type : 'json'
                    }
                },
                listeners : {
                    'beforeload' : function(store,op) {
                        Ext.apply(op.config.params , {
                            source : this.source,
                            condition : this.condition,
                            isdirect : this.isDirect
                        });
                    }
                }
            });
        };

        if (me.multiSelect) {
            me.listConfig = {
                itemTpl: Ext.create('Ext.XTemplate',
                    '<input type=checkbox>{'+me.displayField+'}'),
                onItemSelect: function (record) {
                    var node = this.getNode(record);
                    if (node) {
                        Ext.fly(node).addCls(this.selectedItemCls);

                        var checkboxs = node.getElementsByTagName("input");
                        if (checkboxs != null) {
                            var checkbox = checkboxs[0];
                            checkbox.checked = true;
                        }
                    }
                },
                listeners: {
                    itemclick: function (view, record, item, index, e, eOpts) {
                        var isSelected = view.isSelected(item);
                        var checkboxs = item.getElementsByTagName("input");
                        if (checkboxs != null) {
                            var checkbox = checkboxs[0];
                            if (!isSelected) {
                                checkbox.checked = true;
                            } else {
                                checkbox.checked = false;
                            }
                        }
                    }
                }
            };
        }

        me.callParent(arguments);
        //隐藏项ID赋值
        if (!Ext.isIE) {
            if (typeof me.hiddenId=='undefined') {
                me.hiddenId = me.id+'_sub';
            }
        }
    },
    setSource : function(value) {
        this.source = value;
        if (this.mode=='remote')
            this.store.source = value;
        this.store.reload();
    },

    getSource : function() {
        return this.source;
    },

    getNode: function () {
        var me=this;
        if (me.getSource()!='') {
            return me.getSelectedRecord();
        } else {
            return null;
        }
    },

    setCondition : function(value) {
        this.condition = value;
        if (this.mode=='remote')
            this.store.condition = value;
        this.store.reload();
    },

    getCondition : function() {
        return this.condition;
    },

    setEnumData : function(value) {
        this.enumData = value;
        this.store.loadData(this.getEnumData());
    },

    getEnumData : function() {
        var r = [];
        if (this.enumData!=null && this.enumData!='') {
            if (this.enumData.indexOf('#')!=-1) {
                var newData = this.enumData+'+';
                var ipos = newData.indexOf('+');
                while (ipos!=-1) {
                    var subData = newData.substring(0,ipos);
                    newData = newData.substring(ipos+1,newData.length);
                    var ifieldpos = subData.indexOf('#');
                    var f = {};
                    if (ifieldpos!=-1) {
                        f.value = subData.substring(0,ifieldpos);
                        f.text = subData.substring(ifieldpos+1,subData.length);
                    }
                    r.push(f);
                    ipos = newData.indexOf('+');
                }
            } else {
                var newData = this.enumData+',';
                var ipos = newData.indexOf(',');
                while (ipos!=-1) {
                    var subData = newData.substring(0,ipos);
                    newData = newData.substring(ipos+1,newData.length);
                    var f = {value : subData, text : subData};
                    r.push(f);
                    ipos = newData.indexOf(',');
                }
            }
        }
        return r;
    },
    getSubmitValue: function() {
        var value = this.getValue();
        if (Ext.isEmpty(value)) {
            value = '';
        } else if (this.multiSelect && this.multiStringVal) {
            value = value.join();
        }
        return value;
    },
    setValue: function(val) {
        var value = val;
        if (this.multiSelect && this.multiStringVal && val!=null) {
            if (!Ext.isArray(val)) {
                value = val.split(',');
            }
        }
        return Ext.vcf.ComboField.superclass.setValue.call(this, value);
    }

});

Ext.define('Ext.vcf.TreeField', {
    extend: 'Ext.form.field.Picker',
    xtype : 'treefield',
    requires: [
        'Ext.tree.Panel'
    ],
    uses: [
        'Ext.tree.Panel'
    ],
    triggerCls: Ext.baseCSSPrefix + 'form-arrow-trigger',
    url : '/manage/ele_chk_tree.htm',
    treeRootText : '全部',
    source : '',
    condition : '',
    rootVisible : true,
    editable : false,
    //是否将编码作为值
    isCodeAsValue : false,
    /**
     * 是否从数据库后台获取基础数据
     */
    isDirect : false,
    /**
     * 是否按权限获取基础数据
     */
    isPermission : true,
    /**
     * 是否启用要素关系控制
     */
    isRelation : undefined,
    /**
     * 是否获取基础数据表所有字段信息，这样可以在node.attributes属性集获取扩展字段值
     * 默认为false，只获取id,code,name,text,leaf等节点字段值
     */
    isFullData : false,
    /**
     * 是否获取基础数据表所有记录信息，设置为false时，这样可以逐级获取后台数据，但节点查找和定位将不可用
     * 默认为true，获取所有级次的记录信息
     */
    isFullLevel : true,
    /**
     * 节点文本是否只显示名称
     */
    onlyName : false,
    mode : 'local',
    triggerAction : 'all',
    selectedClass : '',
    validateOnBlur: false,

    // all:所有结点都可选中
    // exceptRoot：除根结点，其它结点都可选（默认）
    // folder:只有目录（非叶子和非根结点）可选
    // leaf：只有叶子结点可选
    selectNodeModel : 'leaf',

    /*
     * single单选 （默认） multiple 多选的
     */
    selectModel : 'single',
    /**
     * 树节点是否级联选择
     * none,up,down,both
     */
    checkPropagation : 'both',
    initComponent: function() {
        var me = this;
        me.relationobj = [];
        me.treeId = Ext.id() + '-tree';
        me.callParent(arguments);
        // me.addEvents('afterchange');
    },
    createPicker: function() {
        var combo = this;
        if (typeof combo.store=='undefined') {
            combo.store = Ext.create('Ext.data.TreeStore', {
                ownerField : combo,
                proxy: {
                    type: 'ajax',
                    url: combo.url
                },
                root: {
                    id: '0',
                    text: combo.treeRootText,
                    expanded: true
                },
                listeners: {
                    'beforeload': function (treeloader, operation) {
                        combo.fireEvent('beforequery', treeloader, operation);
                        var bparams = {
                            selectmodel: combo.selectModel,
                            source: combo.source,
                            condition: combo.condition,
                            isdirect: combo.isDirect,
                            ispermission: combo.isPermission,
                            isfulldata: combo.isFullData,
                            isfulllevel: combo.isFullLevel,
                            onlyname: combo.onlyName,
                            values: combo.getValue()
                        };
                        if (combo.isRelation) {
                            bparams.relationfilter = JSON.stringify(combo.relationobj);
                        }
                        treeloader.baseParams = mergeObj(bparams,this.params);
                        //根据源码，此处可导致lastOperations同步变化
                        Ext.apply(operation.config.params,treeloader.baseParams);
                        //Ext.apply(treeloader.proxy.extraParams, treeloader.baseParams);
                    }
                }
            });
        }
        var tooltop =  Ext.create('Ext.toolbar.Toolbar',{
            items:[{
                text: '确定',
                handler: function () {
                    combo.setValue(combo.picker.getRootNode());
                    combo.collapse();
                    combo.fireEvent('afterok', combo, combo.value);
                }
            }, '-',
                {
                text: '取消',
                handler: function () {
                    combo.collapse();
                }
            }, '->', {
                xtype : 'locatefield',
                emptyText: '请输入模糊文本',
                enableKeyEvents: true,
                listeners: {
                    locate: function (field, e) {

                            combo.queryTempletItem(field.getValue());

                    }
                }
            }]
        });


        var myPicker = combo.picker;
        combo.picker = myPicker = Ext.create('Ext.tree.Panel',{
            //shrinkWrapDock: 2,
            scrollable:true,
            floating: true,
            //focusOnToFront: false,
            shadow: false,
            //ownerCt: this.ownerCt,
            //useArrows: true,
            store: this.store,
            rootVisible: this.rootVisible,
            checkPropagation: this.checkPropagation,
            triStateCheckbox: 0,
            tbar: this.selectModel=='multiple' ? tooltop : null
        });
        if (combo.selectModel=='single') {
            myPicker.on({
                itemclick: combo.onItemClick,
                itemkeydown: combo.onPickerKeyDown,
                scope: this
            });
        }
        return myPicker;
    },
    onItemClick: function(view, record, item, index, e, options) {
        this.selectItem(record);
    },
    onPickerKeyDown: function(treeView, record, item, index, e) {
        var key = e.getKey();

        if (key === e.ENTER || (key === e.TAB && this.selectOnTab)) {
            this.selectItem(record);
        }
    },
    selectItem: function(record) {
        var me = this;
        var selModel = me.selectNodeModel;
        var isLeaf = record.isLeaf();
        if (record.getId() != '0') {
            if (selModel == 'folder' && isLeaf) {
                return;
            } else if (selModel == 'leaf' && !isLeaf) {
                return;
            }
        }

        var oldNode = me.getNode();
        me.setValue(record);
        me.collapse();
        me.fireEvent('select', me, record, oldNode);
    },
    onExpand : function() {
        var me = this;
        if (this.selectModel == 'multiple') {
            //下面两行也可以替换成this.opRoot.reload()从后台读取
            this.resetChecked(this.getPicker().getRootNode(), this.getValue(), this.isCodeAsValue);
        } else {
            if (typeof this.treePath != 'undefined') {
                this.findChildNodesById(this.getPicker().getRootNode(), this.treePath, false);
            }
        }
    },
    alignPicker: function() {
        var me = this,
            picker, isAbove, newPos, aboveSfx = '-above';
        if (me.rendered && !me.destroyed) {
            picker = me.getPicker();
            if (me.matchFieldWidth) {
                picker.setWidth(me.bodyEl.getWidth());
            }
            picker.setHeight(300);
            if (picker.isVisible() && picker.isFloating()) {
                picker.el.alignTo(me.triggerWrap, me.pickerAlign, me.pickerOffset); // ""->tl
                newPos = picker.floatParent ? picker.getOffsetsTo(picker.floatParent.getTargetEl()) : picker.getXY();
                picker.x = newPos[0];
                picker.y = newPos[1];
                // add the {openCls}-above class if the picker was aligned above
                // the field due to hitting the bottom of the viewport
                isAbove = picker.el.getY() < me.inputEl.getY();
                me.bodyEl[isAbove ? 'addCls' : 'removeCls'](me.openCls + aboveSfx);
                picker[isAbove ? 'addCls' : 'removeCls'](picker.baseCls + aboveSfx);
            }
        }
    },
    onTriggerClick: function(e) {
        var me = this,
            store = me.store;
        if (!me.readOnly && !me.disabled) {
            if (me.isExpanded) {
                me.collapse();
            } else {
                if (me.requestload) {
                    if (store) {
                        if (!store.isLoading()) {
                            store.reload();
                        }
                    }
                    delete me.requestload;
                }
                me.expand();
            }
        }
    },
    resetChecked : function(startNode,values,iscode){
        var combo = this,
            picker = combo.picker;
        startNode = startNode || picker.getRootNode();
        var f = function(){
            //为了能级联选中 by aeon
            this.set('checked',false);
            if(combo.isExistsNode(this,values,iscode)){
                picker.view.setChecked(this,true);
            }
        };
        startNode.cascadeBy(f);
    },
    isExistsNode : function(node,values,iscode) {
        if (values!=null && values!='') {
            if (iscode && node.get('code')) {
                var code=','+node.get('code')+',';
                if ((','+values+',').indexOf(code)!= -1)
                    return true;
            } else {
                var ids=','+node.get('id')+',';
                if ((','+values+',').indexOf(ids)!= -1)
                    return true;
            }
        }
        return false;
    },

    setSource : function(value) {
        var me = this;
        me.source = value;
        me.reset();
        me.requestload = true;
        return me;
    },

    getSource : function() {
        return this.source;
    },

    setCondition : function(value) {
        var me = this;
        me.condition = value;
        me.reset();
        me.requestload = true;
        return me;
    },

    getCondition : function() {
        return this.condition;
    },

    resetRelations : function() {
        this.relationobj = [];
    },

    getRelations : function() {
        return this.relationobj;
    },

    appendRelations : function(obj) {
        this.relationobj.push(obj);
        return this;
    },

    removeRelations : function(index) {
        if (index>-1) {
            this.relationobj.splice(index,1);
        }
        return this;
    },

    getNodeById : function(id) {
        var node = this.store.byIdMap[id];
        return node;
    },

    getNode : function() {
        return this.node;
    },

    getNodeByCode : function(code) {
        return this.store.findNode('code',code);
    },

    findTextById : function (root,id) {
    },

    findChildNodesById : function (root,path,isclick) {
        //var path = node.getPath('id');
        var comp = this,
            tree = comp.picker;
        //展开路径,并在回调函数里面选择该节点
        this.getPicker().expandPath(path,'id','/',function(bSucess,oLastNode){
            if (!isclick) {
                tree.getSelectionModel().select(oLastNode);
            } else {
                tree.fireEvent('itemclick', tree, oLastNode);
            }
            comp.node = oLastNode;
        });
    },

    requestTreeValue : function(combo,value) {
        //避免从后台返回数据延迟
        combo.value = value;
        if (combo.source=='') {
            return;
        }
        var dataurl = '/manage/eleinfo_ajax_id.htm';
        if (combo.isCodeAsValue){
            dataurl = '/manage/eleinfo_ajax_code.htm';
        }
        Ext.Ajax.request({
            url : dataurl,
            success : function(response) {
                var resultArray = Ext.JSON
                    .decode(response.responseText);
                if (resultArray.length!=0) {
                    var node = new Ext.vcf.Node();
                    node.set(resultArray);
                    combo.node = node;
                    if (combo.isCodeAsValue) {
                        combo.lastSelectionText = resultArray.code+' '+resultArray.name;
                        if (combo.hiddenField) {
                            combo.hiddenField.value = resultArray.code;
                        }
                        Ext.form.field.Picker.superclass.setValue.call(combo, resultArray.code+' '+resultArray.name);
                        combo.value = resultArray.code;
                    } else {
                        combo.lastSelectionText = resultArray.code+' '+resultArray.name;
                        if (combo.hiddenField) {
                            combo.hiddenField.value = resultArray.id;
                        }
                        Ext.form.field.Picker.superclass.setValue.call(combo, resultArray.code+' '+resultArray.name);
                        combo.value = resultArray.id;
                    }
                    var treepath = '/0';
                    if (resultArray.level_num) {
                        for (var i = 1; i <= resultArray.level_num; i++) {
                            treepath = treepath + '/' + resultArray['id' + i];
                        }
                    }
                    combo.setTreePath(treepath);
                } else {
                    combo.setValue('');
                    combo.node = null;
                }
            },
            failure : function(response) {
                combo.setValue('');
            },
            params : {
                source : combo.source,
                value : value
            }
        });
    },

    requestTreeChkValue : function(combo,chkvalue) {
        //避免从后台返回数据延迟
        combo.value = chkvalue;
        if (combo.source=='') {
            return;
        }
        //end

        var dataurl = '/manage/eleinfo_ajax_ids.htm';
        if (combo.isCodeAsValue){
            dataurl = '/manage/eleinfo_ajax_codes.htm';
        }
        Ext.Ajax.request({
            url : dataurl,
            success : function(response) {
                var resultArray = Ext.JSON
                    .decode(response.responseText);
                var value = '';
                var text = '';
                for (var i=0; i<resultArray.length; i++) {
                    if (combo.isCodeAsValue) {
                        value = value+resultArray[i].code+',';
                        text = text+resultArray[i].name+'/';

                    } else {
                        value = value+resultArray[i].id+',';
                        text = text+resultArray[i].name+'/';
                    }
                }
                if (value.length>0) {
                    value = value.substring(0,value.length-1);
                    text = text.substring(0,text.length-1);
                }
                combo.lastSelectionText = text;
                if (combo.hiddenField) {
                    combo.hiddenField.value = value;
                }
                Ext.form.field.Picker.superclass.setValue.call(combo, text);
                combo.value = value;
            },
            failure : function(response) {
                combo.setValue('');
            },
            params : {
                source : combo.source,
                value : chkvalue
            }
        });
    },

    setValue : function(v) {
        if (typeof v=='undefined') return;
        var me = this,
            oldvalue = me.value;
        if (me.selectModel=='multiple') {
            if (typeof v == "object" && v!=null) {
                var checkedNodes = me.getPicker().getChecked();
                if (Ext.isEmpty(checkedNodes)) {
                    (oldvalue == null || oldvalue !== me.value) ? me.fireEvent('afterchange',
                        me, me.value, oldvalue) : '';
                    return me;
                }
                var selnodeids = '';
                var selnodetexts = '';
                Ext.each(checkedNodes, function(tn) {
                    if (tn.isLeaf()) {
                        if (me.isCodeAsValue){
                            selnodeids = selnodeids + tn.get('code') + ',';
                        } else {
                            selnodeids = selnodeids + tn.get('id') + ',';
                        }
                        if (selnodetexts.length<100) {
                            selnodetexts = selnodetexts + tn.get('name') + '/';
                        }
                    }
                });
                if (selnodeids.length>0) {
                    selnodeids = selnodeids.substring(0,selnodeids.length-1);
                    if (selnodetexts.length>=100) {
                        selnodetexts = selnodetexts.substring(0,selnodetexts.length-1)+'...';
                    } else {
                        selnodetexts = selnodetexts.substring(0,selnodetexts.length-1);
                    }
                }
                me.lastSelectionText = selnodetexts;
                if (me.hiddenField) {
                    me.hiddenField.value = selnodeids;
                }
                Ext.form.field.Picker.superclass.setValue.call(me, selnodetexts);
                me.value = selnodeids;
            } else {
                if (v==null || v=='') {
                    this.lastSelectionText = '';
                    if (this.hiddenField) {
                        this.hiddenField.value = '';
                    }
                    Ext.form.field.Picker.superclass.setValue.call(me, '');
                    this.value = '';
                } else {
                    this.requestTreeChkValue(this,v);
                }
            }
        } else {
            try {
                me.node = undefined;
                if (typeof v == "object") {
                    if (v['data']) {
                        me.node = v;
                    } else {
                        v = v['id'];
                        if (me.picker) {
                            me.node = me.getNodeById(v);
                        }
                    }

                } else {
                    if (v != '') {
                        if (me.picker) {
                            me.node = me.getNodeById(v);
                        }
                    }
                }
                if (v == null || v == '') {
                    if (me.picker) {
                        me.node = me.picker.getRootNode();
                    } else {
                        me.value = '';
                        Ext.form.field.Picker.superclass.setValue.call(this, '');
                        delete me.node;
                        (oldvalue == null || oldvalue !== me.value) ? me.fireEvent('afterchange',
                            me, me.value, oldvalue) : '';
                        return me;
                    }
                }
                if (typeof me.node == 'undefined') {
                    if (me.source == 'ELE') {
                        me.value = v;
                    } else {
                        me.requestTreeValue(me, v);
                    }
                    (oldvalue == null || oldvalue !== me.value) ? me.fireEvent('afterchange',
                        me, me.value, oldvalue) : '';
                    return me;
                }
                var node = me.node;
                var text = node.get('text');
                var value = node.get('id');
                if (node == me.getPicker().getRootNode()) {
                    value = '';
                    text = '';
                } else {
                    if (me.isCodeAsValue) {
                        value = node.get('code');
                    }
                }
                me.lastSelectionText = text;
                if (me.hiddenField) {
                    me.hiddenField.value = value;
                }
                Ext.form.field.Picker.superclass.setValue.call(this, text);
                me.value = value;
                me.setTreePath(node.getPath('id'));
            } catch (err) {
                console.log(err);
            }
        }
        (oldvalue == null || oldvalue !== me.value) ? me.fireEvent('afterchange',
            me, me.value, oldvalue) : '';

        return me.value;
    },
    getValue : function() {
        return typeof this.value != 'undefined' ? this.value : '';
    },

    getSubmitValue: function() {
        var value = this.getValue();
        // If the value is null/undefined, we still return an empty string. If we
        // don't, the field will never get posted to the server since nulls are ignored.
        if (Ext.isEmpty(value)) {
            value = '';
        }
        return value;
    },

    setTreePath : function(path) {
        this.treePath = path;
    },

    getTreePath : function() {
        return this.treePath;
    },
    clearValue : function() {
        this.value = null;
        this.node = null;
    },
    queryTempletItem : function (text) {
        var tree = this.picker;
        var selectModel = tree.getSelectionModel();
        var selectNode = null;
        if (selectModel!=null)
            selectNode = selectModel.selected.items[0];
        if (selectNode ==null)
            selectNode =tree.getRootNode();
        var isBegin = false;
        var selnode = selectNode;
        var fn = function() {
            var result = true;
            if (this==selectNode) {
                isBegin = true;
                return true;
            }
            if (!isBegin)
                return true;

            if (this.get('text').indexOf(text)>=0) {
                result = false;
            }
            if (!result && selnode==selectNode) {
                selnode = this;
            }
            return result;
        };
        tree.getRootNode().cascadeBy(fn);
        this.findChildNodesById(tree.root,selnode.getPath(),false);
    }
});

Ext.define('Ext.vcf.MonthField', {
    extend: 'Ext.form.field.Picker',
    xtype : 'monthfield',
    requires: ['Ext.picker.Month'],

    format: "Y-m",

    showButtons: true,

    altFormats: "m/y|m/Y|m-y|m-Y|my|mY|y/m|Y/m|y-m|Y-m|ym|Ym",

    triggerCls: Ext.baseCSSPrefix + 'form-date-trigger',

    componentCls: Ext.baseCSSPrefix + 'form-field-date',

    matchFieldWidth: false,

    useStrict: undefined,

    invalidText : "{0} 是无效的月份 - 必须符合格式 {1}",

    startDay: new Date(),

    initComponent: function () {
        var me = this;

        me.disabledDatesRE = null;

        me.callParent();
    },

    initValue: function () {
        var me = this,
            value = me.value;

        if (Ext.isString(value)) {
            me.value = me.rawToValue(value);
            me.rawDate = me.value;
            me.rawDateText = me.parseMonth(me.value);
        } else {
            me.value = value || null;
            me.rawDate = me.value;
            me.rawDateText = me.value ? me.parseMonth(me.value) : '';
        }
        if (me.value)
            me.startDay = me.value;
        me.callParent();
    },

    rawToValue: function (rawValue) {
        return Ext.Date.parse(rawValue, this.format) || this.parseMonth(rawValue) || null;
    },

    valueToRaw: function (value) {
        return this.formatDate(value);
    },

    formatDate: function (date) {
        return Ext.isDate(date) ? Ext.Date.dateFormat(date, this.format) : date;
    },
    createPicker: function () {
        var me = this,
            format = Ext.String.format;

        return Ext.create('Ext.picker.Month', {
            id: me.id + '-picker',
            pickerField: me,
            floating: true,
            preventRefocus: true,
            hidden: true,
            focusOnShow: true,
            shadow: false,
            showButtons: me.showButtons,
            listeners: {
                scope: me,
                cancelclick: me.onCancelClick,
                okclick: me.onOkClick,
                yeardblclick: me.onOkClick,
                monthdblclick: me.onOkClick
            }
        });
    },

    onExpand: function () {
        this.picker.setValue(this.startDay);
    },

    setValue: function(v) {
        var me = this;

        me.lastValue = me.rawDateText;
        me.lastDate = me.rawDate;
        if (Ext.isDate(v)) {
            me.rawDate  = v;
            me.rawDateText = me.formatDate(v);
        }
        else {
            me.rawDate = me.rawToValue(v);
            me.rawDateText = me.formatDate(v);
            if (me.rawDate === v) {
                me.rawDate = null;
                me.rawDateText = '';
            }
        }
        me.callParent(arguments);
    },
    getValue: function() {
        return this.rawDate || null;
    },
    getSubmitValue: function() {
        var format = this.submitFormat || this.format,
            value = this.rawDate;

        return value ? Ext.Date.format(value, format) : '';
    },

    setRawValue: function(value) {
        var me = this;

        me.callParent([value]);

        me.rawDate = Ext.isDate(value) ? value : me.rawToValue(value);
        me.rawDateText = this.formatDate(value);
    },

    getErrors: function(value) {
        value = arguments.length > 0 ? value : this.formatDate(this.processRawValue(this.getRawValue()));
        var me = this,
            format = Ext.String.format,
            errors = me.callParent([value]),
            svalue;
        if (value === null || value.length < 1) { // if it's blank and textfield didn't flag it then it's valid
            return errors;
        }

        svalue = value;
        value = me.parseMonth(value);
        if (!value) {
            errors.push(format(me.invalidText, svalue, Ext.Date.unescapeFormat(me.format)));
            return errors;
        }
        return errors;
    },

    parseMonth: function(value) {
        if(!value || Ext.isDate(value)){
            return value;
        }
        var me = this,
            val = me.safeParse(value, me.format),
            altFormats = me.altFormats,
            altFormatsArray = me.altFormatsArray,
            i = 0,
            len;

        if (!val && altFormats) {
            altFormatsArray = altFormatsArray || altFormats.split('|');
            len = altFormatsArray.length;
            for (; i < len && !val; ++i) {
                val = me.safeParse(value, altFormatsArray[i]);
            }
        }
        return val;
    },

    safeParse : function(value, format) {
        var me = this,
            utilDate = Ext.Date,
            result = null,
            strict = me.useStrict,
            parsedDate;

        // set time to 12 noon, then clear the time
        parsedDate = utilDate.parse(value, format, strict);
        return parsedDate;
    },
    checkChange: function() {
        var me = this,
            newVal, oldVal, lastDate;

        if (!me.suspendCheckChange) {
            newVal = me.getRawValue();
            oldVal = me.lastValue;
            lastDate = me.lastDate;

            if (!me.destroyed && me.didValueChange(newVal, oldVal)) {
                me.rawDate = me.rawToValue(newVal);
                me.rawDateText = me.formatDate(newVal);
                me.lastValue = newVal;
                me.lastDate = me.rawDate;
                me.fireEvent('change', me, me.getValue(), lastDate);
                me.onChange(newVal, oldVal);
            }
        }
    },

    onOkClick: function (picker, value) {
        var me = this,
            month = value[0],
            year = value[1],
            date = new Date(year, month, 1);
        me.startDay = date;
        me.setValue(date);
        this.picker.hide();
        //this.blur();
    },

    onCancelClick: function () {
        this.picker.hide();
        //this.blur();
    },

    onBlur: function(e) {
        var me = this,
            v = me.rawToValue(me.getRawValue());

        if (v === '' || Ext.isDate(v)) {
            me.setValue(v);
        }
        me.callParent([e]);
    }

});

Ext.define('Ext.vcf.CheckboxField', {
    extend : 'Ext.form.field.Checkbox',
    xtype : 'chkfield',
    trueValue : '1',
    falseValue : '0',
    initComponent : function() {
        this.inputValue = this.trueValue;
        this.uncheckedValue = this.falseValue;
        this.callParent(arguments);
    }
});

Ext.define('Ext.vcf.ChkGroup' , {
    extend : 'Ext.form.CheckboxGroup',
    xtype : 'chkgroup',
    delimiter : ',',
    /**
     * 枚举项。格式：1#男+2#女
     */
    enumData : '',
    initComponent : function() {
        this.items = this.getEnumData();
        this.callParent(arguments);
    },
    getSubmitValue: function() {
        return this.getValue();
    },
    getEnumData : function() {
        var r = [];
        if (this.enumData!=null && this.enumData!='') {
            var newData = this.enumData+'+';
            var ipos = newData.indexOf('+');
            while (ipos!=-1) {
                var subData = newData.substring(0,ipos);
                newData = newData.substring(ipos+1,newData.length);
                var ifieldpos = subData.indexOf('#');
                var f = {name:this.name};
                if (ifieldpos!=-1) {
                    f.inputValue = subData.substring(0,ifieldpos);
                    f.boxLabel = subData.substring(ifieldpos+1,subData.length);
                } else {
                    f.inputValue = subData;
                    f.boxLabel = '';
                }
                if (f.inputValue==this.value) {
                    f.checked = true;
                }
                r.push(f);
                ipos = newData.indexOf('+');
            }
        }
        return r;
    }
//返回值形式可以自定义，重载getvalue方法和setvalue方法
});

Ext.define('Ext.vcf.RadioField' , {
    extend : 'Ext.form.RadioGroup',
    xtype : 'rdofield',
    /**
     * 枚举项。格式：1#男+2#女
     */
    enumData : '',
    simpleValue : true,
    initComponent : function() {
        this.items = this.getEnumData();
        this.callParent(arguments);
    },
    setValue: function(value) {
        var items = this.items,
            cbValue, cmp, formId, radios, i, len, name;
        Ext.suspendLayouts();
        if (this.simpleValue) {
            for (i = 0 , len = items.length; i < len; ++i) {
                cmp = items.items[i];
                if (cmp.inputValue == value) {
                    cmp.setValue(true);
                    break;
                }
            }
        } else if (Ext.isObject(value)) {
            cmp = items.first();
            formId = cmp ? cmp.getFormId() : null;
            for (name in value) {
                cbValue = value[name];
                radios = Ext.form.RadioManager.getWithValue(name, cbValue, formId).items;
                len = radios.length;
                for (i = 0; i < len; ++i) {
                    radios[i].setValue(true);
                }
            }
        }
        Ext.resumeLayouts(true);
        return this;
    },
    getSubmitValue: function() {
        return this.getValue();
    },
    getEnumData : function() {
        var r = [];
        if (this.enumData!=null && this.enumData!='') {
            var newData = this.enumData+'+';
            var ipos = newData.indexOf('+');
            while (ipos!=-1) {
                var subData = newData.substring(0,ipos);
                newData = newData.substring(ipos+1,newData.length);
                var ifieldpos = subData.indexOf('#');
                var f = {name:this.name};
                if (ifieldpos!=-1) {
                    f.inputValue = subData.substring(0,ifieldpos);
                    f.boxLabel = subData.substring(ifieldpos+1,subData.length);
                } else {
                    f.inputValue = subData;
                    f.boxLabel = '';
                }
                if (f.inputValue==this.value) {
                    f.checked = true;
                }
                r.push(f);
                ipos = newData.indexOf('+');
            }
        }
        return r;
    }

});

Ext.define('Ext.vcf.FormPanel', {
    extend: 'Ext.form.Panel',
    xtype: 'inputpanel',
    layout: 'column',
    userDefined: false,
    fieldDefaults: {
        labelAlign: 'left',
        labelWidth: 80,
        msgTarget: 'side'
    },
    initComponent: function() {
        this.callParent(arguments);
        this.ajaxLoaded=false;
        if (this.userDefined) {
            this.initUI();
        } else {
            this.ajaxLoaded = true;
        }
    },
    isAjaxLoaded: function () {
        return this.ajaxLoaded;
    },
    getDictionaryValues: function () {
        var me=this,
            fields=me.form.getFields(),
        values = {};
        for (var i = 0; i < fields.getCount(); i++) {
            var field = fields.get(i);
            if (field.source && field.source!='') {
                var source = field.source.toLowerCase(),
                    node = field.getNode();
                if (node) {
                    values[source + '_id'] = node.get('id');
                    values[source + '_code'] = node.get('code');
                    values[source + '_name'] = node.get('name');
                } else {
                    values[source + '_id'] = '';
                    values[source + '_code'] = '';
                    values[source + '_name'] = '';
                }
            } else {
                values[field.getName()]=field.getSubmitValue();
            }
        }
        return values;
    }
});

Ext.define('Ext.vcf.QueryPanel', {
    extend: 'Ext.vcf.FormPanel',
    xtype: 'querypanel',
    /**
     * 状态关键字state是否放进查询条件
     */
    contain_state: false,
    initComponent: function() {
        this.callParent();
    },
    getQuery : function () {
        var me=this,
            fields = me.form.getFields(),
            rules = [];
        for (var i = 0; i < fields.getCount(); i++) {
            var field = fields.get(i);
            if (me.contain_state || field.getName()!='state') {
                if (!containsArrayNew(rules,field.getName(),'field')) {
                    var meta = {
                        field: field.getName(),
                        op: field.logic || '=',
                        data: field.getSubmitValue(),
                        type: field.data_type || 'string'
                    };
                    rules.push(meta);
                }
            }
        }
        return rules;
    },
    /**
     * 获取Json格式的条件参数
     */
    getQueryString : function () {
        return Ext.encode(this.getQuery());
    },
    getState : function () {
        var me=this,
            field = me.form.findField('state');
        if (field) {
            return field.getSubmitValue();
        } else {
            return '';
        }
    },
    /**
     * 获取条件参数对象，querystr作为条件对象值
     * @returns {*}
     */
    getQueryParams : function () {
        var me=this;
        if (me.contain_state) {
            return {querystr: me.getQueryString()};
        } else {
            return {querystr: me.getQueryString(), state: me.getState()};
        }
    },
    /**
     * 获取Request请求参数URL字串
     * @returns {string}
     */
    getReportParams : function () {
        var me=this,
            fields = me.form.getFields(),
            params = '';
        for (var i = 0; i < fields.getCount(); i++) {
            var field = fields.get(i);
            if (me.contain_state || field.getName()!='state') {
                params += '&'+field.getName()+'='+field.getSubmitValue();
            }
        }
        return params;
    }
});

Ext.define('Ext.vcf.ColumnModel', {
    extend: 'Ext.grid.ColumnModel',
    xtype: 'clmodel',
    setEnumData: function(col, val) {
        this.config[col].enumData = val;
    },
    getEnumData: function(col) {
        if (!this.config[col].enumData) {
            return '';
        }
        return this.config[col].enumData;
    }
});

// Ext.vcf.TableGrid = function() {
//     Ext.vcf.TableGrid.superclass.constructor.apply(this, arguments);
// };

Ext.define('Ext.vcf.TableGrid', {
    extend: 'Ext.grid.Panel',
    xtype: 'tablegrid',
    //是否拥有复选框
    isChecked: false,
    //是否只允许复选框选中
    checkOnly: false,
    //是否启用分页
    isPaged: true,
    //是否启用序号列
    isRowNumber: true,
    //预定义的grid列
    /**@columnBase: [],*/
    //预定义的字段列
    /**@fieldBase: [],*/
    //使用自定义的远程调用地址
    url: '',
    //默认查询参数
    params: {},
    //默认50条记录一页的页参数
    pageSize: 50,
    //是否读取界面视图配置
    userDefined: false,
    //创建时加载数据
    autoLoad: true,
    scrollable: true,
    region: 'center',
    margins: '3 3 3 3',
    implicitIncludes: true,
    loadMask: {
        msg: '正在加载表格数据,请稍等...'
    },
    columnLines: true,
    stripeRows: true,
    frame: true,
    constructor: function (config) {
        var me = this;
        me.callParent([config]);
    },
    initComponent: function() {
        var me = this,
            columnBase = me.columnBase || [];
        me.fieldBase = me.fieldBase || [];
        if (typeof me.columns == 'undefined') {
            if (columnBase.length == 0) {
                if (isRowNumber) {
                    columnBase.push({xtype: 'rownumberer', width: 40});
                }
                if (me.isChecked) {
                    var sm = Ext.create('Ext.selection.CheckboxModel', {checkOnly: this.checkOnly, injectCheckbox: 1});
                    me.selModel = sm;
                }
            }
            // var cm = Ext.create('Ext.vcf.ColumnModel',{columns:columnBase});
            me.columns = me.columnBase = columnBase;
        }

        if (typeof me.store == 'undefined') {
            var model = me.model || Ext.create('Ext.data.Model', {
                fields: me.fieldBase,
                validators : me.validators,
                idProperty : me.keyDataIndex || 'id'
            });
            /**
             * 数据存储
             */
            me.store = Ext.create('Ext.data.Store',{
                model : model,
                proxy : {
                    type : 'ajax',
                    url : me.url,
                    actionMethods: {
                        read: 'POST' // Store设置请求的方法，与Ajax请求有区别
                    },
                    reader : {
                        type : 'json',
                        totalProperty: 'total',
                        rootProperty : 'data',
                        implicitIncludes: me.implicitIncludes
                    }
                }
            });

        }

        if (me.isPaged && me.cls!="x-grid-inner-locked") {
            var pagesize_combo = Ext.create('Ext.form.field.ComboBox',{
                name: 'pagesize',
                triggerAction: 'all',
                lazyRender: true,
                mode: 'local',
                store: new Ext.data.ArrayStore({
                    fields: ['value', 'text'],
                    data: [
                        [10, '10条/页'],
                        [20, '20条/页'],
                        [50, '50条/页'],
                        [100, '100条/页'],
                        [250, '250条/页'],
                        [500, '500条/页'],
                        [5000, '5000条/页']
                    ]
                }),
                valueField: 'value',
                displayField: 'text',
                value: me.pageSize,
                editable: false,
                width: 100
            });
            pagesize_combo.on("select", function(comboBox) {
                var number = parseInt(comboBox.getValue());
                me.store.setPageSize(number);
                me.store.currentPage =1;
                bbar.doRefresh();
            });

            me.store.pageSize = me.pageSize;
            var bbar = Ext.create('Ext.toolbar.Paging',{
                // pageSize: number,
                displayInfo: true,
                // displayMsg: '显示{0}条到{1}条,共{2}条',
                plugins: 'ux-progressbarpager', // 分页进度条
                emptyMsg: "没有符合条件的记录",
                items: ['-', '&nbsp;&nbsp;', pagesize_combo]
            });
            me.bbar = bbar;
        }
        me.ajaxLoaded=false;
        //this.addEvents('loadrelation', this);
        me.callParent(arguments);
        if (!me.userDefined) {
            if (me.autoLoad) {
                me.queryData(me.params);
            }
            me.ajaxLoaded=true;
        } else {
            me.initUI();
            me.autoQuery = me.autoLoad;
            me.autoLoad= false;
        }
    },
    isAjaxLoaded: function () {
        return this.ajaxLoaded;
    },
    /**
     * 初始化加载数据
     * params直接替换到this.params
     */
    queryData: function(params) {
        this.params = params;
        Ext.apply(this.store.proxy.extraParams, this.params);
        this.store.load(this.params);
    },
    /**
     * 重新加载数据
     * params作为this.params的子集或追加属性，更新或追加到this.params
     */
    requeryData: function(params) {
        mergeObj(this.params, params);
        Ext.apply(this.store.proxy.extraParams, this.params);
        this.store.reload();
    },
    requery: function (params) {
        this.requeryData(params);
    }
});

/**
 * 目前只用于界面视图
 */
Ext.define('Ext.vcf.chkCellSelModel', {
    extend: 'Ext.selection.CheckboxModel',
    contain_field: '',

    constructor: function() {
        var me = this;
        me.callParent(arguments);

        this.contains = new Ext.util.MixedCollection(false, function(o) {
            return o.id;
        });

    },


    initEvents: function() {
        Ext.vcf.ChkCellSelModel.superclass.initEvents.call(this);
        this.grid.on('render', function() {
            var view = this.grid.getView();
            view.mainBody.on('mousedown', this.onMouseDown, this);
            Ext.fly(view.innerHd).on('mousedown', this.onHdMouseDown, this);

        }, this);
    },

    onRefresh: function() {
        var ds = this.grid.store,
            index;
        var s = this.getContains();
        this.clearContains(true);
        for (var i = 0, len = s.length; i < len; i++) {
            var r = s[i];
            if ((index = ds.indexOfId(r.id)) != -1) {
                this.containRow(index, true);
            }
        }
    },

    onViewChange: function() {
        this.clearSelections(true);
        this.clearContains(true);
    },


    getCount: function() {
        return this.contains.length;
    },


    onMouseDown: function(e, t) {
        if (e.button === 0 && t.className == 'x-grid3-row-checker') {
            e.stopEvent();
            var row = e.getTarget('.x-grid3-row');
            if (row) {
                var setContain = function(comp, v, i) {
                    if (comp.contain_field != '') {
                        var record = comp.grid.store.getAt(i);
                        record.set(comp.contain_field, v);
                    }
                };
                var index = row.rowIndex;
                if (this.isContained(index)) {
                    setContain(this, 0, index);
                    this.decontainRow(index, false);
                } else {
                    setContain(this, 1, index);
                    this.containRow(index, true, false);
                }
            }
        }
    },


    onHdMouseDown: function(e, t) {
        if (t.className == 'x-grid3-hd-checker') {
            e.stopEvent();
            var hd = Ext.fly(t.parentNode);
            var isChecked = hd.hasClass('x-grid3-hd-checker-on');
            var setContainAll = function(comp, v) {
                if (comp.contain_field != '') {
                    for (var i = 0; i < comp.grid.store.getCount(); i++) {
                        var record = comp.grid.store.getAt(i);
                        record.set(comp.contain_field, v);
                    }
                }
            };
            if (isChecked) {
                hd.removeClass('x-grid3-hd-checker-on');
                setContainAll(this, 0);
                this.clearContains();
            } else {
                hd.addClass('x-grid3-hd-checker-on');
                setContainAll(this, 1);
                this.containAll();
            }
        }
    },

    isContained: function(index) {
        var r = Ext.isNumber(index) ? this.grid.store.getAt(index) : index;
        return (r && this.contains.key(r.id) ? true : false);
    },

    containRow: function(index, keepExisting, preventViewNotify) {
        if (this.isLocked() || (index < 0 || index >= this.grid.store.getCount()) || (keepExisting && this.isContained(index))) {
            return;
        }
        var r = this.grid.store.getAt(index);
        if (r) {
            if (!keepExisting) {
                this.clearContains();
            }
            this.contains.add(r);
            this.last = this.lastActive = index;
            if (!preventViewNotify) {
                this.grid.getView().onRowSelect(index);
            }
        }
    },


    decontainRow: function(index, preventViewNotify) {
        if (this.isLocked()) {
            return;
        }
        if (this.last == index) {
            this.last = false;
        }
        if (this.lastActive == index) {
            this.lastActive = false;
        }
        var r = this.grid.store.getAt(index);
        if (r) {
            this.contains.remove(r);
            if (!preventViewNotify) {
                this.grid.getView().onRowDeselect(index);
            }
        }
    },

    getContains: function() {
        return [].concat(this.contains.items);
    },


    getContained: function() {
        return this.contains.itemAt(0);
    },


    each: function(fn, scope) {
        var s = this.getContains();
        for (var i = 0, len = s.length; i < len; i++) {
            if (fn.call(scope || this, s[i], i) === false) {
                return false;
            }
        }
        return true;
    },


    clearContains: function(fast) {
        if (this.isLocked()) {
            return;
        }
        if (fast !== true) {
            var ds = this.grid.store;
            var s = this.contains;
            s.each(function(r) {
                this.decontainRow(ds.indexOfId(r.id));
            }, this);
            s.clear();
        } else {
            this.contains.clear();
        }
        this.last = false;
    },

    containAll: function() {
        this.contains.clear();
        for (var i = 0, len = this.grid.store.getCount(); i < len; i++) {
            this.containRow(i, true);
        }
    },


    renderer: function(v, p, record) {
        return '<div class="x-grid3-row-checker">&#160;</div>';
    }
});

Ext.define('Ext.vcf.EditorColumnModel', {
    extend: 'Ext.grid.header.Container',
    constructor: function(grid, store, columns) {
        var me = this,
            headerCtCfg = columns || [];
        me.grid = grid;
        me.store = store;

        //追加editor行级绑定
        if (Ext.isArray(headerCtCfg)) {
            headerCtCfg = {
                items: headerCtCfg
            };
        }
        if (headerCtCfg.items) {
            var i,len,items,item;
            items = headerCtCfg.items;
            for (i=0, len=items.length; i<len; i++) {
                item = items[i];
                if (!item.isGroupHeader && item.editable) {
                    //允许编辑的列才绑定
                    item.getEditor = me.getCellEditor.bind(me,item);
                }
            }
        }
        headerCtCfg.isRootHeader = true;
        me.callParent([headerCtCfg]);
    },
    getCellEditor: function(item, record){
        var column = item;
        return this.grid.getCellEditor(record,column);
    }
});

Ext.define('Ext.vcf.EditorTableGrid', {
    extend: 'Ext.vcf.TableGrid',
    xtype: 'editortablegrid',
    isChecked : false,
    checkOnly : false,
    isPaged : true,
    /**
     * 是否按行记录定义编辑框类型
     */
    isRowEditor : false,
    /**
     * 主键字段名称
     */
    keyDataIndex : undefined,
    /**
     * 判断记录是否可编辑的字段名，和columnBase里的editable属性配合使用
     */
    editDataIndex : '',
    /**
     * @columnBase : [],
     */
    /**
     * @fieldBase : [],
     */
    url : '',
    params : {},
    pageSize : 50,
    userDefined : false,
    clicksToEdit : 1,
    autoScroll: true,
    region: 'center',
    margins: '3 3 3 3',
    implicitIncludes: false,
    loadMask: {
        msg: '正在加载表格数据,请稍等...'
    },
    stripeRows: true,
    frame: true,
    initComponent: function() {
        var me = this,
            columnBase = me.columnBase || [];
        me.fieldBase = me.fieldBase || [];
        me.editing = Ext.create('Ext.grid.plugin.CellEditing',{
            clicksToEdit: me.clicksToEdit
        });
        me.plugins = me.plugins || [];
        me.plugins.push(me.editing);

        if (typeof me.columns == 'undefined') {
            if (columnBase.length == 0) {
                columnBase.push({xtype:'rownumberer'});
                if (me.isChecked) {
                    var sm = Ext.create('Ext.selection.CheckboxModel',{ checkOnly: this.checkOnly });
                    me.selModel = sm;
                }
            }
            if (!me.isRowEditor) {
                me.columns = me.columnBase = columnBase;
            } else {
                me.editors = {
                    'string': new Ext.grid.CellEditor({
                        field: new Ext.form.field.Text({
                            selectOnFocus: true,
                            allowBlank: false
                        })
                    }),
                    'object': new Ext.grid.CellEditor({
                        field: new Ext.form.field.Text({selectOnFocus: true, allowBlank: false})
                    }),
                    'combox': new Ext.grid.CellEditor({
                        field: new Ext.vcf.ComboField({selectOnFocus: true, allowBlank: false})
                    }),
                    'tree': new Ext.grid.CellEditor({
                        field: new Ext.vcf.TreeField({selectOnFocus: true, allowBlank: false})
                    }),
                    'date': new Ext.grid.CellEditor({
                        field: new Ext.form.field.Date({selectOnFocus: true, allowBlank: false})
                    }),
                    'number': new Ext.grid.CellEditor({
                        field: new Ext.form.field.Number({selectOnFocus: true, allowBlank: false, style: 'text-align:left;'})
                    }),
                    'boolean': new Ext.grid.CellEditor({
                        field: new Ext.form.field.ComboBox({
                            editable: false,
                            store: [
                                [
                                    true,
                                    '是'
                                ],
                                [
                                    false,
                                    '否'
                                ]
                            ]
                        })
                    })
                };

                me.columnBase = columnBase;
                me.columns = new Ext.vcf.EditorColumnModel(me, me.store, me.columnBase);
            }
        }

        me.callParent(arguments);
    },
    getCellEditor: function(record, column) {
        var p = record,
            n = Ext.isEmpty(this.editDataIndex) ? 1 : p.data[this.editDataIndex],
            val = record.data[column.dataIndex];
        if (n == 1 || n == true) {
            if (Ext.isDate(val)) {
                return this.editors.date;
            } else if (typeof val == 'number') {
                return this.editors.number;
            } else if (typeof val == 'boolean') {
                return this.editors['boolean'];
            } else if (typeof val == 'object') {
                return this.editors['object'];
            } else {
                return this.editors.string;
            }
        }
    }

});

Ext.define('Ext.vcf.CustomNumberField', {
    xtype : 'custnumberfield',
    requires: [
    ],
    alias : 'widget.CustNumberField',
    extend : 'Ext.form.field.Text',
    alternateClassName: 'Ext.vcf.CustomNumberField',

    fieldLabel : '',

    fieldStyle : {
        "text-align":"right"
    },

    minValue: Number.NEGATIVE_INFINITY,

    maxValue: Number.MAX_VALUE,

    minText : "数字最小为 {0}",

    maxText : "数字最大为 {0}",

    nanText : "{0} 不是一个有效数字",

    /**
     * 默认币种标志
     */
    prefixChar : '',

    /**
     * 后缀字符
     */
    suffixChar : '',

    /**
     * 数字格式
     */
    numberFormatter : '',

    /**
     * 是否使用红字提示
     */
    numberColored : true,

    /**
     * 小数允许
     */
    allowDecimals : false,

    /**
     * 负数允许
     */
    allowNegative : true,

    baseChars : "0123456789",

    decimalSeparator : '.',

    /**
     * 保留小数位数
     */
    decimalPrecision : 2,

    value : 0,

    _displayVal : 0,

    _submitVal : 0,

    selectOnFocus : true,

    listeners: {
        'focus': function(_this, event, eOpts) {
            if (this.editable) {
                this.value = this._submitVal;
                this.setRawValue(this.value);
                this.focus(true, 100);
            }
        }
    },

    initComponent: function() {
        this.setValue(this.value);
        this.callParent(arguments);
    },

    setValue : function(v) {
        var v = this.parseValue(v);
        if (v) {
        } else {
            v = 0;
        }
        this._submitVal = this.fixPrecision(v);
        if (this.numberFormatter != '') {
            this._displayVal = this.prefixChar + ' ' + Ext.util.Format.number(this._submitVal, this.numberFormatter) + this.suffixChar;
        } else {
            this._displayVal = this.prefixChar + ' ' + this._submitVal + this.suffixChar;
        }

        arguments[0] = this._displayVal;
        this.callParent(arguments);
        if (this.numberColored) {
            if (this._submitVal >= 0) {
                this.setFieldStyle({
                    "text-align":"right",
                    "color" : 'BLACK'
                });
            } else {
                this.setFieldStyle({
                    "text-align":"right",
                    "color" : 'RED'
                });
            }
        }
        this.value =this._submitVal;
        return this.value;
    },

    beforeBlur : function() {
        var v = this.parseValue(this.getRawValue());
        this.setValue(v);
    },

    validateValue : function(value) {
        value = this._submitVal == '' ? 0 : this._submitVal;
        if(value.length < 1){ // if it's blank and textfield didn't flag it then it's valid
            return true;
        }
        value = String(value).replace(this.decimalSeparator, ".");
        if(isNaN(value)){
            this.markInvalid(String.format(this.nanText, value));
            return false;
        }
        var num = this.parseValue(value);
        if(num < this.minValue){
            this.markInvalid(String.format(this.minText, this.minValue));
            return false;
        }
        if(num > this.maxValue){
            this.markInvalid(String.format(this.maxText, this.maxValue));
            return false;
        }
        return true;
    },

    parseValue : function(value){
        value = parseFloat(String(value).replace(this.decimalSeparator, "."));
        return isNaN(value) ? '' : value;
    },

    fixPrecision : function(value){
        var nan = isNaN(value);
        if(!this.allowDecimals || this.decimalPrecision == -1 || nan || !value){
            return nan ? '' : value;
        }
        return parseFloat(parseFloat(value).toFixed(this.decimalPrecision));
    },

    initEvents : function() {
        var allowed = this.baseChars+'';
        var pressAllowed = this.baseChars+'';
        if (this.prefixChar != '') {
            allowed += this.prefixChar;
        }
        if (this.suffixChar != '') {
            allowed += this.suffixChar;
        }
        if (this.numberFormatter != '') {
            allowed += ',:';
        }
        if(this.allowDecimals){
            allowed += this.decimalSeparator;
            pressAllowed += this.decimalSeparator;;
        }
        if(this.allowNegative){
            allowed += "-";
            pressAllowed += '-';
        }
        this.stripCharsRe = new RegExp('[^'+allowed+']', 'gi');
        var keyPress = function(e){
            var k = e.getKey();
            if(!Ext.isIE && (e.isSpecialKey())){
                return;
            }
            var c = e.getCharCode();
            if(pressAllowed.indexOf(String.fromCharCode(c)) === -1){
                e.stopEvent();
            }
        };
        this.el.on("keypress", keyPress, this);
        this.callParent(arguments);
    },

    constructor: function (config) {
        this.callParent(arguments);
    },

    getSubmitValue : function() {
        return this._submitVal == 0 ? 0 : this._submitVal;
    },
    getValue : function() {
        this.value = this._submitVal == 0 ? 0 : this._submitVal;
        return this.value;
    }
});

Ext.define('Ext.vcf.MoneyField', {
    extend: 'Ext.vcf.CustomNumberField',
    xtype: 'moneyfield',
    currencyChar : '¥',
    moneyFormatter: '0,000.00',
    allowDecimals: true,
    minText : "金额最小为 {0}",
    maxText : "金额最大为 {0}",
    nanText : "{0} 不是一个有效金额",
    initComponent : function() {
        Ext.apply(this, {
            prefixChar: this.currencyChar,
            suffixChar: '',
            numberFormatter: this.moneyFormatter
        });
        this.callParent(arguments);
    }
});


Ext.define('Ext.vcf.PercentField', {
    extend: 'Ext.vcf.CustomNumberField',
    xtype: 'percentfield',
    allowDecimals: true,
    allowNegative: false,
    percentChar : '%',
    initComponent : function() {
        Ext.apply(this, {
            prefixChar: '',
            suffixChar : this.percentChar
        });
        this.callParent(arguments);
    }
});

/**
 * 定位控件
 * @author eonook
 */
Ext.define('Ext.vcf.LocateField', {
    extend: 'Ext.form.field.Text',
    xtype: 'locatefield',
    triggers: {
        clear: {
            weight: 0,
            cls: Ext.baseCSSPrefix + 'form-clear-trigger',
            hidden: true,
            handler: 'onClearClick',
            scope: 'this'
        },
        search: {
            weight: 1,
            cls: Ext.baseCSSPrefix + 'form-search-trigger',
            handler: 'onSearchClick',
            scope: 'this'
        }
    },
    hasSearch: false,
    paramName: 'query',
    initComponent: function() {
        var me = this,
            proxy;
        me.callParent(arguments);
        me.on('specialkey', function(f, e) {
            if (e.getKey() == e.ENTER) {
                me.onSearchClick();
            }
        });
    },
    onClearClick: function() {
        var me = this;
        me.setValue('');
        me.getTrigger('clear').hide();
        me.updateLayout();
    },
    onSearchClick: function() {
        var me = this,
            value = me.getValue();
        me.fireEvent('locate',me, value);
        me.getTrigger('clear').show();
        me.updateLayout();
    }
});

/**
 * 图标控件
 * @author eonook
 */
Ext.define('Ext.vcf.IconField',{
    extend : 'Ext.form.field.ComboBox',
    alias : 'vcf.iconfield',
    xtype : 'iconfield',
    validateOnBlur: false,
    forceSelection : true,
    triggerAction : 'all',
    initComponent:function() {
        var me = this;

        Ext.apply(this, {
            store: new Ext.data.ArrayStore({
                autoLoad : true,
                fields : ['icons'],
                proxy : {
                    type : 'ajax',
                    url : '/manage/icons_list.htm',
                    reader : {
                        type : 'json'
                    }
                }
            }),
            queryMode: 'local',
            displayField : 'icons',
            valueField : 'icons',
            iconClsField : 'icons',
            tpl: Ext.create('Ext.XTemplate', '<table><tr><tpl for="."><td class="x-boundlist-item :qtip={icons}" ><img width=16 height=16 style="vertical-align: middle;" class="{icons}" /><span>{icons}</span></td><tpl if="xindex % 2 === 0"></tr><tr></tpl></tpl></tr></table>'),
            // 模版显示在文本框内
            displayTpl: Ext.create('Ext.XTemplate', '<tpl for=".">', '{icons}','</tpl>')

        });

        me.callParent(arguments);

    },

    onRender:function(ct, position) {
        var me = this;
        me.callParent(arguments);

        me.inputWrap.applyStyles({position:'relative'});
        me.inputEl.addCls('ux-icon-combo-input');


        me.icon = Ext.core.DomHelper.append(me.inputEl.up('div.x-form-text-wrap'), {
            tag: 'div', style:'position:absolute'
        });

    },

    onBeforeSelect: function(list, record, recordIndex) {
        this.setIconCls(record.get('icons'));
    },

    setIconCls:function(val) {
        var me = this;
        if (me.icon) me.icon.className = 'ux-icon-combo-icon ' + val;
    },

    setValue: function(value) {
        var me = this;
        me.callParent(arguments);
        me.setIconCls(value);
    }
});

Ext.override(Ext.container.Viewport,{
    //是否触发ajax的afterloadui事件
    loadOnShow: false,
    initComponent: function() {
        var me = this;
        me.callParent(arguments);
        me.ajaxLoaded = false;
        me.toolLoaded = false;
        me.toolLoading();

        if (me.loadOnShow)
            me.triggerLoading();
    },
    triggerLoading: function () {
        if (!this.ajaxLoaded) {
            var me = this,
                comps = me.query('inputpanel, tablegrid');
            var time = setInterval(function () {
                var isLoaded = true;
                for (var i = 0, len = comps.length; i < len; i++) {
                    if (comps[i].isAjaxLoaded && !comps[i].isAjaxLoaded()) {
                        isLoaded = false;
                        break;
                    }
                }
                if (isLoaded) {
                    me.ajaxLoaded = true;
                    clearInterval(time);
                    if (me.hasListeners.afterloadui) {
                        me.fireEvent('afterloadui', me);
                    }
                }
            },100);
        }
    },
    toolLoading: function () {
        if (!this.toolLoaded) {
            var me = this,
                pnls = me.query('form,grid'),
                dockPnl;  //根据菜单配置查找到的工具栏所在panel
            me.toolLoaded = true;
            if (typeof main_action!='undefined') {
                if (main_action.toolbarIndex==0) {
                    dockPnl = me.child('panel');
                } else {
                    for (var i = 0; i < pnls.length; i++) {
                        if (i + 1 == main_action.toolbarIndex) {
                            dockPnl = pnls[i];
                        }
                    }
                }
                if (dockPnl) {
                    var tbar = dockPnl.getDockedItems('toolbar[dock="top"]');
                    //对应panel是否已设置工具栏
                    if (!Ext.isEmpty(main_action.toolbar)) {
                        if (Ext.isEmpty(tbar)) {
                            dockPnl.addDocked([{
                                xtype: 'toolbar',
                                dock: 'top',
                                items: main_action.toolbar
                            }]);
                        } else {
                            tbar[0].add(main_action.toolbar);
                        }
                    }
                }
            }
        }
    }
});

Ext.define('Ext.vcf.Window', {
    extend: 'Ext.window.Window',
    layout: 'fit',
    width: 400,
    autoHeight: true,
    resizable: true,
    draggable: true,
    closable: true,
    modal: true,
    closeAction: 'hide',
    // iconCls : 'page_addIcon',
    collapsible: true,
    titleCollapse: true,
    maximizable: true,
    buttonAlign: 'right',
    border: false,
    animCollapse: true,
    pageY: 20,
    //是否触发ajax的afterloadui事件
    loadOnShow: false,
    //animateTarget: Ext.getBody(),
    constrain: true,
    constructor: function() {
        Ext.vcf.Window.superclass.constructor.apply(this, arguments);
        if (!Ext.isIE8) {
            this.pageX = document.body.clientWidth / 2 - (this.width + 20) / 2;
            if (this.height>20) {
                this.pageY = (document.body.clientHeight - this.height) * 0.4;
            }
        }
    },
    initComponent: function() {
        this.callParent(arguments);
        this.ajaxLoaded = false;
    },
    triggerLoading: function () {
        if (!this.ajaxLoaded) {
            var me = this,
                comps = me.query('inputpanel, tablegrid');
            var time = setInterval(function () {
                var isLoaded = true;
                for (var i = 0, len = comps.length; i < len; i++) {
                    if (comps[i].isAjaxLoaded && !comps[i].isAjaxLoaded()) {
                        isLoaded = false;
                        break;
                    }
                }
                if (isLoaded) {
                    me.ajaxLoaded = true;
                    clearInterval(time);
                    if (me.hasListeners.afterloadui) {
                        me.fireEvent('afterloadui', me);
                    }
                }
            },100);
        }
    },
    onShow: function () {
        var me = this;
        me.callParent(arguments);

        if (me.loadOnShow)
            me.triggerLoading();
    }
});
