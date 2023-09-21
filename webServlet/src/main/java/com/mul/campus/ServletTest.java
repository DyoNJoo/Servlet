package com.mul.campus;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/home.do")
public class ServletTest extends HttpServlet {
    public ServletTest() {
        super();
    }
    
    public void init(ServletConfig config) throws ServletException {
    	System.out.println("init()메소드 실행됨");
    }
    
    public void destroy() {
    	System.out.println("destroy()메소드 실행됨");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doGet()메소드 실행됨");
        // 클라이언트 측에서 서버로 보낸 데이터 얻어오기
        String name = request.getParameter("name");
        //int age = Integer.parseInt(request.getParameter("age"));
        int age = 25;
        System.out.println("이름 : " + name);
        
        // 서버에서 클라이언트 측으로 정보 내보내기
        // 응답하는 내용이 문서라는 표시와 인코딩 정보를 세팅
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter pw = response.getWriter();
        
        pw.println("<!DOCTYPE html>");
        pw.println("<html>");
        pw.println("<head>");
        pw.println("<title>서블릿홈테스트중</title>");
        pw.println("<style>");
        pw.println(".c1 { background-color:pink; }");
        pw.println("</style>");
        pw.println("<script>");
        pw.println("function clk() {");
        pw.println("alert('클릭을 선택하였습니다.');");
        pw.println("}");
        pw.println("</script>");
        pw.println("</head>");
        pw.println("<body>");
        pw.println("<h1 onclick = 'clk()'>서블릿 홈페이지</h1>");
        pw.println("<ul><li>이름 = " + name + " </li>");
        pw.println("<li>나이 = " + age + "세 </li></ul>");
        pw.println("<div style='border:1px solid #ddd;padding:50px;margin:50px;'><a href=''></a>");
        
        //로그인 여부따라 로그인, 로그아웃 표시하기
        HttpSession ses = request.getSession();
        String logStatus = (String)ses.getAttribute("logStatus");
        String logName = (String)ses.getAttribute("logName");
        
        if(logStatus==null || !logStatus.equals("Y")) {
        	pw.println("<a href='"+request.getContextPath()+"/login.do'>로그인</a>");
        } else if (logStatus!=null && logStatus.equals("Y")) {
        	pw.println(logName + "님 <a href='"+request.getContextPath()+"/logout.do'>로그아웃</a>");
        }
        
        pw.println("<a href='"+request.getContextPath()+"/board/list.do'>게시판목록</a>");

        pw.println("</body>");
        pw.println("</html>");
        
        pw.flush();
        pw.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doPost()메소드 실행됨");
    }
}
