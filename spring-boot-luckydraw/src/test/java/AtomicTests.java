import com.bugjc.grocery.UserDrawCountAtomic;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Auther: qingyang
 * @Date: 2018/6/11 00:11
 * @Description:
 */
public class AtomicTests {

    @Test
    public void testUserDrawCountAtomic(){
        String userId = "1";
        UserDrawCountAtomic.init(userId,new AtomicInteger(10));
        UserDrawCountAtomic.clear(userId);
        UserDrawCountAtomic.init(userId,new AtomicInteger(11));
        System.out.println(UserDrawCountAtomic.decrementAndGet(userId));
        System.out.println(UserDrawCountAtomic.decrementAndGet(userId));
    }

}
