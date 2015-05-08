package com.kingdee.eas.fdc.schedule.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.schedule.AchievementTypeInfo;
import java.util.List;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface LbFacadeController extends BizController
{
    public String getBiggestNumber(Context ctx) throws BOSException, RemoteException;
    public boolean isQuoteProfeseeion(Context ctx, String fprofession) throws BOSException, RemoteException;
    public boolean isQuoteStage(Context ctx, String fstage) throws BOSException, RemoteException;
    public boolean isQuoteScheduleTask(Context ctx, String strFachievementTypeId) throws BOSException, RemoteException;
    public String getBiggestNumber2(Context ctx) throws BOSException, RemoteException;
    public String getBiggestNumber3(Context ctx) throws BOSException, RemoteException;
    public List getAchievementManagerHeader1(Context ctx) throws BOSException, RemoteException;
    public List getAchievementManagerHeader2(Context ctx) throws BOSException, RemoteException;
    public List getAchievementManagerData(Context ctx, String curproId) throws BOSException, RemoteException;
    public AchievementTypeInfo getAchievementManagerInfo(Context ctx, String taskTypeName) throws BOSException, RemoteException;
    public FDCScheduleInfo isMain(Context ctx, String taskid) throws BOSException, RemoteException;
}