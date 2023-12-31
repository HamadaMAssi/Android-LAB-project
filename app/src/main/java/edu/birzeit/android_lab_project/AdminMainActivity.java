package edu.birzeit.android_lab_project;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private DrawerLayout drawerLayout;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setAppTheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set custom title text
        getSupportActionBar().setTitle("Home");
        // Change the color of the Toolbar title
        toolbar.setTitleTextColor(getResources().getColor(R.color.color2));

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);

        // Set the color of the toggle icon
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.color2));

        toggle.setToolbarNavigationClickListener(v -> onBackPressed());
        toggle.setDrawerSlideAnimationEnabled(true);
        toggle.syncState();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AdminHomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        View headerView = navigationView.getHeaderView(0);

        CircleImageView userImage = headerView.findViewById(R.id.imageViewPhoto);
        TextView userName = headerView.findViewById(R.id.userName);


        DataBaseHelper databasehelper = new DataBaseHelper(AdminMainActivity.this, "train", null, 1);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

//        userImage.setImageURI(Uri.parse(intent.getStringExtra("photo")));
//        userName.setText(email);

        Admin user = databasehelper.getAdminObjectByEmail(email);
        Bitmap bitmap = BitmapFactory.decodeByteArray(user.getPersonal_Photo(), 0, user.getPersonal_Photo().length);
        userImage.setImageBitmap(bitmap);
        userName.setText(user.getEmail_Address());

    }

    @Override
    protected void onResume() {
        super.onResume();
        setAppTheme();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setAppTheme();
    }

    @Override
    protected void onPause() {
        super.onPause();
        setAppTheme();
    }

    private void setAppTheme() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String appearance = prefs.getString("Appearance", "Light theme");

        if (appearance.equals("Dark theme")) {
            setTheme(R.style.Theme_AndroidLABproject_Dark);
        } else {
            setTheme(R.style.Theme_AndroidLABproject_Light);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case R.id.nav_home:
                Toast.makeText(this, "home!", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AdminHomeFragment()).commit();
                break;

            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SettingsFragment()).commit();
                Toast.makeText(this, "Settings!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_about:
                Toast.makeText(this, "about!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_account:
                Toast.makeText(this, "account!", Toast.LENGTH_SHORT).show();
                AdminProfileFragment fragment = new AdminProfileFragment();
                Bundle args = new Bundle();
                args.putString("email", email); // Replace "myString" with the desired key and "Hello, world!" with your actual string
                fragment.setArguments(args);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                break;

            case R.id.nav_logout:
                Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdminMainActivity.this,MainActivity2.class);
                AdminMainActivity.this.startActivity(intent);
                finish();
                break;

            case R.id.nav_share:
                Toast.makeText(this, "Link copied!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_rate:
                Toast.makeText(this, "rate!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_createNewCourse:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CreateNewCourseFragment()).commit();
                break;

            case R.id.nav_editOrDelete:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new EditDeleteCourseFragment()).commit();
                break;

            case R.id.nav_makeAvailable:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MakeCourseAvailableFragment()).commit();
                break;

            case R.id.nav_takeDecisions:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TakeDecisionsFragment()).commit();
                break;
            case R.id.nav_viewProfiles:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ViewProfilesFragment()).commit();
                break;

            case R.id.nav_viewOfferingHistory:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ViewOfferingHistoryFragment()).commit();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}