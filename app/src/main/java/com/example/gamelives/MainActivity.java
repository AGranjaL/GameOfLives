package com.example.gamelives;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import javax.security.auth.callback.Callback;

class MyApp{
    public MyApp(Context context, Callback callback){

    }
    void start(){

    }
    void stop(){

    }
}
public class MainActivity extends AppCompatActivity {
    //private static final String GAME_STATE_KEY = "Hello State";
    //private static final String TEXT_VIEW_KEY =  "Hello Text";
    TextView textView;
    //String gameState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button = findViewById(R.id.button_id);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button pressed");
            }
            // Code here executes on main thread after user presses button
        });
        /*if(savedInstanceState != null){
            gameState = savedInstanceState.getString(GAME_STATE_KEY);

        }
        setContentView(R.layout.activity_main);
        assert savedInstanceState != null;
        textView.setText(savedInstanceState.getString(TEXT_VIEW_KEY));
        */
    }

    /*@Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        textView.setText(savedInstanceState.getString(TEXT_VIEW_KEY));
    }

    // invoked when the activity may be temporarily destroyed, save the instance state here
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(GAME_STATE_KEY, gameState);
        outState.putString(TEXT_VIEW_KEY, (String) textView.getText());

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }*/
    @Override
    public void onStart(){
        super.onStart();

    }

}