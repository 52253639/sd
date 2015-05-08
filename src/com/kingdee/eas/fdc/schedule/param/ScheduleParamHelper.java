package com.kingdee.eas.fdc.schedule.param;

import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.base.param.IParamControl;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.contract.FDCUtils;

public class ScheduleParamHelper {
	/**
	 * 允许在任务审批后修改并保持新增的WBS节点任务
	 */
	public static final String FDCSCH_PARAM_CANSAVEADDNEWTASK = "FDCSCH001";
	/**
	 * 支持月度计划导入主项计划
	 */
	public static final String FDCSCH_PARAM_MONTHPLANIMPORTMAINTASK = "FDCSCH002";
	//进度管理  是否允许在计划审批后添加新
	

	
	/**
     * 获得房地产系统进度管理的参数
     * 
     * @param ctx
     * @param companyID
     * @return
     */
    public static HashMap getAllSchParam(Context ctx, String companyID) throws BOSException, EASBizException {
		// IObjectPK comPK = null;
		// if(companyID!=null){
		// comPK = new ObjectUuidPK(companyID);
		// }
        
        HashMap hmParamIn = new HashMap();
        hmParamIn.put(FDCSCH_PARAM_CANSAVEADDNEWTASK, null);
        hmParamIn.put(FDCSCH_PARAM_MONTHPLANIMPORTMAINTASK, null);
        
        IParamControl pc;
        if(ctx!=null)
        	pc = ParamControlFactory.getLocalInstance(ctx);
        else
        	pc= ParamControlFactory.getRemoteInstance();
        HashMap hmAllParam = pc.getParamHashMap(hmParamIn);
        
        return hmAllParam;
    }
    
    /**
     * 获得房地产系统参数,仅限于值为是/否类型的参数
     * 
     * @param ctx
     * @param companyID
     * @param fdcParamKey
     * @return
     */
    public static boolean getSchParamByKey(Context ctx, String companyID,String fdcParamKey) throws BOSException, EASBizException {
        HashMap hmAllParam = getAllSchParam(ctx,companyID);
        Object theValue = hmAllParam.get(fdcParamKey);
        if(theValue != null){
			return Boolean.valueOf(theValue.toString()).booleanValue();
		}
        return false;
    }
    
    /**
     * 
     * 易用性方法,通过传入参数值Map,及参数编码取参数,用于批量取参数的实现
     * 例:
     * Map paramMap=FDCUtils.getDefaultFDCParam(...);
     * boolean flag=FDCUtils.getParamValue(paramMap,FDCConstants.FDC_PARAM_BUDGET_DISPLAYCOST);
     *  by sxhong 2008-09-23 17:03:05
     * @param paramMap
     * @param fdcParamKey
     * @return
     */
    public static boolean getParamValue(Map paramMap,String fdcParamKey){
        return FDCUtils.getParamValue(paramMap, fdcParamKey);
    }
}
