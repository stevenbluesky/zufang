define('components/webuploader/widgets/runtime', ['require', 'exports', 'module', 'components/webuploader/uploader', 'components/webuploader/runtime/runtime', 'components/webuploader/widgets/widget'], function(require, exports, module) {

  /**
   * @fileOverview 添加获取Runtime相关信息的方法。
   */
  
  
  
  var Uploader = require('components/webuploader/uploader');
  var Runtime = require('components/webuploader/runtime/runtime');
  require('components/webuploader/widgets/widget');
  Uploader.support = function () {
      return Runtime.hasRuntime.apply(Runtime, arguments);
  };
  module.exports = Uploader.register({
      name: 'runtime',
      init: function () {
          if (!this.predictRuntimeType()) {
              throw Error('Runtime Error');
          }
      },
      /**
           * 预测Uploader将采用哪个`Runtime`
           * @grammar predictRuntimeType() => String
           * @method predictRuntimeType
           * @for  Uploader
           */
      predictRuntimeType: function () {
          var orders = this.options.runtimeOrder || Runtime.orders, type = this.type, i, len;
          if (!type) {
              orders = orders.split(/\s*,\s*/g);
              for (i = 0, len = orders.length; i < len; i++) {
                  if (Runtime.hasRuntime(orders[i])) {
                      this.type = type = orders[i];
                      break;
                  }
              }
          }
          return type;
      }
  }) || module.exports;;

});