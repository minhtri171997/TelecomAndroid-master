package com.github.dentou;

import com.github.dentou.interfaces.IOChannelListener;

import java.util.LinkedList;
import java.util.Queue;

public class IOChannel {

    /* Singleton */
    private IOChannel() {}

    private static class IOChannelHolder {
        private static final IOChannel INSTANCE = new IOChannel();
    }

    public static IOChannel getInstance() {
        return IOChannelHolder.INSTANCE;
    }
    /*************/


    /* Processing */
    private IOChannelListener ioChannelListener = null;
    private Queue<String> inputQueue = new LinkedList<>();

    public void registerIoChannelListener(IOChannelListener _ioChannelListener) {
        ioChannelListener = _ioChannelListener;
    }

    public void unRegisterIoChannelListener() {
        ioChannelListener = null;
    }

    public boolean input(String inputString) {
        inputString += "\r\n";
        return inputQueue.add(inputString);
    }


    public String getInput() {
        if (inputQueue.size() == 0) {
            return "none";
        }
        return inputQueue.remove();
    }

    public int getInputQueueSize() {
        return inputQueue.size();
    }

    public boolean output(String outputString) {
        if (ioChannelListener == null) {
            return false;
        }
        ioChannelListener.receive(outputString);
        return true;
    }
    /**************/
}
