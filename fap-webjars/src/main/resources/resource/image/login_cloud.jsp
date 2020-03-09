<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<Wafa:html title="敏行- WAFA云服务平台" showLoading="false" exportParams="true"
	isSubPage="false" jqueryEnabled="true">

<link href="resource/cloud/login.css" rel="stylesheet" type="text/css" />
<%-- <Wafa:import src="/resource/jquery/jquery.jslides1.js" /> --%>
<!-- <link href="resource/cloud/l_style/style.css" rel="stylesheet" type="text/css" /> -->
<link rel="stylesheet" href="resource/cloud/l_css/base.css" />
<!-- <link rel="stylesheet" href="resource/cloud/index1.css" />  -->

<link rel="Shortcut Icon" href="images/favicon.ico"/>
<!-- <link rel="stylesheet" type="text/css" href="resource/cloud/css/ext-all1.css" /> -->
<!-- <link rel="stylesheet" type="text/css" href="resource/cloud/css/wd-all1.css" />  -->

<style type="text/css">
.top{ width:auto; height:96px; top : 0px; left:0px;margin:auto 320;}
.logo{ width:245px;potision: absolute ;padding-left: 60px;}
/*幻灯片*/
#banner {background-color: #fff; height:430px; margin: 0 auto; min-width: 864px; overflow: hidden; position: relative; width: 100%; z-index: 100;}
#full-screen-slider { width:100%; height:430px; *top:-4px; position:relative; z-index:99;}
#slides { display:block; width:100%; height:430px; list-style:none; padding:0; margin:0; position:relative}
#slides li { display:block; width:100%; height:100%; list-style:none; padding:0; margin:0; position:absolute}
#slides li a { display:block; width:100%; height:100%; text-indent:-9999px}
#pagination { display:block; list-style:none; position:absolute; left:30%; top:370px; z-index:9900;padding:5px 15px 5px 0; margin:0}
#pagination li { display:block; background: url(eon/image/cloud/btn_bg2.png)  no-repeat; list-style:none; width:16px; height:16px; float:left; margin-left:0px; }
#pagination li a { display:block; width:100%; height:100%; padding:0; margin:0;  text-indent:-9999px;}
#pagination li.current { background: url(eon/image/cloud/btn_bg1.png)  no-repeat; width:16px; height:16px;}

/*登录*/
.login-box-a {height: 0; margin: 0 0;position:relative; top:-340px; left:1164px; width: 864px; zoom: 1; z-index: 120;}

.nlogin-a {width: 312px; height:272px;position:relative;right:0\0;}
.login-modern-a {border: 1px solid transparent;background:url(eon/image/cloud/login-bg.png) no-repeat;}

#banner .login-box-a .nav{ width:312px;height:46px;}
#banner .login-box-a .nav a.email_1{ width:154px; height:46px; float:left; display:block; font-size:16px; color:#FFFFFF; line-height:46px; text-align:center;}
#banner .login-box-a .nav a.email{ width:154px; height:46px; float:left; display:block; font-size:16px; color:#5e5d5d; line-height:46px; text-align:center;background:url(../l_images/email-h.png) no-repeat}
#banner .login-box-a .nav a.dz1{  background:url(eon/image/cloud/email-h.png) no-repeat; width:212px; height:46px; float:left; display:block; font-size:16px; color:#6a6767; line-height:63px; text-align:center}

#banner .login-box-a .nav a.tel{ width:158px; height:46px; float:right; display:block; font-size:16px; color:#FFFFFF; line-height:46px; text-align:center}
#banner .login-box-a .nav a.tel_1{ width:158px; height:46px; float:right; display:block; font-size:16px; color:#5e5d5d; line-height:46px; text-align:center;background:url(../l_images/tel-h.png) no-repeat}
#banner .login-box-a .nav a.dz2{ background:url(eon/image/cloud/tel-h.png) no-repeat;width:147px; height:46px; float:right; display:block; font-size:16px; color:#6a6767; line-height:63px; text-align:center}

#banner .login-box-a ul.dl{padding-left : 30px; padding-top:18px;} 
#banner .login-box-a ul.dl li{ width:250px; height:39px;}
#banner .login-box-a ul.dl1{padding-left : 30px; padding-top:18px;} 
#banner .login-box-a ul.dl1 li{ width:250px; height:19px;}

#footer{ height:16px;font-size:14px;}
.zhdl{
   width:308px;
   height:46px; 
   display:block; 
   font-size:16px; 
   color:#ffffff; 
   line-height:66px; 
   text-align:center;
   }

</style>

<script type="text/javascript" language="javascript"> 

  function doRedirect() {
   var u = document.getElementById("USERID").value;
   var m = document.getElementById("PASSWORD").value; 
   if(u==''||u=='username'){
      alert("请输入用户名！");
      return;
   }
   
   if(m==''||m==null){
      alert("请输入密码！");
      return;
   }
    
	/**
	 * 提交登陆请求
	 */
	Ext.Ajax.request({
			url : 'login.do?reqCode=login',
			success : function(response) {
				var resultArray = Ext.util.JSON.decode(response.responseText);
				if (resultArray.success===true) {
					var account = u;
					setCookie("vcf.login.account", account, 240);
					setCookie("vcf.login.userid", resultArray.userid, 240);
					setCookie("vcf.lockflag", '0', 240);
					window.location.href = 'index.do?reqCode=indexInit';
				} else {
					var errmsg = resultArray.msg;
					var errtype = resultArray.errorType;
					
					var formRedirect = function () {
						var account = document.all("USERID");
					    var password = document.all("PASSWORD"); 
								if (errtype == '1') {
									account.value = "";
									password.value = "";
									account.focus();
								} else if (errtype == '2') {
									password.focus();
									password.value = "";
								} else if (errtype == '3') {
									account.focus();
								}
							};
							alert(errmsg);
							formRedirect();
				}
			},
			failure : function(response) {
				
			},
			params : {
				account : u,
				password : m
			}
		});
     
   
  };
  
  function doKeyDown(event) {
    var keyCode = event.keyCode;
    var src = event.srcElement || event.target;
    var edt1 = document.all("USERID");
    var edt2 = document.all("PASSWORD"); 
    var BTN = document.all("BTN");
    switch (keyCode) {
        case 27:    // ESC
            edt1.value = "";
            edt2.value = "";
            return false;
            break;
        case 38:    // UP
            if (src == edt1) {
                BTN.focus();
            }
            else if (src == edt2) {
                edt1.focus();
            }
            else if (src == BTN) {
                edt2.focus();
            }
            return false;
            break;
        case 40:    // Down
            if (src == edt1) {
                edt2.focus();
            }
            else if (src == edt2) {
                BTN.focus();
            }
            else if (src == BTN) {
                edt1.focus();
            }
            return false;
            break;
        case 13:    // Enter
        case 108:   // Enter(小键盘上) 
           if (src == edt1){
               edt2.focus();
           }else if((src == edt2) || (src == BTN)){
                doRedirect();
                return true;
            }
            return false;
        }
    return false;
 };
 
</script>

<Wafa:body >

 <div id="index">
  <div class="top">
<!--    <div class="logo"><img src="resource/image/cloud/vcf.png" /></div> -->
<!--    <div class="el"><a href="javaScript:void(0)">app下载</a>　|　<a href="javaScript:void(0)">敏行官网</a>　|　<a href="help.htm">帮助中心</a>　|　<a href="javaScript:void(0)">加入收藏夹</a></div> -->
  </div>
  
  <div id="banner" class="slide">
      <div id="full-screen-slider">
            <ul id="slides">
                <li style="background: url('eon/image/cloud/12.png') no-repeat center top"><a href="javaScript:void(0)">
                    01</a></li>
            </ul>
       </div>

	<div class="login-box-a">
    <div id="J-login" class="nlogin-a login-modern-a clearfix">
   
    <div class="nav" id="nav">
     <span class="zhdl">账号登录</span>
    </div> 
    
     <ul class="dl">
      <li style="margin-bottom:21px;">
     <input id="USERID" type="text" onkeydown="doKeyDown(event)"  value=""   style="color:#000000;width:255px; height:40px; background:url(eon/image/cloud/user.png) no-repeat; line-height:40px; text-indent:40px; font-size:19px; border:none"/>
     </li>

      <li>
	 <input id="PASSWORD" onkeydown="doKeyDown(event)" type="password" value="" style="width:255px; height:40px; background:url(eon/image/cloud/pass.png) no-repeat; line-height:40px;   text-indent:40px; font-size:19px; color:#000000; border:none"/>
	  </li>

    </ul>
    <ul class="dl1">

      <li style="height:12px;line-height45px;">
       </li>
      <li>
       <a href='javascript:doRedirect();'><img id="BTN" src="resource/image/cloud/login.png" /></a>
       </li>
  
     </ul>
     

     </div><!--j-login -->
     </div><!--  login-box -->
  </div> <!--  banner-->
  <div id="footer">
  
   <p style="height:34px; line-height:34px; text-align:center; padding-top:20px;">
   法律声明　|　隐私条约　|　联系我们　|　版权所有：江苏敏行信息技术有限公司    苏ICP备11029617号 
   </p>
    
  </div>
  
  		
 
 </div><!--index-->

</Wafa:body>
<script type="text/javascript" language="javascript"> 
  if (window.ActiveXObject) {
	  $("#banner .login-box-a ul.dl").css("position","relative");
	  $("#banner .login-box-a ul.dl").css("left","-40px");
	  $("#banner .login-box-a ul.dl1").css("padding-top","0px");
  }
  var c_account = getCookie('vcf.login.account');
  var useraccount = document.getElementById("USERID");
  if (typeof c_account!='undefined') {
	useraccount.value = c_account;
  }
</script>
<%@ include file="/common/include/synclogout.jsp"%>
</Wafa:html>
