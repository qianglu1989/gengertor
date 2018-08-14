(function($) {

    function HCheck(that){
        this.addLink();
        this.initHCheck(that);
        this.showHCheck(that);
    }

    /*  hcheck定义的样式表的加载  */
    HCheck.prototype.addLink = function(){
        if(!$('#hCheckboxRadio').attr('id')){
            $(document.head).append('<link id="hCheckboxRadio" rel="stylesheet" href="js/hcheck-blue.css">');
        }
    };

    /*  初始化radio和CheckBox的样式  */
    HCheck.prototype.initHCheck = function(that){
        var _checkbox = $(that).find('input:checkbox:not(:hidden)');
        var _radio = $(that).find('input:radio:not(:hidden)');

        _checkbox.each(function(index){
            if(!$(this).parent().hasClass('hcheckbox')){
                $(this).before('<span class="hcheckbox"></span>');
                $('.hcheckbox').eq(index).append($(this));

                if(this.checked){
                    $(this).parent().addClass('checked');
                }

                if(this.disabled){
                    $(this).parent().addClass('disabled');
                }

                var _this = this;
                if($(this).parent().parent()[0].localName == 'label') {
                    $(this).parent().parent().hover(function () {
                        $(_this).parent().addClass('hover');
                    }, function () {
                        $(_this).parent().removeClass('hover');
                    });
                }else {
                    $(this).hover(function(){
                        $(this).parent().addClass('hover');
                    }, function(){
                        $(this).parent().removeClass('hover');
                    });
                }

                $(that).find('label').each(function(){
                    if($(this).attr('for')){
                        $(this).hover(function(){
                            $('#' + $(this).attr('for')).parent().addClass('hover');
                        }, function(){
                            $('#' + $(this).attr('for')).parent().removeClass('hover');
                        });
                    }
                });
            }
        });
        _radio.each(function(index){
            if(!$(this).parent().hasClass('hradio')){
                $(this).before('<span class="hradio"></span>');
                $('.hradio').eq(index).append($(this));

                if(this.checked){
                    $(this).parent().addClass('checked');
                }

                if(this.disabled){
                    $(this).parent().addClass('disabled');
                }

                var _this = this;
                if($(this).parent().parent()[0].localName == 'label') {
                    $(this).parent().parent().hover(function () {
                        $(_this).parent().addClass('hover');
                    }, function () {
                        $(_this).parent().removeClass('hover');
                    });
                }else {
                    $(this).hover(function(){
                        $(this).parent().addClass('hover');
                    }, function(){
                        $(this).parent().removeClass('hover');
                    });
                }

                $(that).find('label').each(function(){
                    if($(this).attr('for')){
                        $(this).hover(function(){
                            $('#' + $(this).attr('for')).parent().addClass('hover');
                        }, function(){
                            $('#' + $(this).attr('for')).parent().removeClass('hover');
                        });
                    }
                });
            }
        });

    };

    /*  根据单复选按钮的勾选情况显示相应的样式  */
    HCheck.prototype.showHCheck = function(that){
        $(that).on('click', 'input:checkbox:not(:hidden)', function(){
            if(this.checked){
                $(this).parent().addClass('checked');
            }else{
                $(this).parent().removeClass('checked');
            }
        });
        $(that).on('click', 'input:radio:not(:hidden)', function(){
            if(this.checked){
                $('input:radio[name="' + $(this).attr('name') + '"]').parent().removeClass('checked');
                $(this).parent().addClass('checked');
            }
        });
    };

    $.fn.hcheck = function(){
        var _hCheck = new HCheck(this);
        return _hCheck;
    };

})(window.jQuery);