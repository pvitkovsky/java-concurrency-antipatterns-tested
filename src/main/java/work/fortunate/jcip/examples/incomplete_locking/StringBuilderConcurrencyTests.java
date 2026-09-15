package work.fortunate.jcip.examples.incomplete_locking;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@JCStressTest
@Outcome(id = "0", expect = Expect.ACCEPTABLE, desc = "Invariant held.")
@Outcome(id = "-1", expect = Expect.FORBIDDEN, desc = "💥 Race Condition! StringBuilder has lost updates.")
@State
public class StringBuilderConcurrencyTests {
    @Actor
    public void stringBuilderThreadUnsafe(I_Result r) {
        StringBuilder sb = new StringBuilder();
        var unused = IntStream.range(0, 10)
                .parallel()
                .peek(sb::append) // don't do this! just to prove a point... https://stackoverflow.com/a/48559259
                .boxed()
                .collect(Collectors.toList());

        if (sb.toString().length() != 10) {
            r.r1 = -1;
        }
    }

    public void testAbstractStringBuilder(I_Result r) {
        StringBuilder builder = new StringBuilder();
        final long SIZE = 1000;
        final Supplier<String> GENERATOR = () -> "a";

        final CharSequence sequence = Stream
                .generate(GENERATOR)
                .parallel() // https://stackoverflow.com/a/48560152
                .limit(SIZE)
                .reduce(builder, StringBuilder::append, (b1, b2) -> b1);

        if(
                SIZE * GENERATOR.get().length() != sequence.toString().length()
        ){
            r.r1 = -1;
        }
    }
}
