package com.example.starock.quickkeep.Drawer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.starock.quickkeep.Database.NoteType;
import com.example.starock.quickkeep.R;

import java.util.List;

public class NoteTypeAdapter extends RecyclerView.Adapter<NoteTypeAdapter.ViewHolder> {
    private List<NoteType> noteTypeList;
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView typeName;
        TextView typeCount;
        public ViewHolder(View view){
            super(view);
            typeName = view.findViewById(R.id.textview_drawer_name);
            typeCount = view.findViewById(R.id.textview_drawer_count);
        }
    }
    public NoteTypeAdapter(List<NoteType> noteTypeList){
        this.noteTypeList = noteTypeList;
    }

    @NonNull
    @Override
    public NoteTypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drawer_type_item,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteTypeAdapter.ViewHolder viewHolder, int i) {
        NoteType noteType = noteTypeList.get(i);
        viewHolder.typeName.setText(noteType.getName());
        viewHolder.typeCount.setText(Integer.toString(noteType.getCount())+"条");
    }

    @Override
    public int getItemCount() {
        return noteTypeList.size();
    }
}
