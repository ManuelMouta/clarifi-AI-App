package manuelmouta.clarifi_ai_app.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;

public class BaseActivity extends AppCompatActivity {

    public ClarifaiClient client = new ClarifaiBuilder("pHwSI_Y9di14R5M2e8JbAd0d9hwSJyOT8CjvaTzy", "WhB99eBQ5DQu5EYuqZ6opXwdXu_JXdhWL_XTNYy8").buildSync();

    public final int MY_PERMISSIONS_REQUEST_CAMERA = 1;

    public void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(BaseActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(BaseActivity.this,
                    Manifest.permission.CAMERA)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(BaseActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }
}
