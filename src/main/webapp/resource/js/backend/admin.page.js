zblog.register("zblog.page");
$(function(){
  if(!document.getElementById("container")) return ;
  
  zblog.page.editor = UE.getEditor('container',{
	  autoHeightEnabled: true,
	  autoFloatEnabled: true
  });
  
  zblog.page.uploadToken=new Date().getTime()+"";
  zblog.page.editor.ready(function(){
	zblog.page.editor.execCommand('serverparam', {'uploadToken': zblog.page.uploadToken});
  });
});

zblog.page.insert=function(){
  var title=$.trim($("#title").val());
  if(title==""){
	 $("#title").focus();
	 return ;
  }

  var postid=$("#postid").val();
  var data={title:title,
	content:zblog.page.editor.getContent(),
	parent:$("#parent").val(),
	uploadToken:zblog.page.uploadToken};
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

zblog.page.remove=function(postid){
 $.ajax({
   type:"DELETE",
   url:"pages/"+postid,
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

zblog.page.edit=function(postid){
  window.location.href="pages/edit?pid="+postid;
}