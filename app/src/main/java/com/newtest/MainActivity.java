package com.newtest;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.peak.PeakAsyncAdRequest;
import com.peak.PeakSdk;
import com.peak.PeakSdkListener;
import com.peak.PeakSdkUiHelper;
import com.peak.exception.PeakSdkException;
import com.peak.nativeads.PeakNativeAd;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    PeakSdkUiHelper uiHelper = new PeakSdkUiHelper(MainActivity.this);

    private static final String PEAK_APP_ID = "5b1656281bbad6b8";
    private static final String PEAK_INTERSTITIAL_ZONE_ID = "27022";
    private static final String PEAK_BANNER_ZONE_ID = "";
    private static final String NATIVE_AD_ID = "";
    private String TAG = "peakkk";

    private boolean interstitialShown = false;
    private boolean bannerShown = false;

    private ImageView mainImageView;
    private ImageView logoImageView;
    private ImageView privacyIconImageView;
    private TextView titleTextView;
    private TextView descriptionTextView;
    private Button adActionButton;
    private ProgressBar progressBar;
    private final Handler uiThreadHandler = new Handler();
    PeakNativeAd nativeAd;

    PeakAsyncAdRequest asyncAdRequest;

    private final PeakSdkListener peakSdkListener = new PeakSdkLogListener(TAG);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PeakSdk.initialize(PEAK_APP_ID, uiHelper, peakSdkListener);

        if(PeakSdk.checkAdAvailable(PEAK_BANNER_ZONE_ID)) {
            View banner = PeakSdk.showBanner(PEAK_BANNER_ZONE_ID);
            if (banner != null) {
                showBanner(banner);
            }
        }

        PeakAsyncAdRequest.PeakAsyncAdRequestListener asyncAdRequestListener = new PeakAsyncAdRequest.PeakAsyncAdRequestListener() {
            @Override
            public void onAdReady(String adZoneId) {
                //Show the ad with adZoneId
                //Request stops itself when the ad is ready.
                if (PeakSdk.checkAdAvailable(adZoneId)) {
                    interstitialShown = true;
                    PeakSdk.showInterstitial(adZoneId);
                }
            }
        };
        asyncAdRequest = PeakSdk.createAdRequest(PEAK_INTERSTITIAL_ZONE_ID);

        if (asyncAdRequest != null) {
            asyncAdRequest.start(asyncAdRequestListener);
        }
    }

    @SuppressWarnings("ConstantConditions")
    private void showBanner(View banner) {
        Log.d(TAG, "showBanner: true");
        ((ViewGroup) findViewById(R.id.bannerContainer)).addView(banner);
    }

    @Override
    protected void onPause() {
        uiHelper.pause();
        asyncAdRequest.cancel();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        uiHelper.resume();
    }

    @Override
    protected void onDestroy() {
        uiHelper.destroy();
        super.onDestroy();
    }

    private void findViews() {
        mainImageView = (ImageView) findViewById(R.id.mainImageView);
        logoImageView = (ImageView) findViewById(R.id.logoImageView);
        privacyIconImageView = (ImageView) findViewById(R.id.privacyInformationIconImageView);
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
        adActionButton = (Button) findViewById(R.id.interactWithAdButton);
    }

    private void showNativeAd() {
        if (PeakSdk.checkAdAvailable(NATIVE_AD_ID)) {
            nativeAd = PeakSdk.showNativeAd(NATIVE_AD_ID);
            if (nativeAd != null) {
                progressBar.setVisibility(View.GONE);
                PeakSdk.trackNativeAdShown(NATIVE_AD_ID);
                bindNativeAdToViews(nativeAd);
            }
        }
    }

    private void bindNativeAdToViews(PeakNativeAd nativeAd) {
        Picasso imageLoader = Picasso.with(this);
        fillMainImage(nativeAd, imageLoader);
        fillIcon(nativeAd, imageLoader);
        fillPrivacyInformationIcon(nativeAd, imageLoader);
        titleTextView.setText(nativeAd.getTitle());
        descriptionTextView.setText(nativeAd.getText());
        adActionButton.setVisibility(View.VISIBLE);
        adActionButton.setText(nativeAd.getActionText());
        adActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PeakSdk.handleNativeAdClicked(NATIVE_AD_ID);
            }
        });
    }

    private void fillMainImage(PeakNativeAd nativeAd, Picasso imageLoader) {
        String mainImage = nativeAd.getMainImage();
        if (!TextUtils.isEmpty(mainImage)) {
            imageLoader.load(mainImage).into(mainImageView);
        }
    }

    private void fillIcon(PeakNativeAd nativeAd, Picasso imageLoader) {
        String icon = nativeAd.getIcon();
        if (!TextUtils.isEmpty(icon)) {
            imageLoader.load(icon).into(logoImageView);
        }
    }

    private void fillPrivacyInformationIcon(PeakNativeAd nativeAd, Picasso imageLoader) {
        String privacyIcon = nativeAd.getPrivacyIcon();
        if (!TextUtils.isEmpty(privacyIcon)) {
            imageLoader.load(privacyIcon).into(privacyIconImageView);
        }
    }
}
