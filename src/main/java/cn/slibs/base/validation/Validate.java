package cn.slibs.base.validation;

import com.iofairy.top.G;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 校验抽象类
 *
 * @since 0.0.1
 */
@Getter
@Setter
public abstract class Validate {
    public final static String VALIDATE_CLASS_PREFIX = "javax.validation.constraints.";
    protected Object value;
    protected Class<?>[] groups;
    protected String groupsString;
    protected String className;
    protected String importStatement;
    protected String message;
    protected String code;

    public Validate(Object value, Class<?>[] groups, String messageTag, String fieldName, String className) {
        this.value = value;
        this.groups = groups;
        this.groupsString = G.isEmpty(groups) ? "" : ", groups = {" + Arrays.stream(groups).map(c -> c.getSimpleName() + ".class")
                .collect(Collectors.joining(", ")) + "}";
        this.className = className;
        this.importStatement = "import " + className + ";";
    }

    protected abstract String generateMessage(String messageTag, String fieldName);

    protected abstract String generateCode();

    @Override
    public String toString() {
        return this.code;
    }

}
