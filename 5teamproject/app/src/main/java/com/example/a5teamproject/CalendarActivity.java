package com.example.a5teamproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.bumptech.glide.Glide;

public class CalendarActivity extends AppCompatActivity {

    GridView grid;
    GridAdapter adt;
    Calendar cal;
    TextView date, text;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    String a,b;
    Uri uri;
    private ImageView imageview;
    boolean img;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calview);

        adt = new GridAdapter(this); //어댑터 객체 생성
        grid = findViewById(R.id.grid); //그리드뷰 객체 참조
        date = (TextView) findViewById(R.id.date);

        text=(TextView)findViewById(R.id.userText);

        imageview = (ImageView)findViewById(R.id.imageView);

        cal = Calendar.getInstance();
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH) + 1;
        cal.set(y, m - 1, 1);

        show();

        CharSequence info[] = new CharSequence[] {"사진 추가 및 수정", "글 작성 및 수정", "사진 삭제", "글 삭제","★" };
        AlertDialog.Builder dialog = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);

        //날짜 선택
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                text.setText("새로운 내용을 추가하세요.");
                writeDownload(i);
                a = adt.mItem.get(i).month()+"."+adt.mItem.get(i).day()+"일"+user.getUid();
                StorageReference storageRef=storage.getReferenceFromUrl("gs://teamproject-4aa56.appspot.com").child("Photo");
                StorageReference storageRef1 = storageRef.child(a);
                if(storageRef1==null){
                    Toast.makeText(getApplicationContext(), "저장소에 사진이 없습니다.", Toast.LENGTH_LONG).show();

                }else {

                    storageRef1.getDownloadUrl()
                            .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Glide.with(CalendarActivity.this).load(uri).into(imageview);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    imageview.setImageResource(R.drawable.ic_image);

                                }
                            });
                }

                dialog .setTitle("메뉴 선택(글 표시: 뒤로가기버튼)")
                        .setSingleChoiceItems(info, -1, new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int j) {

                                switch (j)
                                {
                                    case 0:
                                        Intent intent = new Intent();
                                        intent.setType("image/*");
                                        intent.setAction(Intent.ACTION_GET_CONTENT);
                                        startActivityForResult(intent, 1);
                                        break;
                                    case 1:
                                        Toast.makeText(getApplicationContext(), "글 작성화면으로 이동", Toast.LENGTH_LONG).show();
                                        showDialog(view,i);
                                        break;
                                    case 2:

                                        photoDelete(i);
                                        break;
                                    case 3:

                                        writeDelete(i);
                                        break;
                                    case 4:
                                        Toast.makeText(getApplicationContext(), "즐겨찾기 화면으로 이동", Toast.LENGTH_LONG).show();
                                        break;
                                }
                            }
                        })
                        .show();
            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK) {
                uri = data.getData();
                StorageReference storageRef=storage.getReferenceFromUrl("gs://teamproject-4aa56.appspot.com").child("Photo/"+a);
                storageRef.putFile(uri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(getApplicationContext(), "사진이 정상적으로 업로드 되었습니다.", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "사진이 정상적으로 업로드 되지 않았습니다.", Toast.LENGTH_LONG).show();
                            }
                        });
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    imageview.setImageBitmap(img);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }

    //달력 표시
    private void show()
    {
        adt.clear();
        int y = cal.get(Calendar.YEAR);
        int m =cal.get(Calendar.MONTH)+1;
        date.setText(y+"-"+m);

        // 1일의 요일
        int fText = cal.get(Calendar.DAY_OF_WEEK);

        // 빈 날짜 넣기
        for (int i = 1; i<fText; i++)
        {

            boolean img = false;
            GridItem item = new GridItem(Integer.toString(y), Integer.toString(m), img);
            adt.add(item);
        }

        // 이번 달 마지막 날
        int lDay = getLastDay(y, m);

        for (int i=1; i<=lDay; i++)
        {

            img = true;


            cal.set(y, cal.get(Calendar.MONTH), i);
            int text = cal.get(Calendar.DAY_OF_WEEK);

            GridItem item = new GridItem(Integer.toString(y), Integer.toString(m), Integer.toString(i), text, img);
            adt.add(item);
        }

        grid.setAdapter(adt);
    }

    // 이전 달
    public void pre(View v)
    {
        int y = cal.get(Calendar.YEAR);
        int m =cal.get(Calendar.MONTH)-1;
        cal.set(y, m, 1);
        show();
    }

    // 다음 달
    public void next(View v)
    {
        int y = cal.get(Calendar.YEAR);
        int m =cal.get(Calendar.MONTH)+1;
        cal.set(y, m, 1);
        show();
    }

    // 특정월의 마지막 날짜
    private int getLastDay(int year, int month)
    {
        Date d = new Date(year, month, 1);
        d.setHours(d.getDay()-1*24);
        SimpleDateFormat f = new SimpleDateFormat("dd");
        return Integer.parseInt(f.format(d));
    }
    

    //글 작성
    public void writeUpload(int i , String w){
        b = adt.mItem.get(i-1).month()+"."+adt.mItem.get(i).day()+"일";
        final String text = w;
        final String text1 =b;
        if(text.length()>0){
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            UserWrite userWrite = new UserWrite(text);

            db.collection("posts").document(user.getUid()).collection(b).document(b).set(userWrite)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void avoid) {
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
    
    //작성한 글 표시
    public void writeDownload(int i){

        b = adt.mItem.get(i-1).month()+"."+adt.mItem.get(i).day()+"일";

        final String text1 =b;
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("posts").document(user.getUid()).collection(b).document(b)
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            text.setText(document.getData().toString());
                        } else {
                           //Toast.makeText(getApplicationContext(), "저장된 글이 없습니다.", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "불러오기를 실패했습니다.", Toast.LENGTH_LONG).show();
                    }
                }
            });
    }
    //글 삭제
    public void writeDelete(int i){

        b = adt.mItem.get(i-1).month()+"."+adt.mItem.get(i).day()+"일";

        final String text1 =b;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").document(user.getUid()).collection(b).document(b)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "삭제를 성공했습니다.", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "삭제를 실패했습니다.", Toast.LENGTH_LONG).show();
                    }
                });
    }
    

    //사진삭제
    public void photoDelete(int i){

        b = adt.mItem.get(i-1).month()+"."+adt.mItem.get(i).day()+"일"+user.getUid();

        final String text1 =b;

        StorageReference storageRef = storage.getReference();
        StorageReference desertRef = storageRef.child("Photo/"+text1);
        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "삭제를 성공했습니다.", Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), "삭제를 실패했습니다.", Toast.LENGTH_LONG).show();

            }
        });

    }



    public void showDialog(View view, int j) {
        final EditText editText = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("글 작성");
        builder.setMessage("내용을 입력하세요.");
        builder.setView(editText);
        builder.setPositiveButton("저장", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String edit = editText.getText().toString();
                writeUpload(j,edit);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }



    //메뉴바 설정
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favorites:
                Toast.makeText(getApplicationContext(), "즐겨찾기 버튼 클릭됨", Toast.LENGTH_LONG).show();
                return true;
            case R.id.weather:
                Toast.makeText(getApplicationContext(), "날씨 정보 버튼 클릭됨", Toast.LENGTH_LONG).show();
                return true;
            case R.id.bgm:
                Toast.makeText(getApplicationContext(), "BGM 버튼 클릭됨", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CalendarActivity.this, Bgm.class);
                startActivity(intent);
                return true;
        }
        return true;
    }
}