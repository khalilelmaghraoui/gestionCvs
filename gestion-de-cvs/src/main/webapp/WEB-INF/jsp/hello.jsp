<%@ include file="/WEB-INF/jsp/header.jsp"%>

<%@page import="java.util.Date"%>

<div class="container">
	<h1>Hello de ${message}</h1>
	<p>
	<%
	Date d = new Date();
	out.print("Aujourd'hui : ");
	out.print(d);
	request.setAttribute("today", d);
	%>
	</p>
	
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>
