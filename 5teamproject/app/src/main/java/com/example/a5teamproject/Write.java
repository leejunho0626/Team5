package com.example.a5teamproject;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Write extends AppCompatActivity {

    TextView textView;
    CalendarActivity ca = new CalendarActivity();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        textView = findViewById(R.id.dateWrite);

        for (int i = 0; i < ca.date1.size(); i++) {
            textView.setText(ca.date1.get(i));
    }
        findViewById(R.id.btn_save).setOnClickListener(onClickListener);

    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()){
                case R.id.btn_save:
                    writeUpload();
                    break;
            }
        }
    };

    public void writeUpload(){
        final String text = ((EditText) findViewById(R.id.write)).getText().toString();
        final String text1 = ((EditText) findViewById(R.id.write1)).getText().toString();
        if(text.length()>0){

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            UserWrite userWrite = new UserWrite(text, user.getUid(), text1);

            db.collection("posts").add(userWrite)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                           Toast.makeText(getApplicationContext(), "글이 저장되었습니다.", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Error.", Toast.LENGTH_LONG).show();
                        }
                    });
        }else{
            Toast.makeText(getApplicationContext(), "내용을 입력하세요.", Toast.LENGTH_LONG).show();
        }

    }

}
