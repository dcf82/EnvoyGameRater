package com.envoy.game.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.envoy.game.R;
import com.envoy.game.greendao.Game;
import com.envoy.game.utilities.DatabaseUtilities;
import com.envoy.game.utilities.ImageUtilities;

import java.util.ArrayList;

/**
 * @author David Castillo Fuentes <dcf82@hotmail.com>
 * Adapter for listing the games
 */
public class GamesAdapter extends ArrayAdapter<Game> implements CompoundButton.OnCheckedChangeListener {
    // Num Items
    private static final int NUM_ITEMS = 2;

    // Options
    public static final int LIST_GAMES_ADAPTER = 0;
    public static final int RATE_GAMES_ADAPTER = 1;

    // Current Type
    private int mType;

    LayoutInflater mLayoutInflater;
    Game mGame;
    Holder holder;

    public GamesAdapter(Context context, ArrayList<Game> games, int type) {
        super(context, 0, games);
        mType = type;
        mLayoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null || !(view.getTag() instanceof Holder)) {
            view = buildView(parent);
            holder = new Holder();
            holder.imgGame = (ImageView) view.findViewById(R.id.imgGame);
            holder.gameName = (TextView)view.findViewById(R.id.gameName);
            holder.consoleName = (TextView)view.findViewById(R.id.consoleName);

            if (mType == LIST_GAMES_ADAPTER) {
                holder.finished = (CheckBox)view.findViewById(R.id.finished);
                holder.finished.setOnCheckedChangeListener(this);
            } else {
                holder.rating = (RatingBar)view.findViewById(R.id.rating);
            }

            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        mGame = getItem(position);

        if (mGame.getMGameImage() == null) {
            holder.imgGame.setImageResource(R.drawable.ic_action_picture);
        } else {
            holder.imgGame.setImageBitmap(ImageUtilities.getBitmap(mGame.getMGameImage()));
        }
        holder.gameName.setText(mGame.getMGameName());
        holder.consoleName.setText(mGame.getMConsoleName());

        if (mType == LIST_GAMES_ADAPTER) {
            holder.finished.setTag(mGame);
            holder.finished.setChecked(mGame.getMGameFinished());
        } else {
            holder.game = mGame;
            holder.rating.setRating(mGame.getMStarsNumber());
        }

        return view;
    }

    public int getViewTypeCount() {
        return NUM_ITEMS;
    }

    public int getItemViewType(int position) {
        return mType;
    }

    private View buildView(ViewGroup parent) {
        View view;

        switch(mType) {
            case LIST_GAMES_ADAPTER:
                view = mLayoutInflater.inflate(R.layout.game_item_add, parent, false);
                break;

            default:
            case RATE_GAMES_ADAPTER:
                view = mLayoutInflater.inflate(R.layout.game_item_rate, parent, false);
                break;
        }

        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Game game = (Game)buttonView.getTag();
        if (isChecked != game.getMGameFinished()) {
            game.setMGameFinished(isChecked);
            DatabaseUtilities.updateGame(game);
            notifyDataSetChanged();
        }
    }

    public static class Holder {
        ImageView imgGame;
        TextView gameName;
        TextView consoleName;
        CheckBox finished;
        RatingBar rating;
        public Game game;
    }

}
