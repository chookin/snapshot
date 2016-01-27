package cmri.snapshot.api.domain;

/**
 * Created by zhuyin on 1/19/16.
 */
public enum ModelType {
    User(0),
    Photo(1),
    Work(2),
    Shot(51),
    SpecialShot(52),
    GroupShot(53);
    private byte val;
    ModelType(int val){
        this.val = (byte) val;
    }
    public byte getVal(){
        return this.val;
    }
}
