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
import kirin3.jp.mljanken.award.AwardActivity
import kirin3.jp.mljanken.information.InformationActivity
import kirin3.jp.mljanken.totalization.TotalizationActivity
import kirin3.jp.mljanken.util.LogUtils.LOGD
import kirin3.jp.mljanken.util.SettingsUtils.TAG

class TopFragment : Fragment() {


    private var mActivity: Activity? = null


    companion object {

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mActivity = getActivity();
        return inflater.inflate(R.layout.fragment_top, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val view_game = view.findViewById(R.id.game) as LottieAnimationView
        val view_award = view.findViewById(R.id.award) as LottieAnimationView
        val view_totalization = view.findViewById(R.id.totalization) as LottieAnimationView
        val view_information = view.findViewById(R.id.information) as LottieAnimationView

        LOGD(TAG, "aaaaaaaaaaaaa : " + 1111111111 );

        val text_policy = view.findViewById(R.id.policy) as TextView

        view_game.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val intent = Intent(mActivity, GameActivity::class.java)
                startActivity(intent)
                LOGD(TAG, "aaaaaaaaaaaaa1 : " + 1111111111 );
            }
        })

        view_award.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val intent = Intent(mActivity, AwardActivity::class.java)
                startActivity(intent)
                LOGD(TAG, "aaaaaaaaaaaaa2 : " + 1111111111 );
            }
        })

        view_totalization.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val intent = Intent(mActivity, TotalizationActivity::class.java)
                startActivity(intent)
                LOGD(TAG, "aaaaaaaaaaaaa3 : " + 1111111111 );
            }
        })

        view_information.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val intent = Intent(mActivity, InformationActivity::class.java)
                startActivity(intent)
                LOGD(TAG, "aaaaaaaaaaaaa4 : " + 1111111111 );
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