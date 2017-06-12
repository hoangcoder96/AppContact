package nhatto.com.model;

/**
 * Created by User name on 11/06/2017.
 */

public class LienHe {
    public int id;
    public String name;
    public String phone;
    public byte[] image;

    public LienHe(int id, String name, String phone, byte[] image) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.image = image;
    }

    public LienHe(String name, String phone, byte[] image) {
        this.name = name;
        this.phone = phone;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
