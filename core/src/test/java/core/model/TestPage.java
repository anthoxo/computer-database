package core.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPage {

	List<Integer> list;
	Page<Integer> page;

	@BeforeEach
	public void init() {
		this.list = new ArrayList<Integer>();
		for (int i = 0; i < 100; ++i) {
			this.list.add(i);
		}
		this.page = new Page<Integer>(list);
		this.page.setIndexItem(50);
	}

	@Test
	public void testParam() {
		assertEquals(50, page.getIndexItem());
		assertEquals(this.list, page.getData());
		assertEquals(this.list.size(), page.getLength());
	}

	@Test
	public void testNext() {
		int previousIndex = this.page.indexItem;
		this.page.next();
		int nextIndex = this.page.indexItem;
		assertTrue((previousIndex == this.page.length - 1) || (previousIndex < nextIndex));
	}

	@Test
	public void testPrevious() {
		int previousIndex = this.page.indexItem;
		this.page.previous();
		int nextIndex = this.page.indexItem;
		assertTrue((previousIndex == 0) || (nextIndex < previousIndex));
	}

	@Test
	public void testNextAtSide() {
		this.page.indexItem = this.page.length - 1;
		int previousIndex = this.page.indexItem;
		this.page.next();
		int nextIndex = this.page.indexItem;
		assertTrue((previousIndex == this.page.length - 1) || (previousIndex < nextIndex));
	}

	@Test
	public void testPreviousAtSide() {
		this.page.indexItem = 0;
		int previousIndex = this.page.indexItem;
		this.page.previous();
		int nextIndex = this.page.indexItem;
		assertTrue((previousIndex == 0) || (nextIndex < previousIndex));
	}

	@Test
	public void testNextWithZeroElement() {
		this.page = new Page<Integer>();
		int previousIndex = this.page.indexItem;
		int length = this.page.length;
		this.page.next();
		int nextIndex = this.page.indexItem;
		assertEquals(0, previousIndex);
		assertEquals(0, length);
		assertEquals(0, nextIndex);
	}

	@Test
	public void testPreviousWithZeroElement() {
		this.page = new Page<Integer>();
		int previousIndex = this.page.indexItem;
		int length = this.page.length;
		this.page.previous();
		int nextIndex = this.page.indexItem;
		assertEquals(0, previousIndex);
		assertEquals(0, length);
		assertEquals(0, nextIndex);
	}

	@Test
	public void testGetEntitiesPage() {
		List<Integer> list = this.page.getEntitiesPage();
		assertEquals(Page.NB_ITEMS_PER_PAGE, list.size());
		for (int i = 0; i < Page.NB_ITEMS_PER_PAGE; ++i) {
			assertEquals(list.get(i), 50 + i);
		}
	}

	@Test
	public void testNbPages1() {
		assertEquals(10, page.getNbPages());
	}

	@Test
	public void testNbPages2() {
		this.list = new ArrayList<Integer>();
		for (int i = 0; i < 51; ++i) {
			this.list.add(i);
		}
		this.page = new Page<Integer>(list);

		assertEquals(6, page.getNbPages());
	}

	@Test
	public void testGoTo() {
		this.page.goTo(0);
		assertEquals(0, this.page.getIndexItem());
		this.page.goTo(71);
		assertEquals(71, this.page.getIndexItem());
		this.page.goTo(-10);
		assertEquals(0, this.page.getIndexItem());
		this.page.goTo(list.size() + 100);
		assertEquals(this.page.getIndexItem(), list.size() - 1);
	}
}
