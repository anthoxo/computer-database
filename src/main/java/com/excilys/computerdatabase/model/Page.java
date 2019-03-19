package com.excilys.computerdatabase.model;

import java.util.List;

public class Page<T> {

	List<T> data;
	int index;
	int length;

	public static final int NB_PAGES = 10;

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
		this.index = 0;
		data = list;
		this.length = list.size();
	}

	public List<T> getData() {
		return this.data;
	}

	public int getIndex() {
		return this.index;
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
		return data.subList(index, Math.min(index + NB_PAGES, length));
	}

	/**
	 * Method that literally turn the next page. It used to move forward in the
	 * entire list.
	 */
	public void next() {
		if (this.length == 0) {
			this.index = 0;
		} else {
			this.index = Math.min(this.index + NB_PAGES, this.length - 1);
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
			this.index = Math.max(this.index - NB_PAGES, 0);
		}
	}

}
