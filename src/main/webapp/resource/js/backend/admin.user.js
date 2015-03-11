zblog.register("zblog.user");

zblog.user.remove=function(userid){
 $.ajax({
   type:"DELETE",
   url:"users/"+userid,
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
  window.location.href="users/edit?uid="+userid;
}