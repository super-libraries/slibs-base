package cn.slibs.base.validation;

import com.iofairy.si.SI;
import com.iofairy.top.S;

public class VLength extends Validate {
    private int min;
    private int max;

    public VLength(int min, int max, Class<?>[] groups, String messageTag, String fieldName) {
        super(null, groups, messageTag, fieldName, "org.hibernate.validator.constraints.Length");
        this.min = min;
        this.max = max;
        this.message = generateMessage(messageTag, fieldName);
        this.code = generateCode();
    }

    @Override
    protected String generateMessage(String messageTag, String fieldName) {
        String msgTpl = "${messageTag}[${fieldName}]的长度必须在 (${min} ~ ${max}) 之间！";
        return SI.$(msgTpl, S.isBlank(messageTag) ? "字段" : messageTag, fieldName, min, max);
    }

    @Override
    protected String generateCode() {
        String codeTpl = "@Length(message = \"${message}\", min = ${min}, max = ${max}${groups})";
        return SI.$(codeTpl, message, min, max, groupsString);
    }

}
