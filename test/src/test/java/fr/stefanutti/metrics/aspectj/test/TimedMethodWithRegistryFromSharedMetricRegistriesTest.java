package fr.stefanutti.metrics.aspectj.test;


import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SharedMetricRegistries;
import com.codahale.metrics.Timer;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class TimedMethodWithRegistryFromSharedMetricRegistriesTest {

    private TimedMethodWithRegistryFromSharedMetricRegistries instance;

    @Before
    public void createAtMetricsInstance() {
        instance = new TimedMethodWithRegistryFromSharedMetricRegistries();
    }

    @Test
    public void assertTimerWithZeroCount() {
        assertThat(SharedMetricRegistries.names(), hasItem("staticRegistry"));
        MetricRegistry registry = SharedMetricRegistries.getOrCreate("staticRegistry");
        assertThat(registry.getTimers(), hasKey("singleTimedMethod"));
        Timer timer = registry.getTimers().get("singleTimedMethod");

        // Make sure that the timer hasn't been called yet
        assertThat(timer.getCount(), is(equalTo(0L)));
    }

    @Test
    public void assertTimerWithOneCountAfterMethodInvocation() {
        assertThat(SharedMetricRegistries.names(), hasItem("staticRegistry"));
        MetricRegistry registry = SharedMetricRegistries.getOrCreate("staticRegistry");
        assertThat(registry.getTimers(), hasKey("singleTimedMethod"));
        Timer timer = registry.getTimers().get("singleTimedMethod");

        // Call the timed method and assert it's been timed
        instance.singleTimedMethod();
        assertThat(timer.getCount(), is(equalTo(1L)));
    }
}