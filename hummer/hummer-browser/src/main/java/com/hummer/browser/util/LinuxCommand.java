package com.hummer.browser.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LinuxCommand {
    private static Logger logger = LoggerFactory.getLogger(LinuxCommand.class);


    /**
     * 执行linux命令
     *
     * @param command
     */
    public static void exec(String command) {
        Runtime runtime = Runtime.getRuntime();
        logger.info("【LinuxCommand执行器】:starting exec linux command: " + command);
        if (StringUtils.isBlank(command)) return;
        try {
            //查找进程号
            Process p = runtime.exec(command);
            InputStream inputStream = p.getInputStream();
            List<String> read = read(inputStream, "UTF-8");
            logger.info("【LinuxCommand执行器】:linux command exec end ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> read(InputStream in, String charset) throws IOException {
        List<String> data = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("COMMAND")) continue;
            logger.info("【LinuxCommand执行器】:" + line);
        }
        reader.close();
        return data;
    }

}