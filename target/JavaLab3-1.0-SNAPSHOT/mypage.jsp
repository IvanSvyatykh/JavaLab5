<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Lab3</title>
</head>
<body>
<h1>Страница создана:${generationTime}</h1>
<h2>Список файлов и папок в текущей директории: ${currentDir}</h2>
<c:url value="files" var="upUrl">
    <c:param name="path" value="${parentDirPath}"/>
</c:url>
<a href="${upUrl}">Вверх</a>
<style type="text/css">
    BODY {
        background: white;
    }

    TD, TH {
        padding: 3px;
        border: 1px solid maroon;
        text-align: left;
    }
</style>
<table>
    <tr>
        <th>File</th>
        <th>Size</th>
        <th>Date</th>
    </tr>

    <c:forEach var="listElement" items="${list}">
        <tr>
            <td>
                <c:choose>
                    <c:when test="${fn:endsWith(listElement.name, '/')}">
                        <c:url value="files" var="downUrl">
                            <c:param name="path" value="${listElement.path}"/>
                        </c:url>
                        <a href="${downUrl}">"${listElement.name}"</a>
                    </c:when>
                    <c:otherwise>
                        <c:url value="load" var="loadUrl">
                            <c:param name="path" value="${listElement.path}"/>
                        </c:url>
                        <a href="${loadUrl}">"${listElement.name}"</a>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>${listElement.size}</td>
            <td>${listElement.date}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>