package com.example.llcgs.android_kotlin.material.douya.detail.view;

/**
 * com.example.llcgs.android_kotlin.material.detail.view.CustomTabsHelperFragment
 *
 * @author liulongchao
 * @since 2017/12/14
 */
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsSession;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;


import java.util.List;

/**
 * A Fragment that manages
 */
public class CustomTabsHelperFragment extends Fragment {

    private static final String FRAGMENT_TAG = CustomTabsHelperFragment.class.getName();

    private CustomTabsActivityHelper mCustomTabsActivityHelper = new CustomTabsActivityHelper();

    /**
     * Ensure that an instance of this fragment is attached to an activity.
     *
     * @param activity The target activity.
     * @return An instance of this fragment.
     */
    public static CustomTabsHelperFragment attachTo(FragmentActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        CustomTabsHelperFragment fragment = (CustomTabsHelperFragment) fragmentManager
                .findFragmentByTag(FRAGMENT_TAG);
        if (fragment == null) {
            fragment = new CustomTabsHelperFragment();
            fragmentManager.beginTransaction()
                    .add(fragment, FRAGMENT_TAG)
                    .commit();
        }
        return fragment;
    }

    /**
     * Ensure that an instance of this fragment is attached to the host activity of a fragment.
     *
     * @param fragment The target fragment, which will be used to retrieve the host activity.
     * @return An instance of this fragment.
     */
    public static CustomTabsHelperFragment attachTo(Fragment fragment) {
        return attachTo(fragment.getActivity());
    }

    // Cannot get javadoc to compile, saying "reference not found".
    /*
     * @see CustomTabsActivityHelper#openCustomTab(Activity, CustomTabsIntent, Uri, CustomTabsActivityHelper.CustomTabsFallback)
     */
    public static void open(Activity activity, CustomTabsIntent intent, Uri uri,
                            CustomTabsActivityHelper.CustomTabsFallback fallback) {
        try {
            CustomTabsActivityHelper.openCustomTab(activity, intent, uri, fallback);
        } catch (ActivityNotFoundException e) {
            fallback.openUri(activity, uri);
        }
    }

    /**
     * Get the {@link CustomTabsActivityHelper} this fragment manages.
     *
     * @return The {@link CustomTabsActivityHelper}.
     */
    public CustomTabsActivityHelper getHelper() {
        return mCustomTabsActivityHelper;
    }

    /**
     * @see CustomTabsActivityHelper#getSession()
     */
    public CustomTabsSession getSession() {
        return mCustomTabsActivityHelper.getSession();
    }

    /**
     * @see CustomTabsActivityHelper#setConnectionCallback(CustomTabsActivityHelper.ConnectionCallback)
     */
    public void setConnectionCallback(
            CustomTabsActivityHelper.ConnectionCallback connectionCallback) {
        mCustomTabsActivityHelper.setConnectionCallback(connectionCallback);
    }

    /**
     * @see CustomTabsActivityHelper#mayLaunchUrl(Uri, Bundle, List)
     */
    public boolean mayLaunchUrl(Uri uri, Bundle extras, List<Bundle> otherLikelyBundles) {
        return mCustomTabsActivityHelper.mayLaunchUrl(uri, extras, otherLikelyBundles);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        setUserVisibleHint(false);
    }

    @Override
    public void onStart() {
        super.onStart();

        mCustomTabsActivityHelper.bindCustomTabsService(getActivity());
    }

    @Override
    public void onStop() {
        super.onStop();

        mCustomTabsActivityHelper.unbindCustomTabsService(getActivity());
    }
}

