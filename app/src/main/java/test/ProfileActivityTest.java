package test ;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import profile.ProfileActivity ;

public class ProfileActivityTest {

    private ProfileActivity profileActivity;

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
    private Toast toast;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        profileActivity = spy(ProfileActivity.class);

        doReturn(usernameEdit).when(profileActivity).findViewById(anyInt());
        doReturn(emailEdit).when(profileActivity).findViewById(anyInt());
        doReturn(passwordEdit).when(profileActivity).findViewById(anyInt());
        doReturn(usernameEditButton).when(profileActivity).findViewById(anyInt());
        doReturn(emailEditButton).when(profileActivity).findViewById(anyInt());
        doReturn(passwordEditButton).when(profileActivity).findViewById(anyInt());
        doReturn(toast).when(profileActivity).showToast(anyString(), anyInt());
    }

    @Test
    public void testUsernameEditButtonOnClick_WithValidUsername() {
        // Arrange
        when(usernameEdit.getText().toString()).thenReturn("newUsername");

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

        // Act
        passwordEditButton.performClick();

        // Assert
        verify(profileActivity).updateUser(anyString(), anyString(), anyString(), eq("newPassword"));
        verify(profileActivity).showToast(eq("Password updated successfully!"), eq(Toast.LENGTH_SHORT));
    }
}
