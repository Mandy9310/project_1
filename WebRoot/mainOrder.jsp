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
    
    <title>mainOrder</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="css/common.css">

	<script type="text/javascript" src="js/"></script>	

  </head>
  <style type="text/css">
  	#list_1_1 ul{width:inherit;padding:0px 100px;clear:both;border-bottom:1px double gray;}
  	#list_1_1 li{float:left;color:#666;}
  	#mainOrderList li{width:150px;text-align:center;}
  	.input{text-align:center;width:100px;}
  	#th input{background-color:#4991CB;color:white;font-size:16px;}
  </style>
  <body style="width:1070px;height:600px;">

	<div id="dataList_1" style="width:inherit;height:120px;">
		<div id="list_1_1">
			<form action="servlet/Servlet_messageList?part=1" method="post" id="form_1">
				<ul style="height:50px;line-height:50px;padding:0px 40px;">							
					<li style="width:250px;"> 
					搜索订单:								
						<input type="text" class="input" name="number_order_main" style="width:150px;">
					</li>						
 					<li>
 						<input class="button" type="submit" value="查询"> 						
					</li> 
				</ul>					
			</form>

			<div id="mainOrderList">
			<c:set value="${requestScope.message}" var="message"></c:set> 
			<c:choose>
				<c:when test="${message != null }">			
					<form id="form_2" action="servlet/Servlet_messageList?part=3" method="post">
						<ul id="th" style="background-color:#4991CB;height:40px;line-height:40px;border:1px double gray;border-top:none;">
							<li><input type="text" class="input" value="加入排产" readonly="readonly"></li>
							<li><input type="text" class="input" value="订单编号" readonly="readonly"></li>
							<li style="width:200px;"><input type="text" class="input" value="客户名称" readonly="readonly"></li>
							<li><input type="text" class="input" value="交货日期" readonly="readonly"></li>
							<li><input type="text" class="input" value="生产单" readonly="readonly"></li>
						</ul>					
						<ul style="height:30px;line-height:30px;">
							<c:choose>
								<c:when test="${message.isChecked() == true}">
									<li><input type="checkbox" class="checkbox" checked="checked" name="checked" value="checked" onchange="submit('form_2')"></li>
								</c:when>
								<c:otherwise>
									<li><input type="checkbox" class="checkbox" name="checked" value="checked" onchange="submit('form_2')"></li>
								</c:otherwise>
							</c:choose>												
							<li>				
			    				<input type="text" class="input" name="number_order_main" value="${message.getNumber_order_main()}" readonly="readonly">
			    			</li>	
							<li style="width:200px;">				
			    				<input type="text" class="input" name="number_machine" value="${message.getName_client()}" readonly="readonly" style="width:200px;">
			    			</li>	
							<li>				
			    				<input type="text" class="input" name="number_machine" value="${message.getDate_delivery()}" readonly="readonly">
			    			</li>				    						    					    
		   					<li style="">		    	
								<a href="servlet/Servlet_messageList?part=5&number_order_main=${message.getNumber_order_main()}">
									<input type="button" class="button" value="查看">
								</a>				
		   					</li>		
						</ul>	
					</form>
				</c:when>
				<c:otherwise>
				<ul style="height:30px;line-height:30px;border:none;"></ul>
				<ul id="th" style="background-color:#4991CB;height:40px;line-height:40px;border:1px double gray;">
					<li><input type="text" class="input" value="加入排产" readonly="readonly"></li>
					<li><input type="text" class="input" value="订单编号" readonly="readonly"></li>
					<li style="width:200px;"><input type="text" class="input" value="客户名称" readonly="readonly"></li>
					<li><input type="text" class="input" value="交货日期" readonly="readonly"></li>
					<li><input type="text" class="input" value="生产单" readonly="readonly"></li>
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
				<iframe id="iframe" src="servlet/Servlet_messageList?part=2" style="width:inherit;height:450px;border:none;"></iframe>
			</div>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${sessionScope.number_order_main == message.getNumber_order_main()}">
				<div id="" style="width:inherit;height:450px;margin:0 auto;">
					<div style="background-color:#4991CB;width:inherit;height:30px;line-height:30px;color:white;">
						<ul style="padding:0 50px;">
							<li>订单（${sessionScope.number_order_main }）--生产单</li>
							<li></li>
						</ul>
					</div>
					<div style="width:inherit;">
						<iframe id="iframe" src="servlet/Servlet_messageList?part=7&number_order_main=${sessionScope.number_order_main }" style="width:inherit;height:410px;border:none;"></iframe>
					</div>	
				</div>		
				</c:when>
			</c:choose>			
		</c:otherwise>
	</c:choose>
	

  </body>
  <script type="text/javascript">
  	function submit(ctrlId){
  		document.getElementById(ctrlId).submit();
  	}
  </script>
</html>
