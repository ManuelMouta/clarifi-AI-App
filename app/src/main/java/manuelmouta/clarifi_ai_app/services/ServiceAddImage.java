package manuelmouta.clarifi_ai_app.services;

import android.content.Context;
import android.os.AsyncTask;

import java.io.File;

import clarifai2.api.ClarifaiClient;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.prediction.Concept;
import manuelmouta.clarifi_ai_app.interfaces.AddImageService;

/**
 * Created by manuelmouta on 07/01/17.
 */

public class ServiceAddImage extends AsyncTask<String, Void, Boolean> {

    public AddImageService serviceAddImage = null;

    private Context context;

    private String imagePath;

    private ClarifaiClient client;

    private String concept;

    public ServiceAddImage(Context context, String path,ClarifaiClient client,String concept) {
        this.context = context;
        this.imagePath = path;
        this.client = client;
        this.concept = concept;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        File imgFile = new File(imagePath);
        String imageRes = params[0];
        try {
            if(imageRes.equals("yes"))
            client.addInputs()
                    .plus(
                            ClarifaiInput.forImage(ClarifaiImage.of(imgFile))
                                    .withConcepts(
                                            Concept.forID(concept)
                                    )
                    )
                    .executeSync();
            else if(imageRes.equals("no"))
                client.addInputs()
                        .plus(
                                ClarifaiInput.forImage(ClarifaiImage.of(imgFile))
                                        .withConcepts(
                                                Concept.forID(concept).withValue(false)
                                        )
                        )
                        .executeSync();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if(result)
            serviceAddImage.onImageAdded();
        else
            serviceAddImage.onImageAddedError();
    }
}
