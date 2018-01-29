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
    
    <title>machinePlan</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link rel="stylesheet" type="text/css" href="css/common.css">	

  </head>
  <style type="text/css">
  	#list_1 ul{width:inherit;height:30px;line-height:30px;border-bottom:1px double gray;padding:5px 30px;clear:both;}
  	#list_1 li{float:left;padding:0px 20px 0px 0px;color:#666;border:0px double red;} 
  	#th input{background-color:#4991CB;color:white;font-size:16px;text-align:center;}	
  	#th li{width:150px;padding:0px 0px 0px 0px;border:0px double red;}
  </style>

  <body>
	<div id="dataList" style="width:1050px;height:600px;border:0px double transparent;margin:0 auto;">
		<div id="list_1" style="width:inherit;">
			<form action="servlet/Servlet_plan?part=6" method="post" id="form_1">
				<ul></ul>
				<ul id="ul_1">
					<li class="li_1"> 
						搜索机台：									
						<%
							String querySql = "SELECT * FROM machine";
							DB_Link dbl = new DB_Link();
							//
							dbl.connectDB();
							dbl.executeQuery(querySql);
							ResultSet rs = dbl.getRs();
						%>													
						<select class="button" name="input_1" onchange="submit('form_1')">
							<%	String number_machine = (String)request.getSession().getAttribute("number_machine_MP");	
								String name_machine = "";				
								querySql = "SELECT * FROM machine WHERE number_machine='"+number_machine+"'";
								DB_Link dbl2 = new DB_Link();
								//
								dbl2.connectDB();
								dbl2.executeQuery(querySql);
								ResultSet rs2 = dbl2.getRs();
								if(rs2.next()){
									name_machine = rs2.getString("name_machine");
								}
								dbl2.finishQuery();
								//
								dbl2.disconnectDB();
							%>
							<option selected="selected" value="<%=number_machine %>"><%=name_machine %></option>
							<%
								while(rs.next()){
									out.println("<option class='input' value='"+rs.getString("number_machine")+"'>"+rs.getString("name_machine")+"</option>");
								}
								dbl.finishQuery();
								//
								dbl.disconnectDB();
							%>
						</select>
					</li>	
					<c:choose>
						<c:when test="${sessionScope.browseWay_MP == 'stateGraph'}">
							<li>卡列表:
								<input type="radio" class="radio" name="input_2" value="unitList" onchange="submit('form_1')"/>
							</li>					
							<li>状态图:
								<input type="radio" class="radio" name="input_2" value="stateGraph" checked onchange="submit('form_1')"/>
							</li>						
						</c:when>
						<c:otherwise>
							<li>卡列表:
								<input type="radio" class="radio" name="input_2" value="unitList" checked onchange="submit('form_1')"/>
							</li>					
							<li>状态图:
								<input type="radio" class="radio" name="input_2" value="stateGraph" onchange="submit('form_1')"/>
							</li>
						</c:otherwise>
					</c:choose>																		
				</ul>
				<ul style="height:30px;border:none;padding:0 0;"></ul>
				<c:choose>
					<c:when test="${sessionScope.number_machine_MP != null && sessionScope.browseWay_MP != 'stateGraph'}">
						<ul id="th" style="background-color:#4991CB;width:inherit;height:25px;line-height:25px;border:0px double gray;">
							<li style="width:150px;"><input type="text" class="input" value="本卡编号" readonly="readonly" style="width:inherit;"></li>
							<li style="width:120px;"><input type="text" class="input" value="机台名称" readonly="readonly" style="width:inherit;"></li>
							<li style="width:150px;"><input type="text" class="input" value="计划开始于" readonly="readonly" style="width:inherit;"></li>
							<li style="width:150px;"><input type="text" class="input" value="计划结束于" readonly="readonly" style="width:inherit;"></li>
							<li style="width:150px;"><input type="text" class="input" value="实际开始于" readonly="readonly" style="width:inherit;"></li>
							<li style="width:150px;"><input type="text" class="input" value="实际结束于" readonly="readonly" style="width:inherit;"></li>
							<li style="width:80px;"><input type="text" class="input" value="状态" readonly="readonly" style="width:inherit;"></li>
						</ul>
						<ul style="height:10px;border:none;padding:0 0;"></ul>
					</c:when>
				</c:choose>	
			</form>
		</div>
		<c:choose>
			<c:when test="${sessionScope.browseWay_MP == 'stateGraph'}">
				<div id="" style="width:inherit;height:400px;">
					<iframe id="iframe" src="servlet/Servlet_plan?part=8" style="width:inherit;height:inherit;border:none;border:0px double green;"></iframe>
				</div>					
			</c:when>
			<c:otherwise>
				<div id="" style="width:inherit;height:400px;">
					<iframe id="iframe" src="servlet/Servlet_plan?part=7" style="width:inherit;height:inherit;border:none;"></iframe>
				</div>
			</c:otherwise>
		</c:choose>	
	</div>
  </body>
  <script type="text/javascript">
	function submit(ctrlId){
		document.getElementById(ctrlId).submit();
	}
  </script>
</html>
