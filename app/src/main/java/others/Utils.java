package others;

import static database_connection.FileRequest.getBytesFromInputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class Utils {

    public static Bitmap transformUriToBitmap(Context context, Uri uri) {
        byte[] data;
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            data = getBytesFromInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            data = new byte[0];
        }

        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }
}
