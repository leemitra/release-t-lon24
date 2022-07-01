<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h2>Loan Request</h2>
<p>  </p>
<form action="customerRequestProcess" name="custReq" id="custReq" method="post" >
<table>
<tr><td>Full Name</td><td><input type="text" name="fullName" required="required" > </td></tr>
<tr><td>Contact Number</td><td><input type="text" name="contactNumber" required="required" >  </td></tr>

<tr><td>National Id card Number</td><td><input type="text" name="idCardNumber" value="AM23454" readonly="readonly" required="required"></td></tr>

<tr><td>Loan Amount</td><td><input type="text" name="loanAmount" required="required"> <b>*</b> If amount is greater then 50000 then manager need to approve </td></tr>
<tr><td>Interest Rate</td><td><input type="text" name="interestRate" readonly="readonly" value="12"></td></tr>
<tr><td>Tenor</td><td><input type="text" name="years" required="required"> </td></tr>

<tr><td>Comments </td><td><input type="text" name="comments" required="required"></td></tr>
<tr><td>Decision</td><td>
<select name="userDecision" required="required"  >
<option value="">--select your decision--</option>
<option value="Accept">Accept</option>
<option value="Decline">Decline</option>
</select>
 </td></tr>
<tr><td> </td><td><input type="submit" value="Accept"> <input type="reset"> </td></tr>
</table>
</form>
</body>
</html>