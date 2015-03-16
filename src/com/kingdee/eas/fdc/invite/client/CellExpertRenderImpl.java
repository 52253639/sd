package com.kingdee.eas.fdc.invite.client;

import java.util.Map;

import com.kingdee.bos.ctrl.kdf.util.render.CellTextRender;
import com.kingdee.eas.fdc.invite.ExpertInfo;

public class CellExpertRenderImpl extends CellTextRender{
	public String getText(Object obj) {
		if(obj == null)
			return null;
		
		if(obj instanceof ExpertInfo)
		{
			return ((ExpertInfo)obj).getNumber();
		}
		else if(obj instanceof Map && ((Map)obj).containsKey("number")){
			return String.valueOf(((Map)obj).get("number"));
		}
		else
			return String.valueOf(obj);
	}
}
