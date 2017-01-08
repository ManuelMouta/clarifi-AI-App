package manuelmouta.clarifi_ai_app.services;

import android.content.Context;
import android.os.AsyncTask;

import clarifai2.dto.model.ConceptModel;
import manuelmouta.clarifi_ai_app.interfaces.TrainModelService;

/**
 * Created by manuelmouta on 07/01/17.
 */

public class ServiceTrainModel extends AsyncTask<String, Void, Boolean> {

    private ConceptModel model;

    private Context context;

    public TrainModelService serviceTrainModel = null;


    public ServiceTrainModel(ConceptModel model, Context context) {
        this.model = model;
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try{

            model.train().executeSync();

            return true;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if(result)
            serviceTrainModel.onModelTrained();
        else
            serviceTrainModel.onModelTrainedError();
    }
}
