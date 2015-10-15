package com.envoy.game.utilities;

import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

import com.envoy.game.controller.Controller;

/**
 * @author David Castillo Fuentes <dcf82@hotmail.com>
 * Utility class for general usage
 */
public class Utilities {

    public static float dpFromPx(final float px) {
        return px / Controller.getApp().getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(final float dp) {
        return dp * Controller.getApp().getResources().getDisplayMetrics().density;
    }

    public static void showMessage(String message) {
        Toast msg = Toast.makeText(Controller.getApp(), message, Toast.LENGTH_SHORT);
        msg.setGravity(Gravity.CENTER, 0, 0);
        msg.show();
    }

    public static String getText(EditText field) {
        return field.getText().toString().trim();
    }

    public static boolean isEmpty(EditText field, String msg) {
        boolean isEmpty = field.getText().toString().trim().equals("");
        if (isEmpty) {
            Utilities.showMessage(msg);
        }
        return isEmpty;
    }
}
