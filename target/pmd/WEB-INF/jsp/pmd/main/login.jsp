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
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/asset/css/plugins/icheck/skins/flat/aero.css"/>
  	
  <link href="${pageContext.request.contextPath}/asset/css/style.css" rel="stylesheet">
  <!-- end: Css -->

  <link rel="shortcut icon" href="${pageContext.request.contextPath}/asset/img/logomi.png">
  <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]> 
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
      <![endif]-->      
    </head>

    <body id="mimin" class="dashboard form-signin-wrapper" onload="servletMessage_loginPage()">
	<input type="hidden" name="autoLoginSwitch" value= "${autoLogin}"/>
	<input type="hidden" name="servletMessage" value= "${servletMessage}"/>
	
      <div class="container">

        <form name="loginForm" class="form-signin">
          <div class="panel periodic-login">
              <div class="panel-body text-center">
              	  <img src="${pageContext.request.contextPath}/asset/images/PMDLogo_invert.png"/>
                  <!-- <h1 class="atomic-symbol">Coordy</h1> -->
                  <!-- <p class="element-name">코디 소프트웨어 자산관리</p> -->
				  <br/>
                  <i class="icons icon-arrow-down"></i>
                  <div class="form-group form-animate-text" style="margin-top:40px !important;">
                    <input name="userId" type="text" class="form-text" value="${userId}" required>
                    <span class="bar"></span>
                    <label>아이디</label>
                  </div>
                  <div class="form-group form-animate-text" style="margin-top:40px !important;">
                    <input name="userPw" type="password" class="form-text" onkeydown="doKeyLogin_loginPage(this.form, event)" required>
                    <span class="bar"></span>
                    <label>비밀번호</label>
                  </div>
                  <label class="pull-left">
                  <input type="checkbox" class="icheck pull-left" name="saveId" ${saveIdChecked}/> 아이디 저장
                  <input type="checkbox" class="icheck pull-left" name="autoLoginChk"/> 자동 로그인
                  <input type="hidden" name="autoLogin" value="${autoLogin}">
                  </label>
                  <input type="submit" class="btn col-md-12" value="로그인" onclick="doLogin_loginPage(this.form)"/>
              </div>
                <div class="text-center" style="padding:5px;">
                    <a href="${pageContext.request.contextPath}/web/main/clientDownload.do">다운로드 </a>
                    <a href="${pageContext.request.contextPath}/web/main/findPassword.do">| 비밀번호 찾기 </a>
                    <a href="${pageContext.request.contextPath}/web/main/joinPage.do">| 회원가입</a>
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

      <!-- custom -->
      <script src="${pageContext.request.contextPath}/asset/js/main.js"></script>
      <script type="text/javascript">
       $(document).ready(function(){
         $('input').iCheck({
          checkboxClass: 'icheckbox_flat-aero',
          radioClass: 'iradio_flat-aero'
        });
       });
       
       function servletMessage_loginPage(){
   		var v=document.getElementsByName("servletMessage");
   		var auto= document.getElementsByName("autoLoginSwitch");
   		var loginForm= document.getElementsByName("loginForm");
   		
   		if(v[0].value != ""){
   			alert(v[0].value);
   			v[0].value="";	
   		}
   		
   		if(auto[0].value == "auto") {
   			loginForm[0].userId.value= "${userId}";
   			loginForm[0].userPw.value= "${userPw}";
   			loginForm[0].autoLogin.value="auto";
   			loginForm[0].method="post";
   			loginForm[0].action="${pageContext.request.contextPath}/web/main/login.do";
   			loginForm[0].submit();
   		} 
   	}
   	function doLogin_loginPage(f){
   		if(f.userId.value == "" || f.userId.value == null){
   			alert("아이디를 입력하세요");
   			f.userId.focus();
   			return false;
   		}
   		if(f.userPw.value == "" || f.userPw.value == null){
   			alert("비밀번호를 입력하세요");
   			f.userPw.focus();
   			return false;
   		}
   		
   		f.method="post";
   		f.action="${pageContext.request.contextPath}/web/main/login.do";
   		f.submit();
   	}
   	function doKeyLogin_loginPage(f, evt){
   		var keyCode = evt.which?evt.which:event.keyCode;
   		if(keyCode==13){
   			doLogin(f);
   		}
   	}
     </script>
     <!-- end: Javascript -->
   </body>
   </html>