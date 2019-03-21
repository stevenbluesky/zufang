define('components/webuploader/runtime/html5/runtime', ['require', 'exports', 'module', 'components/webuploader/base', 'components/webuploader/runtime/runtime', 'components/webuploader/runtime/compbase'], function(require, exports, module) {

  /**
   * @fileOverview Html5Runtime
   */
  
  
  
  var Base = require('components/webuploader/base');
  var Runtime = require('components/webuploader/runtime/runtime');
  var CompBase = require('components/webuploader/runtime/compbase');
  var type = 'html5', components = {};
  function Html5Runtime() {
      var pool = {}, me = this, destroy = this.destroy;
      Runtime.apply(me, arguments);
      me.type = type;
      // 这个方法的调用者，实际上是RuntimeClient
      me.exec = function (comp, fn) {
          var client = this, uid = client.uid, args = Base.slice(arguments, 2), instance;
          if (components[comp]) {
              instance = pool[uid] = pool[uid] || new components[comp](client, me);
              if (instance[fn]) {
                  return instance[fn].apply(instance, args);
              }
          }
      };
      me.destroy = function () {
          // @todo 删除池子中的所有实例
          return destroy && destroy.apply(this, arguments);
      };
  }
  Base.inherits(Runtime, {
      constructor: Html5Runtime,
      // 不需要连接其他程序，直接执行callback
      init: function () {
          var me = this;
          setTimeout(function () {
              me.trigger('ready');
          }, 1);
      }
  });
  // 注册Components
  Html5Runtime.register = function (name, component) {
      var klass = components[name] = Base.inherits(CompBase, component);
      return klass;
  };
  // 注册html5运行时。
  // 只有在支持的前提下注册。
  if (window.Blob && window.FileReader && window.DataView) {
      Runtime.addRuntime(type, Html5Runtime);
  }
  module.exports = Html5Runtime || module.exports;;

});