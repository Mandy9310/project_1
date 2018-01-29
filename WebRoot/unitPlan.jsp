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
    
    <title>unitPlan</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="css/common.css">

	<script type="text/javascript" src="js/"></script>	

  </head>
  <style type="text/css">
  	#list_1 ul{width:inherit;height:30px;line-height:30px;padding:5px 30px;clear:both;border-bottom:1px double gray;}
  	#list_1 li{width:150px;float:left;color:#666;text-align:center;border:0px double red;}
  	#mainOrderList li{width:150px;text-align:center;}
  	.input{text-align:center;width:100px;}
  	#th input{background-color:#4991CB;color:white;font-size:16px;}
  </style>
  <body style="">

	<div id="dataList" style="width:1050px;height:600px;border:0px double transparent;margin:0 auto;">
		<div id="list_1" style="width:inherit;border:0px double red;">
			<form action="servlet/Servlet_plan?part=4" method="post" id="form_1">
				<ul></ul>
				<ul style="">							
					<li style="width:250px;"> 
					搜索卡:								
						<input type="text" class="input" name="number_unit" style="width:150px;">
					</li>						
 					<li>
 						<input class="button" type="submit" value="查询"> 						
					</li> 
				</ul>							
	
				<ul style="background-color:white;height:1px;border:none;"></ul>
				<c:choose>
					<c:when test="${sessionScope.number_unit_UP != null}">
						<ul style="background-color:#4991CB;width:inherit;height:30px;line-height:30px;border:0px double red;padding:5px 60px;">
							<li style="width:auto;color:white;">生产单（${sessionScope.number_unit_UP }）--卡</li>
						</ul>
						<ul style="background-color:white;height:1px;border:none;"></ul>						
						<ul id="th" style="background-color:#4991CB;width:inherit;height:20px;line-height:20px;border:0px double gray;">
							<li style="width:150px;"><input type="text" class="input" value="本卡编号" readonly="readonly" style="width:inherit;"></li>
							<li style="width:120px;"><input type="text" class="input" value="机台名称" readonly="readonly" style="width:inherit;"></li>
							<li style="width:150px;"><input type="text" class="input" value="计划开始于" readonly="readonly" style="width:inherit;"></li>
							<li style="width:150px;"><input type="text" class="input" value="计划结束于" readonly="readonly" style="width:inherit;"></li>
							<li style="width:150px;"><input type="text" class="input" value="实际开始于" readonly="readonly" style="width:inherit;"></li>
							<li style="width:150px;"><input type="text" class="input" value="实际结束于" readonly="readonly" style="width:inherit;"></li>
							<li style="width:80px;"><input type="text" class="input" value="状态" readonly="readonly" style="width:inherit;"></li>
						</ul>
						<ul style="background-color:white;height:1px;border:none;"></ul>
					</c:when>
				</c:choose>		
			</form>
		</div>
		<div id="" style="width:inherit;height:400px;">
			<iframe id="iframe" src="servlet/Servlet_plan?part=5" style="width:inherit;height:inherit;border:none;"></iframe>
		</div>
	</div>	
  </body>
</html>
