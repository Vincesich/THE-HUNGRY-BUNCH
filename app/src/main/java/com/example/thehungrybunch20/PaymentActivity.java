package com.example.thehungrybunch20;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;



public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Button madePaymentButton = findViewById(R.id.madePaymentButton);

        madePaymentButton.setOnClickListener(v -> {
            // Return to the Ordering activity
            Intent intent = new Intent(PaymentActivity.this, OrderingActivity.class);
            startActivity(intent);
        });
    }
}
