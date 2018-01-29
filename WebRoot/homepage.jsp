<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">    
    <title>印染车间排产系统</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="SHORTCUT ICON" href="image/logo.ico"/>	
	<link rel="stylesheet" type="text/css" href="css/common.css">	
	<script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
	<style type="text/css">
		/* 左侧导航栏样式，关于菜单列表的相关设计 */
		#accordion{
			width: 100%;
		}
		#accordion li a{
			font-size:18px;
			text-decoration: none;
			color:#ffffff;
			display: block;
			background-color: #3C86C3;
			height: 50px;
			line-height:50px;
			padding-left: 0px;
			text-align:left;
			padding-left:45px;
		}
		#accordion li ul li a{
			font-size: 16px;
			color:#ffffff;
			text-decoration: none;
			display: block;
			padding-left:25px;
			background-color: #4991CB;
			height: 42px;
			line-height: 42px;/*  */
			padding-left:60px;
		}
		#acordion a:hover{
			background-color: #990020;
			color: #ffff00;
		}
		#accordion li ul li a:hover{
			background-color: #01437B;
			color: #ffffff;
		}
		/* 关于菜单图标的设计 */
		.menu.icon {
		  color: #fff;
		  position: absolute;
		  margin-left: 20px;
		  margin-top: 24px;
		  width: 17px;
		  height: 3px;
		  background-color: currentColor;
		}	
		.menu.icon:before {
		  content: '';
		  position: absolute;
		  top: -5px;
		  left: 0;
		  width: 17px;
		  height: 3px;
		  background-color: currentColor;
		}	
		.menu.icon:after {
		  content: '';
		  position: absolute;
		  top: 5px;
		  left: 0;
		  width: 17px;
		  height: 3px;
		  background-color: currentColor;
		}
	</style>
  </head>
  
  <body style="background-color:#eee;">
  	<div id="overall"style="background-color:#fff;width:1250px;height:800px;margin:auto auto;">
	  	<div id="top" style="width:inherit;height:120px;">
	  	<!-- 头部logo部分 -->
	   		<div style="float:left;">
	   			<img width="170px" height="120px" alt="logo" src="image/logo1.png">
	   		</div>
	   		<!-- 中部字体    微软雅黑 -->
	   		<div style="float:left;font-family: 微软雅黑;position:relative;top:30px;left:300px;">
	   			<div style="font-size:30px;letter-spacing:20px;">
	   				印染车间排产系统
	   			</div>
	   			<div style="font-size:18px;position:relative;top:10px;left:30px;">
	   				Dyeing Workshop Scheduling System
	   			</div>
	   		</div>
	  	</div>
	  	<div style="background-color:#fff;width:inherit;height:10px;"></div>
		<div style="background-color:#4991CB;width:inherit;height:30px;"></div>
		<div style="background-color:#fff;width:inherit;height:40px;"></div>
		<div id="tab" style="width:170px;height:600px;float:left;">
			<ul id="accordion">
				<li>
					<div class="menu icon"></div>
					<a href="javascript:void(0);">信息查询</a>
					<ul>
						<li class ="second"><a id="" onclick="setSrc('showCon','./mainOrder.jsp')">订单列表</a></li>
						<li class ="second"><a id="" onclick="setSrc('showCon','./taskOrder.jsp')">生产单表</a></li>
						<li class ="second"><a id="" onclick="setSrc('showCon','./unit.jsp')">车卡列表</a></li>
					</ul>
				</li>
		
				<li>
					<div class="menu icon"></div>
					<a href="javascript:void(0);">排产计划</a>
					<ul>
						<li class ="second"><a id="" onclick="setSrc('showCon','./workSchedule.jsp')">进行排产</a></li>
						<li class ="second"><a id="" onclick="setSrc('showCon','./taskOrderPlan.jsp')">生产单计划</a></li>
						<li class ="second"><a id="" onclick="setSrc('showCon','./unitPlan.jsp')">查询卡计划</a></li>
						<li class ="second"><a id="" onclick="setSrc('showCon','./machinePlan.jsp')">机台计划</a></li>
					</ul>
				</li>
				<li>
					<div class="menu icon"></div>
					<a href="javascript:void(0);">车卡更改</a>
					<ul>
						<li class ="second"><a id="" onclick="setSrc('showCon','./splitUnit.jsp')">进行拆卡</a></li>
						<li class ="second"><a id="" onclick="setSrc('showCon','./transUnit.jsp')">进行转卡</a></li>
					</ul>
				</li>
				<li>
					<div class="menu icon"></div>
					<a href="javascript:void(0);">系统设置</a>
					<ul>
						<li class ="second"><a id="" onclick="setSrc('showCon','./systemParam.jsp')">系统参数</a></li>
						<li class ="second"><a id="addMachine" onclick="setSrc('showCon','./addMachine.jsp')">添加机台</a></li>
						<li class ="second"><a id="machineMsg" onclick="setSrc('showCon','./machineMsg.jsp')">机台设置</a></li>			 						
					</ul>
				</li>			 				
			</ul>
		</div>
	<div id="tabCon" style="float:right;">
		<div style="background-color:#4991CB;width:1070px;height:10px;"></div>
		<iframe id="showCon" style="width:1070px;height:600px;border:none;"></iframe>
	</div>
	</div>
  </body>
  <script type="text/javascript">
    /*代码初始化，左侧导航栏*/
	$(document).ready(function(){
		$("#accordion>li>a+*:not(:first)").hide();
		$("#accordion>li>a").click(function(){
			$(this).parent().parent().each(function(){
				$(">li>a+*",this).slideUp();
			});
			$("+*",this).slideDown();
		});
		$("#accordion2>li>a+*:not(:first)").hide();
		$("#accordion2").hide();
		$("#accordion2>li>a").click(function(){
			$(this).parent().parent().each(function(){
				$(">li>a+*",this).slideUp();
			});
			$("+*",this).slideDown();
		});
		//$("#mycontent").load("homepage.jsp");
	});
	$("#addMachine").click(function(){
		$("#mycontent").load("addMachine.jsp");
	});
	$("#machineMsg").click(function(){
		$("#mycontent").load("machineMsg.jsp");
	});
	/**
	 * 
	 */
	function setSrc(id_ctrl,src){
		document.getElementById(id_ctrl).src = src;
	}
  </script> 
</html>
