package com.kingdee.eas.fdc.finance.client;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.util.ParamManager;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.ma.budget.VisualItemInfo;

/**
 * 处理付款单界面根据预算管理参数【BG056资金业务单据使用项目】显示预算项目还是计划项目的辅助类
 * 
 * @author cassiel 2010-09-30
 */
public class FDCDealFPOrBudgetItemUtil {
	/**
	 * 描述:返回false 界面显示预算项目 否则显示计划项目
	 */
	public static boolean getIsFpOrBg() {
		String paramValue;
		try {
			paramValue = ParamManager.getParamValue(null, null, "BG056");
			if (FMHelper.isEmpty(paramValue) || ("0").equals(paramValue)) {
				return false;
			} else {
				return true;
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static FDCBgItemProp getBgItemProps() {
		return new FDCBgItemProp("outBgItemId", "outBgItemNumber",
				"outBgItemName");
	}

	public static void loadFieldsBgItem(KDBizPromptBox prmt, CoreBaseInfo info,
			FDCBgItemProp bgItemProp) {
		String valueBgItemID = info.getString(bgItemProp.getBgItemIDProp());
		String valueBgItemNumber = info.getString(bgItemProp
				.getBgItemNumberProp());
		String valueBgItemName = info.getString(bgItemProp.getBgItemNameProp());
		if (valueBgItemNumber == null) {
			prmt.setValue(null);
			return;
		} else {
			VisualItemInfo bgItem = new VisualItemInfo();
			BOSUuid uuId = valueBgItemID == null ? null : BOSUuid
					.read(valueBgItemID);
			bgItem.setId(uuId);
			bgItem.setNumber(valueBgItemNumber);
			bgItem.setName(valueBgItemName);
			prmt.setValue(bgItem);
		}
	}

	/** 预算控件，info，info的属性名:预算项目ID,预算项目Number,预算项目name */

	public static void storeFieldsBgItem(KDBizPromptBox prmt,
			CoreBaseInfo info, FDCBgItemProp bgItemProp) {
		if (prmt.getValue() == null) {
			info.setString(bgItemProp.getBgItemIDProp(), null);
			info.setString(bgItemProp.getBgItemNumberProp(), null);
			info.setString(bgItemProp.getBgItemNameProp(), null);
		} else {
			VisualItemInfo bgItem = (VisualItemInfo) prmt.getValue();
			String bgItemId = bgItem.getId() == null ? null : bgItem.getId()
					.toString();
			info.setString(bgItemProp.getBgItemIDProp(), bgItemId);
			info
					.setString(bgItemProp.getBgItemNumberProp(), bgItem
							.getNumber());
			info.setString(bgItemProp.getBgItemNameProp(), bgItem.getName());
		}
	}
}
