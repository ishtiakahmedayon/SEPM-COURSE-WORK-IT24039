<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>YOUR NAME – MATH WHIZ</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<main class="screen">
  <header class="marquee">
    <p class="tag">★ HALL OF FAME ★</p>
    <h1>TOP 10<br><span>HIGH SCORES</span></h1>
  </header>

  <section class="panel">
    <c:if test="${not empty error}">
      <p class="alert"><c:out value="${error}"/></p>
    </c:if>

    <div class="table-wrap">
      <table>
        <tr><th>#</th><th>Player</th><th>Score</th><th>Level</th><th>Played at</th></tr>
        <c:forEach var="s" items="${scores}" varStatus="st">
          <tr>
            <td>${st.count}</td>
            <td><c:out value="${s.playerName}"/></td>
            <td>${s.score}</td>
            <td>${s.difficulty}</td>
            <td><fmt:formatDate value="${s.playedAt}" pattern="yyyy-MM-dd HH:mm"/></td>
          </tr>
        </c:forEach>
        <c:if test="${empty scores}">
          <tr><td colspan="5">NO SCORES YET. BE THE FIRST!</td></tr>
        </c:if>
      </table>
    </div>

    <div class="actions spaced">
      <a class="btn" href="${pageContext.request.contextPath}/index.jsp">▶ PLAY</a>
    </div>
  </section>
</main>
</body>
</html>
