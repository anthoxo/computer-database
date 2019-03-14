package dao;

public interface DAOInterface<T> {
	public boolean create(T obj);
	public T get(int id);
	public boolean update(T obj);
	public boolean delete(T obj);
}
