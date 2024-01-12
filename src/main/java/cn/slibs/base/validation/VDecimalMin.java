package cn.slibs.base.validation;

import com.iofairy.si.SI;
import com.iofairy.top.S;

public class VDecimalMin extends Validate {
    private String strValue;
    private boolean inclusive;

    public VDecimalMin(String value, boolean inclusive, Class<?>[] groups, String messageTag, String fieldName) {
        super(value, groups, messageTag, fieldName, VALIDATE_CLASS_PREFIX + "DecimalMin");
        this.strValue = value;
        this.inclusive = inclusive;
        this.message = generateMessage(messageTag, fieldName);
        this.code = generateCode();
    }

    @Override
    protected String generateMessage(String messageTag, String fieldName) {
        String msgTpl1 = "${messageTag}[${fieldName}]必须大于或等于[${value}]";
        String msgTpl2 = "${messageTag}[${fieldName}]必须大于[${value}]";
        return SI.$(inclusive ? msgTpl1 : msgTpl2, S.isBlank(messageTag) ? "字段" : messageTag, fieldName, strValue);
    }

    @Override
    protected String generateCode() {
        String codeTpl = "@DecimalMin(message = \"${message}\", value = \"${value}\", inclusive = ${inclusive}${groups})";
        return SI.$(codeTpl, message, strValue, inclusive, groupsString);
    }

}
