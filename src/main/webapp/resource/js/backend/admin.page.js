zblog.register("zblog.page");
$(function(){
  $('#editor-nav a').click(function (e) {
    e.preventDefault();//阻止a链接的跳转行为
    $(this).tab('show');//显示当前选中的链接及关联的content
  });
  
  if(!document.getElementById("container")) return ;
  
  zblog.page.editor = UE.getEditor('container',{
    /* 阻止div标签自动转换为p标签 */
    allowDivTransToP: false,
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
    url:zblog.getDomainLink("pages"),
    data:data,
    dataType:"json",
    success:function(data){
	    if(data&&data.success){
	       window.location.reload();
      }else{
    	  alert(data.msg);
      }
     }
  });
}

zblog.page.remove=function(postid){
 $.ajax({
   type:"DELETE",
   url:zblog.getDomainLink("pages/"+postid),
   dataType:"json",
   success:function(data){
	  if(data&&data.success){
	    window.location.reload();
    }else{
      alert(data.msg);
     }
   }
 });
}

zblog.page.edit=function(postid){
  window.location.href=zblog.getDomainLink("pages/edit?pid="+postid);
}