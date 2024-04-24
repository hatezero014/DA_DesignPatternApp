package com.example.designpattern.Services;

import android.content.Context;
import android.database.Cursor;

import com.example.designpattern.Models.Pattern;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class PatternService extends BaseService{
    public PatternService(Context context) {
        super(context);
    }

    public <T> ArrayList<T> GetAll(Class<T> clazz) {
        ArrayList<T> list = new ArrayList<>();
        db = this.getReadableDatabase();
        Cursor cursor = db.query(clazz.getSimpleName(), null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                T object = CreateModelObjectFromCursor(clazz, cursor);
                if (object != null) {
                    list.add(object);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    public <T> ArrayList<T> GetAllByLanguage(Class<T> clazz, String language) {
        ArrayList<T> list = new ArrayList<>();
        db = this.getReadableDatabase();

        Cursor cursor = db.query(clazz.getSimpleName(), null, "Language=?", new String[]{language}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                T object = CreateModelObjectFromCursor(clazz, cursor);
                if (object != null) {
                    list.add(object);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    public ArrayList<Pattern> Search(String key) {
        ArrayList<Pattern> list = new ArrayList<>();
        db = this.getReadableDatabase();

        Field[] fields = Pattern.class.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isSynthetic() && !field.isEnumConstant()) {
                String fieldName = field.getName();
                String condition = "%" + key + "%";
                String[] selectionArgs = new String[]{condition};
                Cursor cursor = db.query(Pattern.class.getSimpleName(), null, "LOWER(" + fieldName + ") LIKE ?", selectionArgs, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        Pattern object = CreateModelObjectFromCursor(Pattern.class, cursor);
                        if (object != null) {
                            list.add(object);
                        }
                    } while (cursor.moveToNext());
                    cursor.close();
                }
            }
        }
        return list;
    }
}
