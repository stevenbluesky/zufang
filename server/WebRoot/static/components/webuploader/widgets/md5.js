define('components/webuploader/widgets/md5', ['require', 'exports', 'module', 'components/webuploader/base', 'components/webuploader/uploader', 'components/webuploader/lib/md5', 'components/webuploader/lib/blob', 'components/webuploader/widgets/widget'], function(require, exports, module) {

  /**
   * @fileOverview 图片操作, 负责预览图片和上传前压缩图片
   */
  
  
  
  var Base = require('components/webuploader/base');
  var Uploader = require('components/webuploader/uploader');
  var Md5 = require('components/webuploader/lib/md5');
  var Blob = require('components/webuploader/lib/blob');
  require('components/webuploader/widgets/widget');
  module.exports = Uploader.register({
      name: 'md5',
      /**
           * 计算文件 md5 值，返回一个 promise 对象，可以监听 progress 进度。
           *
           *
           * @method md5File
           * @grammar md5File( file[, start[, end]] ) => promise
           * @for Uploader
           * @example
           *
           * uploader.on( 'fileQueued', function( file ) {
           *     var $li = ...;
           *
           *     uploader.md5File( file )
           *
           *         // 及时显示进度
           *         .progress(function(percentage) {
           *             console.log('Percentage:', percentage);
           *         })
           *
           *         // 完成
           *         .then(function(val) {
           *             console.log('md5 result:', val);
           *         });
           *
           * });
           */
      md5File: function (file, start, end) {
          var md5 = new Md5(), deferred = Base.Deferred(), blob = file instanceof Blob ? file : this.request('get-file', file).source;
          md5.on('progress load', function (e) {
              e = e || {};
              deferred.notify(e.total ? e.loaded / e.total : 1);
          });
          md5.on('complete', function () {
              deferred.resolve(md5.getResult());
          });
          md5.on('error', function (reason) {
              deferred.reject(reason);
          });
          if (arguments.length > 1) {
              start = start || 0;
              end = end || 0;
              start < 0 && (start = blob.size + start);
              end < 0 && (end = blob.size + end);
              end = Math.min(end, blob.size);
              blob = blob.slice(start, end);
          }
          md5.loadFromBlob(blob);
          return deferred.promise();
      }
  }) || module.exports;;

});