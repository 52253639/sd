package com.kingdee.eas.fdc.sellhouse.app;

import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.sellhouse.MortagageControlFactory;
import com.kingdee.eas.fdc.sellhouse.MortagageControlInfo;
import com.kingdee.eas.fdc.sellhouse.MortagageEnum;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.util.NumericExceptionSubItem;

public class MortagageControlControllerBean extends AbstractMortagageControlControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.MortagageControlControllerBean");

    /**
	 * 重写校验:
	 * 不检查编码名称是否重复
	 */
	protected void checkBill(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		this._checkNumberBlank(ctx, null, model);
		this._checkNumberDup(ctx, null, model);
	}

	/**
	 * 解除抵押
	 */
	protected boolean _antiMortagage(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		MortagageControlInfo info = (MortagageControlInfo)model;
		if (info != null) {
			RoomInfo room = info.getRoom();
			boolean isHasAntiMortagage = false;
			if (room != null && room.size() > 0) {
				// 未推盘、待售、保留、预约排号状态房间才可以被抵押
				if (room.getSellState() != null) {
					if (room.getSellState().getValue().equals("Init")
							&& room.getSellState().getValue().equals(
									"Onshow")
							&& room.getSellState().getValue().equals(
									"KeepDown")
							&& room.getSellState().getValue().equals(
									"PrePurchase")) {
						throw new EASBizException(new NumericExceptionSubItem("100","房间: "+room.getNumber() +" 无需解押" ));
					}
				}
				isHasAntiMortagage = true;
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("mortagageState");
				selector.add("antiMortagagor");
				selector.add("antiMortagageDate");
				info.setMortagageState(MortagageEnum.ANTIMORTAGAGE);
				info.setAntiMortagagor((UserInfo) (SysContext
						.getSysContext().getCurrentUserInfo()));
				info.setAntiMortagageDate(new Date());
				MortagageControlFactory.getLocalInstance(ctx)
						.updatePartial(info, selector);
				selector = new SelectorItemCollection();
				selector.add("isMortagaged");
				room.setIsMortagaged(false);
				RoomFactory.getLocalInstance(ctx).updatePartial(room,
						selector);
			}
			return isHasAntiMortagage;
		}else{
			return false;
		}
		
	}

	/**
	 * 审批
	 */
	protected boolean _audit(Context ctx, IObjectValue model) throws BOSException,
			EASBizException {
		MortagageControlInfo info = (MortagageControlInfo)model;
		if (info.getMortagageState().equals(MortagageEnum.SUBMITTED) ||info.getMortagageState().equals(MortagageEnum.AUDITTING)) {
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("mortagageState");
			selector.add("auditor");
			selector.add("auditTime");
			info.setMortagageState(MortagageEnum.AUDITTED);
			
			info.setAuditor((UserInfo) (SysContext.getSysContext()
					.getCurrentUserInfo()));
			info.setAuditTime(new Date());
			MortagageControlFactory.getLocalInstance(ctx).updatePartial(info,
					selector);
			RoomInfo room = info.getRoom();
			selector = new SelectorItemCollection();
			selector.add("isMortagaged");
			room.setIsMortagaged(true);
			RoomFactory.getLocalInstance(ctx).updatePartial(room,
					selector);
			return true;
		} else{
			return false;
		}
	}

	/**
	 * 反审批
	 */
	protected boolean _unAudit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		MortagageControlInfo info = (MortagageControlInfo)model;
		if (info.getMortagageState().equals(MortagageEnum.AUDITTED) ||info.getMortagageState().equals(MortagageEnum.AUDITTING)) {
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("mortagageState");
			info.setMortagageState(MortagageEnum.SUBMITTED);
			MortagageControlFactory.getLocalInstance(ctx).updatePartial(info,
					selector);
			RoomInfo room = info.getRoom();
			selector = new SelectorItemCollection();
			selector.add("isMortagaged");
			room.setIsMortagaged(false);
			RoomFactory.getLocalInstance(ctx).updatePartial(room,
					selector);
			return true;
		} else{
			return false;
		}
	}
	
	/**
	 * 重载此方法，设可继续保存
	 */
	protected boolean _checkCanSubmit(Context ctx, String id)
			throws BOSException, EASBizException {
		return true;
	}

	/**
	 * 置审批中
	 */
	protected void _setAuditingStatus(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		MortagageControlInfo info = (MortagageControlInfo)model;
		if (info.getMortagageState().equals(MortagageEnum.SUBMITTED)|| info.getMortagageState().equals(MortagageEnum.AUDITTING)) {
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("mortagageState");
			selector.add("state");
			selector.add("auditor");
			selector.add("auditTime");
			info.setMortagageState(MortagageEnum.AUDITTING);
			info.setState(FDCBillStateEnum.AUDITTING);
			info.setAuditor((UserInfo) (SysContext.getSysContext()
					.getCurrentUserInfo()));
			info.setAuditTime(new Date());
			MortagageControlFactory.getLocalInstance(ctx).updatePartial(info,
					selector);
		}
	}

}