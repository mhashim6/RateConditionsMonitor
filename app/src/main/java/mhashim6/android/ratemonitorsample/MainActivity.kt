package mhashim6.android.ratemonitorsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import mhashim6.lib.ratemonitor.RateConditionsMonitor
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RateConditionsMonitor.setup(context = this@MainActivity) {
            applyConditions(launchTimes = 0, remindTimes = 2)
        }

        rate_btn.setOnClickListener {
            if (RateConditionsMonitor.isConditionsMet)
                alert {
                    title = "test rate"
                    yesButton {
                        RateConditionsMonitor.rated()
                    }
                    /*    noButton {
                            RateConditionsMonitor.denied()
                        }*/

                    negativeButton("later") {
                        RateConditionsMonitor.later()
                    }
                }.show()
        }

    }
}
