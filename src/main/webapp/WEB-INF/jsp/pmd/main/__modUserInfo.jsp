<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="pmd.common.vo.UserInfoVO" %>
<%@ page import="pmd.common.common.PMDUtil" %>
<%
	UserInfoVO userInfo= (UserInfoVO)session.getAttribute("userInfo");
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Coordy 소프트웨어 자산관리 - M Soft</title>

	<!-- 부트스트랩 및 제이쿼리 항상 최신버전 적용 -->
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/latest/css/bootstrap.min.css">
	<script src="//code.jquery.com/jquery.min.js"></script>
	<script src="//maxcdn.bootstrapcdn.com/bootstrap/latest/js/bootstrap.min.js"></script>
	<!-- !부트스트랩 및 제이쿼리 항상 최신버전 적용 -->
	
	<!-- 주소검색 -->
	<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
	<!-- !주소검색 -->
	
	<style>
	 	html,body {
            height:100%;
            width:100%;
            padding:0px; 
            margin:0px;

            background-color:#eef6fd;
            text-align:center; 
            vertical-align:middle; 
            font-family: dotum;
        }
        
        #popLayer {
        	display:none;
        	background-color:white;
        	border:1px solid #cfded8;
        	width:600px;
        	height:800px;
        }
        
        #popTable {
        	margin:24px;
        	width:550px;
        	height:750px;
            border:4px;
            border-color:#cfded8;
        }
        
        #popTable1r {
        	background-color:#DBECFC;
        	height: 55px;
        }
        #popTable2r {
        	height: 640px;
        	padding-left:20px;
        }
        #userPwForm {
        	background: url('${pageContext.request.contextPath}/Content/images/wrest.gif') #f7f7f7 top right no-repeat !important;
            border: 1px solid #e4eaec;
            width:400px;
            text-align:center;
            color: #9a9a9a;
        }
        #popTable3r {
        	height: 55px;
        	text-align:center;
        	background-color: #F5F6FA;
        }
        
        #submitButton {
        	background-color:#0b72ce;
            font-size:14px;
            font-weight:300;
            text-shadow:none;
            color:white;
            padding-top:9px;
            padding-bottom:11px;
            cursor: pointer;
            width:70px;
            height:35px;
            float:left; 
            margin-left:205px;
        }
        #cancelButton {
        	background-color:#4b545e; 
        	font-size:14px;
            font-weight:300;
            text-shadow:none;
            color:white;
            padding-top:9px;
            padding-bottom:11px;
            cursor: pointer;
            width:50px;
            height:35px;
            float:right;
            margin-right:218px;
        }
                
        .column{
        	width:70%;
        	text-align:left;
        	font-weight: 300;
        	margin-bottom:15px;
        }
        .textBox{
        	background: url('${pageContext.request.contextPath}/Content/images/wrest.gif') #f7f7f7 top right no-repeat !important;
            border: 1px solid #e4eaec;
            text-align:left;
            color: #9a9a9a;
            width:100%;
            margin-right:0;
        }
        .columnText{
        	color:red;
        }
        
        #sample6_postcode{
			background: url('${pageContext.request.contextPath}/Content/images/wrest.gif') #f7f7f7 top right no-repeat !important;
	        border: 1px solid #e4eaec;
	        text-align:left;
	        color: #9a9a9a;
	        width:30%;
	        margin-bottom: 5px;
		}
		
		#findZipButton{
			background-color:white;
             font-size:14px;
             font-weight:300;
             text-shadow:none;
             color:black;
             padding-top:1px;
             padding-bottom:2px;
             margin-left:10px;
             margin-bottom:4px;
		}
		
		#sample6_address, #sample6_address2{
			background: url('${pageContext.request.contextPath}/Content/images/wrest.gif') #f7f7f7 top right no-repeat !important;
	        border: 1px solid #e4eaec;
	        text-align:left;
	        color: #9a9a9a;
	        width:100%;
	        margin-bottom: 5px; 
		}
		#dupMsg {
			font-size:9px;
		}
	</style>
</head>
<body onload="servletMessage()">
	<!-- 서블릿에서 보낸 메시지 출력 -->
	<script type="text/javascript">
		function servletMessage(){
			var v=document.getElementsByName("servletMessage");
			if(v[0].value != ""){
				alert(v[0].value);
				v[0].value="";
			}
			
			$("#popLayer").show();
		   	$("#popLayer").center();
		}
		
		 jQuery.fn.center = function () {
		        this.css("position","absolute");
		        this.css("top", Math.max(0, (($(window).height() - $(this).outerHeight())*37 / 100) + $(window).scrollTop()) + "px");
		        this.css("left", Math.max(0, (($(window).width() - $(this).outerWidth()) / 2) + $(window).scrollLeft()) + "px");
		        return this;
		 }
		
		
		function modInfo(){
			var f= document.getElementsByName("userInfoForm");
			
			if(blankCheck()){
				f[0].method="post";
				f[0].action="${pageContext.request.contextPath}/web/main/modUserInfo.do";
				f[0].submit();
			}
		}
		
		 function clean(t){
	    	t.value="";
	    }
	    function fill(t,text){
	    	if(t.value=="") t.value=text;
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
	<input type="hidden" name="servletMessage" value= "${servletMessage}"/>
	<!-- !서블릿에서 보낸 메시지 출력 -->
	
	<div id="popLayer">
		<form name="userInfoForm">
			<table id="popTable" border="1">
			<tr id="popTable1r">
				<td>
					<img src="${pageContext.request.contextPath}/Content/images/mypage_title.jpg">
				</td>
			</tr>
			<tr id="popTable2r">
				<td>
					<label class="column"> 아이디/이메일 변경에 관해서는 <br>hk@msoft.co.kr로 문의 바랍니다.</label>
					<label class="column">
						
						아이디<span class="dupMsg" id="idchk"></span><br>
						<input class="textBox" type="text" id="userId" name="userId" placeholder=" 5-12자리의 영문으로 시작하는 영문 대/소문자 및 숫자"
						 		value="<%=userInfo.getUserId()%>" readonly="readonly">
						<input type="hidden" id="idChecked" name="idChecked">
					</label>
					<label class="column">
						이메일<span class="dupMsg" id="emailchk"></span><br>
						<input class="textBox" type="text" id="userEmail" value="<%=userInfo.getUserEmail()%>" 
							name="userEmail" placeholder="ex) example@domain.etc (타 계정 중복 불가)" readonly="readonly">
						<input type="hidden" id="emailChecked" name="emailChecked" value="">
					</label>
					<br>
					<label class="column">
						<span class="columnText">*</span>비밀번호<br>
						<input class="textBox" type="password" name="userPw" placeholder=" 6-20자리의 영문 대/소문자/숫자/특수문자(_@./#&+-)">
					</label>
					<label class="column">
						<span class="columnText">*</span>이름<br>
						<input class="textBox" type="text" name="userName" value="<%=userInfo.getUserName()%>" placeholder="한글 및 영문 대/소문자">
					</label>
					
					<label class="column">
						<span class="columnText">*</span>회사명<br> 
						<input class="textBox" type="text" name="userCoName" value="<%=userInfo.getUserCoName()%>" placeholder="한글 및 영문 대/소문자, 특수문자 ()_">
					</label>
					<label class="column">
						<!-- 주소 -->
						<span class="columnText">*</span>주소<br>
						<input type="text" id="sample6_postcode" placeholder="우편번호" name="userCoZip" readonly="readonly"  tabindex="-1">
						<input type="button" id="findZipButton" name="findZipButton" class="btn btn-default" onclick="sample6_execDaumPostcode()" value="우편번호 찾기"><br>
						<input type="text" id="sample6_address" placeholder="주소" name="userCoAddr1" readonly="readonly" tabindex="-1">
							<!-- 도로명(새주소):R, 지번(옛주소):J -->
						<input type="text" id="sample6_address2" placeholder="상세주소" name="userCoAddr2">
						<input type="hidden" id="sample6_address_sys" name="userCoAddrSys" value="">
						<!-- !주소 -->
					</label>
					<label class="column">
						<span class="columnText">*</span>유선전화<br>
						<input class="textBox" type="text" placeholder="ex) 02-123-1234" name="userTel" value="<%=userInfo.getUserTel()%>">
					</label>
					<label class="column">
						<span class="columnText">*</span>휴대전화<br>
						<input class="textBox" type="text" placeholder="ex) 010-1234-1234" name="userHp" value="<%=userInfo.getUserHp()%>">
					</label>
				</td>
			</tr>
			<tr id="popTable3r">
				<td>
					<div id="submitButton" onClick="modInfo()">정보수정</div>
					<div id="cancelButton" onClick="window.location.href='<%=PMDUtil.PMD_URL%>/web/main/summary.do'">취소</div>
				</td>
			</tr>
			</table>
		</form>
	</div>
	
</body>
</html>