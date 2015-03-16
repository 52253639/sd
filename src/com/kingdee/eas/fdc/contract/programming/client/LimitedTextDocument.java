package com.kingdee.eas.fdc.contract.programming.client;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

import com.kingdee.bos.ctrl.swing.LimitedLengthDocument;

public class LimitedTextDocument extends LimitedLengthDocument {
	private boolean isOnload = false;
	private boolean isAutoUpdate = false;
	private String limitedText;
	
	public LimitedTextDocument(String limitedText){
		super();
		this.limitedText = limitedText;
	}

	public LimitedTextDocument(String limitedText , boolean isOnload) {
		super();
		this.limitedText = limitedText;
		this.isOnload = isOnload;
	}

	public void remove(int offs, int len) throws BadLocationException {
		int limitLenth = limitedText.substring(0, limitedText.lastIndexOf('.') + 1).length();
		int length = this.getLength();
		if(!isAutoUpdate){
			if (limitedText != null && length > 0 && limitLenth > offs) {
				return;
			}
		}
		super.remove(offs, len);
	}

	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		int limitLenth = limitedText.substring(0, limitedText.lastIndexOf('.') + 1).length();
		int length = this.getLength();
		if(!isAutoUpdate && !isOnload){
			if(str.length() > 1)
				return;
		}
		if(!isAutoUpdate){
			if (limitedText != null && length > 0 && limitLenth > offs) {
				return;
			}
		}
		
		if(!isOnload){
			if (!str.equals(limitedText)) {
				if (str.matches("^\\.+$")) {
					return;
				}
			}
		}
		super.insertString(offs, str, a);
	}

	
	public void setLimitedText(String limitedText){
		this.limitedText = limitedText;
	}
	
	public void setIsOnload(boolean isOnload){
		this.isOnload = isOnload;
	}
	
	public void setIsAutoUpdate(boolean isFalg){
		this.isAutoUpdate = isFalg;
	}
}
