
# RateConditionsMonitor
There are several libraries for rating, but all of them rely on good-old AlertDialog, and leaves no area for customization; as it doesn't expose it's internal conditions-checking-api.

 This library does just that: a rating-conditions-checking-api that leaves the UI part on the developer; now that creating AlertDialog is now trivial thanks to projects like Anko.
## Usage:

initialize the library at a starting point in your app :

```kotlin
RateConditionsMonitor.apply {
      init(context = this@MainActivity)
      applyConditions(launchTimes = 3, remindTimes = 7)

      //launchTimes: how many app launches it takes to ask for rating.
      //remindTimes: how many app launches it takes to ask for rating after RateConditionsMonitor.later() is called.
}
```
 then in a check point in your app, check if conditions are met:

```kotlin
rate_btn.setOnClickListener {
   if (RateConditionsMonitor.isConditionsMet)
      alert {
         title = "test rate"
         yesButton {
            //perform rating action here
            RateConditionsMonitor.rated() //let the monitor know.        }
/*      noButton {
          RateConditionsMonitor.denied() //let the monitor know.
         }*/
         negativeButton("later") {
            RateConditionsMonitor.later() //let the monitor know.
         }
      }.show()
}
```