<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="pmd.common.vo.UserInfoVO" %>
<%@ page import="pmd.common.vo.SoftwareInfoVO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="pmd.common.common.PMDUtil" %>
<%	
	UserInfoVO userInfo= (UserInfoVO)session.getAttribute("userInfo");
	String restOfExpiry= (String) session.getAttribute("restOfExpiry");

	if(userInfo == null || restOfExpiry == null) response.sendRedirect(PMDUtil.PMD_URL);
	@SuppressWarnings("unchecked")
	ArrayList<SoftwareInfoVO> chargedList= (ArrayList<SoftwareInfoVO>)session.getAttribute("chargedList");
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
	
	<!-- datePicker -->
	
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" />
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
	<script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>
	<!-- !datePicker -->

	
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
		#table_div{
			width:960px;
			margin-top:20px;
			margin-left:20px;
			margin-right:20px;
			margin-bottom:20px;
			height:600px; 
			margin:0 auto; 
			text-align:center;
		}
		
		#list_menu_bucket{
			padding-top:10px;
			padding-bottom:10px;
			margin:0 auto;
			margin-top:20px;
			margin-left:-20px;
			border-bottom: 1px solid #dde4e9;
			border-top: 1px solid #dde4e9;
			
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
		
		#searchBar {
			margin-top: 20px;
			padding-bottom:20px;
			margin-bottom:10px;
			border-bottom: 1px solid #dde4e9;
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
		
	
	function servletMessage(){
		var v=document.getElementsByName("servletMessage");
		
		if(v[0].value != ""){
			if(!confirm(v[0].value)){
				window.location.href="${pageContext.request.contextPath}/web/info/manage.do";
			}
			v[0].value="";	
		}
	}
	</script>
</head>
<body onload="servletMessage()">
<input type="hidden" name="servletMessage" value= "${servletMessage}"/>
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
			window.location.href='${pageContext.request.contextPath}/web/info/summary.do';
		}
		function goPresent(){
			window.location.href='${pageContext.request.contextPath}/web/info/present.do';
		}
		function goManage(){
			window.location.href='${pageContext.request.contextPath}/web/info/manage.do';
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
			<script type="text/javascript">
		      google.charts.load('current', {'packages':['table']});
		      google.charts.setOnLoadCallback(drawTable);
		
		      function drawTable() {
		        var data = new google.visualization.DataTable();
		        data.addColumn('string', '');
		        data.addColumn('string', '소프트웨어명');
		        data.addColumn('string', '제조사');
		        data.addColumn('string', '버전');
		        
		        data.addRows([
                <% 
                	if(chargedList!=null && chargedList.size()!=0){
                		for(int i=0; i<chargedList.size(); i++){
                %>
					['<label style="width:100%; height:100%;">'+
					 	'<input type="checkbox" name="chk" value="<%=chargedList.get(i).getSwName()%>"'+
					 	' class="list_chk"/> </label>',
					 '<%=chargedList.get(i).getSwName()%>',  '<%=chargedList.get(i).getSwVendor()%>', 
					 '<%=chargedList.get(i).getSwVersion()%>']
				<%
					if(i!=(chargedList.size()-1)){
				%>
					,
				 	 
				<%
                		}}}
				%>
		        ]);
		
		        var table = new google.visualization.Table(document.getElementById('table_div'));
		
		        table.draw(data, {showRowNumber: false, width: '100%', height: '100%', allowHtml: 'true'});
		      }
		      
		     
		    </script>
			<form name="registerForm">
				<div id="searchBar">
					<script type="text/javascript">
	                  	function doSearch(f, evt){
	                  		var keyCode = evt.which?evt.which:event.keyCode;
	                  		var f= document.getElementsByName("registerForm");
	                  		if(keyCode==13){
	                  			if(f[0].searchKeyword.value == null || f[0].searchKeyword.value == ""){
	                  				alert("검색어를 입력하세요.");
	                  				f[0].searchKeyword.focus();
	                  				return false;
	                  			}
	                  			f[0].method="post";
	  	  			    	    f[0].action="${pageContext.request.contextPath}/web/info/registerPage.do";
	  	  			    	    f[0].submit();
	                  		}
	                  	}

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

	                  	function swRegister(){
	  			    	  var f= document.getElementsByName("registerForm");
	  			    	  var count= 0;
	  			    	  
	  			    	  for(var i=0; i<f[0].chk.length; i++){
	  			    		  if(f[0].chk[i].checked){
	  			    			count++;  
	  			    		  }
	  			    	  }
	  			    	  
	  			    	  if(count==0){
	  			    		  alert("제품을 선택해주세요.");
	  			    		  f[0].searchKeyword.focus();
	  			    		  return false;
	  			    	  }
	  			    	  
	  			    	  if(f[0].permanent.checked){
	  			    		  f[0].permanent.value="on";
	  			    	  }else{
	  			    		 f[0].permanent.value="off";
	  			    		  if(f[0].expiryDate.value == null || f[0].expiryDate.value == ""){
	  			    			  alert("만료일자를 입력해주세요.");
	  			    			  f[0].expiryDate.focus();
	  			    			  return false;
	  			    		  }
	  			    	  }
	  			    	  
	  			    	  if(f[0].quantity==null || f[0].quantity.value==""){
	  			    		  alert("수량을 입력해주세요.");
	  			    		  f[0].quantity.focus();
	  			    		  return false;
	  			    	  }
	  			    	  
	  			    	  f[0].method="post";
	  			    	  f[0].action="${pageContext.request.contextPath}/web/info/register.do";
	  			    	  f[0].submit();
	  			      }
	                  	
	                  	function number_filter(str_value){
	                  		return str_value.replace(/[^0-9]/gi, ""); 
	                  	}
	                </script>
	                
	                <span style="color:red; font-size:20px;">*</span>
					<input class="textBox" type="text" name="searchKeyword" 
							placeholder="검색어를 입력하세요.(enter)" onkeydown="doSearch(this.form, event)">
					<span style="color:red; font-size:20px;">*</span>
					<input id="datePicker" class="textBox" type="text" name="expiryDate" 
							placeholder="만료일자를 입력하세요." readonly="readonly"> 
					<input type="checkbox" name="permanent" value=""> 영구 라이센스
					<span style="color:red; font-size:20px; margin-left:20px;">*</span>
					<input class="textBox" type="text" name="quantity"
							placeholder="수량을 입력하세요." onkeyup="this.value=number_filter(this.value);" >
					
				</div>
				<div id="table_div"></div>
				<input type="checkbox"  id="hiding" name="chk" value="" style="width:0px; height:0px;">
				<ul id="list_menu_bucket">
					<li class="list_menu_item" onclick="swRegister()">등록</li>
					<li class="list_menu_item" onclick="goManage()">취소</li>
				</ul>
			</form>
		
		</div>
	</div>
	<div id="bot">
		Copyright &uml; <b>Msoft</b>, All rights reserved.
	</div>
      	
    	
</body>
</html>