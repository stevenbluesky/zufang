define('components/jquery-raty/jquery.raty', ['require', 'exports', 'module'], function(require, exports, module) {

  /*!
   * jQuery Raty - A Star Rating Plugin
   *
   * The MIT License
   *
   * @author  : Washington Botelho
   * @doc     : http://wbotelhos.com/raty
   * @version : 2.7.0
   *
   */
  ;
  (function($) {
    'use strict';
  
    var methods = {
      init: function(options) {
        return this.each(function() {
          this.self = $(this);
  
          methods.destroy.call(this.self);
  
          this.opt = $.extend(true, {}, $.fn.raty.defaults, options);
  
          methods._adjustCallback.call(this);
          methods._adjustNumber.call(this);
          methods._adjustHints.call(this);
  
          this.opt.score = methods._adjustedScore.call(this, this.opt.score);
  
          if (this.opt.starType !== 'img') {
            methods._adjustStarType.call(this);
          }
  
          // methods._adjustPath.call(this);
          methods._createStars.call(this);
  
          if (this.opt.cancel) {
            methods._createCancel.call(this);
          }
  
          if (this.opt.precision) {
            methods._adjustPrecision.call(this);
          }
  
          methods._createScore.call(this);
          methods._apply.call(this, this.opt.score);
          methods._setTitle.call(this, this.opt.score);
          methods._target.call(this, this.opt.score);
  
          if (this.opt.readOnly) {
            methods._lock.call(this);
          } else {
            this.style.cursor = 'pointer';
  
            methods._binds.call(this);
          }
        });
      },
  
      _adjustCallback: function() {
        var options = ['number', 'readOnly', 'score', 'scoreName', 'target'];
  
        for (var i = 0; i < options.length; i++) {
          if (typeof this.opt[options[i]] === 'function') {
            this.opt[options[i]] = this.opt[options[i]].call(this);
          }
        }
      },
  
      _adjustedScore: function(score) {
        if (!score) {
          return score;
        }
  
        return methods._between(score, 0, this.opt.number);
      },
  
      _adjustHints: function() {
        if (!this.opt.hints) {
          this.opt.hints = [];
        }
  
        if (!this.opt.halfShow && !this.opt.half) {
          return;
        }
  
        var steps = this.opt.precision ? 10 : 2;
  
        for (var i = 0; i < this.opt.number; i++) {
          var group = this.opt.hints[i];
  
          if (Object.prototype.toString.call(group) !== '[object Array]') {
            group = [group];
          }
  
          this.opt.hints[i] = [];
  
          for (var j = 0; j < steps; j++) {
            var
              hint = group[j],
              last = group[group.length - 1];
  
            if (last === undefined) {
              last = null;
            }
  
            this.opt.hints[i][j] = hint === undefined ? last : hint;
          }
        }
      },
  
      _adjustNumber: function() {
        this.opt.number = methods._between(this.opt.number, 1, this.opt.numberMax);
      },
  
      _adjustPath: function() {
        this.opt.path = this.opt.path || '';
  
        if (this.opt.path && this.opt.path.charAt(this.opt.path.length - 1) !== '/') {
          this.opt.path += '/';
        }
      },
  
      _adjustPrecision: function() {
        this.opt.half = true;
      },
  
      _adjustStarType: function() {
        var replaces = ['cancelOff', 'cancelOn', 'starHalf', 'starOff', 'starOn'];
  
        this.opt.path = '';
  
        for (var i = 0; i < replaces.length; i++) {
          this.opt[replaces[i]] = this.opt[replaces[i]].replace('.', '-');
        }
      },
  
      _apply: function(score) {
        methods._fill.call(this, score);
  
        if (score) {
          if (score > 0) {
            this.score.val(score);
          }
  
          methods._roundStars.call(this, score);
        }
      },
  
      _between: function(value, min, max) {
        return Math.min(Math.max(parseFloat(value), min), max);
      },
  
      _binds: function() {
        if (this.cancel) {
          methods._bindOverCancel.call(this);
          methods._bindClickCancel.call(this);
          methods._bindOutCancel.call(this);
        }
  
        methods._bindOver.call(this);
        methods._bindClick.call(this);
        methods._bindOut.call(this);
      },
  
      _bindClick: function() {
        var that = this;
  
        that.stars.on('click.raty', function(evt) {
          var
            execute = true,
            score   = (that.opt.half || that.opt.precision) ? that.self.data('score') : (this.alt || $(this).data('alt'));
  
          if (that.opt.click) {
            execute = that.opt.click.call(that, +score, evt);
          }
  
          if (execute || execute === undefined) {
            if (that.opt.half && !that.opt.precision) {
              score = methods._roundHalfScore.call(that, score);
            }
  
            methods._apply.call(that, score);
          }
        });
      },
  
      _bindClickCancel: function() {
        var that = this;
  
        that.cancel.on('click.raty', function(evt) {
          that.score.removeAttr('value');
  
          if (that.opt.click) {
            that.opt.click.call(that, null, evt);
          }
        });
      },
  
      _bindOut: function() {
        var that = this;
  
        that.self.on('mouseleave.raty', function(evt) {
          var score = +that.score.val() || undefined;
  
          methods._apply.call(that, score);
          methods._target.call(that, score, evt);
          methods._resetTitle.call(that);
  
          if (that.opt.mouseout) {
            that.opt.mouseout.call(that, score, evt);
          }
        });
      },
  
      _bindOutCancel: function() {
        var that = this;
  
        that.cancel.on('mouseleave.raty', function(evt) {
          var icon = that.opt.cancelOff;
  
          if (that.opt.starType !== 'img') {
            icon = that.opt.cancelClass + ' ' + icon;
          }
  
          methods._setIcon.call(that, this, icon);
  
          if (that.opt.mouseout) {
            var score = +that.score.val() || undefined;
  
            that.opt.mouseout.call(that, score, evt);
          }
        });
      },
  
      _bindOver: function() {
        var that   = this,
            action = that.opt.half ? 'mousemove.raty' : 'mouseover.raty';
  
        that.stars.on(action, function(evt) {
          var score = methods._getScoreByPosition.call(that, evt, this);
  
          methods._fill.call(that, score);
  
          if (that.opt.half) {
            methods._roundStars.call(that, score, evt);
            methods._setTitle.call(that, score, evt);
  
            that.self.data('score', score);
          }
  
          methods._target.call(that, score, evt);
  
          if (that.opt.mouseover) {
            that.opt.mouseover.call(that, score, evt);
          }
        });
      },
  
      _bindOverCancel: function() {
        var that = this;
  
        that.cancel.on('mouseover.raty', function(evt) {
          var
            starOff = that.opt.path + that.opt.starOff,
            icon    = that.opt.cancelOn;
  
          if (that.opt.starType === 'img') {
            that.stars.attr('src', starOff);
          } else {
            icon = that.opt.cancelClass + ' ' + icon;
  
            that.stars.attr('class', starOff);
          }
  
          methods._setIcon.call(that, this, icon);
          methods._target.call(that, null, evt);
  
          if (that.opt.mouseover) {
            that.opt.mouseover.call(that, null);
          }
        });
      },
  
      _buildScoreField: function() {
        return $('<input />', { name: this.opt.scoreName, type: 'hidden' }).appendTo(this);
      },
  
      _createCancel: function() {
        var icon   = this.opt.path + this.opt.cancelOff,
            cancel = $('<' + this.opt.starType + ' />', { title: this.opt.cancelHint, 'class': this.opt.cancelClass });
  
        if (this.opt.starType === 'img') {
          cancel.attr({ src: icon, alt: 'x' });
        } else {
          // TODO: use $.data
          cancel.attr('data-alt', 'x').addClass(icon);
        }
  
        if (this.opt.cancelPlace === 'left') {
          this.self.prepend('&#160;').prepend(cancel);
        } else {
          this.self.append('&#160;').append(cancel);
        }
  
        this.cancel = cancel;
      },
  
      _createScore: function() {
        var score = $(this.opt.targetScore);
  
        this.score = score.length ? score : methods._buildScoreField.call(this);
      },
  
      _createStars: function() {
        for (var i = 1; i <= this.opt.number; i++) {
          var
            name  = methods._nameForIndex.call(this, i),
            attrs = { alt: i, src: this.opt.path + this.opt[name] };
  
          if (this.opt.starType !== 'img') {
            attrs = { 'data-alt': i, 'class': attrs.src }; // TODO: use $.data.
          }
  
          attrs.title = methods._getHint.call(this, i);
  
          $('<' + this.opt.starType + ' />', attrs).appendTo(this);
  
          if (this.opt.space) {
            this.self.append(i < this.opt.number ? '&#160;' : '');
          }
        }
  
        this.stars = this.self.children(this.opt.starType);
      },
  
      _error: function(message) {
        $(this).text(message);
  
        $.error(message);
      },
  
      _fill: function(score) {
        var hash = 0;
  
        for (var i = 1; i <= this.stars.length; i++) {
          var
            icon,
            star   = this.stars[i - 1],
            turnOn = methods._turnOn.call(this, i, score);
  
          if (this.opt.iconRange && this.opt.iconRange.length > hash) {
            var irange = this.opt.iconRange[hash];
  
            icon = methods._getRangeIcon.call(this, irange, turnOn);
  
            if (i <= irange.range) {
              methods._setIcon.call(this, star, icon);
            }
  
            if (i === irange.range) {
              hash++;
            }
          } else {
            icon = this.opt[turnOn ? 'starOn' : 'starOff'];
  
            methods._setIcon.call(this, star, icon);
          }
        }
      },
  
      _getFirstDecimal: function(number) {
        var
          decimal = number.toString().split('.')[1],
          result  = 0;
  
        if (decimal) {
          result = parseInt(decimal.charAt(0), 10);
  
          if (decimal.slice(1, 5) === '9999') {
            result++;
          }
        }
  
        return result;
      },
  
      _getRangeIcon: function(irange, turnOn) {
        return turnOn ? irange.on || this.opt.starOn : irange.off || this.opt.starOff;
      },
  
      _getScoreByPosition: function(evt, icon) {
        var score = parseInt(icon.alt || icon.getAttribute('data-alt'), 10);
  
        if (this.opt.half) {
          var
            size    = methods._getWidth.call(this),
            percent = parseFloat((evt.pageX - $(icon).offset().left) / size);
  
          score = score - 1 + percent;
        }
  
        return score;
      },
  
      _getHint: function(score, evt) {
        if (score !== 0 && !score) {
          return this.opt.noRatedMsg;
        }
  
        var
          decimal = methods._getFirstDecimal.call(this, score),
          integer = Math.ceil(score),
          group   = this.opt.hints[(integer || 1) - 1],
          hint    = group,
          set     = !evt || this.move;
  
        if (this.opt.precision) {
          if (set) {
            decimal = decimal === 0 ? 9 : decimal - 1;
          }
  
          hint = group[decimal];
        } else if (this.opt.halfShow || this.opt.half) {
          decimal = set && decimal === 0 ? 1 : decimal > 5 ? 1 : 0;
  
          hint = group[decimal];
        }
  
        return hint === '' ? '' : hint || score;
      },
  
      _getWidth: function() {
        var width = this.stars[0].width || parseFloat(this.stars.eq(0).css('font-size'));
  
        if (!width) {
          methods._error.call(this, 'Could not get the icon width!');
        }
  
        return width;
      },
  
      _lock: function() {
        var hint = methods._getHint.call(this, this.score.val());
  
        this.style.cursor = '';
        this.title        = hint;
  
        this.score.prop('readonly', true);
        this.stars.prop('title', hint);
  
        if (this.cancel) {
          this.cancel.hide();
        }
  
        this.self.data('readonly', true);
      },
  
      _nameForIndex: function(i) {
        return this.opt.score && this.opt.score >= i ? 'starOn' : 'starOff';
      },
  
      _resetTitle: function(star) {
        for (var i = 0; i < this.opt.number; i++) {
          this.stars[i].title = methods._getHint.call(this, i + 1);
        }
      },
  
       _roundHalfScore: function(score) {
        var integer = parseInt(score, 10),
            decimal = methods._getFirstDecimal.call(this, score);
  
        if (decimal !== 0) {
          decimal = decimal > 5 ? 1 : 0.5;
        }
  
        return integer + decimal;
      },
  
      _roundStars: function(score, evt) {
        var
          decimal = (score % 1).toFixed(2),
          name    ;
  
        if (evt || this.move) {
          name = decimal > 0.5 ? 'starOn' : 'starHalf';
        } else if (decimal > this.opt.round.down) {               // Up:   [x.76 .. x.99]
          name = 'starOn';
  
          if (this.opt.halfShow && decimal < this.opt.round.up) { // Half: [x.26 .. x.75]
            name = 'starHalf';
          } else if (decimal < this.opt.round.full) {             // Down: [x.00 .. x.5]
            name = 'starOff';
          }
        }
  
        if (name) {
          var
            icon = this.opt[name],
            star = this.stars[Math.ceil(score) - 1];
  
          methods._setIcon.call(this, star, icon);
        }                                                         // Full down: [x.00 .. x.25]
      },
  
      _setIcon: function(star, icon) {
        star[this.opt.starType === 'img' ? 'src' : 'className'] = this.opt.path + icon;
      },
  
      _setTarget: function(target, score) {
        if (score) {
          score = this.opt.targetFormat.toString().replace('{score}', score);
        }
  
        if (target.is(':input')) {
          target.val(score);
        } else {
          target.html(score);
        }
      },
  
      _setTitle: function(score, evt) {
        if (score) {
          var
            integer = parseInt(Math.ceil(score), 10),
            star    = this.stars[integer - 1];
  
          star.title = methods._getHint.call(this, score, evt);
        }
      },
  
      _target: function(score, evt) {
        if (this.opt.target) {
          var target = $(this.opt.target);
  
          if (!target.length) {
            methods._error.call(this, 'Target selector invalid or missing!');
          }
  
          var mouseover = evt && evt.type === 'mouseover';
  
          if (score === undefined) {
            score = this.opt.targetText;
          } else if (score === null) {
            score = mouseover ? this.opt.cancelHint : this.opt.targetText;
          } else {
            if (this.opt.targetType === 'hint') {
              score = methods._getHint.call(this, score, evt);
            } else if (this.opt.precision) {
              score = parseFloat(score).toFixed(1);
            }
  
            var mousemove = evt && evt.type === 'mousemove';
  
            if (!mouseover && !mousemove && !this.opt.targetKeep) {
              score = this.opt.targetText;
            }
          }
  
          methods._setTarget.call(this, target, score);
        }
      },
  
      _turnOn: function(i, score) {
        return this.opt.single ? (i === score) : (i <= score);
      },
  
      _unlock: function() {
        this.style.cursor = 'pointer';
        this.removeAttribute('title');
  
        this.score.removeAttr('readonly');
  
        this.self.data('readonly', false);
  
        for (var i = 0; i < this.opt.number; i++) {
          this.stars[i].title = methods._getHint.call(this, i + 1);
        }
  
        if (this.cancel) {
          this.cancel.css('display', '');
        }
      },
  
      cancel: function(click) {
        return this.each(function() {
          var self = $(this);
  
          if (self.data('readonly') !== true) {
            methods[click ? 'click' : 'score'].call(self, null);
  
            this.score.removeAttr('value');
          }
        });
      },
  
      click: function(score) {
        return this.each(function() {
          if ($(this).data('readonly') !== true) {
            score = methods._adjustedScore.call(this, score);
  
            methods._apply.call(this, score);
  
            if (this.opt.click) {
              this.opt.click.call(this, score, $.Event('click'));
            }
  
            methods._target.call(this, score);
          }
        });
      },
  
      destroy: function() {
        return this.each(function() {
          var self = $(this),
              raw  = self.data('raw');
  
          if (raw) {
            self.off('.raty').empty().css({ cursor: raw.style.cursor }).removeData('readonly');
          } else {
            self.data('raw', self.clone()[0]);
          }
        });
      },
  
      getScore: function() {
        var score = [],
            value ;
  
        this.each(function() {
          value = this.score.val();
  
          score.push(value ? +value : undefined);
        });
  
        return (score.length > 1) ? score : score[0];
      },
  
      move: function(score) {
        return this.each(function() {
          var
            integer  = parseInt(score, 10),
            decimal  = methods._getFirstDecimal.call(this, score);
  
          if (integer >= this.opt.number) {
            integer = this.opt.number - 1;
            decimal = 10;
          }
  
          var
            width   = methods._getWidth.call(this),
            steps   = width / 10,
            star    = $(this.stars[integer]),
            percent = star.offset().left + steps * decimal,
            evt     = $.Event('mousemove', { pageX: percent });
  
          this.move = true;
  
          star.trigger(evt);
  
          this.move = false;
        });
      },
  
      readOnly: function(readonly) {
        return this.each(function() {
          var self = $(this);
  
          if (self.data('readonly') !== readonly) {
            if (readonly) {
              self.off('.raty').children('img').off('.raty');
  
              methods._lock.call(this);
            } else {
              methods._binds.call(this);
              methods._unlock.call(this);
            }
  
            self.data('readonly', readonly);
          }
        });
      },
  
      reload: function() {
        return methods.set.call(this, {});
      },
  
      score: function() {
        var self = $(this);
  
        return arguments.length ? methods.setScore.apply(self, arguments) : methods.getScore.call(self);
      },
  
      set: function(options) {
        return this.each(function() {
          $(this).raty($.extend({}, this.opt, options));
        });
      },
  
      setScore: function(score) {
        return this.each(function() {
          if ($(this).data('readonly') !== true) {
            score = methods._adjustedScore.call(this, score);
  
            methods._apply.call(this, score);
            methods._target.call(this, score);
          }
        });
      }
    };
  
    $.fn.raty = function(method) {
      if (methods[method]) {
        return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
      } else if (typeof method === 'object' || !method) {
        return methods.init.apply(this, arguments);
      } else {
        $.error('Method ' + method + ' does not exist!');
      }
    };
  
    $.fn.raty.defaults = {
      cancel       : false,
      cancelClass  : 'raty-cancel',
      cancelHint   : '取消评分!',
      cancelOff    : 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAALEgAACxIB0t1+/AAAABx0RVh0U29mdHdhcmUAQWRvYmUgRmlyZXdvcmtzIENTNAay06AAAAI1SURBVDiNpZMxS1thFIafGyqJOJigBG8kGdqCkpCf0F/hJmrdpYuTi+Dk5HbHTFXpnfwV3RQUFEUMqYNEE7zGCIYr937v93WpaUsKHXzhLIfvfTmH83yec4636B2A53nDRhiGH4wxi865FUkz1lokday1u5K+ra2ttV7fOufwnHPDgDAMF4wxQalU8svlMrlcDmMM/X6f8/Nzer3enaQv6+vrByMBv8xfq9XqRLlcptvt0u/3cc4xPj7O5OQkp6enXF9fDyR93tjYOBgGhGH4XtL3ubm5UqVS4fLykjiOMcaQy+UASNMU3/c5Ozvj5ubmVtKnzc3NHxkASUv5fL7k+z5XV1ckSUKSJEhiMBgwGAxIkoRms0mtVsPzvJKkJYDMr12Wfd+n0+nw9PREu92m1+vx+Pj4VwE8PDxQrVaRtDy8gqTZbDZLt9vFGMPq6uo/T9ZoNEjTlEqlgqTZ4QSSeHl5IY5j/seFJACMMb85kNSOouhjNpvl+fmZRqPxT7PneRQKBTqdDsaY9nACY8xes9mkUCiQpiljY2MjZmstAMVikePjY5xze3+usG+tvb24uGB+fh6ATCbDKyzWWjKZDPV6ncPDQ+I4vpW0D/wGaWdnZ0HS1+np6Yl6vU4URdzf32OtpVgsMjU1xdHREa1WayDpcxAEByMob29vL0gKAL9Wq5HP5wGIooiTkxPiOL6T9CUIglGUX7W1tfVB0qIxZsU5NyMJY0zHObcr6VsQBKOf6S36CXlLhYTB2zgjAAAAAElFTkSuQmCC',
      cancelOn     : 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAABGdBTUEAAK/INwWK6QAAABl0RVh0U29mdHdhcmUAQWRvYmUgSW1hZ2VSZWFkeXHJZTwAAAJdSURBVDjLpZP7S1NhGMf9W7YfogSJboSEUVCY8zJ31trcps6zTI9bLGJpjp1hmkGNxVz4Q6ildtXKXzJNbJRaRmrXoeWx8tJOTWptnrNryre5YCYuI3rh+8vL+/m8PA/PkwIg5X+y5mJWrxfOUBXm91QZM6UluUmthntHqplxUml2lciF6wrmdHriI0Wx3xw2hAediLwZRWRkCPzdDswaSvGqkGCfq8VEUsEyPF1O8Qu3O7A09RbRvjuIttsRbT6HHzebsDjcB4/JgFFlNv9MnkmsEszodIIY7Oaut2OJcSF68Qx8dgv8tmqEL1gQaaARtp5A+N4NzB0lMXxon/uxbI8gIYjB9HytGYuusfiPIQcN71kjgnW6VeFOkgh3XcHLvAwMSDPohOADdYQJdF1FtLMZPmslvhZJk2ahkgRvq4HHUoWHRDqTEDDl2mDkfheiDgt8pw340/EocuClCuFvboQzb0cwIZgki4KhzlaE6w0InipbVzBfqoK/qRH94i0rgokSFeO11iBkp8EdV8cfJo0yD75aE2ZNRvSJ0lZKcBXLaUYmQrCzDT6tDN5SyRqYlWeDLZAg0H4JQ+Jt6M3atNLE10VSwQsN4Z6r0CBwqzXesHmV+BeoyAUri8EyMfi2FowXS5dhd7doo2DVII0V5BAjigP89GEVAtda8b2ehodU4rNaAW+dGfzlFkyo89GTlcrHYCLpKD+V7yeeHNzLjkp24Uu1Ed6G8/F8qjqGRzlbl2H2dzjpMg1KdwsHxOlmJ7GTeZC/nesXbeZ6c9OYnuxUc3fmBuFft/Ff8xMd0s65SXIb/gAAAABJRU5ErkJggg==',
      cancelPlace  : 'left',
      click        : undefined,
      half         : false,
      halfShow     : true,
      hints        : ['bad', 'poor', 'regular', 'good', 'gorgeous'],
      iconRange    : undefined,
      mouseout     : undefined,
      mouseover    : undefined,
      noRatedMsg   : '尚未评分!',
      number       : 5,
      numberMax    : 20,
      path         : '',
      precision    : false,
      readOnly     : false,
      round        : { down: 0.25, full: 0.6, up: 0.76 },
      score        : undefined,
      scoreName    : 'score',
      single       : false,
      space        : true,
      starHalf     : 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAAK8AAACvABQqw0mAAAABx0RVh0U29mdHdhcmUAQWRvYmUgRmlyZXdvcmtzIENTNAay06AAAAIVSURBVDiNlZFNSBRhGMd/887M665ru4u5gUZB1ib2cSijvAR1CRLJk0Jq3YQiyItXDboE3TQSIrpElHkTijp1WHZBo6CDlt8SSq1WNKvmbDPzznSwDaF2qz88h4fn93xDCb0aCO3JpFOHSzGiVLC+blc/MFiKKarp+1VH88/i/tTkuyCTTrX/9wS7qyO9IrC0RCIBcDOTTlX8cwFrZMcFlNWiCfADRXVNzU7gTiadasikU3VbWW3gIpVtp+JXjPKK/boRJHXdTQqxUalr6+gGfDmyhJSSfN7h08oKvq+wbRsgC7QYtoPr53PnlGcdQwLmpmkGEEAQBNi2jWlKavfWImWImelJRsdGH3d1XXqpAQx3EztZz3PdpFE3QTdAGCAEWI3vUcrD8zyklFhfLcbHJ56eb+9s/nWDtn5yLyY44yvSvgfKA+WC54Lj5HGc7wghWF/bYH5+YWx2dq71tyN23GLtzQKdhWTlbJrreiilME2T5eUsudxqa2/fNfuPX3g9xyflEhS6ew6Ew2EMwwAgEokQi8eSRd+YTHBAuWiF7sqBnLVKWVmEUKicaCxKmZTHixbYt51Dyv25gsuq53J7cXGpY2Z66m32Y5ZoNI5pyhNbc4ytTjQsDirPzzv6tsEP3+I3TvctfoZmgIdDjx5crqpK9AghGiimJ3d7ro/c6+suCgDDw0NXm5rOxgu+FgRBKf6v+gHrFd+qH3TtXAAAAABJRU5ErkJggg==',
      starOff      : 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAAK8AAACvABQqw0mAAAABx0RVh0U29mdHdhcmUAQWRvYmUgRmlyZXdvcmtzIENTNAay06AAAAInSURBVDiNnZI/TBNhGMZ/vV7va3vXu4otYCSNxYIJhBhDAiZUEjdlcXJhAMKkCXFycyNOmJgYZiMDAWTBxdGlaU10hiJaSgKKloZE24Y7Kr3PgT8BQ5vos715/rxvvueDBsikU5cz6VRPI43aiASmgBhwq55AabA9GQqZw5FINJlJp4b/KSCTTnmB6eaWFqLRKMBUJp0yztN6/jL2Aq3AndbWSxPhC2GkdCmVynzf3p4DngOVgeTg2klAJp1KAO+AmK4b+DQflhVGDwbZrzpIKdE0DcepUtzZwXVr2LYN8AO4pwwkB3P5/MZ0KBSi81onbW1tBAICZ98GQEqJbduoqpf2q+10dfdgmibZ1ezrgeTgRy/A0tKb9zf7+2JCiBuqquI4NlJyElCrHeA4NgcHv9n+9pXl5ZW3o2PjI3CqxpXV1QemZcWFJm77g36qVQePRwEktVoNVfVRKe+Rz298yOXW75/7iDOvXsbj8Sv5REeCSqWConhwXYmULoYRIvdlnc3NrdjI6NjWuTU2NTUlgvphW16vghCCQCCAqh4equs6VtjqOO05EyCE6DF0AyECBIMG5fIev36WEELH7w9iWiZC0/pOe858ZVX1deuGQaFQYKdQyDmO80xKKsVi8Ulzc0vXxUgEn0/rrxsgpXv989qn/O7u7ovJyaczK9ls6YiaW5iffRiJRB8ritJLPczPzY7XJY+wuLjwaGjobvh49sjjwv8TfwCXtcZoRHu4ugAAAABJRU5ErkJggg==',
      starOn       : 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAALEgAACxIB0t1+/AAAABx0RVh0U29mdHdhcmUAQWRvYmUgRmlyZXdvcmtzIENTNAay06AAAAHxSURBVDiNpZIxaNNBFMZ/7/5/i6k1xDa1CCYa0ii2HaQIGUToVBy03QQHuziqKLrUoSpOLhYSECcX3dpFp3RwFS2aQehQEoJFBYeKjW1qYu5/dw5pYgtJQPzg8R73vu/dd7yDLviQ3Z9YeRpKdOP43ZqnTsYyIiJQuNiN1xaF59HxWi5ia0sR+3VxaLwTT3VqxI8cmFOuLB5lGRwI3funAeVXh69gytOiQPmg7MbUdm5oph1XsjP0X5qIXPN7+054vkt5nk4p9avfkwqeD6LAGTD0YWzohzU9RRNIMahuF3JvN5741Tra1n5OmaB8hh5gXyPEBxyIgHNgTQWrK/1GkzaatA0kX62T8V6vUE8PsxCPMiHCURGQpj8H1jYc2ADs3/wuX2LycpZND2Bxmd/jCRYSg5wTiDfFzu2IzR7xm+UC5y88YhN2XQawdJdjpxOsKQ+Uary/ZcY2hhS/kTg7x1rbLeRLrBuNMxoCDUEdzE4OdKP++In1jmtMDTJiNGLq0IxgV200JKOM7Nbs+crDA4wZDa5hfxPhhQjOOWacJewcHD/EGPC+rYNwSI2agFrNHJwvbcWS8Vtcj93kxmo5lqzZ8LzRUvWQ0Y4OVqO3q0b1zk5ffZiBrdb55IMv34E7L5/d/+zbygA8bvXEOcf/4A98LtzAZGRLlgAAAABJRU5ErkJggg==',
      starType     : 'img',
      target       : undefined,
      targetFormat : '{score}',
      targetKeep   : false,
      targetScore  : undefined,
      targetText   : '',
      targetType   : 'hint'
    };
  
  })(jQuery);
  

});