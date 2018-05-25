package at.fhj.swd.k_uber.helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import at.fhj.swd.k_uber.ErrorActivity;

/**
 * Helper class making/throwing Errors
 */
public abstract class ErrorHelper {

    public static final String TITLE_TAG = "ErrorTitle";
    public static final String MESSAGE_TAG = "ErrorMessage";




    /**
     * starts ErrorActivity with given message and throwable
     * history stack is cleared (prevents jumping pack to same error)
     * @param activity
     * @param title
     * @param message
     */
    public static void makeError(Activity activity, String title, String message, @Nullable Throwable exception) {
        // Log the errors
        // Log cannot handle null. However, String + null is a nice work around
        Log.e(activity.getLocalClassName(), TITLE_TAG + " " + title);
        Log.e(activity.getLocalClassName(), MESSAGE_TAG + " " + message);
        Log.e(activity.getLocalClassName(), "exception caused by: ", exception);

        // Give error message to ErrorActivity
        Bundle extras = new Bundle();
        extras.putString(TITLE_TAG, title);
        extras.putString(MESSAGE_TAG, message);

        // put the bundle and tell android to clear history
        Intent intent = new Intent(activity, ErrorActivity.class);
        intent.putExtras(extras);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // start error activity and finish old activity to prevent jump back
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     * starts ErrorActivity with given message
     * history stack is cleared (prevents jumping pack to same error)
     * @param activity
     * @param title
     * @param message
     */
    public static void makeError(Activity activity, String title, String message) {
        makeError(activity, title, message, null);
    }

}
