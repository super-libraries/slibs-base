package cn.slibs.base.validation;

import com.iofairy.si.SI;
import com.iofairy.top.S;

public class VMin extends Validate {
    private long minValue;

    public VMin(long value, Class<?>[] groups, String messageTag, String fieldName) {
        super(value, groups, messageTag, fieldName, "javax.validation.constraints.Min");
        this.minValue = value;
        this.message = generateMessage(messageTag, fieldName);
        this.code = generateCode();
    }

    @Override
    protected String generateMessage(String messageTag, String fieldName) {
        String msgTpl = "${messageTag}[${fieldName}]必须大于或等于[${value}]";
        return SI.$(msgTpl, S.isBlank(messageTag) ? "字段" : messageTag, fieldName, minValue);
    }

    @Override
    protected String generateCode() {
        String codeTpl = "@Max(message = \"${message}\", value = ${value}${groups})";
        return SI.$(codeTpl, message, minValue, groupsString);
    }

}
