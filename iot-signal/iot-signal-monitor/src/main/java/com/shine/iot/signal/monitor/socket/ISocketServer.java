package com.shine.iot.signal.monitor.socket;

import java.io.IOException;
import java.net.DatagramPacket;

public interface ISocketServer {

    public void receiveData(DatagramPacket data) throws IOException;


    public void sendData(DatagramPacket sendData) throws IOException;

}
