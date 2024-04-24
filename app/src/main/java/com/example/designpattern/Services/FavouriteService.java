package com.example.designpattern.Services;

import android.content.Context;
import android.database.Cursor;

public class FavouriteService extends BaseService {
    public FavouriteService(Context context) {
        super(context);
    }

    public <T> T FindByPatternId(Class<T> clazz, int patternId) {
        db = this.getReadableDatabase();
        Cursor cursor = db.query(clazz.getSimpleName(), null, "PatternId=?", new String[]{String.valueOf(patternId)}, null, null, null);
        T object = null;
        if (cursor != null && cursor.moveToFirst()) {
            object = CreateModelObjectFromCursor(clazz, cursor);
            cursor.close();
        }
        return object;
    }
    public <T> void DeleteByPatternId(Class<T> clazz, int patternId) {
        db = this.getWritableDatabase();
        db.delete(clazz.getSimpleName(), "PatternId=?", new String[]{String.valueOf(patternId)});
    }
}
