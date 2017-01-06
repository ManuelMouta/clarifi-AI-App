package manuelmouta.clarifi_ai_app;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;

/**
 * Created by manuelmouta on 05/01/17.
 */

public class LandingPageActivity extends BaseActivity{

    //Buttons
    private Button takePicBtn;

    private TextView predictionValue;

    static final int REQUEST_TAKE_PHOTO = 0;

    //photo utils
    private String mCurrentPhotoPath;
    private String mtimeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);

        takePicBtn = (Button) findViewById(R.id.takePicBtn);

        predictionValue = (TextView) findViewById(R.id.prediction);

        takePicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if(requestCode == REQUEST_TAKE_PHOTO && resultCode != RESULT_OK){
            try{
                File file = new File(mCurrentPhotoPath);
                if (file.exists()) {
                    file.delete();
                }
            }catch(Exception e){
                Log.e("ERROR: ",e.toString());
            }
        }
        else if(requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK){

            new PredictResultsTask().execute(mCurrentPhotoPath);
            
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        mtimeStamp = new SimpleDateFormat("dd-MM-yy").format(new Date());
        String imageFileName = "Clarifi   " + mtimeStamp + "   ";

        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES) +
                File.separator + "Clarifi");
        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdir();
        }
        if (success) {
            // Do something on success
        } else {
            // Do something else on failure
        }

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    class PredictResultsTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... args) {
            try {
                File file = new File(args[0]);
                final List<ClarifaiOutput<Concept>> predictionResults =
                        client.getDefaultModels().generalModel() // You can also do client.getModelByID("id") to get custom models
                                .predict()
                                .withInputs(
                                        ClarifaiInput.forImage(ClarifaiImage.of(file))
                                )
                                .executeSync()
                                .get();

                return predictionResults.get(0).data().get(0).name();

            } catch (Exception e) {
                return "";
            }
        }

        protected void onPostExecute(String result) {
            predictionValue.setText("Great... You're watching a "+result+"...");
        }
    }
}
