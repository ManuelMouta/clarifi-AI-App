package manuelmouta.clarifi_ai_app.services;

import android.content.Context;
import android.os.AsyncTask;

import clarifai2.api.ClarifaiClient;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.output_info.ConceptOutputInfo;
import clarifai2.dto.prediction.Concept;
import manuelmouta.clarifi_ai_app.interfaces.AddConceptService;
import manuelmouta.clarifi_ai_app.interfaces.AddToModelService;

/**
 * Created by manuelmouta on 07/01/17.
 */

public class ServiceAddToModel extends AsyncTask<String, Void, Boolean> {

    public AddToModelService serviceAddModel = null;

    private Context context;

    private ClarifaiClient client;

    private String concept;

    public ServiceAddToModel(Context context, ClarifaiClient client, String concept) {
        this.context = context;
        this.client = client;
        this.concept = concept;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            final ConceptModel Model = client.createModel("pets")
                    .withOutputInfo(ConceptOutputInfo.forConcepts(
                            Concept.forID(concept)
                    ))
                    .executeSync()
                    .get();

            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}
