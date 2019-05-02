package core.model;

import java.util.List;

public class Page<T> {

	List<T> data;
	int indexItem;
	int indexPage;
	int nbPages;
	int length;

	public static final int NB_ITEMS_PER_PAGE = 10;

	/**
	 * Default constructor.
	 */
	public Page() {
		this.indexItem = 0;
		this.indexPage = 0;
		this.length = 0;
	}

	/**
	 * The most used constructor.
	 *
	 * @param list A list that use to fill the page object.
	 */
	public Page(List<T> list) {
		this.setData(list);
		this.indexItem = 0;
		this.indexPage = 0;
		this.nbPages = (int) Math.ceil((double) this.length / NB_ITEMS_PER_PAGE);
	}

	public List<T> getData() {
		return this.data;
	}

	public int getIndexItem() {
		return this.indexItem;
	}

	public int getIndexPage() {
		return this.indexPage;
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

	public void setIndexItem(int index) {
		this.indexItem = index;
	}

	public void setIndexPage(int index) {
		this.indexPage = index;
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
			return data.subList(indexItem, Math.min(indexItem + NB_ITEMS_PER_PAGE, length));
		}
	}

	/**
	 * Method that literally turn the next page. It used to move forward in the
	 * entire list.
	 */
	public void next() {
		if (this.length == 0) {
			this.indexItem = 0;
			this.indexPage = 0;
		} else {
			this.indexItem = Math.min(this.indexItem + NB_ITEMS_PER_PAGE, this.length - 1);
			this.indexPage = this.indexItem / NB_ITEMS_PER_PAGE;
		}
	}

	/**
	 * Method that literally turn the previous page. It used to move backward in the
	 * entire list.
	 */
	public void previous() {
		if (this.length == 0) {
			this.indexItem = 0;
			this.indexPage = 0;
		} else {
			this.indexItem = Math.max(this.indexItem - NB_ITEMS_PER_PAGE, 0);
			this.indexPage = this.indexItem / NB_ITEMS_PER_PAGE;
		}
	}

	/**
	 * Method that allow us to navigate to a specific item.
	 *
	 * @param itemIndex The item that we want to go.
	 */
	public void goTo(int itemIndex) {
		int n = Math.min(Math.max(0, itemIndex), this.length - 1);
		this.indexItem = n;
		this.indexPage = this.indexItem / NB_ITEMS_PER_PAGE;
	}

}
