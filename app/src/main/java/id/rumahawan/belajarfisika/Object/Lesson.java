package id.rumahawan.belajarfisika.Object;

public class Lesson {
    private String id;
    private String name;
    private String subject;
    private String level;
    private String youtubeLink;

    public Lesson() {}

    public Lesson(String id, String name, String subject, String level, String youtubeLink) {
        this.id = id;
        this.name = name;
        this.subject = subject;
        this.level = level;
        this.youtubeLink = youtubeLink;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }
}
