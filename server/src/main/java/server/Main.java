/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package server;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Temporary comment for checkstyle.
 */

@SpringBootApplication
@EntityScan(basePackages = {"commons", "server"})
public class Main {

    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(Main.class, args);
    }

    /**
     * Restart the server
     * @return true if restarting, false otherwise
     */
    public static Boolean restart() {
        ApplicationArguments args = context.getBean(ApplicationArguments.class);

        //restart server on a separate thread
        Thread thread = new Thread(() -> {
            context.close();
            context = SpringApplication.run(Main.class, args.getSourceArgs());
        });

        try {
            thread.setDaemon(false);
            thread.start();
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}