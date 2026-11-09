<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <p class="tag">GAME OVER</p>
    <h1>YOUR NAME<br><span>MATH WHIZ</span></h1>
  </header>

  <section class="panel">
    <h2><c:out value="${playerName}"/></h2>
    <p class="score">${score} / 10</p>

    <c:if test="${not empty dbError}">
      <p class="alert"><c:out value="${dbError}"/></p>
    </c:if>

    <div class="table-wrap">
      <table>
        <tr><th>Q</th><th>Question</th><th>Your pick</th><th>Correct</th><th>Result</th><th>Marks</th></tr>
        <c:forEach var="r" items="${results}" varStatus="st">
          <tr>
            <td>Q${st.count}</td>
            <td><c:out value="${r.prompt}"/></td>
            <td><c:out value="${r.chosen}"/></td>
            <td><c:out value="${r.correct}"/></td>
            <td class="${r.right ? 'ok' : 'bad'}">${r.right ? 'PASS' : 'FAIL'}</td>
            <td>${r.marks}/${r.maxMarks}</td>
          </tr>
        </c:forEach>
      </table>
    </div>

    <div class="actions spaced">
      <a class="btn" href="${pageContext.request.contextPath}/index.jsp">▶ PLAY AGAIN</a>
      <a class="btn alt" href="${pageContext.request.contextPath}/game?action=leaderboard">HIGH SCORES</a>
    </div>
  </section>
</main>
</body>
</html>
