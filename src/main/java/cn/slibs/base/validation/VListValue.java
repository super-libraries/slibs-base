package cn.slibs.base.validation;

import com.iofairy.si.SI;
import com.iofairy.tcf.Try;
import com.iofairy.top.G;
import com.iofairy.top.S;
import com.iofairy.tuple.EasyTuple;
import com.iofairy.tuple.EasyTuple2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class VListValue extends Validate {
    private String[] values;
    private final String[] valueComments;

    public VListValue(String[] value, String[] valueComments, Class<?>[] groups, String messageTag, String fieldName) {
        super(value, groups, messageTag, fieldName, "com.nsn.commons.validate.ListValue");
        this.values = value;
        this.valueComments = valueComments;
        if (G.isEmpty(values)) {
            throw new RuntimeException("VListValue的value参数不能为空！");
        }
        this.message = generateMessage(messageTag, fieldName);
        this.code = generateCode();
    }

    @Override
    protected String generateMessage(String messageTag, String fieldName) {
        List<EasyTuple2<String>> ets = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            final int finalI = i;
            ets.add(EasyTuple.of(values[finalI], Try.tcf(() -> valueComments[finalI], false)));
        }
        String joins = ets.stream().map(e -> e._1 + (S.isEmpty(e._2) ? "" : "(" + e._2 + ")")).collect(Collectors.joining(", "));
        String msgTpl = "${messageTag}[${fieldName}]取值只能是：${values}";
        return SI.$(msgTpl, S.isBlank(messageTag) ? "字段" : messageTag, fieldName, joins);
    }

    @Override
    protected String generateCode() {
        String codeTpl = "@ListValue(message = \"${message}\", values = {${values}}${groups})";
        String valueJoin = Arrays.stream(values).map(s -> '"' + s + '"').collect(Collectors.joining(", "));
        return SI.$(codeTpl, message, valueJoin, groupsString);
    }

}
