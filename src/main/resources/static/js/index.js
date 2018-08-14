
$(function(){
    $('body').hcheck();
    var vm = new Vue({
        el: '#warpper',
        data: {
            msg: [],
            renderMsg: [{
                name: '--',
                type: '--',
                len: '--',
                precision: '--',
                primaryKey: false,
                remark: '--'
            }],
            conns:[],
            new_source:{},
            current_datasource:{},
            select_tables:[]
        },
        mounted: function(){
        	var _this=this;
            this.$nextTick(function () {
            	_this.init();
            });
        },
        computed: {

        },
        methods: {
        	init:function(){
        		axios.get('/getConns')
                .then(function (response) {
                    if(response.data.resultCode==200){
                    	vm.conns=response.data.data;
                    	if(vm.conns!=null && vm.conns.length>0)
                		{
                    		var conn=vm.conns[0];
                    		vm.current_datasource=conn;
                    		vm.loadTables(conn.key);
                		}
                    	else
                    		vm.addConn();
                    }
                    else
                    	console.log(response.data);
                })
                .catch(function (error) {
                    console.log(error);
                });
        	},
        	cleanData:function(){
        	  vm.msg=[];
          	  vm.select_tables=[];
          	  vm.renderMsg = [{
                    name: '--',
                    type: '--',
                    len: '--',
                    precision: '--',
                    primaryKey: false,
                    remark: '--'
                }];
        	},
        	loadTables:function(key){
        	  this.cleanData();
              axios.get('/getTables/'+key)
              .then(function (response) {
                  if(response.data.resultCode==200){
                  	var data=response.data.data;
                  	for(var key in data){
                  		vm.msg.push({"key":key,"val":data[key]});
                  	}
                  }
                  else
                    $.BootstrapDialog.alertE(response.data.message,'错误');
              })
              .catch(function (error) {
                  console.log(error);
              });
        	},
            render: function(item){
            	var that = this.renderMsg=[];
                var _columns = item.val.columnList;
                for(var i=0;i<_columns.length;i++)
            	{
                  var item =_columns[i];
            	  var _column={};
            	  _column.name=item.name;
            	  _column.type=item.type;
            	  _column.len=item.len;
            	  _column.precision=item.precision;
            	  _column.primaryKey=item.primaryKey;
            	  _column.remark=item.remark;
            	  that.push(_column);
            	}
            },
            generator:function(){
            	var ignore_table="";
            	var tables="";
            	for(var i=0;i<vm.select_tables.length;i++)
        		{
            		var table=vm.select_tables[i];
        		    if(table.primaryKey.indexOf(",")>0)
    		    	{
        		    	if(ignore_table!="") ignore_table+=",";
        		    	ignore_table+=table.name;
    		    	}
        		    if(tables!="") tables+=",";
        		    tables+=table.name;
        		}
            	if(tables=="")
            	{
            		$.BootstrapDialog.alertW('请先选择要生成的表！','警告');
            	    return;
            	}
            	if(ignore_table=="")
        		{
            		$("#tables").html(tables);
            		var _dialog = $.BootstrapDialog.openContent($('.layer_generator').clone(true),
            				'信息确认',
            				{area:['560px','140px'], btn: [{
                            label: '保存',
                            action: function(dialog){
                            	var packageName=dialog.getModalContent().find('#packageName').val().trim();
                            	var param={};
                            	param.tables=tables;
                            	param.packageName=packageName;
	                        	axios.post('/generator',$.param(param))
	                            .then(function (response) {
	                                if(response.data.resultCode==200){
	                               	 $.BootstrapDialog.alertS('生成成功！','提示');
	                               	 window.location.href="/downloadZip";
	                               	 dialog.close();
	                                }
	                                else
	                               	 $.BootstrapDialog.alertE('生成失败！','错误');
	                            });
                            }
                        },
               		 {
                        label: '取消',
                        action: function(dialog){
                            dialog.close();
                        }
                    },],closable:false,draggable:false});
        		}
            	else
            		$.BootstrapDialog.alertW(ignore_table+'中含有多个主键！','警告');
            },
            addConn:function(){
            	var _dialog = $.BootstrapDialog.openContent($(".layer_addConn").clone(true),'<i class="fa fa-database"></i> 新建数据库链接',
        				{area:['560px','460px'], btn: [{ 
           		    	   label: '保存',
           		    	   action: function(dialog){
                        	 var _vals=dialog.getModalContent().find('form').serializeObject();
                        	 axios.post('/addConn',_vals)
                             .then(function (response) {
                                 if(response.data.resultCode==200){
                                	 vm.current_datasource=response.data.data;
                                	 vm.loadTables(vm.current_datasource.key);
                                	 vm.conns.push(vm.current_datasource);
                                 }
                                 else
                                	 $.BootstrapDialog.alertE(response.data.message,'错误');
                             })
                             dialog.close();
                         }
                     },
            		 {
                     label: '取消',
                     action: function(dialog){
                         dialog.close();
                     }
                 },],closable:false,draggable:false});
            	_dialog.getModalContent().find('#btn_test_conn').on('click',function(){
            		var _vals =_dialog.getModalContent().find('form').serializeObject();
            		 axios.post('/getConnTest',_vals)
                     .then(function (response) {
                         if(response.data.resultCode==200){
                        	 $.BootstrapDialog.alertS('链接成功！','提示');
                         }
                         else
                        	 $.BootstrapDialog.alertE('链接失败！','错误');
                     });
            	});
            },
            changeConn:function(){
            	var _dialog = $.BootstrapDialog.openContent($(".layer_changeConn").clone(true),'<i class="fa fa-database"></i> 切换数据源',{area:['850px','500px'], btn: [
            		 {
                     label: '取消',
                     action: function(dialog){
                         dialog.close();
                     }
                 },],closable:false,draggable:false});
            	//选择
            	_dialog.getModalContent().find('.changeConn').click(function(){
                	var key =$(this).val();
                	vm.conns.forEach(function(item){
                		if(item.key==key){
                			vm.current_datasource=item;
                       	    vm.loadTables(item.key);
                		}
                	});
                	_dialog.close();
                });
            	//删除
            	_dialog.getModalContent().find('.delConn').click(function(){
            		var _this=$(this);
                	var key =_this.attr("def-val");
                	if(key==vm.current_datasource.key)
                	{
                		$.BootstrapDialog.alertS(' 不能删除当前数据源！','提示');
                	}
                	else
                	{
	                	axios.get('/delConn?key='+key)
	                    .then(function (response) {
	                        if(response.data.resultCode==200){
	                       	 $.BootstrapDialog.alertS('删除成功！','提示');
	                       	 vm.conns.forEach(function(item,index){
	                    		if(item.key==key){
	                    			vm.conns.splice(index,1);
	                    			return;
	                    		}
	                    	 });
	                       	 //删除dom
	                       	_this.parent().parent().parent().remove();
	                        }
	                        else
	                       	 $.BootstrapDialog.alertE('删除失败！','错误');
	                    });
                	}
                });
            }
        }
    });


    /* 左侧导航条的关闭展开 */
    $('#nav_close').click(function(){
        if($(this).hasClass('fa-angle-double-left')){
            $(this).removeClass('fa-angle-double-left').addClass('fa-angle-double-right');
            $('#nav').animate({
                left: '-250px'
            });
            $('#main_container').animate({
                paddingLeft: 0
            });
        }else{
            $(this).addClass('fa-angle-double-left').removeClass('fa-angle-double-right');
            $('#nav').animate({
                left: 0
            });
            $('#main_container').animate({
                paddingLeft: '250px'
            });
        }
    });

    /* 增加删除数据源显示消失 */
    $('#search_container').hover(function(){
        $('#data_layout').stop().slideDown();
    }, function(){
        $('#data_layout').stop().slideUp();
    });

    /* 全选全不选功能 */
    $('#select_all').click(function(){
        if(this.checked){
            $('#nav_container').find('input').each(function(){
                this.checked = true;
                $(this).parent().addClass('checked');
            });
        }
        else{
            $('#nav_container').find('input').each(function(){
                this.checked = false;
                $(this).parent().removeClass('checked');
            });
        }

    });

    /* 表过滤 */
    $('#filter_input').keyup(function(){
        var filterContent = $(this).val();
        $('.nav_small_title .nav_level_two').hide();
        $('.nav_small_title .nav_level_two:contains(' + filterContent + ')').show();
    });
    $('#filter_input').change(function(){
        var filterContent = $(this).val();
        $('.nav_small_title .nav_level_two').hide();
        $('.nav_small_title .nav_level_two:contains(' + filterContent + ')').show();
    });

    /* 弹窗 */
    $('.add_data_layer').click(function(){
       vm.addConn();
    });
    
    
});













