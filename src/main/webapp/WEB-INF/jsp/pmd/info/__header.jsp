<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="pmd.common.vo.UserInfoVO" %>
<%@ page import="pmd.common.common.PMDUtil" %>
<%	
	UserInfoVO userInfo= (UserInfoVO)session.getAttribute("userInfo");
	String restOfExpiry= (String) session.getAttribute("restOfExpiry");

	if(userInfo == null || restOfExpiry == null) response.sendRedirect(PMDUtil.PMD_URL);
%>
<header>
	<div id="top">
		<div id="top_wrap">
			<div id="top_logo">
				<a href="${pageContext.request.contextPath}/web/info/summary.do">
					<img id="top_logo_img" src="${pageContext.request.contextPath}/Content/images/logo.jpg">
				</a>
			</div>
			<div id="top_content">
				<b><span style="color:#045FB4;"><%=userInfo.getUserId() %></span></b>님 안녕하세요.
					<a href="${pageContext.request.contextPath}/web/main/myPage.do">정보수정</a>
					<a href="${pageContext.request.contextPath}/web/main/logout.do">로그아웃</a>
					<br>
				<%=restOfExpiry %>
			</div>
		</div>
		<div id="top_menu">
			<ul id="top_menu_bucket">
				<li class="top_menu_item" onclick="goSummary()">요약 정보</li>
				<li class="top_menu_item" onclick="goPresent()">소프트웨어 현황</li> 
				<li class="top_menu_item" onclick="goManage()">소프트웨어 관리</li>
				<li class="top_menu_item" onclick="goSearch()">소프트웨어 검색</li>
				<li class="top_menu_item" onclick="goDownload()">다운로드</li>
			</ul>
		</div>
	</div>
</header>