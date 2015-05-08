package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import java.util.List;

public interface ILbFacade extends IBizCtrl
{
    public String getBiggestNumber() throws BOSException;
    public boolean isQuoteProfeseeion(String fprofession) throws BOSException;
    public boolean isQuoteStage(String fstage) throws BOSException;
    public boolean isQuoteScheduleTask(String strFachievementTypeId) throws BOSException;
    public String getBiggestNumber2() throws BOSException;
    public String getBiggestNumber3() throws BOSException;
    public List getAchievementManagerHeader1() throws BOSException;
    public List getAchievementManagerHeader2() throws BOSException;
    public List getAchievementManagerData(String curproId) throws BOSException;
    public AchievementTypeInfo getAchievementManagerInfo(String taskTypeName) throws BOSException;
    public FDCScheduleInfo isMain(String taskid) throws BOSException;
}