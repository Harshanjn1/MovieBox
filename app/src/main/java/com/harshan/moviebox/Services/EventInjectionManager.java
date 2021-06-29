package com.harshan.moviebox.Services;

import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Vector;

public class EventInjectionManager {
    public static final int SEND_RESPONSE = -1;
    private static final String TAG = "EventInjectManager";
    private Vector<InjectedEventNotifier> listOfListeners = new Vector<>();
    public interface InjectedEventNotifier {
        // interface to be implemented in ur activity or fragment
        void onReceiveEvent(int eventType, RecyclerView recyclerView);
    }
    private static EventInjectionManager mgr;
    private EventInjectionManager() {
        Log.d(TAG, "EventInjectManager initializing");
    }
    public static synchronized EventInjectionManager getInstance() {
        if (mgr == null) {
            mgr = new EventInjectionManager();
        }return mgr;
    }
    public void addListener(int type, InjectedEventNotifier listener) {
        if (listener == null) {
            Log.e(TAG, "Listener is null , so returning");
            return;
        }
        Log.d(TAG, "Listener added");
        listOfListeners.add(listener);
    }
    public void removeListener(int type, InjectedEventNotifier listener) {
        if (listener == null) {
            Log.e(TAG, "Listener is null , so returning");
            return;
        }
        listOfListeners.remove(listener);
    }
    public void inject(int type, RecyclerView recyclerView) {
        Log.d(TAG, "inject: " + type);
        if (listOfListeners == null) {
            Log.e(TAG, "listener added" + type);
            return;
        }
        for (InjectedEventNotifier listener : listOfListeners) {
            listener.onReceiveEvent(type, recyclerView);
        }
    }
}

