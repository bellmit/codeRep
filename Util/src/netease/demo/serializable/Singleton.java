package netease.demo.serializable;

import java.io.Serializable;

/**
 * @author hzzhuhao1
 *  2016年3月4日
 *  测试序列化对单例的破坏
 */
@SuppressWarnings("serial")
public class Singleton implements Serializable{
    private volatile static Singleton singleton;
    private Singleton (){}
    public static Singleton getSingleton() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
    
    
    //加上这个方法能防止对序列化对单例的破坏
    private Object readResolve() {
        return singleton;
    }
}