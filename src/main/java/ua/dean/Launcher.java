package ua.dean;


import org.apache.commons.lang3.StringUtils;
import org.zeroturnaround.exec.ProcessExecutor;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Launcher {

    private static String TEST_IP1 = "8.8.8.23";
    private static String TEST_IP2 = "8.8.4.4";
    private static String TEST_DOMAIN1 = "google.com";
    private static String TEST_DOMAIN2 = "wnet.ua";

    public static void main(String[] args) throws InterruptedException, TimeoutException, IOException {

//        String nslookupHost1 = new ProcessExecutor().command("nslookup", TEST_DOMAIN1).readOutput(true).execute()
//                .outputUTF8();
//        String nslookupHost2 = new ProcessExecutor().command("ping", TEST_DOMAIN2).readOutput(true).execute()
//                .outputUTF8();

        System.out.println(getDnsServers());

    }

    private static boolean pingIsSuccess(String ipAddress) throws InterruptedException, TimeoutException, IOException {
        String pingResult = new ProcessExecutor().command("ping", ipAddress).readOutput(true).execute().outputUTF8();
        if ( pingResult.split("\r\n").length == 11 ) return true;
        return false;
    }

    private static List<String> getGateways() throws InterruptedException, TimeoutException, IOException {
        String routingTable = new ProcessExecutor().command("netstat", "-rn").readOutput(true).execute()
                .outputUTF8();
        List<String> gw = new LinkedList<>();
        Scanner scanner = new Scanner(routingTable);
        scanner.useDelimiter("\n");
        while (scanner.hasNext()) {
            String str = scanner.next();
            if (StringUtils.countMatches(str, "0.0.0.0") == 2) {
                Scanner stringScaner = new Scanner(str);
                IntStream.range(0, 2).forEach(i -> stringScaner.next());
                gw.add(stringScaner.next().trim());
            }
        }
        return gw;
    }

    private static List<String> getDnsServers() throws InterruptedException, TimeoutException, IOException {
        String dnsServers = new ProcessExecutor().command("netsh", "int", "ipv4","show","dnsservers").readOutput(true)
                .execute().outputUTF8();
        List<String> dnss = new LinkedList<>();
        Scanner scanner = new Scanner(dnsServers);
        scanner.useDelimiter("\n");
        while (scanner.hasNext()) {
            String str = scanner.next();
            if (str.contains("DNS servers configured through DHCP") || str.contains("Statically Configured DNS " +
                    "Servers")) {
                Scanner stringScaner = new Scanner(str);
                IntStream.range(0, 4).forEach(i -> stringScaner.next());
                stringScaner.useDelimiter("\r");
                dnss.add(stringScaner.next().trim());
            }
        }
        return dnss;
    }

    private static List<String> getUpInterfaces() throws InterruptedException, TimeoutException, IOException {
        String interfaces = new ProcessExecutor().command("netsh", "int", "ipv4", "show", "interfaces").readOutput(true)
                .execute().outputUTF8();
        List<String> ifaces = new LinkedList<>();
        Scanner scanner = new Scanner(interfaces);
        scanner.useDelimiter("\n");
        while (scanner.hasNext()) {
            String str = scanner.next();
            if (str.contains("connected")) {
                Scanner stringScaner = new Scanner(str);
                IntStream.range(0, 4).forEach(i -> stringScaner.next());
                stringScaner.useDelimiter("\r");
                ifaces.add(stringScaner.next().trim());
            }
        }
        return ifaces;
    }

}
