package com.mul.campus;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login.do")
public class LoginServlet extends HttpServlet {       
    public LoginServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = response.getWriter();
        
        pw.println("<!DOCTYPE html>");
        pw.println("<html><head><title>로그인</title>");
        
        pw.println("<style>");
        pw.println(".logForm { width:600px; margin:0 auto; margin:100px; padding:30px; background-color:#ddd; }");
        pw.println(".logForm ul { margin: 0; padding: 0; list-style-type: none; }");
        pw.println(".logForm li { padding: 10px 0px; }");
        pw.println(".logForm input { width: 100%; }");
        pw.println("</style>");
        
        pw.println("<script>");
        pw.println("function logCheck() {");
        pw.println("    if(document.getElementById('userid').value == '') {");
        pw.println("        alert('아이디를 입력하세요.'); return false;");
        pw.println("    }");
        pw.println("    if(document.getElementById('userpwd').value == '') {");
        pw.println("        alert('비밀번호를 입력하세요.'); return false;");
        pw.println("    }");
        pw.println("    return true;");
        pw.println("}");
        pw.println("</script>");
        pw.println("</head><body>");
        pw.println("<div class='logForm'>"); // 여기서 수정: 닫는 따옴표 추가
        pw.println("<h1>로그인 폼</h1>");
        pw.println("<form method='post' action='" + request.getContextPath() + "/login.do' onsubmit='return logCheck()'>"); // 여기서 수정: post와 action 간 빈칸 수정
        pw.println("<ul>");
        pw.println("<li>아이디</li>");
        pw.println("<li><input type='text' name='userid' id='userid'/></li>");
        pw.println("<li>비밀번호</li>");
        pw.println("<li><input type='password' name='userpwd' id='userpwd'/></li>");
        pw.println("<li><input type='submit' value='Login'/></li>");
        pw.println("</ul>");
        pw.println("</form>");
        pw.println("</div>");
        pw.println("</body></html>");
        
        pw.flush();
        pw.close();
    }

	//로그인 DB처리
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//폼에 입력된 아이디와 비밀번호를 서버로 가져오기
		String userid = request.getParameter("userid");
		String userpwd = request.getParameter("userpwd");
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			//1. jdbc드라이브 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//2. DB연결
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/mydb","root","tiger1234");
			
			//3. PreparedStatement	
			String sql = "select userid, username from users where userid=? and userpwd=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			pstmt.setString(2, userpwd);
			//4. 실행
			rs = pstmt.executeQuery();
			
			//5. 결과
			if(rs.next()) { //로그인 됨
				//request객체내에 session객체를 얻어올 수 있다.
				HttpSession session = request.getSession();
				session.setAttribute("logId", rs.getString(1));
				session.setAttribute("logName", rs.getString(2));
				session.setAttribute("logStatus", "Y");
				
				pw.println("<script>");
				pw.println("alert('로그인이 성공하였습니다.');");
				pw.println("location.href='"+request.getContextPath()+"/home.do'");
				pw.println("</script>");
			} else { //로그인 안됨 -> 이전페이지
				pw.println("<script>");
				pw.println("alert('로그인에 실패하였습니다.');");
				pw.println("</script>");
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (Exception e) {}
		}
		pw.flush();
		pw.close();
	}

}

