package com.example.computervisionwithandroidcourse.MVC;

public interface ObservableViewMvc<ListenerType> extends MvcView {

    void registerListener(ListenerType listenerType);

    void unregisterListener(ListenerType listenerType);


}
