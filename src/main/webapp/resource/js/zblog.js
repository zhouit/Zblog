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
  };

  $("#comment_form").submit(function(){
    var mark = true;
    if(!getCookie("comment_author")){
      var regmap = {
        '1' : mail,
        '2' : url
      };
      
      $("#respond :text").each(function(index, item){
        var value = $.trim(item.value);
        if(!value || value == ""){
          $(this).focus();
          mark = false;
          return false;
        }

        if(regmap[index + ""] && !regmap[index + ""].test(value)){
          $(this).focus();
          mark = false;
          return false;
        }
      });
    }
    
    var content = $.trim($("#comment").val());
    /* 评论中需包含中文，escape对字符串进行编码时，字符值大于255(非英文字符)的以"%u****"格式存储 */
    mark = content && content != "" && escape(content).indexOf("%u")>0;
    return mark;
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