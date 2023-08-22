<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>

<style type="text/css">
body{
background: rgb(238,174,202);
background: radial-gradient(circle, rgba(238,174,202,1) 0%, rgba(148,187,233,1) 100%);}
</style>

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous" />
<meta charset="ISO-8859-1">
<title>List Of User</title>
</head>
<body>
<jsp:include page="menu.jsp" />
<br>
	<table class="table table-striped table-dark">
		<thead>
			<tr>

				<th>USERNAME</th>
				<th>PASSWORD</th>
				<th>GENDER</th>

				<th>EMAIL</th>
				<th>PHONE</th>
				<th>QUESTION</th>

				<th>ANSWER</th>
				<th>ROLE</th>

			</tr>

		</thead>
		<tbody>
<c:forEach items="${users}" var="u">

<tr>
<td><c:out value="${u.username}"></c:out></td>
<td><c:out value="${u.password}"></c:out></td>
<td><c:out value="${u.gender}"></c:out></td>
<td><c:out value="${u.email}"></c:out></td>
<td><c:out value="${u.phone}"></c:out></td>

<td><c:out value="${u.question}"></c:out></td>
<td><c:out value="${u.answer}"></c:out></td>
<td><c:out value="${u.role}"></c:out></td>
</tr>

</c:forEach>
			
		</tbody>
	</table>
	<jsp:include page="footer.jsp" />
	
</body>
</html>