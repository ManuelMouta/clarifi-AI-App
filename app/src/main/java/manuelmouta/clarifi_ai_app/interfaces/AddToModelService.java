package manuelmouta.clarifi_ai_app.interfaces;

import clarifai2.dto.model.ConceptModel;

/**
 * Created by manuelmouta on 07/01/17.
 */

public interface AddToModelService {
    void onAddToModel(ConceptModel model);
    void onAddToModelError();
}
