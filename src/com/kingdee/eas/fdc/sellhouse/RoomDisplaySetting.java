package com.kingdee.eas.fdc.sellhouse;

import java.awt.Color;
import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.kdf.data.logging.Logger;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.CRMConstants;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.util.StringUtils;
import com.sun.xml.messaging.saaj.util.ByteInputStream;

/**
 * 取参数方式示例
 * 1.RoomDisplaySetting set = new RoomDisplaySetting(SHEParamConstant.PROJECT_PRODUCTTYPE_SET_MAP);
 * 	 ProjectProductTypeSet detailSet = set.getProjectProductTypeSet("", "");
 *   BigDecimal paramValue = detailSet.getBigDecimal(SHEParamConstant.T1_PRE_STANDARD);
 * */
public class RoomDisplaySetting implements Serializable,IDisplayRule,SHEParamConstant {

	private static final long serialVersionUID = -927599730681101074L;	

	private Map functionSetMap = new HashMap();	//功能设置	项目id 对应 设置类FunctionSetting
	private Map faithAmountSetMap = new HashMap();	//诚意金额设置  项目id 对应 设置类FaithAmountSetting
	private Map preMoneySetMap = new HashMap();	//预订功能设置  项目id 对应 设置类PreMoneySetting
	private CasSetting casSetting;	//财务相关设置
	private BaseRoomSetting baseRoomSetting = new BaseRoomSetting(); //基础房间的设置
	//添加是否自动将预定金转到定金 eric_wang 2010.08.12
	private Map isPreToOtherMoneyMap = new HashMap();
	
	private Map projectProductTypeSetMap = new HashMap();
	private Map projectSetMap = new HashMap();
	private Map chooseRoomSetMap = new HashMap();

	public RoomDisplaySetting(){
		this(null, new String[]{});
	}
	
	private Object[][] ss = {
						{BaseRoomNumber, "baseRoomSetting", "售楼设置--基础房间设置"},
						{PreMoneyNumber, "preMoneySetMap", "售楼设置--预订功能设置"},
						{FaithAmount, "faithAmountSetMap", "售楼设置--默认诚意金额设置"},
						{FunctionNumber, "functionSetMap", "售楼设置--默认诚意金额设置"},
						{CasNumber, "casSetting", "售楼设置--财务相关设置"},
						
						{ISPRETOOTHERMONEY, "isPreToOtherMoneyMap", "售楼设置--功能设置--预定金转定金"},
						{PROJECT_PRODUCTTYPE_SET_MAP, "projectProductTypeSetMap", "售楼设置--项目产品类型设置"},
						{PROJECT_SET_MAP, "projectSetMap", "售楼设置--项目设置"},
						{CHOOSE_ROOM_SET_MAP, "chooseRoomSetMap", "售楼设置--选房设置"},
					};
	
	public RoomDisplaySetting(Context ctx) {
		this(ctx, new String[]{});
	}

	public RoomDisplaySetting(String[] numSetStrs) {
		this(null, numSetStrs);
	}
	
	//如果明确只去某一功能设置的参数， 则可以只查询该功能部分的设置
	public RoomDisplaySetting(Context ctx,String[] numSetStrs) {
//		try {
//			IRoomDisplaySet iface = null;
//			if(ctx == null){
//				iface = RoomDisplaySetFactory.getRemoteInstance();
//			}else{
//				iface = RoomDisplaySetFactory.getLocalInstance(ctx);
//			}
//			
//			EntityViewInfo view = new EntityViewInfo();
//			SelectorItemCollection sels = new SelectorItemCollection();
//			sels.add("number");
//			sels.add("value");
//			
//			if(numSetStrs != null  &&  numSetStrs.length > 0){
//				FilterInfo filter = new FilterInfo();
//				
//				Set set = new HashSet();
//				for (int i = 0; i < numSetStrs.length; i++) {
//					set.add(numSetStrs[i]);
//				}
//				
//				if(set.size() == 1){
//					filter.getFilterItems().add(new FilterItemInfo("number", numSetStrs[0]));
//				}else{
//					filter.getFilterItems().add(new FilterItemInfo("number", set, CompareType.INCLUDE));
//				}
//				
//				view.setFilter(filter);
//			}
//			
//			RoomDisplaySetCollection roomSets = iface.getRoomDisplaySetCollection(view);
//			
//			if(roomSets == null  ||  roomSets.isEmpty()){
//				return;
//			}
//			
//			for(int i=0; i<roomSets.size(); i++){
//				RoomDisplaySetInfo roomSetInfo = roomSets.get(i);
//				
//				byte[] b = roomSetInfo.getValue();
//				ObjectInputStream objectInputStream = new ObjectInputStream(new ByteInputStream(b, 0, b.length));
//				
//				for (int j = 0; j < ss.length; j++) {
//					if (ss[j][0].equals(roomSetInfo.getNumber())) {
//						try {
//							Field fds = RoomDisplaySetting.class.getDeclaredField((String) ss[j][1]);
//							fds.set(this, objectInputStream.readObject());
//						} catch (InvalidClassException ee) {
//							Logger.info(ss[j][2] + "--转换错误！");
//						}
//						break;
//					}
//					
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		Map colV = getCRMConstants(SysContext.getSysContext().getCurrentOrgUnit().getId());
		
		baseRoomSetting.setInitColor(new Color(Integer.parseInt((String)colV.get(CRMConstants.FDCSHE_PARAM_ROOMCOLOR_INIT))));
		baseRoomSetting.setOnShowColor(new Color(Integer.parseInt((String)colV.get(CRMConstants.FDCSHE_PARAM_ROOMCOLOR_ONSHOW))));
		baseRoomSetting.setPrePurColor(new Color(Integer.parseInt((String)colV.get(CRMConstants.FDCSHE_PARAM_ROOMCOLOR_PREPURCHASE))));
		baseRoomSetting.setPurColor(new Color(Integer.parseInt((String)colV.get(CRMConstants.FDCSHE_PARAM_ROOMCOLOR_PURCHASE))));
		baseRoomSetting.setSignColor(new Color(Integer.parseInt((String)colV.get(CRMConstants.FDCSHE_PARAM_ROOMCOLOR_SIGN))));
		baseRoomSetting.setKeepDownColor(new Color(Integer.parseInt((String)colV.get(CRMConstants.FDCSHE_PARAM_ROOMCOLOR_KEEPDOWN))));
		baseRoomSetting.setSincerPurColor(new Color(Integer.parseInt((String)colV.get(CRMConstants.FDCSHE_PARAM_ROOMCOLOR_SINPURCHASE))));
		baseRoomSetting.setControlColor(new Color(Integer.parseInt((String)colV.get(CRMConstants.FDCSHE_PARAM_ROOMCOLOR_CONTROL))));
//		baseRoomSetting.setOnShowColor((Color)colV.get(CRMConstants.FDCSHE_PARAM_ROOMCOLOR_ONSHOW));
//		baseRoomSetting.setPrePurColor((Color)colV.get(CRMConstants.FDCSHE_PARAM_ROOMCOLOR_KEEPDOWN));
//		baseRoomSetting.setPurColor((Color)colV.get(CRMConstants.FDCSHE_PARAM_ROOMCOLOR_SINPURCHASE));
//		baseRoomSetting.setSignColor((Color)colV.get(CRMConstants.FDCSHE_PARAM_ROOMCOLOR_PREPURCHASE));
//		baseRoomSetting.setKeepDownColor((Color)colV.get(CRMConstants.FDCSHE_PARAM_ROOMCOLOR_PURCHASE));
//		baseRoomSetting.setSincerPurColor((Color)colV.get(CRMConstants.FDCSHE_PARAM_ROOMCOLOR_SIGN));
	}
	
	public static Map getCRMConstants(BOSUuid orgid){
		HashMap param = new HashMap();
		HashMap value=null; 
		IObjectPK pk=new ObjectUuidPK(orgid);
		param.put(CRMConstants.FDCSHE_PARAM_ROOMCOLOR_INIT, pk);
		param.put(CRMConstants.FDCSHE_PARAM_ROOMCOLOR_ONSHOW, pk);
		param.put(CRMConstants.FDCSHE_PARAM_ROOMCOLOR_KEEPDOWN, pk);
		param.put(CRMConstants.FDCSHE_PARAM_ROOMCOLOR_SINPURCHASE, pk);
		
		param.put(CRMConstants.FDCSHE_PARAM_ROOMCOLOR_PREPURCHASE, pk);
		param.put(CRMConstants.FDCSHE_PARAM_ROOMCOLOR_PURCHASE, pk);
		param.put(CRMConstants.FDCSHE_PARAM_ROOMCOLOR_SIGN, pk);
		param.put(CRMConstants.FDCSHE_PARAM_ROOMCOLOR_CONTROL, pk);
		
		try {
			value = ParamControlFactory.getRemoteInstance().getParamHashMap(param);
		}catch (EASBizException e1) {
			e1.printStackTrace();
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		return value;
	}
	
	//如果明确只去某一功能设置的参数， 则可以只查询该功能部分的设置
	public RoomDisplaySetting(Context ctx,String numberSetString) {
		this(ctx, new String[]{numberSetString});
	}
	
	private byte[] getBytesFromObject(Object obj) throws Exception {
		ByteArrayOutputStream bOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream oOutputStream = new ObjectOutputStream(bOutputStream);
		oOutputStream.writeObject(obj);  
		return bOutputStream.toByteArray();		
	}
	
	
	public void save() throws Exception {
		
		RoomDisplaySetCollection setOldColl = RoomDisplaySetFactory.getRemoteInstance().getRoomDisplaySetCollection();	
		
		CoreBaseCollection coreCollUpdate = new CoreBaseCollection(); 
		CoreBaseCollection coreCollAdd = new CoreBaseCollection(); 
		for (int i = 0; i < ss.length; i++) {
			Field fds = RoomDisplaySetting.class.getDeclaredField((String) ss[i][1]);
			byte[] baseRoomBytes = getBytesFromObject(fds.get(this));
			RoomDisplaySetInfo baseRoomSetInfo = new RoomDisplaySetInfo();
			baseRoomSetInfo.setValue(baseRoomBytes);
			baseRoomSetInfo.setNumber((String) ss[i][0]);

			if (checkIsExist(setOldColl, (String) ss[i][0])) {
				for (int j = 0; j < setOldColl.size(); j++) {
					RoomDisplaySetInfo setInfo = setOldColl.get(j);
					if (setInfo.getNumber() != null  &&  ss[i][0].equals(setInfo.getNumber())) {
						setInfo.setValue(baseRoomBytes);
						coreCollUpdate.add(setInfo);
					}
				}
			} else {
				coreCollAdd.add(baseRoomSetInfo);
			}
		}

		RoomDisplaySetFactory.getRemoteInstance().update(coreCollUpdate);
		RoomDisplaySetFactory.getRemoteInstance().addnew(coreCollAdd);	

		CacheServiceFactory.getInstance().discardType(new RoomDisplaySetInfo().getBOSType());
	}
	private boolean checkIsExist(RoomDisplaySetCollection coll, String number ){
		boolean flag = false;
		for(int i=0;i<coll.size();i++) {
			RoomDisplaySetInfo setInfo = coll.get(i);
			if(setInfo.getNumber()!=null) {
				if(setInfo.getNumber().equals(number)) {
					flag=true;
					break;
				}
			}
		}
		return flag;
	}
	public int getAttachDisType() {
		return this.baseRoomSetting.getAttachDisType();
	}


	public Font getFont() {
		return this.baseRoomSetting.getFont();
	}

	public Color getFrontColor() {
		return this.baseRoomSetting.getFrontColor();
	}	

	public int getRoomHeight() {
		return this.baseRoomSetting.getRoomHeight();
	}	
	
	public int getRoomWidth() {
		return this.baseRoomSetting.getRoomWidth();
	}

	public String getDisplayField() {
		return this.baseRoomSetting.getDisplayField();
	}

	public Color getCellBackgroundColor(String key) {
		if(StringUtils.isEmpty(key)){
			return null;
		}		
		
		if (key.equals(RoomSellStateEnum.INIT_VALUE))	{
			return this.baseRoomSetting.getInitColor();
		} else if (key.equals(RoomSellStateEnum.ONSHOW_VALUE))	{
			return this.baseRoomSetting.getOnShowColor();
		} else if (key.equals(RoomSellStateEnum.PREPURCHASE_VALUE))	{
			return this.baseRoomSetting.getPrePurColor();
		} else if (key.equals(RoomSellStateEnum.PURCHASE_VALUE))		{
			return this.baseRoomSetting.getPurColor();
		} else if (key.equals(RoomSellStateEnum.SIGN_VALUE))		{
			return this.baseRoomSetting.getSignColor();
		} else if (key.equals(RoomSellStateEnum.KEEPDOWN_VALUE))		{
			return this.baseRoomSetting.getKeepDownColor();
		}else if(key.equals(RoomSellStateEnum.SINCERPURCHASE_VALUE)){
			return this.baseRoomSetting.getSincerPurColor();
		}else if(key.equals("CONTROL")){
			return this.baseRoomSetting.getControlColor();
		}
		else if("noSellhouse".equals(key))		{
			return this.baseRoomSetting.getNoSellhouseColor();
		}
		//by zgy 增加认购收款状态
		else if(key.equals("FirstRe")){
			return this.baseRoomSetting.getSinReColor();
		}
		return null;
	}

	public MoneySysTypeEnum getSysType() {
		return MoneySysTypeEnum.SalehouseSys;
	}

	public Map getFunctionSetMap()
	{
		return functionSetMap;
	}

	public void setFunctionSetMap(Map functionMap)
	{
		this.functionSetMap = functionMap;
	}

	public Map getPreMoneySetMap(){
		return this.preMoneySetMap;
	}
	
	public void setPreMoneySetMap(Map preMoneySetMap) {
		this.preMoneySetMap = preMoneySetMap;
	}

	public BaseRoomSetting getBaseRoomSetting() {
		return baseRoomSetting;
	}

	public void setBaseRoomSetting(BaseRoomSetting baseRoomSetting) {
		this.baseRoomSetting = baseRoomSetting;
	}

	public CasSetting getCasSetting() {
		return casSetting;
	}

	public void setCasSetting(CasSetting casSetting) {
		this.casSetting = casSetting;
	}

	public void setFaithAmountSetMap(Map faithAmountSetMap) {
		this.faithAmountSetMap = faithAmountSetMap;
	}

	public Map getFaithAmountSetMap() {
		return faithAmountSetMap;
	}

	public Map getIsPreToOtherMoneyMap() {
		return isPreToOtherMoneyMap;
	}

	public void setIsPreToOtherMoneyMap(Map isPreToOtherMoneyMap) {
		this.isPreToOtherMoneyMap = isPreToOtherMoneyMap;
	}

	public Map getProjectSetMap() {
		return projectSetMap;
	}

	public void setProjectSetMap(Map projectSetMap) {
		this.projectSetMap = projectSetMap;
	}

	public Map getChooseRoomSetMap() {
		return chooseRoomSetMap;
	}

	public void setChooseRoomSetMap(Map chooseRoomSetMap) {
		this.chooseRoomSetMap = chooseRoomSetMap;
	}

	public Map getProjectProductTypeSetMap() {
		return projectProductTypeSetMap;
	}

	public void setProjectProductTypeSetMap(Map projectProductTypeSetMap) {
		this.projectProductTypeSetMap = projectProductTypeSetMap;
	}
	
	
	private ProjectProductTypeSet innerGetProjectProductTypeSet(String projectId, String productTypeId){
		if(projectProductTypeSetMap == null  ||  projectProductTypeSetMap.isEmpty()){
			return null;
		}
		
		List list = (List) projectProductTypeSetMap.get(projectId);
		if(list == null || list.isEmpty()){
			return null;
		}
		
		for(int i=0; i<list.size(); i++){
			Map inMap = (Map) list.get(i);
			String proTypeDes = (String) inMap.get(T1_COL_PRODUCT_TYPE);
			
			if(proTypeDes == null  ||  proTypeDes.trim().equals("")){
				if(productTypeId == null){
					return new ProjectProductTypeSet(inMap);
				}else{
					continue;
				}
			}else{
				if(productTypeId == null){
					continue;
				}else{
					if(Arrays.asList(proTypeDes.split(SPIT_STR)).contains(productTypeId)){
						return new ProjectProductTypeSet(inMap);
					}else{
						continue;
					}
				}
			}
		}
		
		return null;
	}
	
//	public ProjectProductTypeSet getProjectProductTypeSet(String projectId, String productTypeId){
//		ProjectProductTypeSet r = innerGetProjectProductTypeSet(projectId, productTypeId);
//		//如果传入了产品类型取得空，再扩大范围找项目的设置
//		if(r == null  &&  productTypeId != null){
//			return innerGetProjectProductTypeSet(projectId, null);
//		}
//		
//		return r;
//	}
//	
//	public ProjectSet getProjectSet(String projectId){
//		if(projectId == null  ||  projectSetMap == null  ||  projectSetMap.isEmpty()){
//			return null;
//		}
//		
//		Map map = (Map) projectSetMap.get(projectId);
//		if(map == null  ||  map.isEmpty()){
//			return null;
//		}
//		return new ProjectSet(map);
//	}
//	
//	public ChooseRoomSet getChooseRoomSet(String buildingId){
//		if(buildingId == null  ||  chooseRoomSetMap == null  ||  chooseRoomSetMap.isEmpty()){
//			return null;
//		}
//		
//		Map map = (Map) chooseRoomSetMap.get(buildingId);
//		if(map == null  ||  map.isEmpty()){
//			return null;
//		}
//		return new ChooseRoomSet(map);
//	}
	
	/**
	 * 
	 * @param projectId 项目id
	 * @param productTypeId 产品类型id
	 * @return
	 */
	public static Map getNewProjectProductTypeSet(Context ctx,String projectId,String productTypeId){
		Map projectMap = null;
		
		if(projectId==null || "".equals(projectId)){
			return projectMap;
		}
		try{
			projectMap = new HashMap();
			
			if(productTypeId!=null){
				ISHEFunProductTypeEntry iSHEFunProductTypeEntry=null;
				if(ctx==null){
					iSHEFunProductTypeEntry=SHEFunProductTypeEntryFactory.getRemoteInstance();
				}else{
					iSHEFunProductTypeEntry=SHEFunProductTypeEntryFactory.getLocalInstance(ctx);
				}
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("head.sellProject.id",projectId,CompareType.EQUALS));
				filter.getFilterItems().add(new FilterItemInfo("productType.id",productTypeId,CompareType.EQUALS));
				
				view.setFilter(filter);
				SelectorItemCollection select = new SelectorItemCollection();
				select.add(new SelectorItemInfo("head.sellProject.id"));
				select.add(new SelectorItemInfo("head.sellProject.name"));
				select.add(new SelectorItemInfo("head.sellProject.number"));
				select.add(new SelectorItemInfo("head.appointInvalidLimit"));
				select.add(new SelectorItemInfo("head.bookingInvalidLimit"));
				select.add(new SelectorItemInfo("head.purchaseInvalidLimit"));
				select.add(new SelectorItemInfo("head.purchaseSignLimit"));
				select.add(new SelectorItemInfo("head.signInvalidLimit"));
				select.add(new SelectorItemInfo("head.sincerAmount"));
				select.add(new SelectorItemInfo("head.purBookingAmount"));
				select.add(new SelectorItemInfo("head.bookingAmount"));
				select.add(new SelectorItemInfo("head.productTypeEntry.id"));
				select.add(new SelectorItemInfo("head.productTypeEntry.name"));
				select.add(new SelectorItemInfo("head.productTypeEntry.number"));
				view.setSelector(select);
				SHEFunProductTypeEntryCollection coll = iSHEFunProductTypeEntry.getCollection(view);
				if(coll!=null && coll.size()>0){
					SHEFunProductTypeEntryInfo info= coll.get(0);
					if(info!=null){
						projectMap.put(T1_COL_PROEJCT_NAME,info.getHead().getSellProject());
						
						projectMap.put( T1_KEEP_DOWN_LIMIT_TIME,new Integer(info.getHead().getAppointInvalidLimit()));
						
						projectMap.put( T1_PRE_PURCHASE_LIMIT_TIME,new Integer(info.getHead().getBookingInvalidLimit()));
						
						projectMap.put(T1_PURCHASE_LIMIT_TIME,new Integer(info.getHead().getPurchaseInvalidLimit()));
						
						projectMap.put(T1_TO_SIGN_LIMIT_TIME,new Integer(info.getHead().getPurchaseSignLimit()));
						
						projectMap.put(T1_SIGN_LIMIT_TIME,new Integer(info.getHead().getSignInvalidLimit()));
						
						projectMap.put(T1_SIN_PURCHASE_STANDARD,(BigDecimal)info.getHead().getSincerAmount());
						
						projectMap.put(T1_PRE_PURCHASE_STANDARD,(BigDecimal)info.getHead().getPurBookingAmount());
						
						projectMap.put(T1_PRE_STANDARD,(BigDecimal)info.getHead().getBookingAmount());
						
						return projectMap;
					}
				}
			}
			
			
			ISHEFunctionSetEntry iSHEFunctionSetEntry=null;
			if(ctx==null){
				iSHEFunctionSetEntry=SHEFunctionSetEntryFactory.getRemoteInstance();
			}else{
				iSHEFunctionSetEntry=SHEFunctionSetEntryFactory.getLocalInstance(ctx);
			}
			
			EntityViewInfo pview = new EntityViewInfo();
			FilterInfo pfilter = new FilterInfo();
			pfilter.getFilterItems().add(new FilterItemInfo("sellProject.id",projectId,CompareType.EQUALS));
			pview.setFilter(pfilter);
			SelectorItemCollection pselect = new SelectorItemCollection();
			pselect.add(new SelectorItemInfo("sellProject.id"));
			pselect.add(new SelectorItemInfo("sellProject.name"));
			pselect.add(new SelectorItemInfo("sellProject.number"));
			pselect.add(new SelectorItemInfo("appointInvalidLimit"));
			pselect.add(new SelectorItemInfo("bookingInvalidLimit"));
			pselect.add(new SelectorItemInfo("purchaseInvalidLimit"));
			pselect.add(new SelectorItemInfo("purchaseSignLimit"));
			pselect.add(new SelectorItemInfo("signInvalidLimit"));
			pselect.add(new SelectorItemInfo("sincerAmount"));
			pselect.add(new SelectorItemInfo("purBookingAmount"));
			pselect.add(new SelectorItemInfo("bookingAmount"));
			pview.setSelector(pselect);
			SHEFunctionSetEntryCollection pcoll = iSHEFunctionSetEntry.getSHEFunctionSetEntryCollection(pview);
			if(pcoll!=null && pcoll.size()>0){
				SHEFunctionSetEntryInfo info= pcoll.get(0);
				if(info!=null){
					projectMap.put(T1_COL_PROEJCT_NAME,info.getSellProject());
					
					projectMap.put( T1_KEEP_DOWN_LIMIT_TIME,new Integer(info.getAppointInvalidLimit()));
					
					projectMap.put( T1_PRE_PURCHASE_LIMIT_TIME,new Integer(info.getBookingInvalidLimit()));
					
					projectMap.put(T1_PURCHASE_LIMIT_TIME,new Integer(info.getPurchaseInvalidLimit()));
					
					projectMap.put(T1_TO_SIGN_LIMIT_TIME,new Integer(info.getPurchaseSignLimit()));
					
					projectMap.put(T1_SIGN_LIMIT_TIME,new Integer(info.getSignInvalidLimit()));
					
					projectMap.put(T1_SIN_PURCHASE_STANDARD,(BigDecimal)info.getSincerAmount());
					
					projectMap.put(T1_PRE_PURCHASE_STANDARD,(BigDecimal)info.getPurBookingAmount());
					
					projectMap.put(T1_PRE_STANDARD,(BigDecimal)info.getBookingAmount());
		
					return projectMap;
				}
			}else{
				SellProjectInfo sellProject=null;
				if(ctx==null){
					sellProject=SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(projectId));
				}else{
					sellProject=SellProjectFactory.getLocalInstance(ctx).getSellProjectInfo(new ObjectUuidPK(projectId));
				}
				if(sellProject!=null){
					SellProjectInfo ps=SHEManageHelper.getParentSellProject(ctx, sellProject);
					if(ps==null){
						return null;
					}else if(ps.getId().toString().equals(projectId)){
						return null;
					}else{
						return getNewProjectProductTypeSet(ctx,ps.getId().toString(),productTypeId);
					}
				}
			}
		}catch(BOSException ex){
			return null;
		} catch (EASBizException e) {
			e.printStackTrace();
		}
		return projectMap;
	}
	
	/**
	 * 
	 * @param projectId
	 * @return
	 */
	public static Map getNewProjectSet(Context ctx,String projectId){
		Map projectMap = null;
		
		if(projectId==null || "".equals(projectId)){
			return projectMap;
		}
		
		try{
			ISHEProjectSet iSHEProjectSet=null;
			projectMap = new HashMap();
			
			if(ctx==null){
				iSHEProjectSet=SHEProjectSetFactory.getRemoteInstance();
			}else{
				iSHEProjectSet=SHEProjectSetFactory.getLocalInstance(ctx);
			}
			
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id",projectId,CompareType.EQUALS));
			view.setFilter(filter);
			SelectorItemCollection select = new SelectorItemCollection();
			select.add(new SelectorItemInfo("sellProject.id"));
			select.add(new SelectorItemInfo("sellProject.name"));
			select.add(new SelectorItemInfo("sellProject.number"));
			select.add(new SelectorItemInfo("isUpdateForAmount"));
			select.add(new SelectorItemInfo("isUseAgioScheme"));
			select.add(new SelectorItemInfo("isUpdateForAgioDetail"));
			select.add(new SelectorItemInfo("isBasePriceControl"));
			select.add(new SelectorItemInfo("isBank"));
			select.add(new SelectorItemInfo("isStandardTotalAmount"));
			select.add(new SelectorItemInfo("isPriceAuditedForQuitRoom"));
			select.add(new SelectorItemInfo("isPriceAuditedForChanceRoom"));
			select.add(new SelectorItemInfo("isPriceAuditedForAreaChance"));
			select.add(new SelectorItemInfo("isUpdateForbelongDate"));
			select.add(new SelectorItemInfo("changeRoomDefaultBelongDate"));
			select.add(new SelectorItemInfo("priceChangeDefaultBelongDate"));
			select.add(new SelectorItemInfo("changeNameDefaultBelongDate"));
			select.add(new SelectorItemInfo("isSincerForNotSell"));
			select.add(new SelectorItemInfo("isUseTrackLimeDate"));
			select.add(new SelectorItemInfo("customerTrackLimitDate"));
			select.add(new SelectorItemInfo("commerTrackLimitDate"));
			select.add(new SelectorItemInfo("commerLimitDate"));
			select.add(new SelectorItemInfo("nameRepeatRule"));
			select.add(new SelectorItemInfo("phoneRepeatRule"));
			select.add(new SelectorItemInfo("cerRepeatRule"));
			view.setSelector(select);
			SHEProjectSetCollection coll = iSHEProjectSet.getSHEProjectSetCollection(view);
			if(coll!=null && coll.size()>0){
				SHEProjectSetInfo info= coll.get(0);
				if(info!=null){
					projectMap.put(T2_PROJECT,info.getSellProject());
					projectMap.put(T2_IS_DEAL_AMOUNT_EDIT,new Boolean(info.isIsUpdateForAmount()));
					projectMap.put(T2_IS_ENABLE_AGIO,new Boolean(info.isIsUseAgioScheme()));
					projectMap.put(T2_IS_ENABLE_ADJUST_AGIO,new Boolean(info.isIsUpdateForAgioDetail()));
					projectMap.put(T2_IS_FORCE_LIMIT_PRICE,new Boolean(info.isIsBasePriceControl()));
					projectMap.put(T2_IS_MUST_BY_BANK,new Boolean(info.isIsBank()));
					projectMap.put(T2_IS_DEAL_AMT_BY_STAND_AMT,new Boolean(info.isIsStandardTotalAmount()));
					projectMap.put(T2_IS_RE_PRICE_OF_QUIT_ROOM,new Boolean(info.isIsPriceAuditedForQuitRoom()));
					projectMap.put(T2_IS_RE_PRICE_OF_CHANGE_ROOM,new Boolean(info.isIsPriceAuditedForChanceRoom()));
					projectMap.put(T2_IS_ENABLE_ADJUST_AGIO,new Boolean(info.isIsUpdateForAgioDetail()));
					projectMap.put(T2_IS_AREA_CHANGE_NEED_AUDIT,new Boolean(info.isIsPriceAuditedForAreaChance()));
					projectMap.put(T2_IS_BIZ_DATE_EDITABLE,new Boolean(info.isIsUpdateForbelongDate()));
					projectMap.put(T2_CHANGE_ROOM_DEF_BIZ_DATE,info.getChangeRoomDefaultBelongDate());
					projectMap.put(T2_PRICE_ADJUST_DEF_BIZ_DATE,info.getPriceChangeDefaultBelongDate());
					projectMap.put(T2_CHANGE_CUS_DEF_BIZ_DATE,info.getChangeNameDefaultBelongDate());
					projectMap.put(T2_IS_NO_SELL_CAN_SIN,new Boolean(info.isIsSincerForNotSell()));
					projectMap.put(T2_IS_ENABLE_TRADE,new Boolean(info.isIsUseTrackLimeDate()));
					projectMap.put(T2_IS_BIZ_DATE_EDITABLE,new Boolean(info.isIsUpdateForbelongDate()));
					projectMap.put(T2_CUS_TRADE_DAYS,new Integer(info.getCustomerTrackLimitDate()));
					projectMap.put(T2_CHANCE_DAYS,new Integer(info.getCommerTrackLimitDate()));
					projectMap.put(T2_COMMERCECHANCE_DAYS,new Integer(info.getCommerLimitDate()));
					projectMap.put(T2_NAME_REPEAT_RULE, info.getNameRepeatRule());
					projectMap.put(T2_PHONE_REPEAT_RULE, info.getPhoneRepeatRule());
					projectMap.put(T2_CER_REPEAT_RULE, info.getCerRepeatRule());
					
					return projectMap;
				}
			}else{
				SellProjectInfo sellProject=null;
				if(ctx==null){
					sellProject=SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(projectId));
				}else{
					sellProject=SellProjectFactory.getLocalInstance(ctx).getSellProjectInfo(new ObjectUuidPK(projectId));
				}
				if(sellProject!=null){
					SellProjectInfo ps=SHEManageHelper.getParentSellProject(ctx, sellProject);
					if(ps==null){
						return null;
					}else if(ps.getId().toString().equals(projectId)){
						return null;
					}else{
						return getNewProjectSet(ctx,ps.getId().toString());
					}
				}
			}
			
		}catch(BOSException ex){
			return null;
		} catch (EASBizException e) {
			e.printStackTrace();
		}
		
		return projectMap;
	}
	
	/**
	 * 
	 * @param buildingId
	 * @return
	 */
	public static Map getNewChooseRoomSet(Context ctx,String buildingId){
		Map chooseRoomMap = null;
		
		if(buildingId==null || "".equals(buildingId)){
			return chooseRoomMap;
		}
		
		try{
			ISHEFunctionChooseRoomSet iSHEFunctionChooseRoomSet=null;
			if(ctx==null){
				iSHEFunctionChooseRoomSet=SHEFunctionChooseRoomSetFactory.getRemoteInstance();
			}else{
				iSHEFunctionChooseRoomSet=SHEFunctionChooseRoomSetFactory.getLocalInstance(ctx);
			}
			
			chooseRoomMap = new HashMap();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("building.id",buildingId,CompareType.EQUALS));
			view.setFilter(filter);
			SelectorItemCollection select = new SelectorItemCollection();
			select.add(new SelectorItemInfo("sellProject.id"));
			select.add(new SelectorItemInfo("sellProject.name"));
			select.add(new SelectorItemInfo("sellProject.number"));
			select.add(new SelectorItemInfo("building.id"));
			select.add(new SelectorItemInfo("building.name"));
			select.add(new SelectorItemInfo("building.number"));
			select.add(new SelectorItemInfo("startDate"));
			select.add(new SelectorItemInfo("endDate"));
			select.add(new SelectorItemInfo("isEnabled"));
			select.add(new SelectorItemInfo("defaultLimitDate"));
			view.setSelector(select);
			SHEFunctionChooseRoomSetCollection coll = iSHEFunctionChooseRoomSet.getSHEFunctionChooseRoomSetCollection(view);
			if(coll!=null && coll.size()>0){
				SHEFunctionChooseRoomSetInfo info= coll.get(0);
				if(info!=null){
					chooseRoomMap.put(T3_PROJECT,info.getSellProject());
					chooseRoomMap.put(T3_BUILDING,info.getBuilding());
					chooseRoomMap.put(T3_BEGIN_DATE,info.getStartDate());
					chooseRoomMap.put(T3_END_DATE,info.getEndDate());
					chooseRoomMap.put(T3_IS_ENABLE,new Boolean(info.isIsEnabled()));
					chooseRoomMap.put(T3_EXPIRY_DATE,new Integer(info.getDefaultLimitDate()));
				}
			}else{
				return null;
			}
		}catch(BOSException ex){
			return null;
		}
		
		return chooseRoomMap;
	}
}







