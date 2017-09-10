package com.uofk.school.klaam.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.uofk.school.klaam.R;
import com.uofk.school.klaam.services.ApiService;
import com.uofk.school.klaam.services.SignLanguageApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView mVoiceInputTv;
    private SignLanguageApi signLanguageApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ApiService apiService = new ApiService();
        signLanguageApi = apiService.getSignLanguageApi();

        setContentView(R.layout.activity_main);
        mVoiceInputTv = (TextView) findViewById(R.id.voiceInput);
        ImageButton micSpeakBtn = (ImageButton) findViewById(R.id.btnSpeak);
        micSpeakBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startVoiceInput();
            }
        });
    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar-SA");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "العربية");

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);

            Toast toast = Toast.makeText(getApplicationContext(),
                    "Recording...",
                    Toast.LENGTH_SHORT);
            toast.show();
        } catch (ActivityNotFoundException a) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Oops! Your device doesn't support Speech to Text",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && data != null) {
                    String results = RecognizerIntent.EXTRA_RESULTS;
                    signLanguageApi.search(results).enqueue(new Callback<List<String>>() {
                        @Override
                        public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
                            List<String> instructions = response.body();
                            mVoiceInputTv.setText(android.text.TextUtils.join(",", instructions));
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {

                        }
                    });
                }
                break;
            }

        }
    }
}
