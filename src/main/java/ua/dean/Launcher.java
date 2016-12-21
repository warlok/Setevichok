package ua.dean;


import org.zeroturnaround.exec.ProcessExecutor;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class Launcher {

    private static String TEST_IP1 = "8.8.8.8";
    private static String TEST_IP2 = "8.8.4.4";
    private static String TEST_DOMAIN1 = "google.com";
    private static String TEST_DOMAIN2 = "wnet.ua";

    public static void main(String[] args) throws InterruptedException, TimeoutException, IOException {
        String routingTable = new ProcessExecutor().command("netstat", "-rn").readOutput(true).execute().outputUTF8();
        String ipconfig = new ProcessExecutor().command("ipconfig", "/all").readOutput(true).execute().outputUTF8();
        String interfaces = new ProcessExecutor().command("netsh", "int", "ipv4", "show", "interfaces").readOutput(true)
                .execute()
                .outputUTF8();
        String pingResultIp1 = new ProcessExecutor().command("ping", TEST_IP1).readOutput(true).execute().outputUTF8();
        String pingResultIp2 = new ProcessExecutor().command("ping", TEST_IP2).readOutput(true).execute().outputUTF8();
        String nslookupHost1 = new ProcessExecutor().command("nslookup", TEST_DOMAIN1).readOutput(true).execute()
                .outputUTF8();
        String nslookupHost2 = new ProcessExecutor().command("ping", TEST_DOMAIN2).readOutput(true).execute()
                .outputUTF8();
        List<String> dns = new LinkedList<>();
        List<String> gw = new LinkedList<>();

    }

}
