package core;

import java.sql.*;
import java.util.ArrayList;
import javafx.collections.*;
import javafx.collections.ObservableList;
import classes.*;

/**
 * The Database class of the ProgEx project contains methods for establishing a
 * database connection as well as data transfer from and to the database.
 * 
 * @version 1.0
 * @see java.sql
 */
public class Database {

	/**
	 * The loadFurniture method loads a list of Furniture objects from the
	 * database.
	 * 
	 * @return ObervableList An ObservableList of the data type Furniture
	 * @see Furniture
	 */
	public static ObservableList<Furniture> loadFurniture() {

		ObservableList<Furniture> furnitureList = FXCollections.observableArrayList();

		Connection c = null;
		Statement stmt = null;

		try {

			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Database.db");

		} catch (Exception e) {
			System.out.println(e);
			System.exit(0);
		}

		try {

			// get Furniture from DB
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Furniture;");
			while (rs.next()) {
				int id = rs.getInt("ID");
				int category = rs.getInt("Category");
				int stock = rs.getInt("Stock");
				String name = rs.getString("Name");
				String description = rs.getString("Description");
				String pictureFile = rs.getString("PictureFile");
				float price = rs.getFloat("Price");

				Furniture temp = new Furniture(id, category, stock, name, description, pictureFile, price);

				furnitureList.add(temp);
			}

			rs.close();
			c.close();

		} catch (Exception e) {
			System.out.println(e);
			System.exit(0);
		}
		return furnitureList;
	}

	/**
	 * The saveFurniture method saves a list of Furniture objects in the
	 * database.
	 * 
	 * @param furnitureList
	 *            An ObservableList of the data type Furniture
	 * @see Furniture
	 */
	public static void saveFurniture(ObservableList<Furniture> furnitureList) {

		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Database.db");
			c.setAutoCommit(false);
			stmt = c.createStatement();

			// Delete the old stuff
			stmt.executeUpdate("DELETE FROM Furniture;");

			// Save everything
			for (Furniture f : furnitureList) {
				stmt.executeUpdate(
						"INSERT INTO Furniture (ID, Category, Stock, Name, Description, PictureFile, Price)VALUES("
								+ f.getId() + ", " + f.getCategory() + ", " + f.getStock() + ", '" + f.getName()
								+ "', '" + f.getDescription() + "', '" + f.getPictureFile() + "', "
								+ Float.toString(f.getPrice()) + ");");
			}
			c.commit();

		} catch (Exception e) {
			try {
				c.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.out.println(e);
		} finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * The loadAdmins method loads a list of Admin objects from the database.
	 * 
	 * @return ObservableList An ObservableList of the data type Admin
	 * @see Admin
	 */
	public static ArrayList<Admin> loadAdmins() {

		ArrayList<Admin> adminList = new ArrayList<Admin>();

		Connection c = null;
		Statement stmt = null;

		try {

			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Database.db");

		} catch (Exception e) {
			System.out.println(e);
			System.exit(0);
		}

		try {

			// get Admins from DB
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Admin;");
			while (rs.next()) {
				int id = rs.getInt("ID");
				String login = rs.getString("Login");
				String password = rs.getString("Password");

				Admin temp = new Admin(id, login, password);

				adminList.add(temp);
			}

			rs.close();
			c.close();

		} catch (Exception e) {
			System.out.println(e);
			System.exit(0);
		}

		return adminList;
	}

}
