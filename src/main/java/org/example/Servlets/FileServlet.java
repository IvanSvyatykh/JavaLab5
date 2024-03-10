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

        UserData currentUser = AccountService.getUserBySessionId(httpServletRequest.getSession().getId());
        if (currentUser == null) {
            String currentURL = httpServletRequest.getRequestURL().toString();
            httpServletResponse.sendRedirect(PathUtilitie.createNewUrl(currentURL, "/login"));
            return;
        }

        String pathToUserDir = "/home/ivan/FileContainer/" + currentUser.getLogin();
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
        String sessionId = httpServletRequest.getSession().getId();
        AccountService.deleteSession(sessionId);
        String currentURL = httpServletRequest.getRequestURL().toString();
        httpServletResponse.sendRedirect(PathUtilitie.createNewUrl(currentURL, "/"));
    }
}