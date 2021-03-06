<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/asset/css/plugins/simple-line-icons.css"/>
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

    <body id="mimin" class="form-signin-wrapper" onload="servletMessage()">
		<input type="hidden" name="servletMessage" value= "${servletMessage}"/>
      <div class="container">

        <form name="findAccountForm" class="form-signin">
          <div class="panel periodic-login">
              <div class="panel-body text-center">
                  <img src="${pageContext.request.contextPath}/asset/images/PMDLogo_invert.png"/>
                  <br/>
                  <i class="icons icon-arrow-down"></i>
                  <div class="form-group form-animate-text" style="margin-top:40px !important;">
                    <input name="userEmail" type="text" class="form-text" required>
                    <span class="bar"></span>
                    <label>Email 주소</label>
                    <p>가입 시 입력한 메일주소를 입력해주세요.</p>
                  </div>
                  <div id="recaptcha" class="g-recaptcha" data-sitekey="6Ldu2RoTAAAAAExhqpUhtFfWxd_qZ4cHacnn8a9f" data-theme="dark" 
                  	style="transform:scale(0.80);-webkit-transform:scale(0.80);transform-origin:0 0;-webkit-transform-origin:0 0;"></div>

                  <input type="button" class="btn col-md-12" value="메일 발송" onclick="submitFindAccount()"/>
                  
              </div>
                <div class="text-center" style="padding:5px;">
                    <a href="${pageContext.request.contextPath}/web/main/index.do">로그인 화면으로</a>
                </div>
          </div>
        </form>

      </div>

      <!-- end: Content -->
      <!-- start: Javascript -->
      <script src="${pageContext.request.contextPath}/asset/js/jquery.min.js"></script>
      <script src="${pageContext.request.contextPath}/asset/js/jquery.ui.min.js"></script>
      <script src="${pageContext.request.contextPath}/asset/js/bootstrap.min.js"></script>
      
      <script src="${pageContext.request.contextPath}/asset/js/plugins/moment.min.js"></script>
      <script src="${pageContext.request.contextPath}/asset/js/plugins/icheck.min.js"></script>
      <script src="${pageContext.request.contextPath}/asset/js/plugins/jquery.nicescroll.js"></script>
      <script src="https://www.google.com/recaptcha/api.js"></script>
	
      
      <!-- custom -->
      <script src="${pageContext.request.contextPath}/asset/js/main.js"></script>
      <script type="text/javascript">
       $(document).ready(function(){
         
       });
       
      
       
       jQuery.fn.center = function () {
	          this.css("position","absolute");
	          this.css("top", Math.max(0, (($(window).height() - $(this).outerHeight())*37 / 100) + $(window).scrollTop()) + "px");
	          this.css("left", Math.max(0, (($(window).width() - $(this).outerWidth()) / 2) + $(window).scrollLeft()) + "px");
	          return this;
	      }
	      
	      showPopup = function() {
	  	   	$("#popLayer").show();
	  	   	$("#popLayer").center();
	     	}
	      
	      hidePopup = function() {
	  	   	$("#popLayer").hide();
	     	}
	      
	      function clean(t){
	      	t.value="";
	      }
	      function fill(t,text){
	      	if(t.value=="") t.value=text;
	      }
	      function submitFindAccount(){
	      	var f= document.getElementsByName("findAccountForm");
	      	
	      	if(f[0].userEmail.value == "이메일 주소를 입력하세요." || f[0].userEmail.value == "") {
	      		alert("이메일 주소를 입력하세요.");
	      		return false;
	      	}
	      	f[0].method="post";
	  		f[0].action="<c:url value='/web/main/findAccount.do'/>";
	  		f[0].submit();
	      }
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