package com.onursedef.salepos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.UUID;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {

    URI wsUri = URI.create("http://192.168.1.8:3000");
    Socket mSocket = IO.socket(wsUri);

    EditText productNameInput;
    EditText productPriceInput;
    Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        productNameInput = findViewById(R.id.productName);
        productPriceInput = findViewById(R.id.productPrice);
        sendButton = findViewById(R.id.button);
        Gson g = new Gson();

        sendButton.setOnClickListener(view -> {
            String productName = productNameInput.getText().toString();
            String productPrice = productPriceInput.getText().toString();
            UUID code = UUID.randomUUID();

            String paymentSend = "{\"type\": \"PRODUCT_PAYMENT_SEND\", \"name\": \"" + productName + "\",\"price\": " + productPrice + ", \"code\": \"" + code + "\"}";

            mSocket.send(paymentSend);

            Intent intent = new Intent(MainActivity.this, WaitingForResponse.class);
            startActivity(intent);
        });
    }
}