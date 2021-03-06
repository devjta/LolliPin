package com.github.omadahealth.lollipin.lib.managers;

import android.content.Context;

import com.github.omadahealth.lollipin.lib.PinActivity;
import com.github.omadahealth.lollipin.lib.PinCompatActivity;
import com.github.omadahealth.lollipin.lib.PinFragmentActivity;

/**
 * Allows to handle the {@link AppLock} from within
 * the actual app calling the library.
 * You must get this static instance by calling {@link #getInstance()}
 */
public class LockCompatManager<T extends AppLockCompatActivity> {

    /**
     * The static singleton instance
     */
    private static LockCompatManager mInstance;
    /**
     * The static singleton instance of {@link AppLock}
     */
    private static AppLock mAppLocker;

    /**
     * Used to retrieve the static instance
     */
    public static LockCompatManager getInstance() {
        synchronized (LockCompatManager.class) {
            if (mInstance == null) {
                mInstance = new LockCompatManager<>();
            }
        }
        return mInstance;
    }

    /**
     * You must call that into your custom {@link android.app.Application} to enable the
     * {@link PinActivity}
     */
    public void enableAppLock(Context context, Class<T> activityClass) {
        if (mAppLocker != null) {
            mAppLocker.disable();
        }
        mAppLocker = AppLockCompatImpl.getInstance(context, activityClass);
        mAppLocker.enable();
    }

    /**
     * Tells the app if the {@link AppLock} is enabled or not
     */
    public boolean isAppLockEnabled() {
        return (mAppLocker != null && (PinActivity.hasListeners() ||
                PinFragmentActivity.hasListeners() || PinCompatActivity.hasListeners()));
    }

    /**
     * Disables the app lock by calling {@link AppLock#disable()}
     */
    public void disableAppLock() {
        if (mAppLocker != null) {
            mAppLocker.disable();
        }
        mAppLocker = null;
    }

    /**
     * Disables the previous app lock and set a new one
     */
    public void setAppLock(AppLock appLocker) {
        if (mAppLocker != null) {
            mAppLocker.disable();
        }
        mAppLocker = appLocker;
    }

    /**
     * Get the {@link AppLock}. Used for defining custom timeouts etc...
     */
    public AppLock getAppLock() {
        return mAppLocker;
    }
}
