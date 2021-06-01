/**
 * 显示请求等待进度条窗口
 *
 * @param {}
 *            msg
 */
function showWaitMsg(msg) {
    Ext.MessageBox.show({
        title: '系统提示',
        msg: msg == null ? '正在处理数据,请稍候...' : msg,
        progressText: 'processing now,please wait...',
        width: 300,
        wait: true,
        waitConfig: {
            interval: 150
        }
    });
}

/**
 * 隐藏请求等待进度条窗口
 */
function hideWaitMsg() {
    Ext.MessageBox.hide();
}

/**
 * 将JS数组转换为JS字符串 表格复选框专用
 */
function jsArray2JsString(arrayChecked, id) {
    var strChecked = "";
    for (var i = 0; i < arrayChecked.length; i++) {
        strChecked = strChecked + arrayChecked[i].get(id) + ',';
    }
    return strChecked.substring(0, strChecked.length - 1)
}

function jsArray2SingleArray(arrayChecked, id) {
    var strList = new Array();
    for (var i = 0; i < arrayChecked.length; i++) {
        strList.push(arrayChecked[i].get(id));
    }
    return strList;
}

/**
 * 清除Ext.Form表单元素
 */
function clearForm(pForm) {
    var formItems = pForm.items['items'];
    for (i = 0; i < formItems.length; i++) {
        element = formItems[i];
        element.setValue('');
        element.reset(); // 避免出现红色波浪线
    }
    ;
}

/**
 * 复制到剪贴板
 */
function copyToClipboard(txt) {
    if (window.clipboardData) {
        window.clipboardData.clearData();
        window.clipboardData.setData("Text", txt);
    } else if (navigator.userAgent.indexOf("Opera") != -1) {
        window.location = txt;
    } else if (window.netscape) {
        try {
            netscape.security.PrivilegeManager
                .enablePrivilege("UniversalXPConnect");
        } catch (e) {
            Ext.Msg
                .alert(
                    '提示',
                    "复制单元格操作被浏览器拒绝！\n请在浏览器地址栏输入'about:config'并回车\n然后将'signed.applets.codebase_principal_support'设置为'true'")
        }
        var clip = Components.classes['@mozilla.org/widget/clipboard;1']
            .createInstance(Components.interfaces.nsIClipboard);
        if (!clip)
            return;
        var trans = Components.classes['@mozilla.org/widget/transferable;1']
            .createInstance(Components.interfaces.nsITransferable);
        if (!trans)
            return;
        trans.addDataFlavor('text/unicode');
        var str = new Object();
        var len = new Object();
        var str = Components.classes["@mozilla.org/supports-string;1"]
            .createInstance(Components.interfaces.nsISupportsString);
        var copytext = txt;
        str.data = copytext;
        trans.setTransferData("text/unicode", str, copytext.length * 2);
        var clipid = Components.interfaces.nsIClipboard;
        if (!clip)
            return false;
        clip.setData(trans, null, clipid.kGlobalClipboard);
        // Ext.Msg.alert('提示','单元格内容已成功复制到剪贴板!')
    }
}

/**
 * 初始化报表打印窗口
 */
function doPrint(pFlag, pWidth, pHeight) {
    var initUrl = '/report.do?reqCode=initAppletPage';
    if (!Ext.isEmpty(pFlag))
        initUrl = initUrl + '&flag=' + pFlag;
    if (Ext.isEmpty(pWidth))
        pWidth = 800;
    if (Ext.isEmpty(pHeight))
        pHeight = 600;
    var left = (screen.width - pWidth) / 2;
    var top = (screen.height - pHeight) / 2;
    var str = 'width='
        + pWidth
        + ',height='
        + pHeight
        + ',top='
        + top
        + ",left="
        + left
        + ',toolbar=no,menubar=no,scrollbars=no,resizable=yes,location=no,status=no';
    window.open(webContext + initUrl, '', str);
}

/**
 * 初始化报表打印窗口，窗口关闭后执行回调函数
 */
function doPrintWithCallback(pFlag, pWidth, pHeight) {
    var initUrl = '/report.do?reqCode=initAppletPage';
    if (!Ext.isEmpty(pFlag))
        initUrl = initUrl + '&flag=' + pFlag;
    if (Ext.isEmpty(pWidth))
        pWidth = 800;
    if (Ext.isEmpty(pHeight))
        pHeight = 600;
    var timer, popwin;
    var left = (screen.width - pWidth) / 2;
    var top = (screen.height - pHeight) / 2;
    var str = 'width='
        + pWidth
        + ',height='
        + pHeight
        + ',top='
        + top
        + ",left="
        + left
        + ',toolbar=no,menubar=no,scrollbars=no,resizable=yes,location=no,status=no';
    popwin = window.open(webContext + initUrl, '', str);
    timer = window.setInterval(function () {
        if (popwin.closed == true) {
            window.clearInterval(timer);
            Ext.MessageBox.confirm('请确认', '打印是否成功?',
                function (btn, text) {
                    if (btn == 'yes') {
                        // 在这个回调函数中实现打印次数纪录功能,此函数不能写在Ext作用域内
                        fnPrintCallback();
                    } else {
                        return;
                    }
                });
        }
    }, 500);
}

/**
 * 初始化PDF导出窗口
 */
function doExport(pFlag, pWidth, pHeight) {
    var initUrl = '/report.do?reqCode=initPdfPage';
    if (!Ext.isEmpty(pFlag))
        initUrl = initUrl + '&flag=' + pFlag;
    if (Ext.isEmpty(pWidth))
        pWidth = 800;
    if (Ext.isEmpty(pHeight))
        pHeight = 600;
    var left = (screen.width - pWidth) / 2;
    var top = (screen.height - pHeight) / 2;
    var str = 'width='
        + pWidth
        + ',height='
        + pHeight
        + ',top='
        + top
        + ",left="
        + left
        + ',toolbar=no,menubar=no,scrollbars=no,resizable=yes,location=no,status=no';
    window.open(webContext + initUrl, '', str);
}

/**
 * 初始化报PDF导出窗口，窗口关闭后执行回调函数
 */
function doExportWithCallback(pFlag, pWidth, pHeight) {
    var initUrl = '/report.do?reqCode=initPdfPage';
    if (!Ext.isEmpty(pFlag))
        initUrl = initUrl + '&flag=' + pFlag;
    if (Ext.isEmpty(pWidth))
        pWidth = 800;
    if (Ext.isEmpty(pHeight))
        pHeight = 600;
    var timer, popwin;
    var left = (screen.width - pWidth) / 2;
    var top = (screen.height - pHeight) / 2;
    var str = 'width='
        + pWidth
        + ',height='
        + pHeight
        + ',top='
        + top
        + ",left="
        + left
        + ',toolbar=no,menubar=no,scrollbars=no,resizable=yes,location=no,status=no';
    popwin = window.open(webContext + initUrl, '', str);
    timer = window.setInterval(function () {
        if (popwin.closed == true) {
            window.clearInterval(timer);
            Ext.MessageBox.confirm('请确认', '打印/导出是否成功?', function (btn,
                                                                  text) {
                if (btn == 'yes') {
                    // 在这个回调函数中实现打印次数纪录功能,此函数不能写在Ext作用域内
                    fnExportCallback();
                } else {
                    return;
                }
            });
        }
    }, 500);
}

/**
 * 通过iFrame实现类ajax文件下载
 */
function exportExcel(url) {
    var exportIframe = document.createElement('iframe');
    exportIframe.src = url;
    exportIframe.style.display = "none";
    document.body.appendChild(exportIframe);
    hideWaitMsg();
}

// 这个可以验证15位和18位的身份证，并且包含生日和校验位的验证。
function isIdCardNo(num) {
    if (Ext.isEmpty(num))
        return false;
    num = num.toUpperCase();
    // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。
    if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num))) {
        Ext.MessageBox.alert('提示',
            '输入的身份证号长度不对，或者号码不符合规定！\n15位号码应全为数字，18位号码末位可以为数字或X。');
        return false;
    }
    // 校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
    // 下面分别分析出生日期和校验位
    var len, re;
    len = num.length;
    if (len == 15) {
        re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
        var arrSplit = num.match(re);
        // 检查生日日期是否正确
        var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3] + '/'
            + arrSplit[4]);
        var bGoodDay;
        bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2]))
            && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3]))
            && (dtmBirth.getDate() == Number(arrSplit[4]));
        if (!bGoodDay) {
            Ext.MessageBox.alert('提示', '输入的身份证号里出生日期不对！');
            return false;
        } else {
            // 将15位身份证转成18位
            // 校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
            var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,
                8, 4, 2);
            var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4',
                '3', '2');
            var nTemp = 0, i;
            num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6);
            for (i = 0; i < 17; i++) {
                nTemp += num.substr(i, 1) * arrInt[i];
            }
            num += arrCh[nTemp % 11];
            return num;
        }
    }
    if (len == 18) {
        re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
        var arrSplit = num.match(re);
        // 检查生日日期是否正确
        var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/"
            + arrSplit[4]);
        var bGoodDay;
        bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2]))
            && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3]))
            && (dtmBirth.getDate() == Number(arrSplit[4]));
        if (!bGoodDay) {
            // alert(dtmBirth.getYear());
            // alert(arrSplit[2]);
            Ext.MessageBox.alert('提示', '输入的身份证号里出生日期不对！');
            return false;
        } else {
            // 检验18位身份证的校验码是否正确。
            // 校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
            var valnum;
            var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,
                8, 4, 2);
            var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4',
                '3', '2');
            var nTemp = 0, i;
            for (i = 0; i < 17; i++) {
                nTemp += num.substr(i, 1) * arrInt[i];
            }
            valnum = arrCh[nTemp % 11];
            if (valnum != num.substr(17, 1)) {
                Ext.MessageBox.alert('提示', '18位身份证的校验码不正确！应该为:' + valnum);
                return false;
            }
            return num;
        }
    }
    return false;
}

/**
 * 设置Cookie
 *
 * @param {} name
 * @param {} value
 */
function setCookie(name, value, minuts) {
    var argv = setCookie.arguments;
    var argc = setCookie.arguments.length;
    var expiration = new Date((new Date()).getTime() + minuts * 60000 * 60);
    document.cookie = name
        + "="
        + escape(value)
        + "; expires=" + expiration
            .toGMTString();
}

/**
 * 获取Cookie
 *
 * @param {} Name
 * @return {}
 */
function getCookie(Name) {
    var search = Name + "="
    if (document.cookie.length > 0) {
        offset = document.cookie.indexOf(search)
        if (offset != -1) {
            offset += search.length
            end = document.cookie.indexOf(";", offset)
            if (end == -1)
                end = document.cookie.length
            return unescape(document.cookie.substring(offset, end))
        } else
            return ""
    }
}

/**
 * 从缓存中清除Cookie
 *
 * @param {} name
 */
function clearCookie(name) {
    var expdate = new Date();
    expdate.setTime(expdate.getTime() - (86400 * 1000 * 1));
    setCookie(name, "", expdate);
}

/**
 * 合并对象，合并到第一个参数对象并返回
 * @param o1
 * @param o2
 * @returns
 */
function mergeObj(o1, o2) {
    for (var key in o2) {
        o1[key] = o2[key];
    }
    return o1;
}

/**
 * 合并对象，合并到新对象并返回
 * @param o1
 * @param o2
 * @returns
 */
function unionObj(o1, o2) {
    o = {};
    for (var key in o1) {
        o[key] = o1[key];
    }
    for (var key in o2) {
        o[key] = o2[key];
    }
    return o;
}

/**
 * 树节点对象显示
 * @param value
 * @returns {string}
 */
function objEntityRender(value) {
    if (value != null && typeof value == "object") {
        return '<span data-qtip="' + value.code + ' ' + value.name + '">' + value.name + '</span>';
    } else {
        return value == null ? "" : value;
    }
}

/**
 *
 * 表格列自适应
 * @param value
 * @param cellmeta
 * @param record
 * @returns
 */
function columnWrapRender(value, cellmeta, record) {
    cellmeta.attr = 'style="white-space:normal;"';
    return value;
}

/**
 * 表格列提示，适用于Ext3.x
 * @param value
 * @param cellmeta
 * @param record
 * @returns
 */
function columnTooltipRender(value, cellmeta, record) {
    if (value != null) {
        //return '<span ext:qtip="'+value+'">'+value+'</span>';
        //适用于Ext4.x
        //return '<span data-qtip="' + value + '">' + value + '</span>';
        cellmeta.tdAttr = 'data-qtip="' + value + '"';
    }
    return value;
}

/**
 * 枚举值转换
 * @param value
 * @param enumData
 * @returns {*}
 */
function enumRender(value, enumData) {
    if (enumData) {
        if (enumData.indexOf('#') != -1) {
            var newData = enumData + '+';
            var ipos = newData.indexOf('+');
            while (ipos != -1) {
                var subData = newData.substring(0, ipos);
                newData = newData.substring(ipos + 1, newData.length);
                var ifieldpos = subData.indexOf('#');
                if (ifieldpos != -1) {
                    if (subData.substring(0, ifieldpos) == value) {
                        return subData.substring(ifieldpos + 1, subData.length);
                    }
                }
                ipos = newData.indexOf('+');
            }
        }
    }
    return value;
}

function columnEnumRender(value, cellmeta, record, row, col, store) {
    return enumRender(value, cellmeta.column.enumData);
}

/**
 * 用于合计行显示
 * @param value
 * @param cellmeta
 * @param record
 * @returns {string}
 */
function columnSumRender(value, cellmeta, record) {
    cellmeta.attr = 'style="white-space:normal;"';
    return '合计';
}

//金额段
function columnMoneyRender(v, cellmeta, record) {
    return Ext.util.Format.currency(v);
}

//数字千分位
function columnThousandNumberRender(v, cellmeta, record) {
    return Ext.util.Format.number(v,"0,000.00");
}

//整数千分位
function columnThousandIntRender(v, cellmeta, record) {
    return Ext.util.Format.number(v,"0,000");
}

//*   判断在数组中是否含有给定的一个变量值
//*   参数：
//*   obj：需要查询的值
//*    a：被查询的数组
//*   在a中查询obj是否存在，如果找到返回true，否则返回false。
//*   此函数只能对字符和数字有效
function containsArray(arr, obj, field) {
    return indexArray(arr, obj, field) > -1;
}

function indexArray(arr, obj, field) {

    function containMeta(arr, obj) {
        for (var i = 0; i < arr.length; i++) {
            if (typeof field == 'undefined') {
                if (arr[i] === obj) {
                    return i;
                }
            } else {
                if (arr[i][field] === obj) {
                    return i;
                }
            }
        }
        return -1;
    }

    if (obj instanceof Array) {
        for (var j = 0; j < obj.length; j++) {
            var idx = containMeta(arr, obj[j]);
            if (idx > -1) {
                return idx;
            }
        }
        return -1;
    } else {
        return containMeta(arr, obj);
    }
}
//*   判断在数组中是否含有给定的一个变量值
//*   参数：
//*   obj：需要查询的值
//*    a：被查询的数组
//*    arrfield: 当arr为json格式的数组时，此参数可指定具体的属性字段
//*    objfield: 当obj为json格式的数组时，此参数可指定具体的属性字段
//*   在a中查询obj是否存在，如果找到返回true，否则返回false。
//*   此函数只能对字符和数字有效
function containsArrayNew(arr, obj, arrfield, objfield) {

    function containMeta(arr, obj) {
        for (var i = 0; i < arr.length; i++) {
            if (arrfield==undefined || arrfield=="") {
                if (arr[i] === obj) {
                    return true;
                }
            } else {
                if (arr[i][arrfield] === obj) {
                    return true;
                }
            }
        }
        return false;
    }

    if (obj instanceof Array) {
        for (var j = 0; j < obj.length; j++) {
            if (objfield==undefined || objfield=="") {
                if (!containMeta(arr,obj[j])) {
                    return false;
                }
            } else {
                if (!containMeta(arr,obj[j][objfield])) {
                    return false;
                }
            }
        }
        return true;
    } else {
        return containMeta(arr,obj);
    }
}
/**
 * 删除arr数组中指定的元素，支持json对象
 * @param arr 被删除的数组对象
 * @param obj 需要查找arr中的值
 * @param arrfield arr为json数组对象时，指定需要查找的唯一属性字段的值
 * @param objfield obj为json数组对象时，指定需要用obj的那一个属性字段值去查找arr
 */
function removeArrayNew(arr, obj, arrfield, objfield) {
    function removeMeta(arr, obj) {
        for (var i = 0; i < arr.length; i++) {
            if (arrfield==undefined || arrfield=="") {
                if (arr[i] === obj) {
                    arr.splice(i,1);
                    return;
                }
            } else {
                if (arr[i][arrfield] === obj) {
                    arr.splice(i,1);
                    return;
                }
            }
        }
    }
    if (obj instanceof Array) {
        for (var j = 0; j < obj.length; j++) {
            if (objfield==undefined || objfield=="") {
                removeMeta(arr,obj[j]);
            } else {
                removeMeta(arr,obj[j][objfield]);
            }
        }
    } else {
        removeMeta(arr,obj);
    }
}
/**
 * 查找指定arr数组对象的位置，从0开始，支持json数组对象
 * @param arr 被查数组对象
 * @param obj 查找的值对象
 * @param arrfield arr为json数组对象时，指定需要查找的唯一属性字段的值
 * @returns 位置
 */
function findArrayNew(arr, obj, arrfield) {
    var i = 0;
    for (; i < arr.length; i++) {
        if (arrfield==undefined || arrfield=="") {
            if (arr[i] === obj) {
                return i;
            }
        } else {
            if (arr[i][arrfield] === obj) {
                return i;
            }
        }
    }
    return -1;
}
function trim(str){ //删除左右两端的空格
    return str.replace(/(^\s*)|(\s*$)/g, "");
}

/**
 * 模板字符串
 *   var str = `<div class="{%data.className%}">{%data.name%}</div>`
 *   var d = {data: {name: 123,className:'hd'}}
 *   var t = tagTpl(str,d)
 * @param template 用{% %}标签标识的字符串模板
 * @param data 与模板内标签匹配的对象
 */
function tagTpl(template,data){
    return template.replace(/\{%([^%\{\}]+)%}/g,function(orgin,item){
        item = item.replace(/^\s+|\s+$/,"");
        if(item.indexOf('.') > -1){
            var params = [];
            params = item.split('.');
            return data[params[1]];
        } else {
            return data[item];
        }
    })
}

function showFlowOperation(business_id) {
    var pnl_tab = Ext.create('Ext.tab.Panel', {
        region: 'center',
        plain: true,
        defaults: {
            scrollable: true
        },
        activeTab : 0,
        items: [{
            title: '流程记录',
            region: 'center',
            xtype: 'tablegrid',
            url: '/flow/operaHistory',
            params: {businessId: business_id,menu_id:MENU_ID},
            columnBase: [{
                xtype: 'rownumberer',
                width: 30
            }, {
                header: '任务名称',
                dataIndex: 'taskName',
                width: 140,
                sortable: true
            }, {
                header: '任务状态',
                dataIndex: 'state',
                width: 70,
                sortable: true
            }, {
                header: '处理意见',
                dataIndex: 'opinion',
                width: 110,
                sortable: true,
                renderer: columnTooltipRender
            }, {
                header: '操作人',
                dataIndex: 'assignee',
                sortable: true,
                width: 100,
                renderer: columnTooltipRender
            }, {
                header: '操作时间',
                dataIndex: 'endDate',
                sortable: true,
                width: 140
            }],
            fieldBase: [{
                name : 'businessId'
            }, {
                name : 'taskName'
            }, {
                name : 'state'
            }, {
                name : 'opinion'
            }, {
                name : 'assignee'
            }, {
                name : 'endDate'
            }, {
                name : 'userCode'
            }]

        },{
            title: '流程显示',
            layout: 'fit',
            html: '<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="/flow/diagram?businessId='+business_id+'&menu_id='+MENU_ID+'"></iframe>'
        }]
    });
    var win = Ext.create('Ext.vcf.Window', {
        title: '流程显示',
        autoShow: true,
        height: 580,
        width: 600,
        closeAction: 'destroy',
        items: pnl_tab,
        buttons : [{
            text : '关闭',
            iconCls : 'deleteIcon',
            handler : function () {
                win[win.closeAction]();
            }
        } ]
    })
}