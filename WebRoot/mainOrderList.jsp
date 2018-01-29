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
    
    <title>mainOrderList</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link rel="stylesheet" type="text/css" href="css/common.css">

  </head>
  <style type="text/css">
  	#list_1 ul{width:inherit;height:25px;line-height:20px;padding:0px 100px;border-bottom:1px double gray;clear:both;}
  	#list_1 li{width:150px;float:left;margin:0 0;text-align:center;}
  	.input{width:100px;text-align:center;}
  	.button, .input, .button:hover, .input:hover{border:1px double transparent;}
  	select.button{width:100px;}
  </style>
  <body style="width:1050px;height:450px;">
	<div id="dataList_1" style="width:inherit;height:170px;margin:0 auto;overflow:auto;border:0px double red;">
		<div id="list_1">	
			<c:forEach items="${requestScope.messages}" var="messages">
			<form id="form_1" action="servlet/Servlet_messageList?part=4" method="post">
				<ul id="ul_3">
					<c:choose>
						<c:when test="${messages.isChecked() == true}">
							<li><input type="checkbox" class="checkbox" checked="checked" name="checked" value="checked" onchange="submit('form_1')"></li>
						</c:when>
						<c:otherwise>
							<li><input type="checkbox" class="checkbox" name="checked" value="checked" onchange="submit('form_1')"></li>
						</c:otherwise>
					</c:choose>														
					<li>				
	    				<input type="text" class="input" name="number_order_main" value="${messages.getNumber_order_main()}" readonly="readonly">
	    			</li>	
					<li style="width:200px;">				
	    				<input type="text" class="input" name="" value="${messages.getName_client()}" readonly="readonly" style="width:200px;">
	    			</li>	
					<li>				
	    				<input type="text" class="input" name="" value="${messages.getDate_delivery()}" readonly="readonly">
	    			</li>				    						    					    
   					<li style="">		    	
						<a href="servlet/Servlet_messageList?part=6&number_order_main=${messages.getNumber_order_main()}">
							<input type="button" class="button" value="查看">
						</a>				
   					</li>		
				</ul>
			</form>
			</c:forEach>
		</div>
		
	</div>
	<div id="" style="width:inherit;margin:0 0;border-bottom:1px double gray;padding:5px 0px 0px 0px;">
		<c:choose>
			<c:when test="${requestScope.count_pages > 1}">
				<ul style="height:20px;line-height:20px;clear:both;border:0px double blue;padding:0px 0px 0px 230px;">
	    			<li><span class="pageLink">第${requestScope.page}页/共${requestScope.count_pages}页</span>
						<c:choose>
							<%--判断是否第一页来选择向前跳转的显示--%>  
						    <c:when test="${requestScope.page==1}">
						    	<span class="pageLink">上一页 首页</span>
						    </c:when>  
						    <c:otherwise>  
						        <a href="<c:url value ='${requestScope.src}&page=${requestScope.page-1}'/>">
						        	<span class="pageLink">上一页</span>
				        		</a>
						        <a href="<c:url value ='${requestScope.src}&page=1'/>">
						        	<span class="pageLink">首页</span>
				        		</a>
							</c:otherwise>
						</c:choose>  
						<%--计算begin和end --%>  
						<c:choose>  
							<%--如果总页数不足10，那么就把所有的页都显示出来 --%>  
						    <c:when test="${requestScope.count_pages<=10}">  
						        <c:set var="begin" value="1" />  
						        <c:set var="end" value="${requestScope.count_pages}" />
							</c:when>  
						    <c:otherwise>  
						        <%--如果总页数大于10，通过公式计算出begin和end --%>  
						        <c:set var="begin" value="${requestScope.page-5}" />  
						        <c:set var="end" value="${requestScope.page+4}" />  
						        <%--头溢出 --%>  
						        <c:if test="${begin<1}">  
						            <c:set var="begin" value="1"></c:set>  
						            <c:set var="end" value="10"></c:set>
								</c:if>  
						        <%--尾溢出 --%>  
						        <c:if test="${end>requestScope.count_pages}">  
						            <c:set var="begin" value="${requestScope.count_pages-9}"></c:set>  
						            <c:set var="end" value="${requestScope.count_pages}"></c:set>
						        </c:if>
						     </c:otherwise>
						</c:choose>  
						<%--循环显示页码列表 --%>  
						<c:forEach var="i" begin="${begin}" end="${end}">  
						    <c:choose>  
						        <c:when test="${i == requestScope.page}">
						        	<span class="pageLink">${i}</span>				        	
					        	</c:when>  
						        <c:otherwise>  
						            <a href="<c:url value ='${requestScope.src}&page=${i}'/>">
						            	<span class="pageLink">${i}</span>
				            		</a>
						        </c:otherwise>
						    </c:choose>
						</c:forEach>  
						<c:choose>
						<%--判断是否最后一页来选择向后跳转的显示--%>  
						    <c:when test="${requestScope.page==requestScope.count_pages}">
						    	<span class="pageLink" style="border-right:1px double gray">下一页  尾页</span>
					    	</c:when>  
						    <c:otherwise>  
						        <a href="<c:url value ='${requestScope.src}&page=${requestScope.page+1}'/>">
						        	<span class="pageLink">下一页</span>
				        		</a>  
						 		<a href="<c:url value ='${requestScope.src}&page=${requestScope.count_pages}'/>">
						 			<span class="pageLink" style="border-right:1px double gray">尾页</span>
				 				</a>
							</c:otherwise>
						</c:choose>	 
					</li>
				</ul>
			</c:when>
    	</c:choose>
    </div>
	<c:choose>
		<c:when test="${sessionScope.number_order_main != null}">
		<div id="dataList_1" style="width:inherit;height:250px;margin:0 auto;border:0px double green;">
			<div style="background-color:white;width:inherit;height:5px;"></div>
			<div style="background-color:#4991CB;width:inherit;height:30px;line-height:30px;color:white;">
				<ul style="padding:0 50px;">
					<li>订单（${sessionScope.number_order_main }）--生产单</li>
					<li></li>
				</ul>
			</div>
			<div style="width:inherit;">
				<iframe id="iframe" src="servlet/Servlet_messageList?part=7&number_order_main=${sessionScope.number_order_main }" style="width:inherit;height:210px;border:none;"></iframe>
			</div>	
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
