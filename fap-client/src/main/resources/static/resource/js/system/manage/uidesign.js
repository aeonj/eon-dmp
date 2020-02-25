/**
 * 界面视图控件显示处理
 * @author aeon
 */
Ext.override(Ext.vcf.FormPanel, {
    isPreview: false,
    initUI: function () {
        var comp = this;
        Ext.Ajax.request({
            url: '/manage/ui_designer.htm',
            success: function (response) {
                var resultArray = Ext.JSON
                    .decode(response.responseText);
                addUIByServer(comp, resultArray);
                //comp.add(childcomp);
                //comp.doComponentLayout();
                if (comp instanceof (Ext.vcf.FormPanel) === true)
                    comp.fireEvent('afterLoadUI', this.ownerField);
            },
            failure: function (response) {

            },
            params: {
                comp_id: comp.viewid || comp.getId(),
                servletpath: webServlet || '/'
            }
        });
    },
    execMethod: function (items) {
        for (var attr in items) {
            var value = items[attr];
            if (attr == 'title') {
                this.setTitle(value);
            } else if (attr == 'height') {
                this.setHeight(value);
            } else if (attr == 'width') {
                this.setWidth(value);
            } else if (attr == 'listeners') {
                for (var event in value) {
                    this.on(event, value[event]);
                }
            }
        }
    }
});

Ext.override(Ext.vcf.TableGrid, {
    isPreview: false,
    isLoadConfig: false,
    initConfig1: function () {
        var comp = this;
        if (!comp.isLoadConfig) {
            Ext.Ajax.request({
                url: '/manage/uimain_designer.htm',
                success: function (response) {
                    var resultArray = Ext.JSON
                        .decode(response.responseText);
                    var ray_main = resultArray;
                    var items = {};
                    for (var j = 0; j < ray_main.length; j++) {
                        if (ray_main[j].uiconf_value.charAt(0) == '{' && ray_main[j].uiconf_value.charAt(ray_main[j].uiconf_value.length - 1) == '}') {
                            items[ray_main[j].uiconf_field] = eval(ray_main[j].uiconf_value.substring(1, ray_main[j].uiconf_value.length - 1));
                        } else if (ray_main[j].uiconf_datatype == 'boolean') {
                            items[ray_main[j].uiconf_field] = (ray_main[j].uiconf_value == 'true');
                        } else if (ray_main[j].uiconf_datatype == 'int') {
                            items[ray_main[j].uiconf_field] = ray_main[j].uiconf_value
                                - 0;
                        } else if (ray_main[j].uiconf_datatype == 'float') {
                            items[ray_main[j].uiconf_field] = parseFloat(ray_main[j].uiconf_value);
                        } else if (ray_main[j].uiconf_datatype == 'string') {
                            items[ray_main[j].uiconf_field] = ray_main[j].uiconf_value;
                        } else {
                            if (!comp.isPreview) {
                                items[ray_main[j].uiconf_field] = eval(ray_main[j].uiconf_value);
                            }
                        }
                    }
                    items.isLoadConfig = true;
                    comp = comp.cloneConfig(items);
                },
                failure: function (response) {
                    comp.isLoadConfig = true;
                },
                params: {
                    comp_id: comp.viewid || comp.id,
                    servletpath: webServlet || '/'
                }
            });
        }
    },
    initUI: function () {
        var comp = this;
        Ext.Ajax.request({
            url: '/manage/ui_designer.htm',
            success: function (response) {
                var resultArray = Ext.JSON
                    .decode(response.responseText);
                addGridByServer(comp, resultArray);
            },
            failure: function (response) {
                uiArray = null;
            },
            params: {
                comp_id: comp.viewid || comp.getId(),
                servletpath: webServlet || '/'
            }
        });
    },
    execMethod: function (items) {
        for (var attr in items) {
            var value = items[attr];
            if (attr == 'title') {
                this.setTitle(value);
            } else if (attr == 'height') {
                this.setHeight(value);
            } else if (attr == 'width') {
                this.setWidth(value);
            } else if (attr == 'listeners') {
                for (var event in value) {
                    this.on(event, value[event]);
                }
            } else if (attr == 'limit') {
                this.pagesize_combo.setValue(value);
            }
        }
    }
});

function addUIByServer(comp, ray, pos) {
    var initcol = 0,
        initrow = 0,
        pnl,
        isonecol = false,
        relations = [],
        elenames = [];
    if (typeof ray.detail != 'undefined') {
        var ray_detail = ray.detail;
        var downcol=0,lastcol = 0;
        //计算最后一行控件，用以设置padding底间距
        if (typeof comp.lastitems == 'undefined') {
            for (var k = ray_detail.length - 1; k >= 0; k--) {
                if (ray_detail[k].field_type != 'hidden') {
                    downcol = downcol + ray_detail[k].cols;
                }
                if (downcol > ray_detail[k].total_column) {
                    lastcol = k;
                    break;
                }
            }
        } else {
            lastcol = 99;
        }
        for (var i = 0; i < ray_detail.length; i++) {
            var items = {};
            //新版本中labelWidth需要手动添加或者父容器中默认配置
            //items.labelWidth = comp.labelWidth;
            if (ray_detail[i].field_type == 'button') {
                items.text = ray_detail[i].field_title;
            } else {
                items.name = ray_detail[i].field_name;
                items.fieldLabel = ray_detail[i].field_title;
            }
            items.xtype = ray_detail[i].field_type;
            items.logic = ray_detail[i].field_logic;
            var children = ray_detail[i].children;
            if (children && typeof children == 'object') {
                for (var j = 0; j < children.length; j++) {
                    if (children[j].uiconf_value.charAt(0) == '{' && children[j].uiconf_value.charAt(children[j].uiconf_value.length - 1) == '}') {
                        items[children[j].uiconf_field] = eval(children[j].uiconf_value.substring(1, children[j].uiconf_value.length - 1));
                    } else if (children[j].uiconf_datatype == 'boolean') {
                        items[children[j].uiconf_field] = (children[j].uiconf_value == 'true');
                    } else if (children[j].uiconf_datatype == 'int') {
                        items[children[j].uiconf_field] = children[j].uiconf_value
                            - 0;
                    } else if (children[j].uiconf_datatype == 'float') {
                        items[children[j].uiconf_field] = parseFloat(children[j].uiconf_value);
                    } else if (children[j].uiconf_datatype == 'string') {
                        items[children[j].uiconf_field] = children[j].uiconf_value;
                    } else {
                        if (!comp.isPreview) {
                            if (children[j].uiconf_field=='store') {
                                items[children[j].uiconf_field] = eval(children[j].uiconf_value+"Store");
                            } else {
                                items[children[j].uiconf_field] = eval(children[j].uiconf_value);
                            }
                        }
                    }
                    //要素关联关系
                    if (children[j].uiconf_field=='source') {
                        elenames.push({source:children[j].uiconf_value,compname:items.name});

                        //判断是否在要素维护关系里面
                        var relamanager = eon_relation_ele || [];
                        //遍历要素关联关系静态对象
                        for (var m=0; m<relamanager.length; m++) {
                            if (relamanager[m].prisource == children[j].uiconf_value) {
                                //作为主控要素
                                var secs = [];
                                //载入已实例化的被控要素
                                for (var n=0; n<elenames.length; n++) {
                                    if (elenames[n].source==relamanager[m].secsource) {
                                        secs.push({secsource : elenames[n].source, seccompname : elenames[n].compname});
                                    }
                                }
                                relations.push({prisource:children[j].uiconf_value,pricompname:items.name,secs:secs});
                            }
                            if (relamanager[m].secsource == children[j].uiconf_value) {
                                //作为被控要素
                                for (var n=0; n<relations.length; n++) {
                                    if (relations[n].prisource==relamanager[m].prisource) {
                                        relations[n].secs.push({
                                            secsource: children[j].uiconf_value,
                                            seccompname: items.name
                                        });
                                    }
                                }
                            }
                        }

                    }
                }

                if (!Ext.isEmpty(items.allowBlank)) {
                    if (items.allowBlank == false) {
                        items.afterLabelTextTpl= [
                            '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>'
                        ];
                        items.labelStyle = micolor;
                    }
                }
                if (comp.getLayout().type=='form') {
                    comp.add(Ext.create(items));
                    isonecol = true;
                } else if (ray_detail[i].field_type == 'hidden') {
                    comp.add(Ext.create(items));
                } else {
                    var colWidth = (initcol + ray_detail[i].cols >= ray_detail[i].total_column) ? ((ray_detail[i].total_column - initcol) / ray_detail[i].total_column) : ray_detail[i].cols / ray_detail[i].total_column;
                    var pnlCol,padding;
                    if (i>lastcol) {
                        if (initrow==0) {
                            padding = "10 12 10 12";
                        } else {
                            padding = "3 12 10 12";
                        }
                    } else {
                        if (initrow==0) {
                            padding = "10 12 3 12";
                        } else {
                            padding = "3 12 3 12";
                        }
                    }
                    pnlCol= Ext.create('Ext.panel.Panel', {
                        columnWidth: colWidth,
                        layout: 'fit',
                        padding : padding,
                        labelWidth: comp.labelWidth,
                        border: false,
                        items: [items]
                    });
                    comp.add(pnlCol);
                    if (initcol + ray_detail[i].cols >= ray_detail[i].total_column || i == ray_detail.length - 1) {
                        initcol = 0;
                        initrow++;
                    } else {
                        initcol = initcol + ray_detail[i].cols;
                    }
                }
            }
        }
    }
    //追加界面自定义列
    if (typeof comp.lastitems != 'undefined') {
        for (var i = 0; i < comp.lastitems.length; i++) {
            if (isonecol) {
                comp.add(Ext.create(comp.lastitems[i]));
            } else {
                var pnlCol = new Ext.Panel(comp.lastitems[i]);
                comp.add(pnlCol);
            }
        }
    }
    if (typeof ray.main!='undefined') {
        var ray_main = ray.main;
        var items = {};
        //console.log(typeof ray.main);
        for (var j = 0; j < ray_main.length; j++) {
            if (ray_main[j].uiconf_value.charAt(0)=='{' && ray_main[j].uiconf_value.charAt(ray_main[j].uiconf_value.length-1)=='}') {
                items[ray_main[j].uiconf_field] = eval(ray_main[j].uiconf_value.substring(1,ray_main[j].uiconf_value.length-1));
            } else if (ray_main[j].uiconf_datatype == 'boolean') {
                items[ray_main[j].uiconf_field] = (ray_main[j].uiconf_value == 'true');
            } else if (ray_main[j].uiconf_datatype == 'int') {
                items[ray_main[j].uiconf_field] = ray_main[j].uiconf_value
                    - 0;
            } else if (ray_main[j].uiconf_datatype == 'float') {
                items[ray_main[j].uiconf_field] = parseFloat(ray_main[j].uiconf_value);
            } else if (ray_main[j].uiconf_datatype == 'string') {
                items[ray_main[j].uiconf_field] = ray_main[j].uiconf_value;
            } else {
                if (!comp.isPreview) {
                    items[ray_main[j].uiconf_field] = eval(ray_main[j].uiconf_value);
                }
            }
            if (ray_main[j].uiconf_field=='isRelation' && ray_main[j].uiconf_value == 'true') {
                //relations = [{prisource:'BK',pricompname:'bk_id',secs : [{secsource:'BKA',seccompname:'parent_id'}]}];
                for (var i=0; i<relations.length; i++) {
                    var pri_comp = comp.getForm().findField(relations[i].pricompname);
                    if (pri_comp) {
                        var secs = relations[i].secs;

                        var pri_source = relations[i].prisource;
                        pri_comp.on('select', function (picker, node) {
                            for (var k=0; k<secs.length; k++) {
                                var sec_comp = comp.getForm().findField(secs[k].seccompname);
                                if (sec_comp) {
                                    sec_comp.reset();
                                }
                            }
                        });
                        pri_comp.on('afterchange', function (picker, value, oldvalue) {
                            for (var k=0; k<secs.length; k++) {
                                var sec_comp = comp.getForm().findField(secs[k].seccompname);
                                if (sec_comp) {
                                    if (containsArray(sec_comp.getRelations(), pri_source, 'source')) {
                                        var index = -1;
                                        for (var j = 0; j < sec_comp.getRelations().length; j++) {
                                            if (sec_comp.getRelations()[j].source === pri_source) {
                                                index = j;
                                                break;
                                            }
                                        }
                                        if (index > -1) {
                                            sec_comp.removeRelations(index);
                                        }
                                    }
                                    sec_comp.requestload = true;
                                    sec_comp.appendRelations({source: pri_source, value: value});
                                }
                            }
                        });
                    }
                }
            }

        }
        if (comp.execMethod) {
            comp.execMethod(items);
        }
    }
    //console.log(comp.getConfigurator());

}

function addGridByServer(comp, ray) {
    if (typeof ray.detail != 'undefined') {
        var newColumnModel = function (comp, ray, store) {
            var cmArray = [];
            var findIsCodeAsValue = function (childs) {
                for (var k = 0; k < childs.length; k++) {
                    if (childs[j].uiconf_field == 'isCodeAsValue') {
                        return true;
                    }
                }
                return false;
            };
            var setOtherProp = function (colray, fieldtype) {
                if (fieldtype == 'checkbox'
                    || fieldtype == 'chkfield') {
                    colray.renderer = YESNORender;
                } else if (fieldtype == 'radiogroup') {
                    // aa.renderer = {};
                } else if (fieldtype == 'hidden') {
                    colray.hidden = true;
                }
            };
            for (var i = 0; ray != null && i < ray.length; i++) {
                var newField = {
                    header: ray[i].field_title,
                    dataIndex: ray[i].field_name,
                    sortable: true,
                    //用于表嵌套render事件处理
                    parentData: comp.parentData,
                    //hidden : ray[i].hidden == 1,
                    width: ray[i].width
                };
                setOtherProp(newField, ray[i].field_type);
                var children = ray[i].children;
                if (children && typeof children == 'object') {
                    for (var j = 0; j < children.length; j++) {
                        if (!comp.isPreview) {
                            if (ray[i].field_type == 'colmodel') {
                                if (children[j].uiconf_datatype == 'boolean') {
                                    newField[children[j].uiconf_field] = (children[j].uiconf_value == 'true');
                                } else if (children[j].uiconf_datatype == 'int') {
                                    newField[children[j].uiconf_field] = children[j].uiconf_value
                                        - 0;
                                } else if (children[j].uiconf_datatype == 'float') {
                                    newField[children[j].uiconf_field] = parseFloat(children[j].uiconf_value);
                                } else if (children[j].uiconf_datatype == 'string') {
                                    newField[children[j].uiconf_field] = children[j].uiconf_value;
                                    if (children[j].uiconf_field == 'enumData') {
                                        newField.renderer = eval('typeof columnEnumRender!=\'undefined\' ? columnEnumRender : null');
                                    }
                                } else {
                                    newField[children[j].uiconf_field] = eval('typeof ' + children[j].uiconf_value + '!=\'undefined\' ? ' + children[j].uiconf_value + ' : null');
                                }
                            } else {
                                if (children[j].uiconf_field == 'source') {
                                    if (findIsCodeAsValue(children)) {
                                        newField.renderer = eval('typeof ' + children[j].uiconf_value + 'CodeRender!=\'undefined\' ? '
                                            + children[j].uiconf_value + 'CodeRender : objEntityRender');
                                    } else {
                                        newField.renderer = eval('typeof ' + children[j].uiconf_value + 'IdRender!=\'undefined\' ? '
                                            + children[j].uiconf_value + 'IdRender : objEntityRender');
                                    }
                                } else if (children[j].uiconf_field == 'store') {
                                    newField.renderer = eval('typeof ' + children[j].uiconf_value.replace('Store', 'Render') + '!=\'undefined\' ? ' + children[j].uiconf_value.replace('Store', 'Render') + ' : null');
                                } else if (children[j].uiconf_field == 'enumData') {
                                    eval('function ' + newField.dataIndex + 'EnumRender(v) {return enumRender(v,"' + children[j].uiconf_value + '")};');
                                    newField.renderer = eval('typeof ' + newField.dataIndex + 'EnumRender!=\'undefined\' ? ' + newField.dataIndex + 'EnumRender : null');
                                } else if (children[j].uiconf_field == 'renderer') {
                                    newField.renderer = eval('typeof ' + children[j].uiconf_value + '!=\'undefined\' ? ' + children[j].uiconf_value + ' : null');
                                }
                            }
                        }
                    }
                }
                cmArray.push(newField);
            }
            if (typeof this == Ext.vcf.EditorColumnModel) {
                return new Ext.vcf.EditorColumnModel(comp.columnBase.concat(cmArray), store, comp.editDataIndex);
            } else {
                return comp.columnBase.concat(cmArray);
            }
        };
        var newStore = function (comp, ray) {
            var stArray = [];
            for (var i = 0; ray != null && i < ray.length; i++) {
                var newField = {
                    name: ray[i].field_name
                };
                stArray.push(newField);
            }
            var recNew = Ext.create('Ext.data.Model', {
                fields: comp.fieldBase.concat(stArray)
            });
            var reStore = Ext.create('Ext.data.Store', {
                pageSize: 50,
                model: recNew,
                proxy: {
                    type: 'ajax',
                    url: comp.url,
                    actionMethods: {
                        read: 'POST' // Store设置请求的方法，与Ajax请求有区别
                    },
                    reader: {
                        type: 'json',
                        totalProperty: 'total',
                        rootProperty: 'data',
                        implicitIncludes: false
                    }
                }
            });
            return reStore;
        };
        var store = newStore(comp, ray.detail);
        var cm = newColumnModel(comp, ray.detail, store);
        comp.reconfigure(store, cm);
        //if (typeof comp.getBottomToolbar()!='undefined') {
        //    comp.getBottomToolbar().bind(store);
        //}
        if (comp.autoLoad) {
            comp.store.reload(comp.params);
        }
    }
    if (typeof ray.main != 'undefined') {
        var ray_main = ray.main;
        var items = {};
        for (var j = 0; j < ray_main.length; j++) {
            if (ray_main[j].uiconf_value.charAt(0) == '{' && ray_main[j].uiconf_value.charAt(ray_main[j].uiconf_value.length - 1) == '}') {
                items[ray_main[j].uiconf_field] = eval(ray_main[j].uiconf_value.substring(1, ray_main[j].uiconf_value.length - 1));
            } else if (ray_main[j].uiconf_datatype == 'boolean') {
                items[ray_main[j].uiconf_field] = (ray_main[j].uiconf_value == 'true');
            } else if (ray_main[j].uiconf_datatype == 'int') {
                items[ray_main[j].uiconf_field] = ray_main[j].uiconf_value
                    - 0;
            } else if (ray_main[j].uiconf_datatype == 'float') {
                items[ray_main[j].uiconf_field] = parseFloat(ray_main[j].uiconf_value);
            } else if (ray_main[j].uiconf_datatype == 'string') {
                items[ray_main[j].uiconf_field] = ray_main[j].uiconf_value;
            } else {
                if (!comp.isPreview) {
                    items[ray_main[j].uiconf_field] = eval(ray_main[j].uiconf_value);
                }
            }
        }
        comp.execMethod(items);
    }
}


