<%@page import="java.util.ResourceBundle"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/font-awesome/css/font-awesome.css" />

<script >
 var BASE_PATH = '<%=request.getContextPath()%>' ;
 <%
 //加载i18n资源文件，request.getLocale()获取访问用户浏览器语言
 ResourceBundle myResourcesBundle = ResourceBundle.getBundle("messages",request.getLocale());
%>

</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/label/<%=myResourcesBundle.getString("js.label.resourcefile")%>"></script>
<div class="g-bd f-cb">
	<div class="g-sd">
	    <div class="g-sdc">
	        <div class="m-logo">
			    <a href="cell.html">
			        <img src="http://dev.isurpass.com.cn/zufang/static/img/main-logo_b4459c8.png" />
			    </a>
			</div>
			<ul>
				<li class="" id="menudistrict"><a href="<%=request.getContextPath()%>/cell.html"><i class="fa fa-building"></i><%=myResourcesBundle.getString("view.label.navcommunity")%></a><i class="caret"></i></li>
				<li class=""><a href="<%=request.getContextPath()%>/lock.html"><i class="fa fa-lock"></i><%=myResourcesBundle.getString("view.label.navdevice")%></a><i class="caret"></i></li>
				<li class=""><a href="<%=request.getContextPath()%>/wifi.html"><i class="fa fa-wifi"></i><%=myResourcesBundle.getString("view.label.navgateway")%></a><i class="caret"></i></li>
				<%
				String user = (String)request.getSession().getAttribute("user");
				if("0".equals(user.split("type")[1].substring(2,3))){%>
					<li class=""><a href="<%=request.getContextPath()%>/admin.html"><i class="fa fa-user"></i><%=myResourcesBundle.getString("secondadmin") %></a><i class="caret"></i></li>
				<%}%>
				<li class=""><a href="<%=request.getContextPath()%>/chart.html"><i class="fa fa-info-circle"></i><%=myResourcesBundle.getString("view.label.navfaultwarning")%></a><i class="caret"></i></li>
				<%
				if("0".equals(user.split("type")[1].substring(2,3))){%>
					<li class=""><a href="<%=request.getContextPath()%>/log.html"><i class="fa fa-bar-chart"></i><%=myResourcesBundle.getString("view.label.navsystemlog")%></a><i class="caret"></i></li>
					<li class=""><a href="<%=request.getContextPath()%>/electricityLog.html"><i class="fa fa-feed"></i><%=myResourcesBundle.getString("view.label.navanalysis")%></a><i class="caret"></i></li>
					<li class="z-sel" id="menuroomfee"><a href="<%=request.getContextPath()%>/roombalance.html"><i class="fa fa-rmb"></i><%=myResourcesBundle.getString("view.label.navcost")%></a><i class="caret"></i></li>
				<%}%>
				<li class=""><a href="<%=request.getContextPath()%>/user.html"><i class="fa fa-cogs"></i><%=myResourcesBundle.getString("view.label.navusercenter")%></a><i class="caret"></i></li>
			</ul>    
		</div>
	</div> 
	<div class="g-mn">
		<div class="g-hd">
			<div class="f-cb m-gray">
				<div class="f-fr f-cb">
					<div class="line f-fl"></div>
					<div class="m-logout f-fr">
						<a href="<%=request.getContextPath()%>/index.html?type=logout"><i class="fa fa-sign-out fa-fw"></i><%=myResourcesBundle.getString("view.label.logout")%></a>
					</div>
				</div>
			</div>
		</div>