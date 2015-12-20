package ua.com.gentleware.zaietsv.dao.data_access_object_detail;

import zaietsv.complextask.mvc.dao.EntityDAO;
import zaietsv.complextask.mvc.entity.instance.User;
import zaietsv.complextask.mvc.entity.instance_detail.InstanceDetail;

/**
 * An interface describes access to a database
 * @author Voww
 *
 * @param <I> a class which implements InstanceDetail interface
 */
public interface DataAccessObjectDetail<I extends InstanceDetail> extends EntityDAO<I> {
	
	/**
	 * Inserts a new record into a database
	 * @param instanceDetail - an entity of a class which implements InstanceDetail interface
	 * @return - affected rows count
	 */
	int insert(I instanceDetail);

	int insert(long address_id, User user);
	
	//boolean insert(ArrayList<InstanceDetail> instances);
	
	/**
	 * Updates an existing record in a database
	 * @param instanceDetail  - an entity of a class which implements InstanceDetail interface
	 * @return a quantity of affected rows (1 on success 0 on fault)
	 */
	int update(I instanceDetail);

	/**
	 * Unlinks all of the ties between an instance and it's detail
	 * @param instance_id - an instance's database id number
	 * @return true on success false otherwise
	 */
	boolean unlink(long instance_id);

	boolean unlink(long instance_id, long detail_id);

	boolean delete(long instance_id, long detail_id);
	
	/**
	 * Reads an existing record from a database
	 * @param id - the record's key identifier
	 * @return an entity of a class which implements InstanceDetail interface filled with parameters being red
	 */
	I read(long id);

	long read(I instanceDetail);

	/**
	 * Reads all of the existing records from a table or a schema
	 * @return a list of instances of a class which implements InstanceDetail interface filled with parameters being red
	 */
	//ArrayList<I> readAll();

}
