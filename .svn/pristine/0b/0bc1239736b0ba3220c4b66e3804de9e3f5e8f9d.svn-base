var $ = require('jquery'),
	common = require('static/libs/util/common'),
	laypage = require('static/libs/laypage/laypage'),
	validate = require('static/libs/util/validate'),
	echarts = require('components/echarts/echarts.min'),
	moment = require('moment'),
	tmpl = __inline('_electricityLog.tmpl');
	watertmpl = __inline('_waterLog.tmpl');
	roomfeetmpl = __inline('_roomfee.tmpl');
require('static/libs/laydate/laydate');
require('select2');
require('select2/select2_locale_zh-CN');
var _ = require('underscore');
var data = {
		page: 1,
		rows: 10,
		devicetype: 1,
		devicesubtype: null,
		currenttag:0
	},
	config = {
		toolbox: {
			show: true,
			orient: 'horizontal',
			itemSize: 15,
			itemGap: 10,
			showTitle: true,
			feature: {
				saveAsImage: {
					show: true
				},
				restore: {
					show: true
				},
				dataView: {
					show: true
				},
				dataZoom: {
					show: false
				},
				magicType: {
					show: false
				}
			}
		}
	};
init();

function init() {
	moment.locale('zh_cn');
	window.monthStart = {
		elem: '#search-monthStart',
		format: 'YYYY-MM-DD',
		max: '2099-06-16 23:59:59', //最大日期
		istime: false,
		istoday: true,
		choose: function(datas) {
			monthEnd.min = datas; //开始日选好后，重置结束日的最小日期
			monthEnd.start = datas //将结束日的初始值设定为开始日
		}
	};
	window.monthEnd = {
		elem: '#search-monthEnd',
		format: 'YYYY-MM-DD',
		min: laydate.now(),
		max: '2099-06-16 23:59:59',
		istime: false,
		istoday: false,
		choose: function(datas) {
			monthStart.max = datas; //结束日选好后，重置开始日的最大日期
		}
	};
	window.dateStart = {
		elem: '#search-dateStart',
		format: 'YYYY-MM-DD',
		max: '2099-06-16 23:59:59', //最大日期
		istime: false,
		istoday: true,
		choose: function(datas) {
			dateEnd.min = datas; //开始日选好后，重置结束日的最小日期
			var t = new Date(datas);
			var iToDay = t.getDate();
			var iToMon = t.getMonth();
			var iToYear = t.getFullYear();
			var newDay = new Date(iToYear, iToMon, (iToDay + 31));
			dateEnd.max = moment(newDay).format('YYYY-MM-DD'); //开始日选好后，重置结束日的最小日期
			dateEnd.start = datas //将结束日的初始值设定为开始日
		}
	};
	window.dateEnd = {
		elem: '#search-dateEnd',
		format: 'YYYY-MM-DD',
		min: laydate.now(),
		max: '2099-06-16 23:59:59',
		istime: false,
		istoday: false,
		choose: function(datas) {
			var t = new Date(datas);
			var iToDay = t.getDate();
			var iToMon = t.getMonth();
			var iToYear = t.getFullYear();
			var newDay = new Date(iToYear, iToMon, (iToDay - 31));
			dateStart.min = moment(newDay).format('YYYY-MM-DD'); //结束日选好后，重置开始日期的最小日期
			dateStart.max = datas; //结束日选好后，重置开始日的最大日期
		}
	};
	window.hourStart = {
		elem: '#search-hourStart',
		format: 'YYYY-MM-DD hh:mm:ss',
		max: '2099-06-16 23:59:59', //最大日期
		istime: true,
		istoday: true,
		choose: function(datas) {
			hourEnd.min = datas;
			hourEnd.max = moment(datas).format('YYYY-MM-DD') + " 23:59:59";
		}
	};
	window.hourEnd = {
		elem: '#search-hourEnd',
		format: 'YYYY-MM-DD hh:mm:ss',
		min: laydate.now(),
		max: '2099-06-16 23:59:59',
		istime: true,
		istoday: false,
		choose: function(datas) {
			hourStart.min = moment(datas).format('YYYY-MM-DD') + " 00:00:00";
			hourStart.max = datas;
		}
	};
	$(document)
		.on('click', '.search-item li', function(event) {
			event.preventDefault();
			$(this).addClass('z-sel').siblings('li').removeClass('z-sel');
		})
		// 切换
		.on('click', '.form-box-ul li', function(event) {
			event.preventDefault();
			$(this).addClass('z-sel').siblings('.z-sel').removeClass('z-sel');
			var index = $(this).index();
			var boxindex = index ;
			if ( boxindex >= 1  && boxindex <= 4)
				boxindex = 1 ;
			$(".show-box").hide().eq(boxindex).show();
			data.currenttag = index ;
			switch (index) {
				case 1:
					data.devicetype = 1 ;
					data.devicesubtype = null;
					loadList(1 );
					break;
				case 2:
					data.devicetype = 17 ;
					data.devicesubtype = 1;
					loadList(1);
					break;
				case 3:
					data.devicetype = 17 ;
					data.devicesubtype = 2 ;
					loadList(1);
					break;
				case 4:
					loadRoomfee(1);
					break;
			}  
		})
		// 统计方式
		.on('click', '#search-type .item', function(event) {
			event.preventDefault();
			switch ($(this).index()) {
				case 1:
					$(".time").hide();
					$(".month-box").show();
					break;
				case 2:
					$(".time").hide();
					$(".date-box").show();
					break;
				case 3:
					$(".time").hide();
					$(".hour-box").show();
					break;
			}
		})
		// 搜索
		.on('click', '#btn-search', function(event) {
			event.preventDefault();
			loadChart();
		})
		// 搜索
		.on('click', '#btn-search-list', function(event) {
			event.preventDefault();
			if (data.currenttag == 4 )
				loadRoomfee(1);
			else 
				loadList(1);
		});

	loadSelect();
}
// 加载图表
function loadChart(index) {
	var index = index || $("#search-type .z-sel").attr('data-id'),
		formData = {},
		obj = {
			container: '#chart',
			index: index,
			title: label.elereport,
			initFn: function() {}
		};
	formData.districtId = $("#district-search").select2('val');
	var build = $("#build-search").select2('val'),
		uint = $("#unit-search").select2('val'),
		floor = $("#floor-search").select2('val'),
		roomId = $("#room-search").select2('val');
	if (typeof build == 'string' && build.length > 0) {
		formData.build = build;
	}
	if (typeof uint == 'string' && uint.length > 0) {
		formData.unit = uint;
	}
	if (typeof floor == 'string' && floor.length > 0) {
		formData.floor = floor;
	}
	if (typeof roomId == 'string' && roomId.length > 0) {
		formData.roomId = roomId;
	}
	switch (parseInt(index, 10)) {
		case 0:
			if ($("#search-monthStart").val() && $("#search-monthStart").val().length > 0)
				formData.beginMonth = moment($("#search-monthStart").val()).format('YYYYMM');
			if ($("#search-monthEnd").val() && $("#search-monthEnd").val().length > 0)
				formData.endMonth = moment($("#search-monthEnd").val()).format('YYYYMM');
			reportByMonth(formData, function(json) {
				obj.data = json.object;
				load_bar(obj)();
			});
			break;
		case 1:
			if ($("#search-dateStart").val() && $("#search-dateStart").val().length > 0)
				formData.beginDay = moment($("#search-dateStart").val()).format('YYYYMMDD');
			if ($("#search-dateEnd").val() && $("#search-dateEnd").val().length > 0)
				formData.endDay = moment($("#search-dateEnd").val()).format('YYYYMMDD');
			reportByDay(formData, function(json) {
				obj.data = json.object;
				load_bar(obj)();
			});
			break;
		case 2:
			if ($("#search-hourStart").val() && $("#search-hourStart").val().length > 0)
				formData.beginHour = moment($("#search-hourStart").val()).format('YYYYMMDDHH');
			if ($("#search-hourEnd").val() && $("#search-hourEnd").val().length > 0)
				formData.endHour = moment($("#search-hourEnd").val()).format('YYYYMMDDHH');
			reportByHour(formData, function(json) {
				obj.data = json.object;
				load_bar(obj)();
			})
			break;
	}
};

function initqueryfilter()
{
	data.districtId = $("#district-list").select2('val');
	var build = $("#build-list").select2('val'),
		uint = $("#unit-list").select2('val'),
		floor = $("#floor-list").select2('val'),
		roomId = $("#room-list").select2('val');
	if (typeof build == 'string' && build.length > 0) {
		data.build = build;
	}
	if (typeof uint == 'string' && uint.length > 0) {
		data.unit = uint;
	}
	if (typeof floor == 'string' && floor.length > 0) {
		data.floor = floor;
	}
	if (typeof roomId == 'string' && roomId.length > 0) {
		data.roomId = roomId;
	}
	var month = $("#search-month").select2('val');
	if (typeof month == 'string' && month.length > 0)
		data.month = month;
	else
		delete data.month;
};

function loadRoomfee(cur)
{
	data.page = cur;
	initqueryfilter();
	reportRoomFee(data, function(json) {
		var selectData = [],
			sData = [];
		$.each(json.object.list, function(index, val) {
			if (_.indexOf(selectData, val.month) == -1)
				selectData.push(val.month)
		});
		$.each(selectData, function(index, val) {
			sData.push({
				id: val + '',
				text: val + ''
			})
		});
		$("#search-month").select2({
			placeholder: label.month,
			allowClear: true,
			data: {
				results: sData
			}
		});
		
		var _tmpl = $.trim(roomfeetmpl({
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
						/*loadList(e.curr);*/
						loadRoomfee(e.curr);
					}
				}
			});
			$("#page").show();
		} else {
			$("#page").hide();
		}
	})
};

// 加载
function loadList(cur ) {
	data.page = cur;
	initqueryfilter();
	reportDeviceDegreesLog(data, function(json) {
		var selectData = [],
			sData = [];
		$.each(json.object.list, function(index, val) {
			if (_.indexOf(selectData, val.month) == -1)
				selectData.push(val.month)
		});
		$.each(selectData, function(index, val) {
			sData.push({
				id: val + '',
				text: val + ''
			})
		});
		$("#search-month").select2({
			placeholder:label.month,
			allowClear: true,
			data: {
				results: sData
			}
		});
		
		var _tmpl ;
		if ( data.devicetype != null && data.devicetype == 17 )
			_tmpl = $.trim(watertmpl({
				data: json.object.list
			}));
		else 
			_tmpl = $.trim(tmpl({
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
// 加载下拉框
function loadSelect() {
	$(document)
		// 小区
		.on('change', '#district-search', function(e) {
			$('#build-search').select2('destroy');
			$('#unit-search').select2('destroy');
			$('#floor-search').select2('destroy');
			$('#room-search').select2('destroy');
			e.val = $("#district-search").select2('val');
			if (e.val.length != 0) {
				findBuildByDistrictId({
					districtId: e.val
				}, function(json) {
					$("#build-search").select2({
						placeholder: label.building,
						data: {
							results: parsePlaceData(json.object, 'build', 'build')
						}
					});
				});
			}
		})
		// 栋
		.on('change', '#build-search', function(e) {
			$('#unit-search').select2('destroy');
			$('#floor-search').select2('destroy');
			$('#room-search').select2('destroy');
			if (e.val.length != 0) {
				findUnitByBuild({
					districtId: $("#district-search").select2("val"),
					build: e.val
				}, function(json) {
					$("#unit-search").select2({
						placeholder: label.unit,
						data: {
							results: parsePlaceData(json.object, 'unit', 'unit')
						}
					});
				});
			}
		})
		// 单元
		.on('change', '#unit-search', function(e) {
			$('#floor-search').select2('destroy');
			$('#room-search').select2('destroy');
			if (e.val.length != 0) {
				findFloorByUnit({
					districtId: $("#district-search").select2("val"),
					build: $('#build-search').select2("val"),
					unit: e.val
				}, function(json) {
					$("#floor-search").select2({
						placeholder: label.floor,

						data: {
							results: parsePlaceData(json.object, 'floor', 'floor')
						}
					});
				});
			}
		})
		// 房间
		.on('change', '#floor-search', function(e) {
			$('#room-search').select2('destroy');
			if (e.val.length != 0) {
				findRoomByFloor({
					districtId: $("#district-search").select2("val"),
					build: $('#build-search').select2("val"),
					unit: $('#unit-search').select2("val"),
					floor: e.val
				}, function(json) {
					$("#room-search").select2({
						placeholder: label.room,

						data: {
							results: parsePlaceData(json.object, 'id', 'roomName')
						}
					});
				});
			}
		})
		// 小区
		.on('change', '#district-list', function(e) {
			$('#build-list').select2('destroy');
			$('#unit-list').select2('destroy');
			$('#floor-list').select2('destroy');
			$('#room-list').select2('destroy');
			e.val = $("#district-list").select2('val');
			if (e.val.length != 0) {
				findBuildByDistrictId({
					districtId: e.val
				}, function(json) {
					$("#build-list").select2({
						placeholder: label.building,

						data: {
							results: parsePlaceData(json.object, 'build', 'build')
						}
					});
				});
			}
		})
		// 栋
		.on('change', '#build-list', function(e) {
			$('#unit-list').select2('destroy');
			$('#floor-list').select2('destroy');
			$('#room-list').select2('destroy');
			if (e.val.length != 0) {
				findUnitByBuild({
					districtId: $("#district-list").select2("val"),
					build: e.val
				}, function(json) {
					$("#unit-list").select2({
						placeholder:label.unit,

						data: {
							results: parsePlaceData(json.object, 'unit', 'unit')
						}
					});
				});
			}
		})
		// 单元
		.on('change', '#unit-list', function(e) {
			$('#floor-list').select2('destroy');
			$('#room-list').select2('destroy');
			if (e.val.length != 0) {
				findFloorByUnit({
					districtId: $("#district-list").select2("val"),
					build: $('#build-list').select2("val"),
					unit: e.val
				}, function(json) {
					$("#floor-list").select2({
						placeholder: label.floor,

						data: {
							results: parsePlaceData(json.object, 'floor', 'floor')
						}
					});
				});
			}
		})
		// 房间
		.on('change', '#floor-list', function(e) {
			$('#room-list').select2('destroy');
			if (e.val.length != 0) {
				findRoomByFloor({
					districtId: $("#district-list").select2("val"),
					build: $('#build-list').select2("val"),
					unit: $('#unit-list').select2("val"),
					floor: e.val
				}, function(json) {
					$("#room-list").select2({
						placeholder: label.room,

						data: {
							results: parsePlaceData(json.object, 'id', 'roomName')
						}
					});
				});
			}
		});
	// 查询小区列表
	findDistrict(null, function(json) {
		$("#district-search,#district-list").select2({
			placeholder: label.community,
			data: {
				results: parsePlaceData(json.object, 'id', 'districtName')
			}
		});
		if (json.object && json.object.length > 0) {
			$("#district-search").select2('val', json.object[0].id).trigger('change');
			$("#search-type .item").eq(0).trigger('click');
			$("#btn-search").trigger('click');
		}
	});
};
// 解析地址
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
// 报表
function load_bar(obj) {
	if (typeof obj.initFn === 'function') {
		obj.initFn.call(this);
	};
	return function() {
		var dtd = $.Deferred(),
			myChart = echarts.init($(obj.container)[0]),
			xData = [],
			yData = [];
		$.each(obj.data, function(index, val) {
			if (obj.index == 0) xData.push(val.reportMonth);
			else if (obj.index == 1) xData.push(val.reportDay);
			else if (obj.index == 2) xData.push(val.reportDay);
			yData.push(val.degrees);
		});
		var option = {
			title: {
				text: obj.title,
				subtext: ''
			},
			toolbox: config.toolbox,
			tooltip: {
				trigger: 'axis'
			},
			legend: {
				data: obj.title
			},
			calculable: true,
			xAxis: [{
				type: 'category',
				data: xData
			}],
			yAxis: [{
				type: 'value',
				axisLabel: {
					formatter: '{value}'+label.du
				}
			}],
			series: [{
				name: label.elereport,
				type: 'bar',
				data: yData,
				markPoint: {
					data: [{
						type: 'max',
						name: label.max
					}, {
						type: 'min',
						name: label.min
					}]
				},
				markLine: {
					data: [{
						type: 'average',
						name: label.average
					}]
				}
			}]
		};
		myChart.setOption(option);
		setTimeout(function() {
			dtd.resolve();
		}, 500);
		return dtd;
	}
};
// 按月统计用电量
function reportByMonth(data, callback, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/deviceDegreesHourLog/reportByMonth',
		data: data,
		success: function(json) {
			typeof callback === 'function' && callback(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}
// 按日统计
function reportByDay(data, callback, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/deviceDegreesHourLog/reportByDay',
		data: data,
		success: function(json) {
			typeof callback === 'function' && callback(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}
// 按时统计
function reportByHour(data, callback, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/deviceDegreesHourLog/reportByHour',
		data: data,
		success: function(json) {
			typeof callback === 'function' && callback(json);
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
// 查询录入的栋
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
// 查询录入的单元
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
// 查询录入的楼
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
// 查询录入的房间
function findRoomByFloor(data, success, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/room/findRoom',
		data: data,
		success: function(json) {
			typeof success === 'function' && success(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}
// 统计电费
function reportDeviceDegreesLog(data, success, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/device/reportDeviceDegreesLog',
		data: data,
		success: function(json) {
			typeof success === 'function' && success(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}

function reportRoomFee(data , success , failure){
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/userfee/findPage',
		data: data,
		success: function(json) {
			typeof success === 'function' && success(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}