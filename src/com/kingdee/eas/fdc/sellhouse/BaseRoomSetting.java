package com.kingdee.eas.fdc.sellhouse;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;

public class BaseRoomSetting implements Serializable {
	
	private int attachDisType = 0;
	private Font font = new Font("宋体", 5, 10);	
	private Color frontColor = Color.BLACK;		//初始字体颜色  - 黑
	private int roomHeight = 45;
	private int roomWidth = 40;
	private String displayField = "number";
	
	
	private Color initColor = Color.LIGHT_GRAY;  //初始房间 未推盘-灰 

	private Color onShowColor = Color.WHITE;	 //初始房间 待售 - 白

	private Color keepDownColor = Color.GREEN;   //初始房间 保留 - 绿
	
	private Color controlColor = Color.BLUE;   //初始房间 销控 - 绿
	
	private Color prePurColor = Color.YELLOW;    //初始房间  预定 - 黄

	private Color purColor = Color.ORANGE;		 //初始房间  认购 - 橙色

	private Color signColor = Color.RED;		//初始房间  签约 - 黄色
	
	private Color noSellhouseColor = Color.BLACK;	//没有售楼属性 - 洋红
	
	//增加认购收款颜色默认gy 2010-12-10  飞鹰计划（需求：刘威）
	private Color sinReColor = Color.PINK;      //初始房间  认购收款 —  
	
	public Color getSinReColor() {
		return sinReColor;
	}

	public void setSinReColor(Color sinReColor) {
		this.sinReColor = sinReColor;
	}

	private Color sincerPurColor = new Color(255,153,204);	

	private boolean isAuditDate = false; //特殊业务以审批时间为准	
	
	private int lockTime =0; //房间锁定时间
	
	private Color chooseColor = new Color(128,0,128);	//选房颜色 -  r=128,g=0,b=128
	
	public int getAttachDisType() {
		return attachDisType;
	}

	public void setAttachDisType(int attachDisType) {
		this.attachDisType = attachDisType;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public Color getFrontColor() {
		return frontColor;
	}

	public void setFrontColor(Color frontColor) {
		this.frontColor = frontColor;
	}

	public int getRoomHeight() {
		return roomHeight;
	}

	public void setRoomHeight(int roomHeight) {
		this.roomHeight = roomHeight;
	}

	public int getRoomWidth() {
		return roomWidth;
	}

	public void setRoomWidth(int roomWidth) {
		this.roomWidth = roomWidth;
	}

	public String getDisplayField() {
		return displayField;
	}

	public void setDisplayField(String displayField) {
		this.displayField = displayField;
	}



	public Color getInitColor() {
		return initColor;
	}

	public void setInitColor(Color initColor) {
		this.initColor = initColor;
	}

	public Color getOnShowColor() {
		return onShowColor;
	}

	public void setOnShowColor(Color onShowColor) {
		this.onShowColor = onShowColor;
	}

	public Color getKeepDownColor() {
		return keepDownColor;
	}

	public void setKeepDownColor(Color keepDownColor) {
		this.keepDownColor = keepDownColor;
	}
	public Color getControlColor() {
		return controlColor;
	}

	public void setControlColor(Color controlColor) {
		this.controlColor = controlColor;
	}
	public Color getPrePurColor() {
		return prePurColor;
	}

	public void setPrePurColor(Color prePurColor) {
		this.prePurColor = prePurColor;
	}

	public Color getPurColor() {
		return purColor;
	}

	public void setPurColor(Color purColor) {
		this.purColor = purColor;
	}

	public Color getSignColor() {
		return signColor;
	}

	public void setSignColor(Color signColor) {
		this.signColor = signColor;
	}

	public Color getNoSellhouseColor() {
		return noSellhouseColor;
	}

	public void setNoSellhouseColor(Color noSellhouseColor) {
		this.noSellhouseColor = noSellhouseColor;
	}
	
	public Color getSincerPurColor() {
		return sincerPurColor;
	}

	public void setSincerPurColor(Color sincerPurColor) {
		this.sincerPurColor = sincerPurColor;
	}

	public boolean isAuditDate() {
		return isAuditDate;
	}

	public void setAuditDate(boolean isAuditDate) {
		this.isAuditDate = isAuditDate;
	}
	
	public int getLockTime() {
		return lockTime;
	}

	public void setLockTime(int lockTime) {
		this.lockTime = lockTime;
	}
	
	public Color getChooseColor() {
		return chooseColor;
	}

	public void setChooseColor(Color chooseColor) {
		this.chooseColor = chooseColor;
	}
}
