package com.example.caregiverapplication;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caregiverapplication.adapter.FirstAidAdapter;
import com.example.caregiverapplication.model.FirstAidData;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FrmFirstAidActivity extends AppCompatActivity implements
        AppBarLayout.OnOffsetChangedListener, FirstAidAdapter.FirstAidListener {
    private RecyclerView rvFirstAidList;
    private CaregiverViewModel caregiverViewModel;
    private FirstAidAdapter firstAidAdapter;
    private FloatingActionButton fabSettings;
    private  LinearLayout layoutSearchHospital,layoutAlarm,layoutNewFeed;
    private boolean fabExpanded = false;
    private AppBarLayout appBarLayout;
    private List<FirstAidData> firstAidDataList;
    private SearchView searchView;
   // private String[] countryNameList = {"India", "China", "Australia", "New Zealand", "England", "Pakistan"};
   private RelativeLayout relativeLayout;
    private TextView tvNoData;
    private ImageView btnSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_first_aid);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        relativeLayout = findViewById(R.id.relayout);
        tvNoData = findViewById(R.id.tv_no_data);
        btnSearch = findViewById(R.id.btn_search);
        // toolbar fancy stuff
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      //  getSupportActionBar().setTitle("FirstAid");

        appBarLayout = findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                // hToolbar.setBackgroundColor(Color.parseColor("#ffffff"));
                Log.d("tag_scroll", "recycler_view current offset: " + verticalOffset);
                if (Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange() == 0) {

                   }else{

                }  //Expanded
                // Log.i("Expanded", "Expanded");


            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(FrmFirstAidActivity.this);
            }
        });
        /***********************/

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) findViewById(R.id.action_search);

        SearchView.SearchAutoComplete searchAutoComplete =
                (SearchView.SearchAutoComplete)searchView.findViewById(androidx.appcompat.R.id.search_src_text);


        searchAutoComplete.setHintTextColor(Color.BLACK);
        searchAutoComplete.setTextColor(Color.BLACK);



      //  searchView.setQueryHint(Html.fromHtml("<font color = #000000>" + "Search...." + "</font>"));


        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);


        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                firstAidAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                firstAidAdapter.getFilter().filter(query);

                return false;
            }
        });






        /***************************/




        rvFirstAidList = findViewById(R.id.rv_first_aid_list);
        caregiverViewModel = ViewModelProviders.of(this).get(CaregiverViewModel.class);



        firstAidAdapter = new FirstAidAdapter(this, new FirstAidAdapter.FirstAidListener() {
            @Override
            public void onFirstAidSelected(FirstAidData firstAidData) {
                Intent intent = new Intent(FrmFirstAidActivity.this,FrmAddNewsfeed.class);
                intent.putExtra("faid",firstAidData.getFaid());
                intent.putExtra("name",firstAidData.getName());
                intent.putExtra("instruction",firstAidData.getInstruction());
                intent.putExtra("caution",firstAidData.getCaution());
                intent.putExtra("photoData",firstAidData.getPhoto());
                startActivityForResult(intent,1);
            }
        });
        rvFirstAidList.setNestedScrollingEnabled(false);
        rvFirstAidList.setLayoutManager(new LinearLayoutManager(this));
        rvFirstAidList.setAdapter(firstAidAdapter);


        caregiverViewModel.allFirstAids.observe(this, new Observer<List<FirstAidData>>() {
            @Override
            public void onChanged(List<FirstAidData> firstAidData) {
                if(firstAidData.size()>0)
                {
                    relativeLayout.setVisibility(View.VISIBLE);
                    tvNoData.setVisibility(View.GONE);

                }else{
                    relativeLayout.setVisibility(View.GONE);
                    tvNoData.setVisibility(View.VISIBLE);
                }

               firstAidAdapter.setFirstAidData(firstAidData);

            }
        });

        fabSettings = (FloatingActionButton) this.findViewById(R.id.fabSetting);

        layoutSearchHospital = (LinearLayout) this.findViewById(R.id.layoutSearchHospital);
        layoutAlarm = (LinearLayout) this.findViewById(R.id.layoutAlarm);
        layoutNewFeed = (LinearLayout) this.findViewById(R.id.layoutNewFeed);
        layoutNewFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrmFirstAidActivity.this,FrmAddNewsfeed.class);
                startActivityForResult(intent,1);
            }
        });
        layoutAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrmFirstAidActivity.this,FrmSetAlarm.class);
                startActivityForResult(intent,2);
            }
        });
        layoutSearchHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrmFirstAidActivity.this,FrmSearchHospitalActivity.class);
                startActivityForResult(intent,2);
            }
        });
        fabSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fabExpanded == true){
                    closeSubMenusFab();
                } else {
                    openSubMenusFab();
                }
            }
        });


        closeSubMenusFab();

    }
    //closes FAB submenus
    private void closeSubMenusFab(){
        layoutNewFeed.setVisibility(View.INVISIBLE);
        layoutAlarm.setVisibility(View.INVISIBLE);
        layoutSearchHospital.setVisibility(View.INVISIBLE);
        fabSettings.setImageResource(R.drawable.ic_add_circle_black_24dp);
        fabExpanded = false;
    }

    //Opens FAB submenus
    private void openSubMenusFab(){
        layoutNewFeed.setVisibility(View.VISIBLE);
        layoutAlarm.setVisibility(View.VISIBLE);
        layoutSearchHospital.setVisibility(View.VISIBLE);
        //Change settings icon to 'X' icon
        fabSettings.setImageResource(R.drawable.ic_close_black_24dp);
        fabExpanded = true;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        appBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {

    }

    @Override
    public void onFirstAidSelected(FirstAidData firstAidData) {
        Toast.makeText(getApplicationContext(), "Selected: " + firstAidData.getInstruction() + ", " + firstAidData.getCaution(), Toast.LENGTH_LONG).show();
    }



    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        closeSubMenusFab();
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            hideKeyboard(this);
            exitAlert();
          //  return;
        }else{
            super.onBackPressed();
        }




    }
    private void exitAlert(){
        new AlertDialog.Builder(this)
                .setTitle("")
                .setMessage("Are you sure you want to exit?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        finish();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("FirmFirstAid","onActivity Result = "+requestCode);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            Log.d("FirmFirstAid","onActivity OK");
            closeSubMenusFab();
            hideKeyboard(FrmFirstAidActivity.this);
          //  searchView.setFocusable(false);
            if(data != null)
            {
                Log.d("FirmFirstAid","onActivity data not null ");
                String nameField = data.getStringExtra("name");
                String instructionField = data.getStringExtra("instruction");
                String cautionField = data.getStringExtra("caution");
                String photoField = data.getStringExtra("photoData");
                String checkFunField = data.getStringExtra("checkFunc");

                FirstAidData firstAidData = new FirstAidData(nameField,instructionField,cautionField,photoField);
                if(checkFunField.equalsIgnoreCase("save"))
                {
                    caregiverViewModel.insert(firstAidData);
                    Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show();
                }

                else{
                    int faid = data.getIntExtra("faid",0);
                    Log.d("FirmFirstAid","onActivity data not null update = "+nameField+","+instructionField+","+cautionField+","+photoField);
                    Log.d("FirmFirstAid","onActivity data not null update = "+firstAidData.getName()+","+firstAidData.getInstruction()+","+firstAidData.getCaution()+","+firstAidData.getPhoto());
                    firstAidData.setFaid(faid);
                    caregiverViewModel.update(firstAidData);
                    Toast.makeText(this,"Updated",Toast.LENGTH_SHORT).show();
                }


             //   Toast.makeText(this,"Note saved",Toast.LENGTH_SHORT).show();
            }

        }
        else if(requestCode == 2 && resultCode == Activity.RESULT_OK){
            hideKeyboard(FrmFirstAidActivity.this);
           // searchView.setFocusable(false);
            closeSubMenusFab();
        }

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View currentFocusedView = activity.getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
