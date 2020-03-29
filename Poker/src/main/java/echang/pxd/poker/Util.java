package echang.pxd.poker;

import java.util.Random;

/**
 * @Description
 * @Author 彭孝东
 * @QQ 932056657
 */

public class Util {
    /**
     * 自定生成名字
     * @return
     */
    public static String autoGenerateName(){
        Random random = new Random();
        //姓名的随机数
        int f_index = Math.abs(random.nextInt()%Constants.IPlayerName.NAMES_XING.length);
        int m_index = Math.abs(random.nextInt()%Constants.IPlayerName.NAMES_MING_M.length);
        int l_index = Math.abs(random.nextInt()%Constants.IPlayerName.NAMES_MING_L.length);

        String f = Constants.IPlayerName.NAMES_XING[f_index];
        String m = Constants.IPlayerName.NAMES_MING_M[m_index];
        String l = Constants.IPlayerName.NAMES_MING_L[l_index];

        return f+m+l;
    }

    /**
     * 输出语句
     */
    public static void show(boolean nextLine,boolean needNumber,String... args){
        StringBuilder builder = new StringBuilder();
        if (needNumber){
            for (int i = 0; i < args.length; i++){
                String content = (i+1)+"."+args[i]+" ";
                builder.append(content);
            }
        }else{
            for (String content: args){
                builder.append(content+" ");
            }
        }

        if (nextLine){
            System.out.println(builder.toString());
        }else{
            System.out.print(builder.toString());
        }
    }

    /**
     * 输出一行不换行 不需要编号的数据
     * @param content
     */
    public static void show(String content){
        show(false,false,new String[]{content});
    }

}
