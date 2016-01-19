package cmri.snapshot.api.domain;

/**
 * Created by zhuyin on 1/19/16.
 */
public enum CommentObject {
    User(0),
    Shot(3),
    SpecailShot(3),
    GroupShot(4),
    Work(1),
    Photo(2);
    private byte val;
    CommentObject(int val){
        this.val = (byte) val;
    }
    public byte getVal(){
        return this.val;
    }
}
