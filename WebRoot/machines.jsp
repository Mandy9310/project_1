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
    
    <title>b_setMachine</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link rel="stylesheet" type="text/css" href="css/common.css">
	<link rel="stylesheet" type="text/css" href="css/machines.css">
  </head>
   <style type="text/css">
  	#list_1 ul{width:inherit;height:25px;line-height:20px;padding:0px 40px;border-bottom:1px double gray;clear:both;}
  	#list_1 li{float:left;padding:0px 20px 0px 0px;margin:0 0;}
  	input.input{width:100px;text-align:center;}
  	.button, .input, .button:hover, .input:hover{border:1px double transparent;}
  	select.button{width:100px;}
  </style>
  <body style="width:1050px;height:430px;">
	<div id="dataList" style="width:inherit;height:390px;margin:0 auto;border:0px double red;">
		<div id="list_1">	
			<c:forEach items="${requestScope.messages}" var="message">  
				<form action="servlet/Servlet_machine?part=6" method="post">
			    	<ul id="ul_2">    		
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
								String querySql = "SELECT * FROM department";
								DB_Link dbl = new DB_Link();
								//
								dbl.connectDB();
								dbl.executeQuery(querySql);
								ResultSet rs = dbl.getRs();
							%>													
							<select class="button" name="input_5_${message.getNumber_machine()}" id="select_5_${message.getNumber_machine()}">
								<option selected="selected" value="${message.getName_department() }">${message.getName_department() }</option>
								<%
									while(rs.next()){
										out.println("<option class='input' value='"+rs.getString("name_department")+"'>"+rs.getString("name_department")+"</option>");
									}
									dbl.finishQuery();
									//
									dbl.disconnectDB();
								%>
							</select>
			    		</li>  
			    		<li>		    
			    			<input type="submit" class="button" value="确认">
	   					</li>
	   					<li>		    
			    			<a href="servlet/Servlet_machine?part=7&number_machine=${message.getNumber_machine() }">
				    			<input type="button" class="button" value="删除">
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
				<ul style="height:20px;line-height:20px;clear:both;border:0px double blue;padding:0px 0px 0px 300px;">
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
  </body>
</html>
