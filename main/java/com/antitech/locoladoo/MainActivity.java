package com.antitech.locoladoo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends Activity {
	private InterstitialAd mInterstitialAd;
    private AdView mAdView;

	private WebView webview1;



	private Timer _timer = new Timer();
	private AlertDialog.Builder Exit;
	private Vibrator vibrator;
	private TimerTask timer;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

MobileAds.initialize(this,"ca-app-pub-3229800713013562~9655167746");
		mInterstitialAd = new InterstitialAd(this);
		mInterstitialAd.setAdUnitId("ca-app-pub-3229800713013562/4435341090");

		mAdView = (AdView) findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);

		mAdView.setAdListener(new AdListener() {
				@Override
				public void onAdLoaded() {
// Code to be executed when an ad finishes loading.
					Log.i("Ads", "onAdLoaded");
				}
				@Override
				public void onAdFailedToLoad(int errorCode) {
// Code to be executed when an ad request fails.
					Log.i("Ads", "onAdFailedToLoad");
				}
				@Override
				public void onAdOpened() {
// Code to be executed when an ad opens an overlay that
					// covers the screen.
					Log.i("Ads", "onAdOpened");
				}
				@Override
				public void onAdLeftApplication() {
					// Code to be executed when the user has left the app.
					Log.i("Ads", "onAdLeftApplication");
				}
				@Override
				public void onAdClosed() {
// Code to be executed when when the user is about to return
// to the app after tapping on an ad.
					Log.i("Ads", "onAdClosed");
				}
			});
		initialize();
		initializeLogic();
	}

	private void  initialize() {
		webview1 = (WebView) findViewById(R.id.webview1);
		webview1.getSettings().setJavaScriptEnabled(true);
		webview1.getSettings().setSupportZoom(true);
		webview1.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView _view,final String _url, Bitmap _favicon) {

				super.onPageStarted(_view, _url, _favicon);
			}
			@Override
			public void onPageFinished(WebView _view,final String _url) {

				super.onPageFinished(_view, _url);
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
			}
		});

		Exit = new AlertDialog.Builder(this);
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);



	}

	private void  initializeLogic() {
		webview1.loadUrl("https://sites.google.com/view/locoladoo");


	}

	@Override
	public void onStart() {
		super.onStart();

	}

    @Override
		public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.super_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){

			case R.id.menu_back:
				onBackPressed();
				break;

			case R.id.menu_refresh:
				webview1.reload();
				break;

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		if (webview1.canGoBack())
		webview1.goBack();
	    else
		finish();
	}



	// created automatically
	private void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}

	private int getLocationX(View _v) {
		 int _location[] = new int[2];
		 _v.getLocationInWindow(_location);
		 return _location[0];
	}

	private int getLocationY(View _v) {
		 int _location[] = new int[2];
		 _v.getLocationInWindow(_location);
		 return _location[1];
	}

	private int getRandom(int _minValue ,int _maxValue){
		Random random = new Random();
		return random.nextInt(_maxValue - _minValue + 1) + _minValue;
	}

	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
				_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}

	private float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}

	private int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}

	private int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}

}
