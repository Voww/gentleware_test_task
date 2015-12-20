package ua.com.gentleware.zaietsv.dao.data_acces_object;

import zaietsv.complextask.mvc.dao.EntityDAO;
import zaietsv.complextask.mvc.entity.instance.Instance;
import zaietsv.complextask.mvc.exception.DAOException;

import java.util.ArrayList;

/**
 * An interface describes access to a database
 * @author Voww
 *
 * @param <I> a class which implements Instance interface
 */
public interface DataAccessObject<I extends Instance> extends EntityDAO<I> {

	/**
	 * @param instance - an instance of a class which implements Instance interface
	 * @return a number of rows inserted (1 on success 0 on fault -1 on error)
	 * @throws DAOException
	 */
	int insert(I instance);

	/**
	 * Reads an existing record from a database
	 * @param id - the record's key identifier
	 * @return an entity of a class which implements Instance interface filled with parameters being red
	 */
	I read(long id);

	/**
	 * Reads entity's database id number searching the entity by its essential parameters.
	 * Sets derived fields values into the source entity
	 * @param instance - an instance to be read
	 * @return instance's database id number
	 */
	long read(I instance);

	/**
	 * Updates an existing record in a database
	 * @param instance  - an instance of a class which implements Instance interface
	 * @return a quantity of affected rows (1 on success 0 on fault -1 on error)
	 */
	int update(I instance);
	
	/**
	 * Deletes an existing record from a database
	 * @param id - the record's key identifier
	 * @return true on success false otherwise
	 */
	boolean delete(long id);

	/**
	 * Checks if exists a record having specified database identification number
	 * @param id - an instance's ID number
	 * @return true if exists false otherwise
	 */
	boolean exists(long id);

	/**
	 * Checks if exists a database record having specified fields
	 * (excluding immutable fields)
	 * @param instance - an instance to be verified
	 * @return true if exists false otherwise
	 */
	boolean exists(I instance);

	/**
	 * Reads all of the existing records from a table or a schema
	 * @return a list of instances of a class which implements Instance interface filled with parameters being red
	 */
	ArrayList<I> readAll();

}
