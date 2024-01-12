package cn.slibs.base.validation;

import com.iofairy.si.SI;
import com.iofairy.top.S;

public class VPositive extends Validate {

    public VPositive(Class<?>[] groups, String messageTag, String fieldName) {
        super(null, groups, messageTag, fieldName, VALIDATE_CLASS_PREFIX + "Positive");
        this.message = generateMessage(messageTag, fieldName);
        this.code = generateCode();
    }

    @Override
    protected String generateMessage(String messageTag, String fieldName) {
        String msgTpl = "${messageTag}[${fieldName}]必须是正数！";
        return SI.$(msgTpl, S.isBlank(messageTag) ? "字段" : messageTag, fieldName);
    }

    @Override
    protected String generateCode() {
        String codeTpl = "@Positive(message = \"${message}\"${groups})";
        return SI.$(codeTpl, message, groupsString);
    }

}
