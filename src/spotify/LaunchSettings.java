package spotify;

import utils.Utils;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class LaunchSettings extends UiAutomatorTestCase {

	private static String ID_BROWSE_PANEL_BUTTON = "com.spotify.music:id/navigation_item_browse";
	private static String ID_CARD_VIEW = "com.spotify.music:id/card_view";

	private void returnToMainMenu() {
		UiObject mainPanel = Utils
				.getObjectWithDescription("YOUR MUSIC, Navigate up");
		while (!mainPanel.exists()) {
			getUiDevice().pressBack();
			mainPanel = Utils
					.getObjectWithDescription("YOUR MUSIC, Navigate up");
		}
	}

	private void listenMusic() {
		sleep(1000);
		assertTrue("Cannot have panel menu",
 Utils.click(Utils
				.getObjectWithDescription("YOUR MUSIC, Navigate up")));
		sleep(1000);
		assertTrue("Cannot browse music",
				Utils.click(Utils.getObjectWithId(ID_BROWSE_PANEL_BUTTON)));
		sleep(2000);
		assertTrue(
				"Cannot have first album card",
				Utils.click(Utils.getObjectWithId(ID_CARD_VIEW, 0)));
		sleep(2000);
		assertTrue(
				"Cannot listen music",
 Utils.click(Utils
				.getObjectWithClassNameAndText("android.widget.Button",
						"SHUFFLE PLAY")));
	}

	public void testDemo() throws UiObjectNotFoundException {
		assertTrue("OOOOOpps",
				Utils.openApp(this, "Spotify", "com.spotify.music"));
		returnToMainMenu();
		listenMusic();
	}

}