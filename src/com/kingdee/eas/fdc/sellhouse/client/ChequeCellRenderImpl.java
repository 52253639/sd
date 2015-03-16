package com.kingdee.eas.fdc.sellhouse.client;

import com.kingdee.bos.ctrl.kdf.util.render.CellTextRender;
import com.kingdee.eas.fdc.sellhouse.ChequeInfo;

public class ChequeCellRenderImpl extends CellTextRender{
	public String getText(Object obj) {
		if(obj == null)
			return null;
				
		if(obj instanceof String){
			return String.valueOf(obj);
		}else if(obj instanceof ChequeInfo)
		{
			ChequeInfo info = (ChequeInfo)obj;
			StringBuffer workareaString = new StringBuffer();
			workareaString.append(info.getNumber());
			return workareaString.toString();
		}else
		{
			return String.valueOf(obj);
		}
	}
}
