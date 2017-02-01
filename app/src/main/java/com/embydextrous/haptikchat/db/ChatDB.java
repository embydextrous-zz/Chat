package com.embydextrous.haptikchat.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.embydextrous.haptikchat.model.Message;

import java.util.ArrayList;

public class ChatDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "haptik_chat.db";
    private static final int DB_VERSION = 1;

    private static final String TAB_CHAT_DB = "chat_table";

    public static final String KEY_TIMESTAMP = "timestamp";
    public static final String KEY_FROM_USER = "user_from_id";
    public static final String KEY_SENDER_NAME = "sender_name";
    public static final String KEY_FAVOURITE = "is_favorite";
    public static final String KEY_BODY = "body";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_ID = "id";

    public ChatDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TAB_CHAT_DB + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_TIMESTAMP + " TEXT, "
                + KEY_BODY+ " TEXT, "
                + KEY_FROM_USER + " TEXT, "
                + KEY_SENDER_NAME + " TEXT, "
                + KEY_FAVOURITE + " INTEGER DEFAULT 0, "
                + KEY_IMAGE + " TEXT );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertMessage(Message message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_BODY, message.getBody());
        values.put(KEY_TIMESTAMP, message.getTimeStamp());
        values.put(KEY_FROM_USER, message.getUserName());
        values.put(KEY_SENDER_NAME, message.getName());
        values.put(KEY_FAVOURITE, message.isFavourite()?1:0);
        values.put(KEY_IMAGE, message.getImageUrl());
        db.insert(TAB_CHAT_DB, null, values);
    }

    public ArrayList<Message> getMessages() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TAB_CHAT_DB + " ORDER BY " + KEY_TIMESTAMP, null);
        ArrayList<Message> messages = new ArrayList<>();
        if (c.getCount()==0) {
            c.close();
            return messages;
        }
        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex(KEY_ID));
            String timestamp = c.getString(c.getColumnIndex(KEY_TIMESTAMP));
            String body = c.getString(c.getColumnIndex(KEY_BODY));
            String user = c.getString(c.getColumnIndex(KEY_FROM_USER));
            String name = c.getString(c.getColumnIndex(KEY_SENDER_NAME));
            String image = c.getString(c.getColumnIndex(KEY_IMAGE));
            boolean isFavorite = c.getInt(c.getColumnIndex(KEY_FAVOURITE))==1;
            Message message = new Message(id, body, user, image, timestamp, name, isFavorite);
            messages.add(message);
        }
        c.close();
        return messages;
    }

    public boolean markFavourite(Message message) {
        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_FAVOURITE, message.isFavourite()?1:0);
        int result = db.update(TAB_CHAT_DB, contentValues, KEY_ID + "=?", new String[]{message.getId() + ""});
        return result != -1;
    }

    public Cursor getUserCursor() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT *, count(*), sum(" + KEY_FAVOURITE + ") FROM " + TAB_CHAT_DB + " GROUP BY " + KEY_FROM_USER, null);
        return c;
    }
}
