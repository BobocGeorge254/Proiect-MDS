import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProfileActivityTest {
    @Mock
    private EditText usernameEdit;
    @Mock
    private EditText emailEdit;
    @Mock
    private EditText passwordEdit;
    @Mock
    private Button usernameEditButton;
    @Mock
    private Button emailEditButton;
    @Mock
    private Button passwordEditButton;
    @Mock
    private Button backButton;

    private ProfileActivity profileActivity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        profileActivity = spy(ProfileActivity.class);
        doNothing().when(profileActivity).startActivity(any(Intent.class));
        when(usernameEdit.getText()).thenReturn(mock(CharSequence.class));
        when(emailEdit.getText()).thenReturn(mock(CharSequence.class));
        when(passwordEdit.getText()).thenReturn(mock(CharSequence.class));
    }

    @Test
    public void testUsernameEditButtonOnClick_WithValidUsername() {
        // Arrange
        when(usernameEdit.getText().toString()).thenReturn("newUsername");
        doReturn(mock(Toast.class)).when(profileActivity).showToast(anyString(), anyInt());

        // Act
        usernameEditButton.performClick();

        // Assert
        verify(profileActivity).updateUser(anyString(), eq("newUsername"), anyString(), anyString());
        verify(profileActivity).showToast(eq("Username updated successfully!"), eq(Toast.LENGTH_SHORT));
    }

    @Test
    public void testEmailEditButtonOnClick_WithValidEmail() {
        // Arrange
        when(emailEdit.getText().toString()).thenReturn("newEmail");
        doReturn(mock(Toast.class)).when(profileActivity).showToast(anyString(), anyInt());

        // Act
        emailEditButton.performClick();

        // Assert
        verify(profileActivity).updateUser(anyString(), anyString(), eq("newEmail"), anyString());
        verify(profileActivity).showToast(eq("Email updated successfully!"), eq(Toast.LENGTH_SHORT));
    }

    @Test
    public void testPasswordEditButtonOnClick_WithValidPassword() {
        // Arrange
        when(passwordEdit.getText().toString()).thenReturn("newPassword");
        doReturn(mock(Toast.class)).when(profileActivity).showToast(anyString(), anyInt());

        // Act
        passwordEditButton.performClick();

        // Assert
        verify(profileActivity).updateUser(anyString(), anyString(), anyString(), eq("newPassword"));
        verify(profileActivity).showToast(eq("Password updated successfully!"), eq(Toast.LENGTH_SHORT));
    }

    @Test
    public void testBackButtonOnClick() {
        // Arrange
        Intent intent = mock(Intent.class);
        doReturn(intent).when(profileActivity).createIntent(any(Class.class));

        // Act
        backButton.performClick();

        // Assert
        verify(profileActivity).startActivity(intent);
    }
}
 */
