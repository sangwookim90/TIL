package me.helpeachother.singleton;

public class Main {

    public static void main(String[] args) {
        AClazz aClazz = new AClazz();
        BClazz bClass = new BClazz();

        SocketClient aClient = aClazz.getSocketClient();
        SocketClient bClient = bClass.getSocketClient();

        System.out.println(aClient.equals(bClient));
        // aClient == bClient (동일한 객체이다.)
    }
}
