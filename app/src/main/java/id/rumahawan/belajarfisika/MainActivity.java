package id.rumahawan.belajarfisika;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import id.rumahawan.belajarfisika.Data.Session;
import id.rumahawan.belajarfisika.Fragment.LessonListFragment;
import id.rumahawan.belajarfisika.Fragment.StudentListFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener  {
    private Session session;

    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flContainer, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.student:
                fragment = new StudentListFragment();
                getSupportActionBar().setTitle("Student");
                break;
            case R.id.lesson:
                fragment = new LessonListFragment();
                getSupportActionBar().setTitle("Lesson");
                break;
        }
        return loadFragment(fragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new Session(this);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(this);

        if (session.getSessionString("currentLevel").equals("teacher")) {
            getSupportActionBar().setTitle("Student");
            loadFragment(new StudentListFragment());
        }
        else{
            getSupportActionBar().setTitle("Lesson");
            loadFragment(new LessonListFragment());
            bottomNavigation.setVisibility(View.GONE);
        }

        //Navigation drawer
        mDrawerlayout = findViewById(R.id.drawerContainer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout, R.string.open, R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.inflateHeaderView(R.layout.drawer_header);
        View headerLayout = navigationView.getHeaderView(0);
        TextView tvHeaderName = headerLayout.findViewById(R.id.tvHeaderName);
        tvHeaderName.setText(session.getSessionString("currentName"));
        TextView tvHeaderEmail = headerLayout.findViewById(R.id.tvHeaderEmail);
        tvHeaderEmail.setText(session.getSessionString("currentEmail"));
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Loged Off
                if (item.getItemId() == R.id.logout) {
                    session.removeSession("currentEmail");
                    session.removeSession("currentName");
                    session.removeSession("currentLevel");
                    mDrawerlayout.closeDrawers();
                    startActivity(new Intent(MainActivity.this, OnBoardingActivity.class));
                }
                return false;
            }
        });
        //End navigation drawer
    }
}
