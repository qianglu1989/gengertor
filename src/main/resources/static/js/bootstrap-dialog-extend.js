/**
 * jQuery 扩展函数
 *
 * jquery-extend - v1.0.0 - 2017-04-15 Copyright (c) 2017 hua
 */

(function($){
    $.BootstrapDialog={
        /********************************alert********************************/
        // 文本信息提示
        alert:function(text,title,options) {
            if(!title) title="信息";
            if(!options){
                options={};
                options.area=["300px"];
            }
            if(!options.area){
                options.area=["300px"];
            }
            var dialog=BootstrapDialog.show({
                title:title,
                closable: options.closable, // <-- Default value is true
                draggable: options.draggable, // <-- Default value is false
                message:'<div style="padding:15px;text-align: center;display: table-cell;vertical-align: middle;">'+text+'</div>'
            });
            /*自定义modal-dialog的宽度，modal-body的高度*/
            dialog.getModalDialog().css('width',options.area[0]);
            dialog.getModalBody().css('height',options.area[1]);
            //设置右上角的叉号一直显示
            dialog.getModalHeader().find('.bootstrap-dialog-close-button').css('display','block');
            return dialog;
        },
        // 成功信息提示
        alertS: function(text, title,options) {
            if(!title) title="信息";
            if(!options){
                options={};
                options.area=["300px"];
            }
            if(!options.area){
                options.area=["300px"];
            }
            var dialog=BootstrapDialog.show({
                title: title,
                message:'<div style="padding:15px;text-align: center;">'+text+'</div>',
                type: BootstrapDialog.TYPE_SUCCESS,
                closable: options.closable, // <-- Default value is true
                draggable: options.draggable, // <-- Default value is false
                buttons:[{
                    label: '确定',
                    action: function(dialog) {
                        dialog.close();

                    }
                }]
            });
            /*自定义modal-dialog的宽度，modal-body的高度*/
            dialog.getModalDialog().css('width',options.area[0]);
            dialog.getModalBody().css('height',options.area[1]);
            //设置右上角的叉号一直显示
            dialog.getModalHeader().find('.bootstrap-dialog-close-button').css('display','block');
            return dialog;
        },
        // 错误信息提示
        alertE: function(text, title,options) {
            if(!title) title="信息";
            if(!options){
                options={};
                options.area=["300px"];
            }
            if(!options.area){
                options.area=["300px"];
            }
            var dialog=BootstrapDialog.show({
                title: title,
                message:'<div style="padding:15px;text-align: center;">'+text+'</div>',
                type: BootstrapDialog.TYPE_DANGER ,
                closable: options.closable, // <-- Default value is true
                draggable: options.draggable, // <-- Default value is false
                buttons:[{
                    label: '确定',
                    action: function(dialog) {
                        dialog.close();
                    }
                }]
            });
            /*自定义modal-dialog的宽度，modal-body的高度*/
            dialog.getModalDialog().css('width',options.area[0]);
            dialog.getModalBody().css('height',options.area[1]);
            //设置右上角的叉号一直显示
            dialog.getModalHeader().find('.bootstrap-dialog-close-button').css('display','block');
            return dialog;
        },
        // 警告信息提示
        alertW: function(text, title,options) {
            if(!title) title="信息";
            if(!options){
                options={};
                options.area=["300px"];
            }
            if(!options.area){
                options.area=["300px"];
            }
            var dialog= BootstrapDialog.show({
                title: title,
                message:'<div style="padding:15px;text-align: center;">'+text+'</div>',
                type:BootstrapDialog.TYPE_WARNING , // <-- Default value is BootstrapDialog.TYPE_PRIMARY
                closable: options.closable, // <-- Default value is true
                draggable: options.draggable, // <-- Default value is false
                buttons:[{
                label: '确定',
                action: function(dialog) {
                    dialog.close();
                }
            }]
            });
            /*自定义modal-dialog的宽度，modal-body的高度*/
            dialog.getModalDialog().css('width',options.area[0]);
            dialog.getModalBody().css('height',options.area[1]);
            //设置右上角的叉号一直显示
            dialog.getModalHeader().find('.bootstrap-dialog-close-button').css('display','block');
            return dialog;

        },
        // 打开一个div
        openContent:function(content,title,options) {
            if(!title) title="信息";
            if(!options){
                options={};
                options.area=["300px"];
            }
            if(!options.area){
                options.area=["300px"];
            }
             var dialog=BootstrapDialog.show({
                title: title,
                 message:content,
                closable: options.closable, // <-- Default value is true
                draggable: options.draggable, // <-- Default value is false
                buttons:options.btn
            });
            /*自定义modal-dialog的宽度，modal-body的高度*/
            dialog.getModalDialog().css('width',options.area[0]);
            dialog.getModalBody().css('height',options.area[1]);
            //设置右上角的叉号一直显示
            dialog.getModalHeader().find('.bootstrap-dialog-close-button').css('display','block');
            return dialog;
        },
        //打开一个url
        openUrl:function(url,title,options){
            if(!title) title="信息";
            if(!options){
                options={};
                options.area=["900px","300px"];
            }
            if(!options.area){
                options.area=["900px"];
            }
            var dialog=BootstrapDialog.show({
                title: title,
                message: '<iframe style="width:100%;height:100%;border:none" src="'+url+'"></iframe>',
                closable:options.closable,
                draggable:options.draggable,
                buttons:options.btn
            });
            /*自定义modal-dialog的宽度，modal-body的高度*/
            dialog.getModalDialog().css('width',options.area[0]);
            dialog.getModalBody().css('height',options.area[1]);
            //设置右上角的叉号一直显示
            dialog.getModalHeader().find('.bootstrap-dialog-close-button').css('display','block');
            return dialog;
        },
        //打开一个文本确认框
        confirmContext:function(content,title,btn,callBack,options){
            if(!title) title="信息";
            if(!options){
                options={};
                options.area=["300px"];
            }
            if(!options.area){
                options.area=["300px"];
            }
            var dialog=BootstrapDialog.confirm({
                title: title,
                message:content,
                closable:options.closable,
                draggable:options.draggable,
                btnCancelLabel: btn.cancel, // <-- Default value is 'Cancel',
                btnOKLabel:btn.ok, // <-- Default value is 'OK',
                callback:callBack
            });
            /*自定义modal-dialog的宽度，modal-body的高度*/
            dialog.getModalDialog().css('width',options.area[0]);
            dialog.getModalBody().css('height',options.area[1]);
            //设置右上角的叉号一直显示
            dialog.getModalHeader().find('.bootstrap-dialog-close-button').css('display','block');
            return dialog;
        }
    }
}(jQuery));