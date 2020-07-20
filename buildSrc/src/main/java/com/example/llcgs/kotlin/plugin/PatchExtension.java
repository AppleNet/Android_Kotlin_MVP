package com.example.llcgs.kotlin.plugin;

/**
 * com.example.kotlin.plugin.PatchPlugin
 *
 * @author liulongchao
 * @since  2020-06-22
 *
 */
public class PatchExtension {

    boolean debugOn;
    String output;
    String applicationName;


    public PatchExtension() {
        debugOn = false;
    }

    public void setDebugOn(boolean debugOn) {
        this.debugOn = debugOn;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
