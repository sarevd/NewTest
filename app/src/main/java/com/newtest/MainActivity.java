package com.newtest;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
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
//TODO ids for flurry
//    private static final String PEAK_APP_ID = "343b9d1657f5f935";
//    private static final String PEAK_INTERSTITIAL_ZONE_ID = "112145";
//    private static final String PEAK_VIDEO_ZONE_ID = "112268";
//    private static final String PEAK_REWARDED_ZONE_ID = "112391";
//    private static final String PEAK_BANNER_ZONE_ID = "59665";
//    private static final String NATIVE_AD_ID = "59678";

//// FIXME: ids for leadbolt
    private static final String PEAK_APP_ID = "6b3258be2b328de3";
    private static final String PEAK_INTERSTITIAL_ZONE_ID = "123765";
    private static final String PEAK_VIDEO_ZONE_ID = "";
    private static final String PEAK_REWARDED_ZONE_ID = "";
    private static final String PEAK_BANNER_ZONE_ID = "123792";
    private static final String NATIVE_AD_ID = "123805";
    private String TAG = "peakkk";

    private boolean interstitialShown = false;
    private boolean bannerShown = false;
    private boolean initialization = false;

    private ImageView mainImageView;
    private ImageView logoImageView;
    private ImageView privacyIconImageView;
    private TextView titleTextView;
    private TextView descriptionTextView;
    private Button adActionButton;
    private ProgressBar progressBar;
    private final Handler uiThreadHandler = new Handler();
    PeakNativeAd nativeAd;
    Button staticB,videoB,rewardedB;

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
    PeakAsyncAdRequest.PeakAsyncAdRequestListener asyncAdRequestListener1 = new PeakAsyncAdRequest.PeakAsyncAdRequestListener() {
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
    PeakAsyncAdRequest.PeakAsyncAdRequestListener asyncAdRequestListener2 = new PeakAsyncAdRequest.PeakAsyncAdRequestListener() {
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
    PeakAsyncAdRequest.PeakAsyncAdRequestListener asyncAdRequestListener3 = new PeakAsyncAdRequest.PeakAsyncAdRequestListener() {
        @Override
        public void onAdReady(String adZoneId) {
            //Show the ad with adZoneId
            //Request stops itself when the ad is ready.
            if (PeakSdk.checkAdAvailable(adZoneId)) {
                interstitialShown = true;
                View banner = PeakSdk.showBanner(adZoneId);
                if (banner != null) {
                    showBanner(banner);
                }
            }
        }
    };
    PeakAsyncAdRequest.PeakAsyncAdRequestListener asyncAdRequestListener4 = new PeakAsyncAdRequest.PeakAsyncAdRequestListener() {
        @Override
        public void onAdReady(String adZoneId) {
            //Show the ad with adZoneId
            //Request stops itself when the ad is ready.
            if (PeakSdk.checkAdAvailable(adZoneId)) {
                interstitialShown = true;
                showNativeAd();
            }
        }
    };

    PeakAsyncAdRequest asyncAdRequest, asyncAdRequest1, asyncAdRequest2, asyncAdRequest3, asyncAdRequest4;
    //PeakSdkListener peakSdkListener = new PeakSdkLogListener(TAG);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        PeakSdkListener peakSdkListener = new PeakSdkListener() {
            @Override
            public void onInitializationSuccess() {
                Log.d(TAG, "onInitializationSuccess: ");
                asyncAdRequest = PeakSdk.createAdRequest(PEAK_INTERSTITIAL_ZONE_ID);
                asyncAdRequest1 = PeakSdk.createAdRequest(PEAK_VIDEO_ZONE_ID);
                asyncAdRequest2 = PeakSdk.createAdRequest(PEAK_REWARDED_ZONE_ID);
                asyncAdRequest3 = PeakSdk.createAdRequest(PEAK_BANNER_ZONE_ID);
                //asyncAdRequest3.start(asyncAdRequestListener3);
                if (asyncAdRequest3 != null) {
                    asyncAdRequest3.start(asyncAdRequestListener3);
                }
                asyncAdRequest4 = PeakSdk.createAdRequest(NATIVE_AD_ID);
                //asyncAdRequest4.start(asyncAdRequestListener4);
                if (asyncAdRequest4 != null) {
                    asyncAdRequest4.start(asyncAdRequestListener4);
                }
            }

            @Override
            public void onInitializationFailed(PeakSdkException e) {
                Log.d(TAG, "onInitializationFailed: ");
            }

            @Override
            public void onBannerShowSuccess(String s) {
                Log.d(TAG, "onBannerShowSuccess: ");
            }

            @Override
            public void onBannerShowFailed(String s, PeakSdkException e) {
                Log.d(TAG, "onBannerShowFailed: ");
            }

            @Override
            public void onInterstitialShowSuccess(String s) {
                Log.d(TAG, "onInterstitialShowSuccess: ");
            }

            @Override
            public void onInterstitialShowFailed(String s, PeakSdkException e) {
                Log.d(TAG, "onInterstitialShowFailed: ");
                if (PeakSdk.checkAdAvailable(PEAK_INTERSTITIAL_ZONE_ID)) {
                    //  PeakSdk.showInterstitial(PEAK_INTERSTITIAL_ZONE_ID);
                }
            }

            @Override
            public void onInterstitialClosed(String s) {
                Log.d(TAG, "onInterstitialClosed: ");
                if (PeakSdk.checkAdAvailable(PEAK_INTERSTITIAL_ZONE_ID)) {
                    //  PeakSdk.showInterstitial(PEAK_INTERSTITIAL_ZONE_ID);
                }
            }

            @Override
            public void onCompletedRewardExperience(String s) {
                Log.d(TAG, "onCompletedRewardExperience: ");
                if (PeakSdk.checkAdAvailable(PEAK_INTERSTITIAL_ZONE_ID)) {
                    //  PeakSdk.showInterstitial(PEAK_INTERSTITIAL_ZONE_ID);
                }
            }

            @Override
            public void onNativeAdShowSuccess(String s) {
                Log.d(TAG, "onNativeAdShowSuccess: ");
            }

            @Override
            public void onNativeAdShowFailed(String s, PeakSdkException e) {
                Log.d(TAG, "onNativeAdShowFailed: ");
            }
        };
        PeakSdk.initialize(PEAK_APP_ID, uiHelper, peakSdkListener);

        staticB = (Button) findViewById(R.id.button);
        videoB = (Button) findViewById(R.id.button2);
        rewardedB = (Button) findViewById(R.id.button3);

        staticB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    asyncAdRequest = PeakSdk.createAdRequest(PEAK_INTERSTITIAL_ZONE_ID);
                //asyncAdRequest.start(asyncAdRequestListener);
                if (asyncAdRequest != null) {
                    asyncAdRequest.start(asyncAdRequestListener);
                }
            }
        });

        videoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    asyncAdRequest1 = PeakSdk.createAdRequest(PEAK_VIDEO_ZONE_ID);
//                asyncAdRequest1.start(asyncAdRequestListener1);
                if (asyncAdRequest1 != null) {
                    asyncAdRequest1.start(asyncAdRequestListener1);
                }
            }
        });

        rewardedB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // asyncAdRequest2 = PeakSdk.createAdRequest(PEAK_REWARDED_ZONE_ID);
//                asyncAdRequest2.start(asyncAdRequestListener2);
                if (asyncAdRequest2 != null) {
                    asyncAdRequest2.start(asyncAdRequestListener2);
                }
            }
        });


    }


    @SuppressWarnings("ConstantConditions")
    private void showBanner(View banner) {
        Log.d(TAG, "showBanner: true");
        ((ViewGroup) findViewById(R.id.bannerContainer)).addView(banner);
    }

    @Override
    protected void onPause() {
        asyncAdRequest.cancel();
        asyncAdRequest1.cancel();
        asyncAdRequest2.cancel();
        asyncAdRequest3.cancel();
        asyncAdRequest4.cancel();
        uiHelper.pause();
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

            nativeAd = PeakSdk.showNativeAd(NATIVE_AD_ID);
            if (nativeAd != null) {
               // progressBar.setVisibility(View.GONE);
                PeakSdk.trackNativeAdShown(NATIVE_AD_ID);
                bindNativeAdToViews(nativeAd);
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
