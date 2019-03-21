var $ = require('jquery'),
	common = require('static/libs/util/common'),
	laypage = require('static/libs/laypage/laypage'),
	validate = require('static/libs/util/validate'),
	upload = require('static/libs/util/upload'),
	tmpl = __inline('_wifi.tmpl');
require('select2');
require('select2/select2_locale_zh-CN');
var data = {
		page: 1,
		rows: 10
	},
	provinceCode,
	cityCode,
	areasCode;
init();

function init() {
	loadList(1);
	$(document)
		.on('click', '.btn-open-save', function(event) {
			event.preventDefault();
			var _tmpl = $.trim(tmpl({
				type: 0
			}));
			provinceCode = '';
			cityCode = '';
			areasCode = '';
			common.open({
				title: false,
				closeBtn: false,
				zIndex: 100,
				content: _tmpl,
				shadeClose: false,
				scrollbar: false,
				success: function(layero, index) {
					$(layero).find('.btn-close').attr('data-index', index);
					findProvince(null, function(json) {
						$("#provinceCode").select2({
							placeholder: label.province,
							data: {
								results: parsePlaceData(json.object, 'provinceCode', 'provinceName')
							}
						});
					});
				}
			});
		})
		.on('click', '.search-item li a', function(event) {
			event.preventDefault();
			$(this).parent().addClass('z-sel').siblings('li').removeClass('z-sel');
			$("#btn-search").trigger('click');
		})
		// 搜索
		.on('click', '#btn-search', function(event) {
			event.preventDefault();
			var signalFlag = $('#search-signalFlag .z-sel').attr('data-id');
			if (typeof signalFlag == 'string' && signalFlag.length > 0)
				data.signalFlag = signalFlag;
			else
				delete data.signalFlag;
			data.districtId = $("#district-search").select2('val');
			loadList(1);
		});
	findProvince(null, function(json) {
		$("#provinceCode-search").select2({
			placeholder: label.province,
			data: {
				results: parsePlaceData(json.object, 'provinceCode', 'provinceName')
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
}
//分页查询
function loadList(cur) {
	if (cur)
		data.page = cur;
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
//新增
function saveDistrict(data, callback, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/district/saveDistrict',
		data: data,
		success: function(json) {
			typeof callback === 'function' && callback(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}
// 修改
function updateDistrict(data, callback, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/district/updateDistrict',
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
		url: common.BASE_PATH + '/service/gateway/findPage',
		data: data,
		success: function(json) {
			typeof callback === 'function' && callback(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}
//查询省份
function findProvince(data, success, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/province/findProvince',
		data: data,
		success: function(json) {
			typeof success === 'function' && success(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}
//根据省查询市
function findCity(data, success, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/province/findCity',
		data: data,
		success: function(json) {
			typeof success === 'function' && success(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}
//根据市查询区县
function findAreas(data, success, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/province/findAreas',
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