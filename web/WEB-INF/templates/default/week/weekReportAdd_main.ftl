<script language="JavaScript">
    $("#menu000500").addClass("active");
    $("#menu000500").addClass("open");
    $("#menu000501").addClass("active");
</script>
<div class="page-content">
    <div class="page-header">
        <h1>
            周工作汇报
            <small>
                <i class="icon-double-angle-right"></i>
                工作汇报新增/编辑
            </small>
        </h1>
    </div>
    <!-- /.page-header -->

    <div class="row">
        <div class="col-xs-12">
            <!-- PAGE CONTENT BEGINS -->
            <div class="row">
                <div class="col-xs-12">
                    <div class="widget-box">
                        <div class="widget-header">
                            <h4></h4>
                        </div>

                        <div class="widget-body">
                            <div class="widget-main no-padding">
                                <form id="searchForm" action="/week/reportAdd" method="post">
                                <input type="hidden" id="pros" value="${pros}">
                                <input type="hidden" id="prodetails" value="${prodetails}">
                                    <table class="table table-stript table-bordered">
                                        <tr>
                                            <td class="align-center col-xs-2">
                                                门店
                                            </td>
                                            <td class="col-xs-4">
                                                <select class="form-control col-xs-4" id="shops" name="shop">
                                                <#if shops??>
                                                    <#list shops as item>
                                                            <Option value="${item[0]},${item[1]}" ${item[2]}>${item[0]},${item[1]}</Option>
                                                    </#list>
                                                </#if>
                                                </select>
                                            </td>
                                            <td class="align-center col-xs-2">
                                                门店等级
                                            </td>
                                            <td class="">
                                                <div class="">
                                                    <#if shoplevel??>${shoplevel}</#if>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="align-center col-xs-2">
                                                月份
                                            </td>
                                            <td class="">
                                                <div class="">
                                                    <#if month??>${month}</#if>
                                                </div>
                                            </td>
                                            <td class="align-center col-xs-2">
                                                评估日期
                                            </td>
                                            <td class="">
                                                <div class="">
                                                    <#if pgdate??>${pgdate}</#if>
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12">
                    
                    <div class="table-responsive">


							<table id="grid-table"></table>

							<div id="grid-pager"></div>

							
							<script type="text/javascript">
			window.jQuery || document.write("<script src='/assets/js/jquery-2.0.3.min.js'>"+"<"+"/script>");
		</script>

		<!-- <![endif]-->

		<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='/assets/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
</script>
<![endif]-->

		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="/assets/js/bootstrap.min.js"></script>
		<script src="/assets/js/typeahead-bs2.min.js"></script>

		<!-- page specific plugin scripts -->

		<script src="/assets/js/date-time/bootstrap-datepicker.min.js"></script>
		<script src="/assets/js/jqGrid/jquery.jqGrid.min.js"></script>
		<script src="/assets/js/jqGrid/i18n/grid.locale-cn.js"></script>

		<!-- ace scripts -->

		<!-- inline scripts related to this page -->

		<script type="text/javascript">
		
			jQuery(function($) {
				var grid_selector = "#grid-table";
				var pager_selector = "#grid-pager";
				var url = "/week/getJsondata?shop="+document.getElementById('shops').value;
				var editurl = "/week/operJsondata?shop="+document.getElementById('shops').value;
				var pro = document.getElementById('pros').value;
				var prodetails = document.getElementById('prodetails').value;
	
				jQuery(grid_selector).jqGrid({
					url:url,
					datatype: "json",
					type: "POST",
					height: 250,
					colNames:[' ', '编号','问题项目','问题描述','总部支援', '解决方案', '需要资源','追踪结果一','追踪结果二','追踪结果三','追踪结果四','追踪结果五','完成日期'],
					colModel:[
						{name:'myac',index:'', width:80, fixed:true, sortable:false, resize:false,
							formatter:'actions', 
							formatoptions:{ 
								keys:true,
								delOptions:{recreateForm: true, beforeShowForm:beforeDeleteCallback},
								//editformbutton:true, editOptions:{recreateForm: true, beforeShowForm:beforeEditCallback}
							}
						},
						{name:'id',index:'编号', width:80, editable: false},
						{name:'questionValue',index:'问题项目', width:80,editable: true,edittype:"select",editoptions:{value:pro,dataEvents: [
  {type: 'change',fn: function(e) {
 						var str = "";
                           $.ajax({
                               url: '/week/getpros',
                               type: "POST",
                               async: false,
                               cache: false,
                               dataType: "text",
                               data:{
                                    pro:this.value  //传入值，到后台获取json
                                },
                               success: function(json) {
                                    str=json
                                }
                           });
                           

                           if(null!=this.parentNode.parentNode.parentNode.childNodes[3]){
                           this.parentNode.parentNode.parentNode.childNodes[3].childNodes[1].childNodes[1].innerHTML=str;
                           }
                           else if (null != this.parentNode.parentNode.childNodes[4]) {
                          	 this.parentNode.parentNode.childNodes[4].childNodes[0].innerHTML=str;
                           }
  }}]}},
						{name:'questionDescribe',index:'问题描述',width:80,editable: true,edittype:"select",editoptions:{value:prodetails}},
						{name:'attr11',index:'总部支援', width:80,editable: true,edittype:"select",editoptions:{value:"N:否;Y:是"}},
						{name:'sovle',index:'解决方案', width:80, editable: true},
						{name:'needResource',index:'需要资源', width:80, editable: true},
						{name:'storeResult',index:'追踪结果一', width:90, editable: true},
						{name:'attr13',index:'追踪结果二', width:90, editable: true},
						{name:'attr14',index:'追踪结果三', width:90, editable: true},
						{name:'attr15',index:'追踪结果四', width:90, editable: true},
						{name:'attr12',index:'追踪结果五', width:90, editable: true},
						{name:'sdate',index:'完成日期', width:80, editable:true, sorttype:"date",unformat: pickDate} 
					], 
			
					viewrecords : true,
					rowNum:10,
					rowList:[10,20,30],
					pager : pager_selector,
					altRows: true,
					//toppager: true,
					
					multiselect: true,
					//multikey: "ctrlKey",
			        multiboxonly: true,
			
					loadComplete : function() {
						var table = this;
						setTimeout(function(){
							styleCheckbox(table);
							
							updateActionIcons(table);
							updatePagerIcons(table);
							enableTooltips(table);
						}, 0);
					},
			
					editurl: editurl,//nothing is saved
					
					
					jsonReader:{ // 从服务端返回的实际数据，名字随意起，但是在Action类中必须有与之匹配的属性
				        repeatitems : false            
				    },
			
					autowidth: true
			
				});
			
				//enable search/filter toolbar
				//jQuery(grid_selector).jqGrid('filterToolbar',{defaultSearch:true,stringResult:true})
			
				//switch element when editing inline
				function aceSwitch( cellvalue, options, cell ) {
					setTimeout(function(){
						$(cell) .find('input[type=checkbox]')
								.wrap('<label class="inline" />')
							.addClass('ace ace-switch ace-switch-5')
							.after('<span class="lbl"></span>');
					}, 0);
				}
				//enable datepicker
				function pickDate( cellvalue, options, cell ) {
					setTimeout(function(){
						$(cell).find('input[type=text]').datepicker({format:'yyyy-mm-dd' , autoclose:true}); 
					}, 0);
				}
			
			
				//navButtons
				jQuery(grid_selector).jqGrid('navGrid',pager_selector,
					{ 	//navbar options
						edit: true,
						editicon : 'icon-pencil blue',
						add: true,
						addicon : 'icon-plus-sign purple',
						del: true,
						delicon : 'icon-trash red',
						search: true,
						searchicon : 'icon-search orange',
						refresh: true,
						refreshicon : 'icon-refresh green',
						view: true,
						viewicon : 'icon-zoom-in grey',
					},
					{
						//edit record form
						//closeAfterEdit: true,
						recreateForm: true,
						beforeShowForm : function(e) {
							var form = $(e[0]);
							form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
							style_edit_form(form);
						}
					},
					{
						//new record form
						closeAfterAdd: true,
						recreateForm: true,
						viewPagerButtons: false,
						beforeShowForm : function(e) {
							var form = $(e[0]);
							form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
							style_edit_form(form);
						}
					},
					{
						//delete record form
						recreateForm: true,
						beforeShowForm : function(e) {
							var form = $(e[0]);
							if(form.data('styled')) return false;
							
							form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
							style_delete_form(form);
							
							form.data('styled', true);
						},
						onClick : function(e) {
							alert(1);
						}
					},
					{
						//search form
						recreateForm: true,
						afterShowSearch: function(e){
							var form = $(e[0]);
							form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
							style_search_form(form);
						},
						afterRedraw: function(){
							style_search_filters($(this));
						}
						,
						multipleSearch: true,
						/**
						multipleGroup:true,
						showQuery: true
						*/
					},
					{
						//view record form
						recreateForm: true,
						beforeShowForm: function(e){
							var form = $(e[0]);
							form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
						}
					}
				)
			
			
				
				function style_edit_form(form) {
					//enable datepicker on "sdate" field and switches for "stock" field
					form.find('input[name=sdate]').datepicker({format:'yyyy-mm-dd' , autoclose:true})
						.end().find('input[name=stock]')
							  .addClass('ace ace-switch ace-switch-5').wrap('<label class="inline" />').after('<span class="lbl"></span>');
			
					//update buttons classes
					var buttons = form.next().find('.EditButton .fm-button');
					buttons.addClass('btn btn-sm').find('[class*="-icon"]').remove();//ui-icon, s-icon
					buttons.eq(0).addClass('btn-primary').prepend('<i class="icon-ok"></i>');
					buttons.eq(1).prepend('<i class="icon-remove"></i>')
					
					buttons = form.next().find('.navButton a');
					buttons.find('.ui-icon').remove();
					buttons.eq(0).append('<i class="icon-chevron-left"></i>');
					buttons.eq(1).append('<i class="icon-chevron-right"></i>');		
				}
			
				function style_delete_form(form) {
					var buttons = form.next().find('.EditButton .fm-button');
					buttons.addClass('btn btn-sm').find('[class*="-icon"]').remove();//ui-icon, s-icon
					buttons.eq(0).addClass('btn-danger').prepend('<i class="icon-trash"></i>');
					buttons.eq(1).prepend('<i class="icon-remove"></i>')
				}
				
				function style_search_filters(form) {
					form.find('.delete-rule').val('X');
					form.find('.add-rule').addClass('btn btn-xs btn-primary');
					form.find('.add-group').addClass('btn btn-xs btn-success');
					form.find('.delete-group').addClass('btn btn-xs btn-danger');
				}
				function style_search_form(form) {
					var dialog = form.closest('.ui-jqdialog');
					var buttons = dialog.find('.EditTable')
					buttons.find('.EditButton a[id*="_reset"]').addClass('btn btn-sm btn-info').find('.ui-icon').attr('class', 'icon-retweet');
					buttons.find('.EditButton a[id*="_query"]').addClass('btn btn-sm btn-inverse').find('.ui-icon').attr('class', 'icon-comment-alt');
					buttons.find('.EditButton a[id*="_search"]').addClass('btn btn-sm btn-purple').find('.ui-icon').attr('class', 'icon-search');
				}
				
				function beforeDeleteCallback(e) {
					var form = $(e[0]);
					if(form.data('styled')) return false;
					
					form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
					style_delete_form(form);
					
					form.data('styled', true);
				}
				
				function beforeEditCallback(e) {
					var form = $(e[0]);
					form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
					style_edit_form(form);
				}
			
			
			
				//it causes some flicker when reloading or navigating grid
				//it may be possible to have some custom formatter to do this as the grid is being created to prevent this
				//or go back to default browser checkbox styles for the grid
				function styleCheckbox(table) {
				/**
					$(table).find('input:checkbox').addClass('ace')
					.wrap('<label />')
					.after('<span class="lbl align-top" />')
			
			
					$('.ui-jqgrid-labels th[id*="_cb"]:first-child')
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
						'ui-icon-pencil' : 'icon-pencil blue',
						'ui-icon-trash' : 'icon-trash red',
						'ui-icon-disk' : 'icon-ok green',
						'ui-icon-cancel' : 'icon-remove red'
					};
					$(table).find('.ui-pg-div span.ui-icon').each(function(){
						var icon = $(this);
						var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
						if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
					})
					*/
				}
				
				//replace icons with FontAwesome icons like above
				function updatePagerIcons(table) {
					var replacement = 
					{
						'ui-icon-seek-first' : 'icon-double-angle-left bigger-140',
						'ui-icon-seek-prev' : 'icon-angle-left bigger-140',
						'ui-icon-seek-next' : 'icon-angle-right bigger-140',
						'ui-icon-seek-end' : 'icon-double-angle-right bigger-140'
					};
					$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
						var icon = $(this);
						var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
						
						if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
					})
				}
			
				function enableTooltips(table) {
					$('.navtable .ui-pg-button').tooltip({container:'body'});
					$(table).find('.ui-pg-div').tooltip({container:'body'});
				}
			
				//var selr = jQuery(grid_selector).jqGrid('getGridParam','selrow');
			
			
			});
		</script>


                        
                    </div>
                </div>
            </div>
        </div>

        <!-- PAGE CONTENT ENDS -->
    </div>
    <!-- /.col -->
</div>
<!-- /.row -->