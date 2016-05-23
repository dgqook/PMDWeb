<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="pmd.common.vo.UserInfoVO" %>
<%@ page import="pmd.common.vo.SoftwareInfoVO" %>
<%@ page import="pmd.common.common.PMDUtil" %>
<%@ page import="java.util.ArrayList" %>
<%	
	UserInfoVO userInfo= (UserInfoVO)session.getAttribute("userInfo");
	String restOfExpiry= (String) session.getAttribute("restOfExpiry");

	if(userInfo == null || restOfExpiry == null) response.sendRedirect(PMDUtil.PMD_URL);
	@SuppressWarnings("unchecked")
	ArrayList<String> nameList= (ArrayList<String>)session.getAttribute("nameList");
	@SuppressWarnings("unchecked")
	ArrayList<SoftwareInfoVO> dataList= (ArrayList<SoftwareInfoVO>)session.getAttribute("dataList");
%>
<!DOCTYPE html>

<html>
<head>
    <meta name="viewport" content="width=device-width" charset="UTF-8"/>
    
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
		
		#present_table{
			width:100%;
			min-height:700px;
			height:100%;
			vertical-align:top;
			border:1px solid #dde4e9;
			margin-top:-1px;
			margin-bottom:-1px;
		}
		#present_table_name{
			width:180px;
			padding-left:20px;
			padding-right:20px;
			
		}
		#table_div1{
			position:absolute;
			margin-top:30px;
			width:140px;
		}
		#table_div2{
			margin-top:30px;
			margin-left:10px;
			margin-right:20px;
			margin-bottom:20px;
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
			window.location.href='${pageContext.request.contextPath}/web/info/summary.do';
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
			<script type="text/javascript">
				google.charts.load('current', {'packages':['table']});
			    google.charts.setOnLoadCallback(drawTable1);
	
			    function drawTable1() {
				    var data = new google.visualization.DataTable();
				    data.addColumn('string', '사용자 목록');
				    data.addRow(['ALL']);
				    <%if(nameList!=null){%>
			    	<%for(int i=0; i<nameList.size(); i++){%>
				    	data.addRow(['<%=nameList.get(i)%>']);
					<%}}%>
				    var table = new google.visualization.Table(document.getElementById('table_div1'));
				    table.draw(data, {sort:'disable', showRowNumber: false, width: '100%', height: '100%'});
				    
				    google.visualization.events.addListener(table, 'select', function(){
				    	var row= table.getSelection()[0].row;
				    	var selectedRow= data.getValue(row, 0);
				    	location.href= "/web/info/present.do?pcName="+selectedRow;
				    });
			    }
			    $(window).scroll(function()
	    	    {  
	    	        $('#table_div1').animate({top:$(window).scrollTop()+150+"px" },{queue: false, duration: 360});    
	    	    });  
	    	    //when the close button at right corner of the message box is clicked   
	    	    $('#table_div1').click(function()  
	    	    {  
	    	        //the messagebox gets scrool down with top property and gets hidden with zero opacity   
	    	        $('#table_div1').animate({ top:"+=15px",opacity:0 }, "slow");  
	    	    });  
	    	    
	    	    /////////////////////////////////////////////////////////////////////////////////////////////////////
	    	    
	    	    google.charts.setOnLoadCallback(drawTable2);
	    	    function drawTable2() {
				    var data = new google.visualization.DataTable();
				    data.addColumn('string', 'PC명');
				    data.addColumn('string', 'OS');
				    data.addColumn('string', '소프트웨어명');
				    data.addColumn('string', '정보업데이트');
				    data.addColumn('string', '비고');
				    <%if(nameList!=null){%>
			    	<%for(SoftwareInfoVO p: dataList){%>
				    	data.addRow(['<%=p.getPcName()%>','<%=p.getPcOs()%>','<%=p.getSwName()%>','<%=p.getUpdateDate()%>','<%=p.getParam1()%>']);
					<%}}%>
				    var table = new google.visualization.Table(document.getElementById('table_div2'));
				    table.draw(data, {sort:'enable', showRowNumber: true, width: '100%', height: '100%', allowHtml: true});
			    }
			</script>
			<table id="present_table" border=1>
				<tr>
					<td valign="top" id="present_table_name"><div id="table_div1"></div></td>
					
					<td valign="top" id="present_table_data"><div id="table_div2"></div></td>
				</tr>
			</table> 
		</div>
	</div>
	<div id="bot">
		Copyright &uml; <b>Msoft</b>, All rights reserved.
	</div>
      	
    	
</body>
</html>