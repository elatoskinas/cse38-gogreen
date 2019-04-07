package frontend.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXProgressBar;
import data.Achievement;
import data.User;
import data.UserAchievement;
import frontend.gui.General;
import frontend.gui.Main;
import frontend.gui.NavPanel;
import frontend.gui.ProfilePageLogic;
import frontend.gui.StageSwitcher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;


public class ProfilePageController implements Initializable {

    private static User thisUser;

    @FXML
    ImageView profilePicture;

    @FXML
    JFXHamburger menu;

    @FXML
    JFXDrawer drawer;

    @FXML
    AnchorPane mainPane;

    @FXML
    AnchorPane headerPane;

    @FXML
    private Label userName;

    @FXML
    private Label firstName;

    @FXML
    private Label lastName;

    @FXML
    private Label age;

    @FXML
    private Label email;

    @FXML
    private Label lastseen;

    @FXML
    private Label score;

    @FXML
    private Label level;

    @FXML
    private JFXButton editProfile;

    @FXML
    private JFXProgressBar levelProgress;

    @FXML
    private VBox com;

    @FXML
    private VBox incom;

    @FXML
    private HBox badgeZone;

    public static void setUser(User user) {
        thisUser = user;
    }

    /**
     Checks completed Achievements.
     */
    public static boolean isComplete(Achievement achievement) {
        for (UserAchievement userAchievement : thisUser.getProgress().getAchievements()) {
            if (achievement.getId() == userAchievement.getId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        levelProgress.setProgress(
                (Double.parseDouble(Integer.toString(thisUser.getProgress().getLevel()))) / 8.0
        );

        userName.setText(thisUser.getUsername());
        firstName.setText(thisUser.getFirstName());
        lastName.setText(thisUser.getLastName());
        email.setText(thisUser.getEmail());
        age.setText(thisUser.getAge() + "");
        lastseen.setText(thisUser.getLastLoginDate().toString());
        level.setText("Level: " + (thisUser.getProgress().getLevel()));
        score.setText("Total\nCarbon\nSaved: " + thisUser.getTotalCarbonSaved());
        profilePicture.setImage(new Image("avatars/" + thisUser.getAvatar() + ".jpg"));

        editProfile.setOnAction(e -> {

            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);

            FXMLLoader loader = new FXMLLoader(
                    Main.class.getResource("/frontend/fxmlPages/EditProfilePopUp.fxml"));
            Parent popup = null;
            try {
                popup = loader.load();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            EditProfilePopUpController controller = loader.getController();

            Scene scene = new Scene(popup,
                    General.getBounds()[0] / 2, General.getBounds()[1] / 2);
            stage.setScene(scene);
            stage.show();
            stage.toFront();
        });

        //        ObservableValue<Number> level = ObservableValue < Number > (5);
        //        levelProgress.progressProperty().bind(thisUser.getProgress().getLevel());

        try {
            NotificationPanelController.addNotificationPanel(headerPane, mainPane);
            StageSwitcher.homeDrawer = NavPanel.addNavPanel(mainPane, headerPane, menu);
        } catch (IOException e) {
            e.printStackTrace();
        }


        ProfilePageController.setUser(thisUser);

        // for every completed achievement module  is created
        // and added to a VBox small pics might be added later
        for (int i = 0; i < thisUser.getProgress().getAchievements().size(); i++) {

            count++;
            HBox hbox = new HBox();
            hbox.setSpacing(10.0);
            ImageView achievementimage = new ImageView();
            Image path = new Image("achievementsimages/" + thisUser.getProgress()
                    .getAchievements().get(i).getId() + ".png");
            achievementimage.setFitHeight(32);
            achievementimage.setFitWidth(32);
            achievementimage.setImage(path);
            Text name = new Text(i + 1 + ") " + ProfilePageLogic.getNameString(
                    thisUser.getProgress().getAchievements().get(i)));
            name.setFill(Color.GREEN);
            Text bonus = new Text(",Got: " + ProfilePageLogic.getBonusString(
                    thisUser.getProgress().getAchievements().get(i)) + " Points");
            Text date = new Text(", Completed On: " + ProfilePageLogic.getDateString(
                    thisUser.getProgress().getAchievements().get(i)) + ".");
            hbox.getChildren().addAll(achievementimage, name, bonus, date);
            com.getChildren().add(hbox);
        }

        if (count == 0) {
            Label noachiements = new Label("No completed achievements yet");
            com.getChildren().add(noachiements);
        }

        for (Achievement a : ProfilePageLogic.getList()) {
            HBox hbox = new HBox();
            hbox.setSpacing(10.0);
            if (!isComplete(a)) {
                ImageView achievementimage1 = new ImageView();
                Image path1 = new Image("achievementsimages/8.png");
                achievementimage1.setFitHeight(32);
                achievementimage1.setFitWidth(32);
                achievementimage1.setImage(path1);
                Text name = new Text(a.getName());
                Text points = new Text(",Complete to Get: " + a.getBonus() + " points.");
                hbox.getChildren().addAll(achievementimage1, name, points);
                incom.getChildren().add(hbox);
            }
        }
    }

        int levelcount = 1;
        for (int i = 1; i <= (thisUser.getProgress().getLevel()); i++) {

            ImageView badgeimage = new ImageView();
            Image path = new Image("badges/" + levelcount + ".png");
            badgeimage.setFitHeight(150);
            badgeimage.setFitWidth(150);
            badgeimage.setImage(path);
            badgeZone.getChildren().add(badgeimage);
            levelcount++;
        }

        addCompletedAchievements(com);
        addPendingAchievements(incom);


    }

}


        Image badgeimg = new Image(ProfilePageLogic.getBadge(thisUser));

        badge.setImage(badgeimg);

    }


