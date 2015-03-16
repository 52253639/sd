package com.kingdee.eas.fdc.market.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.query.util.CompareType;
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
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.market.BoutiqueRoomTypeInfo;
import com.kingdee.eas.fdc.market.RoomPhotoCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.app.CoreBaseControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.fdc.market.RoomPhotoInfo;

public class RoomPhotoControllerBean extends AbstractRoomPhotoControllerBean
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.market.app.RoomPhotoControllerBean");
	public IObjectPK submit(Context ctx, CoreBaseInfo model)
	 throws BOSException, EASBizException {
// TODO Auto-generated method stub

		RoomPhotoInfo rpInfo = (RoomPhotoInfo) model;
		
		rpInfo.setRoomTypePicData((byte[]) rpInfo.get("roomPhotofile"));
		rpInfo.setFlatTypePicData((byte[]) rpInfo.get("flatPhotofile"));
		
		rpInfo.setHead((BoutiqueRoomTypeInfo) rpInfo.get("BoutiqueRoomTypefile"));
		
		String eid = model.getString("BRTID");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("head.id", eid,
						CompareType.EQUALS));
//		_delete(ctx, filter);
//		this.delete(ctx, " where roomPhoto.id='"+eid+"'");
		this.delete(ctx, filter);
		
		return super.submit(ctx, rpInfo);
		}
	protected IObjectPK[] _delete(Context ctx, FilterInfo filter)
			throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		return super._delete(ctx, filter);
	}
}
