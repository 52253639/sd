package com.kingdee.eas.fdc.schedule.framework.parser;

import java.util.List;

import net.sourceforge.ganttproject.parser.GPParser;
import net.sourceforge.ganttproject.parser.ParsingContext;
import net.sourceforge.ganttproject.parser.ParsingListener;

public interface IKDParser{
	void addHandler(IKDHandler handler);
	List getHandlers();
    void addParsingListener(ParsingListener listener);
    ParsingContext getContext();
    void parse();
    /**
     * 再次解析,实现reHandle方法的handle将被执行
     */
    void reParse();
}
