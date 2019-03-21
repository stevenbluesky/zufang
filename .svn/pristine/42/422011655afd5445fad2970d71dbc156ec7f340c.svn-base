define('components/webuploader/promise-third', ['require', 'exports', 'module', 'components/webuploader/dollar'], function(require, exports, module) {

  /**
   * @fileOverview 使用jQuery的Promise
   */
  
  
  
  var $ = require('components/webuploader/dollar');
  module.exports = {
      Deferred: $.Deferred,
      when: $.when,
      isPromise: function (anything) {
          return anything && typeof anything.then === 'function';
      }
  } || module.exports;;

});