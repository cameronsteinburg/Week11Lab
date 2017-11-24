package servlets;

import businesslogic.AccountService;
import businesslogic.UserService;
import dataaccess.NotesDBException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 734972
 */
public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String uuid = request.getParameter("uuid");
        request.setAttribute("uuid", uuid);

        getServletContext().getRequestDispatcher("/WEB-INF/resetNewPassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = (String) request.getParameter("action");

        if (action != null && action.equals("newPassword")) {

            String uuid = (String) request.getParameter("uuid");
            String password = (String) request.getParameter("password");

            UserService us = new UserService();
            String username = us.getByUUID(uuid).getUUID();

            AccountService as = new AccountService();
            as.changePassword(username, password);

            
            request.setAttribute("errormessage", "Password reset");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }

        String url = request.getRequestURL().toString();
        String email = (String) request.getParameter("email");

        AccountService as = new AccountService();

        String path = request.getServletContext().getRealPath("/WEB-INF/emailtemplates/resetpassword.html");

        try {

            int result = as.resetPassword(email, path, url);
            if (result == 1) {
                request.setAttribute("message", "sent");
            } else {
                request.setAttribute("message", "no go");
            }
        } catch (NotesDBException ex) {
            Logger.getLogger(ResetPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
    }
}
