$(function(){
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
	 $('body,html').animate({
		 scrollTop: 0
	  }, 1000, 'easeOutExpo');
	 return false;
   });
});