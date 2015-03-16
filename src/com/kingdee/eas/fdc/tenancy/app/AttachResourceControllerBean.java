package com.kingdee.eas.fdc.tenancy.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.tenancy.AttachResourceFactory;
import com.kingdee.eas.fdc.tenancy.AttachResourceInfo;
import com.kingdee.eas.fdc.tenancy.AttachResourceRentBillInfo;
import com.kingdee.eas.fdc.tenancy.AttachResourceRentEntrysCollection;
import com.kingdee.eas.fdc.tenancy.AttachResourceRentEntrysInfo;

public class AttachResourceControllerBean extends AbstractAttachResourceControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.AttachResourceControllerBean");
}