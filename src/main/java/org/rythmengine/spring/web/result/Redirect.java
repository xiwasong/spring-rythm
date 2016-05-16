package org.rythmengine.spring.web.result;

import org.osgl.util.E;
import org.osgl.util.S;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Created by luog on 30/12/13.
 */
@SuppressWarnings("serial")
public class Redirect extends Result {

    /**
     * @param url the url to redirect
     */
    public Redirect(String url) {
        super(HttpStatus.TEMPORARY_REDIRECT, url);
        E.npeIf(S.empty(url));
    }

    public Redirect(String url, Object ... args) {
        super(HttpStatus.TEMPORARY_REDIRECT, S.fmt(url, args));
        E.npeIf(S.empty(url));
    }

    @Override
    protected ModelAndView writeToResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
        //response.sendRedirect(getMessage());
        return new ModelAndView("redirect:" + getMessage());
    }
}
