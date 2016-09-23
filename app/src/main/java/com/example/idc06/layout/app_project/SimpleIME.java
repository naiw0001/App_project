package com.example.idc06.layout.app_project;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

/**
 * Created by idc06 on 2016-09-19.
 */

public class SimpleIME extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView kv;
    private Keyboard keyboard;

    private boolean caps = false;

    public final static int codeabc = 50000; // 쿼티 키보드로 돌아가는 변수
    public final static int codenum = 55000; // 숫자,기호 키보드로 가는 변수
    public final static int codenum2 = 55001;// 숫자,기호 키보드 2번쨰
    public final static int codenum3 = 55002;// 숫자,기호 키보드 3번쨰
    public final static int codenum4 = 55003;// 숫자,기호 키보드 1번쨰로 돌아가는 변수
    public final static int codekor = 55004;//한글로 돌아감


    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();
        playClick(primaryCode);
        switch (primaryCode) {
            case Keyboard.KEYCODE_DELETE:
                ic.deleteSurroundingText(1, 0);
                break;
            case Keyboard.KEYCODE_SHIFT:
                caps = !caps;
                keyboard.setShifted(caps);
                kv.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;
            case codenum: // 기본 키보드를 -> 숫자,기호 키보드로 바꿈
                keyboard = new Keyboard(this, R.xml.numkey);
                kv.setKeyboard(keyboard);
                break;
            case codenum2:
                keyboard = new Keyboard(this, R.xml.numkey2);
                kv.setKeyboard(keyboard);
                break;
            case codenum3:
                keyboard = new Keyboard(this, R.xml.numkey3);
                kv.setKeyboard(keyboard);
                break;
            case codenum4:
                keyboard = new Keyboard(this, R.xml.numkey);
                kv.setKeyboard(keyboard);
                break;
            case codeabc: // 쿼티 키보드로 돌아오기
                keyboard = new Keyboard(this, R.xml.qwerty);
                kv.setKeyboard(keyboard);
                break;
            case codekor: // 한글 쿼티 키보드
                keyboard = new Keyboard(this, R.xml.korean);
                kv.setKeyboard(keyboard);
                break;
            default:
                char code = (char) primaryCode;
                if (Character.isLetter(code) && caps) {
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code), 1);
        }
    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

    public void playClick(int keyCode) {
        AudioManager am = (AudioManager) getSystemService(AUDIO_SERVICE);
        switch (keyCode) {
            case 32:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case Keyboard.KEYCODE_DONE:
            case 10:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
            case Keyboard.KEYCODE_DELETE:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }
    }

    public View onCreateInputView() {
        kv = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboard = new Keyboard(this, R.xml.qwerty);
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        return kv;
    }


}
