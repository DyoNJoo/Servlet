package com.mul.campus.board;

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

@WebServlet("/BoardList")
public class BoardList extends HttpServlet {
    public BoardList() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.println("<!DOCTYPE html>");
		pw.println("<html><head><title>게시판목록</title>");
		pw.println("<link rel='stylesheet' href='"+request.getContextPath()+"/inc/boardStyle.css' type='text/css'/>");
		pw.println("</head><body>");
		pw.println("<main><h1>게시판목록</main></h1>");
		
		//글쓰기
		String status = (String)request.getSession().getAttribute("logStatus");
		if(status!=null && status.equals("Y")) {
			pw.println("<div><a href='"+request.getContextPath()+"/board/write.do'>글쓰기</a></div>");
		}
		
		//리스트제목
		pw.println("<ul class='list'>");
		pw.println("<li>번호</li><li>제목</li><li>글쓴이</li><li>조회수</li><li>등록일</li>");
		
		//------------------------------------------------------------------------------
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/mydb", "root", "tiger1234");
			String sql = "select no, subject, userid, hit, date_format(writedate, '%m-%d %h:%i') writedate " + "from board order by no desc";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				pw.println("<li>" + rs.getInt(1)+"</li>");
				pw.println("<li><a href='"+request.getContextPath()+"/board/view.do?no="+rs.getInt(1)+"'>"+rs.getString(2)+"</a></li>");
				pw.println("<li>"+rs.getString(3)+"</li>");
				pw.println("<li>"+rs.getInt(4)+"</li>");
				pw.println("<li>"+rs.getString(5)+"</li>");
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
		
		pw.println("</ul>");
		pw.println("</main></body></html>");
		pw.flush();
		pw.close();
	}
}
