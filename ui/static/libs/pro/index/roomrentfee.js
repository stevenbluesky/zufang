var $ = require('jquery'),
	common = require('static/libs/util/common'),
	laypage = require('static/libs/laypage/laypage'),
	validate = require('static/libs/util/validate'),
	Data = require('static/libs/util/data'),
	upload = require('static/libs/util/upload'),
	moment = require('moment');
	tmpl = __inline('_rftransaction.tmpl');
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
	//loadList(1);
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
		.on('click', '#btn_recharge', function(event) 
		{
			event.preventDefault();
			if (validate.check("#frm_charge")) 
			{
				var amount = $('#amount').val();
				var rechargetype = $('#rechargetype option:selected').val();
				//if ( amount == null )
				//alert(amount);
				
				common.confirm(label.chongzhi + amount + label.fen, function(index) {
					recharge(amount , rechargetype);
					common.closeAll();
					$('#amount').val('');
				});
			}
		})
		.on('click', '#btn_withdraw', function(event) 
		{
			event.preventDefault();
			if (validate.check("#frm_charge")) 
			{
				var amount = $('#amount').val();
				//if ( amount == null )
				//alert(amount);
				
				common.confirm(label.tuikuan + amount + label.fen, function(index) {
					withdraw(amount);
					common.closeAll();
					$('#amount').val('');
				});
			}
		})
		.on('click', '#btn_charge', function(event) 
			{
				event.preventDefault();
				
				if ( balance < arrearage )
				{
					common.msg(label.cannotcharge, function() {
						common.closeAll();
					});
					return ;
				}
				
				charge();
			});
}

var balance ;  
var arrearage;

function charge()
{
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/roomfee/charge',
		data: {roomid: roomId},
		success: function(json) 
		{
			if ( json.success != 0 )
				common.msg(json.message, function() {
					common.closeAll();
				});
			else 
				load();
		},
		failure: function(json) {
		}
	})
}

function recharge(amount , rechargetype)
{
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/roomfee/recharge',
		data: {id: roomId , balance: amount,rechargetype:rechargetype},
		success: function(json) {
			load();
		},
		failure: function(json) {
			;
		}
	})
}

function withdraw(amount)
{
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/roomfee/withdraw',
		data: {id: roomId , balance: amount},
		success: function(json) 
		{   
			if ( json.success != 0 )
				common.msg(json.message, function() {
					common.closeAll();
				});
			else 
				load();
		},
		failure: function(json) {
			;
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
		balance = json.object.balance;
		arrearage = json.object.arrearage;
		$("#balance").html(balance);
		$("#arrearage").html(arrearage);
		
	});
	
	loadDetail(1);
}

function loadDetail(cur)
{
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/roomfee/querytransactionlist',
		data: {roomid: roomId,page:cur},   
		success: function(json)   
		{  
			var _tmpl = $.trim(tmpl({
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
							loadDetail(e.curr);
						}
					}
				});
				$("#page").show();
			} else {
				$("#page").hide();
			}
		},
		failure: function(json) 
		{
			
		}
	})
}

