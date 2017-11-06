package at.technikumwien.maps.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import at.technikumwien.maps.AppDependencyManager;
import at.technikumwien.maps.MyApplication;
import at.technikumwien.maps.util.managers.Cancelable;
import at.technikumwien.maps.util.managers.SyncManager;

/**
 * Created by nicoleang on 06.11.17.
 */

public class SyncDrinkingFountainsJobService extends JobService{

    private Cancelable cancelable = null;
    private static String TAG = SyncDrinkingFountainsJobService.class.getSimpleName();


    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.i(TAG, "JobService started, jobId=" + jobParameters.getJobId());
        AppDependencyManager manager = ((MyApplication) getApplicationContext()).getAppDependencyManager();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.i(TAG, "JobService stopped, jobId=" + jobParameters.getJobId());
        if(cancelable != null && !cancelable.isCanceled()){
            cancelable.cancel();
        }
        cancelable = null;
        return false;
    }
}
