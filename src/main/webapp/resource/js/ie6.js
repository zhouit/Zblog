$(function(){
  $("#post_mask").css("height",$(document).height());
  /* ie6 a设置display:block不能自动宽度*/
  var width=$(".post_content:eq(0)").width()-2;
  $(".title a").css("width",width);
});