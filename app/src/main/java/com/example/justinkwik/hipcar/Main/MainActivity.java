package com.example.justinkwik.hipcar.Main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.justinkwik.hipcar.CustomViewPager.NonSwipeableViewPager;
import com.example.justinkwik.hipcar.ExpandAnimation.ExpandAnimation;
import com.example.justinkwik.hipcar.HipCarApplication;
import com.example.justinkwik.hipcar.Login.LoginActivity;
import com.example.justinkwik.hipcar.Login.UserCredentials;
import com.example.justinkwik.hipcar.Main.Reservation.OnGoingFragmentClasses.OnGoingReservationFragment;
import com.example.justinkwik.hipcar.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private UserCredentials userCredentials;
    private final SharedPreferences sharedPreferences = HipCarApplication.getSharedPreferences();
    private DrawerLayout drawerLayout;
    private ListView drawerListView;
    private boolean openDrawer;
    private String navBarEntries[];
    private LottieAnimationView navBarButtonHtA;
    private LottieAnimationView navBarButtonAtH;
    private View navBarClickView;
    private TextView mainActivityTitle;
    private NonSwipeableViewPager viewPager;
    private LinearLayout changePassLogOutLayout;
    private TextView logoutTextView;
    private TextView changePasswordTextView;
    private LinearLayout reservationSubMenuLayout;
    private TextView onGoingReservationTextView;
    private TextView checkOutReservationTextView;
    private LinearLayout vehicleSubMenuLayout;
    private TextView vehicleTextView;
    private TextView vehicleMakeTextView;
    private TextView vehicleModelTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userCredentials = LoginActivity.getUserCredentials();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        Log.e("Token: ", userCredentials.getToken()); //TODO: delete this token log.

        drawerListView = (ListView) findViewById(R.id.drawerListView);
        openDrawer = true;
        navBarEntries = getResources().getStringArray(R.array.navBarEntries);
        navBarButtonHtA = (LottieAnimationView) findViewById(R.id.navBarButtonHtA);
        navBarButtonAtH = (LottieAnimationView) findViewById(R.id.navBarButtonAtH);
        navBarClickView = findViewById(R.id.navBarClickView);
        mainActivityTitle = (TextView) findViewById(R.id.mainActivityTitle);
        viewPager = (NonSwipeableViewPager) findViewById(R.id.mainActivityViewPager);

        //TODO: need to figure out how much and when to set the viewPager.setOffScreenPageLimit.

        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        drawerListView.setAdapter(new MyNavBarAdapter());
        drawerListView.setOnItemClickListener(this);

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

                if (!openDrawer) {

                    arrowToHamburger();

                }

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        navBarClickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (openDrawer) {

                    openDrawer();

                } else {

                    closeDrawer();

                }

            }
        });

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(1, false);

        //TODO: RESERVATION IMPLEMENTATION
        //Add a loading thing to the reservation while retrieving the information.
        //Add an arrow to the reservation tab for sub menu's to indicate collapse and expand.
        //Add an arrow to each recycler view views in ongoing and check-out.
        //Add a search bar to both tabs or filter?
        //Finish the empty rows for the grace period and etc.
        //Finish the layout for the row, make borders for each row
        //Add the action button.
        //Add progress bar to ongoingreservation

        //TODO: LOG OUT AND CHANGE PASSWORD
        //for logging out delete the credentials and set loggedin boolean to false (both in sharedPreference)
        //when clicking another page while logout view is expanded, call the animation on the relative layout to collapse
            //also when the drawer closes, but we first have to check if it is expanded.
        //put a border on the log out and change password textviews.
        //add an arrow on the welcome nav bar to indicate expanded and collapsed log out/change password (lottie?)

        //TODO: FOR VEHICLE TAB
        //Add an arrow to signal expand/collapse and implement

        //TODO: MISCELLANEOUS
        //give an option to skip splash screen in beginning
        //put the app in background if they click back
        //add a profile picture to the welcome nav bar from slack? pull from slack using slack api
        //damage, add api in the backend
        //in the gradle file, figure out how to delete the dev key words in the api endpoints
        //make sure to recycle all views
        //finish activity if clicking back
        //remove fresco if not using
        //remove placeholder fragment when all pages are implemented.
        //add a border around the login window (???)

    }

    public class MyNavBarAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return navBarEntries.length;
        }

        @Override
        public Object getItem(int position) {
            return navBarEntries[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                if(position == 0) {

                    convertView = layoutInflater.inflate(R.layout.welcomenavbar, null);

                    changePassLogOutLayout = (LinearLayout) convertView.findViewById(R.id.changePassLogOutLayout);
                    logoutTextView = (TextView) convertView.findViewById(R.id.logoutTextView);
                    changePasswordTextView = (TextView) convertView.findViewById(R.id.changePasswordTextView);

                    setOnClickListenersLogoutChangePass();

                } else if( position == 3) {

                    convertView = layoutInflater.inflate(R.layout.rownavbarreservation, null);

                    reservationSubMenuLayout = (LinearLayout) convertView.findViewById(R.id.reservationSubMenuLayout);
                    checkOutReservationTextView = (TextView) convertView.findViewById(R.id.checkOutReservationTextView);
                    onGoingReservationTextView = (TextView) convertView.findViewById(R.id.onGoingReservationTextView);

                    setOnClickListenersReservation();


                } else if(position == 9) {

                    convertView = layoutInflater.inflate(R.layout.rownavbarvehicle, null);
                    vehicleSubMenuLayout = (LinearLayout) convertView.findViewById(R.id.vehicleSubMenuLayout);
                    vehicleTextView = (TextView) convertView.findViewById(R.id.vehicleTextView);
                    vehicleMakeTextView = (TextView) convertView.findViewById(R.id.vehicleMakeTextView);
                    vehicleModelTextView = (TextView) convertView.findViewById(R.id.vehicleModelTextView);

                    setOnClickListenersVehicle();


                } else {

                    convertView = layoutInflater.inflate(R.layout.rownavbar, null);

                }

            }

            if(position == 0) {

                TextView nameTextView = (TextView) convertView.findViewById(R.id.nameTextView);
                nameTextView.setText(userCredentials.getName());

            } else {

                TextView navBarEntryTextView = (TextView) convertView.findViewById(R.id.navBarEntryTextView);
                navBarEntryTextView.setText(navBarEntries[position - 1]);

            }

            return convertView;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {
            case 0:
                expandCollapseSubMenus(changePassLogOutLayout);
                break;
            case 1:
                viewPager.setCurrentItem(3, false);
                break;
            case 2:
                viewPager.setCurrentItem(4, false);
                break;
            case 3:
                expandCollapseSubMenus(reservationSubMenuLayout);
                break;
            case 4:
                viewPager.setCurrentItem(8, false);
                break;
            case 5:
                viewPager.setCurrentItem(9, false);
                break;
            case 6:
                viewPager.setCurrentItem(10, false);
                break;
            case 7:
                viewPager.setCurrentItem(11, false);
                break;
            case 8:
                viewPager.setCurrentItem(12, false);
                break;
            case 9:
                expandCollapseSubMenus(vehicleSubMenuLayout);
                break;

        }

        if(position != 0 && position != 3 && position != 9) {

            mainActivityTitle.setText(navBarEntries[position - 1]);
            closeDrawer();

        }

    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {

            closeDrawer();

            switch (position) { //Skip 0, 5, 13 because these are expandable
                case 1:
                    return new PlaceHolderFragment();
                case 2:
                    return new PlaceHolderFragment();
                case 3:
                    return new PlaceHolderFragment();
                case 4:
                    return new PlaceHolderFragment();
                case 6:
                    return new OnGoingReservationFragment();
                case 7:
                    return new PlaceHolderFragment();
                case 8:
                    return new PlaceHolderFragment();
                case 9:
                    return new PlaceHolderFragment();
                case 10:
                    return new PlaceHolderFragment();
                case 11:
                    return new PlaceHolderFragment();
                case 12:
                    return new PlaceHolderFragment();
                case 14:
                    return new PlaceHolderFragment();
                case 15:
                    return new PlaceHolderFragment();
                case 16:
                    return new PlaceHolderFragment();

            }

            return new PlaceHolderFragment();

        }

        @Override
        public int getCount() {
            return navBarEntries.length + 7;
        }
    }

    private void closeDrawer() {

        navBarButtonAtH.setVisibility(View.VISIBLE);
        navBarButtonHtA.setVisibility(View.INVISIBLE);
        navBarButtonAtH.playAnimation();

        openDrawer = true;

        drawerLayout.closeDrawer(Gravity.LEFT);

    }

    private void openDrawer() {

        navBarButtonHtA.setVisibility(View.VISIBLE);

        navBarButtonAtH.setVisibility(View.INVISIBLE);

        navBarButtonHtA.playAnimation();

        drawerLayout.setVisibility(View.VISIBLE);
        drawerLayout.openDrawer(Gravity.LEFT);

        openDrawer = false;

    }

    private void arrowToHamburger() {

        navBarButtonAtH.setVisibility(View.VISIBLE);
        navBarButtonHtA.setVisibility(View.INVISIBLE);
        navBarButtonAtH.playAnimation();
        drawerLayout.setVisibility(View.GONE);

        openDrawer = true;

    }

    private void expandCollapseSubMenus(View view) {

        ExpandAnimation expandAnimation = new ExpandAnimation(view, 390);
        view.startAnimation(expandAnimation);

    }

    //TODO implement these onitemclicks then getitem and onclick method.

    private void setOnClickListenersLogoutChangePass() {

        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        changePasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void setOnClickListenersVehicle() {

        vehicleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        vehicleMakeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        vehicleModelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setOnClickListenersReservation() {

        checkOutReservationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        onGoingReservationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(6, false); //Set to 6 starting from index 0, this submenu is position 6
                expandCollapseSubMenus(reservationSubMenuLayout);
                mainActivityTitle.setText("On-Going Reservation");
                closeDrawer();
            }
        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
