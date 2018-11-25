package id.rumahawan.belajarfisika.Object;

public class ThreeItems {
    private String id;
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

    public String getId() {
        return id;
    }

    public void setId(String key) {
        this.id = key;
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
