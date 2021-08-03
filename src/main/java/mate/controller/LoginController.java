package mate.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.exception.AuthenticationException;
import mate.lib.Injector;
import mate.model.Driver;
import mate.service.AuthService;

public class LoginController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("mate");
    private final AuthService authService = (AuthService) injector
            .getInstance(AuthService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try {
            Driver driver = authService.login(login, password);
            req.getSession().setAttribute("driver_id", driver.getId());
            req.getSession().setAttribute("username", driver.getName());
            resp.sendRedirect("/");
        } catch (AuthenticationException e) {
            req.setAttribute("errorMsg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }
}