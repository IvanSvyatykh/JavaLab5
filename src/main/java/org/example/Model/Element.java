package org.example.Model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Element implements FileElement {

    private String path;
    private String name;
    private String date;
    private String size;

    public Element(String name, Long date, Long size, String path) {
        this.name = name;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy HH:mm:ss");
        this.date = dateFormat.format(new Date(date)).toString();
        if (size == 0) {
            this.size = "";
        } else {
            this.size = size.toString() + " B";
        }

        this.path = path;
    }

    @Override
    public String getName() {
        return size.equals("") ? name + "/" : name;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public String getSize() {
        return size;
    }

    @Override
    public String getPath() {
        return path;
    }
}