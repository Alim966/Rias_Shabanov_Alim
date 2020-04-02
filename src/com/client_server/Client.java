package com.client_server;

import java.io.*;
import java.util.*;
import java.net.*;

public class Client { // Главный класс

    private static int port = 6404;
    private static String host = "127.0.0.1";
    private static Socket cs;

    public static void main(String[] args) {

        try {
            cs = new Socket(host, port); //соединение
            new InputFromServer();
            new OutputToServer();
        } catch (IOException ex) {
        }
    }

    private static class InputFromServer extends Thread { // Подкласс, который принимиает информацию от сервера

        private DataInputStream input;

        public InputFromServer() {
            try {
                input = new DataInputStream(cs.getInputStream());
                start(); //метод run()
            } catch (IOException ex) {
            }
        }

        public void run() {
            try {
                while (true) {
                    String data = input.readUTF();
                    if (data != null) {
                        receiveText(data);
                    }
                }
            } catch (IOException ex) {
            }
        }

        private void receiveText(String text) {
            System.out.println(text);
        }

    }

    private static class OutputToServer extends Thread { // Подкласс, который отправляет информацию серверу

        private DataOutputStream output;
        private Scanner scan;

        public OutputToServer() {
            try {
                output = new DataOutputStream(cs.getOutputStream());
                scan = new Scanner(System.in);//считывает ввод с клавиатуры
                start();
            } catch (IOException ex) {
            }
        }

        public void run() {
            while (true) {
                String data = scan.nextLine();
                if (data != null) {
                        sendText(data);
                }
            }
        }

        private void sendText(String text) {
            try {
                output.writeUTF(text);
            } catch (IOException ex) {
            }
        }

    }
}