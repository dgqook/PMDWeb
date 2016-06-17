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
  <link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" />
  <link href="${pageContext.request.contextPath}/asset/css/datepicker.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/asset/css/style.css" rel="stylesheet">
  
  <!-- end: Css -->

  <link rel="shortcut icon" href="${pageContext.request.contextPath}/asset/img/logomi.png">
  <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
      <![endif]-->
</head>

<body id="mimin" class="dashboard"  onload="servletMessage()">
<input type="hidden" name="servletMessage" value= "${servletMessage}"/>
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
            
            	<form name="estimateForm">
            
		                <div class="col-md-4 padding-0">
		                   
		                      
		                      
		                      <div class="col-md-12" id="defaultChartSize">
		                        <div class="panel chart-title">
		                          <h3><span class="fa fa-cog"></span> 신규 제품 견적</h3>
		                        </div>
		                      
		                        <div class="panel">
		                           <div class="panel-heading-white panel-heading text-center">
		                              <h4>검색 조건</h4>
		                            </div>
		                            <div class="panel-body">
		                            
		                            
		                                <input class="form-control" type="text" name="searchKeyword" 
												placeholder="검색어를 입력하세요.(enter)" onkeydown="doSearch(this.form, event)">
										<!-- <input id="datePicker" class="form-control" type="text" name="expiryDate" 
												placeholder="만료일자를 입력하세요." readonly="readonly" style="margin-top:10px;"> 
											 -->
										<input type="text" class="form-control" placeholder="만료일자를 입력하세요." id="dp1" name="expiryDate" readonly="readonly" style="margin-top:10px;">	 	
										<input type="checkbox" name="permanent" value=""> 영구 라이센스
										<input class="form-control" type="text" name="quantity"
												placeholder="수량을 입력하세요." onkeyup="this.value=number_filter(this.value);" 
												 style="margin-top:10px;">
									
										<input type="checkbox"  id="hiding" name="chk" value="" style="width:0px; height:0px;">
										
										<div style="margin:0px auto; text-align: center;">
											<input type="button" class="btn btn-primary" onclick="swEstimate()" value="신청">
											<input type="button" class="btn btn-primary" onclick="goManage()" value="취소">
										</div>
									
		                            </div>
		                        </div>
		                      </div>
		                      
		                      
							
		               		  
		           		  </div>
		           		  <div class="col-md-8 padding-0">
		           		  	<div class="col-md-12">
			           		  	<div class="panel">
			           		  		<div class="panel-heading-white panel-heading text-center">
			                              <h4>검색 목록</h4>
			                            </div>
			           		  	 	<div class="panel-body">
			                        	<div id="table_div"></div>
			                        </div>
		                        </div>
		           		  	</div>
		           		  </div>
		           		  
   		  		</form>
   		  		
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
    <script src="${pageContext.request.contextPath}/asset/js/bootstrap-datepicker.js"></script>
     <script src="${pageContext.request.contextPath}/asset/js/main.js"></script>
     <script>
     function servletMessage(){
 		var v=document.getElementsByName("servletMessage");
 		
 		if(v[0].value != ""){
 			if(!confirm(v[0].value)){
 				window.location.href="${pageContext.request.contextPath}/web/info/manage.do";
 			}
 			v[0].value="";	
 		}
 	}
     
     function goSummary(){
		 window.location.href='${pageContext.request.contextPath}/web/info/summary.do';
	 }
	 function goPresent(){
	 	window.location.href='${pageContext.request.contextPath}/web/info/present.do';
	 }
	 function goManage(){
	 	window.location.href='${pageContext.request.contextPath}/web/info/manage.do';
	 }
		
		
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
      
      function doSearch(f, evt){
    		var keyCode = evt.which?evt.which:event.keyCode;
    		var f= document.getElementsByName("estimateForm");
    		if(keyCode==13){
    			if(f[0].searchKeyword.value == null || f[0].searchKeyword.value == ""){
    				alert("검색어를 입력하세요.");
    				f[0].searchKeyword.focus();
    				return false;
    			}
    			f[0].method="post";
		    	    f[0].action="${pageContext.request.contextPath}/web/info/estimatePage.do";
		    	    f[0].submit();
    		}
    	}
/*
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
*/

if (top.location != location) {
    top.location.href = document.location.href ;
  }

$(function(){
	window.prettyPrint && prettyPrint();
	$('#dp1').datepicker({
		format: 'yy-mm-dd'
	});	
});
                          
                          
    	function swEstimate(){
    	  var f= document.getElementsByName("estimateForm");
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
    	  f[0].action="${pageContext.request.contextPath}/web/info/estimate.do";
    	  f[0].submit();
      }
    	
    	
    	function number_filter(str_value){
    		return str_value.replace(/[^0-9]/gi, ""); 
    	}
	</script>
     
  <!-- end: Javascript -->
  </body>
</html>