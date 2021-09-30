package api;

public class Account {
    private static String accessToken;
    private static String id;

    public static Account getInstance(){
        return new Account();
    }
    private Account(){

    }

    public String getId() {
        return id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
