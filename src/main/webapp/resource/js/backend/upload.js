zblog.register("zblog.upload");

zblog.upload.uploader=WebUploader.create({
  pick:{
	id: '#picker',
	innerHTML: '点击选择文件'
  },
  dnd: '#uploader .upload-list',
  paste: document.body,
  accept:{
	title: '图片文件',
	extensions: 'gif,jpg,jpeg,bmp,png',
	mimeTypes: 'image/*'
  },
  swf:'../../webuploader-0.1.5/js/Uploader.swf',
  disableGlobalDnd: true,
  chunked: true,
  server: '.',
  fileVal: 'file',
  fileNumLimit: 300,
  fileSizeLimit: 5 * 1024 * 1024,    // 200 M
  fileSingleSizeLimit: 1 * 1024 * 1024    // 50 M
});

zblog.upload.upload=function(){
  zblog.upload.uploader.upload();
}

zblog.upload.fileCount=0;
zblog.upload.fileSize=0;
zblog.upload.addFile=function(file){
  var progress="<div class='progress progress-striped' style='display:none;'>" +
  		"<div class='progress-bar progress-bar-success'></div></div>";
  var uploadItem=$("<div id='"+file.id+"' class='upload-item'>"
      +"<img src='../../resource/img/type/document.png' /><span class='upload-item-name'>"
	  +file.name+"</span>"+progress+"<a href='#'>删除</a></div>");
  uploadItem.find("a").click(function(){
	 zblog.upload.fileCount--;
	 zblog.upload.fileSize-=file.size;
	 zblog.upload.uploader.removeFile(file);
  });
  
  $("#uploader .upload-list").append(uploadItem);
  zblog.upload.showInfo();
};

zblog.upload.removeFile=function(file){
  $("#"+file.id).remove();
  if(zblog.upload.fileCount==0){
	$("#status-bar").hide();
	$("#uploader .placeholder").show();
  }
};

//添加随传csrf参数
zblog.upload.uploader.onUploadBeforeSend=function(block, data){
  data.CSRFToken=zblog.newCsrf();
};

zblog.upload.uploader.onFileQueued=function(file){
  zblog.upload.fileCount++;
  zblog.upload.fileSize+=file.size;
  zblog.upload.addFile(file);
  //添加“添加文件”的按钮
  zblog.upload.uploader.addButton({
     id: "#append-picker",
     innerHTML: '继续添加'
  });
  
  $("#uploader .placeholder").hide();
  $("#status-bar").show();
};

zblog.upload.uploader.onFileDequeued=function(file){
  zblog.upload.removeFile(file);
};

zblog.upload.uploader.onUploadStart=function(file){
   $('#'+file.id).find(".progress").show();
};

zblog.upload.uploader.onUploadProgress=function(file, percentage){
  var item = $('#'+file.id);
  item.find('.progress-bar').css( 'width', percentage * 100 + '%' );
  if(percentage==1) item.find("a").off().text("成功");
};

zblog.upload.uploader.onUploadError=function(file,reason){
  var item = $('#'+file.id);
  item.find('.progress-bar').removeClass("progress-bar-success").addClass("progress-bar-warning");
  item.find("a").off().text("失败");
};

zblog.upload.showInfo=function(){
   $("#status-bar .info").html('选中' + zblog.upload.fileCount + '张图片，共' +
	 WebUploader.formatSize(zblog.upload.fileSize) + '。');
};