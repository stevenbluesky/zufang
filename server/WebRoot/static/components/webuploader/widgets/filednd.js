define('components/webuploader/widgets/filednd', ['require', 'exports', 'module', 'components/webuploader/base', 'components/webuploader/uploader', 'components/webuploader/lib/dnd', 'components/webuploader/widgets/widget'], function(require, exports, module) {

  /**
   * @fileOverview DragAndDrop Widget。
   */
  
  
  
  var Base = require('components/webuploader/base');
  var Uploader = require('components/webuploader/uploader');
  var Dnd = require('components/webuploader/lib/dnd');
  require('components/webuploader/widgets/widget');
  var $ = Base.$;
  Uploader.options.dnd = '';
  module.exports = Uploader.register({
      name: 'dnd',
      init: function (opts) {
          if (!opts.dnd || this.request('predict-runtime-type') !== 'html5') {
              return;
          }
          var me = this, deferred = Base.Deferred(), options = $.extend({}, {
                  disableGlobalDnd: opts.disableGlobalDnd,
                  container: opts.dnd,
                  accept: opts.accept
              }), dnd;
          this.dnd = dnd = new Dnd(options);
          dnd.once('ready', deferred.resolve);
          dnd.on('drop', function (files) {
              me.request('add-file', [files]);
          });
          // 检测文件是否全部允许添加。
          dnd.on('accept', function (items) {
              return me.owner.trigger('dndAccept', items);
          });
          dnd.init();
          return deferred.promise();
      },
      destroy: function () {
          this.dnd && this.dnd.destroy();
      }
  }) || module.exports;;

});