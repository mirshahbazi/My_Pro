package top.linjia.wizarposapp.utils;

import android.os.Build;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by 河南巧脉信息技术 on 2016/11/17 20:36
 * 作者：陈文豪
 * 邮箱：firstwenshao@163.com
 *
 * 获取系统信息的工具类
 */
public class GetSystemInfoUtil {

    /**
     * 调用这个方法返回ip
     * @return ip4地址
     */
    public static String getAddressIpV4(){
        Enumeration<NetworkInterface> netInterfaces = null;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        while (netInterfaces.hasMoreElements()) {
            NetworkInterface nif = netInterfaces.nextElement();
            Enumeration<InetAddress> iparray = nif.getInetAddresses();
            while (iparray.hasMoreElements()) {
                InetAddress ip = iparray.nextElement();
                if(!ip.isLoopbackAddress()&& ip instanceof Inet4Address) {
                    return  ip.getHostAddress();
                }
            }
        }
        return null;
    }

    /**
     * 返回本机的sn号码
     * @return SN号码
     */
    public static String getLocalSN(){
        return Build.SERIAL;
    }
}
