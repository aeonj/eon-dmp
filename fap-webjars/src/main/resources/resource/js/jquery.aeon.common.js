//模拟alert
var alert_timer_id;
function showDialog() {
  var id=arguments[0];//窗口id
  var title=arguments[1];   //窗口标题
  var content=arguments[2];//提示内容
  var type=arguments[3];//0为倒计时提示框，1为确认框（包含2个按钮，点击确定执行回调）,2为提示确认框（只含有一个确认按钮）
  var icon=arguments[4];//显示图标，包括warning,succeed,question,smile,sad,error
  var second=arguments[5];//倒计时时间数,默认时间5秒，
  var confirm_action=arguments[6];//callback方法
  var back_function_args=arguments[7];//带参数回调函数发送的参数
  if(id==undefined||id==""){
    id=1;
  }
  if(title==undefined||title==""){
    title="系统提示";
  }
  if(type==undefined||type==""){
    type==0;
  }
  if(icon==undefined||icon==""){
     icon="succeed";
  }
  if(second==undefined||second==""){
     second=5;
  }
  var s="<div id='"+id+"'><div class='message_white_content'> <a href='javascript:void(0);' class='white_close' onclick='javascript:jQuery(\"#"+id+"\").remove();'></a><div><div class='message_white_iframe'><h3 class='message_white_title'><span>"+title+"</span></h3><div class='message_white_box'><span class='message_white_img_"+icon+"'></span><span class='message_white_font'>"+content+"</span></div><h3 class='message_white_title_bottom'><span id='time_down'>"+second+"</span>秒后窗口关闭</h3></div></div></div><div class='black_overlay'></div>";
  
  var c="<div id='"+id+"'><div class='message_white_content'> <a href='javascript:void(0);' class='white_close' onclick='javascript:jQuery(\"#"+id+"\").remove();'></a><div ><div class='message_white_iframe_del'><h3 class='message_white_title'><span>"+title+"</span></h3><div class='message_white_box_del'><span class='message_white_img_"+icon+"'></span><span class='message_white_font' style='font-size:14px;'>"+content+"</span></div>   <div class='message_white_box1'><input id='sure' type='button' value='确定'/><input id='cancel' type='button' value='取消'/></div>    </div></div></div><div class='black_overlay'></div>";
  var t="<div id='"+id+"'><div class='message_white_content'> <a href='javascript:void(0);' class='white_close' onclick='javascript:jQuery(\"#"+id+"\").remove();'></a><div ><div class='message_white_iframe_del'><h3 class='message_white_title'><span>"+title+"</span></h3><div class='message_white_box_del'><span class='message_white_img_"+icon+"'></span><span class='message_white_font' style='font-size:14px;'>"+content+"</span></div>   <div class='message_white_box2'><input id='ok' type='button' value='确定'/></div></div></div></div><div class='black_overlay'></div>";
  if(type==0){//消息框
   jQuery("body").append(s);
  }
  if(type==1){//确认并回调框
   jQuery("body").append(c);	  
  } 
  if(type==2){//确认框
   jQuery("body").append(t);
  }
  var top=jQuery(window).scrollTop()+(jQuery(window).height()-jQuery(document).outerHeight())/2; 
  jQuery(".message_white_content").css("margin-top",jQuery(window).scrollTop()+"px");
  var h=jQuery(document).height();
  jQuery('.black_overlay').css("height",h);
  //设置关闭时间

  if(confirm_action==undefined||confirm_action==""){
	 alert_timer_id=window.setInterval("closewin('"+id+"','')",1000);
  }else{
	  if(back_function_args==undefined||back_function_args==""){
    	 alert_timer_id=window.setInterval("closewin('"+id+"',"+confirm_action+")",1000);
  	}else{
		 alert_timer_id=window.setInterval("closewin('"+id+"',"+confirm_action+",'"+back_function_args+"')",1000);
	} 
  }
  //点击确定执行回调
  jQuery("#sure").click(function(){						 
		jQuery("#"+id).remove();
		runcallback(confirm_action);	
	});
  //点击确定关闭窗口
  jQuery("#ok").click(function(){						 
		jQuery("#"+id).remove();
		runcallback(confirm_action);
	});
  function runcallback(callback){   
    if(confirm_action!=undefined&&confirm_action!=""){
    	if(back_function_args==undefined||back_function_args==""){
    	   callback(); 
  	   }else{
		   callback(back_function_args); 
	   } 
	}
  } 
//点击取消
  jQuery("#cancel").click(function(){
	jQuery("#"+id).remove();							   
	});
  	//点击选择发布类型，将参数添加到页面隐藏域中
  	jQuery("a[id^=share_select_]").click(function(){
	jQuery("#share_select_mark").val(jQuery(this).attr("share_mark"));
	jQuery("#"+id).remove();	
	runcallback(confirm_action);
	});
} 

function closewin(id,callback,args) {
  var count=parseInt(jQuery("#"+id+" span[id=time_down]").text());
  count--;
  if(count==0){
   	  window.clearInterval(alert_timer_id);
     if(callback!=""){
		 if(args==undefined||args==""){
    		callback(); 
  	 	 }else{
			callback(args); 
		  } 
  		}else{//没有回调，移除当前弹框
		     jQuery("#"+id).remove();	
		}
  }else{
   jQuery("#"+id+" span[id=time_down]").text(count);
  }
} 
jQuery.widget("ui.dialog", jQuery.extend({}, jQuery.ui.dialog.prototype, {
	_title: function(title) {
		var $title = this.options.title || '&nbsp;'
		if( ("title_html" in this.options) && this.options.title_html == true )
			title.html($title);
		else title.text($title);
	}
}));
/**
 * 将JS数组转换为JS字符串 
 */
function jsArray2JsString(arrayChecked, id) {
	var strChecked = "";
	for (var i = 0; i < arrayChecked.length; i++) {
		strChecked = strChecked + arrayChecked[i][id] + ',';
	}
	return strChecked.substring(0, strChecked.length - 1);
}
/**
 * 获取ztree多选树的叶节点id数据,以逗号分隔
 */
function zTreeCheckedId2JsString(arr_nodes) {
	var strChecked = "";
	for (var i = 0; i < arr_nodes.length; i++) {
		if (arr_nodes[i].leaf) {
			strChecked = strChecked + arr_nodes[i].id + ',';
		}
	}
	return strChecked.substring(0, strChecked.length - 1);
}
//*   判断在数组中是否含有给定的一个变量值
//*   参数：
//*   obj：需要查询的值
//*    a：被查询的数组
//*    arrfield: 当arr为json格式的数组时，此参数可指定具体的属性字段
//*    objfield: 当obj为json格式的数组时，此参数可指定具体的属性字段
//*   在a中查询obj是否存在，如果找到返回true，否则返回false。
//*   此函数只能对字符和数字有效
function containsArray(arr, obj, arrfield, objfield) {
	
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
function removeArray(arr, obj, arrfield, objfield) {
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
function findArray(arr, obj, arrfield) {
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
/*
 * 判断当前用户有没有操作数据的权限
 */
function checkdata(rg_code,table_id){
	var mulitId=jQuery("#"+table_id+"").jqGrid("getGridParam","selarrrow");
	for (x in mulitId){
		var rowData = $("#"+table_id+"").getRowData(mulitId[x]);
		if(rg_code!=rowData.rg_code){
			 return false;
		}
	}
	return true;
}
function fileFmatter(cellvalue, options, rowObject){ 
	if(cellvalue!=null){
		return  "<a href=\"../manage/downloadfile.htm?file_id="+cellvalue.id+"\" style='text-decoration:underline;color:blue'>"+cellvalue.real_name+"</a>";  
    }else{
    	return "";
    }
}; 
/**  
* 实时动态强制更改用户录入
* 金额输入框的控制  
* 
**/  
function amount(th){  
    var regStrs = [  
        ['^0(\\d+)$', '$1'], //禁止录入整数部分两位以上，但首位为0  
        ['[^\\d\\.]+$', ''], //禁止录入任何非数字和点  
        ['\\.(\\d?)\\.+', '.$1'], //禁止录入两个以上的点  
        ['^(\\d+\\.\\d{2}).+', '$1'] //禁止录入小数点后两位以上  
    ];  
    for(var i=0; i<regStrs.length; i++){  
        var reg = new RegExp(regStrs[i][0]);  
        th.value = th.value.replace(reg, regStrs[i][1]);  
    }  
}  
   
/**  
* 录入完成后，输入模式失去焦点后对录入进行判断并强制更改，并对小数点进行0补全  
* 金额输入框的控制  
**/  
function overFormat(th){  
    var v = th.value;  
    if(v === ''){  
        v = '0.00';  
    }else if(v === '0'){  
        v = '0.00';  
    }else if(v === '0.'){  
        v = '0.00';  
    }else if(/^0+\d+\.?\d*.*$/.test(v)){  
        v = v.replace(/^0+(\d+\.?\d*).*$/, '$1');  
        v = inp.getRightPriceFormat(v).val;  
    }else if(/^0\.\d$/.test(v)){  
        v = v + '0';  
    }else if(!/^\d+\.\d{2}$/.test(v)){  
        if(/^\d+\.\d{2}.+/.test(v)){  
            v = v.replace(/^(\d+\.\d{2}).*$/, '$1');  
        }else if(/^\d+$/.test(v)){  
            v = v + '.00';  
        }else if(/^\d+\.$/.test(v)){  
            v = v + '00';  
        }else if(/^\d+\.\d$/.test(v)){  
            v = v + '0';  
        }else if(/^[^\d]+\d+\.?\d*$/.test(v)){  
            v = v.replace(/^[^\d]+(\d+\.?\d*)$/, '$1');  
        }else if(/\d+/.test(v)){  
            v = v.replace(/^[^\d]*(\d+\.?\d*).*$/, '$1');  
            ty = false;  
        }else if(/^0+\d+\.?\d*$/.test(v)){  
            v = v.replace(/^0+(\d+\.?\d*)$/, '$1');  
            ty = false;  
        }else{  
            v = '0.00';  
        }  
    }  
    th.value = v;   
}  
//自动生成查询条件
function query(){
  jQuery("#queryCondition").empty();
  jQuery.each(jQuery("#queryForm :input"),function(){
	 	if(this.type!="button"&&this.value!=""){
		  jQuery("#queryCondition").append("<input name='q_"+this.name+"'type='hidden' id='q_"+this.name+"' value='"+this.value+"' />");	
		}	
  });
 jQuery("#ListForm").submit();
}
//表单方式分页
function gotoPage(n){
	jQuery("#currentPage").val(n);
	jQuery("#ListForm").submit();
}
