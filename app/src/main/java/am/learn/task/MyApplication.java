package am.learn.task;
import android.app.Application;
import am.learn.task.database.RealmManager;
import am.learn.task.server.RetrofitClient;
import am.learn.task.utils.PhoneUtil;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PhoneUtil.getInstance();
        RetrofitClient.getInstance();
        RealmManager.getInstance().init(this);

    }
}

