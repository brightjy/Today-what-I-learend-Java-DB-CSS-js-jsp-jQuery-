<%@page import="com.tj.model1ex.dto.CustomerDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String conPath = request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
header{width:1000px; margin: 0 auto;}
header #nav ul {overflow: hidden;}
header #nav ul li{list-style: none; float: right; padding:10px 30px;}
header #nav ul li a {text-decoration: none; color:#003300;
	font-weight: bold; font-size: 1.5em;
}	
</style>
</head>
<body>
	<header>
		<div id="nav">
		<%if(session.getAttribute("customer")==null) { // 로그인 전%>
			<ul>
				<li><a href="../customer/join.jsp">회원가입</a></li>
				<li><a href="../customer/login.jsp">로그인</a></li>
				<li><a href="../main/main.jsp">홈</a></li>
			</ul>
		<%}else{ // 로그인 후%>
			<ul>
				<li><a href="../customer/logout.jsp">로그아웃</a></li>
				<li><a href="../customer/modify.jsp">정보수정</a></li>
				<li><a href="mypage.jsp"><%=((CustomerDto)session.getAttribute("customer")).getCname() %>님 </a></li>
			</ul>
		
		<%} %>

		</div>

	</header>
</body>
</html>