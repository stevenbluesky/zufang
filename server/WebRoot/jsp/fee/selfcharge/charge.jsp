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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-confirm.js"></script>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery-confirm.css">
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery-confirm-center.css">
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/iremotewebstyle.css" charset="utf-8"/>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/jsp/fee/selfcharge/feestyle.css" charset="utf-8"/>
<script>
function onSubmit(form)
{	
	jQuery.ajax(
		{
			url:'<%=request.getContextPath()%>/service/roomfee/weixincharge',
			data:$("#roomfeecharge").serialize(),
			dataType: "json",
			success: function(data)
			{
				if ( data.resultCode == 0 )
				{
					location.href = data.mweb_url;
				}
				else 
					alert('<%=myResourcesBundle.getString("topupfailure")%>');
			},
			error: function(data)
			{
				alert('<%=myResourcesBundle.getString("topuprequestfailed")%>');
			}
			
		});
}

function askchargeresult()
{
	$.confirm({
	    title: '',
	    content: <%=myResourcesBundle.getString("confirmpayment")%>,
	    buttons: {
	    	<%=myResourcesBundle.getString("yiwancheng")%>: function () 
	    	{
	        	window.location.href = "<%=request.getContextPath()%>/service/roomfee/roomcharge?roomid=" + ${room.id } ;
	        },
	        <%=myResourcesBundle.getString("chuxianwenti")%>: function () 
	        {
	        }
	    },
	    theme: 'center'
	});
}

$(document).ready(function()
{
	<c:if test="${askchargeresult == 'true' }">
		askchargeresult();
	</c:if>
});

function avoidIMJumpKey()
{
	if(event.keyCode==13)
		return false;
}

</script>
<style>
.trstyle
{
	height:48px;
	border-bottom:1px solid #f4f4f4;
	border-top-style:none;
	border-left-style:none; 
	border-right-style:none;
	background:#ffffff;
}


</style>
</head>
<body style="background-color:#f4f4f4;margin:0px;padding:0px">
<form id="roomfeecharge" action="<%=request.getContextPath()%>/service/roomfee/weixincharge" method="POST" onkeydown="return avoidIMJumpKey();">
<input type="hidden" name="roomid" value="${room.id }">
<table style="background-color:#626262" width="100%" >
<tr >
	<td width="100%" align="center" height="48px">
		<span class="title_c"><%=myResourcesBundle.getString("autopayment")%></span>
	</td>   
</tr>
</table>     

<div style="height:48px;background:#ffffff;">
	<div style="float:left;margin-left:30px;margin-top:15px">${room.districtName }${room.build }<%=myResourcesBundle.getString("view.label.building")%>${room.roomName }</div>
</div>
<div style="height:48px;background:#ffffff;;border-top:1px solid #f4f4f4;">
	<div style="float:left;width:48%;margin-left:30px;margin-top:10px"><%=myResourcesBundle.getString("yue")%></div>
	<div style="float:right;margin-right:30px;margin-top:10px">${room.balance / 100 }<%=myResourcesBundle.getString("yuan")%></div>
</div>

<div style="height:48px;background:#ffffff;border-top:1px solid #f4f4f4;">
	<div style="float:left;width:48%;margin-left:30px;margin-top:10px"><%=myResourcesBundle.getString("qianfei")%></div>
	<div style="float:right;margin-right:30px;margin-top:10px">${room.arrearage / 100 }<%=myResourcesBundle.getString("yuan")%></div>
</div>

<div style="height:10px"></div>
<div style="background:#ffffff;height:54px"></div>
<div style="background:#ffffff;" class="fontsytle">
	<span style="margin-left:30px"><%=myResourcesBundle.getString("jiaofei")%>&nbsp;&nbsp;&nbsp;</span><input type="text" name="amount" id="amount" maxLength="16" class="inputtext" placeholder=<%=myResourcesBundle.getString("rechargeamount")%> style="border:0px;"  /><br/>
</div> 

<div style="height:54px"></div>
<center>
<input type="button" name="ok" id="commitbutton" class="button" onclick="onSubmit(this.form)"; value='<%=myResourcesBundle.getString("topup")%>' />
 </center>
</form>  
</body>
</html>