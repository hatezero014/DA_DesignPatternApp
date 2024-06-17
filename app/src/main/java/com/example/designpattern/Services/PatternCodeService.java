package com.example.designpattern.Services;

import android.content.Context;
import android.database.Cursor;

import com.example.designpattern.Models.PatternCode;

import java.util.ArrayList;

public class PatternCodeService extends BaseService{
    public PatternCodeService(Context context) {
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

    public PatternCode getCode(String patternName){
        PatternCode patternCode = null;
        db = getReadableDatabase();

        try (Cursor cursor = db.rawQuery("Select PatternCode.* from PatternCode inner join Pattern on PatternCode.PatternId = Pattern.Id where Pattern.Name = ?", new String[]{patternName})){
            if(cursor != null && cursor.moveToFirst()){
                patternCode = CreateModelObjectFromCursor(PatternCode.class,cursor);
            }
        }
        finally {
            if (db != null) {
                db.close();
            }
        }
        return patternCode;
    }
}
