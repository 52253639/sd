/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Rectangle;
import java.math.BigDecimal;
import java.util.Map;

import com.kingdee.bos.ctrl.swing.KDFontChooser;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.invite.AssessSetting;
import com.kingdee.eas.fdc.invite.QuotePeopleDisplayEnum;
import com.kingdee.eas.fdc.invite.QuoteStandEnum;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class QuoteSetUI extends AbstractQuoteSetUI {

	boolean isClickYes = false;

	/**
	 * output class constructor
	 */
	public QuoteSetUI() throws Exception {
		super();
	}

	public boolean destroyWindow() {
		if (!isClickYes) {
			this.getUIContext().put("setting", null);
		}
		return super.destroyWindow();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output kDButton1_actionPerformed method
	 */
	protected void kDButton1_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		AssessSetting setting = (AssessSetting) getUIContext().get("setting");
		setting.setStand((QuoteStandEnum) this.selectStand.getSelectedItem());
		setting
				.setBidderDisplay((QuotePeopleDisplayEnum) this.selectPeopleDisplay
						.getSelectedItem());

		BigDecimal high, low;
		try {
			high = new BigDecimal(this.txtHighLimit.getText());
			if (high.floatValue() <= 100) {
				MsgBox.showWarning("上限限值必须大于100！");
				return;
			}
			high = high.setScale(2, BigDecimal.ROUND_FLOOR);
		} catch (NumberFormatException ex) {
			this.txtHighLimit.setText("");
			MsgBox.showWarning("上限请输入数值！");
			return;
		}
		setting.setAllowHigh(high);

		try {
			low = new BigDecimal(this.txtLowLimit.getText());
			if (low.floatValue() >= 100) {
				MsgBox.showWarning("下限值必须小于100！");
				return;
			}
			low = low.setScale(2, BigDecimal.ROUND_FLOOR);
		} catch (NumberFormatException ex) {
			this.txtLowLimit.setText("");
			MsgBox.showWarning("下限请输入数值！");
			return;
		}
		setting.setAllowLow(low);

		if (low.floatValue() > high.floatValue()) {
			MsgBox.showWarning("上限值应该大于下限值！");
			return;
		}
		setting.setLowColor(this.colorLow.getColor());
		setting.setHighColor(this.colorHigh.getColor());
		setting.setLowFont(this.lowFont.getSelectionFont());
		setting.setHighFont(this.highFont.getSelectionFont());
		this.getUIContext().put("setting", setting);
		setting.save();
		this.isClickYes=true;
		disposeUIWindow();
	}

	private KDFontChooser lowFont = new KDFontChooser();

	private KDFontChooser highFont = new KDFontChooser();

	public void onLoad() throws Exception {
		super.onLoad();
		Rectangle rectangle = this.colorLow.getBounds();
		rectangle.x += 50;
		rectangle.width = 250;
		lowFont.setBounds(new Rectangle(rectangle));
		kDPanel2.add(lowFont, null);
		rectangle = this.colorHigh.getBounds();
		rectangle.x += 50;
		rectangle.width = 250;
		highFont.setBounds(new Rectangle(rectangle));
		kDPanel2.add(highFont, null);
		loadSetting();
	}

	private void loadSetting() {
		AssessSetting setting = (AssessSetting) this.getUIContext().get(
				"setting");
		this.selectStand.setSelectedItem(setting.getStand());
		this.selectPeopleDisplay.setSelectedItem(setting.getBidderDisplay());
		this.txtLowLimit.setText(setting.getAllowLow() + "");
		this.txtHighLimit.setText(setting.getAllowHigh() + "");
		this.lowFont.setSelectionFont(setting.getLowFont());
		this.colorLow.setColor(setting.getLowColor());
		this.highFont.setSelectionFont(setting.getHighFont());
		this.colorHigh.setColor(setting.getHighColor());
	}

	public static AssessSetting showSetting(CoreUI ui, AssessSetting setting)
			throws UIException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put("setting", setting);
		uiContext.put("inviteListingId", ui.getUIContext().get(
				"inviteListingId"));
		IUIWindow window = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(QuoteSetUI.class.getName(), uiContext, null, "Edit");
		window.show();
		Map ctx = window.getUIObject().getUIContext();
		return (AssessSetting) ctx.get("setting");
	}
}