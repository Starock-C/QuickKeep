package com.example.starock.quickkeep;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.starock.quickkeep.Database.NoteType;

import java.util.List;

public class SelectTypeAdapter extends RecyclerView.Adapter<SelectTypeAdapter.ViewHolder> {
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
        TextView selectType;
        public ViewHolder(View view){
            super(view);
            selectType = view.findViewById(R.id.textview_takenote_type);
        }
    }
    public SelectTypeAdapter(List<NoteType> noteTypeList){this.noteTypeList = noteTypeList;}
    @NonNull
    @Override
    public SelectTypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.add_type_item,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectTypeAdapter.ViewHolder viewHolder, final int i) {
        NoteType  selectType= noteTypeList.get(i);
        viewHolder.selectType.setText(selectType.getName());

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
