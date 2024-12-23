package com.dynamiciconchange;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.app.Application;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

import java.util.HashSet;
import java.util.Set;

@ReactModule(name = "DynamicIconChange")
public class DynamicIconChangeModule extends ReactContextBaseJavaModule implements Application.ActivityLifecycleCallbacks {
    public static final String NAME = "DynamicIconChange";
    private final String packageName;
    private final Set<String> classesToKill = new HashSet<>();
    private Boolean iconChanged = false;
    private String componentClass = "";

    public DynamicIconChangeModule(ReactApplicationContext reactContext, String packageName) {
        super(reactContext);
        this.packageName = packageName;
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }

    @ReactMethod
    public void getAppIcon(Promise promise) {
        final Activity activity = getCurrentActivity();
        if (activity == null) {
            promise.reject("ANDROID:ACTIVITY_NOT_FOUND");
            return;
        }

        final String activityName = activity.getComponentName().getClassName();

        if (activityName.endsWith("MainActivity")) {
          promise.resolve("Default");
          return;
      }

        String[] activityNameSplit = activityName.split("MainActivity");
        if (activityNameSplit.length != 2) {
            promise.reject("ANDROID:UNEXPECTED_COMPONENT_CLASS:" + this.componentClass);
            return;
        }
        promise.resolve(activityNameSplit[1]);
        return;
    }

    @ReactMethod
    public void changeAppIcon(String iconName, Promise promise) {

      final Activity activity = getCurrentActivity();

      if (activity == null) {
          promise.reject("ACTIVITY_NOT_FOUND", "The activity is null. Check if the app is running properly.");
          return;
      }
      if (iconName.isEmpty()) {
          promise.reject("EMPTY_ICON_STRING", "Icon name is missing i.e. changeAppIcon('YOUR_ICON_NAME_HERE')");
          return;
      }
      if (this.componentClass.isEmpty()) {
          this.componentClass = activity.getComponentName().getClassName();
      }

      final String newIconName = (iconName == null || iconName.isEmpty()) ? "Default" : iconName;
        final String activeClass = this.packageName + ".MainActivity" + newIconName;
        
        if (this.componentClass.equals(activeClass)) {
          promise.reject("ICON_ALREADY_USED", "This icons is the current active icon. " +  this.componentClass);
          return;
      }
        try {
            activity.getPackageManager().setComponentEnabledSetting(
                    new ComponentName(this.packageName, activeClass),
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);

        Intent intent = new Intent(activity, activity.getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);

        ActivityManager activityManager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            activityManager.restartPackage(activity.getPackageName());
        }

                promise.resolve(newIconName);
        } catch (Exception e) {
               promise.reject("ICON_INVALID", detailedMessage, e);
            }

      this.classesToKill.add(this.componentClass);
      this.componentClass = activeClass;
      activity.getApplication().registerActivityLifecycleCallbacks(this);
      iconChanged = true;
    }

    private void completeIconChange() {
        if (!iconChanged)
            return;
        final Activity activity = getCurrentActivity();
        if (activity == null)
            return;
        
            classesToKill.remove(componentClass);
            classesToKill.forEach((cls) -> activity.getPackageManager().setComponentEnabledSetting(
                    new ComponentName(this.packageName, cls),
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP));
            classesToKill.clear();
            iconChanged = false;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        completeIconChange();
    }

  @Override
  public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
  }

  @Override
  public void onActivityStarted(Activity activity) {
  }

  @Override
  public void onActivityResumed(Activity activity) {
  }

  @Override
  public void onActivityStopped(Activity activity) {
  }

  @Override
  public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
  }

  @Override
  public void onActivityDestroyed(Activity activity) {
  }
}