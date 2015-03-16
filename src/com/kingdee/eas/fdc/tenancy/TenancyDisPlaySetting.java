package com.kingdee.eas.fdc.tenancy;

import java.awt.Color;
import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.IDisplayRule;
import com.kingdee.eas.fdc.sellhouse.client.IBackgroundColor;
import com.kingdee.util.StringUtils;
import com.sun.xml.messaging.saaj.util.ByteInputStream;

/*
 * 租赁参数 增加方法
 * 1.增加常量KEY
 * 2.checkMap方法中增加个checkValue
 * 3.增加所增加常量的get/set方法
 * */
public class TenancyDisPlaySetting implements Serializable, IDisplayRule, IBackgroundColor {

	private static final long serialVersionUID = 4719371343189186310L;

	private static final String KEY_RECORD_ID = "recordUUID";
	
	//房间显示页签
	private static final String KEY_ROOM_HEIGHT = "roomHeight";
	private static final String KEY_ROOM_WIDTH = "roomWidth";
	private static final String KEY_DISPLAY_FIELD = "displayField";
	private static final String KEY_ATTACH_DIS_TYPE = "attachDisType";
	private static final String KEY_FONT = "font";
	private static final String KEY_FONT_COLOR = "frontColor";
	
	//租赁控制页签
	private static final String KEY_UNTENANCY_COLOR = "unTenancyColor";
	private static final String KEY_WAIT_TENANCY_COLOR = "waitTenancyColor";
	private static final String KEY_NEW_TENANCY_COLOR = "newTenancyColor";
	private static final String KEY_CONTINUE_TENANCY_COLOR = "continueTenancyColor";
	private static final String KEY_ENLARGE_TENANCY_COLOR = "enlargeTenancyColor";
	private static final String KEY_KEEP_TENANCY_COLOR = "keepTenancyColor";
	private static final String KEY_NO_TENANCY_COLOR = "noTenancyColor";
	private static final String KEY_SINCER_OBLI_COLOR = "sincerObliColor";
	
	//其他设置
	//收款单套打以收款单为准
	private static final String KEY_ISRECEIVING = "isReceiving";
	//收款单套打以款项为准
	private static final String KEY_ISMONEYDEFINE = "isMoneyDefine";	
	//退租申请审批过程中是否允许财务处理
	private static String KEY_ISAUDITREF = "isAuditRef";		
	
	private static final String KEY_MONEYMAP = "moneyMap";
	private Map moneyMap;
	
	// 由于该设置序列化存储，为避免每次修改该类结构后,反序列化取值时解析出错的问题，使用Map来存储这些配置项
	private Map mainMap;

	public TenancyDisPlaySetting() {
		String recordId = loadData();
		checkMap();
		mainMap.put(KEY_RECORD_ID, recordId);
	}

	private void checkMap() {
		if(mainMap == null){
			mainMap = new HashMap();
		}
		//房间显示页签
		checkValue(mainMap, KEY_ROOM_HEIGHT, new Integer(45));
		checkValue(mainMap, KEY_ROOM_WIDTH, new Integer(40));
		checkValue(mainMap, KEY_DISPLAY_FIELD, "number");
		checkValue(mainMap, KEY_ATTACH_DIS_TYPE, new Integer(0));
		checkValue(mainMap, KEY_FONT, new Font("宋体", 5, 10));
		checkValue(mainMap, KEY_FONT_COLOR, Color.BLACK);
		
		//租赁控制页签
		checkValue(mainMap, KEY_UNTENANCY_COLOR, Color.CYAN);
		checkValue(mainMap, KEY_WAIT_TENANCY_COLOR, Color.WHITE);
		checkValue(mainMap, KEY_NEW_TENANCY_COLOR, Color.RED);
		checkValue(mainMap, KEY_CONTINUE_TENANCY_COLOR, Color.BLUE);
		checkValue(mainMap, KEY_ENLARGE_TENANCY_COLOR, Color.YELLOW);
		checkValue(mainMap, KEY_KEEP_TENANCY_COLOR, Color.GREEN);
		checkValue(mainMap, KEY_NO_TENANCY_COLOR, Color.GRAY);
		checkValue(mainMap, KEY_SINCER_OBLI_COLOR, Color.ORANGE);
		
		checkValue(mainMap, KEY_ISRECEIVING,Boolean.TRUE);
		checkValue(mainMap, KEY_ISMONEYDEFINE,Boolean.FALSE);
		
		checkValue(mainMap, KEY_ISAUDITREF,Boolean.FALSE);
		checkValue(mainMap, KEY_MONEYMAP,moneyMap);
	}

	/**
	 * 检查某项值是否为空,如果为空,设置默认值
	 * */
	private void checkValue(Map map, String key, Object defaultValue) {
		Object obj = map.get(key);
		if(obj == null){
			map.put(key, defaultValue);
		}
	}

	private String loadData() {
		// 载入数据库设置
		String recordId = null;
		try {
			TenancyDisPlaySetCollection priceSetCollection = TenancyDisPlaySetFactory.getRemoteInstance().getTenancyDisPlaySetCollection();
			if (priceSetCollection.size() > 0) {
				TenancyDisPlaySetInfo record = priceSetCollection.get(0);
				recordId = record.getId().toString();
				byte[] b = record.getValue();
				ObjectInputStream objectInputStream = new ObjectInputStream(new ByteInputStream(b, 0, b.length));
				mainMap = (Map) objectInputStream.readObject();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return recordId;
	}

	public void save() throws Exception {
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream);
		objectOutputStream.writeObject(mainMap);
		byte[] b = arrayOutputStream.toByteArray();
		TenancyDisPlaySetInfo setInfo = new TenancyDisPlaySetInfo();
		setInfo.setValue(b);
		String recordId = (String) mainMap.get(KEY_RECORD_ID);
		if(recordId != null){
			setInfo.setId(BOSUuid.read(recordId));
		}
		TenancyDisPlaySetFactory.getRemoteInstance().submit(setInfo);
	}

	public int getAttachDisType() {
		return ((Integer)mainMap.get(KEY_ATTACH_DIS_TYPE)).intValue();
	}

	public void setAttachDisType(int attachDisType) {
		mainMap.put(KEY_ATTACH_DIS_TYPE, new Integer(attachDisType));
	}

	public Font getFont() {
		return (Font) mainMap.get(KEY_FONT);
	}

	public void setFont(Font font) {
		mainMap.put(KEY_FONT, font);
	}

	public Color getFrontColor() {
		return (Color) mainMap.get(KEY_FONT_COLOR);
	}

	public void setFrontColor(Color color) {
		mainMap.put(KEY_FONT_COLOR, color);
	}

	public int getRoomHeight() {
		return ((Integer)mainMap.get(KEY_ROOM_HEIGHT)).intValue();
	}

	public void setRoomHeight(int roomHeight) {
		mainMap.put(KEY_ROOM_HEIGHT, new Integer(roomHeight));
	}

	public int getRoomWidth() {
		return ((Integer)mainMap.get(KEY_ROOM_WIDTH)).intValue();
	}

	public void setRoomWidth(int roomWidth) {
		mainMap.put(KEY_ROOM_WIDTH, new Integer(roomWidth));
	}

	public String getDisplayField() {
		return (String) mainMap.get(KEY_DISPLAY_FIELD);
	}

	public void setDisplayField(String displayField) {
		mainMap.put(KEY_DISPLAY_FIELD, displayField);
	}

	public Color getUnTenancyColor() {
		return (Color) mainMap.get(KEY_UNTENANCY_COLOR);
	}

	public void setUnTenancyColor(Color color) {
		mainMap.put(KEY_UNTENANCY_COLOR, color);
	}

	public Color getWaitTenancyColor() {
		return (Color) mainMap.get(KEY_WAIT_TENANCY_COLOR);
	}
	
	public boolean getIsAuditRef(){
		return ((Boolean)mainMap.get(KEY_ISAUDITREF)).booleanValue();
	}
	
	public boolean getIsReceiving() {
		return ((Boolean)mainMap.get(KEY_ISRECEIVING)).booleanValue();
	}

	public boolean getIsMoneyDeifine() {
		return ((Boolean)mainMap.get(KEY_ISMONEYDEFINE)).booleanValue();
	}
	
	public void setIsReceiving(boolean boo)
	{
		mainMap.put(KEY_ISRECEIVING, new Boolean(boo));
	}
	
	public void setIsMoneyDefine(boolean boo)
	{
		mainMap.put(KEY_ISMONEYDEFINE,new Boolean(boo));
	}
	
	public void setIsAuditRef(boolean boo)
	{
		mainMap.put(KEY_ISAUDITREF, new Boolean(boo));
	}

	public void setWaitTenancyColor(Color color) {
		mainMap.put(KEY_WAIT_TENANCY_COLOR, color);
	}
	
	public void setMoneyMap(Map moneyMap)
	{
		mainMap.put(KEY_MONEYMAP, moneyMap);
	}

	public Map getMoneyMap()
	{
		return (Map)mainMap.get(KEY_MONEYMAP);
	}
	
	public Color getNewTenancyColor() {
		return (Color) mainMap.get(KEY_NEW_TENANCY_COLOR);
	}

	public void setNewTenancyColor(Color color) {
		mainMap.put(KEY_NEW_TENANCY_COLOR, color);
	}

	public Color getContinueTenancyColor() {
		return (Color) mainMap.get(KEY_CONTINUE_TENANCY_COLOR);
	}

	public void setContinueTenancyColor(Color color) {
		mainMap.put(KEY_CONTINUE_TENANCY_COLOR, color);
	}

	public Color getEnlargeTenancyColor() {
		return (Color) mainMap.get(KEY_ENLARGE_TENANCY_COLOR);
	}

	public void setEnlargeTenancyColor(Color color) {
		mainMap.put(KEY_ENLARGE_TENANCY_COLOR, color);
	}

	public Color getKeepTenancyColor() {
		return (Color) mainMap.get(KEY_KEEP_TENANCY_COLOR);
	}

	public void setKeepTenancyColor(Color color) {
		mainMap.put(KEY_KEEP_TENANCY_COLOR, color);
	}

	public Color getNoTenancyColor() {
		return (Color) mainMap.get(KEY_NO_TENANCY_COLOR);
	}

	public void setNoTenancyColor(Color color) {
		mainMap.put(KEY_NO_TENANCY_COLOR, color);
	}

	public Color getSincerObliColor() {
		return (Color) mainMap.get(KEY_SINCER_OBLI_COLOR);
	}

	public void setSincerObliColor(Color color) {
		mainMap.put(KEY_SINCER_OBLI_COLOR, color);
	}		

	public Color getCellBackgroundColor(String tenancyStatEnum) {
		if (StringUtils.isEmpty(tenancyStatEnum) || TenancyStateEnum.UNTENANCY_VALUE.equals(tenancyStatEnum)) {
			return this.getUnTenancyColor();
		} else if (TenancyStateEnum.WAITTENANCY_VALUE.equals(tenancyStatEnum)) {
			return this.getWaitTenancyColor();
		} else if (TenancyStateEnum.NEWTENANCY_VALUE.equals(tenancyStatEnum)) {
			return this.getNewTenancyColor();
		} else if (TenancyStateEnum.CONTINUETENANCY_VALUE.equals(tenancyStatEnum)) {
			return this.getContinueTenancyColor();
		} else if (TenancyStateEnum.ENLARGETENANCY_VALUE.equals(tenancyStatEnum)) {
			return this.getEnlargeTenancyColor();
		} else if (TenancyStateEnum.KEEPTENANCY_VALUE.equals(tenancyStatEnum)) {
			return this.getKeepTenancyColor();
		} else if (TenancyStateEnum.SINCEROBLIGATE_VALUE.equals(tenancyStatEnum)) {
			return this.getSincerObliColor();
		} else if ("noTenancy".equals(tenancyStatEnum)) {
			return this.getNoTenancyColor();
		}
		return null;
	}	

	public MoneySysTypeEnum getSysType() {
		return MoneySysTypeEnum.TenancySys;
	}

}
