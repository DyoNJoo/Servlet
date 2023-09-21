package com.mul.campus;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/init.do")
public class InitParamTest extends HttpServlet {
	String userid;
	String username;
	
    public InitParamTest() {
        super();
    }

	public void init(ServletConfig config) throws ServletException {
		//web.xml의 매핑에 선언한 변수의 값을 서블릿클래스로 가져오기
		userid = config.getInitParameter("userid");
		username = config.getInitParameter("username");
		
		System.out.println("아이디 = " + userid + ", 사용자 이름 = " + username);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter pw = response.getWriter();
		
		pw.print("<!DOCTYPE html><html><head><title>init테스트중</title></head>");
		pw.print("<body>");
		pw.print("<h1>아이디 : " + userid + ", 이름 : " + username + "</h1>");
		pw.print("</body></html>");
		pw.flush();
		pw.close();
	}

}
