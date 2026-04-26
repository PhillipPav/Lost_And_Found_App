package com.example.sit305ass3;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresExtension;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sit305ass3.db.AppDatabase;
import com.example.sit305ass3.db.ItemModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class CreateNewAdvertFragment extends Fragment
{
    final String[] categories = {"All", "Electronics", "Pets", "Wallets"};
    String postType = "", title, category, phone, description, date, location;
    RadioGroup radioGroup;
    RadioButton lost, found;
    EditText editTitle, editPhone, editDescription, editDate, editLocation;
    Spinner categorySpinner;
    Button saveButton, insertImageButton;
    ImageView imageView;
    ArrayAdapter<String> adapterCategories;
    ActivityResultLauncher<Intent> resultLauncher;
    Uri imageUri;
    boolean imageSelected = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_new_advert, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        AppDatabase db = AppDatabase.getDbInstance(this.requireActivity().getApplicationContext());

        //FIND ALL VIEWS
        initViews();

        //RADIO BUTTON CHANGES ON CLICK
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull RadioGroup radioGroup, int selectedId)
            {
                if (selectedId == R.id.lost)
                {
                    postType = "LOST";
                }
                else if (selectedId == R.id.found)
                {
                    postType = "FOUND";
                }
            }
        });

        //CATEGORIES SPINNER ADAPTER
        adapterCategories = new ArrayAdapter<>(requireActivity(), R.layout.custom_spinner_style, categories);
        adapterCategories.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        categorySpinner.setAdapter(adapterCategories);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l)
            {
                 category = adapterView.getItemAtPosition(pos).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //URI IMAGE RESULT
        registerResult();
        //ONCLICK LISTENER FOR BUTTON
        insertImageButton.setOnClickListener(new View.OnClickListener() {
            @RequiresExtension(extension = Build.VERSION_CODES.R, version = 2)
            @Override
            public void onClick(View view)
            {
                pickImage();
            }
        });
        //SAVE BUTTON, TO CREATE ITEM
        saveButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view)
            {
                getUserInputs();
                if (areInputsValid())
                {
                    ItemModel itemModel = new ItemModel(postType, title, category, phone,
                            description, date, location, imageUri.toString());
                    db.itemModelDao().insertItem(itemModel);
                    Toast.makeText(getActivity(), "An item was successfully added", Toast.LENGTH_SHORT).show();
                    NavController navController = Navigation.findNavController(getActivity().findViewById(R.id.flFragment));
                    navController.navigate(R.id.action_createNewAdvertFragment_to_homeFragment);
                }
            }
        });
    }

    private void registerResult() {
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        try {
                            imageUri = result.getData().getData();
                            requireActivity().getContentResolver().takePersistableUriPermission(imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            imageView.setImageURI(imageUri);
                            imageSelected = true;
                        }catch (Exception e){
                            Toast.makeText(getActivity(), "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    @RequiresExtension(extension = Build.VERSION_CODES.R, version = 2)
    private void pickImage()
    {
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(intent);
    }

    //EXCLUDES POST TYPE, CATEGORY AND IMAGE URI BECAUSE THEY ARE UPDATED AS THE USER SELECTS THE RADIO AND INSERTS IMAGE
    private void getUserInputs()
    {
        title = editTitle.getText().toString();
        phone = editPhone.getText().toString();
        description = editDescription.getText().toString();
        date = editDate.getText().toString();
        location = editLocation.getText().toString();
    }

    private boolean areInputsValid()
    {
        String[] allUserInputs = {postType, title, phone, description, date, location};
        for (String str:allUserInputs)
        {
            if (str.isEmpty())
            {
                Toast.makeText(getActivity(), "Please fill all required fields", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!imageSelected)
            {
                Toast.makeText(getActivity(), "Please attach an image", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private void initViews()
    {
        editTitle = (EditText) getView().findViewById(R.id.editTitle);
        editPhone = (EditText) getView().findViewById(R.id.editPhone);
        editDescription = (EditText) getView().findViewById(R.id.editDescription);
        editDate = (EditText) getView().findViewById(R.id.editDate);
        editLocation = (EditText) getView().findViewById(R.id.editLocation);
        radioGroup = (RadioGroup) getView().findViewById(R.id.mRadioGroup);
        lost = (RadioButton) getView().findViewById(R.id.lost);
        found = (RadioButton) getView().findViewById(R.id.found);
        categorySpinner = (Spinner) getView().findViewById(R.id.categorySpinner);
        imageView = (ImageView) getView().findViewById(R.id.image);
        insertImageButton = (Button) getView().findViewById(R.id.insertImageButton);
        saveButton = (Button) getView().findViewById(R.id.saveButton);
    }
}