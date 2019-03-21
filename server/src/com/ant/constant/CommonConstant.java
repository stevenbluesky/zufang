package com.ant.constant;

import io.netty.channel.EventLoopGroup;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class CommonConstant {
	
	/**系统路径*/
	public static String realPath = "";
	
	public static String webPath = "";
	
	public static final String DEFAULT_COUNTRYCODE = "86";
	
	/**过滤session控制*/
	public static Map<String, String> webPathMap = new HashMap<String, String>();
	
	static {
		webPathMap.put("/service/person/regist", "/service/person/regist");
		webPathMap.put("/service/person/login", "/service/person/login");
		webPathMap.put("/service/person/logout", "/service/person/logout");
		webPathMap.put("/service/person/resetPersonPassword", "/service/person/resetPersonPassword");
		
		webPathMap.put("/service/province/findProvince", "/service/province/findProvince");
		webPathMap.put("/service/province/findCity", "/service/province/findCity");
		webPathMap.put("/service/province/findAreas", "/service/province/findAreas");
		webPathMap.put("/service/person/sendRegistCheckNumber", "/service/person/sendRegistCheckNumber");
		webPathMap.put("/service/person/sendResetPassCheckNumber", "/service/person/sendResetPassCheckNumber");
		
		webPathMap.put("/service/roomfee/queryuserrooms", "/service/roomfee/queryuserrooms");
		webPathMap.put("/service/roomfee/selfcharge", "/service/roomfee/selfcharge");
		webPathMap.put("/service/roomfee/charge", "/service/roomfee/charge");
		webPathMap.put("/service/roomfee/weixincharge", "/service/roomfee/weixincharge");
		webPathMap.put("/service/roomfee/winxinnotify", "/service/roomfee/winxinnotify");
		webPathMap.put("/service/roomfee/roomcharge", "/service/roomfee/roomcharge");
		webPathMap.put("/service/roomfee/queryroomchargeresult", "/service/roomfee/queryroomchargeresult");
	}
	
	/**调用接口开发，0 关闭；1 开启*/
	public static final Integer restFlag = 1;
	
	/**0未打开连接；1已打开连接*/
	public static  Integer tcpFlag=0;
	
	public static EventLoopGroup group;
	
	public static String tcpEnd="";
	
	public static String getErrorMsg(int resultCode, ResourceBundle rb){
		String message =resultCode + "";
		switch (resultCode)
		{
		case 10100:
			message = rb.getString("10100");//管理员已注册
			break;
		case 10022:
			message = rb.getString("10022");//没有权限
			break ;
		case 10023:
			message = rb.getString("10023");//未找到目标管理员
			break ;
		case 10024:
			message = rb.getString("10024");//设备无响应
			break ;
		case 10006:
			message = rb.getString("10006");//超时
			break ;
		case 10005:
			message = rb.getString("10005");//密码不合法，设置失败-->云端错误
			break ;
		case 30510:
			message = rb.getString("30510");//密码已使用过，请重新录入
			break ;
		case 30311:
			message = rb.getString("30311");//目标设备不存在
			break ;
		case 30312:
			message = rb.getString("30312");//网关或门锁离线
			break ;
		case 30313:
			message = rb.getString("30313");//激活后再开锁
			break ;
		case 30315:
			message = rb.getString("30315");//参数不正确
			break ;
		case 10210:
			message = rb.getString("10210");//用户未注册
			break ;
		case 30530:
			message = rb.getString("30530");//管理员账号不合法
			break ;
		case 30511:
			message = rb.getString("30511");//密码冲突
			break ;
		case 30512:
			message = rb.getString("30512");//密码已满
			break ;
		case 30513:
			message = rb.getString("30513");//密码重复
			break ;
		case 30514:
			message = rb.getString("30514");//门锁禁止远程开锁
			break ;
		case 30515:
			message = rb.getString("30515");//指纹设置失败
			break ;
		case 30516:
			message = rb.getString("30516");//门锁指纹已满
			break ;
		case 30517:
			message = rb.getString("30517");//门锁无响应
			break ;
		case 30518:
			message = rb.getString("30518");//指纹写入失败
			break ;
		case 30519:
			message = rb.getString("30519");//指纹存储失败
			break ;
		case 30580:
			message = rb.getString("30580");//门锁用户下发成功，有效期下发失败
			break;
		case 30581:
			message = rb.getString("30581");//找不到指定的门锁用户
			break;
		case 30582:
			message = rb.getString("30582");//删除门锁用户失败
			break;
		default :
			message = rb.getString("seeci") + String.valueOf(resultCode);//系统错误，错误代码是：
			break;
		}
		return message;
	}
	
}

