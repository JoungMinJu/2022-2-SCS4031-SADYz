package com.example.android.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.R;
import com.example.android.dto.ChatBotAnswerDto;
import com.example.android.dto.ChatbotQuestionlDto;
import com.example.android.dto.ChatbotStartDto;
import com.example.android.dto.EmergencyDto;
import com.example.android.firebase.MyFirebaseMessagingService;
import com.example.android.receiver.AlramReceiver;
import com.example.android.retrofit.ChatbotRetrofit;
import com.example.android.retrofit.EmergencyRetrofit;
import com.example.android.retrofit.FlaskRestrofit;
import com.example.android.retrofit.SpringRetrofit;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private OutingActivity outingActivity;
    TextToSpeech tts;
    TextView text;
    Intent intent;
    Intent ttsIntent;
    SpeechRecognizer mRecognizer;
    Button sttBtn;
    Button meal;
    Button bathroom;
    Button emotion;
    Button emergencyButton;
    final int PERMISSION = 1;
    ChatbotQuestionlDto result;
    private List<int[]> alramtimes = new ArrayList<>(Arrays.asList(new int[]{1, 12, 00}, new int[]{2, 14, 30}, new int[]{3, 20, 00},
            new int[]{4, 10, 00}, new int[]{5, 15, 00}, new int[]{6, 21, 00}));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sttBtn = (Button) findViewById(R.id.audio);
        ttsIntent = getIntent();
        text = (TextView) findViewById(R.id.question);

        // tts
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.KOREA);

                    if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA) {
                        Log.e("TTS", "Language not supported.");
                    } else {
                    }
                } else {
                    Log.e("TTS", "Initialization failed.");
                }
                if (ttsIntent != null) {
                    text.setText(ttsIntent.getStringExtra("answer"));
                }
                CharSequence question = text.getText();
                tts.setPitch((float) 1.0);
                tts.setSpeechRate((float) 1.0);
                tts.speak(question, TextToSpeech.QUEUE_FLUSH, null, "uid");
            }
        });

        //모달창 화면
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        Intent outIntent = getIntent();
        String out = outIntent.getStringExtra("외출 여부 파악");
        if (out != null) {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(1500);
            outingActivity = new OutingActivity(this);
            outingActivity.setCancelable(false);
            outingActivity.show();
        }


        // stt 말하는 부분
        if (Build.VERSION.SDK_INT >= 23) {
            // 퍼미션 체크
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET,
                    Manifest.permission.RECORD_AUDIO}, PERMISSION);
        }

        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");

        sttBtn.setOnClickListener((View v) -> {
            mRecognizer = SpeechRecognizer.createSpeechRecognizer(MainActivity.this);
            mRecognizer.setRecognitionListener(listener);
            mRecognizer.startListening(intent);
        });

        //알림 만들기
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            Intent intent = new Intent(this, AlramReceiver.class);
            PendingIntent alarmIntent;
            // 버전체크
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            } else {
                alarmIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            }

            // 12시로 시간 설정
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 17);
            calendar.set(Calendar.MINUTE, 42);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
        }
        meal = (Button) findViewById(R.id.meal);
        bathroom = (Button) findViewById(R.id.bathroom);
        emotion = (Button) findViewById(R.id.emotion);

        meal.setOnClickListener((v) -> {
            FlaskRestrofit retrofit = FlaskRestrofit.getInstance();
            ChatbotRetrofit chatbotRetrofit = FlaskRestrofit.getChatbotRetrofit();

            ChatbotStartDto chatbotStartDto = new ChatbotStartDto("010-1212-1212");
            Gson gson = new Gson();
            String chatBotStartInfo = gson.toJson(chatbotStartDto);
            Log.e("JSON",chatBotStartInfo);

            Call<ChatbotQuestionlDto> call = chatbotRetrofit.startMealCommunication(chatbotStartDto);
            call.clone().enqueue(new Callback<ChatbotQuestionlDto>() {

                @Override
                public void onResponse(Call<ChatbotQuestionlDto> call, Response<ChatbotQuestionlDto> response) {
                    if (response.isSuccessful()){
                        Log.e("post", "성공");
                        result = response.body();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("answer", result.getAnswer());
                        intent.putExtra("dialog_type", result.getDialog_type());
                        intent.putExtra("conv_id", result.getConv_id());
                        startActivity(intent);
                        Log.e("answer", result.getAnswer());

                    }
                }

                @Override
                public void onFailure(Call<ChatbotQuestionlDto> call, Throwable t) {
                    Log.e("post", "실패");
                    t.printStackTrace();
                }
            });
        });

        emergencyButton = (Button) findViewById(R.id.emergencyCall);
        emergencyButton.setOnClickListener((v) ->{
            SpringRetrofit retrofit = SpringRetrofit.getInstance();
            EmergencyRetrofit emergencyRetrofit = SpringRetrofit.getEmergencyRetrofit();

            EmergencyDto emergencyDto = new EmergencyDto(true, "button");
            Gson gson = new Gson();
            String emergencyInfo = gson.toJson(emergencyDto);
            Log.e("JSON", emergencyInfo);

            Call<EmergencyDto> call = emergencyRetrofit.putEmergency("010-1111-1111", emergencyDto);
            call.clone().enqueue(new Callback<EmergencyDto>() {

                @Override
                public void onResponse(Call<EmergencyDto> call, Response<EmergencyDto> response) {
                    if (response.isSuccessful()){
                        Log.e("put", "성공");
                    }
                }

                @Override
                public void onFailure(Call<EmergencyDto> call, Throwable t) {
                    Log.e("put", "실패");
                    t.printStackTrace();
                }
            });
        });

        bathroom.setOnClickListener((v) -> {
            FlaskRestrofit retrofit = FlaskRestrofit.getInstance();
            ChatbotRetrofit chatbotRetrofit = FlaskRestrofit.getChatbotRetrofit();

            ChatbotStartDto chatbotStartDto = new ChatbotStartDto("010-1212-1212");
            Gson gson = new Gson();
            String chatBotStartInfo = gson.toJson(chatbotStartDto);
            Log.e("JSON",chatBotStartInfo);

            Call<ChatbotQuestionlDto> call = chatbotRetrofit.startBathroomCommunication(chatbotStartDto);
            call.clone().enqueue(new Callback<ChatbotQuestionlDto>() {

                @Override
                public void onResponse(Call<ChatbotQuestionlDto> call, Response<ChatbotQuestionlDto> response) {
                    if (response.isSuccessful()){
                        Log.e("post", "성공");
                        result = response.body();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("answer", result.getAnswer());
                        intent.putExtra("dialog_type", result.getDialog_type());
                        intent.putExtra("conv_id", result.getConv_id());
                        startActivity(intent);
                        Log.e("answer", result.getAnswer());

                    }
                }

                @Override
                public void onFailure(Call<ChatbotQuestionlDto> call, Throwable t) {
                    Log.e("post", "실패");
                    t.printStackTrace();
                }
            });
        });

        emotion.setOnClickListener((v) -> {
            FlaskRestrofit retrofit = FlaskRestrofit.getInstance();
            ChatbotRetrofit chatbotRetrofit = FlaskRestrofit.getChatbotRetrofit();

            ChatbotStartDto chatbotStartDto = new ChatbotStartDto("010-1212-1212");
            Gson gson = new Gson();
            String chatBotStartInfo = gson.toJson(chatbotStartDto);
            Log.e("JSON",chatBotStartInfo);

            Call<ChatbotQuestionlDto> call = chatbotRetrofit.startEmotionCommunication(chatbotStartDto);
            call.clone().enqueue(new Callback<ChatbotQuestionlDto>() {

                @Override
                public void onResponse(Call<ChatbotQuestionlDto> call, Response<ChatbotQuestionlDto> response) {
                    if (response.isSuccessful()){
                        Log.e("post", "성공");
                        result = response.body();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("answer", result.getAnswer());
                        intent.putExtra("dialog_type", result.getDialog_type());
                        intent.putExtra("conv_id", result.getConv_id());
                        startActivity(intent);
                        Log.e("answer", result.getAnswer());

                    }
                }

                @Override
                public void onFailure(Call<ChatbotQuestionlDto> call, Throwable t) {
                    Log.e("post", "실패");
                    t.printStackTrace();
                }
            });
        });

    }


    private RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {
            Toast.makeText(getApplicationContext(), "음성인식을 시작합니다.", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBeginningOfSpeech() {
        }

        @Override
        public void onRmsChanged(float rmsdB) {
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
        }

        @Override
        public void onEndOfSpeech() {
        }

        @Override
        public void onError(int error) {
            String message;

            switch (error) {
                case SpeechRecognizer.ERROR_AUDIO:
                    message = "오디오 에러";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message = "클라이언트 에러";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "퍼미션 없음";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "네트워크 에러";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "네트웍 타임아웃";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    message = "찾을 수 없음";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "RECOGNIZER가 바쁨";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "서버가 이상함";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "말하는 시간초과";
                    break;
                default:
                    message = "알 수 없는 오류임";
                    break;
            }

            Toast.makeText(getApplicationContext(), "에러가 발생하였습니다. : " + message, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResults(Bundle results) {
            // 말을 하면 ArrayList에 단어를 넣고 textView에 단어를 이어줍니다.
            ArrayList<String> matches =
                    results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            for (int i = 0; i < matches.size(); i++) {
                text.setText(matches.get(i));
            }

            FlaskRestrofit retrofit = FlaskRestrofit.getInstance();
            ChatbotRetrofit chatbotRetrofit = FlaskRestrofit.getChatbotRetrofit();

            ChatBotAnswerDto chatBotAnswerDto = new ChatBotAnswerDto(text.getText().toString(), "010-1212-1212", ttsIntent.getIntExtra("dialog_type", 0), ttsIntent.getIntExtra("conv_id", 0));
            Gson gson = new Gson();
            String chatBotStartInfo = gson.toJson(chatBotAnswerDto);
            Log.e("JSON", chatBotStartInfo);
            // 분기
            Call<ChatbotQuestionlDto> call = null;
            if(ttsIntent.getIntExtra("dialog_type",0) == 1) {
               call = chatbotRetrofit.emotionCommunication(chatBotAnswerDto);
            } else if (ttsIntent.getIntExtra("dialog_type",0) == 2){
                call = chatbotRetrofit.mealCommunication(chatBotAnswerDto);
            } else {
                call = chatbotRetrofit.bathroomCommunication(chatBotAnswerDto);
            }
            call.clone().enqueue(new Callback<ChatbotQuestionlDto>() {

                @Override
                public void onResponse(Call<ChatbotQuestionlDto> call, Response<ChatbotQuestionlDto> response) {
                    if (response.isSuccessful()) {
                        Log.e("post", "성공");
                        result = response.body();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("answer", result.getAnswer());
                        intent.putExtra("dialog_type", result.getDialog_type());
                        intent.putExtra("conv_id", result.getConv_id());
                        startActivity(intent);
                        Log.e("answer", result.getAnswer());

                    }
                }

                @Override
                public void onFailure(Call<ChatbotQuestionlDto> call, Throwable t) {
                    Log.e("post", "실패");
                    t.printStackTrace();
                }
            });
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
        }
    };

}