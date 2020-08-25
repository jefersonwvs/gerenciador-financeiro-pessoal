package aplicacao;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Programa extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage palcoInicial) {
    
        try {
            FXMLLoader carregador = new FXMLLoader(getClass().getResource("/interfacegrafica/LoginView.fxml"));
            AnchorPane painel = carregador.load();

            Scene cena = new Scene(painel);
            palcoInicial.setScene(cena);
            palcoInicial.setResizable(false);
            palcoInicial.setTitle("GFP – Login");
            palcoInicial.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }

}
