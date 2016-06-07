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
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/asset/css/plugins/jquery.steps.css"/>
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
        <nav class="navbar navbar-default header navbar-fixed-top">
          <div class="col-md-12 nav-wrapper">
            <div class="navbar-header" style="width:100%;">
              <div class="opener-left-menu is-open">
                <span class="top"></span>
                <span class="middle"></span>
                <span class="bottom"></span>
              </div>
                <a href="http://pmdc.kr" class="navbar-brand"> 
                 <b>PMD</b>
                </a>

			<!-- 
              <ul class="nav navbar-nav navbar-right user-nav">
                <li class="user-name"><span>(사용자이름)님 안녕하세요</span></li>
                  <li class="dropdown avatar-dropdown">
                   <img src="${pageContext.request.contextPath}/asset/img/avatar.jpg" class="img-circle avatar" alt="user name" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true"/>
                   <ul class="dropdown-menu user-dropdown">
                     <li><a href="#"><span class="fa fa-user"></span> 정보수정</a></li>
                     <li><a href="#"><span class="fa fa-power-off"></span> 로그아웃</a></li>
                     <li role="separator" class="divider"></li>
                  </ul>
                </li>
                
              </ul>
               -->
            </div>
          </div>
        </nav>
      <!-- end: Header -->

      <div class="container-fluid mimin-wrapper">
  
          <!-- start:Left Menu -->
            <div id="left-menu">
              <div class="sub-left-menu scroll">
                <ul class="nav nav-list">
                    <li><div class="left-bg"></div></li>
                    <li class="time">
                      <h1 class="animated fadeInLeft">21:00</h1>
                      <p class="animated fadeInRight">Sat,October 1st 2029</p>
                    </li>
                    <li class="active ripple">
                      <a class="tree-toggle nav-header">
                      <span class="fa-home fa"></span> 요약정보 
                        <span class="fa-angle-right fa right-arrow text-right"></span>
                      </a>
                    </li>
                    <li class="ripple">
                      <a class="tree-toggle nav-header">
                        <span class="fa-diamond fa"></span> 소프트웨어 현황
                        <span class="fa-angle-right fa right-arrow text-right"></span>
                      </a>
                    </li>
                    <li class="ripple">
                      <a class="tree-toggle nav-header">
                        <span class="fa-diamond fa"></span> 소프트웨어 관리
                        <span class="fa-angle-right fa right-arrow text-right"></span>
                      </a>
                    </li>
                    <li class="ripple">
                      <a class="tree-toggle nav-header">
                        <span class="fa-area-chart fa"></span> 소프트웨어 검색
                        <span class="fa-angle-right fa right-arrow text-right"></span>
                      </a>
                    </li>
                    <li class="ripple"><a class="tree-toggle nav-header">
                    <span class="fa fa-pencil-square"></span> 다운로드
                    <span class="fa-angle-right fa right-arrow text-right"></span> </a>
                    </li>
                  </ul>
                </div>
            </div>
          <!-- end: Left Menu -->


        <!-- start: Content -->
          <div id="content">
             <div class="panel box-shadow-none content-header">
                  <div class="panel-body">
                    <div class="col-md-12">
                        <h3 class="animated fadeInLeft">회원가입</h3>
                        
                    </div>
                  </div>
              </div>
            <div class="col-md-12">
                <div class="panel">
                    <div class="panel-heading"></div>
                    <div class="panel-body">
                        <form id="joinForm" action="http://pmdc.kr/web/main/createAccount.do">
                        	<h3>가입약관</h3>
                            <fieldset>
                                <h4><b>이용약관</b></h4>
								<textarea cols=30 rows=6 name="agreeText1" style="border-width:1px; border-color:gray; border-style:solid;width:95%; background-color:white;" readonly="readonly" disabled>
									<jsp:include page="_agreement1.jsp"></jsp:include>
								</textarea>
								<label class="agreeTextLabel" >회원가입약관의 내용에 동의합니다. <input type="checkbox" name="agree1" class="required"></label>
								
								<h4><b>개인정보처리방침안내</b></h4>					
								<textarea cols=30 rows=6 name="agreeText2" style="border-width:1px; border-color:gray; border-style:solid;width:95%; background-color:white;" readonly="readonly" disabled>
									<jsp:include page="_agreement2.jsp"></jsp:include>
								</textarea>
								<label class="agreeTextLabel">개인정보처리방침안내의 내용에 동의합니다. <input type="checkbox" name="agree2" class="required"><br></label>
                         
                            </fieldset>
                        
                            <h3>기본 정보</h3>
                            <fieldset>
                                
                                <label for="userId"> * 계정 아이디  (5-12자리) <span class="dupMsg" id="idchk"></span></label>
                                <input id="userId" name="userId" type="text" class="required">
                                <label for="password-2"> * 비밀번호  (6-20자리, _@./#&+- 가능)</label>
                                <input id="password-2" name="userPw" type="password" class="required">
                                <label for="confirm-2"> * 비밀번호 확인</label>
                                <input id="confirm-2" name="confirm" type="password" class="required">
                                <label for="userEmail"> * 메일 주소  (중복 불가) <span class="dupMsg" id="emailchk"></span></label>
                                <input id="userEmail" name="userEmail" type="text" class="required">
                                
                                <input type="hidden" id="idChecked" name="idChecked" value="">
                                <input type="hidden" id="emailChecked" name="emailChecked" value="">
                                <p>(*) 필수 항목</p>
                            </fieldset>
                         
                            <h3>상세 정보</h3>
                            <fieldset>
                                
                                <label for="name-2">* 이름</label>
                                <input id="name-2" name="userName" type="text" class="required">
                                <label for="surname-2">* 회사명</label>
                                <input id="surname-2" name="userCoName" type="text" class="required">
                                <label for="sample6_address">* 주소</label>
                                <div>
                                <input id="sample6_address" name="userCoAddr1" type="text" class="required" readonly="readonly" placeholder="주소검색" onclick="sample6_execDaumPostcode()">
                                <input id="sample6_address2" name="userCoAddr2" type="text" class="required" placeholder="나머지 주소">
                                </div>
                                <input id="sample6_postcode" name="userCoZip" type="hidden">
                                <input id="sample6_address_sys" name="userCoAddrSys" type="hidden">
                                <label for="address-2">* 유선전화(ex, 02-123-1234)</label>
                                <input id="address-2" name="userTel" type="text" class="required">
                                <label for="age-2">* 휴대전화 (ex, 010-1234-1234)</label>
                                <input id="age-2" name="userHp" type="text" class="required">
                                <p>(*) 필수 항목</p>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
          </div>
        <!-- end: content -->
      </div>

      <!-- start: Mobile -->
      <div id="mimin-mobile" class="reverse">
        <div class="mimin-mobile-menu-list">
            <div class="col-md-12 sub-mimin-mobile-menu-list animated fadeInLeft">
                <ul class="nav nav-list">
                    <li class="active ripple">
                      <a class="tree-toggle nav-header">
                        <span class="fa-home fa"></span>요약 정보 
                        <span class="fa-angle-right fa right-arrow text-right"></span>
                      </a>
                    </li>
                    <li class="ripple">
                      <a class="tree-toggle nav-header">
                        <span class="fa-diamond fa"></span>소프트웨어 현황
                        <span class="fa-angle-right fa right-arrow text-right"></span>
                      </a>
                    </li>
                    <li class="ripple">
                      <a class="tree-toggle nav-header">
                        <span class="fa-area-chart fa"></span>스프트웨어 관리
                        <span class="fa-angle-right fa right-arrow text-right"></span>
                      </a>
                     
                    </li>
                    <li class="ripple">
                      <a class="tree-toggle nav-header">
                        <span class="fa fa-pencil-square"></span>소프트웨어 검색
                        <span class="fa-angle-right fa right-arrow text-right"></span>
                      </a>
                     
                    </li>
                    <li class="ripple">
                      <a class="tree-toggle nav-header">
                       <span class="fa fa-check-square-o"></span>다운로드
                       <span class="fa-angle-right fa right-arrow text-right"></span>
                      </a>
                    </li>
                  </ul>
            </div>
        </div>       
      </div>
      <button id="mimin-mobile-menu-opener" class="animated rubberBand btn btn-circle btn-danger">
        <span class="fa fa-bars"></span>
      </button>
       <!-- end: Mobile -->

<!-- start: Javascript -->
<script src="${pageContext.request.contextPath}/asset/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/asset/js/jquery.ui.min.js"></script>
<script src="${pageContext.request.contextPath}/asset/js/bootstrap.min.js"></script>


<!-- plugins -->
<script src="${pageContext.request.contextPath}/asset/js/plugins/jquery.steps.min.js"></script>
<script src="${pageContext.request.contextPath}/asset/js/plugins/moment.min.js"></script>
<script src="${pageContext.request.contextPath}/asset/js/plugins/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath}/asset/js/plugins/jquery.nicescroll.js"></script>


<!-- custom -->
<script src="${pageContext.request.contextPath}/asset/js/main.js"></script>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
	
<script type="text/javascript">
  $(document).ready(function(){



    var form = $("#joinForm").show();
     
    form.steps({
        headerTag: "h3",
        bodyTag: "fieldset",
        transitionEffect: "slideLeft",
        onStepChanging: function (event, currentIndex, newIndex)
        {
            // Allways allow previous action even if the current form is not valid!
            if (currentIndex > newIndex)
            {
                return true;
            }
            // Forbid next action on "Warning" step if the user is to young
            //if (newIndex === 3 && Number($("#age-2").val()) < 18)
            //{
            //    return false;
            //}
            if(String($("#idChecked").val()) == "false" || String($("#emailChecked").val()) == "false") {
            	return false;
            }
            
            if(currentIndex == 1){
            	if(String($("#password-2").val()).length < 6 || String($("#password-2").val()).length > 20){
            		return false;
            	}
            }
            // Needed in some cases if the user went back (clean up)
            if (currentIndex < newIndex)
            {
                // To remove error styles
                form.find(".body:eq(" + newIndex + ") label.error").remove();
                form.find(".body:eq(" + newIndex + ") .error").removeClass("error");
            }
            form.validate().settings.ignore = ":disabled,:hidden";
            return form.valid();
        },
        onStepChanged: function (event, currentIndex, priorIndex)
        {
            // Used to skip the "Warning" step if the user is old enough.
            //if (currentIndex === 2 && Number($("#age-2").val()) >= 18)
            //{
            //    form.steps("next");
            //}
            // Used to skip the "Warning" step if the user is old enough and wants to the previous step.
            if (currentIndex === 2 && priorIndex === 3)
            {
                form.steps("previous");
            }
        },
        onFinishing: function (event, currentIndex)
        {
            form.validate().settings.ignore = ":disabled";
            return form.valid();
        },
        onFinished: function (event, currentIndex)
        {
            if(!confirm('이대로 진행하시겠습니까?')){
            	return false;
            }
            form.submit();
        }
    }).validate({
        errorPlacement: function errorPlacement(error, element) { element.before(error); },
        rules: {
            confirm: {
                equalTo: "#password-2"
            }
        }
    });


    $("#userId").keyup(function(){
		 if ( $('#userId').val().length > 0) {
			$.post('${pageContext.request.contextPath}/web/main/idCheck.do'
				,{"userId":$('#userId').val()}
				,function(data){
					console.log(data);
					
					if(data == "true"){
						$("#idchk").text("  사용 가능");
						$("#idchk").css('color', 'blue');
						$("#idChecked").val("true");
					}else{
						$("#idchk").text("  사용 불가");
						$("#idchk").css('color', 'red');
						$("#idChecked").val("false");
					}
				})
		}else{
			$("#idchk").text("");
			$("#idChecked").val("false");
		}
	});
    
    $("#userEmail").keyup(function() {
   	 if ( $('#userEmail').val().length > 0) {
   		$.post('${pageContext.request.contextPath}/web/main/emailCheck.do'
   			,{"userEmail":$('#userEmail').val()}
   			,function(data){
   				console.log(data);
   				
   				if(data == "true"){
   					$("#emailchk").text("  사용 가능");
   					$("#emailchk").css('color', 'blue');
   					$("#emailChecked").val("true");
   				}else{
   					$("#emailchk").text("  사용 불가");
   					$("#emailchk").css('color', 'red');
   					$("#emailChecked").val("false");
   				}
   			})
   	}else{
   		$("#emailchk").text("");
   		$("#emailChecked").val("false");
   	}
   });

  });
  
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
  
  function servletMessage(){ 
		var v=document.getElementsByName("servletMessage");
		if(v[0].value != ""){
			alert(v[0].value);
			v[0].value="";
		}
		
	}
  
  
  
  //--주소
</script>
<!-- end: Javascript -->
</body>
</html>