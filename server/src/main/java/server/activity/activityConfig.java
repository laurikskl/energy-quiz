package server.activity;

import commons.Activity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import server.database.ActivityRepository;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Configuration
public class activityConfig {

    @Bean
    CommandLineRunner commandLineRunner(ActivityRepository repository) {
        return args -> {
            List activitiesList = new ArrayList<Activity>();

            File activitiesFile = new File("activities");
            for (File file : activitiesFile.listFiles()) {
                for (File f : file.listFiles()) {
                    String fileName = f.getName();
                    //File name without the last 4 chars
                    String beginning = fileName.substring(0, fileName.length() - 4);
                    //Last 4 chars of the file name
                    String ending = fileName.substring(fileName.length() - 4);

                    if (ending.equals("json")) {
                        //Read the activity from the current json file
                        Activity activity = Activity.JSONActivityReader(new Scanner(f));
                        //Add the path for the image
                        String path = file.toString() + "\\" + beginning;
                        if (Files.exists(Path.of(path + "jpg"))) {
                            activity.setImagePath(path + "jpg");
                        }
                        if (Files.exists(Path.of(path + "png"))) {
                            activity.setImagePath(path + "png");
                        }
                        if (Files.exists(Path.of(path + "jpeg"))) {
                            activity.setImagePath(path + "jpeg");
                        }
                        activitiesList.add(activity);
                    }
                }
            }
            repository.saveAll(activitiesList);
        };
    }

}
