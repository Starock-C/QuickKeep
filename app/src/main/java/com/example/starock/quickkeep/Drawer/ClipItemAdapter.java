package com.example.starock.quickkeep.Drawer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.starock.quickkeep.Database.ClipItem;
import com.example.starock.quickkeep.Database.Note;
import com.example.starock.quickkeep.Database.NoteType;
import com.example.starock.quickkeep.ItemTouchHelperAdapter;
import com.example.starock.quickkeep.R;

import org.litepal.LitePal;

import java.util.Date;
import java.util.List;

import static com.example.starock.quickkeep.MainFragment.noteAdapter;
import static com.example.starock.quickkeep.MainFragment.noteList;
import static com.example.starock.quickkeep.MainActivity.initTypes;

public class ClipItemAdapter extends RecyclerView.Adapter<ClipItemAdapter.ViewHolder>{
    private List<ClipItem> clipItemList;

//    @Override
//    public void onItemDissmiss(int position) {
//        if (!clipItemList.isEmpty()){
//            ClipItem clipItem = clipItemList.get(position);
//            clipItemList.remove(position);
//
//            Note note = new Note();
//            note.setTitle("无标题");
//            note.setType("剪贴板");
//            note.setContent(clipItem.getContent());
//            note.setSource("剪贴板");
//            note.setDatetime(new Date(System.currentTimeMillis()));
//            note.save();
//            NoteType cliptype = LitePal.where("name = ?","剪贴板").findFirst(NoteType.class);
//            cliptype.setCount(cliptype.getCount()+1);
//            cliptype.save();
//            initTypes();
//            noteList.clear();
//            noteList.addAll(LitePal.findAll(Note.class));
//            noteAdapter.notifyDataSetChanged();
//        }
//    }

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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView content;
        public ViewHolder(View view){
            super(view);
            content = view.findViewById(R.id.textview_clipitem);
        }
    }
    public ClipItemAdapter(List<ClipItem> clipItemList) {
        this.clipItemList = clipItemList;
    }
    @NonNull
    @Override
    public ClipItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.clipboard_item,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClipItemAdapter.ViewHolder viewHolder, final int i) {
        ClipItem clipItem = clipItemList.get(i);
        viewHolder.content.setText(clipItem.getContent());

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
        return clipItemList.size();
    }


}
