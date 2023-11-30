package com.example.whatsup_certifier;

import java.sql.*;

public class SQL {

    private static final String user = "root";
    private static final String password = "root";
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

    public String getQuery (String ID){
        String query = "SELECT * FROM certificados WHERE ID=" + ID;

        try{
            Statement statement = connection.createStatement();
            ResultSet queryOutput = statement.executeQuery(query);

            if(queryOutput.next()){
                String queryResult = queryOutput.getString("private_key") + ";" + queryOutput.getString("public_key");
                System.out.println(queryResult);
                return queryResult;
            } else {
                return "Certificate does not exist";
            }

        } catch (Exception e){
            return "SQL Error";
        }
    }

}
