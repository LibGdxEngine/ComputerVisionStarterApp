package com.example.computervisionwithandroidcourse.MVC;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class BaseObservableMvcView<ListenerType> extends BaseViewMvc
        implements ObservableViewMvc<ListenerType> {

    private Set<ListenerType> mListeners = new HashSet<>();


    @Override
    public void registerListener(ListenerType listenerType) {
        mListeners.add(listenerType);
    }

    @Override
    public void unregisterListener(ListenerType listenerType) {
        mListeners.remove(listenerType);
    }


    protected Set<ListenerType> getmListeners() {
        return Collections.unmodifiableSet(mListeners);
    }
}
