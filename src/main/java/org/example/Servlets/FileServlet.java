package org.example.Servlets;

import org.example.Model.UserData;
import org.example.Services.AccountService;
import org.example.Services.FileService;
import org.example.Services.PathUtilitie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/files")
public class FileServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException, ServletException {

        String login = (String) httpServletRequest.getSession().getAttribute("login");
        String pass = (String) httpServletRequest.getSession().getAttribute("pass");

        if (AccountService.getUserByLogin(login) == null || !AccountService.getUserByLogin(login).getPassword().equals(pass)) {
            String currentURL = httpServletRequest.getRequestURL().toString();
            httpServletResponse.sendRedirect(PathUtilitie.createNewUrl(currentURL, "/log"));
            return;
        }

        String pathToUserDir = "/home/ivan/FileContainer/" + login;
        String pathFromRequest = httpServletRequest.getParameter("path");
        if (httpServletRequest.getParameter("path") != null) {
            if (!pathFromRequest.startsWith(pathToUserDir)) {
                pathFromRequest = pathToUserDir;
            }
        } else {
            pathFromRequest = pathToUserDir;
        }

        httpServletRequest.setAttribute("currentDir", pathFromRequest);
        httpServletRequest.setAttribute("list",
                FileService.GetElements(new File(pathFromRequest)));

        String parentDirPath = new File(pathFromRequest).getParent();
        if (parentDirPath == null) {
            parentDirPath = pathFromRequest;
        }
        httpServletRequest.setAttribute("parentDirPath", parentDirPath);

        Date generationDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy HH:mm:ss");

        httpServletRequest.setAttribute("generationTime", dateFormat.format(generationDate));
        httpServletRequest.getRequestDispatcher("mypage.jsp").forward(httpServletRequest, httpServletResponse);
    }

    public void doPost(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse) throws IOException {

        httpServletRequest.getSession().removeAttribute("login");
        httpServletRequest.getSession().removeAttribute("pass");
        String currentURL = httpServletRequest.getRequestURL().toString();
        httpServletResponse.sendRedirect(PathUtilitie.createNewUrl(currentURL, "/"));
    }
}