package com.kingdee.eas.fdc.invite.client;

import com.kingdee.bos.ctrl.swing.KDMenu;
import com.kingdee.eas.framework.client.TableMenuUI;

public class NewListingTableMenuUI extends TableMenuUI {

	public NewListingTableMenuUI() throws Exception {
		super();
	}
	
	/**
	 * 招标清单编辑表上右键单击导出时，右键菜单的“导出到Excel”和“导出选择部分到excel”有重复，去掉上面两项。
	 * 如果全部去掉pop.add的话，会影响到招标说明和报价汇总表的右键菜单会不显示，因此，用pop.add添加一个菜单项，但不让它显示。
	 * Modidied by Owen_wen 2011-08-31
	 */
	public KDMenu getMenu()
    {
        KDMenu pop = null;
            pop = new KDMenu();
		pop.add(menuItemExportExcel);
		pop.getItem(0).setVisible(false);
		// pop.add(menuItemExportSelected);
        return pop;
    }

}
