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
    
    <title>machineMsg</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link rel="stylesheet" type="text/css" href="css/common.css">

	<script type="text/javascript" src="js/"></script>	

  </head>
  <style type="text/css">
  	#list_1_1 ul, #list_2_1 ul{width:inherit;height:25px;line-height:25px;border-bottom:1px double gray;padding:10px 40px;clear:both;}
  	#list_1_1 li, #list_2_1 li{float:left;padding:0px 20px 0px 0px;color:#666;}
  	input.input{width:100px;text-align:center;}
  	#th input{background-color:#4991CB;color:white;font-size:16px;border:none;}
  	select.button{width:100px;}
  </style>
  <body>
	<div id="dataList_1" style="width:1050px;height:140px;border:1px double transparent;">
		<div id="list_1_1">
			<form action="servlet/Servlet_machine?part=2" method="post">
				<ul style="width:inherit;height:20px;padding:0 0;"></ul>
				<ul>								
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
					<select class="button" name="number_machine" id="input_1" onchange="select_1('input_1')">
						<option selected="selected"></option>
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
 					<li class="li_1">
 						<input class="button" type="submit" value="查询">
					</li> 
				</ul>
			</form>
			<form action="servlet/Servlet_machine?part=3" method="post">
				<c:set value="${requestScope.message}" var="message"></c:set> 
				<c:choose>
					<c:when test="${message != null }">			
						<ul id="th" style="background-color:#4991CB;">
							<li><input type="text" class="input" value="机台号" readonly="readonly"></li>
							<li><input type="text" class="input" value="机台状态" readonly="readonly"></li>
							<li><input type="text" class="input" value="机台幅数" readonly="readonly"></li>
							<li><input type="text" class="input" value="处理上界" readonly="readonly"></li>
							<li><input type="text" class="input" value="处理下界" readonly="readonly"></li>
							<li><input type="text" class="input" value="所属部门" readonly="readonly"></li>
							<li><input type="text" class="input" value="操作" readonly="readonly"></li>
						</ul>
						<ul id="ul_3">					
							<li> 				
			    				<input type="text" class="input" name="number_machine" value="${message.getNumber_machine()}" readonly="readonly">
			    			</li>	
				    		<li>		    			
				    			<select class="button" name="input_1_${message.getNumber_machine()}" id="select_1_${message.getNumber_machine()}">
				    				<option value="${message.getState_machine() }" selected="selected">${message.getState_machine() }</option>
				    				<option value="空闲">空闲</option>
				    				<option value="下界报警">下界报警</option>
				    				<option value="正常">正常</option>
				    				<option value="上界报警">上界报警</option>
				    				<option value="停机维修">停机维修</option>
				    			</select>
				    		</li>    		
							<li>						
				    			<select class="button" name="input_2_${message.getNumber_machine()}" id="select_2_${message.getNumber_machine()}">
				    				<option value="${message.getCount_inlet() }" selected="selected">${message.getCount_inlet() }幅</option>
				    				<option value="1">1幅</option>
				    				<option value="2">2幅</option>
				    				<option value="4">4幅</option>
				    			</select>
				    		</li>   
				    		<li>		    			
				    			<select class="button" name="input_3_${message.getNumber_machine()}" id="select_3_${message.getNumber_machine()}">
				    				<option value="${message.getBound_upper() }" selected="selected">${message.getBound_upper() }卡</option>
				    				<option value="4">4卡</option>
				    				<option value="5">5卡</option>
				    				<option value="6">6卡</option>
				    			</select>
				    		</li>  
				    		<li>		    			
				    			<select class="button" name="input_4_${message.getNumber_machine()}" id="select_4_${message.getNumber_machine()}">
				    				<option value="${message.getBound_lower() }" selected="selected">${message.getBound_lower() }卡</option>
				    				<option value="1">1卡</option>
				    				<option value="2">2卡</option>
				    				<option value="3">3卡</option>
				    			</select>
				    		</li>  
				    		<li>			    										
								<%
									querySql = "SELECT * FROM department";
									DB_Link dbl2 = new DB_Link();
									//
									dbl2.connectDB();
									dbl2.executeQuery(querySql);
									rs = dbl2.getRs();
								%>													
								<select class="button" name="input_5_${message.getNumber_machine()}" id="select_5_${message.getNumber_machine()}">
									<option selected="selected" value="${message.getName_department() }">${message.getName_department() }</option>
									<%
										while(rs.next()){
											out.println("<option class='input' value='"+rs.getString("name_department")+"'>"+rs.getString("name_department")+"</option>");
										}
										dbl2.finishQuery();
										//
										dbl2.disconnectDB();
									%>
								</select>
				    		</li>  
				    		<li>		    
				    			<input type="submit" class="button" value="确认">
		   					</li>
		   					<li>		    	
								<a href="servlet/Servlet_machine?part=4&number_machine=${message.getNumber_machine() }">
									<input type="button" class="button" value="删除">
								</a>				
		   					</li>		
						</ul>	
					</c:when>
					<c:otherwise>
						<ul style="width:inherit;height:20px;padding:0 0;border:none;"></ul>
						<ul id="th" style="background-color:#4991CB;border:none;">
							<li><input type="text" class="input" value="机台号" readonly="readonly"></li>
							<li><input type="text" class="input" value="机台状态" readonly="readonly"></li>
							<li><input type="text" class="input" value="机台幅数" readonly="readonly"></li>
							<li><input type="text" class="input" value="处理上界" readonly="readonly"></li>
							<li><input type="text" class="input" value="处理下界" readonly="readonly"></li>
							<li><input type="text" class="input" value="所属部门" readonly="readonly"></li>
							<li><input type="text" class="input" value="操作" readonly="readonly"></li>
						</ul>							
					</c:otherwise>
				</c:choose>		
			</form>  	
		</div>
	</div>
	<c:set value="${requestScope.message}" var="message"></c:set> 
	<c:choose>
	<c:when test="${message == null }">
	<div id="dataList_2" style="width:1050px;height:430px;">
		<div id="list_2_1">	
			<iframe id="iframe" src="servlet/Servlet_machine?part=5" style="width:1050px;height:430px;border:none;"></iframe>
		</div>
	</div>
	</c:when>
	</c:choose>
  </body>
</html>
