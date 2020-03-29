package echang.pxd.poker;

/**
 * @Description
 * @Author 彭孝东
 * @QQ 932056657
 */
public abstract class BaseGameCenter implements IGame{
    protected static Object instance;
    protected int mTotalSuccess;

    protected BaseGameCenter(){
        initPlayers();
        initPokers();
        initGame();
    }

    protected abstract void initGame();

    protected abstract void initPokers();

    protected abstract void initPlayers();

    public static Object getInstance(){
        if (instance == null){
            synchronized (BaseGameCenter.class){
                instance = Util.createInstance(PokerGameCenter.class.getName());
            }
        }
        return instance;
    }
}
