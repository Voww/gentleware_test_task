package ua.com.gentleware.zaietsv.dao.data_access_object_detail;

import zaietsv.complextask.mvc.dao.EntityDAO;
import zaietsv.complextask.mvc.entity.instance_detail.InstancesDetails;

import java.util.ArrayList;

/**
 * An interface describes access to a database
 * @author Voww
 *
 * @param <I> a class which implements InstanceDetail interface
 */
public interface DataAccessObjectsDetails<I extends InstancesDetails> extends EntityDAO<I> {
	
	/**
	 * Inserts a new record into a database
	 * @param instancesDetails - an entity of a class which implements InstanceDetail interface
	 * @return - an id for new added entity
	 */
	long insert(I instancesDetails);
	
	//boolean insert(ArrayList<InstanceDetail> instances);
	
	/**
	 * Updates an existing record in a database
	 * @param instancesDetails  - an entity of a class which implements InstanceDetail interface
	 * @return a quantity of affected rows (1 on success 0 on fault)
	 */
	int update(I instancesDetails);

	/**
	 *
	 * @param id - an Instance's database id number
	 * @return true on success false otherwise
	 */
	boolean delete(long id);
	
	/**
	 * Reads an existing record from a database
	 * @param id - the record's key identifier
	 * @return an entity of a class which implements InstanceDetail interface filled with parameters being red
	 */
	I read(long id);
	
	/**
	 * Reads all of the existing records from a table or a schema
	 * @return a list of instances of a class which implements InstanceDetail interface filled with parameters being red
	 */
	ArrayList<I> readAll();

}
