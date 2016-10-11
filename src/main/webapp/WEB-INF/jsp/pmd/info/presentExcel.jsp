<%@ page language="java" contentType="application/vnd.ms-excel;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="pmd.common.vo.SoftwareInfoVO" %>
<%
 
 	String header = request.getHeader("User-Agent");
 	ArrayList<SoftwareInfoVO> softwareList= (ArrayList<SoftwareInfoVO>)session.getAttribute("softwareList");
 	String pcName= (String) session.getAttribute("pcName");
 
    response.setHeader("Content-Disposition", "attachment; filename=PMD_List_"+pcName+".xls;"); 
    response.setHeader("Content-Description", "JSP Generated Data");
    response.setHeader("Content-Transfer-Encoding", "binary;");
    response.setHeader("Pragma", "no-cache;");
    response.setHeader("Expires", "-1;");
    
    
    
%>
<html>
<head>
<META HTTP-EQUIVE="CONTENT-TYPE" CONTENT="TEXT/HTML; CHARSET=utf-8">
<title>엑셀파일변환</title>
</head>
<body bgcolor=white>
<table border=1>
<tr bgcolor="#CACACA">
<th>번호</th>
<th>구분</th>
<th>PC명</th>
<th>OS</th>
<th>소프트웨어명</th>
<th>최종업데이트</th>
</tr>
<%
   int index= 0;
   for(SoftwareInfoVO s:softwareList) {
   index++;
%>       
<tr>
<td><%=index%></td>
<%if(s.getParam1().equals("T")) {%>
<td>정품</td>
<%}else{ %>
<td>복제</td>
<%} %>
<td><%=s.getPcName()%></td>
<td><%=s.getPcOs()%></td>
<td><%=s.getSwName()%></td>
<td style="mso-number-format:'yyyy-MM-dd hh:mm:ss'"><%=s.getUpdateDate()%></td>
</tr>
<%
}
%>
</table>
</body>
</html>