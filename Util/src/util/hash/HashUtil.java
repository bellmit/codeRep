/**
 * user info center(uic)
 * tyread.com Inc.
 * Copyright (c) 天翼阅读文化传播有限公司  All Rights Reserved.
 */
package util.hash;

/**
 * @author <a href="mailto:zhuhao.567@163.com.">朱豪</a>
 * 2014年12月25日下午6:00:36
 * @version 1.0
 */


public class HashUtil {
    public static int hashCode(byte[] data, int offset, int len, int seed) {
        final int c1 = 0xcc9e2d51;
        final int c2 = 0x1b873593;
        int h1 = seed;
        int roundedEnd = offset + (len & 0xfffffffc); // round down to 4 byte block
        for (int i = offset; i < roundedEnd; i += 4) {
            // little endian load order
            int k1 = (data[i] & 0xff) | ((data[i + 1] & 0xff) << 8) | ((data[i + 2] & 0xff) << 16)
                    | (data[i + 3] << 24);
            k1 *= c1;
            k1 = (k1 << 15) | (k1 >>> 17); // ROTL32(k1,15);
            k1 *= c2;
            h1 ^= k1;
            h1 = (h1 << 13) | (h1 >>> 19); // ROTL32(h1,13);
            h1 = h1 * 5 + 0xe6546b64;
        }
        // tail
        int k1 = 0;
        switch (len & 0x03) {
            case 3:
                k1 = (data[roundedEnd + 2] & 0xff) << 16;
                // fallthrough
            case 2:
                k1 |= (data[roundedEnd + 1] & 0xff) << 8;
                // fallthrough
            case 1:
                k1 |= (data[roundedEnd] & 0xff);
                k1 *= c1;
                k1 = (k1 << 15) | (k1 >>> 17); // ROTL32(k1,15);
                k1 *= c2;
                h1 ^= k1;
        }
        // finalization
        h1 ^= len;
        // fmix(h1);
        h1 ^= h1 >>> 16;
        h1 *= 0x85ebca6b;
        h1 ^= h1 >>> 13;
        h1 *= 0xc2b2ae35;
        h1 ^= h1 >>> 16;
        return h1;
    }

    public static int hashCode(String str) {
        byte[] data = str.getBytes();
        return hashCode(data, 0, data.length, 2342);
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            hashCode("aff faecd4sfafafafdvdzfvsgej;owt4;otijgmvczl/das;fj;sajf;safjsadf;lafjalksfj");
            hashCode("adfd2434 faecdrsfafafafdvdzfvsgej;owt4;otijgmvczl/das;fj;sajf;safjsadf;lafjalksfj");
            hashCode("ds2434 fasf;osssssssssswt4;otiddddddddddddddddddddddddddddddddddddddjgmvczl/das;fj;sajf;safjsadf;lafjalksfj");
            hashCode("2ffss434 faeasfacdsfafafafdvdzfdddddvsgej;owt4;otijgmvczl/das;fj;sajf;safjsadf;lafjalksfj");
            hashCode("4352fsdf434 faeafacdsfafafafdvdzfvsgej;owt4;otijgmvczl/das;fj;sajf;safjsadf;lafjalksfj");
            hashCode("1514243afdsafaf4 faecdsfafafafdvdzfvsgej;owt4;otijgmvczl/das;fj;sajf;safjsadf;lafjalksfj");
            hashCode("t2434 faec3dsfafafafdvdzfvsgej;owt4;otijgmvczl/das;fj;sajf;safjsadf;lafjalksfj");
            hashCode("f25435434 fa3ecdffsfafafafdvdzfvsgej;owt4;otijgmvczl/das;fj;sajf;safjsadf;lafjalksfj");
            hashCode("df452434 q11faecdaafsfafafafdvdzfvsgej;owt4;otijgmvczl/das;fj;sajf;safjsadf;lafjalksfj");
            hashCode("a2434 faecdsfafafafdvdzfvsgej;owt4;otijgmvczl/das;fj;sajf;safjsadf;lafjalksfj");
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);
        System.out.println(hashCode("2aaa434a dsffaecfdsfafafafdvdzfvsgej;owt4;otijgmvczl/das;fj;sajf;safjsadf;lafjalksfj"));
        int test = hashCode("2aaa434a dsffaecfdsfafafafdvdzfvsgej;owt4;otijgmvczl/das;fj;sajf;safjsadf;lafjalksfj");
        test = test < 0 ? (-1)*test : test;
        System.out.println(test);
        System.out.println(hashCode("Aa"));
        String s = String.valueOf(hashCode("Aa"));
        System.out.println(s.substring(s.length()-7, s.length()));
        System.out.println(hashCode("BB"));
        System.out.println(hashCode("BB"));
        System.out.println("Aa".hashCode() + "," + "BB".hashCode());
        System.out.println(("Ba").hashCode() + "," + ("CB").hashCode());
    }

}

