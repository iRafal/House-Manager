package com.medvid.andriy.housemanager.voice_recognition;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.medvid.andriy.housemanager.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;

import static edu.cmu.pocketsphinx.SpeechRecognizerSetup.defaultSetup;

/**
 * Created by Андрій on 6/3/2015.
 */
public class RecognitionManager implements RecognitionListener {

    private static final String TAG = "recognitionManager.tag";
    private static final String DEVICES_COMMANDS_SEARCH = "devices_commands";
    private static final String ACOUSTIC_MODEL = "en-us-ptm";
    private static final String DICTIONARY_FILE_NAME = "cmudict-en-us.dict";

    //Context Independent phonetic search
    private static final String PHONETIC_SEARCH_TYPE = "-allphone_ci";
    private static final String GRAMMAR_NAME = "commands";
    private static final String GRAMMAR_FILE_NAME = "voice_commands.gram";
    private static final float KEYPHRASE_THRESHOLD_TUNE = 1e-45f;

    private boolean mIsRecognitionPaused = false;

    private String mRecognizedString = null;
    private OnCommandRecognizedListener mRecognitionListener = null;

    private boolean mAllowDebugLogs = true;

    private SpeechRecognizer recognizer;
    private Context mContext = null;
    private List<String> mCommandsList = null;

    private AsyncTask<Void, Void, Exception> mVoiceRecognizerInitialize
            = new AsyncTask<Void, Void, Exception>() {
        @Override
        protected Exception doInBackground(Void... params) {
            try {
                Assets assets = new Assets(mContext);
                File assetDir = assets.syncAssets();
                setupRecognizer(assetDir);
            } catch (IOException e) {
                return e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Exception result) {
            if (result != null) {
                showLog("Failed to init recognizer " + result);
            } else {
                showLog("Recognizer init done" + result);
            }
            mRecognitionListener.onInitializationFinished();
        }
    };

    public RecognitionManager(Context context, List<String> commandsList) {
        mContext = context;
        mCommandsList = new ArrayList<>(commandsList);
        initRecognition();
    }

    public RecognitionManager(Context context,
                              List<String> commandsList,
                              OnCommandRecognizedListener recognitionListener) {
        mContext = context;
        mCommandsList = new ArrayList<>(commandsList);
        this.mRecognitionListener = recognitionListener;
        initRecognition();
    }

    private void setupRecognizer(File assetsDir) throws IOException {
        recognizer = defaultSetup()
                .setAcousticModel(new File(assetsDir, ACOUSTIC_MODEL))
                .setDictionary(new File(assetsDir, DICTIONARY_FILE_NAME))

                        // To disable logging of raw audio comment out this call
                        // (takes a lot of space on the device)
                        // .setRawLogDir(assetsDir)

                        // Threshold to tune for keyphrase to balance between false alarms and misses
                .setKeywordThreshold(KEYPHRASE_THRESHOLD_TUNE)
                        // Use context-independent phonetic search, context-dependent is too slow for mobile
                .setBoolean(PHONETIC_SEARCH_TYPE, true)

                .getRecognizer();
        recognizer.addListener(this);


        // Create Commands grammar-based search
        File menuGrammar = new File(assetsDir, GRAMMAR_FILE_NAME);
        FileUtils.writeGrammarToFile(GRAMMAR_NAME, menuGrammar, mCommandsList);

        recognizer.addGrammarSearch(DEVICES_COMMANDS_SEARCH, menuGrammar);
    }

    @Override
    public void onBeginningOfSpeech() {
        showLog("onBeginningOfSpeech");
    }

    @Override
    public void onPartialResult(Hypothesis hypothesis) {
        showLog("onPartialResult");

        if (hypothesis == null)
            return;

        String text = hypothesis.getHypstr();

        if(mCommandsList.contains(text)) {
            mRecognitionListener.onCommandRecognized(text);
            mRecognizedString = text;
        }
    }

    @Override
    public void onEndOfSpeech() {
        showLog("onEndOfSpeech");

        if (mRecognitionListener != null) {
            if(mRecognizedString!=null && mCommandsList.contains(mRecognizedString)) {

                mRecognitionListener.onCommandRecognized(mRecognizedString);
                pauseRecognition();
                mRecognitionListener.onRecognitionFinished(mRecognizedString);
            }
        }
        showLog("recognized text = " + mRecognizedString);
    }

    @Override
    public void onResult(Hypothesis hypothesis) {
        showLog("onResult");
    }

    @Override
    public void onError(Exception e) {
        showLog("onError = " + e);
    }

    @Override
    public void onTimeout() {
    }

    private void initRecognition() {
        mVoiceRecognizerInitialize.execute();
    }

    public void startRecognition()  {
        recognizer.startListening(DEVICES_COMMANDS_SEARCH);
        mIsRecognitionPaused = false;
    }

    public void pauseRecognition()  {
        if(!mIsRecognitionPaused) {
            recognizer.stop();
            mIsRecognitionPaused = true;
        }
    }

    public void stopRecognition() {
        recognizer.cancel();
        recognizer.shutdown();
        mRecognizedString = null;
    }

    public void restartRecognition() {
        pauseRecognition();
        if(mIsRecognitionPaused)  {
            mIsRecognitionPaused = false;
        }
        startRecognition();
    }

    private void showLog(String message) {
        if (mAllowDebugLogs) {
            Log.d(TAG, message);
        }
    }

    public List<String> getCommandsList() {
        return mCommandsList;
    }

    public void setCommandsList(List<String> сommandsList) {
        this.mCommandsList = сommandsList;
    }

    public void setRecognitionListener(OnCommandRecognizedListener recognitionListener) {
        this.mRecognitionListener = recognitionListener;
    }

    public interface OnCommandRecognizedListener {
        public void onInitializationFinished();
        public void onCommandRecognized(String command);
        public void onRecognitionFinished(String command);
    }

    public boolean isVoiceRecognitionPaused()  {
        return mIsRecognitionPaused;
    }
}
