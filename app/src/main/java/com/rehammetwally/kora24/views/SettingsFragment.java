package com.rehammetwally.kora24.views;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.databinding.FragmentSettingsBinding;
import com.rehammetwally.kora24.utils.Config;
import com.rehammetwally.kora24.utils.MyApplication;
import com.rehammetwally.kora24.utils.NotificationUtils;

import org.json.JSONException;
import org.json.JSONObject;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.facebook.FacebookSdk.getFacebookDomain;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {
    private static final int SOUND_REQUEST_CODE = 5;
    public static final String MATCHES_REMINDER_SWITCH_ON_OFF = "MATCHES_REMINDER_SWITCH_ON_OFF";
    private String switchText;
    private FragmentSettingsBinding binding;
    public static final int SETTING_REQUEST_CODE = 10001;
    public static final String EXTRA_SETTINGS = "EXTRA_SETTINGS";
    private String chosenRingtone;

    private static final String TOPIC_POST_NOTIFICATION = "POST";
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private static final String TAG = "SettingsFragment";
    private Uri uri;
    private NotificationUtils notificationUtils;

    public SettingsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_settings, container, false);
        View view = binding.getRoot();
        setDefault();
        checkSwitch(binding.nightModeSwitch);
        checkSwitch(binding.matchesReminderSwitch);
        checkSwitch(binding.activateNotificationSwitch);
        checkSwitch(binding.muteNewsNotificationSwitch);
        checkSwitch(binding.muteNotificationSwitch);

        binding.notificationSoundLayout.setOnClickListener(this);


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");
//
//                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    Log.e(TAG, "onReceive: " + message);
                }
            }
        };
        return view;
    }

    private void setDefault() {
        if (MyApplication.getPref().getBoolean(MainActivity.NIGHT_MODE_SWITCH_ON_OFF)) {
//            if (MyApplication.getPref().getString(MainActivity.NIGHT_MODE_SWITCH_ON_OFF).equals("ON")) {
            binding.nightModeSwitch.setChecked(true);
//            } else if (MyApplication.getPref().getString(MainActivity.NIGHT_MODE_SWITCH_ON_OFF).equals("OFF")) {
//                binding.nightModeSwitch.setChecked(false);
//            } else {
//                binding.nightModeSwitch.setChecked(false);
//            }
        } else {
            binding.nightModeSwitch.setChecked(false);
        }
        if (MyApplication.getPref().getBoolean(MATCHES_REMINDER_SWITCH_ON_OFF)) {
//            if (MyApplication.getPref().getBoolean(MATCHES_REMINDER_SWITCH_ON_OFF)) {
            binding.matchesReminderSwitch.setChecked(true);
//            } else if (!MyApplication.getPref().getBoolean(MATCHES_REMINDER_SWITCH_ON_OFF)) {
//                binding.matchesReminderSwitch.setChecked(false);
//            } else {
//                binding.matchesReminderSwitch.setChecked(false);
//            }
        } else {
            binding.matchesReminderSwitch.setChecked(false);
        }
    }

//    private void addNotification() {
//        NotificationCompat.Builder builder =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.drawable.ic_notifications_green_24dp)
//                        .setContentTitle("Unique Andro Code")
//                        .setContentText("welcome To Unique Andro Code")
//                        .setAutoCancel(true);
//        Intent notificationIntent = new Intent(this, NotificationActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(pendingIntent);
//
//        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(0, builder.build());
//    }

    private void checkSwitch(final Switch switchButton) {

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switchText = switchButton.getTextOn().toString();
                } else {
                    switchText = switchButton.getTextOff().toString();
                }

                if (switchButton.getId() == R.id.night_mode_switch) {
                    if (switchText.equals("ON")) {
                        MyApplication.getPref().putBoolean(MainActivity.NIGHT_MODE_SWITCH_ON_OFF, true);
                        MyApplication.getPref().putInt(MainActivity.THEME_SELECTED, R.style.NightModeAppTheme);
                    } else if (switchText.equals("OFF")) {
                        MyApplication.getPref().putBoolean(MainActivity.NIGHT_MODE_SWITCH_ON_OFF, false);
                        MyApplication.getPref().putInt(MainActivity.THEME_SELECTED, R.style.NoActionBarTheme);
                    }

                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.putExtra(EXTRA_SETTINGS, true);
                    startActivityForResult(intent, SETTING_REQUEST_CODE);
                }

                if (switchButton.getId() == R.id.matches_reminder_switch) {
                    if (switchText.equals("ON")) {
//                        MyApplication.getPref().putString(MATCHES_REMINDER_SWITCH_ON_OFF,"ON");
                        MyApplication.getPref().putBoolean(MATCHES_REMINDER_SWITCH_ON_OFF, true);
                        subscribePostNotification();
//                        FirebaseInstanceId.getInstance().getInstanceId()
//                                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                                        if (!task.isSuccessful()) {
//                                            Log.w(TAG, "getInstanceId failed", task.getException());
//                                            return;
//                                        }
//
//                                        // Get new Instance ID token
//                                        String token = task.getResult().getToken();
//                                        Log.e(TAG, "onComplete: " + token);
//                                        // Log and toast
//                                        String msg = getString(R.string.fcm_token, token);
//                                        Log.d(TAG, msg);
//                                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                        MyApplication.getPref().putString("matches_reminder_switch", "ON");
//                        Gson gson = new Gson();
//                        final String remoteMessageStr = MyApplication.getPref().getString("REMOTEMESSAGE");
//                        RemoteMessage remoteMessage = gson.fromJson(remoteMessageStr, RemoteMessage.class);
////                        Log.e(TAG, "Notification: " + remoteMessage.getData().get("title"));
//                        if (remoteMessage != null) {
//                            if (remoteMessage.getNotification() != null) {
//                                Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
//                                handleNotification(remoteMessage.getNotification().getBody());
//                            }
//
//                            // Check if message contains a data payload.
//                            if (remoteMessage.getData().size() > 0) {
//                                Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
//
//                                try {
//                                    JSONObject json = new JSONObject(remoteMessage.getData().toString());
//                                    handleDataMessage(json);
//                                } catch (Exception e) {
//                                    Log.e(TAG, "Exception: " + e.getMessage());
//                                }
//                            }
//
//                        }

//                        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
//                        FirebaseInstanceId.getInstance().getInstanceId()
//                                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                                        if (!task.isSuccessful()) {
////To do//
//                                            return;
//                                        }
//
//// Get the Instance ID token//
//                                        String token = task.getResult().getToken();
//                                        String msg = getString(R.string.fcm_token, token);
//                                        Log.d(TAG, msg);
//
//                                    }
//                                });
                    } else if (switchText.equals("OFF")) {
                        unSubscribePostNotification();
//                        MyApplication.getPref().putString(MATCHES_REMINDER_SWITCH_ON_OFF,"OFF");
//                        MyApplication.getPref().putString(MATCHES_REMINDER_SWITCH_ON_OFF, "OFF");
                        MyApplication.getPref().putBoolean(MATCHES_REMINDER_SWITCH_ON_OFF, false);
                        FirebaseMessaging.getInstance().setAutoInitEnabled(false);
                    }

                }


                if (switchButton.getId() == R.id.activate_notification_switch) {
                    if (switchText.equals("ON")) {
                        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
                    } else if (switchText.equals("OFF")) {
                        FirebaseMessaging.getInstance().setAutoInitEnabled(false);
                    }

                }

            }
        });
    }


    private void unSubscribePostNotification() {
        FirebaseMessaging.getInstance().unsubscribeFromTopic("" + TOPIC_POST_NOTIFICATION).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                String msg = "You will not receive post notification";
                if (!task.isSuccessful())
                    msg = "UnSubscription failed";
                MyApplication.showMessageBottom(binding.content, "تم ايقاف اشعارات المباريات");
            }
        });
    }

    private void subscribePostNotification() {
        FirebaseMessaging.getInstance().subscribeToTopic("" + TOPIC_POST_NOTIFICATION).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                String msg = "You will receive post notification";
                if (!task.isSuccessful())
                    msg = "Subscription Successfully";
                MyApplication.showMessageBottom(binding.content, "تم تفعيل اشعارات المباريات");
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    private void displayFirebaseRegId() {
        String regId = MyApplication.getPref().getString("TOKEN");

        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId)) {
//            if (checkConnection())
//                settingsPresenter.addSubscriber(regId);
//            else
//                showInternetSnackBar();
        } else
            Log.e(TAG, "displayFirebaseRegId: " + "Firebase Reg Id is not received yet!");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.notification_sound_layout:
                setNotificationSound();
                break;
        }
    }

    private void setNotificationSound() {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
        this.startActivityForResult(intent, SOUND_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent intent) {
        if (resultCode == Activity.RESULT_OK && requestCode == SOUND_REQUEST_CODE) {
            uri = intent.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);

            if (uri != null) {
                this.chosenRingtone = uri.toString();
                playNotificationSound();
                sound();


            } else {
                this.chosenRingtone = null;
            }


            Log.e(TAG, "onActivityResult: " + chosenRingtone);
        }
    }

    public void playNotificationSound() {
        try {
            Log.e(TAG, "playNotificationSound: " + uri.toString());
            Ringtone r = RingtoneManager.getRingtone(getContext(), uri);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Uri sound() {
        return uri;
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//    }
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.details_menu, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                Intent intent = new Intent(getContext(), MainActivity.class);
//                intent.putExtra(MainActivity.EXTRA_MAIN,true);
//                startActivity(intent);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}
