package com.github.misterchangra.appmonitor.base.util;

import com.github.misterchangra.appmonitor.base.intf.UDPHandler;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer extends Thread {

        private DatagramSocket socket;
        private boolean running;
        private byte[] buf = new byte[1024];
        private UDPHandler udpHandler;

        public UDPServer(int port, UDPHandler handler) {
            try {
                socket = new DatagramSocket(port);
                udpHandler = handler;
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            running = true;

            while (running) {
                try {
                    DatagramPacket packet
                        = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);

                    byte[] data = packet.getData();
                    this.udpHandler.handle(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            socket.close();
        }
}
