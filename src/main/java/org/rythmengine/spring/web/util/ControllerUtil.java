package org.rythmengine.spring.web.util;

import java.io.File;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgl.util.S;
import org.osgl.web.util.UserAgent;
import org.rythmengine.spring.web.Flash;
import org.rythmengine.spring.web.HttpUtils;
import org.rythmengine.spring.web.ServerContext;
import org.rythmengine.spring.web.Session;
import org.rythmengine.spring.web.SessionManager;
import org.rythmengine.spring.web.UADetector;
import org.rythmengine.spring.web.result.BadRequest;
import org.rythmengine.spring.web.result.BinaryResult;
import org.rythmengine.spring.web.result.Forbidden;
import org.rythmengine.spring.web.result.NotFound;
import org.rythmengine.spring.web.result.Ok;
import org.rythmengine.spring.web.result.Redirect;
import org.rythmengine.spring.web.result.TextResult;

/**
 * The base controller class provides utility methods to
 * send out response easily
 */
public abstract class ControllerUtil {

    protected final void notFoundIf(boolean predicate) {
        if (predicate) notFound();
    }

    protected final void notFoundIfNot(boolean predicate) {
        if (!predicate) notFound();
    }

    protected static NotFound notFound() {
        throw new NotFound();
    }

    protected static NotFound notFound(String reason) {
        throw new NotFound(reason);
    }

    protected static void notFoundIfNull(Object object) {
        if (null == object) notFound();
    }

    protected static void notFoundIfNull(Object object, String reason) {
        if (null == object) notFound(reason);
    }

    protected static void forbidden() {
        throw new Forbidden();
    }

    protected static void badRequest() {
        throw new BadRequest();
    }

    protected static void badRequest(String reason) {
        throw new BadRequest(reason);
    }

    protected static void badRequestIf(boolean expression) {
        if (expression) badRequest();
    }

    protected static void badRequestIf(boolean expression, String reason) {
        if (expression) badRequest(reason);
    }

    protected static void badRequestIfNot(boolean expression) {
        if (!expression) badRequest();
    }

    protected static void badRequestIfNot(boolean expression, String reason) {
        if (!expression) badRequest();
    }

    protected static void ok() {
        throw new Ok();
    }

    protected static void renderText(String text) {
        throw new TextResult(text);
    }

    protected static void renderText(String fmt, Object... args) {
        throw new TextResult(fmt, args);
    }


    protected static void setJSON() {
        // IE9 doesn't support application/json
        UserAgent ua = UADetector.get();
        response().setContentType(ua.isIE9Down() ? "text/plain" : "application/json");
    }

    /**
     * Return a 200 OK application/binary response
     * @param is The stream to copy
     */
    protected static void renderBinary(InputStream is) {
        throw new BinaryResult(is, null, true);
    }

    /**
     * Return a 200 OK application/binary response. Content is streamed.
     *
     * @param is The stream to copy
     * @param length Stream's size in bytes.
     */
    protected static void renderBinary(InputStream is, long length) {
        throw new BinaryResult(is, null, length, true);
    }

    /**
     * Return a 200 OK application/binary response with content-disposition attachment.
     *
     * @param is The stream to copy
     * @param name Name of file user is downloading.
     */
    protected static void renderBinary(InputStream is, String name) {
        throw new BinaryResult(is, name, false);
    }

    /**
     * Return a 200 OK application/binary response with content-disposition attachment.
     *
     * @param is The stream to copy. Content is streamed.
     * @param name Name of file user is downloading.
     * @param length Stream's size in bytes.
     */
    protected static void renderBinary(InputStream is, String name, long length) {
        throw new BinaryResult(is, name, length, false);
    }

    /**
     * Return a 200 OK application/binary response with content-disposition attachment.
     *
     * @param is The stream to copy
     * @param name Name of file user is downloading.
     * @param inline true to set the response Content-Disposition to inline
     */
    protected static void renderBinary(InputStream is, String name, boolean inline) {
        throw new BinaryResult(is, name, inline);
    }

    /**
     * Return a 200 OK application/binary response with content-disposition attachment.
     *
     * @param is The stream to copy
     * @param name The attachment name
     * @param length Stream's size in bytes.
     * @param inline true to set the response Content-Disposition to inline
     */
    protected static void renderBinary(InputStream is, String name, long length, boolean inline) {
        throw new BinaryResult(is, name, length, inline);
    }

    /**
     * Return a 200 OK application/binary response with content-disposition attachment
     * @param is The stream to copy
     * @param name The attachment name
     * @param contentType The content type of the attachment
     * @param inline true to set the response Content-Disposition to inline
     */
    protected static void renderBinary(InputStream is, String name, String contentType, boolean inline) {
        throw new BinaryResult(is, name, contentType, inline);
    }

    /**
     * Return a 200 OK application/binary response with content-disposition attachment.
     *
     * @param is The stream to copy
     * @param name The attachment name
     * @param length Content's byte size.
     * @param contentType The content type of the attachment
     * @param inline true to set the response Content-Disposition to inline
     */
    protected static void renderBinary(InputStream is, String name, long length, String contentType, boolean inline) {
        throw new BinaryResult(is, name, length, contentType, inline);
    }

    /**
     * Return a 200 OK application/binary response
     * @param file The file to copy
     */
    protected static void renderBinary(File file) {
        throw new BinaryResult(file);
    }

    /**
     * Return a 200 OK application/binary response with content-disposition attachment
     * @param file The file to copy
     * @param name The attachment name
     */
    protected static void renderBinary(File file, String name) {
        throw new BinaryResult(file, name);
    }

    protected static void redirect(String url) {
        throw new Redirect(url);
    }

    protected static void redirect(String urlTmpl, Object... args) {
        throw new Redirect(S.fmt(urlTmpl, args));
    }

    protected static void setCookie(String name, String value) {
        SessionManager.setCookie(name, value);
    }

    protected static void setCookie(String name, String value, String expiration) {
        SessionManager.setCookie(name, value, expiration);
    }

    protected static void setCookie(String name, String value, String domain, String path, int maxAge, boolean secure) {
        SessionManager.setCookie(name, value, domain, path, maxAge, secure);
    }

    protected static HttpServletRequest request() {
        return SessionManager.request();
    }

    protected static HttpServletResponse response() {
        return SessionManager.response();
    }

    protected static boolean isAjax() {
        return HttpUtils.isAjax(request());
    }

    protected static final boolean isAjax(HttpServletRequest request) {
        return HttpUtils.isAjax(request);
    }

    protected static final Session session() {
        return Session.current();
    }

    protected static final Flash flash() {
        return Flash.current();
    }

    private static class Context {
        String serverName;
        int port;
        @SuppressWarnings("unused")
		int securePort;
        String ctxPath;

        Context(String serverName, int port, int securePort, String ctxtPath) {
            this.serverName = serverName;
            this.port = 0 == port ? 80 : port;
            this.securePort = 0 == securePort ? 443 : securePort;
            this.ctxPath = S.empty(ctxtPath) ? "" : ctxtPath;
        }
    }

    private static volatile Context context;

    private static Context getContext() {
        if (null != context) return context;
        synchronized (ControllerUtil.class) {
            if (null != context) return context;
            context = new Context(ServerContext.getHostName(),
                ServerContext.getPort(),
                ServerContext.getSecurePort(),
                ServerContext.getContextPath());
            return context;
        }
    }

    public static String fullUrl(String url) {
        HttpServletRequest request = SessionManager.request();
        return fullUrl(url, request);
    }

    public static String fullUrl(String url, HttpServletRequest request) {
        if (null == url) url = "/";
        if (url.startsWith("http:") || url.startsWith("https:")) return url;
        if (null != request && !url.startsWith("/")) {
            StringBuffer sb = request.getRequestURL();
            sb.append("/").append(url);
            return sb.toString();
        }
        String scheme, serverName, ctxPath;
        int port;
        if (null != request) {
            scheme = request.isSecure() ? "https://" : "http://";
            serverName = request.getServerName();
            ctxPath = request.getContextPath();
            port = request.getServerPort();
        } else {
            Context context = getContext();
            scheme = "http://";
            serverName = context.serverName;
            ctxPath = context.ctxPath;
            port = context.port;
        }
        StringBuilder sb = new StringBuilder(scheme);
        if (url.startsWith("//")) return sb.append(url.substring(2)).toString();
        sb.append(serverName);
        sb.append(":");
        sb.append(port);
        if (url.startsWith("/")) sb.append(ctxPath).append(url);
        else sb.append(ctxPath).append("/").append(url);
        return sb.toString();
    }

    public static String url(String url) {
        HttpServletRequest request = SessionManager.request();
        return url(url, request);
    }

    public static String url(String url, HttpServletRequest request) {
        if (url == null) return "/";
        if (url.startsWith("//") || url.startsWith("http:") || url.startsWith("https://")) {
            return url;
        }
        if (null != request && !url.startsWith("/")) {
            StringBuilder sb = new StringBuilder(request.getRequestURI());
            sb.append("/").append(url);
            return sb.toString();
        }
        String ctxPath = "";
        if (null != request) {
            ctxPath = request.getContextPath();
        } else if (null != context) {
            ctxPath = context.ctxPath;
        }
        StringBuilder sb = new StringBuilder(ctxPath);
        if (!url.startsWith("/")) sb.append("/");
        sb.append(url);
        return sb.toString();
    }

}
