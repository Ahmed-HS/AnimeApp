package com.team.animeapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.team.animeapp.R;
import com.team.animeapp.databinding.ActivityMainBinding;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        styleBottomNavigationBar();
    }

    private void styleBottomNavigationBar()
    {
        MaterialShapeDrawable bottomNavigationDrawable = (MaterialShapeDrawable) binding.bottomNav.getBackground();
        float radius = getResources().getDimension(R.dimen.bottom_nav_radius);
        ShapeAppearanceModel shape = bottomNavigationDrawable.getShapeAppearanceModel()
                .toBuilder()
                .setTopRightCorner(CornerFamily.ROUNDED, radius)
                .setTopLeftCorner(CornerFamily.ROUNDED, radius)
                .build();

        bottomNavigationDrawable.setShapeAppearanceModel(shape);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(binding.bottomNav,navController);

    }

}