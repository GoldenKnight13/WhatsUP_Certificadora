package com.example.whatsup_certifier;

import java.sql.*;

public class SQL {

    private static final String user = "root";
    private static final String password = "toor";
    private final String database = "Certificadora";
    public Connection connection;

    public SQL(){
        connection = getConnection();
    }

    public Connection getConnection(){
        String URL = "jdbc:mysql://localhost:3306/" + database;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, user, password);
            System.out.println("Success");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
            System.out.println("Class error");
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("SQL error");
        }

        return connection;
    }

    public void getQuery (String ID){
        String query = "SELECT * FROM  certificados";

        try{
            Statement statement = connection.createStatement();
            ResultSet queryOutput = statement.executeQuery(query);

            while(queryOutput.next()){
                System.out.println(queryOutput);
            }

        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Query error");
        }
    }

}
