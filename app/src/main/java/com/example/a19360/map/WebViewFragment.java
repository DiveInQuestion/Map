package com.example.a19360.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //String name = this.getArguments().getString("title");
        View contentView = inflater.inflate(R.layout.fragment_web_view, container, false);
        WebView webView = (WebView)contentView.findViewById(R.id.webView);
                //用于在本地显示而不是弹出一个新窗口
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.baidu.com/");
        return contentView;
        //return webView;
    }
}
