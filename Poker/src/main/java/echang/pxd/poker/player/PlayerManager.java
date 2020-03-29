package echang.pxd.poker.player;

import java.util.ArrayList;

import echang.pxd.poker.Constants;
import echang.pxd.poker.Util;
import echang.pxd.poker.game.IGameInitListener;

/**
 * @Description
 * @Author 彭孝东
 * @QQ 932056657
 */
public class PlayerManager {
    private IGameInitListener listener;
    private static PlayerManager manager;
    public ArrayList<Player> players;

    private PlayerManager(){

    }

    public static PlayerManager getManager(){
        if (manager == null){
            synchronized (PlayerManager.class){
                if (manager == null){
                    manager = new PlayerManager();
                }
            }
        }
        return manager;
    }

    /**
     * 初始化玩家 姓名随机
     * @param totalPlayer
     */
    public void initPlayers(int totalPlayer){
        //创建输出
        players = new ArrayList<>();

        for (int i = 0; i < totalPlayer; i++){
            //获取玩家名字
            String name = Util.autoGenerateName();
            //创建玩家对象
            Player player = new Player(i+1,name, Constants.IPlayer.CHAPS);
            //保存玩家
            players.add(player);
        }

        //当玩家初始化成功 就回调成功的事件给监听者（游戏中心）
        if (listener != null){
            listener.onInitSuccess();
        }
    }

    /**
     * 所有玩家扣钱:打底
     * @param count
     */
    public void deDuctMoney(int count){
        for (Player player: players){
            player.lostMoney(count);
        }
    }

    /**
     * 后去玩家人数
     * @return
     */
    public int getPlayerCount(){
        return players.size();
    }

    /**
     * 获取一个玩家
     * @param index
     * @return
     */
    public Player getPlayer(int index){
        return players.get(index);
    }

    public void awardPlayer(int money){
        for (Player player: players){
            if (player.playerState == Constants.IPlayerState.HAND){
                player.winMoney(money);
            }
        }
    }

    public void setListener(IGameInitListener listener) {
        this.listener = listener;
    }
}
