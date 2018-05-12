package com.app.pubsub.impl;

import com.app.pubsub.Receiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageReceiver implements Receiver {

    private static final Logger LOG = LoggerFactory.getLogger(MessageReceiver.class);

    // subscriber of the message
    @Override
    public void receiveMessage(String message) {
        LOG.info("\nReceived: {}" , message );
    }
}
