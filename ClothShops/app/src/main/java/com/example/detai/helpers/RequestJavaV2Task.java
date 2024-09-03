package com.example.detai.helpers;

import static android.content.ContentValues.TAG;

import android.os.AsyncTask;
import android.util.Log;


import com.example.detai.Interface.BotReply;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;


import com.google.cloud.dialogflow.v2.DetectIntentRequest;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;

public class RequestJavaV2Task extends AsyncTask<Void, Void, DetectIntentResponse> {

  private BotReply mInterface;
  private SessionName session;
  private SessionsClient sessionsClient;
  private QueryInput queryInput;

  public RequestJavaV2Task(BotReply mInterface, SessionName session, SessionsClient sessionsClient,
      QueryInput queryInput) {
    this.mInterface = mInterface;
    this.session = session;
    this.sessionsClient = sessionsClient;
    this.queryInput = queryInput;
  }

  @Override
  protected DetectIntentResponse doInBackground(Void... voids) {
    try {
      DetectIntentRequest detectIntentRequest =
          DetectIntentRequest.newBuilder()
              .setSession(session.toString())
              .setQueryInput(queryInput)
              .build();
      return sessionsClient.detectIntent(detectIntentRequest);
    } catch (Exception e) {
      Log.d(TAG, "doInBackground: " + e.getMessage());
      e.printStackTrace();
    }
    return null;
  }

  @Override
  protected void onPostExecute(DetectIntentResponse response) {
    mInterface.callback(response);
  }
}