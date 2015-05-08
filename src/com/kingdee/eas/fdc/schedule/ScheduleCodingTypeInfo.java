package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;

public class ScheduleCodingTypeInfo extends AbstractScheduleCodingTypeInfo implements Serializable 
{
//	主项任务
	public static final String MAINTASK_CODINGID = "HDFh7Jv6SzunFhWu1p94khl/whE=";
	public static final String MAINTASK_CODINGNUM = "主项任务";
//	专项任务
	public static final String SPECIALTASK_CODINGID = "MfsBXhLbTD24smWF1GKi9Bl/whE=";
	public static final String SPECIALTASK_CODINGNUM = "专项任务";
    public ScheduleCodingTypeInfo()
    {
        super();
    }
    protected ScheduleCodingTypeInfo(String pkField)
    {
        super(pkField);
    }
}