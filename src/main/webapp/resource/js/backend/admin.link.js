zblog.register("zblog.link");

zblog.link.remove=function(linkid){
 $.ajax({
   type:"DELETE",
   url:zblog.getDomainLink("links/"+linkid),
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

zblog.link.edit=function(linkid){
  window.location.href=zblog.getDomainLink("links/edit?lid="+linkid);
}