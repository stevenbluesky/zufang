//配置常量 以及共有功能
var $ = require('jquery'),
	config = require('config'),
	Cookie = require('cookie'),
	layer = require('static/libs/layer/layer'),
	NProgress = require('nprogress');
require('jquery-placeholder');
var base = config.base,
	tipsIndex,
	timeIndex;
initEvent();

function initEvent() {
	$("input,textarea").placeholder();
	//序列化表单为JSON
	$.fn.serializeJson = function() {
		var serializeObj = {};
		var array = this.serializeArray();
		$(array).each(function() {
			if (serializeObj[this.name]) {
				if ($.isArray(serializeObj[this.name])) {
					serializeObj[this.name].push(this.value);
				} else {
					serializeObj[this.name] = [serializeObj[this.name], this.value];
				}
			} else {
				serializeObj[this.name] = this.value;
			}
		});
		return serializeObj;
	};
	$.fn.hoverDelay = function(options) {
		var defaults = {
			hoverDuring: 200,
			outDuring: 200,
			hoverEvent: function() {
				$.noop();
			},
			outEvent: function() {
				$.noop();
			}
		};
		var sets = $.extend(defaults, options || {});
		var hoverTimer, outTimer, that = this;
		return $(this).each(function() {
			$(this).hover(function() {
				clearTimeout(outTimer);
				hoverTimer = setTimeout(function() {
					sets.hoverEvent.apply(that);
				}, sets.hoverDuring);
			}, function() {
				clearTimeout(hoverTimer);
				outTimer = setTimeout(function() {
					sets.outEvent.apply(that);
				}, sets.outDuring);
			});
		});
	};
	$(document)
		.on('click', '.layer-box .btn-close', function(event) {
			event.preventDefault();
			var index = $(this).attr('data-index');
			common.closeAll(index);
		})
		.on('mouseenter', '.fa', function(event) {
			event.preventDefault();
			var tips = $(this).attr('data-tips');
			if (tips && tips.length > 0)
				tipsIndex = common.tips(tips, this, {
					tips: [3, '#000'],
					time: 0,
					shift: 5
				});
		})
		.on('mouseleave ', '.fa', function(event) {
			event.preventDefault();
			layer.close(tipsIndex);
		})
		// 搜索
		.on('focus', '.m-nav-search input', function(event) {
			event.preventDefault();
			$(this).parent().addClass('z-sel');
		})
		.on('blur', '.m-nav-search input', function(event) {
			event.preventDefault();
			$(this).parent().removeClass('z-sel');
		})
		.on('keydown', '.m-nav-search input', function(event) {
			var val = $(this).val();
			if (event.keyCode == 13) {
				location.href = base + '/search.html?q=' + encodeURIComponent($(this).val());
			}
		})
		.on('click', '.m-nav-search .fa-search', function(event) {
			event.preventDefault();
			location.href = base + '/search.html?q=' + encodeURIComponent($(this).prev().val());
		});

};
var common = {
	BASE_PATH: base,
	//配置首页地址
	INDEX_PATH: base + '/index.html',
	IMG_PATH: base + "/static/img/",
	msg: function(msg, callback, data) {
		var obj = data || {
			time: 1000
		}
		return layer.msg(msg, obj, function() {
			typeof callback === 'function' && callback();
		});
	},
	tips: function(msg, ele, data) {
		var obj = data || {
			tips: 1,
			time: 3000,
			shift: 5
		}
		return layer.tips(msg, ele, obj);
	},
	alert: function(msg, callback) {
		return layer.alert(msg, function(index) {
			typeof callback === 'function' && callback(index);
		});
	},
	open: function(obj) {
		var ele = {
			title: label.message,
			type: 1,
			maxWidth: 0,
			shift: 5,
			shadeClose: true, //点击遮罩关闭
			content: '\<\div style="padding:20px;">自定义内容\<\/div>',
			cancel: function(index) {
				layer.closeAll(index);
			}
		};
		return layer.open($.extend({}, ele, obj));
	},
	confirm: function(msg, callback) {
		return layer.confirm(msg, {}, function(index) {
			typeof callback === 'function' && callback(index);
		});
	},
	closeAll: function(index) {
		if (typeof(index) != 'undefined')
			layer.close(index);
		else
			layer.closeAll();
	},
	/**
	 * 功能： 初始化下拉框 id, 首个option文本, url: 地址, key: 文本， value: 值， params: 参数, text: 第一个option text: false 不设默认参数
	 */
	loadSelect: function(data) {
		this.Ajax({
			url: data.url,
			data: data.data,
			success: function(json) {
				var json = json.object,
					options = '';
				if (typeof(data.text) != 'undefined')
					if (!data.text)
						options = '';
					else
						options = "<option value='' >" + data.text + "</option>";
				else {
					options = "<option value='' >-- 请选择  --</option>";
				}
				var temp;
				for (var i = 0; i < json.length; i++) {
					temp = json[i];
					options += "<option data-type='data' value=" + temp[data.key] + ">" + temp[data.value] + "</option>";
				}
				$(data.obj).html(options);
				data.success && data.success(json);
			}
		});
	},
	loadSelectNoData: function(data) {
		var json = data.data,
			options = '';
		if (typeof(data.text) != 'undefined')
			if (!data.text)
				options = '';
			else
				options = "<option value='' >" + data.text + "</option>";
		else {
			options = "<option value='' >-- 请选择  --</option>";
		}
		var temp;
		for (var i = 0; i < json.length; i++) {
			temp = json[i];
			options += "<option data-type='data' value=" + temp[data.key] + ">" + temp[data.value] + "</option>";
		}
		$(data.obj).html(options);
		data.success && data.success(json);
	},
	// 设置下拉框值
	setSelectVal: function(id, key) {
		var options = $(id + " > option");
		$.each(options, function(i, n) {
			var option = n.value;
			if (option == key) {
				$(id).val(key).attr("selected", "selected");
			};
		});
	},
	// 清空下拉框
	clearSelect: function(id) {
		$(id).find('option[data-type=data]').remove();
	},
	// html实体转化
	htmlEncode: function(value) {
		return $('<div/>').text(value).html();
	},
	//Html解码获取Html实体
	htmlDecode: function(value) {
		return $('<div/>').html(value).text();
	},
	getUrlParm: function(name) {
		var search = document.location.search;
		var pattern = new RegExp("[?&]" + name + "\=([^&]+)", "g");
		var matcher = pattern.exec(search);
		var items = null;
		if (null != matcher) {
			try {
				items = decodeURIComponent(decodeURIComponent(matcher[1]));
			} catch (e) {
				try {
					items = decodeURIComponent(matcher[1]);
				} catch (e) {
					items = matcher[1];
				};
			};
		}
		return items;
	},
	Ajax: function(obj) {
		var that = this;
		$(document).ajaxStart(function() {
			NProgress.start();
		}).ajaxComplete(function() {
			NProgress.done();
		});
		$.ajax({
				url: that.BASE_PATH + obj.url,
				type: "POST",
				async: obj.async || true,
				cache: false,
				processData: true,
				timeout: 3e4,
				data: obj.data
			}).then(function(data) {
				var json = JSON.parse(data);
				//session失效标志
				if (json.session == false) {
					return window.location.href = that.INDEX_PATH;
				}
				switch (json.success) {
					case 0:
						layer.msg(json.message || '系统异常，请稍后重试', {
							icon: 2,
							time: 3000
						});
						typeof obj.failure === "function" && (NProgress.done(), obj.failure(json));
						break;
					case 1:
						typeof obj.success === "function" && (NProgress.done(), obj.success(json));
						break;
					default:
						layer.msg(json.message || '系统异常，请稍后重试', {
							icon: 2,
							time: 3000
						});
						typeof obj.failure === "function" && (NProgress.done(), obj.failure(json));
						break;
				}
			})
			.fail(function(json) {
				layer.msg("服务器错误，错误码" + json.message + "，请稍后再试" || '系统异常，请稍后重试', {
					icon: 2,
					time: 3000
				});
			})
			.always(function() {
				typeof obj.always === 'function' && obj.always();
			});
	},
	restfulAjax: function(obj) {
		$.ajax({
				type: "get", //使用get方法访问后台
				dataType: "jsonp", //返回jsonp格式的数据
				jsonp: "jsonpCallback", //服务端用于接收callback调用的function名的参数 
				url: obj.url, //要访问的后台地址
				timeout: 3e4,
				data: obj.data
			}).then(function(data) {
				typeof obj.success === "function" && (obj.success(json));
			})
			.fail(function(json) {
				typeof obj.failure === "function" && (obj.failure(json));
			})
			.always(function() {
				typeof obj.always === 'function' && obj.always();
			});;
	}
};
module.exports = common;