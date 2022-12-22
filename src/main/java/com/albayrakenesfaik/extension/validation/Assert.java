package com.albayrakenesfaik.extension.validation;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;

public class Assert {

    private final static Logger log = LoggerFactory.getLogger(Assert.class);

    /**
     * <pre>
     * object == null       = incorrect
     * object != null       = correct
     * </pre>
     *
     * @param object                       The object to be checked.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @param runnable                     If condition is null, the external runnable parameter will run.
     * @throws BusinessException           If object is null then throws BusinessException.
     */
    public static void isNotNull(Object object, BusinessExceptionKey businessExceptionKey, Runnable runnable) {
        if (object != null) {
            return;
        }

        if (runnable != null) {
            runnable.run();
        }

        throw new BusinessException(businessExceptionKey);
    }

    /**
     * <pre>
     * object == null       = incorrect
     * object != null       = correct
     * </pre>
     *
     * @param object                       The object to be checked.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @throws BusinessException           If object is null then throws BusinessException.
     */
    public static void isNotNull(Object object, BusinessExceptionKey businessExceptionKey) {
        Assert.isNotNull(object, businessExceptionKey, null);
    }

    /**
     * <pre>
     * object == null       = incorrect
     * object != null       = correct
     * </pre>
     *
     * @param object            The object to be checked.
     * @param exceptionSupplier It will return java.lang.Throwable when it is wrong.
     * @param runnable          If condition is null, the external runnable parameter will run.
     */
    public static <X extends Throwable> void isNotNull(Object object, Supplier<? extends X> exceptionSupplier, Runnable runnable) throws X {
        if (object != null) {
            return;
        }

        if (runnable != null) {
            runnable.run();
        }

        throw exceptionSupplier.get();
    }

    /**
     * <pre>
     * object == null       = incorrect
     * object != null       = correct
     * </pre>
     *
     * @param object            The object to be checked.
     * @param exceptionSupplier It will return java.lang.Throwable when it is wrong.
     */
    public static <X extends Throwable> void isNotNull(Object object, Supplier<? extends X> exceptionSupplier) throws X {
        Assert.isNotNull(object, exceptionSupplier, null);
    }

    /**
     * <pre>
     * object == null       = correct
     * object != null       = incorrect
     * </pre>
     *
     * @param object                       The object to be checked.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @param runnable                     If condition is not null, the external runnable parameter will run.
     * @throws BusinessException           If object is not null then throws BusinessException.
     */
    public static void isNull(Object object, BusinessExceptionKey businessExceptionKey, Runnable runnable) {
        if (object == null) {
            return;
        }

        if (runnable != null) {
            runnable.run();
        }

        throw new BusinessException(businessExceptionKey);
    }

    /**
     * <pre>
     * object == null       = correct
     * object != null       = incorrect
     * </pre>
     *
     * @param object                       The object to be checked.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @throws BusinessException           If object is not null then throws BusinessException.
     */
    public static void isNull(Object object, BusinessExceptionKey businessExceptionKey) {
        Assert.isNull(object, businessExceptionKey, null);
    }

    /**
     * <pre>
     * Assert.isNotEmpty(null)      = incorrect
     * Assert.isNotEmpty("")        = incorrect
     * Assert.isNotEmpty(" ")       = correct
     * Assert.isNotEmpty("bob")     = correct
     * Assert.isNotEmpty("  bob  ") = correct
     * </pre>
     *
     * @param string                       The object to be checked.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @param runnable                     If condition is empty, the external runnable parameter will run.
     * @throws BusinessException           If object is empty then throws BusinessException.
     */
    public static void isNotEmpty(String string, BusinessExceptionKey businessExceptionKey, Runnable runnable) {
        if (StringUtils.isNotEmpty(string)) {
            return;
        }

        if (runnable != null) {
            runnable.run();
        }

        throw new BusinessException(businessExceptionKey);
    }

    /**
     * <pre>
     * Assert.isNotEmpty(null)      = incorrect
     * Assert.isNotEmpty("")        = incorrect
     * Assert.isNotEmpty(" ")       = correct
     * Assert.isNotEmpty("bob")     = correct
     * Assert.isNotEmpty("  bob  ") = correct
     * </pre>
     *
     * @param string                       The object to be checked.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @throws BusinessException           If object is empty then throws BusinessException.
     */
    public static void isNotEmpty(String string, BusinessExceptionKey businessExceptionKey) {
        Assert.isNotEmpty(string, businessExceptionKey, null);
    }

    /**
     * <pre>
     * Assert.isNotEmpty(null)                      = incorrect
     * Assert.isNotEmpty(Arrays.asList())           = incorrect
     * Assert.isNotEmpty(new ArrayList<>())         = incorrect
     * Assert.isNotEmpty(Arrays.asList("1", "2"))   = correct
     * </pre>
     *
     * @param collection                   The object to be checked.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @param runnable                     If condition is empty, the external runnable parameter will run.
     * @throws BusinessException           If object is empty then throws BusinessException.
     */
    public static void isNotEmpty(Collection<?> collection, BusinessExceptionKey businessExceptionKey, Runnable runnable) {
        if (null != collection && !collection.isEmpty()) {
            return;
        }

        if (runnable != null) {
            runnable.run();
        }

        throw new BusinessException(businessExceptionKey);
    }

    /**
     * <pre>
     * Assert.isNotEmpty(null)                      = incorrect
     * Assert.isNotEmpty(Arrays.asList())           = incorrect
     * Assert.isNotEmpty(new ArrayList<>())         = incorrect
     * Assert.isNotEmpty(Arrays.asList("1", "2"))   = correct
     * </pre>
     *
     * @param collection                   The object to be checked.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @throws BusinessException           If object is empty then throws BusinessException.
     */
    public static void isNotEmpty(Collection<?> collection, BusinessExceptionKey businessExceptionKey) {
        Assert.isNotEmpty(collection, businessExceptionKey, null);
    }

    /**
     * <pre>
     * Assert.isEmpty(null)                      = correct
     * Assert.isEmpty(Arrays.asList())           = correct
     * Assert.isEmpty(new ArrayList<>())         = correct
     * Assert.isEmpty(Arrays.asList("1", "2"))   = incorrect
     * </pre>
     *
     * @param collection                   The object to be checked.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @param runnable                     If condition is not empty, the external runnable parameter will run.
     * @throws BusinessException           If object is not empty then throws BusinessException.
     */
    public static void isEmpty(Collection<?> collection, BusinessExceptionKey businessExceptionKey, Runnable runnable) {
        if (null == collection || collection.isEmpty()) {
            return;
        }

        if (runnable != null) {
            runnable.run();
        }

        throw new BusinessException(businessExceptionKey);
    }

    /**
     * <pre>
     * Assert.isEmpty(null)                      = correct
     * Assert.isEmpty(Arrays.asList())           = correct
     * Assert.isEmpty(new ArrayList<>())         = correct
     * Assert.isEmpty(Arrays.asList("1", "2"))   = incorrect
     * </pre>
     *
     * @param collection                   The object to be checked.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @throws BusinessException           If object is not empty then throws BusinessException.
     */
    public static void isEmpty(Collection<?> collection, BusinessExceptionKey businessExceptionKey) {
        Assert.isEmpty(collection, businessExceptionKey, null);
    }

    /**
     * <pre>
     * Assert.isNotBlank(null)      = incorrect
     * Assert.isNotBlank("")        = incorrect
     * Assert.isNotBlank(" ")       = incorrect
     * Assert.isNotBlank("bob")     = correct
     * Assert.isNotBlank("  bob  ") = correct
     * </pre>
     *
     * @param string                       The object to be checked.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @param runnable                     If condition is blank, the external runnable parameter will run.
     * @throws BusinessException           If object is blank then throws BusinessException.
     */
    public static void isNotBlank(String string, BusinessExceptionKey businessExceptionKey, Runnable runnable) {
        if (StringUtils.isNotBlank(string)) {
            return;
        }

        if (runnable != null) {
            runnable.run();
        }

        throw new BusinessException(businessExceptionKey);
    }

    /**
     * <pre>
     * Assert.isNotBlank(null)      = incorrect
     * Assert.isNotBlank("")        = incorrect
     * Assert.isNotBlank(" ")       = incorrect
     * Assert.isNotBlank("bob")     = correct
     * Assert.isNotBlank("  bob  ") = correct
     * </pre>
     *
     * @param string                       The object to be checked.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @throws BusinessException           If object is blank then throws BusinessException.
     */
    public static void isNotBlank(String string, BusinessExceptionKey businessExceptionKey) {
        Assert.isNotBlank(string, businessExceptionKey, null);
    }

    /**
     * * <pre>
     * Assert.isBlank(null)      = correct
     * Assert.isBlank("")        = correct
     * Assert.isBlank(" ")       = correct
     * Assert.isBlank("bob")     = incorrect
     * Assert.isBlank("  bob  ") = incorrect
     * </pre>
     *
     * @param string                       The object to be checked.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @param runnable                     If condition is not blank, the external runnable parameter will run.
     * @throws BusinessException           If object is not blank then throws BusinessException.
     */
    public static void isBlank(String string, BusinessExceptionKey businessExceptionKey, Runnable runnable) {
        if (StringUtils.isBlank(string)) {
            return;
        }

        if (runnable != null) {
            runnable.run();
        }

        throw new BusinessException(businessExceptionKey);
    }

    /**
     * * <pre>
     * Assert.isBlank(null)      = correct
     * Assert.isBlank("")        = correct
     * Assert.isBlank(" ")       = correct
     * Assert.isBlank("bob")     = incorrect
     * Assert.isBlank("  bob  ") = incorrect
     * </pre>
     *
     * @param string                       The object to be checked.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @throws BusinessException           If object is not blank then throws BusinessException.
     */
    public static void isBlank(String string, BusinessExceptionKey businessExceptionKey) {
        Assert.isBlank(string, businessExceptionKey, null);
    }

    /**
     * <pre>
     * Assert.isEqual(Status.ACTIVE, Status.ACTIVE)     = correct
     * Assert.isEqual("Text1", "Text1")                 = correct
     * Assert.isEqual(Status.ACTIVE, Status.PASSIVE)    = incorrect
     * Assert.isEqual("Text1", "Text2")                 = incorrect
     * </pre>
     *
     * @param object1                      The first object to check for equality.
     * @param object2                      The second object to check for equality.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @param runnable                     If condition is not equal, the external runnable parameter will run.
     * @throws BusinessException           If object is not equal then throws BusinessException.
     */
    public static void isEqual(Object object1, Object object2, BusinessExceptionKey businessExceptionKey, Runnable runnable) {
        if (Objects.equals(object1, object2)) {
            return;
        }

        if (runnable != null) {
            runnable.run();
        }

        throw new BusinessException(businessExceptionKey);
    }

    /**
     * <pre>
     * Assert.isEqual(Status.ACTIVE, Status.ACTIVE)     = correct
     * Assert.isEqual("Text1", "Text1")                 = correct
     * Assert.isEqual(Status.ACTIVE, Status.PASSIVE)    = incorrect
     * Assert.isEqual("Text1", "Text2")                 = incorrect
     * </pre>
     *
     * @param object1                      The first object to check for equality.
     * @param object2                      The second object to check for equality.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @throws BusinessException           If object is not equal then throws BusinessException.
     */
    public static void isEqual(Object object1, Object object2, BusinessExceptionKey businessExceptionKey) {
        Assert.isEqual(object1, object2, businessExceptionKey, null);
    }

    /**
     * <pre>
     * Assert.isEqual(Status.ACTIVE, Status.ACTIVE)     = correct
     * Assert.isEqual("Text1", "Text1")                 = correct
     * Assert.isEqual(Status.ACTIVE, Status.PASSIVE)    = incorrect
     * Assert.isEqual("Text1", "Text2")                 = incorrect
     * </pre>
     *
     * @param object1           The first object to check for equality.
     * @param object2           The second object to check for equality.
     * @param exceptionSupplier Exception that it will return when it is wrong.
     * @param runnable          If condition is not equal, the external runnable parameter will run.
     */
    public static <X extends Throwable> void isEqual(Object object1, Object object2, Supplier<? extends X> exceptionSupplier, Runnable runnable) throws X {
        if (Objects.equals(object1, object2)) {
            return;
        }

        if (runnable != null) {
            runnable.run();
        }

        throw exceptionSupplier.get();
    }

    /**
     * <pre>
     * Assert.isEqual(Status.ACTIVE, Status.ACTIVE)     = correct
     * Assert.isEqual("Text1", "Text1")                 = correct
     * Assert.isEqual(Status.ACTIVE, Status.PASSIVE)    = incorrect
     * Assert.isEqual("Text1", "Text2")                 = incorrect
     * </pre>
     *
     * @param object1           The first object to check for equality.
     * @param object2           The second object to check for equality.
     * @param exceptionSupplier Exception that it will return when it is wrong.
     */
    public static <X extends Throwable> void isEqual(Object object1, Object object2, Supplier<? extends X> exceptionSupplier) throws X {
        Assert.isEqual(object1, object2, exceptionSupplier, null);
    }

    /**
     * <pre>
     * Assert.isNotEqual(Status.ACTIVE, Status.ACTIVE)     = incorrect
     * Assert.isNotEqual("Text1", "Text1")                 = incorrect
     * Assert.isNotEqual(Status.ACTIVE, Status.PASSIVE)    = correct
     * Assert.isNotEqual("Text1", "Text2")                 = correct
     * </pre>
     *
     * @param object1                      The first object to check for equality.
     * @param object2                      The second object to check for equality.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @param runnable                     If condition is equal, the external runnable parameter will run.
     * @throws BusinessException           If object is equal then throws BusinessException.
     */
    public static void isNotEqual(Object object1, Object object2, BusinessExceptionKey businessExceptionKey, Runnable runnable) {
        if (!Objects.equals(object1, object2)) {
            return;
        }

        if (runnable != null) {
            runnable.run();
        }

        throw new BusinessException(businessExceptionKey);
    }

    /**
     * <pre>
     * Assert.isNotEqual(Status.ACTIVE, Status.ACTIVE)     = incorrect
     * Assert.isNotEqual("Text1", "Text1")                 = incorrect
     * Assert.isNotEqual(Status.ACTIVE, Status.PASSIVE)    = correct
     * Assert.isNotEqual("Text1", "Text2")                 = correct
     * </pre>
     *
     * @param object1                      The first object to check for equality.
     * @param object2                      The second object to check for equality.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @throws BusinessException           If object is equal then throws BusinessException.
     */
    public static void isNotEqual(Object object1, Object object2, BusinessExceptionKey businessExceptionKey) {
        Assert.isNotEqual(object1, object2, businessExceptionKey, null);
    }

    /**
     * @param zonedDateTime1               The first object to check for equality.
     * @param zonedDateTime2               The second object to check for equality.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @param runnable                     If condition is not after, the external runnable parameter will run.
     * @throws BusinessException           If object is not after then throws BusinessException.
     */
    public static void isAfter(ZonedDateTime zonedDateTime1, ZonedDateTime zonedDateTime2, BusinessExceptionKey businessExceptionKey, Runnable runnable) {
        if (zonedDateTime1.isAfter(zonedDateTime2)) {
            return;
        }

        if (runnable != null) {
            runnable.run();
        }

        throw new BusinessException(businessExceptionKey);
    }

    /**
     * @param zonedDateTime1               The first object to check for equality.
     * @param zonedDateTime2               The second object to check for equality.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @throws BusinessException           If object is not after then throws BusinessException.
     */
    public static void isAfter(ZonedDateTime zonedDateTime1, ZonedDateTime zonedDateTime2, BusinessExceptionKey businessExceptionKey) {
        Assert.isAfter(zonedDateTime1, zonedDateTime2, businessExceptionKey, null);
    }

    /**
     * @param zonedDateTime1               The first object to check for equality.
     * @param zonedDateTime2               The second object to check for equality.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @param runnable                     If condition is not before, the external runnable parameter will run.
     * @throws BusinessException           If object is not before then throws BusinessException.
     */
    public static void isBefore(ZonedDateTime zonedDateTime1, ZonedDateTime zonedDateTime2, BusinessExceptionKey businessExceptionKey, Runnable runnable) {
        if (zonedDateTime1.isBefore(zonedDateTime2)) {
            return;
        }

        if (runnable != null) {
            runnable.run();
        }

        throw new BusinessException(businessExceptionKey);
    }

    /**
     * @param zonedDateTime1               The first object to check for equality.
     * @param zonedDateTime2               The second object to check for equality.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @throws BusinessException           If object is not before then throws BusinessException.
     */
    public static void isBefore(ZonedDateTime zonedDateTime1, ZonedDateTime zonedDateTime2, BusinessExceptionKey businessExceptionKey) {
        Assert.isBefore(zonedDateTime1, zonedDateTime2, businessExceptionKey, null);
    }

    /**
     * <pre>
     * Assert.isMore(1, 2)      = incorrect
     * Assert.isMore(1, 1)      = incorrect
     * Assert.isMore(2, 1)      = correct
     * </pre>
     *
     * @param integer1                     The first number to check for magnitude.
     * @param integer2                     The second number to check for magnitude.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @param runnable                     If condition is not more, the external runnable parameter will run.
     * @throws BusinessException           If object is not more then throws BusinessException.
     */
    public static void isMore(Integer integer1, Integer integer2, BusinessExceptionKey businessExceptionKey, Runnable runnable) {
        if (integer1 > integer2) {
            return;
        }

        if (runnable != null) {
            runnable.run();
        }

        throw new BusinessException(businessExceptionKey);
    }

    /**
     * <pre>
     * Assert.isMore(1, 2)      = incorrect
     * Assert.isMore(1, 1)      = incorrect
     * Assert.isMore(2, 1)      = correct
     * </pre>
     *
     * @param integer1                     The first number to check for magnitude.
     * @param integer2                     The second number to check for magnitude.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @throws BusinessException           If object is not more then throws BusinessException.
     */
    public static void isMore(Integer integer1, Integer integer2, BusinessExceptionKey businessExceptionKey) {
        Assert.isMore(integer1, integer2, businessExceptionKey, null);
    }

    /**
     * <pre>
     * Assert.isMoreOrEqual(1, 2)      = incorrect
     * Assert.isMoreOrEqual(1, 1)      = correct
     * Assert.isMoreOrEqual(2, 1)      = correct
     * </pre>
     *
     * @param integer1                     The first number to check for magnitude.
     * @param integer2                     The second number to check for magnitude.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @param runnable                     If condition is not more or equal, the external runnable parameter will run.
     * @throws BusinessException           If object is not more or equal then throws BusinessException.
     */
    public static void isMoreOrEqual(Integer integer1, Integer integer2, BusinessExceptionKey businessExceptionKey, Runnable runnable) {
        if (integer1 >= integer2) {
            return;
        }

        if (runnable != null) {
            runnable.run();
        }

        throw new BusinessException(businessExceptionKey);
    }

    /**
     * <pre>
     * Assert.isMoreOrEqual(1, 2)      = incorrect
     * Assert.isMoreOrEqual(1, 1)      = correct
     * Assert.isMoreOrEqual(2, 1)      = correct
     * </pre>
     *
     * @param integer1                     The first number to check for magnitude.
     * @param integer2                     The second number to check for magnitude.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @throws BusinessException           If object is not more or equal then throws BusinessException.
     */
    public static void isMoreOrEqual(Integer integer1, Integer integer2, BusinessExceptionKey businessExceptionKey) {
        Assert.isMoreOrEqual(integer1, integer2, businessExceptionKey, null);
    }

    /**
     * <pre>
     * Assert.isLess(2, 1)      = incorrect
     * Assert.isLess(1, 1)      = incorrect
     * Assert.isLess(1, 2)      = correct
     * </pre>
     *
     * @param integer1                     The first number to check for magnitude.
     * @param integer2                     The second number to check for magnitude.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @param runnable                     If condition is not less, the external runnable parameter will run.
     * @throws BusinessException           If object is not less then throws BusinessException.
     */
    public static void isLess(Integer integer1, Integer integer2, BusinessExceptionKey businessExceptionKey, Runnable runnable) {
        if (integer1 < integer2) {
            return;
        }

        if (runnable != null) {
            runnable.run();
        }

        throw new BusinessException(businessExceptionKey);
    }

    /**
     * <pre>
     * Assert.isLess(2, 1)      = incorrect
     * Assert.isLess(1, 1)      = incorrect
     * Assert.isLess(1, 2)      = correct
     * </pre>
     *
     * @param integer1                     The first number to check for magnitude.
     * @param integer2                     The second number to check for magnitude.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @throws BusinessException           If object is not less then throws BusinessException.
     */
    public static void isLess(Integer integer1, Integer integer2, BusinessExceptionKey businessExceptionKey) {
        Assert.isLess(integer1, integer2, businessExceptionKey, null);
    }

    /**
     * <pre>
     * Assert.isLessOrEqual(2, 1)      = incorrect
     * Assert.isLessOrEqual(1, 1)      = correct
     * Assert.isLessOrEqual(1, 2)      = correct
     * </pre>
     *
     * @param integer1                     The first number to check for magnitude.
     * @param integer2                     The second number to check for magnitude.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @param runnable                     If condition is not less or equal, the external runnable parameter will run.
     * @throws BusinessException           If object is not less or equal then throws BusinessException.
     */
    public static void isLessOrEqual(Integer integer1, Integer integer2, BusinessExceptionKey businessExceptionKey, Runnable runnable) {
        if (integer1 <= integer2) {
            return;
        }

        if (runnable != null) {
            runnable.run();
        }

        throw new BusinessException(businessExceptionKey);
    }

    /**
     * <pre>
     * Assert.isLessOrEqual(2, 1)      = incorrect
     * Assert.isLessOrEqual(1, 1)      = correct
     * Assert.isLessOrEqual(1, 2)      = correct
     * </pre>
     *
     * @param integer1                     The first number to check for magnitude.
     * @param integer2                     The second number to check for magnitude.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @throws BusinessException           If object is not less or equal then throws BusinessException.
     */
    public static void isLessOrEqual(Integer integer1, Integer integer2, BusinessExceptionKey businessExceptionKey) {
        Assert.isLessOrEqual(integer1, integer2, businessExceptionKey, null);
    }

    /**
     * <pre>
     * Assert.isLess(2, 1)      = incorrect
     * Assert.isLess(1, 1)      = incorrect
     * Assert.isLess(1, 2)      = correct
     * </pre>
     *
     * @param bigDecimal1                  The first number to check for magnitude.
     * @param bigDecimal2                  The second number to check for magnitude.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @param runnable                     If condition is not less, the external runnable parameter will run.
     * @throws BusinessException           If object is not less then throws BusinessException.
     */
    public static void isLess(BigDecimal bigDecimal1, BigDecimal bigDecimal2, BusinessExceptionKey businessExceptionKey, Runnable runnable) {
        if (bigDecimal1.compareTo(bigDecimal2) < 0) {
            return;
        }

        if (runnable != null) {
            runnable.run();
        }

        throw new BusinessException(businessExceptionKey);
    }

    /**
     * <pre>
     * Assert.isLess(2, 1)      = incorrect
     * Assert.isLess(1, 1)      = incorrect
     * Assert.isLess(1, 2)      = correct
     * </pre>
     *
     * @param bigDecimal1                  The first number to check for magnitude.
     * @param bigDecimal2                  The second number to check for magnitude.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @throws BusinessException           If object is not less then throws BusinessException.
     */
    public static void isLess(BigDecimal bigDecimal1, BigDecimal bigDecimal2, BusinessExceptionKey businessExceptionKey) {
        Assert.isLess(bigDecimal1, bigDecimal2, businessExceptionKey, null);
    }

    /**
     * <pre>
     * Assert.isLessOrEqual(2, 1)      = incorrect
     * Assert.isLessOrEqual(1, 1)      = correct
     * Assert.isLessOrEqual(1, 2)      = correct
     * </pre>
     *
     * @param bigDecimal1                  The first number to check for magnitude.
     * @param bigDecimal2                  The second number to check for magnitude.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @param runnable                     If condition is not less or equal, the external runnable parameter will run.
     * @throws BusinessException           If object is not less or equal then throws BusinessException.
     */
    public static void isLessOrEqual(BigDecimal bigDecimal1, BigDecimal bigDecimal2, BusinessExceptionKey businessExceptionKey, Runnable runnable) {
        if (bigDecimal1.compareTo(bigDecimal2) <= 0) {
            return;
        }

        if (runnable != null) {
            runnable.run();
        }

        throw new BusinessException(businessExceptionKey);
    }

    /**
     * <pre>
     * Assert.isLessOrEqual(2, 1)      = incorrect
     * Assert.isLessOrEqual(1, 1)      = correct
     * Assert.isLessOrEqual(1, 2)      = correct
     * </pre>
     *
     * @param bigDecimal1                  The first number to check for magnitude.
     * @param bigDecimal2                  The second number to check for magnitude.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @throws BusinessException           If object is not less or equal then throws BusinessException.
     */
    public static void isLessOrEqual(BigDecimal bigDecimal1, BigDecimal bigDecimal2, BusinessExceptionKey businessExceptionKey) {
        Assert.isLessOrEqual(bigDecimal1, bigDecimal2, businessExceptionKey, null);
    }

    /**
     * <pre>
     * Assert.isTrue(Boolean.TRUE)  = correct
     * Assert.isTrue(true)          = correct
     * Assert.isTrue(Boolean.FALSE) = incorrect
     * Assert.isTrue(false)         = incorrect
     * </pre>
     *
     * @param value                        The value to check.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @param runnable                     If condition is false, the external runnable parameter will run.
     * @throws BusinessException           If object is false then throws BusinessException.
     */
    public static void isTrue(Boolean value, BusinessExceptionKey businessExceptionKey, Runnable runnable) {
        if (value) {
            return;
        }

        if (runnable != null) {
            runnable.run();
        }

        throw new BusinessException(businessExceptionKey);
    }

    /**
     * <pre>
     * Assert.isTrue(Boolean.TRUE)  = correct
     * Assert.isTrue(true)          = correct
     * Assert.isTrue(Boolean.FALSE) = incorrect
     * Assert.isTrue(false)         = incorrect
     * </pre>
     *
     * @param value                        The value to check.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @throws BusinessException           If object is false then throws BusinessException.
     */
    public static void isTrue(Boolean value, BusinessExceptionKey businessExceptionKey) {
        Assert.isTrue(value, businessExceptionKey, null);
    }

    /**
     * <pre>
     * Assert.isFalse(Boolean.FALSE) = correct
     * Assert.isFalse(false)         = correct
     * Assert.isFalse(Boolean.TRUE)  = incorrect
     * Assert.isFalse(true)          = incorrect
     * </pre>
     *
     * @param value                        The value to check.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @param runnable                     If condition is true, the external runnable parameter will run.
     * @throws BusinessException           If object is true then throws BusinessException.
     */
    public static void isFalse(Boolean value, BusinessExceptionKey businessExceptionKey, Runnable runnable) {
        if (!value) {
            return;
        }

        if (runnable != null) {
            runnable.run();
        }

        throw new BusinessException(businessExceptionKey);
    }

    /**
     * <pre>
     * Assert.isFalse(Boolean.FALSE) = correct
     * Assert.isFalse(false)         = correct
     * Assert.isFalse(Boolean.TRUE)  = incorrect
     * Assert.isFalse(true)          = incorrect
     * </pre>
     *
     * @param value                        The value to check.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @throws BusinessException           If object is true then throws BusinessException.
     */
    public static void isFalse(Boolean value, BusinessExceptionKey businessExceptionKey) {
        Assert.isFalse(value, businessExceptionKey, null);
    }

    /**
     * <pre>
     * Assert.isNumber("AAA")           = incorrect
     * Assert.isNumber(1)               = correct
     * Assert.isNumber(Boolean.TRUE)    = incorrect
     * Assert.isNumber(true)            = incorrect
     * </pre>
     *
     * @param value                        The value to check.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @param runnable                     If condition is not number, the external runnable parameter will run.
     * @throws BusinessException           If object is not number then throws BusinessException.
     */
    public static void isNumber(String value, BusinessExceptionKey businessExceptionKey, Runnable runnable) {
        boolean isNumeric = value.chars().allMatch(Character::isDigit);

        if (isNumeric && !"".equals(value.trim())) {
            return;
        }

        if (runnable != null) {
            runnable.run();
        }

        throw new BusinessException(businessExceptionKey);
    }

    /**
     * <pre>
     * Assert.isNumber("AAA")           = incorrect
     * Assert.isNumber(1)               = correct
     * Assert.isNumber(Boolean.TRUE)    = incorrect
     * Assert.isNumber(true)            = incorrect
     * </pre>
     *
     * @param value                        The value to check.
     * @param businessExceptionKey         The BusinessException key to return if false.
     * @throws BusinessException           If object is not number then throws BusinessException.
     */
    public static void isNumber(String value, BusinessExceptionKey businessExceptionKey) {
        Assert.isNumber(value, businessExceptionKey, null);
    }

    /**
     * @param input : The value to check.
     * @return boolean : Can it be converted to an integer?
     */
    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param input : The value to check.
     * @return boolean : Can it be converted to an long?
     */
    public static boolean isLong(String input) {
        try {
            Long.parseLong(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param input : The value to check.
     * @return boolean : Can it be converted to an BigDecimal?
     */
    public static boolean isBigDecimal(String input) {
        try {
            new BigDecimal(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param input : The value to check.
     * @return boolean : Is the input value true or false?
     */
    public static boolean isBoolean(String input) {
        return StringUtils.isNotBlank(input) && (input.toLowerCase().equals("true") || input.toLowerCase().equals("false"));
    }

    public static <X extends Throwable> void isInstanceOf(Object object, Class<?> clazz, Supplier<? extends X> exceptionSupplier) throws X {
        if (!(clazz.isInstance(object))) {
            log.error("The argument is null or cannot be cast to the type of the receiver, object : {}", object);
            throw exceptionSupplier.get();
        }
    }
}
