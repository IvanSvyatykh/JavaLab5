package org.example.Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Model.UserData;
import org.example.Services.AccountService;
import org.example.Services.PathUtilitie;

import java.io.File;
import java.io.IOException;

@WebServlet(urlPatterns = {"/registration"})
public class RegistrationServlet extends HttpServlet {

    public void doGet(HttpServletRequest httpServletRequest,
                      HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletRequest.getRequestDispatcher("registration.jsp").forward(httpServletRequest, httpServletResponse);
    }

    //Регистрация в системе
    public void doPost(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse) throws IOException {
        String email = httpServletRequest.getParameter("email");
        String login = httpServletRequest.getParameter("login");
        String pass = httpServletRequest.getParameter("pass");

        if (email.isEmpty() || login.isEmpty() || pass.isEmpty()) {
            httpServletResponse.setContentType("text/html;charset=utf-8");
            httpServletResponse.getWriter().println("Отсутсвует email, логин или пароль");
            return;
        }

        UserData profile = new UserData(login, pass, email);
        if (AccountService.getUserByLogin(login) == null) {
            AccountService.addNewUser(profile);
            AccountService.addSession(httpServletRequest.getSession().getId(), profile);

            File folder = new File("home/ivan/FileContainer/" + login);
            boolean isCreationSuccess = folder.mkdir();

            if (!isCreationSuccess) {
                httpServletResponse.setContentType("text/html;charset=utf-8");
                httpServletResponse.getWriter().println("Случилась ошибка при создании папки, попробуйте ещё раз");
                return;
            }

            String currentURL = httpServletRequest.getRequestURL().toString();
            httpServletResponse.sendRedirect(PathUtilitie.createNewUrl(currentURL, "/files"));
        } else {
            httpServletResponse.setContentType("text/html;charset=utf-8");
            httpServletResponse.getWriter().println("Пользователь с таким логином уже есть в системе");
        }
    }
}