 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="pmd.common.vo.UserInfoVO" %>
<%@ page import="pmd.common.vo.SoftwareInfoVO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="pmd.common.common.PMDUtil" %>
<%	
	UserInfoVO userInfo= (UserInfoVO)session.getAttribute("userInfo");
	String nowDate= (String) session.getAttribute("nowDate");

	if(userInfo == null || nowDate == null) response.sendRedirect(PMDUtil.PMD_URL);
	
	@SuppressWarnings("unchecked")
	ArrayList<UserInfoVO> userList= (ArrayList<UserInfoVO>)session.getAttribute("userList");
	if(userList == null){
		userList= new ArrayList<UserInfoVO>();		
	}
	@SuppressWarnings("unchecked")
	ArrayList<SoftwareInfoVO> swList= (ArrayList<SoftwareInfoVO>)session.getAttribute("swList");
	if(swList == null){
		swList= new ArrayList<SoftwareInfoVO>();
	}
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
	
	<!-- 구글차트 -->
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	<!-- !구글차트 -->
	
	
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
        
        #spanTitle{
        	font-weight:600;
        	margin-bottom:10px;
        }
        #table1{
			width:600px;
			height:750px;
			display:inline-block;
			padding-top:30px;
			margin-right:20px;
			margin-left:20px;
			margin-top:0px;
        }
        #table2{
        	width:800px;
        	height:750px;
        	display:inline-block;
        	padding-top:30px;
        	margin-right:20px;
        	margin-left:20px;
        	margin-top:0px; 
        }
        #table_div1{
        	margin-top:0px;
        	height:700px;
        }
        #table_div2{
        	margin-top:0px;
        	height:700px;  
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
<body onload="servletMessage()">
<input type="hidden" name="servletMessage" value= "${servletMessage}"/>
	<script>
	function servletMessage(){
			var v=document.getElementsByName("servletMessage");
			if(v[0].value != ""){
				alert(v[0].value);
				v[0].value="";
			}
		}
	</script>
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
			<div id="table1">
				<script type="text/javascript">
				  function companyInfo(form){
					  var f= form;
					  f.method="post";
					  f.action="${pageContext.request.contextPath}/web/work/companyInfo.do";
					  f.submit();
				  }
				
			      google.charts.load('current', {'packages':['table']});
			      google.charts.setOnLoadCallback(drawTable);
			
			      function drawTable() {
			        var data = new google.visualization.DataTable();
			        data.addColumn('string', '계정');
			        data.addColumn('string', '업체명');
			        data.addColumn('string', '이동');
			        
			        data.addRows([
						<%if(userList!=null && userList.size()!=0){
							for(int i=0; i<userList.size(); i++){%>
						['<%=userList.get(i).getUserId()%>','<%=userList.get(i).getUserCoName()%>',
						 '<form>'+ 
						 	<%if(userList.size()>=1&&!userList.get(i).getUserId().equals("")){%>
					 	 	'<input type="button" value="이동" onclick="companyInfo(this.form)">'+
					 	 	'<input type="hidden" name="companyId" value="<%=userList.get(i).getUserId()%>">'+<%}%>
				 	 	'</form>']<%if(i!=(userList.size()-1)){%>,<%}}}%>
			        ]);
			      
			
			        var table = new google.visualization.Table(document.getElementById('table_div1'));
			
			        table.draw(data, {showRowNumber: false, width: '100%', height: '100%',allowHtml:true});
			        
			        
			      }
			      
			      function doSearchUser(event){ 
			        	var keyCode = event.which?event.which:event.keyCode;
			        	var f= document.getElementsByName("searchUser");
			        	if(keyCode==13){
                			if(f[0].userKeyword.value == null || f[0].userKeyword.value == ""){
                				alert("검색어를 입력하세요.");
                				f[0].userKeyword.focus();
                				return false;
                			}
                			
                			//alert(f[0].userKeyword.value);
                			
                			f[0].method="post";
                			f[0].action="${pageContext.request.contextPath}/web/work/companiesInfo.do";
                			f[0].submit();
                		} 
			        }
			    </script>
			    
			    <form name="searchUser">
			    	<span id="spanTitle">사용자 목록</span>
			    	<input class="textBox" type="text" placeholder="검색어를 입력하세요(Enter)" name="userKeyword" onKeyPress="doSearchUser(event)">
				</form>
				<div id="table_div1"></div>
				<input type="hidden" name="companyId" value="">
			</div>
			
			
			
			
			<div id="table2">
				<script type="text/javascript">
				  function regChargedSw(form){
					  var f= form;
					  f.method="post";
					  f.action="${pageContext.request.contextPath}/web/work/regChargedSwPage.do";
					  f.submit();
				  }
				  function regFreeSw(form){
					  var f= form;
					  f.method="post";
					  f.action="${pageContext.request.contextPath}/web/work/regFreeSw.do";
					  f.submit();
				  }
				
				
			      google.charts.setOnLoadCallback(drawTable);
			
			      function drawTable() {
			        var data = new google.visualization.DataTable();
			        data.addColumn('string', '계정');
			        data.addColumn('string', '소프트웨어명');
			        data.addColumn('string', '');
			        data.addColumn('string', '');
			        
			        data.addRows([
						<% 
						if(swList!=null && swList.size()!=0){
							for(int i=0; i<swList.size(); i++){
						%>
								[
								 '<%=swList.get(i).getUserId()%>',
								 '<%=swList.get(i).getSwName()%>',
								 '<form>'+
								 '<input type="button" value="유료등록" onclick="regChargedSw(this.form)">'+
								 '<input type="hidden" name="swName" value="<%=swList.get(i).getSwName()%>">'+
								 '<input type="hidden" name="swFile" value="<%=swList.get(i).getSwFile()%>">'+
								 '</form>',
								 '<form>'+
								 '<input type="button" value="제외" onclick="regFreeSw(this.form)">'+
								 '<input type="hidden" name="swName" value="<%=swList.get(i).getSwName()%>">'+
								 '<input type="hidden" name="swFile" value="<%=swList.get(i).getSwFile()%>">'+
								 '</form>'
								]
						<%
								if(i!=(swList.size()-1)){
						%>
								,
								 
						<%
								}
							}
						}
						%>
			        ]);
			 
			        var table = new google.visualization.Table(document.getElementById('table_div2'));
			
			        table.draw(data, {showRowNumber: false, width: '100%', height: '100%',allowHtml:true});
			      }
			    </script>
			    <span id="spanTitle">최근 등록  미분류 소프트웨어</span>
			    &nbsp;<a href="${pageContext.request.contextPath}/web/work/regChargedSwPage.do?manual=true">직접등록</a>
			    <div id="table_div2"></div>
			</div>
		</div>
	 </div>
	<div id="bot">
		Copyright &uml; <b>Msoft</b>, All rights reserved.
	</div>
      	
    	
</body>
</html>