package cn.slibs.base.validation;

import com.iofairy.si.SI;
import com.iofairy.top.S;

public class VDigits extends Validate {
    private int integer;
    private int fraction;

    public VDigits(int integer, int fraction, Class<?>[] groups, String messageTag, String fieldName) {
        super(null, groups, messageTag, fieldName, VALIDATE_CLASS_PREFIX + "Digits");
        this.integer = integer;
        this.fraction = fraction;
        this.message = generateMessage(messageTag, fieldName);
        this.code = generateCode();
    }

    @Override
    protected String generateMessage(String messageTag, String fieldName) {
        String msgTpl = "${messageTag}[${fieldName}]的精度必须为(${integer}, ${fraction})";
        return SI.$(msgTpl, S.isBlank(messageTag) ? "字段" : messageTag, fieldName, integer, fraction);
    }

    @Override
    protected String generateCode() {
        String codeTpl = "@Digits(message = \"${message}\", integer = ${integer}, fraction = ${fraction}${groups})";
        return SI.$(codeTpl, message, integer, fraction, groupsString);
    }

}
