package com.example.starock.quickkeep.Database;

import org.litepal.crud.LitePalSupport;

public class ClipItem extends LitePalSupport {
    private int id;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
