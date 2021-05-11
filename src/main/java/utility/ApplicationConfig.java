package main.java.ticketGenerator.utility;

import java.io.FileReader;
import java.sql.*;
import java.util.Properties;
import java.io.*;
import java.io.BufferedReader;
import main.java.ticketGenerator.clients.*;
import main.java.ticketGenerator.core.*;
import main.java.ticketGenerator.utility.*;
import java.io.*;

public class ApplicationConfig {



    private static Connection connection;
    private static Statement statement;
    private Properties properties = null;
    private String client;
   




    public ApplicationConfig() {
        try {
            properties = new Properties();

            properties.load(new FileReader("src/main/resources/config.properties"));


            this.client = properties.getProperty("CLIENT");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }






    public Properties getProperties() {
        return properties;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        ApplicationConfig.connection = connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        ApplicationConfig.statement = statement;
    }

    public String getClient() {
        return client;
    }


    


    public void mysqlConnection() {
        try {
            Class.forName(properties.getProperty("DRIVER"));
            this.setConnection(DriverManager.getConnection(properties.getProperty("URL"), properties.getProperty("USER"), properties.getProperty("PASSWORD")));
            this.setStatement(connection.createStatement());
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    

   




}