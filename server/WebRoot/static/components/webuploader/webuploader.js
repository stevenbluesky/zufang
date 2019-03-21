define('components/webuploader/webuploader', ['require', 'exports', 'module', 'components/webuploader/preset/all', 'components/webuploader/widgets/log'], function(require, exports, module) {

  /**
   * @fileOverview Uploader上传类
   * @require "components/webuploader/webuploader.css"
   */
  
  
  
  var preset = require('components/webuploader/preset/all');
  require('components/webuploader/widgets/log');
  module.exports = preset || module.exports;;

});