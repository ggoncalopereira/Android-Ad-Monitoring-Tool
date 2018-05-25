package com.example.group4.qosiapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    public MainActivity() throws IOException {
        String serverIP = "localhost";

        //Establishing TCP connection with server
        try (Socket client = new Socket(serverIP, 9999)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));

            // Send to the server the option wanted
            PrintWriter out = new PrintWriter(client.getOutputStream(),true);
            out.println("Start");
            out.flush();
            //Init background process
        }


        //Receiving data and present them in screen


        /**Create graphic with statics like:
         * how many websites were distinct visited (distinct because some websites load ads from another locations)
         * percentage of ads in total
         * percentage per website ???
         * bandwidth consumption by the ads ???
         */

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}