package com.app.pubsub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Receiver class listen message from the topic <b>chat* pattern</b>
 */
public class Receiver {
    private static final Logger LOG = LoggerFactory.getLogger(Receiver.class);

    // subscriber of the message
    public void receiveMessage(String message) {
        LOG.info("Received <" + message + ">");
    }

}
