var window_height=0;
var pagestyle=function(){ 
    var navbar = jQuery("#navbar");
	window_height = jQuery(window).height() - navbar.height()-33;
	jQuery("iframe[id^='iframe_wp']").each(function() {
   try{ 
        var iframe = jQuery(this);
        var itab = jQuery(this).closest('.tab-pane');
        iframe.width(itab.width());
//		var w = jQuery(window).width() - sliderbar.width()-5;
		if(window_height < 300) window_height = 300;
		iframe.height(window_height);
//		iframe.width(w);
     }catch (ex){} 
	});
}; 
function openURL(args){
 var type=arguments[0];
 var item_id="";
 var url="";
 if(type=="show"){
  item_id=arguments[1];	
 }
 if(type=="url"){
   url=arguments[1];
 }
 var content_id=arguments[2];
 var url_id=arguments[3];
 var parent_id=arguments[4];
 var tag_url=arguments[5];
 
 //item_id不为空，显示数据
 if(type=="show"){
 }
 //显示折叠菜单
 if(type=="show_complex"){
 }
 //url不为空加载请求数据
 if(type=="url"){
   jQuery("#sidebar ul li").removeClass("active");
   if(parent_id!=undefined){
	  //显示菜单组页面
	   jQuery("#"+parent_id).addClass("active");
	   jQuery("#"+parent_id).addClass("open");
   }
   jQuery("#"+url_id).addClass("active");
   if(url!=undefined){
     jQuery("#"+content_id,parent.document.body).attr("src",url);
     if (tag_url!=undefined) {
    		jQuery.ajax({type: 'POST',url: url,
    	        success:function(data){
    	        	jQuery("#breadcrumbs .breadcrumb").html(data);
    				 }
    		});

     }
   }
 }
 //
 pagestyle();
}
//默认执行的初始化
jQuery(document).ready(function(){

  document.body.parentNode.style.overflow="hidden";	//禁用浏览器滚动条
  jQuery(window).resize(pagestyle);//窗口重绘时候加载pagestyle函数重置窗体
});

