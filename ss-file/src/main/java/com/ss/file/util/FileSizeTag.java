package com.ss.file.util;

import java.text.DecimalFormat;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 用于页面 jstl文件大小格式化
 */
public class FileSizeTag extends TagSupport {

	private static final long serialVersionUID = 6464168398214506236L;

    private String value;

    @Override
    public int doStartTag() throws JspException {
        String vv = "" + value;
        String size = "";
        try {
            Long fileSize = Long.valueOf(vv.trim());
            DecimalFormat df = new DecimalFormat("#.00"); 
            if (fileSize < 1024) {
                size = df.format((double) fileSize) + "BT";
            } else if (fileSize < 1048576) {
                size = df.format((double) fileSize / 1024) + "KB";
            } else if (fileSize < 1073741824) {
                size = df.format((double) fileSize / 1048576) + "MB";
            } else {
                size = df.format((double) fileSize / 1073741824) +"GB";
            }
            pageContext.getOut().write(size);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.doStartTag();
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}
