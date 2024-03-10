package org.example.Servlets;

import org.example.Model.UserData;
import org.example.Services.AccountService;
import org.example.Services.PathUtilitie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/"})
public class LoginServlet extends HttpServlet {
    public void doGet(HttpServletRequest httpServletRequest,
                      HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletRequest.getRequestDispatcher("login.jsp").forward(httpServletRequest, httpServletResponse);
    }

    //Вход в систему
    public void doPost(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse) throws IOException {
        String login = httpServletRequest.getParameter("login");
        String pass = httpServletRequest.getParameter("pass");

        if (login.isEmpty() || pass.isEmpty()) {
            httpServletResponse.setContentType("text/html;charset=utf-8");
            httpServletResponse.getWriter().println("Отсутсвует логин или пароль");
            return;
        }

        UserData data = AccountService.getUserByLogin(login);
        if (data == null || !data.getPassword().equals(pass)) {
            httpServletResponse.setContentType("text/html;charset=utf-8");
            httpServletResponse.getWriter().println("Неправильный логин или пароль");
            return;
        }

        AccountService.addSession(httpServletRequest.getSession().getId(), data);

        String currentURL = httpServletRequest.getRequestURL().toString();
        httpServletResponse.sendRedirect(PathUtilitie.createNewUrl(currentURL, "/files"));
    }
}