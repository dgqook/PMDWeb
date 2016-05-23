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
	<nav class="navbar navbar-default header navbar-fixed-top">
	  <div class="col-md-12 nav-wrapper">
	    <div class="navbar-header" style="width:100%; padding-right:30px;">
	      <div class="opener-left-menu is-open">
	        <span class="top"></span>
	        <span class="middle"></span>
	        <span class="bottom"></span>
	      </div>
	        <a href="" class="navbar-brand"> 
	         <b>PMD</b>
	        </a>
	
	      
	      <ul class="nav navbar-nav navbar-right user-nav">
	        <li class="user-name"><span><%=userInfo.getUserId() %></span></li>
	          <li class="dropdown avatar-dropdown">
	           <img src="${pageContext.request.contextPath}/asset/img/avatar.jpg" class="img-circle avatar" alt="user name" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true"/>
	           <ul class="dropdown-menu user-dropdown">
	             <li><a href="${pageContext.request.contextPath}/web/main/myPage.do">
	             	<span class="fa fa-user"></span> 정보 수정</a>
	             </li>
	             <li><a href="${pageContext.request.contextPath}/web/main/logout.do">
	             	<span class="fa fa-power-off"></span> 로그아웃</a>
	             </li>
	          </ul>
	        </li>
	      </ul>
	    </div>
	  </div>
	</nav>
</header>