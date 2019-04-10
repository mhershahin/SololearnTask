package am.learn.task.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import am.learn.task.lisner.INewsListener;
import am.learn.task.lisner.IReloadListener;
import am.learn.task.lisner.ISectionListener;


public abstract class AbstractCard extends RecyclerView.ViewHolder {
    private Context context;

    public AbstractCard(View itemView) {
        super(itemView);
    }

    public AbstractCard(View cardView, Context context) {
        super(cardView);
        this.context = context;

    }

    public abstract void bind(Object data);

    private INewsListener iNewsListener;
    public void setINewsListener(INewsListener iNewsListener) {
        this.iNewsListener = iNewsListener;
    }
    protected void newsItemClick(int pos, View imgView) {
        if (iNewsListener != null) {
            iNewsListener.newsItemClick(pos,imgView);
        }
    }

    protected void newsRemove(int pos) {
        if (iNewsListener != null) {
            iNewsListener.newsRemove(pos);
        }
    }
    private ISectionListener iSectionListener;

    public void setISectionListener(ISectionListener iSectionListener) {
        this.iSectionListener = iSectionListener;
    }
    protected void sectionOnClick(int pos) {
        if (iSectionListener != null) {
            iSectionListener.sectionOnClick(pos);
        }
    }
private IReloadListener iReloadListener;

    public void setIReloadListener(IReloadListener iReloadListener) {
        this.iReloadListener = iReloadListener;
    }

    protected void reloadData(){
        if (iReloadListener != null) {
            iReloadListener.reloadData();
        }
    }
}
