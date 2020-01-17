package com.juara.apk_kendaraan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.juara.apk_kendaraan.R;
import com.juara.apk_kendaraan.modelKendaraan.Kendaraan;
import com.squareup.picasso.Picasso;

public class AdapterListSimple extends RecyclerView.Adapter<AdapterListSimple.ViewHolder> {

    java.util.List<Kendaraan> data;
    Context context;


    public AdapterListSimple(Context context, java.util.List<Kendaraan> data){



        this.data = data;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_new__mockupkendaraan, parent, false);


        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        holder.txt_Merk.setText(data.get(position).getMerekKendaraan());
        holder.txt_CC.setText(data.get(position).getCc());
        holder.txt_tahun.setText(data.get(position).getTahunKendaraan());

        String image =  data.get(position).getFotoKendaraan();
        Picasso.get().load(image).into(holder.img_Kendaraan);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder  {
       public TextView txt_Merk,txt_CC,txt_tahun;
       public ImageView img_Kendaraan;


        public ViewHolder(View itemView) {


            super(itemView);

           txt_Merk = (TextView) itemView.findViewById(R.id.txtMerk);
           txt_CC = (TextView) itemView.findViewById(R.id.txtCC);
           txt_tahun = (TextView) itemView.findViewById(R.id.txtThn);

           img_Kendaraan = (ImageView)itemView.findViewById(R.id.img_Kendaraan);


        }

    }


}
