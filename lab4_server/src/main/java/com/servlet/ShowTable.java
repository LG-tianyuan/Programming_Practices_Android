package com.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.dao.PersonDao;
import com.util.Connect;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("serial")
@WebServlet("/ShowTable")
public class ShowTable extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);  
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Type","text/html;character=utf-8");
		PersonDao pd = new PersonDao();
		Connect cn = new Connect();
		Connection con = cn.getConnection();
        JSONObject jsonObject = new JSONObject();
		try {
			List <HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
			list = pd.person_list(con);
			JSONArray json = JSONArray.fromObject(list);
			cn.dis_connect();
			jsonObject.put("list", json);
		} catch (SQLException e) {
			e.printStackTrace(); 
		}
		response.getWriter().print(jsonObject.toString());
	}
}
