package com.kingdee.eas.fdc.schedule.client;

import java.util.HashSet;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.util.SysUtil;

/**
 * 
 * @author guangxin_niu
 * 
 */
public class FDCSCHClientHelper {
	/**
	 * 判断是否系统预设参数（适用于有表格的ListUI）
	 * 
	 * @param activeRowIndex
	 * @param tblMain
	 * @return
	 */
	protected static boolean isSystemDefaultData(int activeRowIndex,
			KDTable tblMain) {
		ICell cell = tblMain.getCell(activeRowIndex, "CU.id");
		if (cell == null)
			cell = tblMain.getCell(activeRowIndex, "CU.ID");
		   		if (cell != null) {
			String CU = (String) cell.getValue();
			if (OrgConstants.SYS_CU_ID.equals(CU)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否系统预设参数（适用于没有表格的EditUI）
	 * 
	 * @param baseDataInfo
	 * @return
	 */
	protected static boolean isSystemDefaultData(DataBaseInfo baseDataInfo) {
		if (baseDataInfo == null || baseDataInfo.getCU() == null
				|| baseDataInfo.getId() == null) {
			return false;
		}
		if (OrgConstants.SYS_CU_ID.equals(baseDataInfo.getCU().getId()
				.toString())) {
			return true;
		}
		return false;
	}
	/**
	 * 责任人是否按当前用户所在组织进行过滤 by cassiel_peng 210-06-07
	 */
	public static boolean filterRespPerson() {
//		boolean filterRespPerson = false;
//		try {
//			filterRespPerson = FDCUtils.getDefaultFDCParamByKey(null, SysContext.getSysContext().getCurrentFIUnit().getId().toString(), FDCConstants.FDC_PARAM_FILTERRESPPERSON);
//		} catch (EASBizException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (BOSException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return filterRespPerson;
		return false;
	}
	/**
	 * 责任人是否可以选择其他部门的人员 by cassiel_peng 210-06-07
	 */
	public static boolean canSelectOtherOrgPerson() {
//		boolean canSelectOtherOrgPerson = false;
//		try {
//			canSelectOtherOrgPerson = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_SELECTPERSON);
//		} catch (EASBizException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (BOSException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return true;
	}
	public static void checkIsEnabled(KDTable table, String colName, String msg) {
		int actRowIdx = table.getSelectManager().getActiveRowIndex();
		if (actRowIdx < 0)
			return;
		if (table.getCell(actRowIdx, colName).getValue() != null
				&& Boolean.TRUE.equals(Boolean.valueOf(table.getCell(actRowIdx,
						"isEnabled").getValue().toString()))) {
			FDCMsgBox.showError(msg);
			SysUtil.abort();
		}
	}

	public static void checkIsEnabled(KDTable table, String msg) {
		checkIsEnabled(table, "isEnabled", msg);
	}

	public static void checkIsEnabled(FDCDataBaseInfo editData, String msg) {
		if (editData != null && editData.isIsEnabled()) {
			FDCMsgBox.showError(msg);
			SysUtil.abort();
		}
	}

	public static void checkIsEnabled(FDCDataBaseInfo editData) {
		checkIsEnabled(editData, "已启用，不能修改！");
	}
	
	public static boolean isEqual(String obj1,String obj2){
		if(obj1 == null && obj2==null)
			return true;
		if(obj1==null && obj2!= null && obj2.trim().length()<1)
			return true;
		if(obj2==null && obj1!= null && obj1.trim().length()<1)
			return true;
		if(obj1 != null && obj2 != null && obj1.trim().equals(obj2.trim()))
			return true;
		return false;
	}
	
	public static Set getUpFIOrgUnit(FullOrgUnitInfo curOrgUnit) throws BOSException{
		Set upFIOrgUnitIds = new HashSet();
		Set longNumSet = new HashSet();
		String[] longNums = curOrgUnit.getLongNumber().split("!");
		String tempStr = new String();
		for(int i=0;i<longNums.length;i++){
			tempStr = tempStr + longNums[i];
			longNumSet.add(tempStr);
			tempStr = tempStr + "!";
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		filter.getFilterItems().add(new FilterItemInfo("isCompanyOrgUnit",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("longNumber",longNumSet,CompareType.INCLUDE));
		sic.add("id");
		view.setFilter(filter);
		view.setSelector(sic);
		FullOrgUnitCollection fiOrgUnits = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitCollection(view);
		for(int i=0;i<fiOrgUnits.size();i++){
			upFIOrgUnitIds.add(fiOrgUnits.get(i).getId().toString());
		}
		return upFIOrgUnitIds;
	}
}
