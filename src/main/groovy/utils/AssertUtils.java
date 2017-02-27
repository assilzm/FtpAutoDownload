package utils;

import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.StringDescription;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;


/**
 * 单元测试断言类
 * $author weiwei
 * $date 2015-07-16
 */
public class AssertUtils {

    /**
     * 生成自定义的断言异常信息，期望值和实际值对比
     * @param reason 断言说明
     * @param actualString 实际值
     * @param matcherString 断言方法
     * @param expectString 期望值
     */
    public static void throwAssertMessage(String reason, String actualString , String matcherString, String expectString){
        StringDescription description = new StringDescription();
        description.appendText(reason).appendText("\nexcept: ").appendText(actualString).appendText(" ").appendText(matcherString).appendText(" ").appendText(expectString);
        throwAssertMessage(description.toString());
    }

    /**
     * 抛出自定义的断言异常信息
     * @param message 需要抛出的信息
     */
    public static void throwAssertMessage(String message){
        throw new AssertionError(message);
    }

    /**
     * 生成自定义的断言异常信息，期望值满足某种条件
     * @param reason 断言说明
     * @param actualString 实际值
     * @param matcherString 断言方法
     */
    public static void throwAssertMessage(String reason, String actualString, String matcherString){
        throwAssertMessage(reason,actualString,matcherString,"");
    }

    /**
     * 简单封装Junit的assertThat断言
     *
     * @param actual  实际值
     * @param matcher 匹配器
     * @param <T>
     */
    public static <T> void assertThat(T actual, Matcher<? super T> matcher) {
        MatcherAssert.assertThat(actual, matcher);
    }

    /**
     * 简单封装Junit的assertThat断言
     *
     * @param reason  断言说明
     * @param actual  实际值
     * @param matcher 匹配器
     * @param <T>
     */
    public static <T> void assertThat(String reason, T actual, Matcher<? super T> matcher) {
        MatcherAssert.assertThat(reason, actual, matcher);
    }

    /**
     * 简单封装Junit的assertThat断言
     *
     * @param reason    断言说明
     * @param assertion 匹配结果
     */
    public static <T> void assertThat(String reason, boolean assertion) {
        MatcherAssert.assertThat(reason, assertion);
    }

    /**
     * 验证实际值大于期望值
     *
     * @param reason 断言说明
     * @param actual 实际值
     * @param expect 期望值
     */
    public static void assertGreater(String reason, int actual, int expect) {
        assertThat(reason, actual, greaterThan(expect));
    }

    /**
     * 验证实际值小于期望值
     *
     * @param reason 断言说明
     * @param actual 实际值
     * @param expect 期望值
     */
    public static void assertLess(String reason, int actual, int expect) {
        assertThat(reason, actual, lessThan(expect));
    }


    /**
     * 验证实际值等于期望值
     *
     * @param reason 断言说明
     * @param actual 实际值
     * @param expect 期望值
     */
    public static void assertEquals(String reason, int actual, int expect) {
        assertThat(reason, actual, equalTo(expect));
    }

    /**
     * 验证实际值大于期望值
     *
     * @param reason 断言说明
     * @param actual 实际值
     * @param expect 期望值
     */
    public static void assertGreater(String reason, long actual, long expect) {
        assertThat(reason, actual, greaterThan(expect));
    }


    /**
     * 验证实际值等于期望值
     *
     * @param reason 断言说明
     * @param actual 实际值
     * @param expect 期望值
     */
    public static void assertEquals(String reason, long actual, long expect) {
        assertThat(reason, actual, equalTo(expect));
    }

    /**
     * 验证实际对象是期望的对象
     *
     * @param reason 断言说明
     * @param actual 实际对象
     * @param expect 期望对象
     */
    public static void assertEquals(String reason, Object actual, Object expect) {
        assertThat(reason, actual, equalTo(expect));
    }

    /**
     * 验证实际对象不是期望的对象
     *
     * @param reason 断言说明
     * @param actual 实际对象
     * @param expect 期望对象
     */
    public static void assertNotEquals(String reason, Object actual, Object expect) {
        assertThat(reason, actual, not(expect));
    }

    /**
     * 验证字符串为空
     *
     * @param reason 断言说明
     * @param actual 实际值
     */
    public static void assertStringIsEmpty(String reason, String actual) {
        assertThat(reason, actual, isEmptyString());

    }

    /**
     * 验证集合相等,不验证顺序
     *
     * @param reason 断言说明
     * @param actual 实际集合
     * @param expect 期望集合
     */
    public static void assertCollectionEquals(String reason, Collection actual, Collection expect) {
        Object[] actualArr = actual.toArray();
        Arrays.sort(actualArr);
        Object[] expectArr = expect.toArray();
        Arrays.sort(expectArr);
        assertThat(reason, actualArr, equalTo(expectArr));
    }

    /**
     * 验证集合相等，验证顺序
     *
     * @param reason 断言说明
     * @param actual 实际集合
     * @param expect 期望集合
     */
    public static void assertCollectionEqualsBySequence(String reason, Collection actual, Collection expect) {
        assertThat(reason, actual, equalTo(expect));
    }
    /**
     * 验证集合中有某对象
     *
     * @param reason 断言说明
     * @param actual 实际集合
     * @param expect 期望对象
     */
    public static void assertCollectionContainsObject(String reason, Collection actual, Object expect) {
        if (!actual.contains(expect))
            throwAssertMessage(reason,actual.toString(),"contains",expect.toString());
    }

    /**
     * 验证集合中有某集合
     *
     * @param reason 断言说明
     * @param actual 实际集合
     * @param expect 期望集合
     */
    public static void assertCollectionContainsCollection(String reason, Collection actual, Collection expect) {
        if (!actual.containsAll(expect))
            throwAssertMessage(reason,actual.toString(),"contains",expect.toString());
    }


    /**
     * 验证集合中没有某对象
     *
     * @param reason 断言说明
     * @param actual 实际集合
     * @param expect 期望值
     */
    public static void assertCollectionNotContainsObject(String reason, Collection actual, Object expect) {
        if (actual.contains(expect))
            throwAssertMessage(reason,actual.toString(),"not contains",expect.toString());
    }

    /**
     * 验证集合中没有某集合
     *
     * @param reason 断言说明
     * @param actual 实际集合
     * @param expect 期望集合
     */
    public static void assertCollectionNotContainsCollection(String reason, Collection actual, Collection expect) {
        if (actual.containsAll(expect))
            throwAssertMessage(reason,actual.toString(),"not contains",expect.toString());
    }

    /**
     * 验证对象在给定的集合中
     *
     * @param reason 断言说明
     * @param actual 实际集合
     * @param expect 期望集合
     */
    public static void assertObjectInCollection(String reason, Object actual, Collection expect) {
        if (!expect.contains(actual))
            throwAssertMessage(reason,actual.toString(),"in",expect.toString());
    }

    /**
     * 验证对象不在给定的集合中
     *
     * @param reason 断言说明
     * @param actual 实际集合
     * @param expect 期望集合
     */
    public static void assertObjectNotInCollection(String reason, Object actual, Collection expect) {
        if (expect.contains(actual))
            throwAssertMessage(reason,actual.toString(),"not in",expect.toString());
    }


    /**
     * 验证集合在给定的集合中
     *
     * @param reason 断言说明
     * @param actual 实际集合
     * @param expect 期望集合
     */
    public static void assertCollectionInCollection(String reason, Collection actual, Collection expect) {
        if (!expect.containsAll(actual))
            throwAssertMessage(reason,actual.toString(),"in",expect.toString());
    }

    /**
     * 验证集合不在给定的集合中
     *
     * @param reason 断言说明
     * @param actual 实际集合
     * @param expect 期望集合
     */
    public static void assertColletiontNotInCollection(String reason, Collection actual, Collection expect) {
        if (expect.containsAll(actual))
            throwAssertMessage(reason,actual.toString(),"not in",expect.toString());
    }

    /**
     * 验证字符串中含有期望的字串
     *
     * @param reason 断言说明
     * @param actual 需要验证的字符串
     * @param expect 期望的字串
     */
    public static void assertStringContainsString(String reason, String actual, String expect) {
        assertThat(reason, actual, containsString(expect));
    }

    /**
     * 验证值为真
     *
     * @param reason 断言说明
     * @param actual 实际值
     */
    public static void assertTrue(String reason, boolean actual) {
        assertThat(reason, actual, is(true));

    }

    /**
     * 验证值为假
     *
     * @param reason 断言说明
     * @param actual 实际值
     */
    public static void assertFalse(String reason, boolean actual) {
        assertThat(reason, actual, is(false));
    }

    /**
     * 验证对象为null
     *
     * @param reason 断言说明
     * @param actual 实际对象
     */
    public static void assertNull(String reason, Object actual) {
        assertThat(reason, actual, nullValue());

    }

    /**
     * 验证对象不为null
     *
     * @param reason 断言说明
     * @param actual 实际值
     */
    public static void assertNotNull(String reason, Object actual) {
        assertThat(reason, actual, notNullValue());

    }

    /**
     * 验证集合为空
     *
     * @param reason 断言说明
     * @param actual 集合
     */
    public static void assertCollectionNotEmpty(String reason, Collection actual) {
        if (actual.size() == 0)
            throwAssertMessage(reason,actual.toString(),"is not empty");
    }


    /**
     * 验证集合不为空
     *
     * @param reason 断言说明
     * @param actual 实际值
     */
    public static void assertCollectionIsEmpty(String reason, Collection actual) {
        if (actual.size() != 0)
            throwAssertMessage(reason,actual.toString(),"is empty");
    }

    /**
     * 验证字符串为数字或小数
     *
     * @param reason 断言说明
     * @param actual 实际值
     */
    public static void assertStringIsNumber(String reason, String actual) {
        if (actual.matches("\\d+(\\.\\d+)?"))
            throwAssertMessage(reason,actual,"is number");
    }




    /**
     * 验证实际对象是期望的对象
     *
     * @param actual 实际对象
     * @param expect 期望对象
     */
    public static void assertEquals(long actual, long expect) {
        assertEquals("", actual, expect);
    }

    /**
     * 验证实际对象是期望的对象
     *
     * @param actual 实际对象
     * @param expect 期望对象
     */
    public static void assertEquals(int actual, int expect) {
        assertEquals("", actual, expect);
    }


    /**
     * 验证实际值大于期望值
     *
     * @param actual 实际值
     * @param expect 期望值
     */
    public static void assertGreater(int actual, int expect) {
        assertGreater("", actual, expect);
    }

    /**
     * 验证实际值小于期望值
     *
     * @param actual 实际值
     * @param expect 期望值
     */
    public static void assertLess(int actual, int expect) {
        assertLess("", actual, expect);
    }

    /**
     * 验证实际值大于期望值
     *
     * @param actual 实际值
     * @param expect 期望值
     */
    public static void assertGreater(long actual, long expect) {
        assertGreater("", actual, expect);
    }

    /**
     * 验证实际对象是期望的对象
     *
     * @param actual 实际对象
     * @param expect 期望对象
     */
    public static void assertEquals(Object actual, Object expect) {
        assertEquals("", actual, expect);
    }

    /**
     * 验证实际对象不是期望的对象
     *
     * @param actual 实际对象
     * @param expect 期望对象
     */
    public static void assertNotEquals(Object actual, Object expect) {
        assertNotEquals("", actual, expect);

    }

    /**
     * 验证字符串为空
     *
     * @param actual 实际值
     */
    public static void assertStringIsEmpty(String actual) {
        assertStringIsEmpty("", actual);

    }

    /**
     * 验证集合相等,不验证顺序
     *
     * @param actual 实际集合
     * @param expect 期望集合
     */
    public static void assertCollectionEquals(Collection actual, Collection expect) {
        assertCollectionEquals("", actual, expect);
    }

    /**
     * 验证集合相等，验证顺序
     *
     * @param actual 实际集合
     * @param expect 期望集合
     */
    public static void assertCollectionEqualsBySequence(Collection actual, Collection expect) {
        assertCollectionEqualsBySequence("", actual, expect);

    }

    /**
     * 验证集合中有某对象
     *
     * @param actual 实际集合
     * @param expect 期望对象
     */
    public static void assertCollectionContainsObject(Collection actual, Object expect) {
        assertCollectionContainsObject("", actual, expect);

    }

    /**
     * 验证集合中没有某对象
     *
     * @param actual 实际集合
     * @param expect 期望值
     */
    public static void assertCollectionNotContainsObject(Collection actual, Object expect) {
        assertCollectionNotContainsObject("", actual, expect);

    }

    /**
     * 验证对象在给定的集合中
     *
     * @param actual 实际集合
     * @param expect 期望值
     */
    public static void assertObjectInCollection(Object actual, Collection expect) {
        assertObjectInCollection("", actual, expect);

    }

    /**
     * 验证字符串中含有期望的字串
     *
     * @param actual 需要验证的字符串
     * @param expect 期望的字串
     */
    public static void assertStringContainsString(String actual, String expect) {
        assertStringContainsString("", actual, expect);

    }

    /**
     * 验证值为真
     *
     * @param actual 实际值
     */
    public static void assertTrue(boolean actual) {
        assertTrue("", actual);

    }

    /**
     * 验证值为假
     *
     * @param actual 实际值
     */
    public static void assertFalse(boolean actual) {
        assertFalse("", actual);

    }

    /**
     * 验证对象为null
     *
     * @param actual 实际对象
     */
    public static void assertNull(Object actual) {
        assertNull("", actual);

    }

    /**
     * 验证对象不为null
     *
     * @param actual 实际值
     */
    public static void assertNotNull(Object actual) {
        assertNotNull("", actual);

    }

    /**
     * 验证集合为空
     *
     * @param actual 集合
     */
    public static void assertCollectionNotEmpty(Collection actual) {
        assertCollectionNotEmpty("", actual);

    }

    /**
     * 验证集合不为空
     *
     * @param actual 实际值
     */
    public static void assertCollectionIsEmpty(Collection actual) {
        assertCollectionIsEmpty("", actual);

    }

    /**
     * 验证字符串为数字或小数
     *
     * @param actual 实际值
     */
    public static void assertStringIsNumber(String actual) {
        assertStringIsNumber("", actual);

    }

    /**
     * 验证集合中有某集合
     *
     * @param actual 实际集合
     * @param expect 期望集合
     */
    public static void assertCollectionContainsCollection(Collection actual, Collection expect) {
        assertCollectionContainsCollection("", actual, expect);
    }


    /**
     * 验证集合中没有某集合
     *
     * @param actual 实际集合
     * @param expect 期望集合
     */
    public static void assertCollectionNotContainsCollection(Collection actual, Collection expect) {
        assertCollectionNotContainsCollection("", actual, expect);
    }

    /**
     * 验证对象不在给定的集合中
     *
     * @param actual 实际集合
     * @param expect 期望集合
     */
    public static void assertObjectNotInCollection(Object actual, Collection expect) {
        assertObjectNotInCollection("", actual, expect);
    }


    /**
     * 验证集合在给定的集合中
     *
     * @param actual 实际集合
     * @param expect 期望集合
     */
    public static void assertCollectionInCollection(Collection actual, Collection expect) {
        assertCollectionInCollection("", actual, expect);
    }




}
