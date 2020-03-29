package echang.pxd.poker;

/**
 * @Description
 * @Author 彭孝东
 * @QQ 932056657
 */
public class PokerGameCenter extends BaseGameCenter{
    public PokerGameCenter(){}
    @Override
    protected void initGame() {
        System.out.println("hello world");
    }

    @Override
    protected void initPokers() {

    }

    @Override
    protected void initPlayers() {

    }

    @Override
    public void onInitSuccess(IGame obj) {

    }

    @Override
    public void onInitFailure(String errMsg) {

    }
}
