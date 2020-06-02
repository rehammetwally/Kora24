package com.rehammetwally.kora24.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.databinding.ActivityAdminBinding;
import com.rehammetwally.kora24.models.User;
import com.rehammetwally.kora24.utils.MyApplication;
import com.rehammetwally.kora24.viewmodels.UserViewModel;

import akndmr.github.io.colorprefutil.ColorPrefUtil;

public class AdminActivity extends AppCompatActivity {
    private ActivityAdminBinding binding;
    private UserViewModel userViewModel;
    private static final String TAG = "AdminActivity";

    @Override
    protected void onStart() {
        super.onStart();
        if (MyApplication.getPref().getBoolean("LOGIN") && MyApplication.getPref().getInt("TYPE")==1) {
            startActivity(new Intent(AdminActivity.this, AdminMainActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int themeSelected = MyApplication.getPref().getInt(MainActivity.THEME_SELECTED);
        ColorPrefUtil.changeThemeStyle(this, themeSelected);
        Log.e(TAG, "onCreate: " + themeSelected);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_admin);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.emailEdittext.getText().toString();
                String password = binding.passwordEdittext.getText().toString();
                if (email.isEmpty() || password.isEmpty()) {
                    MyApplication.showMessage(binding.contentLayout, getResources().getString(R.string.error_message_empty));
                    return;
                }
                if (password.length() < 6) {
                    MyApplication.showMessage(binding.contentLayout, getResources().getString(R.string.error_password));
                    return;
                }
                Log.e(TAG, "loginWithEmail: " + email);
                Log.e(TAG, "loginWithEmail: " + password);
                loginWithEmailAndPassword(email, password);
            }
        });
    }


    private void loginWithEmailAndPassword(String email, String password) {
        Log.e(TAG, "loginWithEmailAndPassword: " + email);
        Log.e(TAG, "loginWithEmailAndPassword: " + password);
        if (email.trim().equals("kora242020@gmail.com") && password.trim().equals("kora123456")) {
            userViewModel.login(email, password).observe(this, new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    Log.e(TAG, "onChanged: " + user);
                    if (user.message.equals("Returned Successfully")) {
                        MyApplication.showMessage(binding.contentLayout, getResources().getString(R.string.login_success));
                        MyApplication.getPref().putBoolean("LOGIN", true);
                        MyApplication.getPref().putInt("TYPE", user.data.user_type);
                        MyApplication.getPref().putInt("USERID",user.data.id);
                        startActivity(new Intent(AdminActivity.this, AdminMainActivity.class));
                    } else if (user.message.equals("Not Found")) {
                        MyApplication.showMessageBottom(binding.contentLayout, getResources().getString(R.string.user_not_found));

                    }
                    Log.e(TAG, "onChanged: " + user.message);

                }
            });
        }else {
            MyApplication.showMessageBottom(binding.contentLayout, getResources().getString(R.string.user_not_found));
        }

    }


}
