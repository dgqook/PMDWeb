<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="pmd.common.vo.UserInfoVO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="pmd.common.common.PMDUtil" %>
<%	
	UserInfoVO userInfo= (UserInfoVO)session.getAttribute("userInfo");
	String nowDate= (String) session.getAttribute("nowDate");

	if(userInfo == null || nowDate == null) response.sendRedirect(PMDUtil.PMD_URL);
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
	
	<!-- datePicker -->
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" />
	<script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>
	<!-- !datePicker -->
	
<title>Coordy 소프트웨어 자산관리 - M Soft</title>

	<style>
		html,body {
        	height:100%;
        	width:100%;
        	padding:0px; 
        	margin:0px;

        	background-color:#b5db92; 
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
			height:100px;
			margin:0 auto;
			z-index:99; /* 항상 위에 있도록 처리 */
			
			
			background-color:white;
		}
		#top_wrap{
			width:1500px;
			margin:0 auto;
			margin-top:-1px;
			height:100px;
			
			border:1px solid #FFFFFF;
		}
		#top_logo{
			float: left;
			width:50%;
			height:90px;
			margin-left:0px;
			vertical-align:middle;
			text-align:left;
			padding-top:10px;
		}
		#top_content{
			float: right;
			width:50%;
			height:90px;
			text-align:right;
			padding-right:100px;
			padding-top: 15px;
			
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
			padding-top:100px;
		}
		#top_menu_bucket{
			width:100%;
			font-size:0px;
			padding-left:0px;
			border:1px solid #7F9A67;
			background-color:#9FC081;
		}
		#top_menu_bucket2{
			margin:0 auto;
			width:1500px;
		}
		.top_menu_item{
			font-size:13px;
			padding-top:12px;
			width:20%;
			height:40px;
			display:inline-block;
			border:0px solid black;
			vertical-align:middle;
		}
		.mouse_over {
			background-color:#8AA66F;
			cursor: pointer;
		}
		
		#mid_wrap{
			width:1500px;
			margin:0 auto;
			background-color:white;
			border-left:1px solid #7F9A67;
			border-right:1px solid #7F9A67;
			min-height:800px;
			padding-top:30px;
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
		.textBox{
        	background: url('${pageContext.request.contextPath}/Content/images/wrest.gif') #f7f7f7 top right no-repeat !important;
            border: 1px solid #e4eaec;
            text-align:left;
            color: #9a9a9a;
            width:200px;
            margin-right:30px;
            margin-left:5px;
        }
        
        #formTable{
        	margin:0 auto;
        }
		.formTable_td{
			height:30px;
		}
		.formTable_tr{
			width:300px;
		}
		#list_menu_bucket{
			padding-top:10px;
			padding-bottom:10px;
			margin:0 auto;
			margin-top:20px;
			margin-left:-20px;
			
		}
		
		.list_menu_item{
			display:inline-block;
			background-color:#0b72ce;
			color:white;
			padding-top:10px;
			padding-bottom:12px;
			padding-left:30px;
			padding-right:30px;
			margin-right:10px;
			margin-left:10px;
		}
		.mouse_over2 {
			background-color:#4591d7;
			cursor: pointer;
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
		$(document).ready(function(){
			$(".list_menu_item").hover(
				function() {
			    	$(this).addClass("mouse_over2").siblings().removeClass("mouse_over2");
				},
				function() {
			    	$(this).removeClass("mouse_over2");
				}
			);
		});
	</script>
</head>
<body>
	<div id="top">
		<div id="top_wrap">
			<div id="top_logo">
				<a href="${pageContext.request.contextPath}/web/work/generalFilter.do">
					<img id="top_logo_img" src="${pageContext.request.contextPath}/Content/images/logo.jpg'/>">
				</a>
			</div>
			<div id="top_content">
				<b><span style="color:#045FB4;"><%=userInfo.getUserId() %></span></b>님 안녕하세요.
					<a href="${pageContext.request.contextPath}/web/main/myPage.do">정보수정</a>
					<a href="${pageContext.request.contextPath}/web/main/logout.do">로그아웃</a>
					<br>
				<%=nowDate%>
			</div>
		</div>
		<script type="text/javascript">
		function goGeneralFilter(){
			window.location.href='${pageContext.request.contextPath}/web/work/generalFilter.do';
		}
		function goCompaniesinfo(){
			window.location.href='${pageContext.request.contextPath}/web/work/companiesInfo.do';
		}
		function goExpiryManage(){
			window.location.href='${pageContext.request.contextPath}/web/work/expiryManage.do';
		}
		function goRegWorker(){
			window.location.href='${pageContext.request.contextPath}/web/work/regWorkerPage.do';
		}
		function goCoordyManage(){
			window.location.href='${pageContext.request.contextPath}/web/work/pmdManage.do';
		}
		</script>
		<div id="top_menu">
			<div id="top_menu_bucket">
				<div id="top_menu_bucket2">
					<div class="top_menu_item" onclick="goGeneralFilter()">필터</div>
					<div class="top_menu_item" onclick="goCompaniesinfo()">업체 현황</div> 
					<div class="top_menu_item" onclick="goExpiryManage()">만료 임박 제품</div>
					<div class="top_menu_item" onclick="goRegWorker()">직원계정 전환</div>
					<div class="top_menu_item" onclick="goCoordyManage()">PMD 이용기간 관리</div>
				</div>
			</div>
		</div>
	</div>
	<div id="mid">
		<div id="mid_wrap">
			<script type="text/javascript">
				$(function() {
            	  $( "#datePicker" ).datepicker({
            			dateFormat: 'yy-mm-dd',
            	        changeMonth: true, 
            	        changeYear: true,
            	        nextText: '다음 달',
            	        prevText: '이전 달',
            	      currentText: '오늘',
            	    monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
            	    monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
            	    dayNames: ['일','월','화','수','목','금','토'],
            	    dayNamesShort: ['일','월','화','수','목','금','토'],
            	    dayNamesMin: ['일','월','화','수','목','금','토']
            	  });
            	});
			</script>
			<form name="regForm">
			<table id="formTable" border=1>
				<tr class="formTable_tr">
					<td class="formTable_td">
					계정
					</td>
					<td class="formTable_td">
					<%=(String) session.getAttribute("userId") %>
					<input type="hidden" name="userId" value="<%=(String) session.getAttribute("userId") %>">
					</td>
				</tr>
				<tr class="formTable_tr">
					<td class="formTable_td">
					기존 만료일자
					</td>
					<td class="formTable_td"> 
					<%=(String) session.getAttribute("userExpiryDate") %>
					<input type="hidden" name="fromExpiryDate" value="<%=(String) session.getAttribute("userExpiryDate") %>">
					</td>
				</tr>
				<tr class="formTable_tr">
					<td class="formTable_td">
					연장일자
					</td>
					<td class="formTable_td">
						<input id="datePicker" class="textBox" type="text" name="toExpiryDate" 
							placeholder="만료일자를 입력하세요." readonly="readonly"> 
					</td>
				</tr>
				<tr class="formTable_tr">
					<td class="formTable_td" colspan=2>
						<script type="text/javascript">
							function extend(){
		                  		var f= document.getElementsByName("regForm");
		                  		
								if(f[0].toExpiryDate.value == null || f[0].toExpiryDate.value == ""){
	                  				alert("만료일자를 입력하세요.");
	                  				f[0].toExpiryDate.focus();
	                  				return false;
								}
	                  			f[0].method="post";
	  	  			    	    f[0].action="<c:url value='/web/work/pmdExtend.do'/>";
	  	  			    	    f[0].submit();
	                  			
							}
							function cancel(){
								window.location.href="${pageContext.request.contextPath}/web/work/pmdManage.do";
							}
						</script>
							<ul id="list_menu_bucket">
							<li class="list_menu_item" onclick="extend()">등록</li>
							<li class="list_menu_item" onclick="cancel()">취소</li>
						</ul>
					</td>
				</tr>
			</table>
			</form>
		</div>
	 </div>
	<div id="bot">
		Copyright &uml; <b>Msoft</b>, All rights reserved.
	</div>
      	
    	
</body>
</html>