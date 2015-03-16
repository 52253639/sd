/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BizDateToEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.ChooseRoomSet;
import com.kingdee.eas.fdc.sellhouse.ProjectSet;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.SHEParamConstant;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.ObjectUtils;

/**
 * output class name
 */
public class SHEFunctionSetUI extends AbstractSHEFunctionSetUI implements SHEParamConstant{
	
	private static final Logger logger = CoreUIObject.getLogger(SHEFunctionSetUI.class);

	RoomDisplaySetting set = new RoomDisplaySetting(new String[]{PROJECT_PRODUCTTYPE_SET_MAP, PROJECT_SET_MAP, CHOOSE_ROOM_SET_MAP});

	/*
	 * RoomDisplaySetting按照原实现增加增加3个Map的成员变量：ProjectProductTypeSetMap，ProjectSetMap
	 * ，ChooseRoomSetMap。结构分别如下 ProjectProductTypeSetMap<项目ID，Map<参数名称，参数值> > ProjectSetMap<项目ID，Map<参数名称，参数值>>
	 * ChooseRoomSetMap<楼栋ID，Map<参数名称，参数值>>
	 */
	
	

	public SHEFunctionSetUI() throws Exception {
		super();
	}

	//混账逻辑混账处理..
	private Set cache = new HashSet();
	
	public void onLoad() throws Exception {
		FDCSysContext.getInstance().checkIsSHEOrg(this);
		
		//缓存项目和产品类型 
		ProductTypeCollection allProTypes = ProductTypeFactory.getRemoteInstance().getProductTypeCollection();
		Map allProTypeCache = new HashMap();
		for(int i=0; i<allProTypes.size(); i++){
			ProductTypeInfo proType = allProTypes.get(i);
			allProTypeCache.put(proType.getId().toString(), proType);
		}
		
		Map allSellProjectCache = new HashMap();
	
		super.onLoad();
		
		

		//加载到界面上
		Map proProSetMap = set.getProjectProductTypeSetMap();
		Map proSetMap = set.getProjectSetMap();
		Map chooseRoomSetMap = set.getChooseRoomSetMap();
		
		Set keySet = proProSetMap.keySet();
		
		List projectIdList  = new ArrayList();
		
		for(Iterator itor = keySet.iterator(); itor.hasNext(); ){
			String sellProId = (String) itor.next();
			if(sellProId!=null){
				projectIdList.add(sellProId);
			}
		}
		
		StringBuffer projectSb = new StringBuffer();
		for (int i = 0; i < projectIdList.size(); i++) {
			projectSb.append("'");
			projectSb.append(projectIdList.get(i).toString());
			projectSb.append("'");
			if(i!=projectIdList.size()-1){
				projectSb.append(",");
			}
			
		}
		
		initTables(projectSb.toString());
		
		if(projectSb.length()>0){
			allSellProjectCache.clear();
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection coll = new SelectorItemCollection();
			coll.add(new SelectorItemInfo("id"));
			coll.add(new SelectorItemInfo("name"));
			coll.add(new SelectorItemInfo("number"));
			coll.add(new SelectorItemInfo("level"));
			coll.add(new SelectorItemInfo("isLeaf"));
			FilterInfo filter  = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",projectSb.toString(),CompareType.INNER));
			FullOrgUnitInfo  orgUnit = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
			if(orgUnit!=null && !orgUnit.getId().toString().equals(OrgConstants.DEF_CU_ID.toString())){
				filter.getFilterItems().add(
						new FilterItemInfo("orgUnit.id", orgUnit.getId().toString(),
								CompareType.EQUALS));
			}
			filter.getFilterItems().add(new FilterItemInfo("id",projectSb.toString(),CompareType.INNER));
			view.setFilter(filter);
			view.setSelector(coll);
			SellProjectCollection sellColl = SellProjectFactory.getRemoteInstance().getSellProjectCollection(view);
			for (int i = 0; i < sellColl.size(); i++) {
				SellProjectInfo info =  sellColl.get(i);
				if(info!=null){
					allSellProjectCache.put(info.getId().toString(), info);
				}
			}
		}
		for(Iterator itor = keySet.iterator(); itor.hasNext(); ){
			String sellProId = (String) itor.next();
			
			SellProjectInfo project = (SellProjectInfo) allSellProjectCache.get(sellProId);
			if(project==null){
				continue;
			}
			if(!project.isIsLeaf()){
				continue;
			}else{
				cache.add(sellProId);
			}
			
			List list = null;
			if(!project.isIsLeaf()){
				continue;
			}else{
				list = (List) proProSetMap.get(sellProId);	
			}
			
			for (int i = 0; i < list.size(); i++) {
				
				
				Map inMap = (Map) list.get(i);

				SellProjectInfo sellProject = (SellProjectInfo) allSellProjectCache.get(sellProId);
				if(project==null){
					continue;
				}
				if(!sellProject.isIsLeaf()){
					continue;
				}
				
				IRow row = this.tblProSetWithProductType.addRow();
				
				row.getCell(T1_COL_PROEJCT_NAME).setValue(allSellProjectCache.get(sellProId));

				String proTypeIdsDes = (String) inMap.get(T1_COL_PRODUCT_TYPE);
				if (proTypeIdsDes != null && proTypeIdsDes.length() != 0) {
					String[] proTypeIds = proTypeIdsDes.split(SPIT_STR);
					int proTypeLength = proTypeIds.length;
					Object[] pros = new Object[proTypeLength];
					for (int j = 0; j < proTypeLength; j++) {
						pros[j] = allProTypeCache.get(proTypeIds[j]);
					}
					row.getCell(T1_COL_PRODUCT_TYPE).setValue(pros);
				}

				row.getCell(T1_KEEP_DOWN_LIMIT_TIME).setValue(inMap.get(T1_KEEP_DOWN_LIMIT_TIME));
				row.getCell(T1_PRE_PURCHASE_LIMIT_TIME).setValue(inMap.get(T1_PRE_PURCHASE_LIMIT_TIME));
				row.getCell(T1_PURCHASE_LIMIT_TIME).setValue(inMap.get(T1_PURCHASE_LIMIT_TIME));
				row.getCell(T1_TO_SIGN_LIMIT_TIME).setValue(inMap.get(T1_TO_SIGN_LIMIT_TIME));
				row.getCell(T1_SIGN_LIMIT_TIME).setValue(inMap.get(T1_SIGN_LIMIT_TIME));

				row.getCell(T1_SIN_PURCHASE_STANDARD).setValue(inMap.get(T1_SIN_PURCHASE_STANDARD));
				row.getCell(T1_PRE_PURCHASE_STANDARD).setValue(inMap.get(T1_PRE_PURCHASE_STANDARD));
				row.getCell(T1_PRE_STANDARD).setValue(inMap.get(T1_PRE_STANDARD));
			}
		}
		
		Map tmpProSetMap = new HashMap();

		for (int i = 0; i < projectIdList.size(); i++) {
			String projectId = projectIdList.get(i).toString();
			if(proSetMap.get(projectId) == null){
				tmpProSetMap.put(projectId, new ProjectSet().getValues());
			}else{
				tmpProSetMap.put(projectId, proSetMap.get(projectId));
			}
		}
		
		
		keySet = tmpProSetMap.keySet();
		for(Iterator itor = keySet.iterator(); itor.hasNext(); ){
			
			
			String sellProId = (String) itor.next();
			
			if(allSellProjectCache.get(sellProId)==null){
				continue;
			}
			SellProjectInfo sellProject = (SellProjectInfo) allSellProjectCache.get(sellProId);
			if(sellProject==null){
				continue;
			}
			if(!sellProject.isIsLeaf()){
				continue;
			}
			
			IRow row = this.tblProSetWithoutProductType.addRow();
			
			row.getCell(T2_PROJECT).setValue(allSellProjectCache.get(sellProId));
			
			Map inMap = (Map) tmpProSetMap.get(sellProId);
			
			row.getCell(T2_IS_DEAL_AMOUNT_EDIT).setValue(inMap.get(T2_IS_DEAL_AMOUNT_EDIT));
			row.getCell(T2_IS_ENABLE_AGIO).setValue(inMap.get(T2_IS_ENABLE_AGIO));
			row.getCell(T2_IS_ENABLE_ADJUST_AGIO).setValue(inMap.get(T2_IS_ENABLE_ADJUST_AGIO));
			row.getCell(T2_IS_FORCE_LIMIT_PRICE).setValue(inMap.get(T2_IS_FORCE_LIMIT_PRICE));
			row.getCell(T2_IS_MUST_BY_BANK).setValue(inMap.get(T2_IS_MUST_BY_BANK));
			
			row.getCell(T2_IS_DEAL_AMT_BY_STAND_AMT).setValue(inMap.get(T2_IS_DEAL_AMT_BY_STAND_AMT));
			row.getCell(T2_DEAL_AMOUNT_CAN_EDIT).setValue(inMap.get(T2_DEAL_AMOUNT_CAN_EDIT));
			row.getCell(T2_IS_RE_PRICE_OF_QUIT_ROOM).setValue(inMap.get(T2_IS_RE_PRICE_OF_QUIT_ROOM));
			row.getCell(T2_IS_RE_PRICE_OF_CHANGE_ROOM).setValue(inMap.get(T2_IS_RE_PRICE_OF_CHANGE_ROOM));
			row.getCell(T2_IS_AREA_CHANGE_NEED_AUDIT).setValue(inMap.get(T2_IS_AREA_CHANGE_NEED_AUDIT));
			
			row.getCell(T2_IS_BIZ_DATE_EDITABLE).setValue(inMap.get(T2_IS_BIZ_DATE_EDITABLE));
			row.getCell(T2_CHANGE_ROOM_DEF_BIZ_DATE).setValue(inMap.get(T2_CHANGE_ROOM_DEF_BIZ_DATE));
			row.getCell(T2_PRICE_ADJUST_DEF_BIZ_DATE).setValue(inMap.get(T2_PRICE_ADJUST_DEF_BIZ_DATE));
			row.getCell(T2_CHANGE_CUS_DEF_BIZ_DATE).setValue(inMap.get(T2_CHANGE_CUS_DEF_BIZ_DATE));
			row.getCell(T2_IS_NO_SELL_CAN_SIN).setValue(inMap.get(T2_IS_NO_SELL_CAN_SIN));
			
			row.getCell(T2_IS_ENABLE_TRADE).setValue(inMap.get(T2_IS_ENABLE_TRADE));
			row.getCell(T2_CUS_TRADE_DAYS).setValue(inMap.get(T2_CUS_TRADE_DAYS));
			row.getCell(T2_CHANCE_DAYS).setValue(inMap.get(T2_CHANCE_DAYS));
			row.getCell(T2_COMMERCECHANCE_DAYS).setValue(inMap.get(T2_COMMERCECHANCE_DAYS));
		}
		
		BuildingCollection permitBuildings = new BuildingCollection();
		
		if(!allSellProjectCache.isEmpty()){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", projectSb.toString(), CompareType.INNER));
			view.setFilter(filter);
			
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("*");
			sels.add("sellProject.*");
			view.setSelector(sels);
			
			SorterItemCollection sors = new SorterItemCollection();
			sors.add(new SorterItemInfo("sellProject.id"));
			view.setSorter(sors);
			
			permitBuildings = BuildingFactory.getRemoteInstance().getBuildingCollection(view);
		}
		
		Map buildCache = new HashMap();
		
		Map tmpChooseRoomSetMap = new LinkedHashMap();
		for(int i=0; i<permitBuildings.size(); i++){
			BuildingInfo build = permitBuildings.get(i);
			String buildid = build.getId().toString();
			if(chooseRoomSetMap.get(buildid) == null){
				ChooseRoomSet chs = new ChooseRoomSet();
				chs.setString(T3_PROJECT, build.getSellProject().getId().toString());
				tmpChooseRoomSetMap.put(build.getId().toString(), chs.getValues());
			}else{
				tmpChooseRoomSetMap.put(build.getId().toString(), chooseRoomSetMap.get(buildid));
			}
			
			buildCache.put(build.getId().toString(), build);
		}
		
		
		keySet = tmpChooseRoomSetMap.keySet();
		for(Iterator itor = keySet.iterator(); itor.hasNext(); ){
			
			String buildingId = (String) itor.next();
			Map inMap = (Map) tmpChooseRoomSetMap.get(buildingId);
			String sellProId = (String) inMap.get(T3_PROJECT);
			
			if(allSellProjectCache.get(sellProId)==null){
				continue;
			}
			SellProjectInfo sellProject = (SellProjectInfo) allSellProjectCache.get(sellProId);
			if(sellProject==null){
				continue;
			}
			if(!sellProject.isIsLeaf()){
				continue;
			}
			
			IRow row = this.tblChooseRoom.addRow();
			row.getCell(T3_BUILDING).setValue(buildCache.get(buildingId));
			row.getCell(T3_PROJECT).setValue(allSellProjectCache.get(sellProId));
			if(inMap.get(T3_BEGIN_DATE)!=null){
				row.getCell(T3_BEGIN_DATE).setValue(inMap.get(T3_BEGIN_DATE));
			}
			if(inMap.get(T3_END_DATE)!=null){
				row.getCell(T3_END_DATE).setValue(inMap.get(T3_END_DATE));
			}
			row.getCell(T3_IS_ENABLE).setValue(inMap.get(T3_IS_ENABLE));
			row.getCell(T3_EXPIRY_DATE).setValue(inMap.get(T3_EXPIRY_DATE));			
		}
		
		this.tblChooseRoom.getMergeManager().mergeBlock(0, 0, this.tblChooseRoom.getRowCount() - 1, 1, KDTMergeManager.FREE_MERGE);
	}

	private void initTables(String projectIdStr) {
		this.tblProSetWithProductType.checkParsed();
		this.tblProSetWithoutProductType.checkParsed();
		this.tblChooseRoom.checkParsed();
		
		/**
		 * 根据现场的要求去掉营销权限的过滤
		 * update by renliang at 2011-8-15
		 */
		FullOrgUnitInfo  orgUnit = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
		EntityViewInfo viewcollection = new EntityViewInfo();
		FilterInfo filtercollection = new FilterInfo();
		filtercollection.getFilterItems().add(
				new FilterItemInfo("isLeaf", new Boolean(true),
						CompareType.EQUALS));
		/**
		 * 当是集团的时候,显示所有的项目出来
		 */
		if(orgUnit!=null && !orgUnit.getId().toString().equals(OrgConstants.DEF_CU_ID.toString())){
			filtercollection.getFilterItems().add(
					new FilterItemInfo("orgUnit.id", orgUnit.getId().toString(),
							CompareType.EQUALS));
			
		}
//		filtercollection.getFilterItems().add(new FilterItemInfo("id",projectIdStr,CompareType.NOTINNER));
		
		viewcollection.setFilter(filtercollection);
		this.tblProSetWithProductType.getColumn(T1_COL_PROEJCT_NAME).setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery", viewcollection));
		
		EntityViewInfo typeviewcollection = new EntityViewInfo();
		FilterInfo typefiltercollection = new FilterInfo();
		typefiltercollection.getFilterItems().add(new FilterItemInfo("isEnabled", new Boolean(true)));
		typeviewcollection.setFilter(typefiltercollection);
//		this.tblProSetWithProductType.getColumn(T1_COL_PRODUCT_TYPE).setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.basedata.app.ProductTypeQuery", typeviewcollection));
		KDBizPromptBox f7Prompt = new KDBizPromptBox();
		f7Prompt.setQueryInfo("com.kingdee.eas.fdc.basedata.app.ProductTypeQuery");
		f7Prompt.setEditable(true);
		f7Prompt.setEnabledMultiSelection(true);
		f7Prompt.setDisplayFormat("$name$");
		f7Prompt.setEditFormat("$number$");
		f7Prompt.setCommitFormat("$number$");
		f7Prompt.setEntityViewInfo(typeviewcollection);
		this.tblProSetWithProductType.getColumn(T1_COL_PRODUCT_TYPE).setEditor(new KDTDefaultCellEditor(f7Prompt));
		
		this.tblProSetWithProductType.getColumn(T1_COL_PRODUCT_TYPE).setRenderer(new ObjectValueRender(){
			public String getText(Object obj) {
				if(obj instanceof Object[] ){
					Object[] objs = (Object[]) obj;
					StringBuffer proDes = new StringBuffer();
					for(int j=0; j<objs.length; j++){
						ProductTypeInfo proType = (ProductTypeInfo) objs[j];
						if(proType==null) continue;
						if(j != 0){
							proDes.append(SPIT_STR);
						}
						proDes.append(proType.getName());
					}
					return proDes.toString();
				}
				return super.getText(obj);
			}
		});
		
		this.tblProSetWithProductType.getColumn(T1_KEEP_DOWN_LIMIT_TIME).setEditor(CRMClientHelper.getIntegerCellEditor());
		this.tblProSetWithProductType.getColumn(T1_PRE_PURCHASE_LIMIT_TIME).setEditor(CRMClientHelper.getIntegerCellEditor());
		this.tblProSetWithProductType.getColumn(T1_PURCHASE_LIMIT_TIME).setEditor(CRMClientHelper.getIntegerCellEditor());
		this.tblProSetWithProductType.getColumn(T1_TO_SIGN_LIMIT_TIME).setEditor(CRMClientHelper.getIntegerCellEditor());
		this.tblProSetWithProductType.getColumn(T1_SIGN_LIMIT_TIME).setEditor(CRMClientHelper.getIntegerCellEditor());
		
		this.tblProSetWithProductType.getColumn(T1_PRE_PURCHASE_STANDARD).setEditor(CRMClientHelper.getKDTDefaultCellEditor());
		this.tblProSetWithProductType.getColumn(T1_SIN_PURCHASE_STANDARD).setEditor(CRMClientHelper.getKDTDefaultCellEditor());
		this.tblProSetWithProductType.getColumn(T1_PRE_STANDARD).setEditor(CRMClientHelper.getKDTDefaultCellEditor());
		
		
		this.tblProSetWithoutProductType.getColumn(T2_PROJECT).setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery", null));
		this.tblProSetWithoutProductType.getColumn(T2_CUS_TRADE_DAYS).setEditor(CRMClientHelper.getIntegerCellEditor());
		this.tblProSetWithoutProductType.getColumn(T2_CHANCE_DAYS).setEditor(CRMClientHelper.getIntegerCellEditor());
		this.tblProSetWithoutProductType.getColumn(T2_COMMERCECHANCE_DAYS).setEditor(CRMClientHelper.getIntegerCellEditor());
		this.tblProSetWithoutProductType.getColumn(T2_PROJECT).getStyleAttributes().setLocked(true);
		this.tblProSetWithoutProductType.getColumn(T2_CHANGE_CUS_DEF_BIZ_DATE).setEditor(CommerceHelper.getKDComboBoxEditor(BizDateToEnum.getEnumList()));
		this.tblProSetWithoutProductType.getColumn(T2_CHANGE_ROOM_DEF_BIZ_DATE).setEditor(CommerceHelper.getKDComboBoxEditor(BizDateToEnum.getEnumList()));
		this.tblProSetWithoutProductType.getColumn(T2_PRICE_ADJUST_DEF_BIZ_DATE).setEditor(CommerceHelper.getKDComboBoxEditor(BizDateToEnum.getEnumList()));
		
		this.tblChooseRoom.getColumn(T3_PROJECT).setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery", null));
		this.tblChooseRoom.getColumn(T3_BUILDING).setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.BuildingQuery", null));
		this.tblChooseRoom.getColumn(T3_BEGIN_DATE).setEditor(CommerceHelper.getKDDatePickerEditor());
		this.tblChooseRoom.getColumn(T3_END_DATE).setEditor(CommerceHelper.getKDDatePickerEditor());
		this.tblChooseRoom.getColumn(T3_EXPIRY_DATE).setEditor(CRMClientHelper.getIntegerCellEditor());
		
		this.tblChooseRoom.getColumn(T3_PROJECT).getStyleAttributes().setLocked(true);
		this.tblChooseRoom.getColumn(T3_BUILDING).getStyleAttributes().setLocked(true);
	}

	private SellProjectCollection getAllSellProjectCollection(SellProjectInfo sellProject) throws BOSException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.id", sellProject.getId()));
		view.setFilter(filter);
		SellProjectCollection col=SellProjectFactory.getRemoteInstance().getSellProjectCollection(view);
		col.add(sellProject);
		return col;
	}
	
	protected void btnOK_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		
		Map proSetMap = (Map) ObjectUtils.createCopy(set.getProjectSetMap());
		
		for(int i=0; i<this.tblProSetWithoutProductType.getRowCount(); i++){
			IRow row = this.tblProSetWithoutProductType.getRow(i);
			SellProjectInfo sellPro = (SellProjectInfo) row.getCell(T2_PROJECT).getValue();
			if(sellPro == null){
				continue;
			}
			
			SellProjectCollection col=getAllSellProjectCollection(sellPro);
			
			for(int s=0;s<col.size();s++){
				String proId = col.get(s).getId().toString();
				
				Map inMap = new HashMap();
				
				inMap.put(T2_IS_DEAL_AMOUNT_EDIT, row.getCell(T2_IS_DEAL_AMOUNT_EDIT).getValue());
				inMap.put(T2_IS_ENABLE_AGIO, row.getCell(T2_IS_ENABLE_AGIO).getValue());
				inMap.put(T2_IS_ENABLE_ADJUST_AGIO, row.getCell(T2_IS_ENABLE_ADJUST_AGIO).getValue());
				inMap.put(T2_IS_FORCE_LIMIT_PRICE, row.getCell(T2_IS_FORCE_LIMIT_PRICE).getValue());
				inMap.put(T2_IS_MUST_BY_BANK, row.getCell(T2_IS_MUST_BY_BANK).getValue());
				
				inMap.put(T2_IS_DEAL_AMT_BY_STAND_AMT, row.getCell(T2_IS_DEAL_AMT_BY_STAND_AMT).getValue());
				inMap.put(T2_DEAL_AMOUNT_CAN_EDIT, row.getCell(T2_DEAL_AMOUNT_CAN_EDIT).getValue());
				inMap.put(T2_IS_RE_PRICE_OF_QUIT_ROOM, row.getCell(T2_IS_RE_PRICE_OF_QUIT_ROOM).getValue());
				inMap.put(T2_IS_RE_PRICE_OF_CHANGE_ROOM, row.getCell(T2_IS_RE_PRICE_OF_CHANGE_ROOM).getValue());
				inMap.put(T2_IS_AREA_CHANGE_NEED_AUDIT, row.getCell(T2_IS_AREA_CHANGE_NEED_AUDIT).getValue());
				
				inMap.put(T2_IS_BIZ_DATE_EDITABLE, row.getCell(T2_IS_BIZ_DATE_EDITABLE).getValue());
				inMap.put(T2_CHANGE_ROOM_DEF_BIZ_DATE, row.getCell(T2_CHANGE_ROOM_DEF_BIZ_DATE).getValue());
				inMap.put(T2_PRICE_ADJUST_DEF_BIZ_DATE, row.getCell(T2_PRICE_ADJUST_DEF_BIZ_DATE).getValue());
				inMap.put(T2_CHANGE_CUS_DEF_BIZ_DATE, row.getCell(T2_CHANGE_CUS_DEF_BIZ_DATE).getValue());
				inMap.put(T2_IS_NO_SELL_CAN_SIN, row.getCell(T2_IS_NO_SELL_CAN_SIN).getValue());
				
				inMap.put(T2_IS_ENABLE_TRADE, row.getCell(T2_IS_ENABLE_TRADE).getValue());
				inMap.put(T2_CUS_TRADE_DAYS, row.getCell(T2_CUS_TRADE_DAYS).getValue());
				inMap.put(T2_CHANCE_DAYS, row.getCell(T2_CHANCE_DAYS).getValue());
				inMap.put(T2_COMMERCECHANCE_DAYS, row.getCell(T2_COMMERCECHANCE_DAYS).getValue());
				proSetMap.put(proId, inMap);
			}
		}
		
		Set toUpBs = new HashSet();
		Map chooseRoomSetMap = (Map) ObjectUtils.createCopy(set.getChooseRoomSetMap());
		
		for(int i=0; i<this.tblChooseRoom.getRowCount(); i++){
			IRow row = this.tblChooseRoom.getRow(i);
			BuildingInfo building = (BuildingInfo) row.getCell(T3_BUILDING).getValue();
			String buildingId = building.getId().toString();
			
			SellProjectInfo sellPro = (SellProjectInfo) row.getCell(T3_PROJECT).getValue();
			String proId = sellPro.getId().toString();
			
			Map inMap = new HashMap();
			inMap.put(T3_PROJECT, proId);
			inMap.put(T3_BEGIN_DATE, row.getCell(T3_BEGIN_DATE).getValue());
			inMap.put(T3_END_DATE, row.getCell(T3_END_DATE).getValue());
			inMap.put(T3_IS_ENABLE, row.getCell(T3_IS_ENABLE).getValue());
			inMap.put(T3_EXPIRY_DATE, row.getCell(T3_EXPIRY_DATE).getValue());
			
			//增加判断，如果针对某楼栋是否启用勾选了则该条记录的开始、结束日期和选房时效为必录项
			if(row.getCell(T3_IS_ENABLE) != null  &&  ((Boolean)row.getCell(T3_IS_ENABLE).getValue()).booleanValue()){
				if(row.getCell(T3_BEGIN_DATE).getValue() == null  ||  row.getCell(T3_END_DATE).getValue() == null  ||  row.getCell(T3_EXPIRY_DATE).getValue() == null){
					MsgBox.showInfo(this, "选房设置第" + (i+1) + "行，开始日期，结束日期，选房时效不能为空！");
					this.abort();
				}
			}
			
			//如果取消启用选房，则需要将该楼栋下的选房单置为选房作废
			if(row.getCell(T3_IS_ENABLE) != null  &&  !((Boolean)row.getCell(T3_IS_ENABLE).getValue()).booleanValue()){
				Map tmp = (Map) chooseRoomSetMap.get(buildingId);
				if(tmp != null  &&  tmp.get(T3_IS_ENABLE) != null){
					if(((Boolean)tmp.get(T3_IS_ENABLE)).booleanValue()){
//						FilterInfo filter = new FilterInfo();
//						filter.getFilterItems().add(new FilterItemInfo("room.building.id", buildingId));
//						filter.getFilterItems().add(new FilterItemInfo("chooseRoomStateEnum", ChooseRoomStateEnum.AFFIRM_VALUE));
//						if(ChooseRoomFactory.getRemoteInstance().exists(filter)){
							toUpBs.add(building);
//						}
					}
				}
			}
			
			chooseRoomSetMap.put(buildingId, inMap);
		}
		
		if (!toUpBs.isEmpty()) {
			StringBuffer sb = new StringBuffer();
			StringBuffer bdes = new StringBuffer();
			int i=0;
			for(Iterator itor = toUpBs.iterator(); itor.hasNext(); ){
				BuildingInfo build = (BuildingInfo) itor.next();
				if(i != 0){
					sb.append(",");
					bdes.append(",");
				}
				sb.append("'");
				sb.append(build.getId().toString());
				sb.append("'");
				
				bdes.append(build.getName());
				i++;
			}
			
			if(!(MsgBox.YES == MsgBox.showConfirm2(this, "楼栋【" + bdes.toString() + "】的选房功能被人工关闭，所有未转销售的选房单都将被取消，继续请点击【确定】按钮，返回重新修改请单击【取消】按钮。"))){
				this.abort();
			}
			
			String sql = "update T_SHE_ChooseRoom set FChooseRoomStateEnum='7CANCELCHOOSE' where FChooseRoomStateEnum='2AFFIRM' and froomid in (select fid from t_she_room where fbuildingid in ("+ sb.toString() +"))";
			FDCSQLBuilder sqlb = new FDCSQLBuilder(sql);
			sqlb.execute();
		}
		
		
		Map proProSetMap = (Map) ObjectUtils.createCopy(set.getProjectProductTypeSetMap());;
		
		for(Iterator itor = cache.iterator(); itor.hasNext(); ){
			proProSetMap.remove(itor.next());
		}
		
		for(int i=0; i<this.tblProSetWithProductType.getRowCount(); i++){
			IRow row = this.tblProSetWithProductType.getRow(i);
			
			SellProjectInfo sellPro = (SellProjectInfo) row.getCell(T1_COL_PROEJCT_NAME).getValue();
			if(sellPro == null){
				continue;
			}
			SellProjectCollection col=getAllSellProjectCollection(sellPro);
			
			for(int s=0;s<col.size();s++){
				String proId = col.get(s).getId().toString();
				
				List list = (List) proProSetMap.get(proId);
				if(list == null){
					list = new ArrayList();
					proProSetMap.put(proId, list);
				}
				
				Map inMap = new HashMap();
				
				Object[] proTypes = (Object[]) row.getCell(T1_COL_PRODUCT_TYPE).getValue();
				if(proTypes != null  &&  proTypes.length != 0){
					StringBuffer proDes = new StringBuffer();
					for(int j=0; j<proTypes.length; j++){
						ProductTypeInfo proType = (ProductTypeInfo) proTypes[j];
						if(j != 0){
							proDes.append(SPIT_STR);
						}
						proDes.append(proType.getId().toString());
					}
					inMap.put(T1_COL_PRODUCT_TYPE, proDes.toString());
				}
				
				inMap.put(T1_KEEP_DOWN_LIMIT_TIME, row.getCell(T1_KEEP_DOWN_LIMIT_TIME).getValue());
				inMap.put(T1_PRE_PURCHASE_LIMIT_TIME, row.getCell(T1_PRE_PURCHASE_LIMIT_TIME).getValue());
				inMap.put(T1_PURCHASE_LIMIT_TIME, row.getCell(T1_PURCHASE_LIMIT_TIME).getValue());
				
				inMap.put(T1_TO_SIGN_LIMIT_TIME, row.getCell(T1_TO_SIGN_LIMIT_TIME).getValue());
				inMap.put(T1_SIGN_LIMIT_TIME, row.getCell(T1_SIGN_LIMIT_TIME).getValue());
				inMap.put(T1_SIN_PURCHASE_STANDARD, row.getCell(T1_SIN_PURCHASE_STANDARD).getValue());
				inMap.put(T1_PRE_PURCHASE_STANDARD, row.getCell(T1_PRE_PURCHASE_STANDARD).getValue());
				inMap.put(T1_PRE_STANDARD, row.getCell(T1_PRE_STANDARD).getValue());
				
				list.add(inMap);
			}
		}
		
		set.setChooseRoomSetMap(chooseRoomSetMap);
		set.setProjectProductTypeSetMap(proProSetMap);
		set.setProjectSetMap(proSetMap);
		
		set.save();
		this.destroyWindow();
	}

	protected void btnCancel_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		this.destroyWindow();
	}

	protected void tblProSetWithProductType_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception {
		if(tblProSetWithProductType.getRow(e.getRowIndex()).getCell(T1_COL_PRODUCT_TYPE).getValue()==null
				||tblProSetWithProductType.getRow(e.getRowIndex()).getCell(T1_COL_PROEJCT_NAME).getValue()==null) return;
		
		String projectName = tblProSetWithProductType.getRow(e.getRowIndex()).getCell(T1_COL_PROEJCT_NAME).getValue().toString();
		Object [] obj = (Object [])tblProSetWithProductType.getRow(e.getRowIndex()).getCell(T1_COL_PRODUCT_TYPE).getValue();
		boolean isExist = false;
		for (int i = 0; i < tblProSetWithProductType.getRowCount(); i++) {
			IRow row = tblProSetWithProductType.getRow(i);
			if (row == null) {
				continue;
			}
			if(e.getRowIndex()==row.getRowIndex()){
				continue;
			}
			isExist = isExistInProductInfo(projectName, obj, isExist, row);
			if(isExist){
				break;
			}
		}
		
		if(isExist){
			FDCMsgBox.showWarning(this,"该项目存在相同产品类型售楼功能设置！");
			tblProSetWithProductType.getRow(e.getRowIndex()).getCell(T1_COL_PRODUCT_TYPE).setValue(null);
		}
	}

	private boolean isExistInProductInfo(String projectName, Object[] obj,
			boolean isExist, IRow row) {
		if (row.getCell(T1_COL_PROEJCT_NAME).getValue() != null) {
			if (projectName.equals(row.getCell(T1_COL_PROEJCT_NAME)
					.getValue().toString())) {
				Object[] temp = (Object[]) row.getCell(T1_COL_PRODUCT_TYPE)
						.getValue();
				if(temp==null){
					isExist = false;
					return isExist;
				}
				for (int j = 0; j < obj.length; j++) {
					if(isExist){
						break;
					}
					ProductTypeInfo type = (ProductTypeInfo) obj[j];
					for (int j2 = 0; j2 < temp.length; j2++) {
						ProductTypeInfo info = (ProductTypeInfo) temp[j2];
						if (type != null
								&& info != null
								&& type.getId().toString().equals(
										info.getId().toString())) {
							isExist = true;
							break;
						}
					}
				}
			}
		}
		return isExist;
	}

	protected void tblProSetWithoutProductType_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception {
		super.tblProSetWithoutProductType_editStopped(e);
	}

	protected void btnAddLine_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		IRow row = this.tblProSetWithProductType.addRow();
//		this.tblProSetWithoutProductType.addRow();
//		this.tblChooseRoom.addRow();
		setDefaultValue(row);
	}

	protected void btnInsertLine_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		int i = this.tblProSetWithProductType.getSelectManager().getActiveRowIndex();
		
		IRow row = null;
		if(i == -1){
			row = this.tblProSetWithProductType.addRow();
		}else{
			row = this.tblProSetWithProductType.addRow(i);
		}
		
		setDefaultValue(row);
	}

	private void setDefaultValue(IRow row){
		for(int i=0; i<t1.length; i++){
			ICell cell = row.getCell((String) t1[i][0]);
			if(cell != null){
				cell.setValue(t1[i][1]);
			}
		}		
	}
	
	protected void btnRmLine_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		int i = this.tblProSetWithProductType.getSelectManager().getActiveRowIndex();
		if(i != -1){
			this.tblProSetWithProductType.removeRow(i);
		}
	}
	
	protected void tblChooseRoom_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception {
		if(e.getValue()==null){
			if(tblChooseRoom.getColumn(e.getColIndex()).getKey().toString().equals(T3_END_DATE)){
				tblChooseRoom.getRow(e.getRowIndex()).getCell(T3_END_DATE).setValue(null);
			}else if(tblChooseRoom.getColumn(e.getColIndex()).getKey().toString().equals(T3_BEGIN_DATE)){
				tblChooseRoom.getRow(e.getRowIndex()).getCell(T3_BEGIN_DATE).setValue(null);
			}
		}
	}

	public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception {
		super.actionPageSetup_actionPerformed(e);
	}

}