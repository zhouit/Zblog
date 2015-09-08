zblog.register("zblog.category");
$(function(){
  $.ajax({
	 type:"GET",
	 url:zblog.getDomainLink("categorys/index"),
	 dataType:"json",
	 success:function(data){
	   if(!data) return ;
	   $('#tree').treeview({data:data,nodeIcon:"glyphicon glyphicon-star-empty"});
	 }
  });
});

zblog.category.insert=function(){
 var newCategory=$("#newCategory").val();
 if(!newCategory) return ;
  
 var select = $("#tree .node-selected");
 $.ajax({
   type:"POST",
   url:zblog.getDomainLink("categorys"),
   dataType:"json",
   data:{parent:select.text(),name:newCategory},
   success:function(msg){
	   if(msg&&msg.success){
	     window.location.reload();
	     zdialog.hide('insert-box');
	   }else{
	      alert(msg.msg); 
		 }
	 }
  });
}

zblog.category.remove=function(){
  var select = $("#tree .node-selected").text();
  if(!select) return ;
  
  $.ajax({
   type:"DELETE",
   url:zblog.getDomainLink("categorys/"+select),
   dataType:"json",
   success:function(msg){
	 if(msg&&msg.success){
	   window.location.reload();
	 }else{
	   alert("删除失败"); 
	  }
	}
  });
}