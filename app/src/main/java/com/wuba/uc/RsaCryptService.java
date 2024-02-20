package com.wuba.uc;

public class RsaCryptService {
    static {
        System.loadLibrary("com_wuba_uc_rsa");
    }

    public static native byte[] encrypt(byte[] bArr, int i);
}
