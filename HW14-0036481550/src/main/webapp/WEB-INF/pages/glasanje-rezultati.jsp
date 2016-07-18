<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page session="true"%>

<html>
<head>
<title>Rezultati</title>
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
        <!-- Poll title -->
        <div id="content">
            <div id="voting">
                <table id="title-table">
                    <thead>
                        <tr>
                            <th><c:out value="${poll.title}" /></th>
                        </tr>
                    </thead>
                </table>
            </div>
        </div>

        <!-- Table -->
        <div id="content">
            <div id="voting">
                <table id="no-hover-table">
                    <thead>
                        <tr>
                            <th colspan="2">Rezultati glasanja</th>
                        </tr>
                        <tr>
                            <th>Odabir</th>
                            <th>Broj glasova</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="options" items="${pollOptions}">
                            <tr>
                                <td><c:out value="${options.optionTitle}" /></td>
                                <td><c:out value="${options.votesCount}" /></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <!-- Pie chart -->
            <div id="voting">
                <table id="chart-table">
                    <thead>
                        <tr bgcolor="#666666">
                            <th>Tortni grafikon</th>
                        </tr>
                    </thead>

                    <tbody>
                        <tr>
                            <td><img src="glasanje-grafika?pollID=${poll.id}" alt="Pie-chart" class="chart"></td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <!-- Excel spreadsheet -->
            <div id="voting">
                <table id="no-hover-table">
                    <thead>
                        <tr>
                            <th>Rezultati ankete u XLS formatu</th>
                        </tr>
                    </thead>

                    <tbody>
                        <tr>
                            <td>
                                <div class="fancy-link"
                                    onclick="location.href='/webapp-baza/glasanje-xls?pollID=${poll.id}';">
                                    Rezultati ankete u XLS formatu su ovdje</div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <!-- Winner links -->
            <div id="voting">
                <table id="no-hover-table">
                    <thead>
                        <tr>
                            <th>Pobjednik:</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="winner" items="${winners}">
                            <tr>
                                <td>
                                    <div class="fancy-link" onclick="location.href='${winner.optionLink}';">
                                        <c:out value="${winner.optionTitle}" />
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