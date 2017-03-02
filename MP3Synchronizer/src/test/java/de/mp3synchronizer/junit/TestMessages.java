package de.mp3synchronizer.junit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Locale;

import org.junit.Test;

import de.docfaust.mp3synchronizer.client.model.constants.MessageConstants;
import de.docfaust.mp3synchronizer.client.util.Messages;

public class TestMessages {

	@Test
	public void testGetStringGerman() {
		Locale.setDefault(Locale.GERMANY);
		assertThat(
				"Die Bereinigung ist beendet",
				equalTo(Messages
						.getString(MessageConstants.MP3SYNCHRONIZER_CONTROL_CLEANREADY_MESSAGE)));
	}

	@Test
	public void testGetStringEnglish() {
		Locale.setDefault(Locale.US);
		assertThat(
				"Cleaning is ready",
				equalTo(Messages
						.getString(MessageConstants.MP3SYNCHRONIZER_CONTROL_CLEANREADY_MESSAGE)));
	}

	@Test
	public void testGetStringUnknownLanguage() {
		Locale.setDefault(Locale.CHINA);
		assertThat(
				"Cleaning is ready",
				equalTo(Messages
						.getString(MessageConstants.MP3SYNCHRONIZER_CONTROL_CLEANREADY_MESSAGE)));
	}

	@Test
	public void testGetStringUnknownKey() {
		Locale.setDefault(Locale.GERMANY);
		assertThat("!uiuiui!", equalTo(Messages.getString("uiuiui")));
	}

	@Test
	public void testGetStringParameters() {
		Locale.setDefault(Locale.GERMANY);
		assertThat("Der Parameter ist wert",
				equalTo(Messages.getString("parameter", "wert")));
	}

	@Test
	public void testGetStringParametersWrongParameter() {
		Locale.setDefault(Locale.GERMANY);
		assertThat("!parameter1!",
				equalTo(Messages.getString("parameter1", "wert")));
	}
}
