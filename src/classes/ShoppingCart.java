package classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The ShoppingCart class of the ProgEx project. It contains methods for adding
 * and removing items to and from the shopping cart.
 *
 * @version 1.0
 */
public class ShoppingCart {

	private ObservableList<Furniture> itemList = FXCollections.observableArrayList();

	public ObservableList<Furniture> getItemList() {
		return itemList;
	}

	/**
	 * The addItemToList method adds one object of the Furniture data type to
	 * the ArrayList of the shopping cart, if the stock value is greater than
	 * one. It then decreases the Stock value of the Furniture object by one.
	 *
	 * @param item
	 *            A Furniture object to be added to the ArrayList of the
	 *            shopping cart.
	 * @return true if the Furniture object was added to the list, and its stock
	 *         value was decreased by one. Otherwise false.
	 * @see Furniture
	 */
	public boolean addItemToList(Furniture item) {
		if (itemList.add(item)) {
			int stock = item.getStock();
			if (stock < 1) {
				return false;
			} else {
				item.setStock(stock - 1);
			}
			return true;
		} else
			return false;
	}

	/**
	 * The addItemToList method adds one or more objects of the Furniture data
	 * type to the ArrayList of the shopping cart, if the stock value is
	 * sufficient. It also decreases the Stock value of the Furniture object by
	 * the given amount.
	 *
	 * @param item
	 *            A Furniture object to be added to the ArrayList of the
	 *            shopping cart.
	 * @param amount
	 *            The amount of objects that should be added to the ArrayList of
	 *            the shopping cart.
	 * @return true if the Furniture object(s) were added to the list, and the
	 *         stock value was decreased. Otherwise false.
	 * @see Furniture
	 */
	public boolean addItemToList(Furniture item, int amount) {
		if (item.getStock() >= amount) {
			for (int i = 0; i < amount; i++) {
				itemList.add(item);
				item.setStock(item.getStock() - 1);
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * The removeItemFromList method removes one object of the Furniture data
	 * type from the ArrayList of the shopping cart, if one exists in the
	 * ArrayList. It then increases the Stock value of the Furniture object by
	 * one.
	 *
	 * @param id
	 *            The id of the Furniture object, that is to be removed from the
	 *            ArrayList of the shopping cart.
	 * @return true if the Furniture object was removed from the list, and its
	 *         stock value was increased by one. Otherwise false.
	 * @see Furniture
	 */
	public boolean removeItemFromList(int id) {
		int count = this.containsItemWithId(id);
		if (count > 0) {
			for (Furniture item : itemList) {
				if (item.getId() == id) {
					item.setStock(item.getStock() + 1);
					itemList.remove(item);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * The removeItemFromList method removes one or more objects of the
	 * Furniture data type from the ArrayList of the shopping cart, if at least
	 * one exists in the ArrayList. It then increases the Stock value of the
	 * Furniture object by the amount that has been removed.
	 *
	 * @param id
	 *            The id of the Furniture object, that is to be removed from the
	 *            ArrayList of the shopping cart.
	 * @param amount
	 *            (OPTIONAL) The amount that should be removed.
	 * @return true if the given amount of items has been removed, otherwise
	 *         false.
	 * @see Furniture
	 */
	public boolean removeItemFromList(int id, int amount) {
		int count = this.containsItemWithId(id);
		Furniture array[] = new Furniture[amount];
		int index = 0;
		if (count > 0 && amount <= count) {
			int diff = count - amount;
			for (Furniture item : itemList) {
				if (item.getId() == id) {
					if (count > diff) {
						array[index] = item;
						count--;
						index++;
					}
				}
			}
			for (Furniture f : array) {
				f.setStock(f.getStock() + 1);
				itemList.remove(f);
			}
			return true;
		}
		return false;
	}

	/**
	 * This method returns the amount of Furniture objects in the shopping cart
	 * with the given id.
	 *
	 * @param id
	 *            The id of the Furniture object
	 * @return the count of the Furniture objects with the given id in the
	 *         shopping cart ArrayList.
	 * @see Furniture
	 */
	public int containsItemWithId(int id) {
		int count = 0;
		for (Furniture i : itemList) {
			if (i.getId() == id) {
				count++;
			}
		}
		if (count >= 1) {
			return count;
		}
		return 0;
	}

	/**
	 * This method calculates the price sum of the entire shopping cart.
	 *
	 * @return float sum
	 */
	public float calculatePrice(){
		float sum = 0;
		for(Furniture f : itemList){
			sum = sum + f.getPrice();
		}
		return sum;
	}

}