var $ = require('jquery'),
	common = require('static/libs/util/common'),
	laypage = require('static/libs/laypage/laypage'),
	validate = require('static/libs/util/validate'),
	Data = require('static/libs/util/data'),
	upload = require('static/libs/util/upload'),
	moment = require('moment'),
	tmpl = __inline('_roomprivilege.tmpl');
require('select2');
require('select2/select2_locale_zh-CN');
require('static/libs/laydate/laydate');
var cellId = common.getUrlParm("id"),
roomId = common.getUrlParm("roomId"),
	data = {
		roomId: roomId,
		page: 1,
		rows: 10
	},
	roomType;

init();

function init() {
	window.start = {
			elem: '#startTime',
			format: 'YYYY-MM-DD hh:mm:ss',
			max: '2099-06-16 23:59:59', //最大日期
			istime: true,
			istoday: true,
			choose: function(datas) {
				end.min = datas; //开始日选好后，重置结束日的最小日期
				end.start = datas //将结束日的初始值设定为开始日
			}
		};
		window.end = {
			elem: '#endTime',
			format: 'YYYY-MM-DD hh:mm:ss',
			min: laydate.now(),
			max: '2099-06-16 23:59:59',
			istime: true,
			istoday: false,
			choose: function(datas) {
				start.max = datas; //结束日选好后，重置开始日的最大日期
			}
		};
	load();
	loadList(1);
	$(document)
		.on('click', '.form-box-ul li', function(event) {
			event.preventDefault();
			var index = $(this).index();
			switch (index) {
				case 0:
					location.href = common.BASE_PATH + '/equipment.html?id=' + cellId +'&roomId=' + roomId;
					break;
				case 1:
					location.href = common.BASE_PATH + '/roomprivilege.html?id=' + cellId +'&roomId=' + roomId;
					break;
				case 2:
					location.href = common.BASE_PATH + '/roomfee.html?id=' + cellId +'&roomId=' + roomId;
					break;
			}
		})
		.on('click', '#btn-grant', function(event) {
			event.preventDefault();
			grantAction();
		})
		.on('click', '.btn-open-save', function(event) {
			event.preventDefault();
			var _tmpl = $.trim(tmpl({
				type: 0
			}));
			roomType = '';
			common.open({
				title: false,
				closeBtn: false,
				zIndex: 100,
				content: _tmpl,
				shadeClose: false,
				scrollbar: false,
				success: function(layero, index) {
					$(layero).find('.btn-close').attr('data-index', index);
					$("#roomType").select2({
						placeholder: label.roomtype,
						data: Data.roomType
					});
					$("#scountrycode").select2({
						placeholder: label.countrycode,
						data: {
							results: parsePlaceData(countryCode, 'countryCode', 'countryName')
						}
					});
				}
			});
		})
		//新增
		.on('click', '#btn-save', function(event) {
			event.preventDefault();
			saveAction();
		})
		// 修改
		.on('click', '.btn-open-edit', function(event) {
			event.preventDefault();
			var that = this,
				_tmpl = $.trim(tmpl({
					type: 2,
					data: {
						id: $(that).attr("data-id"),
						districtId: $(that).attr("data-districtId"),
						roomName: $(that).attr("data-roomName"),
						build: $(that).attr("data-build"),
						unit: $(that).attr("data-unit"),
						floor: $(that).attr("data-floor"),
						remark: $(that).attr("data-remark"),
						roomImg: $(that).attr("data-roomImg")
					}
				}));
			roomType = $(that).attr("data-roomType");
			common.open({
				title: false,
				closeBtn: false,
				zIndex: 100,
				content: _tmpl,
				shadeClose: false,
				scrollbar: false,
				success: function(layero, index) {
					$(layero).find('.btn-close').attr('data-index', index);
					$("#roomType").select2({
						placeholder: label.roomtype,
						data: Data.roomType
					});
					if (roomType && roomType.length > 0)
						$("#roomType").select2('val', roomType).trigger('change');
				}
			});
		})
		//省市区
		.on('change', '#provinceCode-search', function(e) {
			$('#areasCode-search').select2('destroy');
			if (e.val.length == 0) {
				$('#cityCode-search').select2('destroy');
			} else {
				findUnitByBuild({
					districtId: cellId,
					build: e.val
				}, function(json) {
					$("#cityCode-search").select2({
						placeholder: label.unit,
						allowClear: true,
						data: {
							results: parsePlaceData(json.object, 'unit', 'unit')
						}
					});
				});
			}
		})
		.on('change', '#cityCode-search', function(e) {
			if (e.val.length == 0) {
				$('#areasCode-search').select2('destroy');
			} else {
				findFloorByUnit({
					districtId: cellId,
					build: $('#provinceCode-search').select2("val"),
					unit: e.val
				}, function(json) {
					$("#areasCode-search").select2({
						placeholder: label.floor,
						allowClear: true,
						data: {
							results: parsePlaceData(json.object, 'floor', 'floor')
						}
					});
				});
			}
		})
		// 搜索
		.on('click', '#btn-search', function(event) {
			event.preventDefault();
			var build = $('#provinceCode-search').select2('val'),
				unit = $('#cityCode-search').select2('val'),
				floor = $('#areasCode-search').select2('val');
			if (typeof build == 'string' && build.length > 0)
				data.build = build;
			else
				delete data.build;
			if (typeof unit == 'string' && unit.length > 0)
				data.unit = unit;
			else
				delete data.unit;
			if (typeof floor == 'string' && floor.length > 0)
				data.floor = floor;
			else
				delete data.floor;
			data.roomName = $("#roomName-search").val();
			loadList(1);
		})
		//删除房间
		.on('click', '.btn-delete', function(event) {
			event.preventDefault();
			var that = this;
		common.confirm(label.deleteauth, function(index) {
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
	findBuildByDistrictId({
		districtId: cellId
	}, function(json) {
		$("#provinceCode-search").select2({
			placeholder: label.building,
			allowClear: true,
			data: {
				results: parsePlaceData(json.object, 'build', 'build')
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

//查询房间详细信息
function findRoomById(data, success, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/room/findRoomById',
		data: data,
		success: function(json) {  
			typeof success === 'function' && success(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
};

// 查询小区详细信息
function load() {
	findDistrictById({
		id: cellId
	}, function(json) {
		$("#cellName").html("<i>&gt;</i>" + json.object.districtName + label.community);
	});
	findRoomById({
		id: roomId
	}, function(json) {
		var href = $("#roomName").attr('href') + '?id=' + cellId;

		$("#roomName").html("<i>&gt;</i>" + json.object.roomName + label.room).attr('href', href);
		if (json.object.grantFlag == 0) {
			$("#btn-open-grant").show();
		} else {
			$("#btn-delete-grant").show();
			$("#equipmentName").append(label.alreadyauthrizeto + json.object.grantRealName + "(" + json.object.grantUserName + ")");

		}
	});
};

// 删除设备
function deleteAction(id) {
	if (id && id.length > 0) {
		deleteRoomPrivilege({
				id: id,
				roomId: roomId
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
// 新增
function saveAction() {
	var that = '#btn-save';
	if ($(that).hasClass('z-dis')) return;
	if (validate.check("#svaeCellForm")) {
		var formData = $("#svaeCellForm").serializeJson();
		formData.roomType = $('#roomType').select2('val');
		formData.roomImg = $("#coverImgUrl").attr("data-src");
		formData.districtId = cellId;
		if (formData.roomType.length == 0) {
			$('#s2id_roomType').focus();
			common.tips(label.chooseroomtype, '#s2id_roomType');
			return false;
		}
		$(that).addClass('z-dis');
		if (formData.id && formData.id.length > 0)
			updateRoom(formData,
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
			saveRoom(
				formData,
				function(json) {
					common.msg(json.message, function() {
						$(that).prev().trigger('click');
						var len = $("#list>li").not($('.new')).length;
						if (len >= 10) {
							loadList(data.page+1);
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
//新增
function saveRoom(data, callback, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/room/saveRoom',
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
function updateRoom(data, callback, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/room/updateRoom',
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
		url: common.BASE_PATH + '/service/roomprivilege/findPage',
		data: data,
		success: function(json) {
			typeof callback === 'function' && callback(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}
//查询我录入的栋
function findBuildByDistrictId(data, success, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/room/findBuildByDistrictId',
		data: data,
		success: function(json) {
			typeof success === 'function' && success(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}
//查询我录入的单元
function findUnitByBuild(data, success, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/room/findUnitByBuild',
		data: data,
		success: function(json) {
			typeof success === 'function' && success(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}
//查询我录入的楼
function findFloorByUnit(data, success, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/room/findFloorByUnit',
		data: data,
		success: function(json) {
			typeof success === 'function' && success(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}
//查询小区详细信息
function findDistrictById(data, success, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/district/findDistrictById',
		data: data,
		success: function(json) {
			typeof success === 'function' && success(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}
//删除房间
function deleteRoomPrivilege(data, success, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/roomprivilege/deleteRoomPrivilege',
		data: data,
		success: function(json) {
			typeof success === 'function' && success(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}

//删除授权
function deleteGrantAction(id) {
	unGrant({
			id: roomId
		},
		function(json) {
			common.msg(json.message, function() {
				common.closeAll();
				location.reload(true);
			})
		},
		function() {});
};
//授权
function grantAction(id) {
	var that = '#btn-save',
		id = $(".fa-dot-circle-o").attr('data-id');
	if ($(that).hasClass('z-dis')) return;
	if (validate.check("#svaeCellForm")) {
		$(that).addClass('z-dis');
		var formData = $("#svaeCellForm").serializeJson();
		formData.roomId = roomId;
		grant(formData,
			function(json) {
				common.msg(json.message, function() {
					common.closeAll();
					location.reload(true);
				})
			},
			function() {
				$(that).removeClass('z-dis');
			});
	}
};

function grant(data, success, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/roomprivilege/saveRoomPrivilege',
		data: data,
		success: function(json) {
			typeof success === 'function' && success(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}