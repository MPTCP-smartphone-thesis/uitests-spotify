package spotify;

import utils.Utils;

import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class LaunchSettings extends UiAutomatorTestCase {

	private static final String ID_BROWSE_PANEL_BUTTON = "com.spotify.music:id/navigation_item_browse";
	private static final String ID_CARD_VIEW = "com.spotify.music:id/card_view";
	private static final String ID_BUTTON_HOME = "android:id/home";
	private static int TIME_LISTENING = 75000;
	private static final int MAX_ERRORS = 7;

	private void listenMusic() {
		// Panel
		int errors = 0;
		while (!Utils.click(ID_BUTTON_HOME) && errors++ < MAX_ERRORS)
			sleep(1000);
		Utils.customAssertTrue(this, "Cannot have panel menu",
				errors <= MAX_ERRORS);
		sleep(1000);
		assertTrue("Cannot browse music",
				Utils.click(Utils.getObjectWithId(ID_BROWSE_PANEL_BUTTON)));
		sleep(2000);
		// List takes some times to be uploaded, especially for low connections
		int errors = 0;
		while (!Utils.click(Utils.getObjectWithId(ID_CARD_VIEW, 0))
				&& errors < MAX_ERRORS) {
			sleep(1000);
		}
		if (errors >= MAX_ERRORS) {
			Utils.returnToHomeScreen(this);
			assertTrue("Cannot have first album card",
					Utils.click(Utils.getObjectWithId(ID_CARD_VIEW, 0)));
		}
		sleep(2000);
		// The same for the SHUFFLE PLAY button (but less likely to occur if
		// previously succeeded)
		errors = 0;
		while (!Utils.click(Utils.getObjectWithClassNameAndText(
				"android.widget.Button", "SHUFFLE PLAY"))
				&& errors < MAX_ERRORS) {
			sleep(1000);
		}
		if (errors >= MAX_ERRORS) {
			Utils.returnToHomeScreen(this);
			assertTrue("Cannot listen music", Utils.click(Utils
					.getObjectWithClassNameAndText("android.widget.Button",
							"SHUFFLE PLAY")));
		}
		/* Now enjoy the music */
		sleep(TIME_LISTENING);
	}

	public void testDemo() throws UiObjectNotFoundException {
		// Purge cache
		String cacheDir = Utils.homeDir + "/Android/data/com.spotify.music/files/spotifycache/Storage";
		Utils.runAsUser("rm -rf " + cacheDir);

		assertTrue("OOOOOpps",
				Utils.openApp(this, "Spotify",
						"com.spotify.music",
						"com.spotify.music.MainActivity"));
		TIME_LISTENING *= Utils.getMultTime(this);
		sleep(5000);

		listenMusic();
		Utils.returnToHomeScreen(this);
	}

}
