package com.ss.client;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;

import co.com.ss.models.AppObj;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback) throws IllegalArgumentException;
	void selectApp(AsyncCallback<String> asyncCallback);
	void insertMetrics(String nameMetrics, float valueMetric, AsyncCallback<String> callback);
	void validarVersion(String id, AsyncCallback<String> callback);
	void selectAppaVersion(AsyncCallback<String> callback);
	void saveAppVersion(String app, String version, boolean aux, AsyncCallback<String> callback);
	void insertCiclos(String name, int idVersionfk, AsyncCallback<String> callback);
	void reporteCiclos(int idApp, int idVersion, AsyncCallback<String[]> callback); 
}
