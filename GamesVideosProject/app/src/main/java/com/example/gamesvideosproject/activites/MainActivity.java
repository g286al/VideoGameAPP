package com.example.gamesvideosproject.activites;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.gamesvideosproject.R;
import com.example.gamesvideosproject.adapters.ItemClickListener;
import com.example.gamesvideosproject.adapters.OnScrollListener;
import com.example.gamesvideosproject.fragments.GameDetails;
import com.example.gamesvideosproject.fragments.GamesList;
import com.example.gamesvideosproject.fragments.HomeFragment;
import com.example.gamesvideosproject.fragments.LoginFragment;
import com.example.gamesvideosproject.fragments.RegisterFragment;
import com.example.gamesvideosproject.fragments.SplashScreenFragment;
import com.example.gamesvideosproject.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity implements OnScrollListener, ItemClickListener {
    MainActivityViewModel viewModel = new MainActivityViewModel();
    GamesList mFragment = new GamesList(this,this);
    public enum FragmentType{
        register,login,gamelist,splash,home
        };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        //showHomeFragment();
        showSplashFragment();
    }

    public void showLoginFragment()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, new LoginFragment(this)).addToBackStack("loginFragment").commit();
    }
    public void showRegisterFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, new RegisterFragment()).addToBackStack("registerFragment").commit();
    }
    public void showHomeFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, new HomeFragment(this)).commit();
    }
    public void showSplashFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, new SplashScreenFragment(this)).commit();
    }
    public void showGameListFragment()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, mFragment).commit();
        viewModel.dataService.fetchGames();
        viewModel.newItems.observe(this,games -> {
            mFragment.updateData(games);
        });
    }
    void didTapGame(String name) {

        viewModel.getGameByName(name);
        viewModel.gameDetailFetched.observe(this,game ->
        {
            GameDetails mFragment  = new GameDetails(game);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, mFragment).addToBackStack("gameDetailsFragment").commit();

        });
    }
    @Override
    public void onScrollEnd() {
        viewModel.fetchMoreData();
    }

    @Override
    public void onClick(String name) {
        didTapGame(name);
    }
    @Override
    public void onClick(FragmentType fragmentType) {
        switch (fragmentType){
            case login:
                showLoginFragment();
                break;
            case gamelist:
                showGameListFragment();
                break;
            case register:
                showRegisterFragment();
                break;
            case splash:
                showSplashFragment();
                break;
            case home:
                showHomeFragment();
                break;
        }
    }

}
