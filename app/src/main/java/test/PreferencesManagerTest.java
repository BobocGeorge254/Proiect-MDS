package test;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import others.PreferencesManager ;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;


@RunWith(RobolectricTestRunner.class)
public class PreferencesManagerTest {

    @Mock
    Context context;

    @Mock
    SharedPreferences sharedPreferences;

    @Mock
    SharedPreferences.Editor editor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(PreferenceManager.getDefaultSharedPreferences(context)).thenReturn(sharedPreferences);
        when(sharedPreferences.edit()).thenReturn(editor);
    }

    @Test
    public void testGetUserId() {
        String expectedUserId = "12345";
        when(sharedPreferences.getString("USER_ID", null)).thenReturn(expectedUserId);

        String userId = PreferencesManager.getUserId(context);

        assertEquals(expectedUserId, userId);
    }

    @Test
    public void testGetLastOpenedTeamId() {
        String expectedTeamId = "67890";
        when(sharedPreferences.getString("LAST_OPENED_TEAM_ID", null)).thenReturn(expectedTeamId);

        String teamId = PreferencesManager.getLastOpenedTeamId(context);

        assertEquals(expectedTeamId, teamId);
    }

    @Test
    public void testGetLastOpenedTeamChanelId() {
        String expectedTeamChannelId = "channel123";
        when(sharedPreferences.getString("LAST_OPENED_TEAM_CHANEL_ID", null)).thenReturn(expectedTeamChannelId);

        String teamChannelId = PreferencesManager.getLastOpenedTeamChanelId(context);

        assertEquals(expectedTeamChannelId, teamChannelId);
    }

    @Test
    public void testGetLastOpenedTeamPostReplyingId() {
        String expectedPostId = "post987";
        when(sharedPreferences.getString("LAST_OPENED_TEAM_POST_REPLYING_ID", null)).thenReturn(expectedPostId);

        String postId = PreferencesManager.getLastOpenedTeamPostReplyingId(context);

        assertEquals(expectedPostId, postId);
    }

    @Test
    public void testGetLastTeamPressedEditId() {
        String expectedTeamId = "edit123";
        when(sharedPreferences.getString("LAST_TEAM_PRESSED_EDIT_ID", null)).thenReturn(expectedTeamId);

        String teamId = PreferencesManager.getLastTeamPressedEditId(context);

        assertEquals(expectedTeamId, teamId);
    }

    @Test
    public void testGetLastURISelected() {
        Uri expectedUri = Uri.parse("file:///path/to/file");
        when(sharedPreferences.getString("LAST_URI_SELECTED", null)).thenReturn(expectedUri.toString());

        Uri uri = PreferencesManager.getLastURISelected(context);

        assertEquals(expectedUri, uri);
    }
}
