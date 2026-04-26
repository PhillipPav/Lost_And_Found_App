package com.example.sit305ass3;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sit305ass3.db.AppDatabase;
import com.example.sit305ass3.db.ItemModel;

public class AdvertExpandedFragment extends Fragment
{
    int itemId;
    ItemModel itemModel;
    TextView postType, title, phone, description, date, location, dateTimeStamp;
    Button removeButton;
    ImageView itemImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_advert_expanded, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initViews();

        getParentFragmentManager().setFragmentResultListener("itemSelected",
                this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result)
                    {
                        itemId = result.getInt("itemId");
                        loadItemById(itemId);
                    }
                });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                deleteItemById(itemId);
            }
        });
    }

    private void initViews()
    {
        dateTimeStamp = (TextView) getView().findViewById(R.id.dateTimeStamp);
        postType = (TextView) getView().findViewById(R.id.postType);
        title = (TextView) getView().findViewById(R.id.title);
        phone = (TextView) getView().findViewById(R.id.phone);
        description = (TextView) getView().findViewById(R.id.description);
        date = (TextView) getView().findViewById(R.id.date);
        location = (TextView) getView().findViewById(R.id.location);
        itemImage = (ImageView) getView().findViewById(R.id.image);
        removeButton = (Button) getView().findViewById(R.id.removeButton);
    }

    private void loadItemById(int itemId)
    {
        //Load item by from database query
        AppDatabase db = AppDatabase.getDbInstance(this.requireActivity().getApplicationContext());
        itemModel = db.itemModelDao().getItemByid(itemId);

        //CONCAT DATETIME STAMP STRINGS, ITEM KEEPS THEM SEPARATE IF WE EVER NEED TO FILTER ITEMS BY YEAR, MONTH, ETC.
        String str = itemModel.getDateStampAsString() + " " + itemModel.getTimeStampAsString();
        //Set TextViews
        dateTimeStamp.setText(str);
        postType.setText(itemModel.getPostType());
        title.setText(itemModel.getTitle());
        phone.setText(itemModel.getPhone());
        description.setText(itemModel.getDescription());
        date.setText(itemModel.getDate());
        location.setText(itemModel.getLocation());
        itemImage.setImageURI(Uri.parse(itemModel.getImageUri()));
    }

    private void deleteItemById(int itemId)
    {
        //Delete item using database query
        AppDatabase db = AppDatabase.getDbInstance(this.requireActivity().getApplicationContext());
        db.itemModelDao().deleteItemById(itemId);

        Toast.makeText(getActivity(), "The item was successfully removed", Toast.LENGTH_SHORT).show();

        //Navigate back to item list
        NavController navController = Navigation.findNavController(getActivity().findViewById(R.id.flFragment));
        navController.navigate(R.id.action_advertExpandedFragment_to_homeFragment);
    }

}