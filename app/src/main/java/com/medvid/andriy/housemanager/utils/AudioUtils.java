package com.medvid.andriy.housemanager.utils;

import android.media.AudioManager;
import android.media.ToneGenerator;

/**
 * Created by Андрій on 6/4/2015.
 */
public class AudioUtils {
    public static void startPipTone(int duration) {
        new ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME)
                .startTone(ToneGenerator.TONE_CDMA_PIP, duration);
    }
}
