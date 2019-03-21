define('components/webuploader/lib/filepaste', ['require', 'exports', 'module', 'components/webuploader/base', 'components/webuploader/mediator', 'components/webuploader/runtime/client'], function(require, exports, module) {

  /**
   * @fileOverview 错误信息
   */
  
  
  
  var Base = require('components/webuploader/base');
  var Mediator = require('components/webuploader/mediator');
  var RuntimeClent = require('components/webuploader/runtime/client');
  var $ = Base.$;
  function FilePaste(opts) {
      opts = this.options = $.extend({}, opts);
      opts.container = $(opts.container || document.body);
      RuntimeClent.call(this, 'FilePaste');
  }
  Base.inherits(RuntimeClent, {
      constructor: FilePaste,
      init: function () {
          var me = this;
          me.connectRuntime(me.options, function () {
              me.exec('init');
              me.trigger('ready');
          });
      }
  });
  Mediator.installTo(FilePaste.prototype);
  module.exports = FilePaste || module.exports;;

});