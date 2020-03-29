package echang.pxd.poker;


import echang.pxd.poker.poker.Poker;

/**
 * @Description
 * @Author 彭孝东
 * @QQ 932056657
 */
public interface Constants {

    interface IBet{
        String[] NORMAL_BET = new String[]{"下注", "跟注", "all-in", "比牌", "弃牌"};
        String[] FIRST_BET = new String[]{"下注", "all-in", "比牌", "弃牌"};
        String[] LOWER_BET = new String[]{"all-in","弃牌"};
    }

    interface IPlayer{
        int CHAPS = 1000;//筹码
    }

    interface IPlayerName{
        String[] NAMES_XING = {"王","李","张","彭"};
        String[] NAMES_MING_M = {"红","涛","国","东","建","强"};
        String[] NAMES_MING_L = {"高","萧","博","督","黎"};
    }
    interface IPlayerState{
        int HAND = 0; //还在玩
        int DISCARD = 1;//弃牌
    }
    /**
     * 扑克牌使用的常量
     */
    interface IPoker{
        //点数
        String[] DOTS = {"2","3","4","5","6","7","8","9","10","J","Q","K","A"};
        //四种花色
        Poker.PicType[] PIC_TYPES = {Poker.PicType.SPADE, Poker.PicType.HEARTS,Poker.PicType.CLUBS, Poker.PicType.DIAMONDS};
    }
}

