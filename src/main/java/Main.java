import SeOmJegTrenger.Course;
import SeOmJegTrenger.Folder;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static String ACCESS_TOKEN = "";
    public static String blacklist = "";
    public static String domain = "";
    public static PrintWriter printWriter;
    public static String timeStamp = new SimpleDateFormat("yyyy.dd.MM_HH:mm:ss ").format(Calendar.getInstance().getTime());

    public static void main(String[] args) throws IOException {
        printWriter = new PrintWriter(new FileWriter("log.txt", true));
        loadConfig();
        downloadAllFiles();
        printWriter.close();
    }

    public static void loadConfig() {
        try {
            File configFile = FileUtils.getFile("config.txt");
            Scanner sc = new Scanner(configFile);
            Pattern pattern = Pattern.compile("#Access token:.?\"([\\d\\w~]+)\"#Course blacklist:.?\"([\\d\\w\\s,-]+)\"#Domain:.?\"(https.//.+/)\"");

            String config = "";
            while (sc.hasNextLine()) {
                config += sc.nextLine();
            }
            Matcher matcher = pattern.matcher(config);
            while(matcher.find()) {
                ACCESS_TOKEN = matcher.group(1);
                blacklist = matcher.group(2);
                domain = matcher.group(3);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void downloadAllFiles() {
        String mainPath = "Emner";
        File mainFolder = new File(mainPath);
        if(!mainFolder.exists())
            mainFolder.mkdir();
        CourseHenter courseHenter = new CourseHenter(ACCESS_TOKEN);
        List<Course> courses = courseHenter.getUserCourses();
        ////COURSES//////
        if(courses == null) {
            printWriter.println(timeStamp + "domain/accestoken is invalid");
            printWriter.close();
            System.exit(0);
        }
        for(Course course: courses) {
            File courseFolder = new File(mainPath + "/" + course.getCourse_code());
            if(blacklist.contains(course.getCourse_code())) {
                if(courseFolder.exists())
                    try {
                        FileUtils.deleteDirectory(courseFolder);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                continue;
            } else {
                    if(!courseFolder.exists()) {
                        courseFolder.mkdir();
                }
            }
            EmneFilHenter filHenter = new EmneFilHenter(ACCESS_TOKEN, course.getId());
            List<Folder> folders = filHenter.hentFolders();
            ////FOLDERS////////
            for(Folder folder: folders) {
                File courseFolderFolder = new File(mainPath + "/" + course.getCourse_code() + "/" +
                        folder.getFull_name().replace("course files", "").replace("unfiled", ""));
                if(!courseFolderFolder.exists())
                    courseFolderFolder.mkdirs();
                String folders_url = folder.getFiles_url();
                List<String> fileUrls = filHenter.hentFilerFraFolder(folders_url);

                deleteOld(courseFolderFolder.listFiles(), filHenter.hentFilNavnFraFolder(folders_url));
                /////FILES/////
                try {
                    for (String filurl : fileUrls) {
                        Pattern pattern = Pattern.compile("(.+\\.[\\w]+) £& (" + domain + "files.+)");
                        Matcher matcher = pattern.matcher(filurl);
                        if (matcher.find()) {
                            try {
                                String urli = matcher.group(2);
                                URL url = new URL(urli);

                                File file = new File(mainPath + "/" + course.getCourse_code() + "/" +
                                        folder.getFull_name().replace("course files", "").replace("unfiled", "") + "/" +
                                        matcher.group(1).replace(",", " ").replace("/", " "));
                                if (!file.exists()) {
                                    Main.printWriter.println(timeStamp + urli);
                                    Main.printWriter.println(timeStamp + "Downloading: " + matcher.group(1));
                                    FileUtils.copyURLToFile(url, file);
                                    Main.printWriter.println(timeStamp + "Done");
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (NullPointerException e) {
                    Main.printWriter.println(timeStamp + "Error nullpointer");
                }
            }

        }
    }
    public static void deleteOld(File[] path, List<String> filer) {
        List<String> systemFiles = new ArrayList<>();
        List<String> systemPath = new ArrayList<>();
        for (int i = 0; i < path.length; i++) {
            systemPath.add(path[i].toString());
            if(path[i].toString().contains(".")) {
                    Pattern pattern = Pattern.compile("(.+\\\\)(.+\\..+)");
                    Matcher matcher = pattern.matcher(path[i].toString());
                while (matcher.find()) {
                    systemFiles.add(matcher.group(2));
                }
            }
        }
        try {
            systemFiles.removeAll(filer);
        } catch (NullPointerException e) {
            Main.printWriter.println(timeStamp + "Error nullpointer");
        }

        for(String filStreng: systemFiles) {
            for(String lPath: systemPath) {
                if(lPath.contains(filStreng)) {
                    Main.printWriter.println(timeStamp + "Deleting: " + lPath);
                    File file = new File(lPath);
                    try {
                        FileUtils.forceDelete(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

        }
    }

}
