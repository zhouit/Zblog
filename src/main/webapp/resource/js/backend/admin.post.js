zblog.register("zblog.post");
$(function(){
  if(!document.getElementById("container")) return ;
  
  zblog.post.editor = UE.getEditor('container',{
    /* 阻止div标签自动转换为p标签 */
    allowDivTransToP: false,
	  autoHeightEnabled: true,
	  autoFloatEnabled: true
  });
  
  zblog.post.editor.ready(function(){
	  zblog.post.editor.execCommand('serverparam', {'CSRFToken': zblog.getCookie("x-csrf-token")});
  });
});

zblog.post.insert=function(){
  var title=$.trim($("#title").val());
  if(title==""){
	 $("#title").focus();
	 return ;
  }

  var postid=$("#postid").val();
  var data={title:title, content:zblog.post.editor.getContent(), categoryid:$("#category").val()};
  if(postid.length>0) data.id=postid;
  
  $.ajax({
    type:postid.length>0?"PUT":"POST",
    url:".",
    data:data,
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

zblog.post.edit=function(postid){
  window.location.href="posts/edit?pid="+postid;
}