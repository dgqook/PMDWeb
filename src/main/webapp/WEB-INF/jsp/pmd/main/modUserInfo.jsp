<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="pmd.common.vo.UserInfoVO" %>
<%@ page import="pmd.common.common.PMDUtil" %>
<%
	UserInfoVO userInfo= (UserInfoVO)session.getAttribute("userInfo");
	
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
  
  <style type="text/css">
  input{
  	margin-bottom:10px;
  }
  </style>
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
                            
                            
                              <form name="userInfoForm">
								
								아이디/이메일 변경에 관해서는 hk@msoft.co.kr로 문의 바랍니다.<br><br>
								
									아이디 <span class="dupMsg" id="idchk"></span>
									<input class="form-control" type="text" id="userId" name="userId" placeholder=" 5-12자리의 영문으로 시작하는 영문 대/소문자 및 숫자"
									 		value="<%=userInfo.getUserId()%>" readonly="readonly">
									<input type="hidden" id="idChecked" name="idChecked">
								
								
									이메일 <span class="dupMsg" id="emailchk"></span>
									<input class="form-control" type="text" id="userEmail" value="<%=userInfo.getUserEmail()%>" 
										name="userEmail" placeholder="ex) example@domain.etc (타 계정 중복 불가)" readonly="readonly">
									<input type="hidden" id="emailChecked" name="emailChecked" value="">
								
								
								
									* 비밀번호
									<input class="form-control" type="password" name="userPw" placeholder=" 6-20자리의 영문 대/소문자/숫자/특수문자(_@./#&+-)">
								
									* 이름
									<input class="form-control" type="text" name="userName" value="<%=userInfo.getUserName()%>" placeholder="한글 및 영문 대/소문자">
								
								
							
									* 회사명 
									<input class="form-control" type="text" name="userCoName" value="<%=userInfo.getUserCoName()%>" placeholder="한글 및 영문 대/소문자, 특수문자 ()_">
								
									<!-- 주소 -->
									* 주소 <input style="margin-bottom:10px;" type="button" id="findZipButton" name="findZipButton" class="btn btn-primary" onclick="sample6_execDaumPostcode()" value="우편번호 찾기">
									<input class="form-control" type="text" id="sample6_postcode" placeholder="우편번호" name="userCoZip" readonly="readonly"  tabindex="-1">
									
									<input class="form-control" type="text" id="sample6_address" placeholder="주소" name="userCoAddr1" readonly="readonly" tabindex="-1">
										<!-- 도로명(새주소):R, 지번(옛주소):J -->
									<input class="form-control" type="text" id="sample6_address2" placeholder="상세주소" name="userCoAddr2">
									<input type="hidden" id="sample6_address_sys" name="userCoAddrSys" value="">
									<!-- !주소 -->
								
									* 유선전화
									<input class="form-control" type="text" placeholder="ex) 02-123-1234" name="userTel" value="<%=userInfo.getUserTel()%>">
								
									* 휴대전화
									<input class="form-control" type="text" placeholder="ex) 010-1234-1234" name="userHp" value="<%=userInfo.getUserHp()%>">
								
							
								<div class="btn btn-primary" id="submitButton" onClick="modInfo()">정보수정</div>
								<div class="btn btn-primary" id="cancelButton" onClick="javascript:history.back()">취소</div> 
							
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
	
	<!-- 주소검색 -->
	<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
	<!-- !주소검색 -->

    <!-- custom -->
     <script src="${pageContext.request.contextPath}/asset/js/main.js"></script>
     <script>
     function servletMessage(){
			var v=document.getElementsByName("servletMessage");
			if(v[0].value != ""){
				alert(v[0].value);
				v[0].value="";
			}
		}
     
     function modInfo(){
			var f= document.getElementsByName("userInfoForm");
			
			if(blankCheck()){
				f[0].method="post";
				f[0].action="http://pmdc.kr/web/main/modUserInfo.do";
				f[0].submit();
			}
		}
     
   //주소
	    function sample6_execDaumPostcode() {
	        new daum.Postcode({
	            oncomplete: function(data) {
	                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
	
	                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
	                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
	                var fullAddr = ''; // 최종 주소 변수
	                var extraAddr = ''; // 조합형 주소 변수
	
	                // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
	                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
	                    fullAddr = data.roadAddress;
	
	                } else { // 사용자가 지번 주소를 선택했을 경우(J)
	                    fullAddr = data.jibunAddress;
	                }
	
	                // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
	                if(data.userSelectedType === 'R'){
	                    //법정동명이 있을 경우 추가한다.
	                    if(data.bname !== ''){
	                        extraAddr += data.bname;
	                    }
	                    // 건물명이 있을 경우 추가한다.
	                    if(data.buildingName !== ''){
	                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
	                    }
	                    // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
	                    fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
	                }
	
	                // 우편번호와 주소 정보를 해당 필드에 넣는다.
	                document.getElementById('sample6_postcode').value = data.zonecode; //5자리 새우편번호 사용
	                document.getElementById('sample6_address').value = fullAddr;
					document.getElementById('sample6_address_sys').value = data.userSelectedType;
	                // 커서를 상세주소 필드로 이동한다.
	                document.getElementById('sample6_address2').focus();
	            }
	        }).open();
	    }
	    //--주소
	    
	    function blankCheck(){
	    	f= document.getElementsByName("userInfoForm");
	    		    	
	    	if(f[0].userPw.value==""){
	    		alert("비밀번호를 입력해주세요.");
	    		f[0].userPw.focus();
	    		return false;
	    	}
	    	
	    	if(f[0].userName.value==""){
	    		alert("이름을 입력해주세요.");
	    		f[0].userName.focus();
	    		return false;
	    	}
	    	
	    	if(f[0].userCoName.value==""){
	    		alert("회사명을 입력해주세요.");
	    		f[0].userCoName.focus();
	    		return false;
	    	}
	    	
	    	if(f[0].userCoZip.value==""){
	    		alert("주소를 입력해주세요.");
	    		f[0].findZipButton.focus();
	    		return false;
	    	}
	    	
	    	if(f[0].userCoAddr2.value==""){
	    		alert("상세주소를 입력해주세요.");
	    		f[0].userCoAddr2.focus();
	    		return false;
	    	}
	    	
	    
	    	if(f[0].userTel.value==""){
	    		alert("유선전화 번호를 입력해주세요.");
	    		f[0].userTel.focus();
	    		return false;
	    	}
	    	
	    	if(f[0].userHp.value==""){
	    		alert("휴대전화 번호를 입력해주세요.");
	    		f[0].userHp.focus();
	    		return false;
	    	}
	    	
	    	return true;
	    	
	    }
	</script>
     
  <!-- end: Javascript -->
  </body>
</html>