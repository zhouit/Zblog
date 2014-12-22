$(function(){
  $(".nav-header").each(function(){
	 var _this=$(this);
	 _this.click(function(){
		 var subMenu=_this.siblings();
		 if(subMenu.length==0) return ;
		 
		 if(subMenu.css("display")=="none"){
			 subMenu.slideDown();
		   _this.find(":last").removeClass("glyphicon-chevron-up");
		   _this.find(":last").addClass("glyphicon-chevron-down");
		 }else{
			subMenu.slideUp();
		    _this.find(":last").removeClass("glyphicon-chevron-down");
			_this.find(":last").addClass("glyphicon-chevron-up");
		 }
	 });
  });
})