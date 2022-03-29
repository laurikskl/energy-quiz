package server.Activity;

import commons.Activity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class ActivityConfig {

    /**
     * Setting up the database. Reading all the JSON activities files
     * and inserting the activities into the database.
     * Database is also storing a path to the image for the activity.
     *
     * @param repository
     * @return
     */
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
                        //Get the content of the file to String
                        String jsonFileToString = Files.readString(Path.of(f.getPath()));
                        //Read the activity from the current json file
                        Activity activity = Activity.JSONActivityReader(jsonFileToString);
                        //Add the path for the image
                        String path = file.toString() + "\\" + beginning;
                        if (Files.exists(Path.of(path + "jpg"))) {
                            File image = new File(path + "jpg");
                            byte[] bytes = Files.readAllBytes(image.toPath());
                            activity.setImageContent(bytes);
                        }
                        if (Files.exists(Path.of(path + "png"))) {
                            File image = new File(path + "png");
                            byte[] bytes = Files.readAllBytes(image.toPath());
                            activity.setImageContent(bytes);
                        }
                        if (Files.exists(Path.of(path + "jpeg"))) {
                            File image = new File(path + "jpeg");
                            byte[] bytes = Files.readAllBytes(image.toPath());
                            activity.setImageContent(bytes);
                        }
                        activitiesList.add(activity);
                    }
                }
            }
            repository.saveAll(activitiesList);
        };
    }
}
