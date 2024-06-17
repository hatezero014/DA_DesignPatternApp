package com.example.designpattern.Services;

import android.content.Context;
import android.database.Cursor;

import com.example.designpattern.Models.PatternCode;
import com.example.designpattern.Models.PatternResult;

import java.util.ArrayList;

public class PatternResultService extends BaseService{
    public PatternResultService(Context context) {
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

    public PatternResult getCodeResult(String patternName){
        PatternResult patternResult = null;
        db = getReadableDatabase();

        try (Cursor cursor = db.rawQuery("Select PatternResult.* from PatternResult inner join Pattern on PatternResult.PatternId = Pattern.Id where Pattern.Name = ?", new String[]{patternName})){
            if(cursor != null && cursor.moveToFirst()){
                patternResult = CreateModelObjectFromCursor(PatternResult.class,cursor);
            }
        }
        finally {
            if (db != null) {
                db.close();
            }
        }
        return patternResult;
    }
}
