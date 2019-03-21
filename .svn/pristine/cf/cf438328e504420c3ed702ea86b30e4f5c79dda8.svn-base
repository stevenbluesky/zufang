/*
 	select 下拉框
 * @Auth ： 274793580@qq.com
 * */
"use strict";
var $ = require('jquery'),
	common = require('common');
var Class = function(settings) {
	var config = this.config;
	this.config = $.extend({}, config, settings);
	this.create();
	this.initEvent();
};
Class.pt = Class.prototype;
//下拉框配置
Class.pt.config = {
	element: null, //触发元素 jquery 元素
	width: 210, //下拉框宽度
	height: 35, //下拉框高度
	//	url: null, //后台加载路径
	urlType: null, //后台加载类型 普通ajax /jsonp
	//	searchKey: null, //后台加载过滤字段
	key: null, //下拉框key值
	value: null, //下拉框value值
	dftValue: "请选择", //默认值  传入false代表没有默认值
	readonly: true, //是否只读 默认只读
	canModify: true, //是否可以修改
	param: {}, //加载时参数
	zIndex: 9999, //默认参数 不建议修改
	data: null, //如果不传入 url 就得传入data 表示 数据
	success: null, //级联
	callback:null //成功回调
};
//创建骨架
Class.pt.create = function() {
	var that = this,
		config = that.config;
	this.loadHtml(that.getData());
};
//获取ul数据
Class.pt.getData = function(val) {
	var that = this,
		config = that.config;
	return config.data;
};
//初始化事件
Class.pt.initEvent = function() {
	var that = this,
		config = that.config;
	config.element.parent().off("**").on('click', 'ul li', function() {
		var _li = this,
			data = $(_li).data("nodeData");
		if (!config.canModify) {
			return;
		}
		if (typeof data == 'undefined' || data == null) {
			config.element.val(config.dftValue).removeAttr("data-id");
		} else {
			config.element.val(data[config.value]).attr("data-id", data[config.key]).data('sltData', data);
		}
		typeof config.success == 'function' && config.success(data);
		$(_li).parent().fadeOut();
	}).on('click', '.fa-caret-down', function() {
		var _that = this,
			$that = $(_that);
		$that.siblings('ul').fadeIn();
	}).on('mouseleave', function() {
		var _that = this,
			$that = $(_that);
		$that.find('ul').fadeOut();
	}).on('click', 'input', function() {
		var _that = this,
			$that = $(_that);
		$that.siblings('ul').fadeIn();
	}).on('keyup', 'input', function(e) {
		var _that = this,
			$that = $(_that);
		if (!config.readonly) {
			$that.removeAttr("data-id");
			$.each($that.siblings('ul').find("li"), function(i, n) {
				if(eval('/('+$that.val().trim()+')/').test($(n).text()) || $that.val().replace(/\s/g, '').length == 0){
					$(n).removeClass("f-dn");
				}else{
					$(n).addClass("f-dn");
				}
			})
		}
	});
};
//加载html骨架
Class.pt.loadHtml = function(data) {
	var that = this,
		config = that.config;
	var $span = $('<span class="u-select f-pr"></span>'),
		$downIcon = $('<i class="fa fa-caret-down fa-fw"></i>'),
		$downList = $('<ul class="f-dn"></ul>'),
		$dftLi;
	//构造下拉框第一个默认下拉项
	if (config.dftValue != false) {
		$dftLi = $('<li>' + config.dftValue + '</li>');
	}
	$downList.css({
		"z-index": config.zIndex + 1,
		"top": config.height + 1
	});
	$downList.append($dftLi);
	config.element.css({
		'width': config.width,
		'padding-top': (config.height - 20) / 2 + 2,
		'padding-bottom': (config.height - 20) / 2
	});
	config.element.addClass('u-select-ipt');
	config.element.wrap($span);
	config.element.parent().css({
		'height': config.height,
		'width': config.width + 50
	});
	config.element.after($downIcon);
	config.element.after($downList);
	config.element.data('sltCfg', config);
	that.render(data);
	config.readonly && config.element.attr("readonly", "readonly");
	!config.canModify && config.element.addClass("canModify");
	config.element.removeClass('f-dn');
	typeof config.callback === 'function' && config.callback(); 
};
/*
 构造下拉框
 * @param : data 加载的数据
 * */
Class.pt.render = function(data) {
	var that = this,
		config = that.config;
	Select.render(config.element, data);
};
var Select = {
	//初始化下拉框
	create: function(setting) {
		try {
			if ($.isArray(setting)) {
				$.map(setting, function(i, n) {
					load(i);
				});
			} else {
				load(setting);
			}
		} catch (e) {
			console.log(e);
		}

		function load(i) {
			if (!i.element.data('sltCfg')) {
				new Class(i);
			} else {
				Select.render(i.element, i.data);
			}
		}
	},
	getValue: function(element) {
		return $(element).attr("data-id") || null;
	},
	getData: function(element) {
		return $(element).data("sltData") || null;
	},
	//设置值 element : "#id" 元素 val : 123 select 的值 key 
	setValue: function(element, val) {
		if (val) {
			var data = $(element).siblings("ul").find("li[data-id=" + val + "]").data("nodeData"),
				config = $(element).data("sltCfg");
			$(element).val(data[config.value]).attr("data-id", data[config.key]).data('sltData', data);
		}
	},
	clear: function(element) {
		var config = $(element).data("sltCfg");
		if(config){
			config.element.siblings('ul').children('li:not(:eq(0))').remove();
			config.element.val(config.dftValue).removeAttr("data-id");
		}
	},
	render: function(element, data) {
		var config = $(element).data("sltCfg");
		if(!config.dftValue){
			config.element.siblings('ul').children('li').remove();
		}else{
			config.element.siblings('ul').children('li:not(:eq(0))').remove();
		}
		//遍历当前元素
		for (var i in data) {
			var $li = $('<li data-id="' + data[i][config.key] + '">' + data[i][config.value] + '</li>');
			$li.data('nodeData', data[i]);
			config.element.siblings('ul').append($li);
		}
		if (config.dftValue == false && data.length > 0) {
			config.element.val(data[0].value).attr("data-id", data[0].key).data('sltData', data[0]);
		} else
			config.element.val(config.dftValue == false ? '' : config.dftValue).removeAttr("data-id");
	}

};

module.exports = Select;