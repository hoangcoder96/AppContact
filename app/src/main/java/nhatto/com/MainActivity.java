package nhatto.com;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import nhatto.com.adapter.AdapterLienHe;
import nhatto.com.data.Database;
import nhatto.com.model.LienHe;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final String DATABASE_NAME = "ContactEmployeeDB.sqlite";
    SQLiteDatabase database;

    FloatingActionButton fabNew;

    private ListView listView;
    private ArrayList<LienHe> list;
    private AdapterLienHe adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabNew = (FloatingActionButton) findViewById(R.id.fab_new);
        
        fabNew.setOnClickListener(this);
        
        addControls();
        readData();
    }

    private void addControls() {
        listView = (ListView) findViewById(R.id.lv_ds);
        list = new ArrayList<>();
        adapter =  new AdapterLienHe(this, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(MainActivity.this,DetailLH.class));
            }
        });
    }

    private void readData(){
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM LienHe", null);
        list.clear();
        for (int i=0; i<cursor.getCount(); i++){
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String phone = cursor.getString(2);
            byte[] image = cursor.getBlob(3);
            list.add(new LienHe(id, name, phone, image));
        }
        adapter.notifyDataSetChanged();
        database.close();

//        database = SQLiteDatabase.openDatabase(DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
//        openDatabase();
//        Cursor cursor = database.rawQuery("SELECT * FROM LienHe", null);
//        if (cursor != null) {
//            cursor.moveToFirst();
//            int indexID = cursor.getColumnIndex("id");
//            int indexAsk = cursor.getColumnIndex("ask");
//            int indexRa = cursor.getColumnIndex("ra");
//            int indexRb = cursor.getColumnIndex("rb");
//            int indexRc = cursor.getColumnIndex("rc");
//            int indexRd = cursor.getColumnIndex("rd");
//            int indexLevel = cursor.getColumnIndex("level");
//
//            while (!cursor.isAfterLast()) {
//                int id = cursor.getInt(indexID);
//                String aks = cursor.getString(indexAsk);
//                String rA = cursor.getString(indexRa);
//                String rB = cursor.getString(indexRb);
//                String rC = cursor.getString(indexRc);
//                String rD = cursor.getString(indexRd);
//                int levele = cursor.getInt(indexLevel);
//                cursor.moveToNext();
//
//            }
//
//            closeDatabase();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_new:
                ////// TODO: 11/06/2017  
                startActivity(new Intent(this,AddLH.class));
                break;
        }
    }

}
