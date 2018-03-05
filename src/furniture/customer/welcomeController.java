package furniture.customer;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

import classes.*;
import furniture.Main;
public class welcomeController {
private Main main;
private Image img;

	@FXML
	private ImageView welcomePic;


	@FXML
	private void initialize(){
		img = new Image("file:media/welcome.png");
		welcomePic.setImage(img);

	}


	@FXML
	private void goCustomer() throws IOException{
		main.showCustomerScene();
	}
}
