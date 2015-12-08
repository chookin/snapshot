package cmri.snapshot.api.helper;

import cmri.utils.dao.JdbcDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

/**
 * Created by zhuyin on 15/12/6.
 */
public class ModelHelper {
    private static final Logger LOG = LoggerFactory.getLogger(ModelHelper.class);
    public static Long getMaxId(String table) {
        try {
            return (Long) new JdbcDAO().executeQuery("select max(id) as id from "+table, rs -> {
                        if (rs.next()){
                            return rs.getLong( 1);
                        }else {
                            return null;
                        }
                    }
            );
        } catch (SQLException e) {
            LOG.error("fail to get max id of table '" + table + "'", e);
            System.exit(-1);
            return null;
        }
    }
}
