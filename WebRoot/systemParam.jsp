<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="lk.ysu.workingschedule.db.*" %>
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
    
    <title>systemParam</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link rel="stylesheet" type="text/css" href="css/common.css">	

  </head>
  <style type="text/css">
  	#list_1 ul{width:inherit;height:30px;line-height:30px;border-bottom:1px double gray;padding:10px 40px;clear:both;}
  	#list_1 li{float:left;padding:0px 20px 0px 0px;color:#666;}
  	input.input{width:100px;}
  </style>

  <body>
	<div id="dataList"  style="width:1050px;height:140px;border:1px double transparent;margin:0 auto;">
		<div id="list_1" style="width:inherit;height:140px;">
			<form action="servlet/Servlet_system?part=1" method="post">
				<ul></ul>
				<ul id="ul_1">
					<%
						String querySql = "SELECT * FROM param_system";
						String length_default = "";
						String deviation_allowed = "";		
						DB_Link dbl = new DB_Link();
						//
						dbl.connectDB();
						dbl.executeQuery(querySql);
						ResultSet rs = dbl.getRs();				
						if(rs.next()){
							length_default = rs.getString("length_default");
							deviation_allowed = rs.getString("deviation_allowed");						
						}
						dbl.finishQuery();
						//
						dbl.disconnectDB();
					%>	
					<li>卡默认布长:
						<input type="text" class="input" name="length_default" id="input_1" value="<%=length_default %>" onblur="rangeLengthDefault()">
					</li>					
					<li>允许误差值:
						<input type="text" class="input" name="deviation_allowed" id="input_2" value="<%=deviation_allowed %>">
					</li>														
					<li>
						<input type="submit" class="button" value="初始化" onmouseover="check()">
					</li>
				</ul>	
			</form>
		</div>
	</div>
  </body>
</html>
