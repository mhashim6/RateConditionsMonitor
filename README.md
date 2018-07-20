
# RateConditionsMonitor
There are several libraries for rating, but all of them force using AlertDialog, and leaves no area for customization; as it doesn't expose it's internal conditions-checking-api.

 This library does just that: a rating-conditions-checking-api that leaves the UI part on the developer.

 [![](https://jitpack.io/v/mhashim6/RateConditionsMonitor.svg)](https://jitpack.io/#mhashim6/RateConditionsMonitor)

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
Make sure to check the [sample](https://github.com/mhashim6/RateConditionsMonitor/blob/master/app/src/main/java/mhashim6/android/ratemonitorsample/MainActivity.kt).

---

## Dependency:
Add it in your root build.gradle at the end of repositories:

```groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

**Step 2.**  Add the dependency

```css
	dependencies {
	        implementation 'com.github.mhashim6:RateConditionsMonitor:1.0'
	}
```