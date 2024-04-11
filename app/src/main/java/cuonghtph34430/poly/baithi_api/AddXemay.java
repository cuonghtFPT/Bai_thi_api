package cuonghtph34430.poly.baithi_api;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import cuonghtph34430.poly.baithi_api.API.APIServer;
import cuonghtph34430.poly.baithi_api.DTO.XemayDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddXemay extends AppCompatActivity {

    private static final int MY_RES_CODE = 10;
    TextView tvTitle;
    ImageView ivBack,ivImageXemay;
    AppCompatButton btnADD;
    EditText edtTen,edtMausac,edtGia;
    LinearLayout btnChooseImage;
    Uri selectUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_xemay);

        tvTitle=findViewById(R.id.tvTitle);
        ivBack=findViewById(R.id.ivBackCreUp);
        btnADD=findViewById(R.id.btnNewAndEdit);
        edtTen=findViewById(R.id.edTen);
        edtMausac=findViewById(R.id.edMau);
        edtGia=findViewById(R.id.edGia);
        btnChooseImage=findViewById(R.id.btnChooseImage);
        ivImageXemay=findViewById(R.id.ivImageShoe);

        ChangeUI();

        btnADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleAdd = getIntent().getStringExtra("titleAdd");
                if (titleAdd == null) {
                    UpdateXemay();
                } else {
                    CreateXemay();
                }
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddXemay.this, MainActivity.class));
                finish();
            }
        });
        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage();
            }
        });

    }
    @SuppressLint("ObsoleteSdkInt")
    private void ChooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,MY_RES_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MY_RES_CODE && resultCode == RESULT_OK && data != null) { // Sửa ở đây
            selectUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),selectUri);
                ivImageXemay.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void CreateXemay() {
        String ten = edtTen.getText().toString();
        String mau = edtMausac.getText().toString();
        String gia = edtGia.getText().toString();

        if(CheckCreatXemay()) {
            String imgUrl = selectUri.toString();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(APIServer.DOMAIN)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIServer apiServer = retrofit.create(APIServer.class);
            Call<XemayDTO> call = apiServer.createXemay(new XemayDTO(ten,mau,Long.parseLong(gia),imgUrl));
            call.enqueue(new Callback<XemayDTO>() {
                @Override
                public void onResponse(Call<XemayDTO> call, Response<XemayDTO> response) {
                    if(response.isSuccessful()) {
                        Toast.makeText(AddXemay.this,"Thêm thành công",Toast.LENGTH_SHORT).show();
                        ivImageXemay.setImageURI(selectUri);
                        startActivity(new Intent(AddXemay.this, MainActivity.class));
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<XemayDTO> call, Throwable t) {
                    Log.e("zzzzz","onFailune" + t.getMessage());
                }
            });
        }
    }
    private boolean CheckCreatXemay() {
        return  true;
    }

    private void UpdateXemay() {
        String ten = edtTen.getText().toString();
        String mau = edtMausac.getText().toString();
        String gia = edtGia.getText().toString();
        String id = getIntent().getStringExtra("id");

        if (CheckCreatXemay()) {
            // Kiểm tra nếu người dùng đã chọn ảnh mới từ bộ nhớ máy ảo
            if (selectUri != null) {
                String imageUrl = selectUri.toString(); // Lấy đường dẫn của ảnh đã chọn
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(APIServer.DOMAIN)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                APIServer apiService = retrofit.create(APIServer.class);

                Call<XemayDTO> call = apiService.updateXemay(id, new XemayDTO(ten, mau, Long.parseLong(gia), imageUrl));

                call.enqueue(new Callback<XemayDTO>() {
                    @Override
                    public void onResponse(Call<XemayDTO> call, Response<XemayDTO> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(AddXemay.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                            // Hiển thị ảnh đã chọn lên sau khi cập nhật
                            ivImageXemay.setImageURI(selectUri);
                            startActivity(new Intent(AddXemay.this, MainActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<XemayDTO> call, Throwable t) {
                        Log.e("zzzzz", "onFailure: " + t.getMessage());
                    }
                });
            } else {
                // Nếu không chọn ảnh mới, chỉ cập nhật thông tin mà không thay đổi ảnh
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(APIServer.DOMAIN)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                APIServer apiService = retrofit.create(APIServer.class);

                Call<XemayDTO> call = apiService.updateXemay(id, new XemayDTO(ten, mau, Long.parseLong(gia),""));

                call.enqueue(new Callback<XemayDTO>() {
                    @Override
                    public void onResponse(Call<XemayDTO> call, Response<XemayDTO> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(AddXemay.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddXemay.this, MainActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<XemayDTO> call, Throwable t) {
                        Log.e("zzzzz", "onFailure: " + t.getMessage());
                    }
                });
            }
        }
    }



    private void ChangeUI() {
        String titleAdd = getIntent().getStringExtra("titleAdd");
        String titleBtnAdd = getIntent().getStringExtra("titleBtnAdd");
        String titleUpdate = getIntent().getStringExtra("titleEdit");
        String titleBtnUp = getIntent().getStringExtra("titleBtnEdit");
        String ten = getIntent().getStringExtra("ten");
        String mau = getIntent().getStringExtra("mau");
        Long gia = getIntent().getLongExtra("gia", 0);
        if (titleUpdate == null) {
            tvTitle.setText(titleAdd);
            btnADD.setText(titleBtnAdd);
        } else {
            tvTitle.setText(titleUpdate);
            edtTen.setText(ten);
            edtMausac.setText(mau);
            edtGia.setText(gia + "");
            btnADD.setText(titleBtnUp);
        }

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddXemay.this, MainActivity.class));
                finish();
            }
        });
    }
}