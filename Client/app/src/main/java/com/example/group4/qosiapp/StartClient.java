package com.example.group4.qosiapp;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

class StartClient extends AsyncTask<Void, Void, Void> {

    TextView textResponse;
    ProgressBar progressBar;
    Socket client;
    String serverIP = "192.168.1.81"; //change this!!
    int port = 9998;

    public StartClient(ProgressBar progressBar) {
        this.progressBar = progressBar;
        progressBar.setVisibility(View.VISIBLE);
    }

    public StartClient(ProgressBar progressBar, TextView textResponse) {
        this.progressBar = progressBar;
        progressBar.setVisibility(View.VISIBLE);
        this.textResponse = textResponse;
        textResponse.setText("A Ligar ao Servidor ...");
    }

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
            long startTime = System.currentTimeMillis();
            long currentTime = System.currentTimeMillis();
            while(currentTime - startTime < 20000){
                currentTime = System.currentTimeMillis();
            }
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(final Void result){
        // Update your views here
        progressBar.setVisibility(View.GONE);
        textResponse.setText("Ligado ao Servidor");
    }
}

