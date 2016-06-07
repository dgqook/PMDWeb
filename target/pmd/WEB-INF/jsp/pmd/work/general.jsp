<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="pmd.common.vo.UserInfoVO" %>
<%@ page import="pmd.common.vo.WorkDataVO" %>
<%@ page import="pmd.common.common.PMDUtil" %>
<%@ page import="java.util.ArrayList" %>
<%	
	UserInfoVO userInfo= (UserInfoVO)session.getAttribute("userInfo");
	String nowDate= (String) session.getAttribute("nowDate");

	if(userInfo == null || nowDate == null) response.sendRedirect(PMDUtil.PMD_URL);
	@SuppressWarnings("unchecked")
	ArrayList<WorkDataVO> workDataList= (ArrayList<WorkDataVO>)session.getAttribute("workDataList");
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
	
	<!-- 제이쿼리 폼 플러그인 -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.form/3.51/jquery.form.min.js"></script>
	<!-- !제이쿼리 폼 플러그인 -->
	
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
			padding-top:165px;
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
        
        #searchBar {
			padding-top: 20px;
			padding-bottom:20px;
			margin-bottom:10px;
			border-bottom: 1px solid #7F9A67;
			background-color: white;
		}
		
		#table_div{
			width:1450px;
			margin-top:20px;
			margin-left:20px;
			margin-right:20px;
			margin-bottom:20px;
			height:700px; 
			margin:0 auto; 
			text-align:center;
		}
		#searchForm{
			display: inline-block;
		}
		#fileForm{
			display: inline-block; 
			margin-left:50px;
		}
		#excelFile{
			display: inline-block; 
			width:200px;
		}
		#excelBtn{
			display: inline-block; 
		}
		
		.inlineForms{
			display: inline-block;
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
		
		<div id="searchBar">
			    <div id="searchForm">
			    	<script type="text/javascript">
			    	function doSearch1(f, evt){
                  		var keyCode = evt.which?evt.which:event.keyCode;
                  		var f= document.getElementsByName("registerForm1");
                  		if(keyCode==13){
                  			if(f[0].searchKeyword.value == null || f[0].searchKeyword.value == ""){
                  				alert("검색어를 입력하세요.");
                  				f[0].searchKeyword.focus();
                  				return false;
                  			}
                  			f[0].method="post";
  	  			    	    f[0].action="http://pmdc.kr/web/work/generalFilter.do";
  	  			    	    f[0].submit();
                  		}
                  	}
			    	function doSearch2(f, evt){
                  		var keyCode = evt.which?evt.which:event.keyCode;
                  		var f= document.getElementsByName("registerForm2");
                  		if(keyCode==13){
                  			if(f[0].searchKeyword.value == null || f[0].searchKeyword.value == ""){
                  				alert("검색어를 입력하세요.");
                  				f[0].searchKeyword.focus();
                  				return false;
                  			}
                  			f[0].method="post";
  	  			    	    f[0].action="http://pmdc.kr/web/work/generalFilter.do";
  	  			    	    f[0].submit();
                  		}
                  	}
			    	
			    	function clearForm(f){
			    		f.value= "";
			    	}
	                </script>
			    	<form name="registerForm1" class="inlineForms">
					   	업체명 : 
					    <input class="textBox" type="text" name="searchKeyword" 
										placeholder="검색어를 입력하세요.(enter)" onkeydown="doSearch1(this.form, event)"
										value="${companyKeyword}" onclick="clearForm(this)">
						<input type="hidden" name="searchType" value="company"/>
					</form>
					<form name="registerForm2" class="inlineForms">
					   	대표자 : 
					    <input class="textBox" type="text" name="searchKeyword" 
										placeholder="검색어를 입력하세요.(enter)" onkeydown="doSearch2(this.form, event)"
										value="${ownerKeyword}" onclick="clearForm(this)">
						<input type="hidden" name="searchType" value="owner"/>
					</form>
				</div>
				
				<div id="fileForm">
					<script>  
						function checkFileType(filePath){
							var fileFormat = filePath.split(".");
							if(fileFormat.indexOf("xls") > -1){
							    return true;
							}else if(fileFormat.indexOf("xlsx") > -1){
							    return true;
							}else{
							    return false;
							}
						}
					
						function check(){
					          var formData = new FormData();
					        	  
				        	  var file = $("#excelFile").val();
				        	    if(file == "" || file == null){
				        	        alert("파일을 선택해주세요.");
				        	        return false;
				        	    }else if(!checkFileType(file)){
				        	        alert("엑셀 파일만 업로드 해주세요.");
				        	        return false;
				        	    }
								  
				        	    if(confirm("업로드 하시겠습니까?")){
				        	        $("#ajaxform").attr("action", "http://pmdc.kr/web/work/excelUpload.do");
				        	   		var options = {
				        	   			success : function(data) {
				        	   				alert("모든 데이터가 업로드 되었습니다.");
				        	   				$("#ajax-content").html(data);
				        	   			},
				        	   			type : "POST"
				        	   		};
				        	   		$('#ajaxform').ajaxSubmit(options);
				        	    }
					          
						};
					</script>  
					<form id="ajaxform" role="form" action="" method="post" enctype="multipart/form-data">
							<input id="excelFile" type="file" name="excelFile" />
							<input id="excelBtn" type="button" value="엑셀 업로드" onclick="check()"/>
					</form>
				
				</div>
			</div>
	</div>
	<div id="mid">
		<div id="mid_wrap">
			<script type="text/javascript">
		      google.charts.load('current', {'packages':['table']});
		      google.charts.setOnLoadCallback(drawTable);
		
		      function drawTable() {
		        var data = new google.visualization.DataTable();
		        data.addColumn('string', '<span style="width:70px;">유형</span>');
		        data.addColumn('string', '<span style="width:60px;">날짜</span>');
		        data.addColumn('string', '업체명');
		        data.addColumn('string', '대표자');
		        data.addColumn('string', '주소');
		        data.addColumn('string', '제품명');
		        data.addColumn('string', '버전');
		        data.addColumn('string', '수량');
		        data.addColumn('string', '라이센스');
		        data.addColumn('string', '셀러');
		        data.addColumn('string', '일련번호');
		        
		        data.addRows([
					<% 
					if(workDataList!=null && workDataList.size()!=0){
						for(int i=0; i<workDataList.size(); i++){
					%>
					[
					 '<%=workDataList.get(i).getType()%>',
					 '<%=workDataList.get(i).getDate()%>',
					 '<%=workDataList.get(i).getCompany()%>',
					 '<%=workDataList.get(i).getOwner()%>',
					 '<%=workDataList.get(i).getAddress()%>',
					 '<%=workDataList.get(i).getProductname()%>',
					 '<%=workDataList.get(i).getVersion()%>',
					 '<%=workDataList.get(i).getNumber()%>',
					 '<%=workDataList.get(i).getLicense()%>',
					 '<%=workDataList.get(i).getSeller()%>',
					 '<%=workDataList.get(i).getSerial()%>'
					]
					<%
					if(i!=(workDataList.size()-1)){
					%>
					,
						 
					<%
						}}}
					%>
		        ]);
		
		        var table = new google.visualization.Table(document.getElementById('table_div'));
		
		        table.draw(data, {showRowNumber: false, width: '100%', height: '100%',allowHtml:true});
		      }
		    </script>
		    
				
			<div id="table_div"></div>
			
		</div>
	 </div>
	<div id="bot">
		Copyright &uml; <b>Msoft</b>, All rights reserved.
	</div>
      	
    	
</body>
</html>