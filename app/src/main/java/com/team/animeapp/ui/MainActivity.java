package com.team.animeapp.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.team.animeapp.R;
import com.team.animeapp.databinding.ActivityMainBinding;
import com.team.animeapp.util.AnimationHelper;

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
        setUpOnDestinationChangedListener();
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

    private void hideBottomNav()
    {
        if(binding.BottomBar.getVisibility() == View.VISIBLE)
        {
            AnimationHelper.playAnimation(binding.BottomBar,R.anim.slide_down_fade_out);
            binding.BottomBar.setVisibility(View.INVISIBLE);
        }
    }

    private void showBottomNav()
    {
        if(binding.BottomBar.getVisibility() == View.INVISIBLE)
        {
            AnimationHelper.playAnimation(binding.BottomBar,R.anim.slide_up_fade_in);
            binding.BottomBar.setVisibility(View.VISIBLE);
        }
    }

    private void setUpOnDestinationChangedListener()
    {
        ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView))
                .getNavController()
                .addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
                    @Override
                    public void onDestinationChanged(@NonNull NavController controller,
                                                     @NonNull NavDestination destination,
                                                     @Nullable  Bundle arguments) {


                        switch (destination.getId())
                        {
                            case R.id.topAnimeFragment:
                            case R.id.searchFragment:
                            case R.id.genreFragment:
                            case R.id.watchLaterFragment:
                                showBottomNav();
                                break;
                            default:
                                hideBottomNav();
                                break;
                        }

                    }
                });
    }

}