package com.example.starock.quickkeep.Drawer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.starock.quickkeep.Database.NoteType;
import com.example.starock.quickkeep.ItemTouchHelperAdapter;
import com.example.starock.quickkeep.R;

import java.util.List;

public class NoteTypeAdapter extends RecyclerView.Adapter<NoteTypeAdapter.ViewHolder>  {
    private List<NoteType> noteTypeList;


    public interface OnItemClickListener {
        void onClick(int position);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemLongClickListener {
        void onClick(int position);
    }

    private OnItemLongClickListener longClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener){
        this.longClickListener = longClickListener;
    }

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
    public void onBindViewHolder(@NonNull NoteTypeAdapter.ViewHolder viewHolder, final int i) {
        NoteType noteType = noteTypeList.get(i);
        viewHolder.typeName.setText(noteType.getName());
        viewHolder.typeCount.setText(Integer.toString(noteType.getCount())+"条");

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(i);
            }
        });

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longClickListener != null){
                    longClickListener.onClick(i);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteTypeList.size();
    }
}
