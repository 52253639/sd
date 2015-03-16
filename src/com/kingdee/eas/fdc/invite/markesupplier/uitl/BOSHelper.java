package com.kingdee.eas.fdc.invite.markesupplier.uitl;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.framework.IDynamicObject;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;

public class BOSHelper {
	
	/**
	 * ¶¯Ì¬¶ÔÏó
	 * @param bosId
	 * */
	public static IObjectValue getDynamicObject(Context ctx, String bosId)
    throws BOSException
	{
		IObjectValue objectValue = null;
		if(bosId != null && bosId.trim().length() != 0)
	    {
			BOSUuid uuid = BOSUuid.read(bosId);
			ObjectUuidPK pk = new ObjectUuidPK(uuid);
			BOSObjectType objType = uuid.getType();
			IDynamicObject dynamicObject = null;
			if(ctx == null)
				dynamicObject = DynamicObjectFactory.getRemoteInstance();
			else
				dynamicObject = DynamicObjectFactory.getLocalInstance(ctx);
			objectValue = dynamicObject.getValue(objType, pk);
	    }
		return objectValue;
	}
}
