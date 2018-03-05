package furniture;

import java.io.IOException;

import classes.Furniture;
import classes.ShoppingCart;
import core.Database;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	private static Stage primaryStage;
	private static BorderPane mainLayout;
	private static ObservableList<Furniture> furnitureList;
	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Nimax Furniture Shop");
		this.primaryStage.getIcons().add(new Image("file:media/icon.png"));

		showMainView();
		showCustomerScene();
		//showWelcomeScene();
		Main.primaryStage.setResizable(false);
	}

	private Furniture getFurnitureById(int id) {
		for (Furniture item : this.furnitureList) {
			if (id == item.getId()) {
				return item;
			}
		}
		return null;
	}

	private void showMainView() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/MainView.fxml"));
		mainLayout = loader.load();
		Scene scene = new Scene(mainLayout);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void showCustomerScene() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("customer/Customer.fxml"));
		BorderPane mainItems = loader.load();
		mainLayout.setCenter(mainItems);
	}



	public static void showWelcomeScene () throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("customer/Welcome.fxml"));
		BorderPane welcome = loader.load();
		mainLayout.setCenter(welcome);

	}

	public static void main(String[] args) {
		launch(args);
		furnitureList = Database.loadFurniture();

	}
}
