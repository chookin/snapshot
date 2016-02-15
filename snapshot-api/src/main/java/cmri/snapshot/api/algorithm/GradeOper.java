package cmri.snapshot.api.algorithm;

import cmri.utils.configuration.ConfigManager;
import org.apache.commons.lang3.Validate;

/**
 * Created by zhuyin on 2/15/16.
 *
 * 级别
 */
public class GradeOper {
    private double paraOrderCount = ConfigManager.getDouble("grade.para.orderCount", 5);
    private double paraOrderAmount = ConfigManager.getDouble("grade.para.orderAmount", 500);
    private double paraEquipment = ConfigManager.getDouble("grade.para.equipment", 5000);
    private double favRatioThreshold = ConfigManager.getDouble("grade.para.favRatioThreshold", 0.8);
    private int orderCountThreshold = ConfigManager.getInt("grade.para.orderCountThreshold", 2);
    private static double log2Val = Math.log(2);

    public static GradeOper instance(){
        return new GradeOper();
    }
    /**
     * 检查普通用户是否升级
     */
    public int checkUpgrade(int curGrade, int newOrderCount, long newOrderAmount, double favRatio, double weight) {
        return checkUpgrade(curGrade, newOrderCount, newOrderAmount, favRatio, 0L, weight);
    }

    /**
     * 检查摄影师是否升级，每个自然月的1号00:30检查自上次级别调整后的订单情况，并判定是否升级或降级
     *
     * @param curGrade 当前级别
     * @param newOrderCount 新增订单数
     * @param newOrderAmount 新增订单金额，元
     * @param favRatio 新增订单的好评率，好评率＝好评数/评价总数
     * @param equipmentPrice 器材价格，元
     * @param weight 权重系数，之前曾升级后又降级时，具有较大的权重系数
     * @return 如果为-1，意味着需降一级；如果为0，不升级不降级；如果为正数，则意味着可连升相应的级数
     */
    // 需要考虑直升几级的情况
    // 星级 级别 好评率，升的是级别
    // 
    public int checkUpgrade(int curGrade, int newOrderCount, long newOrderAmount, double favRatio, long equipmentPrice, double weight) {
        Validate.isTrue(curGrade > 0, "para 'curGrade' must be positive");

        if( favRatio < favRatioThreshold || newOrderCount < orderCountThreshold){
            if(curGrade > 1) {
                return -1;
            }else {
                return 0;
            }
        }

        double val = Math.floor(Math.log(newOrderCount / paraOrderCount) / log2Val) + Math.floor(Math.log(newOrderAmount / paraOrderAmount) / log2Val);
        if(equipmentPrice > 0L){
            val = val + Math.floor(Math.log(equipmentPrice / paraEquipment) / log2Val);
        }
        val = val * weight;
        if( val >= curGrade + 1){
            // cast to int, ignore fractional part.
            return (int) (val - curGrade);
        }

        return 0;
    }

}
