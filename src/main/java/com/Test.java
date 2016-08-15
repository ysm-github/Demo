package com;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	static AbstractApplicationContext ctx=new ClassPathXmlApplicationContext("springContext.xml");
	public static void main(String[] args)  throws Exception{
		System.out.println("ctx :" +ctx);
		//1.test jdbc batch sql
		testJDBC();
		
		//2.
		
		ctx.destroy();
		ctx.close();
	}
	public static void testJDBC() throws Exception{
		DataSource ds=(DataSource)ctx.getBean("dataSource");
		Connection conn=ds.getConnection();
//		conn.setAutoCommit(false);
		String sql_ps="insert into c3p0_test values(?)";
		String sql_st="insert into c3p0_test values('%s')";
		PreparedStatement ps=conn.prepareStatement(sql_ps);
		Statement st=conn.createStatement();
//		ps.setString(1, "1");
//		ps.executeUpdate();
//		
//		ps.setString(1, "2");
//		ps.executeUpdate();
		
		for(int i=65;i<=90;i++){
			ps.setString(1, (char)i+"");
//			ps.executeUpdate();
//			st.executeUpdate(String.format(sql_st, (char)i+""));
			
			ps.addBatch();
			st.addBatch(String.format(sql_st, (char)i+""));
		}
		int rows=st.executeBatch().length;
		 rows=ps.executeBatch().length;
		System.out.println("rows :"+rows);
//		conn.commit();
		ps.close();
		st.close();
		conn.close();
	}
}
