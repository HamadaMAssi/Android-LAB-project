package edu.birzeit.android_lab_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class TraineeMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TraineeHomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        View headerView = navigationView.getHeaderView(0);

        CircleImageView userImage = headerView.findViewById(R.id.imageViewPhoto);
        TextView userName = headerView.findViewById(R.id.userName);


        DataBaseHelper databasehelper = new DataBaseHelper(TraineeMainActivity.this, "train", null, 1);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        Trainee user = databasehelper.getTraineeObjectByEmail(email);
        Bitmap bitmap = BitmapFactory.decodeByteArray(user.getPersonal_Photo(), 0, user.getPersonal_Photo().length);
        userImage.setImageBitmap(bitmap);
        userName.setText(user.getEmail_Address());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home:
                Toast.makeText(this, "home!", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TraineeHomeFragment()).commit();
                break;

            case R.id.nav_settings:
                Toast.makeText(this, "Settings!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_about:
                Toast.makeText(this, "about!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_account:
                Toast.makeText(this, "account!", Toast.LENGTH_SHORT).show();
                TraineeProfileFragment fragment = new TraineeProfileFragment();
                Bundle args = new Bundle();
                args.putString("email", email);
                fragment.setArguments(args);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();                break;

            case R.id.nav_logout:
                Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TraineeMainActivity.this,MainActivity2.class);
                TraineeMainActivity.this.startActivity(intent);
                finish();
                break;

            case R.id.nav_share:
                Toast.makeText(this, "Link copied!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_rate:
                Toast.makeText(this, "rate!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_SearchCourses:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SearchCoursesFragment()).commit();
                break;

            case R.id.nav_enrollCourse:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new EnrollInCourseFragment()).commit();
                break;

            case R.id.nav_previousCourses:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ViewOldCoursesFragment()).commit();
                break;

            case R.id.nav_coursesHistory:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ViewCenterCourseFragment()).commit();
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