package com.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.dao.PersonDao;
import com.entity.Person;
import com.util.Connect;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;

@SuppressWarnings("serial")
@WebServlet("/Verify")
public class Verify extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);  
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Type","text/html;character=utf-8");
		String username=request.getParameter("username");
		String tel = request.getParameter("tel"); 
		PersonDao pd = new PersonDao();
		Connect cn = new Connect();
		Connection con = cn.getConnection();
		Person person = new Person(username,"null","null",tel);
		Map<String,String> params = new HashMap<>();
        JSONObject jsonObject = new JSONObject();
		try {
			boolean flag=pd.check_person_tel(con, person);
			if(flag) {
				params.put("result", "Success!");
			}else {
				params.put("result", "Tel not matched!");
			}
			cn.dis_connect();
		} catch (SQLException e) {
			e.printStackTrace(); 
		}
		jsonObject.put("params", params);
		response.getWriter().print(jsonObject.toString());
	}
}
