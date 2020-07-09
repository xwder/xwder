package com.xwder.app.config.database;

import com.google.common.collect.Range;
import io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.RangeShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import io.shardingjdbc.core.api.algorithm.sharding.standard.RangeShardingAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 分表
 * routeInfo 按日期分表
 *
 * @author wande
 * @version 1.0
 * @date 2019/07/18
 */
@Component
public class TableShardingAlgorithm implements PreciseShardingAlgorithm<Integer> ,RangeShardingAlgorithm<Integer> {
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Integer> preciseShardingValue) {

        String tb_name = preciseShardingValue.getLogicTableName() + "_" + tableSuffix(preciseShardingValue.getValue());
        for (String each : collection) {
            if (each.equals(tb_name)) {
                return each;
            }
        }
        return null;
    }

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Integer> rangeShardingValue) {
        Set<String> result = new LinkedHashSet<>();
        String tb_name = rangeShardingValue.getLogicTableName() + "_";
        Range<Integer> shardingKey = rangeShardingValue.getValueRange();
        Integer startShardingKey = shardingKey.lowerEndpoint();
        Integer endShardingKey = shardingKey.upperEndpoint();

        Set<Integer> dateSet = new LinkedHashSet<>();
        Integer i = startShardingKey;
        for(;i<=endShardingKey;i++){
            dateSet.add(i);
        }
        dateSet.forEach(item -> {
            String tb_realname = tb_name+ tableSuffix(item);
            if(collection.contains(tb_realname)) {
                result.add(tb_realname);
            }
        });

        return result;
    }

    /**
     * 分表 表后缀
     * @param bookId
     * @return
     */
    private String tableSuffix(Integer bookId) {
        if (bookId <= 1000) {
            return "1";
        } else {
            if (bookId % 1000 == 0) {
                return (bookId / 1000) + "";
            } else {
                return ((bookId / 1000) + 1) + "";
            }
        }
    }
}
