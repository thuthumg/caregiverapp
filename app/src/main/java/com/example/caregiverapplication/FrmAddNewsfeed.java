package com.example.caregiverapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static com.example.caregiverapplication.FileUtility.getPhotoFolderPath;

public class FrmAddNewsfeed extends AppCompatActivity {

   // TextInputLayout nametxtinput,instructiontxtinput,cautiontxtinput;
    TextInputEditText nametxtedit,instructiontxtedit,cautiontxtedit;
    ImageView btnback;
    MaterialButton btnSave;
    private static final int PERMISSION_REQUEST_CAMERA = 15;
    private final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 25;
    private static final int GALLERY = 1, CAMERA = 2;
    Bitmap imageBitmap;
    AppCompatTextView imgChooseText;
    String UpdateFunc="";
    String nameData = "",instructionData ="",cautionData = "",photoData = "";
    int faid ;
    ScrollView sview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_add_newsfeed);
       // Toolbar toolbar = findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);

        btnback = findViewById(R.id.btn_back);

        nametxtedit = findViewById(R.id.name_txtedit);
        instructiontxtedit = findViewById(R.id.instruction_txtedit);
        cautiontxtedit = findViewById(R.id.caution_txtedit);
        btnSave = findViewById(R.id.btn_save);
        imgChooseText = findViewById(R.id.img_choose_text);

      //  sview = findViewById(R.id.sview);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            UpdateFunc = "true";
            faid = extras.getInt("faid");

            nameData = extras.getString("name");
            instructionData = extras.getString("instruction");

            cautionData = extras.getString("caution");

            photoData = extras.getString("photoData");

            nametxtedit.setText(nameData);
            instructiontxtedit.setText(instructionData);
            cautiontxtedit.setText(cautionData);
            imgChooseText.setText(photoData);

            btnSave.setText("Update");
           /* sview.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
            sview.setFocusable(true);
            sview.setFocusableInTouchMode(true);
            sview.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    v.requestFocusFromTouch();
                    return false;
                }
            });
            */

        }




        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForm();
            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(FrmAddNewsfeed.this);
                setResult(RESULT_OK);
                finish();
            }
        });
        imgChooseText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
    }

    private void checkForm(){
        String nameField = nametxtedit.getText().toString();
        String instructionField = instructiontxtedit.getText().toString();
        String cautionField = cautiontxtedit.getText().toString();
        String photoField = imgChooseText.getText().toString();
        if(TextUtils.isEmpty(nameField))
        {
            nametxtedit.setError("Please fill Name");
            nametxtedit.requestFocus();
        }else  if(TextUtils.isEmpty(instructionField))
        {
            instructiontxtedit.setError("Please fill Instruction");
            instructiontxtedit.requestFocus();
        }else  if(TextUtils.isEmpty(cautionField))
        {
            cautiontxtedit.setError("Please fill Caution");
            cautiontxtedit.requestFocus();
        }else{
            saveFunction();
        }

        /*else  if(TextUtils.isEmpty(photoField))
        {
            imgChooseText.setError("Please choose photo");
            imgChooseText.requestFocus();
        }*/
    }
    private void saveFunction(){
        hideKeyboard(FrmAddNewsfeed.this);
        String checkFunction="";
        String nameField = nametxtedit.getText().toString();
        String instructionField = instructiontxtedit.getText().toString();
        String cautionField = cautiontxtedit.getText().toString();
        String photoField = imgChooseText.getText().toString();
        if(UpdateFunc.equalsIgnoreCase("true")){
            checkFunction = "update";
        }else{
            checkFunction = "save";
        }
       Log.d("FrmAddNewsFeed","data check = "+nameField+","+instructionField+","+cautionField+","+photoField+","+checkFunction);
        Intent intent = new Intent();
        intent.putExtra("faid",faid);
        intent.putExtra("name",nameField);
        intent.putExtra("instruction",instructionField);
        intent.putExtra("caution",cautionField);
        intent.putExtra("photoData",photoField);
        intent.putExtra("checkFunc",checkFunction);
        setResult(Activity.RESULT_OK,intent);

        finish();
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

    private void showPictureDialog() {
        final AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Take Photo", "Choose from Gallery", "Cancel"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                if (!isPermissionCameraGranted()) {
                                    ActivityCompat.requestPermissions(FrmAddNewsfeed.this,
                                            new String[]{Manifest.permission.CAMERA},
                                            PERMISSION_REQUEST_CAMERA);


                                } else {
                                    takePhotoFromCamera();
                                }
                                break;
                            case 1:
                                choosePhotoFromGallary();
                                break;
                            case 2:
                                dialog.dismiss();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    private boolean isPermissionCameraGranted() {
        return ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(galleryIntent, GALLERY);

    }
    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }
    private void imageFileWrite() throws IOException{
        String filePath = FileUtility.getPhotoFolderPath();
        String fileName = String.valueOf(System.currentTimeMillis()).concat(".jpg");
        Pair<Boolean,String> returnResult = FileUtility.writeImageFile(filePath, fileName,imageBitmap);
        imgChooseText.setText(filePath+"/"+fileName);
       /* if (returnResult.first) {
            imageRVAdapter.addNewFile(new File(filePath,fileName));
        }*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CAMERA:
                if(resultCode == Activity.RESULT_OK){
                    try {
                        Bundle extras = data.getExtras();
                        imageBitmap = (Bitmap) extras.get("data");
                        if(!isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                            ActivityCompat.requestPermissions(this,
                                    new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                        }else{
                            imageFileWrite();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                break;
            case GALLERY:
                if(resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        Uri contentURI = data.getData();
                        try {
                            imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                            if(!isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                                ActivityCompat.requestPermissions(this,
                                        new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                            }else{
                                imageFileWrite();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                break;
        }
    }
    private boolean isPermissionGranted(String permission){
        return ContextCompat.checkSelfPermission(this,
                permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onBackPressed() {
        hideKeyboard(FrmAddNewsfeed.this);
        setResult(RESULT_OK);
        finish();
       // super.onBackPressed();
    }
}
