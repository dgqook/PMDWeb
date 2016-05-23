<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="pmd.common.vo.UserInfoVO" %>
<%@ page import="pmd.common.common.PMDUtil" %>
<%	
	UserInfoVO userInfo= (UserInfoVO)session.getAttribute("userInfo");
	String restOfExpiry= (String) session.getAttribute("restOfExpiry");

	if(userInfo == null || restOfExpiry == null) response.sendRedirect(PMDUtil.PMD_URL);
%>
<!DOCTYPE html>

<html>
<head>
    <meta name="viewport" content="width=device-width" />
    
    <!-- 부트스트랩 및 제이쿼리 항상 최신버전 적용 -->
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/latest/css/bootstrap.min.css">
	<script src="//code.jquery.com/jquery.min.js"></script>
	<script src="//maxcdn.bootstrapcdn.com/bootstrap/latest/js/bootstrap.min.js"></script>
	<!-- !부트스트랩 및 제이쿼리 항상 최신버전 적용 -->
	
	
<title>Coordy 소프트웨어 자산관리 - M Soft</title>

	<style>
		html,body {
        	height:100%;
        	width:100%;
        	padding:0px; 
        	margin:0px;

        	background-color:#eef6fd;
        	text-align:center; 
        	vertical-align:middle; 
        	font-family: dotum;
      	}
		label {
			font-weight:300;
		}
		#top{
			position:fixed;
			width:100%;
			height:130px;
			margin:0 auto;
			z-index:99; /* 항상 위에 있도록 처리 */
			
			
			background-color:white;
		}
		#top_wrap{
			width:1000px;
			margin:0 auto;
			margin-top:-1px;
			height:130px;
			
			border:1px solid #FFFFFF;
		}
		#top_logo{
			float: left;
			width:50%;
			height:90px;
			margin-left:0px;
			vertical-align:middle;
			text-align:left;
			padding-top:25px;
		}
		#top_content{
			float: right;
			width:50%;
			height:90px;
			text-align:right;
			padding-right:100px;
			padding-top: 40px;
			
		}
		#top_menu{
			position:fixed;
			margin-top:-40px;
			width:100%;
			height:40px;
			background-color:#E0E0F8;
			vertical-align:middle;
		}
		#mid{
			padding-top:130px;
		}
		#top_menu_bucket{
			width:100%;
			font-size:0px;
			padding-left:0px;
			border:1px solid #dde4e9;
			background-color:#a0cbf4;
		}
		.top_menu_item{
			font-size:13px;
			padding-top:12px;
			width:199.5px;
			height:40px;
			display:inline-block;
			border:0px solid black;
			vertical-align:middle;
		}
		.mouse_over {
			background-color:#4591d7;
			cursor: pointer;
		}
		
		#mid_wrap{
			width:1000px;
			margin:0 auto;
			background-color:white;
			border:1px solid #dde4e9;
			min-height:700px;
		}
		#bot {
			width:100%;
			height:40px;
			color:white;
			text-align:center;
			background-color:#424242;
			position:fixed bottom;
			padding-top:10px;
			font-size:12px;
		}     	
		th {
			text-align:center;
		}
		thead{ 
			height:25px;
		} 
    </style>
	<script>
	$(document).ready(function(){
		$(".top_menu_item").hover(
			function() {
		    	$(this).addClass("mouse_over").siblings().removeClass("mouse_over");
			},
			function() {
		    	$(this).removeClass("mouse_over");
			}
		);
	});
		
	</script>
</head>
<body>
	<div id="top">
		<div id="top_wrap">
			<div id="top_logo">
				<a href="${pageContext.request.contextPath}/web/info/summary.do">
					<img id="top_logo_img" src="${pageContext.request.contextPath}/Content/images/logo.jpg">
				</a>
			</div>
			<div id="top_content">
				<b><span style="color:#045FB4;"><%=userInfo.getUserId() %></span></b>님 안녕하세요.
					<a href="${pageContext.request.contextPath}/web/main/myPage.do">정보수정</a>
					<a href="${pageContext.request.contextPath}/web/main/logout.do">로그아웃</a>
					<br>
				<%=restOfExpiry %>
			</div>
		</div>
		<script type="text/javascript">
		function goSummary(){
			window.location.href='/web/info/summary.do';
		}
		function goPresent(){
			window.location.href='${pageContext.request.contextPath}/web/info/present.do';
		}
		function goManage(){
			window.location.href='${pageContext.request.contextPath}/web/info/manage.do';
		}
		function goSearch(){
			window.location.href='${pageContext.request.contextPath}/web/info/search.do';
		}
		function goDownload(){
			window.location.href='${pageContext.request.contextPath}/web/main/clientDownload.do';
		}
		</script>
		<div id="top_menu">
			<ul id="top_menu_bucket">
				<li class="top_menu_item" onclick="goSummary()">요약 정보</li>
				<li class="top_menu_item" onclick="goPresent()">소프트웨어 현황</li> 
				<li class="top_menu_item" onclick="goManage()">소프트웨어 관리</li>
				<li class="top_menu_item" onclick="goSearch()">소프트웨어 검색</li>
				<li class="top_menu_item" onclick="goDownload()">다운로드</li>
			</ul>
		</div>
	</div>
	<div id="mid">
		<div id="mid_wrap">
		
		${success}
		
		</div>
	</div>
	<div id="bot">
		Copyright &uml; <b>Msoft</b>, All rights reserved.
	</div>
      	
    	
</body>
</html>