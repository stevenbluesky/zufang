var $ = require('jquery'),
	common = require('static/libs/util/common'),
	laypage = require('static/libs/laypage/laypage'),
	upload = require('static/libs/util/upload'),
	tmpl = __inline('_log.tmpl');
require('static/libs/laydate/laydate');
var data = {
	page: 1,
	rows: 10
};
init();

function init() {
	loadList(1);
	$(document)
		// 搜索
		.on('click', '#btn-search', function(event) {
			event.preventDefault();
			var beginDate = $('#startTime-search').val(),
				endDate = $('#endTime-search').val(),
				operateName = $('#operateName-search').val();
			data.beginDate = beginDate;
			data.endDate = endDate;
			data.operateName = operateName;
			loadList(1);
		})
	var start = {
		elem: '#startTime-search',
		format: 'YYYY-MM-DD',
		max: '2099-06-16 23:59:59', //最大日期
		istime: false,
		istoday: false,
		choose: function(datas) {
			end.min = datas; //开始日选好后，重置结束日的最小日期
			end.start = datas //将结束日的初始值设定为开始日
		}
	};
	var end = {
		elem: '#endTime-search',
		format: 'YYYY-MM-DD ',
		min: laydate.now(),
		max: '2099-06-16 23:59:59',
		istime: false,
		istoday: false,
		choose: function(datas) {
			start.max = datas; //结束日选好后，重置开始日的最大日期
		}
	};
	laydate(start);
	laydate(end);

}
//分页查询
function loadList(cur) {
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
//分页查询小区
function findPage(data, callback, failure) {
	$.ajax({
		type:"post",
		url: common.BASE_PATH + '/service/operateLog/findPage',
		data: data,
		dataType: "json",
		success: function(json) {
			typeof callback === 'function' && callback(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}