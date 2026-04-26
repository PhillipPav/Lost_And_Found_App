package com.example.sit305ass3;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment
{

    NavController navController;
    Button createButton, showListButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        // BUTTONS FOR ONCLICK LISTENER
        createButton = (Button) getView().findViewById(R.id.createButton);
        showListButton = (Button) getView().findViewById(R.id.showListButton);

        //ONCLICK LISTENERS
        createButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                navToCreateFragment(view);
            }
        });
        showListButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                navToListFragment(view);
            }
        });
    }

    public void navToCreateFragment(View view)
    {
        navController = Navigation.findNavController(getActivity().findViewById(R.id.flFragment));
        navController.navigate(R.id.action_homeFragment_to_createNewAdvertFragment);
    }

    public void navToListFragment(View view)
    {
        navController = Navigation.findNavController(getActivity().findViewById(R.id.flFragment));
        navController.navigate(R.id.action_homeFragment_to_showListFragment);
    }
}