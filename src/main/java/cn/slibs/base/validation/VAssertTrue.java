package cn.slibs.base.validation;

import com.iofairy.si.SI;
import com.iofairy.top.S;

public class VAssertTrue extends Validate {

    public VAssertTrue(Class<?>[] groups, String messageTag, String fieldName) {
        super(null, groups, messageTag, fieldName, "javax.validation.constraints.AssertTrue");
        this.message = generateMessage(messageTag, fieldName);
        this.code = generateCode();
    }

    @Override
    protected String generateMessage(String messageTag, String fieldName) {
        String msgTpl = "${messageTag}[${fieldName}]取值只能是：true";
        return SI.$(msgTpl, S.isBlank(messageTag) ? "字段" : messageTag, fieldName);
    }

    @Override
    protected String generateCode() {
        String codeTpl = "@AssertTrue(message = \"${message}\"${groups})";
        return SI.$(codeTpl, message, groupsString);
    }

}
