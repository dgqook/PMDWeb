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
      <!-- start: Header -->
        <jsp:include page="header.jsp" flush="false"/>
      <!-- end: Header -->

      <div class="container-fluid mimin-wrapper">
  
          <!-- start:Left Menu -->
            <jsp:include page="leftMenu.jsp" flush="false"/>
          <!-- end: Left Menu -->

          <!-- start:content -->
          <div id="content">
          <div class="col-md-12 padding-0" style="margin-top:20px;">
            <div class="col-md-12 padding-0">
                <div class="col-md-3 padding-0">
                   <div class="col-md-12 padding-0">
                      
                      
                      <div class="col-md-12" id="defaultChartSize">
                        <div class="panel chart-title">
                          <h3><span class="fa fa-table"></span> 소프트웨어 현황</h3>
                        </div>
                      
                        <div class="panel">
                           <div class="panel-heading-white panel-heading text-center">
                              <h4>사용자 목록</h4>
                            </div>
                            <div class="panel-body">
                              <div id="table_div1" style="margin:0 auto;"></div>
                            </div>
                        </div>
                      </div>
                      
                      
					</div>
               		  
           		  </div>
           		  <div class="col-md-9 padding-0">
               		  <div class="col-md-12">
               		  
               		  
                        <div class="panel">
                           <div class="panel-heading-white panel-heading text-center">
                              <h4>사용 현황</h4>
                            </div>
                            <div class="panel-body">
                              <div id="table_div2" style="margin:0 auto;"></div>
                            </div>
                        </div>
                     
                      
                      
                      
                    </div>
       			</div>
   		  	
   		  	</div>
   		  	</div>
      </div>
          <!-- end: content -->


          
      
      </div>

      <!-- start: Mobile -->
      <jsp:include page="mobileMenu.jsp" flush="false"/>
       <!-- end: Mobile -->


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
     
  <!-- end: Javascript -->
  </body>
</html>