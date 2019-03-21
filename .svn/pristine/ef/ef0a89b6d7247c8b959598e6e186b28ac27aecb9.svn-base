var $ = require('jquery'),
	common = require('static/libs/util/common'),
	laypage = require('static/libs/laypage/laypage'),
	validate = require('static/libs/util/validate'),
	tmpl = __inline('_lock.tmpl'),
	tmpl_ = __inline('_wifi.tmpl');
var data = {
		page: 1,
		rows: 10
	},
	roomType;
init();

function init() {
	$(document)
		.on('click', '.form-box-ul li', function(event) {
			event.preventDefault();
			$(this).addClass('z-sel').siblings('.z-sel').removeClass('z-sel');
			var index = $(this).index();
			$(".show-box").hide().eq(index).show();
			loadList(1);
		})
		.on('click', '.search-item li', function(event) {
			event.preventDefault();
			$(this).addClass('z-sel').siblings('li').removeClass('z-sel');
		})
		.on('click', '.search-type li', function(event) {
			event.preventDefault();
			loadList(1);
		})
		//开锁
		.on('click', '.btn-open-lock', function(event) {
			event.preventDefault();
			openDeviceAction($(this).attr('data-id'));
		});
	$('.form-box-ul li').trigger('click');
}
//分页查询
function loadList(cur) {
	if (cur)
		data.page = cur;
	var type = $(".form-box-ul .z-sel").index(),
		url = '/service/gateway/report';
	switch (parseInt(type, 10)) {
		case 0:
			var index = $('.search-type li.z-sel').index();
			data.noticeFlag = index;
			url = '/service/device/report';
			break;
		case 1:
			delete data.noticeFlag;
			url = '/service/gateway/report';
			break;
	}
	findPage(url, data, function(json) {
		switch (parseInt(type, 10)) {
			case 0:
				var _tmpl = $.trim(tmpl({
					type: 2,
					data: json.object.device.list
				}));
				$("#lock-list").html(_tmpl);
				$("#lock-count").html("("+label.zongshu + json.object.sumDeviceCount + "&nbsp;"+label.guzhang + json.object.deviceCount + ")");
				if (json.object.deviceCount > 0) {
					laypage({
						cont: 'lock-page', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
						pages: Math.ceil(json.object.deviceCount / data.rows), //总页数
						curr: cur, //当前页
						jump: function(e, first) { //触发分页后的回调
							if (!first) {
								loadList(e.curr);
							}
						}
					});
					$("#lock-page").show();
				} else {
					$("#lock-page").hide();
				}
				break;
			case 1:
				var _tmpl = $.trim(tmpl_({
					type: 1,
					data: json.object.gateway.list
				}));
				$("#wifi-list").html(_tmpl);
				$("#wifi-count").html("("+label.zongshu + json.object.sumGatewaryCount + "&nbsp;"+label.guzhang + json.object.gatewayCount + ")");
				if (json.object.gatewayCount > 0) {
					laypage({
						cont: 'wifi-page', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
						pages: Math.ceil(json.object.gatewayCount / data.rows), //总页数
						curr: cur, //当前页
						jump: function(e, first) { //触发分页后的回调
							if (!first) {
								loadList(e.curr);
							}
						}
					});
					$("#wifi-page").show();
				} else {
					$("#wifi-page").hide();
				}
				break;
		}


	})
};
//开锁
function openDeviceAction(id) {
	openDevice({
		id: id
	}, function(json) {
		common.msg(json.message, function() {
			loadList();
		})
	})
}
//锁
function openDevice(data, success, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/device/openDevice',
		data: data,
		success: function(json) {
			typeof success === 'function' && success(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}
//统计分析 service/device/report ,service/gateway/report
function findPage(url, data, callback, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + url,
		data: data,
		success: function(json) {
			typeof callback === 'function' && callback(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}