package eon.hg.fap.uflo.event;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.listener.ProcessListener;
import org.springframework.stereotype.Service;

/**
 * 基础流程启动事件处理
 */
@Service
public class BaseProcessListener implements ProcessListener {
    @Override
    public void processStart(ProcessInstance processInstance, Context context) {

    }

    @Override
    public void processEnd(ProcessInstance processInstance, Context context) {

    }
}
