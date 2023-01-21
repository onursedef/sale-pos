package com.onursedef.salepos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;

import io.socket.client.IO;
import io.socket.client.Socket;

public class WaitingForResponse extends AppCompatActivity {

    URI wsUri = URI.create("http://192.168.1.8:3000");
    Socket mSocket = IO.socket(wsUri);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiting_for_response);

        mSocket.connect();

        mSocket.on(Socket.EVENT_CONNECT, args -> {
            Log.i("websocket", "client connected");
        });

        mSocket.on(Socket.EVENT_CONNECT_ERROR, args -> {
            Log.e("websocket", "couldn't connect");
        });

        mSocket.on("message", args -> {
            Log.i("websocket", args[0].toString());
            String message = args[0].toString();
            try {
                JSONObject json = new JSONObject(message);
                String type = json.getString("type");
                if (type.equals("CONN_START")) {
                    Log.i("wsCon", "true");
                }

                if (type.equals("BANK_APPROVED") || type.equals("QRCODE_APPROVED"))  {
                    Intent intent = new Intent(WaitingForResponse.this, Success.class);
                    startActivity(intent);
                }

                if (type.equals("BANK_DECLINED") || type.equals("QRCODE_DECLINED")) {
                    Intent intent = new Intent(WaitingForResponse.this, Failure.class);
                    startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }
}