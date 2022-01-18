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
@WebServlet("/ShowInfo")
public class ShowInfo extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);  
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Type","text/html;character=utf-8");
		String username=request.getParameter("username");
		PersonDao pd = new PersonDao();
		Connect cn = new Connect();
		Connection con = cn.getConnection();
		Person person = new Person();
		Map<String,String> params = new HashMap<>();
        JSONObject jsonObject = new JSONObject();
		try {
			person=pd.getInfo(con, username);
			params.put("username", username);
			params.put("name", person.getName());
			params.put("age", person.getAge());
			params.put("tele", person.getTele());
			cn.dis_connect();
			
		} catch (SQLException e) {
			e.printStackTrace(); 
		}
		jsonObject.put("params", params);
		response.getWriter().print(jsonObject.toString());
	}
}
