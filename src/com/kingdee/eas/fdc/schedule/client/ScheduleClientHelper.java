package com.kingdee.eas.fdc.schedule.client;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.client.f7.CostCenterF7;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.schedule.framework.client.IScheduleUIFacade;
import com.kingdee.eas.fdc.schedule.framework.util.ScheduleTaskPropertyHelper;
import com.kingdee.eas.util.client.EASResource;

public class ScheduleClientHelper {
	/** 删除按钮名称 **/
	public static final String BUTTON_DEL_NAME = "删除";

	/** 新增按钮名称 **/
	public static final String BUTTON_ADD_NAME = "新增";
	
	private ScheduleClientHelper(){}
	
	/**
	 * 计划部门、责任部门可选全集团组织
	 * by cassiel 2010-06-28
	 */
	public static boolean chooseAllOrg(){
		boolean retValue = false;
		String cuID = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
		try {
			retValue=FDCUtils.getDefaultFDCParamByKey(null, cuID, FDCConstants.FDC_PARAM_CHOOSEALLORG);
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retValue;
	}
	
	public static void setPersonF7(KDPromptBox box, CoreUIObject owner, String cuId){
		FDCClientUtils.setPersonF7(box,owner,null);
	}
	// 设置责任部门 by cassiel_peng 2010-04-20
	public static void setRespDept(KDBizPromptBox box, CoreUIObject owner, String cuId) {
		FDCClientUtils.setRespDeptF7(box,owner);
	}
	
	/**
	 * 返回UI的标识
	 * 
	 * @param ui
	 * @return
	 */
	public static int getUIMark(IScheduleUIFacade ui) {
		if (ui instanceof SpecialScheduleExecuteUI) {
			return ScheduleTaskPropertyHelper.SPCL_EX;
		} else if (ui instanceof MainScheduleExecuteUI) {
			return ScheduleTaskPropertyHelper.MAIN_EX;
		} else if (ui instanceof TotalScheduleEditUI) {
			return ScheduleTaskPropertyHelper.TOTAL;
		} else if (ui instanceof MainScheduleEditUI) {
			return ScheduleTaskPropertyHelper.MAIN;
		} else if (ui instanceof SpecialScheduleEditUI) {
			return ScheduleTaskPropertyHelper.SPCL;
		} else if(ui instanceof FDCScheduleBaseEditUI){
			return  ScheduleTaskPropertyHelper.MAIN;
		}else{
			return ScheduleTaskPropertyHelper.ALL;
		}
	}
	
	/**
	 * 创建按钮
	 * @param name 按钮名称
	 * @param text 按钮显示名称
	 * @param image 使用哪个图标
	 * @return 被创建的按钮
	 */
	public static KDWorkButton createButton(String name, String text, String image) {
		KDWorkButton btn = new KDWorkButton();
		btn.setName(name);
		btn.setText(text);
		btn.setSize(21, 9);
		btn.setIcon(EASResource.getIcon(image));
		btn.setVisible(true);
		btn.setEnabled(true);
		return btn;
	}
	
}
