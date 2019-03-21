define('components/webuploader/runtime/flash/blob', ['require', 'exports', 'module', 'components/webuploader/runtime/flash/runtime', 'components/webuploader/lib/blob'], function(require, exports, module) {

  /**
   * @fileOverview Blob Html实现
   */
  
  
  
  var FlashRuntime = require('components/webuploader/runtime/flash/runtime');
  var Blob = require('components/webuploader/lib/blob');
  module.exports = FlashRuntime.register('Blob', {
      slice: function (start, end) {
          var blob = this.flashExec('Blob', 'slice', start, end);
          return new Blob(this.getRuid(), blob);
      }
  }) || module.exports;;

});