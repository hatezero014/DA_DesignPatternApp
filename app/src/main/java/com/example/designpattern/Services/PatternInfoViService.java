package com.example.designpattern.Services;

import android.content.Context;
import android.database.Cursor;

import com.example.designpattern.Models.PatternInfoVi;
import com.example.designpattern.Models.PatternInformation;

public class PatternInfoViService extends BaseService{
    public PatternInfoViService(Context context) {
        super(context);
    }

    public PatternInfoVi getInfoVi(String patternName){
        PatternInfoVi patternInfoVi = null;
        db = getReadableDatabase();

        try (Cursor cursor = db.rawQuery("Select PatternInfoVi.* from PatternInfoVi inner join Pattern on PatternInfoVi.PatternId = Pattern.Id where Pattern.Name = ?", new String[]{patternName})){
            if(cursor != null && cursor.moveToFirst()){
                patternInfoVi = CreateModelObjectFromCursor(PatternInfoVi.class,cursor);
            }
        }
        finally {
            if (db != null) {
                db.close();
            }
        }
        return patternInfoVi;
    }
}
