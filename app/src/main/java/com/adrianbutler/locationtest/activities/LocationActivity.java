package com.adrianbutler.locationtest.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.adrianbutler.locationtest.R;
import com.google.android.gms.location.CurrentLocationRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.CancellationTokenSource;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

public class LocationActivity extends AppCompatActivity
{
	private static final String TAG = "LOCATION";
	private FusedLocationProviderClient fusedLocationClient;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);
		fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		getLocation();
	}

	@SuppressLint("MissingPermission")
	private void getLocation()
	{
		Log.i(TAG, "Requesting location.");
		CancellationToken cancellationToken = new CancellationTokenSource().getToken();
		CurrentLocationRequest locationRequest = new CurrentLocationRequest.Builder()
				.setPriority(Priority.PRIORITY_HIGH_ACCURACY)
				.build();

		fusedLocationClient.getCurrentLocation(locationRequest, cancellationToken)
				.addOnSuccessListener(success ->
				{
					Log.i(TAG, "Successfully retrieved location");
					Double latitude = success.getLatitude();
					Double longitude = success.getLongitude();
					setLocationText(latitude, longitude);
				});
	}

	private void setLocationText(Double latitude, Double longitude)
	{
		final TextView latitudeTextView = findViewById(R.id.LocationLatitudeTextView);
		final TextView longitudeTextView = findViewById(R.id.LocationLongitudeTextView);

		latitudeTextView.setText(latitude.toString());
		longitudeTextView.setText(longitude.toString());
	}
}