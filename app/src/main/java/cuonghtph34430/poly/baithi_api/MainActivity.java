package cuonghtph34430.poly.baithi_api;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cuonghtph34430.poly.baithi_api.API.APIServer;
import cuonghtph34430.poly.baithi_api.Adapter.AdapterXemay;
import cuonghtph34430.poly.baithi_api.DTO.XemayDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    static List<XemayDTO> list = new ArrayList<>();
    static AdapterXemay adapterXemay;
    static RecyclerView recyclerView;
    FloatingActionButton floaAdd;
    EditText edtSearch;
    Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rcvList);
        floaAdd = findViewById(R.id.floatAdd);
        edtSearch = findViewById(R.id.edt_Search);
        btnSearch = findViewById(R.id.btn_Search);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIServer.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CallAPI(retrofit);

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String key = edtSearch.getText().toString().trim();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(APIServer.DOMAIN)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    APIServer apiService = retrofit.create(APIServer.class);

                    apiService.searchShoe(key).enqueue(new Callback<List<XemayDTO>>() {
                        @Override
                        public void onResponse(Call<List<XemayDTO>> call, Response<List<XemayDTO>> response) {
                            if (response.isSuccessful()) {
                                List<XemayDTO> list1 = response.body();
                                getDs(list1);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<XemayDTO>> call, Throwable t) {
                            Log.e("zzzzzzzzzz", "onFailure: " + t.getMessage());
                        }
                    });
                    return true;
                }
                return false;
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(APIServer.DOMAIN)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    //Call Api Retrofit
                    CallAPI(retrofit);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        floaAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddXemay.class);
                intent.putExtra("titleAdd", "Create xeMay");
                intent.putExtra("titleBtnAdd", "Create");
                startActivity(intent);
                finish();
            }
        });
    }

    public static void CallAPI(Retrofit retrofit) {
        APIServer apiServer = retrofit.create(APIServer.class);
        Call<List<XemayDTO>> call = apiServer.getXemay();

        call.enqueue(new Callback<List<XemayDTO>>() {
            @Override
            public void onResponse(Call<List<XemayDTO>> call, Response<List<XemayDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    list = response.body();
                    adapterXemay = new AdapterXemay(recyclerView.getContext(), list);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(adapterXemay);
                    adapterXemay.notifyDataSetChanged();
                } else {
                    Log.e("TAG", "Response is null or not successful.");
                }
            }

            @Override
            public void onFailure(Call<List<XemayDTO>> call, Throwable t) {
                Log.e("zzzz", "onFailure: " + t.getMessage());
            }
        });
    }
    private static void getDs(List<XemayDTO> list) {

        adapterXemay = new AdapterXemay(recyclerView.getContext(), list);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterXemay);
        adapterXemay.notifyDataSetChanged();

    }
}
