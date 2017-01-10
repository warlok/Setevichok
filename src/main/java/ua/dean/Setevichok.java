package ua.dean;


import org.apache.commons.lang3.StringUtils;
import org.zeroturnaround.exec.ProcessExecutor;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;

public class Setevichok {

    public static String TEST_IP1 = "8.8.8.23";
    public static String TEST_IP2 = "8.8.4.4";
    public static String TEST_DOMAIN1 = "google.com";
    public static String TEST_DOMAIN2 = "wnet.ua";

    public boolean nsLookupIsSuccess(String domain) throws InterruptedException, TimeoutException,
            IOException {
        String pingResult = new ProcessExecutor().command("ping", "-n", "1", domain).readOutput(true).execute()
                .outputUTF8();
        if ( pingResult.split("\r\n").length == 1 ) return false;
        return true;
    }

    public boolean pingIsSuccess(String ipAddress) throws InterruptedException, TimeoutException, IOException {
        String pingResult = new ProcessExecutor().command("ping", ipAddress).readOutput(true).execute().outputUTF8();
        if ( pingResult.split("\r\n").length == 11 ) return true;
        return false;
    }

    public List<String> getGateways() throws InterruptedException, TimeoutException, IOException {
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

    public Set<String> getDnsServers() throws InterruptedException, TimeoutException, IOException {
        String dnsServers = new ProcessExecutor().command("netsh", "int", "ipv4","show","dnsservers").readOutput(true)
                .execute().outputUTF8();
        Set<String> dnss = new HashSet<>();
        Scanner scanner = new Scanner(dnsServers);
        scanner.useDelimiter("\n");
        while (scanner.hasNext()) {
            String str = scanner.next();
            if (str.contains("DNS servers configured through DHCP")) {
                Scanner stringScaner = new Scanner(str);
                IntStream.range(0, 5).forEach(i -> stringScaner.next());
                stringScaner.useDelimiter("\r");
                dnss.add(stringScaner.next().trim());
                str = scanner.next();
                if (str.contains("Register")) continue;
                else {
                    Scanner scane = new Scanner(str);
                    dnss.add(scane.next().trim());
                }
            }
            if (str.contains("Statically Configured DNS Servers")) {
                Scanner stringScaner = new Scanner(str);
                IntStream.range(0, 4).forEach(i -> stringScaner.next());
                stringScaner.useDelimiter("\r");
                dnss.add(stringScaner.next().trim());
                str = scanner.next();
                if (str.contains("Register")) continue;
                else {
                    Scanner scane = new Scanner(str);
                    dnss.add(scane.next().trim());
                }
            }
        }
        dnss.remove("None");
        return dnss;
    }

    public List<String> getUpInterfaces() throws InterruptedException, TimeoutException, IOException {
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

        Iterator<String> iterator = ifaces.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().contains("Loopback")) {
                iterator.remove();
            }
        }
        return ifaces;
    }

}
