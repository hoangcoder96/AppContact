package nhatto.com;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import nhatto.com.data.Database;

public class DetailLH extends AppCompatActivity implements View.OnClickListener{

    final String DATABASE_NAME = "ContactEmployeeDB.sqlite";

    private FloatingActionButton fabSave, fabHuy;
    private Button btnPhone, btnSend;
    private TextView tvName;
    private CircleImageView civAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_lh);

        addControls();
        initUI();
    }

    private void initUI() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("ID", 1);
        SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM LienHe WHERE ID = ? ", new String[]{id + ""});
        cursor.moveToFirst();
        String name = cursor.getString(1);
        String phone = cursor.getString(2);
        byte[] image = cursor.getBlob(3);

        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        civAvatar.setImageBitmap(bitmap);
        btnPhone.setText(phone);
        tvName.setText(name);
    }

    private void addControls() {

        tvName = (TextView) findViewById(R.id.tv_name);

        civAvatar = (CircleImageView) findViewById(R.id.civ_avatar);

        btnPhone = (Button) findViewById(R.id.btn_phone);
        btnSend = (Button) findViewById(R.id.btn_send);

        fabSave = (FloatingActionButton) findViewById(R.id.fab_save);
        fabHuy = (FloatingActionButton) findViewById(R.id.fab_close);

        fabSave.setOnClickListener(this);
        fabHuy.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_save:
                ////// TODO: 12/06/2017
                startActivity(new Intent(this,FabUpdate.class));
                break;
            case R.id.fab_close:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}
