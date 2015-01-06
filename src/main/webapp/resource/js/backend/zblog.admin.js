if(typeof com=="undefined"){
  var zblog={};
}

//增加命名空间  使用方法：zblog.register('bbb.ccc','admin.zhou');
zblog.register =function(){
  var result={},temp;
  for(var i=0;i<arguments.length;i++){
	 temp=arguments[i].split(".");
	 result=window[temp[0]]=window[temp[0]] || {};
	 for(var j=0;j<temp.slice(1).length;j++){
	   result=result[temp[j+1]]=result[temp[j+1]] || {};
	  }
   }
  
  return result;
}

$(document).ajaxSend(function(event, xhr, settings){
   function getCookie(name){
     var cookieValue = null;
     if(document.cookie && document.cookie != ''){
        var cookies = document.cookie.split('; ');
        for(var i = 0; i < cookies.length; i++){
           var cookie=jQuery.trim(cookies[i]);
           var index = cookie.indexOf("=");
           if(cookie.substring(0,index)==name){
             cookieValue = decodeURIComponent(cookie.substring(index+2,cookie.length-1));
             break;
           }
        }
      }
     
     return cookieValue;
   }
 
   if(!/^(GET|HEAD|OPTIONS|TRACE)$/.test(settings.type)){
	 /* 此处token值可以放在cookie中 */
     xhr.setRequestHeader("CSRFToken", getCookie("x-csrf-token"));
   }
});