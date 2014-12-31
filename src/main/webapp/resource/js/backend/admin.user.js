zblog.register("zblog.user");

zblog.post.remove=function(userid){
 $.ajax({
   type:"DELETE",
   url:"users/"+userid,
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