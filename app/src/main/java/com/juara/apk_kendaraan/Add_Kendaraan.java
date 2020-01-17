package com.juara.apk_kendaraan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.juara.apk_kendaraan.addKendaraan.AddModel;
import com.juara.apk_kendaraan.merkkendaraan.MerkModel;
import com.juara.apk_kendaraan.modelKendaraan.KendaraanModel;
import com.juara.apk_kendaraan.service.APIClient;
import com.juara.apk_kendaraan.service.APIInterfacesRest;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_Kendaraan extends AppCompatActivity {

    ImageButton btn_picture, btn_folder;
    ImageView imgCamera;
    EditText txt_CC;
    Button button_Send;
    Spinner spn_Merek;
    CalendarView calendarView;
    RecyclerView kd ;
    Date tanggal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__kendaraan);

        //Button
        btn_picture = findViewById(R.id.btnCapture);
        btn_folder = findViewById(R.id.btnFolder);
        button_Send = findViewById(R.id.buttonSend);

        //Edittext
        txt_CC = findViewById(R.id.txtCC);

        //Spinner
        spn_Merek = findViewById(R.id.spnMerek);
        calendarView = findViewById(R.id.cbThnkendaraan);

        //ImageView
        imgCamera = findViewById(R.id.imgCamera);

        btn_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });


        btn_folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFolder();
            }
        });


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                tanggal = new GregorianCalendar(year, month, dayOfMonth).getTime();
                Toast.makeText(view.getContext(), "Tahun=" + year + " Bulan=" + month + " Hari=" + dayOfMonth, Toast.LENGTH_LONG).show();
            }
        });


        button_Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txt_CC == null && imgCamera == null  ) {
                    Toast.makeText(Add_Kendaraan.this, "Isi Terlebih Dahulu Kolom Yang Kosong", Toast.LENGTH_LONG).show();
                }
                SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd");
                String format = ss.format(tanggal);




                postMobil(txt_CC.getText().toString(), String.valueOf(spn_Merek.getSelectedItem()),String.valueOf(format),"photo1");

            }
        });


        spinerMerek();

    }

    public RequestBody toRequestBody(String value) {
        if (value==null){
            value="";
        }
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }

    APIInterfacesRest apiInterface;
    ProgressDialog progressDialog;
    public void postMobil(String cc,String merk, String tanggal, String photo1){



        RequestBody requestFile1 = RequestBody.create(MediaType.parse("image/jpeg"),byteArray);

        MultipartBody.Part bodyImg1 = MultipartBody.Part.createFormData("foto_kendaraan", "gambar1"+new Date()+".png", requestFile1);




        apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        progressDialog = new ProgressDialog(Add_Kendaraan.this);
        progressDialog.setTitle("Loading");
        progressDialog.show();
        Call<AddModel> call3 = apiInterface.addkendaraan(toRequestBody(merk),toRequestBody(cc),toRequestBody(tanggal),bodyImg1);
        call3.enqueue(new Callback<AddModel>() {
            @Override
            public void onResponse(Call<AddModel> call, Response<AddModel> response) {
                progressDialog.dismiss();
                AddModel data = response.body();

                if (data !=null) {


                    Toast.makeText(Add_Kendaraan.this,data.getMessage(),Toast.LENGTH_LONG).show();






                }else{

                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(Add_Kendaraan.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(Add_Kendaraan.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<AddModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Maaf koneksi bermasalah",Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });




    }


    //camera
    private int CAMERA_REQUEST = 200;
    //gallery
    private int REQUEST_GALLERY = 100;

    void openCamera() {

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    public void openFolder() {

        Intent folderIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(folderIntent, REQUEST_GALLERY);

    }

    Bitmap bitmap;
    byte[] byteArray;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");


            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            byteArray = baos.toByteArray();
            imgCamera.setImageBitmap(bitmap);


        } else if (requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();

            imgCamera.setImageURI(selectedImage);
            Bitmap bitmap = ((BitmapDrawable) imgCamera.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byteArray = baos.toByteArray();
        }
    }


    public void spinerMerek(){

        apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        /*progressDialog = new ProgressDialog(AddActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.show();*/
        Call<MerkModel> call3 = apiInterface.getmerk();
        call3.enqueue(new Callback<MerkModel>() {
            @Override
            public void onResponse(Call<MerkModel> call, Response<MerkModel> response) {
                //progressDialog.dismiss();
                MerkModel data = response.body();
                //Toast.makeText(LoginActivity.this,userList.getToken().toString(),Toast.LENGTH_LONG).show();
                if (data !=null) {


                    List<String> listSpinner = new ArrayList<String>();
                    for (int i = 0; i < data.getData().getMerkKendaraan().size(); i++){
                        listSpinner.add(data.getData().getMerkKendaraan().get(i).getNamaKendaraan());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Add_Kendaraan.this,
                            android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_Merek.setAdapter(adapter);

                }else{

                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(Add_Kendaraan.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(Add_Kendaraan.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<MerkModel> call, Throwable t) {
                //progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Maaf koneksi bermasalah",Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });}


    //POST

    public void tambahmobil(String merek_kendaraan , String cc , String tahun_kendaraan , String foto_kendaraan){



        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"),byteArray);
        MultipartBody.Part bodyImg = MultipartBody.Part.createFormData("foto_kendaraan", "dewa.png", requestFile);


        apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        progressDialog = new ProgressDialog(Add_Kendaraan.this);
        progressDialog.setTitle("Loading");
        progressDialog.show();
        //  Call<AddModel> call3 = apiInterface.addkendaraan();
        Call<AddModel> call3 = apiInterface.addkendaraan(toRequestBody(merek_kendaraan), toRequestBody(cc) , toRequestBody(tahun_kendaraan) , bodyImg);
        call3.enqueue(new Callback<AddModel>() {
            @Override
            public void onResponse(Call<AddModel> call, Response<AddModel> response) {
                progressDialog.dismiss();
                AddModel data = response.body();
                //Toast.makeText(LoginActivity.this,userList.getToken().toString(),Toast.LENGTH_LONG).show();

                if (data !=null) {


                    Toast.makeText(Add_Kendaraan.this,data.getMessage(),Toast.LENGTH_LONG).show();
//
//                    txt_Question.setText(data.getData().getSoalQuizAndroid().get(0).getPertanyaan().toString());
//                    btn_A.setText(data.getData().getSoalQuizAndroid().get(0).getA().toString());
//                    btn_B.setText(data.getData().getSoalQuizAndroid().get(0).getB().toString());
//                    btn_C.setText(data.getData().getSoalQuizAndroid().get(0).getC().toString());
//                    btn_D.setText(data.getData().getSoalQuizAndroid().get(0).getD().toString());


//
//                    String image = "http://dewabrata.com:80/dewa/uploads/soal_quiz_android/" + data.getData().getSoalQuizAndroid().get(0).getGambar()+"20200110101347-2020-01-10soal_quiz_android101342.jpg";
//                    Picasso.get().load(image).into(gambar1);

                 /*   AdapterListSimple adapter = new AdapterListSimple(MainActivity.this,data.getData().getMoviedb());

                    lst_Movie.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    lst_Movie.setItemAnimator(new DefaultItemAnimator());
                    lst_Movie.setAdapter(adapter);

                     */





                }else{

                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(Add_Kendaraan.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(Add_Kendaraan.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Maaf koneksi bermasalah",Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });




    }


}