package com.github.amihalik.rya.debugging;

import org.apache.hadoop.conf.Configuration;
import org.apache.log4j.Logger;
import org.apache.rya.accumulo.AccumuloRdfConfiguration;
import org.apache.rya.api.RdfCloudTripleStoreConfiguration;
import org.apache.rya.indexing.accumulo.ConfigUtils;
import org.apache.rya.indexing.external.PrecomputedJoinIndexerConfig;
import org.apache.rya.indexing.external.PrecomputedJoinIndexerConfig.PrecomputedJoinStorageType;
import org.apache.rya.sail.config.RyaSailFactory;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.repository.sail.SailRepositoryConnection;
import org.openrdf.sail.Sail;

public class RyaUtil {
    private static final Logger log = Logger.getLogger(RyaUtil.class);

    public static SailRepositoryConnection getSailRepo() throws Exception {
        final Configuration conf = getConf();

        log.info("Connecting to Indexing Sail Repository.");
        final Sail extSail = RyaSailFactory.getInstance(conf);
        SailRepository repository = new SailRepository(extSail);

        return repository.getConnection();
    }

    private static Configuration getConf() {

        final AccumuloRdfConfiguration conf = new AccumuloRdfConfiguration();

        conf.setBoolean(ConfigUtils.USE_MOCK_INSTANCE, true);
        conf.setBoolean(ConfigUtils.DISPLAY_QUERY_PLAN, true);
        conf.set(PrecomputedJoinIndexerConfig.PCJ_STORAGE_TYPE, PrecomputedJoinStorageType.ACCUMULO.name());
        conf.set(RdfCloudTripleStoreConfiguration.CONF_TBL_PREFIX, "rya_");
        conf.set(ConfigUtils.CLOUDBASE_USER, "root");
        conf.set(ConfigUtils.CLOUDBASE_PASSWORD, "");
        conf.set(ConfigUtils.CLOUDBASE_INSTANCE, "instance");
        conf.set(ConfigUtils.CLOUDBASE_AUTHS, "U");

        return conf;
    }
}
