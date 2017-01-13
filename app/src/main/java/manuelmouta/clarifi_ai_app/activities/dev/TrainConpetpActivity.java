package manuelmouta.clarifi_ai_app.activities.dev;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import clarifai2.dto.model.ConceptModel;
import manuelmouta.clarifi_ai_app.R;
import manuelmouta.clarifi_ai_app.activities.BaseActivity;
import manuelmouta.clarifi_ai_app.interfaces.AddImageService;
import manuelmouta.clarifi_ai_app.interfaces.AddToModelService;
import manuelmouta.clarifi_ai_app.interfaces.TrainModelService;
import manuelmouta.clarifi_ai_app.services.ServiceAddImage;
import manuelmouta.clarifi_ai_app.services.ServiceAddToModel;
import manuelmouta.clarifi_ai_app.services.ServiceTrainModel;

/**
 * Created by manuelmouta on 08/01/17.
 */

public class TrainConpetpActivity extends BaseActivity implements
        AddImageService,AddToModelService,TrainModelService{

    //services
    //add image service
    private ServiceAddImage addImageParser;
    private AddImageService service1 = this;
    //add image service
    private ServiceAddToModel addToModelParser;
    private AddToModelService service2 = this;
    //train model service
    private ServiceTrainModel trainModelParser;
    private TrainModelService service3 = this;

    //buttons
    private Button isCorrectBtn;
    private Button isIncorrectBtn;

    private String imagePath;

    private TextView conceptName;

    private Context mCtx;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_page);

        mCtx = this;

        imagePath = getIntent().getStringExtra("photoPath");

        isCorrectBtn = (Button) findViewById(R.id.right);
        isIncorrectBtn = (Button) findViewById(R.id.wrong);

        conceptName = (TextView) findViewById(R.id.concept);

        isCorrectBtn.setOnClickListener(new View.OnClickListener() {
            String contept = conceptName.getText().toString();
            @Override
            public void onClick(View v) {
                addImageParser = new ServiceAddImage(mCtx,imagePath,client,contept);
                addImageParser.serviceAddImage = service1;
                addImageParser.execute("yes");
            }
        });

        isIncorrectBtn.setOnClickListener(new View.OnClickListener() {
            String contept = conceptName.getText().toString();
            @Override
            public void onClick(View v) {
                addImageParser = new ServiceAddImage(mCtx,imagePath,client,contept);
                addImageParser.serviceAddImage = service1;
                addImageParser.execute("no");
            }
        });

    }

    @Override
    public void onImageAdded() {
        String concept = conceptName.getText().toString();

        addToModelParser = new ServiceAddToModel(mCtx,client,concept);
        addToModelParser.serviceAddModel = service2;
        addToModelParser.execute();
    }

    @Override
    public void onImageAddedError() {
        Toast toast = Toast.makeText(mCtx, "Failed to Add Image", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onAddToModel(ConceptModel model) {
        trainModelParser = new ServiceTrainModel(model,mCtx);
        trainModelParser.serviceTrainModel = service3;
        trainModelParser.execute();
    }

    @Override
    public void onAddToModelError() {
        Toast toast = Toast.makeText(mCtx, "Failed Adding to Model", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onModelTrained() {
        finish();
    }

    @Override
    public void onModelTrainedError() {
        Toast toast = Toast.makeText(mCtx, "Failed training Model", Toast.LENGTH_SHORT);
        toast.show();
    }
}
