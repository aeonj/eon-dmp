function jqGridLoad(gridid,config) {
	/**
	 * 
	 */
	var grid_selector = gridid;
	var pager_selector = config.pager;
	var parent_column = jQuery(grid_selector).closest('[class*="col-"]');
	//resize to fit page size
	jQuery(window).on('resize.jqGrid', function () {
		jQuery(grid_selector).jqGrid( 'setGridWidth', parent_column.width() );
    })
	
	//resize on sidebar collapse/expand
	jQuery(document).on('settings.ace.jqGrid' , function(ev, event_name, collapsed) {
		if( event_name === 'sidebar_collapsed' || event_name === 'main_container_fixed' ) {
			//setTimeout is for webkit only to give time for DOM changes and then redraw!!!
			setTimeout(function() {
				jQuery(grid_selector).jqGrid( 'setGridWidth', parent_column.width() );
			}, 20);
		}
    })
    
	jQuery(grid_selector).jqGrid($.extend({
		//direction: "rtl",
		jsonReader : {
		    repeatitems: false
	    },
		datatype : "json",
		height: 450,
		viewrecords : true,
		rowNum: 20,
		rowList: ['1000000:全部','10','20','50','100','500'],
		pager : pager_selector,
		altRows: true,
		//toppager: true,
		
		multiselect: true,
		//multikey: "ctrlKey",
        //multiboxonly: true,

		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				styleCheckbox(table);
				
				updateActionIcons(table);
				updatePagerIcons(table);
				enableTooltips(table);
			}, 0);
		}
	},config));
	jQuery(grid_selector).jqGrid('navGrid', pager_selector, {edit : false,add : false,del : false});

	jQuery(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size


	//var selr = jQuery(grid_selector).jqGrid('getGridParam','selrow');

	jQuery(document).one('ajaxloadstart.page', function(e) {
		jQuery.jgrid.gridDestroy(grid_selector);
		jQuery('.ui-jqdialog').remove();
	});

}

//it causes some flicker when reloading or navigating grid
//it may be possible to have some custom formatter to do this as the grid is being created to prevent this
//or go back to default browser checkbox styles for the grid
function styleCheckbox(table) {
/**
	jQuery(table).find('input:checkbox').addClass('ace')
	.wrap('<label />')
	.after('<span class="lbl align-top" />')


	jQuery('.ui-jqgrid-labels th[id*="_cb"]:first-child')
	.find('input.cbox[type=checkbox]').addClass('ace')
	.wrap('<label />').after('<span class="lbl align-top" />');
*/
}


//unlike navButtons icons, action icons in rows seem to be hard-coded
//you can change them like this in here if you want
function updateActionIcons(table) {
	/**
	var replacement = 
	{
		'ui-ace-icon fa fa-pencil' : 'ace-icon fa fa-pencil blue',
		'ui-ace-icon fa fa-trash-o' : 'ace-icon fa fa-trash-o red',
		'ui-icon-disk' : 'ace-icon fa fa-check green',
		'ui-icon-cancel' : 'ace-icon fa fa-times red'
	};
	jQuery(table).find('.ui-pg-div span.ui-icon').each(function(){
		var icon = jQuery(this);
		var _class = jQuery.trim(icon.attr('class').replace('ui-icon', ''));
		if(_class in replacement) icon.attr('class', 'ui-icon '+replacement[_class]);
	})
	*/
}

//replace icons with FontAwesome icons like above
function updatePagerIcons(table) {
	var replacement = 
	{
		'ui-icon-seek-first' : 'ace-icon fa fa-angle-double-left bigger-140',
		'ui-icon-seek-prev' : 'ace-icon fa fa-angle-left bigger-140',
		'ui-icon-seek-next' : 'ace-icon fa fa-angle-right bigger-140',
		'ui-icon-seek-end' : 'ace-icon fa fa-angle-double-right bigger-140'
	};
	jQuery('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
		var icon = jQuery(this);
		var _class = jQuery.trim(icon.attr('class').replace('ui-icon', ''));
		
		if(_class in replacement) icon.attr('class', 'ui-icon '+replacement[_class]);
	})
}

function enableTooltips(table) {
	jQuery('.navtable .ui-pg-button').tooltip({container:'body'});
	jQuery(table).find('.ui-pg-div').tooltip({container:'body'});
}
