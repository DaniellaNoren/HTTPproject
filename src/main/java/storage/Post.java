package storage;

public class Post {

    public int id;
    public String post;

    public Post() {}

    public Post(int id, String post){
        this.id = id;
        this.post = post;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "Comment { id: " + id + "Post:"+ post + "}";
    }
}
