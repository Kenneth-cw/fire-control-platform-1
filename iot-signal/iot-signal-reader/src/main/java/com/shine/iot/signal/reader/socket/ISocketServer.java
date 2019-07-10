package com.shine.iot.signal.reader.socket;

import java.io.IOException;
import java.net.DatagramPacket;

public interface ISocketServer {

    public void receiveData(DatagramPacket data) throws IOException;


    public void sendData(DatagramPacket sendData) throws IOException;

}
