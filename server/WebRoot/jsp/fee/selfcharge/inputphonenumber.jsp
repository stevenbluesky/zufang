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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.cookie.js"></script>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/iremotewebstyle.css" charset="utf-8"/>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/jsp/fee/selfcharge/feestyle.css" charset="utf-8"/>
<style>
.fontsytle
{
	font: normal 16px arial, sans-serif; 
	font-family:"Source Han Sans";
}

.inputtext
{
	border:1px solid #e1e1e1;
	border-radius:6px;
}
</style>
<script>
$(document).ready(function()
{
	var pk = $.cookie("phonenumber");
	if ( pk != null )
		$("#phonenumber").val(pk);
});

function avoidIMJumpKey()
{
	if(event.keyCode==13)
		return false;
}

</script>
</head>
<body style="background-color:#f4f4f4;margin:0px;padding:0px">
<form id="roofeeinputphonenumber" action="<%=request.getContextPath()%>/service/roomfee/queryuserrooms" method="POST"  onkeydown="return avoidIMJumpKey();">
<table style="background-color:#626262" width="100%" >
<tr >
	<td width="100%" align="center" height="48px">
		<span class="title_c"> <%=myResourcesBundle.getString("autopayment")%></span>
	</td>   
</tr>
</table>      
<div>
<img src="<%=request.getContextPath()%>/images/fee/icon_iphone.png" style="position:absolute;left:51px;top:179px;height:48px;width:48px"/> 
<input type="text" name="phonenumber" id="phonenumber" class="fontsytle inputtext" maxlength="11" placeholder=<%=myResourcesBundle.getString("typephonenumber")%> style="position:absolute;top:179px;height:48px;width:202px;left:106px;padding-left:18px;"  /> 
<input type="submit" name="ok" id="commitbutton" onclick="operate();" class="button fontsytle" value='<%=myResourcesBundle.getString("confirm")%>' style="position:absolute;top:314px;left:52px;height:48px;width:275px;" />
</div>
<p><p><p><p>
</form>  
</body>
</html>