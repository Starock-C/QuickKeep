package com.example.starock.quickkeep;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.starock.quickkeep.Database.ClipItem;
import com.example.starock.quickkeep.Database.Note;
import com.example.starock.quickkeep.Database.NoteType;
import com.example.starock.quickkeep.Drawer.ClipItemAdapter;
import com.example.starock.quickkeep.Drawer.NoteTypeAdapter;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.qzb.richeditor.RE;

import static com.example.starock.quickkeep.MainFragment.noteList;
import static com.example.starock.quickkeep.MainFragment.noteAdapter;
public class MainActivity extends AppCompatActivity {
    public static List<NoteType> noteTypeList = new ArrayList<>();
    public static NoteTypeAdapter noteTypeAdapter = new NoteTypeAdapter(noteTypeList);
    public static List<ClipItem> clipItemList = new ArrayList<>();
    public static ClipItemAdapter clipItemAdapter = new ClipItemAdapter(clipItemList);

    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        initTypes();

        RecyclerView recyclerView = findViewById(R.id.recyclerview_drawer_type);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(noteTypeAdapter);
        RecyclerView recyclerView2 = findViewById(R.id.recyclerview_drawer_clipboard);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getBaseContext());
        recyclerView2.setLayoutManager(linearLayoutManager2);
        recyclerView2.setAdapter(clipItemAdapter);

        drawerLayout = findViewById(R.id.drawer_main);
        setDrawerSlidingDistance();
        MainFragment mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main_note,mainFragment).commit();

        noteTypeAdapter.setOnItemClickListener(new NoteTypeAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                NoteType noteType = noteTypeList.get(position);
                Toast.makeText(getApplicationContext(),"find type "+noteType.getName(),Toast.LENGTH_SHORT).show();
                noteList.clear();
                if (noteType.getName().equals("全部"))
                    noteList.addAll(LitePal.findAll(Note.class));
                else
                    noteList.addAll(LitePal.where("type = ?",noteType.getName()).find(Note.class));
                noteAdapter.notifyDataSetChanged();
            }
        });

        clipItemAdapter.setOnItemClickListener(new ClipItemAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                ClipItem clipItem = clipItemList.get(position);
                clipItemList.remove(position);

                Note note = new Note();
                note.setTitle("无标题");
                note.setType("剪贴板");
                note.setContent(clipItem.getContent());
                note.setSource("剪贴板");
                note.setDatetime(new Date(System.currentTimeMillis()));
                note.save();

                NoteType cliptype = LitePal.where("name = ?","剪贴板").findFirst(NoteType.class);
                cliptype.setCount(cliptype.getCount()+1);
                cliptype.save();
                initTypes();
                noteList.clear();
                noteList.addAll(LitePal.findAll(Note.class));
                noteAdapter.notifyDataSetChanged();
                LitePal.delete(ClipItem.class,clipItem.getId());
                clipItemAdapter.notifyDataSetChanged();
            }
        });

//        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(clipItemAdapter);
//        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
//        touchHelper.attachToRecyclerView(recyclerView2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initTypes();
        iniyClipboard();
    }

    private void iniyClipboard() {
        ClipboardManager clipboardmanager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = clipboardmanager.getPrimaryClip();
        if (clipData != null && clipData.getItemCount() > 0 && !clipData.getItemAt(0).getText().equals("")) {
            // 从数据集中获取（粘贴）第一条文本数据
            CharSequence text = clipData.getItemAt(0).getText();
            ClipItem clipItem = new ClipItem();
            clipItem.setContent(text.toString());
            if (LitePal.where("content = ?",clipItem.getContent()).find(ClipItem.class).isEmpty()){
                clipItem.save();
            }

        }
        clipItemList.clear();
        clipItemList.addAll(LitePal.findAll(ClipItem.class));
        clipItemAdapter.notifyDataSetChanged();
        clipboardmanager.setPrimaryClip(ClipData.newPlainText(null, ""));
    }

    public static void initTypes(){
        if (LitePal.findAll(NoteType.class).isEmpty()){
            NoteType noteType = new NoteType();
            noteType.setName("未分类");
            noteType.setCount(0);
            noteType.save();
            NoteType noteType3 = new NoteType();
            noteType3.setName("剪贴板");
            noteType3.setCount(0);
            noteType3.save();
            NoteType noteType1 =new NoteType();
            noteType1.setName("工作");
            noteType1.setCount(0);
            noteType1.save();
            NoteType noteType2 = new NoteType();
            noteType2.setName("生活");
            noteType2.setCount(0);
            noteType2.save();
        }
        noteTypeList.clear();
        noteTypeList.addAll(LitePal.findAll(NoteType.class));
        NoteType all = new NoteType();
        all.setCount(LitePal.count(Note.class));
        all.setName("全部");
        all.setId(0);
        noteTypeList.add(all);
        noteTypeAdapter.notifyDataSetChanged();
    }

    private void setDrawerSlidingDistance(){
        try {
            // 用反射来设置划动出来的距离 mMinDrawerMargin
            Field mMinDrawerMarginField = DrawerLayout.class.getDeclaredField("mMinDrawerMargin");
            mMinDrawerMarginField.setAccessible(true);
            int minDrawerMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,
                    800, getResources().getDisplayMetrics());
            mMinDrawerMarginField.set(drawerLayout, minDrawerMargin);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
