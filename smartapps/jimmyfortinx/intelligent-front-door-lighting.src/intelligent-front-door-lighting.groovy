definition(
    name: "Intelligent Front Door Lighting",
    namespace: "jimmyfortinx",
    author: "Jimmy Fortin",
    description: "Adjust the ligthing based on multiple conditions like sunset, sunrise, time and more.",
    category: "Mode Magic",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/ModeMagic/rise-and-shine.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/ModeMagic/rise-and-shine@2x.png"
)

preferences {
    section ("Control these lights") {
        input "switches", "capability.switch", multiple: true
    }

	section ("At morning") {
		input "morningOpenTime", "time", title: "What time to open ligths?", required: true
	}

    section ("At night") {
		input "nightCloseTime", "time", title: "What time to close ligths?", required: true
	}
}

def installed() {
	initialize()
}

def updated() {
	unsubscribe()
	initialize()
}

def initialize() {
    subscribe(location, "position", onLocationChange)
	subscribe(location, "sunrise", onSunrise)
	subscribe(location, "sunset", onSunset)

    schedule(morningOpenTime, "onMorning")
    schedule(nightCloseTime, "onNight")

    controlLights()
}

def onLocationChange(event) {
    log.trace "onLocationChange"

    controlLights()
}

def onSunrise(event) {
    log.trace "onSunrise"

    controlLights()
}

def onSunset(event) {
    log.trace "onSunset"

    controlLights()
}

def onMorning(event) {
    log.trace "onMorning"

    controlLights()
}

def onNight(event) {
    log.trace "onNight"

    controlLights()
}

def filterSwitches(isOn) {
	def filter = isOn ? { value -> value == "on" } : { value -> value != "on" }

	return switches.currentSwitch.findAll(filter)
}

def turnOnLigthsIfNeeded(message) {
	def closedLights = filterSwitches(false)

    if (closedLights.size() > 0) {
        // switches.on();
        sendNotificationEvent(message)
    }
}

def turnOffLigthsIfNeeded(message) {
    def openedLights = filterSwitches(true)

    if (openedLights.size() > 0) {
        // switches.off();
        sendNotificationEvent(message)
    }
}

def controlLights() {
    def sunDetails = getSunriseAndSunset(sunriseOffset: sunriseOffset, sunsetOffset: sunsetOffset)

    def nowTime = now()
	def riseTime = sunDetails.sunrise
	def setTime = sunDetails.sunset
    
    if (nowTime < riseTime.time) {
        if (nowTime >= timeToday(morningOpenTime).time) {
            turnOnLigthsIfNeeded("As you requested, I opened front door ligths at morning.")
        }
    } else if (nowTime < setTime.time) {
        turnOffLigthsIfNeeded("As you requested, I closed front door ligths at sunrise.")
    } else if (nowTime < timeToday(nightCloseTime).time) {
        turnOnLigthsIfNeeded("As you requested, I opened front door ligths at sunset.")
    } else {
        turnOffLigthsIfNeeded("As you requested, I closed front door ligths at night.")
    }
}