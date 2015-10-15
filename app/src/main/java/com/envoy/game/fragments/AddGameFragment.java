package com.envoy.game.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.envoy.game.R;
import com.envoy.game.controller.Controller;
import com.envoy.game.events.Message;
import com.envoy.game.greendao.Game;
import com.envoy.game.utilities.DatabaseUtilities;
import com.envoy.game.utilities.ImageUtilities;
import com.envoy.game.utilities.Utilities;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * @author David Castillo Fuentes <dcf82@hotmail.com>
 * Fragment for adding a new game
 */
public class AddGameFragment extends FragmentBase {
    private static final int SELECT_PICTURE = 1;

    @Bind(R.id.gameName)
    EditText gameName;
    @Bind(R.id.consoleName)
    EditText consoleName;
    @Bind(R.id.photo)
    ImageView photo;

    Bitmap image;

    public static AddGameFragment newInstance() {
        AddGameFragment fragment = new AddGameFragment();
        return fragment;
    }

    public AddGameFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_game, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void onResume() {
        super.onResume();
        if (image != null) {
            photo.setImageBitmap(image);
        }
    }

    @OnClick(R.id.photo)
    public void photo() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PICTURE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Clean up
        menu.clear();

        // Add Menu Options
        inflater.inflate(R.menu.menu_add_game, menu);

        // Set Title
        showGlobalContextActionBar(Controller.getApp().getResources().getString(R.string.title_fragment_add_game));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_save:
                if (!Utilities.isEmpty(gameName, getString(R.string.write_game_name))
                        && !Utilities.isEmpty(consoleName, getString(R.string.write_console_name))) {
                    // Save the new game
                    Game game = new Game();
                    game.setMGameName(Utilities.getText(gameName));
                    game.setMConsoleName(Utilities.getText(consoleName));
                    game.setMGameFinished(false);
                    game.setMStarsNumber(0);
                    if (image != null) {
                        game.setMGameImage(ImageUtilities.getByteArray(image));
                    }
                    DatabaseUtilities.saveGame(game);
                    EventBus.getDefault().postSticky(new Message(Message.EventType.REFRESH_GAME));
                    getActivity().onBackPressed();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onDestroy() {
        super.onDestroy();
        image = null;
        ButterKnife.unbind(this);
    }

    public void onActivityResult(int requestCode, int responseCode, Intent imageReturned) {
        if (requestCode == Activity.RESULT_CANCELED) return;

        switch(requestCode) {
            case SELECT_PICTURE:{
                image = ImageUtilities.resizeBitmap(imageReturned, (int) Utilities.pxFromDp(100),
                        (int) Utilities.pxFromDp(100));
            }
                break;
        }
    }
}
