<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h2>Loan Officer or Bank Manager Dashboard</h2>
<table border="1" >
		<thead>
			<tr>
				<th>Sl No.</th>
				<th>JBPM Task-id</th>
				<th>Role Name</th>
				<th>Description</th>
				<th>Approve</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="loan" items="${loans}" varStatus="count" >
		<tr>
		<td>${count.count}</td>
		<td>${loan.taskId}</td>
		<td>${loan.role}</td>
		<td>${loan.description}</td>
		<td><a href="managerApproved/${loan.taskId}">Approve</a>  </td>
		</tr>
		</c:forEach>
		</tbody>

	</table>
</body>
</html>