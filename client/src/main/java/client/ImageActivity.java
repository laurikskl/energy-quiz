package client;

import commons.Activity;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.ByteArrayInputStream;

/**
 * Helper class to load Activities with Images
 */
public class ImageActivity {

    private String id;
    private String name;
    private Long powerConsumption;
    private String source;
    private ImageView imageView;
    private Button button;

    private EventHandler<MouseEvent> eventHandler1;
    private EventHandler<MouseEvent> eventHandler2;

    /**
     * @param activity Activity to load image from
     */
    public ImageActivity(Activity activity) {
        this.id = activity.getId();
        this.name = activity.getName();
        this.powerConsumption = activity.getPowerConsumption();
        this.source = activity.getSource();

        this.button = new Button("Show");

        this.eventHandler1 = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (imageView == null) {
                    imageView = new ImageView(new Image(new ByteArrayInputStream(activity.getImageContent())));
                    imageView.setPreserveRatio(true);
                    imageView.setFitHeight(100);
                }

                button.setText("");
                button.setGraphic(imageView);

                button.onMouseClickedProperty().set(eventHandler2);
            }
        };

        this.eventHandler2 = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button.setText("Show");
                button.setGraphic(null);
                button.onMouseClickedProperty().set(eventHandler1);
            }
        };

        this.button.onMouseClickedProperty().set(eventHandler1);
    }

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @return powerConsumption
     */
    public Long getPowerConsumption() {
        return powerConsumption;
    }

    /**
     * @return source
     */
    public String getSource() {
        return source;
    }

    /**
     * @return button
     */
    public Button getButton() {
        return button;
    }

}