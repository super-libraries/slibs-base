package cn.slibs.base.validation;

import com.iofairy.si.SI;
import com.iofairy.top.S;

public class VPositiveOrZero extends Validate {

    public VPositiveOrZero(Class<?>[] groups, String messageTag, String fieldName) {
        super(null, groups, messageTag, fieldName, "javax.validation.constraints.PositiveOrZero");
        this.message = generateMessage(messageTag, fieldName);
        this.code = generateCode();
    }

    @Override
    protected String generateMessage(String messageTag, String fieldName) {
        String msgTpl = "${messageTag}[${fieldName}]必须是0或正数！";
        return SI.$(msgTpl, S.isBlank(messageTag) ? "字段" : messageTag, fieldName);
    }

    @Override
    protected String generateCode() {
        String codeTpl = "@PositiveOrZero(message = \"${message}\"${groups})";
        return SI.$(codeTpl, message, groupsString);
    }

}
