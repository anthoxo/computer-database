package model;

import java.util.List;

public class Page<T> {
	
	List<T> data;
	int index;
	int length;
	
	int nb_pages = 10;
	
	public Page() {
		this.index = 0;
		this.length = 0;
	}
	
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
	
	public void setData(List<T> data) {
		this.data = data;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public void setLength(int length) {
		this.length = length;
	}

	public List<T> getEntitiesPage() {
		return data.subList(index, Math.min(index + this.nb_pages, length));
	}
	
	public void next() {
		this.index = Math.min(this.index + this.nb_pages, this.length - 1);
	}
	
	public void previous() {
		this.index = Math.max(this.index - this.nb_pages, 0);
	}
	
}
