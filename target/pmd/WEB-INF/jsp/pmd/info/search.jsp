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
	ArrayList<SoftwareInfoVO> chargedList= (ArrayList<SoftwareInfoVO>)session.getAttribute("chargedList");
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
                <div class="col-md-12 padding-0">
                   
                      
                      
                      <div class="col-md-12" id="defaultChartSize">
                        <div class="panel chart-title">
                          <h3><span class="fa fa-search"></span> 소프트웨어 검색</h3>
                        </div>
                      
                        <div class="panel">
                           <div class="panel-heading-white panel-heading text-center">
                              <h4>유료 소프트웨어 검색</h4>
                            </div>
                            <div class="panel-body">
                              <form name="registerForm">
								<div id="searchBar" style="padding-bottom:10px;">
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
					  	  			    	    f[0].action="${pageContext.request.contextPath}/web/info/search.do";
					  	  			    	    f[0].submit();
					                  		}
					                  	}
				
					                </script>
					                
									<input class="form-control input-sm" type="text" name="searchKeyword" 
											placeholder="검색어를 입력하세요.(enter)" onkeydown="doSearch(this.form, event)">
											
									
								</div>
								<div id="table_div"></div>
							</form>
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
     
  <!-- end: Javascript -->
  </body>
</html>