package com.example.whatsup_certifier;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener {

    private final String serverIP = "localhost";
    private final int serverPort = 1001;

    private ServerSocket serverConnection;
    private VBox log;
    private SQL database;


    public ServerListener(SQL database, VBox log){

        this.database = database;

        try {

            this.serverConnection = new ServerSocket();
            serverConnection.bind( new InetSocketAddress(serverIP, serverPort) );
            this.log = log;

            Thread newConnections = new Thread(()->{
                while(!serverConnection.isClosed()){
                    try {

                        Socket socket = serverConnection.accept();
                        CertifierController.addLog("Server connected", log);
                        ServerHandler serverHandler = new ServerHandler(socket);
                        Thread connection = new Thread( serverHandler );
                        connection.start();

                    } catch (IOException e){
                        CertifierController.addLog("Error creating server connection", log);
                    }
                }
            });
            newConnections.start();

        } catch(IOException e){
            CertifierController.addLog("Error creating connection",log);
        }

    }

    private class ServerHandler implements Runnable{

        private Socket socket;
        public BufferedWriter bufferedWriter;
        public BufferedReader bufferedReader;

        @Override public void run(){
            listening();
        }

        public ServerHandler(Socket socket){
            try{

                this.socket = socket;
                this.bufferedReader = new BufferedReader( new InputStreamReader( socket.getInputStream() ));
                this.bufferedWriter = new BufferedWriter( new OutputStreamWriter( socket.getOutputStream() ));

            } catch (IOException e){
                stopHandler();
                CertifierController.addLog("Error connecting to server", log);
            }
        }

        public void stopHandler(){
            try{

                if( this.socket.isConnected() ){ this.socket.close(); }
                if( bufferedReader != null){ bufferedReader.close(); }
                if( this.bufferedWriter != null){ bufferedWriter.close(); }

            }catch (IOException e){
                CertifierController.addLog("Error disconnecting from server", log);
            }
        }

        private void listening(){
            Thread listener = new Thread(()->{
                while(socket.isConnected()){
                    try{

                        String request = bufferedReader.readLine();

                        if(request == null){
                            stopHandler();
                            break;
                        }

                        CertifierController.addLog("Server requested certificate with ID: " + request, log);

                        bufferedWriter.write( database.getQuery(request) );
                        bufferedWriter.newLine();
                        bufferedWriter.flush();

                    } catch (IOException e){
                        stopHandler();
                        break;
                    }
                }
            });
            listener.start();
        }

    }



}
