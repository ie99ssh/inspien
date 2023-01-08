package com.inspien.customannotation;

import com.inspien.customannotation.container.IOCContainer;
import com.inspien.customannotation.container.impl.IOCContainerImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class IOCContainerTest {

    @Test
    public void test() {
        IOCContainer iocContainer = new IOCContainerImpl();
        iocContainer.initialize("com.inspien.customannotation");
    }


}
