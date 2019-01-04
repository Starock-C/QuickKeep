package com.example.starock.quickkeep.Search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.starock.quickkeep.Database.History;
import com.example.starock.quickkeep.Database.Note;
import com.example.starock.quickkeep.MainActivity;
import com.example.starock.quickkeep.R;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;
import static com.example.starock.quickkeep.MainFragment.noteList;
import static com.example.starock.quickkeep.MainFragment.noteAdapter;
import static com.example.starock.quickkeep.MainFragment.btn_search;

public class SearchActivity extends AppCompatActivity {
    private static List<History> historyList = new ArrayList<>();
    private static HistoryAdapter historyAdapter = new HistoryAdapter(historyList);
    private EditText keyword;
    private TextView search;
    private TextView clearHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final RecyclerView recyclerView = findViewById(R.id.recyclerview_history);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(historyAdapter);

        clearHistory = findViewById(R.id.textview_clear_history);
        clearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.deleteAll(History.class);
                initHistories();
            }
        });
        search = findViewById(R.id.textview_search_notes);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword = findViewById(R.id.edittext_search_notes);
                String word = keyword.getText().toString();
                noteList.clear();
                noteList.addAll(LitePal.where("title like '%"+word+"%' or content like '%"+word+"%'").find(Note.class));
                noteAdapter.notifyDataSetChanged();
                if (!word.equals("") && LitePal.where("content = '"+word+"'").find(History.class).isEmpty()){
                    History history = new History();
                    history.setContent(word);
                    history.save();
                }
                btn_search.setText(R.string.search+" : "+word);
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                intent.setAction("Search");
                intent.putExtra("Search",1);
                startActivity(intent);
                finish();
            }
        });
        historyAdapter.setOnItemClickListener(new HistoryAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                History history  = historyList.get(position);
                Toast.makeText(SearchActivity.this,"find history "+history.getContent(),Toast.LENGTH_SHORT).show();
                String word = history.getContent();
                noteList.clear();
                noteList.addAll(LitePal.where("title like '%"+word+"%' or content like '%"+word+"%'").find(Note.class));
                noteAdapter.notifyDataSetChanged();
                btn_search.setText(R.string.search+" : "+word);
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                intent.setAction("Search");
                intent.putExtra("Search",1);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initHistories();
    }

    private void initHistories(){
        historyList.clear();
        historyList.addAll(LitePal.findAll(History.class));
        historyAdapter.notifyDataSetChanged();
    }
}
