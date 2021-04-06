package eon.hg.fap.flow.console.handler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ServletHandler {
    void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
    String url();
}
