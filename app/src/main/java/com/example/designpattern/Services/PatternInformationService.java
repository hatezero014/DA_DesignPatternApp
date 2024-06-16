package com.example.designpattern.Services;

import android.content.Context;
import android.database.Cursor;

import com.example.designpattern.Models.PatternInformation;

import java.util.ArrayList;

public class PatternInformationService extends BaseService{
    public PatternInformationService(Context context) {
        super(context);
    }

    public <T> ArrayList<T> GetAll(Class<T> clazz) {
        ArrayList<T> list = new ArrayList<>();
        db = this.getReadableDatabase();
        Cursor cursor = db.query(clazz.getSimpleName(),null,null,null,null,null,null,null);
        if(cursor != null && cursor.moveToFirst()){
            do{
                T object = CreateModelObjectFromCursor(clazz,cursor);
                if (object != null) {
                    list.add(object);
                }
            }while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    public PatternInformation getInfo(String patternName){
        PatternInformation patternInfomation = null;
        db = getReadableDatabase();

        try (Cursor cursor = db.rawQuery("Select PatternInformation.* from PatternInformation inner join Pattern on PatternInformation.PatternId = Pattern.Id where Pattern.Name = ?", new String[]{patternName})){
            if(cursor != null && cursor.moveToFirst()){
                patternInfomation = CreateModelObjectFromCursor(PatternInformation.class,cursor);
            }
        }
        finally {
            if (db != null) {
                db.close();
            }
        }
        return patternInfomation;
    }
}
