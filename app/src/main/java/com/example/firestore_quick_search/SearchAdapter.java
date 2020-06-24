package com.example.firestore_quick_search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder> {


    Context context;
    List<Name> nameList;

    public SearchAdapter(Context context, List<Name> nameList) {
        this.context = context;
        this.nameList = nameList;

    }

    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_layout, parent, false);
        return new SearchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHolder holder, int position) {
        Name name = nameList.get(position);
        holder.name.setText(name.getName());
        holder.sname.setText(name.getSname());
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }


    public class SearchHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView sname;


        public SearchHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            sname = itemView.findViewById(R.id.sname);

        }
    }


}
