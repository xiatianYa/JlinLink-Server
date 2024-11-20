package com.jinlink;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
class JinLinkServerApplicationTests {

    @Test
    void contextLoads() {
        try {
            InetAddress address = InetAddress.getByName("202.189.15.61");
            DatagramSocket socket = new DatagramSocket();
            // 构造挑战请求
            byte[] challengeRequest = new byte[9];
            challengeRequest[8] = 0x55; // 标头 'U'

            DatagramPacket packet = new DatagramPacket(challengeRequest, challengeRequest.length, address, 27001);
            socket.send(packet);

            // 接收挑战响应
            byte[] buffer = new byte[9];
            DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(responsePacket);

            if (buffer[8] == 0x41) { // 检查是否是挑战响应 'A'
                int challengeNumber = ((buffer[4] & 0xFF) << 24) | ((buffer[5] & 0xFF) << 16) | ((buffer[6] & 0xFF) << 8) | (buffer[7] & 0xFF);
                System.out.println("Challenge Number: " + Integer.toHexString(challengeNumber));

                // 现在你可以使用这个挑战号来发送A2S_PLAYER请求
            } else {
                System.out.println("Invalid response received.");
            }

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
