package com.kingdee.eas.fdc.sellhouse.client;

import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.util.client.ExceptionHandler;

/**
 * 选择项目弹出来的F7对话框
 * @author youzhen_qin
 *
 */
public final class FDCSellProjectDialog extends KDCommonPromptDialog
{
	protected IUIWindow classDlg = null;
	protected boolean isRefresh = false;
	/**
	 * 是否多选择
	 */
	private Boolean isMultiSelect = Boolean.FALSE;

	/**
	 * 所属系统
	 *
	 */
	private MoneySysTypeEnum moneySysTypeEnum;
	

	public FDCSellProjectDialog()
	{
	}
	
	public FDCSellProjectDialog(Boolean isMultiSelect, MoneySysTypeEnum moneySysTypeEnum)
	{
		this.isMultiSelect = isMultiSelect;
		this.moneySysTypeEnum = moneySysTypeEnum;
	}


	public String getUITitle()
	{
		return new String("选择共享项目");
	}

	public void show()
	{

		Map map = new HashMap();
		
		map.put("isMultiSelect",this.isMultiSelect);
		map.put("moneySysTypeEnum",this.moneySysTypeEnum);
		IUIFactory uiFactory = null;
		try
		{

			if (isRefresh || classDlg == null)
			{
				uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
				classDlg = uiFactory.create(FDCSellProjectSelectUI.class.getName(),map,null,"VIEW");
			}
			//确保每次显示isCanceled=true
			if (classDlg != null && classDlg.getUIObject() != null)
			{
				((FDCSellProjectSelectUI)classDlg.getUIObject()).isCanceled=true;
			}
			classDlg.show();
		} catch (BOSException ex)
		{
			ExceptionHandler.handle(this, ex);
			return;
		}
	}
	
	/**
	 * 判断是否点击取消按钮，决定是否调用取值函数
	 * 
	 */
	public boolean isCanceled()
	{

        if(classDlg!=null)
        {
            return((FDCSellProjectSelectUI)classDlg.getUIObject()).isCanceled ;
        }
        else
        {
            return true;
        }
	}

	/**
	 * 返回值
	 */
	public Object getData()
	{
		FDCSellProjectSelectUI ui = (FDCSellProjectSelectUI) classDlg.getUIObject();
		Object[] returnValue;
		try
		{
			//房间多选
			if(this.isMultiSelect.booleanValue())
			{
				return ui.getReturnValueArray();
			}
			//单选
			else
			{
				SellProjectInfo tempProject = (SellProjectInfo) ui.getReturnValue();
				if(tempProject != null)
				{
					returnValue = new Object[]{tempProject};
				}
				else
				{
					return null;
				}
			}
		} catch (Exception err)
		{
			return null;
		}
		return returnValue;
	}
}
