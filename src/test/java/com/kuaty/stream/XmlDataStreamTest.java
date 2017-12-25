package com.kuaty.stream;

import org.cactoos.TextHasString;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

public final class XmlDataStreamTest {
    @Test
    public void xml() {
        MatcherAssert.assertThat(
            "Can't convert XmlDataStream to XML",
            new XmlDataStream("person")
                .with("firstname", "jeff")
                .with("lastname", "boom")
                .with("age", 32),
            new TextHasString(
                "<person><firstname>jeff</firstname>" +
                "<lastname>boom</lastname><age>32</age></person>"
            )
        );
    }

    @Test
    public void merge() {
        MatcherAssert.assertThat(
            "Can't convert XmlDataStream to XML",
            new XmlDataStream("person")
                .with("firstname", "jeff")
                .with("lastname", "boom")
                .with("age", 32)
                .merge(
                    new XmlDataStream("address")
                    .with("street", "5th Avenue")
                    .with("zip", "356392")
                ),
            new TextHasString(
                "<person><firstname>jeff</firstname>" +
                "<lastname>boom</lastname><age>32</age>" +
                "<address><street>5th Avenue</street>" +
                "<zip>356392</zip></address></person>"
            )
        );
    }

    @Test
    public void mergeOfMerge() {
        MatcherAssert.assertThat(
            "Can't convert XmlDataStream to XML",
            new XmlDataStream("person")
                .with("firstname", "jeff")
                .with("lastname", "boom")
                .with("age", 32)
                .merge(
                    new XmlDataStream("address")
                    .with("street", "5th Avenue")
                    .with("zip", "356392")
                    .merge(
                        new XmlDataStream("aaa")
                            .with("bbb", "kkkk")
                            .with("ccc", "yyyy")
                    )
                ),
            new TextHasString(
                "<person><firstname>jeff</firstname>" +
                "<lastname>boom</lastname><age>32</age>" +
                "<address><street>5th Avenue</street>" +
                "<zip>356392</zip><aaa><bbb>kkkk</bbb>" +
                "<ccc>yyyy</ccc></aaa></address></person>"
            )
        );
    }
}
