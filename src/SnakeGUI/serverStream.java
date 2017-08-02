package SnakeGUI;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;


/**
 * Created by victo on 7/28/2017.
 */
public class serverStream {

    // Connection from server, return String response.

    //These are GSON Object

    private String userID;
    private int score=100; //Default Score is 100.

    private String jsonString; //JsonString from Server through Gson.
    private JsonObject jsonObject; //JsonObject from Server through Gson.

    //This is unsorted scoreMap with playerName and Score.
    private Map<String, Integer> scoreMap = new HashMap<>();

    //This is unsorted user List with playerName and id#.
    private Map<String,Integer> userNumberMap = new HashMap<>();


    /**
     * Default Constructor
     */
    public serverStream(){

    }

    /**
     * This method is to connect server and get response as JSON text.
     * JSON text would then pass as GSON JsonString, JsonObject as private attribute.
     */
        public void fromServer() {

            try {
                String url = "http://ec2-204-236-221-207.compute-1.amazonaws.com/snake/players";

                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                // optional default is GET
                con.setRequestMethod("GET");

                //add request header
                //con.setRequestProperty("User-Agent", USER_AGENT);

                int responseCode = con.getResponseCode();
                System.out.println("Sending 'GET' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine + "\n");
                }
                in.close();

                //print result
                System.out.println(response.toString());

                JsonParser parser = new JsonParser();

                // String
                jsonString = response.toString();

                // Object
                jsonObject = (JsonObject)parser.parse(jsonString);

                }

         catch(Exception e){
                    System.err.println("Got an exception! ");
                    System.err.println(e.getMessage());
                }
            }

    /**
     * This method is to create scoreMap and userNumberMap from JsonString and JsonObject by calling fromServer().
     * JsonArray is sitting in "data".
     * We will first need to put "Successful, "data" & "Error" into a MAP, and then
     * loop through again to get JsonArray from "data" and populate into meaningful MAPs.
     */
    public void jsonToMap(){

            //Call Server to get response
            fromServer();

        // MAP to hold each JsonElement from "Successful, "data" & "Error"
        HashMap<String, JsonElement> hashMap = new HashMap<>();

        // Loop to get "Successful", "data and "Error" to MAP
        for (Map.Entry<String,JsonElement> entry1 : jsonObject.entrySet()) {
            hashMap.put(entry1.getKey(), entry1.getValue());
        }

        //Loop to get JsonArray from "data" and populate into meaningful MAPs.
            for (JsonElement entry2 : hashMap.get("data").getAsJsonArray()) {

                HashMap<String, String> map = new Gson().fromJson(entry2.toString(), new TypeToken<HashMap<String, String>>() {
                }.getType());

                scoreMap.put(map.get("playerName"), Integer.parseInt(map.get("bestScore")));

                userNumberMap.put(map.get("playerName"),Integer.parseInt(map.get("id")));
            }
    }


    /**
     * This method is to connect server and submit score by "PUT" request through HTTP.
     */
    public void putScoreServer(){
        jsonToMap();
        try {
            String url = "http://ec2-204-236-221-207.compute-1.amazonaws.com/snake/"+getUserNumber(userID)+"/best_score?bestScore="+score;

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is PUT
            con.setRequestMethod("PUT");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

            int responseCode = con.getResponseCode();
            System.out.println("Sending 'PUT' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
        }

        catch(Exception e){
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }


    /**
     * This method is to connect server and create a new user profile on server.
     */
    public void loginServer(){

        // Create new user to server

        try {
            String url = "http://ec2-204-236-221-207.compute-1.amazonaws.com/snake/login";

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is PUT
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());

            String params = "playerName="+userID;
            out.write(params);
            out.flush();
            out.close();

            int responseCode = con.getResponseCode();
            System.out.println("Sending 'POST' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
        }

        catch(Exception e){
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }


    /**
     * Mutator to get userID into this class.
     * @param login
     */
    public void setUserID(String login) {
        userID = login;
    }

    /**
     * Mutator to get score into this class.
     * @param score
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * Accessor to unsorted scoreList
     * @return unsorted scoreList
     */
    public Map<String, Integer> getScoreMap() {
        return scoreMap;
    }

    /**
     * This method will translate playerName into unqiue id# on the server from userNumberMap.
     * @param userName
     * @return id# corresponding to playerName
     */
    public int getUserNumber(String userName) {

        int result = userNumberMap.get(userName);

        return result;
    }

    public static void main(String[] args) {

        //serverStream model = new serverStream();

        //model.jsonToMap();

        //model.setUserID("Jordan");
        //model.setScore(160);
        //model.loginServer();
        //model.putScoreServer();
        //model.loginServer();

        //System.out.println(model.getScoreMap());
    }
}
