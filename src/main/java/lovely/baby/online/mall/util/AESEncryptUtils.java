package lovely.baby.online.mall.util;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class AESEncryptUtils {

    private final SecretKey KEY = new SecretKeySpec(new byte[] { 90, -33, -85, -78, 39, -42, -74, -9, 113, 36, 107, -22,
            19, -42, -72, 89, -25, 40, -63, -121, 124, 38, -85, 116, 5, -107, -73, 96, -109, 18, -109, -110 }, "AES");

    private final IvParameterSpec IV = new IvParameterSpec(
            new byte[] { 111, 69, -104, 85, -15, -80, -99, 62, 16, 122, 82, 57, 77, 95, -66, 114 });

    private final byte VERSION = 1;

    public String encrypt(@NonNull String string, @NonNull Charset charset) {
        byte[] allBytes = null;
        try {
            byte[] raw = string.getBytes(charset); // 把要加密的客串以给定的字符集转成byte数组,后面要用
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); // 获取Cipher对象,实际用来加密的工具对象,参数是算法名,没有为什么,就长这样
            cipher.init(Cipher.ENCRYPT_MODE, KEY, IV); // 初始化Cipher对象
            byte[] encrypted = cipher.doFinal(raw); // 加密,传入的数组就是上面转换的结果,加密的结果也是以byte数组返回
            // 到这里真正的加密其实已经完事了,下面是对加密得到的结果再做一个组装,就是再加了个版本信息

            // add version for further extend
            // VERSION 1: byte[0] -> 0001**** (* is random data), byte[1-n] -> encryped data
            allBytes = new byte[encrypted.length + 1]; // 新建了一个数组用来存放最终的结果,可以看到就比上面的加密结果多了一个字节,附加的信息就是放在了这个字节里面
            allBytes[0] = (byte) ((VERSION << 4) + ((int) (Math.random() * 16) & 0xf)); // 构造附加信息,一个字段就是8个二进制位,前4位是版本信息,你看到那个移位操作了;后四位是一个随机数,生成过程是先生成一个[0,16)左闭右开范围内的整数,然后和0xf也就是15也就是1111作按位与运算,作用是把可能存在的低4位之前的都干成0(理论上不可能,因为16按二进制算最大也就是1111不可能到5位及以上的二进制数,哦这个地方可能是在把符号位清0)
            System.arraycopy(encrypted, 0, allBytes, 1, encrypted.length); // 把上面加密结果的那个数组放到最终结果数组里面来,从第2个元素开始放

            // convert to base64 string
            return Base64.getEncoder().encodeToString(allBytes); // 把最终结果数组用Base64加密,目的一是转成字符串,这个只是将要目的;主要目的是Base64的长度是可控的
        } catch (Exception e) {
            log.error("加密失败: string = {}, charset = {}, allBytes[0] = {}", string, charset, allBytes, e);
            throw new RuntimeException(e);
        }
    }

    public String decrypt(@NonNull String string, @NonNull Charset charset) {
        try {
            // decode from base64
            byte[] allBytes = Base64.getDecoder().decode(string); // Base64解密后拿到上面的最终结果数组

            // check version
            byte version = (byte) ((allBytes[0] & 0xf0) >>> 4); // 把第一个元素,就是上面说的存附加信息的那个元素取出来,然后和0xf0也就是240也就是11110000作按位与运算,这样就只会留下高4位,低4位就都是0了,就无符号右移4位,结果就是高4位的值,也就是上面的版本号
            if (version != VERSION) { // 加密版本号和解密版本号不一致
                throw new IllegalArgumentException("version " + version + " not supported");
            }
            byte[] raw = new byte[allBytes.length - 1];
            System.arraycopy(allBytes, 1, raw, 0, raw.length); // 把加密结果拿出来放到raw数组里面去

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); // 获取Cipher对象,实际用来加密的工具对象,参数是算法名,没有为什么,就长这样
            cipher.init(Cipher.DECRYPT_MODE, KEY, IV); // 初始化Cipher对象
            byte[] decrypted = cipher.doFinal(raw); // 解密

            return new String(decrypted, charset); // 用给定的字符集把解密得到的byte数组转成String,理论上应该和加密时用的字符集一致
        } catch (Exception e) {
            log.error("解密失败: string = {}, charset = {}", string, charset, e);
            throw new RuntimeException(e);
        }
    }

    private static byte[] generateRandomBytes(int length) {
        List<Byte> result = Lists.newArrayListWithCapacity(length);
        for (int i = 0; i < length; ++i) {
            result.add((byte) ((int) (Math.random() * 256) - 128)); // 这一行的目的的是为了获取一个[-128,127](也就是byte的取值范围)范围内的随机数:Math.random()的返回值得是[0,1)左闭右开区间的double,乘以256那就是[0,256)区间,取个整,其实就是[0,255]的整数,再减128就是[-128,127]
        }
        System.out.println(Joiner.on(", ").join(result));
        return ArrayUtils.toPrimitive(Iterables.toArray(result, Byte.class));
    }

    public static void main(String[] args) {
        generateRandomBytes(10);
    }
}
