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
    
    <title>unit</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/common.css">
  </head>
   <style type="text/css">
  	#list_1_1 ul{width:inherit;padding:0px 50px;clear:both;border-bottom:1px double gray;}
  	#list_1_1 li{float:left;color:#666;}
  	#unitList li{width:150px;text-align:center;}
  	.input{text-align:center;width:100px;}
  	#th input{background-color:#4991CB;color:white;font-size:16px;}
  </style>
  <body style="width:1070px;height:600px;">
	<div id="dataList_1" style="width:inherit;height:120px;">
		<div id="list_1_1">
			<form action="servlet/Servlet_messageList?part=15" method="post" id="form_1">
				<ul style="height:50px;line-height:50px;">							
					<li style="width:250px;"> 
						搜索卡:								
						<input type="text" class="input" name="number_unit" style="width:150px;">
					</li>						
 					<li>
 						<input class="button" type="submit" value="查询"> 						
					</li> 
				</ul>					
			</form>
			<div id="unitList">
				<c:set value="${requestScope.message}" var="message"></c:set> 
				<c:choose>
					<c:when test="${message != null }">			
						<ul id="th" style="background-color:#4991CB;height:40px;line-height:40px;border:1px double gray;border-top:none;">
							<li style="width:80px;"><input type="text" class="input" value="加入排产" readonly="readonly"></li>
							<li><input type="text" class="input" value="车卡编号" readonly="readonly"></li>
							<li><input type="text" class="input" value="本卡布长" readonly="readonly"></li>							
							<li style="width:500px;"><input type="text" class="input" value="对应工艺" readonly="readonly"></li>
						</ul>	
						<form id="form_2" action="servlet/Servlet_messageList?part=17" method="post">				
							<ul style="height:30px;line-height:30px;">
								<c:choose>
									<c:when test="${message.isChecked() == true}">
										<li style="width:80px;"><input type="checkbox" class="checkbox" checked="checked" name="checked" value="checked" onchange="submit('form_2')" style="width:80px;"></li>
									</c:when>
									<c:otherwise>
										<li style="width:80px;"><input type="checkbox" class="checkbox" name="checked" value="checked" onchange="submit('form_2')" style="width:80px;"></li>
									</c:otherwise>
								</c:choose>												
							<li>				
			    				<input type="text" class="input" name="number_unit" value="${message.getNumber_unit()}" readonly="readonly" style="width:150px;">
			    			</li>
							<li>				
			    				<input type="text" class="input" name="length_unit" value="${message.getLength_unit()}">
			    			</li>	    				
			    			<li style="width:500px;">				
			    				<input type="text" class="input" name="" value="${message.getName_craft()}" readonly="readonly" style="width:500px;">
			    			</li>		
							</ul>	
						</form>
					</c:when>
					<c:otherwise>
						<ul style="height:30px;line-height:30px;border:none;"></ul>
						<ul id="th" style="background-color:#4991CB;height:40px;line-height:40px;border:1px double gray;">
							<li style="width:80px;"><input type="text" class="input" value="加入排产" readonly="readonly" style="width:80px;"></li>
							<li><input type="text" class="input" value="车卡编号" readonly="readonly"></li>
							<li><input type="text" class="input" value="本卡布长" readonly="readonly"></li>							
							<li style="width:500px;"><input type="text" class="input" value="对应工艺" readonly="readonly"></li>
						</ul>			
					</c:otherwise>
				</c:choose>		
			</div>
		</div>
	</div>	
	<div style="background-color:white;width:inherit;height:10px;"></div>
	
	<c:set value="${requestScope.message}" var="message"></c:set> 
	<c:choose>
		<c:when test="${message == null }">
			<div id="" style="width:inherit">
				<iframe id="iframe" src="servlet/Servlet_messageList?part=16" style="width:inherit;height:450px;border:none;"></iframe>
			</div>
		</c:when>
	</c:choose>
  </body>
  <script type="text/javascript">
  	function submit(ctrlId){
  		document.getElementById(ctrlId).submit();
  	}
  </script>
</html>
