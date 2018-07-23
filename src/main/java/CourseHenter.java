

import SeOmJegTrenger.Course;
import com.google.gson.*;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CourseHenter {
    public String ACCESS_TOKEN;
    public List<Course> courses = new ArrayList<Course>();
    public PrintWriter writer;

    public CourseHenter(String ACCESS_TOKEN) {
        this.ACCESS_TOKEN = ACCESS_TOKEN;
    }

    public void addAllCoursesAtUIB() {
        try {
            writer = new PrintWriter("sizedata.txt", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //115 for alle 114*50
        for (int i = 1; i < 115; i++) {
            addCoursesAtPage(i);
        }
    }

    public List<Course> getUserCourses() {
        String url = Main.domain + "api/v1/courses/?access_token=" + ACCESS_TOKEN;
        List<Course> pageCourses = JsonHandler.hentArray(url, Course.class);
        return pageCourses;
    }

    public void close() {
        writer.close();
    }

    /**
     * Legger til alle emner fra Json på en spesifikk side,
     * henter 50 emner fra hver side
     *
     * @param page
     */
    public void addCoursesAtPage(int page) {
        String url = Main.domain + "api/v1/search/all_courses?page=" + page + "&per_page=100000&access_token=" + ACCESS_TOKEN;
        List<Course> pageCourses = hentArray(url);
        if(pageCourses.size() < 50) {
            System.out.println("Error: size=" + pageCourses.size());
        }
        for(Course course: pageCourses) {
            courses.add(course);
        }
        System.out.println(page);
        writer.println(page + " size=" + pageCourses.size());
    }

    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Lager en liste med emner fra Json fra URL, der objektene får emnekode, navn og id
     *
     * @param urli
     * @return
     */
    public List<Course> hentArray(String urli) {
        try {
            URL url = new URL(urli);
            URLConnection request = url.openConnection();
            request.connect();
            //  Convert to a JSON object to print data
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
            GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
            Gson gson = gsonBuilder.create();
            JsonArray array = root.getAsJsonArray();

            List<Course> objList = new ArrayList<Course>();
            Pattern pattern = Pattern.compile("\\{\"course\":\\{\"id\":(\\d+),\"name\":\"(.+)\",\"account_id.+\"course_code\":\"(.+)\",\"default_view\":");

            for (int i = 0; i <array.size(); i++) {
                Matcher matcher = pattern.matcher(array.get(i).toString());
                while (matcher.find()) {
                    String id = matcher.group(1);
                    String name = matcher.group(2);
                    String code = matcher.group(3);
                    objList.add(new Course(id, code, name));
                }
            }
            return objList;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
