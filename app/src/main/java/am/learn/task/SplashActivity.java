package am.learn.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.github.loadingview.LoadingView;
import am.learn.task.activity.HomeActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {
    private SplashActivity splashActivity;
    @BindView(R.id.splash_loader)
    LoadingView splashLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        splashLoader.start();
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                 goHomeActivity();
                }
            }
        };
        timer.start();
    }

    private void goHomeActivity() {
        Intent i = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(i);
    }
}
