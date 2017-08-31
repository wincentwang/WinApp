package wang.wincent.winapp.oss.test;

import org.apache.shiro.crypto.hash.Sha256Hash;

/**
 * Created by user on 2017-8-31.
 */
public class Test {


    public static void main(String[] args) {
        System.out.println(new Sha256Hash("admin").toHex());
    }
}
