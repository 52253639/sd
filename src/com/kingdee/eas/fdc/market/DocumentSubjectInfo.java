package com.kingdee.eas.fdc.market;

import java.awt.Font;
import java.io.Serializable;

import javax.swing.SwingConstants;

public class DocumentSubjectInfo extends AbstractDocumentSubjectInfo
{

	public final static int MAX_FONT_SIZE = 80;
	public final static int MIN_FONT_SIZE = 8;

    public DocumentSubjectInfo()
    {
        super();
    }
    protected DocumentSubjectInfo(String pkField)
    {
        super(pkField);
    }
    
    
    public Font getFont() {
		Font font = null;
		if(getXFontName() == null || getXFontSize() <= 8 || getXFontSize() >= MAX_FONT_SIZE){
			font = getDefaultFont();
		}else {
			font = new Font(getXFontName(),Font.PLAIN,getXFontSize());
		}
		return font;
	}
    
    
    
	public String getAbsractTopic() {
		StringBuffer ss = new StringBuffer();
		if(getIsShowNumber() == 1){
//			ss.append(getShowNumber());
			//src start
			if(getIsRequired()==1){
				ss.append("*");
			}
			ss.append(getSubjectNumber());
			//src end
			ss.append(".");
		}
		ss.append(super.getTopic());
		return ss.toString();
	}
	
	
	public Font getDefaultFont(){
		return new Font("Dialog",Font.PLAIN,12);
	}

	public String getXFontName() {
		return "Dialog";
	}
    
    
    
    
    
    
}