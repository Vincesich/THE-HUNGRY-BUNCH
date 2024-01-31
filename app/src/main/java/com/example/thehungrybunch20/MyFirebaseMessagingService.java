package com.example.thehungrybunch20;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // Handle the received message
        if (remoteMessage.getNotification() != null) {
            // Display the notification message
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            // Process the notification as needed
            showNotification(title, body);
        }

        // If the message contains data payload
        if (remoteMessage.getData().size() > 0) {
            // Extract the data payload
            // Process the data payload as needed
            String dataValue = remoteMessage.getData().get("data_key");
            processData(dataValue);
        }
    }

    private void showNotification(String title, String body) {
        // Code to display the notification
        // You can use the NotificationCompat.Builder class or any other notification mechanism
        // Customize the notification based on the title and body
    }

    private void processData(String dataValue) {
        // Code to process the data payload
        // Perform any required operations or update your app's UI based on the received data
    }
}

