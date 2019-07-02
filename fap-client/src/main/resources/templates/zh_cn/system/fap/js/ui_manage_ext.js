/**
 * 扩展编辑表格控件，editor按行属性进行控制
 * @author:aeon
 */
Ext.define('Ext.vcf.EditChkCellSelModel', {

    extend: 'Ext.selection.CheckboxModel',
    contain_field: '',
    showHeaderCheckbox : false,
    constructor: function() {
        var me = this;
        me.callParent(arguments);

        me.contains = new Ext.util.MixedCollection(false, function(o) {
            return o.id;
        });

    },

    isContained : function(index){
        var r = Ext.isNumber(index) ? this.grid.store.getAt(index) : index;
        return (r && this.contains.get(r.id) ? true : false);
    },

    onSelectChange: function(record, isSelected, suppressEvent, commitFn) {
        var me      = this,
            views   = me.views || [me.view],
            viewsLn = views.length,
            recordIndex = me.store.indexOf(record),
            eventName = isSelected ? 'select' : 'deselect',
            i, view;

        if ((suppressEvent || me.fireEvent('before' + eventName, me, record, recordIndex)) !== false &&
            commitFn() !== false) {

            // Selection models can handle more than one view
            for (i = 0; i < viewsLn; i++) {
                view = views[i];
                recordIndex  = view.indexOf(record);

                // The record might not be rendered due to either buffered rendering,
                // or removal/hiding of all columns (eg empty locked side).
                if (view.indexOf(record) !== -1) {
                    var setContain = function(comp,v,i) {
                        if (comp.contain_field!='') {
                            var record = comp.grid.store.getAt(i);
                            record.set(comp.contain_field,v);
                        }
                    };
                    if(me.isContained(recordIndex)){
                        setContain(me,0,recordIndex);
                        this.contains.remove(record);
                        view.onRowDeselect(recordIndex, suppressEvent);
                    }else{
                        setContain(me,1,recordIndex);
                        me.contains.add(record);
                        view.onRowSelect(recordIndex, suppressEvent);
                    }
                }
            }

            if (!suppressEvent && !me.destroyed) {
                me.fireEvent(eventName, me, record, recordIndex);
            }
        }
        if (me.column) {
            me.column.updateCellAriaDescription(record, isSelected);
        }

        if (!me.suspendChange) {
            me.updateHeaderState();
        }
    },

    containRow : function(index) {
        var me = this,
            view = me.view;
        view.onRowSelect(index);
    },

    decontainRow : function(index) {
        var me = this,
            view = me.view;
        view.onRowDeselect(index);
    },

    querySelectRecords : function(){
        if (this.contain_field=='') return;
        var ds = this.grid.store;
        for (var j=0, len = ds.getCount(); j<len; j++) {
            if (ds.getAt(j).get(this.contain_field)==1) {
                this.containRow(j, true);
            } else {
                this.decontainRow(j);
            }
        }
    }

});

Ext.define('Ext.vcf.ViewEditorTableGrid',{
    extend:'Ext.vcf.EditorTableGrid',
    contain_field : '',
    initComponent : function() {
        var me = this;
        me.selModel = Ext.create('Ext.vcf.EditChkCellSelModel',{
            grid : me,
            store : me.store,
            contain_field : me.contain_field,
            checkOnly: true
        });
        me.callParent(arguments);
    },
    getCellEditor : function(record){
        var p = record,
            n = Ext.isEmpty(this.editDataIndex) ? 1 : p.data[this.editDataIndex],
            xtype = p.data['uiconf_datatype'],
            ref = p.data['ref_selmodel'];
        if (n==1 || n==true) {
            if(xtype=='string'){
                if (ref==null || ref=='') {
                    var editor = this.editors.string;
                    if (p.data['uiconf_field']=='anchor') {
                        editor.field.regex = /^-?\d+%$/;
                        editor.field.regexText = '值必须为百分比';
                    } else {
                        editor.field.regex = null;
                        editor.field.regexText = '';
                    }
                    return editor;
                } else {
                    var editor = this.editors.combox;
                    editor.field.setEnumData(ref);
                    return editor;
                }
            }else{
                return this.editors[xtype];
            }
        }
    }
});