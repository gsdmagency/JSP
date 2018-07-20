package jsp.co.za.jspandroid.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.transition.Slide;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.*;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.florent37.runtimepermission.RuntimePermission;
import com.github.florent37.runtimepermission.callbacks.PermissionListener;

import java.util.List;

import jsp.co.za.jspandroid.Fragments.ApproachFragment;
import jsp.co.za.jspandroid.Fragments.ContactFragment;
import jsp.co.za.jspandroid.Fragments.FAQFragment;
import jsp.co.za.jspandroid.Fragments.GalleryFragment;
import jsp.co.za.jspandroid.Fragments.HomeFragment;
import jsp.co.za.jspandroid.Fragments.WebsiteFragment;
import jsp.co.za.jspandroid.Fragments.WeddingFragment;
import jsp.co.za.jspandroid.R;

import static com.github.florent37.runtimepermission.RuntimePermission.askPermission;

public class MainActivity extends AppCompatActivity {
    public DrawerLayout mDrawer;
    public NavigationView navigationView;
    public boolean isRotation = false;
    String title = "";
    int value=0;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        askPermission(this, Manifest.permission.CALL_PHONE).ask(new PermissionListener() {
            @Override
            public void onAccepted(RuntimePermission runtimePermission, List<String> accepted) {

            }

            @Override
            public void onDenied(RuntimePermission runtimePermission, List<String> denied, List<String> foreverDenied) {
                //the list of denied permissions
            }
        });
        ImageView imageView=findViewById(R.id.landingImage);
        Glide
                .with(this)
                .load("http://www.jolandiesphotography.co.za/jspapp/image7.jpg")
                .into(imageView);

        // you need to open setting manually if you really need it
        //runtimePermission.goToSettings();

        this.mDrawer = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navview);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        //Enable support action bar to display hamburger
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("About");

        navigationView.inflateMenu(R.menu.navmenu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        title = "About";
                        setFragment(new HomeFragment());
                        break;
                    case R.id.nav_approach:
                        title = "Our Approach";
                        setFragment(new ApproachFragment());
                        break;
                    case R.id.nav_gallery:
                        title = "Gallery";
                        setFragment(new GalleryFragment());
                        break;
                    case R.id.nav_facebook:
                        startActivity(getOpenFacebookIntent(MainActivity.this));
                        break;
                    case R.id.nav_package:
                        title = "Wedding Packages";
                        setFragment(new WeddingFragment());
                        break;
                    case R.id.nav_website:
                        title = "Wedding Website";
                        setFragment(new WebsiteFragment());
                        break;
                    case R.id.nav_faq:
                        title = "FAQ's";
                        setFragment(new FAQFragment());
                        break;
                    case R.id.nav_contact:
                        title = "Contact Us";
                        setFragment(new ContactFragment());
                        break;
                }
                mDrawer.closeDrawers();
                return true;
            }

        });
        



    }

    @Override
    protected void onResume() {
        super.onResume();
        //setFragment(new HomeFragment());
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
        isRotation = true;
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //your code
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //your code
            value=1;

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                if (mDrawer.isDrawerOpen(GravityCompat.START)) {
                    mDrawer.closeDrawers();

                } else {
                    mDrawer.openDrawer(GravityCompat.START);

                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setFragment(Fragment f) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        getSupportActionBar().setTitle(title);
        transaction.add(f, f.getTag());
        transaction.replace(R.id.homeFrameLayout, f);
        transaction.addToBackStack(f.getTag());
        f.setEnterTransition(new Slide(Gravity.RIGHT));
        f.setExitTransition(new Slide(Gravity.LEFT));
        transaction.commit();
    }

    @Override
    public void onBackPressed() {

        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();
            } else {
                finish();
            }

        }
    }

    public static Intent getOpenFacebookIntent(Context context) {
        Uri uri = Uri.parse("https://www.facebook.com/jolandiesphotography");
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                // http://stackoverflow.com/a/24547437/1048340
                uri = Uri.parse("fb://facewebmodal/f?href=" + "https://www.facebook.com/jolandiesphotography");
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return new Intent(Intent.ACTION_VIEW, uri);
//        try {
//            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
//            this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/251432025291048")));
//        } catch (Exception e) {
//           this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/jolandiesphotography")));
//        }
    }
}
