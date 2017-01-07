package manuelmouta.clarifi_ai_app.services;

import android.content.Context;
import android.os.AsyncTask;

import clarifai2.api.ClarifaiClient;
import clarifai2.dto.prediction.Concept;
import manuelmouta.clarifi_ai_app.interfaces.addConceptService;

/**
 * Created by manuelmouta on 07/01/17.
 */

public class ServiceAddConcept extends AsyncTask<String, Void, Boolean> {

    public addConceptService serviceAddConcept = null;

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
        if(client.getConceptByID(concept)==null){
            client.addConcepts()
                    .plus(
                            Concept.forID(concept)
                    )
                    .executeSync();
            return true;
        }else{
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
