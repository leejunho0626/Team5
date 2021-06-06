package com.example.a5teamproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import android.view.View;

public class Favorite extends AppCompatActivity {
    TextView textView,textView1, textView2, textView3,textView4, textView5;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private static final String TAG = "Hello Tag";


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        textView=(TextView)findViewById(R.id.favorite);
        textView1=(TextView)findViewById(R.id.favorite1);
        textView2=(TextView)findViewById(R.id.favorite2);
        textView3=(TextView)findViewById(R.id.favorite3);
        textView4=(TextView)findViewById(R.id.favorite4);
        textView5=(TextView)findViewById(R.id.favorite5);
        setFav1();
        setFav2();
        setFav3();
        setFav4();
        setFav5();


    }


    public void setFav1(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").document("users").collection(user.getUid()).document("favorite")
                .collection("favorite").document("1")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //DB 필드명 표시 지워서 데이터 값만 표시
                        String str1 = document.getData().toString();
                        str1 = str1.substring(str1.indexOf("=")+1);
                        String x = str1.substring(0, str1.indexOf("}"));
                        textView1.setText(x);
                        if(x.equals("{")){
                            textView1.setText("");
                        }

                    } else {
                        //Toast.makeText(getApplicationContext(), "저장된 글이 없습니다.", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "불러오기를 실패했습니다.", Toast.LENGTH_LONG).show();
                }
            }

        });
    }
    public void setFav2(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").document("users").collection(user.getUid()).document("favorite")
                .collection("favorite").document("2")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //DB 필드명 표시 지워서 데이터 값만 표시
                        String str1 = document.getData().toString();
                        str1 = str1.substring(str1.indexOf("=")+1);
                        String x = str1.substring(0, str1.indexOf("}"));
                        textView2.setText(x);
                        if(x.equals("{")){
                            textView2.setText("");
                        }

                    } else {
                        //Toast.makeText(getApplicationContext(), "저장된 글이 없습니다.", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "불러오기를 실패했습니다.", Toast.LENGTH_LONG).show();
                }
            }

        });
    }
    public void setFav3(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").document("users").collection(user.getUid()).document("favorite")
                .collection("favorite").document("3")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //DB 필드명 표시 지워서 데이터 값만 표시
                        String str1 = document.getData().toString();
                        str1 = str1.substring(str1.indexOf("=")+1);
                        String x = str1.substring(0, str1.indexOf("}"));
                        textView3.setText(x);
                        if(x.equals("{")){
                            textView3.setText("");
                        }

                    } else {
                        //Toast.makeText(getApplicationContext(), "저장된 글이 없습니다.", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "불러오기를 실패했습니다.", Toast.LENGTH_LONG).show();
                }
            }

        });
    }
    public void setFav4(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").document("users").collection(user.getUid()).document("favorite")
                .collection("favorite").document("4")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //DB 필드명 표시 지워서 데이터 값만 표시
                        String str1 = document.getData().toString();
                        str1 = str1.substring(str1.indexOf("=")+1);
                        String x = str1.substring(0, str1.indexOf("}"));
                        textView4.setText(x);
                        if(x.equals("{")){
                            textView4.setText("");
                        }

                    } else {
                        //Toast.makeText(getApplicationContext(), "저장된 글이 없습니다.", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "불러오기를 실패했습니다.", Toast.LENGTH_LONG).show();
                }
            }

        });
    }
    public void setFav5(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").document("users").collection(user.getUid()).document("favorite")
                .collection("favorite").document("5")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //DB 필드명 표시 지워서 데이터 값만 표시
                        String str1 = document.getData().toString();
                        str1 = str1.substring(str1.indexOf("=")+1);
                        String x = str1.substring(0, str1.indexOf("}"));
                        textView5.setText(x);
                        if(x.equals("{")){
                            textView5.setText("");
                        }

                    } else {
                        //Toast.makeText(getApplicationContext(), "저장된 글이 없습니다.", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "불러오기를 실패했습니다.", Toast.LENGTH_LONG).show();
                }
            }

        });
    }

}
