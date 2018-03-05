package furniture.customer;

import classes.*;
import core.Database;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import furniture.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class CustomerController {
	String euro = "\u20ac";
	private Main main;
	protected Furniture selectedItem;

	static ObservableList<Furniture> furnitureList = FXCollections.observableArrayList();

	@FXML
	private Text priceTag;

	@FXML
	private Text name;


	@FXML
	Text exText;

	@FXML
	private ImageView imgView;
	@FXML
	private ImageView welcomePic;
	@FXML
	private ImageView imgViewZoom;

	@FXML
	private Rectangle box;
	@FXML
	private Rectangle box2;
	@FXML
	private Button buyMore;

	@FXML
	private Text amount;
	@FXML
	private Text priceFull;
	@FXML
	private Text respText;
	@FXML
	private Text remText;
	@FXML
	private Text cartRespText;

	@FXML
	private Button goTo;
	@FXML
	private Button addTo;

	@FXML
	private TableView<Furniture> furnitureTable;
	@FXML
	private TableColumn<Furniture, String> nameColumn;
	@FXML
	private TableColumn<Furniture, String> priceColumn;
	@FXML
	private TableColumn<Furniture, String> amountColumn;

	@FXML
	private TableView<Furniture> cartTable;
	@FXML
	private TableColumn<Furniture, String> nameColumnCart;
	@FXML
	private TableColumn<Furniture, String> priceColumnCart;
	@FXML
	private TableColumn<Furniture, String> amountColumnCart;

	@FXML
	private TabPane tabPane;
	@FXML
	private Tab firstTab;
	@FXML
	private Tab secondTab;
	@FXML
	private Tab thirdTab;
	@FXML
	private Tab fourthTab;

	@FXML
	private TextField filterField;

	@FXML
	private ListView<Furniture> listView;

	ShoppingCart shoppingCart = new ShoppingCart();

	private float pricetag;
	private String nameIn;
	private String exTextIn;
	int currentId;

	private Image img;
	private Image img2;

	public void fillTable() {
		nameColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Furniture, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Furniture, String> p) {
						return new SimpleStringProperty(p.getValue().getName());
					}
				});

		priceColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Furniture, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Furniture, String> p) {
						return new SimpleStringProperty(Float.toString(p.getValue().getPrice()));
					}
				});

		amountColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Furniture, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Furniture, String> p) {
						return new SimpleStringProperty(Integer.toString(p.getValue().getStock()));
					}
				});

		furnitureTable.setItems(furnitureList);
	}

	public void fillCartTable() {

		ObservableList<Furniture> tableList = FXCollections.observableArrayList();
		Set<Furniture> uniqueElements = new HashSet<Furniture>(shoppingCart.getItemList());
		tableList.addAll(uniqueElements);

		nameColumnCart.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Furniture, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Furniture, String> p) {
						return new SimpleStringProperty(p.getValue().getName());
					}
				});

		priceColumnCart.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Furniture, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Furniture, String> p) {
						return new SimpleStringProperty(Float.toString(p.getValue().getPrice()));
					}
				});

		amountColumnCart.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Furniture, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Furniture, String> p) {
						return new SimpleStringProperty(
								Integer.toString(shoppingCart.containsItemWithId(p.getValue().getId())));
					}

				});

		cartTable.setItems(tableList);
		cartTable.refresh();
		furnitureTable.refresh();
	}

	private void filter() {

		// Wrap the ObservableList in a FilteredList (initially display all
		// data).
		FilteredList<Furniture> filteredData = new FilteredList<>(furnitureList, p -> true);

		// Set the filter Predicate whenever the filter changes.
		filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(items -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter
				// text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (items.getName().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches name.
				}
				return false; // Does not match.
			});
		});

		// Wrap the FilteredList in a SortedList.
		SortedList<Furniture> sortedData = new SortedList<>(filteredData);

		// Bind the SortedList comparator to the TableView comparator.
		sortedData.comparatorProperty().bind(furnitureTable.comparatorProperty());

		// Add sorted (and filtered) data to the table.
		furnitureTable.setItems(filteredData);
		furnitureTable.refresh();

	}

	@FXML
	private void initialize() {
		furnitureList = Database.loadFurniture();
		selectedItem = furnitureList.get(0);
		pricetag = furnitureList.get(selectedItem.getId() - 1).getPrice();
		nameIn = furnitureList.get(selectedItem.getId() - 1).getName();
		exTextIn = furnitureList.get(selectedItem.getId() - 1).getDescription();
		img = new Image(furnitureList.get(selectedItem.getId() - 1).getPictureFile());

		priceTag.setText(pricetag + euro);
		name.setText(nameIn);
		exText.setText(exTextIn);
		imgView.setImage(img);
		cartTable.setPlaceholder(new Label("room for furniture"));
		img2 = new Image("file:media/welcome.png");
		welcomePic.setImage(img2);
		fillTable();
		filter();
		buyMore.setDisable(true);
		fourthTab.setDisable(true);

	}

	@FXML
	private void select() {

		selectedItem = furnitureTable.getSelectionModel().getSelectedItem();
		int currentId = selectedItem.getId() - 1;
		pricetag = furnitureList.get(currentId).getPrice();
		nameIn = furnitureList.get(currentId).getName();
		exTextIn = furnitureList.get(currentId).getDescription();
		img = new Image(furnitureList.get(currentId).getPictureFile());

		priceTag.setText(pricetag + euro);
		name.setText(nameIn);
		exText.setText(exTextIn);
		imgView.setImage(img);
		tabPane.getSelectionModel().select(firstTab);

		respText.setText("");
			}

	@FXML
	private void inZoom() throws IOException {

		imgView.setFitHeight(330);
		imgView.setFitWidth(530);
		name.setVisible(false);
		priceTag.setVisible(false);
		exText.setVisible(false);
		imgView.setImage(img);
		box.setStroke(Color.TRANSPARENT);

	}

	@FXML
	private void outZoom()throws IOException {

		imgView.setFitHeight(200);
		imgView.setFitWidth(200);


		name.setVisible(true);
		priceTag.setVisible(true);
		exText.setVisible(true);
		imgView.setImage(img);
		box.setStroke(Color.BLACK);

	}
	// ADD TO CART
	int x = 1;

	@FXML
	private void buyAdd() throws IOException {
		respText.setText("");
		if (x < 99) {
			x++;
			String temp = Integer.toString(x);
			amount.setText(temp);
		}
	}

	@FXML
	private void buySub() throws IOException {
		respText.setText("");
		if (x > 1) {
			x--;
			String temp = Integer.toString(x);
			amount.setText(temp);
		}
	}

	// REMOVE FROM CART
	int y = 1;

	@FXML
	private void removeAdd() throws IOException {
		if (cartTable.getSelectionModel().getSelectedItem() != null) {
			if (y < shoppingCart.containsItemWithId(cartTable.getSelectionModel().getSelectedItem().getId())) {
				y++;
				String temp = Integer.toString(y);
				remText.setText(temp);
				cartRespText.setText("");
			}
		}
	}

	@FXML
	private void removeSub() throws IOException {
		if (y > 1) {
			y--;
			String temp = Integer.toString(y);
			remText.setText(temp);
			cartRespText.setText("");
		}
	}

	@FXML
	private boolean addToCartButton() throws InterruptedException {

		String temp = amount.getText();
		int i = Integer.parseInt(temp);
		if (i == 1) {
			if (shoppingCart.addItemToList(selectedItem)) {
				float temp2 = shoppingCart.calculatePrice();
				String temp3 = Float.toString(temp2);
				fillCartTable();
				respText.setText("Added item to cart!");
				amount.setText("1");
				x = 1;
				priceFull.setText(temp3+ " "+ euro);
				return true;
			} else {
				respText.setText("Item stock not sufficient!");
				amount.setText("1");
				x = 1;
				return false;
			}
		}
		if (i > 1) {
			if (shoppingCart.addItemToList(selectedItem, i)) {
				float temp2 = shoppingCart.calculatePrice();
				String temp3 = Float.toString(temp2);
				fillCartTable();
				respText.setText("Added items to cart!");
				amount.setText("1");
				x = 1;
				priceFull.setText(temp3+ " "+ euro);
				return true;

			} else {
				respText.setText("Item stock not sufficient!");
				amount.setText("1");
				x = 1;
				return false;
			}
		}
		return false;
	}

	@FXML
	private boolean removeFromCartButton() {

		String temp = remText.getText();
		int i = Integer.parseInt(temp);

		if (i == 1) {
			if (cartTable.getSelectionModel().getSelectedItem() != null) {
				if (shoppingCart.removeItemFromList(cartTable.getSelectionModel().getSelectedItem().getId())) {
					float temp2 = shoppingCart.calculatePrice();
					String temp3 = Float.toString(temp2);
					fillCartTable();
					cartRespText.setText("");
					priceFull.setText(temp3+ " "+ euro);
					return true;
				} else {
					cartRespText.setText("Error!");
					return false;
				}
			}
		}

		if (i > 1) {
			if (cartTable.getSelectionModel().getSelectedItem() != null) {
				if (shoppingCart.removeItemFromList(cartTable.getSelectionModel().getSelectedItem().getId(), i)) {
					float temp2 = shoppingCart.calculatePrice();
					String temp3 = Float.toString(temp2);
					fillCartTable();
					remText.setText("1");
					y = 1;
					cartRespText.setText("");
					priceFull.setText(temp3+ " "+ euro);
					return true;
				} else {
					cartRespText.setText("Error!");
					return false;
				}
			}
			return false;
		}
		return false;
	}
	@FXML
	private void checkout() {

		ObservableList<Furniture> tableList = FXCollections.observableArrayList();
		Set<Furniture> uniqueElements = new HashSet<Furniture>(shoppingCart.getItemList());
		tableList.removeAll(uniqueElements);

		for (Furniture f : uniqueElements) {
			shoppingCart.removeItemFromList(f.getId(), shoppingCart.containsItemWithId(f.getId()));
		}
		priceFull.setText("0.00 "+euro);
		cartTable.setItems(tableList);
		cartTable.refresh();
		furnitureTable.refresh();
		Database.saveFurniture(furnitureList);
		tabPane.getSelectionModel().select(fourthTab);
		fourthTab.setDisable(false);
		buyMore.setDisable(false);
		firstTab.setDisable(true);
		secondTab.setDisable(true);
		thirdTab.setDisable(true);

	}

	@FXML
	private void buyMore(){
		tabPane.getSelectionModel().select(firstTab);
		fourthTab.setDisable(true);
		firstTab.setDisable(false);
		secondTab.setDisable(false);
		thirdTab.setDisable(false);
	}






}


