function loadSelect() {
	$(document)
		.on('click', '.form-box-ul li', function(event) {
			event.preventDefault();
			var index = $(this).index();
			switch (index) {
				case 0:
					location.href = BASE_PATH + '/roombalance.html';
					break;
				case 1:
					location.href = BASE_PATH + '/smshistory.html';
					break;
			}
		})
		// 小区
		.on('change', '#district-search', function(e) {
			$('#build-search').val("");
			$('#unit-search').val("");
			$('#floor-search').val("");
			$('#room-search').val("");
			destroySelect2($('#build-search'));
			destroySelect2($('#unit-search'));
			destroySelect2($('#floor-search'));
			destroySelect2($('#room-search'));
			e.val = $("#district-search").select2('val');
			if (e.val.length != 0) {
				findBuildByDistrictId({districtId: e.val});
			}
		})
		// 栋
		.on('change', '#build-search', function(e) {
			$('#unit-search').val("");
			$('#floor-search').val("");
			$('#room-search').val("");
			destroySelect2($('#unit-search'));
			destroySelect2($('#floor-search'));
			destroySelect2($('#room-search'));
			e.val = $("#build-search").select2('val');
			if (e.val.length != 0) {
				findUnitByBuild({
					districtId: $("#district-search").select2("val"),
					build: e.val
				});
			}
		})
		// 单元
		.on('change', '#unit-search', function(e) {
			$('#floor-search').val("");
			$('#room-search').val("");
			destroySelect2($('#floor-search'));
			destroySelect2($('#room-search'));
			e.val = $("#unit-search").select2('val');
			if (e.val.length != 0) {
				findFloorByUnit({
					districtId: $("#district-search").select2("val"),
					build: $('#build-search').select2("val"),
					unit: e.val
				});
			}
		})
		// 房间
		.on('change', '#floor-search', function(e) {
			$('#room-search').val("");
			destroySelect2($('#room-search'));
			e.val = $("#floor-search").select2('val');
			if (e.val.length != 0) {
				findRoomByFloor({
					districtId: $("#district-search").select2("val"),
					build: $('#build-search').select2("val"),
					unit: $('#unit-search').select2("val"),
					floor: e.val
				});
			}
		})		
		.on('click', '.search-item li', function(event) {
			event.preventDefault();
			$(this).addClass('z-sel').siblings('li').removeClass('z-sel');
		})		
		.on('click', '#search-type .item', function(event) {
			event.preventDefault();
			switch ($(this).index()) {
				case 1:
					$("#arrearageonly").val("");
					break;
				case 2:
					$("#arrearageonly").val("false");
					break;
				case 3:
					$("#arrearageonly").val("true");
					break;
			}
		});

	
	// 查询小区列表
	findDistrict(); 
}

function parsePlaceData(obj, key, value) {
	var arr = [];
	arr.push({
		id: "",
		text: ""
	});
	var i = 0 ;
	$.each(obj, function(i, n) {
		arr.push({
			id: n[key],
			text: n[value]
		})
	})

	return arr;
};

function findDistrict(data) 
{
	$.ajax({
		type:"post",
		dataType: "json",
		url: BASE_PATH + '/service/district/findDistrict',
		data: data,
		success: function(json) 
		{
			$("#district-search,#district-list").select2({
				placeholder: label.community,
				allowClear:true,
				data:parsePlaceData(json.object, 'id', 'districtName')
			}); 
			
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}

//查询录入的栋
function findBuildByDistrictId(data) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: BASE_PATH + '/service/room/findBuildByDistrictId',
		data: data,
		success: function(json) 
		{
			$("#build-search").select2({
				placeholder: label.building,
				allowClear:true,
				data:parsePlaceData(json.object, 'build', 'build')
			});

		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}
// 查询录入的单元
function findUnitByBuild(data) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: BASE_PATH + '/service/room/findUnitByBuild',
		data: data,
		success: function(json) {
			$("#unit-search").select2({
				placeholder: label.unit,
				allowClear:true,
				data:parsePlaceData(json.object, 'unit', 'unit')
			});
		},
		failure: function(json) {
			
		}
	})
}
// 查询录入的楼
function findFloorByUnit(data) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: BASE_PATH + '/service/room/findFloorByUnit',
		data: data,
		success: function(json) {
			$("#floor-search").select2({
				placeholder: label.floor,
				allowClear:true,
				data:parsePlaceData(json.object, 'floor', 'floor')
			});
		},
		failure: function(json) {
			
		}
	})
}
// 查询录入的房间
function findRoomByFloor(data) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: BASE_PATH + '/service/room/findRoom',
		data: data,
		success: function(json) {
			$("#room-search").select2({
				placeholder: label.room,
				allowClear:true,
				data:parsePlaceData(json.object, 'id', 'roomName')
			});
		},
		failure: function(json) {
			
		}
	})
}

function destroySelect2(item)
{
	if ( item.data('select2') == null ) 
		return ;
	item.select2('destroy');
}

$(document).ready(function()
{
	loadSelect();
	
	
});