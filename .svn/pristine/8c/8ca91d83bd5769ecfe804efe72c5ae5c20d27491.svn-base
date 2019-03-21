var $ = require('jquery'),
	common = require('static/libs/util/common'),
	validate = require('static/libs/util/validate'),
	upload = require('static/libs/util/upload');
var user = __inline("_user.tmpl");
init();

function init() {
	load();
	$(document)
		//弹出修改窗口
		.on('click', '#btn-modify-pswd', function() { //修改密码
			common.closeAll();
			var that = this;
			var _tmpl = $.trim(user({
				personCode: $(that).attr('data-personCode'),
				type: 0
			}));
			common.open({
				title: false,
				closeBtn: false,
				content: _tmpl,
				scrollbar: false,
				success: function(layero, index) {
					$(layero).find('.btn-close').attr('data-index', index);
				}
			});
		})
		//修改密码
		.on('click', '#resetPswdBtn', function(event) { //修改密码
			if (validate.check('#reset-password-form')) {
				var formData = $('#reset-password-form').serializeJson();
				updatePersonPassword(
					formData,
					function(json) {
						common.msg(json.message, function() {
							location.href = common.BASE_PATH + "/index.html?type=logout";
						})
					})
			}
		})
		// 编辑
		.on('click', '.fa-edit', function(event) {
			event.preventDefault();
			var ipt = $(this).prev('.ipt');
			if ($(this).hasClass('z-sel')) {
				$(this).removeClass('z-sel');
				ipt.find('input').addClass('f-dn');
				ipt.find('span').removeClass('f-dn');
			} else {
				ipt.find('input').removeClass('f-dn');
				ipt.find('span').addClass('f-dn');
				$(this).addClass('z-sel');
			}
		})
		//修改账户信息
		.on('click', '#btn-save', function(event) {
			event.preventDefault();
			saveAction();
		});
}
//加载用户信息
function load() {
	findPerson(null, function(json) {
		var _tmpl01 = $.trim(user({
			type: 1,
			data: json.object
		}));
		var _tmpl02 = $.trim(user({
			type: 2,
			data: json.object
		}));
		$('#main-materials').html(_tmpl01 + _tmpl02);
		//上传封面
		upload.uploadImage({
			id: "#choosePicBtn",
			close: true
		}, function(json, that) {
			var filePath = fileInternet + json.object.filePath;
			if ($("#coverImgUrl").attr("src") || $("#coverImgUrl").attr("data-src"))
				$("#coverImgUrl").attr({
					"src": filePath,
					"data-src": filePath
				})
			else
				$(that).siblings('.head-pic-outline').find('.head-pic').prepend('<div><img src="' + filePath + '" width="140" height="140"  id="coverImgUrl" data-src="' + filePath + '"/></div>');
		});
	})
};
// 新增
function saveAction() {
	var that = '#btn-save';
	if ($(that).hasClass('z-dis')) return;
	if (validate.check("#svaeCellForm")) {
		$(that).addClass('z-dis');
		var formData = $("#svaeCellForm").serializeJson();
		formData.personImgUrl = $("#coverImgUrl").attr("data-src");
		updatePerson(
			formData,
			function(json) {
				common.msg(json.message, function() {
					location.reload(true);
				})
			},
			function() {
				$(that).removeClass('z-dis');
			});

	}
};
//修改密码
function updatePersonPassword(data, callback, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/person/updatePersonPassword',
		data: data,
		success: function(json) {
			typeof callback === 'function' && callback(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}
//修改账户资料
function updatePerson(data, callback, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/person/updatePerson',
		data: data,
		success: function(json) {
			typeof callback === 'function' && callback(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}
//查询用户资料
function findPerson(data, callback, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/person/findPerson',
		data: data,
		success: function(json) {
			typeof callback === 'function' && callback(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}