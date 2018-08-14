(function($) {
	$.fn.serializeObject = function()  
	{  
	   var o = {};  
	   var a = this.serializeArray();  
	   $.each(a, function() {  
	       if (o[this.name]) {  
	           if (!o[this.name].push) {  
	               o[this.name] = [o[this.name]];  
	           }  
	           o[this.name].push(this.value || '');  
	       } else {  
	           o[this.name] = this.value || '';  
	       }  
	   });  
	   return o;  
	};
	
    $.fn.hcheck = function(){
        if(!$('#hcheckSkin').attr('id')){
            $('head').append('<link id="hcheckSkin" rel="stylesheet" href="js/hcheck-1.2.0.css">');
        }
        $('input:radio, input:checkbox').each(function(){
           if(!$(this).next() || !$(this).next().hasClass('hcheck')){
               $(this).after('<span class="hcheck"></span>');
           }
        });
    };

})(window.jQuery);


