<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="left-menu">
  <div class="sub-left-menu scroll">
    <ul class="nav nav-list">
        <li class="time">
          <h1 class="animated fadeInLeft">21:00</h1>
          <p class="animated fadeInRight">Sat,October 1st 2029</p>
        </li>
        
        <li class="ripple"> 
          <a class="tree-toggle nav-header" href="${pageContext.request.contextPath}/web/info/summary.do">
            <span class="fa-bar-chart fa"></span> 요약 정보
            <span class="fa-angle-right fa right-arrow text-right"></span>
          </a>
        </li>
        
        <li class="ripple">
        <a class="tree-toggle nav-header" href="${pageContext.request.contextPath}/web/info/present.do">
        <span class="fa fa-table"></span>  소프트웨어 현황 
        <span class="fa-angle-right fa right-arrow text-right"></span> </a>
        </li>
        
        <li class="ripple">
        <a class="tree-toggle nav-header" href="${pageContext.request.contextPath}/web/info/manage.do">
        <span class="fa fa-cog"></span> 소프트웨어 관리
        <span class="fa-angle-right fa right-arrow text-right"></span> </a>
        </li>
        
        <li class="ripple">
        <a class="tree-toggle nav-header" href="${pageContext.request.contextPath}/web/info/search.do">
        <span class="fa fa-search"></span> 소프트웨어 검색
        <span class="fa-angle-right fa right-arrow text-right"></span> </a>
        </li>
        
        <li class="ripple">
        <a class="tree-toggle nav-header" href="${pageContext.request.contextPath}/web/main/clientDownload.do">
        <span class="fa fa-download"></span> 다운로드
        <span class="fa-angle-right fa right-arrow text-right"></span> </a>
        </li>
        
         
        <li><a href="http://mpmcns.com">MPMCNS</a></li>
      </ul>
    </div>
</div>