package com.example.starock.quickkeep;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.widget.LinearLayout;

import com.example.starock.quickkeep.Database.Note;
import com.example.starock.quickkeep.Database.NoteType;
import com.example.starock.quickkeep.Drawer.NoteTypeAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static List<NoteType> noteTypeList = new ArrayList<>();
    private static NoteTypeAdapter noteTypeAdapter = new NoteTypeAdapter(noteTypeList);
    private static List<Note> noteList = new ArrayList<>();
    private static NoteAdapter noteAdapter = new NoteAdapter(noteList);
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTypes();
        initNotes();

        RecyclerView recyclerView = findViewById(R.id.recyclerview_drawer_type);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(noteTypeAdapter);

        drawerLayout = findViewById(R.id.drawer_main);
        setDrawerSlidingDistance();
        MainFragment mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main_note,mainFragment).commit();

    }
    public void initNotes(){
        noteList.clear();
        Note note = new Note();
        note.setTitle("1");
        note.setContent("111");
        noteList.add(note);
        Note note1 = new Note();
        note1.setTitle("2");
        note1.setContent("222");
        noteList.add(note1);
    }
    public void initTypes(){
        noteTypeList.clear();
        NoteType noteType = new NoteType();
        noteType.setName("未分类");
        noteType.setCount(0);
        noteTypeList.add(noteType);
        NoteType noteType1 = new NoteType();
        noteType1.setName("全部");
        noteType1.setCount(0);
        noteTypeList.add(noteType1);
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
