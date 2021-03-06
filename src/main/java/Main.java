import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends Application {

    private CrawlerTest c;
    private Scene scene1, scene2;
    private String urls;
    private Text urlsText;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.c = new CrawlerTest();
        primaryStage.setTitle("WebCrawler");

        //layout1
        TextField searchInput = new TextField();
        TextField keyword = new TextField();
        Label searchLabel = new Label("Website URL");
        searchLabel.setLabelFor(searchInput);
        Label keywordLabel = new Label("Search for keyword");
        keywordLabel.setLabelFor(keyword);
        Button searchButton = new Button();
        searchButton.setText("Search!");
        searchButton.setOnAction(e -> {
            if (this.checkURL(searchInput.getText())) {
                this.urls = this.c.run(searchInput.getText(), keyword.getText());
                primaryStage.setScene(scene2);
                this.urlsText.setText(this.urls);
            }
            else {
                AlertError.display("Error!", "Entered URL format is incorrect");
            }
        });

        FlowPane container = new FlowPane();
        container.getChildren().addAll(searchInput, keyword, searchButton);
        VBox layout1 = new VBox(10);
        layout1.setPadding(new Insets(20, 20, 20, 20));
        layout1.getChildren().addAll(searchLabel, searchInput, keywordLabel, keyword, searchButton);
        scene1 = new Scene(layout1, 300, 250);

        //layout 2
        this.urlsText = new Text(10, 50, this.urls);
        this.urlsText.setFont(new Font(20));
        Label urlsLabel = new Label("URLs found: ");
        urlsLabel.setLabelFor(urlsText);
        Button backButton = new Button();
        backButton.setText("Back");
        backButton.setOnAction(e -> primaryStage.setScene(scene1));
        Button serializeButton = new Button();
        serializeButton.setText("Save as .txt File");
        serializeButton.setOnAction(e -> {
                c.serialize();
                AlertError.display("Success!", "Urls Saved Successfully!");
        });

        VBox layout2 = new VBox(10);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setContent(urlsText);
        layout2.setPadding(new Insets(20, 20, 20, 20));
        layout2.getChildren().addAll(urlsLabel, urlsText, scrollPane, backButton, serializeButton);
        scene2 = new Scene(layout2, 1000, 500);

        primaryStage.setScene(scene1);
        primaryStage.show();
    }

    private boolean checkURL(String input) {
        Pattern pattern = Pattern.compile("((([A-Za-z]{3,9}:(?:\\/\\/)?)(?:[-;:&=\\+\\$,\\w]+@)?[A-Za-z0-9.-]+|(?:www.|[-;:&=\\+\\$,\\w]+@)[A-Za-z0-9.-]+)((?:\\/[\\+~%\\/.\\w-_]*)?\\??(?:[-\\+=&;%@.\\w_]*)#?(?:[\\w]*))?)");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
