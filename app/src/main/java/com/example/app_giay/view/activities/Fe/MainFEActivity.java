package com.example.app_giay.view.activities.Fe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_giay.R;
import com.example.app_giay.adapter.feAdpter;
import com.example.app_giay.dao.SanPhamDao;
import com.example.app_giay.model.SanPham;
import com.example.app_giay.view.activities.Ba.ShoppingCart.DonHangttActivity;
import com.example.app_giay.view.activities.Ba.ShoppingCart.shoppingCartActivity;
import com.example.app_giay.view.activities.SigninActivity;

import java.util.ArrayList;

public class MainFEActivity extends AppCompatActivity {
    GridView gridView;
    feAdpter adapter;
    ImageButton imgbtnGioHang,ImgbtnDonHang;
    SanPhamDao sanPhamDao = new SanPhamDao(this);
    ArrayList<SanPham> data = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_feactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imgbtnGioHang = findViewById(R.id.imgbtnGioHang);
        gridView = findViewById(R.id.gridView);
        data = sanPhamDao.getAllSanPham();
        adapter = new feAdpter(this, R.layout.layout_grid_viewfe, data);
        gridView.setAdapter(adapter);
        imgbtnGioHang.setOnClickListener(v -> {
            Intent intent = new Intent(MainFEActivity.this, shoppingCartActivity.class);
            startActivity(intent);
        });
        ImgbtnDonHang = findViewById(R.id.imgbtnDonHang);
        ImgbtnDonHang.setOnClickListener(v -> {
            Intent intent = new Intent(MainFEActivity.this, DonHangttActivity.class);
            startActivity(intent);
        });

        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            // Hiển thị hộp thoại xác nhận
            new AlertDialog.Builder(MainFEActivity.this)
                    .setTitle("Xác nhận đăng xuất")
                    .setMessage("Bạn có chắc chắn muốn đăng xuất không?")
                    .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                // Thực hiện thao tác đăng xuất ở đây
                                Toast.makeText(MainFEActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                                // Chuyển người dùng về màn hình đăng nhập
                                Intent intent = new Intent(MainFEActivity.this, SigninActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish(); // Đóng màn hình hiện tại
                            } catch (Exception e) {
                                // Bắt lỗi và thông báo cho người dùng
                                Toast.makeText(MainFEActivity.this, "Error during logout: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss(); // Đóng hộp thoại
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert) // Biểu tượng cho hộp thoại
                    .show();
        });
    }
}
