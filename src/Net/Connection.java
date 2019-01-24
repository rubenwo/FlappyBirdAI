package Net;

import com.google.gson.JsonObject;

public class Connection {
    public Connection() {

    }

    public void updateHighScore(String id, int score) {
        JsonObject object = new JsonObject();
        object.addProperty("id", id);
        object.addProperty("score", score);
        System.out.println(object);
        /*
        try {
            URL url = new URL(NetConstants.URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            conn.getOutputStream().write();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
