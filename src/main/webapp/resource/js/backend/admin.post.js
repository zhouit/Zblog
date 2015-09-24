zblog.register("zblog.post");
$(function(){
  $('#editor-nav a').click(function (e) {
    e.preventDefault();
    $(this).tab('show');
    zblog.post.editType=$(this).attr("href").substring(8);
  });
  
  if(!document.getElementById("ueditor")) return ;
  
  zblog.post.editType='mk';
  zblog.post.ueditor = UE.getEditor('ueditor',{
    /* 阻止div标签自动转换为p标签 */
    allowDivTransToP: false,
	  autoHeightEnabled: true,
	  autoFloatEnabled: true
  });
  
  zblog.post.ueditor.ready(function(){
    zblog.post.ueditor.execCommand('serverparam',{'CSRFToken': zblog.newCsrf()});
  });
  
  zblog.post.epiceditor=new EpicEditor({
    basePath: window.location.protocol+"//"+window.location.port+window.location.host+"/resource/epiceditor-0.2.3",
    useNativeFullscreen: false,
    clientSideStorage: false,
    file:{
      defaultContent: $("#editor-txt-tt").val(),
      autoSave: false
    },
    autogrow: {
      minHeight: 400,
      maxHeight: 600
    }
  });
  zblog.post.epiceditor.load();
});

zblog.post.insert=function(){
  var title = $.trim($("#title").val());
  if(title==""){
	  $("#title").focus();
	  return ;
  }
  
  var _getText=function(){
    var result;
    switch(zblog.post.editType){
    case "ue":
      result = zblog.post.ueditor.getContent();
      break;
    case "txt":
      result = $("#editor-txt-tt").val();
      break;
    case "mk":
      result = zblog.post.epiceditor.getElement('previewer').body.innerHTML;
      break;
    default: result="";
    }
    
    return result;
  }

  var postid=$("#postid").val();
  var data={title : title,
        content : _getText(),
        tags : $("#tags").val(),
        categoryid : $("#category").val(),
        pstatus : $("input:radio[name=pstatus]:checked").val(),
        cstatus : $("input:radio[name=cstatus]:checked").val()
      };
  if(postid.length>0) data.id=postid;
  
  $.ajax({
    type:postid.length>0?"PUT":"POST",
    url:zblog.getDomainLink("posts"),
    data:data,
    dataType:"json",
    success:function(data){
	    if(data&&data.success){
	      window.location.href=".";
      }else{
    	 alert(data.msg);
      }
     }
  });
}

zblog.post.remove=function(postid){
 alert(window.location.host);
 $.ajax({
   type:"DELETE",
   url:zblog.getDomainLink("posts/"+postid),
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

zblog.post.fastedit=function(postid){
  if(zblog.post.fastIndex){
    $("#edit-"+zblog.post.fastIndex).show();
  }
  
  zblog.post.fastIndex=postid;
  var editRow=$("#edit-row");
  var editPost=$("#edit-"+postid);
  
  editRow.find("input[name='title']").val(editPost.find(".post-title").text());
  editRow.find("input[name='createTime']").val(editPost.find(".post-ctime").text());
  editRow.find(".er-author").text(editPost.find(".post-author").text());
  editRow.find("#tags").text(editPost.find(".post-tags").text());
  
  var category=editPost.find(".post-category").text();
  editRow.find("input[name='category']").each(function(item){
     $(this).prop("checked",category == $(this).parent().text());
  })
  
  var pstatus=editPost.find(".post-ps").text();
  editRow.find("input[name='pstatus'][value='"+pstatus+"']").prop("checked",true);
  
  var cstatus=editPost.find(".post-cs").text();
  editRow.find("input[name='cstatus'][value='"+cstatus+"']").prop("checked",true);
  
  editPost.hide();
  editPost.after(editRow);
  editRow.show();
}

zblog.post.canclefast=function(){
  if(zblog.post.fastIndex){
    $("#edit-"+zblog.post.fastIndex).show();
  }
  zblog.post.fastIndex=null;
  $("#edit-row").hide();
}

zblog.post.submitfast=function(){
  var editRow=$("#edit-row");
  
  var data={id:zblog.post.fastIndex,
        title : editRow.find("input[name='title']").val(),
        tags : editRow.find("#tags").val(),
        categoryid : editRow.find("input[name='category']:checked").val(),
        pstatus : editRow.find("input:radio[name='pstatus']:checked").val(),
        cstatus : editRow.find("input:radio[name='cstatus']:checked").val()
      };
  
  $.ajax({
    type:"PUT",
    url:zblog.getDomainLink("posts/fast"),
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

zblog.post.edit=function(postid){
  window.location.href=zblog.getDomainLink("posts/edit?pid="+postid);
}