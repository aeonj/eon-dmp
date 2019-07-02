package eon.hg.fap.common.ip;

import eon.hg.fap.common.CommUtil;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;

/**
 * Created by aeon on 2018/1/12.
 */
public class ListNets {

    public static void main(String args[]) throws SocketException, UnknownHostException {
//        String ip = "192.168.3.212";
//        String mac = "D8-CB-8A-04-51-10";
//        boolean flag = validatoIpAndMacAddress(ip, mac);
//        boolean macflag = validateMacAddress( mac);
//        System.out.printf("validatoMacAddress flag=%s\n", macflag);
//        System.out.printf("validatoIpAndMacAddress flag=%s\n", flag);
        getLocalMacAddress("localhost");
    }

    public static String getLocalMacAddress(String ipaddress) throws UnknownHostException, SocketException {
        InetAddress ia = InetAddress.getByName(ipaddress);
        String macaddress = getLocalMacAddress(NetworkInterface.getByInetAddress(ia));
        return macaddress;
    }

    public static String getLocalMacAddress() throws UnknownHostException, SocketException {
        Enumeration<NetworkInterface> nets = NetworkInterface
                .getNetworkInterfaces();
        for (NetworkInterface netint : Collections.list(nets)) {
            String macaddress = getLocalMacAddress(netint);
            if (CommUtil.isEmpty(macaddress)) {
                continue;
            } else {
                return macaddress;
            }
        }

        return "";
    }

    public static String getLocalMacAddress(NetworkInterface netint) throws UnknownHostException, SocketException {
        StringBuilder sb = new StringBuilder();

        byte[] mac = netint.getHardwareAddress();
        if (mac != null) {
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i],
                        (i < mac.length - 1) ? "-" : ""));
            }
            System.out.println("mac=" + sb.toString());
            return sb.toString();
        }

        return sb.toString();
    }

    static void displayInterfaceInformation(NetworkInterface netint)
            throws SocketException {
        System.out.printf("Display name: %s\n", netint.getDisplayName());
        System.out.printf("Name: %s\n", netint.getName());
        byte[] mac = netint.getHardwareAddress();
        if (mac != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i],
                        (i < mac.length - 1) ? "-" : ""));
            }
            System.out.println("mac=" + sb.toString());
        }

        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
            System.out.printf("InetAddress: %s\n", inetAddress);
            System.out
                    .println("InetAddress ip=" + inetAddress.getHostAddress());
        }
        System.out.printf("\n");
    }

    public static boolean validateMacAddress(String macAddress)
            throws SocketException {
        boolean returnFlag = false;
        Enumeration<NetworkInterface> nets = NetworkInterface
                .getNetworkInterfaces();
        for (NetworkInterface netint : Collections.list(nets)) {
            byte[] mac = netint.getHardwareAddress();
            StringBuilder sb = new StringBuilder();
            if (mac != null) {
                for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X%s", mac[i],
                            (i < mac.length - 1) ? "-" : ""));
                }
                System.out.println("mac=" + sb.toString());
            }
            if (sb.toString().equals(macAddress)) {
                returnFlag = true;
            }
        }
        return returnFlag;

    }

    public static boolean validatoIpAndMacAddress(String ipAddress,
                                                  String macAddress) throws SocketException {
        boolean returnFlag = false;
        Enumeration<NetworkInterface> nets = NetworkInterface
                .getNetworkInterfaces();
        for (NetworkInterface netint : Collections.list(nets)) {
            byte[] mac = netint.getHardwareAddress();
            StringBuilder sb = new StringBuilder();
            if (mac != null) {
                for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X%s", mac[i],
                            (i < mac.length - 1) ? "-" : ""));
                }
                System.out.println("mac=" + sb.toString());
            }
            if (sb.toString().equals(macAddress)) {
                Enumeration<InetAddress> inetAddresses = netint
                        .getInetAddresses();
                String ip = "";
                for (InetAddress inetAddress : Collections.list(inetAddresses)) {
                    ip = inetAddress.getHostAddress();
                    System.out.println("InetAddress ip="
                            + inetAddress.getHostAddress());
                    if (ipAddress.toString().equals(ip)) {
                        returnFlag = true;
                    }
                }
            }
        }
        return returnFlag;

    }
}
