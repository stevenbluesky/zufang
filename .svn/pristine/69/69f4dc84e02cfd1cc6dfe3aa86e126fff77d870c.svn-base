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
 	String weiluru=myResourcesBundle.getString("weiluru");
 	String yiluru=myResourcesBundle.getString("yiluru");
  %>
<title><%=myResourcesBundle.getString("communitypaymentsettings")%></title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/label/<%=myResourcesBundle.getString("js.label.resourcefile")%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/layui/layui.js"></script>
<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/static/pkg/select2/select2.min.js"></script> --%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/select2/js/select2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/underscore-min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/underscore-setting.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/static/scss/user.css" media="all">

<script>
var layer ;
layui.use('layer', function(){
	  layer = layui.layer;
	}); 
var cellId = ${district.id};

function save()
{
	$.ajax({
		type:"post",
		dataType: "json",
		url: BASE_PATH + '/service/district/updatedistrictpaymentsetting',
		data: $("#frmdistrictpaymentsetting").serialize(),
		success: function(json) 
		{
			layer.alert(json.message);
		},
		failure: function(json) 
		{
			layer.alert('<%=myResourcesBundle.getString("networkfault")%>');
		}
	});
}

$(document).ready(function()
{
	$('#authuserselect').val(${dps.autofee});
	
	$(document).on('click', '.form-box-ul li', function(event) {
		event.preventDefault();
		var index = $(this).index();
		switch (index) {
			case 0:
				location.href = BASE_PATH + '/room.html?id=' + cellId;
				break;
			case 1:
				location.href = BASE_PATH + '/manager.html?id=' + cellId;
				break;
			case 2:
				location.href = BASE_PATH + '/service/district/paymentsetting?districtid=' + cellId;
				break;
			case 3:
				location.href = BASE_PATH + '/pwdgenerationrule.html?id=' + cellId;
				break;	
		}
	});
	
	$("#menuroomfee").removeClass("z-sel");
	$("#menudistrict").addClass("z-sel");
});

</script>
</head>
<body style="background-color:#ffffff;margin:0px;padding:0px">
<!-- dps.wexinpayappid==null?'<%=myResourcesBundle.getString("weiluru")%>':'<%=myResourcesBundle.getString("yiluru")%>'} ; -->
<jsp:include page="/jsp/base/menu.jsp"/>
     <div class="m-page-title">
     
        <span><i>&gt;</i><a href="<%=request.getContextPath()%>/cell.html" id="cellName">${district.districtName}<%=myResourcesBundle.getString("view.label.community")%></a><i>&gt;</i><%=myResourcesBundle.getString("paymentsettings")%></span>
    </div>
    <div class="g-mnc">
        <ul class="form-box-ul">
        	<li ><%=myResourcesBundle.getString("roomlist")%></li> 
        	<li ><%=myResourcesBundle.getString("adminlist")%></li> 
            <li class="z-sel"><%=myResourcesBundle.getString("paymentsettings")%></li>  
            <li ><%=myResourcesBundle.getString("passwordgenerationrule")%></li>
        </ul>
  	
  	<form id="frmdistrictpaymentsetting" method="post">
  		<input type="hidden" name="districtid" value="${district.id}"/>
  		<div class="head-pic-outer">
		    <div class="m-head-pic-box">
		        <div class="head-pic-outline">
		            <div class="head-pic">
		            	<c:if test="${district.districtImg=='no-cell.jpg'}">
		               	 	<img src="<%=request.getContextPath()%>/static/img/no-cell.jpg"  width="140" height="140" id="coverImgUrl">
		               	</c:if>
		            	<c:if test="${district.districtImg!='no-cell.jpg'}">
		               	 	<img src="${district.districtImg}"  width="140" height="140" id="coverImgUrl">
		               	</c:if>
		            </div>
		        </div>
		    </div>
		</div>
        <div class="m-form" >
        	<div class="formitm">
        		<label class="lab"><%=myResourcesBundle.getString("autopay")%>:</label>
        		<div class="ipt">
                   	<select id="authuserselect" name="autofee" style="width:80px">
                   		<option value="0" ><%=myResourcesBundle.getString("no")%></option>
                   		<option value="1"><%=myResourcesBundle.getString("yes")%></option>
                   	 </select>
        		</div>
        	</div>
         	<div class="formitm">
        		<label class="lab"><%=myResourcesBundle.getString("appname")%>:</label>
        		<div class="ipt">
                   	<input type="text" class="u-ipt" name="wexinpayappname" value="${dps.wexinpayappname}" autocomplete="off" >
        		</div>
        	</div>
        	<div class="formitm">
        		<label class="lab"><%=myResourcesBundle.getString("accountid")%>:</label>
        		<div class="ipt">
        			<c:if test="${dps.wexinpayappid==null}">
        					<input type="text" class="u-ipt" name="wexinpayappid" value="" autocomplete="off" placeholder="<%=weiluru%>" >
        			</c:if>
                   <c:if test="${dps.wexinpayappid!=null}">
        					<input type="text" class="u-ipt" name="wexinpayappid" value="" autocomplete="off" placeholder="<%=yiluru%>" >
        			</c:if>
        		</div>
        	</div>
        	<div class="formitm">
        		<label class="lab"><%=myResourcesBundle.getString("shanghuhao")%></label>
        		<div class="ipt">
        			<c:if test="${dps.wexinpaymchid==null}">
        					<input type="text" class="u-ipt" name="wexinpaymchid" value="" autocomplete="off" placeholder="<%=weiluru%>" >
        			</c:if>
                   <c:if test="${dps.wexinpaymchid!=null}">
        					<input type="text" class="u-ipt" name="wexinpaymchid" value="" autocomplete="off" placeholder="<%=yiluru%>" >
        			</c:if>
        		</div>
        	</div>
        	<div class="formitm">
        		<label class="lab"><%=myResourcesBundle.getString("apikey")%>:</label>
        		<div class="ipt">
        			<c:if test="${dps.wexinpayapikey==null}">
        					<input type="text" class="u-ipt" name="wexinpayapikey" value="" autocomplete="off" placeholder="<%=weiluru%>" >
        			</c:if>
                   <c:if test="${dps.wexinpayapikey!=null}">
        					<input type="text" class="u-ipt" name="wexinpayapikey" value="" autocomplete="off" placeholder="<%=yiluru%>" >
        			</c:if>
        		</div>
        	</div>
			<div class="formitm formitm-1">
	            <div class="ipt">
	                <a class="u-btn gray" id="btn-save" onclick="save()" style="width:100px;margin-left:55px"><%=myResourcesBundle.getString("save")%></a>
	            </div>
	        </div>
      </div>
      </form>

</div>
</body>
<jsp:include page="/jsp/base/footer.jsp"/>
