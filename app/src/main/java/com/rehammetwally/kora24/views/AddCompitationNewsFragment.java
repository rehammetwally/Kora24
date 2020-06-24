package com.rehammetwally.kora24.views;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.adapters.CustomSpinnerAdapter;
import com.rehammetwally.kora24.databinding.FragmentAddCompitationNewsBinding;
import com.rehammetwally.kora24.models.News;
import com.rehammetwally.kora24.models.Tournaments;
import com.rehammetwally.kora24.utils.FileUtils;
import com.rehammetwally.kora24.utils.MyApplication;
import com.rehammetwally.kora24.viewmodels.NewsViewModel;
import com.rehammetwally.kora24.viewmodels.TournamentsViewModel;

import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCompitationNewsFragment extends Fragment implements View.OnClickListener {

    private FragmentAddCompitationNewsBinding binding;
    private NewsViewModel newsViewModel;
    private TournamentsViewModel tournamentsViewModel;
    private CustomSpinnerAdapter customSpinnerAdapter;
    private String photo;
    private Calendar calendar;
    private int year, month, day;
    private static final int PICK_IMAGE = 2025;
    private static final String TAG = "AddCompitationNewsFragm";

    public AddCompitationNewsFragment() {
        // Required empty public constructor
    }
    public boolean onBackPressed() {
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_compitation_news, container, false);
        newsViewModel = ViewModelProviders.of(getActivity()).get(NewsViewModel.class);
        View view = binding.getRoot();

        tournamentsViewModel = ViewModelProviders.of(getActivity()).get(TournamentsViewModel.class);
        binding.selectNewsImage.setOnClickListener(this);
        binding.selectNewsDate.setOnClickListener(this);
        binding.addNewsInCompitationButton.setOnClickListener(this);
        tournamentsViewModel.showCompetitions().observe(getActivity(), new Observer<List<Tournaments>>() {
            @Override
            public void onChanged(List<Tournaments> tournaments) {

                Tournaments tournament = new Tournaments();
                tournament.title = getResources().getString(R.string.add_compitation_title);
                tournament.id = 0;
                tournaments.add(0, tournament);
                customSpinnerAdapter = new CustomSpinnerAdapter(getContext(), tournaments);
                binding.compitationSpinner.setAdapter(customSpinnerAdapter);
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_news_image:
                Dexter.withActivity(getActivity())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                openGallery();
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                if (response.isPermanentlyDenied()) {
                                    showSettingsDialog();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        })
                        .check();
                break;
            case R.id.select_news_date:
                Log.e(TAG, "onClick:select ");
                showDialog(999).show();
                break;


            case R.id.add_news_in_compitation_button:
                String title = binding.titleEdittext.getText().toString();
                String content = binding.contentEdittext.getText().toString();
                String date = binding.newsDate.getText().toString();
                int compitationId = (int) binding.compitationSpinner.getSelectedItemId();

                Log.e(TAG, "onClick: " + title);
                Log.e(TAG, "onClick: " + content);
                Log.e(TAG, "onClick: " + date);
                Log.e(TAG, "onClick: " + compitationId);
                Log.e(TAG, "onClick: " + photo);
                if (photo == null || title.isEmpty() || content.isEmpty() || date.isEmpty() || compitationId == 0) {
                    if (photo == null) {
                        MyApplication.showMessageBottom(binding.contentLayout, getResources().getString(R.string.error_photo_empty));
                        return;
                    }
                    if (title.isEmpty()) {
                        MyApplication.showMessageBottom(binding.contentLayout, getResources().getString(R.string.add_news_title) + getResources().getString(R.string.error_message_empty));
                        return;
                    }
                    if (content.isEmpty()) {
                        MyApplication.showMessageBottom(binding.contentLayout, getResources().getString(R.string.add_news_content) + getResources().getString(R.string.error_message_empty));
                        return;
                    }
                    if (date.isEmpty()) {
                        MyApplication.showMessageBottom(binding.contentLayout, getResources().getString(R.string.add_news_date) + getResources().getString(R.string.error_message_empty));
                        return;
                    }

                    if (compitationId == 0) {
                        MyApplication.showMessageBottom(v, getResources().getString(R.string.error_select) + " " + getResources().getString(R.string.add_compitation_title));
                        return;
                    }
                }
                addNewsForCompitation(title, content, date, compitationId, photo);
                break;
        }
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GO TO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getContext().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, PICK_IMAGE);
    }

    private void addNewsForCompitation(String title, String content, String date, int compitationId, String photo) {
        newsViewModel.addNewsForCompitation(title, content, date, compitationId, photo).observe(getActivity(), new Observer<News>() {
            @Override
            public void onChanged(News news) {
                Log.e(TAG, "onChanged: " + news.title);
                if (news != null) {
                    MyApplication.showMessageBottom(binding.contentLayout, getResources().getString(R.string.add_news_success));
                    binding.titleEdittext.setText("");
                    binding.contentEdittext.setText("");
                    binding.newsDate.setText("");
                    binding.newsPhoto.setImageDrawable(null);
                    binding.compitationSpinner.setSelection(0);
                } else {
                    MyApplication.showMessageBottom(binding.contentLayout, getResources().getString(R.string.try_again));
                }
            }
        });

    }


    public Dialog showDialog(int id) {
        if (id == 999) {
            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getContext(),
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        String monthStr = String.valueOf(month);
        String dayStr = String.valueOf(day);
        if (month < 10) {
            monthStr = "0" + month;
        }
        if (day < 10) {
            dayStr = "0" + day;
        }
        binding.newsDate.setText(new StringBuilder().append(year).append("-")
                .append(monthStr).append("-").append(dayStr));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && data != null) {
            Uri selectedImage = data.getData();
            photo = FileUtils.getPath(getContext(), selectedImage);

            Log.e(TAG, "onActivityResult: " + photo);
            Glide.with(getContext())
                    .setDefaultRequestOptions(new RequestOptions())
                    .load(photo)
                    .placeholder(R.drawable.ic_launcher)
                    .into(binding.newsPhoto);
        }
    }
}
