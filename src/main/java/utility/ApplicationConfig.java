package main.java.ticketGenerator.utility;

import java.io.FileReader;
import java.sql.*;
import java.util.Properties;
import java.io.*;
import java.io.BufferedReader;
import main.java.ticketGenerator.clients.*;
import main.java.ticketGenerator.core.*;
import main.java.ticketGenerator.utility.*;
import org.bson.Document;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.io.*;


public class ApplicationConfig {



    private static Connection connection;
    private static Statement statement;
    private static MongoDatabase database;
    private static MongoCollection < Document > collection;
    private static BasicDBObject updateFields = null;
    private static BasicDBObject setQuery = null;
    private static BasicDBObject whereQuery = null;
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


    public MongoDatabase getDatabase() {
        return database;
    }

    public void setDatabase(MongoDatabase database) {
        ApplicationConfig.database = database;
    }

    public MongoCollection < Document > getCollection() {
        return collection;
    }

    public void setCollection(MongoCollection < Document > collection) {
        ApplicationConfig.collection = collection;
    }

    public BasicDBObject getUpdateFields() {
        return updateFields;
    }

    public void setUpdateFields(BasicDBObject updateFields) {
        ApplicationConfig.updateFields = updateFields;
    }

    public BasicDBObject getSetQuery() {
        return setQuery;
    }

    public void setSetQuery(BasicDBObject setQuery) {
        ApplicationConfig.setQuery = setQuery;
    }

    public BasicDBObject getWhereQuery() {
        return whereQuery;
    }

    public void setWhereQuery(BasicDBObject whereQuery) {
        ApplicationConfig.whereQuery = whereQuery;
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
    public void mongoConnection() {
        MongoClient mongoDBClient = new MongoClient(properties.getProperty("HOST"), Integer.parseInt(properties.getProperty("mongoPort")));
        MongoDatabase database = mongoDBClient.getDatabase("mysql");
        this.setDatabase(database);
        MongoCollection < Document > collection = database.getCollection("Vehicle");
        this.setCollection(collection);
        this.setUpdateFields(new BasicDBObject());
        this.setSetQuery(new BasicDBObject());


    }






}