package com.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.entity.User;

public class UserDao {
	public void users_Increase(Connection con,User usr) throws SQLException{
		Statement sta;
		sta=con.createStatement(); 
		String sql="insert into users values('"+usr.getUsername()+"','"+usr.getPwd()+"')";
		sta.executeUpdate(sql);
	}
	public String delete_user(Connection con,String username) throws SQLException {
		String str="Success!";
		Statement sta,state;
		sta=con.createStatement();
	    String sql="select * from users";          
	    ResultSet rs=sta.executeQuery(sql); 
	    boolean flag=false;
	    while(rs.next()){    
	    	if(rs.getString(1).equals(username)) {   
	    		state=con.createStatement();
	            sql="delete from users where username='"+username+"'";   //SQLÓï¾ä
	            state.executeUpdate(sql);
	            sql="delete from person where username='"+username+"'";   //SQLÓï¾ä
	            state.executeUpdate(sql);
	            flag=true;
	    	}
	    }
	    rs.close();
	    if(!flag) {str="Fail!";}
	    return str;
	}
	public void delete_test(Connection con) throws SQLException {
		Statement sta,state;
		sta=con.createStatement();
	    String sql="select * from users";          
	    ResultSet rs=sta.executeQuery(sql); 
	    while(rs.next()){    
	    	if(rs.getString(1).indexOf("test")!=-1) { 
	    		state=con.createStatement();
	            sql="delete from users where username='"+rs.getString(1)+"'";   //SQLÓï¾ä
	            state.executeUpdate(sql);
	            PersonDao pd = new PersonDao();
	            pd.delete_person(con, rs.getString(1));
	    	}
	    }
	    rs.close();
	}
	public List<User> display_users(Connection con) throws SQLException {
		List<User> list = new ArrayList<User>();
		Statement sta;
		sta=con.createStatement();
	    String sql="select * from users";          
	    ResultSet rs=sta.executeQuery(sql); 
	    while(rs.next()){ 
	    	String username=rs.getString(1);
	    	String password=rs.getString(2);
	    	User usr=new User(username,password);
	    	list.add(usr);
	    }
	    rs.close();
		return list;
	}
	public  int check_user(Connection con,String username) throws SQLException {
		Statement sta;
		sta=con.createStatement(); 
		String sql="select * from users";          
	    ResultSet rs=sta.executeQuery(sql); 
	    boolean flag=false;
	    if(username.equals("test")) {
	    	while(rs.next()){    
		    	if(rs.getString(1).indexOf("test")!=-1) {
		    		flag=true;
		            break;
		    	}
	    	}
	    }else {
		    while(rs.next()){    
		    	if(rs.getString(1).equals(username)) {   
		            flag=true;
		            break;
		    	}
		    }
	    }
	    rs.close();
	    if(flag) {
	    	return 1;
	    }else {
	    	return 0;
	    }
	}
	public String login_check(Connection con, User usr) throws SQLException {
		String message="Success!";
		boolean flag1 = false, flag2 = false;
		Statement sta;
		sta=con.createStatement(); 
		String sql="select * from users";          
	    ResultSet rs=sta.executeQuery(sql);
	    while(rs.next()){    
	    	if(rs.getString(1).equals(usr.getUsername())) {   
	            flag1=true;
	            if(!rs.getString(2).equals(usr.getPwd())) {
	            	flag2=true;
	            }
	            break;
	    	}
	    }
	    if(!flag1) {
	    	message="The user does not exist!";
	    }else if(flag2) {
	    	message="Incorrect password!";
	    }else {
	    	message="Success!";
	    }
		return message;
	}
	public void ResetPassword(Connection con,User usr) throws SQLException {
		Statement sta;
		sta=con.createStatement(); 
		String sql="update users set pass='"+usr.getPwd()+"' where username='"+usr.getUsername()+"'";
		sta.executeUpdate(sql);
	}
}
