package echang.pxd.poker.player;

import java.util.ArrayList;

import echang.pxd.poker.Constants;
import echang.pxd.poker.poker.Poker;

/**
 * @Description
 * @Author 彭孝东
 * @QQ 932056657
 */
public class Player {
    public int id; //编号
    public String name;//名字
    public int chip; //筹码
    public Poker poker; //牌
    public ArrayList<Poker> pokers; //多张牌
    public int playerState;//玩家的状态

    public Player(int id, String name, int chip) {
        this.id = id;
        this.name = name;
        this.chip = chip;

        //初始化状态
        playerState = Constants.IPlayerState.HAND;
    }

    /**
     * 扣钱
     * @param count
     */
    public void lostMoney(int count){
        chip -= count;
    }

    /**
     * 赢钱了
     * @param count
     */
    public void winMoney(int count){
        chip += count;
    }

    @Override
    public String toString() {
        return "id:"+id+" name:"+name+" "+poker.dot+poker.type.pic+"("+chip+")";
    }
}
