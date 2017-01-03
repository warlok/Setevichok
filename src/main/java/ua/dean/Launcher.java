package ua.dean;


import javafx.application.Platform;
import org.zeroturnaround.exec.ProcessExecutor;
import ua.dean.ui.MainApp;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Launcher {

    private static String TEST_IP1 = "8.8.8.8";
    private static String TEST_IP2 = "8.8.4.4";
    private static String TEST_DOMAIN1 = "google.com";
    private static String TEST_DOMAIN2 = "wnet.ua";

    public static void main(String[] args) throws InterruptedException, TimeoutException, IOException {


        String routingTable = new ProcessExecutor().command("netstat", "-rn").readOutput(true).execute().outputUTF8();
//        String ipconfig = new ProcessExecutor().command("ipconfig", "/all").readOutput(true).execute().outputUTF8();
//        String interfaces = new ProcessExecutor().command("netsh", "int", "ipv4", "show", "interfaces").readOutput(true)
//                .execute()
//                .outputUTF8();
//        String pingResultIp1 = new ProcessExecutor().command("ping", TEST_IP1).readOutput(true).execute().outputUTF8();
//        String pingResultIp2 = new ProcessExecutor().command("ping", TEST_IP2).readOutput(true).execute().outputUTF8();
//        String nslookupHost1 = new ProcessExecutor().command("nslookup", TEST_DOMAIN1).readOutput(true).execute()
//                .outputUTF8();
//        String nslookupHost2 = new ProcessExecutor().command("ping", TEST_DOMAIN2).readOutput(true).execute()
//                .outputUTF8();

        Scanner scanner = new Scanner(routingTable);
        Pattern defaultDelimiter = scanner.delimiter();
        System.err.println(defaultDelimiter);
        scanner.useDelimiter("\n");
        while (scanner.hasNext()) {
            String str = scanner.next();
            if (str.contains("IPv4 Route Table")) {
                IntStream.range(0,2).forEach( i -> scanner.next());
                scanner.useDelimiter(defaultDelimiter);
            }
            System.out.println(scanner.next());
        }
        List<String> dns = new LinkedList<>();
        List<String> gw = new LinkedList<>();


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                MainApp.main(new String[]{});
//            }
//            }).start();
//        for (int i = 0; i < 100000; i++) {
//            System.out.println(i);
//        }
//        MainApp.text.set("aaaaaaaaasds");
//        for (int i = 0; i < 100000; i++) {
//            System.out.println(i);
//        }
//        MainApp.text.set("dskljnDNFLSCVdncksdNcKL");
//        for (int i = 0; i < 100000; i++) {
//            System.out.println("");
//        }
//        MainApp.text.set("FFFFFFFFUUUUUUUUUUUfffffffffuuuuuuuu");


    }

}
