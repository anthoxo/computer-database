package model;

import java.util.List;

public class Page<T> {

	List<T> data;
	int index;
	int nbPages;
	int length;

	public static final int NB_ITEMS_PER_PAGE = 10;

	/**
	 * Default constructor.
	 */
	public Page() {
		this.index = 0;
		this.length = 0;
	}

	/**
	 * The most used constructor.
	 *
	 * @param list A list that use to fill the page object.
	 */
	public Page(List<T> list) {
		this.setData(list);
		this.index = 0;
		this.nbPages = this.length / NB_ITEMS_PER_PAGE + (this.length % NB_ITEMS_PER_PAGE == 0 ? 0 : 1);
	}

	public List<T> getData() {
		return this.data;
	}

	public int getIndex() {
		return this.index;
	}

	public int getNbPages() {
		return this.nbPages;
	}

	public int getLength() {
		return this.length;
	}

	/**
	 * @param data The list that use to fill the Page object.
	 */
	public void setData(List<T> data) {
		this.data = data;
		this.setLength(data.size());
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * @return The sublist with size of NB_PAGES.
	 */
	public List<T> getEntitiesPage() {
		if (this.length == 0) {
			return data;
		} else {
			return data.subList(index, Math.min(index + NB_ITEMS_PER_PAGE, length));
		}
	}

	/**
	 * Method that literally turn the next page. It used to move forward in the
	 * entire list.
	 */
	public void next() {
		if (this.length == 0) {
			this.index = 0;
		} else {
			this.index = Math.min(this.index + NB_ITEMS_PER_PAGE, this.length - 1);
		}
	}

	/**
	 * Method that literally turn the previous page. It used to move backward in the
	 * entire list.
	 */
	public void previous() {
		if (this.length == 0) {
			this.index = 0;
		} else {
			this.index = Math.max(this.index - NB_ITEMS_PER_PAGE, 0);
		}
	}

	/**
	 * Method that allow us to navigate to a specific item.
	 *
	 * @param itemIndex The item that we want to go.
	 */
	public void goTo(int itemIndex) {
		int n = Math.min(Math.max(0, itemIndex), this.length - 1);
		this.index = n;
	}

}
