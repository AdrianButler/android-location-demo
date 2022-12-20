package com.adrianbutler.locationtest.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.adrianbutler.locationtest.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity
{
	private static final String TAG = "MAIN";

	boolean fineLocationGranted;
	boolean coarseLocationGranted;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		checkForPermissions();
		setupWhereAmIButton();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
	}

	private void setupWhereAmIButton()
	{
		Button whereAmIButton = findViewById(R.id.HomeWhereAmIButton);
		whereAmIButton.setOnClickListener(view ->
		{
			Intent goToLocationIntent = new Intent(this, LocationActivity.class);
			startActivity(goToLocationIntent);
		});
	}

	private void checkForPermissions()
	{
		ActivityResultLauncher<String[]> locationPermissionRequest =
				registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(),
						result ->
						{
							fineLocationGranted = Boolean.TRUE.equals(result.getOrDefault(
									Manifest.permission.ACCESS_FINE_LOCATION, false));
							coarseLocationGranted = Boolean.TRUE.equals(result.getOrDefault(
									Manifest.permission.ACCESS_COARSE_LOCATION, false));
							if (!fineLocationGranted && !coarseLocationGranted)
							{
								Log.w(TAG, "App does not have location permissions.");
								showPermissionsTextView();
							}
							else {
								Log.i(TAG, "App has location permissions.");
								hidePermissionsTextView();
							}
						});

		locationPermissionRequest.launch(new String[]
				{
						Manifest.permission.ACCESS_FINE_LOCATION,
						Manifest.permission.ACCESS_COARSE_LOCATION
				});
	}

	private void showPermissionsTextView()
	{
		TextView permissionsTextView = findViewById(R.id.HomePermissionsTextView);
		permissionsTextView.setVisibility(View.VISIBLE);
	}

	private void hidePermissionsTextView()
	{
		TextView permissionsTextView = findViewById(R.id.HomePermissionsTextView);
		permissionsTextView.setVisibility(View.GONE);

		Button whereAmIButton = findViewById(R.id.HomeWhereAmIButton);
		whereAmIButton.setVisibility(View.VISIBLE);
	}

}