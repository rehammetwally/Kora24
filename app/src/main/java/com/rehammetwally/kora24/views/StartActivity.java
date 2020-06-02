package com.rehammetwally.kora24.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.databinding.ActivityStartBinding;
import com.rehammetwally.kora24.utils.MyApplication;

import akndmr.github.io.colorprefutil.ColorPrefUtil;

public class StartActivity extends AppCompatActivity {
    private ActivityStartBinding binding;
    private static final String TAG = "StartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int themeSelected = MyApplication.getPref().getInt(MainActivity.THEME_SELECTED);
        ColorPrefUtil.changeThemeStyle(this, themeSelected);
        Log.e(TAG, "onCreate: " + themeSelected);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_start);
        Handler handler = new Handler(this);
        binding.setHandler(handler);
    }

    public class Handler {
        private Context context;

        public Handler(Context context) {
            this.context = context;
        }

        public void onStartAdmin(View view) {
            startActivity(new Intent(StartActivity.this, AdminActivity.class));
        }

        public void onStartUser(View view) {
            startActivity(new Intent(StartActivity.this, MainActivity.class));
        }
    }
}
