package com.sasha.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class GameDataSource {
    private SQLiteDatabase database;
    private DBHelper dbHelper;

    public GameDataSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insertGame(Game game) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_TITLE, game.getTitle());
        values.put(DBHelper.COLUMN_GENRE, game.getGenre());
        values.put(DBHelper.COLUMN_PLATFORM, game.getPlatform());

        return database.insert(DBHelper.TABLE_GAMES, null, values);
    }

    public List<Game> getAllGames() {
        List<Game> games = new ArrayList<>();
        Cursor cursor = database.query(DBHelper.TABLE_GAMES, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Game game = cursorToGame(cursor);
                games.add(game);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return games;
    }

    private Game cursorToGame(Cursor cursor) {
        Game game = new Game();
        game.setId(cursor.getLong(cursor.getColumnIndex(DBHelper.COLUMN_ID)));
        game.setTitle(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TITLE)));
        game.setGenre(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_GENRE)));
        game.setPlatform(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_PLATFORM)));

        return game;
    }
}