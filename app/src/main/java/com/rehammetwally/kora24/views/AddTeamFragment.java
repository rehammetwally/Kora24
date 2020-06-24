package com.rehammetwally.kora24.views;

import android.Manifest;
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
import com.rehammetwally.kora24.adapters.CustomSpinnerAdapter;
import com.rehammetwally.kora24.databinding.FragmentAddTeamBinding;
import com.rehammetwally.kora24.models.Country;
import com.rehammetwally.kora24.models.Message;
import com.rehammetwally.kora24.utils.FileUtils;
import com.rehammetwally.kora24.utils.MyApplication;
import com.rehammetwally.kora24.viewmodels.TournamentsViewModel;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddTeamFragment extends Fragment implements View.OnClickListener {
    private FragmentAddTeamBinding binding;
    private TournamentsViewModel tournamentsViewModel;
    private CustomSpinnerAdapter adapter;
    private String photo;
    private static final String TAG = "AddTeamFragment";
    private static final int PICK_IMAGE = 20205;

    public AddTeamFragment() {
        // Required empty public constructor
    }

    public boolean onBackPressed() {
        return true;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_team, container, false);
        View view = binding.getRoot();
        tournamentsViewModel = ViewModelProviders.of(getActivity()).get(TournamentsViewModel.class);
        tournamentsViewModel.showCountries().observe(getActivity(), new Observer<List<Country>>() {
            @Override
            public void onChanged(List<Country> countries) {
                Country country = new Country();
                country.country_name = getResources().getString(R.string.country);
                country.id = 0;
                countries.add(0,country);
                Log.e(TAG, "onChanged: "+countries.size() );
                adapter = new CustomSpinnerAdapter(getContext(), countries);
                binding.countriesSpinner.setAdapter(adapter);
            }
        });

        binding.selectTeamImage.setOnClickListener(this);
        binding.addTeamButton.setOnClickListener(this);
        return view;
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
    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.select_team_image:
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
            case R.id.add_team_button:
                String team = binding.teamEdittext.getText().toString();
                int countryId = (int) binding.countriesSpinner.getSelectedItemId();
                Log.e(TAG, "onClick: " + team);
                Log.e(TAG, "onClick: " + countryId);
                Log.e(TAG, "onClick: " + photo);
                if (photo == null || team.isEmpty() ||countryId == 0) {
                    if (photo == null) {
                        MyApplication.showMessageBottom(v, getResources().getString(R.string.error_photo_empty));
                        return;
                    }
                    if (team.isEmpty()) {
                        MyApplication.showMessageBottom(v, getResources().getString(R.string.add_team_title) +" "+ getResources().getString(R.string.error_message_empty));
                        return;
                    }
                    if (countryId == 0) {
                        MyApplication.showMessageBottom(v, getResources().getString(R.string.error_select) + " " + getResources().getString(R.string.country));
                        return;
                    }

                }

               tournamentsViewModel.addTeam(team,photo,countryId).observe(getActivity(), new Observer<Message>() {
                   @Override
                   public void onChanged(Message message) {
                       if (message.message.equals("team added successfully")){
                           MyApplication.showMessageBottom(v,getString(R.string.add_team_success));

                           binding.teamEdittext.setText("");
                           binding.teamPhoto.setImageDrawable(null);
                           binding.countriesSpinner.setSelection(0);

                       }
                   }
               });
                break;
        }
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
                    .into(binding.teamPhoto);
        }
    }
}
