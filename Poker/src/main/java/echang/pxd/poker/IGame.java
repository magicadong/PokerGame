package echang.pxd.poker;

/**
 * @Description
 * @Author 彭孝东
 * @QQ 932056657
 */
public interface IGame {
    void onInitSuccess(IGame obj);
    void onInitFailure(String errMsg);
}
