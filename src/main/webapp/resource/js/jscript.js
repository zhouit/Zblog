$(function(){
  var menu_show=false;
  $("#menu_button").click(function(){
    if(menu_show){
      $("#header").animate({"right":0},500);
      $("#content").animate({"right":0},500,function(){
        $("#menu").hide();
      });
    }else{
      $("#menu").css("height",$(document).height()).show();
      $("#header").animate({"right":"70%"},500);
      $("#content").animate({"right":"70%"},500);
    }
    
    menu_show = !menu_show;
    
    return false;
  });
  
  $(window).bind('resize', function() {
    if($(window).width()>770&&menu_show){
      $("#header").css("right", 0);
      $("#content").css("right", 0);
      $("#menu").css("height","auto").show();
    }
  });
  
  var ie6=!-[1,]&&!window.XMLHttpRequest;
  if(ie6) return ;
  
  jQuery.easing.easeOutExpo = function (x, t, b, c, d) {
	  return -c * ((t=t/d-1)*t*t*t - 1) + b;
   };
	
  var topBtn = $('#return_top');	
  topBtn.hide();
  $(window).scroll(function () {
	  if ($(this).scrollTop() > 100) {
	   	topBtn.fadeIn();
	  } else {
		   topBtn.fadeOut();
	   }
   });
  
   topBtn.click(function () {
	   $('body,html').animate({scrollTop: 0}, 1000, 'easeOutExpo');
	   return false;
   });
});