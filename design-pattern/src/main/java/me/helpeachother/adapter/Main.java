package me.helpeachother.adapter;

public class Main {

    public static void main(String[] args) {
        HairDryer hairDryer = new HairDryer();
        connect(hairDryer);

        Cleaner cleaner = new Cleaner();
        Electronic110v adapter = new SocketAdapter(cleaner);
        connect(adapter);



    }

    public static void connect(Electronic110v electronic110v) {
        electronic110v.powerOn();
    }
}
