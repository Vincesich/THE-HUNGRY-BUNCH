package com.example.thehungrybunch20;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;

public class OrderingActivity extends AppCompatActivity {
    private EditText roomNumberEditText;
    private Spinner mealSpinner;
    private RadioButton deliveryRadioButton;
    private Spinner buildingSpinner;

    private final String[] meals = {"Chicken Choma With Curry Potato Wedges", "Coconut Bean Stew With Rice",
            "Lentil Curry(Ndegu) With Chapati", "Beef Pilau With Kachumbari", "Fried Mutton with Ugali and Greens",
            "Borewors", "Hamburger", "Beef Samosas", "Chicken Samosas", "Vegetable Spring Rolls",};
    private final int[] mealPrices = {250, 250, 250, 250, 250, 150, 300, 50, 80, 50};

    private DatabaseReference ordersRef;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering);

        roomNumberEditText = findViewById(R.id.roomNumberEditText);
        mealSpinner = findViewById(R.id.mealSpinner);
        RadioButton pickupRadioButton = findViewById(R.id.pickupRadioButton);
        deliveryRadioButton = findViewById(R.id.deliveryRadioButton);
        Button orderButton = findViewById(R.id.orderButton);
        buildingSpinner = findViewById(R.id.buildingSpinner);
        String[] buildingOptions = getResources().getStringArray(R.array.building_options);

        ArrayAdapter<CharSequence> buildingAdapter = ArrayAdapter.createFromResource(this,
                R.array.building_options, android.R.layout.simple_spinner_item);
        buildingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        buildingSpinner.setAdapter(buildingAdapter);
        ArrayAdapter<String> mealAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, meals);
        mealSpinner.setAdapter(mealAdapter);

        // Get reference to the "orders" node in the Firebase Realtime Database
        ordersRef = FirebaseDatabase.getInstance().getReference("orders");

        orderButton.setOnClickListener(v -> {
            String roomNumber = roomNumberEditText.getText().toString().trim();

            // Check if room number is empty
            if (roomNumber.isEmpty()) {
                roomNumberEditText.setError("Please enter a room number");
                return;
            }

            String selectedMeal = mealSpinner.getSelectedItem().toString();

            // Check if no meal is selected
            if (selectedMeal.equals("Select a meal")) {
                Toast.makeText(this, "Please select a meal", Toast.LENGTH_SHORT).show();
                return;
            }

            int mealPrice = mealPrices[mealSpinner.getSelectedItemPosition()];
            boolean isDelivery = deliveryRadioButton.isChecked();

            // Generate registration token
            generateRegistrationToken();

            // Create a unique key for the order
            String orderId = ordersRef.push().getKey();

            // Create an Order object with the order details
            Order order = new Order(orderId, roomNumber, selectedMeal, mealPrice, isDelivery, buildingSpinner.getSelectedItem().toString());

            // Write the order object to the database
            ordersRef.child(orderId).setValue(order)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Handle the success response
                            Toast.makeText(OrderingActivity.this, "Order placed successfully", Toast.LENGTH_SHORT).show();

                            // Pass the selected order details to the Checkout activity
                            Intent intent = new Intent(OrderingActivity.this, CheckoutActivity.class);
                            intent.putExtra("roomNumber", roomNumber);
                            intent.putExtra("selectedMeal", selectedMeal);
                            intent.putExtra("mealPrice", mealPrice);
                            intent.putExtra("isDelivery", isDelivery);
                            intent.putExtra("selectedBuilding", buildingSpinner.getSelectedItem().toString());

                            // Start the Checkout activity
                            startActivity(intent);
                        } else {
                            // Handle the error response
                            Toast.makeText(OrderingActivity.this, "Failed to place order", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

    private void generateRegistrationToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (task.isSuccessful()) {
                            // Get the registration token
                            String token = task.getResult();

                            // Store the registration token on your server or send it along with the order details
                            // for the seller to use when sending notifications

                            // For demonstration purposes, you can log the registration token
                            Log.d("RegistrationToken", token);
                        } else {
                            // Handle error in getting the registration token
                            Log.e("RegistrationToken", "Failed to generate token: " + Objects.requireNonNull(task.getException()).getMessage());
                        }
                    }
                });
    }

    // ...
}



