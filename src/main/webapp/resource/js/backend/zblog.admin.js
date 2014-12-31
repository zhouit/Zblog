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