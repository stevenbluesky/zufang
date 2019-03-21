var $ = require('jquery'),
	common = require('static/libs/util/common'),
	laypage = require('static/libs/laypage/laypage'),
	validate = require('static/libs/util/validate'),
	tmpl = __inline('_electricityDetail.tmpl');
require('select2');
require('select2/select2_locale_zh-CN');
require('static/libs/laydate/laydate');
var id = common.getUrlParm("id"),
	data = {
		deviceId: id,
		page: 1,
		rows: 10
	};
init();

function init() {

	$(document)
		//
		.on('click', '.form-box-ul li', function(event) {
			event.preventDefault();
			$(this).addClass('z-sel').siblings('.z-sel').removeClass('z-sel');
			var index = $(this).index();
			$(".show-box").hide().eq(index).show();
			if (index == 1)
				$(".search-type li :visible").eq(0).trigger('click');
		})
		// 
		.on('click', '.search-item li', function(event) {
			event.preventDefault();
			$(this).addClass('z-sel').siblings('li').removeClass('z-sel');
		})
		// 
		.on('click', '.search-type li', function(event) {
			event.preventDefault();
			var index = $(this).index();
			data.type = index;
			loadList(1);
		})
		.on('click', '.search-time li', function(event) {
			event.preventDefault();
			var index = $(this).index();
			data.queryFlag = index;
			loadList(1);
		});
	loadDetail();
	$(".form-box-ul li").eq(0).trigger('click');
}
//分页查询
function loadList(cur) {
	data.page = cur || 1;
	var url = '/service/openDeviceLog/findPage';
	switch (data.type) {
		case 0:
			url = '/service/openDeviceLog/findPage';
			break;
		case 1:
			url = '/service/deviceDegreesLog/findPage';
			break;
		case 2:
			url = '/service/commuLog/findPage';
			break;
	};
	findPage(url, data, function(json) {
		var _tmpl = $.trim(tmpl({
			type: 4,
			flag: data.type,
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
	initDevice({
		id: id
	}, function(json) {
		var _tmpl = $.trim(tmpl({
			type: 5,
			data: json.object
		}));
		$(".m-wifi-station").eq(0).html(_tmpl);
		var _tmpl = $.trim(tmpl({
			type: 0,
			data: json.object
		}));
		$(".show-box").eq(0).html(_tmpl);
		if ( json.object.deviceType == 17 )
			$(".show-item-1").hide();
	})
}
//查询设备详情
function initDevice(data, callback, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/device/initDevice',
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
//用户密码修改  失效密码传ff即可
function updatePassword(data, callback, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/device/updatePassword',
		data: data,
		success: function(json) {
			typeof callback === 'function' && callback(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}
//动态密码修改
function updateTempPassword(data, callback, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/device/updateTempPassword',
		data: data,
		success: function(json) {
			typeof callback === 'function' && callback(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}
// 查找Room是否授权
function findRoomById(data, callback, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/room/findRoomById',
		data: data,
		success: function(json) {
			typeof callback === 'function' && callback(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}