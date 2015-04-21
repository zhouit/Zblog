zblog.register("zblog.upload");

zblog.upload.remove=function(uploadid){
 $.ajax({
   type:"DELETE",
   url:zblog.getDomainLink("uploads/"+uploadid),
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