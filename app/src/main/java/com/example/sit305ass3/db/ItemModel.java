package com.example.sit305ass3.db;

import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.time.Month;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@Entity
public class ItemModel {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "postType")
    String postType;
    @ColumnInfo(name = "title")
    String title;
    @ColumnInfo(name = "category")
    String category;
    @ColumnInfo(name = "phone")
    String phone;
    @ColumnInfo(name = "description")
    String description;
    @ColumnInfo(name = "date")
    String date;
    @ColumnInfo(name = "location")
    String location;
    @ColumnInfo(name = "imageUri")
    String imageUri;
    @ColumnInfo(name = "year")
    int year;
    @ColumnInfo(name = "month")
    String month;
    @ColumnInfo(name = "day")
    int day;
    @ColumnInfo(name = "hour")
    int hour;
    @ColumnInfo(name = "minute")
    int minute;


    @RequiresApi(api = Build.VERSION_CODES.O)
    public ItemModel(String postType, String title,String category, String phone, String description,
                      String date, String location, String imageUri) {
        //USER INPUTS
        this.postType = postType;
        this.title = title;
        this.category = category;
        this.phone = phone;
        this.description = description;
        this.date = date;
        this.location = location;
        this.imageUri = imageUri;

        //DATETIME STAMP
        LocalDateTime dateTimeStamp = LocalDateTime.now();
        year = dateTimeStamp.getYear();
        month = dateTimeStamp.getMonth().toString();
        day = dateTimeStamp.getDayOfMonth();
        hour = dateTimeStamp.getHour();
        minute = dateTimeStamp.getMinute();
    }
    public String getPostType() {
        return postType;
    }
    public String getTitle() {
        return title;
    }
    public String getCategory() {
        return category;
    }
    public String getPhone() {
        return phone;
    }
    public String getDescription() {
        return description;
    }
    public String getDate() {
        return date;
    }
    public String getLocation() {
        return location;
    }
    public String getImageUri()
    {
        return imageUri;
    }
    public int getYear() {
        return year;
    }
    public String getMonth() {
        return month;
    }
    public int getDay() {
        return day;
    }
    public int getHour() {
        return hour;
    }
    public int getMinute() {
        return minute;
    }
    public String getTimeStampAsString() {
        String m = String.valueOf(minute);
        if (minute < 10)
        {
            m = "0" + minute;
        }
        int h = hour % 12;
        if (h == 0) h = 12;
        String AM_PM = (hour > 12) ? "pm" : "am";
        return h + ":" + m + AM_PM;
    }
    public String getDateStampAsString() {
        return year + " " + month + " " + day;
    }
}
