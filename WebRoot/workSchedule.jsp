<%@ page language="java" pageEncoding="UTF-8"%>
<!-- 使用jstl核心标签需要添加核心标签库，<taglib>标签 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>workSchedule</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link rel="stylesheet" type="text/css" href="css/common.css">	

  </head>
  <style type="text/css">
  	#list_1 ul{width:inherit;height:30px;line-height:30px;border-bottom:1px double gray;padding:5px 30px;clear:both;}
  	#list_1 li{float:left;color:#666;text-align:center;}
  	input.input{width:100px;}
  </style>

  <body>
	<div id="dataList"  style="width:1050px;height:140px;margin:0 auto;">
		<div id="list_1" style="width:inherit;height:140px;">
			<form>
				<ul></ul>
				<ul>
	 				<li style="float:left;width:80px;">	
	 					执行排产  				
	 				</li>														
	 				<li style="float:left;width:80px;">	
						<a href="servlet/Servlet_plan?part=1">							
							<input type="button" class="button" value="点击开始">
						</a>				
	 				</li>
				</ul>
			</form>	
		</div>
	</div>
  </body>
</html>
