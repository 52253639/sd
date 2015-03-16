package com.kingdee.eas.fdc.sellhouse;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.kingdee.bos.Context;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.IParamControl;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.param.ParamDataType;
import com.kingdee.eas.base.param.ParamInfo;
import com.kingdee.eas.base.param.ParamItemFactory;
import com.kingdee.eas.base.param.ParamItemInfo;
import com.kingdee.eas.base.param.ParamValueRangeInfo;
import com.kingdee.eas.base.param.util.ParamManager;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.CRMConstants;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.StringUtils;



/**
 *  * 20100506由于项目级别的设置不适合搬迁到系统参数中去，因而售楼设置搬迁到系统参数中的工作先暂停
 * 日后可考虑，吧项目的相关设置放到项目实体上去设置，其它的可以迁移到系统设置里去
 * 
 * @author jeegan_wang
 *
 */
public class RoomDisplaySetting_BK implements Serializable,IDisplayRule {

	private static final long serialVersionUID = -927599730681101074L;	

	private Map functionSetMap = new HashMap();	//功能设置	项目id 对应 设置类FunctionSetting
	private Map preMoneySetMap = new HashMap();	//预订功能设置  项目id 对应 设置类PreMoneySetting
	private CasSetting casSetting = null;	//财务相关设置
	private BaseRoomSetting baseRoomSetting = new BaseRoomSetting(); //基础房间的设置
	
	Map paramHashMap = new HashMap(); 

	public RoomDisplaySetting_BK(){
		this(null);
	}

	public RoomDisplaySetting_BK(Context ctx) {
		// 载入数据库设置		
		try {
			IParamControl iface = null;
			if(ctx == null){
				iface = ParamControlFactory.getRemoteInstance();
			}else{
				iface = ParamControlFactory.getLocalInstance(ctx);
			}
			
			paramHashMap = iface.getParamHashMap(OrgConstants.DEF_CU_ID,"com.kingdee.eas.fdc.sellhouse.sellhouse");
			Iterator iter = paramHashMap.keySet().iterator();
			while(iter.hasNext()) {
				String keyStr = (String)iter.next();
				ParamItemInfo paramItemInfo = (ParamItemInfo)paramHashMap.get(keyStr);
				String valueStr = (String)paramItemInfo.getValue();
				if(keyStr.startsWith("Fun")) {  //功能设置
					String spId = keyStr.substring(3);
					String values[] = valueStr.split(";");
					if(values.length==9) {
						FunctionSetting funSet = new FunctionSetting();
						funSet.setIsBasePrice(new Boolean(values[0]));
						funSet.setIsSignGathering(new Boolean(values[1]));
						funSet.setIsMortagage(new Boolean(values[2]));
						funSet.setIsActGathering(new Boolean(values[3]));
						funSet.setIsSincerSellOrder(new Boolean(values[4]));
						funSet.setIsSincerPrice(new Boolean(values[5]));
						funSet.setIsHouseMoney(new Boolean(values[6]));
						funSet.setIsEditPurAndSignDate(new Boolean(values[7]));
						funSet.setIsAdjustPrices(new Boolean(values[8]));
						functionSetMap.put(spId, funSet);
					}
				}else if(keyStr.startsWith("Pre")) {//预订功能设置
					String spId = keyStr.substring(3);
					String values[] = valueStr.split(";");
					if(values.length==4) {
						PreMoneySetting preSet = new PreMoneySetting();
						preSet.setPreLevelAmount(new BigDecimal(values[0]));
						preSet.setIsLevelModify(new Boolean(values[1]));
						preSet.setPreStandAmount(new BigDecimal(values[2]));
						preSet.setIsStandModify(new Boolean(values[3]));
						preMoneySetMap.put(spId, preSet);
					}
				}else if(keyStr.startsWith("FDCCRM_SHE_Cas_")){  //财务相关设置 
					IMoneyDefine iMoDeFace = MoneyDefineFactory.getRemoteInstance();
					if(ctx!=null) iMoDeFace = MoneyDefineFactory.getLocalInstance(ctx);
					String values[] = valueStr.split(";");
					if(values.length==2) {
						casSetting = new CasSetting();
						if(keyStr.equals(CRMConstants.FDCCRM_SHE_Cas_QuitMoneyType)) {
							casSetting.setQuitMoneyType(iMoDeFace.getMoneyDefineInfo("select name,number where id ='"+values[0]+"'"));
							casSetting.setIsQuitUpdate(new Boolean(values[1]));
						}else if(keyStr.equals(CRMConstants.FDCCRM_SHE_Cas_ChangeFeeType)) {
							casSetting.setChangeMoneyType(iMoDeFace.getMoneyDefineInfo("select name,number where id ='"+values[0]+"'"));
							casSetting.setIsChangeUpdate(new Boolean(values[1]));
						}else if(keyStr.equals(CRMConstants.FDCCRM_SHE_Cas_ChangeTrsfType)) {
							casSetting.setChangeRoomMoney(iMoDeFace.getMoneyDefineInfo("select name,number where id ='"+values[0]+"'"));
							casSetting.setIsChangeRoomUpdate(new Boolean(values[1]));
						}else if(keyStr.equals(CRMConstants.FDCCRM_SHE_Cas_ChangeBalcObj)) {
							casSetting.setChangeBalance((ChangeBalanceObjectEnum)ChangeBalanceObjectEnum.getEnumMap().get(values[0]));
							casSetting.setIsChangeObjectUpdate(new Boolean(values[1]));
						}else if(keyStr.equals(CRMConstants.FDCCRM_SHE_Cas_SincerMoneyType)) {
							casSetting.setSincerMoneyType(iMoDeFace.getMoneyDefineInfo("select name,number where id ='"+values[0]+"'"));
							casSetting.setIsChangeRoomUpdate(new Boolean(values[1]));
						}
					}
				}	
			}		
			
			SaleOrgUnitInfo curOrgUnit = null;
			if(ctx!=null) curOrgUnit = ContextUtil.getCurrentSaleUnit(ctx);
			else  curOrgUnit = SysContext.getSysContext().getCurrentSaleUnit();
			
			String[] numbers = {CRMConstants.FDCCRM_ROOM_AttachDisType,CRMConstants.FDCCRM_ROOM_DispField,CRMConstants.FDCCRM_ROOM_FontColor,CRMConstants.FDCCRM_ROOM_FontName
					,CRMConstants.FDCCRM_ROOM_FontSize,CRMConstants.FDCCRM_ROOM_FontStyle,CRMConstants.FDCCRM_ROOM_Height,CRMConstants.FDCCRM_ROOM_Width
					,CRMConstants.FDCCRM_SHE_IsSpcilOnAudit,CRMConstants.FDCCRM_SHE_ROOM_InitColor,CRMConstants.FDCCRM_SHE_ROOM_KeepDownColor,CRMConstants.FDCCRM_SHE_ROOM_NoSaleColor
					,CRMConstants.FDCCRM_SHE_ROOM_OnShowColor,CRMConstants.FDCCRM_SHE_ROOM_PrePurColor,CRMConstants.FDCCRM_SHE_ROOM_PurchseColor,CRMConstants.FDCCRM_SHE_ROOM_SignColor
					};
			Map baseMap = ParamManager.getParamHashMap(ctx, numbers, curOrgUnit.getId().toString());
			String attchType = (String)baseMap.get(CRMConstants.FDCCRM_ROOM_AttachDisType);
			if(attchType!=null)		baseRoomSetting.setAttachDisType((new Integer(attchType)).intValue());
			baseRoomSetting.setDisplayField((String)baseMap.get(CRMConstants.FDCCRM_ROOM_DispField));
			String fontColor = (String)baseMap.get(CRMConstants.FDCCRM_ROOM_FontColor);
			if(fontColor!=null)	baseRoomSetting.setFrontColor(new Color((new Integer(fontColor)).intValue()));
			String fontName = (String)baseMap.get(CRMConstants.FDCCRM_ROOM_FontName);
			String fontStyle = (String)baseMap.get(CRMConstants.FDCCRM_ROOM_FontStyle);
			String fontSize = (String)baseMap.get(CRMConstants.FDCCRM_ROOM_FontSize);
			if(fontName!=null && fontStyle!=null && fontSize!=null) {
				Font font = new Font(fontName,(new Integer(fontStyle)).intValue(),(new Integer(fontSize)).intValue());
				baseRoomSetting.setFont(font);
			}
			String roomHeight = (String)baseMap.get(CRMConstants.FDCCRM_ROOM_Height);
			if(roomHeight!=null) baseRoomSetting.setRoomHeight((new Integer(roomHeight)).intValue());
			String roomWidth = (String)baseMap.get(CRMConstants.FDCCRM_ROOM_Width);
			if(roomWidth!=null) baseRoomSetting.setRoomHeight((new Integer(roomWidth)).intValue());
			Object isSpilAudit = baseMap.get(CRMConstants.FDCCRM_SHE_IsSpcilOnAudit);
			if(isSpilAudit!=null) baseRoomSetting.setAuditDate(((Boolean)isSpilAudit).booleanValue());
			String initColor = (String)baseMap.get(CRMConstants.FDCCRM_SHE_ROOM_InitColor);
			if(initColor!=null) baseRoomSetting.setInitColor(new Color((new Integer(initColor)).intValue()));
			String keepDownColor = (String)baseMap.get(CRMConstants.FDCCRM_SHE_ROOM_KeepDownColor);
			if(keepDownColor!=null) baseRoomSetting.setKeepDownColor(new Color((new Integer(keepDownColor)).intValue()));
			String noSaleColor = (String)baseMap.get(CRMConstants.FDCCRM_SHE_ROOM_NoSaleColor);
			if(noSaleColor!=null) baseRoomSetting.setNoSellhouseColor(new Color((new Integer(noSaleColor)).intValue()));
			String noShowColor = (String)baseMap.get(CRMConstants.FDCCRM_SHE_ROOM_OnShowColor);
			if(noShowColor!=null) baseRoomSetting.setOnShowColor(new Color((new Integer(noShowColor)).intValue()));
			String prePurColor = (String)baseMap.get(CRMConstants.FDCCRM_SHE_ROOM_PrePurColor);
			if(prePurColor!=null) baseRoomSetting.setPrePurColor(new Color((new Integer(prePurColor)).intValue()));
			String purchaseColor = (String)baseMap.get(CRMConstants.FDCCRM_SHE_ROOM_PurchseColor);
			if(purchaseColor!=null) baseRoomSetting.setPurColor(new Color((new Integer(purchaseColor)).intValue()));
			String signColor = (String)baseMap.get(CRMConstants.FDCCRM_SHE_ROOM_SignColor);
			if(signColor!=null) baseRoomSetting.setSignColor(new Color((new Integer(signColor)).intValue()));			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void saveNew() throws Exception {			
		Iterator iter = functionSetMap.keySet().iterator();
		while(iter.hasNext()) {
			String idStr = (String)iter.next();
			FunctionSetting funSet = (FunctionSetting)functionSetMap.get(idStr);
			String keyValues = funSet.getIsBasePrice().toString() + ";";
			keyValues += funSet.getIsSignGathering().toString() + ";";
			keyValues += funSet.getIsMortagage().toString() + ";";
			keyValues += funSet.getIsActGathering().toString() + ";";
			keyValues += funSet.getIsSincerSellOrder().toString() + ";";
			keyValues += funSet.getIsSincerPrice().toString() + ";";
			keyValues += funSet.getIsHouseMoney().toString() + ";";
			keyValues += funSet.getIsEditPurAndSignDate().toString() + ";";
			keyValues += funSet.getIsAdjustPrices().toString();
			
			String keyStr = "Fun"+idStr;
			if(paramHashMap.get(keyStr)!=null)
				ParamControlFactory.getRemoteInstance().updateParamItemByNumberAndOrg(keyStr, OrgConstants.DEF_CU_ID, keyValues);
			else{
				addParamItem(keyStr,keyValues);
			}
		}
		
		iter = preMoneySetMap.keySet().iterator();
		while(iter.hasNext()) {
			String idStr = (String)iter.next();
			PreMoneySetting preSet = (PreMoneySetting)preMoneySetMap.get(idStr);
			String keyValues = preSet.getPreLevelAmount() + ";";
			keyValues += preSet.getIsLevelModify().toString() + ";";
			keyValues += preSet.getPreStandAmount() + ";";
			keyValues += preSet.getIsStandModify().toString();
			
			String keyStr = "Pre"+idStr;
			if(paramHashMap.get(keyStr)!=null)
				ParamControlFactory.getRemoteInstance().updateParamItemByNumberAndOrg(keyStr, OrgConstants.DEF_CU_ID, keyValues);
			else
				addParamItem(keyStr,keyValues);
		}
		
		String keyValues = casSetting.getQuitMoneyType().getId().toString()+";"+casSetting.getIsQuitUpdate().toString();
		if(paramHashMap.get(CRMConstants.FDCCRM_SHE_Cas_QuitMoneyType)!=null)
			ParamControlFactory.getRemoteInstance().updateParamItemByNumberAndOrg(CRMConstants.FDCCRM_SHE_Cas_QuitMoneyType, OrgConstants.DEF_CU_ID, keyValues);
		else
			addParamItem(CRMConstants.FDCCRM_SHE_Cas_QuitMoneyType,keyValues);
		keyValues = casSetting.getChangeMoneyType().getId().toString()+";"+casSetting.getIsChangeUpdate().toString();
		if(paramHashMap.get(CRMConstants.FDCCRM_SHE_Cas_ChangeFeeType)!=null)
			ParamControlFactory.getRemoteInstance().updateParamItemByNumberAndOrg(CRMConstants.FDCCRM_SHE_Cas_ChangeFeeType, OrgConstants.DEF_CU_ID, keyValues);
		else
			addParamItem(CRMConstants.FDCCRM_SHE_Cas_ChangeFeeType,keyValues);
		keyValues = casSetting.getChangeRoomMoney().getId().toString()+";"+casSetting.getIsChangeRoomUpdate().toString();
		if(paramHashMap.get(CRMConstants.FDCCRM_SHE_Cas_ChangeTrsfType)!=null)
			ParamControlFactory.getRemoteInstance().updateParamItemByNumberAndOrg(CRMConstants.FDCCRM_SHE_Cas_ChangeTrsfType, OrgConstants.DEF_CU_ID, keyValues);
		else
			addParamItem(CRMConstants.FDCCRM_SHE_Cas_ChangeTrsfType,keyValues);
		keyValues = casSetting.getChangeBalance().getValue()+";"+casSetting.getIsChangeObjectUpdate().toString();
		if(paramHashMap.get(CRMConstants.FDCCRM_SHE_Cas_ChangeBalcObj)!=null)
			ParamControlFactory.getRemoteInstance().updateParamItemByNumberAndOrg(CRMConstants.FDCCRM_SHE_Cas_ChangeBalcObj, OrgConstants.DEF_CU_ID, keyValues);
		else
			addParamItem(CRMConstants.FDCCRM_SHE_Cas_ChangeBalcObj,keyValues);
		keyValues = casSetting.getSincerMoneyType().getId().toString()+";"+casSetting.getIsSincerUpdate().toString();
		if(paramHashMap.get(CRMConstants.FDCCRM_SHE_Cas_SincerMoneyType)!=null)
			ParamControlFactory.getRemoteInstance().updateParamItemByNumberAndOrg(CRMConstants.FDCCRM_SHE_Cas_SincerMoneyType, OrgConstants.DEF_CU_ID, keyValues);
		else
			addParamItem(CRMConstants.FDCCRM_SHE_Cas_SincerMoneyType,keyValues);


/*
 	保存界面只是针对其它部分的，所以以下是不需要的
  		SaleOrgUnitInfo curOrgUnit = SysContext.getSysContext().getCurrentSaleUnit();
		ParamManager.updateParamItemByNumberAndOrg(null,CRMConstants.FDCCRM_ROOM_AttachDisType, curOrgUnit.getId().toString(), String.valueOf(baseRoomSetting.getAttachDisType()));
		ParamManager.updateParamItemByNumberAndOrg(null,CRMConstants.FDCCRM_ROOM_DispField, curOrgUnit.getId().toString(), String.valueOf(baseRoomSetting.getDisplayField()));
		ParamManager.updateParamItemByNumberAndOrg(null,CRMConstants.FDCCRM_ROOM_FontColor, curOrgUnit.getId().toString(), String.valueOf(baseRoomSetting.getFrontColor().getRGB()));
		ParamManager.updateParamItemByNumberAndOrg(null,CRMConstants.FDCCRM_ROOM_FontName, curOrgUnit.getId().toString(), baseRoomSetting.getFont().getFontName());
		ParamManager.updateParamItemByNumberAndOrg(null,CRMConstants.FDCCRM_ROOM_FontSize, curOrgUnit.getId().toString(), String.valueOf(baseRoomSetting.getFont().getSize()));
		ParamManager.updateParamItemByNumberAndOrg(null,CRMConstants.FDCCRM_ROOM_FontStyle, curOrgUnit.getId().toString(), String.valueOf(baseRoomSetting.getFont().getStyle()));
		ParamManager.updateParamItemByNumberAndOrg(null,CRMConstants.FDCCRM_ROOM_Height, curOrgUnit.getId().toString(), String.valueOf(baseRoomSetting.getRoomHeight()));
		ParamManager.updateParamItemByNumberAndOrg(null,CRMConstants.FDCCRM_ROOM_Width, curOrgUnit.getId().toString(), String.valueOf(baseRoomSetting.getRoomWidth()));
		ParamManager.updateParamItemByNumberAndOrg(null,CRMConstants.FDCCRM_SHE_IsSpcilOnAudit, curOrgUnit.getId().toString(), String.valueOf(baseRoomSetting.isAuditDate()));
		ParamManager.updateParamItemByNumberAndOrg(null,CRMConstants.FDCCRM_SHE_ROOM_InitColor, curOrgUnit.getId().toString(), String.valueOf(baseRoomSetting.getInitColor().getRGB()));
		ParamManager.updateParamItemByNumberAndOrg(null,CRMConstants.FDCCRM_SHE_ROOM_KeepDownColor, curOrgUnit.getId().toString(), String.valueOf(baseRoomSetting.getKeepDownColor().getRGB()));
		ParamManager.updateParamItemByNumberAndOrg(null,CRMConstants.FDCCRM_SHE_ROOM_NoSaleColor, curOrgUnit.getId().toString(), String.valueOf(baseRoomSetting.getNoSellhouseColor().getRGB()));
		ParamManager.updateParamItemByNumberAndOrg(null,CRMConstants.FDCCRM_SHE_ROOM_OnShowColor, curOrgUnit.getId().toString(), String.valueOf(baseRoomSetting.getOnShowColor().getRGB()));
		ParamManager.updateParamItemByNumberAndOrg(null,CRMConstants.FDCCRM_SHE_ROOM_PrePurColor, curOrgUnit.getId().toString(), String.valueOf(baseRoomSetting.getPrePurColor().getRGB()));
		ParamManager.updateParamItemByNumberAndOrg(null,CRMConstants.FDCCRM_SHE_ROOM_PurchseColor, curOrgUnit.getId().toString(), String.valueOf(baseRoomSetting.getPurColor().getRGB()));
		ParamManager.updateParamItemByNumberAndOrg(null,CRMConstants.FDCCRM_SHE_ROOM_SignColor, curOrgUnit.getId().toString(), String.valueOf(baseRoomSetting.getSignColor().getRGB()));
		*/
	}
	
	
	private void addParamItem(String keyStr,String keyValues) throws Exception {
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setId(BOSUuid.create(paramInfo.getBOSType()));
		paramInfo.setIsUserDefine(true);
		paramInfo.setNumber(keyStr);
		paramInfo.setIsGroupControl(true);
		paramInfo.setCanBeModified(true);
		paramInfo.setOrgType(OrgType.Sale);
		paramInfo.setDataType(ParamDataType.String);
		ParamValueRangeInfo range = new ParamValueRangeInfo();
		paramInfo.setTheValueRange(range);
		paramInfo.setSubSysID("com.kingdee.eas.fdc.sellhouse.sellhouse");
		ParamItemInfo paramItemInfo = new ParamItemInfo();
		paramItemInfo.setValue(keyValues);
		paramItemInfo.setIsControlSub(false);
		paramItemInfo.setIsModify(false);
		paramItemInfo.setOrgUnitID(SysContext.getSysContext().getCurrentSaleUnit());
		ParamControlFactory.getRemoteInstance().addParamItem(paramItemInfo, paramInfo);
		
		paramHashMap.put(keyStr, keyValues);
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
		}else if("noSellhouse".equals(key))		{
			return this.baseRoomSetting.getNoSellhouseColor();
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
	




}
