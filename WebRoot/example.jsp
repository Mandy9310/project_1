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
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="SHORTCUT ICON" href="image/logo.ico"/>

	<style type="text/css">
	*{
		margin:0px;
		padding:0px;
		font-family: Tahoma;
	}
	.menu_ul{
		width:280px;
		float: left;
		display: block;
		list-style-type: none;
		text-align: center;
		position:relative;
		top:11px;
		font-size: 20px;
	}
	ul,li{
		list-style-type: none;
	}
	/* 关于菜单列表的相关设计 */
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
	/*内容的边框部分*/
	.myborder{
		width: 98.3%;
		height: auto;
		border: 2px solid #E1E1E1;
	}
	.mytitle{
		border-bottom: 2px solid #E1E1E1;
		border-top:5px solid #3C86C3 ;
		font-size: 18px;
		padding: 9px;
		padding-left: 20px;
	}
	.mytitlebold{
		background-color: #3C86C3;
		width: 10px;
		height: 22px;
		float: left;
		border-right: 10px solid #fff;
	}
	.mycontent{
		font-size: 18px;
		padding: 13px;
		padding-left: 20px;
		height: auto;
	}
	
	/*内容部分模拟例子*/
	.example{
		font-size:16px;
		padding:3px;
	}
	.exButton{
		width:100px;
		height:30px;
		font-size:16px;
		
	}
	</style>
  </head>
  
	<body style="background-color: #F2F4F7">
		<center>
			<div style="width: 1160px;min-height: 500px; background-color: white;">
		   		<!-- 头部logo部分 -->
		   		<div style="width:1160px;height:160px;background-color:white;">
		    		<!-- 左侧logo  logo.png-->
		    		<div style="float:left;">
		    		<img width="200px" height="150px" alt="logo" src="image/logo1.png">
		    		</div>
		    		<!-- 中部字体    微软雅黑 -->
		    		<div style="float:left;font-family: 微软雅黑;position:relative;top:50px;left:180px;">
		    			<div style="font-size: 30px;letter-spacing:20px;margin:0 auto;">
		    				印染车间排产系统
		    			</div>
		    			<div style="font-size: 20px;position:relative;left:-9px;">
		    				Dyeing Workshop Scheduling System
		    			</div>
		    		</div>
		    		<!-- 右侧图片   apartment.png-->
		    		<div style="float:right;">
		    		</div>
		    	</div>
		    	<!-- 清除浮动 -->
		    	<div style="clear: both;"></div>
		   	 	<!-- 天蓝色间隔 -->
		    	<div style="width:1160px;height:29px;background-color:#3C86C3;"></div>
		    	<!-- 导航栏部分 -->
		    	<div style="width:1160px;height: 47px;background-color: white;">
			 		<!--  -->
			 		<ul class="menu_ul">
			 			<li></li>
			 		</ul>
			 		<!---->
			 		<ul class="menu_ul">
			 			<li></li>
			 		</ul>
			 		<!---->
			 		<ul class="menu_ul">
			 			<li></li>
			 		</ul>
			 		<!--  -->
			 		<ul class="menu_ul">
			 			<li></li>
			 		</ul>
		  			 </div>
		   		<div style="width:1160px;height:auto;">
		  				<!-- 左侧子菜单 -->
		   			<div style="width:15%;height:auto;float: left;">
			    		<ul id="accordion">
			 				<li>
			 					<div class="menu icon"></div>
			 					<a href="javascript:void(0);">信息查询</a>
			 					<ul>
			 						<li class ="second"><a id="">订单列表</a></li>
			 						<li class ="second"><a id="">生产单表</a></li>
			 						<li class ="second"><a id="">车卡列表</a></li>
			 					</ul>
			 				</li>
			 		
			 				<li>
			 					<div class="menu icon"></div>
			 					<a href="javascript:void(0);">排产计划</a>
			 					<ul>
			 						<li class ="second"><a id="">进行排产</a></li>
			 						<li class ="second"><a id="">机台排产</a></li>
			 					</ul>
			 				</li>
			 				<li>
			 					<div class="menu icon"></div>
			 					<a href="javascript:void(0);">车卡更改</a>
			 					<ul>
			 						<li class ="second"><a id="">进行拆卡</a></li>
			 						<li class ="second"><a id="">进行转卡</a></li>
			 					</ul>
			 				</li>
			 				<li>
			 					<div class="menu icon"></div>
			 					<a href="javascript:void(0);">系统设置</a>
			 					<ul>
			 						<li class ="second"><a id="">系统参数</a></li>
			 						<li class ="second"><a id="addMachine">添加机台</a></li>
			 						<li class ="second"><a id="machineMsg">机台设置</a></li>			 						
			 					</ul>
			 				</li>			 				
			 			</ul>
		   			</div>
		    		<!-- 右侧内容 -->
		    		<div style="width:84%;min-height:500px;float: right;text-align: left;">
				    	<div class="myborder" >
				    		<div class="mytitle">
				    		<div class="mytitlebold"></div>
				  			<p>系统查询与处理</p>
				  			</div>
				  			<div class="mycontent" id="mycontent" >
				  			<!-- 这里填入内容-->
				  			
				  			</div>
				  		</div>
		   			</div>
		   		</div>
		    	<!-- 清除浮动效果 -->
		    	<div style="clear:both;"></div>
		    	<!-- 空白 -->
		   		<div style="width:100%;height:30px;"></div>
		    	<!-- 底部 -->
		   		 <div style="width:1160px;height:55px;background-color: #3C86C3;">
			     	<div style="float: left;position: relative;color: #FFFFFF;top:15px;left: 440px;">
			     		<div>Dyeing Workshop Scheduling System</div>
			     	</div>
		   		</div>
		   	</div>
		</center>
	</body>
	<script src="jquery/jquery-3.2.1.js"></script>
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
  </script> 
</html>
