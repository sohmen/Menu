package Menu;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


import javafx.scene.text.*;
import javafx.util.Duration;



import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GameMenu extends Application{
    Stage window;
    Scene sceneplay;

    private Gamemenu gamemenu; // on veut instancier notre menu





    @Override
    public void start(Stage primaryStage) throws Exception{


        // on creer la base pour mettre l'image

        Pane root = new Pane();
        root.setPrefSize(1200, 800); // choisi la taille qu'on veut lui mettre

        InputStream background = Files.newInputStream(Paths.get("res/images/Background.png")); // charger l'image
        Image monImage = new Image(background); //on essaye de charger l'image background et pour ça on a besoin de recup l'image avec le input
        background.close(); // on le ferme pour pas que les autres programmes y ai accès



        ImageView imageView = new ImageView(monImage); // va permettre de l'afficher
        imageView.setFitHeight(800); // si notre image est plus petit ou plus grand on peut la recadrer
        imageView.setFitWidth(1200);
        gamemenu = new Gamemenu(); // on l'instancie ici
        root.getChildren().addAll(imageView, gamemenu); // on l'attache a la base donc on a le background et on viens lui ajouter le menu par dessus



        Scene scene = new Scene(root);



        // on veut acceder au menu via une touche du clavier
        scene.setOnKeyPressed(event -> {

            if(event.getCode() == KeyCode.ESCAPE){      // si l'utilisateur presse ESCAPE
                if(!gamemenu.isVisible()){ // et que le menu avec les bouttons n'est pas visible
                    FadeTransition ft = new FadeTransition(Duration.seconds(0.5), gamemenu); // il va faire une animation dans le gamemenu
                    ft.setFromValue(0); // l'animation changera l'oppacité de 0 a 1 pendant les 0,5 seconde
                    ft.setToValue(1);

                    gamemenu.setVisible(true); //donc on le remet visible
                    ft.play(); // la on lance l'animation
                }


            }
            else if(event.getCode() != KeyCode.ESCAPE){ // pour qu'on puisse le controler d'une seul touche
                gamemenu.setVisible(true);

            }
            else {
                FadeTransition ft = new FadeTransition(Duration.seconds(0.5), gamemenu); // si le menu est visible et qu'on appuie sur esc on veut qu'il soit invisible
                ft.setFromValue(1);
                ft.setToValue(0);
                ft.setOnFinished(event1 -> gamemenu.setVisible(false));
                ft.play();
            }
        });

        primaryStage.setScene((scene));
        primaryStage.show(); // on l'affiche
        primaryStage.setResizable(false);






    }

    // design du menu




    // on assigne chaque bouton et on ajoute des menus dans les menus :D

    private class Gamemenu extends Parent { // la classe parents c'est la classe qui va contenir toutes les classes children

        public Gamemenu() { // contructeur
            VBox menu0 = new VBox(10); // donc on creer les moules
            VBox menu1 = new VBox(10); // les chiffres donnent l'espacement entre les deux "boites" (bouton)
            // donc menu0 c'est le menu de base et menu1 c'est un autre menu dans le menu genre exemple
            // si on veut choisir la difficulté on rentre dans le 1er menu puis dans le deuxieme on aura ia simple ia difficile



            menu0.setTranslateX(500);
            menu0.setTranslateY(350);

            menu1.setTranslateX(500);
            menu1.setTranslateY(350);

            final int offset = 400; // valeur pour faire l'animation du décalage du menu
            menu1.setTranslateX(offset);





            MenuButton btnPlay = new MenuButton("PLAY"); // création du bouton play
            btnPlay.setOnMouseClicked(event -> {



                FadeTransition ft = new FadeTransition(Duration.seconds(0.5), this); // animation random
                ft.setFromValue(1);
                ft.setToValue(0);
                ft.setOnFinished(event1 -> this.setVisible(false));
                ft.play(); // lancer l'animation





            });

            MenuButton btnOptions = new MenuButton("Options"); // on crée le bouton options sauf que si on va dans les options il nous faut un new menu  et pour revnir en arriere il nous faut un bouton back
            btnOptions.setOnMouseClicked(event -> {
                getChildren().add(menu1); // on ajoute menu 1 après options donc menu 1 sera visible
                menu0.setVisible(false);
                getChildren().remove(menu0);
                menu1.setVisible(true);

            });

            //bouton back pour revenir du menu options
            MenuButton btnBack = new MenuButton("Back");
            btnBack.setOnMouseClicked(event -> {
                getChildren().add(menu0); //on ajoute le menu de base
                menu1.setVisible(false);
                getChildren().remove(menu1);
                menu0.setVisible(true);



            });

            MenuButton btnSound = new MenuButton("Sound");
            MenuButton btnHardMode = new MenuButton("HARD MODE");
            MenuButton btnEasymode = new MenuButton("easy mode");

            menu1.getChildren().addAll(btnSound, btnEasymode, btnHardMode, btnBack);



            MenuButton btnExit = new MenuButton("Exit");
            btnExit.setOnMouseClicked(event -> {
                System.exit(0);
            });









            menu0.getChildren().addAll(btnPlay, btnOptions, btnExit); // on ajoute au menu de basse les boutons



            Rectangle rectanglebackground = new Rectangle(1200, 800); // on créer le background du menu
            rectanglebackground.setFill(Color.GREY);
            rectanglebackground.setOpacity(0.4);

            getChildren().addAll(rectanglebackground, menu0); // on ajoute le background au menu




        }

    }
















    //les boutons (création du moule) on dit ce qu'est un bouton


    private static class MenuButton extends StackPane{ //stackPane c'est pour permettre de mettre le bouton au dessus de l'image
        private Text text; //le texte sur le bouton

        public MenuButton(String name){ // le constructeur va prendre comme un argument un nom qui est le nom du bouton
            text = new Text(name);

            text.setFont(text.getFont().font(20)); //on donne une police d'écriture et la taille
            text.setFill(Color.WHITE); // pour que quand on bouge dans le menu le bouton change de couleur

            Rectangle rectanglebouton = new Rectangle(300, 25); // (moule) créer le rectangle pour les boutons
            rectanglebouton.setOpacity(0.6); // opacité du bouton
            rectanglebouton.setFill(Color.BLACK); // couleur du bouton

            setAlignment(Pos.CENTER_LEFT); // permet de centrer les boutons
            //setRotate(-0.5);  // faire une legere rotation du menu
            getChildren().addAll(rectanglebouton, text); //sert a le stack au dessus du background


            // on va faire un event pour que quand on mets la souris sur un bouton de menu celui ci change de couleur et ressors un peu

            setOnMouseEntered(event -> {
                rectanglebouton.setTranslateX(20);
                text.setTranslateX(20);
                rectanglebouton.setFill(Color.WHITE);
                text.setFill(Color.BLACK);
            });
            // une fois que la souris n'est plus dessus on doit refaire le sens inverse =D

            setOnMouseExited(event -> {
                rectanglebouton.setTranslateX(0);
                text.setTranslateX(0);
                rectanglebouton.setFill(Color.BLACK);
                text.setFill(Color.WHITE);
            });


            // pour dire a l'utilisateur qu'il a cliquer sur l'élément

            DropShadow drop = new DropShadow(50, Color.BLUE);
            drop.setInput(new Glow());
            setOnMousePressed(event -> setEffect(drop));
            // puis on enleve la couleur
            setOnMouseReleased(event -> setEffect(null));



        }


    }

    public static void main(String[] args) {
        launch(args); // lancer l'application
    }
}
