package com.route.myapplication.hms.ui.HomeFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.FragmentActivity
import com.route.myapplication.hms.R

class ChatBotFragment : Fragment() {

    lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_chat_bot, container, false)
        webView = view.findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://www.netflix.com/eg-en/")





        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //https://brain-tumur-classification-app.herokuapp.com/
        webView = requireActivity().findViewById(R.id.webView)
        webView.loadUrl("https://www.netflix.com/eg-en/")

        //val webSettings = webView.settings
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()

       // webView.canGoBack()
//        webView.setOnKeyListener(View.OnKeyListener { view, i, keyEvent ->
//            if (i == keyEvent)
//        })



    }

}