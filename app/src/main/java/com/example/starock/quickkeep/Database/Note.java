package com.example.starock.quickkeep.Database;


import org.litepal.crud.LitePalSupport;

import java.util.Date;

public class Note extends LitePalSupport {
    private int id;                 //笔记ID
    private String title;           //笔记标题
    private String content;         //笔记内容
    private String type;            //笔记分类
    private String source;          //笔记来源
    private Date datetime;        //记录时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
}
