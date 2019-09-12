package org.izdebski;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Map<Integer, String> users = new ConcurrentHashMap<>();
    private AtomicInteger counter;


    @Override
    public void init() throws ServletException {
        super.init();
        users.put(1, "Vanya");
        users.put(2, "Ivan");
        users.put(3, "Vova");
        counter = new AtomicInteger(3);
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Integer id = Integer.parseInt(request.getParameter("id"));
        String user = users.get(id);
        if(user==null){
            user="";
        }

        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println("<h3>user: " + user + "</h3><br>");
        out.print("<a href='javascript:history.back();'>Назад</a>");
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");;

        String name = request.getParameter("name");
        Integer id = null;
        if(!users.containsValue(name)){
            id = counter.incrementAndGet();
            users.put(id, name);
        }

        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        if(id==null) {
            out.println("<h3>user " + name + " уже существует</h3>");
            ;
        }else{
            out.println("<h3>Создан user " + name + "c id=" + counter.get()+"</h3>");
        }
        out.println("<br>");
        out.println("<a href='http://localhost:8080/JavaWeb/user.html'>На главную</a>");
        out.close();
    }
}