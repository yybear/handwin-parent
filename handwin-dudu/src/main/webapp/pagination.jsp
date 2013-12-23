<%@page import="cn.v5.framework.web.Page"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	Page<?> p = (Page<?>) request.getAttribute("page");
	String pageQuery = (String) request.getAttribute("pageQuery");
	if (p != null) {
		long totalPages = p.getTotalPage();
		if(totalPages>1){
		
			long currentPage = p.getPageNumber();
			long size = p.getPageSize();
			int maxButton = 5;
			int half = maxButton/2;
		
			long startButton = 1;
			if(totalPages > maxButton) {
				startButton = currentPage-half<=0 ?1:(int)currentPage-half;
			}
			long endButton = startButton + maxButton -1;
			if(endButton > totalPages)
				endButton = totalPages;
			
			if(endButton - currentPage < half) {
				startButton = endButton - maxButton + 1;
			}
			
		%>
		<ul class="pagination">
			<% for (int i = (int)startButton; i<= endButton; i++) {
				if(startButton > 1 && i == startButton){
			    %>
					<li><a href="?s=<%=currentPage-1 %>">上一页</a></li>
			    <%}%>
				<li <%if(i==currentPage){%>class="active"<%} %>>
					<a <%if(i!=currentPage){%>href="?s=<%=i%>"<%}%>><%=i%></a>
				</li>
		        <%if(endButton<totalPages && i==endButton){%>
					<li><a href="?s=<%=currentPage+1 %>">下一页</a></li>
			    <%}
			}%>	
		</ul>
		<%
	}
	}
%>