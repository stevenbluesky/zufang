<%@page import="java.util.ResourceBundle"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" /> 
 <%
      //加载i18n资源文件，request.getLocale()获取访问用户所在的国家地区
      ResourceBundle myResourcesBundle = ResourceBundle.getBundle("messages",request.getLocale());
  %>
<title><%=myResourcesBundle.getString("messagelog")%></title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/label/<%=myResourcesBundle.getString("js.label.resourcefile")%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/layui/layui.all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/select2/js/select2.full.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/underscore-min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/underscore-setting.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/fee/roomfee/queryroombalance.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/layui/css/layui.css" media="all">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/scss/log.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/select2/css/select2.css" />  

<script id="templt" type="text/template">
<table width="80%">
	<thead>
	<tr>
		<td align="center"><%=myResourcesBundle.getString("roomno")%></td>
		<td align="center"><%=myResourcesBundle.getString("shouji")%></td>
		<td align="center" style="width:50%"><%=myResourcesBundle.getString("smstext")%></td>
		<td align="center"><%=myResourcesBundle.getString("sendingtime")%></td>
	</tr>
	</thead>
		<@ if ( object != null && object.list != null ) {@>
			<@ for ( var i = 0 ; i < object.list.length ; i ++ ){@>
				<@ var sms = object.list[i];@>
				<tbody>
				<tr>
					<td align="left">
						<@= sms.districtName@><@=sms.build@><%=myResourcesBundle.getString("view.label.building")%><@=sms.roomName @>
					</td>
					<td align="right">
						<@= sms.phonenumber @>
					</td>
					<td align="left" >
						<@= sms.message @>
					</td>
					<td align="right">
						<@ if ( sms.sendtime != null ) print(sms.sendtime); else print('<%=myResourcesBundle.getString("weifasong")%>'); @>
					</td>
				</tr>
				</tbody>
			<@} @>
		<@} @>
	</table> 
</script>

<script>

var laypage;
var laydate;
layui.use(['laypage','laydate'], function(){
	 laypage = layui.laypage;
	 laydate = layui.laydate;
});

var compiled = _.template($('#templt').html());

function load()
{
	$.ajax({
		type:"post",
		dataType: "json",
		url: BASE_PATH + '/service/sms/querysmshistory',
		data: $("#querycondition").serialize(),
		success: function(json) 
		{
			if ( json.success == 0 )
			{
				$("#div_content").html(compiled(json));
				showpage(json);
			}
			else 
				layer.alert(json.message);
		},
		failure: function(json) 
		{
			alert('<%=myResourcesBundle.getString("networkfault")%>');
		}
	});
}

function showpage(json)
{
	
	if (json.object.count > 0) {
		laypage.render({
			elem: 'page',
			count:json.object.count,
			curr: $("#pageindex").val(), 
			jump: function(e, first) { 
				if (!first) 
				{
					$("#pageindex").val(e.curr);
					load();
				}
			}
		});
		$("#page").show();
	} else {
		$("#page").hide();
	}
}

$(document).ready(function()
{
	laydate.render({
		   elem: '#startTime-search' ,type: 'datetime'
		});
	laydate.render({
		   elem: '#endTime-search' ,type: 'datetime'
		});
	load({});

});
</script>
</head>
<body style="background-color:#ffffff;margin:0px;padding:0px">
<jsp:include page="/jsp/base/menu.jsp"/>
     <div class="m-page-title">
        <span><i>&gt;</i><%=myResourcesBundle.getString("messagelog")%></span>
    </div>
    <div class="g-mnc">
        <ul class="form-box-ul">
            <li ><%=myResourcesBundle.getString("fjye")%></li>  
            <li class="z-sel"><%=myResourcesBundle.getString("messagelog")%></li> 
        </ul>  
  	
  		<form id="querycondition" method="post">
        <div class="search-item f-cb" >
          <ul class="f-cb">
              <li><i class="fa fa-map-marker fa-fw"></i><%=myResourcesBundle.getString("condition")%>：</li>
              <li>
                  <input type="hidden" class="u-select" id="district-search" name="districtId"/>
                  <input type="hidden" class="u-select" id="build-search" name="build" />
                  <input type="hidden" class="u-select" id="unit-search" name="unit" />
                  <input type="hidden" class="u-select" id="floor-search" name="floor" />
                  <input type="hidden" class="u-select" id="room-search" name="id" />
              </li>
                <ul class="f-cb f-fl" id="search-type">
                    <li><i class="fa fa-bar-chart fa-fw"></i><%=myResourcesBundle.getString("view.label.phonenumber")%>：</li>
                	<input type="text" class="u-ipt" name="phonenumber" id="phonenumber" value=""/>
                </ul>
          </ul>
      </div>
      <div class="search-item search-item-last">
        <ul class="f-cb">
            <li><i class="fa fa-map-marker fa-fw"></i><%=myResourcesBundle.getString("view.label.date")%>：</li>
            <li>
                <input type="text" class="u-ipt" id="startTime-search" name="starttime" placeholder="<%=myResourcesBundle.getString("view.label.startdate")%>" onfocus="this.blur()" />
            </li>
            <li>
                <input type="text" class="u-ipt" id="endTime-search"  name="endtime" placeholder="<%=myResourcesBundle.getString("view.label.enddate")%>" onfocus="this.blur()" />
            </li>
	          <li>
	          		<button type="button" class="u-btn gray" id="btn-search" onclick="load();"><%=myResourcesBundle.getString("view.label.search")%></button>
	          </li>
        </ul>
    </div>
    	<input type="hidden" name="page" id="pageindex" value="0">
      </form>

	<div class="m-demo" id="div_content"></div>
	<div class="page" id="page"></div>
</div>
</body>
<jsp:include page="/jsp/base/footer.jsp"/>
