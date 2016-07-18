package com.bg.async;

import java.util.List;

/**
 * Created by Administrator on 2016/7/18.
 */
public interface EventHandler {
    void doHandle(EventModel model);
    List<EventType> getSupportEventTypes();
}
