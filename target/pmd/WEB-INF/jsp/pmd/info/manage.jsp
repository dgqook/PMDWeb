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
	ArrayList<SoftwareInfoVO> ownList= (ArrayList<SoftwareInfoVO>)session.getAttribute("ownList");
	
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

<body id="mimin" class="dashboard" onload="servletMessage()">
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
                <div class="col-md-12 padding-0">
                   
                      
                      
                      <div class="col-md-12" id="defaultChartSize">
                        <div class="panel chart-title">
                          <h3><span class="fa fa-cog"></span> 소프트웨어 관리</h3>
                        </div>
                      
                        <div class="panel">
                           <div class="panel-heading-white panel-heading text-center">
                              <h4>보유 소프트웨어 목록</h4>
                            </div>
                            <div class="panel-body">
                              <form name="estimateForm">
                              	<div style="margin-bottom:10px;">
								<input type="button" class="btn btn-primary" onclick="swRegister()" value="제품 등록">
								<input type="button" class="btn btn-primary" onclick="swDelete()" value="선택 삭제">
								<input type="button" class="btn btn-primary" onclick="swRequest()" value="견적 요청">
								</div>
								
								<div id="table_div"></div>
								<input type="checkbox" name="chk" style="width:0px; height:0px;" value="" >
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
	       data.addColumn('string', '선택');
	       data.addColumn('string', '소프트웨어명');
	       data.addColumn('string', '제조사');
	       data.addColumn('string', '보유수량');
	       data.addColumn('string', '잔여일');
	       data.addRows([
	       <% 
	       	if(ownList!=null && ownList.size()!=0){
	       		for(int i=0; i<ownList.size(); i++){
	       %>
				['<label style="width:100%; height:100%;">'+
				 '<input type="checkbox" name="chk" value="<%=ownList.get(i).getOwnSer()%>" class="list_chk"/>'+
				 '</label>',
				 '<%=ownList.get(i).getSwName()%>',  '<%=ownList.get(i).getSwVendor()%>', 
				 '<%=ownList.get(i).getOwnQuantity()%>','<%=ownList.get(i).getOwnExpDate()%>'
				 ]
			<%
				if(i!=(ownList.size()-1)){
			%>
				,
			 	 
			<%
	       		}}}
			%>
	       ]);
	
	       var table = new google.visualization.Table(document.getElementById('table_div'));
	
	       table.draw(data, {showRowNumber: false, width: '100%', height: '100%', allowHtml: 'true'});
	     }
	     
	     function swRegister(){
	   	  window.location.href="${pageContext.request.contextPath}/web/info/registerPage.do?searchKeyword=all";
	     }
	     
	     function swDelete(){
			  var f= document.getElementsByName("estimateForm");
	   	  var message= "";
	   	  var count= 0;
	   	  
	   	  for(var i=0; i<f[0].chk.length; i++){
		    	  if(f[0].chk[i].checked){
					  count++;
				  }
	   	  }
	   	  message+="선택한 소프트웨어 보유 정보를 정말 삭제하시겠습니까?";
	   	  if(count==0){
	   		  alert("삭제할 소프트웨어가 없습니다.");
	   		  return false;
	   	  } else {
	   		  if(!confirm(message)){
	   			  return false;
	   		  }
	   	  }
	   	  
	   	  f[0].method="post";
	   	  f[0].action="${pageContext.request.contextPath}/web/info/delete.do";
	   	  f[0].submit();
	     }
	     
	     function swRequest(){
	   	  var f= document.getElementsByName("estimateForm");
	   	  var message= "";
	   	  var count= 0;
	   	  
	   	  for(var i=0; i<f[0].chk.length; i++){
		    	  if(f[0].chk[i].checked){
					  count++;
				  }
	   	  }
	   	  message+="선택한 소프트웨어의 견적 정보를 요청하시겠습니까?";
	   	  if(count==0){
	   		  alert("선택한 소프트웨어가 없습니다.");
	   		  return false;
	   	  } else {
	   		  if(!confirm(message)){
	   			  return false;
	   		  }
	   	  }
	   	  f[0].method="post";
	   	  f[0].action="${pageContext.request.contextPath}/web/info/request.do";
	   	  f[0].submit();
	     }
	     
	     $(document).ready(function(){
	 		$(".list_menu_item").hover(
	 			function() {
	 		    	$(this).addClass("mouse_over2").siblings().removeClass("mouse_over");
	 			}, 
	 			function() {
	 		    	$(this).removeClass("mouse_over2");
	 			}
	 		);
	 	});
	 		
	 	
	 	function servletMessage(){
	 		var v=document.getElementsByName("servletMessage");
	 		
	 		if(v[0].value != ""){
	 			alert(v[0].value);
	 			v[0].value="";	
	 		}
	 	}
	</script>
     
  <!-- end: Javascript -->
  </body>
</html>