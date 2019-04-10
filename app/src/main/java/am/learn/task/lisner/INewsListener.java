package am.learn.task.lisner;

import android.view.View;

public interface INewsListener {
    void newsItemClick(int position, View imgView);
    void newsRemove(int position);
}
