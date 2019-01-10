package com.example.starock.quickkeep.Note;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.starock.quickkeep.Database.Note;
import com.example.starock.quickkeep.Database.NoteType;
import com.example.starock.quickkeep.ItemTouchHelperAdapter;
import com.example.starock.quickkeep.R;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.List;
import static com.example.starock.quickkeep.MainActivity.noteTypeList;
import static com.example.starock.quickkeep.MainActivity.noteTypeAdapter;
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> implements ItemTouchHelperAdapter {
    private List<Note> noteList;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    public void onItemDissmiss(int position) {
        if (!noteList.isEmpty()){
            Note note = noteList.get(position);
            noteList.remove(position);

            NoteType noteType = LitePal.where("name = ?",note.getType()).findFirst(NoteType.class);
            noteType.setCount(noteType.getCount()-1);
            noteType.save();

            LitePal.delete(Note.class,note.getId());
            notifyItemRemoved(position);
            notifyItemChanged(position);
            //notifyDataSetChanged();

            noteTypeList.clear();
            noteTypeList.addAll(LitePal.findAll(NoteType.class));
            NoteType all = new NoteType();
            all.setCount(LitePal.count(Note.class));
            all.setName("全部");
            all.setId(0);
            noteTypeList.add(all);
            noteTypeAdapter.notifyDataSetChanged();
        }
    }

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
        TextView noteTitle;
        TextView noteTime;
        TextView noteType;
        TextView noteContent;
        public ViewHolder(View view){
            super(view);
            noteTitle = view.findViewById(R.id.textview_main_title);
            noteTime = view.findViewById(R.id.textview_main_time);
            noteType = view.findViewById(R.id.textview_main_type);
            noteContent = view.findViewById(R.id.textview_main_content);
        }
    }
    public NoteAdapter(List<Note> noteList){
        this.noteList = noteList;
    }
    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_note_item,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder viewHolder, final int i) {
        Note note = noteList.get(i);
        viewHolder.noteTitle.setText(note.getTitle());
        viewHolder.noteTime.setText(sdf.format(note.getDatetime()));
        viewHolder.noteType.setText(note.getType());
        viewHolder.noteContent.setText(note.getContent());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener(){
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
        return noteList.size();
    }

    public void removeItem(int adapterPosition){
        if (adapterPosition==RecyclerView.NO_POSITION)
            return;
        if (adapterPosition >= 0 && adapterPosition < noteList.size()) {  //mDatas为数据集合
            noteList.remove(adapterPosition);
            notifyItemRemoved(adapterPosition);
        }
    }

}
