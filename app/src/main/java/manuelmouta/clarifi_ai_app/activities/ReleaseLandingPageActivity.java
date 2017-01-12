package manuelmouta.clarifi_ai_app.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import manuelmouta.clarifi_ai_app.R;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.util.ViewAnimator;

/**
 * Created by manuelmouta on 09/01/17.
 */

public class ReleaseLandingPageActivity extends BaseActivity implements ViewAnimator.ViewAnimatorListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_page);
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        return null;
    }

    @Override
    public void disableHomeButton() {

    }

    @Override
    public void enableHomeButton() {

    }

    @Override
    public void addViewToContainer(View view) {

    }
}
