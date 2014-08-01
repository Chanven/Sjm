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
	private String mUrl = "http://game.9g.com/sjm/game.html";
	
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
		// ��ȡҪǶ�������Ĳ���
		adLayout=(LinearLayout)this.findViewById(R.id.adLayout);
	}
	
	private void initData() {
		// ʵ���������
		adView = new AdView(this, AdSize.FIT_SCREEN);
		adView.setAdListener(new AdViewListener() {
		    @Override
		    public void onSwitchedAd(AdView adView) {
		        // �л���沢չʾ
		    }

		    @Override
		    public void onReceivedAd(AdView adView) {
		        // ������ɹ�
		    }

		    @Override
		    public void onFailedToReceivedAd(AdView adView) {
		        // ������ʧ��
		    }
		});
		
		mWebView.loadUrl(mUrl);
		mWebView.getSettings().setJavaScriptEnabled(true);
		
		mWebView.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				loadingTv.setVisibility(View.VISIBLE);
			}
			
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				loadingTv.setVisibility(View.GONE);
				mHandler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// ����������뵽������
						adLayout.addView(adView);
					}
				}, 10*1000);
			}
			
		});
	}
}
