package cn.slibs.base.validation;

import com.iofairy.si.SI;
import com.iofairy.top.S;

import java.util.Arrays;
import java.util.stream.Collectors;

public class VMax extends Validate {
    private long maxValue;

    public VMax(long value, Class<?>[] groups, String messageTag, String fieldName) {
        super(value, groups, messageTag, fieldName, VALIDATE_CLASS_PREFIX + "Max");
        this.maxValue = value;
        this.message = generateMessage(messageTag, fieldName);
        this.code = generateCode();
    }

    @Override
    protected String generateMessage(String messageTag, String fieldName) {
        String msgTpl = "${messageTag}[${fieldName}]必须小于或等于[${value}]";
        return SI.$(msgTpl, S.isBlank(messageTag) ? "字段" : messageTag, fieldName, maxValue);
    }

    @Override
    protected String generateCode() {
        String codeTpl = "@Max(message = \"${message}\", value = ${value}${groups})";
        return SI.$(codeTpl, message, maxValue, groupsString);
    }

}
