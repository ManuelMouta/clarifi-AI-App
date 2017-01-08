package manuelmouta.clarifi_ai_app.services;

import android.content.Context;
import android.os.AsyncTask;

import clarifai2.api.ClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.prediction.Concept;
import manuelmouta.clarifi_ai_app.interfaces.AddConceptService;

/**
 * Created by manuelmouta on 07/01/17.
 */

public class ServiceAddConcept extends AsyncTask<String, Void, Boolean> {

    public AddConceptService serviceAddConcept = null;

    private Context context;

    private ClarifaiClient client;

    private String concept;

    public ServiceAddConcept(Context ctx,ClarifaiClient client, String concept) {
        this.context = ctx;
        this.client = client;
        this.concept = concept;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            client.addConcepts()
                    .plus(
                            Concept.forID(concept)
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
            serviceAddConcept.onConceptAdded();
        else
            serviceAddConcept.onConceptAddedError();

    }
}
