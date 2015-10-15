package com.envoy.game.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.envoy.game.R;
import com.envoy.game.adapters.GamesAdapter;
import com.envoy.game.controller.Controller;
import com.envoy.game.events.Message;
import com.envoy.game.utilities.DatabaseUtilities;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * @author David Castillo Fuentes <dcf82@hotmail.com>
 * Fragment that lists all the games added to the app
 */
public class GamesFragment extends FragmentBase {
    private ListView mListView;
    private GamesAdapter mAdapter;

    public static GamesFragment newInstance() {
        GamesFragment fragment = new GamesFragment();
        return fragment;
    }

    public GamesFragment() {}

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);

        View view = getView();

        mAdapter = new GamesAdapter(getActivity(), new ArrayList<>(DatabaseUtilities.getGames()),
                GamesAdapter.LIST_GAMES_ADAPTER);

        mListView = (ListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);

        TextView emptyText = (TextView)view.findViewById(android.R.id.empty);
        mListView.setEmptyView(emptyText);
    }

    public void onResume() {
        super.onResume();
        EventBus.getDefault().registerSticky(this);
    }

    public void onStop() {
        super.onResume();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(Message event) {
        switch (event.getEvent()) {
            case  REFRESH_GAME:
                mAdapter.clear();
                mAdapter.addAll(DatabaseUtilities.getGames());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_games, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_list_games, menu);
        showGlobalContextActionBar(Controller.getApp().getResources().getString(R.string.title_activity_list_games));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add:
                EventBus.getDefault().post(new Message(Message.EventType.ADD_NEW_GAME));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
