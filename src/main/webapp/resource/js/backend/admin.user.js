zblog.register("zblog.user");

zblog.user.remove=function(userid){
 $.ajax({
   type:"DELETE",
   url:zblog.getDomainLink("users/"+userid),
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

zblog.user.edit=function(userid){
  window.location.href=zblog.getDomainLink("users/edit?uid="+userid);
}