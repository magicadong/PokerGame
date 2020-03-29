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

    /**
     * A 50
     * B 20
     * C 20
     * @param money
     */
    public void awardPlayer(int money,int smallestAllinBet){
        Player max = null;
        for (Player player: players){
            if (player.playerState == Constants.IPlayerState.HAND){
                if (max == null){
                    //找到第一个没有弃牌的人
                    max = player;
                }else{
                    int result = max.poker.compareTo(player.poker);
                    if (result == -1){
                        //max对应的牌比player的牌要小
                        //max记录最大的哪个玩家
                        max = player;
                    }
                }
            }
        }
        //最大的人赢钱
        max.winMoney(money);

        //A 500 -100 = 400
        //B 200 -100 = 100
        //C 100
        //100*3 = 300
        //800 - 500 = 300;
        if (smallestAllinBet == 0){
            return;
        }
        //将max之外的所有all-in的人多于的钱返还
        int totalReturn = 0;
        for (Player player: players){
            //找到没有弃牌 并且 不是当前最大的那个人
            if (!player.equals(max) && player.playerState==Constants.IPlayerState.HAND){
                player.returnMoney(player.currentBet-smallestAllinBet);
                totalReturn += (player.currentBet-smallestAllinBet);
            }
        }
        //从max中退回多余的钱
        max.lostMoney(totalReturn);
    }

    public void setListener(IGameInitListener listener) {
        this.listener = listener;
    }
}
