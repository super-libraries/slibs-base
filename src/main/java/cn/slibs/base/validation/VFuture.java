package cn.slibs.base.validation;

import com.iofairy.si.SI;
import com.iofairy.top.S;

public class VFuture extends Validate {

    public VFuture(Class<?>[] groups, String messageTag, String fieldName) {
        super(null, groups, messageTag, fieldName, "javax.validation.constraints.Future");
        this.message = generateMessage(messageTag, fieldName);
        this.code = generateCode();
    }

    @Override
    protected String generateMessage(String messageTag, String fieldName) {
        String msgTpl = "${messageTag}[${fieldName}]必须大于当前时间！";
        return SI.$(msgTpl, S.isBlank(messageTag) ? "字段" : messageTag, fieldName);
    }

    @Override
    protected String generateCode() {
        String codeTpl = "@Future(message = \"${message}\"${groups})";
        return SI.$(codeTpl, message, groupsString);
    }

}
