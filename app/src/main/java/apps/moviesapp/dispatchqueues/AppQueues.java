package apps.moviesapp.dispatchqueues;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;


public class AppQueues {
    private static Handler dbHandler = null;
    private static HandlerThread dbHandlerThread = null;

    public static void postToDbQueue(Runnable runnable) {
        if (dbHandlerThread == null) {
            dbHandlerThread = new HandlerThread("dbQueue");
            dbHandlerThread.start();
        }

        if (dbHandler == null) {
            dbHandler = new Handler(dbHandlerThread.getLooper());
        }
        dbHandler.post(runnable);
    }

    private static Handler mainThreadHandler = null;

    public static void postToMainThreadQueue(Runnable runnable) {
        if (mainThreadHandler == null) {
            mainThreadHandler = new Handler(Looper.getMainLooper());
        }
        mainThreadHandler.post(runnable);
    }
}
