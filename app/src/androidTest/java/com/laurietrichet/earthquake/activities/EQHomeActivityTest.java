package com.laurietrichet.earthquake.activities;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import com.google.android.apps.common.testing.ui.espresso.IdlingResource;
import com.laurietrichet.earthquake.R;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.registerIdlingResources;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isDisplayed;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.common.base.Preconditions.checkNotNull;

public class EQHomeActivityTest extends ActivityInstrumentationTestCase2<EQHomeActivity> {
    private final static String IDLING_RESOURCE_NAME = "GET_EQ_CALL";
    private EQHomeActivity mActivity;

    public EQHomeActivityTest() {
        super(EQHomeActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = (EQHomeActivity) getActivity();
    }

    public void testGetData (){
        EQHomeIdlingResource idlingResource = new EQHomeIdlingResource(IDLING_RESOURCE_NAME);
        registerIdlingResources(idlingResource);

        onView(withId (R.id.list_fragment)).check(matches(isDisplayed()));
 //       onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())));
    }

    private class EQHomeIdlingResource implements IdlingResource {

        private volatile ResourceCallback mResourceCallback;
        private final String mResourceName;

        public EQHomeIdlingResource (String resourceName){
            mResourceName =  resourceName;
        }

        @Override
        public String getName() {
            return mResourceName;
        }

        @Override
        public boolean isIdleNow() {
            checkNotNull (mActivity);
            if (mActivity.getProgressBar().getVisibility() == View.VISIBLE){
                mResourceCallback.onTransitionToIdle();
                return true;
            }
            return false;
        }

        @Override
        public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
            mResourceCallback = resourceCallback;
        }
    }

}