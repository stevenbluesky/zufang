var $ = require('jquery'),
	common = require('static/libs/util/common'),
	laypage = require('static/libs/laypage/laypage'),
	validate = require('static/libs/util/validate'),
	tmpl = __inline('_admin.tmpl');
var cellId = common.getUrlParm("id");
var districtId=cellId;
var data = {
	page: 1,
	rows: 10
};
var doorlockpasswordruleid = 0;
init();

function init() {
	loadList(1);
	$(document)
		// 新增页面
		.on('click', '#btn-add', function(event) {
			event.preventDefault();
			var _tmpl = $.trim(tmpl({
				type: 2,
			}));
			common.open({
				title: false,
				closeBtn: false,
				zIndex: 100,
				content: _tmpl,
				shadeClose: false,
				scrollbar: false,
				success: function(layero, index) {
					$(layero).find('.btn-close').attr('data-index', index);
					
				}
			});
			if(label.dlang!="cn"){
				$("#phone").attr({datatype:"n"});
			};
		})
		// 打开修改页面
		.on('click', '#btn-edit', function(event) {
			event.preventDefault();
			var ids = [];
			var ci = document.getElementsByName("no");
			//遍历复选框数组  如果被选中ci[i].checked的值就是true
			for(var i = 0;i < ci.length;i++){
				if(ci[i].checked==true){
					ids.push(ci[i].value);
				}
			}
			if(ids.length!=1){
				var mess = label.choosearecord;
				common.msg(mess);
				return ;
			}
			$.ajax({
				type:"post",
				dataType: "json",
				url: common.BASE_PATH + '/service/person/getadmininfo',
				data: {"adminid":ids[0]},
				success: function(json) {
					typeof success === 'function' && success(json);
					var _tmpl = $.trim(tmpl({
						type: 3,
						data: json.object
					}));
					common.open({
						title: false,
						closeBtn: false,
						zIndex: 100,
						content: _tmpl,
						shadeClose: false,
						scrollbar: false,
						success: function(layero, index) {
							$(layero).find('.btn-close').attr('data-index', index);
						}
					});
					if(label.dlang!="cn"){
						$("#phone").attr({datatype:"n"});
					};
				},
				failure: function(json) {
					typeof failure === 'function' && failure(json);
				}
			})
		})
		// 打开修改密码页面
		.on('click', '#btn-changepassword', function(event) {
			event.preventDefault();
			var ids = [];
			var ci = document.getElementsByName("no");
			//遍历复选框数组  如果被选中ci[i].checked的值就是true
			for(var i = 0;i < ci.length;i++){
				if(ci[i].checked==true){
					ids.push(ci[i].value);
				}
			}
			if(ids.length!=1){
				var mess = label.choosearecord;
				common.msg(mess);
				return ;
			}
			$.ajax({
				type:"post",
				dataType: "json",
				url: common.BASE_PATH + '/service/person/getadmininfo',
				data: {"adminid":ids[0]},
				success: function(json) {
					typeof success === 'function' && success(json);
					var _tmpl = $.trim(tmpl({
						type: 4,
						data: json.object
					}));
					common.open({
						title: false,
						closeBtn: false,
						zIndex: 100,
						content: _tmpl,
						shadeClose: false,
						scrollbar: false,
						success: function(layero, index) {
							$(layero).find('.btn-close').attr('data-index', index);
						}
					});
				},
				failure: function(json) {
					typeof failure === 'function' && failure(json);
				}
			})
		})
		//dataprivilege
		.on('click', '#btn-dataprivilege', function(event) {
			event.preventDefault();
			var ids = [];
			var ci = document.getElementsByName("no");
			//遍历复选框数组  如果被选中ci[i].checked的值就是true
			for(var i = 0;i < ci.length;i++){
				if(ci[i].checked==true){
					ids.push(ci[i].value);
				}
			}
			if(ids.length!=1){
				var mess = label.choosearecord;
				common.msg(mess);
				return ;
			}
			$.ajax({
				type:"post",
				url: common.BASE_PATH + '/service/person/findprivilege',
				data: {"adminid":ids[0]},
				dataType: "json",
				success: function(json) {
					typeof callback === 'function' && callback(json);
					var _tmpl = $.trim(tmpl({
						type: 5,
						data: json.object,
						admindata: json.obj
					}));
					common.open({
						title: false,
						closeBtn: false,
						zIndex: 100,
						content: _tmpl,
						shadeClose: false,
						scrollbar: false,
						success: function(layero, index) {
							$(layero).find('.btn-close').attr('data-index', index);
						}
					});
				},
				failure: function(json) {
					typeof failure === 'function' && failure(json);
				}
			})
		})
		//btn-save-privilege
		.on('click', '#btn-save-privilege', function(event) {
			event.preventDefault();
			var ids = [];
			var ci = document.getElementsByName("districtno");
			var adminid = document.getElementById("adminid").value;
			//遍历复选框数组  如果被选中ci[i].checked的值就是true
			for(var i = 0;i < ci.length;i++){
				if(ci[i].checked==true){
					ids.push(ci[i].value);
				}
			}
			$.ajax({
				type:"post",
				url: common.BASE_PATH + '/service/person/updateprivilege',
				data: {"adminid":adminid,"ids":ids},
				dataType: "json",
				success: function(json) {
					common.msg(json.message, function() {
						common.closeAll();
					})
				},
				failure: function(json) {
					common.msg(json.message, function() {
						common.closeAll();
					})
				}
			})
		})
		//新增界面保存操作
		.on('click', '#btn-save-add', function(event) {
			event.preventDefault();
			saveAction(2);
		})
		//修改界面保存操作
		.on('click', '#btn-save-modify', function(event) {
			event.preventDefault();
			saveAction(3);
		})
		//修改界面保存操作
		.on('click', '#btn-save-modifyp', function(event) {
			event.preventDefault();
			saveAction(4);
		})
		//点击删除按钮btn-delete
		.on('click', '#btn-delete', function(event) {
			event.preventDefault();
			var ids = [];
			var ci = document.getElementsByName("no");
			//遍历复选框数组  如果被选中ci[i].checked的值就是true
			for(var i = 0;i < ci.length;i++){
				if(ci[i].checked==true){
					ids.push(ci[i].value);
				}
			}
			if(ids.length!=1){
				var mess = label.choosearecord;
				common.msg(mess);
				return ;
			}
			var mess = label.confirmtodeleteadmin;
			common.confirm(mess, function(index) {
				updateAdminStatus('9');
			});

		})
		//恢复
		.on('click', '#btn-recover', function(event) {
			event.preventDefault();
			var ids = [];
			var ci = document.getElementsByName("no");
			//遍历复选框数组  如果被选中ci[i].checked的值就是true
			for(var i = 0;i < ci.length;i++){
				if(ci[i].checked==true){
					ids.push(ci[i].value);
				}
			}
			if(ids.length!=1){
				var mess = label.choosearecord;
				common.msg(mess);
				return ;
			}
			var mess = label.confirmtorecoveradmin;
			common.confirm(mess, function(index) {
				updateAdminStatus('1');
			});

		})
		//冻结
		.on('click', '#btn-freeze', function(event) {
			event.preventDefault();
			var ids = [];
			var ci = document.getElementsByName("no");
			//遍历复选框数组  如果被选中ci[i].checked的值就是true
			for(var i = 0;i < ci.length;i++){
				if(ci[i].checked==true){
					ids.push(ci[i].value);
				}
			}
			if(ids.length!=1){
				var mess = label.choosearecord;
				common.msg(mess);
				return ;
			}
			var mess = label.confirmtofreezeadmin;
			common.confirm(mess, function(index) {
				updateAdminStatus('2');
			});

		})
}


function updateAdminStatus(obj){
	var ids = [];
	var ci = document.getElementsByName("no");
	//遍历复选框数组  如果被选中ci[i].checked的值就是true
	for(var i = 0;i < ci.length;i++){
		if(ci[i].checked==true){
			ids.push(ci[i].value);
		}
	}
	if(ids.length<1){
		var mess = label.choosearecord;
		common.msg(mess);
		return ;
	}
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/person/deleteadmin',
		data: {"ids":ids,"operate":obj},
		success: function(json) {
			typeof success === 'function' && success(json);
			loadList(1);
			var mess = label.operatesuccess;
			common.msg(mess);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}

//分页查询
function loadList(cur) {
	data.page = cur;
	data.districtid = districtId;
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
	});
	
};


//分页查询二级管理员
function findPage(data, callback, failure) {
	$.ajax({
		type:"post",
		url: common.BASE_PATH + '/service/person/findAdminPage',
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
//修改
function saveAction(type) {
	var that = '#btn-save-add';
	if ($(that).hasClass('z-dis')) return;
	if (validate.check("#svaeCellForm")) {
		$(that).addClass('z-dis');
		var formData = $("#svaeCellForm").serializeJson();
		if(type==2){
			saveadmin(formData,
					function(json){
				common.msg(json.message,function(){
					if(json.success==-1){
						$(that).removeClass('z-dis');
						return;
					}
					common.closeAll();
				})
			}
			);
		}else if(type==3){
			updateadminbaseinfo(formData,
					function(json){
				common.msg(json.message,function(){
					common.closeAll();
				})
			}
			);
		}else if(type==4){
			updateadminpassword(formData,
					function(json){
				common.msg(json.message,function(){
					common.closeAll();
				})
			}
			);
		}
	}
};
function saveadmin(data, callback, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/person/saveadmin',
		data: data,
		success: function(json) {
			typeof callback === 'function' && callback(json);
			loadList(1);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}
function updateadminbaseinfo(data, callback, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/person/updateadminbaseinfo',
		data: data,
		success: function(json) {
			typeof callback === 'function' && callback(json);
			loadList(1);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}
function updateadminpassword(data, callback, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/person/updateadminpassword',
		data: data,
		success: function(json) {
			typeof callback === 'function' && callback(json);
			loadList(1);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}