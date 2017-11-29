<%--
  Created by IntelliJ IDEA.
  User: 63160
  Date: 11/28/2017
  Time: 11:30 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table align='center' border='1' cellspacing='0'>
    <tr>
        <td>id</td>
        <td>name</td>
        <td>Dept</td>
        <td>credits</td>
    </tr>
    <c:forEach items="${cs}" var="c" varStatus="st">
        <tr>
            <td>${c.course_id}</td>
            <td>${c.title}</td>
            <td>${c.dept_name}</td>
            <td>${c.credits}</td>

        </tr>
    </c:forEach>
</table>

