
import com.google.gson.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JsonHandler {

    private String directory;

    public JsonHandler(String directory) {
        this.directory = directory;
    }

    public void save(String fileName, Object value) {
        Gson gson = getGson();
        String json = gson.toJson(value);
        saveAsFile(fileName, json);
    }

    public <T> T load(String fileName, Class<T> clazz) {
        String fileText = loadFile(fileName);
        Gson gson = getGson();
        return gson.fromJson(fileText, clazz);
    }

    private void saveAsFile(String fileName, String json){
        File file = getFile(fileName);
        try {
            FileUtils.writeStringToFile(file, json, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String loadFile(String fileName){
        try {
            InputStream inputStream = new FileInputStream(getFile(fileName));
            return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public File getFile(String fileName){
        File folder = new File(directory);
        if(!folder.exists())
            folder.mkdir();
        return new File(folder, fileName);
    }

    public String getStorageDirectory() {
        return directory;
    }

    private Gson getGson(){
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        return gsonBuilder.create();
    }

    /**
     * Henter info fra JSON obj i URL
     *
     * @param urli
     * @param tClass Java Class pojo
     * @param <T>
     * @return Object
     */
    public static <T> List<T> hentArray(String urli, Class<T> tClass) {
        try {
            URL url = new URL(urli);
            URLConnection request = url.openConnection();
            request.connect();
            //  Convert to a JSON object to print data
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
            GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
            Gson gson = gsonBuilder.create();
            JsonArray rootobj = root.getAsJsonArray();

            List<T> objList = new ArrayList<T>();
            for (int i = 0; i <rootobj.size() ; i++) {
                JsonObject asJsonObject = rootobj.get(i).getAsJsonObject();
                T obj = gson.fromJson(asJsonObject, tClass);
                objList.add(obj);
            }
            return objList;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Main.printWriter.println(Main.timeStamp + "Feil ved henting: " + urli);
        }
        return null;
    }

    public static <T> T hentObj(String urli, Class<T> tClass) {
        try {
            URL url = new URL(urli);
            URLConnection request = url.openConnection();
            request.connect();

            // Convert to a JSON object to print data
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
            JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.

            GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
            Gson gson = gsonBuilder.create();

            T obj = gson.fromJson(rootobj, tClass);
            return obj;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}