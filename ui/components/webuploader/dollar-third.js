/**
 * @fileOverview jQuery or Zepto
 * @require "jquery"
 *  "zepto"
 */


var req = window.require;
// var $ = window.__dollar || window.jQuery || window.Zepto || req('jquery') || req('zepto');
var $ = window.__dollar || window.jQuery || req('jquery');
if (!$) {
    throw new Error('jQuery  not found!');
}
module.exports = $ || module.exports;;