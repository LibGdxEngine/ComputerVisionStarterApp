package com.example.computervisionwithandroidcourse.MVC;

import android.content.Context;
import android.view.View;

public abstract class BaseViewMvc implements MvcView {

    View mRootView;

    @Override
    public View getRootView() {
        return this.mRootView;
    }

    protected <T extends View> T findViewById(int id) {
        return getRootView().findViewById(id);
    }

    protected Context getContext() {
        return getRootView().getContext();
    }

    protected void setRootView(View view) {
        this.mRootView = view;
    }
}
