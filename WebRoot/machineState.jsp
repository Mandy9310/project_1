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
    
    <title>machineState</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="css/common.css">	
  </head>
   <style type="text/css">
  	#dataList ul{width:inherit;height:30px;line-height:30px;clear:both;}
  	#dataList li{float:none;border:0px double red;color:#666;text-align:center;} 
  </style>
  <body style="">
	 <div id="dataList" style="width:1050px;height:400px;margin:0 auto;border:0px double red;padding:0 0;">
	 	<div style="width:inherit;height:30px;float:left;margin:0 0;border:0px double red;">
			 <ul>
	 			<li>机台号--${requestScope.number_machine }</li>
	 		</ul>
		</div>
	 	<div style="width:100;height:320px;float:left;margin:0 0;border:0px double red;">
	 		<ul></ul>
	 		<ul>
	 			<li>持续时间</li>
	 			<li>（小时）</li>
	 		</ul>
	 	</div>
	 	<div style="width:900;height:320px;float:left;margin:0 0;overflow:auto;border:0px double red;">
	 		<img alt="${requestScope.number_machine }" src="${requestScope.imgurl }">
	 	</div>
		<div style="width:inherit;height:30px;float:left;margin:0 0;border:0px double red;">
			 <ul>
	 			<li>开始时间(卡编号)</li>
	 		</ul>
		</div>
	</div>
  </body>
</html>
