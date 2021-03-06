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
	SoftwareInfoVO softwareInfo= (SoftwareInfoVO)session.getAttribute("softwareInfo");
	String modifyMode= (String)session.getAttribute("modifyMode");
	
	if(softwareInfo == null) response.sendRedirect(PMDUtil.PMD_URL);
	
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

<body id="mimin" class="dashboard" onload="servletMessage()">
<input type="hidden" name="servletMessage" value= "${servletMessage}"/>

	<div id="progressDiv" style="background:'#000000'; filter:alpha(opacity:'60'); background:rgba(0,0,0,0.6); width:100%; height:100%; z-index: 1000; display:none; position:absolute;">
		<img src="${pageContext.request.contextPath}/asset/images/loading_gif.gif" style="position:absolute; width:400px; height:300px; border:0; top:50%; left:50%; margin:-200px 0 0 -150px;">
	</div>
 
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
                              <h4>보유 소프트웨어 정보 수정</h4>
                            </div>
                            <div class="panel-body">
                              <form name="estimateForm">
                              	<div style="margin-bottom:10px;">
								<input type="button" class="btn btn-primary" value="수정" onclick="doModify(this.form)"> 
								<input type="button" class="btn btn-primary" value="취소" onclick="javascript:history.back()">
								</div>
								제품명 :
								<input type="text" name="swName" class="form-control input-sm" value="<%=softwareInfo.getSwName() %>" readonly="readonly">
								<input type="text" class="form-control" placeholder="만료일자를 입력하세요." id="dp1" name="ownExpDate" 
									readonly="readonly" value="<%=softwareInfo.getOwnExpDate() %>" style="margin-top:10px;">	 	
										<input type="checkbox" name="permanent" value=""
											<%if(softwareInfo.getOwnExpDate() == null && modifyMode.equals("own")){%> checked="checked"<%} %>> 영구 라이센스
								보유 수량 :
								<input id="onlyNumber" type="text" name="ownQuantity" class="form-control input-sm" value="<%=softwareInfo.getOwnQuantity()%>"
									style="ime-mode:disabled;">
								<input type="hidden" name="ownSer" value="<%=softwareInfo.getOwnSer()%>">
								<input type="hidden" name="swVendor" value="<%=softwareInfo.getSwVendor()%>">
								<input type="hidden" name="modifyMode" value="<%=modifyMode%>">
								 
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
    <script src="${pageContext.request.contextPath}/asset/js/bootstrap-datepicker.js"></script>
     <script src="${pageContext.request.contextPath}/asset/js/main.js"></script>
     <script>
     
     $("#onlyNumber").keyup(function(event){
    	    var inputVal = $(this).val();
    	    $(this).val(inputVal.replace(/[^0-9]/gi,''));
    	});
     
     function servletMessage(){
	 		var v=document.getElementsByName("servletMessage");
	 		
	 		if(v[0].value != ""){
	 			alert(v[0].value);
	 			v[0].value="";	
	 			window.location.href="${pageContext.request.contextPath}/web/info/manage.do";
	 		}
	 	}
     
     $(function(){
    		window.prettyPrint && prettyPrint();
    		$('#dp1').datepicker({
    			format: 'yyyy-mm-dd',
    			startDate: '<%=softwareInfo.getOwnExpDate() %>'
    		});	
    	});
     
     function doModify(f){
    	 if(f.ownQuantity.value == null || f.ownQuantity.value == ""){
    	 	alert("수량을 입력해주세요.");	 
    	 	return false;
    	 }
    	 showProgress();
    	 if(!confirm("수정하시겠습니까?")){
    		 return false;
    	 }else{
    		 f.method="POST";
    		 f.action="${pageContext.request.contextPath}/web/info/modify.do";
    		 
    		 f.submit();
    		
    	 }
     }
     
     function showProgress(){
    	 var pdiv= document.getElementById("progressDiv");
    	 pdiv.style.display="block";
     }
     function hideProgress(){
    	 var pdiv= document.getElementById("progressDiv");
    	 pdiv.style.display="none";
     }
     
	</script>
     
  <!-- end: Javascript -->
  </body>
</html>