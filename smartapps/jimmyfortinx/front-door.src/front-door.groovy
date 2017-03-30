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
		input "morningOpenTime", "mode", title: "What time to open ligths?", required: true
	}

    section ("At night") {
		input "nightCloseTime", "mode", title: "What time to close ligths?", required: true
	}
}