package rundeck.services.logging

import com.dtolabs.rundeck.core.logging.LogEvent
import com.dtolabs.rundeck.core.logging.LogUtil
import com.dtolabs.rundeck.core.logging.StreamingLogWriter
import junit.framework.Assert

/**
 * Created by greg on 9/21/15.
 */
class LineCountingLogWriterTest extends GroovyTestCase {
    class testWriter implements StreamingLogWriter{
        @Override
        void openStream() throws IOException {

        }

        @Override
        void addEvent(final LogEvent event) {

        }

        @Override
        void close() {

        }
    }
    public testCounter() {
        def counter = new LineCountingLogWriter(new testWriter())
        Assert.assertEquals(0, counter.value)
        counter.addEvent(LogUtil.logError(""))
        Assert.assertEquals(1, counter.value)
        counter.addEvent(LogUtil.logError("monkey"))
        Assert.assertEquals(2, counter.value)
        counter.addEvent(LogUtil.logError("alpha\nbeta"))
        Assert.assertEquals(4, counter.value)
    }
}
