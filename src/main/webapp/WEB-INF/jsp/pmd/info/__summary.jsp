<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="pmd.common.vo.Chart1VO" %>
<%@ page import="pmd.common.vo.Chart2VO" %>
<%@ page import="pmd.common.vo.Chart3VO" %>
<%@ page import="java.lang.Integer" %>
<%
    @SuppressWarnings("unchecked")
    ArrayList<Chart1VO> chart1= (ArrayList<Chart1VO>)session.getAttribute("chart1");
    @SuppressWarnings("unchecked")
    ArrayList<Chart2VO> chart2= (ArrayList<Chart2VO>)session.getAttribute("chart2");
    @SuppressWarnings("unchecked")
    ArrayList<Chart3VO> chart3= (ArrayList<Chart3VO>)session.getAttribute("chart3");
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
				<b><span style="color:#045FB4;">${userInfo.userId}</span></b>님 안녕하세요.
					<a href="${pageContext.request.contextPath}/web/main/myPage.do">정보수정</a>
					<a href="${pageContext.request.contextPath}/web/main/logout.do">로그아웃</a>
					<br>
				${restOfExpiry}
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
		
		<!-- 구글차트 -->
			<script>
				google.charts.load('current', {packages: ['corechart', 'bar', 'table']});
				google.charts.setOnLoadCallback(drawAxisTickColors);
				function drawAxisTickColors() {
					
				  // 데이터 입력부
			      var data = google.visualization.arrayToDataTable([
			        ['분류', '정품 프로그램', '불법 복제 프로그램', '재고 프로그램', { role: 'annotation' } ]
			        <%
				        if(chart1!=null && chart1.size()!=0){
							for(int i=0; i<chart1.size(); i++){
								if(chart1.get(i)==null||chart1.get(i).getSwName()==null||!chart1.get(i).getSwName().equals("")){
					%>
						        ,[	'<%=chart1.get(i).getSwName()%>', 
						        	<%=chart1.get(i).getOwnQuantity()%>, 
						        	<%=chart1.get(i).getCopyQuantity()%>, 
						        	<%=chart1.get(i).getStockQuantity()%>,
						        	'']
			        <%
			        			}
							}
						} else {
			        %>
			        	,['데이터 없음',1,1,1,'']
			        <%
						}
			        %>
			      ]);
				  // --데이터 입력부
				  
				  
			      var options = {
			        title: '소프트웨어 사용 정보',
			        chartArea: {width: '50%'}, 
			        width: 900,
			        height: 50 * <%=session.getAttribute("chart1Count")%>,	// 항목 개수를 따로 전달 받아서 동적으로 높이를 조절할 수 있게, 항목당 80px정도면 될듯
			        legend: { position: 'right', maxLines: 3 },
			        bar: { groupWidth: '75%' },
			        isStacked: true,
			        hAxis: {
			          minValue: 0,
			          textStyle: {
			            bold: true,
			            fontSize: 12,
			            color: '#4d4d4d'
			          },
			          titleTextStyle: {
			            bold: true,
			            fontSize: 18,
			            color: '#4d4d4d'
			          }
			        },
			        vAxis: {
			          textStyle: {
			            fontSize: 14,
			            bold: true,
			            color: '#848484'
			          },
			          titleTextStyle: {
			            fontSize: 14,
			            bold: true,
			            color: '#848484'
			          }
			        }
			      };
			      var chart = new google.visualization.BarChart(document.getElementById('chart_div'));
			      chart.draw(data, options);
			    }
			</script>
		  	
  			<div id="chart_div" style='width:900px;  margin-left:100px;
  										<%if(session.getAttribute("chart1Count")!=null){ %> 
  										<%if((Integer)session.getAttribute("chart1Count")>3){ %>
  										margin-top:-<%=3*(Integer)session.getAttribute("chart1Count")%>px;
  										<%}}%> min-height:200px;'></div>
		<!-- !구글차트 -->
		
		<!-- 구글차트 -->
		<script type="text/javascript">
	      google.charts.setOnLoadCallback(drawChart);
	      function drawChart() {
	    	//데이터 부분
	        var data = google.visualization.arrayToDataTable([ 
	          ['Software', 'quantity']
	        <%
	          	if(chart2!=null && chart2.size()!=0){
	          		for(int i=0; i<chart2.size(); i++){
	          			if(chart2.get(i)!=null&&chart2.get(i).getSwName()!=null&&!chart2.get(i).getSwName().equals("")){
	        %>
				          ,
				          ['<%=chart2.get(i).getSwName()%>',
				           <%=chart2.get(i).getUsingQuantity()%>]
	        <%
	          			}
          			}
          		} else {
       		%>
       		  ,['데이터가 없습니다.',1]
       		<%	
       			}
   			%>
	        ]);
			//데이터 부분
	        var options = {
	          title: '사용 중인 소프트웨어',
	          //is3D: true,
	          pieHole: 0.4,
	        };
	
	        var chart = new google.visualization.PieChart(document.getElementById('donutchart'));
	        chart.draw(data, options);
	      }
	    </script>
	    <div id="donutchart" style="width: 900px; height: 450px; margin-left:100px; 
	    							<%if(session.getAttribute("chart1Count")!=null){ %> 
	    							margin-top:-<%=5*(Integer)session.getAttribute("chart1Count")%>px;
	    							<%}%>"></div>
		<!-- !구글차트 -->
		
		<!-- 구글차트 -->
		<script type="text/javascript">
		  google.charts.setOnLoadCallback(drawTable);
	
	      function drawTable() {
	        var data = new google.visualization.DataTable();
	        data.addColumn('string', '컴퓨터명');
	        data.addColumn('string', 'IP주소');
	        data.addColumn('string', '운영체제');
	        data.addColumn('string', '소프트웨어명');
	        data.addColumn('string', '업데이트');
	        
	        //데이터 부분
	        data.addRows([
	        <%
		        if(chart3!=null && chart3.size()!=0){
	          		for(int i=0; i<chart3.size(); i++){
	          			if(chart3.get(i)==null||chart3.get(i).getSwName()==null||!chart3.get(i).getSwName().equals("")){
	        %>
	          ['<%=chart3.get(i).getPcName()%>', '<%=chart3.get(i).getPcIp()%>', '<%=chart3.get(i).getPcOs()%>', 
	           	'<%=chart3.get(i).getSwName()%>', '<%=chart3.get(i).getUpdateDate()%>']  
			<%
							if((i+1)!=chart3.size()){
								%>,<%
							}
	          			}
	          		}
		        } else {
			%>
				['데이터가 없습니다.','','','','']
			<%
		        }
			%>
	        ]);
			//데이터 부분
	        
	        var table = new google.visualization.Table(document.getElementById('table_div'));
	        table.draw(data, {showRowNumber: false, width: '100%', height: '300px', 'text-align': 'center'});
	      }
	    </script>
	    <div id="table_div" style="width:800px; height:300px; margin:0 auto; text-align:center; margin-top:-20px;"></div>
		<!-- !구글차트 -->
		<br>
		<br>
		</div>
	</div>
	<div id="bot">
		Copyright &uml; <b>Msoft</b>, All rights reserved.
	</div>
      	
    	
</body>
</html>