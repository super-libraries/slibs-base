package cn.slibs.base.validation;

import com.iofairy.top.G;

import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class Validate {
    protected Object value;
    protected Class<?>[] groups;
    protected String groupsString;
    protected String className;
    protected String importClass;
    protected String message;
    protected String code;

    public Validate(Object value, Class<?>[] groups, String messageTag, String fieldName, String className) {
        this.value = value;
        this.groups = groups;
        this.groupsString = G.isEmpty(groups) ? "" : ", groups = {" + Arrays.stream(groups).map(c -> c.getSimpleName() + ".class")
                .collect(Collectors.joining(", ")) + "}";
        this.className = className;
        this.importClass = "import " + className + ";";
    }

    protected abstract String generateMessage(String messageTag, String fieldName);

    protected abstract String generateCode();

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Class<?>[] getGroups() {
        return groups;
    }

    public void setGroups(Class<?>[] groups) {
        this.groups = groups;
    }

    public String getGroupsString() {
        return groupsString;
    }

    public void setGroupsString(String groupsString) {
        this.groupsString = groupsString;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getImportClass() {
        return importClass;
    }

    public void setImportClass(String importClass) {
        this.importClass = importClass;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return this.code;
    }

}
