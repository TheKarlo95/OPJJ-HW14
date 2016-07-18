<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page session="true"%>

<html>
<head>
<title>Glasaj</title>
<link rel="shortcut icon" href="drawable/favicon.ico" />
<link href="<c:url value="css/style.css" />" rel="stylesheet">
</head>

<body bgcolor="#121b21">
    <div id="wrapper">
        <!-- Home button -->
        <div id="content">
            <table id="voting-table">
                <tbody>
                    <tr>
                        <td>
                            <div class="home-button" onclick="location.href='/webapp-baza/index.html';">HOME</div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div id="content">
            <div id="voting">
                <table id="title-table">
                    <thead>
                        <tr>
                            <th><c:out value="${poll.title}" /></th>
                        </tr>
                    </thead>

                    <tbody>
                        <tr>
                            <td><c:out value="${poll.message}" /></td>
                        </tr>
                    </tbody>
                </table>

                <table id="voting-table">
                    <tbody>
                        <c:forEach var="option" items="${pollOptions}">
                            <c:if test="${option.pollID == poll.id}">
                                <tr>
                                    <td>
                                        <div class="fancy-button"
                                            onclick="location.href='/webapp-baza${option.voteLink}';">
                                            <c:out value="${option.optionTitle}" />
                                        </div>
                                    </td>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>