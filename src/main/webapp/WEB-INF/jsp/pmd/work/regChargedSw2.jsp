<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="pmd.common.vo.UserInfoVO" %>
<%@ page import="pmd.common.common.PMDUtil" %>
<%@ page import="java.util.ArrayList" %>
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
			padding-top:80px;
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
            width:250px;
            margin-right:10px;
            margin-left:10px;
            margin-bottom:15px;
            margin-top:15px;
        }
        #sheet{ 
        	padding-top:30px;
        	margin:0 auto;
        	border:1px solid black;
        	width:400px;
        	height:450px;
        	vertical-align: middle;
        }
        
        #buttonWrap{
        	margin:0 auto;
        	margin-top:30px;
        }
		#registerButton{
			background-color:#8AA66F;
			border:1px solid black; 
			display:inline-block;
			width:100px;
			height:30px;
			padding-top:5px;
			cursor: pointer;
		}
		#cancelButton{
			background-color:white;
			border:1px solid black;
			display:inline-block;
			width:100px;
			height:30px;
			padding-top:5px;
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
	</script>
</head>
<body>
	<div id="top">
		<div id="top_wrap">
			<div id="top_logo">
				<a href="${pageContext.request.contextPath}/web/work/generalFilter.do">
					<img id="top_logo_img" src="${pageContext.request.contextPath}/Content/images/logo.jpg">
				</a>
			</div>
			<div id="top_content">
				<b><span style="color:#045FB4;"><%=userInfo.getUserId() %></span></b>님 안녕하세요.
					<a href="${pageContext.request.contextPath}/web/main/myPage.do">정보수정</a>
					<a href="${pageContext.request.contextPath}/web/main/logout.do">로그아웃</a>
					<br>
				<%=nowDate%>
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
			
		</div>
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
			function register(form){
				var f= form[0];
				
				var message= "현재 내용으로 추가하시겠습니까?";
				
				if(f.swName.value == null || f.swName.value == ""){
					alert("소프트웨어 이름을 입력해주세요!");
					return false;
				}
				
				if(!confirm(message)){
					return false;
				}
				
			    f.method="post";
			    f.action="<c:url value='/web/work/regChargedSw.do'/>";
			    f.submit();
			}
			function cancel(){
				window.location.href="${pageContext.request.contextPath}/web/work/companiesInfo.do";	
			}
		</script>
			<div id="sheet">
			<span style="height:50px;">유료 소프트웨어 등록</span>
			<form name="regForm">
				<input class="textBox" type="text" name="swName" placeholder="제품명 (주요 exe파일명, 정확히 입력)">
				<input class="textBox" type="text" name="swVendor" placeholder="개발사">
				<input class="textBox" type="text" name="swFile" placeholder="주요 실행 프로세스(확장자 포함)">
				<input class="textBox" type="text" name="swVersion" placeholder="버전">
				<input class="textBox" type="text" name="swVendorKr" placeholder="검색키워드(한글)">
				<div id="buttonWrap">
					<div id="registerButton" onclick="register(document.getElementsByName('regForm'))">
						<span style="color:white">등록</span>
					</div>
					<div id="cancelButton" onclick="cancel()">
						<span style="color:black">취소</span>
					</div>
				</div>
			</form>
			</div>
		</div>
	 </div>
	<div id="bot">
		Copyright &uml; <b>Msoft</b>, All rights reserved.
	</div>
      	
    	
</body>
</html>