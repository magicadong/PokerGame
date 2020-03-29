package echang.pxd.poker;

/**
 * @Description
 * @Author 彭孝东
 * @QQ 932056657
 */
public class Util {
    public static Object createInstance(String name){
        Object instance = null;
        try {
            Class<?> clz = Class.forName(name);
            instance =  clz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return instance;
        }
    }
}
