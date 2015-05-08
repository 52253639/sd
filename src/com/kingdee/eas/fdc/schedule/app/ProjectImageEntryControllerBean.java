package com.kingdee.eas.fdc.schedule.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.schedule.ProjectImageEntryCollection;
import com.kingdee.eas.fdc.schedule.ProjectImageEntryFactory;
import com.kingdee.eas.fdc.schedule.ProjectImageEntryInfo;

public class ProjectImageEntryControllerBean extends AbstractProjectImageEntryControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.schedule.app.ProjectImageEntryControllerBean");

	protected byte[] _getOriginalImageById(Context ctx, BOSUuid ID) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		try {
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection sic = new SelectorItemCollection();
			// sic.add("file");
			sic.add("fileName");
			sic.add("file");
			view.setSelector(sic);
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", ID.toString()));
			view.setFilter(filter);
			ProjectImageEntryCollection imgEntrys = ProjectImageEntryFactory.getLocalInstance(ctx).getProjectImageEntryCollection(view);
			if (imgEntrys != null && imgEntrys.size() > 0) {
				for (int i = 0; i < imgEntrys.size(); i++) {
					ProjectImageEntryInfo imgInfo = imgEntrys.get(i);
					return imgInfo.getFile();
				}
			}

		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		return null;
	}
}