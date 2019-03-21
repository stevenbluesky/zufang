var $ = require('jquery'),
	common = require('static/libs/util/common'),
	validate = require('static/libs/util/validate');
var findPswd = __inline("static/libs/util/_findpswd.tmpl");
var register = __inline("static/libs/util/_register.tmpl");
var timer;
init();

function init() {
	$(document)
		.on('click', '#btn-login', function(event) {
			event.preventDefault();
			loginAction();
		})
		.on('keydown','#personPassword',function(event){
			if (event.keyCode == 13) {
	            event.returnValue=false;
	            event.cancel = true;
	            event.preventDefault();
				loginAction();
	        }
		})
		.on('click', '#btn-get-captcha', function() { //获取验证码
			getCaptchaAction();
		})
		.on('click', '#btn-get-captcha-1', function(event) {
			event.preventDefault();
			getCaptcha1Action();
		})
		.on('click', '#registerBtn', function(event) { //注册
			/*if ($('#agree').is(':checked')) {*/
				if (validate.check('#register-form')) {
					userRegister({
						countrycode:$('#scountrycode').val(),
						personCode: $.trim($('#personCode').val()),
						personPassword: $('#password').val(),
						captcha: $('#captcha').val(),
						phone: $('#phone').val(),
						realName: $('#real-name').val()
					}, function(json) {
						if (parseInt(json.success) === 1) {
							$('#js-register-form').addClass('f-dn').next('.f-cb').removeClass('f-dn');
						}else if(parseInt(json.success) == -1){
							common.msg(json.message, function() {
								
							})
						}
					}, function(json) {

					})
				}
			/*}*/
		})
		.on('click', '#findPswdBtn', function(event) { //忘记密码
			if (validate.check('#find-password-form')) {
				var formData = $('#find-password-form').serializeJson();
				resetPersonPassword(
					formData,
					function(json) {
						if (parseInt(json.success) === 1) {
							$('#js-forget-form').addClass('f-dn').next('.f-cb').removeClass('f-dn');
						}else if(parseInt(json.success) == -1){
							common.msg(json.message, function() {
								
							})
						}
					},
					function(json) {

					}
				)
			}
		});
	$('#btn-register').on('click', function() { //注册
		common.closeAll();
		clearInterval(timer);
		var _tmpl = $.trim(register());
		common.open({
			title: false,
			closeBtn: false,
			zIndex: 100,
			content: _tmpl,
			shadeClose: false,
			scrollbar: false,
			success: function(layero, index) {
				$(layero).find('.btn-close').attr('data-index', index);
				$("#scountrycode").select2({
					placeholder: label.countrycode,
					//data: Data.roomType
					data: {
						results: parsePlaceData(countryCode, 'countryCode', 'countryName')
					}
				});
			}
		});
	});
	$('#btn-find-pswd').on('click', function() { //找回密码
		common.closeAll();
		clearInterval(timer);
		var _tmpl = $.trim(findPswd());
		common.open({
			title: false,
			closeBtn: false,
			content: _tmpl,
			scrollbar: false,
			shadeClose: 0, //点击遮罩关闭
			success: function(layero, index) {
				$(layero).find('.btn-close').attr('data-index', index);
			}
		});
	})
}
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
// 获取验证码
function getCaptchaAction() {
	var that = '#btn-get-captcha';
	if ($(that).hasClass('z-dis')) return;
	if (validate.checkEle("#personCode") && validate.checkEle("#phone")) {
		$(that).addClass('z-dis');
		var formData = {};
		formData.phone = $("#phone").val();
		formData.personCode = $("#personCode").val();
		sendRegistCheckNumber(
			formData,
			function(json) {
				common.msg(json.message);
				var i = 59;
				timer = setInterval(function() {
					if (i == 1) {
						clearInterval(timer);
						$(that).text(label.resend);
						$(that).removeClass('z-dis');
					} else {
						$(that).text(i + 's');
						i--;
					}
				}, 1000);
			},
			function() {
				$(that).removeClass('z-dis');
			});
	}
};
// 忘记密码
function getCaptcha1Action() {
	var that = '#btn-get-captcha-1';
	if ($(that).hasClass('z-dis')) return;
	if (validate.checkEle("#personCode")) {
		$(that).addClass('z-dis');
		var formData = {};
		formData.personCode = $("#personCode").val();
		sendResetPassCheckNumber(
			formData,
			function(json) {
				common.msg(json.message);
				var i = 59;
				timer = setInterval(function() {
					if (i == 1) {
						clearInterval(timer);
						$(that).text(label.resend);
						$(that).removeClass('z-dis');
					} else {
						$(that).text(i + 's');
						i--;
					}
				}, 1000);
			},
			function() {
				$(that).removeClass('z-dis');
			});
	}
};
// 登陆
function loginAction() {
	var that = '#btn-login';
	if ($(that).hasClass('z-dis')) return;
	if (validate.check("#form")) {
		$(that).addClass('z-dis');
		var formData = $("#form").serializeJson();
		formData.ip = returnCitySN.cip;
		login(
			formData,
			function(json) {
				common.msg(json.message, function() {
					location.href = common.BASE_PATH + '/cell.html';
				})
			},
			function() {
				$(that).removeClass('z-dis');
			});
	}
};
//登录
function login(data, callback, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/person/login',
		data: data,
		success: function(json) {
			if(json.success==1){
				typeof callback === 'function' && callback(json);
			}else{
				common.msg(json.message);
				typeof failure === 'function' && failure(json);
			}	
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}
//注册
function userRegister(data, callback, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/person/regist',
		data: data,
		success: function(json) {
			typeof callback === 'function' && callback(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}
//忘记密码
function resetPersonPassword(data, callback, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/person/resetPersonPassword',
		data: data,
		success: function(json) {
			typeof callback === 'function' && callback(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}
//获取验证码
function sendRegistCheckNumber(data, callback, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/person/sendRegistCheckNumber',
		data: data,
		success: function(json) {
			typeof callback === 'function' && callback(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}
function sendResetPassCheckNumber(data, callback, failure) {
	$.ajax({
		type:"post",
		dataType: "json",
		url: common.BASE_PATH + '/service/person/sendResetPassCheckNumber',
		data: data,
		success: function(json) {
			typeof callback === 'function' && callback(json);
		},
		failure: function(json) {
			typeof failure === 'function' && failure(json);
		}
	})
}