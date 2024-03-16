package org.example.Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

        UserData data = new UserData(login, pass, email);
        if (AccountService.getUserByLogin(login) == null) {
            AccountService.addNewUser(data);

            httpServletRequest.getSession().setAttribute("login", login);
            httpServletRequest.getSession().setAttribute("pass", pass);

            File folder = new File("/home/ivan/FileContainer/" + login);
            boolean isCreationSuccess = true;

            if (new File(folder.getParent()).exists()) {
                if (!folder.exists()) {
                    isCreationSuccess = folder.mkdir();
                }
            } else {
                isCreationSuccess = folder.mkdirs();
            }


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