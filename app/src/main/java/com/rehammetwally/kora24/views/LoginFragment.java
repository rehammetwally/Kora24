package com.rehammetwally.kora24.views;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.databinding.FragmentLoginBinding;
import com.rehammetwally.kora24.models.User;
import com.rehammetwally.kora24.utils.FileUtils;
import com.rehammetwally.kora24.utils.MyApplication;
import com.rehammetwally.kora24.viewmodels.UserViewModel;
import com.facebook.FacebookSdk;

import java.io.File;
import java.net.URI;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private static final int GOOGLE_SIGN_IN = 1005;
    private static final int PICK_IMAGE = 2020;
    private static final int GALLERY_REQUEST_CODE = 8888;
    private FragmentLoginBinding binding;
    private CallbackManager callbackManager;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private AccessTokenTracker accessTokenTracker;
    private GoogleSignInClient googleSignInClient;
    private UserViewModel userViewModel;
    private String photo;
    private static final String TAG = "LoginFragment";

    public static final int REQUEST_GALLERY_IMAGE = 1;

    public LoginFragment() {
        // Required empty public constructor
    }

    public boolean onBackPressed() {
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);

        View view = binding.getRoot();
        firebaseAuth = FirebaseAuth.getInstance();
//        FacebookSdk.sdkInitialize(getContext());
        FacebookSdk.fullyInitialize();
        callbackManager = CallbackManager.Factory.create();
        binding.facebookLoginButton.setFragment(this);
        facebookLogin();

        binding.loginButton.setOnClickListener(this);


        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                userDetails(user);
            }
        };

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    firebaseAuth.signOut();
                }
            }
        };

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        binding.googleLoginButton.setOnClickListener(this);


        binding.fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == binding.fb) {
                    binding.facebookLoginButton.performClick();
                }
            }
        });

        MyApplication.setGooglePlusButtonText(getContext(), binding.googleLoginButton, getResources().getString(R.string.google_login));

        binding.dontHasAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.dontHasAccount.getText().toString().equals(getResources().getString(R.string.dont_has_account))) {
                    resetEditText(binding.userNameEdittext, Gravity.RIGHT, getResources().getColor(R.color.colorPrimary), getResources().getColor(android.R.color.white));
                    binding.registerLayout.setVisibility(View.VISIBLE);
                    binding.userImage.setVisibility(View.GONE);
                    binding.userNameEdittext.setVisibility(View.VISIBLE);
                    binding.loginButton.setText(getResources().getString(R.string.register));
                    binding.dontHasAccount.setText(getResources().getString(R.string.have_account));
                } else if (binding.dontHasAccount.getText().toString().equals(getResources().getString(R.string.have_account))) {
                    binding.registerLayout.setVisibility(View.GONE);
                    binding.userImage.setVisibility(View.VISIBLE);
                    binding.userNameEdittext.setVisibility(View.INVISIBLE);
                    binding.loginButton.setText(getResources().getString(R.string.login));
                    binding.dontHasAccount.setText(getResources().getString(R.string.dont_has_account));
                }
            }
        });

        binding.registerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
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
        Log.e(TAG, "openGallery: ");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                String name = account.getPhotoUrl().toString();
                String email = account.getEmail();
                String password = "123456";
//                registerWithEmailAndPassword(name, email, password,photo);
                Log.e(TAG, "registerWithEmailAndPassword:email " + email);
                Log.e(TAG, "registerWithEmailAndPassword:password " + password);
                Log.e(TAG, "registerWithEmailAndPassword:name " + name);
                Log.e(TAG, "registerWithEmailAndPassword:photo " + photo);
                userViewModel.register(name, email, password, photo).observe(getActivity(), new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        if (user != null) {
                            Log.e(TAG, "onChanged: " + user);
                            Log.e(TAG, "onChanged: " + user.message);
//                                MyApplication.showMessage(binding.contentLayout, getResources().getString(R.string.register_success));
                            binding.registerLayout.setVisibility(View.GONE);
                            binding.userImage.setVisibility(View.VISIBLE);
                            binding.userNameEdittext.setVisibility(View.INVISIBLE);
                            binding.loginButton.setText(getResources().getString(R.string.login));
                            binding.dontHasAccount.setText(getResources().getString(R.string.dont_has_account));
//                    Log.e(TAG, "onChanged: Firebase"+user.data.id );
                        } else {
                            Log.e(TAG, "onChanged: " + user.message);
//                                MyApplication.showMessageBottom(binding.contentLayout, getResources().getString(R.string.try_again));
                        }
                        Log.e(TAG, "onChanged: " + user.message);

                    }
                });
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {

                MyApplication.showMessageBottom(binding.contentLayout, task.getException().getMessage());
                Log.w(TAG, "Google sign in failed", e);
            }
        }


        if (requestCode == PICK_IMAGE && data != null) {
            Uri selectedImage = data.getData();
            binding.registerImage.setBorderWidth(0);
            binding.registerImage.setImageURI(selectedImage);
            photo = FileUtils.getPath(getContext(), selectedImage);

            Log.e(TAG, "onActivityResult: " + photo);
            Log.e(TAG, "onActivityResult: " + selectedImage.toString());
//            binding.registerImage.setImageURI(selectedImage);
            Glide.with(getContext())
                    .setDefaultRequestOptions(new RequestOptions())
                    .load(photo)
                    .circleCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .into(binding.registerImage);
        }
    }


    private void facebookLogin() {
        binding.facebookLoginButton.setPermissions("email", "public_profile");
        binding.facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.e(TAG, "onCancel: ");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e(TAG, "onError: " + error);
            }
        });
    }

    private void loginWithEmailAndPassword(String email, String password) {
        Log.e(TAG, "loginWithEmailAndPassword: " + email);
        Log.e(TAG, "loginWithEmailAndPassword: " + password);
        if (!email.trim().equals("kora242020@gmail.com") && !password.trim().equals("kora123456")) {
            userViewModel.login(email, password).observe(getActivity(), new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    Log.e(TAG, "onChanged: " + user);
                    if (user != null) {
                        MyApplication.showMessageBottom(binding.contentLayout, getResources().getString(R.string.login_success));
                        MyApplication.getPref().putInt("USERID", user.data.id);
                        MyApplication.getPref().putBoolean("LOGIN", true);
                        MyApplication.getPref().putInt("TYPE", user.data.user_type);
                        MyApplication.getPref().putString("EMAIL", user.data.email);
                        MyApplication.getPref().putString("NAME", user.data.name);
                        MyApplication.getPref().putString("PHOTO", "http://kora24life.tk/kora24/public/User/" + user.data.photo);
                        userDetails(user);
                    } else {

                        MyApplication.showMessageBottom(binding.contentLayout, getResources().getString(R.string.try_again));
                    }
                    if (user.message.equals("Invalid Data")) {
                        MyApplication.showMessageBottom(binding.contentLayout, getResources().getString(R.string.error_password));
                    }


                    Log.e(TAG, "onChanged: " + user.message);

                }
            });
        } else if (email.trim().equals("kora242020@gmail.com") && password.trim().equals("kora123456")) {
            userViewModel.login(email, password).observe(this, new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    Log.e(TAG, "onChanged: " + user);
                    if (user.message.equals("Returned Successfully")) {
//                        MyApplication.showMessage(binding.contentLayout, getResources().getString(R.string.login_success));
//                        MyApplication.getPref().putBoolean("LOGIN", true);
//                        MyApplication.getPref().putInt("TYPE", user.data.user_type);
//                        MyApplication.getPref().putInt("USERID",user.data.id);
//                        startActivity(new Intent(getContext(), AdminMainActivity.class));
                        MyApplication.showMessageBottom(binding.contentLayout, getResources().getString(R.string.login_success));
                        MyApplication.getPref().putInt("USERID", user.data.id);
                        MyApplication.getPref().putBoolean("LOGIN", true);
                        MyApplication.getPref().putString("EMAIL", user.data.email);
                        MyApplication.getPref().putString("NAME", user.data.name);
                        MyApplication.getPref().putString("PHOTO", "http://kora24life.tk/kora24/public/User/" + user.data.photo);
                        MyApplication.getPref().putInt("TYPE", user.data.user_type);
                        userDetails(user);
                        // to refresh activity
                        Intent intent = getActivity().getIntent();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        getActivity().finish();
                        startActivity(intent);
                    } else if (user.message.equals("Not Found")) {
                        MyApplication.showMessageBottom(binding.contentLayout, getResources().getString(R.string.user_not_found));

                    }
                    Log.e(TAG, "onChanged: " + user.message);

                }
            });
        } else {
            MyApplication.showMessageBottom(binding.contentLayout, getResources().getString(R.string.user_not_found));
        }

    }


    private void logout() {
        if (firebaseAuth.getCurrentUser() != null) {
            firebaseAuth.signOut();
        }
        if (LoginManager.getInstance() != null)
            LoginManager.getInstance().logOut();
        Log.e(TAG, "onClick: " + MyApplication.getPref().getBoolean("LOGIN"));
        if (MyApplication.getPref().getBoolean("LOGIN")) {
            MyApplication.getPref().remove("LOGIN");
            MyApplication.getPref().remove("EMAIL");
            MyApplication.getPref().remove("NAME");
            MyApplication.getPref().remove("PHOTO");
            MyApplication.getPref().remove("TYPE");
            MyApplication.getPref().remove("USERID");
        }
        MyApplication.showMessageBottom(binding.contentLayout, getResources().getString(R.string.logout_success));
        binding.userNameEdittext.setVisibility(View.INVISIBLE);
        resetEditText(binding.emailEdittext, Gravity.RIGHT, getResources().getColor(R.color.colorPrimary), getResources().getColor(android.R.color.white));
        resetEditText(binding.passwordEdittext, Gravity.RIGHT, getResources().getColor(R.color.colorPrimary), getResources().getColor(android.R.color.white));
        binding.view.setVisibility(View.VISIBLE);
        binding.passwordEdittext.setVisibility(View.VISIBLE);
        binding.loginButton.setText(getResources().getString(R.string.login));
        binding.googleLoginButton.setVisibility(View.VISIBLE);
        binding.facebookLoginFrameLayout.setVisibility(View.VISIBLE);
        binding.dontHasAccount.setVisibility(View.VISIBLE);
        binding.userImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));
        // to refresh activity
        Intent intent = getActivity().getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        getActivity().finish();
        startActivity(intent);
    }

    private void resetEditText(EditText editText, int gravity, int color, int background) {
        editText.setText("");
        editText.setGravity(gravity);
        editText.setTextColor(color);
        editText.setHintTextColor(color);
        editText.setBackgroundColor(background);
    }

    private void googleSignIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }

//    private void setResultCancelled() {
//        Intent intent = new Intent();
//        setResult(Activity.RESULT_CANCELED, intent);
//        finish();
//    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            userDetails(user);
                        } else {
                            MyApplication.showMessage(binding.contentLayout, task.getException().getMessage());
                            Log.e(TAG, "signInWithCredential:failure", task.getException());
//                            userDetails(null);
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);

        if (MyApplication.getPref().getBoolean("LOGIN")) {
            String photoUrl = MyApplication.getPref().getString("PHOTO");
            Log.e(TAG, "onStart: " + photoUrl);
            if (photoUrl != null) {
                Glide.with(getContext())
                        .setDefaultRequestOptions(new RequestOptions())
                        .load(photoUrl)
                        .circleCrop()
                        .placeholder(R.drawable.ic_launcher)
                        .into(binding.userImage);
            }
            binding.userNameEdittext.setVisibility(View.VISIBLE);
            resetEditText(binding.userNameEdittext, Gravity.CENTER, getResources().getColor(android.R.color.white), getResources().getColor(android.R.color.transparent));
            binding.userNameEdittext.setText(MyApplication.getPref().getString("NAME"));
            binding.view2.setVisibility(View.INVISIBLE);
            resetEditText(binding.emailEdittext, Gravity.CENTER, getResources().getColor(android.R.color.white), getResources().getColor(android.R.color.transparent));
            binding.emailEdittext.setText(MyApplication.getPref().getString("EMAIL"));
            binding.view.setVisibility(View.INVISIBLE);
            binding.passwordEdittext.setVisibility(View.INVISIBLE);
            binding.loginButton.setText(getResources().getString(R.string.logout));
            binding.dontHasAccount.setVisibility(View.INVISIBLE);
            binding.googleLoginButton.setVisibility(View.INVISIBLE);
            binding.facebookLoginFrameLayout.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    private void handleFacebookToken(AccessToken accessToken) {
        Log.e(TAG, "handleFacebookToken: " + accessToken);
        AuthCredential authCredential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.e(TAG, "onComplete: success");
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    String name = user.getPhotoUrl().toString();
                    String email = user.getEmail();
                    String password = "123456";
                    userViewModel.register(name, email, password, photo).observe(getActivity(), new Observer<User>() {
                        @Override
                        public void onChanged(User user) {
                            if (user != null) {
                                Log.e(TAG, "onChanged: " + user);
                                Log.e(TAG, "onChanged: " + user.message);
//                                MyApplication.showMessage(binding.contentLayout, getResources().getString(R.string.register_success));
                                binding.registerLayout.setVisibility(View.GONE);
                                binding.userImage.setVisibility(View.VISIBLE);
                                binding.userNameEdittext.setVisibility(View.INVISIBLE);
                                binding.loginButton.setText(getResources().getString(R.string.login));
                                binding.dontHasAccount.setText(getResources().getString(R.string.dont_has_account));
//                    Log.e(TAG, "onChanged: Firebase"+user.data.id );
                            } else {
                                Log.e(TAG, "onChanged: " + user.message);
//                                MyApplication.showMessageBottom(binding.contentLayout, getResources().getString(R.string.try_again));
                            }
                            Log.e(TAG, "onChanged: " + user.message);

                        }
                    });
                    userDetails(user);
                } else {

                    if (task.getException().getMessage().equals("An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.")) {
                        MyApplication.showMessage(binding.contentLayout, "يوجد حساب بالفعل بنفس عنوان البريد الإلكتروني.");
                    }
                    Log.e(TAG, "onComplete: failed " + task.getException().getMessage());
                }
            }
        });

    }

    private void userDetails(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            Log.e(TAG, "userDetails:FirebaseUser " + firebaseUser.getDisplayName());
            Log.e(TAG, "userDetails:FirebaseUser " + firebaseUser.getEmail());
            Log.e(TAG, "userDetails:FirebaseUser " + firebaseUser.getUid());
            Log.e(TAG, "userDetails:FirebaseUser " + firebaseUser.getPhotoUrl().getPath());

            userViewModel.login(firebaseUser.getEmail().toString(), "123456").observe(this, new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    Log.e(TAG, "onChanged: " + user);
                    if (user.message.equals("Returned Successfully")) {
                        Log.e(TAG, "onChanged: " + user.message);

                        MyApplication.getPref().putInt("USERID", user.data.id);
                        MyApplication.getPref().putBoolean("LOGIN", true);
                        MyApplication.getPref().putBoolean("SOCIAL_LOGIN", true);
                        MyApplication.getPref().putInt("TYPE", user.data.user_type);
                        MyApplication.getPref().putString("EMAIL", user.data.email);
                        MyApplication.getPref().putString("NAME", firebaseUser.getDisplayName());
                        MyApplication.getPref().putString("PHOTO", user.data.name);
                        userDetails(user);
                        if (firebaseUser.getPhotoUrl() != null) {
                            Glide.with(getContext())
                                    .setDefaultRequestOptions(new RequestOptions())
                                    .load(firebaseUser.getPhotoUrl())
                                    .circleCrop()
                                    .placeholder(R.drawable.ic_launcher)
                                    .into(binding.userImage);
                        }
                        binding.userNameEdittext.setVisibility(View.VISIBLE);
                        binding.userNameEdittext.setText(firebaseUser.getDisplayName());
                        binding.userNameEdittext.setEnabled(false);
                        resetEditText(binding.userNameEdittext, Gravity.CENTER, getResources().getColor(android.R.color.white), getResources().getColor(android.R.color.transparent));
                        binding.userNameEdittext.setText(firebaseUser.getDisplayName());
                        binding.view2.setVisibility(View.INVISIBLE);
                        resetEditText(binding.emailEdittext, Gravity.CENTER, getResources().getColor(android.R.color.white), getResources().getColor(android.R.color.transparent));
                        binding.emailEdittext.setText(firebaseUser.getEmail());
                        binding.emailEdittext.setEnabled(false);
                        binding.view.setVisibility(View.INVISIBLE);
                        binding.passwordEdittext.setVisibility(View.INVISIBLE);
                        binding.loginButton.setText(getResources().getString(R.string.logout));
                        binding.dontHasAccount.setVisibility(View.INVISIBLE);
                        binding.googleLoginButton.setVisibility(View.INVISIBLE);
                        binding.facebookLoginFrameLayout.setVisibility(View.INVISIBLE);
                    } else if (user.message.equals("Not Found")) {
                        MyApplication.showMessageBottom(binding.contentLayout, getResources().getString(R.string.user_not_found));
                        Log.e(TAG, "onChanged: " + user.message);
                    }
                    Log.e(TAG, "onChanged: " + user.message);

                }
            });
        }
    }

    private void userDetails(User user) {
        if (user != null) {
            Log.e(TAG, "userDetails: " + user.data.user_type);
            Log.e(TAG, "userDetails:  http://kora24life.tk/kora24/public/User/" + user.data.photo);

            if (user.data.photo != null) {
                String photoUrl = "http://kora24life.tk/kora24/public/User/" + user.data.photo;
                Log.e(TAG, photoUrl);

                Glide.with(getContext())
                        .setDefaultRequestOptions(new RequestOptions())
                        .load("http://kora24life.tk/kora24/public/User/" + user.data.photo)
                        .circleCrop()
                        .placeholder(R.drawable.ic_launcher)
                        .into(binding.userImage);
            }
            binding.userNameEdittext.setVisibility(View.VISIBLE);
            resetEditText(binding.userNameEdittext, Gravity.CENTER, getResources().getColor(android.R.color.white), getResources().getColor(android.R.color.transparent));
            binding.userNameEdittext.setText(user.data.name);
            binding.view2.setVisibility(View.INVISIBLE);
            resetEditText(binding.emailEdittext, Gravity.CENTER, getResources().getColor(android.R.color.white), getResources().getColor(android.R.color.transparent));
            binding.emailEdittext.setText(user.data.email);
            binding.view.setVisibility(View.INVISIBLE);
            binding.passwordEdittext.setVisibility(View.INVISIBLE);
            binding.loginButton.setText(getResources().getString(R.string.logout));
            binding.dontHasAccount.setVisibility(View.INVISIBLE);
            binding.googleLoginButton.setVisibility(View.INVISIBLE);
            binding.facebookLoginFrameLayout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.google_login_button:
                googleSignIn();
                break;
            case R.id.login_button:
                if (binding.loginButton.getText().toString().equals(getString(R.string.logout))) {
                    showDialog(v);
                } else if (binding.loginButton.getText().toString().equals(getString(R.string.login))) {
                    Log.e(TAG, "onClick: login");
                    loginWithEmail();
                } else if (binding.loginButton.getText().toString().equals(getString(R.string.register))) {
                    registerWithEmail();
                }
                break;
        }
    }

    private void registerWithEmail() {
        String username = binding.userNameEdittext.getText().toString();
        String email = binding.emailEdittext.getText().toString();
        String password = binding.passwordEdittext.getText().toString();
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            MyApplication.showMessageBottom(binding.contentLayout, getResources().getString(R.string.error_message_empty));
            return;
        }
        if (password.length() < 6) {
            MyApplication.showMessageBottom(binding.contentLayout, getResources().getString(R.string.error_password));
            return;
        }
        Log.e(TAG, "registerWithEmail: " + photo);
        if (photo != null) {
            registerWithEmailAndPassword(username, email, password, photo);
        } else {
            MyApplication.showMessageBottom(binding.contentLayout, getResources().getString(R.string.error_photo_empty));
        }
    }

    private void registerWithEmailAndPassword(String name, String email, String password, String photo) {
        Log.e(TAG, "registerWithEmailAndPassword:email " + email);
        Log.e(TAG, "registerWithEmailAndPassword:password " + password);
        Log.e(TAG, "registerWithEmailAndPassword:name " + name);
        Log.e(TAG, "registerWithEmailAndPassword:photo " + photo);
        userViewModel.register(name, email, password, photo).observe(getActivity(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    Log.e(TAG, "onChanged: " + user);
                    MyApplication.showMessage(binding.contentLayout, getResources().getString(R.string.register_success));
                    binding.registerLayout.setVisibility(View.GONE);
                    binding.userImage.setVisibility(View.VISIBLE);
                    binding.userNameEdittext.setVisibility(View.INVISIBLE);
                    binding.loginButton.setText(getResources().getString(R.string.login));
                    binding.dontHasAccount.setText(getResources().getString(R.string.dont_has_account));
//                    Log.e(TAG, "onChanged: Firebase"+user.data.id );
                } else {
                    MyApplication.showMessageBottom(binding.contentLayout, getResources().getString(R.string.try_again));
                }
                Log.e(TAG, "onChanged: " + user.message);

            }
        });
    }

    private void loginWithEmail() {
        String email = binding.emailEdittext.getText().toString();
        String password = binding.passwordEdittext.getText().toString();
        if (email.isEmpty() || password.isEmpty()) {
            MyApplication.showMessageBottom(binding.contentLayout, getResources().getString(R.string.error_message_empty));
            return;
        }
        if (password.length() < 6) {
            MyApplication.showMessageBottom(binding.contentLayout, getResources().getString(R.string.error_password));
            return;
        }
        Log.e(TAG, "loginWithEmail: " + email);
        Log.e(TAG, "loginWithEmail: " + password);
        loginWithEmailAndPassword(email, password);
    }

    public void showDialog(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        ViewGroup viewGroup = getActivity().findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.custom_dialog, viewGroup, false);
        TextView title = dialogView.findViewById(R.id.dialog_title);
        title.setGravity(Gravity.RIGHT);
        title.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        title.setText(getResources().getString(R.string.logout));
        TextView message = dialogView.findViewById(R.id.dialog_message);
        message.setGravity(Gravity.RIGHT);
        message.setText(getResources().getString(R.string.logout_confirm));
        message.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        TextView positive = dialogView.findViewById(R.id.dialog_positive);
        TextView negative = dialogView.findViewById(R.id.dialog_negative);
        positive.setText(getResources().getString(R.string.yes));
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
                alertDialog.dismiss();
            }
        });
        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }


}
