package eon.hg.fap.hessian.job;

import eon.hg.fap.hessian.ibs.IPreLogin;
import eon.hg.fap.hessian.ibs.ISysBillType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Async
@Component
public class ScheduledService {

    @Autowired
    private IPreLogin preLogin;

    @Autowired
    private ISysBillType sysBillType;

    @Scheduled(cron = "0/50 * * * * *")
    public void scheduled(){
        log.info("=====>>>>>使用cron  {}",System.currentTimeMillis());
        List list =sysBillType.getBusvou_type();
        System.out.println(list.get(0));
    }
    @Scheduled(fixedRate = 5000)
    public void scheduled1() {
        log.info("=====>>>>>使用fixedRate{}", System.currentTimeMillis());
    }
    @Scheduled(fixedDelay = 5000)
    public void scheduled2() {
        log.info("=====>>>>>fixedDelay{}",System.currentTimeMillis());
        try {
            List list = preLogin.queryBusiYear();
            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
