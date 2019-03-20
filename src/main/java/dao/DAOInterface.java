package dao;

interface DAOInterface<T> {

	/**
	 * Method to create an object into a database.
	 *
	 * @param obj The object that we want to put in the database.
	 * @return true if it's done, else false.
	 */
	boolean create(T obj);

	/**
	 * Method to retrieve an object of type T by giving its name.
	 *
	 * @param id The id (in the database) of the desired object.
	 * @return The object (if it's in database), else null.
	 */
	T get(int id);

	/**
	 * Method to update an object in database.
	 *
	 * @param obj The object that we want to update.
	 * @return true if it's done, else false.
	 */
	boolean update(T obj);

	/**
	 * Method to delete an object in database.
	 *
	 * @param obj The object we want to delete.
	 * @return true if it's correctly deleted else false.
	 */
	boolean delete(T obj);
}
