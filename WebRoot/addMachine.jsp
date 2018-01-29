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
    
    <title>b_addMachine</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link rel="stylesheet" type="text/css" href="css/common.css">
	<link rel="stylesheet" type="text/css" href="css/addMachine.css">

	<script type="text/javascript" src="js/"></script>	

  </head>
  <style type="text/css">
  	#list_1 ul{width:inherit;height:30px;line-height:30px;border-bottom:1px double gray;padding:10px 40px;clear:both;}
  	#list_1 li{float:left;padding:0px 20px 0px 0px;color:#666;}
  </style>
  <body>
	<div id="dataList"  style="width:1050px;height:140px;border:1px double transparent;margin:0 auto;">
		<div id="list_1" style="width:inherit;height:140px;">
			<form action="servlet/Servlet_machine?part=1" method="post">
				<ul></ul>
				<ul id="ul_1">
					<%
						String querySql = "SELECT * FROM type_machine";
							DB_Link dbl = new DB_Link();
							//
							dbl.connectDB();
							dbl.executeQuery(querySql);
							ResultSet rs = dbl.getRs();
					%>		
					<li>
						机器类型:
						<SELECT class="button" id="input_1" name="input_1">
							<option selected="selected" value=""></option>				
							<%
								while(rs.next()){
									out.println("<option class='input' value='"+rs.getString("number_type_machine")+"'>"+rs.getString("name_type_machine")+"</option>");
								}
								dbl.finishQuery();
								//
								dbl.disconnectDB();
							%>
						</SELECT>
					</li>
					<li>
						机台幅数:
						<SELECT class="button" id="input_2" name="input_2">
							<option value="1" selected="selected">1</option>
							<option value="2">2</option>
							<option value="4">4</option>
						</SELECT>
					</li>
					<li>
						处理上界:
						<SELECT class="button" id="input_3" name="input_3">
							<option value="4" selected="selected">4卡</option>
							<option value="5">5卡</option>
							<option value="6">6卡</option>
						</SELECT>
					</li>
					<li>
						处理下界:
						<SELECT class="button" id="input_4" name="input_4">
							<option value="1" selected="selected">1卡</option>
							<option value="2">2卡</option>
							<option value="3">3卡</option>
						</SELECT>
					</li>
					<%
						String querySql2 = "SELECT * FROM department";
						DB_Link dbl2 = new DB_Link();
						//
						dbl2.connectDB();
						dbl2.executeQuery(querySql2);
						ResultSet rs2 = dbl2.getRs();
					%>		
					<li>
						所属部门:
						<SELECT class="button" id="input_5" name="input_5">
							<option selected="selected" value=""></option>							
							<%
								while(rs2.next()){
									out.println("<option class='input' value='"+rs2.getString("name_department")+"'>"+rs2.getString("name_department")+"</option>");
								}
								dbl2.finishQuery();
								//
								dbl2.disconnectDB();
							%>
						</SELECT>
					</li>										
					<li>
						<input type="submit" class="button" value="确认添加">
					</li>
				</ul>
				<c:choose>
					<c:when test="${ sessionScope.b_number_machine_1 == ''}">		
					</c:when>
					<c:otherwise>
						<ul id="ul_2">
							<li>
								新添机台信息
							</li>
							<li>
								机台号:
								<input type="text" class="input" id="input_6" name="input_6" value="${sessionScope.b_number_machine_1 }" readonly="readonly">
							</li>
							<li>
								机台名:
								<input type="text" class="input" id="input_7" name="input_7" value="${sessionScope.b_name_machine_1 }" readonly="readonly">
							</li>
						</ul>
					</c:otherwise>
				</c:choose>		
			</form>
		</div>
	</div>
  </body>
</html>
