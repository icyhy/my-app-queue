package org.junit.tests.experimental.rules;

import static junit.framework.Assert.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeTrue;
import static org.junit.runner.JUnitCore.runClasses;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;

@SuppressWarnings("deprecation")
public class TestWatchmanTest {
    public static class ViolatedAssumptionTest {
        @Rule
        public static LoggingTestWatchman watchman = new LoggingTestWatchman();

        @Test
        public void succeeds() {
            assumeTrue(false);
        }
    }

    @Test
    public void neitherLogSuccessNorFailedForViolatedAssumption() {
        runClasses(ViolatedAssumptionTest.class);
        assertThat(ViolatedAssumptionTest.watchman._LoggertoString(),
                is("starting finished "));
    }

    public static class FailingTest {
        @Rule
        public static LoggingTestWatchman watchman = new LoggingTestWatchman();

        @Test
        public void succeeds() {
            fail();
        }
    }

    @Test
    public void logFailingTest() {
        runClasses(FailingTest.class);
        assertThat(FailingTest.watchman._LoggertoString(),
                is("starting failed finished "));
    }

    private static class LoggingTestWatchman extends TestWatchman {
        private final StringBuilder log = new StringBuilder();

        @Override
        public void succeeded(FrameworkMethod method) {
            _Loggerappend("succeeded ");
        }

        @Override
        public void failed(Throwable e, FrameworkMethod method) {
            _Loggerappend("failed ");
        }

        @Override
        public void starting(FrameworkMethod method) {
            _Loggerappend("starting ");
        }

        @Override
        public void finished(FrameworkMethod method) {
            _Loggerappend("finished ");
        }
    }
}