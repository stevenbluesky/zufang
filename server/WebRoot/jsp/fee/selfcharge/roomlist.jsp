<%@page import="java.util.ResourceBundle"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" /> 
 <%
      //加载i18n资源文件，request.getLocale()获取访问用户所在的国家地区
      ResourceBundle myResourcesBundle = ResourceBundle.getBundle("messages",request.getLocale());
  %>
<title><%=myResourcesBundle.getString("autopayment")%></title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/label/<%=myResourcesBundle.getString("js.label.resourcefile")%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.9.0.min.js"></script>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/iremotewebstyle.css" charset="utf-8"/>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/jsp/fee/selfcharge/feestyle.css" charset="utf-8"/>
<script>
function oncharge(roomid)
{	
	location.href = "<%=request.getContextPath()%>/service/roomfee/roomcharge?roomid=" + roomid ;
}
</script>
<style>
.divblock
{
	width:344px;
	background:#ffffff;
	border-radius:6px;
}
</style>
</head>
<body style="background-color:#f4f4f4;margin:0px;padding:0px">
<form id="roomfeecharge" action="<%=request.getContextPath()%>/service/roomfee/weixincharge" method="POST">
<table style="background-color:#626262" width="100%" >
<tr >
	<td width="100%" align="center" height="48px">
		<span class="title_c"> <%=myResourcesBundle.getString("autopayment")%></span>
	</td>   
</tr>
</table>      
<div style="height:10px;"></div>
<center>
<div class="divblock" style="height:86px;">
	<div class="fontsytle" style="float:left;;margin-left:30px;margin-top:10px">
		<img src="<%=request.getContextPath()%>/images/fee/icon_user.png" style="width:32px;height:32px;margin-top:10px"/>
	</div>
	<div style="float:left;margin-top:24px;margin-left:17px">
		<span class="fontsytle" style="margin-top:10px">${phonenumber }</span>
	</div>
</div>

<div style="height:15px;"></div>

<c:forEach var="room" items="${rooms}" >   
<div class="divblock" style="height:205px;">

<div style="height:48px;">
	<div style="float:left;margin-left:30px;margin-top:15px">${room.districtName }${room.build }<%=myResourcesBundle.getString("view.label.building")%>${room.roomName }</div>
</div>
<div style="height:48px;background:#ffffff;;border-top:1px solid #f4f4f4;">
	<div style="float:left;margin-left:30px;margin-top:10px"><%=myResourcesBundle.getString("yue")%></div>
	<div style="float:right;margin-right:30px;margin-top:10px">${room.balance / 100 }<%=myResourcesBundle.getString("yuan")%></div>
</div>

<div style="height:48px;background:#ffffff;border-top:1px solid #f4f4f4;">
	<div style="float:left;;margin-left:30px;margin-top:10px"><%=myResourcesBundle.getString("qianfei")%></div>
	<div style="float:right;margin-right:30px;margin-top:10px">${room.arrearage / 100 }<%=myResourcesBundle.getString("yuan")%></div>
</div>

<input type="button" name="ok" id="commitbutton" class="button" style="width:87px;height:32px" onclick="oncharge(${room.id})"; value='<%=myResourcesBundle.getString("topup")%>' />

</div>

<p>
</c:forEach>   
 </center>


   
</form>  
</body>
</html>