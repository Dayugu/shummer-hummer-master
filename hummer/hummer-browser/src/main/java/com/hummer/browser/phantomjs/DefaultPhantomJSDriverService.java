package com.hummer.browser.phantomjs;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;

import java.io.File;
import java.io.IOException;

/**
 * @Discribe
 * @Author gzy
 * @Date 2018/11/5 10:18
 */
public class DefaultPhantomJSDriverService {

    private  PhantomJSDriverService phantomJSDriverService;

    /**
     * 创建默认的PhantomJSDriverService
     */
    public DefaultPhantomJSDriverService(File exe,int port,ImmutableList<String> args,
                                         ImmutableMap<String, String> env) {
        PhantomJSDriverService.Builder builder = new PhantomJSDriverService.Builder();

        builder.usingPort(port);
        builder.withEnvironment(env);
        builder.usingPhantomJSExecutable(exe);
        //builder.withArgs(args).withEnvironment(environment);
        String[] strs = new String[args.size()];
        builder.usingCommandLineArguments(args.toArray(strs));
        PhantomJSDriverService phantomJSDriverService = builder.build();

        this.phantomJSDriverService = phantomJSDriverService;
    }


    public PhantomJSDriverService getPhantomJSDriverService() {
        return phantomJSDriverService;
    }

}
