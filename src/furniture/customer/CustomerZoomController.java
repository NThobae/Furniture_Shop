package furniture.customer;
import furniture.Main;
import java.io.IOException;

import classes.Furniture;
import core.Database;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CustomerZoomController {
	private Main main;
	ObservableList<Furniture> furnitureList;
	@FXML
	private ImageView imgView;
	private Image img;

	@FXML
	private void initialize(){
		furnitureList = Database.loadFurniture();
		img = new Image(furnitureList.get(0).getPictureFile());
		imgView.setImage(img);
	}

	@FXML
	private void goCustomer() throws IOException{
		main.showCustomerScene();

	}

}
