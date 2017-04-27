package feec.vutbr.cz.multimediatesting.Model;

import android.content.Context;
import android.os.Environment;
import feec.vutbr.cz.multimediatesting.Contract.HistoryActivityContract;
import feec.vutbr.cz.multimediatesting.Listener.BaseActionListener;
import feec.vutbr.cz.multimediatesting.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class FileWriter implements HistoryActivityContract.File {

    private Context mCtx;
    private BaseActionListener mListener;
    private String mPath;
    private WriterThread mWriter;
    private HistoryActivityContract.Database mDatabase;

    private static final String FOLDER_NAME = "MultimediaTesting";
    private static final String FILE_EXTENSION = ".csv";

    public FileWriter(Context context) {
        mCtx = context;
    }

    @Override
    public void start(long id) {
        if (mWriter == null) {
            mWriter = new WriterThread(id);
            if (!mWriter.isRunning()) {
                mWriter.start();
            }
        }
    }


    private void checkFolder() {
        File folder = new File(mPath + File.separator + FOLDER_NAME);
        if (!folder.exists()) {
            if (!folder.mkdir()) {
                mListener.onError(mCtx.getString(R.string.file_cannot_create_folder));
            }
        }
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    private FileOutputStream createFile(String name) {
        String filePath = mPath + File.separator + FOLDER_NAME + File.separator + name;
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filePath);
        } catch (FileNotFoundException e) {
            mListener.onError(e.getMessage());
        }
        return out;
    }


    @Override
    public void setDatabase(HistoryActivityContract.Database database) {
        mDatabase = database;
    }

    @Override
    public void removeDatabase() {
        mDatabase = null;
    }

    @Override
    public void addListener(BaseActionListener listener) {
        mListener = listener;
    }

    @Override
    public void removeListener() {
        mListener = null;
    }

    @Override
    public String canWrite() {
        if (isExternalStorageWritable()) {
            return "";
        }
        return mCtx.getString(R.string.file_cannot_write);
    }

    @Override
    public void close() {
        if (mWriter != null) {
            if (mWriter.isRunning()) {
                mWriter.close();
            }
            mWriter = null;
        }

    }

    private void removeFile(String name) {
        String path = mPath + File.separator + FOLDER_NAME + File.separator + name;
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    protected class WriterThread extends Thread {
        private volatile boolean mRunning;
        private long mId;

        public WriterThread(long id) {
            mId = id;
        }

        @Override
        public synchronized void start() {
            super.start();
            mRunning = true;
        }

        @Override
        public void run() {
            mPath = Environment.getExternalStorageDirectory().getAbsolutePath();
            checkFolder();
            String name = mDatabase.getFileName(mId);
            if (name == null) {
                mListener.onError(mCtx.getString(R.string.file_invalid_name));
                return;
            }
            String[] data = mDatabase.getFileExport(mId);
            if (data.length == 0) {
                mListener.onError(mCtx.getString(R.string.file_invalid_data));
                return;
            }

            FileOutputStream out = createFile(name + FILE_EXTENSION);
            if (out != null) {
                for (int i = 0; i < data.length && mRunning; i++) {
                    try {
                        String line = data[i] + System.getProperty("line.separator");
                        out.write(line.getBytes(Charset.defaultCharset()));
                    } catch (IOException e) {
                        mListener.onError(e.getMessage());
                        removeFile(name + FILE_EXTENSION);
                        break;
                    }
                }
                try {
                    out.close();
                } catch (IOException e) {
                    mListener.onError(e.getMessage());
                }
            }
            mListener.onSuccess();
        }

        public boolean isRunning() {
            return mRunning;
        }

        public void close() {
            mRunning = false;
        }
    }
}
