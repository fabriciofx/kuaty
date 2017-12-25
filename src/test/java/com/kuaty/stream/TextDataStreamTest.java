/**
 * 
 */
package com.kuaty.stream;

import org.cactoos.TextHasString;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * 
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 
 */
public final class TextDataStreamTest {
    @Test
    public void text() {
        MatcherAssert.assertThat(
            "Can't convert TextDataStream to Text",
            new TextDataStream("person")
                .with("firstname", "jeff")
                .with("lastname", "boom")
                .with("age", 32)
                .with("address.street", "5th Avenue"),
            new TextHasString(
                "person.firstname=jeff\n" +
                "person.address.street=5th Avenue\n" +
                "person.age=32\n" + 
                "person.lastname=boom\n"
            )
        );
    }

    @Test
    public void merge() {
        MatcherAssert.assertThat(
            "Can't convert TextDataStream to Text",
            new TextDataStream("person")
                .with("firstname", "jeff")
                .merge(
                    new TextDataStream("address")
                    .with("street", "5th Avenue")
                    .with("zip", "356392")
                )
                .with("lastname", "boom")
                .with("age", 32),
            new TextHasString(
                "person.address.zip=356392\n" +
                "person.firstname=jeff\n" +
                "person.address.street=5th Avenue\n" +
                "person.age=32\n" + 
                "person.lastname=boom\n"
            )
        );
    }
}
