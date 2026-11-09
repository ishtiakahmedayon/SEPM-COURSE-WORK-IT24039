<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${empty game}"><c:redirect url="/index.jsp"/></c:if>
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
    <p class="tag">PLAYER: <c:out value="${game.playerName}"/></p>
    <h1>YOUR NAME<br><span>MATH WHIZ</span></h1>
  </header>

  <div class="progress">
    <c:forEach begin="1" end="4" var="n">
      <i class="${n lt game.questionNumber ? 'done' : (n eq game.questionNumber ? 'now' : '')}"></i>
    </c:forEach>
  </div>

  <section class="q">
    <div class="meta">
      <span>Q${game.questionNumber} / ${game.total} · ${game.title}</span>
      <span>${game.marks} PTS</span>
    </div>

    <c:if test="${not empty error}">
      <p class="alert"><c:out value="${error}"/></p>
    </c:if>

    <c:choose>
      <c:when test="${not game.choosing}">
        <form action="${pageContext.request.contextPath}/game" method="post">
          <input type="hidden" name="action" value="generate">
          <c:choose>
            <c:when test="${game.type == 'PRIME'}">
              <p class="text">Enter an integer. The game will ask if it is prime.</p>
              <label for="n">YOUR INTEGER</label>
              <input id="n" type="number" name="n" step="1" required autofocus>
            </c:when>
            <c:when test="${game.type == 'MOD'}">
              <p class="text">Enter two integers for the modulus question.</p>
              <div class="row">
                <div>
                  <label for="a">DIVIDEND (A)</label>
                  <input id="a" type="number" name="a" step="1" required autofocus>
                </div>
                <div>
                  <label for="b">DIVISOR (B)</label>
                  <input id="b" type="number" name="b" step="1" required>
                </div>
              </div>
            </c:when>
            <c:otherwise>
              <p class="text">Pick a function and enter an angle in degrees.</p>
              <div class="row">
                <div>
                  <label for="fn">FUNCTION</label>
                  <select id="fn" name="fn">
                    <option value="sin">SIN</option>
                    <option value="cos">COS</option>
                    <option value="tan">TAN</option>
                  </select>
                </div>
                <div>
                  <label for="angle">ANGLE (DEG)</label>
                  <input id="angle" type="number" name="angle" step="1" required autofocus>
                </div>
              </div>
            </c:otherwise>
          </c:choose>
          <button class="btn" type="submit">GET OPTIONS ▶</button>
        </form>
      </c:when>

      <c:otherwise>
        <form action="${pageContext.request.contextPath}/game" method="post">
          <input type="hidden" name="action" value="answer">
          <p class="text"><c:out value="${game.prompt}"/></p>
          <p class="hint">Choose one option.</p>
          <div class="opts">
            <c:forEach var="o" items="${game.choices.options}" varStatus="st">
              <label class="opt">
                <input type="radio" name="choice" value="${st.index}" required>
                <span><c:out value="${o}"/></span>
              </label>
            </c:forEach>
          </div>
          <button class="btn" type="submit">LOCK ANSWER ▶</button>
        </form>
      </c:otherwise>
    </c:choose>
  </section>

  <p class="tag">SCORE SO FAR: ${game.score}</p>
</main>
</body>
</html>
