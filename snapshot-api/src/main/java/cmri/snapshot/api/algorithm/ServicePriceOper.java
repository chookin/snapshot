package cmri.snapshot.api.algorithm;

import cmri.utils.configuration.ConfigManager;

/**
 * Created by zhuyin on 2/15/16.
 */
public class ServicePriceOper {
    private int queuingOrderCountThreshold = ConfigManager.getInt("servicePrice.queuingOrderCountThreshold", 7);
    private static double log2Val = Math.log(2);

    public static ServicePriceOper instance(){
        return new ServicePriceOper();
    }

    public int getPrice(int photographerWorth, int photographerStar, double serviceWeight, int queuingOrderCount, double discountRatio){
        double price = photographerWorth * serviceWeight * ( 1 + 0.2 * Math.max((int)(Math.log(queuingOrderCount * 2 / queuingOrderCountThreshold) / log2Val), 0)) * discountRatio;
        return (int) price;
    }
}
