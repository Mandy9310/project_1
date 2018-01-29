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
    
    <title>splitUnit</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="css/common.css">
	<link rel="stylesheet" href="css/wenk.css">
	<script src="js/"></script>
  </head>
  
  <style type="text/css">
  	#list_1 ul{width:inherit;height:30px;line-height:30px;clear:both;padding:5px 30px;border-bottom:1px double gray;}
  	#list_1 li{width:150px;float:left;color:#666;text-align:center;border:0px double red;}
  	.input{text-align:center;width:120px;}
  	#th input{background-color:#4991CB;color:white;font-size:16px;}
  	#newUnitMsg li{width:200px;}
  
  </style>
  
  <body style="">
	<div id="dataList" style="width:1050px;height:600px;border:0px double red;margin:0 auto;">
		<div id="list_1" style="width:inherit;border:0px double red;">
			<form action="servlet/Servlet_unit?part=4" method="post" id="form_1">
				<ul></ul>
				<ul style="padding:5px 0px 5px 45px;">							
					<li style="width:300px;"> 
						卡编号（拆卡）:								
						<input type="text" class="input" name="number_unit" value="${sessionScope.number_unit_SU }"style="width:150px;">
					</li>						
 					<li style="width:60px;">
 						<input class="button" type="submit" value="查询"> 						
					</li> 
				</ul>							
			</form>
			<c:set value="${requestScope.message}" var="message"></c:set> 
			<c:choose>
				<c:when test="${message != null}">
					<form action="servlet/Servlet_unit?part=5" method="post" id="form_2">
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
							<li style=""><input type="text" class="input" value="生产单号" readonly="readonly" style="width:inherit;"></li>
							<li style="width:100px;"><input type="text" class="input" value="执行操作" readonly="readonly" style="width:inherit;"></li>
						</ul>							    		
				    	<ul id="ul_2" style="">
					    	<li style=""><input type="text" class="input" name="number_unit" value="${message.getNumber_unit()}" readonly="readonly" style=""></li>
							<li style="width:80px;"><input type="text" class="input" name="length_unit" value="${message.getLength_unit()}" readonly="readonly" style="width:inherit;"></li>
							<li style="width:400px;">
								<input type="text" class="input" name="name_craft" value="${message.getName_craft()}" readonly="readonly" style="width:350px">
								<input type="hidden" name="number_craft" value="${message.getNumber_craft()}">
							</li>							
							<li style="width:80px;"><input type="text" class="input" name="process_crt" value="${message.getName_process_crt()}" readonly="readonly" style="width:inherit"></li>
							<li style=""><input type="text" class="input" name="number_order_task" value="${message.getNumber_order_task()}" style=""></li>				    			  	
		    				<li style="width:100px;">
								<input type="submit" class="button" id="" value="拆卡">
				    		</li>	
				    	</ul>
				    </form>
				    <div id="newUnitMsg">
				    <c:choose>
						<c:when test="${requestScope.number_craft != null}">
							<form action="servlet/Servlet_unit?part=6" method="post" id="form_3">
								<input type="hidden" name="number_unit_old" value="${message.getNumber_unit()}" style="">	
								<ul style="background-color:white;border:none;"></ul>					
								<ul id="th" style="background-color:#4991CB;width:inherit;height:25px;line-height:25px;border:0px double gray;">	
									<li style=""><input type="text" class="input" value="序	号" readonly="readonly" style=";"></li>								
									<li style=""><input type="text" class="input" value="新卡编号" readonly="readonly" style=";"></li>
									<li style=""><input type="text" class="input" value="划分布长" readonly="readonly" style=""></li>
									<li style=""><input type="text" class="input" value="起始工序" readonly="readonly" style=";"></li>									
								</ul>							    		
						    	<ul id="" style="">
						    		<li>(1)<input type="hidden" class="" name="unit_new_check_1" value="checked" checked></li>
							    	<li style="">
							    		<input type="text" class="input" name="number_unit_new_1" value="${requestScope.number_unit_new_1}" readonly="readonly" style="">
							    	</li>
							    	<li>
										<span data-wenk='0~${message.getLength_unit()}' data-wenk-pos="right">
											<input class="input" type="number" min="0" max="${message.getLength_unit()}" name="length_unit_new_1" value="0" />
										</span>
									</li>
							    	<li style="">
							    		<%	
							    			String sql = "SELECT * FROM relation_craft_process a LEFT JOIN proces b ON a.number_process=b.number_process WHERE location>="+request.getAttribute("location")+" AND number_craft='"+request.getAttribute("number_craft")+"' ORDER BY location";
							    			DB_Link dbl = new DB_Link();
							    			//
							    			dbl.connectDB();
							    			dbl.executeQuery(sql);
							    			ResultSet rs = dbl.getRs();	
							    							    			
							    		 %>
							    		<select class="button" name="location_start_1">
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
						    	</ul>
								<ul id="" style="">
						    		<li>(2)<input type="hidden" class="" name="unit_new_check_2" value="checked" checked></li>
							    	<li style="">
							    		<input type="text" class="input" name="number_unit_new_2" value="${requestScope.number_unit_new_2}" readonly="readonly" style="">							    		
							    	</li>
							    	<li style="">
										<span data-wenk='0~${message.getLength_unit()}' data-wenk-pos="right">
											<input class="input" type="number" min="0" max="${message.getLength_unit()}" name="length_unit_new_2" value="0" />
										</span>
							    	<li style="">
							    		<%								    			
							    			//
							    			dbl.connectDB();
							    			dbl.executeQuery(sql);
							    			rs = dbl.getRs();					    			
							    		 %>
							    		<select class="button" name="location_start_2">
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
						    	</ul>
				    			<ul>
				    				<li style="width:auto;float:right;clear:both;border:0px double red;padding:0 40px;">
				    					<input type="submit" class="button" value="确认拆卡">
				    				</li>
				    			</ul>				    	
						    </form>
						</c:when>
					</c:choose>	
					</div>					
				</c:when>
			</c:choose>							
		</div>								   
	</div>	
  </body>

</html>
