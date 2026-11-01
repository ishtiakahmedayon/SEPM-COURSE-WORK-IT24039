
package com.example;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.*;

@WebServlet("/prime")
public class HelloWorldServelet extends HttpServlet {

    public boolean isPrime(int n) {

        if (n < 2)
            return false;

        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0)
                return false;
        }

        return true;
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        out.println("<html><body>");

        out.println("<h2>Prime Number Checker</h2>");

        out.println("<form method='post' action='prime'>");

        out.println("Enter Number: ");
        out.println("<input type='number' name='num' required>");

        out.println("<button type='submit'>Check</button>");

        out.println("<br><br>");

        out.println("Result: ");
        out.println("<input type='text' name='result' readonly>");

        out.println("</form>");

        out.println("</body></html>");
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html");

        int num = Integer.parseInt(
                request.getParameter("num")
        );

        String result;

        if (isPrime(num))
            result = num + " is Prime";
        else
            result = num + " is Not Prime";

        PrintWriter out = response.getWriter();

        out.println("<html><body>");

        out.println("<h2>Prime Number Checker</h2>");

        out.println("<form method='post' action='prime'>");

        out.println("Enter Number: ");
        out.println("<input type='number' name='num' value='"
                + num + "' required>");

        out.println("<button type='submit'>Check</button>");

        out.println("<br><br>");

        out.println("Result: ");
        out.println("<input type='text' value='"
                + result + "' readonly>");

        out.println("</form>");

        out.println("</body></html>");
    }
}
