package manuelmouta.clarifi_ai_app;

import android.support.v7.app.AppCompatActivity;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;

public class BaseActivity extends AppCompatActivity {

    public ClarifaiClient client = new ClarifaiBuilder("pHwSI_Y9di14R5M2e8JbAd0d9hwSJyOT8CjvaTzy", "WhB99eBQ5DQu5EYuqZ6opXwdXu_JXdhWL_XTNYy8").buildSync();

}
