package com.example.designpattern.Services;

import android.content.Context;
import android.database.Cursor;

import com.example.designpattern.Models.PatternInformation;
import com.example.designpattern.Models.PatternQuestion;

import java.util.ArrayList;
import java.util.List;

public class PatternQuestionService extends BaseService{
    public PatternQuestionService(Context context) {
        super(context);
    }
    public List<PatternQuestion> getQuestion(String patternName){
        List<PatternQuestion> patternQuestion = new ArrayList<>();
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select PatternQuestion.* from PatternQuestion inner join Pattern on PatternQuestion.PatternId = Pattern.Id where Pattern.Name = ?", new String[]{patternName});
        if (cursor != null && cursor.moveToFirst()) {
            do {
                PatternQuestion object = CreateModelObjectFromCursor(PatternQuestion.class, cursor);
                if (object != null) {
                    patternQuestion.add(object);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        return patternQuestion;
    }

    public <T> List<T> GetQuestionByPatternId(Class<T> clazz, String id) {
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from PatternQuestion where PatternQuestion.PatternId = ?", new String[]{id});
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

    public List<PatternQuestion> GetTableQuestionByQuestion(String patternName) {
        List<PatternQuestion> patternQuestion = new ArrayList<>();
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from PatternQuestion where PatternQuestion.Question = ?", new String[]{patternName});
        if (cursor != null && cursor.moveToFirst()) {
            do {
                PatternQuestion object = CreateModelObjectFromCursor(PatternQuestion.class, cursor);
                if (object != null) {
                    patternQuestion.add(object);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        return patternQuestion;
    }
}
