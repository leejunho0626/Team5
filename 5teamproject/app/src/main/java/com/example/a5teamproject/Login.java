package com.example.a5teamproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Login extends AppCompatActivity {

    private FirebaseAnalytics firebaseAnalytics;
    EditText user_id,user_password, user_name, user_birth;
    Button btn_save;

    private DatabaseReference mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        user_id = (EditText)findViewById(R.id.user_id);
        user_password = (EditText)findViewById(R.id.user_password);
        user_name = (EditText)findViewById(R.id.user_name);
        user_birth = (EditText)findViewById(R.id.user_birth);
        btn_save = (Button)findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getID = user_id.getText().toString();
                String getPassword = user_password.getText().toString();
                String getName = user_name.getText().toString();
                String getBirth = user_birth.getText().toString();

                HashMap result = new HashMap<>();
                result.put("id", getID);
                result.put("password", getPassword);
                result.put("name", getName);
                result.put("birth", getBirth);

                //writeNewUser(getID,getPassword,getName,getBirth);
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child(user_id.getText().toString()).exists()){
                            Toast.makeText(Login.this, "이미 등록된 ID입니다.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            User user = new User(getID,getPassword,getName,getBirth);
                            mDatabase.child(user_id.getText().toString()).setValue(user);
                            Toast.makeText(Login.this, "회원가입을 성공했습니다.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }

}
