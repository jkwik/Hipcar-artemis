package com.example.justinkwik.hipcar.Main.Reservation.OnGoingFragmentClasses;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.justinkwik.hipcar.ConnectionManager;
import com.example.justinkwik.hipcar.Login.LoginActivity;
import com.example.justinkwik.hipcar.Login.UserCredentials;
import com.example.justinkwik.hipcar.R;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class OnGoingReservationFragment extends Fragment {

    private UserCredentials userCredentials;
    private final String onGoingReservationLink = "https://artemis-api-dev.hipcar.com/reservation/on-going";
    private OnGoingReservation[] onGoingReservation;
    private Gson gson;
    private RecyclerView onGoingReservationRecyclerView;

    public OnGoingReservationFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userCredentials = LoginActivity.getUserCredentials();
        gson = new Gson();

        Log.e("Token: ", userCredentials.getToken()); //TODO: delete this token log.

        StringRequest onGoingReservationRequest = new StringRequest(Request.Method.GET, onGoingReservationLink, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                onGoingReservation = gson.fromJson(response, OnGoingReservation[].class);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("token", userCredentials.getToken());

                return headerMap;
            }
        };

        ConnectionManager.getInstance(getActivity().getApplicationContext()).add(onGoingReservationRequest);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reservation, container, false);

        onGoingReservationRecyclerView = (RecyclerView) view.findViewById(R.id.onGoingReservationRecyclerView);
        onGoingReservationRecyclerView.setItemAnimator(new DefaultItemAnimator());
        onGoingReservationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        return view;
    }

}