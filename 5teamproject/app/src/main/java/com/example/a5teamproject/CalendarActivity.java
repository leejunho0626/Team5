package com.example.a5teamproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import android.widget.LinearLayout;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
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
    String a,b, fav1, fav2, fav3, fav4, fav5;
    Uri uri;
    private ImageView imageview;
    boolean img;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    CharSequence info2[] = new CharSequence[] {"비어있음", "비어있음", "비어있음", "비어있음","비어있음" };
    boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calview);

        adt = new GridAdapter(this); //어댑터 객체 생성
        grid = findViewById(R.id.grid); //그리드뷰 객체 참조
        date = (TextView) findViewById(R.id.date);

        text=(TextView)findViewById(R.id.userText);

        imageview = (ImageView)findViewById(R.id.imageView);

        AlertDialog.Builder dialogM = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence menu[] = new CharSequence[] {"즐겨찾기", "날씨 정보", "BGM"};
                dialogM.setTitle("메뉴선택").setSingleChoiceItems(menu,-1,new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        switch (i) {
                            case 0:
                                Toast.makeText(getApplicationContext(), "즐겨찾기 버튼 클릭됨", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(CalendarActivity.this, Favorite.class);
                                startActivity(intent);
                                break;
                            case 1:
                                Toast.makeText(getApplicationContext(), "날씨 정보 버튼 클릭됨", Toast.LENGTH_LONG).show();
                                break;
                            case 2:
                                Toast.makeText(getApplicationContext(), "BGM 버튼 클릭됨", Toast.LENGTH_LONG).show();
                                Intent intent1 = new Intent(CalendarActivity.this, Bgm.class);
                                startActivity(intent1);

                        }
                    }
                })
                .show();

            }
        });

        cal = Calendar.getInstance();
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH) + 1;
        cal.set(y, m - 1, 1);

        show();



        CharSequence info[] = new CharSequence[] {"사진&글 보기","사진 추가 및 수정", "글 작성 및 수정", "사진 삭제", "글 삭제","★추가","★해제" };

        AlertDialog.Builder dialog = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        AlertDialog.Builder dialog2 = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        AlertDialog.Builder dialog3 = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);

        //날짜 선택
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setFav1();
                setFav2();
                setFav3();
                setFav4();
                setFav5();
                text.setText("새로운 내용을 추가하세요.");
                writeDownload(i);
                a = adt.mItem.get(i).month()+"."+adt.mItem.get(i).day()+"일"+user.getUid();
                String favorite = adt.mItem.get(i).month()+"."+adt.mItem.get(i).day()+"일";
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



                dialog .setTitle("메뉴 선택"+" ▶현재 선택한 날짜 : "+adt.mItem.get(i).month()+"."+adt.mItem.get(i).day()+"일")
                        .setIcon(R.drawable.baseline_list_24)
                        .setSingleChoiceItems(info, -1, new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int j) {


                                switch (j)
                                {
                                    case 0:
                                        dialog.cancel();
                                        break;
                                    case 1:
                                        Intent intent = new Intent();
                                        intent.setType("image/*");
                                        intent.setAction(Intent.ACTION_GET_CONTENT);
                                        startActivityForResult(intent, 1);
                                        break;
                                    case 2:
                                        Toast.makeText(getApplicationContext(), "글 작성화면으로 이동", Toast.LENGTH_LONG).show();
                                        showDialog(view,i);
                                        exit = true;
                                        break;
                                    case 3:

                                        photoDelete(i);
                                        break;
                                    case 4:

                                        writeDelete(i);
                                        break;
                                    case 5:

                                        dialog2.setTitle("즐겨찾기"+" ▶현재 선택한 날짜 : "+adt.mItem.get(i).month()+"."+adt.mItem.get(i).day()+"일")
                                                .setIcon(R.drawable.baseline_list_24)
                                                .setSingleChoiceItems(info2, -1, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int k) {

                                                        switch (k)
                                                        {
                                                            case 0:
                                                                favorite1(i,favorite);
                                                                break;
                                                            case 1:
                                                                favorite2(i,favorite);
                                                                break;
                                                            case 2:
                                                                favorite3(i,favorite);
                                                                break;
                                                            case 3:
                                                                favorite4(i,favorite);
                                                                break;
                                                            case 4:
                                                                favorite5(i,favorite);
                                                                break;
                                                        }

                                                    }
                                                })
                                                .show();
                                        break;
                                    case 6:
                                        dialog3.setTitle("즐겨찾기 해제")
                                                .setSingleChoiceItems(info2, -1, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int k) {

                                                        switch (k)
                                                        {
                                                            case 0:
                                                                favDelete1(i);
                                                                info2[0]="비어있음";
                                                                break;
                                                            case 1:
                                                                favDelete2(i);
                                                                info2[1]="비어있음";
                                                                break;
                                                            case 2:
                                                                favDelete3(i);
                                                                info2[2]="비어있음";
                                                                break;
                                                            case 3:
                                                                favDelete4(i);
                                                                info2[3]="비어있음";
                                                                break;
                                                            case 4:
                                                                favDelete5(i);
                                                                info2[4]="비어있음";
                                                                break;
                                                        }
                                                    }
                                                })
                                                .show();
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

            img = false;


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

        if(text.length()>0){
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            UserWrite userWrite = new UserWrite(text);

            db.collection("posts").document("users").collection(user.getUid()).document(b).set(userWrite)
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
            db.collection("posts").document("users").collection(user.getUid()).document(b)
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
                            text.setText(x);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setMessage("정말 글을 삭제하시겠습니까?");
        builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("posts").document("users").collection(user.getUid()).document(b)
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
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }
    

    //사진삭제
    public void photoDelete(int i){

        b = adt.mItem.get(i-1).month()+"."+adt.mItem.get(i).day()+"일"+user.getUid();

        final String text1 =b;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setMessage("정말 사진을 삭제하시겠습니까?");
        builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
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
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }


    //글 작성
    public void showDialog(View view, int j) {
        final EditText editText = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder.setTitle("글 쓰기");
        builder.setMessage("내용을 입력하세요.");
        builder.setView(editText);
        builder.setPositiveButton("저장", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                builder1.setTitle("");
                builder1.setMessage("글을 저장하시겠습니까?");
                builder1.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String edit = editText.getText().toString();
                        text.setText(edit);
                        writeUpload(j,edit);
                    }
                });
                builder1.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder1.show();
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }
    //즐겨찾기 추가
    public void favorite1(int i, String w) {
        b = adt.mItem.get(i - 1).month() + "." + adt.mItem.get(i).day() + "일";
        final String text = b;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (text.length() > 0) {

            ListData listData = new ListData(text);

            db.collection("posts").document("users").collection(user.getUid()).document("favorite").collection("favorite").document("1").set(listData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void avoid) {
                            Toast.makeText(getApplicationContext(), "즐겨찾기 성공.", Toast.LENGTH_LONG).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Error.", Toast.LENGTH_LONG).show();
                        }
                    });

        }
    }

    public void favorite2(int i, String w) {
        b = adt.mItem.get(i-1).month()+"."+adt.mItem.get(i).day()+"일";
        String f = "즐겨찾기 "+b;
        final String text = b;

        if(text.length()>0){
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            ListData listData = new ListData(text);

            db.collection("posts").document("users").collection(user.getUid()).document("favorite").collection("favorite").document("2").set(listData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void avoid) {

                            Toast.makeText(getApplicationContext(), "즐겨찾기 성공.", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Error.", Toast.LENGTH_LONG).show();
                        }
                    });
        }else{
            Toast.makeText(getApplicationContext(), "날짜를 선택하세요.", Toast.LENGTH_LONG).show();
        }

    }
    public void favorite3(int i, String w) {
        b = adt.mItem.get(i-1).month()+"."+adt.mItem.get(i).day()+"일";
        String f = "즐겨찾기 "+b;
        final String text = b;

        if(text.length()>0){
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            ListData listData = new ListData(text);

            db.collection("posts").document("users").collection(user.getUid()).document("favorite").collection("favorite").document("3").set(listData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void avoid) {
                            Toast.makeText(getApplicationContext(), "즐겨찾기 성공.", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Error.", Toast.LENGTH_LONG).show();
                        }
                    });
        }else{
            Toast.makeText(getApplicationContext(), "날짜를 선택하세요.", Toast.LENGTH_LONG).show();
        }

    }
    public void favorite4(int i, String w) {
        b = adt.mItem.get(i-1).month()+"."+adt.mItem.get(i).day()+"일";
        String f = "즐겨찾기 "+b;
        final String text = b;

        if(text.length()>0){
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            ListData listData = new ListData(text);

            db.collection("posts").document("users").collection(user.getUid()).document("favorite").collection("favorite").document("4").set(listData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void avoid) {
                            Toast.makeText(getApplicationContext(), "즐겨찾기 성공.", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Error.", Toast.LENGTH_LONG).show();
                        }
                    });
        }else{
            Toast.makeText(getApplicationContext(), "날짜를 선택하세요.", Toast.LENGTH_LONG).show();
        }

    }
    public void favorite5(int i, String w) {
        b = adt.mItem.get(i-1).month()+"."+adt.mItem.get(i).day()+"일";
        String f = "즐겨찾기 "+b;
        final String text = b;

        if(text.length()>0){
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            ListData listData = new ListData(text);

            db.collection("posts").document("users").collection(user.getUid()).document("favorite").collection("favorite").document("5").set(listData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void avoid) {
                            Toast.makeText(getApplicationContext(), "즐겨찾기 성공.", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Error.", Toast.LENGTH_LONG).show();
                        }
                    });
        }else{
            Toast.makeText(getApplicationContext(), "날짜를 선택하세요.", Toast.LENGTH_LONG).show();
        }

    }
    //즐겨찾기 해제
    public void favDelete1(int i){

        b = adt.mItem.get(i-1).month()+"."+adt.mItem.get(i).day()+"일";
        final String text1 =b;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setMessage("정말 즐겨찾기를 해제하시겠습니까?");
        builder.setPositiveButton("해제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("posts").document("users").collection(user.getUid()).document("favorite")
                        .collection("favorite").document("1")
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "즐겨찾기 해제.", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "해제를 실패했습니다.", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }
    public void favDelete2(int i){

        b = adt.mItem.get(i-1).month()+"."+adt.mItem.get(i).day()+"일";
        final String text1 =b;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setMessage("정말 즐겨찾기를 해제하시겠습니까?");
        builder.setPositiveButton("해제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("posts").document("users").collection(user.getUid()).document("favorite")
                        .collection("favorite").document("2")
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "즐겨찾기 해제.", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "해제를 실패했습니다.", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }
    public void favDelete3(int i){

        b = adt.mItem.get(i-1).month()+"."+adt.mItem.get(i).day()+"일";
        final String text1 =b;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setMessage("정말 즐겨찾기를 해제하시겠습니까?");
        builder.setPositiveButton("해제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("posts").document("users").collection(user.getUid()).document("favorite")
                        .collection("favorite").document("3")
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "즐겨찾기 해제.", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "해제를 실패했습니다.", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }
    public void favDelete4(int i){

        b = adt.mItem.get(i-1).month()+"."+adt.mItem.get(i).day()+"일";
        final String text1 =b;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setMessage("정말 즐겨찾기를 해제하시겠습니까?");
        builder.setPositiveButton("해제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("posts").document("users").collection(user.getUid()).document("favorite")
                        .collection("favorite").document("4")
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "즐겨찾기 해제.", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "해제를 실패했습니다.", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }
    public void favDelete5(int i){

        b = adt.mItem.get(i-1).month()+"."+adt.mItem.get(i).day()+"일";
        final String text1 =b;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setMessage("정말 즐겨찾기를 해제하시겠습니까?");
        builder.setPositiveButton("해제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("posts").document("users").collection(user.getUid()).document("favorite")
                        .collection("favorite").document("5")
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "즐겨찾기 해제.", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "해제를 실패했습니다.", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }
    //즐겨찾기 표시
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
                        fav1 = str1.substring(0, str1.indexOf("}"));
                        info2[0]=fav1;
                        if(fav1.equals("{")){
                            info2[0]="";
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
                        fav2 = str1.substring(0, str1.indexOf("}"));
                        info2[1]=fav2;
                        if(fav2.equals("{")){
                            info2[1]="";
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
                        fav3 = str1.substring(0, str1.indexOf("}"));
                        info2[2]=fav3;
                        if(fav3.equals("{")){
                            info2[2]="";
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
                        fav4 = str1.substring(0, str1.indexOf("}"));
                        info2[3]=fav4;


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
                        fav5 = str1.substring(0, str1.indexOf("}"));
                        info2[4]=fav5;
                        if(fav5.equals("{")){
                            info2[4]="";
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
                Intent intent = new Intent(CalendarActivity.this, Favorite.class);
                startActivity(intent);
                return true;
            case R.id.weather:
                Toast.makeText(getApplicationContext(), "날씨 정보 버튼 클릭됨", Toast.LENGTH_LONG).show();
                return true;
            case R.id.bgm:
                Toast.makeText(getApplicationContext(), "BGM 버튼 클릭됨", Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(CalendarActivity.this, Bgm.class);
                startActivity(intent1);
                return true;
        }
        return true;
    }
}