zblog.register("zblog.comment");

zblog.comment.remove=function(commentid){
 $.ajax({
   type:"DELETE",
   url:"comments/"+commentid,
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

zblog.comment.approve=function(commentid){
  console.info(event.target);
  $.ajax({
    type:"PUT",
    url:"comments/"+commentid,
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