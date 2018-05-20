package me.arnoldwho.hongdou;

public class Messages {

    public String id;
    public String userName;
    public String anonymous;
    public String title;
    public String likes_num;

    public Messages (String id, String userName, String anonymous, String likes_num, String title){
        this.id = id;
        this.userName = userName;
        this.anonymous = anonymous;
        this.title = title;
        this.likes_num = likes_num;
    }

    public String getTitles(){
        return title;
    }

    public String getAnonymous() {
            return anonymous;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getLikes_num() {
        return likes_num;
    }
}
