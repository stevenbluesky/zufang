define('static/libs/util/upload', ['require', 'exports', 'module', 'components/jquery/jquery', 'static/libs/util/common', 'components/webuploader/webuploader'], function(require, exports, module) {

  var $ = require('components/jquery/jquery'),
  	common = require('static/libs/util/common'),
  	WebUploader = require('components/webuploader/webuploader'),
  	tmpl = function(obj){
var __t,__p='',__j=Array.prototype.join,print=function(){__p+=__j.call(arguments,'');};
with(obj||{}){
__p+='';
if(type==0){
__p+='\r\n\t<!--dom结构部分-->\r\n\t<div class="u-uploaderBox">\r\n\t    <!--用来存放item-->\r\n\t    <div id="fileList" class="uploader-list f-cb"></div>\r\n\t    <div id="filePicker">选择图片</div>\r\n\t</div>\r\n';
}else if(type == 1){
__p+='\r\n\t<div id="uploader" class="u-uploaderBox">\r\n\t    <!--用来存放文件信息-->\r\n\t    <div id="thelist" class="uploader-list"></div>\r\n\t    <div class="btns">\r\n\t        <div id="picker">选择文件</div>\r\n\t        <button id="ctlBtn" class="u-btn gray">开始上传</button>\r\n\t    </div>\r\n\t</div>\r\n';
}
__p+='';
}
return __p;
};
  return {
  	// 上传图片
  	uploadImage: function(param, callback) {
  		$(document)
  			.off('click', param.id)
  			.on('click', param.id, function(event) {
  				event.preventDefault();
  				var that = this,
  					_tmpl = $.trim(tmpl({
  						type: 0
  					}));
  				common.open({
  					title: param.title || '上传图片（允许上传单个文件最大5M）',
  					shadeClose: false,
  					content: _tmpl,
  					success: function(layero, layerIndex) {
  						// 初始化Web Uploader
  						var $list = $('#fileList'),
  							// 优化retina, 在retina下这个值是2
  							ratio = window.devicePixelRatio || 1,
  							// 缩略图大小
  							thumbnailWidth = 100 * ratio,
  							thumbnailHeight = 100 * ratio,
  							// Web Uploader实例
  							uploader;
  						uploader = WebUploader.create({
  							// 选完文件后，是否自动上传。
  							auto: true,
  							// swf文件路径
  							swf: common.BASE_PATH + '/static/components/WebUploader/webuploader.swf',
  							// 文件接收服务端。
  							server: common.BASE_PATH + '/service/common/uploadFile',
  							// 选择文件的按钮。可选。
  							// 内部根据当前运行是创建，可能是input元素，也可能是flash.
  							pick: {
  								id: '#filePicker',
  								multiple: param.multiple || false
  							},
  							//文件上传请求的参数表，每次发送都会发送此对象中的参数。
  							formData: {},
  							//验证单个文件大小是否超出限制, 超出则不允许加入队列。
  							fileSingleSizeLimit: 5242880,
  							compress: false,
  							// 只允许选择图片文件。
  							accept: {
  								title: 'Images',
  								extensions: 'gif,jpg,jpeg,bmp,png',
  								mimeTypes: 'image/*'
  							}
  						});
  						uploader.on('uploadStart', function(file) {
  							$("#filePicker").text("").append('<i class="fa fa fa-spinner fa-pulse fa-fw"></i>正在提交').attr('disabled', true);
  						});
  						// 当有文件添加进来的时候
  						uploader.on('fileQueued', function(file) {
  							var $li = $(
  									'<div id="' + file.id + '" class="file-item thumbnail">' +
  									'<img>' +
  									'<div class="info">' + file.name + '</div>' +
  									'</div>'
  								),
  								$img = $li.find('img');
  							// $list为容器jQuery实例
  							$list.append($li);
  
  							// 创建缩略图
  							// 如果为非图片文件，可以不用调用此方法。
  							// thumbnailWidth x thumbnailHeight 为 100 x 100
  							uploader.makeThumb(file, function(error, src) {
  								if (error) {
  									$img.replaceWith('<span>不能预览</span>');
  									return;
  								}
  
  								$img.attr('src', src);
  							}, thumbnailWidth, thumbnailHeight);
  						});
  						// 文件上传过程中创建进度条实时显示。
  						uploader.on('uploadProgress', function(file, percentage) {
  							var $li = $('#' + file.id),
  								$percent = $li.find('.progress span');
  
  							// 避免重复创建
  							if (!$percent.length) {
  								$percent = $('<p class="progress"><span></span></p>')
  									.appendTo($li)
  									.find('span');
  							}
  
  							$percent.css('width', percentage * 100 + '%');
  						});
  
  						// 文件上传成功，给item添加成功class, 用样式标记上传成功。
  						uploader.on('uploadSuccess', function(file, response) {
  							$('#' + file.id).addClass('upload-state-done');
  							if (param.close)
  								common.closeAll(layerIndex);
  							common.msg(file.name + '上传成功！');
  							typeof callback === 'function' && callback(response, that);
  						});
  
  						// 文件上传失败，显示上传出错。
  						uploader.on('uploadError', function(file) {
  							var $li = $('#' + file.id),
  								$error = $li.find('div.error');
  
  							// 避免重复创建
  							if (!$error.length) {
  								$error = $('<div class="error"></div>').appendTo($li);
  							}
  
  							$error.text('上传失败');
  						});
  						// 文件上传失败，显示上传出错。
  						uploader.on('error', function(type) {
  							var msg = '';
  							switch (type) {
  								case "Q_EXCEED_NUM_LIMIT":
  									msg = "只能上传1张图片";
  									break;
  								case "F_EXCEED_SIZE":
  									msg = "文件总大小超出5M";
  									break;
  								default:
  									msg = "只允许上传图片";
  									break;
  							}
  							common.msg(msg);
  						});
  
  						// 完成上传完了，成功或者失败，先删除进度条。
  						uploader.on('uploadComplete', function(file) {
  							$('#' + file.id).find('.progress').remove();
  						});
  						return uploader;
  					}
  				})
  			});
  	},
  	uploadFile: function(param, callback) {
  		$(document)
  			.off('click', param.id)
  			.on('click', param.id, function(event) {
  				event.preventDefault();
  				var that = this,
  					_tmpl = $.trim(tmpl({
  						type: 1
  					}));
  				common.open({
  					title: param.title || '上传文件（允许上传单个文件最大5M）',
  					shadeClose: false,
  					content: _tmpl,
  					success: function(layero, layerIndex) {
  						var $list = $('#thelist'),
  							$btn = $('#ctlBtn'),
  							state = 'pending',
  							uploader;
  						uploader = WebUploader.create({
  							// 不压缩image
  							resize: false,
  							// swf文件路径
  							swf: common.BASE_PATH + '/static/components/WebUploader/webuploader.swf',
  							// 文件接收服务端。
  							server: common.BASE_PATH + '/service/common/uploadFile',
  							// 选择文件的按钮。可选。
  							// 内部根据当前运行是创建，可能是input元素，也可能是flash.
  							pick: {
  								id: '#picker',
  								multiple: param.multiple || false
  							},
  							formData: {},
  							// 只允许选择图片文件。
  							accept: param.accept || {}
  						});
  						// 当有文件添加进来的时候
  						uploader.on('fileQueued', function(file) {
  							$list.append('<div id="' + file.id + '" class="item">' +
  								'<h4 class="info">' + file.name + '</h4>' +
  								'<p class="state">等待上传...</p>' +
  								'</div>');
  						});
  						// 文件上传过程中创建进度条实时显示。
  						uploader.on('uploadProgress', function(file, percentage) {
  							var $li = $('#' + file.id),
  								$percent = $li.find('.progress .progress-bar');
  							// 避免重复创建
  							if (!$percent.length) {
  								$percent = $('<div class="progress progress-striped active">' +
  									'<div class="progress-bar" role="progressbar" style="width: 0%">' +
  									'</div>' +
  									'</div>').appendTo($li).find('.progress-bar');
  							}
  							$li.find('p.state').text('上传中');
  							$percent.css('width', percentage * 100 + '%');
  						});
  						uploader.on('uploadSuccess', function(file, response) {
  							if (param.close)
  								common.closeAll(layerIndex);
  							typeof callback === 'function' && callback(response, that);
  							$('#' + file.id).find('p.state').text('已上传');
  							common.msg(file.name + '上传成功！');
  						});
  						uploader.on('uploadError', function(file) {
  							$('#' + file.id).find('p.state').text('上传出错');
  						});
  						uploader.on('uploadComplete', function(file) {
  							$('#' + file.id).find('.progress').fadeOut();
  						});
  						uploader.on('all', function(type) {
  							if (type === 'startUpload') {
  								state = 'uploading';
  							} else if (type === 'stopUpload') {
  								state = 'paused';
  							} else if (type === 'uploadFinished') {
  								state = 'done';
  							}
  
  							if (state === 'uploading') {
  								$btn.text('暂停上传');
  							} else {
  								$btn.text('开始上传');
  							}
  						});
  
  						$btn.on('click', function() {
  							if (state === 'uploading') {
  								uploader.stop();
  							} else {
  								uploader.upload();
  							}
  						});
  					}
  				})
  			});
  	}
  }

});