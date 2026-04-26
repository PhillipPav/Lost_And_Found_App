package com.example.sit305ass3;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.sit305ass3.db.AppDatabase;
import com.example.sit305ass3.db.ItemModel;

import java.util.List;

public class ShowListFragment extends Fragment implements RecyclerViewInterface
{
    final String[] categories = {"All", "Electronics", "Pets", "Wallets"};
    RecyclerView recyclerView;
    Spinner categorySpinner;
    List<ItemModel> itemModels;
    ItemRecyclerViewAdapter adapter;
    ArrayAdapter<String> adapterCategories;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //INITIALIZE RECYCLER VIEW ON VIEW CREATED SETTING ADAPTER, LAYOUT AND THEN LOADING EVENTS LIST FROM ROOM DB
        initSpinnerView();
        initRecyclerView();
        loadAllItems();

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l)
            {
                String category = adapterView.getItemAtPosition(pos).toString();
                //If user seletcs All, show every item otherwise show categorized result
                if (category.equals("All"))
                    loadAllItems();
                else
                    loadItemsByCategory(category);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void initRecyclerView()
    {
        recyclerView = (RecyclerView) getView().findViewById(R.id.mRecyclerView);
        adapter = new ItemRecyclerViewAdapter(getActivity(), itemModels, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void initSpinnerView()
    {
        //CATEGORIES SPINNER ADAPTER
        categorySpinner = (Spinner) getView().findViewById(R.id.categorySpinner);
        adapterCategories = new ArrayAdapter<>(requireActivity(), R.layout.custom_spinner_style, categories);
        adapterCategories.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        categorySpinner.setAdapter(adapterCategories);
    }

    private void loadAllItems()
    {
        AppDatabase db = AppDatabase.getDbInstance(this.requireActivity().getApplicationContext());
        itemModels = db.itemModelDao().getAllItems();
        adapter.setItemList(itemModels);
    }
    private void loadItemsByCategory(String category)
    {
        AppDatabase db = AppDatabase.getDbInstance(this.requireActivity().getApplicationContext());
        itemModels = db.itemModelDao().getItemsByCategory(category);
        adapter.setItemList(itemModels);
    }
    @Override
    public void onItemClick(int position)
    {
        navController = Navigation.findNavController(getActivity().findViewById(R.id.flFragment));
        navController.navigate(R.id.action_showListFragment_to_advertExpandedFragment);

        Bundle bundle = new Bundle();
        bundle.putInt("itemId", itemModels.get(position).id);
        getParentFragmentManager().setFragmentResult("itemSelected", bundle);
    }

    private void sendBundle()
    {

    }
}