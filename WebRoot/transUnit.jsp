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
    
    <title>transUnit</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="css/common.css">

	<script type="text/javascript" src="js/"></script>	

  </head>
  
  <style type="text/css">
  	#list_1 ul{width:inherit;height:30px;line-height:30px;clear:both;padding:5px 30px;border-bottom:1px double gray;}
  	#list_1 li{width:150px;float:left;color:#666;text-align:center;border:0px double red;}
  	.input{text-align:center;width:120px;}
  	#th input{background-color:#4991CB;color:white;font-size:16px;}
  </style>
  
  <body style="">
	<div id="dataList" style="width:1050px;height:600px;border:0px double red;margin:0 auto;">
		<div id="list_1" style="width:inherit;border:0px double red;">
			<form action="servlet/Servlet_unit?part=1" method="post" id="form_1">
				<ul></ul>
				<ul style="padding:5px 0px 5px 45px;">							
					<li style="width:300px;"> 
						卡编号（转卡）:								
						<input type="text" class="input" name="number_unit" value="${sessionScope.number_unit_TU }"style="width:150px;">
					</li>						
 					<li style="width:60px;">
 						<input class="button" type="submit" value="查询"> 						
					</li> 
				</ul>							
			</form>
			<c:set value="${requestScope.message}" var="message"></c:set> 
			<c:choose>
				<c:when test="${message != null}">
					<form action="servlet/Servlet_unit?part=2" method="post" id="form_2">
						<ul style="background-color:white;height:15px;border:none;padding:0 0;"></ul>	
						<ul style="background-color:#4991CB;width:inherit;height:25px;line-height:25px;border:0px double red;padding:5px 0px 5px 45px;">
							<li style="width:auto;color:white;padding:0 15px;">卡（${message.getNumber_unit()}）</li>
						</ul>
						<ul style="background-color:white;height:15px;border:none;padding:0 0;"></ul>						
						<ul id="th" style="background-color:#4991CB;width:inherit;height:25px;line-height:25px;border:0px double gray;">
							<li style=""><input type="text" class="input" value="本卡编号" readonly="readonly" style="width:inherit"></li>
							<li style="width:80px;"><input type="text" class="input" value="本卡布长" readonly="readonly" style="width:inherit;"></li>
							<li style="width:400px;"><input type="text" class="input" value="处理工艺" readonly="readonly" style="width:inherit;"></li>
							<li style="width:80px;"><input type="text" class="input" value="当前工序" readonly="readonly" style="width:inherit;"></li>
							<li style=""><input type="text" class="input" value="转向生产单" readonly="readonly" style="width:inherit;"></li>
							<li style="width:100px;"><input type="text" class="input" value="执行操作" readonly="readonly" style="width:inherit;"></li>
						</ul>							    		
				    	<ul id="ul_2" style="">
					    	<li style=""><input type="text" class="input" name="number_unit" value="${message.getNumber_unit()}" readonly="readonly" style=""></li>
							<li style="width:80px;"><input type="text" class="input" name="length_unit" value="${message.getLength_unit()}" readonly="readonly" style="width:inherit;"></li>
							<li style="width:400px;"><input type="text" class="input" name="name_craft" value="${message.getName_craft()}" readonly="readonly" style="width:350px"></li>
							<li style="width:80px;"><input type="text" class="input" name="process_crt" value="${message.getName_process_crt()}" readonly="readonly" style="width:inherit"></li>
							<li style=""><input type="text" class="input" name="number_order_task" style=""></li>				    			  	
		    				<li style="width:100px;">
								<input type="submit" class="button" id="" value="转卡">
				    		</li>	
				    	</ul>
				    </form>
				    <c:choose>
						<c:when test="${requestScope.number_unit != null}">
							<form action="servlet/Servlet_unit?part=3" method="post" id="form_3">	
								<ul style="background-color:white;border:none;"></ul>					
								<ul id="th" style="background-color:#4991CB;width:inherit;height:25px;line-height:25px;border:0px double gray;">
									
									<li style="width:200px;"><input type="text" class="input" value="新卡编号" readonly="readonly" style=";"></li>
									<li style="width:200px;"><input type="text" class="input" value="起始工序" readonly="readonly" style=";"></li>
									<li style="width:200px;"><input type="text" class="input" value="生产单号" readonly="readonly" style=";"></li>
									<li style="width:200px;"><input type="text" class="input" value="执行转卡" readonly="readonly" style=""></li>
								</ul>							    		
						    	<ul id="" style="">
							    	<li style="width:200px;">
							    		<input type="text" class="input" name="number_unit_new" value="${requestScope.number_unit}" readonly="readonly" style="">
							    		<input type="hidden" name="number_unit_old" value="${message.getNumber_unit()}" readonly="readonly" style="">
							    	</li>
							    	<li style="width:200px;">
							    		<%	
							    			String sql = "SELECT * FROM relation_craft_process a LEFT JOIN proces b ON a.number_process=b.number_process WHERE location>="+request.getAttribute("location")+" AND number_craft='"+request.getAttribute("number_craft")+"' ORDER BY location";
							    			DB_Link dbl = new DB_Link();
							    			//
							    			dbl.connectDB();
							    			dbl.executeQuery(sql);
							    			ResultSet rs = dbl.getRs();					    			
							    		 %>
							    		<select class="button" name="location_start">
							    			<%
												while(rs.next()){
													out.println("<option value="+rs.getInt("location")+">"+rs.getString("name_process")+"</option>");
												}
												dbl.finishQuery();
												//
												dbl.disconnectDB();
							    			 %>
							    		</select>
							    	</li>
									<li style="width:200px;"><input type="text" class="input" name="number_order_task" value="${requestScope.number_order_task}" readonly="readonly" style=""></li>
									<li style="width:200px;">
						    			<input type="submit" class="button" id="" value="确认">
						    		</li>							
						    	</ul>
						    </form>
						</c:when>
					</c:choose>						
				</c:when>
			</c:choose>							
		</div>
	</div>	
  </body>
</html>
