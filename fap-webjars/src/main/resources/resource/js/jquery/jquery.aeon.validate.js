$(function(){

  $.validator.setDefaults({ 
     submitHandler: function(form) { 
      form.submit(); 
     } 
  }); 
  // 字符验证 
  jQuery.validator.addMethod("stringCheck", function(value, element) { 
     return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value); 
  }, "只能包括中文字、英文字母、数字和下划线"); 
  // 中文字两个字节 
  jQuery.validator.addMethod("byteRangeLength", function(value, element, param) { 
     var length = value.length; 
    for(var i = 0; i < value.length; i++){ 
  if(value.charCodeAt(i) > 127){ 
  length++; 
  } 
  } 
  return this.optional(element) || ( length >= param[0] && length <= param[1] ); 
}, "请确保输入的值在3-15个字节之间(一个中文字算2个字节)"); 

// 身份证号码验证 
jQuery.validator.addMethod("isIdCardNo", function(value, element) { 
  return this.optional(element) || idCardNoUtil.checkIdCardNo(value);     
}, "请正确输入您的身份证号码"); 
//护照编号验证
 jQuery.validator.addMethod("passport", function(value, element) { 
  return this.optional(element) || checknumber(value);     
}, "请正确输入您的护照编号"); 

// 手机号码验证 
jQuery.validator.addMethod("isMobile", function(value, element) { 
  var length = value.length; 
  var mobile = /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
  return this.optional(element) || (length == 11 && mobile.test(value)); 
}, "请正确填写您的手机号码"); 

// 电话号码验证 
jQuery.validator.addMethod("isTel", function(value, element) { 
  var tel = /^\d{3,4}-?\d{7,9}$/; //电话号码格式010-12345678 
  return this.optional(element) || (tel.test(value)); 
}, "请正确填写您的电话号码"); 

// 联系电话(手机/电话皆可)验证 
jQuery.validator.addMethod("isPhone", function(value,element) { 
  var length = value.length; 
  var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/; 
  var tel = /^\d{3,4}-?\d{7,9}$/; 
  return this.optional(element) || (tel.test(value) || mobile.test(value)); 

}, "请正确填写您的联系电话"); 

// 邮政编码验证 
jQuery.validator.addMethod("isZipCode", function(value, element) { 
  var tel = /^[0-9]{6}$/; 
  return this.optional(element) || (tel.test(value)); 
}, "请正确填写您的邮政编码");  

//银行卡标志位校验
jQuery.validator.addMethod("cardNoLuhnCheck", function(bankno, element, param) {
    var lastNum=bankno.substr(bankno.length-1,1);//取出最后一位（与luhn进行比较）

    var first15Num=bankno.substr(0,bankno.length-1);//前15或18位
    var newArr=new Array();
    for(var i=first15Num.length-1;i>-1;i--){    //前15或18位倒序存进数组
        newArr.push(first15Num.substr(i,1));
    }
    var arrJiShu=new Array();  //奇数位*2的积 <9
    var arrJiShu2=new Array(); //奇数位*2的积 >9
    
    var arrOuShu=new Array();  //偶数位数组
    for(var j=0;j<newArr.length;j++){
        if((j+1)%2==1){//奇数位
            if(parseInt(newArr[j])*2<9)
            arrJiShu.push(parseInt(newArr[j])*2);
            else
            arrJiShu2.push(parseInt(newArr[j])*2);
        }
        else //偶数位
        arrOuShu.push(newArr[j]);
    }
    
    var jishu_child1=new Array();//奇数位*2 >9 的分割之后的数组个位数
    var jishu_child2=new Array();//奇数位*2 >9 的分割之后的数组十位数
    for(var h=0;h<arrJiShu2.length;h++){
        jishu_child1.push(parseInt(arrJiShu2[h])%10);
        jishu_child2.push(parseInt(arrJiShu2[h])/10);
    }        
    
    var sumJiShu=0; //奇数位*2 < 9 的数组之和
    var sumOuShu=0; //偶数位数组之和
    var sumJiShuChild1=0; //奇数位*2 >9 的分割之后的数组个位数之和
    var sumJiShuChild2=0; //奇数位*2 >9 的分割之后的数组十位数之和
    var sumTotal=0;
    for(var m=0;m<arrJiShu.length;m++){
        sumJiShu=sumJiShu+parseInt(arrJiShu[m]);
    }
    
    for(var n=0;n<arrOuShu.length;n++){
        sumOuShu=sumOuShu+parseInt(arrOuShu[n]);
    }
    
    for(var p=0;p<jishu_child1.length;p++){
        sumJiShuChild1=sumJiShuChild1+parseInt(jishu_child1[p]);
        sumJiShuChild2=sumJiShuChild2+parseInt(jishu_child2[p]);
    }      
    //计算总和
    sumTotal=parseInt(sumJiShu)+parseInt(sumOuShu)+parseInt(sumJiShuChild1)+parseInt(sumJiShuChild2);
    
    //计算luhn值
    var k= parseInt(sumTotal)%10==0?10:parseInt(sumTotal)%10;        
    var luhn= 10-k;
    
    return lastNum==luhn;
}, $.validator.format("银行卡号输入有误，请核对后重新输入"));
})