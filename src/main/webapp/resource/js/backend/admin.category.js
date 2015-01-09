zblog.register("zblog.category");
$(function(){
  $.ajax({
	 type:"GET",
	 url:"categorys/index",
	 dataType:"json",
	 success:function(data){
	   if(!data) return ;
	   $('#tree').treeview({data:data,nodeIcon:"glyphicon glyphicon-star-empty"});
	 }
  });
});

zblog.category.insert=function(){
 var select = $("#tree .node-selected");
 $.ajax({
   type:"POST",
   url:"categorys",
   dataType:"json",
   data:{parent:select.text(),name:$("#newCategory").val()},
   success:function(msg){
	 if(msg&&msg.success){
	   window.location.href="categorys";
	   zdialog.hide('insert-box');
	 }else{
	   alert("添加失败"); 
		 }
	  }
  });
}

zblog.category.remove=function(){
  var select = $("#tree .node-selected").text();
  if(!select) return ;
  $.ajax({
   type:"DELETE",
   url:"categorys/"+select,
   dataType:"json",
   success:function(msg){
	 if(msg&&msg.success){
	   window.location.href="categorys";
	 }else{
	   alert("删除失败"); 
	  }
	}
  });
}