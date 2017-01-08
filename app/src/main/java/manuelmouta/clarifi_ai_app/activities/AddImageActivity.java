package manuelmouta.clarifi_ai_app.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;

import clarifai2.dto.model.ConceptModel;
import manuelmouta.clarifi_ai_app.R;
import manuelmouta.clarifi_ai_app.interfaces.AddImageService;
import manuelmouta.clarifi_ai_app.interfaces.AddToModelService;
import manuelmouta.clarifi_ai_app.interfaces.TrainModelService;
import manuelmouta.clarifi_ai_app.services.ServiceAddConcept;
import manuelmouta.clarifi_ai_app.interfaces.AddConceptService;
import manuelmouta.clarifi_ai_app.services.ServiceAddImage;
import manuelmouta.clarifi_ai_app.services.ServiceAddToModel;
import manuelmouta.clarifi_ai_app.services.ServiceTrainModel;

/**
 * Created by manuelmouta on 07/01/17.
 */

public class AddImageActivity extends BaseActivity implements AddConceptService,
        AddImageService,AddToModelService,TrainModelService {

    //services
    //add concept service
    private ServiceAddConcept addConceptParser;
    private AddConceptService service = this;
    //add image service
    private ServiceAddImage addImageParser;
    private AddImageService service1 = this;
    //add image service
    private ServiceAddToModel addToModelParser;
    private AddToModelService service2 = this;
    //train model service
    private ServiceTrainModel trainModelParser;
    private TrainModelService service3 = this;

    private ImageView photo;

    private Button addImageBtn;

    private EditText conceptName;

    private Context mCtx;

    private String imagePath;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_image);

        imagePath = getIntent().getStringExtra("photoPath");

        mCtx = this;

        photo = (ImageView) findViewById(R.id.photoTaken);

        addImageBtn = (Button) findViewById(R.id.addImageBtn);

        conceptName = (EditText) findViewById(R.id.conceptName);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addImageToCategory();
            }
        });

        showPhoto(imagePath);
    }

    private void addImageToCategory(){
        String concept = conceptName.getText().toString();

        addConceptParser = new ServiceAddConcept(mCtx,client,concept);
        addConceptParser.serviceAddConcept = service;
        addConceptParser.execute();
    }

    private void showPhoto(String path){
        File imgFile = new File(path);

        if (imgFile.exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile
                    .getAbsolutePath());

            photo.setImageBitmap(myBitmap);
        }
    }

    @Override
    public void onConceptAdded() {
        String concept = conceptName.getText().toString();

        addImageParser = new ServiceAddImage(mCtx,imagePath,client,concept);
        addImageParser.serviceAddImage = service1;
        progressBar.setVisibility(View.VISIBLE);
        addImageParser.execute();
    }

    @Override
    public void onConceptAddedError() {
        Toast toast = Toast.makeText(mCtx, "Failed to Add Concept", Toast.LENGTH_SHORT);
        toast.show();
        progressBar.setVisibility(View.GONE);
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
        progressBar.setVisibility(View.GONE);
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
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onModelTrained() {
        progressBar.setVisibility(View.GONE);
        finish();
    }

    @Override
    public void onModelTrainedError() {
        Toast toast = Toast.makeText(mCtx, "Failed training Model", Toast.LENGTH_SHORT);
        toast.show();
        progressBar.setVisibility(View.GONE);
    }
}
