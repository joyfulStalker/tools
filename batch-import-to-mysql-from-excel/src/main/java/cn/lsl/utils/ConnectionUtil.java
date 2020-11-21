package cn.lsl.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	
	private static final String url = "jdbc:mysql://47.92.167.148:3306/study?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&rewriteBatchedStatements=true";
	
	private static final String user = "study";
	
	private static final String password = "study";
	
	private static final String driverClass = "com.mysql.cj.jdbc.Driver";

	private static Connection conn = null;

	/**
	 * 
	* 获取Connection
	* @author liusonglin
	* @date 2019年3月19日
	* @return
	* @throws ClassNotFoundException
	* @throws SQLException
	 */
	public static Connection getConn() throws ClassNotFoundException, SQLException {

		if (conn == null) {
			
			Class.forName(driverClass);
			
			conn = DriverManager.getConnection(url, user, password);
		}

		return conn;

	}

}
