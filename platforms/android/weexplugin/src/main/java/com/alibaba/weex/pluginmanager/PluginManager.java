package com.alibaba.weex.pluginmanager;

import android.content.Context;

import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by budao on 2016/10/25.
 */
public class PluginManager {
  private static HashMap<String, PluginEntry> sComponents = new HashMap<>();
  private static HashMap<String, PluginEntry> sModules = new HashMap<>();

  /**
   * Load plugin settings from config file config.xml and register them to weex core.
   */
  public static void init(Context context) {
    loadConfig(context);
    registerComponents(getComponents());
    registerModules(getModules());
  }

  public static void registerComponent(String name, String className) {
    try {
      Class clazz = Class.forName(className);
      WXSDKEngine.registerComponent(name, clazz);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (WXException e) {
      e.printStackTrace();
    }
  }

  public static void registerModule(String name, String className) {
    try {
      Class clazz = Class.forName(className);
      WXSDKEngine.registerModule(name, clazz);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (WXException e) {
      e.printStackTrace();
    }
  }

  public static void registerComponents(HashMap<String, PluginEntry> components) {
    for (Map.Entry<String, PluginEntry> component : components.entrySet()) {
      registerComponent(component.getKey(), component.getValue().mPluginClass);
    }
  }

  public static void registerModules(HashMap<String, PluginEntry> modules) {
    for (Map.Entry<String, PluginEntry> module : modules.entrySet()) {
      registerModule(module.getKey(), module.getValue().mPluginClass);
    }
  }

  public static HashMap<String, PluginEntry> getComponents() {
    return sComponents;
  }

  public static void setComponents(HashMap<String, PluginEntry> components) {
    sComponents = components;
  }

  public static HashMap<String, PluginEntry> getModules() {
    return sModules;
  }

  public static void setModules(HashMap<String, PluginEntry> modules) {
    sModules = modules;
  }

  private static void loadConfig(Context context) {
    ConfigXmlParser parser = new ConfigXmlParser();
    parser.parse(context);
    sComponents = parser.getPluginComponents();
    sModules = parser.getPluginModules();
  }
}
