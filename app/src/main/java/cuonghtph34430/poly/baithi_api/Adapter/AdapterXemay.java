package cuonghtph34430.poly.baithi_api.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import cuonghtph34430.poly.baithi_api.API.APIServer;
import cuonghtph34430.poly.baithi_api.AddXemay;
import cuonghtph34430.poly.baithi_api.DTO.XemayDTO;
import cuonghtph34430.poly.baithi_api.MainActivity;
import cuonghtph34430.poly.baithi_api.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdapterXemay extends RecyclerView.Adapter<AdapterXemay.ViewHolder> {

    Context context;
    List<XemayDTO> list;

    public AdapterXemay(Context context, List<XemayDTO> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterXemay.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterXemay.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final String id = list.get(position).get_id();
        holder.tvtenxe.setText(String.valueOf(list.get(position).getTenXePh34430()));
        holder.tvmausac.setText(String.valueOf(list.get(position).getMauSacPh34430()));
        holder.tvgiaban.setText(String.valueOf(list.get(position).getGiaBanPh34430()));
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =new AlertDialog.Builder(context);
                @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_mess, null, false);
                builder.setView(view);
                AlertDialog dialog =builder.create();
                Window window = dialog.getWindow();
                assert window != null;
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();

                AppCompatButton btnHuy, btnXacNhan;
                btnHuy = view.findViewById(R.id.btnHuy);
                btnXacNhan = view.findViewById(R.id.btnXacNhan);

                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnXacNhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(APIServer.DOMAIN)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        APIServer apiService = retrofit.create(APIServer.class);

                        Call<XemayDTO> call = apiService.deleteXemay(id);

                        call.enqueue(new Callback<XemayDTO>() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onResponse(Call<XemayDTO> call, Response<XemayDTO> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(context, "Delete successfully", Toast.LENGTH_SHORT).show();
                                    MainActivity.CallAPI(retrofit);
                                    dialog.dismiss();
                                    notifyDataSetChanged();
                                }
                            }
                            @Override
                            public void onFailure(Call<XemayDTO> call, Throwable t) {
                                Log.e("cccccc", "onFailure: " + t.getMessage());
                            }
                        });
                    }
                });
            }
        });

        Glide.with(context)
                .load(list.get(position).getAnhPh34430())
                .into(holder.ivShoe);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddXemay.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("titleEdit", "Update xeMay");
                intent.putExtra("titleBtnEdit", "Update");
                intent.putExtra("ten", list.get(position).getTenXePh34430());
                intent.putExtra("mau", list.get(position).getMauSacPh34430());
                intent.putExtra("gia", list.get(position).getGiaBanPh34430());
                intent.putExtra("img", list.get(position).getAnhPh34430());
                intent.putExtra("id", list.get(position).get_id());
                context.startActivity(intent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(APIServer.DOMAIN)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                APIServer apiServer = retrofit.create(APIServer.class);

                Call<XemayDTO> call = apiServer.getThongtin2(id);
                call.enqueue(new Callback<XemayDTO>() {
                    @Override
                    public void onResponse(Call<XemayDTO> call, Response<XemayDTO> response) {
                        Log.e("zzzzzzz", "onResponse: " + response.body());
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.layout_dialog1, null
                                , false);

                        builder.setView(view);
                        AlertDialog alertDialog = builder.create();
                        TextView tvName,tvgiaban, tvmausac;
                        ImageView img;
                        tvName = view.findViewById(R.id.tvMessenger);
                        tvgiaban = view.findViewById(R.id.tvMessenger1);
                        tvmausac = view.findViewById(R.id.tvMessenger2);
                        img=view.findViewById(R.id.ivImg);
                        Glide.with(context)
                                .load(response.body().getAnhPh34430())
                                .into(img);
                        tvName.setText(String.valueOf(response.body().getTenXePh34430()));
                        tvgiaban.setText(String.valueOf((int) response.body().getGiaBanPh34430()));
                        tvmausac.setText(response.body().getMauSacPh34430());
                        alertDialog.show();

                    }

                    @Override
                    public void onFailure(Call<XemayDTO> call, Throwable t) {
                        Log.d("zzzzzzzz", "onFailure: " + t.getMessage());
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvtenxe, tvgiaban, tvmausac;
        Button btnEdit, btnDelete, btnDathang;
        ShapeableImageView ivShoe;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvtenxe = itemView.findViewById(R.id.tvTen);
            tvgiaban = itemView.findViewById(R.id.tvGia);
            tvmausac = itemView.findViewById(R.id.tvMausac);
            ivShoe = itemView.findViewById(R.id.ivShoe);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }
}