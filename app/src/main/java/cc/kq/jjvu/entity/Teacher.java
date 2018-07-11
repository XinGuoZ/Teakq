package cc.kq.jjvu.entity;

public class Teacher {
    private Integer id;

    private String teanum;

    private String teapwd;

    private String phone;

    private String teaname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTeanum() {
        return teanum;
    }

    public void setTeanum(String teanum) {
        this.teanum = teanum == null ? null : teanum.trim();
    }

    public String getTeapwd() {
        return teapwd;
    }

    public void setTeapwd(String teapwd) {
        this.teapwd = teapwd == null ? null : teapwd.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getTeaname() {
        return teaname;
    }

    public void setTeaname(String teaname) {
        this.teaname = teaname == null ? null : teaname.trim();
    }
}