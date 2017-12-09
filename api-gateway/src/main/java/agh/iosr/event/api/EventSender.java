package agh.iosr.event.api;

import agh.iosr.event.model.EventMessage;

public interface EventSender {

    void sendEvent(EventMessage message);
}
