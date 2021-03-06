package echang.pxd.poker.game;

import java.util.Scanner;

import echang.pxd.poker.Constants;
import echang.pxd.poker.Util;
import echang.pxd.poker.player.Player;
import echang.pxd.poker.poker.Poker;
import echang.pxd.poker.poker.PokerManager;
import echang.pxd.poker.player.PlayerManager;

/**
 * @Description
 * @Author 彭孝东
 * @QQ 932056657
 */
public class PokerGameCenter extends AGameCenter {
    private static PokerGameCenter instance;
    private int liveCount;
    private int currentPlayerIndex;
    private int lastPlayerBet;
    private boolean isAllIn = false;
    private int allInPosition;
    private int smallestAllinBet;

    private PokerGameCenter() {
    }

    public static PokerGameCenter getInstance() {
        if (instance == null) {
            synchronized (AGameCenter.class) {
                if (instance == null) {
                    instance = new PokerGameCenter();
                }
            }
        }
        return instance;
    }

    @Override
    protected void initGame() {
        //创建对象
        playerManager = PlayerManager.getManager();
        pokerManager = PokerManager.getManager();
        ante = 0;
        totalPlayer = 3;
        liveCount = totalPlayer;
        bottom = 5;
        currentPlayerIndex = 1;

        //设置监听者
        playerManager.setListener(this);
        pokerManager.setListener(this);

        //初始化完毕 成功的计数器+1
        setTotalSuccess(getTotalSuccess() + 1);
    }

    @Override
    protected void initPokers() {
        pokerManager.initPokers();
    }

    @Override
    protected void initPlayers() {
        playerManager.initPlayers(totalPlayer);
    }

    @Override
    protected void start() {
        //先扣底注钱
        playerManager.deDuctMoney(bottom);
        ante = totalPlayer * bottom;

        //发牌
        dealCards();

        //开始下注
        startBets();
    }

    /**
     * 开始下注
     */
    private void startBets() {
        while (liveCount > 1) {
            if (currentPlayerIndex == allInPosition) {
                //直接比大小
                break;
            }
            Player player = playerManager.getPlayer(currentPlayerIndex - 1);

            //判断当前这个人是否已经弃牌
            if (player.playerState == Constants.IPlayerState.DISCARD){
                //这个人已经弃牌，下面不需要做
                changeCurrentIndex();
                continue;
            }
            //判断当前这个人的提示信息
            if (player.chip <= lastPlayerBet || isAllIn){
                //"all-in","弃牌"
                //显示操作列表
                Util.show(true, true, Constants.IBet.LESS);
                //获取当前玩家信息
                Util.show("请" + player.id + "号玩家选择操作(" + player.name + " $" + player.chip + "):");
                //接收用户的输入
                int choice = input(2, 1);
                switch (choice) {
                    case 1:
                        //all-in
                        allIn(player);
                        break;
                    case 2:
                        //弃牌
                        giveup(player);
                        break;
                }
            }else{
                //"下注", "跟注", "all-in", "比牌", "弃牌"
                Util.show(true, true, Constants.IBet.NORMAL);
                //获取当前玩家信息
                Util.show("请" + player.id + "号玩家选择操作(" + player.name + " $" + player.chip + "):");
                //接收用户的输入
                int choice = input(5, 1);
                switch (choice) {
                    case 1:
                        //下注
                        bet(player);
                        break;
                    case 2:
                        //跟注
                        follow(player);
                        break;
                    case 3:
                        //all-in
                        allIn(player);
                        break;
                    case 4:

                        break;
                    case 5:
                        //弃牌
                        giveup(player);
                        break;
                }
            }
            //当前玩家索引指向下一个
            changeCurrentIndex();

        }
        //游戏结束
        playerManager.awardPlayer(ante,smallestAllinBet);
        System.out.println(playerManager.players);
    }

    private void changeCurrentIndex(){
        //当前玩家索引指向下一个
        currentPlayerIndex++;
        if (currentPlayerIndex > totalPlayer) {
            currentPlayerIndex = 1;
        }
    }

    /**
     * all-in
     * 当一个人选择all-in 之比较一次，最大的赢 就结束了
     * A 40 - 40all-in
     * B 20 - 50all-in
     * C 400 - 400
     */
    private void allIn(Player player){
        if (isAllIn){
            //比较我们两个all-in的值
            if (player.chip < smallestAllinBet){
                smallestAllinBet = player.chip;
            }
        }else{
            //第一次all-in
            isAllIn = true;
            //记录当前all-in最小值
            smallestAllinBet = player.chip;
            //记录当前all-in起始点
            allInPosition = currentPlayerIndex;
        }

        //当前这个人all-in
        player.currentBet = player.chip;
        //总金额
        ante += player.chip;
        //下注所有
        player.lostMoney(player.chip);
    }
    /**
     * 跟注
     */
    private void follow(Player player){
        //总金额增加
        ante += lastPlayerBet;
        //扣除这个人的筹谋
        player.lostMoney(lastPlayerBet);
    }

    /**
     * 弃牌
     */
    private void giveup(Player player){
        //弃牌
        player.playerState = Constants.IPlayerState.DISCARD;
        //在玩的玩家数-1
        liveCount--;
    }

    /**
     * 下注
     *
     * @param player
     */
    private void bet(Player player) {
        Util.show("请输入下注金额:");
        int total = input(player.chip, lastPlayerBet);
        //总金额增加
        ante += total;
        //扣除这个人的筹谋
        player.lostMoney(total);
        //记录这次下注金额
        lastPlayerBet = total;
    }

    /**
     * 输入数据
     *
     * @param max
     * @param min
     * @return
     */
    private int input(int max, int min) {
        Scanner scanner = new Scanner(System.in);
        while (1 > 0) {

            int num = scanner.nextInt();
            if (num >= min && num <= max) {
                return num;
            }
            Util.show("数据不正确,请重新输入:");
        }
    }

    /**
     * 发牌
     */
    private void dealCards() {
        int count = playerManager.getPlayerCount();
        for (int i = 0; i < count; i++) {
            //从扑克中心获取一张牌
            Poker poker = pokerManager.getOnePoker();
            //将这张牌发给对应的人
            Player player = playerManager.getPlayer(i);
            player.poker = poker;
        }

        System.out.println(playerManager.players);
    }
}







