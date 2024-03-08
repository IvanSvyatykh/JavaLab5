package org.example.Servlets;

import org.example.Services.FileService;

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
public class MainServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException, ServletException {
        String pathFromRequest = httpServletRequest.getParameter("path");
        if (httpServletRequest.getParameter("path") == null) {
            pathFromRequest = new File(".").getCanonicalPath();
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

}
