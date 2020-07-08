package com.xwder.biz.app.config.database;

import com.google.common.collect.Range;
import com.xwder.biz.app.common.DataBaseConstant;
import io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.RangeShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import io.shardingjdbc.core.api.algorithm.sharding.standard.RangeShardingAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class DatabaseShardingAlgorithm implements PreciseShardingAlgorithm<Long>, RangeShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        Long value = preciseShardingValue.getValue();
        String dbName = DataBaseConstant.DEFAULT_DB;
        for (String each : collection) {
            if (each.equals(dbName)) {
                return each;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Long> rangeShardingValue) {
        Set<String> result = new LinkedHashSet<>();
        String db_name = DataBaseConstant.DEFAULT_DB;
        Range<Long> shardingKey = rangeShardingValue.getValueRange();
        Long startShardingKey = shardingKey.lowerEndpoint();
        Long endShardingKey = shardingKey.upperEndpoint();
        Set<String> dateSet = new LinkedHashSet<String>();

        dateSet.forEach(item -> {
            if (collection.contains(db_name)) {
                result.add(db_name);
            }
        });
        return result;
    }

}