package com.example.starock.quickkeep;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.widget.LinearLayout;

import com.example.starock.quickkeep.Database.NoteType;
import com.example.starock.quickkeep.Drawer.NoteTypeAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_main);
        setDrawerSlidingDistance();
        MainFragment mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main_note,mainFragment).commit();

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
