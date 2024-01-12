package cn.slibs.base.validation;

import com.iofairy.si.SI;
import com.iofairy.top.S;

public class VPastOrPresent extends Validate {

    public VPastOrPresent(Class<?>[] groups, String messageTag, String fieldName) {
        super(null, groups, messageTag, fieldName, VALIDATE_CLASS_PREFIX + "PastOrPresent");
        this.message = generateMessage(messageTag, fieldName);
        this.code = generateCode();
    }

    @Override
    protected String generateMessage(String messageTag, String fieldName) {
        String msgTpl = "${messageTag}[${fieldName}]必须小于或等于当前时间！";
        return SI.$(msgTpl, S.isBlank(messageTag) ? "字段" : messageTag, fieldName);
    }

    @Override
    protected String generateCode() {
        String codeTpl = "@PastOrPresent(message = \"${message}\"${groups})";
        return SI.$(codeTpl, message, groupsString);
    }

}
