package ua.com.gentleware.zaietsv.dao.data_access_link_object_detail;

import java.util.ArrayList;

/**
 * Created by Voww on 11.10.2015.
 */
public interface LinkDAO {

    int insertLink(Long instance_id, Long detail_id);

    ArrayList<Long> readLink(Long instance_id);

    int updateLink(Long instance_id, Long detail_id);

    int deleteLink(Long instance_id, Long detail_id);

}
