package cn.slibs.base.validation;

import com.iofairy.si.SI;
import com.iofairy.top.S;

public class VNotNull extends Validate {

    public VNotNull(Class<?>[] groups, String messageTag, String fieldName) {
        super(null, groups, messageTag, fieldName, VALIDATE_CLASS_PREFIX + "NotNull");
        this.message = generateMessage(messageTag, fieldName);
        this.code = generateCode();
    }

    @Override
    protected String generateMessage(String messageTag, String fieldName) {
        String msgTpl = "${messageTag}[${fieldName}]不能为null！";
        return SI.$(msgTpl, S.isBlank(messageTag) ? "字段" : messageTag, fieldName);
    }

    @Override
    protected String generateCode() {
        String codeTpl = "@NotNull(message = \"${message}\"${groups})";
        return SI.$(codeTpl, message, groupsString);
    }

}
