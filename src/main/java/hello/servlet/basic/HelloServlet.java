package hello.servlet.basic;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "helloServlet", urlPatterns = "/hello") // 해당 url에 접속했을 때 실행되는 서블릿이다.
public class HelloServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("HelloServlet.service");
        System.out.println("request = " + request); // request: HttpServletRequest 인터페이스의 구현체
        System.out.println("response = " + response); // response: HttpServletResponse 인터페이스의 구현 객체

        /****** request ******/
        String username = request.getParameter("username"); // 쿼리 파라미터의 인자 값 조회
        System.out.println("username = " + username);

        /****** response ******/
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("hello " + username);

    } // 서블릿이 실행되는 경우 호출되는 메소드이다.
}
