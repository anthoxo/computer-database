package dao;

public interface DAOInterface<T> {
	public void create(T obj);
	public T get(int id);
	public void update(T obj);
	public void delete(T obj);
}
