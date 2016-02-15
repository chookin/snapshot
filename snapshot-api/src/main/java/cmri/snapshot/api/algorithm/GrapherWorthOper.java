package cmri.snapshot.api.algorithm;

import cmri.utils.configuration.ConfigManager;

/**
 * Created by zhuyin on 2/15/16.
 */
public class GrapherWorthOper {
    private int basePrice = ConfigManager.getInt("grapherWorth.bathPrice", 100);

    public static GrapherWorthOper instance(){
        return new GrapherWorthOper();
    }
    /**
     * 计算摄影师身价，同一级别摄影师具有相同的身价
     * @param grade 级别
     * @return 身价
     */
    public int getWorth(int grade){
        return (int) (basePrice * Math.pow(2, grade));
    }
}
