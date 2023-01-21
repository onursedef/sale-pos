package com.onursedef.salepos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Failure extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.failure);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(Failure.this, MainActivity.class);
            startActivity(intent);
        }, 30 * 1000);
    }
}