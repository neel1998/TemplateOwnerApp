package com.abc.templateownerapp.Model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.abc.templateownerapp.Network;
import com.abc.templateownerapp.utils.AsyncTaskResult;
import com.abc.templateownerapp.utils.NetworkResponse;
import com.abc.templateownerapp.utils.callback;

import java.io.IOException;

public class ItemsTask {

    public static void getData(Context context, final callback<String> cb) {
        FetchTask fetchTask =  new FetchTask() {
            @Override
            protected void onPostExecute(AsyncTaskResult<NetworkResponse> asyncTaskResult) {
                if (asyncTaskResult.isError()) {
                    cb.onError(asyncTaskResult.getError());
                } else {
                    NetworkResponse response = asyncTaskResult.getResult();
                    Log.d("res", response.getResponseString());
                    if (response.getResponseCode() == 200) {
                        cb.onSucess(response.getResponseString());
                    } else {
                        cb.onError(new Exception("404"));
                    }
                }
            }
        };
        fetchTask.execute(context);
    }

    public static class FetchTask extends AsyncTask<Context, Void, AsyncTaskResult<NetworkResponse>> {

        @Override
        protected AsyncTaskResult<NetworkResponse> doInBackground(Context... contexts) {
            try {
                NetworkResponse tokenResponse = Network.verifyToken(User.getUserInstance().getToken());
                if (tokenResponse.getResponseCode() != 200) {
                    return  new AsyncTaskResult<>(new Exception("Invalid Token"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                NetworkResponse response = Network.makeCall("app/getItems", null, Network.getAppId(contexts[0]));
                return new AsyncTaskResult<>(response);
            } catch (IOException  e) {
                e.printStackTrace();
                return new AsyncTaskResult<>(e);
            }
        }
    }
}
