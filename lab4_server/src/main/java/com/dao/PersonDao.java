package com.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.entity.Person;
import com.entity.User;

public class PersonDao {
	public void person_Increase(Connection con,Person person) throws SQLException {
		Statement sta;
		sta=con.createStatement();
	    String sql;          
	    if(person.getAge().equals("")) {
	    	if(person.getTele().equals("")) {
	    		sql="insert into person (username,name) values('"+person.getUsername()+"','"+person.getName()+"')";
	    	}
	    	else {sql="insert into person (username,name,teleno) values('"+person.getUsername()+"','"+person.getName()+"','"+person.getTele()+"')";}
	    }
	    else{
	    	if(person.getTele().equals("")) {
	    		sql="insert into person (username,name,age) values('"+person.getUsername()+"','"+person.getName()+"','"+person.getAge()+"')";
	    	}
	    	else {sql="insert into person values('"+person.getUsername()+"','"+person.getName()+"','"+person.getAge()+"','"+person.getTele()+"')";}
	    }
	    sta.executeUpdate(sql);
	}
	public void delete_person(Connection con,String username) throws SQLException {
		Statement sta,state;
		sta=con.createStatement();
	    String sql="select * from person";          
	    ResultSet rs=sta.executeQuery(sql); 
	    while(rs.next()){    
	    	if(rs.getString(1).equals(username)) {
	    		state=con.createStatement();   
	            String sql1="delete from person where username='"+username+"'";   //SQL语句
	            state.executeUpdate(sql1);
	    	}
	    }
	    rs.close();
	}
	public void Person_update(Connection con, Person person) throws SQLException {
		Statement sta,state;
		sta=con.createStatement();
	    String sql="select * from person";          
	    ResultSet rs=sta.executeQuery(sql);      
	    while(rs.next()){    
	    	if(rs.getString(1).equals(person.getUsername())) {
	    		if(person.getName().equals("")) person.setName(rs.getString(2));
	    		if(person.getAge().equals("")&&(rs.getString(3)!=null)) person.setAge(rs.getString(3));
	    		if(person.getTele().equals("")&&(rs.getString(4)!=null)) person.setTele(rs.getString(4));
	    		state=con.createStatement();
	            String sql1;
	            if(person.getAge().equals(""))
	            {
	            	if(person.getTele().equals("")) {
	            		sql1="update person set name='"+person.getName()+"' where username='"+person.getUsername()+"'";
	            	}else {
	            		sql1="update person set name='"+person.getName()+"',teleno='"+person.getTele()+"' where username='"+person.getUsername()+"'";
	            	}
	            }else {
	            	if(person.getTele().equals("")) {
	            		sql1="update person set name='"+person.getName()+"',age='"+person.getAge()+"' where username='"+person.getUsername()+"'";
	            	}else {
	            		sql1="update person set name='"+person.getName()+"',age='"+person.getAge()+"',teleno='"+person.getTele()+"' where username='"+person.getUsername()+"'"; 
	            	}
	            }
	            state.executeUpdate(sql1);         //将sql语句上传至数据库执行
	    	}
	    }
	    rs.close();
	}
	public String Person_add(Connection con, Person person,User usr) throws SQLException {
		String message = "Success!";
		boolean flag1 = false, flag2 = false;
		Statement sta;
		sta=con.createStatement();
	    String sql="select * from person";          
	    ResultSet rs=sta.executeQuery(sql);
	    while(rs.next()) {
	    	String str1=rs.getString(2).toLowerCase();
	    	String str2=person.getName().toLowerCase();
	    	if(str1.equals(str2)) {
	    		flag1 = true;
	    		break;
	    	}
	    }
	    sql="select * from users";          
	    rs=sta.executeQuery(sql);
	    while(rs.next()) {
	    	String str1=rs.getString(1).toLowerCase();
	    	String str2=person.getUsername().toLowerCase();
	    	if(str1.equals(str2)) {
	    		flag2 = true;
	    		break;
	    	}
	    }
	    rs.close();
	    if(flag1 && flag2){
	    	message="Fail! The username and the name both have been registered!";
	    }else if(flag1 && !flag2) {
	    	message="Fail! The name has been registered!";
	    }else if(!flag1 && flag2) {
	    	message="Fail! The username has been registered!";
	    }else {
	    	UserDao ud = new UserDao();
    		ud.users_Increase(con,usr);
    		person_Increase(con,person);
	    }
		return message;
	}
	/*public String Person_insert(Connection con,Person person) throws SQLException {
		String operate="更新";
		Statement sta,state;
		sta=con.createStatement();
	    String sql="select * from person";          
	    ResultSet rs=sta.executeQuery(sql);      
	    int flag=0;
	    while(rs.next()){    
	    	if(rs.getString(1).equals(person.getUsername())) {
	    		if(person.getName().equals("")) person.setName(rs.getString(2));
	    		if(person.getAge().equals("")&&(rs.getString(3)!=null)) person.setAge(rs.getString(3));
	    		if(person.getTele().equals("")&&(rs.getString(4)!=null)) person.setTele(rs.getString(4));
	    		state=con.createStatement();
	            String sql1;
	            if(person.getAge().equals(""))
	            {
	            	if(person.getTele().equals("")) {
	            		sql1="update person set name='"+person.getName()+"' where username='"+person.getUsername()+"'";
	            	}else {
	            		sql1="update person set name='"+person.getName()+"',teleno='"+person.getTele()+"' where username='"+person.getUsername()+"'";
	            	}
	            }else {
	            	if(person.getTele().equals("")) {
	            		sql1="update person set name='"+person.getName()+"',age='"+person.getAge()+"' where username='"+person.getUsername()+"'";
	            	}else {
	            		sql1="update person set name='"+person.getName()+"',age='"+person.getAge()+"',teleno='"+person.getTele()+"' where username='"+person.getUsername()+"'"; 
	            	}
	            }
	            state.executeUpdate(sql1);         //将sql语句上传至数据库执行
	            flag=1;
	    	}
	    }
	    if(flag==0) {
	    	sql="select * from users";
	    	rs=sta.executeQuery(sql);
	    	boolean flag1=false;
	    	while(rs.next()){    
		    	if(rs.getString(1).equals(person.getUsername())) {
		    		flag1=true;
		    		break;
		    	}
	    	}
	    	if(!flag1){
	    		User usr = new User(person.getUsername(),"888888");
	    		UserDao ud = new UserDao();
	    		ud.users_Increase(con,usr);
	    	}
	    	person_Increase(con,person);
	    	operate="插入";
	    }
	    rs.close();
	    return operate;
	}*/
	public List<Person> display_person(Connection con) throws SQLException{
		List<Person> list = new ArrayList<Person>();
		Statement sta;
		sta=con.createStatement();
	    String sql="select * from person";
	    ResultSet rs=sta.executeQuery(sql);
	    while(rs.next()){ 
	    	String username=rs.getString(1);
	    	String name=rs.getString(2);
	    	String age=rs.getString(3);
	    	String telenumber=rs.getString(4);
	    	if(age==null) age="";
	    	if(telenumber==null) telenumber="";
	    	Person prn=new Person(username,name,age,telenumber);
	    	list.add(prn);
	    }
		return list;
	}
	public List<HashMap<String,String>> person_list(Connection con) throws SQLException{
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		Statement sta;
		sta=con.createStatement();
	    String sql="select * from person";
	    ResultSet rs=sta.executeQuery(sql);
	    while(rs.next()){ 
	    	String username=rs.getString(1);
	    	String name=rs.getString(2);
	    	String age=rs.getString(3);
	    	String telenumber=rs.getString(4);
	    	if(age==null) age="";
	    	if(telenumber==null) telenumber="";
	    	Map<String,String> prn = new HashMap<>();
	    	prn.put("username",username);
	    	prn.put("name", name);
	    	prn.put("age", age);
	    	prn.put("tel", telenumber);
	    	list.add((HashMap<String, String>) prn);
	    }
		return list;
	}
	public  boolean check_person(Connection con,Person person) throws SQLException {
		Statement sta;
		sta=con.createStatement(); 
		String sql="select * from person";          
	    ResultSet rs=sta.executeQuery(sql); 
	    boolean flag=true;
	    while(rs.next()){    
	    	if(rs.getString(2).equals(person.getName())&&!rs.getString(1).equals(person.getUsername())) {   
	            flag=false;
	            break;
	    	}
	    }
	    rs.close();
	    return flag;
	}
	public Person getInfo(Connection con,String username) throws SQLException {
		Person person=new Person();
		Statement sta;
		sta=con.createStatement(); 
		String sql="select * from person";          
	    ResultSet rs=sta.executeQuery(sql); 
	    while(rs.next()){    
	    	if(rs.getString(1).equals(username)) { 
		    	String age=rs.getString(3);
		    	String telenumber=rs.getString(4);
		    	if(age==null) age="";
		    	if(telenumber==null) telenumber="";
	    		person.setUsername(rs.getString(1));
	            person.setName(rs.getString(2));
	            person.setAge(age);
	            person.setTele(telenumber);
	    	}
	    }
	    rs.close();
	    return person;
	}
	public  boolean check_person_tel(Connection con,Person person) throws SQLException {
		Statement sta;
		sta=con.createStatement(); 
		String sql="select * from person";          
	    ResultSet rs=sta.executeQuery(sql); 
	    boolean flag=false;
	    while(rs.next()){    
	    	if(rs.getString(1).equals(person.getUsername())&&rs.getString(4).equals(person.getTele())) {   
	            flag=true;
	            break;
	    	}
	    }
	    rs.close();
	    return flag;
	}
}
