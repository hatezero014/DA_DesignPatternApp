package com.example.designpattern.Services;

import android.content.Context;
import android.database.Cursor;

import com.example.designpattern.Models.Pattern;
import com.example.designpattern.Models.PatternCode;
import com.example.designpattern.Models.PatternQuestion;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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

    public ArrayList<Pattern> GetAllByLanguage(String language) {
        ArrayList<Pattern> list = new ArrayList<>();
        db = this.getReadableDatabase();

        Cursor cursor = db.query(Pattern.class.getSimpleName(), null, "Language=?", new String[]{language}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Pattern object = CreateModelObjectFromCursor(Pattern.class, cursor);
                if (object != null) {
                    list.add(object);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    public ArrayList<Pattern> GetAllByCatalog(String catalog) {
        ArrayList<Pattern> list = new ArrayList<>();
        db = this.getReadableDatabase();

        Cursor cursor = db.query(Pattern.class.getSimpleName(), null, "Catalog=?", new String[]{catalog + " Patterns"}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Pattern object = CreateModelObjectFromCursor(Pattern.class, cursor);
                if (object != null) {
                    list.add(object);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    public ArrayList<Pattern> GetAllByLanguageAndCatalog(String language, String catalog) {
        ArrayList<Pattern> list = new ArrayList<>();
        db = this.getReadableDatabase();

        // Tạo câu truy vấn với điều kiện WHERE cho cả language và catalog
        String selection = "Language=? AND Catalog=?";
        String[] selectionArgs = {language, catalog + " Patterns"};

        Cursor cursor = db.query(Pattern.class.getSimpleName(), null, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Pattern object = CreateModelObjectFromCursor(Pattern.class, cursor);
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

    public Pattern getPatternRow(String patternName){
        Pattern pattern = null;
        db = getReadableDatabase();

        try (Cursor cursor = db.query(Pattern.class.getSimpleName(), null, "Name = ?", new String[]{patternName}, null, null, null)){
            if(cursor != null && cursor.moveToFirst()){
                pattern = CreateModelObjectFromCursor(Pattern.class,cursor);
            }
        }
        finally {
            if (db != null) {
                db.close();
            }
        }
        return pattern;
    }

    public <T> List<T> GetPatternisDone(Class<T> clazz) {
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Pattern where Pattern.isDone = 1", null);
        List<T> objects = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                T object = CreateModelObjectFromCursor(clazz, cursor);
                objects.add(object);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return objects;
    }

    public List<Pattern> GetPatternIdByName(String patternName) {
        List<Pattern> patternQuestion = new ArrayList<>();
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Pattern where Pattern.Name = ?", new String[]{patternName});
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Pattern object = CreateModelObjectFromCursor(Pattern.class, cursor);
                if (object != null) {
                    patternQuestion.add(object);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        return patternQuestion;
    }
}
