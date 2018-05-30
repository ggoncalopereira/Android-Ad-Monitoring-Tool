package com.example.group4.qosiapp;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

class StartClient extends AsyncTask<Void, Void, Void> {

    Socket client;
    String serverIP = "192.168.1.81"; //change this!!
    int port = 9998;

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            client = new Socket(serverIP,port);
            System.out.println("Ligado ao servidor");
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));

            //Establishing TCP connection with server
            // Send to the server the option wanted
            PrintWriter out = new PrintWriter(client.getOutputStream(),true);
            out.println("Start");
            out.flush();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }
}
