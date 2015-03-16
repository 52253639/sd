package com.kingdee.eas.fdc.basecrm.client;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMConstants;
import com.kingdee.eas.fdc.basecrm.CustomerTypeEnum;
import com.kingdee.eas.fdc.basecrm.FDCBaseCustomerInfo;
import com.kingdee.eas.fdc.basecrm.FDCMainCustomerCollection;
import com.kingdee.eas.fdc.basecrm.FDCMainCustomerFactory;
import com.kingdee.eas.fdc.basecrm.FDCMainCustomerInfo;
import com.kingdee.eas.fdc.basecrm.FDCOrgCustomerCollection;
import com.kingdee.eas.fdc.basecrm.FDCOrgCustomerFactory;
import com.kingdee.eas.fdc.basecrm.FDCOrgCustomerInfo;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureCollection;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureFactory;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.CusRepeatRuleEnum;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitCollection;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberCollection;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberFactory;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitSellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitSellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.SHECustomerCollection;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEParamConstant;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.CustomerRptEditUI;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

public class CusClientHelper {
	
	public static final String REPEATCUSSHOWRULE_DIR_VALUE_LEFT = "0";
	public static final String REPEATCUSSHOWRULE_DIR_VALUE_RIGHT = "1";
	
	public static final String CUSREPEATRULE_VALUE_NAME = "0";
	public static final String CUSREPEATRULE_VALUE_TEL = "1";
	public static final String CUSREPEATRULE_VALUE_CER = "2";
	public static final String CUSREPEATRULE_VALUE_NAME_TEL = "3";
	public static final String CUSREPEATRULE_VALUE_NAME_CER = "4";
	public static final String CUSREPEATRULE_VALUE_TEL_CER = "5";
	public static final String CUSREPEATRULE_VALUE_NAME_TEL_CER = "6";
	public static final String CUSREPEATRULE_VALUE_TEL_OR_CER = "7";
	
	public static HashMap getCRMConstants(){
		HashMap param = new HashMap();
		HashMap value=null;
		IObjectPK pk=new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId());
		param.put(CRMConstants.FDCSHE_PARAM_ISSHARECUS, pk);
		param.put(CRMConstants.FDCSHE_PARAM_REPEATCUSSHOWRULE_DIR, pk);
		param.put(CRMConstants.FDCSHE_PARAM_REPEATCUSSHOWRULE_START, pk);
		param.put(CRMConstants.FDCSHE_PARAM_REPEATCUSSHOWRULE_LEN, pk);
		param.put(CRMConstants.FDCSHE_PARAM_CUSREPEATRULE, pk);
		try {
			value = ParamControlFactory.getRemoteInstance().getParamHashMap(param);
			
			if (value == null) {
				value = new HashMap();
				value.put(CRMConstants.FDCSHE_PARAM_ISSHARECUS, Boolean.TRUE);
				value.put(CRMConstants.FDCSHE_PARAM_REPEATCUSSHOWRULE_DIR, REPEATCUSSHOWRULE_DIR_VALUE_LEFT);
				value.put(CRMConstants.FDCSHE_PARAM_REPEATCUSSHOWRULE_START, new Integer(3));
				value.put(CRMConstants.FDCSHE_PARAM_REPEATCUSSHOWRULE_LEN, new Integer(7));
				value.put(CRMConstants.FDCSHE_PARAM_CUSREPEATRULE, CUSREPEATRULE_VALUE_TEL);
			}
		}catch (EASBizException e1) {
			e1.printStackTrace();
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		return value;
	}
	
	//返回表示是否有重复的
	private static Object[] existNameRepeat(Component owner, CusRepeatRuleEnum rule, String value, String sellProjectId, CustomerTypeEnum customerType) throws EASBizException, BOSException{
		FilterInfo eFilter = new FilterInfo();
		eFilter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		eFilter.getFilterItems().add(new FilterItemInfo("customerType", customerType.getValue()));
		eFilter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProjectId));
		eFilter.getFilterItems().add(new FilterItemInfo("name", value));
		if(CusRepeatRuleEnum.NOTHING.equals(rule)){
			return new Object[]{Boolean.TRUE, null};
		}else{
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(eFilter);
			SHECustomerCollection reCuses = SHECustomerFactory.getRemoteInstance().getSHECustomerCollection(view);
			if(reCuses.isEmpty()){
				return new Object[]{Boolean.TRUE, null};
			}else{
				if(CusRepeatRuleEnum.FORBITTEN.equals(rule)){
					return new Object[]{Boolean.FALSE, reCuses};
				}else{
					return new Object[]{Boolean.TRUE, reCuses};
				}
			}
		}
	}
	
	private static Object[] existPhoneRepeat(Component owner, CusRepeatRuleEnum rule, String value, String sellProjectId, CustomerTypeEnum customerType) throws EASBizException, BOSException{
		FilterInfo eFilter = new FilterInfo();
		eFilter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		eFilter.getFilterItems().add(new FilterItemInfo("customerType", customerType.getValue()));
		eFilter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProjectId));
		eFilter.getFilterItems().add(new FilterItemInfo("phone", value));
		eFilter.getFilterItems().add(new FilterItemInfo("tel", value));
		eFilter.getFilterItems().add(new FilterItemInfo("officeTel", value));
		eFilter.getFilterItems().add(new FilterItemInfo("fax", value));
		eFilter.setMaskString("#0 and #1 and #2 and (#3 OR #4 OR #5 OR #6)");
		if(CusRepeatRuleEnum.NOTHING.equals(rule)){
			return new Object[]{Boolean.TRUE, null};
		}else{
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(eFilter);
			SHECustomerCollection reCuses = SHECustomerFactory.getRemoteInstance().getSHECustomerCollection(view);
			if(reCuses.isEmpty()){
				return new Object[]{Boolean.TRUE, null};
			}else{
				if(CusRepeatRuleEnum.FORBITTEN.equals(rule)){
					return new Object[]{Boolean.FALSE, reCuses};
				}else{
					return new Object[]{Boolean.TRUE, reCuses};
				}
			}
		}
	}
	
	private static Object[] existCerRepeat(Component owner, CusRepeatRuleEnum rule, String value, String sellProjectId, CustomerTypeEnum customerType) throws EASBizException, BOSException{
		if(value == null  ||  value.trim().length() == 0){
			return new Object[]{Boolean.TRUE, null};
		}
		
		FilterInfo eFilter = new FilterInfo();
		eFilter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		eFilter.getFilterItems().add(new FilterItemInfo("customerType", customerType.getValue()));
		eFilter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProjectId));
		eFilter.getFilterItems().add(new FilterItemInfo("certificateNumber", value));
		if(CusRepeatRuleEnum.NOTHING.equals(rule)){
			return new Object[]{Boolean.TRUE, null};
		}else{
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(eFilter);
			SHECustomerCollection reCuses = SHECustomerFactory.getRemoteInstance().getSHECustomerCollection(view);
			if(reCuses.isEmpty()){
				return new Object[]{Boolean.TRUE, null};
			}else{
				if(CusRepeatRuleEnum.FORBITTEN.equals(rule)){
					return new Object[]{Boolean.FALSE, reCuses};
				}else{
					return new Object[]{Boolean.TRUE, reCuses};
				}
			}
		}
	}
	
	public static Map addNewCusBegin(Component owner, String sellProjectId) throws BOSException, EASBizException{
		Map linkedCus = new HashMap();
		
		UIContext uiContext = new UIContext(owner);
		uiContext.put("isSHE", "isSHE");
    	
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(AddCustomerBeginUI.class.getName(), uiContext, null, "ADDNEW");
		uiWindow.show();
		
		Map result = uiWindow.getUIObject().getUIContext();
		
		Boolean isCon = (Boolean) result.get("isContinue");
		if(isCon == null  ||  !isCon.booleanValue()){
			SysUtil.abort();
		}
		
		CustomerTypeEnum customerType = (CustomerTypeEnum) result.get("customerType");
		String cusName = (String) result.get("cusName");
		String cusPhone = (String) result.get("cusPhone");
		String cusCertificateNum = (String) result.get("cusCertificateNum");
		
		linkedCus.clear();
		linkedCus.put("customerType", customerType);
		linkedCus.put("cusName", cusName);
		linkedCus.put("cusPhone", cusPhone);
		linkedCus.put("cusCertificateNum", cusCertificateNum);
		//增加状态
		linkedCus.put("addnewDerict", result.get("addnewDerict"));
		
		Map params = getCRMConstants();
		boolean isShareCus = ( Boolean.valueOf((String)params.get(CRMConstants.FDCSHE_PARAM_ISSHARECUS))).booleanValue();
		
		Map proSet = RoomDisplaySetting.getNewProjectSet(null, sellProjectId);
		if(proSet == null){
			MsgBox.showInfo(owner, "请先在售楼功能设置");
			SysUtil.abort();
		}
		if (!isShareCus) {
			return searchCustomerInProject(owner, sellProjectId, linkedCus, customerType, cusName, cusPhone, cusCertificateNum, params, proSet);
		}else{
			EntityViewInfo view = getEntityView();
			//在集团里匹配
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
			filter.getFilterItems().add(new FilterItemInfo("customerType", customerType.getValue()));
			//wyh 在本组织里匹配
			filter.getFilterItems().add(new FilterItemInfo("CU", SysContext.getSysContext().getCurrentCtrlUnit().getId()));
			
			StringBuffer maskStr = new StringBuffer("#0 and #1 and #2");
			int ci = 2;
			
			if(cusCertificateNum == null  ||  cusCertificateNum.length() == 0){
				addNameFilter(cusName, filter);
				addPhoneFilter(cusPhone, filter, false);
				
				maskStr.append(" and #");
				maskStr.append(ci++);
				ci = addPhoneMastStr(maskStr, ci);
				
				filter.setMaskString(maskStr.toString());
			}else{
				addNameFilter(cusName, filter);
				addCerFilter(cusCertificateNum, filter);
				
				addNameFilter(cusName, filter);
				addPhoneFilter(cusPhone, filter, false);
				
				addCerFilter(cusCertificateNum, filter);
				addPhoneFilter(cusPhone, filter, false);
				
				maskStr.append(" and ((#");
				maskStr.append(ci++);
				maskStr.append(" and #");
				maskStr.append(ci++);
				maskStr.append(") ");
				
				maskStr.append(" or (#");
				maskStr.append(ci++);
				ci = addPhoneMastStr(maskStr, ci);
				maskStr.append(") ");
				
				maskStr.append(" or (#");
				maskStr.append(ci++);
				ci = addPhoneMastStr(maskStr, ci);
				maskStr.append(")) ");
				
				filter.setMaskString(maskStr.toString());
			}
			
			view.setFilter(filter);
			
			FDCMainCustomerCollection mainCuses = FDCMainCustomerFactory.getRemoteInstance().getFDCMainCustomerCollection(view);
			boolean isGoProjectSearch = false;
			if(mainCuses.isEmpty()){
				//在集团未匹配到,再到项目中匹配
				isGoProjectSearch = true;
			}else{
				//在集团匹配到了，则给用户选择
				FDCMainCustomerInfo mainCus = (FDCMainCustomerInfo) selectCustomer(owner, mainCuses, params);
				if(mainCus == null){
					isGoProjectSearch = true;
				}else{
					//判断选中的客户在当前项目有没有
					
					EntityViewInfo tView = new EntityViewInfo();
					FilterInfo tFilter = new FilterInfo();
					tView.setFilter(tFilter);
					tFilter.getFilterItems().add(new FilterItemInfo("mainCustomer.id", mainCus.getId().toString()));
					tFilter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProjectId));
					tView.getSelector().add("id");
					SHECustomerCollection cuSheCuses = SHECustomerFactory.getRemoteInstance().getSHECustomerCollection(tView);
					if(cuSheCuses.isEmpty()){
						linkedCus.put("mainCus", mainCus);
						return linkedCus;
					}else{
						//如果存在，判断是否有查看权限
						if (hasCustomerPermission(sellProjectId, cuSheCuses.get(0).getId().toString())) {
							// 存在则表示有权限查看
							showCustomerEditUI(owner, cuSheCuses.get(0).getId().toString(), OprtState.VIEW, CustomerRptEditUI.class.getName());
							linkedCus.put("isAbort", Boolean.TRUE);
							return linkedCus;
						} else {
							MsgBox.showInfo(owner, "您无此客户的查看权限.");
							return addNewCusBegin(owner, sellProjectId);
						}
					}
				}
			}
			
			if(isGoProjectSearch){
				return searchCustomerInProject(owner, sellProjectId, linkedCus, customerType, cusName, cusPhone, cusCertificateNum, params, proSet);
			}
			return null;
		}
	}

	private static Map searchCustomerInProject(Component owner, String sellProjectId, Map linkedCus, CustomerTypeEnum customerType, String cusName,
			String cusPhone, String cusCertificateNum, Map params, Map proSet) throws EASBizException, BOSException, UIException {
		// 在当前项目中判断是否存在
		CusRepeatRuleEnum nameReRule = (CusRepeatRuleEnum) proSet.get(SHEParamConstant.T2_NAME_REPEAT_RULE);
		CusRepeatRuleEnum phoneReRule = (CusRepeatRuleEnum) proSet.get(SHEParamConstant.T2_PHONE_REPEAT_RULE);
		CusRepeatRuleEnum cerReRule = (CusRepeatRuleEnum) proSet.get(SHEParamConstant.T2_CER_REPEAT_RULE);

		Object[] exist1 = null;
		Object[] exist2 = null;
		Object[] exist3 = null;

		SHECustomerCollection toSels = new SHECustomerCollection();
		
		exist1 = existNameRepeat(owner, nameReRule, cusName, sellProjectId, customerType);
		if (((Boolean)exist1[0]).booleanValue()) {
			if(exist1[1] != null){
				toSels.addCollection((SHECustomerCollection) exist1[1]);
			}
			exist2 = existPhoneRepeat(owner, phoneReRule, cusPhone, sellProjectId, customerType);
		}else{
			return notPassStep(owner, sellProjectId, linkedCus, params, exist1, "姓名");
		}
		if (((Boolean)exist2[0]).booleanValue()) {
			if(exist2[1] != null){
				toSels.addCollection((SHECustomerCollection) exist2[1]);
			}
			exist3 = existCerRepeat(owner, cerReRule, cusCertificateNum, sellProjectId, customerType);
		}else{
			return notPassStep(owner, sellProjectId, linkedCus, params, exist2, "联系电话");
		}

		if(((Boolean)exist3[0]).booleanValue()){
			if(exist3[1] != null){
				toSels.addCollection((SHECustomerCollection) exist3[1]);
			}
		}else{
			return notPassStep(owner, sellProjectId, linkedCus, params, exist3, "证件号码");
		}
		
		if(toSels.isEmpty()){
			return linkedCus;
		} else {
			if (MsgBox.YES == MsgBox.showConfirm2(owner, "当前项目存在重复客户，继续则打开所选客户资料")) {
				SHECustomerInfo sheCus = (SHECustomerInfo) selectCustomer(owner, toSels, params);
				if (sheCus == null) {
					return linkedCus;
				} else {
					if (hasCustomerPermission(sellProjectId, sheCus.getId().toString())) {
						// 存在则表示有权限查看
						showCustomerEditUI(owner, sheCus.getId().toString(), OprtState.VIEW, CustomerRptEditUI.class.getName());
						linkedCus.put("isAbort", Boolean.TRUE);
						return linkedCus;
					} else {
						MsgBox.showInfo(owner, "您无此客户的查看权限.");
						return addNewCusBegin(owner, sellProjectId);
					}
				}
			}else{
				if(!(nameReRule.equals(CusRepeatRuleEnum.FORBITTEN)&&!((Boolean)exist1[0]).booleanValue())&&!(phoneReRule.equals(CusRepeatRuleEnum.FORBITTEN)&&!((Boolean)exist2[0]).booleanValue())&&!(cerReRule.equals(CusRepeatRuleEnum.FORBITTEN)&&!((Boolean)exist3[0]).booleanValue()&&cusCertificateNum != null&&cusCertificateNum.trim().length()!= 0)){
					return linkedCus;
				}
				return addNewCusBegin(owner, sellProjectId);
			}
		}
	}

	private static Map notPassStep(Component owner, String sellProjectId, Map linkedCus, Map params, Object[] exist1, String paramDes) throws UIException, BOSException, EASBizException {
		//针对禁止的重复客户，只能修改或查看
		SHECustomerCollection forReCuses = (SHECustomerCollection) exist1[1];
		SHECustomerInfo toShowCus = null;
		
		if(forReCuses.size() == 1){
			toShowCus = forReCuses.get(0);
		}else{
			toShowCus = (SHECustomerInfo) selectCustomer(owner, forReCuses, params);
		}
		
		if(toShowCus == null){
			MsgBox.showInfo(owner, "本项目存在相同" + paramDes +"的客户，不允许新增。");
			return addNewCusBegin(owner, sellProjectId);
		}else{
			if (MsgBox.YES == MsgBox.showConfirm2(owner, "当前项目存在重复客户，继续则打开所选客户资料")) {
				if (hasCustomerPermission(sellProjectId, toShowCus.getId().toString())) {
					// 存在则表示有权限查看
					showCustomerEditUI(owner, toShowCus.getId().toString(), OprtState.VIEW, CustomerRptEditUI.class.getName());
					linkedCus.put("isAbort", Boolean.TRUE);
					return linkedCus;
				} else {
					MsgBox.showInfo(owner, "您无此客户的查看权限.");
					return addNewCusBegin(owner, sellProjectId);
				}
			}else{
				return addNewCusBegin(owner, sellProjectId);
			}
		}
	}
	
	private static boolean hasCustomerPermission(String sellProjectId, String sheCusId) throws EASBizException, BOSException {
		SellProjectInfo sellP = new SellProjectInfo();
		sellP.setId(BOSUuid.read(sellProjectId));

		FilterInfo filter = NewCommerceHelper.getPermitCustomerView(sellP, SysContext.getSysContext().getCurrentUserInfo()).getFilter();
		FilterInfo cuFilter = new FilterInfo();
		cuFilter.getFilterItems().add(new FilterItemInfo("id", sheCusId));
		filter.mergeFilter(cuFilter, "AND");

		return SHECustomerFactory.getRemoteInstance().exists(filter);
	}

	public static Map addNewCusBegin2(Component owner, ICoreBase iface, FilterItemInfo scope) throws BOSException, EASBizException{
		Map linkedCus = new HashMap();
		
		UIContext uiContext = new UIContext(owner);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(AddCustomerBeginUI.class.getName(), uiContext, null, "ADDNEW");
		uiWindow.show();
		
		Map result = uiWindow.getUIObject().getUIContext();
		
		Boolean isCon = (Boolean) result.get("isContinue");
		if(isCon == null  ||  !isCon.booleanValue()){
			SysUtil.abort();
		}
		
		CustomerTypeEnum customerType = (CustomerTypeEnum) result.get("customerType");
		String cusName = (String) result.get("cusName");
		String cusPhone = (String) result.get("cusPhone");
		String cusCertificateNum = (String) result.get("cusCertificateNum");
		
		linkedCus.clear();
		linkedCus.put("customerType", customerType);
		linkedCus.put("cusName", cusName);
		linkedCus.put("cusPhone", cusPhone);
		linkedCus.put("cusCertificateNum", cusCertificateNum);
		
		//EntityViewInfo view = new EntityViewInfo();
		EntityViewInfo view = getEntityView();
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("customerType", customerType.getValue()));
		
		int ci = 0;
		
		StringBuffer maskStr = new StringBuffer();
		maskStr.append("#");
		maskStr.append(ci++);
		maskStr.append(" and #");
		maskStr.append(ci++);
		
		FDCMainCustomerInfo mainCus = null;
		boolean curOrgExist = false;
		if(cusCertificateNum == null  ||  cusCertificateNum.length() == 0){
			addNameFilter(cusName, filter);
			addPhoneFilter(cusPhone, filter, false);
			
			maskStr.append(" and #");
			maskStr.append(ci++);
			ci = addPhoneMastStr(maskStr, ci);
			
			view.setFilter(filter);
			filter.setMaskString(maskStr.toString());
		}else{
			addNameFilter(cusName, filter);
			addCerFilter(cusCertificateNum, filter);
			
			addNameFilter(cusName, filter);
			addPhoneFilter(cusPhone, filter, false);
			
			addCerFilter(cusCertificateNum, filter);
			addPhoneFilter(cusPhone, filter, false);
			
			maskStr.append(" and ((#");
			maskStr.append(ci++);
			maskStr.append(" and #");
			maskStr.append(ci++);
			maskStr.append(") ");
			
			maskStr.append(" or (#");
			maskStr.append(ci++);
			ci = addPhoneMastStr(maskStr, ci);
			maskStr.append(") ");
			
			maskStr.append(" or (#");
			maskStr.append(ci++);
			ci = addPhoneMastStr(maskStr, ci);
			maskStr.append(")) ");
			
			view.setFilter(filter);
			filter.setMaskString(maskStr.toString());
		}
		
		//先在本组织去匹配
		if(iface != null  &&  scope != null){
			FilterInfo tmp = (FilterInfo) filter.clone();
			tmp.getFilterItems().add(scope);
			
			maskStr.append(" and #");
			maskStr.append(ci++);
			tmp.setMaskString(maskStr.toString());
			
			EntityViewInfo tV = new EntityViewInfo();
			tV.setFilter(tmp);
			tV.getSelector().add("*");
			tV.getSelector().add("mainCustomer.*");
//					tV.getSelector().add("certificateType");
//					tV.getSelector().add("certificateNum");
			
			FDCOrgCustomerCollection orgCuses = FDCOrgCustomerFactory.getRemoteInstance().getFDCOrgCustomerCollection(tV);
			if(!orgCuses.isEmpty()){
				curOrgExist = true;
				if(MsgBox.YES != MsgBox.showConfirm2(owner, "当前客户管理组织已存在该客户，继续则打开客户资料查看")){
					return addNewCusBegin2(owner, iface, scope);
				}
				
				FDCOrgCustomerInfo tOrgCus = orgCuses.get(0);
				FDCMainCustomerInfo tMainCus = tOrgCus.getMainCustomer();
				boolean isSame = isSameInfo(tMainCus, tOrgCus);
				
				//如果集团资料与当前匹配到的资料一致，打开查看界面
				if (isSame) {
					showCustomerEditUI(owner, orgCuses.get(0).getId().toString(), OprtState.VIEW, FDCCustomerEditUI.class.getName());
				}else{//否则，提示是否更新
					if(MsgBox.YES == MsgBox.showConfirm2(owner, "当前组织中的客户资料与集团资料不同步，是否更新？")){
						List tL = new ArrayList();
						tL.add(orgCuses.get(0).getId().toString());
						FDCOrgCustomerFactory.getRemoteInstance().updateCustomerInfo(tL);
						showCustomerEditUI(owner, orgCuses.get(0).getId().toString(), OprtState.EDIT, FDCCustomerEditUI.class.getName());
					}else{
						showCustomerEditUI(owner, orgCuses.get(0).getId().toString(), OprtState.VIEW, FDCCustomerEditUI.class.getName());
					}
				}
				
				linkedCus.put("isAbort", Boolean.TRUE);
			}
		}
		
		//当前组织没匹配到，则到集团客户去匹配
		if (!curOrgExist) {
			FDCMainCustomerCollection fdcMainCuses = FDCMainCustomerFactory.getRemoteInstance().getFDCMainCustomerCollection(view);
			if (!fdcMainCuses.isEmpty()) {
				FDCMainCustomerInfo main = fdcMainCuses.get(0);

				EntityViewInfo tV = new EntityViewInfo();
				FilterInfo tF = new FilterInfo();
				tF.getFilterItems().add(new FilterItemInfo("mainCustomer.id", main.getId().toString()));
				tF.getFilterItems().add(scope);
				tV.setFilter(tF);
				FDCOrgCustomerCollection orgCuses = FDCOrgCustomerFactory.getRemoteInstance().getFDCOrgCustomerCollection(tV);
				if (!orgCuses.isEmpty()) {
					if (MsgBox.YES == MsgBox.showConfirm2(owner, "当前组织中的客户资料与集团资料不同步，是否更新？")) {
						List tL = new ArrayList();
						tL.add(orgCuses.get(0).getId().toString());
						FDCOrgCustomerFactory.getRemoteInstance().updateCustomerInfo(tL);
						showCustomerEditUI(owner, orgCuses.get(0).getId().toString(), OprtState.EDIT, FDCCustomerEditUI.class.getName());
					} else {
						showCustomerEditUI(owner, orgCuses.get(0).getId().toString(), OprtState.VIEW, FDCCustomerEditUI.class.getName());
					}

					linkedCus.put("isAbort", Boolean.TRUE);
				} else {
					mainCus = fdcMainCuses.get(0);
					linkedCus.put("mainCus", mainCus);
				}
			}
		}
		
		return linkedCus;
	}
	
	private static boolean isSameInfo(FDCBaseCustomerInfo cus1, FDCBaseCustomerInfo cus2) {
		boolean isNameSame = cus1.getName().equals(cus2.getName());
		boolean isPhoneSame = false;
		boolean isCerSame = false;
		
		Set s = new HashSet();
		if(!StringUtils.isEmpty(cus1.getPhone())){
			s.add(cus1.getPhone());
		}
		if(!StringUtils.isEmpty(cus1.getTel())){
			s.add(cus1.getTel());
		}
		if(!StringUtils.isEmpty(cus1.getOfficeTel())){
			s.add(cus1.getOfficeTel());
		}
		if(!StringUtils.isEmpty(cus1.getFax())){
			s.add(cus1.getFax());
		}
		
		if(!isPhoneSame && !StringUtils.isEmpty(cus2.getPhone())){
			if(s.contains(cus2.getPhone())){
				isPhoneSame = true;
			}
		}
		if(!isPhoneSame && !StringUtils.isEmpty(cus2.getTel())){
			if(s.contains(cus2.getTel())){
				isPhoneSame = true;
			}
		}
		if(!isPhoneSame && !StringUtils.isEmpty(cus2.getOfficeTel())){
			if(s.contains(cus2.getOfficeTel())){
				isPhoneSame = true;
			}
		}
		if(!isPhoneSame && !StringUtils.isEmpty(cus2.getFax())){
			if(s.contains(cus2.getFax())){
				isPhoneSame = true;
			}
		}
		
		if(cus1.getCertificateNumber() == null  ||  cus2.getCertificateNumber() == null){
			isCerSame = false;
		}else{
			isCerSame = cus1.getCertificateType().getId().equals(cus2.getCertificateType().getId()) && cus1.getCertificateNumber().equals(cus2.getCertificateNumber());
		}
		
		if((isNameSame && isPhoneSame) || (isNameSame && isCerSame) || (isPhoneSame && isCerSame)){
			return true;
		}
		return false;
	}

	private static void showCustomerEditUI(Component owner, String id, String type, String uiName) throws UIException {
		UIContext uc = new UIContext(owner);
		uc.put("ID", id);
		UIFactory.createUIFactory(UIFactoryName.MODEL).create(uiName, uc, null, type).show();
	}

	/**
	 * @param cusName
	 * @param filter
	 */
	private static void addNameFilter(String cusName, FilterInfo filter) {
		filter.getFilterItems().add(new FilterItemInfo("name", cusName));
	}

	/**
	 * @param cusCertificateNum
	 * @param filter
	 */
	private static void addCerFilter(String cusCertificateNum, FilterInfo filter) {
		if(cusCertificateNum == null  ||  cusCertificateNum.length() == 0){
			filter.getFilterItems().add(new FilterItemInfo("certificateNumber", "nullnull"));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("certificateNumber", "%"+cusCertificateNum+"%", CompareType.LIKE));
		}
	}

	/**
	 * @param cusPhone
	 * @param filter
	 */
	private static void addPhoneFilter(String cusPhone, FilterInfo filter, boolean like) {
		if(like){
			filter.getFilterItems().add(new FilterItemInfo("phone", "%"+cusPhone+"%", CompareType.LIKE));
			filter.getFilterItems().add(new FilterItemInfo("tel", "%"+cusPhone+"%", CompareType.LIKE));
			filter.getFilterItems().add(new FilterItemInfo("officeTel", "%"+cusPhone+"%", CompareType.LIKE));
			//filter.getFilterItems().add(new FilterItemInfo("otherTel", "%"+cusPhone+"%", CompareType.LIKE));
			filter.getFilterItems().add(new FilterItemInfo("fax", "%"+cusPhone+"%", CompareType.LIKE));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("phone", cusPhone));
			filter.getFilterItems().add(new FilterItemInfo("tel", cusPhone));
			filter.getFilterItems().add(new FilterItemInfo("officeTel", cusPhone));
			//filter.getFilterItems().add(new FilterItemInfo("otherTel", cusPhone));
			filter.getFilterItems().add(new FilterItemInfo("fax", cusPhone));
		}
	}

	private static int addOrPhoneMastStr(StringBuffer maskStr, int ci){
		maskStr.append(" and (#");
		maskStr.append(ci++);
		maskStr.append(" or #");
		maskStr.append(ci++);
		maskStr.append(" or #");
		maskStr.append(ci++);
		maskStr.append(" or #");
		maskStr.append(ci++);
//		maskStr.append(" or #");
//		maskStr.append(ci++);
		maskStr.append(")");
		
		return ci;
	}
	
	private static int addPhoneMastStr(StringBuffer maskStr, int ci){
		maskStr.append(" and (#");
		maskStr.append(ci++);
		maskStr.append(" or #");
		maskStr.append(ci++);
		maskStr.append(" or #");
		maskStr.append(ci++);
		maskStr.append(" or #");
		maskStr.append(ci++);
//		maskStr.append(" or #");
//		maskStr.append(ci++);
		maskStr.append(")");
		
		return ci;
	}
	
	/**
	 * @param owner
	 * @param fdcMainCuses
	 * @return
	 * @throws UIException
	 */
	public static IObjectValue selectCustomer(Object owner, IObjectCollection cuses, Map params)
			throws UIException {
		UIContext uiContext;
		IUIWindow uiWindow;
		Map result;
		uiContext = new UIContext(owner);
		uiContext.put("fdcMainCuses", cuses);
		uiContext.put("params", params);
		if(params != null  &&  !params.isEmpty()){
			uiContext.putAll(params);
		}
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(MainCustomerSelectUI.class.getName(), uiContext, null, "ADDNEW");
		uiWindow.show();
		
		result = uiWindow.getUIObject().getUIContext();
		IObjectValue mainCus = (IObjectValue) result.get("mainCus");
		return mainCus;
	}
	
	public static IObjectValue selectCustomer(Object owner, IObjectCollection cuses) throws UIException {
		return selectCustomer(owner, cuses, null);
	}
	
	/**
	 * 删除前判断是否被下级引用
	 * @return
	 * @throws BOSException
	 */
	public static boolean beforeRemove(Component owner,List ids) throws BOSException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", FDCHelper.list2Set(ids), CompareType.INCLUDE));
		view.setFilter(filter);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("id");
		sels.add("number");
		sels.add("name");
		sels.add("belongUnit.id");
		sels.add("belongUnit.number");
		sels.add("belongUnit.longNumber");
		view.setSelector(sels);
		FDCOrgCustomerCollection orgCuses = FDCOrgCustomerFactory.getRemoteInstance().getFDCOrgCustomerCollection(view);
		
		if(orgCuses.isEmpty()){
			return true;
		}
		
		for(int i=0;i<orgCuses.size();i++){
			FDCOrgCustomerInfo info = orgCuses.get(i);
			String name = info.getName();
			String longNumber = info.getBelongUnit().getLongNumber();
			 view = new EntityViewInfo();
			 filter = new FilterInfo();
			 sels = new SelectorItemCollection();
			sels.add("id");
			filter.getFilterItems().add(new FilterItemInfo("longNumber",longNumber+"%", CompareType.LIKE));
			filter.getFilterItems().add(new FilterItemInfo("longNumber",longNumber, CompareType.NOTEQUALS));
			view.setFilter(filter);
			
			FullOrgUnitCollection tmp = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitCollection(view);
			if(tmp.isEmpty()){
				return true;
			}
			
			Set belongUnitIdSet = new HashSet();
			for(int j=0;j<tmp.size();j++){
				belongUnitIdSet.add(tmp.get(j).getId());
			}
			
			if(belongUnitIdSet.isEmpty()){
				return true;
			}
			
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("belongUnit.id", belongUnitIdSet, CompareType.INCLUDE));
			filter.getFilterItems().add(new FilterItemInfo("name", name, CompareType.EQUALS));
			view.setFilter(filter);
			FDCOrgCustomerCollection allOrgCuses = FDCOrgCustomerFactory.getRemoteInstance().getFDCOrgCustomerCollection(view);
			
			if(allOrgCuses.isEmpty()){
				return true;
			}else{
				MsgBox.showInfo(owner, "客户资料被下级引用，不能删除！");
				return false;
			}
		}
		return true;
	}
	
    
    public static void existCodingRule(String isSHE) throws BOSException, EASBizException{
    	FullOrgUnitInfo belongUnit = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
    	boolean isExistCodingRule  = false;
    	ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
    	if(isSHE == null || "".equals(isSHE)){
    		isExistCodingRule = iCodingRuleManager.isExist(new FDCOrgCustomerInfo(), belongUnit.getId().toString());
    	}else{
    		isExistCodingRule = iCodingRuleManager.isExist(new SHECustomerInfo(), belongUnit.getId().toString());
    	}
    	
    	if(!isExistCodingRule){
    		MsgBox.showWarning(belongUnit.getName()+"的编码规则未配置，请先配置编码规则！");
    		SysUtil.abort();
    	}else{
    		List ids = getBelongUnitSet(belongUnit);
    		String orgName = "";
    		if(ids != null && ids.size()>0){
    			for(int i = 0; i<ids.size(); i++){
    				String orgId = ids.get(i).toString();
    				if("".equals(isSHE)){
    				isExistCodingRule  = iCodingRuleManager.isExist(new FDCOrgCustomerInfo(), orgId);
    				}else{
    					isExistCodingRule  = iCodingRuleManager.isExist(new SHECustomerInfo(), orgId);
    				}
    				if(!isExistCodingRule){
        				FullOrgUnitInfo tempOrg = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(orgId));
        				if(!orgName.equals("")){
        					orgName += ", "+tempOrg.getName();
        				}else{
        					orgName = tempOrg.getName();
        				}
        			}
    			}
    		}
    		if(!isExistCodingRule && !orgName.equals("")){
    			MsgBox.showWarning(orgName+"的编码规则未配置，请先配置编码规则！");
    			SysUtil.abort();
    		}
    	}
   }

	public static List getBelongUnitSet(FullOrgUnitInfo orgUnit)throws BOSException, EASBizException {
		String longNumber = orgUnit.getLongNumber();
		String numbers[] = longNumber.split("!");// 对长编码拆分
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		// sic.add("id");
		FilterInfo filter = new FilterInfo();

		// 排序
		SorterItemCollection sorts = new SorterItemCollection();
		SorterItemInfo sort = new SorterItemInfo("createTime");
		sort.setSortType(SortType.DESCEND);
		sorts.add(sort);
		view.setSorter(sorts);

		// 所有上级的编码
		for (int i = 0; i < numbers.length; i++) {
			FilterInfo parentFilter = new FilterInfo();
			parentFilter.getFilterItems().add(
					new FilterItemInfo("number", numbers[i]));
			parentFilter.mergeFilter(parentFilter, "OR");
			filter.mergeFilter(parentFilter, "OR");
		}
		view.setSelector(sic);
		view.setFilter(filter);

		// 得到所有上级
		FullOrgUnitCollection coll = FullOrgUnitFactory.getRemoteInstance()
				.getFullOrgUnitCollection(view);
		List idList = new ArrayList();
		if (coll != null && coll.size() > 0) {
			for (int j = 0; j < coll.size(); j++) {// 遍历，查出当前组织中最近的一个客户组织
				FullOrgUnitInfo parentUnit = coll.get(j);
				if (isCustMangageUnit(parentUnit)) {// 如果是客户管理组织,添加到集合中
					idList.add(parentUnit.getId().toString());
				}
			}
		}
		return idList;
	}
	
	/**
	 * 判断是否是客户管理组织
	 * @param unit
	 * @return
	 * @throws BOSException 
	 */
	public  static boolean isCustMangageUnit(FullOrgUnitInfo unit) throws BOSException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("custMangageUnit",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("unit.id",unit.getId()));
		view.setFilter(filter);
		
		FDCOrgStructureCollection orgColl = FDCOrgStructureFactory.getRemoteInstance().getFDCOrgStructureCollection(view);
		if(orgColl != null && orgColl.size() > 0){
			return true;
		}
		return false;
	}
	
	public static EntityViewInfo getEntityView(){
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("id");
		selector.add("name");
		selector.add("number");
		selector.add("phone");
		selector.add("certificateNumber");
		selector.add("customerType");
		selector.add("certificateType.id");
		selector.add("certificateType.name");
		selector.add("createUnit.id");
		selector.add("createUnit.name");
		selector.add("sellProject.id");
		selector.add("sellProject.name");
		selector.add("isEnabled");
		selector.add("propertyConsultant.id");
		selector.add("propertyConsultant.name");
		selector.add("*");
		view.setSelector(selector);
		return view;
	}
	
	public static void openDetailPage(Component owner,KDTMouseEvent e, KDTable tbl, String uiClassName){
		if (e.getType() == KDTStyleConstants.BODY_ROW
				&& e.getButton() == MouseEvent.BUTTON1
				&& e.getClickCount() == 2) {
			IRow row = tbl.getRow(e.getRowIndex());
			Object idObj = row.getCell("id").getValue();
			if (idObj == null) {
				return;
			}
			String idStr = "";
			if (idObj instanceof String) {
				idStr = (String) idObj;
			} else if (idObj instanceof BOSUuid) {
				idStr = ((BOSUuid) idObj).toString();
			}
			if (!idStr.equals("")) {
				UIContext uiContext = new UIContext(owner);
				uiContext.put(UIContext.ID, idStr);
				try {
					IUIWindow uiWindow = UIFactory.createUIFactory(
							UIFactoryName.MODEL).create(uiClassName, uiContext,
							null, OprtState.VIEW);
					uiWindow.show();
				} catch (UIException e1) {
					Logger logger = CoreUIObject.getLogger(uiClassName);
					logger.error(e1.getMessage() + "打开"+uiClassName+"界面失败！");
				}
			}
		}
	}
	
	/**
	 * 根据项目得到当前登录者可共享的置业顾问
	 * @param sellProject
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
    public static EntityViewInfo getPermitViewInfo(SellProjectInfo sellProject) throws EASBizException, BOSException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
	 	filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId(), CompareType.EQUALS));
	 	view.setFilter(filter);
	 	//根据项目得到营销团队
	 	MarketingUnitSellProjectCollection sellProjectColl = MarketingUnitSellProjectFactory.getRemoteInstance().getMarketingUnitSellProjectCollection(view);
		Set idSet = new HashSet();
		if(sellProjectColl != null && sellProjectColl.size() > 0){
			for(int i=0;i<sellProjectColl.size();i++){
				String id = sellProjectColl.get(i).getHead().getId().toString();
				EntityViewInfo tempView = new EntityViewInfo();
				FilterInfo tempFilter = new FilterInfo();
				FullOrgUnitInfo currentUnit = SysContext.getSysContext().getCurrentSaleUnit().castToFullOrgUnitInfo();
				tempFilter.getFilterItems().add(new FilterItemInfo("id", id, CompareType.EQUALS));
				tempFilter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE, CompareType.EQUALS));
				tempFilter.getFilterItems().add(new FilterItemInfo("orgUnit.id", currentUnit.getId(), CompareType.EQUALS));
				tempView.setFilter(tempFilter);
				MarketingUnitCollection unitColl = MarketingUnitFactory.getRemoteInstance().getMarketingUnitCollection(tempView);
				if(unitColl != null && unitColl.size() > 0){
					idSet.add(id);
				}
			}
		}
		
		//这些营销团队的成员（非负责人）
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("head.id", idSet, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("isDuty", Boolean.FALSE, CompareType.EQUALS));
		view.setFilter(filter);
		MarketingUnitMemberCollection memberColl = MarketingUnitMemberFactory.getRemoteInstance().getMarketingUnitMemberCollection(view);
		Set memerIdSet = new HashSet();
		if(memberColl != null && memberColl.size() > 0){
			for(int j=0;j<memberColl.size();j++){
				memerIdSet.add(memberColl.get(j).getMember().getId().toString());
			}
		}
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", memerIdSet, CompareType.INCLUDE));
		view.setFilter(filter);
		return view;
    }
    
	public static boolean isImportNotNum(KDTextField txt) {
		String value = txt.getText();
		if(value == null  ||  value.trim().equals("")){
			return false;
		}
		
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(value.trim());
		if(isNum.matches()){
			return false;
		}
		return true;
	}
	
}
