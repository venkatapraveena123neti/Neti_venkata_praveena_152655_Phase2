package com.cg.walletapplicationphase2.util;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DButil {
	
	/*******************************************************************************************************
	 - Class Name	    :	<DButil>
	 - Input Parameters	:	<null>
	 - Return Type		:	<database details>
	 - Throws			:  	<null>
	 - Author			:	<Neti_Venkata_Praveena>
	 - Creation Date	:	31/07/2018
	 - Description		:	connecting to database
	 ********************************************************************************************************/ 
static Connection con;
static Properties prop;
static
{
	
	try {
		prop=new Properties();
		File file=new File("jdbc.properties");
		FileInputStream fileInputStream=new FileInputStream(file);
		prop.load(fileInputStream);
		
	} catch (Exception exception) {
		// TODO Auto-generated catch block
	System.out.println(exception.getMessage());
	}
}
public static Connection getConnection()
{
	try {

		String driver=prop.getProperty("driver");
		String url=prop.getProperty("url");
		String username=prop.getProperty("username");
		String password=prop.getProperty("password");
		Class.forName(driver);
		try {
			con=DriverManager.getConnection(url,username,password);
		} catch (SQLException sqlexception) {
			// TODO Auto-generated catch block
			System.out.println(sqlexception.getMessage());
		}
	} catch (ClassNotFoundException classNotFoundException) {
		// TODO Auto-generated catch block
		System.out.println(classNotFoundException.getMessage());
	}
	return con;
}
}
