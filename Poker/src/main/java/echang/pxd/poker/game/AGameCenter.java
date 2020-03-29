package echang.pxd.poker.game;

import echang.pxd.poker.player.PlayerManager;
import echang.pxd.poker.poker.PokerManager;

/**
 * @Description
 * @Author 彭孝东
 * @QQ 932056657
 */
public abstract class AGameCenter implements IGameInitListener {
    private int totalSuccess;

    protected PlayerManager playerManager;
    protected PokerManager pokerManager;
    protected int ante; //台面的总金额
    protected int totalPlayer;
    protected int bottom;//底注

    protected AGameCenter(){
        // 初始化游戏本身的数据
        initGame();
        // 初始化玩家
        initPlayers();
        // 初始化扑克
        initPokers();
    }

    protected abstract void initGame();

    protected abstract void initPokers();

    protected abstract void initPlayers();

    protected abstract void start();

    @Override
    public void onInitSuccess() {
        // 对成功的计数器+1
        setTotalSuccess(getTotalSuccess()+1);
    }

    @Override
    public void onInitFailure() {

    }

    public void setTotalSuccess(int totalSuccess) {
        this.totalSuccess = totalSuccess;
        if (this.totalSuccess == 3){
            start();
        }
    }

    public int getTotalSuccess() {
        return totalSuccess;
    }
}
