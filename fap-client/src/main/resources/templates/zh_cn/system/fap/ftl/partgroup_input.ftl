<form id="edt-form" class="form-horizontal" role="form">
			<input type="hidden" name="id" value="${(obj.id)!}"/>
			<div class="form-group">
				<label class="col-sm-2 control-label no-padding-right" for="edt-form-belong_name"> 权限组名称 </label>
				<div class="col-sm-10">
					<input type="text" id="edt-form-belong_name" name="belong_name" value="${(obj.belong_name)!}" placeholder="请输入权限组名称" class="col-xs-10 col-sm-8" />
				</div>
			</div>
<hr />
</form>
<div class="tabbable" id='edt-tab'>
	<ul class="nav nav-tabs" id="edt-tab-ul">
		<li>
			<a aria-expanded="true" href="#" id="btn_dec">
				<i class="blue ace-icon glyphicon glyphicon-minus bigger-120"></i>
			</a>
		</li>
		<li>
			<a aria-expanded="true" href="#" id="btn_add">
				<i class="blue ace-icon glyphicon glyphicon-plus bigger-120"></i>
			</a>
		</li>
	</ul>

	<div class="tab-content" id="edt-tab-content">
	</div>
</div>
<div class="modal fade" id="edt-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h5 class="modal-title">添加要素</h5>
            </div>
            <div class="modal-body">
				<form id="edt-modal-form" class="form-horizontal" role="form">
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="edt-modal-eleCode"> 选择要素 </label>
						<div class="col-sm-9">
							<div class="input-group input-group-sm col-xs-10 col-sm-8">
								<input type="text" id="edt-modal-eleCode" name="eleCode" placeholder="请选择要素" class="form-control" />
								<span class="input-group-addon">
									<i class="fa fa-list-ul bigger-110"></i>
								</span>
							</div>
						</div>
					</div>
				</form>
			</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-sm btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-sm btn-primary" id="btn-addele">添加</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<script type="text/html" id="tab_load_title">
	{{# for(var i=0;i<d.length;i++){ }}
		<li id="edt-tab-li-{{d[i].eleCode}}">
			<a aria-expanded="true" href="#edt-tab-c-{{d[i].eleCode}}" data-toggle="tab">
				{{d[i].eleName}}
			</a>
		</li>
	{{# } }}
</script>
<script type="text/html" id="tab_load_content">
	{{# for(var i=0;i<d.length;i++){ }}
		<div class="tab-pane fade" id="edt-tab-c-{{d[i].eleCode}}">
		    <div class="control-group">
				<div class="row">
					<div class="col-xs-3">
						<div class="radio">
							<label>
								<input name="radio{{d[i].eleCode}}" type="radio" class="ace" {{# if(d[i].isPart=="0") { }} checked="checked" {{# } }} value="0" onclick="partcheck('{{d[i].eleCode}}',this.value)"/>
								<span class="lbl"> 全部权限</span>
							</label>
						</div>
					</div>
					<div class="col-xs-3">
						<div class="radio">
							<label>
								<input name="radio{{d[i].eleCode}}" type="radio" class="ace" {{# if(d[i].isPart=="1") { }} checked="checked" {{# } }} value="1" onclick="partcheck('{{d[i].eleCode}}',this.value)"/>
								<span class="lbl"> 部分权限</span>
							</label>
						</div>
					</div>
				</div>
		    </div>
			<div id="edt-tab-tree-{{d[i].eleCode}}">
				<ul id="edtTree{{d[i].eleCode}}" class="ztree ztree-left"></ul>
			</div>
		</div>
	{{# } }}
</script>
<script type="text/html" id="tab_add_title">
	<li id="edt-tab-li-{{d.eleCode}}">
		<a aria-expanded="true" href="#edt-tab-c-{{d.eleCode}}" data-toggle="tab">
			{{d.eleName}}
		</a>
	</li>
</script>
<script type="text/html" id="tab_add_content">
	<div class="tab-pane fade" id="edt-tab-c-{{d.eleCode}}">
	    <div class="control-group">
			<div class="row">
				<div class="col-xs-3">
					<div class="radio">
						<label>
							<input name="radio{{d.eleCode}}" type="radio" class="ace" value="0" onclick="partcheck('{{d.eleCode}}',this.value)"/>
							<span class="lbl"> 全部权限</span>
						</label>
					</div>
				</div>
				<div class="col-xs-3">
					<div class="radio">
						<label>
							<input name="radio{{d.eleCode}}" type="radio" class="ace" checked="checked" value="1" onclick="partcheck('{{d.eleCode}}',this.value)"/>
							<span class="lbl"> 部分权限</span>
						</label>
					</div>
				</div>
			</div>
	    </div>
		<div id="edt-tab-tree-{{d.eleCode}}">
			<ul id="edtTree{{d.eleCode}}" class="ztree ztree-left"></ul>
		</div>
	</div>
</script>
<script>
<#if (op_flag=='edit')>
var jsonObj = JSON.parse('${obj.belong_source!}');
<#else>
var jsonObj = [];
</#if>
jQuery(function($) {
	//tab页高度相等
	jQuery('#edt-tab-content').height(300);

	jQuery('#edt-form').validate({
		errorElement: 'div',
		errorClass: 'help-block',
		focusInvalid: false,
		ignore: "",
		rules: {
			belong_name: "required"
		},
		messages: {
			belong_name: "请输入权限组名称"
		},
		highlight: function (e) {
			jQuery(e).closest('.form-group').removeClass('has-info').addClass('has-error');
		},
		success: function (e) {
			jQuery(e).closest('.form-group').removeClass('has-error');//.addClass('has-info');
			jQuery(e).remove();
		}
	});

	jQuery('#edt-modal-form').validate({
		errorElement: 'div',
		errorClass: 'help-block',
		focusInvalid: false,
		ignore: "",
		rules: {
			eleCode: "required"
		},
		messages: {
			eleCode: "请选择要素"
		},
		highlight: function (e) {
			jQuery(e).closest('.form-group').removeClass('has-info').addClass('has-error');
		},
		success: function (e) {
			jQuery(e).closest('.form-group').removeClass('has-error');//.addClass('has-info');
			jQuery(e).remove();
		}
	});

	jQuery("#btn-addele").click(function(){
		var eleCode = jQuery("#edt-modal-eleCode").prev().val();
		if (eleCode=="") {
			return;
		}
		if (containsArray(jsonObj,eleCode,'eleCode')) {
			layer.alert('要素已存在，请重新选择');
			return;
		}
		var data = {eleCode:eleCode,eleName:jQuery("#edt-modal-eleCode").val(),isPart:"1"};
		jsonObj.push(data);
        layui.use('laytpl', function() {
            var laytpl = layui.laytpl;
            laytpl(jQuery("#tab_add_title").html()).render(data, function (html) {
                jQuery("#btn_dec").parent().before(html);
            });
            laytpl(jQuery("#tab_add_content").html()).render(data, function (html) {
                jQuery("#edt-tab-content").append(html);
            });
        });
		//初始化菜单树
		assist_ztree("edtTree"+data.eleCode,{
			source : data.eleCode,
			selectmodel : "multiple",
			isfulllevel : true
		});
		jQuery('#edt-tab-ul a[href="#edt-tab-c-'+data.eleCode+'"]').tab('show');
		jQuery("#edtTree"+data.eleCode).height(250);
		jQuery('#edt-modal').modal("hide");
	});
	
	jQuery("#btn_add").click(function(){
		jQuery('#edt-modal').modal({
		        keyboard: true
	    });
	});
	jQuery("#btn_dec").click(function(){
		var elobj = jQuery("#edt-tab-ul li.active");
		if (!elobj.length) {
			return;
		}
		//找到对应li标签的id
		var li_id = elobj.attr('id');
		var eleCode = li_id.replace("edt-tab-li-","");
		var index = findArray(jsonObj,eleCode,'eleCode');
		if (index==-1) {
			return;
		}
		layer.confirm("确认要删除"+jsonObj[index].eleName+"要素吗？",{btn:["确认","取消"]},function(index) {
			layer.close(index);
		    //激活他的前一个TAB
			var new_active = elobj.prev();
			var can_active = null;
			if (new_active.length) {
				can_active = new_active.find("a");	
			} else {
				new_active = elobj.next();
				if (new_active.find("a").attr('href')!="#") {
					can_active = new_active.find("a");	
				}
			}
		    //关闭TAB
		    jQuery('#edt-tab-c-'+eleCode).remove();
		    elobj.remove();
		    if (can_active!=null)
		    	can_active.tab("show");
			//删除json对象
			removeArray(jsonObj,eleCode,"eleCode");
		});
	});
	jQuery('#edt-modal-eleCode').treefield({source : 'ELE',istable:true,isCodeAsValue:true})
	.next().on('click', function(){
		jQuery(this).prev().focus();
	});
	<#if (op_flag=='edit')>
	layui.use('laytpl', function() {
	    var laytpl = layui.laytpl;
        laytpl(jQuery("#tab_load_title").html()).render(jsonObj, function (html) {
            jQuery("#btn_dec").parent().before(html);
        });
        laytpl(jQuery("#tab_load_content").html()).render(jsonObj, function (html) {
            jQuery("#edt-tab-content").append(html);
            loadTreeData();
        });
    });
	function loadTreeData() {
	    <#list obj.getDetailList() as detail>
		//初始化树
		assist_ztree("edtTree${detail.eleCode}",{
			source : "${detail.eleCode}",
			selectmodel : "multiple",
			isfulllevel : true,
			checkids : "${(detail.eleValue)!}"
		},{
			callback: {
				onAsyncSuccess: function(event, treeId, treeNode, msg) {
					var index${detail.eleCode} = findArray(jsonObj,'${detail.eleCode}','eleCode');
					if (index${detail.eleCode}!=-1) {
						partcheck('${detail.eleCode}',jsonObj[index${detail.eleCode}].isPart);
					}
				}
			}
		});
		</#list>
	}
	if (jQuery('#edt-tab a:first').attr('href')!="#") {
		jQuery('#edt-tab a:first').tab("show");
	}
	</#if>

});
function partcheck(code,val) {
	if (val=="1") {
		disabled_ztree("edtTree"+code,false);
	} else {
		disabled_ztree("edtTree"+code,true);
	}
}
</script>