package com.onursedef.salepos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    EditText productNameInput;
    EditText productPriceInput;
    Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productNameInput = findViewById(R.id.productName);
        productPriceInput = findViewById(R.id.productPrice);
        sendButton = findViewById(R.id.button);

        sendButton.setOnClickListener(view -> {
            String productName = productNameInput.getText().toString();
            String productPrice = productPriceInput.getText().toString();
            UUID code = UUID.randomUUID();

            Toast.makeText(MainActivity.this, "Request Sent!", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra("name", productName);
            intent.putExtra("price", productPrice);
            intent.putExtra("code", code.toString());
            intent.setPackage("com.onursedef.paymentpos");
            startActivity(intent);
        });
    }
}