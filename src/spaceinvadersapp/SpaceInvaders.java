package spaceinvadersapp;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext; 
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SpaceInvaders extends Application {
    private final int WIDTH = 1000, HEIGHT = 800, TAILLE_JOUEUR = 50, TAILLE_ALIEN = 40;// hauteur, largeur de la fenetre + la taille du joueur et les aliens 
    private final Image LEVEL2 = new Image("/Images/level2.png");//image qui s'affiche au début de la 2eme partie 
    private final Image JOUEUR_IMG = new Image("/Images/player.png");//image du joueur 
    private final Image GAME_OVER = new Image("/Images/gameover.png");//image qui s'affiche apres la mort du joueur 3 fois 
    private final Image YOU_WIN = new Image("/Images/youwin.png");//image qui s'affiche apres la fin du partie 2
    private final java.net.URL shot = getClass().getResource("/sound_effects/shot.mp3");// le son de shoot
    private final Media shotMedia = new Media(shot.toString());
    private MediaPlayer mediaPlayer;
    private final java.net.URL game = getClass().getResource("/sound_effects/game.mp3");
    private final Media gameMedia = new Media(game.toString());
    private MediaPlayer gamePlayer = new MediaPlayer(gameMedia);
    private final java.net.URL gameOver = getClass().getResource("/sound_effects/gameover.mp3");
    private final Media gameOverMedia = new Media(gameOver.toString());
    private MediaPlayer gameover = new MediaPlayer(gameOverMedia);
    private final java.net.URL win = getClass().getResource("/sound_effects/youwin.mp3");
    private final java.net.URL boom = getClass().getResource("/sound_effects/boom.mp3");
    private final Media boomMedia = new Media(boom.toString());
    private final Media youWinMedia = new Media(win.toString());
    private MediaPlayer boomSound = new MediaPlayer(boomMedia);
    private MediaPlayer youwin = new MediaPlayer(youWinMedia);
    private Text scoreText;
    private Text levelText;
    private Text vieText;
    private Rectangle textBackground;
    private int niveau = 1;
    private Canvas canvas;
    private Canvas jeu;
    private Pane root;
    private Scene scene;
    private GraphicsContext gc;
    private GraphicsContext menuGc;
    private Fusee player;
    private int tirAlien = 10, cmpt = 0;
    private List<Alien> listAlien1 = new ArrayList<Alien>();
    private List<Alien> listAlien2 = new ArrayList<Alien>();
    private List<Alien> listAlien3 = new ArrayList<Alien>();
    private List<Tir> listTirPlayer = new ArrayList<>();
    private List<Tir> listTirAlien = new ArrayList<>();
    private boolean youWin = false, updatedLevel = false;
    private boolean bonus1=false,bonus2=false,bonus3=false; 
    Timeline timeline ;
    // fonction qui recommence le taimer apres le changement vers la 2eme partie 
    void afterLevel1()
    {
    	//vider l'ecran
    	 gc.setFill(Color.grayRgb(20));
         gc.fillRect(0, 0, WIDTH, WIDTH);
         //afficher le joueur 
         player.affiche(gc);
         listTirPlayer.clear();
         listTirAlien.clear();
         //afficher les deux ligne des Aliens 
         listAlien1.clear();
         listAlien2.clear();
         listAlien3.clear();
         for (int i = 0; i<6; i++){
             int randomAlien = (int)(Math.random() * ((9 - 0) + 1)) + 0;
             listAlien1.add(new Alien(20+i*100, 50, TAILLE_ALIEN, new Image("/Images/" + randomAlien + ".png")));
             listAlien1.get(i).affiche(gc);
         }
         for (int i = 0; i<6; i++){
             int randomAlien = (int)(Math.random() * ((9 - 0) + 1)) + 0;
             listAlien2.add(new Alien(20+ TAILLE_ALIEN +i*100, 120, TAILLE_ALIEN, new Image("/Images/" + randomAlien + ".png")));
             listAlien2.get(i).affiche(gc);
         }
         for (int i = 0; i<6; i++){
             int randomAlien = (int)(Math.random() * ((9 - 0) + 1)) + 0;
             listAlien3.add(new Alien(20+i*100, 180, TAILLE_ALIEN, new Image("/Images/" + randomAlien + ".png")));
             listAlien3.get(i).affiche(gc);
         }
         updatedLevel = true;
         //recommence le timeline
         timeline.play(); 
    }
    private void creerContenu(){
    	//création de la fenêtre du jeu 
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        root = new Pane(canvas);
        root.setPrefSize(WIDTH,HEIGHT);
        
        scene = new Scene(root);
        
        gc.setFill(Color.grayRgb(20));
        gc.fillRect(0, 0, WIDTH, WIDTH);
        
        player = new Fusee(WIDTH/2 - TAILLE_JOUEUR/2, HEIGHT - TAILLE_JOUEUR - 10, TAILLE_JOUEUR, JOUEUR_IMG);
        player.affiche(gc);
        
        //ajouter 5 aliens déffirents selon le numéro de l'image aléatoire 
        /*for (int i = 0; i<6; i++){
            int randomAlien = (int)(Math.random() * ((9 - 0) + 1)) + 0;
            System.out.println(randomAlien);
            listAlien.add(new Alien(20+i*100, 50, TAILLE_ALIEN, new Image("/Images/" + randomAlien + ".png")));
            listAlien.get(i).affiche(gc);
        }*/
        for (int i = 0; i<6; i++){
            int randomAlien = (int)(Math.random() * ((9 - 0) + 1)) + 0;
            listAlien1.add(new Alien(20+i*100, 50, TAILLE_ALIEN, new Image("/Images/" + randomAlien + ".png")));
            listAlien1.get(i).affiche(gc);
        }
        for (int i = 0; i<6; i++){
            int randomAlien = (int)(Math.random() * ((9 - 0) + 1)) + 0;
            listAlien2.add(new Alien(20+ TAILLE_ALIEN +i*100, 120, TAILLE_ALIEN, new Image("/Images/" + randomAlien + ".png")));
            listAlien2.get(i).affiche(gc);
        }
        
        scene.setOnKeyPressed( e -> {
            switch (e.getCode()){
                case LEFT: if(!player.isDetruit())
                    player.moveLeft(gc, WIDTH);
                break;
                case RIGHT: if(!player.isDetruit())
                    player.moveRight(gc, WIDTH);
                break;
                case SPACE: 
                	if(!player.isExplosion()){
                    Tir tir = player.tir();
                    listTirPlayer.add(tir);
                    // play the sound of shoot 
                    mediaPlayer = new MediaPlayer(shotMedia);
                    mediaPlayer.play();
                    }
                    break;
            }
        });
        // play the sound of the game
        gamePlayer.setCycleCount(MediaPlayer.INDEFINITE);
        gamePlayer.play();     
        timeline = new Timeline(new KeyFrame(Duration.millis(50), e -> run()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        
    }
    
    private void run(){
        if (player.isDetruit()){
        	//si le joueur est détruit après 3 morts on affiche une image de game over et on arrete le timeline 
        	timeline.stop();
            gamePlayer.stop();
            gameover.play();
            gc.drawImage(GAME_OVER, 240, 220);
        }
        else if (youWin){
        	//si le joueur a gagner apres la 2eme partie on affiche une image de win
        	timeline.stop();
            gamePlayer.stop();
            youwin.play();
            gc.drawImage(YOU_WIN, 40, 40);
            
        }
        else{
        if(niveau == 2 && !updatedLevel){
            listAlien1.clear();
            listAlien2.clear();
            listAlien3.clear();
            for (int i = 0; i<6; i++){
                int randomAlien = (int)(Math.random() * ((9 - 0) + 1)) + 0;
                listAlien1.add(new Alien(20+i*100, 50, TAILLE_ALIEN, new Image("/Images/" + randomAlien + ".png")));
                listAlien1.get(i).affiche(gc);
            }
            for (int i = 0; i<6; i++){
                int randomAlien = (int)(Math.random() * ((9 - 0) + 1)) + 0;
                listAlien2.add(new Alien(20+ TAILLE_ALIEN +i*100, 120, TAILLE_ALIEN, new Image("/Images/" + randomAlien + ".png")));
                listAlien3.get(i).affiche(gc);
            }
            for (int i = 0; i<6; i++){
                int randomAlien = (int)(Math.random() * ((9 - 0) + 1)) + 0;
                listAlien3.add(new Alien(20+i*100, 50, TAILLE_ALIEN, new Image("/Images/" + randomAlien + ".png")));
                listAlien3.get(i).affiche(gc);
            }
            updatedLevel = true;
        }
        //tir
        if(tirAlien >= 10/niveau){
        	if(listAlien1.size()!=0)
        	{
        		int randomAlien1 = (int)(Math.random() * (((listAlien1.size()-1) - 0) + 1)) + 0;
                if(!listAlien1.get(randomAlien1).isDetruit()){
                    listTirAlien.add(listAlien1.get(randomAlien1).tir());
                    tirAlien = 0;
                }
        	}
        	if(listAlien2.size()!=0)
        	{

                int randomAlien2 = (int)(Math.random() * (((listAlien2.size()-1) - 0) + 1)) + 0;
                if(!listAlien2.get(randomAlien2).isDetruit()){
                    listTirAlien.add(listAlien2.get(randomAlien2).tir());
                    tirAlien = 0;
                }
        	}
        	if(listAlien3.size()!=0)
        	{
        		 if(niveau==2)
        	        {
        	        	int randomAlien3 = (int)(Math.random() * (((listAlien3.size()-1) - 0) + 1)) + 0;
        	            if(!listAlien3.get(randomAlien3).isDetruit()){
        	                listTirAlien.add(listAlien3.get(randomAlien3).tir());
        	                tirAlien = 0;
        	            }
        	        }
        	}
        
       
        
        }else tirAlien++;

        //affichage du alien apres l'explosion 
        for(int i=0; i<listAlien1.size(); i++){
        	//afficher l'image d'explosion pendant 3 seconds 
        	if(listAlien1.get(i).getDureeExplosion()<=20)
            listAlien1.get(i).update(gc);
        	else // apres 3 seconds on retire l'alien de la liste 
        	listAlien1.remove(i);
        }
        for(int i=0; i<listAlien2.size(); i++){
        	//afficher l'image d'explosion pendant 3 seconds 
        	if(listAlien2.get(i).getDureeExplosion()<=20)
            listAlien2.get(i).update(gc);
        	else // apres 3 seconds on retire l'alien de la liste 
        	listAlien2.remove(i);
        }
        for(int i=0; i<listAlien3.size(); i++){
        	//afficher l'image d'explosion pendant 3 seconds 
        	if(listAlien3.get(i).getDureeExplosion()<=20)
            listAlien3.get(i).update(gc);
        	else // apres 3 seconds on retire l'alien de la liste 
        	listAlien3.remove(i);
        }
        //supprimer le tir du joueur apres le depassement de la taille de l'ecran
        for(int i=0; i<listTirPlayer.size(); i++){
            if(listTirPlayer.get(i).getPosY() <= -30)
                listTirPlayer.remove(i);
        }
        //faire monter le tir du joueur 
        for(int i=0; i<listTirPlayer.size(); i++){
            listTirPlayer.get(i).affiche(gc);
            listTirPlayer.get(i).update(gc);
        }
      //supprimer le tir du joueur apres le depassement de la taille de l'ecran
        for(int i=0; i<listTirAlien.size(); i++){
            if(listTirAlien.get(i).getPosY() >= HEIGHT+20)
                listTirAlien.remove(i);
        }
        //faire descendre le tir de l'alien
        for(int i=0; i<listTirAlien.size(); i++){
            listTirAlien.get(i).affiche(gc);
            listTirAlien.get(i).update(gc);
        }
        //la collision de l'alien avec le tir du joueur 
        for (int i=0; i<listAlien1.size(); i++){
            for(int j=0; j<listTirPlayer.size(); j++){
                if(listAlien1.get(i).collision(listTirPlayer.get(j)) && !listAlien1.get(i).isDetruit()){
                	//détruire l'alien
                    listAlien1.get(i).setDetruit(true);
                    listAlien1.get(i).setExplosion(true);
                    //détruire le tir 
                    listTirPlayer.get(j).setdetruit();               
                    listTirPlayer.get(j).update(gc);
                    //supprimer le tir de la list des tirs
                    listTirPlayer.remove(j); 
                    //ajout du score
                    player.update(10);
                    //play the sound of explosion 
                    boomSound = new MediaPlayer(boomMedia);
                    boomSound.play();
                }
            }
        }
        for (int i=0; i<listAlien2.size(); i++){
            for(int j=0; j<listTirPlayer.size(); j++){
                if(listAlien2.get(i).collision(listTirPlayer.get(j)) && !listAlien2.get(i).isDetruit()){
                	//détruire l'alien
                    listAlien2.get(i).setDetruit(true);
                    listAlien2.get(i).setExplosion(true);
                    //détruire le tir 
                    listTirPlayer.get(j).setdetruit();               
                    listTirPlayer.get(j).update(gc);
                    //supprimer le tir de la list des tirs
                    listTirPlayer.remove(j); 
                    //ajout du score
                    player.update(10);
                    //play the sound of explosion 
                    boomSound = new MediaPlayer(boomMedia);
                    boomSound.play();
                }
            }
        }
        for (int i=0; i<listAlien3.size(); i++){
            for(int j=0; j<listTirPlayer.size(); j++){
                if(listAlien3.get(i).collision(listTirPlayer.get(j)) && !listAlien3.get(i).isDetruit()){
                	//détruire l'alien
                    listAlien3.get(i).setDetruit(true);
                    listAlien3.get(i).setExplosion(true);
                    //détruire le tir 
                    listTirPlayer.get(j).setdetruit();               
                    listTirPlayer.get(j).update(gc);
                    //supprimer le tir de la list des tirs
                    listTirPlayer.remove(j); 
                    //ajout du score
                    player.update(10);
                    //play the sound of explosion 
                    boomSound = new MediaPlayer(boomMedia);
                    boomSound.play();
                }
            }
        }
      //la collision du joueur avec le tir de l'alien 
        for (int i=0; i<listTirAlien.size(); i++){
            if (player.collision(listTirAlien.get(i))){
            	//détruire le joueuer 
                player.setDetruit(true);
                player.setExplosion(true);
                //afficher l'explosion
                player.affiche(gc);
                //play the sound of explosion 
                boomSound = new MediaPlayer(boomMedia);
                boomSound.play();
            }
        }
        // si les aliens arrivent au meme niveau Y du joueur on détruit le joueur 
        if(listAlien1.size()!=0)
    	{
        	if (listAlien1.get(0).getPosY() + listAlien1.get(0).getSize() >= player.getPosY()){
                player.setDetruit(true);
                player.setExplosion(true);
                player.affiche(gc);
            }
    	}
        
        if(listAlien2.size()!=0)
    	{
        	if (listAlien2.get(0).getPosY() + listAlien2.get(0).getSize() >= player.getPosY()){
                player.setDetruit(true);
                player.setExplosion(true);
                player.affiche(gc);
            }
    	}
        if(listAlien3.size()!=0)
    	{
        	if(niveau==2)	
                if (listAlien3.get(0).getPosY() + listAlien3.get(0).getSize() >= player.getPosY()){
                    player.setDetruit(true);
                    player.setExplosion(true);
                    player.affiche(gc);
                }
    	}
        
        //faire bouger les aliens en bas 
        if(listAlien1.size()!=0)
        if(listAlien1.get(listAlien1.size()-1).getPosX() >= WIDTH - TAILLE_ALIEN -20){
            Alien.setDirection(0);
            for (int i=0; i<listAlien1.size(); i++){
            	gc.setFill(Color.grayRgb(20));
                gc.fillRect(listAlien1.get(i).getPosX(), listAlien1.get(i).getPosY(), listAlien1.get(i).getSize(), listAlien1.get(i).getSize());
                listAlien1.get(i).setPosY(listAlien1.get(i).getPosY()+15);
            }
            for (int i=0; i<listAlien2.size(); i++){
            	gc.setFill(Color.grayRgb(20));
                gc.fillRect(listAlien2.get(i).getPosX(), listAlien2.get(i).getPosY(), listAlien2.get(i).getSize(), listAlien2.get(i).getSize());
                listAlien2.get(i).setPosY(listAlien2.get(i).getPosY()+15);
            }
            if(niveau==2)	
            for (int i=0; i<listAlien3.size(); i++){
            	gc.setFill(Color.grayRgb(20));
                gc.fillRect(listAlien3.get(i).getPosX(), listAlien3.get(i).getPosY(), listAlien3.get(i).getSize(), listAlien3.get(i).getSize());
                listAlien3.get(i).setPosY(listAlien3.get(i).getPosY()+15);
            }
        }
        else if(listAlien1.get(0).getPosX() <= 20){
            Alien.setDirection(1);
            for (int i=0; i<listAlien1.size(); i++){
            	gc.setFill(Color.grayRgb(20));
                gc.fillRect(listAlien1.get(i).getPosX(), listAlien1.get(i).getPosY(), listAlien1.get(i).getSize(), listAlien1.get(i).getSize());
                listAlien1.get(i).setPosY(listAlien1.get(i).getPosY()+15);
            }
            for (int i=0; i<listAlien2.size(); i++){
            	gc.setFill(Color.grayRgb(20));
                gc.fillRect(listAlien2.get(i).getPosX(), listAlien2.get(i).getPosY(), listAlien2.get(i).getSize(), listAlien2.get(i).getSize());
                listAlien2.get(i).setPosY(listAlien2.get(i).getPosY()+15);
            }
            if(niveau==2)	
            for (int i=0; i<listAlien3.size(); i++){
            	gc.setFill(Color.grayRgb(20));
                gc.fillRect(listAlien3.get(i).getPosX(), listAlien3.get(i).getPosY(), listAlien3.get(i).getSize(), listAlien3.get(i).getSize());
                listAlien3.get(i).setPosY(listAlien3.get(i).getPosY()+15);
            }
        }
        if(listAlien2.size()!=0)
        if(listAlien2.get(listAlien2.size()-1).getPosX() >= WIDTH - TAILLE_ALIEN -20){
            Alien.setDirection(0);
            for (int i=0; i<listAlien1.size(); i++){
            	gc.setFill(Color.grayRgb(20));
                gc.fillRect(listAlien1.get(i).getPosX(), listAlien1.get(i).getPosY(), listAlien1.get(i).getSize(), listAlien1.get(i).getSize());
                listAlien1.get(i).setPosY(listAlien1.get(i).getPosY()+15);
            }
            for (int i=0; i<listAlien2.size(); i++){
            	gc.setFill(Color.grayRgb(20));
                gc.fillRect(listAlien2.get(i).getPosX(), listAlien2.get(i).getPosY(), listAlien2.get(i).getSize(), listAlien2.get(i).getSize());
                listAlien2.get(i).setPosY(listAlien2.get(i).getPosY()+15);
            }
            if(niveau==2)	
            for (int i=0; i<listAlien3.size(); i++){
            	gc.setFill(Color.grayRgb(20));
                gc.fillRect(listAlien3.get(i).getPosX(), listAlien3.get(i).getPosY(), listAlien3.get(i).getSize(), listAlien3.get(i).getSize());
                listAlien3.get(i).setPosY(listAlien3.get(i).getPosY()+15);
            }
        }
        else if(listAlien2.get(0).getPosX() <= 20){
            Alien.setDirection(1);
            for (int i=0; i<listAlien1.size(); i++){
            	gc.setFill(Color.grayRgb(20));
                gc.fillRect(listAlien1.get(i).getPosX(), listAlien1.get(i).getPosY(), listAlien1.get(i).getSize(), listAlien1.get(i).getSize());
                listAlien1.get(i).setPosY(listAlien1.get(i).getPosY()+15);
            }
            for (int i=0; i<listAlien2.size(); i++){
            	gc.setFill(Color.grayRgb(20));
                gc.fillRect(listAlien2.get(i).getPosX(), listAlien2.get(i).getPosY(), listAlien2.get(i).getSize(), listAlien2.get(i).getSize());
                listAlien2.get(i).setPosY(listAlien2.get(i).getPosY()+15);
            }
            if(niveau==2)	
            for (int i=0; i<listAlien3.size(); i++){
            	gc.setFill(Color.grayRgb(20));
                gc.fillRect(listAlien3.get(i).getPosX(), listAlien3.get(i).getPosY(), listAlien3.get(i).getSize(), listAlien3.get(i).getSize());
                listAlien3.get(i).setPosY(listAlien3.get(i).getPosY()+15);
            }
        }
    if(listAlien3.size()!=0) 
    if(niveau==2)	
    if(listAlien3.get(listAlien3.size()-1).getPosX() >= WIDTH - TAILLE_ALIEN -20){
        Alien.setDirection(0);
        for (int i=0; i<listAlien1.size(); i++){
        	gc.setFill(Color.grayRgb(20));
            gc.fillRect(listAlien1.get(i).getPosX(), listAlien1.get(i).getPosY(), listAlien1.get(i).getSize(), listAlien1.get(i).getSize());
            listAlien1.get(i).setPosY(listAlien1.get(i).getPosY()+15);
        }
        for (int i=0; i<listAlien2.size(); i++){
        	gc.setFill(Color.grayRgb(20));
            gc.fillRect(listAlien2.get(i).getPosX(), listAlien2.get(i).getPosY(), listAlien2.get(i).getSize(), listAlien2.get(i).getSize());
            listAlien2.get(i).setPosY(listAlien2.get(i).getPosY()+15);
        }
        if(niveau==2)	
        for (int i=0; i<listAlien3.size(); i++){
        	gc.setFill(Color.grayRgb(20));
            gc.fillRect(listAlien3.get(i).getPosX(), listAlien3.get(i).getPosY(), listAlien3.get(i).getSize(), listAlien3.get(i).getSize());
            listAlien3.get(i).setPosY(listAlien3.get(i).getPosY()+15);
        }
    }
    else if(listAlien3.get(0).getPosX() <= 20){
        Alien.setDirection(1);
        for (int i=0; i<listAlien1.size(); i++){
        	gc.setFill(Color.grayRgb(20));
            gc.fillRect(listAlien1.get(i).getPosX(), listAlien1.get(i).getPosY(), listAlien1.get(i).getSize(), listAlien1.get(i).getSize());
            listAlien1.get(i).setPosY(listAlien1.get(i).getPosY()+15);
        }
        for (int i=0; i<listAlien2.size(); i++){
        	gc.setFill(Color.grayRgb(20));
            gc.fillRect(listAlien2.get(i).getPosX(), listAlien2.get(i).getPosY(), listAlien2.get(i).getSize(), listAlien2.get(i).getSize());
            listAlien2.get(i).setPosY(listAlien2.get(i).getPosY()+15);
        }
        if(niveau==2)	
        for (int i=0; i<listAlien3.size(); i++){
        	gc.setFill(Color.grayRgb(20));
            gc.fillRect(listAlien3.get(i).getPosX(), listAlien3.get(i).getPosY(), listAlien3.get(i).getSize(), listAlien3.get(i).getSize());
            listAlien3.get(i).setPosY(listAlien3.get(i).getPosY()+15);
        }
    }
    
        //si tous les alliens sont détruit en fait un changement vers le niveau 2
        /*for (int i=0; i<listAlien.size(); i++){
            if(!listAlien.get(i).isDetruit())
                break;
            if (i == listAlien.size()-1){
                if (niveau == 2){
                    youWin = true;
                    
                }else if (niveau == 1)
                	{
                	player.update(1000);
                	niveau = 2;
                	 timeline.stop(); 
                	 gc.setFill(Color.grayRgb(20));
                     gc.fillRect(0, 0, WIDTH, WIDTH);
                     gc.drawImage(LEVEL2, 250, 250);
                     Text scorebonus = new Text(350,600,"Bonus +1000");
                     scorebonus.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
                     scorebonus.setFill(Color.RED);
                     root.getChildren().add(scorebonus);
                     new java.util.Timer().schedule( 
                    	        new java.util.TimerTask() {
                    	            @Override
                    	            public void run() {
                    	            	scorebonus.setText("");
                    	                afterLevel1();
                    	            }
                    	        }, 
                    	        5000 
                    	);
                     
                	}
            }
        }*/
    if(listAlien1.size()==0 || listAlien2.size() == 0)
    {
    	 if (niveau == 1)
    	 {
    		 if(bonus1==false && listAlien1.size()==0)
    		 {
    			 player.update(50);
    			 bonus1=true;
    		 }
    		 if(bonus2==false && listAlien2.size()==0)
    		 {
    			 player.update(50);
    			 bonus2=true;
    		 }
    		 if(listAlien1.size()==0 && listAlien2.size() == 0)
    		 {
    			 player.update(1000);
    	         	niveau = 2;
    	         	 timeline.stop(); 
    	         	 gc.setFill(Color.grayRgb(20));
    	              gc.fillRect(0, 0, WIDTH, WIDTH);
    	              gc.drawImage(LEVEL2, 250, 250);
    	              Text scorebonus = new Text(350,600,"Bonus +1000");
    	              scorebonus.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
    	              scorebonus.setFill(Color.RED);
    	              root.getChildren().add(scorebonus);
    	              new java.util.Timer().schedule( 
    	             	        new java.util.TimerTask() {
    	             	            @Override
    	             	            public void run() {
    	             	            	bonus1=false;
    	             	            	bonus2=false;
    	             	            	scorebonus.setText("");
    	             	                afterLevel1();
    	             	            }
    	             	        }, 
    	             	        5000 
    	             	);
    		 }
         	
              
         	}
    		 else if (niveau == 2){
    			 if(bonus1==false && listAlien1.size()==0)
        		 {
        			 player.update(50);
        			 bonus1=true;
        		 }
        		 if(bonus2==false && listAlien2.size()==0)
        		 {
        			 player.update(50);
        			 bonus2=true;
        		 }
        		 if(bonus3==false && listAlien2.size()==0)
        		 {
        			 player.update(50);
        			 bonus3=true;
        		 }
    			 if(listAlien1.size()==0 && listAlien2.size() == 0 && listAlien3.size()==0)
        		 {
    				 player.update(1000);
    				 youWin = true;
        		 }
                 
    	 }
             
    }
    
        scoreText = new Text(10, 20,"Score : " + player.getScore());
        scoreText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        scoreText.setFill(Color.RED);
        levelText = new Text(WIDTH/2 - 60, 20,"Level : " + niveau);
        levelText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        levelText.setFill(Color.WHITE);
        vieText = new Text(WIDTH - 100, 20,"Vie : 0" + player.getVie());
        vieText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        vieText.setFill(Color.WHITE);
        textBackground = new Rectangle(WIDTH,50,Color.grayRgb(20));
        root.getChildren().add(textBackground);
        root.getChildren().add(scoreText);
        root.getChildren().add(levelText);
        root.getChildren().add(vieText);
      }
    }
    
    @Override
    public void start(Stage primaryStage) {
        creerContenu();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Space Invaders");
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
