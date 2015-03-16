package com.kingdee.eas.fdc.finance.client;

/**
 * 包装体
 * 房地产单据使用预算项目，标明哪几个属性设置预算BgItemID,BgItemNumber,BgItemName
 * @author cassiel 
 * @date 2010-09-30
 *
 */
public class FDCBgItemProp {
	String bgItemIDProp = null;
	String bgItemNumberProp = null;
	String bgItemNameProp = null;
	
	/**bgItemIDProp，bgItemNumberProp，bgItemNameProp 的属性名 */
	public FDCBgItemProp(String bgItemIDProp, String bgItemNumberProp, String bgItemNameProp){
		this.bgItemIDProp = bgItemIDProp;
		this.bgItemNumberProp = bgItemNumberProp;
		this.bgItemNameProp = bgItemNameProp;
	}
	public String getBgItemIDProp(){
		return this.bgItemIDProp ;
	};
	public String getBgItemNumberProp(){
		return this.bgItemNumberProp ;
	}
	public String getBgItemNameProp(){
		return this.bgItemNameProp ;
	}
}
