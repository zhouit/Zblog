zblog.register("zblog.upload");

zblog.upload.remove=function(uploadid){
 $.ajax({
   type:"DELETE",
   url:"uploads/"+uploadid,
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