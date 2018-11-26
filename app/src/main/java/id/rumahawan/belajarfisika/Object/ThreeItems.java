package id.rumahawan.belajarfisika.Object;

public class ThreeItems {
    private String id;
    private int img;
    private String title;
    private String subtile;
    private String subsubtitle;

    public ThreeItems() {
    }

    public ThreeItems(String id, String title, String subtile, String subsubtitle) {
        this.id = id;
        this.title = title;
        this.subtile = subtile;
        this.subsubtitle = subsubtitle;
    }

    public ThreeItems(String id, int img, String title, String subtile, String subsubtitle) {
        this.id = id;
        this.img = img;
        this.title = title;
        this.subtile = subtile;
        this.subsubtitle = subsubtitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtile() {
        return subtile;
    }

    public void setSubtile(String subtile) {
        this.subtile = subtile;
    }

    public String getSubsubtitle() {
        return subsubtitle;
    }

    public void setSubsubtitle(String subsubtitle) {
        this.subsubtitle = subsubtitle;
    }
}