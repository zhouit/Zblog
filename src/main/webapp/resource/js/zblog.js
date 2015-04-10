$(function(){
  var mail = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/,
      url = /^https?:\/\/[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+?\/?$/,
   getCookie = function(name){
    var cookieValue = null;
    if(document.cookie && document.cookie != ''){
      var cookies = document.cookie.split('; ');
      for(var i = 0; i < cookies.length; i++){
        var cookie = jQuery.trim(cookies[i]);
        var index = cookie.indexOf("=");
        if(cookie.substring(0, name.length + 1) == (name + '=')){
          cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
          if(cookieValue.charAt(0) == '"')
            cookieValue = cookieValue.substring(1, cookieValue.length - 1);
          break;
        }
      }
    }

    return cookieValue;
  },
  check=function(name,value){
    value=$.trim(value);
    if(!value || value=="") return false;
    
    switch(name){
    case "email":
      return mail.test(value);
    case "url":
      return url.test(value);
    case "content":
      /* 评论中需包含中文，escape对字符串进行编码时，字符值大于255(非英文字符)的以"%u****"格式存储 */
      return escape(value).indexOf("%u")>-1;
    }
    
    return true;
  },
  generate=function(msg,data){
    var meta="<div class='comment-meta'>"
      +"<img class='avatar' width='35' height='35' src='../../resource/img/avatar.png'>"
      +"<ul class='comment-name-date'><li class='comment-name'>"
      +"<a class='url' href='"+msg.url+"' target='_blank'>"+msg.creator+"</a></li>"
      +"<li class='comment-date'>2013</li></ul></div>";
    var content="<div class='comment-content'><span class='comment-note'>你的评论正在等待审核。。。</span>"
         +"<p>"+data.content+"<p></div>";
    $("#nocomment").remove();
    var isEven;
    if(data.parent&&data.parent!=''){
      isEven=$("#comment-"+data.parent).hasClass("even_comment");
    }else{
      isEven=$(".comment").last().hasClass("even_comment");
    }
    
    var li="<li class='comment "+isEven?"odd":"even"+"_comment'>"+meta+content+"</li>";
    if(data.parent&&data.parent!=''){
      var parent=$("#comment-"+data.parent);
      if(parent.length()>0){
        parent.find("commentlist").prepend(li);
      }else{
        parent.chidlren(".comment-content").after("<ol class='commentlist'>"+li+"</ol>");
      }
    }else{
      $(".comment").first().before(li);
    }
  };
  
  $("#submit_comment").click(function(){
    var form=$("#respond").find(":text,textarea");
    if(!getCookie("comment_author")&&form.length!=4) return ;
    
    var data={};
    for(var i=0;i<form.length;i++){
      var item=form.slice(i,i+1);
      if(!check(item.attr("name"),item.val())){
        item.focus();
        return ;
      }
      
      data[item.attr("name")]=item.val();
    }
    
    $("#respond :hidden").each(function(index,item){
      data[item.name]=item.value;
    });
    
    $.post("/comments",data,function(msg){
      if(msg&&msg.success){
        $("textarea").val("");
        window.location.reload();
      }else{
        alert(msg.msg);
      }
    },"json");
  });
  
  $(".comment-reply a").each(function(){
    var t=$(this);
    t.click(function(){
      $("#cancel_comment_reply").show();
      var commentMeta=t.parent().parent();
      $("#comment_parent").val(commentMeta.parent().attr("id").substring(8));
      $("#respond").insertAfter(commentMeta.next());
      return false;
    });
  });
  
  $("#cancel_comment_reply").click(function(){
    $("#cancel_comment_reply").hide();
    $("#comment_parent").val('');
    $("#respond").insertAfter($("#comment_area"));
    return false;
  });
});