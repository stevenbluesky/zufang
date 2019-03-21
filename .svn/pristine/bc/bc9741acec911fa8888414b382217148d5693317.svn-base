define('components/webuploader/runtime/html5/blob', ['require', 'exports', 'module', 'components/webuploader/runtime/html5/runtime', 'components/webuploader/lib/blob'], function(require, exports, module) {

  /**
   * @fileOverview Blob Html实现
   */
  
  
  
  var Html5Runtime = require('components/webuploader/runtime/html5/runtime');
  var Blob = require('components/webuploader/lib/blob');
  module.exports = Html5Runtime.register('Blob', {
      slice: function (start, end) {
          var blob = this.owner.source, slice = blob.slice || blob.webkitSlice || blob.mozSlice;
          blob = slice.call(blob, start, end);
          return new Blob(this.getRuid(), blob);
      }
  }) || module.exports;;

});