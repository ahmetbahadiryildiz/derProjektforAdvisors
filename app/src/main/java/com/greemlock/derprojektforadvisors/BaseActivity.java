package com.greemlock.derprojektforadvisors;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import com.sendbird.calls.AcceptParams;
import com.sendbird.calls.AuthenticateParams;
import com.sendbird.calls.CallOptions;
import com.sendbird.calls.DialParams;
import com.sendbird.calls.DirectCall;
import com.sendbird.calls.RoomInvitation;
import com.sendbird.calls.SendBirdCall;
import com.sendbird.calls.SendBirdException;
import com.sendbird.calls.SendBirdVideoView;
import com.sendbird.calls.User;
import com.sendbird.calls.handler.AuthenticateHandler;
import com.sendbird.calls.handler.CompletionHandler;
import com.sendbird.calls.handler.DialHandler;
import com.sendbird.calls.handler.DirectCallListener;
import com.sendbird.calls.handler.SendBirdCallListener;

import java.util.UUID;

public class BaseActivity extends AppCompatActivity {
    private FirebaseUser firebaseUser;
    private AuthenticateParams params;

    private static final String ACCESS_TOKEN = "ddd833db640dcec7ddb8b262eba602456c78313d";
    private static final String APP_ID = "9119DD5D-6D7D-43DB-8D3A-381D9508E58D";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        authenticateUser();

        Button callbutton = findViewById(R.id.button);
        callbutton.setOnClickListener(view -> {
            DialParams paramsCall = new DialParams("ebO1jYLhoBa4VJx3pNIkoXrBkNz1");
            paramsCall.setVideoCall(true);
            paramsCall.setCallOptions(new CallOptions());

            DirectCall call = SendBirdCall.dial(paramsCall, new DialHandler() {
                @Override
                public void onResult(DirectCall call, SendBirdException e) {
                    if (e != null) {
                        Toast.makeText(BaseActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(BaseActivity.this, "331", Toast.LENGTH_SHORT).show();

                    }
                }
            });

            call.setListener(new DirectCallListener() {
                @Override
                public void onEstablished(DirectCall call) {
                }

                @Override
                public void onConnected(DirectCall call) {
                    Toast.makeText(BaseActivity.this, "33133", Toast.LENGTH_SHORT).show();
                    call.startVideo();
                    SendBirdVideoView sendBirdVideoView = findViewById(R.id.video_view_fullscreen);
                    call.setLocalVideoView(sendBirdVideoView);



                    Button button3 = findViewById(R.id.button3);
                    button3.setOnClickListener(view ->{
                        call.end();
                    });

                }

                @Override
                public void onEnded(DirectCall call) {
                }
            });
        });

        ImageView buttonLogout = findViewById(R.id.imageViewLogout);
        buttonLogout.setOnClickListener(view -> {
            finish();
        });


    }

    @Override
    protected void onResume() {
        super.onResume();



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SendBirdCall.deauthenticate( new CompletionHandler() {
            @Override
            public void onResult(SendBirdException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(),"111331",Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                }
            }
        });

    }

    private void authenticateUser() {

        SendBirdCall.init(
                getApplicationContext(), APP_ID);
        // The USER_ID below should be
        // unique to your Sendbird application.
        params = new AuthenticateParams(firebaseUser.getUid());

        // Authenticate user
        SendBirdCall.authenticate(
                params, new AuthenticateHandler() {
                    @Override
                    public void onResult(User user, SendBirdException e) {
                        if (e != null) {
                            // Invalid user!
                        } else {
                            // User is valid!
                            waitForCalls();
                        }
                    }
                });
    }

    private void waitForCalls() {

        SendBirdCall.removeAllListeners();
        SendBirdCall.addListener("1234567", new SendBirdCallListener() {


            @Override
            public void onInvitationReceived(@NonNull RoomInvitation roomInvitation) {

            }

            @Override
            public void onRinging(DirectCall call) {
                Log.e("11331133","OK");

                call.setListener(new DirectCallListener() {
                    @Override
                    public void onEstablished(DirectCall call) {


                    }

                    @Override
                    public void onConnected(DirectCall call) {
                        Log.i("anan", "Call established");
                    }

                    @Override
                    public void onEnded(DirectCall call) {
                    }

                    @Override
                    public void onRemoteAudioSettingsChanged(DirectCall call) {
                    }
                });

                call.accept(new AcceptParams());
                firebaseToken();


            }
        });
        /** You can define your own sounds for your calls
         *
         SendBirdCall.Options.addDirectCallSound(SendBirdCall.SoundType.DIALING, R.raw.dialing);
         SendBirdCall.Options.addDirectCallSound(SendBirdCall.SoundType.RINGING, R.raw.ringing);
         SendBirdCall.Options.addDirectCallSound(SendBirdCall.SoundType.RECONNECTING, R.raw.reconnecting);
         SendBirdCall.Options.addDirectCallSound(SendBirdCall.SoundType.RECONNECTED, R.raw.reconnected);
         */
        firebaseToken();
    }

    private void firebaseToken() {
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(BaseActivity.this, new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String token) {
                Log.i("FCM Token", token);
                SendBirdCall.registerPushToken(token, false, e -> {
                    if (e != null) {
                        Log.i("TAG",
                                "[PushUtils] registerPushToken() => e: " + e.getMessage());
                    }
                });
            }
        });
    }
}