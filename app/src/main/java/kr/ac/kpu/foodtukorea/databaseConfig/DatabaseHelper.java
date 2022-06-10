package kr.ac.kpu.foodtukorea.databaseConfig;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.provider.ContactsContract;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Error TAG
    protected static String TAG = "DatabaseHelper";

    private static String databasePath = ""; // 데이터베이스 경로
    private static String databaseName = "restaurant.db"; // 데이터베이스 이름
    private static String tableName = "restaurant1"; // 테이블 이름

    private final Context mContext;
    private SQLiteDatabase mDatabase;

    public DatabaseHelper(Context context){
        super(context, databaseName, null, 1);

        if (Build.VERSION.SDK_INT >= 17){
            databasePath = context.getApplicationInfo().dataDir + "/databases/";
        }
        else {
            databasePath = "/data/data/" + context.getPackageName() + "/databases/";
        }

        this.mContext = context;
    }

    // 데이터베이스 파일 열기
    public boolean OpenDatabaseFile() throws SQLException {

        if(!CheckDatabaseFileExist()){
            CreateDatabase();
        }

        String mPath = databasePath + databaseName;
        try{
            mDatabase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
            Log.e(TAG,  "[SUCCESS] " + databaseName + " are Opened");
        }
        catch(SQLException sqlException){
            Log.e(TAG, "[ERROR]" + "Can't Open Database");
        }
        return mDatabase != null;
    }

    // 데이터베이스 파일 존재 여부 확인
    public boolean CheckDatabaseFileExist(){
        File file = new File(databasePath + databaseName);
        return file.exists();
    }

    // Database 만들기
    public void CreateDatabase() throws SQLException{

        this.getReadableDatabase();
        this.close();

        try{
            CopyDatabaseFile();
            Log.e(TAG,  "[SUCCESS] " + databaseName + " are Created");
        }
        catch(IOException ioException){
            // Error Message
            Log.e(TAG, "[ERROR] " + "Unable to create " + databaseName);
            throw new Error(TAG);
        }
    }

    // 데이터베이스 복사
    public void CopyDatabaseFile() throws IOException{

        InputStream inputStream  = mContext.getAssets().open(databaseName);
        String outputFileName = databasePath + databaseName;
        OutputStream outputStream = new FileOutputStream(outputFileName);

        byte[] buffer = new byte[1024];
        int length;
        while((length = inputStream.read(buffer)) > 0){
            outputStream.write(buffer, 0, length);
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }


    // 테이블 정보 가져오기
    public List getTableData() {

        try{
            // 테이블 정보를 저장할 List
            List mList = new ArrayList();

            // 쿼리
            String sql = "SELECT * FROM " + tableName;

            // 테이블 데이터를 읽기 위한 Cursor
            Cursor mCursor = mDatabase.rawQuery(sql, null);

            // 테이블 끝까지 읽기
            if (mCursor != null){

                // 다음 Row로 이동
                while(mCursor.moveToNext()){

                    // 해당 Row 저장
                    RestaurantAdminDisp rad = new RestaurantAdminDisp();

                    rad.setId(mCursor.getLong(0));
                    rad.setPrcscitypoint_bsshnm(mCursor.getString(1));
                    rad.setAddr(mCursor.getString(2));
                    rad.setViltcn(mCursor.getString(3));
                    rad.setDspscn(mCursor.getString(4));
                    rad.setDsps_dcsndt(mCursor.getString(5));
                    rad.setInduty_cd_nm(mCursor.getString(6));
                    rad.setLast_updt_dtm(mCursor.getString(7));
                    rad.setLaword_cd_nm(mCursor.getString(8));
                    rad.setColumn11(mCursor.getString(9));

                    // List에 해당 Row 추가
                    mList.add(rad);
                }

            }
            return mList;

        }
        catch (SQLException mSQLException){
            // Error Message
            Log.e(TAG, mSQLException.toString());
            throw mSQLException;
        }

    }

    // 데이터베이스 닫기
    public void CloseDatabaseFile(){
        if (mDatabase != null){
            mDatabase.close();
        }
    }

    @Override
    public synchronized void close(){
        CloseDatabaseFile();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}