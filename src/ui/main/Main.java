
package ui.main;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application{
    public void start(Stage stage) throws IOException{
        
        Parent principal = FXMLLoader.load(getClass().getResource("Principal.fxml"));
        
        // Titulo da Janela
        stage.setTitle("    JavaFXML Projeto");
        stage.getIcons().add(new Image("/Imagens/icone.png"));
        
        //Maximizar a janela
        stage.setMaximized(true);
        
        Scene cena = new Scene(principal);
        stage.setScene(cena);
        stage.show();
    }
    
    public static void main(String args[]){
        launch(args);
    }
}
