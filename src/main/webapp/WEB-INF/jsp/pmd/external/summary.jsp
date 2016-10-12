<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="pmd.common.vo.Chart1VO" %>
<%@ page import="pmd.common.vo.Chart2VO" %>
<%@ page import="pmd.common.vo.Chart3VO" %>
<%@ page import="pmd.common.vo.UserInfoVO" %>
<%@ page import="pmd.common.common.PMDUtil" %>
<%
    @SuppressWarnings("unchecked")
    ArrayList<Chart1VO> chart1= (ArrayList<Chart1VO>)session.getAttribute("chart1");
    @SuppressWarnings("unchecked")
    ArrayList<Chart2VO> chart2= (ArrayList<Chart2VO>)session.getAttribute("chart2");
    @SuppressWarnings("unchecked")
    ArrayList<Chart3VO> chart3= (ArrayList<Chart3VO>)session.getAttribute("chart3");
    
    
%>
   
<!DOCTYPE html>
<html lang="ko">
<head>
  
  <meta charset="utf-8">
  <meta name="description" content="Miminium Admin Template v.1">
  <meta name="author" content="Isna Nur Azis">
  <meta name="keyword" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>PMD Software Asset Management</title>

  <!-- start: Css -->
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/asset/css/bootstrap.min.css">

  <!-- plugins -->
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/asset/css/plugins/font-awesome.min.css"/>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/asset/css/plugins/animate.min.css"/>
  <link href="${pageContext.request.contextPath}/asset/css/style.css" rel="stylesheet">
  <!-- end: Css -->

  <link rel="shortcut icon" href="${pageContext.request.contextPath}/asset/img/logomi.png">
  <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
      <![endif]-->
</head>

<body id="mimin" class="dashboard">
      <div class="container-fluid mimin-wrapper">
  

          <!-- start:content -->
          <div id="content" style="padding:0px; margin:0 auto;">
          <div class="col-md-12 padding-0">
            <div class="col-md-12 padding-0">
                                     
                      
                   <div class="panel" id="defaultChartSize">
                        <div class="panel-heading-white panel-heading text-center">
                           <h4>소프트웨어 사용 정보</h4>
                         </div>
                         <div class="panel-body">
                           <div id="chart_div" style="margin:0 auto;"></div>
                         </div>
                     </div>
                      
                      
                      
					<div class="panel">
                           <div class="panel-heading-white panel-heading text-center">
                              <h4>사용 중인 소프트웨어</h4>
                            </div>
                            <div class="panel-body">
                              <div id="donutchart" style="margin:0 auto;"></div>
                            </div>
                        </div>
                     
                      <div class="panel" style="margin:0 auto;">
                         <div class="panel-heading-white panel-heading text-center">
                            <h4>정보 업데이트 내역</h4>
                          </div>
                          <div class="panel-body">
                            <div id="table_div" style="margin:0 auto;"></div>
                          </div>
                      </div>
                      
                      
                    </div>
       			</div>
   		  	
   		  	</div>
   		  	</div>
      </div>
          <!-- end: content -->


          
      
      </div>



<!-- end: Content -->





<!-- start: Javascript -->
<script src="${pageContext.request.contextPath}/asset/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/asset/js/jquery.ui.min.js"></script>
<script src="${pageContext.request.contextPath}/asset/js/bootstrap.min.js"></script>


    
    <!-- plugins -->
    <script src="${pageContext.request.contextPath}/asset/js/plugins/moment.min.js"></script>
    <script src="${pageContext.request.contextPath}/asset/js/plugins/chart.min.js"></script>
    <script src="${pageContext.request.contextPath}/asset/js/plugins/jquery.nicescroll.js"></script>

	<!-- 구글차트 -->
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	<!-- !구글차트 -->

    <!-- custom -->
     <script src="${pageContext.request.contextPath}/asset/js/main.js"></script>
     <script>
     	// 1번 차트
		google.charts.load('current', {packages: ['corechart', 'bar', 'table']});
		google.charts.setOnLoadCallback(drawAxisTickColors);
		
		$(window).on("resize", function (event) {
			drawAxisTickColors();
		});
		
		function drawAxisTickColors() {
		  // 데이터 입력부
	      var data = google.visualization.arrayToDataTable([
	        ['분류', '정품', '복제', '재고', { role: 'annotation' } ]
	        <%  if(chart1!=null && chart1.size()!=0){
					for(int i=0; i<chart1.size(); i++){
						if(chart1.get(i)==null||chart1.get(i).getSwName()==null||!chart1.get(i).getSwName().equals("")){
			%>
				        ,[	'<%=chart1.get(i).getSwName()%>', 
				        	<%=chart1.get(i).getOwnQuantity()%>, 
				        	<%=chart1.get(i).getCopyQuantity()%>, 
				        	<%=chart1.get(i).getStockQuantity()%>,
				        	'']
	        <%			}
					}
				} else {
	        %>
	        	,['데이터 없음',1,1,1,'']
	        <%	}
	        %>
	      ]);
		  // --데이터 입력부
	      var options = {
			width: document.getElementById("defaultChartSize").style.width,
			height: 30 * <%=session.getAttribute("chart1Count")%>,	// 항목 개수를 따로 전달 받아서 동적으로 높이를 조절할 수 있게, 항목당 80px정도면 될듯
	        chartArea: {top:30, left:100, bottom:10, right:10, width: '100%', height: '90%'}, 
	        legend: { position: 'top', maxLines: 3, alignment: 'start' },
	        bar: { groupWidth: '75%' },
	        isStacked: true,
	        hAxis: {
	          minValue: 0,
	          textStyle: {
	            bold: true,
	            fontSize: 12,
	            color: '#4d4d4d',
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
	            color: '#848484',
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
		
		
		
		// 2번 차트
		google.charts.setOnLoadCallback(drawChart);
		
		$(window).on("resize", function (event) {
			drawChart();
		});
		
	      function drawChart() {
	    	//데이터 부분
	        var data = google.visualization.arrayToDataTable([ 
	          ['Software', 'quantity']
	        <%  if(chart2!=null && chart2.size()!=0){
	          		for(int i=0; i<chart2.size(); i++){
	          			if(chart2.get(i)!=null&&chart2.get(i).getSwName()!=null&&!chart2.get(i).getSwName().equals("")){
	        %>			  ,
				          ['<%=chart2.get(i).getSwName()%>',
				           <%=chart2.get(i).getUsingQuantity()%>]
	        <%			}
        			}
        		} else {
     		%>,['데이터가 없습니다.',1]
     		<%	}
 			%>
	        ]);
			//데이터 부분
	        var options = {
       		  width: document.getElementById("defaultChartSize").style.width,
       		  height: 400,
       		  chartArea: { top:100, left:10, bottom:0, right:10, height: '100%', width: '100%'}, 
	          pieHole: 0.4,
	          legend: { position: 'top', maxLines: 6, alignment:'start'}
	        };
	
	        var chart = new google.visualization.PieChart(document.getElementById('donutchart'));
	        chart.draw(data, options);
	      }
	      
	      
	      
	      // 3번 차트
	      google.charts.setOnLoadCallback(drawTable);
	
	      $(window).on("resize", function (event) {
	    	  drawTable();
			});
	      
	      function drawTable() {
	        var data = new google.visualization.DataTable();
	        data.addColumn('string', '컴퓨터명');
	        data.addColumn('string', '소프트웨어명');
	        data.addColumn('string', '운영체제');
	        data.addColumn('string', 'IP주소');
	        data.addColumn('string', '최종업데이트');
	        
	        //데이터 부분
	        data.addRows([
	        <%
		        if(chart3!=null && chart3.size()!=0){
	          		for(int i=0; i<chart3.size(); i++){
	          			if(chart3.get(i)==null||chart3.get(i).getSwName()==null||!chart3.get(i).getSwName().equals("")){
	        %>
	          ['<%=chart3.get(i).getPcName()%>', '<%=chart3.get(i).getSwName()%>', '<%=chart3.get(i).getPcOs()%>', 
	           	'<%=chart3.get(i).getPcIp()%>', '<%=chart3.get(i).getUpdateDate()%>']  
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
     
  <!-- end: Javascript -->
  </body>
</html>