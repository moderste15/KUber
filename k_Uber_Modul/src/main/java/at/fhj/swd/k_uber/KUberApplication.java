package at.fhj.swd.k_uber;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toolbar;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;

import at.fhj.swd.k_uber.helper.PrefUtility;

public class KUberApplication extends Application {

    private final String LOGTAG = KUberApplication.class.getSimpleName();

    private SharedPreferences settings;

    private boolean usingDarkTheme;

    @Override
    public void onCreate() {
        applyTheme(this);
        super.onCreate();
        registerActivityLifecycleCallbacks(new KUberLifeCycle(KUberApplication.this));

        setupSharedPrefences();
        usingDarkTheme = settings.getBoolean(PrefUtility.DARKMODE, true);


        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                Log.i(LOGTAG, "AWSMobileClient is instantiated and you are connected to AWS!");
            }
        }).execute();
    }




    private void setupSharedPrefences() {
        settings = getSharedPreferences(
                PrefUtility.PREF_NAME,
                Context.MODE_PRIVATE);
    }



    public boolean isUsingDarkTheme() {
        return usingDarkTheme;
    }

    public void setUsingDarkTheme(boolean usingDarkTheme) {
        this.usingDarkTheme = usingDarkTheme;
    }


    /**
     * Applies current theme to given context
     * or theme without action bar for splash screen
     * @param context
     */
    public void applyTheme(Context context) {
        if (isUsingDarkTheme())
            context.setTheme(R.style.NeonDarkTheme);
        if (!isUsingDarkTheme())
            context.setTheme(R.style.BaseTheme);
        if (isUsingDarkTheme() && context instanceof IntroSplashScreenActivity)
            context.setTheme(R.style.NeonDarkThemeNoActionBar);
        if (!isUsingDarkTheme() && context instanceof IntroSplashScreenActivity)
            context.setTheme(R.style.BaseThemeNoActionBar);
    }

    /**
     * Applies dark background if dark mode is activated
     * Has to be called after setContentView in activity!!
     * @param activity
     */
    public void applyBackground(Activity activity) {
        if (isUsingDarkTheme())
            activity.getWindow()
                    .getDecorView()
                    .setBackgroundColor(
                            // Supports API level below 23
                            ResourcesCompat.getColor(
                                    getResources(),
                                    R.color.neonGrey,
                                    null
                            )
                    );
    }

    public SharedPreferences getSettings() {
        return settings;
    }
}

class KUberLifeCycle implements Application.ActivityLifecycleCallbacks {

    private final String LOGTAG = KUberLifeCycle.class.getSimpleName();
    private final KUberApplication context;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.d(LOGTAG, "Creating new activity: " + activity.getLocalClassName());
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.d(LOGTAG, "Resuming " + activity.getLocalClassName());
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.d(LOGTAG, "Paused " + activity.getLocalClassName());
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    /*
     * Constructor
     */
    public KUberLifeCycle(KUberApplication context) {
        this.context = context;
    }
}
