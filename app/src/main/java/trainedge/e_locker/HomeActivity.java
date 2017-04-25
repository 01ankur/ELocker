package trainedge.e_locker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final int REQUEST_INVITE = 232;
    public static final String TAG = "home";
    private FirebaseDatabase db;
    private DatabaseReference myref;
    private ArrayList<Object> documentList;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Recycler View Code
        db = FirebaseDatabase.getInstance();
        myref = db.getReference();
        //Getting data from firebase code (Recycler view)
        mAuth = FirebaseAuth.getInstance();
        myref = db.getReference("docs_db").child(mAuth.getCurrentUser().getUid());
        //creating blank list in memory
        documentList = new ArrayList<>();
        //Recycler View Object
        final RecyclerView rvDescription = (RecyclerView) findViewById(R.id.rvDescription);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        //passing layput manager
        rvDescription.setLayoutManager(manager);
        //setup Listener
        //using anonymous class
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Data is in data Snapshot obj
                documentList.clear();
                if (dataSnapshot.hasChildren()) {
                    //here write datasnapshot.children().{then write iterator and chose for loop then next line automaticaaly genrated}
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        documentList.add(new ScanModel(snapshot));
                    }
                    Toast.makeText(HomeActivity.this, "Data loaded Successfully", Toast.LENGTH_SHORT).show();

                    DocumentAdapter adapter = new DocumentAdapter(documentList);
                    rvDescription.setAdapter(adapter);
                } else {
                    Toast.makeText(HomeActivity.this, "Data not Available", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void sendInvitation() {
        Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invitation_message))
                .setCallToActionText(getString(R.string.invitation_cta))
                .build();
        startActivityForResult(intent, REQUEST_INVITE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_INVITE) {
            if (resultCode == RESULT_OK) {
                // Check how many invitations were sent and log.
                String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                Log.d(TAG, "Invitations sent: " + ids.length);
            } else {
                // Sending failed or it was canceled, show failure message to the user
                Log.d(TAG, "Failed to send invitation.");
            }
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Scan) {
            Intent i = new Intent(HomeActivity.this, Scan_Document.class);
            startActivity(i);
        } else if (id == R.id.Set) {

        } else if (id == R.id.Feedback) {
            Intent feedback = new Intent(HomeActivity.this, Feedback.class);
            startActivity(feedback);

        } else if (id == R.id.LogOut) {
            FirebaseAuth.getInstance().signOut();
            Intent ii = new Intent(HomeActivity.this, LogIn.class);
            startActivity(ii);
            finish();
        } else if (id == R.id.profile) {

            Intent iiii = new Intent(HomeActivity.this, ProfileInformation.class);
            startActivity(iiii);

        } else if (id == R.id.appinvite) {
            sendInvitation();
        } else if (id == R.id.about) {
            Intent about = new Intent(HomeActivity.this, AboutActivity.class);
            startActivity(about);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
