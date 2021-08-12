package com.example.pratik.speechrecognition;

import android.Manifest;
import android.annotation.SuppressLint;
//import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
//import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    //EditText editText;
    //SpeechRecognizer mSpeechRecognizer;
    //Intent mSpeechRecognizerIntent;
    TextToSpeech t1;
    //Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();

        final EditText editText = findViewById(R.id.editText);
        final SpeechRecognizer mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        final Intent mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                //getting all the matches
                ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                //displaying the first match
                if (matches != null)
                    editText.setText(matches.get(0));

            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Timer timer = new Timer();
                timer.schedule(new TimerTask(){
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run(){
                        String  str = editText.getText().toString();
                        if(str.equals("hello")){
                            editText.setText("Hello, how can i help you");
                            t1.speak("Hello, how can i help you", TextToSpeech.QUEUE_FLUSH,null,null);
                        }
                        if(str.equals("what is your name")){
                            editText.setText("My name is jarvis sir");
                            t1.speak("My name is jarvis sir", TextToSpeech.QUEUE_FLUSH,null,null);
                        }
                        if(str.equals("how are you")){
                            editText.setText("I m a robot, i am never tired, sir");
                            t1.speak("I m a robot, i am never tired, sir", TextToSpeech.QUEUE_FLUSH,null,null);
                        }
                        if(str.equals("what can I ask you")){
                            editText.setText("you can ask me anything to help you");
                            t1.speak("you can ask me anything to help you", TextToSpeech.QUEUE_FLUSH,null,null);
                        }
                        if(str.equals("tell me a joke")){
                            editText.setText("two sheep said baa in the field, other one said shit i wanna say that");
                            t1.speak("two sheep said baa in the field, other one said shit i wanna say that", TextToSpeech.QUEUE_FLUSH,null,null);
                        }
                        if(str.equals("do you like Trump")){
                            editText.setText("i will never consider him as a role model");
                            t1.speak("i will never consider him as a role model", TextToSpeech.QUEUE_FLUSH,null,null);
                        }
                        if(str.equals("what is the situation")){
                            editText.setText("there is an ambush, retreat sir");
                            t1.speak("there is an ambush, retreat sir", TextToSpeech.QUEUE_FLUSH,null,null);
                        }
                        //t1.speak(str, TextToSpeech.QUEUE_FLUSH,null,null);
                    }
                },1000);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status!=TextToSpeech.ERROR){
                    t1.setLanguage(Locale.US);
                }
            }
        });

        findViewById(R.id.btn).setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_UP:
                        mSpeechRecognizer.stopListening();
                        editText.setHint("you will see input here");
                        break;

                    case MotionEvent.ACTION_DOWN:
                        editText.setText("");
                        editText.setHint("listening........");
                        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                        break;
                }

                return false;
            }
        });


    }

    private void checkPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)){
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                finish();
            }
        }
    }


}
