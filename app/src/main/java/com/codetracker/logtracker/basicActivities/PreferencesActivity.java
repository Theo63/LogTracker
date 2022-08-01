package com.codetracker.logtracker.basicActivities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.codetracker.logtracker.CustomDialogClass;
import com.codetracker.logtracker.LogtrackerDBHandler;
import com.codetracker.logtracker.R;
import com.codetracker.logtracker.showTotalHoursActivity;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;

public class PreferencesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static Button SaveBtn, ResetStartingHours, DisplayTotalHours, ResetDB, ExportDB, BackupDB, ImportDB, startingHoursHelpBtn, DBHelpBtn;
    EditText startingHours;
    Spinner aircraftSpinner;
    String aircraft;
    Integer pos1;
    ArrayList<ArrayList> hoursArrayList;
    ArrayList<String> typeHoursList;
    HashMap<String, Integer> totalHours;
    LogtrackerDBHandler flightsDBprefs;

    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;


    private static final int WRITE_REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefs);
        getWindow().setNavigationBarColor(Color.parseColor("#557DBC"));
        flightsDBprefs = new LogtrackerDBHandler(this); //creates db obj
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
        hoursArrayList = new ArrayList<>();
        typeHoursList = new ArrayList<>();
        aircraftSpinner = (Spinner) findViewById(R.id.aircraft_pref_spinner1);


        // Create an ArrayAdapter using the string array and a default spinner layout for ALL Spinners
        ArrayAdapter<CharSequence> aircraft_adapter = ArrayAdapter.createFromResource(this,
                R.array.Aircraft_types, R.layout.spinner_item); //Aircraft_types is in res/values/strings.xml

        // Specify the layout to use when the list of choices appears
        aircraft_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapters to the spinners
        aircraftSpinner.setAdapter(aircraft_adapter);
        aircraftSpinner.setOnItemSelectedListener(this);

        startingHours = (EditText) findViewById(R.id.startingHours);


        //save starting hours for each type
        SaveBtn = (Button) findViewById(R.id.Save_prefs_Button);
        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //store name mail and hours
                String hours1 = startingHours.getText().toString();
                boolean isInserted = flightsDBprefs.addStartingHours(aircraft, hours1);
                if (isInserted) {
                    Snackbar successSnack = Snackbar.make(findViewById(R.id.preferences_layout),
                            "Your starting hours info is now stored", 1200);
                    successSnack.show();
                    aircraftSpinner.setSelection(0);
                    startingHours.setText("");
                } else {
                    Snackbar missingfieldsSnack = Snackbar.make(findViewById(R.id.preferences_layout),
                            "Type already registered or a field is missing", 1200);
                    missingfieldsSnack.show();
                }


            }
        });

        //reset button for starting hours
        ResetStartingHours = (Button) findViewById(R.id.resetStrartingHours);
        ResetStartingHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // setting a warning dialog before resetting the database
                AlertDialog alertDialog = new AlertDialog.Builder(PreferencesActivity.this).create();
                alertDialog.setTitle("Starting Hours Reset Warning !");
                alertDialog.setMessage("ALL starting hours will be reset. Are you sure ?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                flightsDBprefs.resetStartingHoursTable(); // database reset after pressing ok
                                Snackbar clearedSnack = Snackbar.make(findViewById(R.id.preferences_layout),
                                        "Starting Hours Cleared", 1200);
                                clearedSnack.show();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                alertDialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

        DisplayTotalHours = (Button) findViewById(R.id.displayTotalHours);
        DisplayTotalHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalHours = flightsDBprefs.getTotalHours();
                for (String key : totalHours.keySet()) {
                    typeHoursList.add(key);
                    String hours = "";
                    hours = totalHours.get(key) / 60 + " Ηours : " + totalHours.get(key) % 60 + " Μinutes ";
                    typeHoursList.add(hours);
                    hoursArrayList.add(typeHoursList);
                    typeHoursList = new ArrayList<>();
                }


                Intent intentShow = new Intent(PreferencesActivity.this, showTotalHoursActivity.class);
                intentShow.putExtra("total hours", hoursArrayList);
                startActivity(intentShow);

                finish();
            }
        });


        ResetDB = (Button) findViewById(R.id.Reset_database_Button);
        ResetDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // setting a warning dialog before resetting the database
                AlertDialog alertDialog = new AlertDialog.Builder(PreferencesActivity.this).create();
                alertDialog.setTitle("Database Reset Warning !");
                alertDialog.setMessage("ALL data will be reset. Are you sure ?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                flightsDBprefs.resetDB(); // database reset after pressing ok
                                Snackbar dbemptySnack = Snackbar.make(findViewById(R.id.preferences_layout),
                                        "Database is now empty", 1500);
                                dbemptySnack.show();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                alertDialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

        ExportDB = (Button) findViewById(R.id.DB_exportToExcelButton);
        ExportDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String path = flightsDBprefs.writeFlightsToExcel();
                Snackbar exportExcelSnack = Snackbar.make(findViewById(R.id.preferences_layout),
                        "FLights.xls is in your internal storage home directory", 1500);
                exportExcelSnack.show();

            }
        });

        BackupDB = (Button) findViewById(R.id.DB_exportButton);
        BackupDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                boolean backupStatus = flightsDBprefs.exportDatabase();
//                Snackbar backupSnack;
//                if (backupStatus){
//                    backupSnack = Snackbar.make(findViewById(R.id.preferences_layout),
//                            "Your backup file is in your internal storage home directory", 1500);
//                }
//                else {
//                    backupSnack = Snackbar.make(findViewById(R.id.preferences_layout),
//                            "Backup could not be completed", 1500);
//                }
//                backupSnack.show();

                exportContent();

            }

        });


        ImportDB = (Button) findViewById(R.id.DB_importButton);
        ImportDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // setting a warning dialog before resetting the database
                AlertDialog alertDialog = new AlertDialog.Builder(PreferencesActivity.this).create();
                alertDialog.setTitle("Database Overwrite Warning !");
                alertDialog.setMessage("Your changes to logbook since last backup will be overwritten. Are you sure ?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                openSomeActivityForResult();

                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                alertDialog.dismiss();
                            }
                        });
                alertDialog.show();


            }


        });

        startingHoursHelpBtn = (Button) findViewById(R.id.helpButtonStarting);
        startingHoursHelpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialogClass cdd = new CustomDialogClass(PreferencesActivity.this);
                cdd.show();
                cdd.setInfo("• Set an hour amount prior to using the app for every helicopter type you register flights for (set 0 if you fly a helicopter for the first time)." +
                        "\n\n• Only types with starting hours set are shown in the 'Total Hours' screen. ");
            }
        });


        DBHelpBtn = (Button) findViewById(R.id.helpButtonDB);
        DBHelpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialogClass cdd = new CustomDialogClass(PreferencesActivity.this);
                cdd.show();
                cdd.setInfo("• Database and excel file backup are stored in directory of your choice \n\n• To export choose between\n  - Cloud drives \n  - Third party file managers " +
                        " \n\n• To import choose your backup database ");
            }
        });


    }

    //////spinners ////////////////
    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        String spinnerInput = parent.getItemAtPosition(pos).toString();
        //just a toast to make sure item is passed
        if (parent.getId() == R.id.aircraft_pref_spinner1 && !spinnerInput.equals("choose")) {
            // first spinner selected
//            Toast.makeText(parent.getContext(),"first spinner" + spinnerInput, Toast.LENGTH_SHORT).show();
            pos1 = pos;
            aircraft = spinnerInput; // set the type of aircraft
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public void onBackPressed() {   // resets  Search activity and every field is cleared for a new search
        startActivity(new Intent(this, MainActivity.class));
    }


    //Backup flights to any directory user chooses
    private void exportContent() {
        copyContentToPrivateStorage();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("application/octet-stream");
        Uri uri = new FileProvider().getDatabaseURI(this);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(intent, "Backup via:"));
    }
    private void copyContentToPrivateStorage() {
        // From https://stackoverflow.com/a/2661882
        try {
            File data = Environment.getDataDirectory();
            File sd = getFilesDir();
            if (sd.canWrite()) {
                String currentDBPath = "//data//com.codetracker.logtracker//databases//flightsDB.db";
                String backupDBPath = "flightsDB.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);
                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    public class FileProvider extends androidx.core.content.FileProvider {
        public Uri getDatabaseURI(Context c) {
            File exportFile = new File(c.getFilesDir(), "flightsDB.db");
            Uri uri = getUriForFile(c, "com.codetracker.logtracker.fileprovider", exportFile);
            c.grantUriPermission("*", uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            return uri;
        }
    }



        // Import flights from any directory user chooses
        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Snackbar importSnack;
                            Intent data = result.getData();

                            String extension = MimeTypeMap.getFileExtensionFromUrl(String.valueOf(data.getData()));
                            if (!extension.equals("db")){
                                importSnack = Snackbar.make(findViewById(R.id.preferences_layout),
                                        "Import could not be completed. There is a problem with the file you chose ", 1500);
                                importSnack.show();
                                return;
                            }

                            try {
                                FileInputStream fileInputStream = (FileInputStream) getContentResolver().openInputStream(data.getData());
                                FileChannel src = fileInputStream.getChannel();
                                if (fileInputStream.available()==0){
                                    importSnack = Snackbar.make(findViewById(R.id.preferences_layout),
                                            "The file you chose is empty. ", 1500);
                                    importSnack.show();
                                    return;
                                }

                                File dataDB = Environment.getDataDirectory();
                                String currentDBPath = "//data//com.codetracker.logtracker//databases//flightsDB.db";
                                File currentDB = new File(dataDB, currentDBPath);
                                FileChannel dst = new FileOutputStream(currentDB).getChannel();

                                dst.transferFrom(src, 0, src.size());
                                src.close();
                                dst.close();

                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                                importSnack = Snackbar.make(findViewById(R.id.preferences_layout),
                                        "Import could not be completed because file not found", 1500);
                                importSnack.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                                importSnack = Snackbar.make(findViewById(R.id.preferences_layout),
                                        "Import could not be completed. There is a problem with the file you chose ", 1500);
                                importSnack.show();
                            }
                            importSnack = Snackbar.make(findViewById(R.id.preferences_layout),
                                    "Import Completed Successfully", 1500);
                            importSnack.show();

                        }
                    }
                });

        public void openSomeActivityForResult() {
            Intent chooseFile;
            chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
            chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
            chooseFile.setType("*/*");
            someActivityResultLauncher.launch(chooseFile);
        }







}