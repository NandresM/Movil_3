package co.edu.uniminuto.actividad_4.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;


import java.util.ArrayList;

import co.edu.uniminuto.actividad_4.dataaccess.ManagerDataBase;
import co.edu.uniminuto.actividad_4.entities.User;

public class UserRepository {
    private ManagerDataBase dataBase;
    private Context context;
    private View view;
    private User user;

    public UserRepository(Context context, View view){
        this.context = context;
        this.view = view;
        this.dataBase = new ManagerDataBase(context);
    }

    public void insertUser(User user){
        try {
            SQLiteDatabase dataBaseSql = dataBase.getWritableDatabase();
            if (dataBaseSql != null){
                ContentValues values = new ContentValues();
                values.put("use_document", user.getDocument());
                values.put("user_name", user.getName());
                values.put("use_lastname", user.getLastname());
                values.put("use_user", user.getUser());
                values.put("use_pass", user.getPass());
                values.put("use_status", "1");
                long response = dataBaseSql.insert(ManagerDataBase.TABLE_USERS, null, values);
                String message = (response >= 1) ? "Se registro correctamente" : "No se registro correctamente";
                Snackbar.make(this.view, message, Snackbar.LENGTH_LONG).show();
                dataBaseSql.close();
            }
        } catch (SQLException e){
            Log.i("Error en Base de datos", "insertUser: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public ArrayList<User> getUserList() {
        SQLiteDatabase dataBaseSql = dataBase.getReadableDatabase();
        String query = "SELECT * FROM users WHERE use_status = 1";
        ArrayList<User> users = new ArrayList<>();
        Cursor cursor = dataBaseSql.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                User user = new User( );
                user.setDocument(cursor.getInt(0));
                user.setName(cursor.getString(1));
                user.setLastname(cursor.getString(2));
                user.setUser(cursor.getString(3));
                user.setPass(cursor.getString(4));
                users.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        dataBaseSql.close();
        return users;
    }

    public User getUserByDocument(int documento) {
        SQLiteDatabase dataBaseSql = dataBase.getReadableDatabase();
        String query = "SELECT * FROM " + ManagerDataBase.TABLE_USERS + " WHERE use_document = ? AND use_status = '1'";
        Cursor cursor = dataBaseSql.rawQuery(query, new String[]{String.valueOf(documento)});
        User user = null;
        if (cursor.moveToFirst()) {
            user = new User();
            user.setDocument(cursor.getInt(0));
            user.setName(cursor.getString(1));
            user.setLastname(cursor.getString(2));
            user.setUser(cursor.getString(3));
            user.setPass(cursor.getString(4));
        }
        cursor.close();
        dataBaseSql.close();
        return user;
    }

    public boolean deleteUser(int documento) {
        SQLiteDatabase dataBaseSql = dataBase.getWritableDatabase();
        int result = dataBaseSql.delete(ManagerDataBase.TABLE_USERS, "use_document = ?",
                new String[]{String.valueOf(documento)});
        dataBaseSql.close();
        return result > 0;
    }





}

