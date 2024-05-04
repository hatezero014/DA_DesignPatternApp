package com.example.designpattern.Services;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

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

    public <T> List<T> FindFavouriteByPatternId(Class<T> clazz) {
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Pattern.* FROM Favourite INNER JOIN Pattern ON Favourite.PatternId = Pattern.Id", null);
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
}
