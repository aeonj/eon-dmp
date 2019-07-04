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
    border : true, // 面板边框
    useArrows : true, // 箭头节点图标
    autoScroll : true,
    animate : false,
    clearOnLoad : true,
    checkPropagation : 'both',
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
                    expanded: true
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
        if (!this.getRootNode().isLoaded()) {
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
        this.findChildNodesById(selnode.getPath(),isclick);
    },

    selectNodeByValue : function(value,isclick) {
        var selNode;
        if (typeof value == 'string') {
            if (this.isCodeAsValue){

            } else {
                selNode = this.store.getNodeById(value);
            }
        } else {
            selNode = value;
        }
        var treepath = selNode.getPath("id");
        this.findChildNodesById(treepath,isclick);
    },
    findChildNodesById : function(path, isclick) {
        var tree = this;
        // 展开路径,并在回调函数里面选择该节点
        this.expandPath(path, 'id', '/', function(bSucess, oLastNode) {
            if (typeof isclick != 'undefined' || !isclick) {
                tree.getSelectionModel().select(oLastNode);
            } else {
                tree.fireEvent('itemclick', tree,oLastNode);
            }
        });
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
    isDirect : false,
    forceSelection : true,
    typeAhead : true,
    triggerAction : 'all',
    initComponent : function() {
        //if (Ext.isDefined(this.name)){
        //    if (!Ext.isDefined(this.hiddenName)) {
        //        this.hiddenName = this.name;
        //    }
        //this.name = undefined;
        //}
        if (this.source=='') {
            if (typeof this.store == 'undefined') {
                this.store = new Ext.data.Store({
                    fields : ['value', 'text'],
                    data : this.getEnumData()
                });
            }
        } else {
            this.queryMode = 'remote';
            this.valueField = this.isCodeAsValue ? 'code' : 'id';
            this.displayField = 'name';
            this.store = new Ext.data.Store({
                fields : ['id','code','name'],
                url : this.url,
                source : this.source,
                condition : this.condition,
                isDirect : this.isDirect,
                proxy : {
                    type : 'ajax',
                    url : this.url,
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
        this.callParent(arguments);
        //隐藏项ID赋值
        if (!Ext.isIE) {
            if (typeof this.hiddenId=='undefined') {
                this.hiddenId = this.id+'_sub';
            }
        }
        if (this.queryMode == 'remote')
            this.store.reload();
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
    }


});

Ext.define('Ext.vcf.treefield', {
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
    fieldLabel : '下拉树',
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
    isRelation : false,
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
    mode : 'local',
    triggerAction : 'all',
    selectedClass : '',

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
        return this.store.getNodeById(code);
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
        if (combo.hiddenField) {
            combo.hiddenField.value = chkvalue;
        }
        //end

        var dataurl = '../platform/dictionary.do?reqCode=getEleInfoByIds';
        if (combo.isCodeAsValue){
            dataurl = '../platform/dictionary.do?reqCode=getEleInfoByCodes';
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
                        if (tn.getOwnerTree().isCodeAsValue){
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
            if (typeof v == "object") {
                me.node = v;
            } else {
                if (v!='') {
                    if (me.picker) {
                        me.node = me.getNodeById(v);
                    }
                }
            }
            if (v==null || v=='') {
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
                if (me.source=='ELE') {
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
            if (node==me.getPicker().getRootNode()) {
                value = '';
                text = '';
            } else {
                if (me.isCodeAsValue){
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
        }
        (oldvalue == null || oldvalue !== me.value) ? me.fireEvent('afterchange',
            me, me.value, oldvalue) : '';

        return me;
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
    layout: 'form',
    userDefined: false,
    fieldDefaults: {
        labelAlign: 'left',
        labelWidth: 80,
        msgTarget: 'side'
    },
    initComponent: function() {
        this.callParent();
        if (this.userDefined) {
            this.initUI();
        }
    }
});

Ext.define('Ext.vcf.QueryPanel', {
    extend: 'Ext.vcf.FormPanel',
    xtype: 'querypanel',
    initComponent: function() {
        this.callParent();
    },
    getQuery : function () {
        var me=this,
            fields = me.form.getFields(),
            rules = [];
        for (var i = 0; i < fields.getCount(); i++) {
            var field = fields.get(i);
            var meta = {field : field.getName(),op : field.logic, data : field.getValue()};
            rules.push(meta);
        }
        return rules;
    },
    getQueryString : function () {
        return Ext.encode(this.getQuery());
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
    //预定义的grid列
    columnBase: [],
    //预定义的字段列
    fieldBase: [],
    //使用自定义的远程调用地址
    url: '',
    //默认查询参数
    params: {},
    //默认50条记录一页的页参数
    pageSize: 50,
    //是否读取界面视图配置
    userDefined: false,
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
    initComponent: function() {
        var grid = this;

        if (typeof grid.columns == 'undefined') {
            if (grid.columnBase.length == 0) {
                grid.columnBase.push({xtype: 'rownumberer', width: 40});
                if (grid.isChecked) {
                    var sm = Ext.create('Ext.selection.CheckboxModel', {checkOnly: this.checkOnly, injectCheckbox: 1});
                    grid.selModel = sm;
                }
            }
            // var cm = Ext.create('Ext.vcf.ColumnModel',{columns:this.columnBase});
            grid.columns = grid.columnBase;
        }

        if (typeof grid.store == 'undefined') {
            grid.keyDataIndex = grid.keyDataIndex || 'id';
            if (typeof grid.model != 'undefined') {

            }
            var model = Ext.create('Ext.data.Model', {
                fields: grid.fieldBase,
                validators : grid.validators,
                idProperty : grid.keyDataIndex
            });
            /**
             * 数据存储
             */
            grid.store = Ext.create('Ext.data.Store',{
                model : grid.model || model,
                proxy : {
                    type : 'ajax',
                    url : this.url,
                    actionMethods: {
                        read: 'POST' // Store设置请求的方法，与Ajax请求有区别
                    },
                    reader : {
                        type : 'json',
                        totalProperty: 'records',
                        rootProperty : 'rows',
                        implicitIncludes: grid.implicitIncludes
                    }
                }
            });

        }

        if (grid.isPaged) {
            var pagesize_combo = Ext.create('Ext.form.field.ComboBox',{
                name: 'pagesize',
                typeAhead: true,
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
                value: grid.pageSize,
                editable: false,
                width: 100
            });
            pagesize_combo.on("select", function(comboBox) {
                var number = parseInt(comboBox.getValue());
                grid.store.setPageSize(number);
                grid.store.currentPage =1;
                bbar.doRefresh();
            });

            grid.store.pageSize = grid.pageSize;
            var bbar = Ext.create('Ext.toolbar.Paging',{
                // pageSize: number,
                displayInfo: true,
                // displayMsg: '显示{0}条到{1}条,共{2}条',
                plugins: 'ux-progressbarpager', // 分页进度条
                emptyMsg: "没有符合条件的记录",
                items: ['-', '&nbsp;&nbsp;', pagesize_combo]
            });
            grid.bbar = bbar;
        }

        this.callParent(arguments);
        //this.addEvents('loadrelation', this);
        if (!this.userDefined) {
            grid.store.load({
                params: grid.params
            });
        } else {
            grid.initUI();
        }
    },
    /**
     * 初始化加载数据
     * params直接替换到this.params
     */
    query: function(params) {
        this.params = params;
        Ext.apply(this.store.proxy.extraParams, this.params);
        this.store.load(this.params);
    },
    /**
     * 重新加载数据
     * params作为this.params的子集或追加属性，更新或追加到this.params
     */
    requery: function(params) {
        mergeObj(this.params, params);
        Ext.apply(this.store.proxy.extraParams, this.params);
        this.store.reload();
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
    columnBase : [],
    fieldBase : [],
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
        var grid = this;

        grid.editing = Ext.create('Ext.grid.plugin.CellEditing',{
            clicksToEdit: grid.clicksToEdit
        });
        grid.plugins = grid.plugins || [];
        grid.plugins.push(grid.editing);

        if (typeof this.columns == 'undefined') {
            if (this.columnBase.length == 0) {
                this.columnBase.push({xtype:'rownumberer'});
                if (this.isChecked) {
                    var sm = Ext.create('Ext.selection.CheckboxModel',{ checkOnly: this.checkOnly });
                    this.selModel = sm;
                }
            }
            if (!this.isRowEditor) {
                this.columns = this.columnBase;
            } else {
                this.editors = {
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

                this.columns = new Ext.vcf.EditorColumnModel(grid, grid.store, grid.columnBase);
            }
        }

        this.callParent(arguments);
        if (!this.userDefined) {
            grid.store.load(grid.params);
        } else {
            grid.initUI();
        }
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

//Ext.reg('editortablegrid', Ext.vcf.EditorTableGrid);

/*
Ext.apply(Ext.grid.GridPanel.prototype, {
    moveUp: function() {
        var d = this.getStore();
        var c = this.getColumnModel();
        var r = this.getSelectionModel().getSelections();
        var idx;
        if (r.length == 0) {
            Ext.Msg.alert('提示', '请选择记录进行操作！');
            return;
        }
        if (r.length == d.getCount())
            return;
        if (d.indexOf(r[0]) == 0)
            return;
        for (var i = 0; i < r.length; i++) {
            idx = d.indexOf(r[i]) - 1;
            d.remove(r[i]);
            d.insert(idx, r[i]);
        }
        this.getSelectionModel().selectRow(idx);
        this.reconfigure(d, c);
    },
    moveDown: function() {
        var d = this.getStore();
        var c = this.getColumnModel();
        var r = this.getSelectionModel().getSelections();
        var idx;
        var len = r.length;
        if (len == 0) {
            Ext.Msg.alert('提示', '请选择记录进行操作！');
            return;
        }
        if (len == d.getCount())
            return;
        if (d.indexOf(r[len - 1]) == d.getCount() - 1)
            return;
        for (var i = 0; i < len; i++) {
            idx = d.indexOf(r[len - i - 1]) + 1;
            d.remove(r[len - i - 1]);
            d.insert(idx, r[len - i - 1]);
        }
        this.getSelectionModel().selectRow(idx);
        this.reconfigure(d, c);
    }
});

Ext.apply(Ext.vcf.CustomNumberField.prototype, {
    validateValue: function(value) {
        if (!Ext.form.NumberField.superclass.validateValue.call(this, value)) {
            return false;
        }
        if (value.length < 1) {
            return true;
        }
        value = String(value).replace(this.decimalSeparator, ".");
        value = String(value).replace(",", "");
        value = String(value).replace(this.prefixChar, "");
        value = String(value).replace(this.suffixChar, "");
        if (isNaN(value)) {
            this.markInvalid(String.format(this.nanText, value));
            return false;
        }
        var num = this.parseValue(value);
        if (num < this.minValue) {
            this.markInvalid(String.format(this.minText, this.minValue));
            return false;
        }
        if (num > this.maxValue) {
            this.markInvalid(String.format(this.maxText, this.maxValue));
            return false;
        }
        return true;
    }
});
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
        var me = this
        me.setValue('');
        me.getTrigger('clear').hide();
        me.updateLayout();
    },
    onSearchClick: function() {
        var me = this,
            value = me.getValue();
        if (value.length > 0) {
            me.fireEvent('locate',me);
            me.getTrigger('clear').show();
            me.updateLayout();
        }
    }
});



Ext.define('Ext.vcf.Window', {
    extend: 'Ext.Window',
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
    }
});