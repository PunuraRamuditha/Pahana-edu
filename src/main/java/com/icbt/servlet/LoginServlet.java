package com.icbt.servlet;
import com.icbt.model.User;
import com.icbt.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

import com.icbt.servlet.BillServlet;




import com.icbt.servlet.BillServlet;





import com.icbt.servlet.BillServlet;





public class LoginServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init(){userService=new UserService();}
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String uname = request.getParameter("username");
        String pass = request.getParameter("password");
        User user = userService.login(uname,pass);


            if (user!=null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);


                response.sendRedirect("BillServlet?action=new");

            } else {
                request.setAttribute("error", "Invalid username or password.");
                RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                rd.forward(request, response);
            }

    }
}