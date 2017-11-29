<%--
  Created by IntelliJ IDEA.
  User: 63160
  Date: 11/28/2017
  Time: 9:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>CourseManager</title>
</head>
<body>
    <div>
        <h2>Welcome, Student ${student.name}</h2>
        <p>Student ID: ${student.ID}</p>
        <p>Department: ${student.dept_name}</p>
        <p>Total Credit: ${student.tot_cred}</p>
    </div>
    <div style="text-align: center">
        <p>The courses you selected:</p>
        <table align='center' border='1' cellspacing='0'>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Department</th>
                <th>Credits</th>
                <th>Instructor</th>
                <th>Classroom</th>
            </tr>
            <c:forEach items="${courses}" var="c" varStatus="st">
                <tr>
                    <td>${c.course_id}</td>
                    <td>${c.title}</td>
                    <td>${c.dept_name}</td>
                    <td>${c.credits}</td>
                    <td>${c.instructor}</td>
                    <td>${c.classroom}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>
