package echang.pxd.poker.poker;

import java.util.ArrayList;
import java.util.Collections;

import echang.pxd.poker.Constants;
import echang.pxd.poker.game.IGameInitListener;

/**
 * @Description
 * @Author 彭孝东
 * @QQ 932056657
 */
public class PokerManager {
    private IGameInitListener listener;
    private ArrayList<Poker> pokers;

    private static PokerManager manager;
    private PokerManager(){

    }
    public static PokerManager getManager(){
        if (manager == null){
            synchronized (PokerManager.class){
                if (manager == null){
                    manager = new PokerManager();
                }
            }
        }
        return manager;
    }

    public void initPokers(){
        //初始化数组对象
        pokers = new ArrayList<>();

        //创建扑克牌
        for (String dot: Constants.IPoker.DOTS){
            for (Poker.PicType type: Constants.IPoker.PIC_TYPES){
                //创建一张扑克牌
                Poker poker = new Poker(dot,type);
                //添加到数组中保存
                pokers.add(poker);
            }
        }

        //打乱顺序-洗牌
        Collections.shuffle(pokers);

        //当扑克牌初始化成功 就回调成功的事件给监听者（游戏中心）
        if (listener != null){
            listener.onInitSuccess();
        }
    }

    /**
     * 获取一张牌，然后从数组里面将这张牌删掉
     * @return
     */
    public Poker getOnePoker(){
        if (pokers.size() > 0){
            //获取第一张牌
            Poker poker = pokers.get(0);

            //将这张牌从数组里面删除
            pokers.remove(0);

            return poker;
        }
        return null;
    }

    public void setListener(IGameInitListener listener) {
        this.listener = listener;
    }
}
