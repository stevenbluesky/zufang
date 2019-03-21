var $ = require('jquery'),
	common = require('static/libs/util/common'),
	laypage = require('static/libs/laypage/laypage'),
	validate = require('static/libs/util/validate'),
	upload = require('static/libs/util/upload'),
	tmpl = __inline('_cell.tmpl');
require('select2');
require('select2/select2_locale_zh-CN');
var data = {
		page: 1,
		rows: 10
	},
	provinceCode,
	cityCode,
	areasCode;
var isSecondAdmin = false;
init();
var ti = 1;

function init() {
	if(label.dlang!="cn"){
		$(".enhide").hide();
	};
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
			if(label.dlang!='cn'){
				$("#provinceselect").hide();
			};
		})
		//新增小区
		.on('click', '#btn-save', function(event) {
			event.preventDefault();
			saveAction();
		})
		// 修改小区
		.on('click', '.btn-open-edit', function(event) {
			event.preventDefault();
			var that = this,
				_tmpl = $.trim(tmpl({
					type: 2,
					data: {
						id: $(that).attr("data-id"),
						districtName: $(that).attr("data-districtName"),
						address: $(that).attr("data-address"),
						remark: $(that).attr("data-remark"),
						districtImg: $(that).attr("data-districtImg"),
						price: $(that).attr("data-price") || 0,
						waterprice: $(that).attr("data-waterprice") || 0,
						hotwaterprice: $(that).attr("data-hotwaterprice") || 0,
						shareAmount: $(that).attr("data-shareAmount") || 0
					}
				
				}));
			provinceCode = $(that).attr("data-provinceCode");
			cityCode = $(that).attr("data-cityCode");
			areasCode = $(that).attr("data-areasCode");
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
						if (provinceCode && provinceCode.length > 0)
							$("#provinceCode").select2('val', provinceCode).trigger('change');
					});
				}
			});
			if(label.dlang!='cn'){
				$("#provinceselect").hide();
			};
		})
		//省市区
		.on('change', '#provinceCode-search', function(e) {
			$('#areasCode-search').select2('destroy');
			if (e.val.length == 0) {
				$('#cityCode-search').select2('destroy');
			} else {
				findCity({
					provinceCode: e.val
				}, function(json) {
					$("#cityCode-search").select2({
						placeholder: label.city,
						allowClear: true,
						data: {
							results: parsePlaceData(json.object, 'cityCode', 'cityName')
						}
					});
				});
			}

		})
		.on('change', '#cityCode-search', function(e) {
			if (e.val.length == 0) {
				$('#areasCode-search').select2('destroy');
			} else {
				findAreas({
					cityCode: e.val || cityCode
				}, function(json) {
					$("#areasCode-search").select2({
						placeholder: label.district,
						allowClear: true,
						data: {
							results: parsePlaceData(json.object, 'areasCode', 'areasName')
						}
					});
				});
			}
		})
		//省市区
		.on('change', '#provinceCode', function(e) {
			$('#cityCode').select2('val', null);
			$('#areasCode').select2('val', null);
			findCity({
				provinceCode: e.val || provinceCode
			}, function(json) {
				$("#cityCode").select2({
					placeholder: label.city,
					data: {
						results: parsePlaceData(json.object, 'cityCode', 'cityName')
					}
				});
				if (cityCode && cityCode.length > 0)
					$("#cityCode").select2('val', cityCode).trigger('change');
			});
		})
		.on('change', '#cityCode', function(e) {
			$('#areasCode').select2('val', null);
			findAreas({
				cityCode: e.val || cityCode
			}, function(json) {
				$("#areasCode").select2({
					placeholder: label.district,
					data: {
						results: parsePlaceData(json.object, 'areasCode', 'areasName')
					}
				});
				if (areasCode && areasCode.length > 0)
					$("#areasCode").select2('val', areasCode).trigger('change');
			});
		
		})
		// 搜索
		.on('click', '#btn-search', function(event) {
			event.preventDefault();
			var provinceCode = $('#provinceCode-search').select2('val'),
				cityCode = $('#cityCode-search').select2('val'),
				areasCode = $('#areasCode-search').select2('val');
			if (typeof provinceCode == 'string' && provinceCode.length > 0)
				data.provinceCode = provinceCode;
			else
				delete data.provinceCode;
			if (typeof cityCode == 'string' && cityCode.length > 0)
				data.cityCode = cityCode;
			else
				delete data.cityCode;
			if (typeof areasCode == 'string' && areasCode.length > 0)
				data.areasCode = areasCode;
			else
				delete data.areasCode;
			data.districtName = $("#districtName-search").val();
			loadList(1);
		})
		//删除小区
		.on('click', '.btn-delete', function(event) {
			event.preventDefault();
			var that = this;
			common.confirm(label.deletedistrict, function(index) {
				deleteAction($(that).attr('data-id'));
			});
		});
	//上传封面
	upload.uploadImage({
		id: "#btn-add-fengmian",
		close: true
	}, function(json, that) {
		var filePath = fileInternet + json.object.filePath;
		if ($("#coverImgUrl").attr("src") || $("#coverImgUrl").attr("data-src"))
			$("#coverImgUrl").attr({
				"src": filePath,
				"data-src": filePath
			})
		else
			$(that).before('<div><img src="' + filePath + '" width="206" height="150"  id="coverImgUrl" data-src="' + filePath + '"/></div>');
	});
	findProvince(null, function(json) {
		$("#provinceCode-search").select2({
			placeholder: label.province,
			allowClear: true,
			data: {
				results: parsePlaceData(json.object, 'provinceCode', 'provinceName')
			}
		});
	});
}
//分页查询
function loadList(cur) {
	if (cur)
		data.page = cur;
	findPage(data, function(json) {
		isSecondAdmin = json.isUseSpecifiyUsercode;
		var _tmpl = $.trim(tmpl({
			type: 1,
			data: json.object.list,
			isSecondAdmin1 : isSecondAdmin
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
		if (json.object.count == 0 || Math.ceil(json.object.count / data.rows) == data.page) {
			$(".btn-open-save").show();
		} else {
			$(".btn-open-save").hide();
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
// 新增
function saveAction() {
	var that = '#btn-save';
	
	if ($(that).hasClass('z-dis')) return;
	if (validate.check("#svaeCellForm")) {
		var formData = $("#svaeCellForm").serializeJson();
		formData.provinceCode = $('#provinceCode').select2('val');
		formData.cityCode = $('#cityCode').select2('val');
		formData.areasCode = $('#areasCode').select2('val');
		formData.districtImg = $("#coverImgUrl").attr("data-src");
		/*if (!validate.rule['*'].test(formData.provinceCode)) {
			common.tips(label.selectprovince, '#s2id_provinceCode');
			return false;
		}
		if (!validate.rule['*'].test(formData.cityCode)) {
			common.tips(label.selectcity, '#s2id_cityCode');
			return false;
		}
		if (!validate.rule['*'].test(formData.areasCode)) {
			common.tips(label.selectdistrict, '#s2id_areasCode');
			return false;
		}*/
		if(!formData.provinceCode||formData.provinceCode == ""){
			formData.provinceCode = null;
			formData.cityCode = null;
			formData.areasCode = null;
		}
		if(formData.cityCode == ""){
			formData.cityCode = null;
			formData.areasCode = null;
		}
		if(formData.areasCode == ""){
			formData.areasCode = null;
		}
		formData.price = (parseFloat(formData.price, 10) * 100).toFixed(0);
		formData.waterprice = (parseFloat(formData.waterprice, 10) * 100).toFixed(0);
		formData.hotwaterprice = (parseFloat(formData.hotwaterprice, 10) * 100).toFixed(0);
		formData.shareAmount = (parseFloat(formData.shareAmount, 10) * 100).toFixed(0);
		$(that).addClass('z-dis');
		if (formData.id && formData.id.length > 0)
			updateDistrict(formData, 
				function(json) {
					common.msg(json.message, function() {
						$(that).prev().trigger('click');
						loadList();
					})
				},
				function() {
					$(that).removeClass('z-dis');
				});
		else
			saveDistrict(
				formData,
				function(json) {
					common.msg(json.message, function() {
						$(that).prev().trigger('click');
						var len = $("#list>li").not($('.new')).length;
						if (len >= 10) {
							loadList(data.page + 1);
						} else {
							loadList();
						}
					})
				},
				function() {
					$(that).removeClass('z-dis');
				});
	}
};
// 删除
function deleteAction(id) {
	if (id && id.length > 0) {
		deleteDistrict({
				id: id
			},
			function(json) {
				common.msg(json.message, function() {
					common.closeAll();
					if ($("#list>li").not($('.new')).length == 1) {
						loadList(data.page - 1 == 0 ? 1 : data.page - 1);
					} else {
						loadList();
					}
				})
			},
			function() {});
	}
};
//新增小区
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
// 修改小区
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
//分页查询小区
function findPage(data, callback, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/district/findPage',
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
//删除小区
function deleteDistrict(data, success, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/district/deleteDistrict',
		data: data,
		success: function(json) {
			typeof success === 'function' && success(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}