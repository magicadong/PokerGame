package echang.pxd.poker;

/**
 * @Description
 * @Author 彭孝东
 * @QQ 932056657
 */
public class PokerGameCenter {
    // 保存单例对象
    private static PokerGameCenter instance;

    // 私有化默认的构造方法
    private PokerGameCenter(){}

    // 获取单例对象
    public static PokerGameCenter getInstance(){
        if (instance == null){
            synchronized (PokerGameCenter.class){
                if (instance == null){
                    instance = new PokerGameCenter();
                }
            }
        }
        return instance;
    }
}
