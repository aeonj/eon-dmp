<form id="view-form" class="form-horizontal" role="form">
			<div class="form-group">
				<label class="col-sm-2 control-label no-padding-right" for="view-form-belong_name"> 权限组名称 </label>
				<div class="col-sm-10">
					<input type="text" id="view-form-belong_name" name="belong_name" value="${(obj.belong_name)!}" placeholder="请输入权限组名称" class="col-xs-10 col-sm-8" readonly="readonly"/>
				</div>
			</div>
<hr />
</form>
<div class="tabbable" id='view-tab'>
	<ul class="nav nav-tabs" id="view-tab-ul">
	</ul>

	<div class="tab-content" id="view-tab-content">
	</div>
</div>
<script type="text/html" id="view_load_title">
	{{# for(var i=0;i<d.length;i++){ }}
		<li id="view-tab-li-{{d[i].eleCode}}">
			<a aria-expanded="true" href="#view-tab-c-{{d[i].eleCode}}" data-toggle="tab">
				{{d[i].eleName}}
			</a>
		</li>
	{{# } }}
</script>
<script type="text/html" id="view_load_content">
	{{# for(var i=0;i<d.length;i++){ }}
		<div class="tab-pane fade" id="view-tab-c-{{d[i].eleCode}}">
		    <div class="control-group">
				<div class="row">
					<div class="col-xs-3">
						<div class="radio">
							<label>
								<input name="radio{{d[i].eleCode}}" type="radio" class="ace" {{# if(d[i].isPart=="0") { }} checked="checked" {{# } }} value="0" disabled/>
								<span class="lbl"> 全部权限</span>
							</label>
						</div>
					</div>
					<div class="col-xs-3">
						<div class="radio">
							<label>
								<input name="radio{{d[i].eleCode}}" type="radio" class="ace" {{# if(d[i].isPart=="1") { }} checked="checked" {{# } }} value="1" disabled/>
								<span class="lbl"> 部分权限</span>
							</label>
						</div>
					</div>
				</div>
		    </div>
			<div id="view-tab-tree-{{d[i].eleCode}}">
				<ul id="viewTree{{d[i].eleCode}}" class="ztree ztree-left"></ul>
			</div>
		</div>
	{{# } }}
</script>
<script>
var partObj = JSON.parse('${(obj.belong_source)!}');
jQuery(function($) {
    layui.use('laytpl', function() {
        var laytpl = layui.laytpl;
        laytpl(jQuery("#view_load_title").html()).render(partObj, function (html) {
            jQuery("#view-tab-ul").append(html);
        });
        laytpl(jQuery("#view_load_content").html()).render(partObj, function (html) {
            jQuery("#view-tab-content").append(html);
            loadTreeData();
        });
    });
	function loadTreeData() {
	    <#list obj.getDetailList() as detail>
		//初始化树
		assist_ztree("viewTree${detail.eleCode}",{
			source : "${detail.eleCode}",
			selectmodel : "multiple",
			isfulllevel : true,
			checkids : "${(detail.eleValue)!}"
		},{
			callback: {
				beforeCheck: function(treeId, treeNode) {
					return false;
				},
				onAsyncSuccess: function(event, treeId, treeNode, msg) {
					//disabled_ztree(treeId,true);
					//tab页高度相等
					resizeLayout();
					jQuery("ul[id^='viewTree']").each(function() {
						jQuery(this).css("height",jQuery('#view-cxj').height()-180);
					});
				}
			}
		});
		</#list>
	}
	if (jQuery('#view-tab a:first')) {
		jQuery('#view-tab a:first').tab("show");
	}

});
</script>