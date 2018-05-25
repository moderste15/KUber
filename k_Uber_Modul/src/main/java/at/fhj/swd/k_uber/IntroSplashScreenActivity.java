package at.fhj.swd.k_uber;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class IntroSplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // remove actionbar from splash screen
        ((KUberApplication) getApplicationContext()).applyTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_splash_screen);
        ((KUberApplication) getApplicationContext()).applyBackground(this);


        // Move to main
        new Handler().postDelayed(new DelayedMain(), 1000);
    }

    class DelayedMain implements Runnable {
        @Override
        public void run() {
            Intent intent = new Intent(IntroSplashScreenActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
