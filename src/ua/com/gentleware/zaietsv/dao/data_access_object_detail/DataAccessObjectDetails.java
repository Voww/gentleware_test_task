package ua.com.gentleware.zaietsv.dao.data_access_object_detail;

import zaietsv.complextask.mvc.dao.EntityDAO;
import zaietsv.complextask.mvc.entity.instance.Users;
import zaietsv.complextask.mvc.entity.instance_detail.InstanceDetails;

/**
 * An interface describes access to a database
 * @author Voww
 *
 * @param <I> a class which implements InstanceDetail interface
 */
public interface DataAccessObjectDetails<I extends InstanceDetails> extends EntityDAO<I> {
	
	/**
	 * Inserts a new record into a database
	 * @param instanceDetails - an entity of a class which implements InstanceDetail interface
	 * @return - an id for new added entity
	 */
	int insert(I instanceDetails);

	int insert(long address_id, Users users);
	
	/**
	 * Updates an existing record in a database
	 * @param instanceDetails  - an entity of a class which implements InstanceDetail interface
	 * @return a quantity of affected rows (1 on success 0 on fault)
	 */
	int update(I instanceDetails);

	/**
	 * Unlinks all of the ties between an instance and it's detail
	 * @param instance_id - an instance's database id number
	 * @return true on success false otherwise
	 */
	boolean unlink(long instance_id);

	/**
	 * Unlinks the selected pair of ties between an instance and selected detail
	 * @param instance_id - an instance's database id number
	 * @param detail_id - a detail's database id number
	 * @return true on success false otherwise
	 */
	boolean unlink(long instance_id, long detail_id);

	boolean delete(long instance_id, long detail_id);

	/**
	 *
	 * @param id - an Instance's database id number
	 * @return true on success false otherwise
	*/
	//boolean delete(long id);
	
	/**
	 * Reads an existing record from a database
	 * @param id - the record's key identifier
	 * @return an entity of a class which implements InstanceDetail interface filled with parameters being red
	 */
	I read(long id);

	long read(I instanceDetails);

}
