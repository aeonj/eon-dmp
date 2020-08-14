package eon.hg.fap.uflo.service;

import com.bstek.uflo.command.CommandService;
import com.bstek.uflo.service.impl.DefaultTaskService;

public class UserTaskService extends DefaultTaskService {
    private CommandService commandService;

    public void setCommandService(CommandService commandService) {
        super.setCommandService(commandService);
        this.commandService = commandService;
    }

}
