package cmri.snapshot.api.algorithm;

import cmri.utils.configuration.ConfigManager;

/**
 * Created by zhuyin on 2/15/16.
 */
public class StarOper {
    private double dampingFactor = ConfigManager.getDouble("star.dampingFactor", 0.7);
    public static StarOper instance(){
        return new StarOper();
    }

    public int compute(int oldStar, int newOrdercount, long newOrderAmount, double favRatio, double negRatio, int missCount){
        double newStar = dampingFactor * oldStar + (1 - dampingFactor) * ( Math.max(newOrdercount / 20.0, 1) + Math.max(newOrderAmount/5000.0, 1) + favRatio * 3 - negRatio * 5 - missCount * 2 );
        return (int) newStar;
    }
}
