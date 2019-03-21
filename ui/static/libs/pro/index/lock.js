var $ = require('jquery'),
	common = require('static/libs/util/common'),
	laypage = require('static/libs/laypage/laypage'),
	validate = require('static/libs/util/validate'),
	upload = require('static/libs/util/upload'),
	tmpl = __inline('_lock.tmpl');
require('select2');
require('select2/select2_locale_zh-CN');
var data = {
	page: 1,
	rows: 10
};
init();

function init() {
	$(document)
		.on('click', '.search-item li a', function(event) {
			event.preventDefault();
			$(this).parent().addClass('z-sel').siblings('li').removeClass('z-sel');
			$("#btn-search").trigger('click');
		})
		.on('click', '#search-sblx li a', function(event) {
			event.preventDefault();
			var that = this,
				type = $(that).parent().attr('data-id');
			switch (parseInt(type, 10)) {
				case 0:
					$(".lockStatus").show();
					break;
				case 1:
					$(".lockStatus").hide();
					break;
				case 17:
					$(".lockStatus").hide();
					break;
				case 53:
					$(".lockStatus").hide();
					break;
			}
		})
		// 搜索
		.on('click', '#btn-search', function(event) {
			event.preventDefault();
			var signalFlag = $('#search-signalFlag .z-sel').attr('data-id'),
				batteryLowFlag = $('#search-batteryLowFlag .z-sel').attr('data-id'),
				bindStatus = $('#search-bindStatus .z-sel').attr('data-id'),
				grantRealName = $('#grantRealName-search').select2('val');

			if (typeof signalFlag == 'string' && signalFlag.length > 0)
				data.signalFlag = signalFlag;
			else
				delete data.signalFlag;
			if (typeof batteryLowFlag == 'string' && batteryLowFlag.length > 0)
				data.batteryLowFlag = batteryLowFlag;
			else
				delete data.batteryLowFlag;
			if (typeof bindStatus == 'string' && bindStatus.length > 0)
				data.bindStatus = bindStatus;
			else
				delete data.bindStatus;
			if (typeof grantRealName == 'string' && grantRealName.length > 0)
				data.grantRealName = grantRealName;
			else
				delete data.grantRealName;
			data.districtId = $("#district-search").select2('val');
			loadList(1);
		})
		// 开锁
		.on('click', '.btn-open-lock', function(event) {
			event.preventDefault();
			var that = this;
			if ($(that).hasClass('z-dis')) return;
			$(that).addClass('z-dis');
			openDevice({
				id: $(that).attr('data-id')
			}, function(json) {
				common.msg(json.message, function() {
					loadList();
				})
			}, function(json) {
				$(that).removeClass('z-dis');
			})
		})
		// 关闸
		.on('click', '.btn-close-lock', function(event) {
			event.preventDefault();
			var that = this;
			if ($(that).hasClass('z-dis')) return;
			$(that).addClass('z-dis');
			closeDevice({
				id: $(that).attr('data-id')
			}, function(json) {
				common.msg(json.message, function() {
					loadList();
				})
			}, function(json) {
				$(that).removeClass('z-dis');
			})
		});
	// 查询授权人
	findGrantRealName(null, function(json) {
		$("#grantRealName-search").select2({
			placeholder: label.authorizer,
			allowClear: true,
			data: {
				results: parsePlaceData(json.object, 'grantRealName', 'grantRealName')
			}
		});
	});
	// 查询小区列表
	findDistrict(null, function(json) {
		$("#district-search").select2({
			placeholder: label.community,
			allowClear: true,
			data: {
				results: parsePlaceData(json.object, 'id', 'districtName')
			}
		});
	});
	loadList(1);
}
//分页查询
function loadList(cur) {
	if (cur)
		data.page = cur;
	data.deviceType = $('#search-sblx .z-sel').attr('data-id');
	findPage(data, function(json) {
		var _tmpl = $.trim(tmpl({
			type: 1,
			data: json.object.list
		}));
		$("#list").html(_tmpl);
		if (json.object.count > 0) {
			if (cur)
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
//解析地址
function parsePlaceData(obj, key, value) {
	var arr = [];
	$.each(obj, function(i, n) {
		arr.push({
			id: n[key],
			text: n[value]
		})
	})
	return arr;
};
//分页查询
function findPage(data, callback, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/device/findPage',
		data: data,
		success: function(json) {
			typeof callback === 'function' && callback(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
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
//关闸
function closeDevice(data, success, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/device/closeDevice',
		data: data,
		success: function(json) {
			typeof success === 'function' && success(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}
//查询“被授权人”
function findGrantRealName(data, success, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/room/findGrantRealName',
		data: data,
		success: function(json) {
			typeof success === 'function' && success(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}
// 查询小区列表
function findDistrict(data, callback, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/district/findDistrict',
		data: data,
		success: function(json) {
			typeof callback === 'function' && callback(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}