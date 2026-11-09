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
    <p class="tag">★ ARCADE EDITION ★</p>
    <h1>YOUR NAME<br><span>MATH WHIZ</span></h1>
  </header>

  <section class="panel">
    <h2>PLAYER 1</h2>
    <c:if test="${not empty error}">
      <p class="alert"><c:out value="${error}"/></p>
    </c:if>
    <form action="${pageContext.request.contextPath}/game" method="post">
      <input type="hidden" name="action" value="start">
      <label for="playerName">ENTER YOUR NAME</label>
      <input id="playerName" name="playerName" maxlength="100" required autofocus autocomplete="off">
      <div class="actions">
        <button class="btn" type="submit">▶ START GAME</button>
        <a class="btn alt" href="${pageContext.request.contextPath}/game?action=leaderboard">HIGH SCORES</a>
      </div>
    </form>
  </section>
</main>
</body>
</html>
