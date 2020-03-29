package echang.pxd.poker.poker;

/**
 * @Description
 * @Author 彭孝东
 * @QQ 932056657
 */

import echang.pxd.poker.Constants;
import echang.pxd.poker.Util;
import echang.pxd.poker.player.Player;

/**
 * 2 3 4 5 6 7 8 9 10 J Q K A
 */
public class Poker {
    public String dot; //牌的点数
    public PicType type;//花色对象

    public Poker(String dot, PicType type) {
        this.dot = dot;
        this.type = type;
    }

    public static class PicType {
        public String pic; //花色
        public int tag; //花色对应的tag值

        public static final PicType SPADE = new PicType("♠",4);
        public static final PicType HEARTS = new PicType("♥",3);
        public static final PicType CLUBS = new PicType("♣",2);
        public static final PicType DIAMONDS = new PicType("♦",1);

        public PicType(String pic, int tag) {
            this.pic = pic;
            this.tag = tag;
        }
    }

    /**
     * 比较两张牌的大小
     * @return 1:A>B   -1:A < B
     */
    public int compareTo(Poker another){
        //1.首先比点数
        int index_m = Util.indexOfObject(this.dot,Constants.IPoker.DOTS);
        int index_a = Util.indexOfObject(another.dot,Constants.IPoker.DOTS);
        if (index_a == index_m){
            //2.如果点数相同比花色
            if(this.type.tag > another.type.tag){
                return 1;
            }else{
                return -1;
            }
        }else{
            if (index_m > index_a){
                return 1;
            }else{
                return -1;
            }
        }

    }

    @Override
    public String toString() {
        return dot+type.pic;
    }
}
