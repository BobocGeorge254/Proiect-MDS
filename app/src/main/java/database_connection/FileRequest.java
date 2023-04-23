package database_connection;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.register.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import others.Manager;

public class FileRequest {

    public static String uploadTeamLogo(Context context, Uri uri) {
        String path = "team_logo/" + UUID.randomUUID();
        StorageReference storageReference = Manager.dbConnection.getStorage().getReference().child(path);

        byte[] data;
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            data = getBytesFromInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload team image";
        }

        UploadTask uploadTask = storageReference.putBytes(data);

        while (!uploadTask.isComplete()) {
        }

        return path;
    }

    public static Bitmap getTeamLogo(Context context, String path) {
        StorageReference storageReference = Manager.dbConnection.getStorage().getReference().child(path);

        try {
            File localFile = File.createTempFile("images", "jpg");
            FileDownloadTask downloadTask = storageReference.getFile(localFile);

            while (!downloadTask.isComplete()) {
            }

            return BitmapFactory.decodeFile(localFile.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return BitmapFactory.decodeResource(context.getResources(), R.drawable.placeholder);
    }

    public static String deleteFile(String path) {
        if(path.equals("default.png"))
            return "File deleted from storage successfully";

        StorageReference storageReference = Manager.dbConnection.getStorage().getReference().child(path);

        Task<Void> deleteTask = storageReference.delete();

        while(!deleteTask.isComplete()) {
        }

        if(deleteTask.isSuccessful())
            return "File deleted from storage successfully";

        return "Failed to delete file from storage";
    }


    ///////////NON STORAGE REQUEST METHODS///////////////////////////

    public static byte[] getBytesFromInputStream(InputStream inputStream) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int len;
        while ((len = bis.read(buffer)) > -1) {
            bos.write(buffer, 0, len);
        }

        bos.flush();
        return bos.toByteArray();
    }
}
