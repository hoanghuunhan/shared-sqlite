package huunhan.hn.com.myaccount;

public class Cinema {
    private String name;
    private int imgPoster;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgPoster() {
        return imgPoster;
    }

    public void setImgPoster(int imgPoster) {
        this.imgPoster = imgPoster;
    }

    public Cinema(String name, int imgPoster) {
        this.name = name;
        this.imgPoster = imgPoster;
    }
}
