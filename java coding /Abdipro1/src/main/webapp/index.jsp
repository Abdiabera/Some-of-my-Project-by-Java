<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
   <head>
      <head>
         <title>Insert title here</title>
   </head>
   <body>
      <table>
         <tr>
            <th>id</th>
            <th>First Name</th>
            <th>Last  Name</th>
            <th>Action</th>
         </tr>
         <c:forEach var = "list" items = "${lists}">
            <tr>
               <td>${list.id}</td>
               <td>${list.firstName}</td>
               <td>${list.lastName}</td>
               <td>
                  <a href="/view/${list.id}">View</a>
                  <a href="/delete/${list.id}">Delete</a>
                  <a href="/edit/${list.id}">Edit</a>
               </td>
            </tr>
         </c:forEach>
      </table>
      <hr/>
     <form method="post" action = "/save" >
		
		
		<input type ="hidden" id = "id"  name = "id" value = ""/><br/>
		
		First Name :<br>
		
		<input type = "text"  id ="firstName" name = "firstName" value = ""/>
		<br>
		Last Name :<br>
		<input type ="text" id = "lastName"  name = "lastName" value = ""/>
		<br><br>
		<input type = "submit" value = "submit">
		
		</form>
   </body>
</html>