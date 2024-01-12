package cn.slibs.test;

import cn.slibs.base.validation.*;
import cn.slibs.base.validation.groups.*;
import org.junit.jupiter.api.Test;

public class ValidationTest {
    @Test
    void testVListValue() {
        VListValue vListValue1 = new VListValue(new String[]{"0", "1", "2"}, new String[]{"待审核", "审核通过"},
                new Class[]{AddGroup.class, UpdateGroup.class, UpdateStatusGroup.class}, "审核状态", "auditStatus", "xxx.ListValue");
        System.out.println(vListValue1);
        VListValue vListValue2 = new VListValue(new String[]{"0", "1", "2"}, new String[]{"待审核", null, "拒绝"},
                new Class[]{}, null, "auditStatus", "xxx.ListValue");
        System.out.println(vListValue2);
        VListValue vListValue3 = new VListValue(new String[]{"0", "1", "2"}, null,
                new Class[]{AddGroup.class, UpdateGroup.class, UpdateStatusGroup.class}, "审核状态", "auditStatus", "xxx.ListValue");
        System.out.println(vListValue3);
        VListValue vListValue4 = new VListValue(new String[]{"0", "1", "2"}, new String[]{"待审核", "审核通过", "拒绝"},
                null, "审核状态", "auditStatus", "xxx.ListValue");
        System.out.println(vListValue4);
    }

    @Test
    void test1() {
        VAssertFalse vAssertFalse = new VAssertFalse(new Class[]{AddGroup.class}, null, "column1");
        VAssertTrue vAssertTrue = new VAssertTrue(new Class[]{AddGroup.class}, null, "column1");
        VDecimalMax vDecimalMax = new VDecimalMax("99999999", true, new Class[]{AddGroup.class}, null, "column1");
        VDecimalMin vDecimalMin = new VDecimalMin("-99999999", true, new Class[]{AddGroup.class}, null, "column1");
        VDigits vDigits = new VDigits(10, 2, new Class[]{AddGroup.class}, null, "column1");
        VEmail vEmail = new VEmail(new Class[]{AddGroup.class}, null, "column1");
        VFuture vFuture = new VFuture(new Class[]{AddGroup.class}, null, "column1");
        VFutureOrPresent vFutureOrPresent = new VFutureOrPresent(new Class[]{AddGroup.class}, null, "column1");
        VLength vLength = new VLength(1, 5, new Class[]{AddGroup.class}, null, "column1");
        VMax vMax = new VMax(10, new Class[]{AddGroup.class}, null, "column1");
        VMin vMin = new VMin(5, new Class[]{AddGroup.class}, null, "column1");
        VNegative vNegative = new VNegative(new Class[]{AddGroup.class}, null, "column1");
        VNegativeOrZero vNegativeOrZero = new VNegativeOrZero(new Class[]{AddGroup.class}, null, "column1");
        VNotBlank vNotBlank = new VNotBlank(new Class[]{AddGroup.class}, null, "column1");
        VNotEmpty vNotEmpty = new VNotEmpty(new Class[]{AddGroup.class}, null, "column1");
        VNotNull vNotNull = new VNotNull(new Class[]{AddGroup.class}, null, "column1");
        VNull vNull = new VNull(new Class[]{AddGroup.class}, null, "column1");
        VPast vPast = new VPast(new Class[]{AddGroup.class}, null, "column1");
        VPastOrPresent vPastOrPresent = new VPastOrPresent(new Class[]{AddGroup.class}, null, "column1");
        VPositive vPositive = new VPositive(new Class[]{AddGroup.class}, null, "column1");
        VPositiveOrZero vPositiveOrZero = new VPositiveOrZero(new Class[]{AddGroup.class}, null, "column1");
        VRange vRange = new VRange(8, 10, new Class[]{AddGroup.class}, null, "column1");
        VSize vSize = new VSize(-1, 20, new Class[]{AddGroup.class}, null, "column1");

        System.out.println(vAssertFalse);
        System.out.println(vAssertTrue);
        System.out.println(vDecimalMax);
        System.out.println(vDecimalMin);
        System.out.println(vDigits);
        System.out.println(vEmail);
        System.out.println(vFuture);
        System.out.println(vFutureOrPresent);
        System.out.println(vLength);
        System.out.println(vMax);
        System.out.println(vMin);
        System.out.println(vNegative);
        System.out.println(vNegativeOrZero);
        System.out.println(vNotBlank);
        System.out.println(vNotEmpty);
        System.out.println(vNotNull);
        System.out.println(vNull);
        System.out.println(vPast);
        System.out.println(vPastOrPresent);
        System.out.println(vPositive);
        System.out.println(vPositiveOrZero);
        System.out.println(vRange);
        System.out.println(vSize);
        System.out.println("==================");
        System.out.println(vAssertFalse.getImportStatement());
        System.out.println(vAssertTrue.getImportStatement());
        System.out.println(vDecimalMax.getImportStatement());
        System.out.println(vDecimalMin.getImportStatement());
        System.out.println(vDigits.getImportStatement());
        System.out.println(vEmail.getImportStatement());
        System.out.println(vFuture.getImportStatement());
        System.out.println(vFutureOrPresent.getImportStatement());
        System.out.println(vLength.getImportStatement());
        System.out.println(vMax.getImportStatement());
        System.out.println(vMin.getImportStatement());
        System.out.println(vNegative.getImportStatement());
        System.out.println(vNegativeOrZero.getImportStatement());
        System.out.println(vNotBlank.getImportStatement());
        System.out.println(vNotEmpty.getImportStatement());
        System.out.println(vNotNull.getImportStatement());
        System.out.println(vNull.getImportStatement());
        System.out.println(vPast.getImportStatement());
        System.out.println(vPastOrPresent.getImportStatement());
        System.out.println(vPositive.getImportStatement());
        System.out.println(vPositiveOrZero.getImportStatement());
        System.out.println(vRange.getImportStatement());
        System.out.println(vSize.getImportStatement());
    }


    @Test
    void test2() {
        VAssertFalse vAssertFalse = new VAssertFalse(null, null, "column1");
        VAssertTrue vAssertTrue = new VAssertTrue(null, null, "column1");
        VDecimalMax vDecimalMax = new VDecimalMax("99999999", false, null, null, "column1");
        VDecimalMin vDecimalMin = new VDecimalMin("-99999999", false, null, null, "column1");
        VDigits vDigits = new VDigits(10, 2, null, null, "column1");
        VEmail vEmail = new VEmail(null, null, "column1");
        VFuture vFuture = new VFuture(null, null, "column1");
        VFutureOrPresent vFutureOrPresent = new VFutureOrPresent(null, null, "column1");
        VLength vLength = new VLength(1, 5, null, null, "column1");
        VMax vMax = new VMax(10, null, null, "column1");
        VMin vMin = new VMin(5, null, null, "column1");
        VNegative vNegative = new VNegative(null, null, "column1");
        VNegativeOrZero vNegativeOrZero = new VNegativeOrZero(null, null, "column1");
        VNotBlank vNotBlank = new VNotBlank(null, null, "column1");
        VNotEmpty vNotEmpty = new VNotEmpty(null, null, "column1");
        VNotNull vNotNull = new VNotNull(null, null, "column1");
        VNull vNull = new VNull(null, null, "column1");
        VPast vPast = new VPast(null, null, "column1");
        VPastOrPresent vPastOrPresent = new VPastOrPresent(null, null, "column1");
        VPositive vPositive = new VPositive(null, null, "column1");
        VPositiveOrZero vPositiveOrZero = new VPositiveOrZero(null, null, "column1");
        VRange vRange = new VRange(8, 10, null, null, "column1");
        VSize vSize = new VSize(-1, 20, null, null, "column1");

        System.out.println(vAssertFalse);
        System.out.println(vAssertTrue);
        System.out.println(vDecimalMax);
        System.out.println(vDecimalMin);
        System.out.println(vDigits);
        System.out.println(vEmail);
        System.out.println(vFuture);
        System.out.println(vFutureOrPresent);
        System.out.println(vLength);
        System.out.println(vMax);
        System.out.println(vMin);
        System.out.println(vNegative);
        System.out.println(vNegativeOrZero);
        System.out.println(vNotBlank);
        System.out.println(vNotEmpty);
        System.out.println(vNotNull);
        System.out.println(vNull);
        System.out.println(vPast);
        System.out.println(vPastOrPresent);
        System.out.println(vPositive);
        System.out.println(vPositiveOrZero);
        System.out.println(vRange);
        System.out.println(vSize);
        System.out.println("==================");
        System.out.println(vAssertFalse.getImportStatement());
        System.out.println(vAssertTrue.getImportStatement());
        System.out.println(vDecimalMax.getImportStatement());
        System.out.println(vDecimalMin.getImportStatement());
        System.out.println(vDigits.getImportStatement());
        System.out.println(vEmail.getImportStatement());
        System.out.println(vFuture.getImportStatement());
        System.out.println(vFutureOrPresent.getImportStatement());
        System.out.println(vLength.getImportStatement());
        System.out.println(vMax.getImportStatement());
        System.out.println(vMin.getImportStatement());
        System.out.println(vNegative.getImportStatement());
        System.out.println(vNegativeOrZero.getImportStatement());
        System.out.println(vNotBlank.getImportStatement());
        System.out.println(vNotEmpty.getImportStatement());
        System.out.println(vNotNull.getImportStatement());
        System.out.println(vNull.getImportStatement());
        System.out.println(vPast.getImportStatement());
        System.out.println(vPastOrPresent.getImportStatement());
        System.out.println(vPositive.getImportStatement());
        System.out.println(vPositiveOrZero.getImportStatement());
        System.out.println(vRange.getImportStatement());
        System.out.println(vSize.getImportStatement());
    }

}
