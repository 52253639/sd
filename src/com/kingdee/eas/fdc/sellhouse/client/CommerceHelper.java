package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.io.kds.KDSBookToBook;
import com.kingdee.bos.ctrl.excel.model.struct.Sheet;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.read.POIXlsReader;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDComboColor;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDMenu;
import com.kingdee.bos.ctrl.swing.KDPasswordField;
import com.kingdee.bos.ctrl.swing.KDTimePicker;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.PropertyCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.uiframe.client.NewMainFrame;
import com.kingdee.eas.base.uiframe.client.UIModelDialog;
import com.kingdee.eas.basedata.assistant.CountryCollection;
import com.kingdee.eas.basedata.assistant.CountryFactory;
import com.kingdee.eas.basedata.assistant.CountryInfo;
import com.kingdee.eas.basedata.assistant.IndustryCollection;
import com.kingdee.eas.basedata.assistant.IndustryFactory;
import com.kingdee.eas.basedata.assistant.IndustryInfo;
import com.kingdee.eas.basedata.assistant.ProvinceCollection;
import com.kingdee.eas.basedata.assistant.ProvinceFactory;
import com.kingdee.eas.basedata.assistant.ProvinceInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.sellhouse.AreaRequirementCollection;
import com.kingdee.eas.fdc.sellhouse.AreaRequirementFactory;
import com.kingdee.eas.fdc.sellhouse.AreaRequirementInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo;
import com.kingdee.eas.fdc.sellhouse.BuyHouseReasonCollection;
import com.kingdee.eas.fdc.sellhouse.BuyHouseReasonFactory;
import com.kingdee.eas.fdc.sellhouse.BuyHouseReasonInfo;
import com.kingdee.eas.fdc.sellhouse.CertifacateNameEnum;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceCollection;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceIntentionEnum;
import com.kingdee.eas.fdc.sellhouse.CommerceLevelCollection;
import com.kingdee.eas.fdc.sellhouse.CommerceLevelFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceLevelInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceStatusEnum;
import com.kingdee.eas.fdc.sellhouse.CustomerGradeCollection;
import com.kingdee.eas.fdc.sellhouse.CustomerGradeFactory;
import com.kingdee.eas.fdc.sellhouse.CustomerGradeInfo;
import com.kingdee.eas.fdc.sellhouse.CustomerOriginCollection;
import com.kingdee.eas.fdc.sellhouse.CustomerOriginFactory;
import com.kingdee.eas.fdc.sellhouse.CustomerOriginInfo;
import com.kingdee.eas.fdc.sellhouse.CustomerTypeEnum;
import com.kingdee.eas.fdc.sellhouse.EnterprisePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.EventTypeCollection;
import com.kingdee.eas.fdc.sellhouse.EventTypeFactory;
import com.kingdee.eas.fdc.sellhouse.EventTypeInfo;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.FamillyEarningCollection;
import com.kingdee.eas.fdc.sellhouse.FamillyEarningFactory;
import com.kingdee.eas.fdc.sellhouse.FamillyEarningInfo;
import com.kingdee.eas.fdc.sellhouse.FirstPayProportionCollection;
import com.kingdee.eas.fdc.sellhouse.FirstPayProportionFactory;
import com.kingdee.eas.fdc.sellhouse.FirstPayProportionInfo;
import com.kingdee.eas.fdc.sellhouse.HabitationAreaCollection;
import com.kingdee.eas.fdc.sellhouse.HabitationAreaFactory;
import com.kingdee.eas.fdc.sellhouse.HabitationAreaInfo;
import com.kingdee.eas.fdc.sellhouse.HopePitchCollection;
import com.kingdee.eas.fdc.sellhouse.HopePitchFactory;
import com.kingdee.eas.fdc.sellhouse.HopePitchInfo;
import com.kingdee.eas.fdc.sellhouse.HopedDirectionCollection;
import com.kingdee.eas.fdc.sellhouse.HopedDirectionFactory;
import com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo;
import com.kingdee.eas.fdc.sellhouse.HopedFloorCollection;
import com.kingdee.eas.fdc.sellhouse.HopedFloorFactory;
import com.kingdee.eas.fdc.sellhouse.HopedFloorInfo;
import com.kingdee.eas.fdc.sellhouse.HopedTotalPricesCollection;
import com.kingdee.eas.fdc.sellhouse.HopedTotalPricesFactory;
import com.kingdee.eas.fdc.sellhouse.HopedTotalPricesInfo;
import com.kingdee.eas.fdc.sellhouse.HopedUnitPriceCollection;
import com.kingdee.eas.fdc.sellhouse.HopedUnitPriceFactory;
import com.kingdee.eas.fdc.sellhouse.HopedUnitPriceInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneySubjectContrastCollection;
import com.kingdee.eas.fdc.sellhouse.MoneySubjectContrastFactory;
import com.kingdee.eas.fdc.sellhouse.MoneySubjectContrastInfo;
import com.kingdee.eas.fdc.sellhouse.PlanisphereInfo;
import com.kingdee.eas.fdc.sellhouse.ProductDetialCollection;
import com.kingdee.eas.fdc.sellhouse.ProductDetialFactory;
import com.kingdee.eas.fdc.sellhouse.ProductDetialInfo;
import com.kingdee.eas.fdc.sellhouse.ReceptionTypeCollection;
import com.kingdee.eas.fdc.sellhouse.ReceptionTypeFactory;
import com.kingdee.eas.fdc.sellhouse.ReceptionTypeInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFormCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFormFactory;
import com.kingdee.eas.fdc.sellhouse.RoomFormInfo;
import com.kingdee.eas.fdc.sellhouse.RoomModelCollection;
import com.kingdee.eas.fdc.sellhouse.RoomModelFactory;
import com.kingdee.eas.fdc.sellhouse.RoomModelInfo;
import com.kingdee.eas.fdc.sellhouse.RoomModelTypeCollection;
import com.kingdee.eas.fdc.sellhouse.RoomModelTypeFactory;
import com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SexEnum;
import com.kingdee.eas.fdc.sellhouse.SightRequirementCollection;
import com.kingdee.eas.fdc.sellhouse.SightRequirementFactory;
import com.kingdee.eas.fdc.sellhouse.SightRequirementInfo;
import com.kingdee.eas.fdc.sellhouse.TrackRecordEnum;
import com.kingdee.eas.fdc.sellhouse.TrackRecordFactory;
import com.kingdee.eas.fdc.sellhouse.WorkAreaCollection;
import com.kingdee.eas.fdc.sellhouse.WorkAreaFactory;
import com.kingdee.eas.fdc.sellhouse.WorkAreaInfo;
import com.kingdee.eas.fdc.tenancy.BusinessScopeCollection;
import com.kingdee.eas.fdc.tenancy.BusinessScopeFactory;
import com.kingdee.eas.fdc.tenancy.BusinessScopeInfo;
import com.kingdee.eas.fdc.tenancy.CooperateModeCollection;
import com.kingdee.eas.fdc.tenancy.CooperateModeFactory;
import com.kingdee.eas.fdc.tenancy.CooperateModeInfo;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberCollection;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberFactory;
import com.kingdee.eas.fdc.tenancy.NatureEnterpriseCollection;
import com.kingdee.eas.fdc.tenancy.NatureEnterpriseFactory;
import com.kingdee.eas.fdc.tenancy.NatureEnterpriseInfo;
import com.kingdee.eas.fm.common.client.AbstractHidedMenuItem;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseInfo;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.framework.client.mutex.DataObjectMutex;
import com.kingdee.eas.framework.client.mutex.IDataObjectMutex;
import com.kingdee.eas.tools.datatask.client.FileChooserPromptBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;
import com.sun.xml.messaging.saaj.util.ByteInputStream;
import com.sun.xml.messaging.saaj.util.ByteOutputStream;

public class CommerceHelper {
	
	
	private static final Logger logger = CoreUIObject.getLogger(CommerceHelper.class);
	
	public static Color BK_COLOR_MUST = new Color(0xFCFBDF);
	
	
	/**
	 * @deprecated  建议传递系统参数
	 */
	public static EntityViewInfo getPermitCustomerView(SellProjectInfo sp) throws EASBizException, BOSException{
		return getPermitCustomerView(sp,SysContext.getSysContext().getCurrentUserInfo());
	}
	/**
	 * 获得指定营销人员能够看到的客户资料的view (针对客户资料的f7控件)
	 * @return EntityViewInfo
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public static EntityViewInfo getPermitCustomerView(SellProjectInfo sp,UserInfo userInfo) throws EASBizException, BOSException{	
		SaleOrgUnitInfo orgUnitInfo = SysContext.getSysContext().getCurrentSaleUnit();
		
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();		
		if(sp==null) {
			String permitSaleIdStr = getPermitSaleManIdSql(sp,userInfo);
//			String permitProIdStr = getPermitProjectIdSql(userInfo);
//			filter.getFilterItems().add(new FilterItemInfo("createUnit.id",orgUnitInfo.getId().toString()));
			
			filter.getFilterItems().add(new FilterItemInfo("salesMan.id",permitSaleIdStr,CompareType.INNER));			
			//共享给营销顾问的客户
//			String saleSaleIdStr = "select FCustomerID from T_SHE_ShareProperty where FUserID in ("+permitSaleIdStr+")";
//			filter.getFilterItems().add(new FilterItemInfo("id",saleSaleIdStr,CompareType.INNER));			
//			//共享给项目的客户
//			String sellProIdStr = "select FCustomerID from T_SHE_ShareSellProject where FSellProjectID in ("+permitProIdStr+") ";
//			filter.getFilterItems().add(new FilterItemInfo("id",sellProIdStr,CompareType.INNER));
			
//			filter.setMaskString("#0 and (#1 or #2 or #3)");
		}else{
			String permitSaleIdStr = getPermitSaleManIdSql(sp,userInfo);
			//permitSaleIdStr += " and FSellProjectID = '"+sellPorjct.getId()+"' ";
			
//			filter.getFilterItems().add(new FilterItemInfo("createUnit.id",orgUnitInfo.getId().toString()));
			sp=SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(sp.getId()));
			if(sp.getParent()!=null){
				filter.getFilterItems().add(new FilterItemInfo("project.id",sp.getParent().getId().toString()));
			}else{
				filter.getFilterItems().add(new FilterItemInfo("project.id",sp.getId().toString()));
			}
			
			filter.getFilterItems().add(new FilterItemInfo("salesMan.id",permitSaleIdStr,CompareType.INNER));			
			//共享给营销顾问的客户
//			String saleSaleIdStr = "select FCustomerID from T_SHE_ShareProperty where FUserID in ("+permitSaleIdStr+")";
//			filter.getFilterItems().add(new FilterItemInfo("id",saleSaleIdStr,CompareType.INNER));			
//			//共享给项目的客户
//			String sellProIdStr = "select FCustomerID from T_SHE_ShareSellProject where FSellProjectID = '"+sellPorjct.getId()+"' ";
//			filter.getFilterItems().add(new FilterItemInfo("id",sellProIdStr,CompareType.INNER));
			
//			filter.setMaskString("#0 and ((#1 and (#2 or #3)) or #4)  ");			
		}
		
		viewInfo.setFilter(filter);
		return viewInfo;
	}
	
	
	public static String getPermitShareCustomerIdStr(UserInfo userInfo) {
		String userId = "null";
		if(userInfo!=null)	userId = userInfo.getId().toString();
		//T_SHE_ShareSeller	FHeadID	FSellerID	FMarketingUnitID FOrgUnitID
		String idSqlString = "select fheadId from T_SHE_ShareSeller where FSellerID = '"+userId+"'";
		String muIdStr = "";
		try {
			Set muIdSet = getMuIdSetBySaleMan(userInfo);
			if(muIdSet.size()>0)
				muIdStr = getStringFromSet(muIdSet);
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		if(!muIdStr.equals(""))
			idSqlString += " union select fheadId from T_SHE_ShareSeller where FMarketingUnitID in ("+muIdStr+")  ";
		SaleOrgUnitInfo saleUnitInfo = SysContext.getSysContext().getCurrentSaleUnit();
		if(saleUnitInfo!=null && saleUnitInfo.isIsBizUnit()) {
			idSqlString += " union select fheadId from T_SHE_ShareSeller where FOrgUnitID ='"+saleUnitInfo.getId().toString()+"'  ";
		}
		return idSqlString;
	}
	
	public static String getStringFromSet(Set srcSet){
		String retStr = "";
		if(srcSet==null || srcSet.size()==0) return retStr;
		Iterator iter = srcSet.iterator();
		while(iter.hasNext()){
			Object obj = iter.next();
			if(obj instanceof String) retStr += ",'" + (String)obj + "'";
		}
		if(!retStr.equals("")) retStr = retStr.replaceFirst(",", "");
		return retStr;
	}	
	
	
	/**
	 * 获得指定营销顾问所能看到的营销单元
	 */
	private static Set getMuIdSetBySaleMan(UserInfo userInfo) throws BOSException , SQLException {		
		SaleOrgUnitInfo saleOrgUnit = SysContext.getSysContext().getCurrentSaleUnit();
		MarketingUnitMemberCollection muMemColl = MarketingUnitMemberFactory.getRemoteInstance().getMarketingUnitMemberCollection(
				"select id where (head.orgUnit.number = '"+ saleOrgUnit.getNumber() +"' and head.orgUnit.longNumber like '"+saleOrgUnit.getLongNumber()+"!%') " +
						"and member.id = '"+userInfo.getId().toString()+"' ");
		Set idSet = new HashSet();
		for(int i=0;i<muMemColl.size();i++)
			idSet.add(muMemColl.get(i).getHead().getId().toString());
		return idSet;
	}
	
	
	
	/**
	 * 获得当前营销人员能够看到的营销项目的view (针对销售项目的f7控件)
	 * @return
	 * @throws EASBizException 
	 */
	public static EntityViewInfo getPermitProjectView() throws BOSException, EASBizException {	
		UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",getPermitProjectIdSql(currentUserInfo),CompareType.INNER));
		view.setFilter(filter);
		return view;
	}
	
	
	/**
	 * 获得当前营销人员能够看到的营销顾问的view (针对销售顾问的f7控件)
	 * @return
	 * @throws EASBizException 
	 */
	public static EntityViewInfo getPermitSalemanView(SellProjectInfo sp) throws BOSException, EASBizException {
		UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();		
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",getPermitSaleManIdSql(sp,currentUserInfo),CompareType.INNER));
		view.setFilter(filter);
		return view;
	}
	
	
	public static EntityViewInfo getCUFilterView() {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("CU.id",SysContext.getSysContext().getCurrentCtrlUnit().getId().toString()));
		view.setFilter(filter);
		return view;
	}
	
	
	/**
	 * 获得指定销售顾问所有权限看到的营销顾问的idSql---提示：对于filter中的使用要用  CompareType.INNER 方式
	 */
	private static String getPermitSaleManIdSqlFromPlat(UserInfo userInfo) 	{
		String userId = "null";
		if(userInfo!=null)	userId = userInfo.getId().toString();
		
		String idSqlString = "select distinct(fmemberId) from t_ten_marketUnitPlat where fdutyPersonId = '"+userId+"'";
		return idSqlString;
	}

	
	
	/**
	 * 获得指定营销人员能够看到的营销人员的id集合  建议使用 getPermitSaleManIdSql
	 * @return 营销人员的id集合 
	 */
	public static Set getPermitSaleManIdSet(UserInfo userInfo) throws BOSException, EASBizException
	{	
		return MarketingUnitFactory.getRemoteInstance().getPermitSaleManIdSet(userInfo);
	}
	public static String getPermitSaleManIdSql(SellProjectInfo sp,UserInfo userInfo) throws BOSException, EASBizException
	{
		return MarketingUnitFactory.getRemoteInstance().getPermitSaleManIdSql(userInfo,sp);
	}
	
		
	/**
	 * 获得当前营销人员能够看到的营销项目的id集合
	 */
	public static Set getPermitProjectIds()  throws BOSException{
		UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();
		return getPermitProjectIds(currentUserInfo);
	}

	public static Set getPermitProjectIds(UserInfo userInfo)  throws BOSException{
		try {
			return MarketingUnitFactory.getRemoteInstance().getPermitSellProjectIdSet(userInfo);
		} catch (EASBizException e) {
			e.printStackTrace();
			SysUtil.abort();
		}
		return null;
	}
	
	public static String getPermitProjectIdSql(UserInfo userInfo)  throws BOSException, EASBizException{
		return MarketingUnitFactory.getRemoteInstance().getPermitSellProjectIdSql(userInfo);	
	}
	
	
	

	/**
	 * 获得当前营销人员能够看到的商机的view  (针对商机的f7控件)
	 * @return EntityViewInfo
	 */
	public static EntityViewInfo getPermitCommerceChanceView(){	
		UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();
		
		EntityViewInfo viewInfo = new EntityViewInfo();		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("saleMan.id", currentUserInfo.getId().toString()));
		
		viewInfo.setFilter(filter);
		return viewInfo;
	}
	
	
	
	
	/**
	 * 以ModelDialog模式u打开指定的ui查看界面;  如果id为空则打开其新增界面
	 * @param owner
	 * @param uiClassName
	 * @param id
	 */
	public static void openTheUIWindow(Object owner,String uiClassName,String id) {		
		UIContext uiContext = new UIContext(owner); 		
		
		String opera = OprtState.ADDNEW;
		if(id!=null && !id.trim().equals(""))  {
			uiContext.put("ActionView", "OnlyView");
			uiContext.put(UIContext.ID, id);
			opera = OprtState.VIEW;
		}
		try {
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(uiClassName, uiContext, null, opera);
			uiWindow.show();
		} catch (UIException e) {
			e.printStackTrace();
			SysUtil.abort();
		}
	}
	
	
	
	
	/**
	 * 判断f7控件的值是否改变 (当f7的值被重新选择时)  若id一直就认为没变
	 */
	public static boolean isF7DataChanged(DataChangeEvent e){
		CoreBaseInfo oldValue = (CoreBaseInfo)e.getOldValue();
		CoreBaseInfo newValue = (CoreBaseInfo)e.getNewValue();
		if(oldValue==null && newValue!=null)
			return true;
		else if(oldValue!=null && newValue==null)
			return true;
		else if(!oldValue.getId().equals(newValue.getId()))
			return true;
		
		return false;
	}
	
	
	
	
	
	
	public static ICellEditor getKDBizPromptBoxEditor(String queryInfoStr,EntityViewInfo viewInfo){
		return getKDBizPromptBoxEditor(queryInfoStr,viewInfo,null);
	}
	
	/**
	 * 绑定的控件为KDBizPromptBox的cellEditor
	 * @param queryInfoStr
	 * @param viewInfo
	 * @return
	 */
	public static ICellEditor getKDBizPromptBoxEditor(String queryInfoStr,EntityViewInfo viewInfo,String defaultF7UIName){
		KDBizPromptBox f7Prompt = new KDBizPromptBox();
		f7Prompt.setQueryInfo(queryInfoStr);
		f7Prompt.setEditable(true);
		f7Prompt.setDisplayFormat("$name$");
		f7Prompt.setEditFormat("$name$");
		f7Prompt.setCommitFormat("$number$");
		if(viewInfo!=null)
			f7Prompt.setEntityViewInfo(viewInfo);
		if(defaultF7UIName!=null)
			f7Prompt.setDefaultF7UIName(defaultF7UIName);
		ICellEditor f7editor = new KDTDefaultCellEditor(f7Prompt);
		return f7editor;
	}
	
	/**
	 * 绑定的控件为KDComboBox的cellEditor
	 * @param enumList   枚举的list 例如：CertifacateNameEnum.getEnumList()
	 * @return
	 */
	public static ICellEditor getKDComboBoxEditor(List enumList){
		KDComboBox comboField = new KDComboBox();
		if(enumList!=null)
			for (int i = 0; i < enumList.size(); i++) {
				comboField.addItem(enumList.get(i));
			}
		//comboField.addItems(EnumUtils.getEnumList("**").toArray());
		ICellEditor comboEditor = new KDTDefaultCellEditor(comboField);
		return comboEditor;		
	}
	
	/**
	 * 绑定的控件为KDDatePicker的cellEditor
	 */
	public static ICellEditor getKDDatePickerEditor(){
		KDDatePicker datePicker = new KDDatePicker();
		datePicker.setMilliSecondEnable(false);
		datePicker.setTimeEnabled(false);
		ICellEditor comboEditor = new KDTDefaultCellEditor(datePicker);
		return comboEditor;		
	}
	
	/**
	 * 绑定的控件为KDTimePicker的cellEditor
	 */
	public static ICellEditor getKDTimePickerEditor(){
		KDTimePicker timePicker = new KDTimePicker();
		timePicker.setMilliSecondEnable(false);
		timePicker.setTimeEnabled(false);
		ICellEditor comboEditor = new KDTDefaultCellEditor(timePicker);
		return comboEditor;		
	}
	
	/**
	 * 绑定的控件为KDFormattedTextField的cellEditor 数字类型，精度2，不准为负数
	 * @return
	 */
	public static ICellEditor getKDFormattedTextDecimalEditor(){
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor formatTextEditor = new KDTDefaultCellEditor(formattedTextField);
		return formatTextEditor;		
	}
	
	
	/**
	 * 绑定的控件为KDComboColor
	 * @return
	 */
	public static ICellEditor getKDComboColorEditor(){
		KDComboColor comboColor = new KDComboColor();
		ICellEditor formatTextEditor = new KDTDefaultCellEditor(comboColor);
		return formatTextEditor;		
	}
	
	
	/**
	 * 当前组织下是否存在编码规则
	 * @param editData
	 * @return
	 */
	public static boolean isExistNumberRule(IObjectValue editData)
	{
		boolean isExist = false;
		
		OrgUnitInfo crrOu = SysContext.getSysContext().getCurrentSaleUnit();   //当前销售组织;
		if(crrOu==null)
			crrOu = SysContext.getSysContext().getCurrentOrgUnit();            //当前组织
		
		try{
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
			isExist = iCodingRuleManager.isExist(editData, crrOu.getId().toString());
		}catch(Exception e) {
			e.printStackTrace();
			SysUtil.abort();
		}
		
		return isExist;
	}
	
	
	/**
	 * 获得当前组织下的编码 -- 按编码规则 
	 * @param editData
	 * @return
	 */
	public static String getNumberByRule(IObjectValue editData)
	{
		String retNumber = "";
		
		OrgUnitInfo crrOu = SysContext.getSysContext().getCurrentSaleUnit();   //当前销售组织;
		if(crrOu==null)
			crrOu = SysContext.getSysContext().getCurrentOrgUnit();            //当前组织
		
		try{
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
			retNumber = iCodingRuleManager.getNumber(editData, crrOu.getId().toString());
		}catch(Exception e) {
			e.printStackTrace();
			SysUtil.abort();
		}
		
		return retNumber;
	}
	
	
	/**
	 * 批量获得当前组织下的编码 -- 按编码规则 
	 * @param editData
	 * @return
	 */
	public static String[] getBatchNumberByRule(IObjectValue editData,int count)
	{
		String[] retNumber = null;
		
		OrgUnitInfo crrOu = SysContext.getSysContext().getCurrentSaleUnit();   //当前销售组织;
		if(crrOu==null)
			crrOu = SysContext.getSysContext().getCurrentOrgUnit();            //当前组织
		
		try{
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
			retNumber = iCodingRuleManager.getBatchNumber(editData, crrOu.getId().toString(),count);
		}catch(Exception e) {
			e.printStackTrace();
			SysUtil.abort();
		}
		
		return retNumber;
	}
	
	
	/**
	 * 
	 * @param str_input		
	 * @param rDateFormat	格式串 比如：yyyy-MM-dd 或 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static  boolean isDateFormat(String str_input,String rDateFormat){
		if (str_input!=null) {
			SimpleDateFormat formatter = new SimpleDateFormat(rDateFormat);
			try {
				formatter.parse(str_input);
			} catch (Exception e) {
				return false;
			}
			return true;
		}
		return false;
	}
	
	
    public static boolean isNumber(String input)
    {
       if(input ==null || input.equals(""))
           return false;

        boolean havestr = false;
        String tmp = "";
        for (int j = 0; j < input.length(); j++)
        {
            havestr = false;
            tmp = input.substring(j, j + 1);
            if (tmp.getBytes()[0] < 48 || tmp.getBytes()[0] > 57)
            {
                havestr = true;
            }
            if (havestr)
            {
                return false;
            }
        }
        return true;
    }
	
	
	
	
	/**
	 * 性别
	 * @return  mapSexEnum	临时存储的映射
	 */
	public static Map getSexEnumMap(){
		Map mapSexEnum = new HashMap();
		List enumlist = SexEnum.getEnumList();
		for(int j=0;j<enumlist.size();j++) {
			SexEnum sexT = (SexEnum)enumlist.get(j);
			mapSexEnum.put(sexT.getAlias().trim(),sexT);
		}
		return mapSexEnum;
	}
	
	/**
	 * 跟进阶段   已由TrackPhaseEnum--CommerceStatusEnum
	 * @return mapTrackPhase
	 */
	public static Map getTrackPhaseCSMap() {
		Map mapTrackPhaseEnum = new HashMap();
		List enumlist = CommerceStatusEnum.getEnumList();
		for(int j=0;j<enumlist.size();j++) {
			CommerceStatusEnum trackEnum = (CommerceStatusEnum)enumlist.get(j);
			mapTrackPhaseEnum.put(trackEnum.getAlias().trim(),trackEnum);
		}
		return mapTrackPhaseEnum;
	}
	
	/**
	 * 企业性质 EnterprisePropertyEnum
	 * @return mapTrackPhase
	 */
	public static Map getEnterprisePropertyMap() {
		Map mapTrackPhaseEnum = new HashMap();
		List enumlist = EnterprisePropertyEnum.getEnumList();
		for(int j=0;j<enumlist.size();j++) {
			EnterprisePropertyEnum trackEnum = (EnterprisePropertyEnum)enumlist.get(j);
			mapTrackPhaseEnum.put(trackEnum.getAlias().trim(),trackEnum);
		}
		return mapTrackPhaseEnum;
	}
	/**
	 * 企业性质 NatureEnterpriseCollection
	 * @return natureEnterpriseMap
	 * by xiaoao_liu
	 */
	public static Map getNatureEnterpriseMap()  throws BOSException{    	
		Map natureEnterpriseMap = new HashMap();    	    		
		
//		BusinessScopeCollection thisColl = BusinessScopeFactory.getRemoteInstance().getBusinessScopeCollection("select id,name");
		NatureEnterpriseCollection thisColl = NatureEnterpriseFactory.getRemoteInstance().getNatureEnterpriseCollection("select id,name");
		for(int i=0;i<thisColl.size();i++) {
			NatureEnterpriseInfo thisInfo = thisColl.get(i);
			//容错处理　－－zhicheng_jin  090614
			if(thisInfo.getName() == null){
				logger.error("脏数据，业务范围为空.id:" + thisInfo.getId().toString());
				continue;
			}
			natureEnterpriseMap.put(thisInfo.getName().trim(),thisInfo);
		}
		
		return natureEnterpriseMap;
	}
	
	/**
	 * 证件名称
	 * @return mapCertifacateNameEnum
	 */
	public static Map getCertifacateNameMap() {
		Map mapCertifacateNameEnum = new HashMap();
		List enumlist = CertifacateNameEnum.getEnumList();
		for(int j=0;j<enumlist.size();j++) {
			CertifacateNameEnum trackEnum = (CertifacateNameEnum)enumlist.get(j);
			mapCertifacateNameEnum.put(trackEnum.getAlias().trim(),trackEnum);
		}
		return mapCertifacateNameEnum;
	}
	
	/**
	 * 客户类型
	 * @return mapCustomerTypeEnum
	 */
	public static Map getCustomerTypeMap() {
		Map mapCustomerTypeEnum = new HashMap();
		List enumlist = CustomerTypeEnum.getEnumList();
		for(int j=0;j<enumlist.size();j++) {
			CustomerTypeEnum trackEnum = (CustomerTypeEnum)enumlist.get(j);
			mapCustomerTypeEnum.put(trackEnum.getAlias().trim(),trackEnum);
		}
		return mapCustomerTypeEnum;
	}
	
	
	/**
	 * 房屋户型  RoomModelQuery
	 * @return mapRoomModel   临时存储的映射
	 */
	public static Map getRoomModelByBuildingIdMap(String buildingId) throws BOSException{    	
		Map mapRoomModel = new HashMap();    		
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection itemColl = new SelectorItemCollection();
		itemColl.add(new SelectorItemInfo("*"));
		itemColl.add(new SelectorItemInfo("building.id"));
		view.setSelector(itemColl);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("building.id",buildingId));
		view.setFilter(filter);		 
		
		RoomModelCollection thisColl = RoomModelFactory.getRemoteInstance().getRoomModelCollection(view);
		for(int i=0;i<thisColl.size();i++) {
			RoomModelInfo thisInfo = thisColl.get(i);
			mapRoomModel.put(thisInfo.getName().trim(),thisInfo);
		}
		return mapRoomModel;
	}
	
	/**
	 * 省市  ProvinceQuery
	 * @return mapProvince
	 */
	public static Map getProvinceMap()  throws BOSException{    	
		Map	mapProvince = new HashMap();    
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("id");
		selector.add("name");
		view.setSelector(selector);
		ProvinceCollection thisColl = ProvinceFactory.getRemoteInstance().getProvinceCollection(view);
		for(int i=0;i<thisColl.size();i++) {
			ProvinceInfo thisInfo = thisColl.get(i);
			mapProvince.put(thisInfo.getName().trim(),thisInfo);
		}
		
		return mapProvince;
	}
	
	
	
	/**
	 * 居住区域  HabitationAreaQuery
	 * @return   mapHabitationArea
	 */
	public static Map getHabitationAreaMap()  throws BOSException{    	
		Map mapHabitationArea = new HashMap();    	    		

		HabitationAreaCollection thisColl = HabitationAreaFactory.getRemoteInstance().getHabitationAreaCollection("select id,name");
		for(int i=0;i<thisColl.size();i++) {
			HabitationAreaInfo thisInfo = thisColl.get(i);
			//容错处理　－－zhicheng_jin  090614
			if(thisInfo.getName() == null){
				logger.error("脏数据，居住区域/所在区域名称为空.id:" + thisInfo.getId().toString());
				continue;
			}
			mapHabitationArea.put(thisInfo.getName().trim(),thisInfo);
		}
		
		return mapHabitationArea;
	}
	
	/**
	 * 工作区域  HabitationAreaQuery
	 * @return   mapHabitationArea
	 */
	public static Map getWorkAreaMap()  throws BOSException{    	
		Map mapHabitationArea = new HashMap();    	    		

		WorkAreaCollection thisColl = WorkAreaFactory.getRemoteInstance().getWorkAreaCollection("select id,name");
		for(int i=0;i<thisColl.size();i++) {
			WorkAreaInfo thisInfo = thisColl.get(i);
			//容错处理　－－zhicheng_jin  090614
			if(thisInfo.getName() == null){
				logger.error("脏数据，工作区域/服务区域名称为空.id:" + thisInfo.getId().toString());
				continue;
			}
			mapHabitationArea.put(thisInfo.getName().trim(),thisInfo);
		}
		
		return mapHabitationArea;
	}

	
	/**
	 * 行业  HabitationAreaQuery
	 * @return   mapHabitationArea
	 */
	public static Map getIndustryMap()  throws BOSException{    	
		Map mapIndustry = new HashMap();    	    		
		
		IndustryCollection thisColl = IndustryFactory.getRemoteInstance().getIndustryCollection("select id,name");
		for(int i=0;i<thisColl.size();i++) {
			IndustryInfo thisInfo = thisColl.get(i);
			//容错处理　－－zhicheng_jin  090614
			if(thisInfo.getName() == null){
				logger.error("脏数据，行业名称为空.id:" + thisInfo.getId().toString());
				continue;
			}
			mapIndustry.put(thisInfo.getName().trim(),thisInfo);
		}
		
		return mapIndustry;
	}
	
	/**
	 * 合作模式  
	 * @return   mapHabitationArea
	 */
	public static Map getCooperateModeMap()  throws BOSException{    	
		Map cooperateModeMap = new HashMap();    	    		
//		EntityViewInfo view = new EntityViewInfo();
//		SelectorItemCollection selector = new SelectorItemCollection();
//		selector.add("id");
//		selector.add("name");
//		view.setSelector(selector);
		CooperateModeCollection thisColl = CooperateModeFactory.getRemoteInstance().getCooperateModeCollection();
		for(int i=0;i<thisColl.size();i++) {
			CooperateModeInfo thisInfo = thisColl.get(i);
			//容错处理　－－zhicheng_jin  090614
			if(thisInfo.getName() == null){
				logger.error("脏数据，合作模式名称为空.id:" + thisInfo.getId().toString());
				continue;
			}
			cooperateModeMap.put(thisInfo.getName().trim(),thisInfo);
		}
		
		return cooperateModeMap;
	}

	/**
	 * 业务范围  
	 * @return   mapHabitationArea
	 */
	public static Map getBusinessScopeMap()  throws BOSException{    	
		Map businessScopeMap = new HashMap();    	    		
		
		BusinessScopeCollection thisColl = BusinessScopeFactory.getRemoteInstance().getBusinessScopeCollection("select id,name");
		for(int i=0;i<thisColl.size();i++) {
			BusinessScopeInfo thisInfo = thisColl.get(i);
			//容错处理　－－zhicheng_jin  090614
			if(thisInfo.getName() == null){
				logger.error("脏数据，业务范围为空.id:" + thisInfo.getId().toString());
				continue;
			}
			businessScopeMap.put(thisInfo.getName().trim(),thisInfo);
		}
		
		return businessScopeMap;
	}

	/**
	 * 客户经理 
	 * @return   mapHabitationArea
	 */
	public static Map getCustomerManagerMap()  throws BOSException{    	
		Map customerManagerMapMap = new HashMap();    	    		
		
		UserCollection thisColl = UserFactory.getRemoteInstance().getUserCollection("select id,name");
		for(int i=0;i<thisColl.size();i++) {
			UserInfo thisInfo = thisColl.get(i);
			//容错处理　－－zhicheng_jin  090614
			if(thisInfo.getName() == null){
				logger.error("脏数据，客户经理名称为空.id:" + thisInfo.getId().toString());
				continue;
			}
			customerManagerMapMap.put(thisInfo.getName().trim(),thisInfo);
		}
		
		return customerManagerMapMap;
	}

	/**
	 * 国家 
	 * @return   mapHabitationArea
	 */
	public static Map getCountryModeMap()  throws BOSException{    	
		Map countryMap = new HashMap();    	    		
		
		CountryCollection thisColl = CountryFactory.getRemoteInstance().getCountryCollection("select id,name");
		for(int i=0;i<thisColl.size();i++) {
			CountryInfo thisInfo = thisColl.get(i);
			//容错处理　－－zhicheng_jin  090614
			if(thisInfo.getName() == null){
				logger.error("脏数据，国家为空.id:" + thisInfo.getId().toString());
				continue;
			}
			countryMap.put(thisInfo.getName().trim(),thisInfo);
		}
		
		return countryMap;
	}
	
	
	/**
	 * 客户来源  CustomerOriginQuery
	 * @param mapCustomerOrigin
	 * @param nameStr
	 * @return  
	 */
	public static Map getCustomerOriginMap()  throws BOSException{    	
		Map mapCustomerOrigin = new HashMap();    		

		CustomerOriginCollection thisColl = CustomerOriginFactory.getRemoteInstance().getCustomerOriginCollection("select id,name");
		for(int i=0;i<thisColl.size();i++) {
			CustomerOriginInfo thisInfo = thisColl.get(i);
			mapCustomerOrigin.put(thisInfo.getName().trim(),thisInfo);
		}
		
		return mapCustomerOrigin;
	}
	
	
	/**
	 * 接待方式  ReceptionTypeQuery
	 * @return   mapReceptionType
	 */
	public static Map getReceptionTypeMap()  throws BOSException{    	
		Map	mapReceptionType = new HashMap();    
		ReceptionTypeCollection thisColl = ReceptionTypeFactory.getRemoteInstance().getReceptionTypeCollection("select id,name");
		for(int i=0;i<thisColl.size();i++) {
			ReceptionTypeInfo thisInfo = thisColl.get(i);
			mapReceptionType.put(thisInfo.getName().trim(),thisInfo);
		}
		
		return mapReceptionType;
	}
	
	
	/**
	 * 跟进事件  EventTypeQuery
	 * @return   mapEventType
	 */
	public static Map getEventTypeMap()  throws BOSException{    	
		Map	mapEventType = new HashMap();    
		EventTypeCollection thisColl = EventTypeFactory.getRemoteInstance().getEventTypeCollection("select id,name");
		for(int i=0;i<thisColl.size();i++) {
			EventTypeInfo thisInfo = thisColl.get(i);
			mapEventType.put(thisInfo.getName().trim(),thisInfo);
		}
		
		return mapEventType;
	}
	
	
	/**
	 * 客户等级  CustomerGradeQuery
	 * @return   mapCustomerGrade
	 */
	public static Map getCustomerGradeMap() throws BOSException{    	
		Map mapCustomerGrade = new HashMap();   	
		CustomerGradeCollection thisColl = CustomerGradeFactory.getRemoteInstance().getCustomerGradeCollection("select id,name");
		for(int i=0;i<thisColl.size();i++) {
			CustomerGradeInfo thisInfo = thisColl.get(i);
			mapCustomerGrade.put(thisInfo.getName().trim(),thisInfo);
		}
		
		return mapCustomerGrade;
	}
	
	
	/**
	 * 家庭收入  FamillyEarningQuery
	 * @return  mapFamillyEarning
	 */
	public static Map getFamillyEarningMap() throws BOSException{ 
		Map mapFamillyEarning = new HashMap();
		FamillyEarningCollection thisColl = FamillyEarningFactory.getRemoteInstance().getFamillyEarningCollection("select id,name");
		for(int i=0;i<thisColl.size();i++) {
			FamillyEarningInfo thisInfo = thisColl.get(i);
			mapFamillyEarning.put(thisInfo.getName().trim(),thisInfo);
		}
		return mapFamillyEarning;
	}
	
	
	/**
	 * 营销项目  SellProjectQuery  不建议使用 SellProjectFactory.getRemoteInstance()getSellProjectCollection ；它会把所有楼栋和房间分录都查询出来
	 * 必须改成 sql 直接查询，或在 SellProject 上加方法重新写   
	 * @return  mapSellProject
	 */
	public static Map getSellProjectMap() throws BOSException{    	
		Map mapSellProject = new HashMap();   
		try {
			FDCSQLBuilder builder = new FDCSQLBuilder();	
			builder.appendSql("select fid,fname_l2 from t_she_sellProject");
			IRowSet tableSet = builder.executeQuery();
			while (tableSet.next()) {
				String FID = tableSet.getString("FID");
				String FName = tableSet.getString("fname_l2");
				if(FName==null) FName = "";
				SellProjectInfo thisInfo = new SellProjectInfo();
				thisInfo.setId(BOSUuid.read(FID));
				thisInfo.setName(FName);
				mapSellProject.put(FName.trim(),thisInfo);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		return mapSellProject;
	}
		
	
	
	public static Map getPermitUserMap(SellProjectInfo sp,UserInfo saleMan) throws BOSException{
		String permitSaleManIsSql = null;
		try {
			permitSaleManIsSql =getPermitSaleManIdSql(sp,saleMan);
		} catch (EASBizException e) {
			e.printStackTrace();
			SysUtil.abort();
		}

		Map mapUser = new HashMap();    	
			Set dupNameSet = new HashSet();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("isDelete",Boolean.FALSE));
			filter.getFilterItems().add(new FilterItemInfo("isLocked",Boolean.FALSE));
			filter.getFilterItems().add(new FilterItemInfo("isForbidden",Boolean.FALSE));
			filter.getFilterItems().add(new FilterItemInfo("id",permitSaleManIsSql,CompareType.INNER));
			view.setFilter(filter);
			SelectorItemCollection seletor = new SelectorItemCollection();
			seletor.add("id");
			seletor.add("name");
			seletor.add("number");
			view.setSelector(seletor);
			UserCollection thisColl = UserFactory.getRemoteInstance().getUserCollection(view);
			for(int i=0;i<thisColl.size();i++) {
				UserInfo thisInfo = thisColl.get(i);
				String userName = thisInfo.getName();
				if(userName==null) continue;
				userName = userName.trim(); 
				if(mapUser.containsKey(userName))
					dupNameSet.add(userName);
				else	
					mapUser.put(userName,thisInfo);
			}
			Iterator iterator = dupNameSet.iterator();
			while(iterator.hasNext()) {
				String dupName = (String)iterator.next();
				mapUser.remove(dupName);
			}			
		return mapUser;
	}
	/**
	 * @author tim_gao
	 *  根据number字段，返回个人信息
	 * @since 2010-9-27
	 */
	public static UserInfo getUserMapByNumber(SellProjectInfo sp,String saleMan) throws BOSException{
		String permitSaleManIsSql = null;
		try {
			UserInfo sqlUserInfo = new UserInfo();
			sqlUserInfo.setNumber(saleMan);
			permitSaleManIsSql =getPermitSaleManIdSql(sp,sqlUserInfo);
		} catch (EASBizException e) {
			e.printStackTrace();
			SysUtil.abort();
		}
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("isDelete",Boolean.FALSE));
			filter.getFilterItems().add(new FilterItemInfo("isLocked",Boolean.FALSE));
			filter.getFilterItems().add(new FilterItemInfo("isForbidden",Boolean.FALSE));
			filter.getFilterItems().add(new FilterItemInfo("id",permitSaleManIsSql,CompareType.INNER));
			view.setFilter(filter);
			SelectorItemCollection seletor = new SelectorItemCollection();
			seletor.add("id");
			seletor.add("name");
			seletor.add("number");
			view.setSelector(seletor);
			UserCollection thisColl = UserFactory.getRemoteInstance().getUserCollection(view);
			Iterator it = thisColl.iterator();
			while(it.hasNext()){
				UserInfo tempUser = (UserInfo)it.next();
				if(saleMan.toString().trim().equals(tempUser.getNumber().toString().trim()))
				{
					return tempUser;
				}
			}
		return null;
	}
	
	/**
	 * 商机级别  CommerceLevelQuery
	 * @return  mapCommerceLevel
	 */
	public static Map getCommerceLevelMap() throws BOSException{ 
		Map mapCommerceLevel = new HashMap();
		CommerceLevelCollection thisColl = CommerceLevelFactory.getRemoteInstance().getCommerceLevelCollection("select id,name");
		for(int i=0;i<thisColl.size();i++) {
			CommerceLevelInfo thisInfo = thisColl.get(i);
			mapCommerceLevel.put(thisInfo.getName().trim(),thisInfo);
		}

		return mapCommerceLevel;
	}
	
	
	/**
	 * 意向建筑性质  BuildingPropertyQuery
	 * @return  mapIntentBuildingPro
	 */
	public static Map getIntentBuildingPro() throws BOSException{ 
		Map mapIntentBuildingPro = new HashMap();
		BuildingPropertyCollection thisColl = BuildingPropertyFactory.getRemoteInstance().getBuildingPropertyCollection("select id,name");
		for(int i=0;i<thisColl.size();i++) {
			BuildingPropertyInfo thisInfo = thisColl.get(i);
			mapIntentBuildingPro.put(thisInfo.getName().trim(),thisInfo);
		}
		return mapIntentBuildingPro;
	}
	
	
	/**
	 * 意向产品类型  ProductTypeQuery
	 * @return  mapIntentProductType
	 */
	public static Map getIntentProductType() throws BOSException{ 
		Map mapIntentProductType = new HashMap();
		ProductTypeCollection thisColl = ProductTypeFactory.getRemoteInstance().getProductTypeCollection("select id,name");
		for(int i=0;i<thisColl.size();i++) {
			ProductTypeInfo thisInfo = thisColl.get(i);
			mapIntentProductType.put(thisInfo.getName().trim(),thisInfo);
		}

		return mapIntentProductType;
	}
	
	/**
	 * 产品描述  ProductDetialQuery   按租售项目查询
	 * @return  mapProductDeatil
	 */
	public static Map getProductDeatil(String sellProjectId) throws BOSException{ 
		Map mapProductDeatil = new HashMap();
		if(sellProjectId==null) return mapProductDeatil;

		ProductDetialCollection thisColl = ProductDetialFactory.getRemoteInstance().getProductDetialCollection("select id,name where sellProject.id='"+sellProjectId+"'");
		for(int i=0;i<thisColl.size();i++) {
			ProductDetialInfo thisInfo = thisColl.get(i);
			mapProductDeatil.put(thisInfo.getName().trim(),thisInfo);
		}

		return mapProductDeatil;
	}
	
	
	/**
	 * 意向朝向  HopedDirectionQuery
	 * @return  mapIntentDirection
	 */
	public static Map getIntentDirection() throws BOSException{ 
		Map mapIntentDirection = new HashMap();
		HopedDirectionCollection thisColl = HopedDirectionFactory.getRemoteInstance().getHopedDirectionCollection("select id,name");
		for(int i=0;i<thisColl.size();i++) {
			HopedDirectionInfo thisInfo = thisColl.get(i);
			mapIntentDirection.put(thisInfo.getName().trim(),thisInfo);
		}
		return mapIntentDirection;
	}
	
	
	/**
	 * 面积需求  AreaRequirementQuery
	 * @return  mapIntentArea
	 */
	public static Map getIntentArea() throws BOSException{ 
		Map mapIntentArea = new HashMap();
		AreaRequirementCollection thisColl = AreaRequirementFactory.getRemoteInstance().getAreaRequirementCollection("select id,name");
		for(int i=0;i<thisColl.size();i++) {
			AreaRequirementInfo thisInfo = thisColl.get(i);
			mapIntentArea.put(thisInfo.getName().trim(),thisInfo);
		}
		return mapIntentArea;
	}
	
	
	/**
	 * 景观需求  SightRequirementQuery
	 * @return  mapIntentSight
	 */
	public static Map getIntentSight() throws BOSException{ 
		Map mapIntentSight = new HashMap();
		SightRequirementCollection thisColl = SightRequirementFactory.getRemoteInstance().getSightRequirementCollection("select id,name");
		for(int i=0;i<thisColl.size();i++) {
			SightRequirementInfo thisInfo = thisColl.get(i);
			mapIntentSight.put(thisInfo.getName().trim(),thisInfo);
		}
		return mapIntentSight;
	}
	
	
	/**
	 * 房屋形式  RoomFormQuery
	 * @return  mapRoomForm
	 */
	public static Map getRoomForm() throws BOSException{ 
		Map mapRoomForm = new HashMap();
		RoomFormCollection thisColl = RoomFormFactory.getRemoteInstance().getRoomFormCollection("select id,name");
		for(int i=0;i<thisColl.size();i++) {
			RoomFormInfo thisInfo = thisColl.get(i);
			mapRoomForm.put(thisInfo.getName().trim(),thisInfo);
		}

		return mapRoomForm;
	}
	
	
	
	/**
	 * 户型需求  RoomModelTypeQuery
	 * @return  mapIntentRoomType
	 */
	public static Map getIntentRoomType() throws BOSException{ 
		Map mapIntentRoomType = new HashMap();
		RoomModelTypeCollection thisColl = RoomModelTypeFactory.getRemoteInstance().getRoomModelTypeCollection("select id,name");
		for(int i=0;i<thisColl.size();i++) {
			RoomModelTypeInfo thisInfo = thisColl.get(i);
			mapIntentRoomType.put(thisInfo.getName().trim(),thisInfo);
		}

		return mapIntentRoomType;
	}
	
	
	/**
	 * 意向单价  HopedUnitPriceQuery
	 * @return  mapHopedUnitPrice
	 */
	public static Map getHopedUnitPrice() throws BOSException{ 
		Map mapHopedUnitPrice = new HashMap();
		HopedUnitPriceCollection thisColl = HopedUnitPriceFactory.getRemoteInstance().getHopedUnitPriceCollection("select id,name");
		for(int i=0;i<thisColl.size();i++) {
			HopedUnitPriceInfo thisInfo = thisColl.get(i);
			mapHopedUnitPrice.put(thisInfo.getName().trim(),thisInfo);
		}

		return mapHopedUnitPrice;
	}
	
	
	/**
	 * 意向总价  HopedTotalPricesQuery
	 * @return  mapHopedTotalPrices
	 */
	public static Map getHopedTotalPrices() throws BOSException{ 
		Map mapHopedTotalPrices = new HashMap();
			HopedTotalPricesCollection thisColl = HopedTotalPricesFactory.getRemoteInstance().getHopedTotalPricesCollection("select id,name");
			for(int i=0;i<thisColl.size();i++) {
				HopedTotalPricesInfo thisInfo = thisColl.get(i);
				mapHopedTotalPrices.put(thisInfo.getName().trim(),thisInfo);
			}

		return mapHopedTotalPrices;
	}
	
	
	/**
	 * 意向楼层  HopedFloorQuery
	 * @return  mapHopedFloor
	 */
	public static Map getHopedFloor() throws BOSException{ 
		Map mapHopedFloor = new HashMap();
			HopedFloorCollection thisColl = HopedFloorFactory.getRemoteInstance().getHopedFloorCollection("select id,name");
			for(int i=0;i<thisColl.size();i++) {
				HopedFloorInfo thisInfo = thisColl.get(i);
				mapHopedFloor.put(thisInfo.getName().trim(),thisInfo);
			}
		return mapHopedFloor;
	}
	
	
	/**
	 * 商机原因  BuyHouseReasonQuery
	 * @return  mapBuyHouseReason
	 */
	public static Map getBuyHouseReason() throws BOSException{ 
		Map mapBuyHouseReason = new HashMap();
			BuyHouseReasonCollection thisColl = BuyHouseReasonFactory.getRemoteInstance().getBuyHouseReasonCollection("select id,name");
			for(int i=0;i<thisColl.size();i++) {
				BuyHouseReasonInfo thisInfo = thisColl.get(i);
				mapBuyHouseReason.put(thisInfo.getName().trim(),thisInfo);
			}
		return mapBuyHouseReason;
	}
	
	
	/**
	 * 意向强度  HopePitchQuery
	 * @return  mapHopedPitch
	 */
	public static Map getHopePitch() throws BOSException{ 
		Map mapHopedPitch = new HashMap();
			HopePitchCollection thisColl = HopePitchFactory.getRemoteInstance().getHopePitchCollection("select id,name");
			for(int i=0;i<thisColl.size();i++) {
				HopePitchInfo thisInfo = thisColl.get(i);
				mapHopedPitch.put(thisInfo.getName().trim(),thisInfo);
			}
		return mapHopedPitch;
	}
	
	
	/**
	 * 置业目的	CommerceIntentionEnum
	 * @return mapCommerceIntention
	 */
	public static Map getCommerceIntentionMap() {
		Map mapCommerceIntention = new HashMap();
		List enumlist = CommerceIntentionEnum.getEnumList();
		for(int j=0;j<enumlist.size();j++) {
			CommerceIntentionEnum thisEnum = (CommerceIntentionEnum)enumlist.get(j);
			mapCommerceIntention.put(thisEnum.getAlias().trim(),thisEnum);
		}
		return mapCommerceIntention;
	}
	
	
	/**
	 * 首付比例  FirstPayProportionQuery
	 * @return  mapFirstPayProportion
	 */
	public static Map getFirstPayProportion() throws BOSException{ 
		Map mapFirstPayProportion = new HashMap();
			FirstPayProportionCollection thisColl = FirstPayProportionFactory.getRemoteInstance().getFirstPayProportionCollection("select id,name");
			for(int i=0;i<thisColl.size();i++) {
				FirstPayProportionInfo thisInfo = thisColl.get(i);
				mapFirstPayProportion.put(thisInfo.getName().trim(),thisInfo);
			}
		return mapFirstPayProportion;
	}
	
	
	
	
	
	
	/**
	 * @deprecated	有异意  暂时不用
	 * 业务系统商机联用 接口   单据删除时,后台自动先删除单据对应的跟进记录.
	 * @param trackEnum   单据类型 - 对应的跟踪类型
	 * @param idStr		  单据的id
	 * @throws BOSException 
	 * @throws EASBizException     *  
	 */
	public static void deleteTrackRecord(TrackRecordEnum trackEnum,String idStr) throws EASBizException, BOSException  {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("trackResult",trackEnum));
		filter.getFilterItems().add(new FilterItemInfo("relationContract",idStr));   
		if(TrackRecordFactory.getRemoteInstance().exists(filter))
			TrackRecordFactory.getRemoteInstance().delete(filter);
	}
	
	
	/**
	 * 业务系统商机联用  接口  单据保存时增加对应的跟踪记录
	 1)	单据在新增保存后,弹出提示保存成功后,进行跟进分析和跟进操作
	 2)	先分析单据的客户数量,售楼只能有一个客户,租赁多个,循环分析每个客户在单据的销售顾问下有没有对应的商机,没有不用跟进商机.
	 3)	将查找到的商机在界面中展示.
	 4)	选择一个商机点添加跟进,弹出模式跟进编辑界面
	 5)	界面自动填写上对应商机,再根据单据类型填写跟进成果,关联单据.
	 6)	保存跟进后可以再选择其他其他商机再跟进.
	 7)	跟进结束后关闭商机选择界面即可
	 * @param owner				当前的窗体对象
	 * @param fdcCustomerList	房地产客户集合
	 * @param saleMan			营销人员
	 * @param trackEnum			单据类型 - 对应的跟踪类型	 可为空
	 * @param billNumberStr		单据的编号			//可为空,如果不空则要锁定跟踪记录编辑UI中的关联单据的控件
	 * @param billIdStr			单据的id					 可为空
	 */
	public static void addCommerceTrackRecord(Object owner,List fdcCustomerList,UserInfo saleMan,TrackRecordEnum trackEnum,String billNumberStr,String billIdStr) {
		if(fdcCustomerList==null || fdcCustomerList.size()==0) return;
		if(saleMan==null) return;    	
		
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection selectorColl = new SelectorItemCollection();
		selectorColl.add(new SelectorItemInfo("*"));
		selectorColl.add(new SelectorItemInfo("sellProject.name"));
		selectorColl.add(new SelectorItemInfo("commerceLevel.name"));
		selectorColl.add(new SelectorItemInfo("fdcCustomer.name"));
		selectorColl.add(new SelectorItemInfo("fdcCustomer.number"));
		selectorColl.add(new SelectorItemInfo("hopedBuildingProperty.name"));
		selectorColl.add(new SelectorItemInfo("hopedRoomModelType.name"));
		selectorColl.add(new SelectorItemInfo("hopedSightRequirement.name"));
		selectorColl.add(new SelectorItemInfo("hopedDirection.name"));
		selectorColl.add(new SelectorItemInfo("hopedAreaRequirement.name"));
		selectorColl.add(new SelectorItemInfo("hopedProductType.name"));    	
		view.setSelector(selectorColl);
		FilterInfo filter = new FilterInfo();
		StringBuffer buff = new StringBuffer();
		//  "#0 and (#1 or #2 or #3) and #4 "
		filter.getFilterItems().add(new FilterItemInfo("saleMan.id",saleMan.getId().toString()));
		buff.append("#0");
		int addNum = 1;
		for(int i=0;i<fdcCustomerList.size();i++) {
			FDCCustomerInfo customer = (FDCCustomerInfo)fdcCustomerList.get(i);
			filter.getFilterItems().add(new FilterItemInfo("fdcCustomer.id",customer.getId().toString()));  
			if(i==0)  buff.append(" and (");
			if(i>0) buff.append(" or ");
			buff.append("#"+(i+1));    		
			if(i==fdcCustomerList.size()-1) buff.append(")");
			addNum++;
		}    	    	
		if(trackEnum!=null){
			if(trackEnum.equals(TrackRecordEnum.TenancyApp))
				filter.getFilterItems().add(new FilterItemInfo("sysType",MoneySysTypeEnum.TenancySys));
			else
				filter.getFilterItems().add(new FilterItemInfo("sysType",MoneySysTypeEnum.SalehouseSys));
			buff.append("and #"+addNum);
		}    	
		filter.setMaskString(buff.toString());
		view.setFilter(filter);
		CommerceChanceCollection comColl = null;
		try {
			comColl = CommerceChanceFactory.getRemoteInstance().getCommerceChanceCollection(view);
		} catch (BOSException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		if(comColl==null || comColl.size()==0) return;
		
		//打开商机选择界面
		UIContext uiContext = new UIContext(owner); 
		uiContext.put("CommerceChanceCollection", comColl); 
		uiContext.put("TrackRecordEnum", trackEnum);
		uiContext.put("BillNumberString", billNumberStr);
		uiContext.put("BillIdString", billIdStr);
		try {
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(CommerceSelectUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			uiWindow.show();
		} catch (UIException e) {
			e.printStackTrace();
		}
	}
	
	public static void addCommerceTrackRecord(Object owner,FDCCustomerInfo fdcCustomer,UserInfo saleMan,TrackRecordEnum trackEnum,String billNumberStr,String idStr) {
		if(fdcCustomer!=null)  {
			List list = new ArrayList();
			list.add(fdcCustomer);
			addCommerceTrackRecord(owner,list,saleMan,trackEnum,billNumberStr,idStr);
		}    	
	}
	
	
	
	/**
	 * 根据传入的列的key和名称,返回所需要的表   
	 * 其中,若key以R结尾则靠右;R2右靠齐且精度到2;C居中;D时间格式
	 * @param keyNames 数组  ,key 和 name 以,分隔
	 * @return
	 */
	public static KDTable getTheTableByKeyNames(String[] keyNames)	{
		KDTable table = new KDTable();
		table.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		table.getStyleAttributes().setLocked(true);
		
		if(keyNames!=null)  {
			IRow headRow = table.addHeadRow();
			for(int i=0;i<keyNames.length;i++) {
				String[] keyName = keyNames[i].split(",");
				String key = keyName[0];
				String name = keyName[1];
				IColumn column = table.addColumn();
				column.setKey(key);
				if(key.endsWith("R"))  //默认都左靠齐
					column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
				else if(key.endsWith("R2")) {   //右靠齐且精度到2
					column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
					/***
					 * 旧5.4版本的设置数字格式r-[ ]0.2n的方法，已废弃，Excel 2007 不支持 周勇
					 */
					column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
//					column.getStyleAttributes().setNumberFormat("%r-[ ]0.2n");
				}else if(key.endsWith("C"))
					column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
				else if(key.endsWith("D")){     //时间格式列
					column.getStyleAttributes().setNumberFormat("yyyy-MM-dd");
				}else if(key.equals("id")){
					column.getStyleAttributes().setHided(true);
				}					
				headRow.getCell(key).setValue(name);
			}			
			
		}		
		
		/*  添加监听事件
		 bizTable.addKDTMouseListener(new KDTMouseListener()		{
		 public void tableClicked(KDTMouseEvent e)	{
		 try	{
		 if (e.getButton() == 1 && e.getClickCount() == 2)	{
		 int rowIndex = e.getRowIndex();
		 if (rowIndex == -1) 	{
		 return;
		 }
		 IRow row = bizTable.getRow(rowIndex);
		 Object bizObj = row.getUserObject();
		 if(bizObj!=null && bizObj instanceof CoreBaseInfo) {
		 CommerceHelper.openTheUIWindow(this,TenancyBillEditUI.class.getName(),((CoreBaseInfo)bizObj).getId().toString());
		 }						
		 }
		 } catch (Exception exc)		{
		 handUIException(exc);
		 } finally{
		 }
		 }
		 });
		 */	
		return table;
	}
	
	
	
	public static KDTable fillTheTableByKeyObjects(KDTable table,Map keyObjects)	{
		if(keyObjects!=null)  {
			IRow row = table.addRow();
			Set set = keyObjects.keySet();
			Iterator itrt = set.iterator();
			while(itrt.hasNext()) {
				String key = (String)itrt.next();
				if(key!=null) {		
					row.getCell(key).setValue(keyObjects.get(key));
				}
			}		
		}		
		return table;
	}
	
	
	
	
	
	/**
	 * 把excel中的数据导入到table中   默认表头就一行  (判断日期、数字、何字符串)
	 * @param fileName
	 * @param table
	 * @return
	 * @throws Exception
	 */
	public static int importExcelToTable(String fileName, KDTable table) throws Exception {
		KDSBook kdsbook = null;
		try {

			kdsbook = POIXlsReader.parse2(fileName);
		} catch (Exception e) {
			e.printStackTrace();
			MsgBox.showError("读EXCEL出错,EXCEL格式不匹配!");
			SysUtil.abort();
		}
		if (kdsbook == null) {
			SysUtil.abort();
		}
		//只读区excel中sheet0的数据
		Sheet excelSheet = KDSBookToBook.traslate(kdsbook).getSheet(0);
		Map e_colNameMap = new HashMap();     //excel中的列名
		int e_maxRow = excelSheet.getMaxRowIndex();   
		int e_maxColumn = excelSheet.getMaxColIndex();
		for (int col = 0; col <= e_maxColumn; col++) {
			String excelColName = excelSheet.getCell(0, col, true).getText();
			e_colNameMap.put(excelColName, new Integer(col));
		}
		
		for (int i = 0; i < table.getColumnCount(); i++) {    //检查table中的字段是否都在excel中存在
			if (table.getColumn(i).getStyleAttributes().isHided()) {
				continue;
			}
			String colName = (String) table.getHeadRow(0).getCell(i).getValue();
			Integer colInt = (Integer) e_colNameMap.get(colName);
			if (colInt == null) {									
				MsgBox.showError("表头结构不一致!表格上的关键列:" + colName	+ "在EXCEL中没有出现!");
				SysUtil.abort();
			}
		}
		
		
		int successCount = 0;
		for (int rowIndex = 1; rowIndex <= e_maxRow; rowIndex++) {
			IRow row = table.addRow();
			int successColoum=0;//去掉前后的空行
			//successCount++;
			for (int i = 0; i < table.getColumnCount(); i++) {
				if (table.getColumn(i).getStyleAttributes().isHided()) {
					continue;
				}
				
				ICell tblCell = row.getCell(i);
				String colName = (String) table.getHeadRow(0).getCell(i).getValue();
				Integer colInt = (Integer) e_colNameMap.get(colName);
				if (colInt == null) {
					continue;
				}
				Variant cellRawVal = excelSheet.getCell(rowIndex,colInt.intValue(), true).getValue();
				if (Variant.isNull(cellRawVal)) {
					continue;
				}
				successColoum++;
				if(cellRawVal.isDate()) {
					tblCell.setValue(cellRawVal.toDate());
				}else if (cellRawVal.isNumeric()) {
					BigDecimal colValue = cellRawVal.toBigDecimal();
					if(cellRawVal.toString().trim().equals(colValue.toString()))
						tblCell.setValue(colValue.toString().trim());
					else		//解决一0开都的字符串被认为是数字型的问题
						tblCell.setValue(cellRawVal.toString().trim());
				} else if (cellRawVal.isString()) {					
					String colValue = cellRawVal.toString().trim();
					if(!colValue.equals("")) tblCell.setValue(colValue);
				}
				
			}
			if(successColoum==0){//前后空行，去掉
				table.removeRow(row.getRowIndex());
			}else{
				successCount ++;
			}
		}
		return successCount;
		
	}
	
	
	/**
	 * list对象转换为字节数组
	 * @param plist
	 * @return
	 */
	public static  byte[] ListObjectToByteArray(List plist) {
		byte[] bytes = new byte[0];
		if(plist!=null)  {		
			ByteOutputStream stream = new ByteOutputStream();
			try {
				ObjectOutputStream s = new ObjectOutputStream(stream);
				s.writeObject(plist);
				bytes = stream.toByteArray();
			} catch (IOException e) {
				e.printStackTrace();
				SysUtil.abort();
			}
		}
		return bytes;
	}
	
	/**
	 * 字节数组转换为list对象
	 * @param bytes
	 * @return
	 */
	public static List ByteArrayToListObject(byte[] bytes) {		
		List plist = new ArrayList();
		if(bytes!=null && bytes.length>0) {
			ByteInputStream stream = new ByteInputStream(bytes,bytes.length);
			try {
				ObjectInputStream s = new ObjectInputStream(stream);
				plist = (List)s.readObject();
			} catch (Exception e) {
				e.printStackTrace();
				SysUtil.abort();
			}
		}
		return plist;
	}
	
	
	
	/**
	 * 克隆所选树节点，包含子节点
	 */
	public static void cloneTree(DefaultKingdeeTreeNode newNode,	DefaultKingdeeTreeNode oriNode) {
		for (int i = 0; i < oriNode.getChildCount(); i++) {
			DefaultKingdeeTreeNode oriChild = (DefaultKingdeeTreeNode) oriNode.getChildAt(i);
			DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode) oriChild.clone();
			newNode.add(child);
			cloneTree(child, oriChild);
		}
	}
	
	/**
	 * 移除节点中的平面图节点　
	 */
	public static void removePlanisphereNode(DefaultKingdeeTreeNode treeNode){
		for(int i=treeNode.getChildCount()-1;i>=0;i--) {
			DefaultKingdeeTreeNode childNode = (DefaultKingdeeTreeNode)treeNode.getChildAt(i);
			if(!childNode.isLeaf()) {
				removePlanisphereNode(childNode);
			}else{
				if(childNode.getUserObject() instanceof PlanisphereInfo) {
					childNode.removeFromParent();
				}
			}
		}
	}
	
	
	public static void addSqlMenu(final CoreUIObject uiObject, KDMenu menu) {		
		menu.add(new AbstractHidedMenuItem("ctrl shift F12") {
			public void action_actionPerformed() {
				//添加启动参数
				String p = System.getProperty("DevFDC");
				if(p==null||!p.equals("fdc")){
					//密码认证 by sxhong
					KDPasswordField pwd = new KDPasswordField();

					Object[] message = {"输入密码:", pwd};

					int res = JOptionPane.showConfirmDialog(uiObject, message, "请输入密码", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					char cs[]=pwd.getPassword();
					if(res!=JOptionPane.OK_OPTION||cs==null||cs.length<2
							||cs[cs.length-2]!=':'||cs[cs.length-1]!=')'){
						return;
					}
				}
				try {
					IUIFactory fy = UIFactory
							.createUIFactory(UIFactoryName.NEWTAB);
					UIContext uiContext = new UIContext(uiObject);
					IUIWindow wnd = fy.create(
							"com.kingdee.eas.fm.common.client.FMIsqlUI",
							uiContext);
					wnd.show();
				} catch (UIException e) {
					SysUtil.abort(e);
				}
			}

		});
		if(menu.getMenuComponent(menu.getMenuComponentCount()-1) instanceof AbstractHidedMenuItem){
			menu.getMenuComponent(menu.getMenuComponentCount()-1).setVisible(false);
		}
	}	
	
	
	
	
	
	public static Map convertFilterItemCollToMap(FilterInfo filter) {
		Map filterMap = new HashMap();
		if(filter==null) return filterMap;
		FilterItemCollection filterColl = filter.getFilterItems();
		for(int i=0;i<filterColl.size();i++) {
			FilterItemInfo fInfo = filterColl.get(i);
			filterMap.put(fInfo.getPropertyName(), fInfo.getCompareValue());
		}
		return filterMap;
	}
	
	
	/**
	 * 获得某个实体的所有属性
	 * @param objectInfo
	 * @return
	 */
	public static PropertyCollection getAllPropertysOfEntity(IObjectValue objectInfo){
		//AssistantHGInfo assistantHGInfo = new AssistantHGInfo();
		IMetaDataLoader loader = MetaDataLoaderFactory.getRemoteMetaDataLoader();
        EntityObjectInfo assisttantHGEntityObjectInfo = loader.getEntity(objectInfo.getBOSType());
        PropertyCollection assitantHGProperties = assisttantHGEntityObjectInfo.getInheritedNoDuplicatedProperties();
        return assitantHGProperties;
	}
	
	
	
	
	
	
	
	/**
	 * 根据款项类型查询出款项科目对照表
	 * 对照表中包含科目及是否允许被修改的属性
	 * */
	public static MoneySubjectContrastInfo getContractTableByMoneyDefine(MoneyDefineInfo moneyDefine) throws EASBizException, BOSException {
		if(moneyDefine==null) return null;
		CompanyOrgUnitInfo company = SysContext.getSysContext().getCurrentFIUnit();
		String kSql = "select accountView.*,isChanged where moneyDefine.id='"+moneyDefine.getId().toString()+"'";
		if(company != null){
			kSql += " and fullOrgUnit.id='"+ company.getId().toString() +"'";
		}else{
			kSql += " and fullOrgUnit.id='idnull'";
		}
		MoneySubjectContrastCollection monContractInfoColl = MoneySubjectContrastFactory.getRemoteInstance().getMoneySubjectContrastCollection(kSql);
		if(monContractInfoColl.size()>0) {
			return monContractInfoColl.get(0);
		}else{
			return null;
		}
		
	}

	
	//互斥
	public static void releaseDataObjectLock(String id) {
		IDataObjectMutex mutextControl = new DataObjectMutex();
		mutextControl.releaseDataObjectLock(id);
	}

	public static void requestDataObjectLock(String id) {
		try {
			IDataObjectMutex mutextControl = new DataObjectMutex();
			mutextControl.requestDataObjectLock(id);
		} catch (Throwable e) {
			MsgBox.showError("对不起,当前对象已被其他操作锁定!");
			SysUtil.abort();
		}

	}
	
	
	/***通用F7对话框
		KDCommonPromptDialog dlg = new KDCommonPromptDialog();		
		IMetaDataLoader loader = MetaDataLoaderFactory.getRemoteMetaDataLoader();
		dlg.setQueryInfo(loader.getQuery(new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery")));
		dlg.setEnabledMultiSelection(true);
		dlg.setEntityViewInfo(null);
		dlg.show();
		Object[] object = (Object[])dlg.getData();
	 */
	public static KDCommonPromptDialog getANewCommonDialog(IUIObject owner,String queryName,boolean enabledMultiSelect,EntityViewInfo view){
		KDCommonPromptDialog dlg = null;	
		if(owner instanceof ListUI){
			dlg=new KDCommonPromptDialog((Frame)SwingUtilities.getWindowAncestor((Component)owner));	
		}else{
			dlg=new KDCommonPromptDialog((UIModelDialog)SwingUtilities.getWindowAncestor((Component)owner));	
		}
		IMetaDataLoader loader = MetaDataLoaderFactory.getRemoteMetaDataLoader();
		dlg.setQueryInfo(loader.getQuery(new MetaDataPK(queryName)));
		dlg.setEnabledMultiSelection(enabledMultiSelect);
		dlg.setEntityViewInfo(view);
		return dlg;
	}
	
	
	
/**
 * 选择文件的 f7
 * new FileChooserPromptBox(this, FileChooserPromptBox.CHOOSERTYPE_OPEN);
 */
	
	//只有单据的创建组织才能修改和删除
	public static void checkBeforEditOrDelete(ObjectBaseInfo objInfo) {
		if(objInfo.getId()==null) return;
		//要确保对象中有orgUnit属性哦
		FullOrgUnitInfo orgUnitInfo = (FullOrgUnitInfo)objInfo.get("orgUnit");
		if(orgUnitInfo==null) return;
		
		if(!SysContext.getSysContext().getCurrentOrgUnit().getId().equals(orgUnitInfo.getId())){
			MsgBox.showWarning("当前组织非单据的创建组织，不能操作！");
			SysUtil.abort();
		}
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*String aaa = null;
		String bbb = null;
		
		System.out.println(aaa.equals(bbb));
		
		
		BigDecimal aa = new BigDecimal("0");
		BigDecimal bb = new BigDecimal("0.00");
		
		System.out.println(aa == bb);
		System.out.println(aa.equals(bb));
		System.out.println(aa.compareTo(bb));
		*/
		
/*		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("aaaa","11111"));
		filter.getFilterItems().add(new FilterItemInfo("bbbb","22222"));
		
		FilterItemCollection filterColl = filter.getFilterItems();
		for(int i=0;i<filterColl.size();i++) {
			FilterItemInfo thisInfo = filterColl.get(i);
			System.out.println(thisInfo.getPropertyName());
			System.out.println(thisInfo.getCompareValue());
//			if(thisInfo.containsKey("aaaa")) {
//				System.out.println("exit thiskey");
//			}
			
		}*/

		
		
	}
	
	
	/**
	 * 房间辅助资料过滤-视野 
	 * @author tim_gao
	 * 
	 */
	
	public static EntityViewInfo getNewEysSightView(){
		//视野 
		EntityViewInfo viewEysSight = new EntityViewInfo();
		FilterInfo filterEysSight = new FilterInfo();
		filterEysSight.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
	 	filterEysSight.getFilterItems().add(new FilterItemInfo("type.id","pj3Lru9+QVqK/PZoIaNxYLyNrUg="));
		viewEysSight.setFilter(filterEysSight);
		return viewEysSight;
	}
	
	/**
	 * 房间辅助资料过滤-景观
	 * @author tim_gao
	 * 
	 */
	
	public static EntityViewInfo getNewSightView(){
		
    	//景观
    	EntityViewInfo viewSight = new EntityViewInfo();
    	FilterInfo filterSight = new FilterInfo();
    	filterSight.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
    	filterSight.getFilterItems().add(new FilterItemInfo("type.id","aDya2NbITr+ymXXU7/kCW7yNrUg="));
    	viewSight.setFilter(filterSight);
       return viewSight;
	}
	
	/**
	 * 房间辅助资料过滤-噪音
	 * @author tim_gao
	 * 
	 */
	
	public static EntityViewInfo getNewNoiseView(){
		
		//噪音
		EntityViewInfo viewNose = new EntityViewInfo();
		FilterInfo filterNose = new FilterInfo();
		filterNose.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		filterNose.getFilterItems().add(new FilterItemInfo("type.id","Cr7p6ri/QWWy+vdLTV1erLyNrUg="));
		viewNose.setFilter(filterNose);
	
       return viewNose;
	}
	
	
	/**
	 * 房间辅助资料过滤-装修标准
	 * @author tim_gao
	 * 
	 */
	
	public static EntityViewInfo getDecoraStdView(){
		
		//装修标准
		EntityViewInfo viewDecoraStd = new EntityViewInfo();
		FilterInfo filterDecoraStd = new FilterInfo();
		filterDecoraStd.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		filterDecoraStd.getFilterItems().add(new FilterItemInfo("type.id","YPBFahQ6TY+RUDdxyiElfryNrUg="));
		viewDecoraStd.setFilter(filterDecoraStd);
	
       return viewDecoraStd;
	}
 	
	
	/**
	 * 房间辅助资料过滤-交房标准
	 * @author tim_gao
	 * 
	 */
	
	public static EntityViewInfo getPossStdView(){
		
		//交房标准
		EntityViewInfo viewPossStd = new EntityViewInfo();
		FilterInfo filterPossStd  = new FilterInfo();
		filterPossStd .getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		filterPossStd.getFilterItems().add(new FilterItemInfo("type.id","ByHexvLVSEusji3qeetJHLyNrUg="));
		viewPossStd .setFilter(filterPossStd );
       return viewPossStd;
	}

	
	/**
	 * 房间辅助资料过滤-产品描述
	 * @author tim_gao
	 * 
	 */
	
	public static EntityViewInfo getProductDetailView(){
		
//	 	//产品描述
	  	EntityViewInfo viewProductDetail = new EntityViewInfo();
		FilterInfo filterProductDetail  = new FilterInfo();
		filterProductDetail .getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		filterProductDetail.getFilterItems().add(new FilterItemInfo("type.id","/uTPJ0sVSbOC8OFIdM+Nx7yNrUg="));
		viewProductDetail .setFilter(filterProductDetail );
       return viewProductDetail;
	}
	/**
	 * 房间辅助资料过滤-房间用途
	 * @author tim_gao
	 * 
	 */
	
	public static EntityViewInfo getRoomUsageView(){
		
	 	//房间用途
		EntityViewInfo viewRoomUsage = new EntityViewInfo();
		FilterInfo filterRoomUsage = new FilterInfo();
		filterRoomUsage.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		filterRoomUsage.getFilterItems().add(new FilterItemInfo("type.id","zzYdGSPQSuWiAJXK3JKjcbyNrUg="));
		viewRoomUsage.setFilter(filterRoomUsage);
		return viewRoomUsage;
	}

}
