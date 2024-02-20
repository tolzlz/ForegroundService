package com.wuba.uc;

import android.content.Context;

public class RsaCryptService {
    static {
        System.loadLibrary("com_wuba_uc_rsa");
    }

    public static native byte[] encrypt(byte[] bArr, int i);
    public static native void init(Context context);
}
