package com.kingdee.eas.fdc.aimcost.app;

import java.math.BigDecimal;
import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.migrate.K3CostFactory;
import com.kingdee.eas.fdc.migrate.K3CostInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;

/**
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		
 *		
 * @author		黄远昌
 * @version		EAS7.0		
 * @createDate	2011-7-6	 
 * @see						
 */
public class FullProjectDynamicCostTransmission extends AbstractDataTransmission {
	
	private static String resource = "com.kingdee.eas.fdc.aimcost.FullProjectDynamicCostTransmissionResource";
	
	// K3报表对象
	K3CostInfo k3CostInfo = null;
	
	// 项目工程对象
	CurProjectInfo curProjectInfo = null;
	
	// 成本科目对象
	CostAccountInfo costAccountInfo = null;

	/**
	 * @description		
	 * @author			黄远昌		
	 * @createDate		2011-7-6
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see	
	 * (non-Javadoc)
	 * @see com.kingdee.eas.tools.datatask.AbstractDataTransmission#getController(com.kingdee.bos.Context)					
	 */
	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try {
			return K3CostFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException("BOSException: getLocalInstance", e);
		}
	}

	/**
	 * @description		
	 * @author			黄远昌		
	 * @createDate		2011-7-6
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see	
	 * (non-Javadoc)
	 * @see com.kingdee.eas.tools.datatask.IDataTransmission#transmit(java.util.Hashtable, com.kingdee.bos.Context)					
	 */
	public CoreBaseInfo transmit(Hashtable lineData, Context ctx) throws TaskExternalException {
		// 工程项目编码
		String fPrjLongNumber = (String) ((DataToken) lineData.get("FPrjLongNumber")).data;
		
		// 科目编码
		String fAcctLongNumber = (String) ((DataToken) lineData.get("FAcctLongNumber")).data;
		fAcctLongNumber = fAcctLongNumber.replace('!', '.');
		
		// 科目名称
		String fAcctName = (String) ((DataToken) lineData.get("FAcctName")).data;
		
		// 未结算合同金额
		String fUnSettConAmt = (String) ((DataToken) lineData.get("FUnSettConAmt")).data;
		
		// 未结算变更金额
		String fUnSettleChange = (String) ((DataToken) lineData.get("FUnSettleChange")).data;
		
		// 已结算合同金额
		String fSettConAmt = (String) ((DataToken) lineData.get("FSettConAmt")).data;
		
		// 已结算变更金额
		String fSettleChange = (String) ((DataToken) lineData.get("FSettleChange")).data;
		
		// 结算金额
		String fSettSignAmt = (String) ((DataToken) lineData.get("FSettSignAmt")).data;
		
		// 非合同性成本
		String fConWithoutTxtCostAmt = (String) ((DataToken) lineData.get("FConWithoutTxtCostAmt")).data;
		
		// 已发生金额
		String fHappendAmt = (String) ((DataToken) lineData.get("FHappendAmt")).data;
		
		// 已付款金额
		String fHasPayedAmt = (String) ((DataToken) lineData.get("FHasPayedAmt")).data;
		
		// 目前待发生
		String fNotHappenAmt = (String) ((DataToken) lineData.get("FNotHappenAmt")).data;
		
		// 动态成本
		String fDynCostAmt = (String) ((DataToken) lineData.get("FDynCostAmt")).data;
		
		// 目标成本
		String fAimCostAmt = (String) ((DataToken) lineData.get("FAimCostAmt")).data;
		
		// 差异
		String fDiffAmt = (String) ((DataToken) lineData.get("FDiffAmt")).data;
		
		// ZERO金额
		BigDecimal zeroAmt = new BigDecimal(0);
		
		/*
		 * 必录项校验
		 */
		// 工程项目编码必录
		if (StringUtils.isEmpty(fPrjLongNumber)) {
			throw new TaskExternalException(getResource(ctx, "PrjLongNumberNotNull"));
		}

		// 科目编码必录
		if (StringUtils.isEmpty(fAcctLongNumber)) {
			throw new TaskExternalException(getResource(ctx, "AcctLongNumberNotNull"));
		}
		/*
		// 科目名称必录
		if (StringUtils.isEmpty(fAcctName)) {
			throw new TaskExternalException(getResource(ctx, "AcctNameNotNull"));
		}
		*/

		/*
		// 工程项目编码长度校验
		if(fPrjLongNumber != null && fPrjLongNumber.length() > 40) {
			throw new TaskExternalException(getResource(ctx, "PrjLongNumberIsOver40"));
		}
		*/
		
		try {
			// 工程项目编码在EAS系统中存在校验，并取得项目工程对象
			FilterInfo curProjectFilter = new FilterInfo();
			curProjectFilter.getFilterItems().add(new FilterItemInfo("longnumber", fPrjLongNumber.replace('.', '!')));
			EntityViewInfo curProjectView = new EntityViewInfo();
			curProjectView.setFilter(curProjectFilter);
			CurProjectCollection curProjectCollection = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(curProjectView);
			if(curProjectCollection.size() > 0) {
				curProjectInfo = curProjectCollection.get(0);
			} else {
				// 导入编码在系统中不存在时，提示：××编码在系统中不存在！
				// throw new TaskExternalException(getResource(ctx, "CurProjectNumberNotFound"));
				throw new TaskExternalException(fPrjLongNumber + getResource(ctx, "NumberNotFound"));
			}
			
			// 科目编码在EAS系统中存在校验，并取得成本科目对象
			FilterInfo costAccountFilter = new FilterInfo();
			costAccountFilter.getFilterItems().add(new FilterItemInfo("codingNumber", fAcctLongNumber));
			costAccountFilter.getFilterItems().add(new FilterItemInfo("curProject.id", curProjectInfo.getId().toString()));
			EntityViewInfo costAccountView = new EntityViewInfo();
			costAccountView.setFilter(costAccountFilter);
			CostAccountCollection costAccountColl = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(costAccountView);
			if (costAccountColl.size() > 0) {
				costAccountInfo = costAccountColl.get(0);
			} else {
				// 导入编码在系统中不存在时，提示：××编码在系统中不存在！
				// throw new TaskExternalException(getResource(ctx,"AcctLongNumberNotFound"));
				throw new TaskExternalException(fAcctLongNumber + getResource(ctx, "NumberNotFound"));
			}
			
			/*
			 * K3报表对象设置
			 */
			k3CostInfo = new K3CostInfo();
			// 工程项目Id(必须)
			k3CostInfo.setProjectId(curProjectInfo.getId());
			// 成本科目Id(必须)
			k3CostInfo.setCostAccountId(costAccountInfo.getId());
			// 工程项目编码
			k3CostInfo.setPrjLongNumber(fPrjLongNumber);
			// 科目编码
			k3CostInfo.setAcctLongNumber(fAcctLongNumber);
			// 科目名称(导入的科目名称忽略，根据科目编码，自动带出系统中对应的科目名称)
			k3CostInfo.setAcctName(costAccountInfo.getName());
			// 未结算合同金额
			try {
				k3CostInfo.setUnSettConAmt(new BigDecimal(fUnSettConAmt));
			} catch (Exception e) {
				// 导入的未结算合同金额为空或非数值时，默认值0设定
				k3CostInfo.setUnSettConAmt(zeroAmt);
			}
			// 未结算变更金额
			try {
				k3CostInfo.setUnSettleChange(new BigDecimal(fUnSettleChange));
			} catch (Exception e) {
				// 导入的未结算变更金额为空或非数值时，默认值0设定
				k3CostInfo.setUnSettleChange(zeroAmt);
			}
			// 已结算合同金额
			try {
				k3CostInfo.setSettConAmt(new BigDecimal(fSettConAmt));
			} catch (Exception e) {
				// 导入的已结算合同金额为空或非数值时，默认值0设定
				k3CostInfo.setSettConAmt(zeroAmt);
			}
			// 已结算变更金额
			try {
				k3CostInfo.setSettleChange(new BigDecimal(fSettleChange));
			} catch (Exception e) {
				// 导入的已结算变更金额为空或非数值时，默认值0设定
				k3CostInfo.setSettleChange(zeroAmt);
			}
			// 结算金额
			try {
				k3CostInfo.setSettSignAmt(new BigDecimal(fSettSignAmt));
			} catch (Exception e) {
				// 导入的结算金额为空或非数值时，默认值0设定
				k3CostInfo.setSettSignAmt(zeroAmt);
			}
			// 非合同性成本
			try {
				k3CostInfo.setConWithoutTxtCostAmt(new BigDecimal(fConWithoutTxtCostAmt));
			} catch (Exception e) {
				// 导入的非合同性成本为空或非数值时，默认值0设定
				k3CostInfo.setConWithoutTxtCostAmt(zeroAmt);
			}
			// 已发生金额
			try {
				k3CostInfo.setHappendAmt(new BigDecimal(fHappendAmt));
			} catch (Exception e) {
				// 导入的已发生金额为空或非数值时，默认值0设定
				k3CostInfo.setHappendAmt(zeroAmt);
			}
			// 已付款金额
			try {
				k3CostInfo.setHasPayedAmt(new BigDecimal(fHasPayedAmt));
			} catch (Exception e) {
				// 导入的已付款金额为空或非数值时，默认值0设定
				k3CostInfo.setHasPayedAmt(zeroAmt);
			}
			// 目前待发生
			try {
				k3CostInfo.setNotHappenAmt(new BigDecimal(fNotHappenAmt));
			} catch (Exception e) {
				// 导入的目前待发生为空或非数值时，默认值0设定
				k3CostInfo.setNotHappenAmt(zeroAmt);
			}
			// 动态成本
			BigDecimal bdDynCostAmt = null;
			try {
				bdDynCostAmt = new BigDecimal(fDynCostAmt);				
			} catch (Exception e) {
				// 导入的动态成本为空或非数值时，默认值0设定
				bdDynCostAmt = zeroAmt;
			}
			k3CostInfo.setDynCostAmt(bdDynCostAmt);
			// 目标成本
			BigDecimal bdAimCostAmt = null;
			try {
				bdAimCostAmt = new BigDecimal(fAimCostAmt);
			} catch (Exception e) {
				// 导入的目标成本为空或非数值时，默认值0设定
				bdAimCostAmt = zeroAmt;
			}
			k3CostInfo.setAimCostAmt(bdAimCostAmt);
			// 差异
			try {
				// 导入的差异忽略，差异=导入的动态成本-导入的目标成本
				k3CostInfo.setDiffAmt(FDCHelper.subtract(bdDynCostAmt, bdAimCostAmt));
			} catch (Exception e) {
				// 导入的差异为空或非数值时，默认值0设定
				k3CostInfo.setDiffAmt(zeroAmt);
			}			
		} catch (BOSException e) {
			k3CostInfo = null;
			e.printStackTrace();
		}

		return k3CostInfo;
	}
	
	/**
	 * 得到资源文件
	 * @author 黄远昌
	 */
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}
}
