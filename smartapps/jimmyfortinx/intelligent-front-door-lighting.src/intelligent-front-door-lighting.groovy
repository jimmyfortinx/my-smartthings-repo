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
	subscribe(location, "sunriseTime", onSunrise)
	subscribe(location, "sunsetTime", onSunset)

    schedule(startTime, "startTimerCallback")

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

def controlLights() {
    def sunDetails = getSunriseAndSunset(sunriseOffset: sunriseOffset, sunsetOffset: sunsetOffset)

    def now = new Date()
	def riseTime = sunDetails.sunrise
	def setTime = sunDetails.sunset

    log.trace "controlLights > now: $now, riseTime: $riseTime, setTime: $setTime"
}