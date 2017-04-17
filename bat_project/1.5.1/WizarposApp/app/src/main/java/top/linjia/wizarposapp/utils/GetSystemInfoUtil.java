package top.linjia.wizarposapp.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by 河南巧脉信息技术 on 2016/11/17 20:36
 * 作者：陈文豪
 * 邮箱：firstwenshao@163.com
 * <p/>
 * 获取系统信息的工具类
 */
public class GetSystemInfoUtil {

    /**
     * 调用这个方法返回ip
     *
     * @return ip4地址
     */
    public static String getAddressIpV4() {
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
                if (!ip.isLoopbackAddress() && ip instanceof Inet4Address) {
                    return ip.getHostAddress();
                }
            }
        }
        return null;
    }

    /**
     * 返回本机的sn号码
     *
     * @return SN号码
     */
    public static String getLocalSN() {
        return Build.SERIAL;
    }

    /**
     * @Title: getThisVersionNumber
     * @Description: 获取当前版本号
     * @param context
     * @return String
     * @date 2016/12/29 16:06
     * @author 陈文豪
     */
    public static String getThisVersionNumber(Context context) {
        String version = "";
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            version = info.versionName;
        } catch (Exception e) {
            Log.e("getThisVerson:","Get version fail");
        }
        return version;
    }
}
