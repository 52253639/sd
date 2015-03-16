package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import javax.sql.RowSet;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.sellhouse.BuildingCompensateInfo;
import com.kingdee.eas.fdc.sellhouse.IRoom;
import com.kingdee.eas.fdc.sellhouse.IRoomAreaCompensate;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensate;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateCollection;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateFactory;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateInfo;
import com.kingdee.eas.fdc.sellhouse.RoomCompensateStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingCompensateCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.util.app.ContextUtil;

public class BuildingCompensateControllerBean extends
		AbstractBuildingCompensateControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.BuildingCompensateControllerBean");

	protected boolean isUseName() {
		return false;
	}

	protected boolean isUseNumber() {
		return false;
	}

	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		BuildingCompensateInfo info = super.getBuildingCompensateInfo(ctx, new ObjectUuidPK(billId));

//		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
//		builder.clear();
//		builder.appendSql("update T_SHE_RoomAreaCompensate set FCompensateState=?,FAuditorID=?,FAuditTime=? where FRoomID in (select FID from T_SHE_Room where FBuildingID=?) and (FCompensateState='COMSUBMIT' or FCompensateState ='COMAUDITTING') ");
//	
//		builder.addParam(RoomCompensateStateEnum.COMAUDITTED_VALUE);
//		builder.addParam(ContextUtil.getCurrentUserInfo(ctx).getId().toString());
//		builder.addParam(FDCDateHelper.getSqlDate(new Date()));
//		builder.addParam(info.getBuilding().getId().toString());
//		builder.executeUpdate();
//		
//		builder = new FDCSQLBuilder(ctx);
//		builder.clear();
//		builder.appendSql("update T_SHE_Room set FAreaCompensateAmount=(select FCompensateAmount from T_SHE_RoomAreaCompensate where T_SHE_RoomAreaCompensate.FRoomID= T_SHE_Room.FID and (FCompensateState='COMSUBMIT' or FCompensateState ='COMAUDITTING')  ),FSellAmount=(select FLatestAmount from T_SHE_RoomAreaCompensate where T_SHE_RoomAreaCompensate.FRoomID= T_SHE_Room.FID and (FCompensateState='COMSUBMIT' or FCompensateState ='COMAUDITTING') ) where FBuildingID=? and exists (select FID from T_SHE_RoomAreaCompensate where T_SHE_RoomAreaCompensate.FRoomID= T_SHE_Room.FID)");
//		builder.addParam(info.getBuilding().getId().toString());
//		builder.executeUpdate();
		List list = new ArrayList();
		String buildingId = null;
		if(info.getBuilding() != null){
			buildingId = info.getBuilding().getId().toString();
		}
		
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select fid from t_she_roomareacompensate where froomid in (select fid from t_she_room where fbuildingid = ?) and (FCompensateState='COMSUBMIT' or FCompensateState ='COMAUDITTING')");
		builder.addParam(buildingId);
		RowSet rs = builder.executeQuery();
		try {
			while(rs.next()){
				list.add(rs.getString("fid"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.info(builder.getTestSql());
			logger.info(e.getMessage());
		}
			
		RoomAreaCompensateFactory.getLocalInstance(ctx).audit(list);	
	}

	protected void _setAudittingStatus(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		BuildingCompensateInfo info = super.getBuildingCompensateInfo(ctx, new ObjectUuidPK(billId));
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.clear();
		builder.appendSql("update T_SHE_RoomAreaCompensate set FCompensateState=? where FRoomID in (select FID from T_SHE_Room where FBuildingID=?) and FCompensateState='COMSUBMIT'");	
		builder.addParam(RoomCompensateStateEnum.COMAUDITTING_VALUE);
		builder.addParam(info.getBuilding().getId().toString());
		builder.executeUpdate();
	}

	// 反审核
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		BuildingCompensateInfo info = super.getBuildingCompensateInfo(ctx, new ObjectUuidPK(billId));
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.clear();
		builder
				.appendSql("update T_SHE_RoomAreaCompensate set FCompensateState=?,FAuditorID=?,FAuditTime=? where FRoomID in (select FID from T_SHE_Room where FBuildingID=?) and FCompensateState='COMAUDITTED'");
	
		builder.addParam(RoomCompensateStateEnum.COMAUDITTING_VALUE);
		builder.addParam("");
		builder.addParam("");
		builder.addParam(info.getBuilding().getId().toString());
		builder.executeUpdate();
	}

	/**
	 * 设置提交状态(审批不通过时的自动节点)
	 */
	protected void _setSubmitStatus(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		BuildingCompensateInfo info = super.getBuildingCompensateInfo(ctx, new ObjectUuidPK(billId));
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
				.appendSql("update T_SHE_RoomAreaCompensate set FCompensateState=?,FAuditorID=?,FAuditTime=? where FRoomID in (select FID from T_SHE_Room where FBuildingID=?) and FCompensateState='COMAUDITTING' ");	
		builder.addParam(RoomCompensateStateEnum.COMSUBMIT_VALUE);
		builder.addParam("");
		builder.addParam("");
		builder.addParam(info.getBuilding().getId().toString());
		builder.executeUpdate();
	}
}