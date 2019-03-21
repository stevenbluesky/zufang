define('components/webuploader/runtime/flash/md5', ['require', 'exports', 'module', 'components/webuploader/runtime/flash/runtime'], function(require, exports, module) {

  /**
   * @fileOverview  Md5 flash实现
   */
  
  
  
  var FlashRuntime = require('components/webuploader/runtime/flash/runtime');
  module.exports = FlashRuntime.register('Md5', {
      init: function () {
      },
      loadFromBlob: function (blob) {
          return this.flashExec('Md5', 'loadFromBlob', blob.uid);
      }
  }) || module.exports;;

});