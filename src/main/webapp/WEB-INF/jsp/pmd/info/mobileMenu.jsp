
<div id="progressDiv" style="background:'#000000'; filter:alpha(opacity:'60'); background:rgba(0,0,0,0.6); width:100%; height:100%; z-index: 1000; display:none; position:absolute;">
		<img src="${pageContext.request.contextPath}/asset/images/loading_gif.gif" style="position:absolute; width:400px; height:300px; border:0; top:50%; left:50%; margin:-200px 0 0 -150px;">
	</div>
	<script>
	function goSummary(){
		showProgress();
		window.location.href="${pageContext.request.contextPath}/web/info/summary.do";
	}
	function goPresent(){
		showProgress();
		window.location.href="${pageContext.request.contextPath}/web/info/present.do";
	}
	function goManage(){
		showProgress();
		window.location.href="${pageContext.request.contextPath}/web/info/manage.do";
	}
	function goSearch(){
		showProgress();
		window.location.href="${pageContext.request.contextPath}/web/info/search.do";
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
<div id="mimin-mobile" class="reverse">
        <div class="mimin-mobile-menu-list">
            <div class="col-md-12 sub-mimin-mobile-menu-list animated fadeInLeft">
                <ul class="nav nav-list">
                    <li class="ripple">
                      <a class="tree-toggle nav-header" onclick="goSummary()">
                        <span class="fa-bar-chart fa"></span>요약 정보 
                        <span class="fa-angle-right fa right-arrow text-right"></span>
                      </a>
                    </li>
                    <li class="ripple">
                      <a class="tree-toggle nav-header" onclick="goPresent()">
                        <span class="fa fa-table"></span>소프트웨어 현황
                        <span class="fa-angle-right fa right-arrow text-right"></span>
                      </a>
                    </li>
                    <li class="ripple">
                      <a class="tree-toggle nav-header" onclick="goManage()">
                        <span class="fa fa-cog"></span>소프트웨어 관리
                        <span class="fa-angle-right fa right-arrow text-right"></span>
                      </a>
                    </li>
                    <li class="ripple">
                      <a class="tree-toggle nav-header" onclick="goSearch()">
                        <span class="fa fa-search"></span>소프트웨어 검색
                        <span class="fa-angle-right fa right-arrow text-right"></span>
                      </a>
                    </li>
                    <li class="ripple">
                      <a class="tree-toggle nav-header" href="${pageContext.request.contextPath}/web/main/clientDownload.do">
                       <span class="fa fa-download"></span>다운로드
                       <span class="fa-angle-right fa right-arrow text-right"></span>
                      </a>
                    </li>
                    <li><a href="credits.html">Credits</a></li>
                  </ul>
            </div>
        </div>       
      </div>
      <button id="mimin-mobile-menu-opener" class="animated rubberBand btn btn-circle btn-danger">
        <span class="fa fa-bars"></span>
      </button>