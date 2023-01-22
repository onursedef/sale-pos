package com.onursedef.salepos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class WaitingForResponse extends AppCompatActivity {

    Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiting_for_response);

        new ConnectTask().execute();

        String productName = getIntent().getStringExtra("name");
        String productPrice = getIntent().getStringExtra("price");
        String productCode = getIntent().getStringExtra("code");


        String respJson = "{\"type\": \"BANK_APPROVE_PENDING\", \"name\": \"" + productName + "\",\"price\": " + productPrice + ", \"code\": \"" + productCode + "\"}";

        new SendTask().execute(respJson);
    }

    class ConnectTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                socket = new Socket("192.168.1.8", 8000);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    class SendTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... messages) {
            try {
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(messages[0].getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}