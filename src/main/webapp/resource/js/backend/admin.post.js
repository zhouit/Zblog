zblog.register("zblog.post");
$(function(){
  if(!document.getElementById("container")) return ;
  
  zblog.post.editor = UE.getEditor('container',{
	  autoHeightEnabled: true,
	  autoFloatEnabled: true
  });
  
  zblog.post.uploadToken=new Date().getTime()+"";
  zblog.post.editor.ready(function(){
	zblog.post.editor.execCommand('serverparam', {'uploadToken': zblog.post.uploadToken});
  });
});

zblog.post.insert=function(){
  var title=$.trim($("#title").val());
  if(title==""){
	 $("#title").focus();
	 return ;
  }
	
  $.ajax({
    type:"POST",
    url:".",
    data:{title:title,
	    content:zblog.post.editor.getContent(),
	    categoryid:$("#category").val(),
	    uploadToken:zblog.post.uploadToken},
    dateType:"json",
    success:function(data){
	  if(data&&data.success){
	     window.location.href=".";
      }else if(data){
    	 alert(data.msg);
      }
     }
  });
}

zblog.post.remove=function(postid){
 $.ajax({
   type:"DELETE",
   url:"posts/"+postid,
   dateType:"json",
   success:function(data){
	if(data&&data.success){
	  window.location.href=".";
    }else if(data){
      alert(data.msg);
     }
   }
 });
}