package com.rehammetwally.kora24.views;

import android.Manifest;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.databinding.FragmentAddCompitationBinding;
import com.rehammetwally.kora24.databinding.FragmentAddNewsBinding;
import com.rehammetwally.kora24.models.Message;
import com.rehammetwally.kora24.models.Tournaments;
import com.rehammetwally.kora24.utils.FileUtils;
import com.rehammetwally.kora24.utils.MyApplication;
import com.rehammetwally.kora24.viewmodels.TournamentsViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCompitationFragment extends Fragment implements View.OnClickListener {
    private FragmentAddCompitationBinding binding;
    private TournamentsViewModel tournamentsViewModel;
    private static final int PICK_IMAGE = 20201;
    private String photo;
    private static final String TAG = "AddCompitationFragment";

    public AddCompitationFragment() {
        // Required empty public constructor
    }
    public boolean onBackPressed() {
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_compitation, container, false);
        tournamentsViewModel = ViewModelProviders.of(getActivity()).get(TournamentsViewModel.class);
        View view = binding.getRoot();

        binding.selectCompitationImage.setOnClickListener(this);
        binding.addCompitationButton.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_compitation_image:
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
            case R.id.add_compitation_button:
                String compitationName = binding.compitationEdittext.getText().toString();

                Log.e(TAG, "onClick: " + compitationName);
                Log.e(TAG, "onClick: " + photo);
                if (photo == null || compitationName.isEmpty()) {
                    if (photo == null) {
                        MyApplication.showMessageBottom(binding.contentLayout, getResources().getString(R.string.error_photo_empty));
                        return;
                    }
                    if (compitationName.isEmpty()) {
                        MyApplication.showMessageBottom(binding.contentLayout, getResources().getString(R.string.add_compitation_title) + " " + getResources().getString(R.string.error_message_empty));
                        return;
                    }

                }

                addCompitation(compitationName, photo);


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

    private void addCompitation(String compitationName, String photo) {
        tournamentsViewModel.addCompetition(compitationName, photo).observe(getActivity(), new Observer<Message>() {
            @Override
            public void onChanged(Message s) {
                if (s.message.equals("competition added successfully")) {
                    MyApplication.showMessageBottom(binding.contentLayout, getResources().getString(R.string.add_compitation_success));
                    binding.compitationEdittext.setText("");
                    binding.compitationPhoto.setImageDrawable(null);
                } else {
                    MyApplication.showMessageBottom(binding.contentLayout, getResources().getString(R.string.try_again));
                }
            }
        });

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && data != null) {
            Uri selectedImage = data.getData();
            photo = FileUtils.getPath(getContext(), selectedImage);
            ;
            Log.e(TAG, "onActivityResult: " + photo);
            Glide.with(getContext())
                    .setDefaultRequestOptions(new RequestOptions())
                    .load(photo)
                    .placeholder(R.drawable.ic_launcher)
                    .into(binding.compitationPhoto);
        }
    }
}
