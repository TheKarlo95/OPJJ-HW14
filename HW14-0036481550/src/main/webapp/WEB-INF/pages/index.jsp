<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page session="true"%>

<html>
<head>
<title>Dobro došli</title>
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
                <table id="voting-table">
                    <thead>
                        <tr>
                            <th>Ankete</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="poll" items="${polls}">
                            <tr>
                                <td>
                                    <div class="fancy-button"
                                        onclick="location.href='/webapp-baza/glasaj?pollID=${poll.id}';">
                                        <c:out value="${poll.title}" />
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>