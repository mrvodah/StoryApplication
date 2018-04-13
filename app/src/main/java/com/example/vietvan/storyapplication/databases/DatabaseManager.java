package com.example.vietvan.storyapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by VietVan on 23/03/2018.
 */

public class DatabaseManager {

    public static String TAG = "DatabaseManager";
    public static final String TABLE_STORY = "tbl_story";
    public static final String TABLE_TYPE_STORY = "tbl_story_type";

    private SQLiteDatabase sqLiteDatabase;
    public static DatabaseManager db;
    private com.example.vietvan.storyapplication.AssetHelper assetHelper;

    public DatabaseManager(Context context){
        assetHelper = new com.example.vietvan.storyapplication.AssetHelper(context);

        sqLiteDatabase = assetHelper.getWritableDatabase();
    }

    public static DatabaseManager newInstance(Context context){
        if(db == null)
            db = new DatabaseManager(context);

        return db;
    }

    public List<com.example.vietvan.storyapplication.Story> getListStory(){
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_STORY, null);
        List<com.example.vietvan.storyapplication.Story> list = new ArrayList<>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            list.add(new com.example.vietvan.storyapplication.Story(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5), cursor.getInt(6), cursor.getInt(7)));

            cursor.moveToNext();
        }
        Log.d(TAG, "getListStory: " + list);
        return list;
    }

    public List<com.example.vietvan.storyapplication.Story_type> getListStoryType(){
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_TYPE_STORY, null);
        List<com.example.vietvan.storyapplication.Story_type> list = new ArrayList<>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            list.add(new com.example.vietvan.storyapplication.Story_type(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3)));

            cursor.moveToNext();
        }
        Log.d(TAG, "getListStoryType: " + list);
        return list;
    }

    public HashMap<String, List<com.example.vietvan.storyapplication.Story>> getHashMapStory(List<com.example.vietvan.storyapplication.Story_type> story_types, List<com.example.vietvan.storyapplication.Story> stories){
        HashMap<String, List<com.example.vietvan.storyapplication.Story>> hashMap = new HashMap<>();
        for(int i=0;i<story_types.size();i++){
            List<com.example.vietvan.storyapplication.Story> storyList = new ArrayList<>();
            for(int j=0;j<stories.size();j++){
                if(story_types.get(i).id == stories.get(j).id_type)
                    storyList.add(stories.get(j));
            }
            hashMap.put(story_types.get(i).name, storyList);
        }

        return hashMap;
    }

    public HashMap<String, List<com.example.vietvan.storyapplication.Story>> getHashMapStoryLike(List<com.example.vietvan.storyapplication.Story_type> story_types, List<com.example.vietvan.storyapplication.Story> stories){
        HashMap<String, List<com.example.vietvan.storyapplication.Story>> hashMap = new HashMap<>();
        for(int i=0;i<story_types.size();i++){
            List<com.example.vietvan.storyapplication.Story> storyList = new ArrayList<>();
            for(int j=0;j<stories.size();j++){
                if(story_types.get(i).id == stories.get(j).id_type && stories.get(j).bookmark == 1)
                    storyList.add(stories.get(j));
            }
            hashMap.put(story_types.get(i).name, storyList);
        }

        return hashMap;
    }

    public HashMap<String, List<com.example.vietvan.storyapplication.Story>> getHashMapStoryUnLike(List<com.example.vietvan.storyapplication.Story_type> story_types, List<com.example.vietvan.storyapplication.Story> stories){
        HashMap<String, List<com.example.vietvan.storyapplication.Story>> hashMap = new HashMap<>();
        for(int i=0;i<story_types.size();i++){
            List<com.example.vietvan.storyapplication.Story> storyList = new ArrayList<>();
            for(int j=0;j<stories.size();j++){
                if(story_types.get(i).id == stories.get(j).id_type && stories.get(j).bookmark != 1)
                    storyList.add(stories.get(j));
            }
            hashMap.put(story_types.get(i).name, storyList);
        }

        return hashMap;
    }

    public void updateMark(com.example.vietvan.storyapplication.Story story, int mark){
        sqLiteDatabase = assetHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("bookmark", mark);

        sqLiteDatabase.update(TABLE_STORY, values, "id = ?", new String[]{String.valueOf(story.id)});
    }

    public List<String> getListTitle(){
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_STORY, null);
        List<String> list = new ArrayList<>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            list.add(cursor.getString(1));

            cursor.moveToNext();
        }

        return list;
    }

    public com.example.vietvan.storyapplication.Story findStoryByTitle(String title){
        if(title.contains("...")){
            List<com.example.vietvan.storyapplication.Story> list = getListStory();
            for(com.example.vietvan.storyapplication.Story s : list)
                if(s.title.toLowerCase().contains(((title.substring(0, 21)))))
                    return s;
        }
        else{
//            Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_STORY + " where title = '" + title + "'", null);
//            cursor.moveToFirst();
//
//            com.example.vietvan.storyapplication.Story story = new com.example.vietvan.storyapplication.Story(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
//                    cursor.getString(4), cursor.getString(5), cursor.getInt(6), cursor.getInt(7));
//
//            return story;

            List<com.example.vietvan.storyapplication.Story> list = getListStory();
            for(com.example.vietvan.storyapplication.Story s : list)
                if(s.title.toLowerCase().contains((title)))
                    return s;

        }

        return null;
    }

}
