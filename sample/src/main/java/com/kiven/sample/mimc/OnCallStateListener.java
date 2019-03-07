package com.kiven.sample.mimc;

import com.xiaomi.mimc.data.RtsDataType;

/**
 * Created by houminjiang on 18-5-24.
 */

public interface OnCallStateListener {
    void onLaunched(String fromAccount, String fromResource, Long chatId, byte[] data);
    void onAnswered(Long chatId, boolean accepted, String errMsg);
    void handleData(Long chatId, RtsDataType dataType, byte[] data);
    void onClosed(Long chatId, String errMsg);
}
