package servlets;

import DAO.DAOFactory;
import DAO.UserDAO;
import log.MyLog;
import models.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationServlet extends HttpServlet {

    private final static String registration = "/WEB-INF/view/registration.jsp";
    private final static String registration_success = "/WEB-INF/view/registration_success.jsp";
    private final static String registration_email_exists = "/WEB-INF/view/registration_email_exists.jsp";
    private final static String registration_error = "/WEB-INF/view/registration_error.jsp";

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Пример вывода всех пользователей в базе
        /*DAOFactory sqliteFactory = DAOFactory.getDAOFactory(DAOFactory.SQLITE);
        UserDAO userDAO = sqliteFactory.getUserDAO();
        ArrayList<User> lst = userDAO.getAllUsers();
        if (lst != null) {
            for (User el : lst) {
                MyLog.Msg(el.toString());
            }
        } else {
            MyLog.Msg("getAllUsers - error");
        }*/
        req.getRequestDispatcher(registration).forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            DAOFactory sqliteFactory = DAOFactory.getDAOFactory(DAOFactory.SQLITE);
            UserDAO userDAO = sqliteFactory.getUserDAO();
            User user = new User(User.DEFAULTID,
                    req.getParameter("surname"),
                    req.getParameter("name"),
                    req.getParameter("patronymic"),
                    req.getParameter("sex"),
                    req.getParameter("birth"),
                    req.getParameter("email"),
                    BCrypt.hashpw(req.getParameter("pwd"), BCrypt.gensalt(12)),
                    req.getParameter("tel"),
                    req.getParameter("city"),
                    req.getParameter("street"));
            int id = userDAO.insertUser(user);
            if (id == userDAO.INSERT_ERROR) {
                req.getRequestDispatcher(registration_error).forward(req, resp);
                return;
            }
            if (id == userDAO.UNIQUE_EMAIL) {
                req.getRequestDispatcher(registration_email_exists).forward(req, resp);
                return;
            }
            user.setId(id);
            MyLog.Msg("User added: " + user.toString());
            req.getRequestDispatcher(registration_success).forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.getRequestDispatcher(registration_error).forward(req, resp);
        }
    }
}
