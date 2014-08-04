package com.andy.sjm;

import net.youmi.android.AdManager;
import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.banner.AdViewListener;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity{
	
	private WebView mWebView;
	private TextView loadingTv;
	private AdView adView = null;
	private LinearLayout adLayout = null;
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
		}
	};
	
//	private String mUrl = "http://1251001823.cdn.myqcloud.com/1251001823/wechat/sjm/launcher";
//	private String mUrl = "http://game.9g.com/sjm/game.html";
	private String mUrl = "http://117show.com/shenjingmao_android/sjm/";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		AdManager.getInstance(this).init("3cc9445d0c780ce1", "97f9171a388d4fb9", false);
		initView();
		initData();
	}
	
	private void initView() {
		mWebView = (WebView) this.findViewById(R.id.main_webview);
		loadingTv = (TextView) this.findViewById(R.id.loading_tv);
		// 获取要嵌入广告条的布局
		adLayout=(LinearLayout)this.findViewById(R.id.adLayout);
	}
	
	private void initData() {
		// 实例化广告条
		adView = new AdView(this, AdSize.FIT_SCREEN);
		adView.setAdListener(new AdViewListener() {
		    @Override
		    public void onSwitchedAd(AdView adView) {
		        // 切换广告并展示
		    }

		    @Override
		    public void onReceivedAd(AdView adView) {
		        // 请求广告成功
		    }

		    @Override
		    public void onFailedToReceivedAd(AdView adView) {
		        // 请求广告失败
		    }
		});
		
		mWebView.loadUrl(mUrl);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		
		mWebView.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				loadingTv.setVisibility(View.VISIBLE);
			}
			
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
			}
			
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				loadingTv.setVisibility(View.GONE);
				mHandler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// 将广告条加入到布局中
						adLayout.addView(adView);
					}
				}, 10*1000);
			}
			
		});
	}
}