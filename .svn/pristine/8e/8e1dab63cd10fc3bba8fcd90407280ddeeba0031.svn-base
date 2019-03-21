define('components/webuploader/lib/md5', ['require', 'exports', 'module', 'components/webuploader/runtime/client', 'components/webuploader/mediator'], function(require, exports, module) {

  /**
   * @fileOverview Md5
   */
  
  
  
  var RuntimeClient = require('components/webuploader/runtime/client');
  var Mediator = require('components/webuploader/mediator');
  function Md5() {
      RuntimeClient.call(this, 'Md5');
  }
  // 让 Md5 具备事件功能。
  Mediator.installTo(Md5.prototype);
  Md5.prototype.loadFromBlob = function (blob) {
      var me = this;
      if (me.getRuid()) {
          me.disconnectRuntime();
      }
      // 连接到blob归属的同一个runtime.
      me.connectRuntime(blob.ruid, function () {
          me.exec('init');
          me.exec('loadFromBlob', blob);
      });
  };
  Md5.prototype.getResult = function () {
      return this.exec('getResult');
  };
  module.exports = Md5 || module.exports;;

});