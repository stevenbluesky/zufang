package com.ant.util;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookiesUtils
{
  public static void addCookie(HttpServletResponse response, String name, String value, int maxAge)
  {
    Cookie cookie = new Cookie(name, value);
    cookie.setPath("/");
    if (maxAge > 0)
      cookie.setMaxAge(maxAge);
    else if (maxAge == 0)
      cookie.setMaxAge(0);
    response.addCookie(cookie);
  }

  public static Cookie getCookieByName(HttpServletRequest request, String name)
  {
    Map cookieMap = ReadCookieMap(request);
    if (cookieMap.containsKey(name)) {
      Cookie cookie = (Cookie)cookieMap.get(name);
      return cookie;
    }
    return null;
  }

  private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request)
  {
    Map cookieMap = new HashMap();
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        cookieMap.put(cookie.getName(), cookie);
      }
    }
    return cookieMap;
  }

  public static String getCookieValueByName(HttpServletRequest request, String name)
  {
    Map cookieMap = ReadCookieMap(request);
    if (cookieMap.containsKey(name)) {
      Cookie cookie = (Cookie)cookieMap.get(name);
      return cookie.getValue();
    }
    return null;
  }

  public static void deleteCookie(HttpServletRequest request, HttpServletResponse response)
  {
    Cookie[] cookies = request.getCookies();

    for (Cookie cookie : cookies) {
      cookie.setMaxAge(0);
      cookie.setPath("");
      response.addCookie(cookie);
    }
  }

  public static String getSchoolIdCookie(HttpServletRequest request, Map<String, String> pmap)
  {
    pmap.put("SCHOOLID", "28");
    return null;
  }
}