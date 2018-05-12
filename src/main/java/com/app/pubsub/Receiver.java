package com.app.pubsub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Receiver class listen message from the topic <b>chat* pattern</b>
 */
public interface Receiver {

    void receiveMessage(String message);

}
