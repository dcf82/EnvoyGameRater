package com.envoy.game.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.envoy.game.R;
import com.envoy.game.fragments.RateGamesFragment;

import butterknife.ButterKnife;

/**
 * @author David Castillo Fuentes <dcf82@hotmail.com>
 * Activity for rating the games
 */
public class RateGamesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // View Content
        setContentView(R.layout.activity_rate_games);

        // Inject Dependencies
        ButterKnife.bind(this);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setup Action Bar
        setUpActionBar();

        if (savedInstanceState == null) {
            addFragment(R.id.container, RateGamesFragment.newInstance(), true);
        }
    }

}
