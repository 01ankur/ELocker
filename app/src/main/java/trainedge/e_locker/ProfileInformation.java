package trainedge.e_locker;


import android.content.Intent;
import android.graphics.Picture;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import static android.R.attr.data;
import static android.R.attr.onClick;
import static android.R.id.progress;

public class ProfileInformation extends AppCompatActivity {


    public static final String TAG = "Profile";
    private ImageView imageView;
    private FirebaseAuth auth;
    private TextView email;
    private TextView name;
    private ProgressBar progressBar;
    private String username;
    private String email1;
    private boolean emailVerified;
    private String uid;
    private String providerId;
    private UserInfo profile;
    private Uri photoUrl;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String username1;
    private String useremail;
    private String uid1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_profile_information);
        //Initialize ImageView
        imageView = (ImageView) findViewById(R.id.imageView);
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        try {
            name.setText(user.getDisplayName());
            email.setText(user.getEmail());
            Picasso.with(this).load(user.getPhotoUrl()).into(imageView);

        }
        catch (NullPointerException e){
            Toast.makeText(this, "there was an error", Toast.LENGTH_SHORT).show();
        }
     }


}