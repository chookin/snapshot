package cmri.snapshot.api.algorithm;

import org.junit.Test;

/**
 * Created by zhuyin on 2/15/16.
 */
public class GradeOperTest {

    @Test
    public void testCheckUpgrade() throws Exception {
        int gradeInc = GradeOper.instance().checkUpgrade(1, 15, 1500, 0.86, 5000, 1);
        System.out.println(gradeInc);
    }
}