package kirin3.jp.mljanken.top

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import kirin3.jp.mljanken.R
import kirin3.jp.mljanken.award.AwardActivity
import kirin3.jp.mljanken.game.GameActivity
import kirin3.jp.mljanken.totalization.TotalizationActivity
import kirin3.jp.mljanken.webview.WebViewActivity

class TopFragment : androidx.fragment.app.Fragment() {

    private var activity: Activity? = null

    companion object {
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity = getActivity();
        return inflater.inflate(R.layout.fragment_top, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val view_game = view.findViewById(R.id.game) as LottieAnimationView
        val view_award = view.findViewById(R.id.award) as LottieAnimationView
        val view_totalization = view.findViewById(R.id.totalization) as LottieAnimationView
        val view_information = view.findViewById(R.id.information) as LottieAnimationView

        val text_policy = view.findViewById(R.id.policy) as TextView

        view_game.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val intent = Intent(activity, GameActivity::class.java)
                startActivity(intent)
            }
        })

        view_award.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val intent = Intent(activity, AwardActivity::class.java)
                startActivity(intent)
            }
        })

        view_totalization.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val intent = Intent(activity, TotalizationActivity::class.java)
                startActivity(intent)
            }
        })

        view_information.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                // インテントのインスタンス生成
                val intent = Intent(activity, WebViewActivity::class.java)

                intent.putExtra(
                    WebViewActivity.INTENT_INPUT_URL,
                    activity?.applicationContext?.getString(R.string.information_url)
                )

                // プライバシーポリシーの起動
                startActivity(intent)
            }
        })

        text_policy.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                // インテントのインスタンス生成
                val intent = Intent(activity, WebViewActivity::class.java)

                intent.putExtra(
                    WebViewActivity.INTENT_INPUT_URL,
                    activity?.applicationContext?.getString(R.string.privacy_poricy_url)
                )

                // プライバシーポリシーの起動
                startActivity(intent)
            }
        })
    }
}