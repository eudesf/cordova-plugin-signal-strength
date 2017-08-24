package org.apache.cordova.plugin;

import android.content.Context;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SignalStrength extends CordovaPlugin {

        SignalStrengthStateListener ssListener;
        int dbm = -1;

        int snr = -1;
        int cdmaDbm = -1;
        int cdmaEcio =-1;

        @Override
        public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

                if ("snr".equals(action) || "dbm".equals(action) || "cdmDbm".equals(action) || "cdmaDbm".equals(action) || "cdmaEcio".equals(action)) {
                        ssListener = new SignalStrengthStateListener();
                        TelephonyManager tm = (TelephonyManager) cordova.getActivity().getSystemService(Context.TELEPHONY_SERVICE);
                        tm.listen(ssListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);

                        if (action.equals("dbm")) {
                                sleep(dbm);
                                callbackContext.success(dbm);
                                return true;
                        }
                        else if ("snr".equals(action)) {
                                sleep(snr);
                                callbackContext.success(snr);
                                return true;
                        }
                        else if ("cdmaDbm".equals(action)) {
                                sleep(cdmaDbm);
                                callbackContext.success(cdmaDbm);
                                return true;
                        }
                        else if ("cdmaEcio".equals(action)) {
                                sleep(cdmaEcio);
                                callbackContext.success(cdmaEcio);
                                return true;
                        }

                }

                return false;
        }

        private void sleep(int value) {
                int counter = 0;
                while ( value == -1) {
                        try {
                                Thread.sleep(200);
                        } catch (InterruptedException e) {
                                e.printStackTrace();
                        }
                        if (counter++ >= 5) {
                                break;
                        }
                }
        }


        class SignalStrengthStateListener extends PhoneStateListener {

                @Override
                public void onSignalStrengthsChanged(android.telephony.SignalStrength signalStrength) {
                        super.onSignalStrengthsChanged(signalStrength);

                        TelephonyManager tm = (TelephonyManager) cordova.getActivity().getSystemService(Context.TELEPHONY_SERVICE);
                        // based on https://stackoverflow.com/questions/5545026/how-to-get-lte-signal-strength-in-android
                        if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_LTE) {
                          try {
                              dbm = Integer.parseInt(signalStrength.toString().split(" ")[8]) - 140;
                          } catch (Exception ex) {
                              dbm = 99;
                          }
                        } else if (signalStrength.isGsm()) {
                            if (signalStrength.getGsmSignalStrength() != 99)
                                dbm = signalStrength.getGsmSignalStrength() * 2 - 113;
                            else {
                                dbm = signalStrength.getGsmSignalStrength();
                            }
                        } 
                        else {
                            snr = signalStrength.getEvdoSnr();
                            cdmaDbm = signalStrength.getCdmaDbm();
                            cdmaEcio = signalStrength.getCdmaEcio();
                        }

                }

        }


}
