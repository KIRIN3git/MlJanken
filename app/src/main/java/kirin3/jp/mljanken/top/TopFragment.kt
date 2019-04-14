package kirin3.jp.mljanken.top

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import kirin3.jp.mljanken.R
import kirin3.jp.mljanken.game.GameActivity
import kirin3.jp.mljanken.webview.WebViewActivity
import android.app.Activity

class TopFragment : Fragment() {


    private var mActivity: Activity? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mActivity = getActivity();
        return inflater.inflate(R.layout.fragment_top, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val view_buttle = view.findViewById(R.id.buttle) as LottieAnimationView
        val text_policy = view.findViewById(R.id.policy) as TextView

        view_buttle.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val intent = Intent(mActivity, GameActivity::class.java)
                startActivity(intent)
            }
        })

        text_policy.setOnClickListener(object: View.OnClickListener {
            override fun onClick(p0: View?) {
                // インテントのインスタンス生成
                val intent = Intent(mActivity, WebViewActivity::class.java)

                intent.putExtra(
                    WebViewActivity.INTENT_INPUT_URL,
                    mActivity?.applicationContext?.getString(R.string.privacy_poricy_url)
                )

                // プライバシーポリシーの起動
                startActivity(intent)
            }
        })
    }
}