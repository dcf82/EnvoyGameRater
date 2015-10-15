package com.envoy.game.dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RatingBar;

import com.envoy.game.R;
import com.envoy.game.events.Message;
import com.envoy.game.greendao.Game;
import com.envoy.game.utilities.DatabaseUtilities;
import com.envoy.game.utilities.Utilities;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * @author David Castillo Fuentes <dcf82@hotmail.com>
 * Popup dialog for rating a Game
 */
public class MyDialog extends DialogFragment implements RatingBar.OnRatingBarChangeListener {

    Game mGame;
    int mValue = -1;

    @Bind(R.id.rating)
    RatingBar mRating;

    public static MyDialog newDialog(Game game) {
        MyDialog dialog = new MyDialog();
        dialog.mGame = game;
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_dialog, container,
                false);
        ButterKnife.bind(this, view);
        getDialog().setTitle("");
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Dialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        //WindowManager.LayoutParams params = window.getAttributes();
        //params.dimAmount = 0.6f;
        //window.setAttributes(params);
        window.setBackgroundDrawableResource(android.R.color.transparent);
    }


    private void setDialogPosition() {
        Window window = getDialog().getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = window.getAttributes();
        params.y = (int)Utilities.pxFromDp(60);
        window.setAttributes(params);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        mRating.setRating(mGame.getMStarsNumber());
        mRating.setOnRatingBarChangeListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mGame = null;
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.ok)
    public void ok() {
        if (mValue != -1) {
            mGame.setMStarsNumber(mValue);
            DatabaseUtilities.updateGame(mGame);
            EventBus.getDefault().postSticky(new Message(Message.EventType.REFRESH_GAME));
        }
        dismiss();
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        mValue = (int)rating;
    }
}
