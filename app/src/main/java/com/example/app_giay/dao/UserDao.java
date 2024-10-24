package com.example.app_giay.dao;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_giay.database.DatabaseHelper;
import com.example.app_giay.model.User;

public class UserDao {
    private DatabaseHelper dbHelper;

    public UserDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }
    public void addUser(String username, String password, int role_id ,String name) {
        dbHelper.getWritableDatabase();
        String sql = "INSERT INTO users (username, password, role_id, name) VALUES (?, ?, ?, ?)";
        Object[] args = {username, password, role_id, name};
        dbHelper.getWritableDatabase().execSQL(sql, args);

    }
    public int getRoleId(String username) {
        String sql = "SELECT role_id FROM users WHERE username = ?";
        String[] args = {username};
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(sql, args);

        if (cursor != null) {
            if (cursor.moveToFirst()) { // Kiểm tra xem con trỏ có dữ liệu hay không
                int roleId = cursor.getInt(0); // Lấy giá trị role_id
                cursor.close(); // Đảm bảo đóng con trỏ
                return roleId; // Trả về role_id
            }
            cursor.close(); // Đóng con trỏ nếu không có dữ liệu
        }
        return -1; // Trả về -1 nếu không tìm thấy người dùng
    }

    public boolean checkLogin(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        String[] args = {username, password};
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(sql, args);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    public boolean checkUsernameExists(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        String[] args = {username};
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(sql, args);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    public int getUserId(String username) {
        String sql = "SELECT users_id FROM users WHERE username = ?";
        String[] args = {username};

        // Khai báo biến cho SQLiteDatabase
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try (Cursor cursor = db.rawQuery(sql, args)) {
            if (cursor.moveToFirst()) {
                return cursor.getInt(0); // Trả về userId nếu tìm thấy
            }
        } catch (Exception e) {
            e.printStackTrace(); // Ghi lại ngoại lệ nếu có
        }

        return -1; // Trả về -1 nếu không tìm thấy userId
    }

}
