package com.example.thehungrybunch20;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;


public class CheckoutActivity extends AppCompatActivity {
    private TextView buildingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        TextView roomNumberTextView = findViewById(R.id.roomNumberTextView);
        TextView mealTextView = findViewById(R.id.mealTextView);
        TextView amountTextView = findViewById(R.id.amountTextView);
        Button payButton = findViewById(R.id.payButton);
        buildingTextView = findViewById(R.id.buildingTextView); // Initialize buildingTextView

        Intent intent = getIntent();
        final String roomNumber = intent.getStringExtra("roomNumber");
        String selectedMeal = intent.getStringExtra("selectedMeal");
        int mealPrice = intent.getIntExtra("mealPrice", 0);
        boolean isDelivery = intent.getBooleanExtra("isDelivery", false);
        String selectedBuilding = intent.getStringExtra("selectedBuilding");
        int totalAmount = mealPrice;


        if (isDelivery) {
            totalAmount += 20;
        }

        roomNumberTextView.setText(roomNumber);
        mealTextView.setText(selectedMeal);
        amountTextView.setText(String.valueOf(totalAmount));
        buildingTextView.setText(selectedBuilding);

        payButton.setOnClickListener(v -> {
            // Pass the payment details to the Payment activity
            Intent intent1 = new Intent(CheckoutActivity.this, PaymentActivity.class);
            startActivity(intent1);

        });
    }
}

