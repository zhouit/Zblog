zblog.register("zblog.link");

zblog.link.remove=function(linkid){
 $.ajax({
   type:"DELETE",
   url:"links/"+linkid,
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

zblog.link.edit=function(linkid){
  window.location.href="links/edit?lid="+linkid;
}