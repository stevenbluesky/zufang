var $ = require('jquery'),
	common = require('static/libs/util/common'),
	laypage = require('static/libs/laypage/laypage'),
	validate = require('static/libs/util/validate'),
	tmpl = __inline('_wifiDetail.tmpl');
require('select2');
require('select2/select2_locale_zh-CN');
var id = common.getUrlParm("id"),
	data = {
		gatewayId: id,
		page: 1,
		rows: 10
	};
init();

function init() {
	$(document)
		.on('click', '.form-box-ul li', function(event) {
			event.preventDefault();
			$(this).addClass('z-sel').siblings('.z-sel').removeClass('z-sel');
			var index = $(this).index();
			$(".show-box").hide().eq(index).show();
		})
	loadDetail();
	loadList();
	$(".form-box-ul li").eq(0).trigger('click');
}
//分页查询
function loadList(cur) {
	data.page = cur || 1;
	findPage(data, function(json) {
		var _tmpl = $.trim(tmpl({
			type: 1,
			data: json.object.list
		}));
		$("#list").html(_tmpl);
		if (json.object.count > 0) {
			laypage({
				cont: 'page', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
				pages: Math.ceil(json.object.count / data.rows), //总页数
				curr: cur, //当前页
				jump: function(e, first) { //触发分页后的回调
					if (!first) {
						loadList(e.curr);
					}
				}
			});
			$("#page").show();
		} else {
			$("#page").hide();
		}
	})
};
//查询详情
function loadDetail() {
	findById({
		id: id
	}, function(json) {
		var _tmpl = $.trim(tmpl({
			type: 2,
			data: json.object
		}));
		$(".m-wifi-station").eq(0).html(_tmpl);
		var _tmpl = $.trim(tmpl({
			type: 0,
			data: json.object
		}));
		$(".show-box").eq(0).html(_tmpl);
	})
}
//查询网关详情
function findById(data, callback, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/gateway/findById',
		data: data,
		success: function(json) {
			typeof callback === 'function' && callback(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}
//分页查询
function findPage(data, callback, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/device/findPageByGatewayId',
		data: data,
		success: function(json) {
			typeof callback === 'function' && callback(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}