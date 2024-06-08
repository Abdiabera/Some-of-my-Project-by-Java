		<%@ page language="java" contentType="text/html; charset=UTF-8"
		    pageEncoding="UTF-8"%>
		<!DOCTYPE html>
		<html>
		<meta charset = "IS-8859-1">
		
		<title> Insert title here </title>
		
		<style>
		
		table {
		font-family : arial, sans-serif;
		
		border-collapse: collapse;
		
		width 100%;
		
		}
		
		td ,th {
		
		
		border : 1px solid #dddddd;
		
		
		text-align : left;
		
		padding: 8px;
		
		
		}
		
		tr:nth-child(even){
		background-color: #dddddd;
		
		}
		
		</style>
		
		</head>
		
		<body>
		<hr/>
		
		
		<form method="post" action = "/save" >
		
		
		<input type ="hidden" id = "id"  name = "id" value = "${lists.id }"/><br/>
		
		First Name :<br>
		
		<input type = "text"  id ="firstName" name = "firstName" value = "${lists.firstName }"/>
		<br>
		Last Name :<br>
		<input type ="text" id = "lastName"  name = "lastName" value = "${lists.lastName }"/>
		<br><br>
		<input type = "submit" value = "submit">
		
		</form>
		
