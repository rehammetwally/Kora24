package com.rehammetwally.kora24.utils;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;

import com.google.android.gms.common.SignInButton;
import com.google.android.material.snackbar.Snackbar;
import com.rehammetwally.kora24.BuildConfig;
import com.rehammetwally.kora24.R;

import java.util.Locale;

public class MyApplication extends Application {

    private static MyApplication mInstance;
    public static PreferencesHelper pref = null;
    private static final String PREFERENCES = "kora24_preferences";
    private Locale myLocale;

    public MyApplication() {

    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        if (pref == null) {
            pref = new PreferencesHelper(getSP());
        }
    }

    public static void showMessage(View parent, String message) {
        Snackbar snackbar = Snackbar
                .make(parent, message, Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        snackbar.setTextColor(parent.getContext().getResources().getColor(android.R.color.white));
        TextView tv = (snackbar.getView()).findViewById(com.google.android.material.R.id.snackbar_text);
        tv.setTypeface(ResourcesCompat.getFont(parent.getContext(), R.font.bukra));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        ViewCompat.setLayoutDirection(snackbar.getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
        snackbar.show();
    }

    public static void showMessageBottom(View parent, String message) {
        Snackbar snackbar = Snackbar
                .make(parent, message, Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        snackbar.setTextColor(parent.getContext().getResources().getColor(android.R.color.white));
        TextView tv = (snackbar.getView()).findViewById(com.google.android.material.R.id.snackbar_text);
        tv.setTypeface(ResourcesCompat.getFont(parent.getContext(), R.font.bukra));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM;
        view.setLayoutParams(params);
        ViewCompat.setLayoutDirection(snackbar.getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
        snackbar.show();
    }

    public static void showMessage(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        LinearLayout toastLayout = (LinearLayout) toast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        toastTV.setTypeface(ResourcesCompat.getFont(context, R.font.bukra));
        toastTV.setGravity(View.TEXT_ALIGNMENT_CENTER);
        toast.show();
    }


    public static void setGooglePlusButtonText(Context context, SignInButton signInButton, String buttonText) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
//                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) v.getLayoutParams();
//                params.setMargins(8, 8, 8, 8);
//                tv.setLayoutParams(params);
                tv.setTypeface(ResourcesCompat.getFont(context, R.font.bukra));
                tv.setText(buttonText);
                tv.setBackgroundColor(context.getResources().getColor(R.color.colorGoogle));
                tv.setTextColor(context.getResources().getColor(android.R.color.white));
//                Drawable img = context.getResources().getDrawable(R.drawable.google);
////                img.setBounds(8, 8, 8, 16);
//                tv.setCompoundDrawables(img, null, null, null);
                tv.setTextSize(16f);
                tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.google, 0, 0, 0);

                return;
            }
        }
    }

    public static void shareApp(Context context) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
            String shareMessage = "\nاسمحوا لي أن أرشح لكم هذا التطبيق\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            context.startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }

    private SharedPreferences getSP() {
        return getSharedPreferences(PREFERENCES, MODE_PRIVATE);
    }

    public static PreferencesHelper getPref() {
        return pref;
    }


    public static synchronized MyApplication getInstance() {
        return mInstance;
    }


    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }


}
