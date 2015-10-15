package com.envoy.game.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.envoy.game.R;
import com.envoy.game.events.Message;
import com.envoy.game.fragments.AddGameFragment;
import com.envoy.game.fragments.GamesFragment;

import butterknife.ButterKnife;

/**
 * @author David Castillo Fuentes <dcf82@hotmail.com>
 * List of games added to the application
 */
public class ListGamesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // View Content
        setContentView(R.layout.activity_list_games);

        // Inject Dependencies
        ButterKnife.bind(this);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setup Action Bar
        setUpActionBar();

        if (savedInstanceState == null) {
            addFragment(R.id.container, GamesFragment.newInstance(), true);
        }
    }

    public void onEvent(Message event) {
        switch(event.getEvent()) {
            case ADD_NEW_GAME:{
                addFragment(R.id.container, AddGameFragment.newInstance(), true);
            }
            break;
        }
    }
}
