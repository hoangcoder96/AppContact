package nhatto.com;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import nhatto.com.data.Database;

public class FabUpdate extends AppCompatActivity implements View.OnClickListener{

    final String DATABASE_NAME = "ContactEmployeeDB.sqlite";
    final int RESQUEST_TAKE_PHOTO = 123;
    final int RESQUEST_CHOOSE_PHOTO = 321;

    int id = 1;

    private EditText edtName, edtphone;
    private CircleImageView civAvatar;
    private FloatingActionButton fabChup_hinh, fabChon_hinh, fabAdd, fabClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab_update);

        addControls();
        initUI();
    }

    private void addControls() {
        fabChon_hinh = (FloatingActionButton) findViewById(R.id.fab_chon_hinh);
        fabChup_hinh = (FloatingActionButton) findViewById(R.id.fab_chup_hinh);
        fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        fabClose = (FloatingActionButton) findViewById(R.id.fab_close);

        edtName = (EditText) findViewById(R.id.edt_name);
        edtphone = (EditText) findViewById(R.id.edt_phone);

        civAvatar = (CircleImageView) findViewById(R.id.civ_avatar);

        fabChon_hinh.setOnClickListener(this);
        fabChup_hinh.setOnClickListener(this);
        fabAdd.setOnClickListener(this);
        fabClose.setOnClickListener(this);
    }

    private void initUI() {
        Intent intent = getIntent();
        id = intent.getIntExtra("ID", 1);
        SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM LienHe WHERE ID = ? ", new String[]{id + ""});
        cursor.moveToFirst();
        String name = cursor.getString(1);
        String phone = cursor.getString(2);
        byte [] image = cursor.getBlob(3);

        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        civAvatar.setImageBitmap(bitmap);
        edtName.setText(name);
        edtphone.setText(phone);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_chon_hinh:
                ////// TODO: 12/06/2017
                choosePhoto();
                break;
            case R.id.fab_chup_hinh:
                ////// TODO: 12/06/2017
                takePicture();
                break;
            case R.id.fab_add:
                ////// TODO: 12/06/2017
                update();
                break;
            case R.id.fab_close:
                ////// TODO: 12/06/2017
                cancel();
                break;
        }
    }

    private void takePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, RESQUEST_TAKE_PHOTO);
    }

    private void choosePhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, RESQUEST_CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == RESQUEST_CHOOSE_PHOTO){
                try {
                    Uri imgUri = data.getData();
                    InputStream is = getContentResolver().openInputStream(imgUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    civAvatar.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }else if (requestCode == RESQUEST_TAKE_PHOTO){
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    civAvatar.setImageBitmap(bitmap);
            }
        }
    }

    private void update(){
        String name = edtName.getText().toString();
        String phone = edtphone.getText().toString();
        byte[] image = getByteArrayFromCivAvatar(civAvatar);

        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("Phone", phone);
        values.put("Image", image);

        SQLiteDatabase database = Database.initDatabase(this, "ContactEmployeeDB.sqlite");
        database.update("LienHe", values, "id = ?", new String[]{id + ""});
        startActivity(new Intent(this, MainActivity.class));

    }

    private void cancel(){
        startActivity(new Intent(this, MainActivity.class));
    }

    private byte[] getByteArrayFromCivAvatar(CircleImageView circleImageView){
        BitmapDrawable drawable = (BitmapDrawable) circleImageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}


