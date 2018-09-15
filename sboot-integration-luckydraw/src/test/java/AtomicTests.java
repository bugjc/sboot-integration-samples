import cn.hutool.core.date.DateUtil;
import org.junit.Test;

/**
 * @Auther: qingyang
 * @Date: 2018/6/11 00:11
 * @Description:
 */
public class AtomicTests {

    @Test
    public void testUserDrawCountAtomic(){
        System.out.println(DateUtil.current(false));
        String userId = "1";
//        UserDrawCountComponent.init(userId,new AtomicInteger(10));
//        UserDrawCountComponent.clear(userId);
//        UserDrawCountComponent.init(userId,new AtomicInteger(11));
//        System.out.println(UserDrawCountComponent.decrementAndGet(userId));
//        System.out.println(UserDrawCountComponent.decrementAndGet(userId));
    }

}
