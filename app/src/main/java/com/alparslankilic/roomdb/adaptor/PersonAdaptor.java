package com.alparslankilic.roomdb.adaptor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.alparslankilic.roomdb.EditActivity;
import com.alparslankilic.roomdb.R;
import com.alparslankilic.roomdb.database.AppDatabase;
import com.alparslankilic.roomdb.model.Person;

import java.util.List;

public class PersonAdaptor extends RecyclerView.Adapter<PersonAdaptor.MyViewHolder> {

    private Context context;
    private List<Person> mPersonList;

    public PersonAdaptor(Context context)
    {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.person_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(mPersonList.get(position).getName());
        holder.email.setText(mPersonList.get(position).getEmail());
        holder.number.setText(mPersonList.get(position).getNumber());
        holder.pincode.setText(mPersonList.get(position).getPincode());
        holder.city.setText(mPersonList.get(position).getCity());

    }

    @Override
    public int getItemCount() {
        if(mPersonList == null) {
            return 0;
        }
        return mPersonList.size();
    }

    public List<Person> getTasks() {

        return mPersonList;
    }

    public void setTasks(List<Person> personList)
    {
        mPersonList = personList;
        notifyDataSetChanged();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,email,pincode,number,city;
        ImageView editImage;
        AppDatabase mDB;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mDB = AppDatabase.getInstance(context);
            name = itemView.findViewById(R.id.person_name);
            email = itemView.findViewById(R.id.person_email);
            pincode = itemView.findViewById(R.id.person_pincode);
            number = itemView.findViewById(R.id.person_number);
            city = itemView.findViewById(R.id.person_city);
            editImage = itemView.findViewById(R.id.edit_Image);
            editImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int elementId = mPersonList.get(getAdapterPosition()).getId();
                    Intent i = new Intent(context, EditActivity.class);
                    i.putExtra("update_task",elementId);
                    context.startActivity(i);
                }
            });

        }
    }
}

