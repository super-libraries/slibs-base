package cn.slibs.base.validation;

import com.iofairy.si.SI;
import com.iofairy.top.S;

public class VEmail extends Validate {

    public VEmail(Class<?>[] groups, String messageTag, String fieldName) {
        super(null, groups, messageTag, fieldName, VALIDATE_CLASS_PREFIX + "Email");
        this.message = generateMessage(messageTag, fieldName);
        this.code = generateCode();
    }

    @Override
    protected String generateMessage(String messageTag, String fieldName) {
        String msgTpl = "${messageTag}[${fieldName}]必须是合法的邮箱地址！";
        return SI.$(msgTpl, S.isBlank(messageTag) ? "字段" : messageTag, fieldName);
    }

    @Override
    protected String generateCode() {
        String codeTpl = "@Email(message = \"${message}\"${groups})";
        return SI.$(codeTpl, message, groupsString);
    }

}
