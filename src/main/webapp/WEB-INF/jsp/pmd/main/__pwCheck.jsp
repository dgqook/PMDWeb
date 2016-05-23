<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="pmd.common.common.PMDUtil" %>
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
        	height:400px;
        }
        
        #popTable {
        	margin:24px;
        	width:550px;
        	height:350px;
            border:4px;
            border-color:#cfded8;
        }
        
        #popTable1r {
        	background-color:#DBECFC;
        	height: 55px;
        }
        #popTable2r {
        	height: 240px;
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
            margin-left:200px;
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
            width:70px;
            height:35px;
            float:right;
            margin-right:200px;
        }
        
        .agreeTextLabel {
        	font-weight: 400;
        	width:95%;
        	text-align:right;
        	margin-right:10px;
        }
        
        .textBox{
        	background: url('${pageContext.request.contextPath}/Content/images/wrest.gif') #f7f7f7 top right no-repeat !important;
            border: 1px solid #e4eaec;
            text-align:left;
            color: #9a9a9a;
            width:200px;
        } 
        
        #userPwForm{
        	font-size:16px;
        	height: 30px;
        	margin-top:20px;
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
		
		
		function clean(t){
	    	t.value="";
	    }
	    function fill(t,text){
	    	if(t.value=="") t.value=text;
	    }
	    
	    function pressEnter(evt){
      		var keyCode = evt.which?evt.which:event.keyCode;
      		
      		if(keyCode==13){
      			doCheck()
      		}
      	}
	    
	    function doCheck(){
      		var f= document.getElementsByName("pwCheckForm");
      		
  			if(f[0].userPw.value == null || f[0].userPw.value == ""){
  				alert("비밀번호를 입력하세요.");
  				f[0].userPw.focus();
  				return false;
  			}
  			f[0].method="post";
	 	    f[0].action="${pageContext.request.contextPath}/web/main/modUserInfoPage.do";
	 	    f[0].submit();
      	}
	</script>
	<input type="hidden" name="servletMessage" value= "${servletMessage}"/>
	<!-- !서블릿에서 보낸 메시지 출력 -->
	
	
	<div id="popLayer">
		<form name="pwCheckForm">
			<table id="popTable" border="1">
			<tr id="popTable1r">
				<td>
					<img src="${pageContext.request.contextPath}/Content/images/pw_title01.jpg">
				</td>
			</tr>
			<tr id="popTable2r">
				<td>
					<b>비밀번호를 한번 더 입력해주세요.</b><br>
					<p>회원님의 정보를 안전하게 보호하기 위해 비밀번호를 한번 더 확인합니다.</p>
					<input type="password" class="textBox" name="userPw" id="userPwForm" 
						placeholder="비밀번호 입력" onkeypress="pressEnter(event)">
					
				</td>
			</tr>
			<tr id="popTable3r">
				<td>
					<div id="submitButton" onClick="doCheck()">확인</div>
					<div id="cancelButton" onClick="window.location.href='<%=PMDUtil.PMD_URL%>/web/info/summary.do'">취소</div>
				</td>
			</tr>
			</table>
		</form>
	</div>
	
</body>
</html>