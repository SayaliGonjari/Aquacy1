package com.project.aquacy.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.aquacy.ClientListActivity;
import com.project.aquacy.Model.ClientDetailsBean;
import com.project.aquacy.R;

import java.util.ArrayList;

public class ClientListAdapter extends RecyclerView.Adapter<ClientListAdapter.ClientAdapter> {
    ArrayList<ClientDetailsBean> clientDetailsBeanArrayList;
    Context context;

    public ClientListAdapter(Context context, ArrayList<ClientDetailsBean> clientDetailsBeanArrayList1) {

        this.clientDetailsBeanArrayList = clientDetailsBeanArrayList1;
        this.context = context;
    }

    public ClientListAdapter() {

    }

    @NonNull
    @Override
    public ClientListAdapter.ClientAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.vwb_item_add_material, viewGroup, false);
        return new ClientAdapter(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientAdapter holder, int i) {
        try{
            holder.txt_CustName.setText(clientDetailsBeanArrayList.get(i).getCustVendorName());
            holder.txt_Addr.setText(clientDetailsBeanArrayList.get(i).getAddress());
            holder.txt_mob.setText(clientDetailsBeanArrayList.get(i).getMobile());
            holder.txt_Email.setText(clientDetailsBeanArrayList.get(i).getEmail());
            holder.txt_MType.setText(clientDetailsBeanArrayList.get(i).getMeterType());
            holder.txt_MNo.setText(clientDetailsBeanArrayList.get(i).getMeterNo());
            holder.txt_ConnNo.setText(clientDetailsBeanArrayList.get(i).getConnNo());


        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return clientDetailsBeanArrayList.size();
    }

    public void update(ArrayList<ClientDetailsBean> clientDetailsBeanArrayList1) {
        this.clientDetailsBeanArrayList = clientDetailsBeanArrayList1;
        notifyDataSetChanged();
    }


    public class ClientAdapter extends RecyclerView.ViewHolder {
        TextView txt_CustName,txt_Addr,txt_mob,txt_Email,txt_MType,txt_MNo,txt_ConnNo;
        ImageView img_edit,img_delete;

        public ClientAdapter(@NonNull View itemView) {
            super(itemView);
            txt_CustName = itemView.findViewById(R.id.txt_CustName);
            txt_Addr = itemView.findViewById(R.id.txt_Addr);
            txt_mob = itemView.findViewById(R.id.txt_mob);
            txt_Email = itemView.findViewById(R.id.txt_Email);
            txt_MType = itemView.findViewById(R.id.txt_MType);
            txt_MNo = itemView.findViewById(R.id.txt_MNo);
            txt_ConnNo = itemView.findViewById(R.id.txt_ConnNo);
            img_edit = itemView.findViewById(R.id.img_edit);
            img_delete = itemView.findViewById(R.id.img_delete);

            img_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ClientListActivity) context).EditRow(getAdapterPosition(),true);
                   // ((ClientListActivity)context).EditRow(getAdapterPosition(),clientDetailsBeanArrayList.get(getAdapterPosition()),true);
                }
            });

            img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ClientListActivity) context).EditRow(getAdapterPosition(),false);
                }
            });

        }
    }
}
