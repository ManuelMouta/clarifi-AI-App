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

import java.io.File;

import manuelmouta.clarifi_ai_app.R;
import manuelmouta.clarifi_ai_app.interfaces.addImageService;
import manuelmouta.clarifi_ai_app.services.ServiceAddConcept;
import manuelmouta.clarifi_ai_app.interfaces.addConceptService;
import manuelmouta.clarifi_ai_app.services.ServiceAddImage;

/**
 * Created by manuelmouta on 07/01/17.
 */

public class AddImageActivity extends BaseActivity implements addConceptService,addImageService {

    //services
    //add concept service
    private ServiceAddConcept addConceptParser;
    private addConceptService service = this;
    //add image service
    private ServiceAddImage addImageParser;
    private addImageService service1 = this;

    private ImageView photo;

    private Button addImageBtn;

    private EditText conceptName;

    private Context mCtx;

    private String imagePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_image);

        imagePath = getIntent().getStringExtra("photoPath");

        mCtx = this;

        photo = (ImageView) findViewById(R.id.photoTaken);

        addImageBtn = (Button) findViewById(R.id.addImageBtn);

        conceptName = (EditText) findViewById(R.id.conceptName);

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
    }

    @Override
    public void onConceptAddedError() {

    }

    @Override
    public void onImageAdded() {

    }

    @Override
    public void onImageAddedError() {

    }
}
