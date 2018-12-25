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

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static List<NoteType> noteTypeList = new ArrayList<>();
    private static NoteTypeAdapter noteTypeAdapter = new NoteTypeAdapter(noteTypeList);

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

        drawerLayout = findViewById(R.id.drawer_main);
        setDrawerSlidingDistance();
        MainFragment mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main_note,mainFragment).commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initTypes();
        noteTypeAdapter.notifyDataSetChanged();
    }

    public void initTypes(){
        if (LitePal.findAll(NoteType.class).isEmpty()){
            NoteType noteType = new NoteType();
            noteType.setName("未分类");
            noteType.setCount(0);
            noteType.save();
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
