package com.envoy.game.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.envoy.game.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author David Castillo Fuentes <dcf82@hotmail.com>
 * Home screen
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Title
        setTitle(R.string.title_activity_home_games);

        // View Content
        setContentView(R.layout.activity_main);

        // Inject Dependencies
        ButterKnife.bind(this);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    // Click Handler
    @OnClick({ R.id.listGames, R.id.rateGames })
    public void myClickHandler(View button) {
        switch(button.getId()) {
            case R.id.listGames:
                startActivity(new Intent(this, ListGamesActivity.class));
                break;
            case R.id.rateGames:
                startActivity(new Intent(this, RateGamesActivity.class));
                break;
        }
    }
}
