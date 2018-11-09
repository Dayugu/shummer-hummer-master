package com.hummer.browser.util;

import java.util.ResourceBundle;

public class Constants {

    public static final String SPLIT_ARRAY_STRING = ",";
    /**
     * 脚本命名常量
     */
    //公共属性命名
    public static final String ACTION_ATTR_TYPE = "type";
    public static final String ACTION_ATTR_DESC = "desc";
    public static final String ACTION_ATTR_SELECTOR = "selector";
    public static final String ACTION_ATTR_IFRAME_SELECTOR = "iframeselector";

    /*******************************************************************/
    public static final String ACTION_TYPE_INPUT_ARRAY = "inputlist";
    /*******************************************************************/

    //ACTION分类命名
    public static final String ACTION_TYPE_START = "start";
    public static final String ACTION_TYPE_END = "end";
    public static final String ACTION_TYPE_PAGESTART = "pagestart";
    public static final String ACTION_TYPE_PAGEEND = "pageend";
    public static final String ACTION_TYPE_LISTSTART = "liststart";
    public static final String ACTION_TYPE_LISTEND = "listend";
    public static final String ACTION_TYPE_ARRAYSTART = "arraystart";
    public static final String ACTION_TYPE_ARRAYEND = "arrayend";
    public static final String ACTION_TYPE_OPEN = "open";
    public static final String ACTION_TYPE_CLICK = "click";
    public static final String ACTION_TYPE_INPUT = "input";
    public static final String ACTION_TYPE_FIXED = "fixedvalue";
    public static final String ACTION_TYPE_GET = "get";
    public static final String ACTION_TYPE_WAIT = "cwait";
    public static final String ACTION_TYPE_CONDITION = "condition";
    public static final String ACTION_TYPE_CLOSETAB = "closetab";
    public static final String ACTION_TYPE_OPENTAB = "opentab";
    //Action特有属性
    public static final String ACTION_ATTR_MAXPAGE = "maxpage";
    public static final String ACTION_ATTR_TIMEOUT = "timeout";
    public static final String ACTION_ATTR_WAIT = "wait";
    public static final String ACTION_ATTR_DATAS = "datas";
    public static final String ACTION_ATTR_URL = "url";
    //datas属性
    public static final String ACTION_ATTR_COLUMN = "column";
    public static final String ACTION_ATTR_FROM = "from";
    public static final String ACTION_ATTR_REGX = "regx";
    public static final String ACTION_ATTR_REGVALUE = "regvalue";
    public static final String ACTION_ATTR_DEFAULTVALUE = "defaultvalue";
    public static final String ACTION_ATTR_FIXEDVALUE = "fixedvalue";

    public static final String DATA_TYPE_NAME = "datatype";
    public static final String DATA_TYPE_FIXED = "fixed";
    public static final String DATA_TYPE_ABSOLUTE = "absolute";
    public static final String DATA_TYPE_RELATIVE = "relative";

    public static final String DATA_FROM_TEXT = "text";
    public static final String DATA_FROM_HTML = "html";
    public static final String DATA_FROM_INNERHTML = "innerHTML";
    public static final String DATA_FROM_OUTERHTML = "outerHTML";
    public static final String DATA_FROM_SRC = "src";
    public static final String DATA_FROM_HREF = "href";

    public static final String DATA_FROM_PAGE_DOMAIN = "page_domain";
    public static final String DATA_FROM_PAGE_TITLE = "page_title";
    public static final String DATA_FROM_PAGE_KEYWORDS = "page_keywords";
    public static final String DATA_FROM_PAGE_DESCRIPTION = "page_description";
    public static final String DATA_FROM_PAGE_ENCODING = "page_encoding";
    public static final String DATA_FROM_PAGE_URL = "page_url";

    public static final String TAB_TYPE_BLANK = "blank";
    public static final String TAB_TYPE_SELF = "self";
    public static final String TAB_TYPE_NO = "notab";

    public static final String CONDITION_ELEMENT_EXIST = "elementexist";

    public static final String CONDITION_CALLBACK_CONTINUE = "continue";
    public static final String CONDITION_CALLBACK_BREAK = "break";
    public static final String CONDITION_CALLBACK_NEXT = "next";
    public static final String CONDITION_CALLBACK_END = "end";
    public static final String CONDITION_CALLBACK_GEET = "geet";
    public static final String CONDITION_CALLBACK_SLIDER = "slider";

    //标签tag类型
    public static final String HTML_TAG_A = "a";

    
    public static final String PATH_DATA_FILE_WINDOWS = ResourceBundle.getBundle("system-config").getString("path_data_file_windows");
    public static final String PATH_DATA_FILE_LINUX = ResourceBundle.getBundle("system-config").getString("path_data_file_linux");

    public static final String TYPE_OUT_JSON = "json";
    public static final String TYPE_OUT_LINE = "line";

    /*********************************************************************************************/
//	public static final Integer NUMBER_OF_TASKS_PER_ENGINE_CRAWLER = 5;

    public static final String IDDRESS_OF_SERVER = ResourceBundle.getBundle("system-config").getString("iddress_of_server");

//	public static final String IDDRESS_OF_LOCAL = ResourceBundle.getBundle("system-config").getString("iddress_of_local");

    public static final String URL_OF_GETTASK_IN_REGISTER = IDDRESS_OF_SERVER + "/rzx-engine-control-http/engine/getTaskByEngine";

//	public static final String URL_OF_DOWNLOAD_RESULT_IN_REGISTER = "http://127.0.0.1:8888/rzx-engine-control-http/engine/download";

    public static final String URL_OF_RETURN_TASK_INFO = IDDRESS_OF_SERVER + "/rzx-engine-control-http/engine/completeTaskByEngine";
    /*********************************************************************************************/

}
