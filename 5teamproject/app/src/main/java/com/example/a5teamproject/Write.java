package com.example.a5teamproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.analytics.FirebaseAnalytics;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Write extends AppCompatActivity {

    EditText editText;
    Button button;
    private FirebaseAnalytics firebaseAnalytics;
    private DatabaseReference mDatabase;
    Login lgn = new Login();

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        editText = findViewById(R.id.write);
        button = findViewById(R.id.btn_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getText = editText.getText().toString();

                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        UserWrite user = new UserWrite(getText);

                        //mDatabase.child(lgn.userID).setValue(user);
                        Toast.makeText(getApplicationContext(), "저장되었습니다.", Toast.LENGTH_LONG).show();
                        finish();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            };
        });
    }
}
