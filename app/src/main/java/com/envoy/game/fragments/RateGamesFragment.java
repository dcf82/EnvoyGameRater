package com.envoy.game.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.envoy.game.R;
import com.envoy.game.adapters.GamesAdapter;
import com.envoy.game.controller.Controller;
import com.envoy.game.dialogs.MyDialog;
import com.envoy.game.events.Message;
import com.envoy.game.utilities.DatabaseUtilities;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * @author David Castillo Fuentes <dcf82@hotmail.com>
 * Fragment that has the list of games to be rated
 */
public class RateGamesFragment extends FragmentBase implements ListView.OnItemClickListener {
    private ListView mListView;
    private GamesAdapter mAdapter;

    public static RateGamesFragment newInstance() {
        RateGamesFragment fragment = new RateGamesFragment();
        return fragment;
    }

    public RateGamesFragment() {}

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);

        View view = getView();

        mAdapter = new GamesAdapter(getActivity(), new ArrayList<>(DatabaseUtilities.getGames()),
                GamesAdapter.RATE_GAMES_ADAPTER);

        mListView = (ListView) view.findViewById(android.R.id.list);
        mListView.setOnItemClickListener(this);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_games, container, false);
    }

    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
        GamesAdapter.Holder holder = (GamesAdapter.Holder)view.getTag();
        MyDialog dialog = MyDialog.newDialog(holder.game);
        dialog.show(getChildFragmentManager(), null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_base, menu);
        showGlobalContextActionBar(Controller.getApp().getResources().getString(R.string.title_activity_rate_games));
    }

}
