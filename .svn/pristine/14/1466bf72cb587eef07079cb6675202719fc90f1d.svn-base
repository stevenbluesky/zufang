<%@page import="java.util.ResourceBundle"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" /> 
 <%
      //加载i18n资源文件，request.getLocale()获取访问用户所在的国家地区
      ResourceBundle myResourcesBundle = ResourceBundle.getBundle("messages",request.getLocale());
  %>
<title><%=myResourcesBundle.getString("fjye")%></title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/label/<%=myResourcesBundle.getString("js.label.resourcefile")%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/layui/layui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/select2/js/select2.full.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/underscore-min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/underscore-setting.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/fee/roomfee/queryroombalance.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/scss/log.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/select2/css/select2.css" />  

<script id="templt" type="text/template">
<table width="80%">
	<thead>
	<tr>
		<td align="center"><%=myResourcesBundle.getString("roomno")%></td>
		<td align="center"><%=myResourcesBundle.getString("balance")%></td>
		<td align="center"><%=myResourcesBundle.getString("owecost")%></td>
		<td align="center"><%=myResourcesBundle.getString("heji")%></td>
	</tr>
	</thead>
		<@ for ( var i = 0 ; i < rooms.length ; i ++ ){@>
			<@ var room = rooms[i];@>
			<tbody>
			<tr>
				<td align="left">
					<@= room.districtName@><@=room.build@>栋<@=room.roomName @>
				</td>
				<td align="right">
					<@= room.balance / 100 @>
				</td>
				<td align="right">
					<@= room.arrearage / 100 @>
				</td>
				<td align="right">
					<@=( room.balance - room.arrearage) / 100 @>
				</td>
			</tr>
			</tbody>
		<@} @>
	<tr>
		<td align="left"><%=myResourcesBundle.getString("total")%></td>
		<td align="right"><@= totalbalance / 100  @></td>
		<td align="right"><@= totalarrearage / 100 @> </td>
		<td align="right"><@=(totalbalance - totalarrearage) / 100 @> </td>
	</tr>
	</table> 
</script>

<script>
var layer ;
layui.use('layer', function(){
	  layer = layui.layer;
	}); 
var compiled = _.template($('#templt').html());

function load()
{
	$.ajax({
		type:"post",
		dataType: "json",
		url: BASE_PATH + '/service/roomfee/queryroombalance',
		data: $("#querycondition").serialize(),
		success: function(json) 
		{
			if ( json.success == 0 )
				$("#div_content").html(compiled(json));
			else 
				layer.alert(json.message);
		},
		failure: function(json) 
		{
			alert('<%=myResourcesBundle.getString("networkfault")%>');
		}
	});
}

function charge()
{
	layer.confirm('<%=myResourcesBundle.getString("nowdeduce")%>', {} , function(index) {
		$.ajax({
			type:"post",
			dataType: "json",
			url: BASE_PATH + '/service/roomfee/chargeallroom',
			success: function(json) 
			{
				if ( json.success == 0 )
				{
					layer.closeAll();
					load();
				}
				else 
					layer.alert(json.message);
			},
			failure: function(json) 
			{
				alert('<%=myResourcesBundle.getString("networkfault")%>');
			}
		});
	});
}

$(document).ready(function()
{
	load({});
});
</script>
</head>

<body style="background-color:#ffffff;margin:0px;padding:0px">
<jsp:include page="/jsp/base/menu.jsp"/>
     <div class="m-page-title">
        <span><i>&gt;</i><%=myResourcesBundle.getString("fjye")%></span>
    </div>
    <div class="g-mnc">
        <ul class="form-box-ul">
            <li class="z-sel"><%=myResourcesBundle.getString("fjye")%></li>  
            <li ><%=myResourcesBundle.getString("messagelog")%></li> 
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
                	<input type="hidden" name="arrearageonly" id="arrearageonly" value=""/>
                    <li><i class="fa fa-bar-chart fa-fw"></i><%=myResourcesBundle.getString("oweornot")%>：</li>
                    <li class="item z-sel" data-id="0"><a><%=myResourcesBundle.getString("quanbu")%></a></li>
                    <li class="item" data-id="1"><a><%=myResourcesBundle.getString("view.label.owe")%></a></li>
                </ul>
              <li>
              		<button type="button" class="u-btn gray" id="btn-search" onclick="load();"><%=myResourcesBundle.getString("view.label.search")%></button>
              </li>
              <li>
              		<button type="button" class="u-btn gray" id="btn-search" onclick="charge();"><%=myResourcesBundle.getString("costdeducted")%></button>
              </li>
          </ul>
      </div>
      </form>

	<div class="m-demo" id="div_content">
		
	</div>
</div>
</body>
<jsp:include page="/jsp/base/footer.jsp"/>
