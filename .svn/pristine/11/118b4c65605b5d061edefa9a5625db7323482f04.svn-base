/*!
Waypoints Infinite Scroll Shortcut - 4.0.0
Copyright © 2011-2015 Caleb Troughton
Licensed under the MIT license.
https://github.com/imakewebthings/waypoints/blog/master/licenses.txt
*/
'use strict';
var $ = require('jquery');
var $ = window.jQuery;
var Waypoint = window.Waypoint;

/* http://imakewebthings.com/waypoints/shortcuts/infinite-scroll */
function Infinite(options) {
  this.options = $.extend({}, Infinite.defaults, options)
  this.container = this.options.element
  if (this.options.container !== 'auto') {
    this.container = this.options.container
  }
  this.$container = $(this.container)
  this.$more = $(this.options.more)

  if (this.$more.length) {
    this.setupHandler()
    this.waypoint = new Waypoint(this.options)
  }
}

/* Private */
Infinite.prototype.setupHandler = function() {
  this.options.handler = $.proxy(function() {
    this.options.onBeforePageLoad()
    this.destroy()
    this.$container.addClass(this.options.loadingClass)
    this.options.getData(this);
  }, this)
}

Infinite.prototype.newWaypoint = function() {
  this.waypoint = new Waypoint(this.options);
};
/* Public */
Infinite.prototype.destroy = function() {
  if (this.waypoint) {
    this.waypoint.destroy()
  }
}

Infinite.defaults = {
  container: 'auto',
  items: '.infinite-item',
  more: '.infinite-more-link',
  offset: 'bottom-in-view',
  loadingClass: 'infinite-loading',
  onBeforePageLoad: $.noop,
  onAfterPageLoad: $.noop,
  getData: $.noop
}

Waypoint.Infinite = Infinite