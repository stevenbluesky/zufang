package com.ant.util;


import com.alibaba.fastjson.JSON;
import java.util.HashMap;
import java.util.Map;

public class ResultUtil
{
  public static String getSuccessInfo(String message, Object otherObj)
  {
    Map<String , Object> map = new HashMap<String , Object>();
    map.put("success", Integer.valueOf(1));
    map.put("message", message);
    map.put("object", otherObj);
    return JSON.toJSONString(map);
  }


  public static String getFailureInfo(int flag, String message)
  {
    Map<String , Object> map = new HashMap<String , Object>();
    map.put("success", Integer.valueOf(flag));
    map.put("message", message);
    return JSON.toJSONString(map);
  }

  public static String getFailureInfo(String message)
  {
    Map<String , Object> map = new HashMap<String , Object>();
    map.put("success", Integer.valueOf(0));
    map.put("message", message);
    return JSON.toJSONString(map);
  }

  public static String getSessionFailureInfo()
  {
    Map<String , Object> map = new HashMap<String , Object>();
    map.put("session", Boolean.valueOf(false));
    return JSON.toJSONString(map);
  }
}
